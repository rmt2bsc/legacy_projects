package com.security;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;


import com.api.messaging.MessageException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapClientWrapper;

import com.api.security.UserSecurity;

import com.api.security.authentication.AbstractClientAuthentication;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.LoginException;
import com.api.security.authentication.LogoutException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.bindings.JaxbAccountingAdapter;
import com.bean.bindings.JaxbAccountingFactory;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationLogin;
import com.xml.schema.bindings.RQAuthenticationLogout;
import com.xml.schema.bindings.RQAuthenticationUserAppcheck;
import com.xml.schema.bindings.RSAuthenticationLogin;
import com.xml.schema.bindings.RSAuthenticationLogout;
import com.xml.schema.bindings.RSAuthenticationUserAppcheck;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.UserAppLoginCheckType;
import com.xml.schema.bindings.UserLoginType;
import com.xml.schema.bindings.UserLogoutType;
import com.xml.schema.bindings.UserSessionType;

import com.xml.schema.misc.PayloadFactory;

/**
 * An implementation of UserSecurity interface which provides functionality for a 
 * client to remotely log in - log out users and perform user authorization across 
 * application contexts.
 * 
 * @author RTerrell
 */
public class AccountingAuthenticator extends AbstractClientAuthentication implements UserSecurity {

    private static Logger logger =Logger.getLogger(AccountingAuthenticator.class);

    /**
     * Default constructor hat initializes the logger.
     *
     */
    public AccountingAuthenticator() {
	super();
	logger.log(Level.INFO, "Accounting Authenticator initialized");
    }
    
    /**
     * Constructs a RemoteAuthenticationConsumer that will possess an application context, 
     * the user's request object, and user's response object.
     * 
     * @param request The user's request.
     * @param response The user's response.
     */
    public void init(Request request, Response response) {
	super.init(request, response);
    }

    /**
     * Logs a user into the system using the SOAP web service, RQ_authentication_login.   The id of 
     * the service used to perform this task can be located in application's AppParm.properties file 
     * as the key, {@link com.api.config.HttpSystemPropertyConfig#PROPNAME_LOGINSRC}. 
     * 
     * @return {@link RMT2SessionBean} instance or an exception if an error occurs.
     * @throws LoginException For general service call errors.
     */
    public Object doLogin() throws LoginException {
	String loginId = this.reqProp.getProperty(AuthenticationConst.AUTH_PROP_USERID);
	String password = this.reqProp.getProperty(AuthenticationConst.AUTH_PROP_PASSWORD);
	String session = this.reqProp.getProperty(AuthenticationConst.AUTH_PROP_SESSIONID);
	String appName = this.reqProp.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
	ObjectFactory f = new ObjectFactory();
	SoapClientWrapper client = new SoapClientWrapper();
	try {
	    RQAuthenticationLogin ws = f.createRQAuthenticationLogin();
	    UserLoginType lt = f.createUserLoginType();
	    lt.setUid(loginId);
	    lt.setPw(password);
	    lt.setAppCode(appName);
	    lt.setSessionId(session);
	    HeaderType header = PayloadFactory.createHeader("RQ_authentication_login", "SYNC", "REQUEST", (loginId == null ? "" : loginId));
	    ws.setHeader(header);
	    ws.setLoginCredentials(lt);
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		logger.error(errMsg);
		throw new LoginException(errMsg);
	    }
	    RSAuthenticationLogin soapResp = (RSAuthenticationLogin) client.getSoapResponsePayload();
	    UserSessionType ust = soapResp.getSessionToken();
	    // Update UserSessionType with information from user's request instance
	    JaxbAccountingAdapter.updateUserSessionType(ust, this.request);
	    ReplyStatusType rst = soapResp.getReplyStatus();
	    logger.info(rst.getMessage());
	    // copy user session type data to RMT2SessionBean instance
	    RMT2SessionBean sessionBean = JaxbAccountingFactory.getUserSession(ust, this.request);
	    
	    this.sessionToken = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
	    return sessionBean;
	}
	catch (MessageException e) {
	    this.msg = "Accounting Authentication Error regarding server-side messaging";
	    logger.error(this.msg);
	    throw new LoginException(this.msg, e);
	}
    }
    
   
    /**
     * Starts the process of logging the user out of the current application by simply removing the 
     * RMT2SessionBean instance from the Session context.  Once the instance is removed, the logging 
     * out functionality is automatically triggered from the RMT2SessionBean's method, <i>valueUnbound(HttpSessionBindingEvent)</i> 
     * method since the session bean implements the HttpSessionBindingListener interface.   Logs the user out out of the system using the web service, RQ_authentication_logout.   The id 
     * of the service used to perform this task can be located in application's AppParm.properties 
     * file as the key, {@link com.api.config.HttpSystemPropertyConfig#PROPNAME_LOGINSRC}. 
     * 
     * @return 1
     * @throws LogoutException For general service call errors.
     */
    public int doLogout() throws LogoutException {
        RMT2SessionBean bean = this.removeSession();
        // Call authentication service to remove all entries from the 
       // application_access table that are related to the session id.
       return this.doLogout(bean.getLoginId(), bean.getSessionId());
    }
	
    

    /**
     * Logs the specified user, belonging to the session identified as <i>sessionId</i>, 
     * out of the application.  This method relies on the SOAP web service, RQ_authentication_logout, 
     * to handle the process of logging the user out of the current application.
     * 
     * @param loginId
     *         the login id of the user to log out.
     * @param sessionId
     *         the id of the user's session.
     * @return int
     * @throws LogoutException
     */
    public int doLogout(String loginId, String sessionId) throws LogoutException {
	ObjectFactory f = new ObjectFactory();
	SoapClientWrapper client = new SoapClientWrapper();
	ReplyStatusType rst = null;
	try {
	    RQAuthenticationLogout ws = f.createRQAuthenticationLogout();
	    UserLogoutType lt = f.createUserLogoutType();
	    lt.setUid(loginId);
	    lt.setSessionId(sessionId);
	    HeaderType header = PayloadFactory.createHeader("RQ_authentication_logout", "SYNC", "REQUEST", loginId);
	    ws.setHeader(header);
	    ws.setLogoutCredentials(lt);
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		logger.error(errMsg);
		throw new LogoutException(errMsg);
	    }
	    RSAuthenticationLogout soapResp = (RSAuthenticationLogout) client.getSoapResponsePayload();
	    rst = soapResp.getReplyStatus();
	    msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
	    return rst.getReturnCode().getValue().intValue();
	}
	catch (MessageException e) {
	    this.msg = "Accounting Authentication Error regarding server-side messaging";
            logger.error(this.msg);
	    throw new LogoutException(this.msg, e);
	}
    }
    
    public RMT2SessionBean getAuthentication() throws AuthenticationException {
        String loginId = this.reqProp.getProperty(AuthenticationConst.AUTH_PROP_USERID);
        String sessionId = this.reqProp.getProperty(AuthenticationConst.AUTH_PROP_SESSIONID);
        String appName = this.reqProp.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
        return this.getAuthentication(loginId, appName, sessionId);
    }
    
    public RMT2SessionBean getAuthentication(String userName, String appName, String sessionId) throws AuthenticationException {
	ObjectFactory f = new ObjectFactory();
	SoapClientWrapper client = new SoapClientWrapper();
	RSAuthenticationUserAppcheck soapResp = null;
	try {
	    RQAuthenticationUserAppcheck ws = f.createRQAuthenticationUserAppcheck();
	    UserAppLoginCheckType loginCred = f.createUserAppLoginCheckType();
	    loginCred.setUid(userName);
	    loginCred.setAppCode(appName);
	    loginCred.setSessionId(sessionId);
	    HeaderType header = PayloadFactory.createHeader("RQ_authentication_user_appcheck", "SYNC", "REQUEST", userName);
	    ws.setHeader(header);
	    ws.setUserCredentials(loginCred);
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		
		// Setup message for non-Java clients
                RSAuthenticationUserAppcheck errResp = f.createRSAuthenticationUserAppcheck();
                HeaderType h = PayloadFactory.createHeader("RS_authentication_user_appcheck", "SYNC", "RESPONSE", userName);
                ReplyStatusType rst = PayloadFactory.createReplyStatus(false, "Web service call successful", errMsg, -1);
                errResp.setHeader(h);
                errResp.setReplyStatus(rst);
                this.sessionToken = ResourceFactory.getJaxbMessageBinder().marshalMessage(errResp);
                
		logger.warn(errMsg);
		return null;
	    }
	    soapResp = (RSAuthenticationUserAppcheck) client.getSoapResponsePayload();
	}
	catch (MessageException e) {
	    this.msg = "Accounting Authentication Error regarding server-side messaging";
            logger.error(this.msg);
	    throw new AuthenticationException(this.msg, e);
	}
	
	try {
	    RMT2SessionBean session = AuthenticationFactory.getSessionBeanInstance(soapResp.getUserAuth().getUid(), 
                    soapResp.getUserAuth().getAppCode(), 
                    this.request, 
                    this.request.getSession(false));
	    return session;
	}
	catch (AuthenticationException e) {
	    this.msg = "Accounting Authentication Error obtaining the RMT2SessionBean instance";
            logger.error(this.msg);
	    throw new SystemException(this.msg, e);
	}
    }


    public void close() {
	return;
    }
    
    public boolean isAuthenticated(String userName) throws AuthenticationException {
	// TODO Auto-generated method stub
	return false;
    }
    
}

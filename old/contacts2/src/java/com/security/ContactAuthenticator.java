package com.security;


import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.messaging.MessageException;
import com.api.messaging.MessagingException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ProviderConnectionException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.http.client.HttpClientMessageException;
import com.api.messaging.webservice.http.client.HttpClientResourceFactory;
import com.api.messaging.webservice.http.client.HttpMessageSender;

import com.api.messaging.webservice.soap.client.SoapClientWrapper;

import com.api.security.UserSecurity;

import com.api.security.authentication.AbstractClientAuthentication;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.LoginException;
import com.api.security.authentication.LogoutException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.bindings.JaxbContactsFactory;

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
public class ContactAuthenticator extends AbstractClientAuthentication implements UserSecurity {

    private static Logger logger = Logger.getLogger(ContactAuthenticator.class);

    /**
     * Default constructor hat initializes the logger.
     *
     */
    public ContactAuthenticator() {
        super();
        logger.log(Level.INFO, "Authenticator initialized");
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
     * Remotely logs in a user into the system.   The id of the service used to perform this 
     * task can be located in application's AppParm.properties file as the key, 
     * {@link com.api.config.HttpSystemPropertyConfig#PROPNAME_LOGINSRC}. 
     * 
     * @return {@link RMT2SessionBean} instance or an exception if an error occurs.
     * @throws LoginException For general service call errors.
     */
    public Object doLogin() throws LoginException {
        return this.doSoapLogin();
    }

    /**
     * The SOAP client login version
     * 
     * @return
     * @throws LoginException
     */
    private Object doSoapLogin() throws LoginException {
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
                
                // Create Error XML for non-Java clients
                RSAuthenticationLogin errResp = f.createRSAuthenticationLogin();
                HeaderType h = PayloadFactory.createHeader("RS_authentication_login", "SYNC", "RESPONSE", (loginId == null ? "" : loginId));
                ReplyStatusType rst = PayloadFactory.createReplyStatus(false, "Login failed", errMsg, -1);
                errResp.setHeader(h);
                errResp.setReplyStatus(rst);
                this.sessionToken = ResourceFactory.getJaxbMessageBinder().marshalMessage(errResp);
                throw new LoginException(errMsg);
            }
            RSAuthenticationLogin soapResp = (RSAuthenticationLogin) client.getSoapResponsePayload();
            UserSessionType ust = soapResp.getSessionToken();
            // Update UserSessionType with information from user's request instance
            JaxbContactsFactory.updateUserSessionType(ust, this.request);
            ReplyStatusType rst = soapResp.getReplyStatus();
            logger.info(rst.getMessage());
            // copy user session type data to RMT@SessionBean instance
            RMT2SessionBean sessionBean = JaxbContactsFactory.getUserSession(ust, this.request);

            this.sessionToken = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
            return sessionBean;
        }
        catch (MessageException e) {
            throw new LoginException(e);
        }
    }

    // For new HTTP Web service client
    private Object doHttpLogin() throws LoginException {
        HttpMessageSender client = HttpClientResourceFactory.getHttpInstance();
        ProviderConfig config;
        try {
            config = ResourceFactory.getHttpConfigInstance();
            client.connect(config);
            client.sendMessage(this.request);
            RMT2SessionBean sessionToken = (RMT2SessionBean) client.getMessage();
            logger.info(sessionToken);
            return sessionToken;
        }
        catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new LoginException(e);
        }
        catch (ProviderConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new LoginException(e);
        }
        catch (HttpClientMessageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new LoginException(e);
        }
        catch (MessageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new LoginException(e);
        }

    }


    /**
     * Remotely logs out a user into the system.   The id of the service used to perform this 
     * task can be located in application's AppParm.properties file as the key, 
     * {@link com.api.config.HttpSystemPropertyConfig#PROPNAME_LOGINSRC}. 
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
            UserAppLoginCheckType alct = soapResp.getUserAuth();
            // Update UserSessionType with information from user's request instance
            JaxbContactsFactory.updateUserSessionType(alct.getSessionToken(), this.request);
            this.sessionToken = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
        }
        catch (MessageException e) {
            throw new AuthenticationException(e);
        }

        try {
            RMT2SessionBean session = AuthenticationFactory.getSessionBeanInstance(soapResp.getUserAuth().getUid(), 
                                                                                                                                               soapResp.getUserAuth().getAppCode(), 
                                                                                                                                               this.request, 
                                                                                                                                               this.request.getSession(false));
            return session;
        }
        catch (AuthenticationException e) {
            throw new SystemException(e);
        }
    }

    public void close() {
        return;
    }

    public boolean isAuthenticated(String userName) throws AuthenticationException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#doLogout(java.lang.String, java.lang.String)
     */
    public int doLogout(String loginId, String sessionId) throws LogoutException {
	ObjectFactory f = new ObjectFactory();
        SoapClientWrapper client = new SoapClientWrapper();
        RSAuthenticationLogout soapResp = null;
        try {
            RQAuthenticationLogout ws = f.createRQAuthenticationLogout();
            UserLogoutType logoutCred = f.createUserLogoutType();
            logoutCred.setUid(loginId);
            logoutCred.setSessionId(sessionId);
            HeaderType header = PayloadFactory.createHeader("RQ_authentication_logout", "SYNC", "REQUEST", loginId);
            ws.setHeader(header);
           
            ws.setLogoutCredentials(logoutCred);
            String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
            SOAPMessage resp = client.callSoap(msg);
            if (client.isSoapError(resp)) {
                String errMsg = client.getSoapErrorMessage(resp);
                logger.warn(errMsg);
                return -1;
            }
            soapResp = (RSAuthenticationLogout) client.getSoapResponsePayload();
            this.sessionToken = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
            return soapResp.getReplyStatus().getReturnCode().getValue().intValue();
        }
        catch (MessageException e) {
            throw new LogoutException(e);
        }
    }
}

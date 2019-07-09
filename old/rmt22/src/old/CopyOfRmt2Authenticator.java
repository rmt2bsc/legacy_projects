package com.security;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.security.UserSecurity;

import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.AuthorizationException;
import com.api.security.authentication.LoginException;
import com.api.security.authentication.LogoutException;
import com.api.security.authentication.RMT2SessionBean;

import com.api.security.pool.AppPropertyPool;

import com.bean.RMT2Base;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Session;
import com.entity.Members;

import com.members.MemberApi;
import com.members.MemberException;
import com.members.MemberFactory;

import com.remoteservices.http.HttpRemoteServicesConsumer;

import com.util.NotFoundException;
import com.util.SystemException;

/**
 * An implementation of UserSecurity interface which provides functionality for a 
 * client to remotely log in - log out users and perform user authorization across 
 * application contexts.
 * 
 * @author RTerrell
 */
public class CopyOfRmt2Authenticator extends RMT2Base implements UserSecurity {

    private Request request;

    private Response response;
    
    

    private static Logger logger = Logger.getLogger(CopyOfRmt2Authenticator.class);

    /**
     * Default constructor hat initializes the logger.
     *
     */
    public CopyOfRmt2Authenticator() {
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
	this.request = request;
	this.response = response;
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
	String serviceId = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_LOGINSRC);
	// Call service to see if user is a able to access other applications besides RMT2 website
	Object results = null;
	try {
	    results = HttpRemoteServicesConsumer.callService(serviceId, this.request, this.response);
	}
	catch (SystemException e) {
	    switch (e.getErrorCode()) {
	    // User not found in authentication application.  Check local application member table as an alternative
	    case -100:
		results = null;
		break;
	    
	    // Unacceptable authentication errors
	    case -101:
	    case -150:
	    case -151:
	    case -152:
	    case -153:
	    case -154:
	    case -155:
		throw new LoginException(e);
		
 	    // Authorization errors		
	    case -2000:
	    case -2001:
	    case -2003:
		throw new LoginException(e);
	    }
	}

	try {
	    // Process service results as an exception, if applicable
	    if (results != null && results instanceof Exception) {
		throw new LoginException(((Exception) results));
	    }
	    if (results == null) {
		// See if user is just a memeber of the RMT2 website only.
		String emailAddr = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
		results = this.doRmt2SiteLogin(emailAddr);
	    }
	    return results;
	}
	catch (LoginException e) {
	    this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, e.getMessage());
	    throw e;
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
	String serviceId = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_LOGOUTSRC);
	try {
	    // Call service
	    Object results = HttpRemoteServicesConsumer.callService(serviceId, this.request, this.response);

	    // Process service results
	    if (results instanceof Exception) {
		throw new LogoutException(((Exception) results));
	    }
	    this.request.getSession().removeAttribute(RMT2ServletConst.SESSION_BEAN);
	    this.request.getSession().invalidate();
	    return 1;
	}
	catch (Exception e) {
	    throw new LogoutException(e);
	}
    }

    public RMT2SessionBean getAuthentication(String userName, String appName, String sessionId) throws AuthenticationException {
	String serviceId = "checkapp";
	this.request.setAttribute(AuthenticationConst.AUTH_PROP_USERID, userName);
	this.request.setAttribute(AuthenticationConst.AUTH_PROP_MAINAPP, appName);
	this.request.setAttribute(AuthenticationConst.AUTH_PROP_SESSIONID, sessionId);
	return (RMT2SessionBean) HttpRemoteServicesConsumer.callService(serviceId, this.request, this.response);
    }

    private RMT2SessionBean doRmt2SiteLogin(String emailAddr) throws LoginException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	MemberApi memApi = MemberFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    Members mem = (Members) memApi.findByEmail(emailAddr);
	    // Setup user's session bean
	    Session session = request.getSession(false);
	    if (session == null) {
		this.msg = "SessionBean setup failed:  The session within the Authentication application could not be obtained.";
		logger.log(Level.ERROR, this.msg);
		throw new SystemException(this.msg);
	    }

	    // Get session bean token for user who is being atuhenticated.
	    RMT2SessionBean sessionBean = null;
	    sessionBean = AuthenticationFactory.getSessionBeanInstance(this.request, session);
	    sessionBean.setLoginId(emailAddr);
	    return sessionBean;
	}
	catch (MemberException e) {
	    e.printStackTrace();
	    throw new LoginException(e);
	}
	catch (AuthenticationException e) {
	    e.printStackTrace();
	    throw new LoginException(e);
	}
	catch (NotFoundException e) {
	    throw new LoginException("User, " + emailAddr + ", does not exist in the database");
	}
	finally {
	    memApi.close();
	    memApi = null;
	    tx.close();
	    tx = null;
	}
    }

    public boolean isAuthorized(String loginId) throws LoginException {
	// TODO Auto-generated method stub
	return false;
    }

    public boolean isAuthorized(String loginId, String appName) throws AuthorizationException {
	// TODO Auto-generated method stub
	return false;
    }

    /**
     * Release any resources
     */
    public void close() {
	return;
    }

    /**
     * Stub for checking if user has been authenticated.
     * 
     * @return boolean
     */
    public boolean isAuthenticated(String userName) throws AuthenticationException {
	return false;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#doLogout(java.lang.String, java.lang.String)
     */
    public int doLogout(String loginId, String sessionId) throws LogoutException {
        // TODO Auto-generated method stub
        return 0;
    }
}

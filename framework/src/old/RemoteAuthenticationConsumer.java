package com.api.security.authentication;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.config.HttpSystemPropertyConfig;
import com.api.security.UserSecurity;
import com.api.security.pool.AppPropertyPool;
import com.bean.RMT2Base;

import com.constants.RMT2ServletConst;
import com.controller.Context;
import com.controller.Request;
import com.controller.Response;
import com.remoteservices.ServiceHandlerException;
import com.remoteservices.http.HttpRemoteServicesConsumer;

/**
 * An implementation of UserSecurity interface which provides functionality for a 
 * client to remotely log in - log out users and perform user authorization across 
 * application contexts.
 * 
 * @author RTerrell
 * @deprecated Will be removed in future releases.   Each application will need to 
 *             implement the UserSecurity interface.  Might rename as AbstractRemoteAuthenticator
 *             containing only <i>callAuthenticationService(String) method.
 *
 */
class RemoteAuthenticationConsumer extends RMT2Base implements UserSecurity {

    private Request request;

    private Response response;

    private Logger logger;

    /**
     * Default constructor hat initializes the logger.
     *
     */
    private RemoteAuthenticationConsumer() {
	super();
	this.logger = Logger.getLogger("RemoteAuthenticationConsumer");
	this.logger.log(Level.DEBUG, "logger initialized");
    }

    /**
     * Constructs a RemoteAuthenticationConsumer that will possess an application context, 
     * the user's request object, and user's response object.
     * 
     * @param context The application context
     * @param request The user's request.
     * @param response The user's response.
     */
    public RemoteAuthenticationConsumer(Context context, Request request, Response response) {
	this();
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
	try {
	    // Call service
	    Object results = this.callAuthenticationService(serviceId);

	    // Process service results
	    if (results instanceof Exception) {
		throw new LoginException(((Exception) results));
	    }
	    return results;
	}
	catch (Exception e) {
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
	String serviceId = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_LOGOUTSRC);
	try {
	    // Call service
	    Object results = this.callAuthenticationService(serviceId);

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

    /**
     * Defaults to using the functionality of doLogin() method.
     * 
     * @return {@link RMT2SessionBean} instance or an exception if an error occurs.
     * @throws LoginException For general service call errors.
     * @deprecated Will be removed in future releases
     */
    public Object doRemoteLogin() throws LoginException {
	return this.doLogin();
    }

    /**
     * Stub method. 
     * 
     * @param loginId The login id of the user to query.
     * @return false
     * @throws LoginException
     * @deprecated Will be removed in future releases.  This method will be 
     *             replace by isUserAuthorized(String userName, String resource) to obtain the
     *             authorization status of a resource for a given user.
     */
    public boolean isUserAuthenticated(String loginId) throws LoginException {
	return false;
    }

    /**
     * Stub method.
     *  
     * @param loginId The login id of the user to query.
     * @param appName The target application.
     * @return false
     * @throws LoginException
     * @deprecated Will be removed in future releases.  This method will be 
     *             replace by isUserAuthorized(String userName, String resource) to obtain the
     *             authorization status of a resource for a given user.
     */
    public boolean isUserAuthenticated(String loginId, String appName) throws LoginException {
	return false;
    }

    /**
     * Invokes a remote authentication service using by <i>message</i> as the source.
     * 
     * @param message 
     *          The id of the service to call.
     * @return Object 
     *          An arbitrary object specific to the service.
     * @throws ServiceHandlerException 
     */
    private Object callAuthenticationService(String message) throws ServiceHandlerException {
	try {
	    // Call service
	    HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer();
	    srvc.processRequest(this.request, this.response, message);
	    Object results = srvc.getServiceResults();
	    return results;
	}
	catch (Exception e) {
	    throw new ServiceHandlerException(e);
	}
    }
    
    
    /**
     * @deprecated Will be removed in future releases
     */
    public boolean isLoginOriginLocal() {
	// TODO Auto-generated method stub
	return false;
    }


    /**
     * @deprecated Will be removed in future releases
     */
    public boolean isLoginOriginRemote() {
	// TODO Auto-generated method stub
	return false;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#setLoginOrigin(int)
     */
    public void setLoginOrigin(int origin) {
	// TODO Auto-generated method stub
	
    }

    public boolean isAuthorized(String loginId) throws LoginException {
	// TODO Auto-generated method stub
	return false;
    }

    public boolean isAuthorized(String loginId, String appName) throws LoginException {
	// TODO Auto-generated method stub
	return false;
    }

    public void init(Request request, Response response) {
	// TODO Auto-generated method stub
	
    }
}

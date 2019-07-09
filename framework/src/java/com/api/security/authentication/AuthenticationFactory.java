package com.api.security.authentication;

import com.api.config.AbstractEnvironmentConfig;

import com.api.security.UserSecurity;

import com.api.security.pool.AppPropertyPool;

import com.bean.RMT2Base;

import com.controller.Request;
import com.controller.Response;
import com.controller.Session;

import com.util.RMT2Utility;

import com.util.SystemException;

/**
 * Factory that creates instances of classes that are related to authentication 
 * and authorization.
 *  
 * @author RTerrell
 *
 */
public class AuthenticationFactory extends RMT2Base {

    /**
     * Creates an instance of RMT2SessionBean which is not assigned to any particular user.
     * 
     * @return {@link RMT2SessionBean} or null if instance could not be created.
     */
    private static RMT2SessionBean getSessionBeanInstance() {
        RMT2SessionBean obj;
        try {
            obj = new RMT2SessionBean();
            return obj;
        }
        catch (SystemException e) {
            return null;
        }
    }

    /**
     * Creates an instance of RMT2SessionBean which belongs to a user identiified as, 
     * <i>loginId</i>, of application, <i>appId</i>.
     * 
     * @param loginId The user's login id.
     * @param appId The id of the applicaion which the user is assoicated with at login time.
     * @return {@link RMT2SessionBean} or null if instance could not be created.
     * @throws AuthenticationException <i>loginId</i> and/or <i>appId</i> is null or not present.
     */
    public static RMT2SessionBean getSessionBeanInstance(String loginId, String appId) throws AuthenticationException {
        if (loginId != null && loginId.length() <= 0) {
            throw new AuthenticationException("Session Bean token failure...User\'s login id must be present");
        }
        if (appId != null && appId.length() <= 0) {
            throw new AuthenticationException("Session Bean token failure...User\'s application id must be present");
        }
        RMT2SessionBean obj = AuthenticationFactory.getSessionBeanInstance();
        obj.setLoginId(loginId);
        obj.setOrigAppId(appId);
        return obj;
    }

    /**
     * Creates a new RMT2SessionBean object using the user's request and session
     * objects to initialize its properties.
     * 
     * @param request The user's request
     * @param session The user's session.
     * @return {@link RMT2SessionBean} or null if instance could not be created.
     * @throws AuthenticationException <i>request</i> and/or <i>session</i> is null.
     */
    public static RMT2SessionBean getSessionBeanInstance(Request request, Session session) throws AuthenticationException {
        if (request == null) {
            throw new AuthenticationException("Session Bean token failure...User\'s request object is invalid");
        }
        if (session == null) {
            throw new AuthenticationException("Session Bean token failure...User\'s web server session object is invalid");
        }
        RMT2SessionBean bean = null;
        try {
            bean = new RMT2SessionBean(request, session);
            return bean;
        }
        catch (SystemException e) {
            return null;
        }
    }

    /**
     * Creates a new RMT2SessionBean object using the user's login id, application code, and the request and session
     * instances to initialize its properties.
     * 
     * @param loginId
     *          the user's login id
     * @param appCode
     *          the application code
     * @param request
     *          the user's request
     * @param session
     *          the user's session
     * @return {@link RMT2SessionBean} or null if instance could not be created.
     * @throws AuthenticationException
     */
    public static RMT2SessionBean getSessionBeanInstance(String loginId, String appCode, Request request, Session session) throws AuthenticationException {
        RMT2SessionBean bean = AuthenticationFactory.getSessionBeanInstance(request, session);
        bean.setLoginId(loginId);
        bean.setOrigAppId(appCode);
        return bean;
    }

    /**
     * Creates the user authenticator.  The authenticator is created dynamically by the 
     * input of the class name.  The class name can be obtained from the input parameter 
     * of this method, <i>implClassName</i>, or it can be discovered via a http system 
     * property configuration.  Each application that uses this framework api is required 
     * to create a class which implements the {@link com.api.security.UserSecurity UserSecurity} 
     * interface and make the class name available to the authentication process via the 
     * {@link com.api.config.HttpSystemPropertyConfig#PROPNAME_AUTHENTICATOR PROPNAME_AUTHENTICATOR} 
     * property in the AppParms.properties or input parameter, <i>implClassName</i>.
     * 
     * @param implClassName 
     *            The name of the implementing class to instantiate.  If null, then the application 
     *            property, authenticator, is used. 
     * @return {@link com.api.security.UserSecurity UserSecurity}
     *            The specific authenticator implementation.
     * @throws LoginException 
     *           If <i>implClassName</i> renders a null class instance, or the instance does not 
     *           implement the UserSecurity interface.
     */
    public static UserSecurity getAuthenticator(String implClassName) throws LoginException {
        // Obtain the class name from either the input parameter or the application's property pool.
        String className;
        if (implClassName == null || implClassName.length() <= 0) {
            className = AppPropertyPool.getProperty(AbstractEnvironmentConfig.PROPNAME_AUTHENTICATOR);
        }
        else {
            className = implClassName;
        }
        if (className == null) {
            throw new LoginException("User Authenticator object class name is invalid or unknown");
        }

        try {
            // Dynamically create object based on input parameter, className
            Object obj = RMT2Utility.getClassInstance(className);

            // Check if object implements UserSecurity
            if (obj instanceof UserSecurity) {
                // Do nothing...object implements UserSecurity interface.
            }
            else {
                throw new LoginException("User Authenticator object is of the wrong type.  Must implement the interface, " + UserSecurity.class.getName());
            }
            return (UserSecurity) obj;
        }
        catch (SystemException e) {
            throw new LoginException(e);
        }
    }

    /**
     * Creates the user authenticator and initilizes it with the user's Request and Response.
     * 
     *  @param implClassName 
     *            The name of the implementing class to instantiate.  If null, then the application 
     *            property, authenticator, is used. 
     * @param request
     *            The user's request
     * @param response
     *            The user's response
     * @return {@link com.api.security.UserSecurity UserSecurity}
     *            The specific authenticator implementation.
     * @throws LoginException 
     *           If <i>implClassName</i> renders a null class instance, or the instance does not 
     *           implement the UserSecurity interface.
     * @see {@link com.api.security.authentication.AuthenticationFactory#getAuthenticator(String) getAuthenticator(String)} 
     */
    public static UserSecurity getAuthenticator(String implClassName, Request request, Response response) throws LoginException {
        UserSecurity api = AuthenticationFactory.getAuthenticator(implClassName);
        api.init(request, response);
        return api;
    }

}

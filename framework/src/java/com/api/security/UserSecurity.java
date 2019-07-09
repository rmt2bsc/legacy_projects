package com.api.security;

import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthorizationException;
import com.api.security.authentication.LoginException;
import com.api.security.authentication.LogoutException;
import com.api.security.authentication.RMT2SessionBean;

import com.controller.Request;
import com.controller.Response;

/**
 * This interface provides the basic methods that need to be implemented in
 * order to authenticate users. This contract provides a way to develop an
 * application independent of the underlying authentication technology.
 * 
 * @author roy.terrell
 * 
 */
public interface UserSecurity {
    static final int LOGIN_ORIGIN_LOCAL = 1;

    static final int LOGIN_ORIGIN_REMOTE = -1;

    /**
     * Initialize UserSecurity implementation with a Request and Response instnaces.
     *  
     * @param request
     * @param response
     */
    void init(Request request, Response response);

    /**
     * Performs user authentication and authorization.
     * 
     * @return Object
     *           An arbitrary object representing the user's profile.
     *           This is implementation specific.
     * @throws LoginException
     */
    Object doLogin() throws LoginException;

    /**
     * Logs the user out of the application.  The target user should be discovered via 
     * some intrinsic variable or context.
     * 
     * @return int
     * @throws LogoutException
     */
    int doLogout() throws LogoutException;

    /**
     * Logs the specified user, belonging to the session identified as <i>sessionId</i>, 
     * out of the application.
     * 
     * @param loginId
     *         the login id of the user to log out.
     * @param sessionId
     *         the id of the user's session.
     * @return int
     * @throws LogoutException
     */
    int doLogout(String loginId, String sessionId) throws LogoutException;

    
    /**
     * Determines if a user is currently signed on one or more applications and obtains a session bean for a user who 
     * has been potentially authenticated.
     * 
     * @return
     * @throws AuthenticationException
     */
    RMT2SessionBean getAuthentication() throws AuthenticationException;
    
    
    /**
     *  Determines if a  user is currently signed on to one or more applications using user name, application name, and 
     *  session id and obtains a session bean for the user using the following data itmes: .
     *  
     * @param userName
     *          The user's user name
     * @param appName
     *          The name of the application user is trying to access. 
     *          This is optional and can be null.
     * @param sessionId
     *          The id of the source session.                 
     * @return {@link com.api.security.authentication.RMT2SessionBean RMT2SessionBean}
     * @throws AuthenticationException
     */
    RMT2SessionBean getAuthentication(String userName, String appName, String sessionId) throws AuthenticationException;
    
    

    /**
     * Determines if the user is currently signed on one or more applications 
     * within a single application server instance.
     * 
     * @param userName The login id of the user to query.
     * @return Always false.
     * @throws LoginException
     */
    boolean isAuthenticated(String userName) throws AuthenticationException;

    /**
     * Determines if the user possesses one or more of the required roles.
     * 
     * @param roles
     *          A String containing a list of roles.
     * @return
     *         true if one or more of the required roles matches the user's assigned roles.   
     *         Othewise, false is returned.
     * @throws AuthorizationException
     *         AuthenticationException
     */
    boolean isAuthorized(String roles) throws AuthorizationException, AuthenticationException;

    /**
     * Release any resources.
     * 
     */
    void close();

    /**
     * Fetches the security session token which is usually utilized by the client.
     * 
     * @return
     *           an abitrary object representing the session token.
     */
    Object getSessionToken();
}

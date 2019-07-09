package com.api.security;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.Properties;
import java.util.Hashtable;
import java.util.List;

import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.AuthorizationException;
import com.api.security.authentication.InvalidUserCredentialsException;
import com.api.security.authentication.LoginException;
import com.api.security.authentication.LoginIdException;
import com.api.security.authentication.LogoutException;
import com.api.security.authentication.MissingLoginCredentialsException;
import com.api.security.authentication.PasswordException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.RMT2Base;

import com.controller.Request;
import com.controller.Response;

import com.util.RMT2Exception;
import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * This implemntation provides a basic template to authenticate, authorize, and 
 * logout users in a http protocol environment.  The user's basic credentials are 
 * chanllenged and the appropriate exceptions are thrown.  Functionality is provided 
 * to locally and remotely authenticate users, although, by default, each authentication 
 * is always treated as the user's first attempt to log into the system.
 * <p>
 * Logging a user out of the system is completed abstracted and must be implemented 
 * from a descending class.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class AbstractAuthentication extends RMT2Base {

    private Logger logger;

    private Hashtable credentials;

    private String userName;

    private String password;

    private String targetApp;

    private String sessionId;

    private int loginOrigin;

    protected List roles;

    protected Object user;

    protected Request request;

    protected Response response;

    /**
     * Default constructor which initializes the logger.
     * 
     */
    protected AbstractAuthentication() {
        super();
        this.logger = Logger.getLogger("AbstractAuthentication");
    }

    /**
     * Consructs an AbstractAutheticationImpl object using dataObject to 
     * initialize the user's login credentials.
     * 
     * @param request The user's request object.
     * @param credentials 
     *          An arbitrary object containing the user's login credentials.
     * @throws LoginException General login errors.
     * @throws SystemException When a problem occurs initializing the data source.
     * @throws InvalidUserCredentialsException when user credentials are invalid.
     */
    public AbstractAuthentication(Request request, Object credentials) throws LoginException, InvalidUserCredentialsException, SystemException {
        this();
        this.initInstance(request, credentials);
    }

    /**
     * Initializes instance of AbstractAuthentication with the user's request and 
     * login credentials.  By default this method is configured to service remote 
     * requests by setting the login origin property to -1.  Override this method 
     * and set the login origin property to "1" if the deisre to service local 
     * request arises.
     *  
     * 
     * @param request  
     *           The user's request object.
     * @param credentials 
     *           A hash collection containing key/value pairs representing the user's login id, 
     *           password, and a application id which the key names are 
     *           {@link com.api.security.authentication.AuthenticationConst#AUTH_PROP_USERID AUTH_PROP_USERID},
     *           {@link com.api.security.authentication.AuthenticationConst#AUTH_PROP_PASSWORD AUTH_PROP_PASSWORD}, and
     *           {@link com.api.security.authentication.AuthenticationConst#AUTH_PROP_MAINAPP AUTH_PROP_MAINAPP}. 
     * @throws LoginException 
     *           Invalid data contained in <i>credentials</i> or if the data source provider 
     *           is unobtainable.
     */
    public void initInstance(Request request, Object credentials) throws LoginException, InvalidUserCredentialsException {
        this.request = request;
        // Obtain user's credentials
        try {
            this.credentials = this.verifyUserCredentials(credentials);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InvalidUserCredentialsException(e);
        }
        this.userName = (String) this.credentials.get(AuthenticationConst.AUTH_PROP_USERID);
        this.password = (String) this.credentials.get(AuthenticationConst.AUTH_PROP_PASSWORD);
        this.targetApp = (String) this.credentials.get(AuthenticationConst.AUTH_PROP_MAINAPP);
        this.sessionId = (String) this.credentials.get(AuthenticationConst.AUTH_PROP_SESSIONID);
        this.loginOrigin = UserSecurity.LOGIN_ORIGIN_REMOTE;
    }

    /**
     * Authenticates the user, interrogates application authorization and 
     * builds a session bean object.  THis method manages both local and 
     * remote logins.
     * 
     * @return A generic class instance that represents the user's session bean.
     * @throws LoginException
     */
    public RMT2SessionBean doLogin() throws LoginException {
        try {
            this.user = this.authenticate(this.userName, this.targetApp);
            this.roles = this.authorize(this.userName, this.targetApp);
            RMT2SessionBean sessionBean = this.doPostLogin(this.userName, this.targetApp);
            return sessionBean;
        }
        catch (AuthenticationException e) {
            throw new LoginException(e.getMessage(), e.getErrorCode());
        }
        catch (Exception e) {
            if (e instanceof RMT2Exception) {
                throw new LoginException(((RMT2Exception) e).getMessage(), ((RMT2Exception) e).getErrorCode());
            }
            throw new LoginException(e);
        }
    }

    /**
     * Logs a user out of one or more applications that exist on a single 
     * application server.
     * 
     * @return An int greater than or equal to zero which represents the 
     *         to total number of applications the user was logged out of.
     * @throws LogoutException
     */
    public int doLogout() throws LogoutException {
        try {
            int appCount = this.logoutUser(this.userName, this.sessionId);
            this.doPostLogout(this.userName);
            return appCount;
        }
        catch (Exception e) {
            throw new LogoutException(e);
        }
    }

    /**
     * Authenticates a user by perforing basic validations for the following login 
     * credentials that set during instance creation: login id, password, and target 
     * application.
     *   
     * @param userName The user's login id.
     * @param targetApp The application the user is attempting to access.
     * @return null
     * @throws MissingLoginCredentialsException 
     *            When the login id, password, or target application is absent.
     * @throws AuthenticationException 
     *            For general authentication errors.
     */
    protected Object authenticate(String loginId, String targetApp) throws MissingLoginCredentialsException, AuthenticationException {
        this.verifyCredentials();
        return this.buildUserProfile(loginId);
    }

    /**
     * Get user login profile.
     * 
     * @return the generic user login object.
     */
    protected Object getUser() {
        return user;
    }

    /**
     * Validates whether the login id is not null..
     * 
     * @param userName 
     *          the login id of the user to query.
     * @return boolean
     *          returns true when userName is not null.  Otherwise, false is returned.
     * @throws AuthenticationException
     */
    public boolean isAuthenticated(String loginId) throws AuthenticationException {
        return (loginId == null ? false : true);
    }

    /**
     * Stub for determining if the user is currently signed on a particular application 
     * identified as appName.
     *  
     * @param userName 
     *           The login id of the user to query.
     * @param appName 
     *           The target application.
     * @return boolean
     *           returns true when userName and appName are not null.  Otherwise, 
     *           false is returned.
     * @throws AuthenticationException
     */
    public boolean isAuthenticated(String loginId, String appName) throws AuthenticationException {
        return (loginId != null && appName != null ? true : false);
    }

    /**
     * Stub method to determine if user is authorized to access a given application.
     * 
     * @param userName
     *          The user's user name
     * @param appName
     *          The application the user is trying to access
     * @return boolean
     *          Always returns false
     * @throws AuthorizationException
     */
    public boolean isAuthorized(String userName, String appName) throws AuthorizationException {
        return false;
    }

    /**
     * Verifies whether or not a user is authorized to access an application.
     * 
     * @param userName The login id of the user to authorize.
     * @param targetApp The name of the application the user desires to access.
     * @return A List of application role codes assigned to the user.
     * @throws AuthorizationException General authorization errors.
     */
    protected List authorize(String loginId, String targetApp) throws AuthorizationException {
        return null;
    }

    /**
     * Creates a user session bean instance using the user's login id and the 
     * name of the target application.
     * 
     * @param userName The login id of the user.
     * @param targetApp The name of the target appliation the user is accessing.
     * @return An arbitrary object as RMT2SessionBean.
     * @throws SystemException General errors
     */
    protected RMT2SessionBean doPostLogin(String loginId, String targetApp) throws SystemException {
        RMT2SessionBean sessionBean;
        try {
            sessionBean = AuthenticationFactory.getSessionBeanInstance(loginId, targetApp);
            sessionBean.setLoginId(loginId);
            sessionBean.setOrigAppId(targetApp);
            sessionBean.setRoles(this.roles);
            return sessionBean;
        }
        catch (AuthenticationException e) {
            throw new SystemException(e);
        }

    }

    /**
     * Implement to perform any post user applicaltin logout routines.
     * 
     * @param userName The user's logi id.
     * @throws SystemException General errors.
     */
    protected abstract void doPostLogout(String loginId) throws SystemException;

    /**
     * Implement method to conatin logic that will disassoicate the user 
     * from all applications he/she is signed on.  Also, logic should be
     * included to remove the user's session bean instance from the session.
     * 
     * @param userName The user's login id.
     * @param sessionId The user's session id.
     * @return int
     * @throws LogoutException
     */
    protected abstract int logoutUser(String userName, String sessionId) throws LogoutException;

    /**
     * Verifies that dataObject is a valid instance of Hashtable.  Alternatively, the 
     * HttpServlerRequest object is considered to be the source for login credentials 
     * and is adapted as a Properties collection.
     * 
     * @param credentials 
     *           A gernic object holding the user's login credentials
     * @return Properties collection.
     * @throws InvalidUserCredentialsException
     *             if dataObject is null or is found not to be of type
     *             HttpServletRequest.
     */
    protected Hashtable verifyUserCredentials(Object credentials) throws InvalidUserCredentialsException {
        Properties loginData = null;
        if (credentials == null) {
            if (this.request == null) {
                this.msg = "Authentication input data object is null";
                logger.log(Level.ERROR, this.msg);
                throw new InvalidUserCredentialsException(this.msg, -154);
            }
            else {
                // Convert request parameters to a Properties collection.
                loginData = RMT2Utility.getRequestData(this.request);
                return loginData;
            }
        }
        if (credentials instanceof Hashtable) {
            // Do nothing
        }
        else {
            this.msg = "Authentication input data object must be of type HttpServletRequest";
            logger.log(Level.ERROR, this.msg);
            throw new InvalidUserCredentialsException(this.msg, -155);
        }
        return (Hashtable) credentials;
    }

    /**
     * Validates the existence of the login credentials, login id, password, and .
     * target applicatin name.  If this method is overriden, the credentials hash 
     * can be accessed via the method call, getCredentials().
     * 
     * @param credentials
     *            Map of name/value pairs.
     * @throws LoginException
     *             When credentials is invalid, the login id is null, password
     *             is null, or the user's request object is invalid.
     */
    protected void verifyCredentials() throws MissingLoginCredentialsException {
        if (this.credentials == null) {
            throw new MissingLoginCredentialsException("Login credential collection has not been initialized", -150);
        }
        if (this.userName == null || this.userName.equals("")) {
            throw new LoginIdException("Login id is required", -151);
        }
        if (this.password == null || this.password.equals("")) {
            throw new PasswordException("Password is required", -152);
        }
        if (this.targetApp == null || this.targetApp.equals("")) {
            throw new TargetAppException("Target application name is required", -153);
        }
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#isLoginOriginLocal()
     */
    public boolean isLoginOriginLocal() {
        return this.loginOrigin == UserSecurity.LOGIN_ORIGIN_LOCAL;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#isLoginOriginRemote()
     */
    public boolean isLoginOriginRemote() {
        return this.loginOrigin == UserSecurity.LOGIN_ORIGIN_REMOTE;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#setLoginOrigin(int)
     */
    public void setLoginOrigin(int origin) {
        this.loginOrigin = origin;
    }

    /**
     * Creates a new user profile.
     * 
     * @param userName The user's login id to build profile.
     * @return UserLogin
     */
    protected abstract Object buildUserProfile(String loginId);

    /**
     * @return the credentials
     */
    protected Hashtable getCredentials() {
        return this.credentials;
    }

    /**
     * Get the user's login id.
     * 
     * @return the userName
     */
    protected String getUserName() {
        return userName;
    }

    /**
     * Get the user's password.
     * 
     * @return the password
     */
    protected String getPassword() {
        return password;
    }

    /**
     * Get the http servlet request that holds client information.
     * 
     * @return the request
     */
    protected Request getRequest() {
        return request;
    }

    /**
     * Get the name of the application which the user is authenticated 
     * and authorized to access.
     * 
     * @return the targetApp
     */
    protected String getTargetApp() {
        return targetApp;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
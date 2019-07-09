package com.api.security;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import com.api.DaoApi;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceFactory;

import com.api.security.UserSecurity;
import com.api.security.AbstractAuthentication;

import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.AuthorizationException;
import com.api.security.authentication.InvalidUserCredentialsException;
import com.api.security.authentication.LoginException;
import com.api.security.authentication.LogoutException;
import com.api.security.authentication.MissingLoginCredentialsException;
import com.api.security.authentication.RMT2SessionBean;

import com.api.security.user.ApplicationApi;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.UserLogin;
import com.bean.Application;
import com.bean.ApplicationAccess;
import com.bean.VwUserAppRoles;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Session;

import com.util.SystemException;

/**
 * User authentication/authorization implementation using relational database 
 * technology. Users are validated against a relational database in which the 
 * database connections are housed and managed by an object pool. If an user 
 * fails to be authenticated,* an appropriate exception is thrown. Once a user 
 * is accepted into the system, the user is associated with a database connection 
 * object and a session bean object which all are managed on the HttpSession object.
 * <p>
 * Logging a user out of the system simply involves disassociating the database
 * connection and returning the connection back to the connection pool.
 * Likewise, the user's session bean is removed as well.
 * 
 * @author Roy Terrell
 * 
 */
public class DatabaseAuthenticator extends AbstractAuthentication implements UserSecurity {

    private static Logger logger = Logger.getLogger(DatabaseAuthenticator.class);
    

    /**
     * Default constructor
     */
    public DatabaseAuthenticator() {
	super();
    }

    /**
     * Consructs an AbstractAutheticationImpl object using an HttpServletRequest
     * and HttpServletResponse objects.
     * 
     * @param request
     *            An arbitrary object representing the user's request.
     */
    public DatabaseAuthenticator(Request request, Object credentials) throws LoginException, InvalidUserCredentialsException, SystemException {
	super(request, credentials);
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
	try {
	    this.initInstance(request, null);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SystemException(e);
	}
    }
    
    
    public RMT2SessionBean getAuthentication() throws AuthenticationException {
	return this.getAuthentication(this.getUserName(), this.getTargetApp(), this.getSessionId());
    }
    

    /**
     * Verifies if user is logged in to one or more systems and returns a session bean token representing the 
     * authenticated user.  First, the user authentication is verified by determining if the user is logged on 
     * anywhere in the system.  Secondly, if <i>appName</i> is supplied, application authorization is verified 
     * for the user.  Lastly, the a session bean is created and returned to the caller.  
     *  
     * @param userName
     *          The user's user name.  This parameter is optional.  If it is not supplied, then the session id is
     *          used to locate and determine whether the user is logged in.
     * @param appName
     *          The name of the application user is trying to access.  This is optional and can be null.
     * @param sessionId
     *          The session id that is to be used to verify user's login existence.  This is parameter is required.                     
     * @return {@link com.api.security.authentication.RMT2SessionBean RMT2SessionBean}
     *          when user is found to be authenticated and is authorized to access 
     *          said application.   Return null when userName or appName is null or for
     *          authentication and authorization errors.
     * @throws AuthenticationException
     *          general data access errors.
     */
    public RMT2SessionBean getAuthentication(String userName, String appName, String sessionId) throws AuthenticationException {
        if (sessionId == null || sessionId.length() == 0 || sessionId.equals("")) {
            this.msg = "Unable to perform user authentication due to the absence session id parameter";
            logger.error(this.msg);
            throw new AuthenticationException(this.msg);
        }
	DatabaseTransApi tx = DatabaseTransFactory.create();
	// update member variable, sessionId, so that the authorize(String, String) method can update the application_access table
	this.setSessionId(sessionId);
	try {
	    // Is user logged in anywhere in the system?
	    if (!this.isAuthenticated(userName)) {
		return null;
	    }

	    //  User roles, if application name is supplied.
	    if (appName != null) {
		this.roles = this.authorize(userName, appName);
		if (this.roles == null || this.roles.size() == 0) {
		    return null;
		}
	    }

	    // Setup user's session bean
	    RMT2SessionBean sessionBean = this.setupSessionBean(userName, this.getRequest(), (DatabaseConnectionBean) tx.getConnector());
	    return sessionBean;
	}
	catch (AuthenticationException e) {
	    return null;
	}
	catch (AuthorizationException e) {
	    return null;
	}
	catch (Exception e) {
	    throw new AuthenticationException(e);
	}
	finally {
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Determines if the user is logged into one or more systems.   If <i>userName</i> is null, then the 
     * 
     * @param userName
     *            The login id of the user to query.
     * @return boolean
     *            true if user is signed on to one or more applications.  false
     *            is returned when user has no open applications or userName is 
     *            null.
     * @throws LoginException
     *             A problem occured when access application access data.
     */
    public boolean isAuthenticated(String userName) throws AuthenticationException {
        boolean userAvail = super.isAuthenticated(userName);
        if (!userAvail) {
            return false;
        }
	DatabaseTransApi tx = null;
	UserApi api = null;
	UserLogin ul = null;
	try {
	    tx = DatabaseTransFactory.create();
	    api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	    ul = (UserLogin) api.findUserByUserName(userName);
	    
	    //  This is an error if user profile is not found.
	    if (ul == null) {
                throw new AuthenticationException("Unable to authenticate user.  User name, " + userName + ", does not exists");
            }
	    // set member variables
	    this.setUserName(ul.getUsername());
	    this.user = ul;
	    
	    // The loggedIn indicator from the user's profile should have been persisted as "1" in the 
	    // database provided the user is logged in to one or more systems.
	    return (ul.getLoggedIn() == 1 ? true : false);
	}
	catch (UserAuthenticationException e) {
	    throw new AuthenticationException(e);
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Determines if the user is currently signed on a particular application 
     * identified as appName.
     *  
     * @param userName The login id of the user to query.
     * @param appName The target application.
     * @return true when user is signed on an application and false otherwise.
     * @throws AuthenticationException
     */
    public boolean isAuthenticated(String userName, String appName) throws AuthenticationException {
	boolean userLoggedIn = this.isAuthenticated(userName);
	if (!userLoggedIn) {
	    return false;
	}
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi appApi = null;
	ApplicationAccess appAccess = null;
	VwUserAppRoles userApp = null;
	DaoApi dao = null;
	try {
	    appApi = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector());
	    userApp = UserFactory.createVwUserAppRoles();
	    userApp.addCriteria(VwUserAppRoles.PROP_USERNAME, userName);
	    userApp.addCriteria(VwUserAppRoles.PROP_APPNAME, appName);
	    dao = DataSourceFactory.createDao((DatabaseConnectionBean) tx.getConnector());
	    Object data[] = dao.retrieve(userApp);
	    if (data != null && data.length > 0) {
		userApp = (VwUserAppRoles) data[0];
	    }
	    else {
		userApp = null;
	    }

	    // Locate application-access entry for user uid/application id key combination.
	    List<ApplicationAccess> list = (List<ApplicationAccess>) appApi.findAppAccessByUserLoginId(userApp.getLoginUid());
	    if (list != null) {
		for (int ndx = 0; ndx < list.size(); ndx++) {
		    appAccess = (ApplicationAccess) list.get(ndx);
		    if (appAccess.getLoginId() == userApp.getLoginUid() && appAccess.getAppId() == userApp.getApplicationId()) {
			return true;
		    }
		}
	    }
	    return false;
	}
	catch (Exception e) {
	    throw new AuthenticationException(e);
	}
	finally {
	    dao.close();
	    dao = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Verifies that the user has supplied valid credentials.
     * 
     * @param userName
     *            The user's login id.
     * @param targetApp
     *            The application the user is attempting to access.
     * @return {@link com.bean.UserLogin UserLogin}.
     * @throws MissingLoginCredentialsException
     *             When the login id, password, or target application is absent.
     * @throws AuthenticationException
     *             For general authentication errors.
     */
    protected Object authenticate(String userName, String targetApp) throws MissingLoginCredentialsException, AuthenticationException {
	super.authenticate(userName, targetApp);
	DatabaseTransApi tx = DatabaseTransFactory.create();
	((DatabaseConnectionBean) tx.getConnector()).setLoginId(this.getUserName());
	UserApi api = null;
	try {
	    // Get an User Login Object
	    api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	    UserLogin userLoginBean = (UserLogin) api.verifyLogin(this.getUserName(), this.getPassword());
	    return userLoginBean;
	}
	catch (UserAuthenticationException e) {
	    throw new AuthenticationException(e.getMessage(), e.getErrorCode());
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Verifies whether or not a user is authorized to access an application.   
     * If authroization is granted, a row is added to the application_access 
     * table representing the application the user is signed on to for a given 
     * session. 
     * 
     * @param userName
     *            The login id of the user to authorize.
     * @param targetApp
     *            The name of the application the user desires to access.
     * @return List
     *            a List of application role codes assigned to the user or
     *            null if userName and/or targetApp are invalid.
     * @throws AuthorizationException
     *             No user roles are found for targetApp and general data 
     *             access errors.
     */
    protected List authorize(String userName, String targetApp) throws AuthorizationException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	DatabaseConnectionBean conBean = null;
	if (userName != null && targetApp != null) {
	    conBean = (DatabaseConnectionBean) tx.getConnector();
	    conBean.setLoginId(userName);
	}
	else {
	    return null;
	}

	// Get Applicaltion object and verify that application is valid
	ApplicationApi appApi = UserFactory.createAppApi(conBean);
	Application app = null;
	try {
	    app = (Application) appApi.findApp(targetApp);
	    if (app == null) {
		this.msg = "Authentication Failed.  Target application profile could not be found, " + targetApp;
		logger.log(Level.ERROR, this.msg);
		throw new AuthorizationException(this.msg, -2000);
	    }

	    // See if user is authorized to access appliation.
	    List roles;
	    try {
		roles = this.getUserApplicationRoles(userName, targetApp, conBean);
	    }
	    catch (Exception e) {
		throw new AuthorizationException(e.getMessage(), -2003);
	    }
	    if (roles == null || roles.size() <= 0) {
		this.msg = "Authorization Failed.  User, " + userName + ", is not configured to access application [" + targetApp + "]";
		logger.log(Level.ERROR, this.msg);
		throw new AuthorizationException(this.msg, -2001);
	    }

	    // Get user object
	    UserApi userApi = UserFactory.createApi(conBean);
	    UserLogin user = (UserLogin) userApi.findUserByUserName(userName);

	    // Update user's application access profile.
	    appApi = UserFactory.createAppApi(conBean);
	    ApplicationAccess appAccess = UserFactory.createApplicationAccess();
	    appAccess.setAppId(app.getAppId());
	    appAccess.setLoginId(user.getLoginId());
	    appAccess.setLoginDate(new java.util.Date());
	    appAccess.setSessionId(this.getSessionId());
	    appApi.addUserApplicationAccess(appAccess);
	    tx.commitUOW();
	    return roles;
	}
	catch (UserAuthenticationException e) {
	    tx.rollbackUOW();
	    throw new AuthorizationException(e);
	}
	finally {
	    appApi.close();
	    appApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Uses the user api to obtain one or more application roles assigned to a
     * user.
     * 
     * @param loginId
     *            The user's login id
     * @return A list of application role codes assigned to the user.
     * @throws SystemException
     *             When an error occurs obtaining the roles from the database.
     */
    protected List getUserApplicationRoles(String loginId, String targetApp, DatabaseConnectionBean conBean) throws SystemException {
	UserApi api = UserFactory.createApi(conBean);
	try {
	    // Get application roles assigned to user
	    List roles = (List) api.getRoles(loginId, targetApp);
	    return roles;
	}
	catch (Exception e) {
	    throw new SystemException(e);
	}
    }
    
    public RMT2SessionBean doLogin() throws LoginException {
	RMT2SessionBean sb = null;
	try {
	    sb = this.getAuthentication(this.getUserName(), this.getTargetApp(), this.getSessionId());
	}
	catch (AuthenticationException e) {
	    this.msg = "Unable to perform single sign on for user";
	    logger.error(this.msg);
	    throw new LoginException(this.msg, e);
	}
	if (sb == null) {
	    sb = super.doLogin();    
	}
	return sb;
    }

    /**
     * Updates user system login count, adds an application access profile for 
     * the user, and creates a session bean object to be used by the client.
     * 
     * @param userName
     *            The login id of the user.
     * @param targetApp
     *            The name of the target appliation the user is accessing.
     * @return An arbitrary object as RMT2SessionBean.
     * @throws SystemException For database updates errors and general errors.
     */
    protected RMT2SessionBean doPostLogin(String userName, String targetApp) throws SystemException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	String serverMsg;
	Level logLevel;

	// Setup user's session bean
	RMT2SessionBean sessionBean;
	try {
	    sessionBean = this.setupSessionBean(userName, this.getRequest(), (DatabaseConnectionBean) tx.getConnector());
	    sessionBean.setLoginId(userName);
	}
	catch (AuthenticationException e) {
	    throw new SystemException(e);
	}

	if (sessionBean == null) {
	    serverMsg = "User, " + userName + ", failed to obtain a session bean token\n";
	    logLevel = Level.ERROR;
	    return null;
	}
	else {
	    serverMsg = "User, " + userName + ", authentication was successful!\n";
	    serverMsg += "Session ID: " + sessionBean.getSessionId() + "\n";
	    serverMsg += "System updated user's login total successfully!";
	    logLevel = Level.INFO;
	}

	logger.log(logLevel, serverMsg);
	sessionBean.setOrigAppId(targetApp);

	//  Update user's login status
	UserApi api = null;
	UserLogin userLoginBean = null;
	try {
	    api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	    userLoginBean = (UserLogin) api.findUserByUserName(userName);

	    // Set flag to indicate that user is logged in
	    userLoginBean.setLoggedIn(1);
	    // Update user's total successful login count
	    api.updateUserLoginCount(userLoginBean);
	    tx.commitUOW();
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new SystemException(e);
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}

	return sessionBean;
    }
   
   /**
    * This method obtains an HttpSession object and associates the session with the user. A session 
    * bean is instantiated and properly initialized, and obtains the next available database connection 
    * from the connection pool.  The session bean and the database connection are assigned to the session
    * object. The session id is output to the console.
    * 
    * @param userName
    *          The user's login id
    * @param request
    *          This is the Http Servlet request object.
    * @param conBean
    *          A connection to the data source
    * @return
    *          {@link com.api.security.authentication.RMT2SessionBean RMT2SessionBean}
    * @throws AuthenticationException
    * @throws SystemException
    *           Session could not be obtained from the request.
    */
    private RMT2SessionBean setupSessionBean(String userName, Request request, DatabaseConnectionBean conBean) throws AuthenticationException, SystemException {
	// At this point a session should already be assigned. If not, throw an exception.
	Session session = request.getSession(false);
	if (session == null) {
	    this.msg = "SessionBean setup failed:  The session within the Authentication application could not be obtained.";
	    logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}

	// Get session bean token for user who is being atuhenticated.
	RMT2SessionBean sessionBean = null;
	if (this.isLoginOriginLocal()) {
	    sessionBean = AuthenticationFactory.getSessionBeanInstance(request, session);
	    sessionBean.setLoginId(userName);
	}
	else if (this.isLoginOriginRemote()) {
	    sessionBean = AuthenticationFactory.getSessionBeanInstance(this.getUserName(), this.getTargetApp());
	    sessionBean.setAuthSessionId(this.getSessionId());
//	    sessionBean.setSessionId(this.getSessionId());
	}
	else {
	    this.msg = "Unable to determine login origination";
	    logger.log(Level.ERROR, this.msg);
	    throw new AuthenticationException(this.msg);
	}

	// Add user specific data to the session bean
	Object user = this.setUserProfile(conBean, sessionBean);
	if (user == null) {
	    this.msg = "Login failed for user, " + sessionBean.getLoginId() + ", due to the user\'s profile was not found and/or user is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}

	// Get User Roles.
	try {
	    UserApi api = UserFactory.createApi(conBean);
	    List list = (List) api.getRoles(sessionBean.getLoginId());
	    List roles = new ArrayList();
	    if (list == null) {
		list = new ArrayList();
	    }
	    for (int ndx = 0; ndx < list.size(); ndx++) {
		VwUserAppRoles vuar = (VwUserAppRoles) list.get(ndx);
		roles.add(vuar.getAppRoleCode());
	    }
	    sessionBean.setRoles(roles);
	}
	catch (Exception e) {
	    logger.log(Level.WARN, "User, " + userName + ", profile is not configured with any roles");
	}
	return sessionBean;
    }

    /**
     * Retreive current user from the database in order to populate the session
     * bean with user profile data.
     * 
     * @return A valid VwUser object or null if the user is invalid.
     */
    private Object setUserProfile(DatabaseConnectionBean conBean, RMT2SessionBean sessionBean) {
	UserLogin user = UserFactory.createUserLogin();
	DaoApi dao = DataSourceFactory.createDao(conBean);
	user.addCriteria(UserLogin.PROP_USERNAME, sessionBean.getLoginId());
	try {
	    Object[] result = dao.retrieve(user);
	    if (result != null && result.length > 0) {
		user = (UserLogin) result[0];
	    }
	    else {
		user = null;
	    }
	}
	catch (Exception e) {
	    return null;
	}
	if (user != null) {
	    sessionBean.setFirstName(user.getFirstname());
	    sessionBean.setLastName(user.getLastname());
	    sessionBean.setAccessLevel(0);
	    sessionBean.setGroupId(user.getGrpId());
	}
	return user;
    }

    /**
     * Creates an instance of {@link com.bean.UserLogin UserLogin}.
     * 
     * @param loginId
     *            The user's login id to build profile.
     * @return {@link com.bean.UserLogin UserLogin}
     */
    protected Object buildUserProfile(String loginId) {
	UserLogin ul = UserFactory.createUserLogin();
	ul.setUsername(loginId);
	ul.setPassword(this.getPassword());
	return ul;
    }

    /**
     * Disassociates one or more applications from the user by remove 
     * user-application entries from the application access table.
     * 
     * @param userName 
     *          the user's login id.
     * @param sessionId 
     *          the user's session id.  This is required
     * @return int 
     *          the total number of applications the user logged out of.
     * @throws LogoutException
     *          session id or user name is not provided or is null.
     */
    protected int logoutUser(String userName, String sessionId) throws LogoutException {
	if (userName == null) {
	    this.msg = "Unable to logout user.  User name was not provided";
	    logger.log(Level.ERROR, this.msg);
	    throw new LogoutException(this.msg);
	}
	if (sessionId == null) {
	    this.msg = "Unable to logout user.  Session id was not provided";
	    logger.log(Level.ERROR, this.msg);
	    throw new LogoutException(this.msg);
	}

	DatabaseTransApi tx = DatabaseTransFactory.create();
	DatabaseConnectionBean conBean = (DatabaseConnectionBean) tx.getConnector();
	DaoApi dao = DataSourceFactory.createDao(conBean);

	String serverMsg = null;
	Level logLevel = Level.DEBUG;
	try {
	    // Get User Security Manager Api
	    UserApi api = UserFactory.createApi(conBean, this.getRequest());
	    // Get an User Login Object
	    UserLogin userLoginBean = (UserLogin) api.findUserByUserName(userName);

	    // Remove user-application entries from the application access table.
	    ApplicationApi appApi = UserFactory.createAppApi(conBean);
	    int rows = appApi.removeUserApplicationAccess(userLoginBean.getLoginId(), sessionId);

	    // Get remaining user application access records.
	    List access = (List) appApi.findAppAccessByUserLoginId(userLoginBean.getLoginId());
	    // Flag user as being logged out of the system in the event this is the last application logged out of.
	    if (access == null || access.size() == 0) {
		userLoginBean.setLoggedIn(0);
	    }
	    else {
		userLoginBean.setLoggedIn(1);
	    }

	    // Update user login profile.
	    api.updateUserLogin(userLoginBean);
	    tx.commitUOW();
	    serverMsg = "User, " + userName + ", was logged out of " + rows + " applications!";
	    logLevel = Level.INFO;
	    return rows;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    logLevel = Level.ERROR;
	    serverMsg = e.getMessage();
	    throw new LogoutException(e);
	}
	finally {
	    dao.close();
	    dao = null;
	    tx.close();
	    tx = null;
	    logger.log(logLevel, serverMsg);
	}
    }

    /**
     * Invalidates the user's session on the web server just for this applicaton context.
     * 
     * @param userName The user's logi id.
     * @throws SystemException General errors.
     */
    protected void doPostLogout(String userName) throws SystemException {
	// Create new session for the user.
	Session session = this.getRequest().getSession();
	session.removeAttribute(RMT2ServletConst.QUERY_BEAN);
	session.invalidate();
	return;
    }

    /**
     * Returns database connection to the connection pool
     */
    public void close() {
	return;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#doLogout(java.lang.String, java.lang.String)
     */
    public int doLogout(String loginId, String sessionId) throws LogoutException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.api.security.UserSecurity#getSessionToken()
     */
    public Object getSessionToken() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isAuthorized(String roles) throws AuthorizationException, AuthenticationException {
        // TODO Auto-generated method stub
        return false;
    }

    

}
package com.api.security.authentication;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import java.util.Properties;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.api.security.UserAuthenticationException;
import com.api.security.UserSecurity;
import com.api.security.LoginException;

import com.api.security.pool.DatabaseConnectionPool;

import com.api.security.user.ApplicationApi;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.RMT2Base;
import com.bean.UserLogin;
import com.bean.Application;
import com.bean.ApplicationAccess;
import com.bean.RMT2SessionBean;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * This class provides the basic method implementations to authenticate and logout
 * users using Http Servlet and relatinal database technology.  Users are validated
 * against a relational database in which the database connections are housed and
 * managed by an object pool.  If an user fails to be authenticated, an appropriate
 * exception is thrown.  Once a user is accepted into the system, the user is
 * associated with a database connection object and a session bean object which all
 * are managed on the HttpSession object.
 * <p>
 * Logging a user out of the system simply involves disassociating the database
 * connection and returning the connection back to the connection pool.  Likewise, the
 * user's session bean is removed as well.
 *
 * @author Roy Terrell
 *
 */
public abstract class AbstractAuthenticationImpl extends RMT2Base implements UserSecurity {

    private Logger logger;

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    /**
     * Default constructor which initializes className which is not accessible to the public.
     *
     */
    public AbstractAuthenticationImpl() {
	super();
	this.logger = Logger.getLogger("AbstractAuthenticationImpl");
    }

    /**
     * Consructs an AbstractAutheticationImpl object using an HttpServletRequest 
     * and HttpServletResponse objects.
     * 
     * @param request
     * @param response
     */
    public AbstractAuthenticationImpl(HttpServletRequest request, HttpServletResponse response) {
	this();
	this.request = request;
	this.response = response;
    }

    /**
     * Authenticate user who is local to the application's resource that is desired to be accessed.
     * This authenticating process uses HTTP technology.  This method verifies theat the user is 
     * who he/she says it is by challenging the user's login credentials.  Once the user and all 
     * resources it wishes to access are successfully verified, the user is considered to be 
     * authenticated.
     *
     * @param dataObject This parameter is expected to be of type {@link RMT2SessionLoginBean}.
     * @param loginId The user's login id.
     * @param password The user's password.
     * @return The user's session bean disguised as an Object.
     * @throws LoginException
     */
    public Object authenticate(Object dataObject) throws LoginException {
	Level logLevel = null;
	String serverMsg = "";
	DatabaseConnectionBean conBean = null;

	Properties loginData = RMT2Utility.getRequestData(this.request);
	// See if we have a valid login credential object.
	this.validateInputObject(loginData);

	// Perform edits on user's credentials.
	this.validateCredentials(loginData);

	// Get user id and password
	String loginId = (String) loginData.get(AuthenticationConst.AUTH_PROP_USERID);
	String password = (String) loginData.get(AuthenticationConst.AUTH_PROP_PASSWORD);
	String sourceApp = (String) loginData.get(AuthenticationConst.AUTH_PROP_MAINAPP);

	if (loginId == null) {
	    serverMsg = "Local user authentication failed.  Login id is required";
	    logger.log(Level.ERROR, serverMsg);
	    throw new LoginException(serverMsg);
	}
	if (password == null) {
	    serverMsg = "Local user authentication failed.  Password is required";
	    logger.log(Level.ERROR, serverMsg);
	    throw new LoginException(serverMsg);
	}
	if (sourceApp == null) {
	    serverMsg = "Local user authentication failed.  Source application name is required";
	    logger.log(Level.ERROR, serverMsg);
	    throw new LoginException(serverMsg);
	}

	// Create new session for the user.
	HttpServletRequest request = this.request;

	// Borrow a database connection from the pool without registering it.
	try {
	    conBean = DatabaseConnectionPool.getAvailConnectionBean(loginId);
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new LoginException(e.getMessage());
	}

	// Begin to verify user
	try {
	    // Get Applicaltion object
	    ApplicationApi appApi = UserFactory.createAppApi(conBean);
	    Application app = (Application) appApi.findApp(sourceApp);
	    if (app == null) {
		throw new UserAuthenticationException("Authentication Failed.  User\'s system entry point application object could not be found, " + sourceApp);
	    }

	    // Get an User Login Object
	    UserApi api = UserFactory.createApi(conBean);
	    UserLogin userLoginBean = (UserLogin) api.verifyLogin(loginId, password);

	    // Get application access information
	    List roles = (List) api.getRoles(userLoginBean.getLoginId(), app.getName());
	    if (roles == null || roles.size() <= 0) {
		this.msg = "Authentication Failed.  User, " + loginId + ", is not configured to access application [code name] " + sourceApp;
		this.logger.log(Level.ERROR, this.msg);
		throw new UserAuthenticationException(this.msg);
	    }

	    try {
		// Update user's total successful login count
		api.updateUserLoginCount(userLoginBean);

		// Update user's application access profile.
		ApplicationAccess appAccess = UserFactory.createApplicationAccess();
		appAccess.setApplicationId(app.getId());
		appAccess.setUserLoginId(userLoginBean.getId());
		appAccess.setLoggedin(1);
		appAccess.setLoginDate(new java.util.Date());
		appApi.addUserApplicationAccess(appAccess);

		// Commit changes.
		conBean.getDbConn().commit();
	    }
	    catch (Exception e) {
		conBean.getDbConn().rollback();
		throw new UserAuthenticationException(e.getMessage());
	    }

	    RMT2SessionBean sessionBean = this.setupSessionBean(request, conBean);
	    if (sessionBean == null) {
		serverMsg = "User, " + loginId + ", failed authentication and authorization!\n";
		logLevel = Level.ERROR;
		return null;
	    }
	    serverMsg = "User, " + loginId + ", authentication was successful!\n";
	    serverMsg += "Session ID: " + sessionBean.getSessionId() + "\n";
	    serverMsg += "System updated user's login total successfully!";
	    logLevel = Level.INFO;

	    // Ensure the connection pool is on servlet context
	    //DatabaseConnectionPool.verifyConnectionPoolContext(request, conPool);
	    return sessionBean;
	}
	catch (UserAuthenticationException e) {
	    logLevel = Level.ERROR;
	    serverMsg = e.getMessage();
	    throw new LoginException(e.getMessage(), e.getErrorCode());
	}
	catch (SystemException e) {
	    logLevel = Level.ERROR;
	    serverMsg = e.getMessage();
	    throw new LoginException(e.getMessage(), e.getErrorCode());
	}
	catch (SQLException e) {
	    logLevel = Level.ERROR;
	    serverMsg = e.getMessage();
	    throw new LoginException(e.getMessage(), e.getErrorCode());
	}
	finally {
	    logger.log(logLevel, serverMsg);
	    // Render connection available since it was never registered with the session.
	    conBean.setInUse(false);
	    conBean.setLoginId(null);
	}
    }

    /**
     * Verifies that the remote user is valid and determines if the user is logged into the 
     * system via some remote application.
     * 
     * @param loginId The login id of the remote user.
     * @param sourceApp The name of the application the user accessed the system with.
     * @return A RMT2SessionBean instance as an arbitrary object.
     * @throws LoginException When the user fails to authenticate.
     */
    public Object authenticateRemoteUser(String loginId, String sourceApp) throws LoginException {
	Level logLevel = null;
	String serverMsg = null;
	DatabaseConnectionBean conBean = null;

	// Perform edits on user's credentials.
	if (loginId == null) {
	    serverMsg = "Remote user authentication failed.  Login id is required";
	    logLevel = Level.ERROR;
	    throw new LoginException(serverMsg);
	}
	if (sourceApp == null) {
	    serverMsg = "Remote user authentication failed.  Source application name is required";
	    throw new LoginException(serverMsg);
	}

	try {
	    conBean = DatabaseConnectionPool.getAvailConnectionBean(loginId);
	}
	catch (SystemException e) {
	    serverMsg = e.getMessage();
	    logLevel = Level.ERROR;
	    throw new LoginException(serverMsg);
	}
	conBean.setLoginId(loginId);

	// Begin to verify user
	try {
	    // Get User Security Manager API
	    UserApi api = UserFactory.createApi(conBean, this.request);
	    // Get an User Login Object
	    UserLogin userLoginBean = (UserLogin) api.findUserByLoginId(loginId);
	    if (userLoginBean == null) {
		serverMsg = "Remote Application access denied...User does not exist by Login Id: " + loginId;
		logLevel = Level.ERROR;
		throw new LoginException(serverMsg);
	    }

	    // Determine if user is logged into the system via some remote application.
	    ApplicationApi appApi = UserFactory.createAppApi(conBean);
	    ApplicationAccess aa = null;
	    Application a = null;
	    List list = (List) appApi.getLoggedInAppAccessProfiles(userLoginBean.getId());
	    if (list == null || list.size() == 0) {
		//		serverMsg = "Remote Application access denied...User is not logged into the system: " + loginId;
		logLevel = Level.ERROR;
		return null;
		//throw new LoginException(serverMsg);
	    }
	    else {
		aa = (ApplicationAccess) list.get(0);
		a = (Application) appApi.findApp(aa.getApplicationId());
		serverMsg = "System determined that user, " + loginId + ", has signed onto the system via the application,  " + a.getName();
		logLevel = Level.INFO;
	    }
	    // Return session bean to the caller.
	    RMT2SessionBean sessionBean = this.setupSessionBean(request, conBean);
	    // Set application id on the SessionBean
	    sessionBean.setOrigAppId(sourceApp);
	    return sessionBean;
	}
	catch (UserAuthenticationException e) {
	    logLevel = Level.ERROR;
	    serverMsg = e.getMessage();
	    throw new LoginException(e.getMessage(), e.getErrorCode());
	}
	catch (SystemException e) {
	    logLevel = Level.ERROR;
	    serverMsg = e.getMessage();
	    throw new LoginException(e.getMessage(), e.getErrorCode());
	}
	finally {
	    // Render connection available since it was never registered with the session.
	    logger.log(logLevel, serverMsg);
	    conBean.setInUse(false);
	    conBean.setLoginId(null);
	}
    }

    /**
     * This method obtains an HttpSession object ans associates the session with
     * the user. A session bean is instantiated and properly initialized, and
     * obtains the next available database connection from the connection pool.
     * The session bean and the database connection are assigned to the session
     * object. The session id is output to the console.
     * 
     * @param _request
     *            This is the Http Servlet request object.
     * @return RMT2SessionBean
     * @throws SystemException
     */
    protected RMT2SessionBean setupSessionBean(HttpServletRequest request,
            DatabaseConnectionBean conBean) throws SystemException {
        // At this point a session should already be assigned. If not, throw an
        // exception.
        HttpSession session = request.getSession(false);
        if (session == null) {
            this.msg = "SessionBean setup failed:  The session within the Authentication application could not be obtained.";
            logger.log(Level.ERROR, this.msg);
            throw new SystemException(this.msg);
        }

        // Create Session Bean
        RMT2SessionBean sessionBean = RMT2SessionBean.getInstance(request,
                session);
        sessionBean.setLoginId(conBean.getLoginId());

        // Add user specific data to the session bean
        Object user = this.setUserProfile(conBean, sessionBean);
        if (user == null) {
            logger.log(Level.ERROR,
                    "Error occurred obtaining User profile data");
            return null;
        }

        // Get User Roles.
        List roles = this.getUserRoles(sessionBean.getLoginId());
        sessionBean.setRoles(roles);

        return sessionBean;
    }

    /**
     * Logout user using HTTP technology.  This method removes the query object, database
     * connection object, and the session bean from the user's session after session
     * has become invalidated or inactive.
     *
     * @param _request This is the Http Servlet request object.
     */
    public void logout(Object dataObject) throws LoginException {
	Level logLevel = null;
	String serverMsg = null;
	DatabaseConnectionBean conBean = null;

	// See if we have a valid login credential object.
	this.validateInputObject(dataObject);
	Hashtable loginData = (Hashtable) dataObject;

	// Get user id and password
	String loginId = (String) loginData.get(AuthenticationConst.AUTH_PROP_USERID);

	// Create new session for the user.
	HttpServletRequest request = this.request;
	HttpSession session = request.getSession();

	// Borrow a database connection from the pool without registering it.
	try {
	    //conPool = DatabaseConnectionPool.getConnectionPool(context);
	    conBean = DatabaseConnectionPool.getAvailConnectionBean(loginId);
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new LoginException(e.getMessage());
	}
	conBean.setLoginId(loginId);

	// Begin to verify user
	try {
	    // Get User Security Manager API
	    UserApi api = UserFactory.createApi(conBean, this.request);
	    // Get an User Login Object
	    UserLogin userLoginBean = (UserLogin) api.findUserByLoginId(loginId);

	    // Remove user-application entries from the application access table.
	    ApplicationApi appApi = UserFactory.createAppApi(conBean);
	    int rows = appApi.removeUserApplicationAccess(userLoginBean.getId());

	    // Update user login profile.
	    api.updateUserLogin(userLoginBean);
	    conBean.getDbConn().commit();

	    serverMsg = "Total applications logged out: " + rows;
	    logLevel = Level.INFO;
	}
	catch (Exception e) {
	    try {
		conBean.getDbConn().rollback();
	    }
	    catch (Exception ee) {
		// Do nothing
	    }
	    logLevel = Level.ERROR;
	    serverMsg = e.getMessage();
	    throw new LoginException(e.getMessage());
	}
	finally {
	    this.logger.log(logLevel, serverMsg);
	}

	session.removeAttribute(RMT2ServletConst.QUERY_BEAN);
	session.invalidate();
    }

    /**
     * Verifies that dataObject is of type Hashtable.
     *
     * @param dataObject A request object.
     * @throws LoginException if dataObject is null or is found not to be of 
     *     type HttpServletRequest.
     */
    private void validateInputObject(Object dataObject) throws LoginException {
	if (dataObject == null) {
	    this.msg = "User data object is null";
	    logger.log(Level.ERROR, this.msg);
	    throw new LoginException(this.msg);
	}
	if (dataObject instanceof Hashtable) {
	    // dataObject is valid
	}
	else {
	    this.msg = "User data object is not an instance of RMT2SessionLoginBean";
	    logger.log(Level.ERROR, this.msg);
	    throw new LoginException(this.msg);
	}
	return;
    }

    /**
     * Obtains the user's login credentials that were entered on the front-end of
     * the application.
     *
     * @param request HttpServletRequest object.
     * @return Map of name/value pairs.
     */
    private Map getCredentials(HttpServletRequest request) {
	HashMap data = new HashMap();
	String userId = request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
	String password = request.getParameter(AuthenticationConst.AUTH_PROP_PASSWORD);
	data.put(AuthenticationConst.AUTH_PROP_USERID, userId);
	data.put(AuthenticationConst.AUTH_PROP_PASSWORD, password);
	return data;
    }

    /**
     * Validates login credentials.
     *
     * @param credentials Map of name/value pairs.
     * @throws LoginException When credentials is invalid, the login id is null, 
     *         password is null, or the user's request object is invalid.
     */
    private void validateCredentials(Hashtable credentials) throws LoginException {
	String temp;
	if (credentials == null) {
	    throw new LoginException("Login credential collection is invalid");
	}
	temp = (String) credentials.get(AuthenticationConst.AUTH_PROP_USERID);
	if (temp == null) {
	    throw new LoginException("Login must be supplied");
	}
	temp = (String) credentials.get(AuthenticationConst.AUTH_PROP_PASSWORD);
	if (temp == null) {
	    throw new LoginException("Password must be supplied");
	}
    }

    /**
     * Sets the request object
     * 
     * @param request
     */
    public void setRequest(HttpServletRequest request) {
	this.request = request;
    }

    /**
     * Sets the response object
     * 
     * @param request
     */
    public void setResponse(HttpServletResponse response) {
	this.response = response;
    }

    /**
     * The implementation of this method should be used to fetch a specific user profile
     * needed to initialize sessionBean.
     * <p>
     * <p>
     * For Example:
     * 	    VwEmployeeExt user = UserFactory.createEmployeeExt();
     *        DaoApi dao = DataSourceFactory.createDao(conBean);
     *        user.addCriteria("Login", loginId);
     *        try {
     *    	   Object[] result = dao.retrieve(user);
     *    	   if (result != null && result.length > 0) {
     *    		   return (VwEmployeeExt) result[0];
     *    	   }
     *    	   return null;
     *        }
     *        catch (Exception e) {
     *    	    return null;
     *        }
     *      	if (userProfile != null) {
     *	       sessionBean.setFirstName(userProfile.getFirstname());
     *	       sessionBean.setLastName(userProfile.getLastname());
     *	       sessionBean.setAccessLevel(0);
     *      	}
     *      	return userProfile;
     *
     *
     *
     * @param conBean Database connection bean
     * @param sessionBean User's session bean object
     * @return The actual user profile object used to initialize sessionBean.
     */
    protected abstract Object setUserProfile(DatabaseConnectionBean conBean, RMT2SessionBean sessionBean);

    /**
     * Gather each user role and package into a List collection.
     *  
     * @param loginId The user's login id
     * @return A list of roles.
     * @throws SystemException
     */
    protected abstract List getUserRoles(String loginId) throws SystemException;

}
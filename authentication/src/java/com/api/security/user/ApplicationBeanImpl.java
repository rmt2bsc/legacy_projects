package com.api.security.user;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.api.db.DatabaseException;

import com.api.DaoApi;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.security.UserAuthenticationException;

import com.bean.AppRole;
import com.bean.Roles;
import com.bean.UserAppRole;
import com.bean.UserLogin;
import com.bean.VwAppRoles;
import com.bean.Application;
import com.bean.ApplicationAccess;
import com.bean.VwUserAppRoles;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * Class responsible for managing the applications and applicaltion user access
 * profiles
 * 
 * @author roy.terrell
 * 
 */
public class ApplicationBeanImpl extends RdbmsDaoImpl implements ApplicationApi {
    private RdbmsDaoQueryHelper daoHelper;

    private Logger logger;

    /**
     * Default Constructor. Constructor begins the initialization of the
     * DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn
     * @throws SystemException
     * @throws DatabaseException
     */
    public ApplicationBeanImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	logger = Logger.getLogger("ApplicationBeanImpl");
    }

    /**
     * Create an ApplicationBeanImpl object using database connection and a http
     * servlet request.
     * 
     * @param dbConn
     *            THe database connection
     * @param request
     *            The user's request
     * @throws SystemException
     * @throws DatabaseException
     */
    public ApplicationBeanImpl(DatabaseConnectionBean dbConn, Request request) throws SystemException, DatabaseException {
	this(dbConn);
	this.request = request;
    }

    /**
     * Fetches all Application records from the database.   The default ordering will 
     * be by application name.
     * 
     * @return A List of {@link com.bean.Application Application} objects.
     * @throws UserAuthenticationException
     */
    public Object getAll() throws UserAuthenticationException {
	Application app = UserFactory.createApplication();
	app.addOrderBy(Application.PROP_NAME, Application.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Find application using primary key id.
     * 
     * @param uid
     *            A unique id identifying an application.
     * @return An {@link Application}
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Application findApp(int uid) throws UserAuthenticationException {
	Application app = UserFactory.createApplication();
	app.addCriteria(Application.PROP_APPID, uid);
	try {
	    return (Application) this.daoHelper.retrieveObject(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Find application using application name.
     * 
     * @param appName
     *            The name of the applicatio to query.
     * @return A List of {@link Application} objects; null when appName is null.
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Object findApp(String appName) throws UserAuthenticationException {
	if (appName == null) {
	    return null;
	}
	Application app = UserFactory.createApplication();
	app.addCriteria(Application.PROP_NAME, appName);
	try {
	    return this.daoHelper.retrieveObject(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get all roles belonging to an applicaltion.
     * 
     * @param appName
     *            The name of the application.
     * @return A List of one or more {@link com.bean.AppRole AppRole} objects; null when appName is null.
     * @throws UserAuthenticationException
     */
    public Object getRoles(String appName) throws UserAuthenticationException {
	if (appName == null) {
	    return null;
	}
	VwAppRoles roles = UserFactory.createVwAppRoles();
	roles.addCriteria(VwAppRoles.PROP_APPNAME, appName);
	try {
	    return this.daoHelper.retrieveList(roles);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Fetches all applications in the the databse system.
     * 
     * @return A List of {@link com.bean.Application Application}
     * @throws UserAuthenticationException
     */
    public Object getAllApps() throws UserAuthenticationException {
	Application app = UserFactory.createApplication();
	app.addOrderBy(Application.PROP_NAME, Application.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Fetches all roles in the the databse system.
     * 
     * @return A List of {@link com.bean.Roles Roles}
     * @throws UserAuthenticationException
     */
    public Object getAllRoles() throws UserAuthenticationException {
	Roles roles = UserFactory.createRole();
	roles.addOrderBy(Roles.PROP_NAME, Roles.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(roles);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Find a user's Application Access using primary key.
     * 
     * @param uid
     *            A unique id identifying a user's application access.
     * @return An {@link ApplicationAccess} object.
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Object findAppAccess(int uid) throws UserAuthenticationException {
	ApplicationAccess access = UserFactory.createApplicationAccess();
	access.addCriteria(ApplicationAccess.PROP_APPACCESSID, uid);
	try {
	    return this.daoHelper.retrieveObject(access);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Find a user's Application access using the user login id key value.
     * 
     * @param userLoginId
     *            The primary key of the user.
     * @return A List of one or more {@link ApplicationAccess} objects.
     * @throws UserAuthenticationException
     *             General Database errors.
     */
    public Object findAppAccessByUserLoginId(int userLoginId) throws UserAuthenticationException {
	ApplicationAccess access = UserFactory.createApplicationAccess();
	access.addCriteria(ApplicationAccess.PROP_LOGINID, userLoginId);
	try {
	    return this.daoHelper.retrieveList(access);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Find a user's Application access using an applicaltion id.
     * 
     * @param appId
     *            The application id.
     * @return A List of one or more {@link ApplicationAccess} objects.
     * @throws UserAuthenticationException
     */
    public Object findAppAccessByAppId(int appId) throws UserAuthenticationException {
	ApplicationAccess access = UserFactory.createApplicationAccess();
	access.addCriteria(ApplicationAccess.PROP_APPID, appId);
	try {
	    return this.daoHelper.retrieveList(access);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Find a user's application access using the application id and the user's
     * login uid.
     * 
     * @param appId
     *            The application id.
     * @param userLoginId
     *            The user's login id key.
     * @return An {@link ApplicationAccess} object
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Object findUserAppAccess(int appId, int userLoginId) throws UserAuthenticationException {
	ApplicationAccess access = UserFactory.createApplicationAccess();
	access.addCriteria(ApplicationAccess.PROP_APPID, appId);
	access.addCriteria(ApplicationAccess.PROP_LOGINID, userLoginId);
	try {
	    return this.daoHelper.retrieveObject(access);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains a list of applications which the user can access.
     * 
     * @param userLoginId User's login id.
     * @return a List of {@link com.bean.VwUserAppRoles VwUserAppRoles} objects.
     * @throws UserAuthenticationException
     */
    public Object findUserApps(String userLoginId) throws UserAuthenticationException {
	VwUserAppRoles ua = UserFactory.createVwUserAppRoles();
	ua.addCriteria(VwUserAppRoles.PROP_USERNAME, userLoginId);
	try {
	    return this.daoHelper.retrieveList(ua);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Retrieve one or more application access profiles which indicates the user
     * is logged into the system.
     * 
     * @param userLoginId
     *            The unique id of the user.
     * @return A List of one or more {@link ApplicationAccess} profiles.
     * @throws UserAuthenticationException
     *             General database error.
     */
    public Object getLoggedInAppAccessProfiles(int userLoginId) throws UserAuthenticationException {
	ApplicationAccess access = UserFactory.createApplicationAccess();
	access.addCriteria(ApplicationAccess.PROP_LOGINID, userLoginId);
	try {
	    return this.daoHelper.retrieveList(access);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Adds an entry into the application_access table. This entry indicates
     * that the user is logged into the system.
     * 
     * @param appAccess
     *            Application access object to add to the database.
     * @return The id of the ApplicationAccess profile updated.
     * @throws UserAuthenticationException
     */
    public int addUserApplicationAccess(ApplicationAccess appAccess) throws UserAuthenticationException {
	return this.insertApplicationAccess(appAccess);
    }

    /**
     * Removes one or more entries from the table, application_access, which
     * will indicate the user is no longer logged on to any application within
     * the system.
     * 
     * @param loginId
     *            the user's database primary key value.
     * @param sessionId 
     *            the user's unique session identifier.           
     * @return int 
     *            value indicating the total number of applications the user
     *            logged out.
     * @throws UserAuthenticationException
     */
    public int removeUserApplicationAccess(int loginId, String sessionId) throws UserAuthenticationException {
	ApplicationAccess access = UserFactory.createApplicationAccess();
	access.addCriteria(ApplicationAccess.PROP_LOGINID, loginId);
    if (sessionId != null) {
        access.addCriteria(ApplicationAccess.PROP_SESSIONID, sessionId);
    }
	try {
	    int rows = this.deleteRow(access);
	    return rows;
	}
	catch (DatabaseException e) {
	    this.msg = "Database Exception: " + e.getMessage();
	    this.logger.log(Level.ERROR, this.msg);
	    throw new UserAuthenticationException(this.msg);
	}
    }
    
    /**
     * 
     * @param loginId
     * @param appId
     * @return
     * @throws UserAuthenticationException
     */
    public int removeUserApplicationAccess(int loginId, int appId) throws UserAuthenticationException {
    	ApplicationAccess access = UserFactory.createApplicationAccess();
    	access.addCriteria(ApplicationAccess.PROP_LOGINID, loginId);
        access.addCriteria(ApplicationAccess.PROP_APPID, appId);
       try {
    	    int rows = this.deleteRow(access);
    	    return rows;
    	}
    	catch (DatabaseException e) {
    	    this.msg = "Database Exception: " + e.getMessage();
    	    this.logger.log(Level.ERROR, this.msg);
    	    throw new UserAuthenticationException(this.msg);
    	}
        }

    /**
     * Inserts a row into the application_access table for a user.
     * 
     * @param appAccess
     *            {@link ApplicationAccess}
     * @return Value > 0 when update is successful
     * @throws UserAuthenticationException
     */
    private int insertApplicationAccess(ApplicationAccess appAccess) throws UserAuthenticationException {
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	if (appAccess == null) {
	    this.msg = "ApplicaltionAccess input argument is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new UserAuthenticationException(this.msg);
	}
	try {
	    appAccess.addCriteria(ApplicationAccess.PROP_APPACCESSID, appAccess.getAppAccessId());
	    int newId = dao.insertRow(appAccess, true);
	    return newId;
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException("DatabaseException: " + e.getMessage(), e.getErrorCode());
	}
    }

    /**
     * Creates or modifies an application object.
     * 
     * @param app The application object to perform maintenance for.
     * @return The application id.
     * @throws ApplicationException
     */
    public int maintainApp(Application app) throws ApplicationException {
	if (app == null) {
	    throw new ApplicationException("Application Maintenanced failed.  Applicatin instance cannot be null");
	}
	if (app.getAppId() > 0) {
	    this.update(app);
	}
	if (app.getAppId() == 0) {
	    this.create(app);
	}
	return app.getAppId();
    }

    /**
     * Adds an application record to the database,.
     * 
     * @param app {@link com.bean.Application Application}.
     * @throws ApplicationException
     */
    protected void create(Application app) throws ApplicationException {
	this.validate(app);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    app.setDateCreated(ut.getDateCreated());
	    app.setDateUpdated(ut.getDateCreated());
	    app.setUserId(ut.getLoginId());
	    this.insertRow(app, true);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
    }

    /**
     * Updates one an application record in the database.
     * 
     * @param app {@link com.bean.Application Application}.
     * @throws ApplicationException for database and system errors.
     */
    protected void update(Application app) throws ApplicationException {
	this.validate(app);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    app.setDateUpdated(ut.getDateCreated());
	    app.setUserId(ut.getLoginId());
	    app.addCriteria(Application.PROP_APPID, app.getAppId());
	    this.updateRow(app);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
    }

    /**
     * This method is responsble for validating an application profile.  The name 
     * and description of the application are to have values. 
     * 
     * @param app {@link com.bean.Application Application}
     * @throws ApplicationException
     */
    private void validate(Application app) throws ApplicationException {
	if (app.getName() == null || app.getName().length() <= 0) {
	    this.msg = "Application name cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}

	if (app.getDescription() == null || app.getDescription().length() <= 0) {
	    this.msg = "User Maintenance: Description cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
    }

    /**
     * Delete an application from the system using the id of the application.
     * 
     * @param appId  The id of the application to delete.
     * @return Total number of rows deleted.
     * @throws ApplicationException
     */
    public int deleteApp(int appId) throws ApplicationException {
	if (appId <= 0) {
	    this.msg = "Application object delete failure.  Application id is invalid: " + appId;
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
	try {
	    Application app = UserFactory.createApplication();
	    app.addCriteria(Application.PROP_APPID, appId);
	    return this.deleteRow(app);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
    }

    /**
     * Get Application Role data from an RDBMS based on the unique 
     * identifier of the record.
     * 
     * @param uid The unique identifier of the application role.
     * @return An instance of {@link com.bean.AppRole AppRole}
     * @throws UserAuthenticationException
     */
    public Object getAppRole(int uid) throws UserAuthenticationException {
	AppRole obj = UserFactory.createAppRole();
	obj.addCriteria(AppRole.PROP_APPROLEID, uid);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get Application Role data based on the code name beginning with "code".
     * 
     * @param code The code name of the AppRole record.
     * @return List of {@link com.bean.AppRole AppRole} instances.
     * @throws UserAuthenticationException
     */
    public Object getAppRole(String code) throws UserAuthenticationException {
	AppRole obj = UserFactory.createAppRole();
	obj.addLikeClause(AppRole.PROP_CODE, code);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get all roles that are assigned to a particular user for a given application 
     * using objects, UserLogin and AppRole, as a basis for building selection 
     * criteria.
     * 
     * @param userCriteria 
     *           User login criteria set as a {@link com.bean.UserLogin UserLogin} object.
     * @param appRoleCriteria 
     *           Application role criteria set as a {@link com.bean.AppRole AppRole} object.
     * @return A List of {@link com.bean.VwUserAppRoles VwUserAppRoles} objects that match 
     *         the selection criteria.
     * @throws UserAuthenticationException
     */
    public List <VwUserAppRoles> getAppRoleAssigned(UserLogin userCriteria, AppRole appRoleCriteria) throws UserAuthenticationException {
	VwUserAppRoles obj = UserFactory.createVwUserAppRoles();
	if (userCriteria != null) {
	    if (userCriteria.getLoginId() > 0) {
		obj.addCriteria(VwUserAppRoles.PROP_LOGINUID, userCriteria.getLoginId());
	    }
	    if (userCriteria.getUsername() != null) {
		obj.addCriteria(VwUserAppRoles.PROP_USERNAME, userCriteria.getUsername());
	    }
	    if (userCriteria.getFirstname() != null) {
		obj.addCriteria(VwUserAppRoles.PROP_FIRSTNAME, userCriteria.getFirstname());
	    }
	    if (userCriteria.getLastname() != null) {
		obj.addCriteria(VwUserAppRoles.PROP_LASTNAME, userCriteria.getLastname());
	    }
	}

	if (appRoleCriteria != null) {
	    if (appRoleCriteria.getAppRoleId() > 0) {
		obj.addCriteria(VwUserAppRoles.PROP_APPROLEID, appRoleCriteria.getAppRoleId());
	    }
	    if (appRoleCriteria.getAppId() > 0) {
		obj.addCriteria(VwUserAppRoles.PROP_APPLICATIONID, appRoleCriteria.getAppId());
	    }
	    if (appRoleCriteria.getRoleId() > 0) {
		obj.addCriteria(VwUserAppRoles.PROP_ROLEID, appRoleCriteria.getRoleId());
	    }
	}
	StringBuffer customSql = new StringBuffer(100);
	customSql.append("app_role_id in (select x.app_role_id from user_app_role x, app_role z ");
	customSql.append("where x.app_role_id = z.app_role_id and ");
	customSql.append("z.app_id = application_id and ");
	customSql.append("x.login_id = login_uid)");
	obj.addCustomCriteria(customSql.toString());
	obj.addOrderBy(VwUserAppRoles.PROP_APPROLENAME, VwUserAppRoles.ORDERBY_ASCENDING);

	try {
	    List <VwUserAppRoles> results = this.daoHelper.retrieveList(obj);
	    if (results == null) {
		results = new ArrayList <VwUserAppRoles> ();
	    }
	    return results;
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}

    }

    /**
     * Get all roles that have been revoked or have not been assigned to a 
     * user for a given applicatin using the objects, UserLogin and AppRole, 
     * as a basis for building selection criteria.
     * 
     * @param userCriteria
     *           User login criteria set as a {@link com.bean.UserLogin UserLogin} object.
     * @param appRoleCriteria
     *           Application role criteria set as a {@link com.bean.AppRole AppRole} object.
     * @return A List of {@link com.bean.VwAppRoles VwAppRoles} objects that match 
     *         the selection criteria.
     * @throws UserAuthenticationException
     */
    public List <VwAppRoles> getAppRoleRevoked(UserLogin userCriteria, AppRole appRoleCriteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createVwAppRoles();
	if (appRoleCriteria != null) {
	    if (appRoleCriteria.getAppRoleId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_APPROLEID, appRoleCriteria.getAppRoleId());
	    }
	    if (appRoleCriteria.getAppId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_APPLICATIONID, appRoleCriteria.getAppId());
	    }
	    if (appRoleCriteria.getRoleId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_ROLEID, appRoleCriteria.getRoleId());
	    }
	}
	StringBuffer customSql = new StringBuffer(100);
	customSql.append("app_role_id not in (select x.app_role_id from user_app_role x, app_role z ");
	customSql.append("where x.app_role_id = z.app_role_id and ");
	customSql.append("z.app_id = application_id ");
	if (userCriteria.getLoginId() > 0) {
	    customSql.append(" and x.login_id = " + userCriteria.getLoginId());
	}
	customSql.append(")");

	obj.addCustomCriteria(customSql.toString());
	obj.addOrderBy(VwAppRoles.PROP_APPROLENAME, VwAppRoles.ORDERBY_ASCENDING);
	try {
	    List <VwAppRoles> results = this.daoHelper.retrieveList(obj);
	    if (results == null) {
		results = new ArrayList <VwAppRoles> ();
	    }
	    return results;
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get Application Role data based on information contained in an 
     * {@link com.bean.Application Application} instance.   The data 
     * values considered as selection criteria for this implementation
     * are application id (exact match) and application name (begins with match).
     * 
     * @param criteria An instance of the Application object containing values 
     *            needed to build selection criteria.
     * @return List of {@link com.bean.VwAppRoles VwAppRoles} instances.
     * @throws UserAuthenticationException
     */
    public Object getAppRole(Application criteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createVwAppRoles();
	if (criteria != null) {
	    if (criteria.getAppId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_APPLICATIONID, criteria.getAppId());
	    }
	    if (criteria.getName() != null) {
		obj.addLikeClause(VwAppRoles.PROP_APPNAME, criteria.getName());
	    }
	}
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get Application Role data based on information contained in an 
     * {@link com.bean.Roles Roles} instance.   The data values considered 
     * as selection criteria for this implementation are role id (exact match) 
     * and role name (begins with match).
     * 
     * @param criteria An instance of the Roles object containing values 
     *            needed to build selection criteria.
     * @return List of {@link com.bean.VwAppRoles VwAppRoles} instances.
     * @throws UserAuthenticationException
     */
    public Object getAppRole(Roles criteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createVwAppRoles();
	if (criteria != null) {
	    if (criteria.getRoleId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_ROLEID, criteria.getRoleId());
	    }
	    if (criteria.getName() != null) {
		obj.addLikeClause(VwAppRoles.PROP_ROLENAME, criteria.getName());
	    }
	}
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get Application Role data based on information contained in 
     * {@link com.bean.Application Application} and {@link com.bean.Roles Roles} 
     * instances.  The data values considered as selection criteria for this 
     * implementation are application id (exact match), application name (begins 
     * with match), role id (exact match) and role name (begins with match).
     * 
     * @param appCriteria An instance of the Application object containing values 
     *            needed to build selection criteria.
     * @param roleCriteria An instance of the Roles object containing values 
     *            needed to build selection criteria.
     * @return List of {@link com.bean.VwAppRoles VwAppRoles} instances.
     * @throws UserAuthenticationException
     */
    public Object getAppRole(Application appCriteria, Roles roleCriteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createVwAppRoles();
	if (appCriteria != null) {
	    if (appCriteria.getAppId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_APPLICATIONID, appCriteria.getAppId());
	    }
	    if (appCriteria.getName() != null) {
		obj.addLikeClause(VwAppRoles.PROP_APPNAME, appCriteria.getName());
	    }
	}
	if (roleCriteria != null) {
	    if (roleCriteria.getRoleId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_ROLEID, roleCriteria.getRoleId());
	    }
	    if (roleCriteria.getName() != null) {
		obj.addLikeClause(VwAppRoles.PROP_ROLENAME, roleCriteria.getName());
	    }
	}
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.api.security.user.ApplicationApi#maintainAppRole(com.bean.AppRole)
     */
    public int maintainAppRole(AppRole obj) throws ApplicationException {
	if (obj == null) {
	    throw new ApplicationException("Application Role Maintenance failed.  Applicatin instance cannot be null");
	}
	if (obj.getAppRoleId() > 0) {
	    this.updateAppRole(obj);
	}
	if (obj.getAppRoleId() == 0) {
	    this.createAppRole(obj);
	}
	return obj.getAppRoleId();
    }

    /**
     * Adds an application role record to the database,.
     * 
     * @param app {@link com.bean.AppRole AppRole}.
     * @throws ApplicationException
     */
    protected void createAppRole(AppRole obj) throws ApplicationException {
	this.validateAppRole(obj);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	    this.insertRow(obj, true);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
    }

    /**
     * Updates one an application record in the database.
     * 
     * @param app {@link com.bean.Application Application}.
     * @throws ApplicationException for database and system errors.
     */
    protected void updateAppRole(AppRole obj) throws ApplicationException {
	this.validateAppRole(obj);
	UserTimestamp ut = null;
	int rc = 0;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	    obj.addCriteria(AppRole.PROP_APPROLEID, obj.getAppRoleId());
	    rc = this.updateRow(obj);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
    }

    /**
     * This method is responsble for validating an application profile.  The name, 
     * description, code, application id, and role id must contain valid values. 
     * 
     * @param app {@link com.bean.AppRole AppRole}
     * @throws ApplicationException
     */
    private void validateAppRole(AppRole obj) throws ApplicationException {
	if (obj.getAppId() <= 0) {
	    this.msg = "Application Id is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
	if (obj.getRoleId() <= 0) {
	    this.msg = "Role Id is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
	if (obj.getCode() == null || obj.getCode().length() <= 0) {
	    this.msg = "Code cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
	if (obj.getName() == null || obj.getName().length() <= 0) {
	    this.msg = "Name cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
	if (obj.getDescription() == null || obj.getDescription().length() <= 0) {
	    this.msg = "Description cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
    }

    /* (non-Javadoc)
     * @see com.api.security.user.ApplicationApi#deleteAppRole(int)
     */
    public int deleteAppRole(int uid) throws ApplicationException {
	if (uid <= 0) {
	    this.msg = "Application Role delete failure.  Application Role id is invalid: " + uid;
	    logger.log(Level.ERROR, this.msg);
	    throw new ApplicationException(this.msg);
	}
	try {
	    AppRole obj = UserFactory.createAppRole();
	    obj.addCriteria(AppRole.PROP_APPROLEID, uid);
	    return this.deleteRow(obj);
	}
	catch (DatabaseException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ApplicationException(this.msg);
	}
    }

    /**
     * Updates the user's role profile with the newly assigned roles.   First, all 
     * roles are deleted from the database belonging to the user, and lastly, only 
     * the new list of assigned roles are assoicated with the user.
     * 
     * @param loginId The login id belonging to the user.
     * @param appId The id of the application.
     * @param assignedRoles 
     *            A String array of assigned application-role id's.
     * @param revokedRoles 
     *            A String array of revoked application-role id's.  Currently, there is 
     *            no use for these roles.
     * @return Total number of assigned rows added.
     * @throws ApplicationException
     */
    public int maintainUserAppRole(String loginId, int appId, String[] assignedRoles, String[] revokedRoles) throws ApplicationException {
	int rows;
	int rc;
	UserLogin user = UserFactory.createUserLogin();

	// Verify login id.
	user.addCriteria(UserLogin.PROP_USERNAME, loginId);
	try {
	    user = (UserLogin) this.daoHelper.retrieveObject(user);
	    if (user == null) {
		throw new ApplicationException("User Application Role update failed due to user login is invalid");
	    }
	}
	catch (DatabaseException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ApplicationException(e);
	}

	// Get all roles belonging to a particular application
	String roles[] = null;
	try {
	    AppRole ar = UserFactory.createAppRole();
	    ar.addCriteria(AppRole.PROP_APPID, appId);
	    List list = this.daoHelper.retrieveList(ar);
	    if (list != null || list.size() > 0) {
		roles = new String[list.size()];
		int ndx = 0;
		for (Object obj : list) {
		    roles[ndx++] = String.valueOf(((AppRole) obj).getAppRoleId());
		}
	    }
	}
	catch (DatabaseException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ApplicationException(e);
	}

	// Delete all roles assoicated with a particular application for the user.
	try {
	    UserAppRole uar = UserFactory.createUserAppRole();
	    uar.addCriteria(UserAppRole.PROP_LOGINID, user.getLoginId());
	    // Consider role condition only if one or more application roles were found above. 
	    if (roles != null && roles.length > 0) {
		uar.addInClause(UserAppRole.PROP_APPROLEID, roles);
	    }
	    rows = this.deleteRow(uar);
	    logger.log(Level.DEBUG, "Total number of user application-roles deleted for user, " + user.getLoginId() + ": " + rows);
	}
	catch (DatabaseException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ApplicationException(e);
	}

	// Verify that we have a valid list of assigned roles.  An invalid list means 
	// that all user roles were revoked and there are no new roles to assign.
	if (assignedRoles == null) {
	    return 0;
	}
	// Add each user role based on the new list of assigned roles.
	rows = 0;
	for (String role : assignedRoles) {
	    try {
		int roleId = Integer.parseInt(role);
		UserAppRole uar = UserFactory.createUserAppRole();
		uar.setLoginId(user.getLoginId());
		uar.setAppRoleId(roleId);
		UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
		uar.setDateCreated(ut.getDateCreated());
		uar.setDateUpdated(ut.getDateCreated());
		uar.setUserId(ut.getLoginId());
		rc = this.insertRow(uar, true);
		rows++;
	    }
	    catch (DatabaseException e) {
		this.msg = "DatabaseException: " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ApplicationException(this.msg);
	    }
	    catch (SystemException e) {
		this.msg = "SystemException: " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ApplicationException(this.msg);
	    }
	}
	return rows;
    }

}
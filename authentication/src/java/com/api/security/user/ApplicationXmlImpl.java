package com.api.security.user;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.security.UserAuthenticationException;

import com.bean.AppRole;
import com.bean.Application;
import com.bean.ApplicationAccess;
import com.bean.Roles;
import com.bean.UserLogin;
import com.bean.VwAppRoles;
import com.bean.VwUserAppRoles;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

/**
 * Class responsible for managing the applications and applicaltion user access
 * profiles which data is managed as XML.
 * 
 * @author roy.terrell
 * 
 */
public class ApplicationXmlImpl extends RdbmsDaoImpl implements ApplicationApi {
    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    private ApplicationApi api;

    /**
     * Default Constructor. Constructor begins the initialization of the
     * DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn
     * @throws SystemException
     * @throws DatabaseException
     */
    public ApplicationXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	logger = Logger.getLogger("ApplicationXmlImpl");
	this.logger.log(Level.INFO, "ApplicationXmlImpl object created");
	this.api = UserFactory.createAppApi(dbConn);
    }

    /**
     * Create an ApplicationXmlImpl object using database connection and a http
     * servlet request.
     * 
     * @param dbConn
     *            The database connection
     * @param request
     *            The user's request
     * @throws SystemException
     * @throws DatabaseException
     */
    public ApplicationXmlImpl(DatabaseConnectionBean dbConn, Request request) throws SystemException, DatabaseException {
	this(dbConn);
	this.request = request;
    }

    /**
     * Fetches all Application records from the database.   The default ordering will 
     * be by application name.
     * 
     * @return A XML document of one or more {@link com.bean.Application Application} objects as a String.
     * @throws UserAuthenticationException
     */
    public Object getAll() throws UserAuthenticationException {
	Application app = UserFactory.createXmlApplication();
	app.addOrderBy(Application.PROP_NAME, Application.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Fetches all applications in the the databse system.
     * 
     * @return A XML document of one or more {@link com.bean.Application Application} objects as a String.
     * @throws UserAuthenticationException
     */
    public Object getAllApps() throws UserAuthenticationException {
	Application app = UserFactory.createXmlApplication();
	app.addOrderBy(Application.PROP_NAME, Application.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Fetches all roles in the the databse system.
     * 
     * @return A XML document of one or more {@link com.bean.Roles Roles} objects as a String.A List of 
     * @throws UserAuthenticationException
     */
    public Object getAllRoles() throws UserAuthenticationException {
	Roles roles = UserFactory.createXmlRole();
	roles.addOrderBy(Roles.PROP_NAME, Roles.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(roles);
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
     * @return {@link Application} data as a XML document.
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Object findApp(int uid) throws UserAuthenticationException {
	Application app = UserFactory.createXmlApplication();
	app.addCriteria(Application.PROP_APPID, uid);
	try {
	    return this.daoHelper.retrieveXml(app);
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
     * @return One or more {@link Application} objects as a XML document.
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Object findApp(String appName) throws UserAuthenticationException {
	Application app = UserFactory.createXmlApplication();
	app.addCriteria(Application.PROP_NAME, appName);
	try {
	    return this.daoHelper.retrieveXml(app);
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
     * @return An {@link ApplicationAccess} object as a XML document.
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Object findAppAccess(int uid) throws UserAuthenticationException {
	ApplicationAccess app = UserFactory.createXmlApplicationAccess();
	app.addCriteria(ApplicationAccess.PROP_APPACCESSID, uid);
	try {
	    return this.daoHelper.retrieveXml(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Find a user's Application access using the user login id key value.
     * 
     * @param userLoginId
     *            THe primary key of the user.
     * @return One or more {@link ApplicationAccess} objects as a XML document.
     * @throws UserAuthenticationException
     *             General Database errors.
     */
    public Object findAppAccessByUserLoginId(int userLoginId) throws UserAuthenticationException {
	ApplicationAccess app = UserFactory.createXmlApplicationAccess();
	app.addCriteria(ApplicationAccess.PROP_LOGINID, userLoginId);
	try {
	    return this.daoHelper.retrieveXml(app);
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
     * @return One or more {@link ApplicationAccess} objects as a XML document.
     * @throws UserAuthenticationException
     */
    public Object findAppAccessByAppId(int appId) throws UserAuthenticationException {
	ApplicationAccess app = UserFactory.createXmlApplicationAccess();
	app.addCriteria(ApplicationAccess.PROP_APPID, appId);
	try {
	    return this.daoHelper.retrieveXml(app);
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
     * @return An {@link ApplicationAccess} object as a XML document.
     * @throws UserAuthenticationException
     *             General database errors.
     */
    public Object findUserAppAccess(int appId, int userLoginId) throws UserAuthenticationException {
	ApplicationAccess app = UserFactory.createXmlApplicationAccess();
	app.addCriteria(ApplicationAccess.PROP_APPID, appId);
	app.addCriteria(ApplicationAccess.PROP_LOGINID, userLoginId);
	try {
	    return this.daoHelper.retrieveXml(app);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains a list of applications in the form of an XML document which the 
     * user can access.
     * 
     * @param userName User's login id.
     * @return XML document repesenting one or more {@link com.bean.VwUserAppRoles VwUserAppRoles} objects.
     * @throws UserAuthenticationException
     */
    public Object findUserApps(String userName) throws UserAuthenticationException {
	VwUserAppRoles ua = UserFactory.createXmlVwUserAppRoles();
	ua.addCriteria(VwUserAppRoles.PROP_USERNAME, userName);
	try {
	    return this.daoHelper.retrieveXml(ua);
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
     * @return A List of one or more {@link com.bean.AppRole AppRole} objects.
     * @throws UserAuthenticationException
     */
    public Object getRoles(String appName) throws UserAuthenticationException {
	// Using the application object, get all application roles.
	VwAppRoles roles = UserFactory.createVwAppRoles();
	roles.addCriteria(VwAppRoles.PROP_APPNAME, appName);
	try {
	    return this.daoHelper.retrieveXml(roles);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Retrieve one or more application access profiles indicating that a user
     * is logged into the system.
     * 
     * @param userLoginId
     *            The unique id of the user.
     * @return One or more {@link ApplicationAccess} profiles in XML format.
     * @throws UserAuthenticationException
     *             General database error.
     */
    public Object getLoggedInAppAccessProfiles(int userLoginId) throws UserAuthenticationException {
	ApplicationAccess access = UserFactory.createXmlApplicationAccess();
	access.addCriteria(ApplicationAccess.PROP_LOGINID, userLoginId);
	try {
	    return this.daoHelper.retrieveXml(access);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Updates user's Application access profile by indicating that user is
     * logged into the system.
     * 
     * @param appAccess
     *            Application access object.
     * @return The id of the ApplicationAccess profile updated.
     * @throws UserAuthenticationException
     */
    public int addUserApplicationAccess(ApplicationAccess appAccess) throws UserAuthenticationException {
	return this.api.addUserApplicationAccess(appAccess);
    }

    /**
     * Removes one or more entries from the table, application_access, which
     * will indicate the user is no longer logged on to any application within
     * the system.
     * 
     * @param loginId
     *            The user's database primary key value.
     * @param sessionId 
     *            the user's unique session identifier.            
     * @return An int value indicating the total number of applications the user
     *         logged out.
     * @throws UserAuthenticationException
     */
    public int removeUserApplicationAccess(int loginId, String sessionId) throws UserAuthenticationException {
	return api.removeUserApplicationAccess(loginId, sessionId);
    }
    
    /**
     * 
     * @param loginId
     * @param appId
     * @return
     * @throws UserAuthenticationException
     */
    public int removeUserApplicationAccess(int loginId, int appId) throws UserAuthenticationException {
    	return api.removeUserApplicationAccess(loginId, appId);
    }

    /**
     * No action
     */
    public int deleteApp(int appId) throws ApplicationException {
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * No action.
     */
    public int maintainApp(Application app) throws ApplicationException {
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * Get Application Role data from an RDBMS based on the unique 
     * identifier of the record.
     * 
     * @param uid The unique identifier of the application role.
     * @return A XML document representing {@link com.bean.AppRole AppRole}
     * @throws UserAuthenticationException
     */
    public Object getAppRole(int uid) throws UserAuthenticationException {
	AppRole obj = UserFactory.createXmlAppRole();
	obj.addCriteria(AppRole.PROP_APPROLEID, uid);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get Application Role data based on the code name beginning with "code".
     * 
     * @param code The code name of the AppRole record.
     * @return A XML document as a list of {@link com.bean.AppRole AppRole} instances.
     * @throws UserAuthenticationException
     */
    public Object getAppRole(String code) throws UserAuthenticationException {
	AppRole obj = UserFactory.createXmlAppRole();
	obj.addLikeClause(AppRole.PROP_CODE, code);
	try {
	    return this.daoHelper.retrieveXml(obj);
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
     * @return A XML document as a list of {@link com.bean.VwAppRoles VwAppRoles} instances. 
     * @throws UserAuthenticationException
     */
    public Object getAppRole(Application criteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createXmlVwAppRoles();
	if (criteria != null) {
	    if (criteria.getAppId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_APPLICATIONID, criteria.getAppId());
	    }
	    if (criteria.getName() != null) {
		obj.addLikeClause(VwAppRoles.PROP_APPNAME, criteria.getName());
	    }
	}
	try {
	    return this.daoHelper.retrieveXml(obj);
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
     * @return A XML document as a list of {@link com.bean.VwAppRoles VwAppRoles} instances. 
     * @throws UserAuthenticationException
     */
    public Object getAppRole(Roles criteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createXmlVwAppRoles();
	if (criteria != null) {
	    if (criteria.getRoleId() > 0) {
		obj.addCriteria(VwAppRoles.PROP_ROLEID, criteria.getRoleId());
	    }
	    if (criteria.getName() != null) {
		obj.addLikeClause(VwAppRoles.PROP_ROLENAME, criteria.getName());
	    }
	}
	try {
	    return this.daoHelper.retrieveXml(obj);
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
     * @return A XML document as a list of {@link com.bean.VwAppRoles VwAppRoles} instances. 
     * @throws UserAuthenticationException
     */
    public Object getAppRole(Application appCriteria, Roles roleCriteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createXmlVwAppRoles();
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
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Get all roles that have been assigned to a user for a given applicatin using 
     * the objects, UserLogin and AppRole, as a basis for building selection criteria.
     * 
     * @param userCriteria
     *           User login criteria set as a {@link com.bean.UserLogin UserLogin} object.
     * @param appRoleCriteria
     *           Application role criteria set as a {@link com.bean.AppRole AppRole} object.
     * @return XML document as a String which the data is mapped to a 
     *         {@link com.bean.VwUserAppRoles VwUserAppRoles} object.
     * @throws UserAuthenticationException
     */
    public Object getAppRoleAssigned(UserLogin userCriteria, AppRole appRoleCriteria) throws UserAuthenticationException {
	VwUserAppRoles obj = UserFactory.createXmlVwUserAppRoles();
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
	    return this.daoHelper.retrieveXml(obj);
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
     * @return XML document as a String which the data is mapped to a 
     *         {@link com.bean.VwAppRoles VwAppRoles} object.
     * @throws UserAuthenticationException
     */
    public Object getAppRoleRevoked(UserLogin userCriteria, AppRole appRoleCriteria) throws UserAuthenticationException {
	VwAppRoles obj = UserFactory.createXmlVwAppRoles();
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
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * No action.
     * @return zero
     */
    public int maintainAppRole(AppRole obj) throws ApplicationException {
	return 0;
    }

    /**
     * No action.
     * @return zero
     */
    public int deleteAppRole(int uid) throws ApplicationException {
	return 0;
    }

    /* Not implemented.
     * @see com.api.security.user.ApplicationApi#maintainUserAppRole(java.lang.String, java.lang.String[], java.lang.String[])
     */
    public int maintainUserAppRole(String loginId, int appId, String[] assignedRoles, String[] revokedRoles) throws ApplicationException {
	// TODO Auto-generated method stub
	return 0;
    }

}
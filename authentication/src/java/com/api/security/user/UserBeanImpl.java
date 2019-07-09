package com.api.security.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.db.orm.DataSourceFactory;
import com.api.DaoApi;

import com.api.security.UserAuthenticationException;

import com.bean.ApplicationAccess;
import com.bean.Roles;
import com.bean.UserLogin;
import com.bean.UserGroup;
import com.bean.VwUser;
import com.bean.VwUserGroup;
import com.bean.VwUserAppRoles;

import com.bean.criteria.UserCriteria;
import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.constants.UserConst;

import com.controller.Request;

import com.util.RMT2Base64Encoder;
import com.util.RMT2Base64Decoder;
import com.util.RMT2Date;
import com.util.SystemException;

/**
 * Class responsible for qerying, creating, and modifying user and user 
 * group profiles pertaining to security.  
 * 
 * @author roy.terrell
 *
 */
public class UserBeanImpl extends RdbmsDaoImpl implements UserApi {
    /** The selection criteria used to fetch UserLogin data. */
    protected String criteria;

    private int encryptCycles;

    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * Default Constructor. Constructor begins the initialization of the
     * DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn
     * @throws SystemException
     * @throws DatabaseException
     */
    public UserBeanImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
        super(dbConn);
        this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
        logger = Logger.getLogger("UserBeanImpl");

        String temp = System.getProperty(HttpSystemPropertyConfig.PROPNAME_ENCRYPT_CYCLE);
        try {
            this.encryptCycles = new Integer(temp).intValue();
        }
        catch (NumberFormatException e) {
            this.encryptCycles = 0;
        }
    }

    /**
     * Create an UserBeanImpl object using database connection and a http servlet request.
     * 
     * @param dbConn THe database connection
     * @param request The user's request
     * @throws SystemException
     * @throws DatabaseException
     */
    public UserBeanImpl(DatabaseConnectionBean dbConn, Request request) throws SystemException, DatabaseException {
        this(dbConn);
        this.request = request;
    }

    /**
     * Find user using primary key id.
     * 
     * @param uid A unique id identifying the user.
     * @return {@link com.bean.UserLogin UserLogin}
     * @throws UserAuthenticationException
     */
    public Object findUser(int uid) throws UserAuthenticationException {
        UserLogin user = UserFactory.createUserLogin();
        user.addCriteria(UserLogin.PROP_LOGINID, uid);
        try {
            return this.daoHelper.retrieveObject(user);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Find user using login id.
     * 
     * @param userName The user name.
     * @return {@link com.bean.UserLogin UserLogin}
     * @throws UserAuthenticationException
     */
    public UserLogin findUserByUserName(String userName) throws UserAuthenticationException {
        UserLogin user = UserFactory.createUserLogin();
        user.addCriteria(UserLogin.PROP_USERNAME, userName);
        try {
            return (UserLogin) this.daoHelper.retrieveObject(user);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Find one or more users using custom selection criteria.
     * 
     * @param criteria String representing the SQL where clause.
     * @return A List of one or more {@link com.bean.UserLogin UserLogin} objects.
     * @throws UserAuthenticationException
     */
    public Object findUser(String criteria) throws UserAuthenticationException {
        UserLogin user = UserFactory.createUserLogin();
        user.addCustomCriteria(criteria);
        try {
            return this.daoHelper.retrieveList(user);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Retrieve data pertaining to one or more user extension profiles in the format of XML 
     * using custom selection criteria.
     * 
     * @param criteria Custom selection criteria.
     * @return A List of one or more {@link com.bean.VwUser VwUser} instances that  meet the 
     *         custom selection criteria.  The results packaged in ascending order by
     *         last name, first name, and user name.
     * @throws UserAuthenticationException
     */
    public List <VwUser> findUserExt(String criteria) throws UserAuthenticationException {
        VwUser user = UserFactory.createUserExt();
        user.addCustomCriteria(criteria);
        user.addOrderBy(VwUser.PROP_LASTNAME, VwUser.ORDERBY_ASCENDING);
        user.addOrderBy(VwUser.PROP_FIRSTNAME, VwUser.ORDERBY_ASCENDING);
        user.addOrderBy(VwUser.PROP_USERNAME, VwUser.ORDERBY_ASCENDING);
        try {
            return this.daoHelper.retrieveList(user);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Fetches one or more active sessions by session id.
     * 
     * @param sessionId
     *          the id of the session
     * @return
     *         List of {@link com.bean.ApplicationAccess ApplicationAccess} or null if no records are found.
     * @throws UserAuthenticationException
     * @see com.api.security.user.UserApi#getActiveSessions(java.lang.String)
     */
    public List <ApplicationAccess> getActiveSessions(String sessionId) throws UserAuthenticationException {
        ApplicationAccess access = UserFactory.createApplicationAccess();
        access.addCriteria(ApplicationAccess.PROP_SESSIONID, sessionId);
        try {
            return this.daoHelper.retrieveList(access);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }
    
    
    /**
     * Obtains the user's group data.
     * 
     * @param userName the user's login id.
     * @return {@link com.bean.UserGroup VwUserGroup} object.
     * @throws UserAuthenticationException
     */
    public Object getUserGroup(String userName) throws UserAuthenticationException {
        VwUserGroup grp = UserFactory.createVwUserGroup();
        grp.addCriteria(VwUserGroup.PROP_USERNAME, userName);
        try {
            return this.daoHelper.retrieveObject(grp);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Obtains a user group by its unique id from the database.
     * 
     * @param grpId The group id.
     * @return {@link com.bean.UserGroup UserGroup} 
     * @throws UserAuthenticationException
     */
    public Object getGroup(int grpId) throws UserAuthenticationException {
        UserGroup grp = UserFactory.createUserGroup();
        grp.addCriteria(UserGroup.PROP_GRPID, grpId);
        try {
            return this.daoHelper.retrieveObject(grp);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Obtains all user groups.
     * 
     * @return A List of {@link com.bean.UserGroup UserGroup} objects.
     * @throws UserAuthenticationException
     */
    public Object getAllGroups() throws UserAuthenticationException {
        UserGroup grp = UserFactory.createUserGroup();
        grp.addOrderBy(UserGroup.PROP_DESCRIPTION, UserGroup.ORDERBY_ASCENDING);
        try {
            return this.daoHelper.retrieveList(grp);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Obtains one or more roles in which a user is assigned.
     * 
     * @param userName The user's login id.
     * @return A list of {@link com.bean.VwUserAppRoles VwUserAppRoles} that are assigned 
     *         to the user identified as loginId.
     * @throws UserAuthenticationException
     */
    public Object getRoles(String userName) throws UserAuthenticationException {
        VwUserAppRoles roles = UserFactory.createVwUserAppRoles();
        roles.addCriteria(VwUserAppRoles.PROP_USERNAME, userName);
        try {
            return this.daoHelper.retrieveList(roles);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Obtains one or more roles assigned to a user for a specific application.
     * 
     * @param userName The user's login id.
     * @param appName The application to target for user roles.
     * @return A List of {@link com.bean.AppRole AppRole} objects. 
     * @throws UserAuthenticationException
     */
    public Object getRoles(String userName, String appName) throws UserAuthenticationException {
        VwUserAppRoles roles = UserFactory.createVwUserAppRoles();
        roles.addCriteria(VwUserAppRoles.PROP_USERNAME, userName);
        roles.addCriteria(VwUserAppRoles.PROP_APPNAME, appName);
        try {
            return this.daoHelper.retrieveList(roles);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int maintainUser(UserLogin _user) throws UserAuthenticationException {
        int userLoginId;
        String encryptPw;

        if (_user == null) {
            throw new UserAuthenticationException(this.connector, 1013, null);
        }
        if (_user.getLoginId() < 0) {
            throw new UserAuthenticationException(this.connector, 1014, null);
        }
        if (_user.getUsername() == null) {
            throw new UserAuthenticationException(this.connector, 1015, null);
        }
        if (_user.getPassword() == null) {
            throw new UserAuthenticationException(this.connector, 1016, null);
        }

        // Get Encryption version of the password provided the 
        // password has been changed.
        if (_user.getPassword().equals(UserConst.PASSWORD_GARBAGE)) {
            // Do not encrypt.  Prevent password from changing
            encryptPw = _user.getPassword();
        }
        else {
            // User has changed password, so encrypt.
            encryptPw = this.encryptPassword(_user.getPassword());
            logger.log(Level.DEBUG, encryptPw);
            _user.setPassword(encryptPw);
        }

        if (_user.getLoginId() == 0) {
            userLoginId = this.createUserLogin(_user);
        }
        else {
            userLoginId = this.updateUserLogin(_user);
        }

        return userLoginId;
    }

    /**
     * Creates an User Login.
     * 
     * @param _usr
     *            The user login object to persist
     * @return The new user id.
     * @throws UserAuthenticationException
     */
    protected int createUserLogin(UserLogin _usr) throws UserAuthenticationException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        // Verify that user does not exist.
        try {
            this.getUserLoginRecord(_usr.getUsername());
            throw new UserAuthenticationException("User already exists");
        }
        catch (Exception e) {
            // COntinue processing
        }

        // Add record to database
        try {
            UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
            _usr.setActive(1);
            _usr.setDateCreated(ut.getDateCreated());
            _usr.setDateUpdated(ut.getDateCreated());
            _usr.setUserId(ut.getLoginId());
            int rc = dao.insertRow(_usr, true);
            _usr.setLoginId(rc);
            return rc;
        }
        catch (Exception e) {
            throw new UserAuthenticationException(e.getMessage());
        }
    }

    /**
     * Updates user's login profile
     * 
     * @param _usr
     *            The user changes to persist
     * @return The id of the user.
     * @throws UserAuthenticationException
     */
    public int updateUserLogin(UserLogin _usr) throws UserAuthenticationException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        UserLogin origUser = this.getUserLoginRecord(_usr.getUsername());
        // Determine if password will be changeing
        if (_usr.getPassword().equalsIgnoreCase(UserConst.PASSWORD_GARBAGE)) {
            // Password will not be changing.
            _usr.setPassword(origUser.getPassword());
        }
        // Perform updates
        _usr.addCriteria("LoginId", _usr.getLoginId());
        try {
            UserTimestamp ut = RMT2Date.getUserTimeStamp(_usr.getUsername());
            _usr.setDateUpdated(ut.getDateCreated());
            _usr.setUserId(ut.getLoginId());
            dao.updateRow(_usr);
            return _usr.getLoginId();
        }
        catch (Exception e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void deleteUser(UserLogin _user) throws UserAuthenticationException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        _user.setActive(0);
        _user.addCriteria("LoginId", _user.getLoginId());
        try {
            dao.deleteRow(_user);
            return;
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e.getMessage(), e.getErrorCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void activateUser(UserLogin _user) throws UserAuthenticationException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        _user.setActive(1);
        _user.addCriteria("LoginId", _user.getLoginId());
        try {
            dao.updateRow(_user);
            return;
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e.getMessage(), e.getErrorCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void inActivateUser(UserLogin _user) throws UserAuthenticationException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        _user.setActive(0);
        _user.addCriteria("LoginId", _user.getLoginId());
        try {
            dao.updateRow(_user);
            return;
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e.getMessage(), e.getErrorCode());
        }
    }

    /**
     * Updates row in the user_login table by incrementing the current login count by 1.
     * 
     * @param usr
     *            UserLogin
     * @throws UserAuthenticationException
     */
    public void updateUserLoginCount(UserLogin usr) throws UserAuthenticationException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        int count = usr.getTotalLogons();
        count++;
        usr.setTotalLogons(count);
        usr.addCriteria("LoginId", usr.getLoginId());
        try {
            dao.updateRow(usr);
            return;
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e.getMessage(), e.getErrorCode());
        }
    }

    /**
     * Verify a user based the values of _userId and _password.
     * 
     * @param loginId The login id of the user.
     * @param password The user's passowrd.
     * @return A valid UserLogin object or null if login credentials failed.
     * @throws {@link com.api.security.UserAuthenticationException UserAuthenticationException}
     */
    public Object verifyLogin(String userId, String password) throws UserAuthenticationException {
        UserLogin user = this.getUserLoginRecord(userId);
        String pw = this.decryptPassword(user.getPassword());
        if (!pw.equalsIgnoreCase(password)) {
            throw new UserAuthenticationException("Password is incorrect", -101);
        }
        return user;
    }

    /**
     * OBtains user's profile from the database using login id.
     * 
     * @param login THe user login id
     * @return {@link UserLogin}
     * @throws UserAuthenticationException
     */
    private UserLogin getUserLoginRecord(String userName) throws UserAuthenticationException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        UserLogin user = UserFactory.createUserLogin();
        Object results[];
        try {
            user.addCriteria(UserLogin.PROP_USERNAME, userName);
            results = dao.retrieve(user);
        }
        catch (DatabaseException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new UserAuthenticationException(e.getMessage());
        }

        if (results.length > 0) {
            user = (UserLogin) results[0];
            return user;
        }
        else {
            throw new UserAuthenticationException("Profile does not exist for user, " + userName, -100);
        }
    }

    /**
     * Creates an encrypted password using _pw as the source.
     * 
     * @param _pw
     *            The password to encrypt.
     * @return The encrypted password.
     */
    protected String encryptPassword(String _pw) {

        String pwResult;
        pwResult = _pw;
        for (int ndx = 0; ndx < this.encryptCycles; ndx++) {
            pwResult = RMT2Base64Encoder.encode(pwResult);
        }
        return pwResult;
    }

    /**
     * Decodes an encrypted password using _pw as the source.
     * 
     * @param _pw
     *            The password to decrypt.
     * @return The decrypted password.
     */
    protected String decryptPassword(String _pw) {

        String pwResult;
        pwResult = _pw;
        for (int ndx = 0; ndx < this.encryptCycles; ndx++) {
            pwResult = RMT2Base64Decoder.decode(pwResult);
        }
        return pwResult;
    }

    /**
     * Creates or modifies a user group database record.
     * 
     * @param grp The Group that is to be updated.
     * @return The id of the group.
     * @throws UserAuthenticationException If grp is null.
     */
    public int maintainGroup(UserGroup grp) throws UserAuthenticationException {
        if (grp == null) {
            throw new UserAuthenticationException("User Group Maintenance failed.  User Group instance cannot be null");
        }
        if (grp.getGrpId() > 0) {
            this.updateGroup(grp);
        }
        if (grp.getGrpId() == 0) {
            this.createGroup(grp);
        }
        return grp.getGrpId();
    }

    /**
     * Adds an User Group record to the database,.
     * 
     * @param app {@link com.bean.Application Application}.
     * @throws UserAuthenticationException For database and system errors.
     */
    protected void createGroup(UserGroup grp) throws UserAuthenticationException {
        this.validateGroup(grp);
        UserTimestamp ut = null;
        try {
            ut = RMT2Date.getUserTimeStamp(this.request);
            grp.setDateCreated(ut.getDateCreated());
            grp.setDateUpdated(ut.getDateCreated());
            grp.setUserId(ut.getLoginId());
            this.insertRow(grp, true);
        }
        catch (DatabaseException e) {
            this.msg = "DatabaseExeception: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
        catch (SystemException e) {
            this.msg = "SystemException: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
    }

    /**
     * Updates a single user group record to the database.
     * 
     * @param app {@link com.bean.Application Application}.
     * @throws UserAuthenticationException for database and system errors.
     */
    protected void updateGroup(UserGroup grp) throws UserAuthenticationException {
        this.validateGroup(grp);
        UserTimestamp ut = null;
        try {
            ut = RMT2Date.getUserTimeStamp(this.request);
            grp.setDateUpdated(ut.getDateCreated());
            grp.setUserId(ut.getLoginId());
            grp.addCriteria(UserGroup.PROP_GRPID, grp.getGrpId());
            this.updateRow(grp);
        }
        catch (DatabaseException e) {
            this.msg = "DatabaseExeception: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
        catch (SystemException e) {
            this.msg = "SystemException: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
    }

    /**
     * This method is responsble for validating an user group profile.  The  
     * description of the profile is required to have a value. 
     * 
     * @param grp {@link com.bean.UserGroup UserGroup}
     * @throws UserAuthenticationException
     */
    private void validateGroup(UserGroup grp) throws UserAuthenticationException {
        if (grp.getDescription() == null || grp.getDescription().length() <= 0) {
            this.msg = "User Group Maintenance: Description cannot be blank";
            logger.log(Level.ERROR, this.msg);
            throw new UserAuthenticationException(this.msg);
        }
    }

    /**
     * Deletes a user group from the system.
     * 
     * @param grpId The id of the group that is to be delete.
     * @return The total number of targets affected.
     * @throws UserAuthenticationException
     */
    public int deleteGroup(int grpId) throws UserAuthenticationException {
        if (grpId <= 0) {
            this.msg = "User Group object delete failure.  User Group id is invalid: " + grpId;
            logger.log(Level.ERROR, this.msg);
            throw new UserAuthenticationException(this.msg);
        }
        try {
            UserGroup grp = UserFactory.createUserGroup();
            grp.addCriteria(UserGroup.PROP_GRPID, grpId);
            return this.deleteRow(grp);
        }
        catch (DatabaseException e) {
            this.msg = "DatabaseExeception: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
    }

    /**
     * Obtains a complete list of all roles from the database
     * 
     * @return A List of {@link com.bean.Roles Roles} instances
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
     * Retrieves a role based on its unique key value.
     * 
     * @param uid The id of the role to retrieve.
     * @return {@link com.bean.Roles Roles} instance or null if not found
     * @throws UserAuthenticationException
     */
    public Object getRole(int uid) throws UserAuthenticationException {
        Roles roles = UserFactory.createRole();
        roles.addCriteria(Roles.PROP_ROLEID, uid);
        try {
            return this.daoHelper.retrieveObject(roles);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

    /**
     * Creates or modifies a security role.
     * 
     * @param role The {@link com.bean.Roles Role} that is to be updated.
     * @return The id of the role.
     * @throws UserAuthenticationException
     */
    public int maintainRole(Roles role) throws UserAuthenticationException {
        if (role == null) {
            throw new UserAuthenticationException("Role Maintenanced failed.  Role instance cannot be null");
        }
        if (role.getRoleId() > 0) {
            this.updateRole(role);
        }
        if (role.getRoleId() == 0) {
            this.createRole(role);
        }
        return role.getRoleId();
    }

    /**
     * Adds an role record to the database,.
     * 
     * @param role {@link com.bean.Roles Roles}.
     * @throws UserAuthenticationException
     */
    protected void createRole(Roles role) throws UserAuthenticationException {
        this.validateRole(role);
        try {
            this.insertRow(role, true);
        }
        catch (DatabaseException e) {
            this.msg = "DatabaseExeception: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
    }

    /**
     * Updates one an role record in the database.
     * 
     * @param role {@link com.bean.Roles Roles}.
     * @throws UserAuthenticationException for database and system errors.
     */
    protected void updateRole(Roles role) throws UserAuthenticationException {
        this.validateRole(role);
        try {
            role.addCriteria(Roles.PROP_ROLEID, role.getRoleId());
            this.updateRow(role);
        }
        catch (DatabaseException e) {
            this.msg = "DatabaseExeception: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
    }

    /**
     * This method is responsble for validating a role profile.  The name 
     * and description of the application are to have values. 
     * 
     * @param app {@link com.bean.Application Application}
     * @throws UserAuthenticationException
     */
    private void validateRole(Roles role) throws UserAuthenticationException {
        if (role.getName() == null || role.getName().length() <= 0) {
            this.msg = "Role name cannot be blank";
            logger.log(Level.ERROR, this.msg);
            throw new UserAuthenticationException(this.msg);
        }

        if (role.getDescription() == null || role.getDescription().length() <= 0) {
            this.msg = "Role description cannot be blank";
            logger.log(Level.ERROR, this.msg);
            throw new UserAuthenticationException(this.msg);
        }
    }

    /**
     * Deletes a security roles from the system.
     * 
     * @param roleId The id of the role that is to be delete.
     * @return The total number of rows deleted.
     * @throws UserAuthenticationException
     */
    public int deleteRole(int roleId) throws UserAuthenticationException {
        if (roleId <= 0) {
            this.msg = "Role object delete failure.  Role id is invalid: " + roleId;
            logger.log(Level.ERROR, this.msg);
            throw new UserAuthenticationException(this.msg);
        }
        try {
            Roles role = UserFactory.createRole();
            role.addCriteria(Roles.PROP_ROLEID, roleId);
            return this.deleteRow(role);
        }
        catch (DatabaseException e) {
            this.msg = "DatabaseExeception: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            e.printStackTrace();
            throw new UserAuthenticationException(this.msg);
        }
    }

    public List <VwUser> findUserProfile(UserCriteria criteria) throws UserAuthenticationException {
        VwUser user = UserFactory.createUserExt();

        if (criteria != null) {
            if (criteria.getQry_Id() != null) {
                user.addCriteria(VwUser.PROP_LOGINID, Integer.parseInt(criteria.getQry_Id()));
            }
            if (criteria.getQry_Username() != null) {
                user.addCriteria(VwUser.PROP_USERNAME, criteria.getQry_Username());
            }
            if (criteria.getQry_Firstname() != null) {
                user.addCriteria(VwUser.PROP_FIRSTNAME, criteria.getQry_Firstname());
            }
            if (criteria.getQry_Lastname() != null) {
                user.addCriteria(VwUser.PROP_LASTNAME, criteria.getQry_Lastname());
            }
            if (criteria.getQry_Email() != null) {
                user.addCriteria(VwUser.PROP_EMAIL, criteria.getQry_Email());
            }
            if (criteria.getQry_LoggedIn() != null) {
                user.addCriteria(VwUser.PROP_LOGGEDIN, Integer.parseInt(criteria.getQry_LoggedIn()));
            }
            if (criteria.getQry_BirthDate() != null) {
                user.addCriteria(VwUser.PROP_BIRTHDATE, criteria.getQry_BirthDate());
            }
            if (criteria.getQry_Ssn() != null) {
                user.addCriteria(VwUser.PROP_SSN, criteria.getQry_Ssn());
            }
            if (criteria.getQry_StartDate() != null) {
                user.addCriteria(VwUser.PROP_STARTDATE, criteria.getQry_StartDate());
            }
            if (criteria.getQry_TerminationDate() != null) {
                user.addCriteria(VwUser.PROP_TERMINATIONDATE, criteria.getQry_TerminationDate());
            }
            if (criteria.getQry_Active() != null) {
                user.addCriteria(VwUser.PROP_ACTIVE, Integer.parseInt(criteria.getQry_Active()));
            }
        }
        user.addOrderBy(VwUser.PROP_LASTNAME, VwUser.ORDERBY_ASCENDING);
        user.addOrderBy(VwUser.PROP_FIRSTNAME, VwUser.ORDERBY_ASCENDING);
        user.addOrderBy(VwUser.PROP_USERNAME, VwUser.ORDERBY_ASCENDING);
        try {
            return this.daoHelper.retrieveList(user);
        }
        catch (DatabaseException e) {
            throw new UserAuthenticationException(e);
        }
    }

}
package com.api.security.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.security.UserAuthenticationException;

import com.bean.Roles;
import com.bean.UserGroup;
import com.bean.UserLogin;
import com.bean.VwUser;
import com.bean.VwUserAppRoles;
import com.bean.VwUserGroup;
import com.bean.criteria.UserCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

/**
 * Class responsible for managing the user's login credentials and 
 * other user profile items.
 * 
 * @author roy.terrell
 *
 */
public class UserXmlImpl extends RdbmsDaoImpl implements UserApi {
    /** The selection criteria used to fetch UserLogin data. */
    protected String criteria;

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
    public UserXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	logger = Logger.getLogger("UserBeanImpl");
	this.logger.log(Level.INFO, "UserXmlImpl loaded");
    }

    /**
     * Create an UserBeanImpl object using database connection and a http servlet request.
     * 
     * @param dbConn THe database connection
     * @param request The user's request
     * @throws SystemException
     * @throws DatabaseException
     */
    public UserXmlImpl(DatabaseConnectionBean dbConn, Request request) throws SystemException, DatabaseException {
	this(dbConn);
	this.request = request;
    }

    /**
     * Retrieve user data as XML using primary key id.
     * 
     * @param uid A unique id identifying the user.
     * @return User data as a XML document.
     * @throws UserAuthenticationException
     */
    public Object findUser(int uid) throws UserAuthenticationException {
	UserLogin user = UserFactory.createXmlUserLogin();
	user.addCriteria(UserLogin.PROP_LOGINID, uid);
	try {
	    return this.daoHelper.retrieveXml(user);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Retrieve user data as XML using the user's login id.
     * 
     * @param loginId The user's login id.
     * @return User data as a XML document.
     * @throws UserAuthenticationException
     */
    public Object findUserByUserName(String userName) throws UserAuthenticationException {
	UserLogin user = UserFactory.createXmlUserLogin();
	user.addCriteria(UserLogin.PROP_USERNAME, userName);
	try {
	    return this.daoHelper.retrieveXml(user);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Retrieve data pertaining to one or more user in the format of XML 
     * using custom selection criteria.
     * 
     * @param criteria Custom selection criteria.
     * @return XML document representing one or more users that  meet the 
     *         custom selection criteria.
     * @throws UserAuthenticationException
     */
    public Object findUser(String criteria) throws UserAuthenticationException {
	UserLogin user = UserFactory.createXmlUserLogin();
	user.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveXml(user);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }
    
    /**
     * Retrieve data pertaining to one or more user extension profiles in the format of XML 
     * using custom selection criteria.
     * 
     * @param criteria 
     *         Custom selection criteria.
     * @return XML document representing one or more users that  meet the 
     *         custom selection criteria.  The results packaged in ascending order by
     *         last name, first name, and user name.
     * @throws UserAuthenticationException
     */
    public String findUserExt(String criteria) throws UserAuthenticationException {
	VwUser user = UserFactory.createXmlUserExt();
	user.addCustomCriteria(criteria);
	user.addOrderBy(VwUser.PROP_LASTNAME, VwUser.ORDERBY_ASCENDING);
	user.addOrderBy(VwUser.PROP_FIRSTNAME, VwUser.ORDERBY_ASCENDING);
	user.addOrderBy(VwUser.PROP_USERNAME, VwUser.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(user);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains the user's group data.
     * 
     * @param userName the user's login id.
     * @return XML String representing one or more {@link com.bean.VwUserGroup VwUserGroup} objects.
     * @throws UserAuthenticationException
     */
    public Object getUserGroup(String userName) throws UserAuthenticationException {
	VwUserGroup grp = UserFactory.createXmlVwUserGroup();
	grp.addCriteria(VwUserGroup.PROP_USERNAME, userName);
	try {
	    return this.daoHelper.retrieveXml(grp);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains a user group by its unique id from the database.
     * 
     * @param grpId The group id.
     * @return XML String representing an {@link com.bean.UserGroup UserGroup} object. 
     * @throws UserAuthenticationException
     */
    public Object getGroup(int grpId) throws UserAuthenticationException {
	UserGroup grp = UserFactory.createXmlUserGroup();
	grp.addCriteria(UserGroup.PROP_GRPID, grpId);
	try {
	    return this.daoHelper.retrieveXml(grp);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains all user groups.
     * 
     * @return XML String representing one or more {@link com.bean.UserGroup UserGroup} objects.
     * @throws UserAuthenticationException
     */
    public Object getAllGroups() throws UserAuthenticationException {
	UserGroup grp = UserFactory.createXmlUserGroup();
	grp.addOrderBy(UserGroup.PROP_DESCRIPTION, UserGroup.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(grp);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains one or more roles in which a user is assigned.
     * 
     * @param userName The user's login id.
     * @return String of XML representing one or more {@link com.bean.VwUserAppRoles VwUserAppRoles} objects.
     * @throws UserAuthenticationException
     */
    public Object getRoles(String userName) throws UserAuthenticationException {
	VwUserAppRoles roles = UserFactory.createVwUserAppRoles();
	roles.addCriteria(VwUserAppRoles.PROP_USERNAME, userName);
	try {
	    return this.daoHelper.retrieveXml(roles);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains one or more roles assigned to a user for a specific application as XML.
     * 
     * @param userName The user's login id.
     * @param appName The application to target for user roles.
     * @return String of XML representing one or more {@link com.bean.VwUserAppRoles VwUserAppRoles} objects.
     * @throws UserAuthenticationException
     */
    public Object getRoles(String userName, String appName) throws UserAuthenticationException {
	VwUserAppRoles roles = UserFactory.createXmlVwUserAppRoles();
	roles.addCriteria(VwUserAppRoles.PROP_USERNAME, userName);
	roles.addCriteria(VwUserAppRoles.PROP_APPNAME, appName);
	try {
	    return this.daoHelper.retrieveXml(roles);
	}
	catch (DatabaseException e) {
	    throw new UserAuthenticationException(e);
	}
    }

    /**
     * Obtains a complete list of all roles in the system.
     * 
     * @return String as a XML document representing one or more roles.
     * @throws UserAuthenticationException
     */
    public Object getAllRoles() throws UserAuthenticationException {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * Retrieves a role based on its unique key value.
     * 
     * @param uid The id of the role to retrieve.
     * @return String as a XML document representing the role.
     * @throws UserAuthenticationException
     */
    public Object getRole(int uid) throws UserAuthenticationException {
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
     * {@inheritDoc}
     */
    public int maintainUser(UserLogin _user) throws UserAuthenticationException {
	return 1;
    }

    /**
     * {@inheritDoc}
     */
    public void activateUser(UserLogin _user) throws UserAuthenticationException {
    }

    /**
     * {@inheritDoc}
     */
    public void inActivateUser(UserLogin _user) throws UserAuthenticationException {
    }

    public int updateUserLogin(UserLogin _usr) throws UserAuthenticationException {
	return 1;
    }

    /**
     * {@inheritDoc}
     */
    public void updateUserLoginCount(UserLogin _usr) throws UserAuthenticationException {
    }

    public void deleteUser(UserLogin _user) throws UserAuthenticationException {
	return;
    }

    /**
     * {@inheritDoc}
     */
    public Object verifyLogin(String _userId, String _password) throws UserAuthenticationException {
	return null;
    }

    /**
     * No Action
     */
    public int deleteGroup(int grpId) throws UserAuthenticationException {
	return 0;
    }

    /**
     * No Action
     */
    public int maintainGroup(UserGroup grp) throws UserAuthenticationException {
	return 0;
    }

    /**
     * No Action
     */
    public int deleteRole(int roleId) throws UserAuthenticationException {
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * No Action
     */
    public int maintainRole(Roles role) throws UserAuthenticationException {
	// TODO Auto-generated method stub
	return 0;
    }

    public List<VwUser> findUserProfile(UserCriteria criteria) throws UserAuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.api.security.user.UserApi#getActiveSessions(java.lang.String)
     */
    public Object getActiveSessions(String sessionId) throws UserAuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

}
package com.api.security.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.UserLogin;
import com.bean.UserResource;
import com.bean.UserResourceAccess;
import com.bean.UserResourceSubtype;
import com.bean.UserResourceType;
import com.bean.VwResource;
import com.bean.VwResourceType;
import com.bean.VwUserResourceAccess;
import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * An implementation of {@link com.api.security.user.ResourceApi ResourceApi} which functions to 
 * interact with a RDBMS to manage user resources as a single object or as a List of objects.
 * 
 * @author appdev
 * 
 */
public class UserResourceBeanImpl extends RdbmsDaoImpl implements ResourceApi {
    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * Default constructor.
     * 
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    public UserResourceBeanImpl() throws DatabaseException, SystemException {
	return;
    }

    /**
     * Creates an instance of UserResourceBeanImpl class using a 
     * {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * 
     * @param dbConn A database connection bean.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException} 
     * 		if the DatabaseConnectionBean is invalid or if the Dao helper 
     * 		failed to be created.
     */
    public UserResourceBeanImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	if (this.connector == null) {
	    this.msg = "UserResourceBeanImpl class failed instantiation: Database connection is not valid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Creates an instance of UserResourceBeanImpl class using a 
     * {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object 
     * and HttpServletRequest object.
     * 
     * @param dbConn A database connection bean.
     * @param request A user request object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    public UserResourceBeanImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Creates an instance of UserResourceBeanImpl class using a 
     * {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object 
     * and the name of the intended DataSourceView.
     * 
     * @param dbConn A database connection bean.
     * @param dsn The DataSourceView name.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    public UserResourceBeanImpl(DatabaseConnectionBean dbConn, String dsn) throws DatabaseException, SystemException {
	super(dbConn, dsn);
    }

    /**
     * Perform post initialization of a this api by creating the logger.
     * 
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    protected void initApi() throws DatabaseException, SystemException {
	super.initApi();
	this.logger = Logger.getLogger("UserResourceBeanImpl");
    }

    /**
     * Get a user resource object by its unique identifier.
     * 
     * @param uid The unique idenifier of the resource.
     * @return An Object with a runtime type of {@link com.bean.UserResource UserResource}.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object get(int uid) throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	ur.addCriteria(UserResource.PROP_RSRCID, uid);
	return this.daoHelper.retrieveObject(ur);
    }

    /**
     * Get a user resource object by its name.   
     * 
     * @param name The name of the resource
     * @return An Object with a runtime type of {@link com.bean.UserResource UserResource}.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object get(String name) throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	ur.addCriteria(UserResource.PROP_NAME, name);
	return this.daoHelper.retrieveObject(ur);
    }

    /**
     * Get a user resource using a {@link com.bean.UserResource UserResource} object 
     * as the source of selection criteria.
     * 
     * @param criteria 
     *           An instance of UserResource which contains the property value 
     *           assignments neede to build the selection criteria 
     *           
     * @return An Object disguised as List of {@link com.bean.UserResource UserResource} instances.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object get(UserResource criteria) throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	if (criteria != null) {
	    if (criteria.getRsrcId() > 0) {
		ur.addCriteria(UserResource.PROP_RSRCID, criteria.getRsrcId());
	    }
	    if (criteria.getName() != null) {
		ur.addLikeClause(UserResource.PROP_NAME, criteria.getName());
	    }
	    if (criteria.getRsrcTypeId() > 0) {
		ur.addCriteria(UserResource.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 0) {
		ur.addCriteria(UserResource.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	}
	ur.addOrderBy(UserResource.PROP_NAME, UserResource.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveList(ur);
    }

    /**
     * Get extended resource data from the database.
     * 
     * @param criteria {@link com.bean.VwResource VwResource}
     * @return A List of VwResource objects. 
     * @throws DatabaseException
     */
    public Object getExt(VwResource criteria) throws DatabaseException {
	VwResource ur = ResourceFactory.createVwResource();
	if (criteria != null) {
	    if (criteria.getRsrcId() > 0) {
		ur.addCriteria(VwResource.PROP_RSRCID, criteria.getRsrcId());
	    }
	    if (criteria.getName() != null) {
		ur.addLikeClause(VwResource.PROP_NAME, criteria.getName());
	    }
	    if (criteria.getRsrcTypeId() > 0) {
		ur.addCriteria(VwResource.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 0) {
		ur.addCriteria(VwResource.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	    if (criteria.getSecured() > 0) {
		ur.addCriteria(VwResource.PROP_SECURED, criteria.getSecured());
	    }
	}
	ur.addOrderBy(UserResource.PROP_NAME, UserResource.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveList(ur);
    }

    /**
     * Obtains user accessibility data pertaining to one or more user resources using 
     * specific criteria based on VwUserResourceAccess.  The following properties can 
     * be recognized when building the query's selection criteria:  
     * <ol>
     *   <li>resource id</li>
     *   <li>resource name</li>
     *   <li>resource secured flag</li>
     *   <li>resource type id</li>
     *   <li>resource sub type id</li>
     *   <li>user's uid</li>
     *   <li>user's login id</li>
     *   <li>user's first name</li>
     *   <li>user's last name</li>
     *   <li>user's active status</li>
     *   <li>user's email</li>
     *   <li>user's group id</li>
     *   <li>user's group name</li>
     * </ol>
     * 
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess} 
     *          contains the data values needed to build the selection criteria for 
     *          the query. 
     * @return A List of {@link com.bean.VwUserResourceAccess VwUserResourceAccess} instances.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public List <VwUserResourceAccess> getAccessibility(VwUserResourceAccess criteria) throws DatabaseException {
	VwUserResourceAccess ura = ResourceFactory.createUserAccess();
	if (criteria != null) {
	    if (criteria.getRsrcId() > 0) {
		ura.addCriteria(VwUserResourceAccess.PROP_RSRCID, criteria.getRsrcId());
	    }
	    if (criteria.getResrcName() != null) {
		ura.addLikeClause(VwUserResourceAccess.PROP_RESRCNAME, criteria.getResrcName());
	    }
	    if (criteria.getResrcSecured() > 0) {
		ura.addCriteria(VwUserResourceAccess.PROP_RESRCSECURED, criteria.getResrcSecured());
	    }
	    if (criteria.getRsrcTypeId() > 0) {
		ura.addCriteria(VwUserResourceAccess.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 0) {
		ura.addCriteria(VwUserResourceAccess.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	    if (criteria.getUserUid() > 0) {
		ura.addCriteria(VwUserResourceAccess.PROP_USERUID, criteria.getUserUid());
	    }
	    if (criteria.getUsername() != null) {
		ura.addCriteria(VwUserResourceAccess.PROP_USERNAME, criteria.getUsername());
	    }
	    if (criteria.getUserFirstname() != null) {
		ura.addLikeClause(VwUserResourceAccess.PROP_USERFIRSTNAME, criteria.getUserFirstname());
	    }
	    if (criteria.getUserLastname() != null) {
		ura.addLikeClause(VwUserResourceAccess.PROP_USERLASTNAME, criteria.getUserLastname());
	    }
	    if (criteria.getUserActiveStatus() > 0) {
		ura.addCriteria(VwUserResourceAccess.PROP_USERACTIVESTATUS, criteria.getUserActiveStatus());
	    }
	    if (criteria.getUserEmail() != null) {
		ura.addCriteria(VwUserResourceAccess.PROP_USEREMAIL, criteria.getUserEmail());
	    }
	    if (criteria.getUserGroupId() > 0) {
		ura.addCriteria(VwUserResourceAccess.PROP_USERGROUPID, criteria.getUserGroupId());
	    }
	    if (criteria.getUserGroupName() != null) {
		ura.addCriteria(VwUserResourceAccess.PROP_USERGROUPNAME, criteria.getUserGroupName());
	    }
	}
	ura.addOrderBy(VwUserResourceAccess.PROP_RESRCNAME, VwUserResourceAccess.ORDERBY_ASCENDING);

	List <VwUserResourceAccess> results = this.daoHelper.retrieveList(ura);
	if (results == null) {
	    results = new ArrayList <VwUserResourceAccess>();
	}
	return results;
    }

    /**
     * Obtain a list of secured resources that are assigned to the user.
     * 
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess}  
     * @return A List of {@link com.bean.VwUserResourceAccess VwUserResourceAccess} instances.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public List <VwUserResourceAccess> getUserResourceAssigned(VwUserResourceAccess criteria) throws DatabaseException {
	return this.getAccessibility(criteria);
    }

    /**
     * Obtain a list of secured resources that are revoked from the user.
     *  
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess} 
     * @return A List of {@link com.bean.UserResource UserResource} objects
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public List <UserResource> getUserResourceRevoked(VwUserResourceAccess criteria) throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	if (criteria != null) {
	    if (criteria.getRsrcId() > 0) {
		ur.addCriteria(UserResource.PROP_RSRCID, criteria.getRsrcId());
	    }
	    if (criteria.getResrcName() != null) {
		ur.addLikeClause(UserResource.PROP_NAME, criteria.getResrcName());
	    }
	    if (criteria.getResrcSecured() > 0) {
		ur.addCriteria(UserResource.PROP_SECURED, criteria.getResrcSecured());
	    }
	    if (criteria.getRsrcTypeId() > 0) {
		ur.addCriteria(UserResource.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 0) {
		ur.addCriteria(UserResource.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	}

	StringBuffer customSql = new StringBuffer(100);
	customSql.append("rsrc_id not in (select x.rsrc_id from vw_user_resource_access x ");
	customSql.append("where x.user_uid = ");
	customSql.append(criteria.getUserUid());
	customSql.append(" and ");
	customSql.append("x.resrc_secured = 1");
	customSql.append(")");
	ur.addCustomCriteria(customSql.toString());
	ur.addOrderBy(UserResource.PROP_NAME, UserResource.ORDERBY_ASCENDING);
	List <UserResource> results = this.daoHelper.retrieveList(ur);
	if (results == null) {
	    results = new ArrayList<UserResource>();
	}
	return results;
    }

    /**
     * Get all user resource items that belong to a specific resource sub type.
     * 
     * @param subTypeId  The id of the ersource sub type.
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getBySubType(int subTypeId) throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	ur.addCriteria(UserResource.PROP_RSRCSUBTYPEID, subTypeId);
	return this.daoHelper.retrieveList(ur);
    }

    /**
     * Get all user resource items that belong to a specific resource type.
     * 
     * @param subTypeId  The id of the resource type.
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getByType(int typeId) throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	ur.addCriteria(UserResource.PROP_RSRCTYPEID, typeId);
	return this.daoHelper.retrieveList(ur);
    }

    /**
     * Retrieve all secured user resources which requires user authentication/authorization 
     * in order to access.
     * 
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public List <UserResource> getSecured() throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	ur.addCriteria(UserResource.PROP_SECURED, 1);
	return this.daoHelper.retrieveList(ur);
    }

    /**
     * Retrieve all unsecured user resources which do not require user 
     * authentication/authorization in order to access.
     * 
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getUnsecured() throws DatabaseException {
	UserResource ur = ResourceFactory.createUserResource();
	ur.addCriteria(UserResource.PROP_SECURED, 0);
	return this.daoHelper.retrieveList(ur);
    }

    /**
     * Creates or modifies a resource object which data is persisted to the database.
     * 
     * @param res {@link com.bean.UserResource UserResource} containing the 
     * 		   data to be updated.
     * @return The unique identifier of the resource.
     * @throws ResourceException
     */
    public int maintainResource(UserResource res) throws ResourceException {
	if (res == null) {
	    throw new ResourceException("Resource Maintenanced failed.  Resource instance cannot be null");
	}
	if (res.getRsrcId() > 0) {
	    this.updateResource(res);
	}
	if (res.getRsrcId() == 0) {
	    this.createResource(res);
	}
	return res.getRsrcId();
    }

    /**
     * Adds an resource record to the database,.
     * 
     * @param type {@link com.bean.UserResource UserResource}.
     * @throws SystemException
     */
    protected void createResource(UserResource obj) throws ResourceException {
	this.validateResource(obj);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	    int rc = this.insertRow(obj, true);
	    obj.setRsrcId(rc);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Updates one an resource record in the database.
     * 
     * @param type {@link com.bean.UserResource UserResource}.
     * @throws ResourceException for database and system errors.
     */
    protected void updateResource(UserResource obj) throws ResourceException {
	this.validateResource(obj);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	    obj.addCriteria(UserResource.PROP_RSRCID, obj.getRsrcId());
	    this.updateRow(obj);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * This method is responsble for validating a resource profile.  The name, 
     * description, and url of the profile is required to have a value.  The 
     * properties, resource type and resource sub-type, must have a value 
     * greater than zero. 
     * 
     * @param grp {@link com.bean.UserResource UserResource}
     * @throws ResourceException
     */
    private void validateResource(UserResource obj) throws ResourceException {
	if (obj.getName() == null || obj.getName().length() <= 0) {
	    this.msg = "Resource Maintenance: Name cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	if (obj.getDescription() == null || obj.getDescription().length() <= 0) {
	    this.msg = "Resource Maintenance: Description cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	if (obj.getUrl() == null || obj.getUrl().length() <= 0) {
	    this.msg = "Resource Maintenance: URL cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	if (obj.getRsrcTypeId() <= 0) {
	    this.msg = "Resource Maintenance: Resource type contains an invalid value";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	if (obj.getRsrcSubtypeId() <= 0) {
	    this.msg = "Resource Maintenance: Resource sub-type contains an invalid value";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Deletes a resource from the database.
     * 
     * @param uid The unique id of the resource sub-type.
     * @return Total number of database rows affected.
     * @throws DatabaseException
     */
    public int deleteResource(int uid) throws ResourceException {
	if (uid <= 0) {
	    this.msg = "Resource object delete failure.  Resource id is invalid: " + uid;
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	try {
	    UserResource obj = ResourceFactory.createUserResource();
	    obj.addCriteria(UserResource.PROP_RSRCID, uid);
	    return this.deleteRow(obj);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Get a user resource type using UserResourceType and UserResourceSubtype objects as the source 
     * of selection criteria.
     * 
     * @param criteria 
     *           {@link com.bean.UserResourceSubtype UserResourceSubtype} containing the data values to 
     *           use as selection criteria pertaining the the user resource sub type.
     * @return An arbitrary object contining the reslts
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getSubType(UserResourceSubtype criteria) throws DatabaseException {
	UserResourceSubtype urst = ResourceFactory.createUserResourceSubtype();
	if (criteria != null) {
	    if (criteria.getRsrcTypeId() > 0) {
		urst.addCriteria(UserResourceSubtype.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 0) {
		urst.addCriteria(UserResourceSubtype.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	    if (criteria.getName() != null) {
		urst.addLikeClause(UserResourceSubtype.PROP_NAME, criteria.getName());
	    }
	}
	urst.addOrderBy(UserResourceSubtype.PROP_NAME, UserResourceSubtype.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveList(urst);
    }

    /**
     * Get a user resource type using UserResourceType object as the source of selection 
     * criteria.
     * 
     * @param criteria 
     *           {@link com.bean.UserResourceType UserResourceType} containing the data values to 
     *           use as selection criteria.
     * @return An arbitrary object contining the results
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getType(UserResourceType criteria) throws DatabaseException {
	UserResourceType urt = ResourceFactory.createUserResourceType();
	if (criteria != null) {
	    if (criteria.getRsrcTypeId() > 0) {
		urt.addCriteria(UserResourceType.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getDescription() != null) {
		urt.addLikeClause(UserResourceType.PROP_DESCRIPTION, criteria.getDescription());
	    }
	}
	urt.addOrderBy(UserResourceType.PROP_DESCRIPTION, UserResourceType.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveList(urt);
    }

    /**
     * Get type and sub-type data for one or more resources.
     * 
     * @param criteria {@link com.bean.VwResourceType VwResourceType}
     * @return An list arbitrary objects representing resource type data. 
     * @throws DatabaseException
     */
    public Object getTypeSubtype(VwResourceType criteria) throws DatabaseException {
	VwResourceType urt = ResourceFactory.createVwResourceType();
	if (criteria != null) {
	    if (criteria.getResrcTypeId() > 0) {
		urt.addCriteria(VwResourceType.PROP_RESRCTYPEID, criteria.getResrcTypeId());
	    }
	    if (criteria.getResrcSubtypeId() > 0) {
		urt.addCriteria(VwResourceType.PROP_RESRCSUBTYPEID, criteria.getResrcSubtypeId());
	    }
	}
	urt.addOrderBy(VwResourceType.PROP_RESRCTYPENAME, VwResourceType.ORDERBY_ASCENDING);
	urt.addOrderBy(VwResourceType.PROP_RESRCSUBTYPENAME, VwResourceType.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveList(urt);
    }

    /**
     * Deletes a resource type.
     * 
     * @param typeId The unique id of the resource type.
     * @return Total number of resource type targets affected.
     * @throws DatabaseException
     */
    public int deleteType(int uid) throws ResourceException {
	if (uid <= 0) {
	    this.msg = "Resource Type object delete failure.  Resource Type id is invalid: " + uid;
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	try {
	    UserResourceType obj = ResourceFactory.createUserResourceType();
	    obj.addCriteria(UserResourceType.PROP_RSRCTYPEID, uid);
	    return this.deleteRow(obj);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Creates or modifies a resource type object.
     * 
     * @param type {@link com.bean.UserResourceType UserResourceType} containing the 
     * 		   data to be updated.
     * @return The unique identifier of the resource type.
     * @throws ResourceException
     */
    public int maintainType(UserResourceType type) throws ResourceException {
	if (type == null) {
	    throw new ResourceException("Resource Type Maintenanced failed.  Resource Type instance cannot be null");
	}
	if (type.getRsrcTypeId() > 0) {
	    this.updateType(type);
	}
	if (type.getRsrcTypeId() == 0) {
	    this.createType(type);
	}
	return type.getRsrcTypeId();
    }

    /**
     * Adds an resource type record to the database,.
     * 
     * @param type {@link com.bean.UserResourceType UserResourceType}.
     * @throws SystemException
     */
    protected void createType(UserResourceType type) throws ResourceException {
	this.validateType(type);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    type.setDateCreated(ut.getDateCreated());
	    type.setDateUpdated(ut.getDateCreated());
	    type.setUserId(ut.getLoginId());
	    int rc = this.insertRow(type, true);
	    type.setRsrcTypeId(rc);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Updates one an resource type record in the database.
     * 
     * @param type {@link com.bean.UserResourceType UserResourceType}.
     * @throws ResourceException for database and system errors.
     */
    protected void updateType(UserResourceType type) throws ResourceException {
	this.validateType(type);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    type.setDateUpdated(ut.getDateCreated());
	    type.setUserId(ut.getLoginId());
	    type.addCriteria(UserResourceType.PROP_RSRCTYPEID, type.getRsrcTypeId());
	    this.updateRow(type);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * This method is responsble for validating a resource type profile.  The  
     * description of the profile is required to have a value. 
     * 
     * @param grp {@link com.bean.UserResourceType UserResourceType}
     * @throws ResourceException
     */
    private void validateType(UserResourceType obj) throws ResourceException {
	if (obj.getDescription() == null || obj.getDescription().length() <= 0) {
	    this.msg = "Resource Type Maintenance: Description cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Creates or modifies a resource sub-type object.
     * 
     * @param subType {@link com.bean.UserResourceSubtype UserResourceSubtype} containing the 
     * 		   data to be updated.
     * @return The unique identifier of the resource sub-type.
     * @throws ResourceException
     */
    public int maintainSubtype(UserResourceSubtype subType) throws ResourceException {
	if (subType == null) {
	    throw new ResourceException("Resource Sub Type Maintenanced failed.  Resource Sub Type instance cannot be null");
	}
	if (subType.getRsrcSubtypeId() > 0) {
	    this.updateSubtype(subType);
	}
	if (subType.getRsrcSubtypeId() == 0) {
	    this.createSubtype(subType);
	}
	return subType.getRsrcSubtypeId();
    }

    /**
     * Adds an resource sub-type record to the database,.
     * 
     * @param subType {@link com.bean.UserResourceSubtype UserResourceSubtype}.
     * @throws SystemException
     */
    protected void createSubtype(UserResourceSubtype subType) throws ResourceException {
	this.validateType(subType);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    subType.setDateCreated(ut.getDateCreated());
	    subType.setDateUpdated(ut.getDateCreated());
	    subType.setUserId(ut.getLoginId());
	    int rc = this.insertRow(subType, true);
	    subType.setRsrcSubtypeId(rc);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Updates one an resource sub-type record in the database.
     * 
     * @param subType {@link com.bean.UserResourceSubtype UserResourceSubtype}.
     * @throws ResourceException for database and system errors.
     */
    protected void updateSubtype(UserResourceSubtype subType) throws ResourceException {
	this.validateType(subType);
	UserTimestamp ut = null;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    subType.setDateUpdated(ut.getDateCreated());
	    subType.setUserId(ut.getLoginId());
	    subType.addCriteria(UserResourceSubtype.PROP_RSRCSUBTYPEID, subType.getRsrcSubtypeId());
	    this.updateRow(subType);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
	catch (SystemException e) {
	    this.msg = "SystemException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * This method is responsble for validating a resource sub-type profile.  The  
     * name and description of the profile are required to have a value. 
     * 
     * @param grp {@link com.bean.UserResourceSubtype UserResourceSubtype}
     * @throws ResourceException
     */
    private void validateType(UserResourceSubtype obj) throws ResourceException {
	if (obj.getName() == null || obj.getName().length() <= 0) {
	    this.msg = "Resource Sub-Type Maintenance: Name cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	if (obj.getDescription() == null || obj.getDescription().length() <= 0) {
	    this.msg = "Resource Sub-Type Maintenance: Description cannot be blank";
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Deletes a resource sub-type.
     * 
     * @param uid The unique id of the resource sub-type.
     * @return Total number of resource type targets affected.
     * @throws DatabaseException
     */
    public int deleteSubtype(int uid) throws ResourceException {
	if (uid <= 0) {
	    this.msg = "Resource Sub-Type object delete failure.  Resource Sub-Type id is invalid: " + uid;
	    logger.log(Level.ERROR, this.msg);
	    throw new ResourceException(this.msg);
	}
	try {
	    UserResourceSubtype obj = ResourceFactory.createUserResourceSubtype();
	    obj.addCriteria(UserResourceSubtype.PROP_RSRCSUBTYPEID, uid);
	    return this.deleteRow(obj);
	}
	catch (DatabaseException e) {
	    this.msg = "DatabaseExeception: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ResourceException(this.msg);
	}
    }

    /**
     * Updates the user profile with the assigned and revoked resources.
     * 
     * @param userName The login id of the user.
     * @param assignedRsrc A String array of resource id's to assign
     * @param revokedRsrc A String array of resource id's to revoke.
     * @return The total number of resources assigned to the user.
     * @throws ResourceException
     */
    public int maintainUserResourceAccess(String userName, String[] assignedRsrc, String[] revokedRsrc) throws ResourceException {
	int rows;
	int rc;
	UserLogin user = UserFactory.createUserLogin();

	// Verify login id.
	user.addCriteria(UserLogin.PROP_USERNAME, userName);
	try {
	    user = (UserLogin) this.daoHelper.retrieveObject(user);
	    if (user == null) {
		throw new ResourceException("User Resource Access update failed due to user login is invalid");
	    }
	}
	catch (DatabaseException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ResourceException(e);
	}

	// Get complete list of resources that are configured to be secured.
	String resources[] = null;
	try {
	    List <UserResource> list = (List <UserResource>) this.getSecured();
	    if (list.size() > 0) {
		resources = new String[list.size()];
		int ndx = 0;
		for (Object rsrc : list) {
		    resources[ndx++] = String.valueOf(((UserResource) rsrc).getRsrcId());
		}
	    }
	}
	catch (DatabaseException e) {
	    this.logger.log(Level.ERROR, e.getMessage());
	    throw new ResourceException(e);
	}

	//  Delete all secured resources assoicated with a particular user
	try {
	    UserResourceAccess ura = ResourceFactory.createUserResourceAccess();

	    ura.addCriteria(UserResourceAccess.PROP_LOGINID, user.getLoginId());
	    // Consider condition only if one or more resources were found above. 
	    if (resources != null && resources.length > 0) {
		ura.addInClause(UserResourceAccess.PROP_RSRCID, resources);
	    }
	    rows = this.deleteRow(ura);
	    logger.log(Level.DEBUG, "Total number of secured user resource access data items deleted for user, " + user.getLoginId() + ": " + rows);
	}
	catch (DatabaseException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ResourceException(e);
	}

	// Verify that we have a valid list of assigned resources.  An invalid list means 
	// that all user resources were revoked and there are no new resources to assign.
	if (assignedRsrc == null) {
	    return 0;
	}
	// Add each user resource based on the new list of assigned resources.
	rows = 0;
	for (String rsrc : assignedRsrc) {
	    try {
		int rsrcId = Integer.parseInt(rsrc);
		UserResourceAccess ura = ResourceFactory.createUserResourceAccess();
		ura.setLoginId(user.getLoginId());
		ura.setGrpId(user.getGrpId());
		ura.setRsrcId(rsrcId);
		UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
		ura.setDateCreated(ut.getDateCreated());
		ura.setDateUpdated(ut.getDateCreated());
		ura.setUserId(ut.getLoginId());
		rc = this.insertRow(ura, true);
		ura.setRsrcAccessId(rc);
		rows++;
	    }
	    catch (DatabaseException e) {
		this.msg = "DatabaseException: " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ResourceException(this.msg);
	    }
	    catch (SystemException e) {
		this.msg = "SystemException: " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ResourceException(this.msg);
	    }
	}
	return rows;
    }
}

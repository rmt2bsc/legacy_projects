package com.api.security.user;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.UserResource;
import com.bean.UserResourceSubtype;
import com.bean.UserResourceType;
import com.bean.VwResource;
import com.bean.VwResourceType;
import com.bean.VwUserResourceAccess;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

/**
 * An implementation of {@link com.api.security.user.ResourceApi ResourceApi} which functions to 
 * interact with a RDBMS to manage user resources as a XML Document.
 * 
 * @author appdev
 * 
 */
public class UserResourceXmlImpl extends RdbmsDaoImpl implements ResourceApi {
    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * Default constructor.
     * 
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    public UserResourceXmlImpl() throws DatabaseException, SystemException {
	return;
    }

    /**
     * Creates an instance of UserResourceXmlImpl class using a 
     * {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * 
     * @param dbConn A database connection bean.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException} 
     * 		if the DatabaseConnectionBean is invalid or if the Dao helper 
     * 		failed to be created.
     */
    public UserResourceXmlImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	if (this.connector == null) {
	    this.msg = "UserResourceXmlImpl class failed instantiation: Database connection is not valid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Creates an instance of UserResourceXmlImpl class using a 
     * {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object 
     * and HttpServletRequest object.
     * 
     * @param dbConn A database connection bean.
     * @param request A user request object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    public UserResourceXmlImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
    }

    /**
     * Creates an instance of UserResourceXmlImpl class using a 
     * {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object 
     * and the name of the intended DataSourceView.
     * 
     * @param dbConn A database connection bean.
     * @param dsn The DataSourceView name.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    public UserResourceXmlImpl(DatabaseConnectionBean dbConn, String dsn) throws DatabaseException, SystemException {
	super(dbConn, dsn);
    }

    /**
     * Perform post initialization of a this api by creating the logger 
     * and DAO query helper object.
     * 
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     * @throws {@link com.util.SystemException SystemException}
     */
    protected void initApi() throws DatabaseException, SystemException {
	super.initApi();
	this.logger = Logger.getLogger("UserResourceXmlImpl");
    }

    /**
     * Get a user resource object by its unique identifier.
     * 
     * @param uid The unique idenifier of the resource.
     * @return An Object with a runtime type of {@link com.bean.UserResource UserResource}.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object get(int uid) throws DatabaseException {
	UserResource ur = ResourceFactory.createXmlUserResource();
	ur.addCriteria(UserResource.PROP_RSRCID, uid);
	return this.daoHelper.retrieveXml(ur);
    }

    /**
     * Get a user resource object by its name.   
     * 
     * @param name The name of the resource
     * @return An Object with a runtime type of {@link com.bean.UserResource UserResource}.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object get(String name) throws DatabaseException {
	UserResource ur = ResourceFactory.createXmlUserResource();
	ur.addCriteria(UserResource.PROP_NAME, name);
	return this.daoHelper.retrieveXml(ur);
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
	UserResource ur = ResourceFactory.createXmlUserResource();
	if (criteria != null) {
	    if (criteria.getRsrcId() > 1) {
		ur.addCriteria(UserResource.PROP_RSRCID, criteria.getRsrcId());
	    }
	    if (criteria.getName() != null) {
		ur.addLikeClause(UserResource.PROP_NAME, criteria.getName());
	    }
	    if (criteria.getRsrcTypeId() > 1) {
		ur.addCriteria(UserResource.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 1) {
		ur.addCriteria(UserResource.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	}
	return this.daoHelper.retrieveXml(ur);
    }

    /**
     * Get extended resource data.
     * 
     * @param criteria {@link com.bean.VwResource VwResource}
     * @return An Object disguised as List of {@link com.bean.VwResource VwResource} instances. 
     * @throws DatabaseException
     */
    public Object getExt(VwResource criteria) throws DatabaseException {
	VwResource ur = ResourceFactory.createVwResource();
	if (criteria != null) {
	    if (criteria.getRsrcId() > 1) {
		ur.addCriteria(VwResource.PROP_RSRCID, criteria.getRsrcId());
	    }
	    if (criteria.getName() != null) {
		ur.addLikeClause(VwResource.PROP_NAME, criteria.getName());
	    }
	    if (criteria.getRsrcTypeId() > 1) {
		ur.addCriteria(VwResource.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 1) {
		ur.addCriteria(VwResource.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	    if (criteria.getSecured() > 0) {
		ur.addCriteria(VwResource.PROP_SECURED, criteria.getSecured());
	    }
	}
	ur.addOrderBy(UserResource.PROP_NAME, UserResource.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveXml(ur);
    }

    /**
     * Get extended resource data from the database.
     * 
     * @param criteria {@link com.bean.VwResource VwResource}
     * @return String as a XML document representing extended resource 
     *         data. 
     * @throws DatabaseException
     */
    public Object get(VwResource criteria) throws DatabaseException {
	VwResource ur = ResourceFactory.createXmlVwResource();
	if (criteria != null) {
	    if (criteria.getRsrcId() > 1) {
		ur.addCriteria(VwResource.PROP_RSRCID, criteria.getRsrcId());
	    }
	    if (criteria.getName() != null) {
		ur.addLikeClause(VwResource.PROP_NAME, criteria.getName());
	    }
	    if (criteria.getRsrcTypeId() > 1) {
		ur.addCriteria(VwResource.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getRsrcSubtypeId() > 1) {
		ur.addCriteria(VwResource.PROP_RSRCSUBTYPEID, criteria.getRsrcSubtypeId());
	    }
	    if (criteria.getSecured() > 0) {
		ur.addCriteria(VwResource.PROP_SECURED, criteria.getSecured());
	    }
	}
	ur.addOrderBy(UserResource.PROP_NAME, UserResource.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveXml(ur);
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
     * @return XML document String representing one or more {@link com.bean.VwUserResourceAccess VwUserResourceAccess} objects.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getAccessibility(VwUserResourceAccess criteria) throws DatabaseException {
	VwUserResourceAccess ura = ResourceFactory.createXmlUserAccess();
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
	return this.daoHelper.retrieveXml(ura);
    }

    /**
     * Obtain a list of secured resources that are assigned to the user.
     * 
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess}  
     * @return XML document String representing one or more {@link com.bean.VwUserResourceAccess VwUserResourceAccess} 
     *         objects.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getUserResourceAssigned(VwUserResourceAccess criteria) throws DatabaseException {
	return this.getAccessibility(criteria);
    }

    /**
     * Obtain a list of secured resources that are revoked from the user.
     *  
     * @param criteria {@link com.bean.VwUserResourceAccess VwUserResourceAccess}  
     * @return XML document String representing one or more {@link com.bean.UserResource UserResource} objects.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getUserResourceRevoked(VwUserResourceAccess criteria) throws DatabaseException {
	UserResource ur = ResourceFactory.createXmlUserResource();
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
	customSql.append("x.resrc_secured = 1)");
	ur.addCustomCriteria(customSql.toString());
	ur.addOrderBy(UserResource.PROP_NAME, UserResource.ORDERBY_ASCENDING);
	return this.daoHelper.retrieveXml(ur);
    }

    /**
     * Get all user resource items that belong to a specific resource sub type.
     * 
     * @param subTypeId  The id of the ersource sub type.
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getBySubType(int subTypeId) throws DatabaseException {
	UserResource ur = ResourceFactory.createXmlUserResource();
	ur.addCriteria(UserResource.PROP_RSRCSUBTYPEID, subTypeId);
	return this.daoHelper.retrieveXml(ur);
    }

    /**
     * Get all user resource items that belong to a specific resource type.
     * 
     * @param subTypeId  The id of the resource type.
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getByType(int typeId) throws DatabaseException {
	UserResource ur = ResourceFactory.createXmlUserResource();
	ur.addCriteria(UserResource.PROP_RSRCTYPEID, typeId);
	return this.daoHelper.retrieveXml(ur);
    }

    /**
     * Retrieve all secured user resources which requires user authentication/authorization 
     * in order to access.
     * 
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getSecured() throws DatabaseException {
	UserResource ur = ResourceFactory.createXmlUserResource();
	ur.addCriteria(UserResource.PROP_SECURED, 1);
	return this.daoHelper.retrieveXml(ur);
    }

    /**
     * Retrieve all unsecured user resources which do not require user 
     * authentication/authorization in order to access.
     * 
     * @return A List of {@link com.bean.UserResource UserResource} instances disguised as an Object.
     * @throws {@link com.api.db.DatabaseException DatabaseException}
     */
    public Object getUnsecured() throws DatabaseException {
	UserResource ur = ResourceFactory.createXmlUserResource();
	ur.addCriteria(UserResource.PROP_SECURED, 0);
	return this.daoHelper.retrieveXml(ur);
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
	UserResourceSubtype urst = ResourceFactory.createXmlUserResourceSubtype();
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
	return this.daoHelper.retrieveXml(urst);
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
	UserResourceType urt = ResourceFactory.createXmlUserResourceType();
	if (criteria != null) {
	    if (criteria.getRsrcTypeId() > 0) {
		urt.addCriteria(UserResourceType.PROP_RSRCTYPEID, criteria.getRsrcTypeId());
	    }
	    if (criteria.getDescription() != null) {
		urt.addLikeClause(UserResourceType.PROP_DESCRIPTION, criteria.getDescription());
	    }
	}
	return this.daoHelper.retrieveXml(urt);
    }

    /**
     * Get type and sub-type data for one or more resources.
     * 
     * @param criteria {@link com.bean.VwResourceType VwResourceType}
     * @return String as a XML document representing resource type and 
     *         sub-type data. 
     * @throws DatabaseException
     */
    public Object getTypeSubtype(VwResourceType criteria) throws DatabaseException {
	VwResourceType urt = ResourceFactory.createXmlVwResourceType();
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
	return this.daoHelper.retrieveXml(urt);
    }

    /**
     * No Action
     */
    public int deleteType(int typeId) throws ResourceException {
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * No Action
     */
    public int maintainType(UserResourceType type) throws ResourceException {
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * No Action
     */
    public int maintainSubtype(UserResourceSubtype subType) throws ResourceException {
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * No Action
     */
    public int deleteSubtype(int subTypeId) throws ResourceException {
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * No Action
     */
    public int deleteResource(int uid) throws ResourceException {
	return 0;
    }

    /**
     * No Action
     */
    public int maintainResource(UserResource res) throws ResourceException {
	return 0;
    }

    /**
     * No Action
     */
    public int maintainUserResourceAccess(String loginId, String[] assignedRsrc, String[] revokedRsrc) throws ResourceException {
	return 0;
    }

}

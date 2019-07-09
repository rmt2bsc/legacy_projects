package com.api.business;

import com.controller.Request;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.Business;
import com.bean.VwBusinessAddress;
import com.util.RMT2Date;
import com.util.RMT2String;
import com.util.SystemException;

import com.api.ContactException;

import com.api.address.AddressApi;
import com.api.address.AddressFactory;
import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

/**
 * Class that implements the BusinessApi interface which provides functionality 
 * to query the database for Business contacts and to persist business contact 
 * changes to the database.
 * 
 * @author RTerrell
 *
 */
class BusinessBeanImpl extends RdbmsDaoImpl implements BusinessApi {
    private Logger logger;
    protected RdbmsDaoQueryHelper daoHelper;
    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean 
     * at the acestor level.
     * 
     * @param dbConn Database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
    public BusinessBeanImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.className = "BusinessBeanImpl";
	this.baseView = "PersonView";
	this.baseBeanClass = "com.bean.Person";
	this.logger = Logger.getLogger("BusinessBeanImpl");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and 
     * the HttpServletRequest object at the acestor level.
     * 
     * @param dbConn Database connection bean.
     * @param req The user's request object.
     * @throws SystemException
     * @throws DatabaseException
     */
    public BusinessBeanImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Releases allocated resources.
     */
    public void close() {
	this.logger = null;
	this.daoHelper.close();
	this.daoHelper = null;
        super.close();
    }
    
    /**
     * Locates a Business object by primary key or it Person Id and 
     * returns Person object to the caller.
     */
    public Object findContact(int uid) throws ContactException {
	Business bus = BusinessFactory.createBusiness();
	bus.addCriteria("Id", uid);
    try {
        return this.daoHelper.retrieveObject(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates a {@link com.bean.VwBusinessAddress VwBusinessAddress} Contact object using custom 
     * selection criteria.  Be sure that the intended base view and base class is set for this 
     * api before invoking this method.
     */
    public Object findContact(String criteria) throws ContactException {
	VwBusinessAddress bus = BusinessFactory.createBusinessAddress();
	bus.addCustomCriteria(criteria);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }
    
    /**
     * Fetches a business entiry along with its address information.
     * 
     * @param busId THe internal id of the business.
     * @return {@link com.bean.VwBusinessAddress VwBusinessAddress}
     * @throws ContactException
     */
    public Object findBusAddress(int busId) throws BusinessException {
        VwBusinessAddress bus = BusinessFactory.createBusinessAddress();
        bus.addCriteria(VwBusinessAddress.PROP_BUSINESSID, busId);
        try {
            return this.daoHelper.retrieveObject(bus);
        }
        catch (DatabaseException e) {
            throw new BusinessException(e);
        }
        }

    /**
     * Locates one or more Business contacts by the contact person's first name.
     */
    public Object findBusByContactFirstName(String contactFirstName) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addCriteria(Business.PROP_CONTACTFIRSTNAME, contactFirstName);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by the contact person's last name.
     */
    public Object findBusByContactLastName(String contactLastName) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addCriteria(Business.PROP_CONTACTLASTNAME, contactLastName);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by business type.
     * 
     * @param busTypeId The business type id
     * @return List of Business objects.
     * @throws BusinessException
     */
    public Object findBusByBusType(int busTypeId) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addCriteria(Business.PROP_ENTITYTYPEID, busTypeId);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by business service type.
     * 
     * @param servTypeId
     * @return List of Business objects.
     * @throws BusinessException
     */
    public Object findBusByServType(int servTypeId) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addCriteria(Business.PROP_SERVTYPEID, servTypeId);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by long name
     * 
     * @param longName The long name of the business.
     * @return List of Business objects.
     * @throws BusinessException
     * 
     */
    public Object findBusByLongName(String longName) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addLikeClause(Business.PROP_LONGNAME, longName);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by short name
     * 
     * @param shortName
     * @return List of Business objects.
     * @throws BusinessException
     */
    public Object findBusByShortName(String shortName) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addLikeClause(Business.PROP_SHORTNAME, shortName);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts based tax id.
     * 
     * @parm taxId The business' tax id
     * @return List of Business objects.
     * @throws BusinessException
     */
    public Object findBusByTaxId(String taxId) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addLikeClause(Business.PROP_SHORTNAME, taxId);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts based on the URL of the web site.
     * 
     * @param website The URL of the website.
     * @return List of Business objects
     * @throws BusinessException
     */
    public Object findBusByWebsite(String website) throws BusinessException {
	Business bus = BusinessFactory.createBusiness();
	bus.addLikeClause(Business.PROP_SHORTNAME, website);
    try {
        return this.daoHelper.retrieveList(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    
    /**
     * Retrieves one or more business contacts using a comma-separated list 
     * of business id's.  The result set is order by business id.
     * 
     * @param idList 
     *          A list of business id's that are separated by commas.
     * @return A List of {@link com.bean.VwBusinessAddress VwBusinessAddress} objects.
     * @throws BusinessException
     */
    public Object findByBusinessId(String idList) throws BusinessException {
        if (idList == null || idList.length() <= 0) {
            throw new BusinessException("Parameter, id, must contain one or more business id\'s separated by commas");
        }
        String ids[] = idList.split(","); 
        VwBusinessAddress bus = BusinessFactory.createBusinessAddress();
        bus.addInClause(VwBusinessAddress.PROP_BUSINESSID, ids);
        bus.addOrderBy(VwBusinessAddress.PROP_BUSINESSID, VwBusinessAddress.ORDERBY_ASCENDING);
        try {
            return this.daoHelper.retrieveList(bus);
        }
        catch (DatabaseException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * This method is responsible for creating and updating a Business object 
     * and persisting the changes to the database.   If Business id is less than 
     * or equal to zero then Business profile must be created.  Otherwise, updates 
     * to existing Business contcts are applied.
     * 
     * @param business the business object to apply to the database.
     * @return int.  The id of the business object for insert operations or the 
     *         total number of rows effected by an update operation. 
     * @throws ContactException
     */
    public int maintainContact(Object business) throws ContactException {
	int rc;
	Business bus;
	if (business instanceof Business) {
	    bus = (Business) business;
	}
	else {
	    this.msg = "maintainContact method failed.  Input object failed to resolve to Business type.";
	    throw new ContactException(this.msg);
	}
	if (bus.getBusinessId() == 0) {
	    rc = this.insertBusiness(bus);
	}
	else {
	    rc = this.updateBusiness(bus);
	}
	return rc;
    }

    /**
     * This method is responsible for adding a Business profile to the database.
     * 
     * @param obj The business contact to add to the database.
     * @return int - The id of the business object.
     * @throws BusinessException General database errors.
     */
    protected int insertBusiness(Business obj) throws BusinessException {
	try {
	    // Validate person objec
	    this.validateContact(obj);
	    String temp = RMT2String.replace(obj.getLongname(), "''", "'");
	    obj.setLongname(temp);
	    
            if (obj.getEntityTypeId() == 0) {
                obj.setEntityTypeId(BusinessApi.UNKNOWN_BUSTYPE);
            }
            if (obj.getServTypeId() == 0) {
        	obj.setServTypeId(BusinessApi.UNKNOWN_SERVTYPE);
            }
            // Set category to null if value is equal zero
            if (obj.getCategoryId() == 0) {
        	obj.setNull(Business.PROP_CATEGORYID);
            }
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    if (obj.getUserId() == null) {
		obj.setUserId(ut.getLoginId());
	    }
	    int key = this.insertRow(obj, true);
	    obj.setBusinessId(key);
	    return key;
	}
	catch (Exception e) {
	    throw new BusinessException(e);
	}
    }

    /**
     * This method is responsible for updating an existing Business contact.
     * 
     * @param obj The business contact to that is to be updated to the database.
     * @return The total number of rows effected by the transaction.
     * @throws BusinessException General database errors.
     */
    protected int updateBusiness(Business obj) throws BusinessException {
	try {
	    // Validate person objec
	    this.validateContact(obj);
	    String temp = RMT2String.replace(obj.getLongname(), "''", "'");
	    obj.setLongname(temp);
	    if (obj.getEntityTypeId() == 0) {
                obj.setEntityTypeId(BusinessApi.UNKNOWN_BUSTYPE);
            }
            if (obj.getServTypeId() == 0) {
        	obj.setServTypeId(BusinessApi.UNKNOWN_SERVTYPE);
            }
            // Set category to null if value is equal zero
            if (obj.getCategoryId() == 0) {
        	obj.setNull(Business.PROP_CATEGORYID);
            }
            
	    // Update business
	    obj.addCriteria(Business.PROP_BUSINESSID, obj.getBusinessId());
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	    int rows = this.updateRow(obj);
	    return rows;
	}
	catch (Exception e) {
	    throw new BusinessException(e);
	}
    }

    /**
     * Not implemented.
     * 
     * @param source The business contact to copy.
     * @return null.
     * @throws BusinessException
     */
    public Object copyBusiness(Object source) throws BusinessException {
	Business newBus = null;
	return newBus;
    }

    /**
     * Deletes a business contact from the database.
     * 
     * @param business The business contact to delete.
     * @return The number of rows effected.
     * @throws ContactException
     */
    public int deleteContact(Object business) throws ContactException {
	Business bus;
	AddressApi addrApi = AddressFactory.createAddressApi(this.connector);
	if (business instanceof Business) {
	    bus = (Business) business;
	}
	else {
	    this.msg = "Delete Contact method failed.  Input object failed to resolve to Business type.";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new BusinessException(this.msg);
	}
	bus.addCriteria(Business.PROP_BUSINESSID, bus.getBusinessId());
	try {
	    int rows = 0;
	    // First, delete address component
	    rows = addrApi.deleteContact(bus);
	    rows += this.deleteRow(bus);
	    return rows;
	}
	catch (DatabaseException e) {
	    throw new BusinessException(e);
	}

    }

    /**
     * Validates a business contact.
     * 
     * @param business The business contact to validate.
     * @throws ContactException 
     *           If the business contact object is of the wrong type or the long 
     *           name is null or invalid.
     */
    public void validateContact(Object _base) throws ContactException {
	Business bus;
	if (_base instanceof Business) {
	    bus = (Business) _base;
	}
	else {
	    this.msg = "maintainContact method failed.  Input object failed to resolve to Business type.";
	    throw new ContactException(this.msg);
	}
	String longName;

	// Long Name must exist
	longName = bus.getLongname();
	if (longName == null || longName.length() <= 0) {
	    throw new ContactException("Business long name cannot be null or blank");
	}
   }

}

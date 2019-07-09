package com.api.address;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;

import java.util.List;

import com.bean.Address;
import com.bean.Business;
import com.bean.Person;
import com.util.RMT2Date;
import com.util.SystemException;

import com.api.ContactException;

import com.api.address.AddressApi;
import com.api.address.AddressException;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

/**
 * Class that implements the AddressApi interface which provides functionality
 * to maintian addresses for personal and business contacts.
 * 
 * @author RTerrell
 * 
 */
class AddresssBeanImpl extends RdbmsDaoImpl implements AddressApi {
    private Logger logger;
    
    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at
     * the acestor level.
     * 
     * @param dbConn
     * @throws SystemException
     * @throws DatabaseException
     */
    public AddresssBeanImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.className = "AddresssBeanImpl";
	this.baseView = "AddressView";
	this.baseBeanClass = "com.bean.Address";
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and
     * the HttpServletRequest object at the acestor level.
     * 
     * @param dbConn
     * @param req
     * @throws SystemException
     * @throws DatabaseException
     */
    public AddresssBeanImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
    }

    /**
     * Initializes the logger for this class instance.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected void initApi() throws DatabaseException, SystemException {
	super.initApi();
	this.logger = Logger.getLogger("AddresssBeanImpl");
    }

    /**
     * Releases allocated resources.
     */
    public void close() {
	this.logger = null;
        super.close();
    }
    
    /**
     * Locates an Address Contact object by custom selection criteria.
     * Be sure that the intended base view and base class is set for this 
     * api before invoking this method.
     */
    public Object findContact(String value) throws ContactException {
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates a Address object by primary key or its address Id and returns
     * Address object to the caller.
     */
    public Object findContact(int uid) throws ContactException {
	Address addr = AddressFactory.createAddress();
	addr.addCriteria(Address.PROP_ADDRID, uid);
	try {
	    Object data[] = this.retrieve(addr);
	    if (data != null && data.length > 0) {
		return (Address) data[0];
	    }
	    return null;
	}
	catch (DatabaseException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates a Address object by person id.
     */
    public Object findAddrByPersonId(int personId) throws AddressException {
	this.criteria = "person_id = " + personId;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates a Address object by business id.
     */
    public Object findAddrByBusinessId(int businessId) throws AddressException {
	this.criteria = "business_id = " + businessId;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on addr1
     */
    public Object findAddrByAddr1(String addr1) throws AddressException {
	try {
	    this.criteria = "addr1  like \'" + addr1 + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on zip code
     */
    public Object findAddrByZip(String zip) throws AddressException {
	try {
	    this.criteria = "zip  like \'" + zip + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on home phone number
     */
    public Object findAddrByHomeNo(String phoneNo) throws AddressException {
	try {
	    this.criteria = "phone_home  like \'" + phoneNo + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on work phone number
     */
    public Object findAddrByWorkNo(String phoneNo) throws AddressException {
	try {
	    this.criteria = "phone_work  like \'" + phoneNo + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on cellular number.
     */
    public Object findAddrByCellNo(String phoneNo) throws AddressException {
	try {
	    this.criteria = "phone_cell  like \'" + phoneNo + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on Main Phone Number
     */
    public Object findAddrByMainNo(String phoneNo) throws AddressException {
	try {
	    this.criteria = "phone_main  like \'" + phoneNo + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on fax phone number.
     */
    public Object findAddrByFaxNo(String phoneNo) throws AddressException {
	try {
	    this.criteria = "phone_fax  like \'" + phoneNo + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * Locates one or more Address objects based based on pager number
     */
    public Object findAddrByPagerNo(String phoneNo) throws AddressException {
	try {
	    this.criteria = "phone_pager  like \'" + phoneNo + "%\'";
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new AddressException(e.getMessage());
	}
    }

    /**
     * This method is responsible for creating and updating a Address object. If
     * Address id is null then Address profile must be created. If Address id
     * has a value, then apply updates to that Address Profile.
     * 
     * @param data The contact object to apply updates to the data source.
     * @return int.  The id of the address for insert operations or the 
     *         total number of rows effected by an update operation. 
     * @throws ContactException
     */
    public int maintainContact(Object contact) throws ContactException {
	int rc;
	Address addr;
	if (contact instanceof Address) {
	    addr = (Address) contact;
	}
	else {
	    this.msg = "maintainContact method failed.  Input object failed to resolve to Address type.";
	    throw new ContactException(this.msg);
	}
	if (addr.getAddrId() == 0) {
	    rc = this.insertAddress(addr);
	    addr.setAddrId(rc);
	}
	else {
	    rc = this.updateAddress(addr);
	}
	return rc;
    }

    /**
     * This method is responsible for adding a Address profile to the system.
     * 
     * @param obj The contact object that is to be inserted.
     * @return The id of the Address instance.
     * @throws AddressException
     */
    protected int insertAddress(Address obj) throws AddressException {
	try {
	    // Validate person object
	    this.validateContact(obj);
	    this.adjustContactKeys(obj);
	    // Create address
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    if (obj.getUserId() == null) {
		obj.setUserId(ut.getLoginId());
	    }
	    int key = this.insertRow(obj, true);
	    return key;
	}
	catch (Exception e) {
	    throw new AddressException(e);
	}
    }

    /**
     * This method is responsible for updating a Address profile
     * 
     * @param obj The contact object that is to be updated.
     * @return The total number of rows effected by the transaction.
     * @throws AddressException
     */
    protected int updateAddress(Address obj) throws AddressException {
	try {
	    // Validate person objec
	    this.validateContact(obj);
	    this.adjustContactKeys(obj);
	    // UPdate address
	    obj.addCriteria(Address.PROP_ADDRID, obj.getAddrId());
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	    int rows = this.updateRow(obj);
	    return rows;
	}
	catch (Exception e) {
	    throw new AddressException(e);
	}
    }

    /**
     * Check if foreign key values of Contact's address instance are not set, and if true, set to null.
     * 
     * @param contact An instance of {@link com.bean.Address Address}.
     */
    private void adjustContactKeys(Address contact) {
	if (contact.getBusinessId() == 0) {
	    contact.setNull(Address.PROP_BUSINESSID);
	}
	if (contact.getPersonId() == 0) {
	    contact.setNull(Address.PROP_PERSONID);
	}
	if (contact.getZip() == 0) {
	    contact.setNull(Address.PROP_ZIP);
	}
	return;
    }

    /**
     * Deletes a contact's address using the id of the address.
     * 
     * @param id THe address id.
     * @return The total number of rows deleted
     * @throws AddressException
     */
    public int deleteContact(int id) throws AddressException {
	Address addr = AddressFactory.createAddress();
	addr.setAddrId(id);
	return this.deleteContact(addr);
    }

    /**
     * Deletes a contact's address.
     * 
     * @param obj 
     *          Either an {@link com.bean.Address Address}, {@link com.bean.Business Business} or {@link com.bean.Person Person} instance which 
     *          targets the address that will be deleted.  
     * @return The total number of rows deleted
     * @throws AddressException
     */
    public int deleteContact(Object obj) throws AddressException {
	Address addr = AddressFactory.createAddress();
	if (obj instanceof Business) {
	    addr.addCriteria(Address.PROP_BUSINESSID, ((Business) obj).getBusinessId());
	}
	else if (obj instanceof Person) {
	    addr.addCriteria(Address.PROP_PERSONID, ((Person) obj).getPersonId());
	}
	else if (obj instanceof Address) {
	    addr.addCriteria(Address.PROP_ADDRID, ((Address) obj).getAddrId());
	}
	else {
	    this.msg = "Contact Address delete failed.  Input object failed to resolve to Address type";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new AddressException(this.msg);
	}
	try {
	    return this.deleteRow(addr);
	}
	catch (DatabaseException e) {
	    throw new AddressException(e);
	}
    }

    /**
     * Validates a Address object.
     */
    public void validateContact(Object contact) throws ContactException {
	int person_id;
	int business_id;
	Address addr;

	if (contact instanceof Address) {
	    addr = (Address) contact;
	}
	else {
	    throw new AddressException("validateContact method failed.  Input object failed to resolve to Address type.");
	}
	person_id = addr.getPersonId();
	business_id = addr.getBusinessId();
	addr.getZip();

	// Person Id and Business Id cannot be null. Either or both must exist.
	if (person_id <= 0 && business_id <= 0) {
	    throw new AddressException("Contact must be assigned either a Person Id or Business Id");
	}

	// Person Id and Business Id cannot have values concurrently. They are
	// mutually exclusive.
	if (person_id > 0 && business_id > 0) {
	    throw new AddressException("Contact must be a person or a business entity");
	}

	// Zip must be a number > zero.
	// if (zip <= 0) {
	// throw new AddressException(this.dbo, 507, null);
	// }
    }

    /**
     * Not implemented.
     * 
     * @return null
     */
    public Object copyAddress(Object source) throws AddressException {
	return null;
    }

}

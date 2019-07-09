package com.api.address;

import com.controller.Request;

import com.bean.Address;
import com.util.SystemException;

import com.api.ContactException;

import com.api.address.AddressApi;
import com.api.address.AddressException;

import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;

import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.db.DatabaseConnectionBean;

/**
 * An implementation of the AddressApi interface which provides functionality
 * for querying and maintaining address contact data using String data types as
 * input parameters and return values to promote a more open systems
 * architecture. The address contact data targeted for this implementation is
 * understood to reside in a relational database.
 * 
 * @author RTerrell
 * 
 */
class AddresssXmlImpl extends AddresssBeanImpl implements AddressApi {
    protected RdbmsDaoQueryHelper daoHelper;
	protected String criteria;

	/**
	 * Constructor begins the initialization of the DatabaseConnectionBean at
	 * the acestor level.
	 * 
	 * @param dbConn
	 * @throws SystemException
	 * @throws DatabaseException
	 */
	public AddresssXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
		super(dbConn);
        this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
		this.className = "AddresssBeanImpl";
		this.baseView = "PersonView";
		this.baseBeanClass = "com.bean.Person";
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
	public AddresssXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	    super(dbConn, req);
	    this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	}

	
	/**
	 * Locates an Address Contact object by custom selection criteria.
	 * Be sure that the intended base view and base class is set for this 
	 * api before invoking this method.
	 */
	public Object findContact(String criteria) throws ContactException {
		Address addr = AddressFactory.createXmlAddress();
		this.setDatasourceSql(DbSqlConst.WHERE_KEY, criteria);
		try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new ContactException(e);
        }
	}


	/**
	 * Locates a Address object by primary key or its address Id and returns
	 * Address object to the caller.
	 */
	public Object findContact(int uid) throws ContactException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addCriteria(Address.PROP_ADDRID, uid);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new ContactException(e);
        }
	}

	/**
	 * Locates a Address object by person id.
	 */
	public Object findAddrByPersonId(int personId) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addCriteria("PersonId", personId);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates a Address object by business id.
	 */
	public Object findAddrByBusinessId(int businessId) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addCriteria("BusinessId", businessId);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on addr1
	 */
	public Object findAddrByAddr1(String addr1) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("Addr1", addr1);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on zip code
	 */
	public Object findAddrByZip(String zip) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("Zip", zip);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on home phone number
	 */
	public Object findAddrByHomeNo(String phoneNo) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("PhoneHome", phoneNo);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on work phone number
	 */
	public Object findAddrByWorkNo(String phoneNo) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("PhoneWork", phoneNo);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on cellular number.
	 */
	public Object findAddrByCellNo(String phoneNo) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("PhoneCell", phoneNo);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on Main Phone Number
	 */
	public Object findAddrByMainNo(String phoneNo) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("PhoneMain", phoneNo);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on fax phone number.
	 */
	public Object findAddrByFaxNo(String phoneNo) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("PhoneFax", phoneNo);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

	/**
	 * Locates one or more Address objects based based on pager number
	 */
	public Object findAddrByPagerNo(String phoneNo) throws AddressException {
		Address addr = AddressFactory.createXmlAddress();
		addr.addLikeClause("PhonePager", phoneNo);
        try {
            return this.daoHelper.retrieveXml(addr);
        }
        catch (DatabaseException e) {
            throw new AddressException(e);
        }
	}

}

package com.api.business;

import com.controller.Request;

import com.bean.Business;
import com.bean.VwBusinessAddress;

import com.util.SystemException;

import com.api.ContactException;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.db.DatabaseConnectionBean;

/**
 * An implementation of the BusinessApi interface which provides functionality
 * for querying and maintaining business contact data using String data types as
 * input parameters and return values to promote a more open systems
 * architecture. The business contact data targeted for this implementation is
 * understood to reside in a relational database.
 * 
 * @author RTerrell
 * 
 */
class BusinessXmlImpl extends BusinessBeanImpl implements BusinessApi {
    private RdbmsDaoQueryHelper daoHelper;
    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at
     * the acestor level.
     * 
     * @param dbConn
     *            Database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
    public BusinessXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.className = "BusinessXmlImpl";
	this.baseView = "BusinessView";
	this.baseBeanClass = "com.bean.Business";
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and
     * the HttpServletRequest object at the acestor level.
     * 
     * @param dbConn
     *            Database connection bean.
     * @param req
     *            The user's request object.
     * @throws SystemException
     * @throws DatabaseException
     */
    public BusinessXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }


    /**
     * Locates a business contact along with its address information using a
     * primary key or Business Id.  Returns the results as XML.
     */
    public Object findContact(int uid) throws ContactException {
	VwBusinessAddress bus = BusinessFactory.createXmlBusinessAddress();
	bus.addCriteria(VwBusinessAddress.PROP_BUSINESSID, uid);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates a VwBusinessAddress Contact object using custom selection criteria. Be
     * sure that the intended base view and base class is set for this api
     * before invoking this method.
     */
    public Object findContact(String criteria) throws ContactException {
	VwBusinessAddress bus = BusinessFactory.createXmlBusinessAddress();
	bus.addCustomCriteria(criteria);
	//this.setDatasourceSql(DbSqlConst.WHERE_KEY, criteria);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Fetches a business entiry along with its address information.
     * 
     * @param busId THe internal id of the business.
     * @return XML document
     * @throws ContactException
     */
    public Object findBusAddress(int busId) throws BusinessException {
        VwBusinessAddress bus = BusinessFactory.createXmlBusinessAddress();
        bus.addCriteria(VwBusinessAddress.PROP_BUSINESSID, busId);
        try {
            return this.daoHelper.retrieveXml(bus);
        }
        catch (DatabaseException e) {
            throw new BusinessException(e);
        }
        }
    
    
    /**
     * Locates one or more Business contacts by the contact person's first name.
     */
    public Object findBusByContactFirstName(String contactFirstName) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addLikeClause("ContactFirstname", contactFirstName);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by the contact person's last name.
     */
    public Object findBusByContactLastName(String contactLastname) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addLikeClause("ContactLastname", contactLastname);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by business type.
     * 
     * @param busTypeId
     *            The business type id
     * @return XML document
     * @throws BusinessException
     */
    public Object findBusByBusType(int busTypeId) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addCriteria("BusType", busTypeId);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by business service type.
     * 
     * @param servTypeId
     * @return XML document
     * @throws BusinessException
     */
    public Object findBusByServType(int servTypeId) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addCriteria("ServType", servTypeId);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by long name
     * 
     * @param longName
     *            The long name of the business.
     * @return XML document
     * @throws BusinessException
     * 
     */
    public Object findBusByLongName(String longName) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addLikeClause("Longname", longName);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts by short name
     * 
     * @param shortName
     * @return XML document
     * @throws BusinessException
     */
    public Object findBusByShortName(String shortName) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addLikeClause("Shortname", shortName);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts based tax id.
     * 
     * @parm taxId The business' tax id
     * @return XML document
     * @throws BusinessException
     */
    public Object findBusByTaxId(String taxId) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addLikeClause("TaxId", taxId);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }

    /**
     * Locates one or more Business contacts based on the URL of the web site.
     * 
     * @param website
     *            The URL of the website.
     * @return XML document
     * @throws BusinessException
     */
    public Object findBusByWebsite(String website) throws BusinessException {
	Business bus = BusinessFactory.createXmlBusiness();
	bus.addLikeClause("Website", website);
    try {
        return this.daoHelper.retrieveXml(bus);
    }
    catch (DatabaseException e) {
        throw new BusinessException(e);
    }
    }


    /**
     * Retrieves one or more business contacts as an XML document using a 
     * comma-separated list of business id's.  The result set is order by 
     * business id.
     * 
     * @param idList 
     *          A list of business id's that are separated by commas.
     * @return A XML document containing one or more business/address contacts.
     * @throws BusinessException
     */
    public Object findByBusinessId(String idList) throws BusinessException {
        if (idList == null || idList.length() <= 0) {
            throw new BusinessException("Parameter, id, must contain one or more business id\'s separated by commas");
        }
        String ids[] = idList.split(","); 
        VwBusinessAddress bus = BusinessFactory.createXmlBusinessAddress();
        bus.addInClause(VwBusinessAddress.PROP_BUSINESSID, ids);
        bus.addOrderBy(VwBusinessAddress.PROP_BUSINESSID, VwBusinessAddress.ORDERBY_ASCENDING);
        try {
            return this.daoHelper.retrieveXml(bus);
        }
        catch (DatabaseException e) {
            throw new BusinessException(e);
        }
    }
}

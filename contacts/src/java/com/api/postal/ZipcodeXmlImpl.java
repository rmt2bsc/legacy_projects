package com.api.postal;

import java.util.List;

import com.controller.Request;

import com.bean.VwZipcode;
import com.bean.Zipcode;

import com.util.SystemException;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.pagination.PaginationQueryResults;

import com.bean.db.DatabaseConnectionBean;

/**
 * An implementation of the ZipcodeApi interface which provides functionality
 * for querying and maintaining zip code data using String data types as input
 * parameters and return values to promote a more open systems architecture. The
 * zip code data targeted for this implementation is understood to reside in a
 * relational database.
 * 
 * @author RTerrell
 * 
 */
class ZipcodeXmlImpl extends RdbmsDaoImpl implements ZipcodeApi {
    private RdbmsDaoQueryHelper daoHelper;

    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at
     * the acestor level.
     * 
     * @param dbConn
     *            Database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public ZipcodeXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.className = "ZipcodeXmlImpl";
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and
     * the HttpServletRequest object at the acestor level.
     * 
     * @param dbConn
     *            Database connection bean
     * @param req
     *            User's request.
     * @throws SystemException
     * @throws DatabaseException
     */
    public ZipcodeXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Fetches zip code object using exact matching on column, zip.
     * 
     * @param zipcode
     *            The actual zip code number.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCode(String zipcode) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addCriteria("Zip", zipcode);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches zip code using an integer value as the zip code argument.
     * 
     * @param zipcode
     *            An integer zip code value.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCode(int zipcode) throws ZipcodeException {
	return this.findZipByCode(String.valueOf(zipcode));
    }

    /**
     * Fetches zip code extension object using exact matching on column, zip.
     *    
     * @param zipcode The actual zip code number.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipExtByCode(String zipcode) throws ZipcodeException {
	VwZipcode zip = AddressComponentsFactory.createZipcodeExt();
	zip.addCriteria(VwZipcode.PROP_ZIP, zipcode);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Locates Zip Code extension using an unique identifier key.   
     * 
     * @param uid The database primary key associated with this zip code.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipExtById(int uid) throws ZipcodeException {
	VwZipcode zip = AddressComponentsFactory.createZipcodeExt();
	zip.addCriteria(VwZipcode.PROP_ZIPID, uid);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, zip.
     * 
     * @param zipcode
     *            The zip code.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipX(String zipcode) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addLikeClause("Zip", zipcode);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Locates Zip Code using an unique identifier key.
     * 
     * @param uid
     *            The database primary key associated with this zip code.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipById(int uid) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addCriteria(Zipcode.PROP_ZIPID, uid);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column,
     * area_code.
     * 
     * @param areaCode
     *            The area code.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByAreaCode(String areaCode) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addLikeClause(Zipcode.PROP_AREACODE, areaCode);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column,
     * state.
     * 
     * @param state
     *            The state that zip code belongs.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByState(String state) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addLikeClause("State", state);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, city.
     * 
     * @param city
     *            The city in which the zip code belongs.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCity(String city) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addLikeClause("City", city);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column,
     * county.
     * 
     * @param county
     *            The county in which the zip code belongs.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCounty(String county) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addLikeClause("County", county);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column,
     * time_zone.
     * 
     * @param timeZone
     *            The times zone which a zip code belongs.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByTimeZone(String timeZone) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createXmlZipcode();
	zip.addLikeClause("TimeZone", timeZone);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes based on custom criteria supplied by the
     * client. Be sure to match column names to those of the zipcode table
     * definition.
     * 
     * @param criteria The custom criteria.
     * @return If found, returns XML document. Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZip(String criteria) throws ZipcodeException {
	VwZipcode zip = AddressComponentsFactory.createZipcodeExt();
	zip.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveXml(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.api.postal.ZipcodeApi#findZip(java.lang.String, int)
     */
    public PaginationQueryResults findZip(String criteria, int pageNo) throws ZipcodeException {
        // TODO Auto-generated method stub
        return null;
    }

   

}

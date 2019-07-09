package com.api.postal;

import com.controller.Request;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.bean.Country;
import com.bean.db.DatabaseConnectionBean;
import com.util.SystemException;

/**
 * @author RTerrell
 *
 */
public class CountryXmlImpl extends RdbmsDaoImpl implements CountryApi {
    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * Initializes a newly created CountryXmlImpl object using a DatabaseConnectionBean object.
     *   
     * @param dbConn a {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @throws DatabaseException
     * @throws SystemException
     */
    public CountryXmlImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.logger = Logger.getLogger("CountryXmlImpl");
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger.log(Level.INFO, "CountryXmlImpl initialized");
    }

    /**
     * Constructs a new CountryXmlImpl using DatabaseConnectionBean and HttpServletReqeust objects.
     * 
     * @param dbConn a {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param request HttpServletReqeust
     * @throws DatabaseException
     * @throws SystemException
     */
    public CountryXmlImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
	this.logger = Logger.getLogger("CountryXmlImpl");
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Fetches country data using custom criteria supplied by the client.
     *
     * @param criteria Custom selection criteria.
     * @return String XML document pattering {@link com.bean.Country Country} when found 
     *         and null when not found.
     * @throws CountryException
     */
    public Object findCountry(String criteria) throws CountryException {
	Country country = AddressComponentsFactory.createCountry();
	country.addCustomCriteria(criteria);
	country.addOrderBy(Country.PROP_NAME, Country.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(country);
	}
	catch (DatabaseException e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Finds data pertaining to a particular country using country id.
     * 
     * @param countryId The id of the country.
     * @return String XML document pattering {@link com.bean.Country Country} when found 
     *         and null when not found.
     * @throws CountryException
     */
    public Object findCountryById(int countryId) throws CountryException {
	Country country = AddressComponentsFactory.createCountry();
	country.addCriteria(Country.PROP_COUNTRYID, countryId);
	try {
	    return this.daoHelper.retrieveXml(country);
	}
	catch (DatabaseException e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Finds countries by country name.
     * 
     * @param name The name of the country.
     * @return String XML document pattering {@link com.bean.Country Country} when found 
     *         and null when not found.
     * @throws CountryException
     */
    public Object findCountryByName(String name) throws CountryException {
	Country country = AddressComponentsFactory.createCountry();
	country.addLikeClause(Country.PROP_NAME, name);
	try {
	    return this.daoHelper.retrieveXml(country);
	}
	catch (DatabaseException e) {
	    throw new CountryException(e);
	}
    }

    /**
     * N/A
     * 
     * @param obj An {@link com.bean.Country Country} object
     * @return int 0
     * @throws CountryException
     */
    public int maintainCountry(Country obj) throws CountryException {
	return 0;
    }

    /**
     * Deletes a country record from an external datasource.
     * 
     * @param obj An {@link com.bean.Country Country} object
     * @return  The total number of records effected from the transaction.
     * @throws CountryException
     */
    public int deleteCountry(Country obj) throws CountryException {
	return 0;
    }

}

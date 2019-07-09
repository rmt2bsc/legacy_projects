package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.bean.db.DatabaseConnectionBean;
import com.controller.Request;
import com.controller.Context;

import com.action.ActionHandlerException;

import com.api.ContactException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.postal.AddressComponentsFactory;
import com.api.postal.CountryApi;

import com.util.SystemException;

/**
 * Web Service for managing country data.  The result of each query is returned to the caller as 
 * a XML String as the service payload.
 * <p>   
 * See {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction} 
 * for a detailed description of RSPayload format.
 * 
 * @author RTerrell
 * @deprecated No Longer used.  will be removed from future versions
 *
 */
public class CountryProducer extends AbstractContactProducer {
    private static final String SRVC_COUNTRY = "Services.getCountry";

    private static final String SRVC_COUNTRYID = "Services.getCountryById";

    private static final String SRVC_COUNTRYNAME = "Services.getCountryByName";

    private Logger logger;

    private String tempParm;

    /**
     * Contructs a AbstractHttpServerAction object by initializing the servlet
     * context and the user's request.
     * 
     * @throws SystemException
     */
    public CountryProducer() throws SystemException, DatabaseException {
	super();
	this.logger = Logger.getLogger("CountryProducer");
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Drives the process of retrieving country data either by custom criteria, 
     * country id, or country name.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
	String xml = null;

	Properties prop = (Properties) this.inData;

	if (this.command.equalsIgnoreCase(CountryProducer.SRVC_COUNTRY)) {
	    xml = this.getCountry(prop);
	}
	if (this.command.equalsIgnoreCase(CountryProducer.SRVC_COUNTRYID)) {
	    xml = this.getCountryById(prop);
	}
	if (this.command.equalsIgnoreCase(CountryProducer.SRVC_COUNTRYNAME)) {
	    xml = this.getCountryByName(prop);
	}
	this.outData = xml;
    }

    /**
     * Retrieves {@link com.bean.Country Country} data using special selection criteria.  
     * Optionally, this service can utilize the parameter, <i>criteria</i>, to provide 
     * custom selection criteria to filter the results of the query.   If custom criteria
     * does not exist, the query will obtain a complete list of countries available in
     * the database.
     *
     * @param parms the request parameters
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getCountry(Properties parms) throws ActionHandlerException {
	Object xml;
	String criteria = parms.getProperty("criteria");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CountryApi api = AddressComponentsFactory.createCountryXmlApi((DatabaseConnectionBean) tx.getConnector());
	try {
	    xml = api.findCountry(criteria);
	    if (xml != null) {
		return xml.toString();
	    }
	    return null;
	}
	catch (ContactException e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
 	    api.close();
 	    tx.close();
 	    api = null;
 	    tx = null;
 	}
    }

    /**
     * Retrieves country data by country id.  This service requires that the id of the 
     * country that is to be retireve exist on the request as an attribute nameed, 
     * <i>CountryId</i>.
     * 
     * @param parms the request parameters
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getCountryById(Properties parms) throws ActionHandlerException {
	Object xml;
	tempParm = parms.getProperty("CountryId");
	if (tempParm == null) {
	    this.msg = "Country code is required for service, " + this.command;
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	int countryId;
	try {
	    countryId = Integer.parseInt(tempParm);
	}
	catch (NumberFormatException e) {
	    this.msg = "Service call failed: " + this.command + ".  An invalid country code was passed: " + tempParm;
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CountryApi api = AddressComponentsFactory.createCountryXmlApi((DatabaseConnectionBean) tx.getConnector());
	try {
	    xml = api.findCountryById(countryId);
	    if (xml != null) {
		return xml.toString();
	    }
	    return null;
	}
	catch (ContactException e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	 finally {
 	    api.close();
 	    tx.close();
 	    api = null;
 	    tx = null;
 	}
    }

    /**
     * Retrieves country data by its name.  This service requires that the id of the 
     * country that is to be retireve exist on the request as an attribute named, 
     * <i>CountryName</i>.
     * 
     * @param parms the request parameters
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getCountryByName(Properties parms) throws ActionHandlerException {
	Object xml;
	String name = parms.getProperty("CountryName");
	if (name == null) {
	    this.msg = "Country name is required for service, " + this.command;
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CountryApi api = AddressComponentsFactory.createCountryXmlApi((DatabaseConnectionBean) tx.getConnector());
	try {
	    xml = api.findCountryByName(name);
	    if (xml != null) {
		return xml.toString();
	    }
	    return null;
	}
	catch (ContactException e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
 	    api.close();
 	    tx.close();
 	    api = null;
 	    tx = null;
 	}
    }
}

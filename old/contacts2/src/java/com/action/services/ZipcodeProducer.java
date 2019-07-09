package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.api.ContactException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.postal.AddressComponentsFactory;
import com.api.postal.ZipcodeApi;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;



/**
 * Zip code web service that performs various queries against the database by zip code, 
 * area code, state, county, and time zone. * The result of each query is returned to 
 * the caller as a XML String as the service payload.
 * <p>   
 * See {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction} 
 * for a detailed description of RSPayload format.
 * @author RTerrell
 * @deprecated No Longer used.  will be removed from future versions
 *
 */
public class ZipcodeProducer extends AbstractContactProducer {
    private static final String ARG_ZIP = "zipcode";
    private static final String ARG_AREACODE = "areacode";
    private static final String ARG_STATE = "state";
    private static final String ARG_CITY = "city";
    private static final String ARG_COUNTY = "county";
    private static final String ARG_TIMEZONE = "timezone";
    
    private static final String SRVC_ZIP = "getZipByCode";
    private static final String SRVC_ZIPAREACODE = "getZipByAreaCode";
    private static final String SRVC_ZIPSTATE = "getZipByState";
    private static final String SRVC_ZIPCITY = "getZipByCity";
    private static final String SRVC_ZIPCOUNTY = "getZipByCounty";
    private static final String SRVC_ZIPTIMEZONE = "getZipByTimeZone";
    private Logger logger;
       
   
    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public ZipcodeProducer() throws SystemException {
	super();
        this.logger = Logger.getLogger("ZipcodeProducer");
        this.logger.log(Level.INFO, "logger initialized");
    }
    

    /**
     * Processes the user's request to retrieve zip code data filtered by either
     * zip code, area code, state, city, county, or time zone.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        String temp;
        String xml = null;
        
        Properties prop = (Properties) this.inData;
        
        if (this.command.equalsIgnoreCase(ZipcodeProducer.SRVC_ZIP)) {
            temp = prop.getProperty(ZipcodeProducer.ARG_ZIP);
            xml = this.getZip(temp);
        }
        if (this.command.equalsIgnoreCase(ZipcodeProducer.SRVC_ZIPAREACODE)) {
            temp = prop.getProperty(ZipcodeProducer.ARG_AREACODE);
            xml = this.getZipByAreaCode(temp);
        }
        if (this.command.equalsIgnoreCase(ZipcodeProducer.SRVC_ZIPSTATE)) {
            temp = prop.getProperty(ZipcodeProducer.ARG_STATE);
            xml = this.getZipByState(temp);
        }
        if (this.command.equalsIgnoreCase(ZipcodeProducer.SRVC_ZIPCITY)) {
            temp = prop.getProperty(ZipcodeProducer.ARG_CITY);
            xml = this.getZipByCity(temp);
        }
        if (this.command.equalsIgnoreCase(ZipcodeProducer.SRVC_ZIPCOUNTY)) {
            temp = prop.getProperty(ZipcodeProducer.ARG_COUNTY);
            xml = this.getZipByCounty(temp);
        }
        if (this.command.equalsIgnoreCase(ZipcodeProducer.SRVC_ZIPTIMEZONE)) {
            temp = prop.getProperty(ZipcodeProducer.ARG_TIMEZONE);
            xml = this.getZipByTimeZone(temp);
        }
        this.outData = xml;
    }
    
    /**
     * Retrieves zip code data by zip code.
     * 
     * @param zip the zip code.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getZip(String zip) throws ActionHandlerException {
        Object xml;
        if (zip ==null) {
            throw new ActionHandlerException("Zip code is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        ZipcodeApi api = AddressComponentsFactory.createZipcodeXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findZipByCode(zip);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (ContactException e) {
            e.printStackTrace();
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
     * Retrieves zip code data by area code.
     * 
     * @param areacode The area code.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getZipByAreaCode(String areacode) throws ActionHandlerException {
        Object xml;
        if (areacode == null) {
            throw new ActionHandlerException("Area code is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        ZipcodeApi api = AddressComponentsFactory.createZipcodeXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findZipByAreaCode(areacode);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (ContactException e) {
            e.printStackTrace();
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
     * Retrieves zip code data by state code.
     * 
     * @param state The state.
     * @return XML String
     * @throws ActionHandlerException
     */
    protected String getZipByState(String state) throws ActionHandlerException {
        Object xml;
        if (state == null) {
            throw new ActionHandlerException("State is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        ZipcodeApi api = AddressComponentsFactory.createZipcodeXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findZipByState(state);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (ContactException e) {
            e.printStackTrace();
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
     * Retrieves zip code data by city.
     * 
     * @param city The city.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getZipByCity(String city) throws ActionHandlerException {
        Object xml;
        if (city == null) {
            throw new ActionHandlerException("City is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        ZipcodeApi api = AddressComponentsFactory.createZipcodeXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findZipByCity(city);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (ContactException e) {
            e.printStackTrace();
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
     * Retrieves zip code data by county.
     * 
     * @param county The county.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getZipByCounty(String county) throws ActionHandlerException {
        Object xml;
        if (county == null) {
            throw new ActionHandlerException("County is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        ZipcodeApi api = AddressComponentsFactory.createZipcodeXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findZipByCounty(county);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (ContactException e) {
            e.printStackTrace();
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
     * Retrieves zip code data by time zone.
     * 
     * @param tz The time zone code.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getZipByTimeZone(String tz) throws ActionHandlerException {
        Object xml;
        if (tz == null) {
            throw new ActionHandlerException("Time Zone is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        ZipcodeApi api = AddressComponentsFactory.createZipcodeXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findZipByState(tz);
            if (xml != null) {
                return xml.toString();    
            }
            return null;
        }
        catch (ContactException e) {
            e.printStackTrace();
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

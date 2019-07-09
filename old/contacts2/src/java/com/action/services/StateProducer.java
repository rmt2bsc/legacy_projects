package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.api.ContactException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.postal.AddressComponentsFactory;
import com.api.postal.StatesApi;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;



/**
 * Web Service for managing state/provinces.  The result of each query is returned to the caller as 
 * a XML String as the service payload.
 * <p>   
 * See {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction} 
 * for a detailed description of RSPayload format.
 * 
 * @author RTerrell
 * @deprecated use {@link com.services.HttpUsStatesSearchService HttpUsStatesSearchService}
 *
 */
public class StateProducer extends AbstractContactProducer {
    private static final String ARG_STATE = "StateId";
    private static final String ARG_COUNTRY = "CountryId";
    
    private static final String SRVC_STATE = "Services.getState";
    private static final String SRVC_STATECOUNTRY = "Services.getStateByCountry";
    private static final String SRVC_STATEBYCRITERIA = "Services.getStateByCriteria";
    private Logger logger;
       
   
    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public StateProducer() throws SystemException {
	super();
        this.logger = Logger.getLogger("StateProducer");
    }
    

    /**
     * Drives the process of retrieving state/province data either by state id or country id.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        String temp;
        String xml = null;
        
        Properties prop = (Properties) this.inData;
        
        if (this.command.equalsIgnoreCase(StateProducer.SRVC_STATE)) {
            temp = prop.getProperty(StateProducer.ARG_STATE);
            xml = this.getState(temp);
        }
        if (this.command.equalsIgnoreCase(StateProducer.SRVC_STATECOUNTRY)) {
            temp = prop.getProperty(StateProducer.ARG_COUNTRY);
            xml = this.getStateByCountry(temp);
        }
        if (this.command.equalsIgnoreCase(StateProducer.SRVC_STATEBYCRITERIA)) {
            xml = this.getStateByCriteria(prop);
        }
        this.outData = xml;
    }
    
    /**
     * Retrieves U.S. state data by state abbreviated code.
     * 
     * @param state State abbreviation.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getState(String state) throws ActionHandlerException {
        Object xml;
        if (state ==null) {
            this.msg = "State code is required for service, " + this.command;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        StatesApi api = AddressComponentsFactory.createStatesXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findState(" state_id = \'" + state + "\'");
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
     * Retrieves state/province data by country code.
     * 
     * @param countryIdStr The country code.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getStateByCountry(String countryIdStr) throws ActionHandlerException {
        Object xml;
        if (countryIdStr == null) {
            this.msg = "Country code is required for service, " + this.command;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        int countryId;
        try {
            countryId = Integer.parseInt(countryIdStr);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid country code was passed: " + countryIdStr;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        StatesApi api = AddressComponentsFactory.createStatesXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findStateByCountry(countryId);
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
     * Retrieves state/province data using custom criteria.
     * 
     * @param prop A Properties object containing all parameters needed to execute this service.
     * @return XML String.
     * @throws ActionHandlerException Genera contact exception errors.
     */
    protected String getStateByCriteria(Properties prop) throws ActionHandlerException {
        Object xml;
        String selectCriteria = prop.getProperty(StateProducer.ARG_SELECTCRITERIA);
        if (selectCriteria == null) {
            this.msg = "Custom selection criteria is required for service, " + this.command;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        StatesApi api = AddressComponentsFactory.createStatesXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findState(selectCriteria);
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

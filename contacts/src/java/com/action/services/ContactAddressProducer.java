package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.api.ContactException;

import com.api.address.AddressApi;
import com.api.address.AddressFactory;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;



/**
 * Business contact web service that performs various queries against 
 * the database by business id, business name, tax id, business type id, or 
 * service type id.  The result of each query is returned to the caller as 
 * a XML String as the service payload.
 * <p>   
 * See {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction} 
 * for a detailed description of RSPayload format.
 * 
 * @author RTerrell
 * @deprecated No Longer used.  will be removed from future versions
 *
 */
public class ContactAddressProducer extends AbstractContactProducer {
    private static final String ARG_ID = "Id";
    private static final String ARG_BUSID = "BusinessId";
    private static final String ARG_PERID = "PersonId";
    
    private static final String SRVC_ADDR_BY_ID = "Services.getAddressById";
    private static final String SRVC_ADDR_BY_BUSID = "Services.getAddressByBusinessId";
    private static final String SRVC_ADDR_BY_PERID = "Services.getAddressByPersonId";
    private Logger logger;
       
   
    /**
     * Default constructor.
     *  
     * @throws SystemException
     */
    public ContactAddressProducer() throws SystemException {
	super();
        this.logger = Logger.getLogger("ContactAddressProducer");
    }
    

    /**
     * Processes the user's request to retrieve business contact data filtered by 
     * business id, business name, tax id, business type id, or service type id.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        String xml = null;
        Properties prop = (Properties) this.inData;
        if (this.command.equalsIgnoreCase(ContactAddressProducer.SRVC_ADDR_BY_ID)) {
            xml = this.getAddressById(prop);
        }
        if (this.command.equalsIgnoreCase(ContactAddressProducer.SRVC_ADDR_BY_BUSID)) {
            xml = this.getAddressByBusinessId(prop);
        }
        if (this.command.equalsIgnoreCase(ContactAddressProducer.SRVC_ADDR_BY_PERID)) {
            xml = this.getAddressByPersonId(prop);
        }
        this.outData = xml;
    }
    
    /**
     * Retrieves contact address data by address id.
     * 
     * @param prop the input data.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getAddressById(Properties prop) throws ActionHandlerException {
        String temp = prop.getProperty(ContactAddressProducer.ARG_ID);
        Object xml;
        int uid = 0;
        if (temp == null) {
            throw new ActionHandlerException("Address Id is required for service, " + this.command);
        }
        try {
            uid = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid Address Id was passed: " + temp;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        AddressApi api = AddressFactory.createAddressXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findContact(uid);
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
     * Retrieves business contact address data using business id.
     * 
     * @param busName Input data
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getAddressByBusinessId(Properties prop) throws ActionHandlerException {
        String temp = prop.getProperty(ContactAddressProducer.ARG_BUSID);
        Object xml;
        int uid;
        
        if (temp == null) {
            throw new ActionHandlerException("Business Id is required for service, " + this.command);
        }
        try {
            uid = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid Business Id was passed: " + temp;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        AddressApi api = AddressFactory.createAddressXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findAddrByBusinessId(uid);
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
     * Retreives personal contact data using business type id.
     * 
     * @param busTypeIdStr The person type id.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getAddressByPersonId(Properties prop) throws ActionHandlerException {
        String temp = prop.getProperty(ContactAddressProducer.ARG_PERID);
        Object xml;
        int uid;
        
        if (temp == null) {
            throw new ActionHandlerException("Person Id is required for service, " + this.command);
        }
        try {
            uid = Integer.parseInt(temp);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid Person Id was passed: " + temp;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        AddressApi api = AddressFactory.createAddressXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findAddrByPersonId(uid);
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

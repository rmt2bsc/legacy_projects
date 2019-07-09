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

import com.api.db.orm.DataSourceFactory;

import com.api.personal.PersonApi;
import com.api.personal.PersonFactory;

import com.bean.Address;
import com.bean.Person;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;



/**
 * Personal contact web service that performs various queries against the database by 
 * person id, social security number (ssn), and email id.  The result of each query is 
 * returned to the caller as a XML String as the service payload.
 * <p>   
 * See {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction} 
 * for a detailed description of RSPayload format.
 * 
 * @author RTerrell
 * @deprecated No Longer used.  will be removed from future versions
 *
 */
public class PersonalContactProducer extends AbstractContactProducer {
    private static final String ARG_PERSONID = "PersonId";
    private static final String ARG_SSN = "Ssn";
    private static final String ARG_EMAILID = "EmailId";
    
    private static final String SRVC_PERSON_BY_ID = "Services.getPersonById";
    private static final String SRVC_PERSONADDR_BY_ID = "Services.getPersonAddressById";
    private static final String SRVC_PERSON_BY_SSN = "Services.getPersonBySsn";
    private static final String SRVC_PERSON_BY_EMAIL = "Services.getPersonByEmail";
    private static final String SRVC_MAINT = "Services.maintainPerson";
    private Logger logger;
       
   
    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public PersonalContactProducer() throws SystemException {
	super();
        this.logger = Logger.getLogger("PersonalContactProducer");
    }
    

    /**
     * Processes the user's request to retrieve personal contact data filtered by 
     * person id, ssn, or email id.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
        String temp;
        String xml = null;
        
        Properties prop = (Properties) this.inData;
        
        if (this.command.equalsIgnoreCase(PersonalContactProducer.SRVC_PERSON_BY_ID)) {
            temp = prop.getProperty(PersonalContactProducer.ARG_PERSONID);
            xml = this.getPersonById(temp);
        }
        if (this.command.equalsIgnoreCase(PersonalContactProducer.SRVC_PERSONADDR_BY_ID)) {
            temp = prop.getProperty(PersonalContactProducer.ARG_PERSONID);
            xml = this.getPersonAddressById(temp);
        }
        if (this.command.equalsIgnoreCase(PersonalContactProducer.SRVC_PERSON_BY_SSN)) {
            temp = prop.getProperty(PersonalContactProducer.ARG_SSN);
            xml = this.getPersonBySsn(temp);
        }
        if (this.command.equalsIgnoreCase(PersonalContactProducer.SRVC_PERSON_BY_EMAIL)) {
            temp = prop.getProperty(PersonalContactProducer.ARG_EMAILID);
            xml = this.getPersonByEmail(temp);
        }
        if (this.command.equalsIgnoreCase(PersonalContactProducer.SRVC_MAINT)) {
            xml = this.maintainContact(prop);
        }
        this.outData = xml;
    }
    
    /**
     * Retrieves personal contact data by person id.
     * 
     * @param uidStr The person id.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getPersonById(String uidStr) throws ActionHandlerException {
        Object xml;
        int uid = 0;
        if (uidStr ==null) {
            throw new ActionHandlerException("Person Id is required for service, " + this.command);
        }
        try {
            uid = Integer.parseInt(uidStr);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid Person Id was passed: " + uidStr;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        PersonApi api = PersonFactory.createPersonXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findContact(uid);
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
     * Retrieves a person and its address data using the internal person id.
     * 
     * @param uidStr The person internal id as a String.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getPersonAddressById(String uidStr) throws ActionHandlerException {
        Object xml;
        int uid = 0;
        if (uidStr ==null) {
            throw new ActionHandlerException("Person Id is required for service, " + this.command);
        }
        try {
            uid = Integer.parseInt(uidStr);
        }
        catch (NumberFormatException e) {
            this.msg = "Service call failed: " + this.command + ".  An invalid Person Id was passed: " + uidStr;
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        PersonApi api = PersonFactory.createPersonXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findPerAddress(uid);
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
     * Retrieves personal contact data by ssn.
     * 
     * @param ssn Person's social security number withoud separating dashes.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getPersonBySsn(String ssn) throws ActionHandlerException {
        Object xml;
        if (ssn == null) {
            throw new ActionHandlerException("Social Security Number is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        PersonApi api = PersonFactory.createPersonXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findPerBySSN(ssn);
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
     * Retrieves personal contact data by emai address.
     * 
     * @param email The person's email address.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getPersonByEmail(String email) throws ActionHandlerException {
        Object xml;
        if (email == null) {
            throw new ActionHandlerException("Emai Id is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        PersonApi api = PersonFactory.createPersonXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findPerByEMail(email);
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
     * This service is responsible for creating and updating personal contacts.
     * If Person id is less than or equal to zero then a personal profile will
     * be created. Otherwise, update the database with personal contact changes.
     * <p>
     * Upon successful completion of this service call, the results are returned
     * to the caller as an element of <i>data</i> within the RSPayload XML document.  
     * The data layout of the results goes as follows:
     * <pre>
     *    &lt;PersonId&gt;3212&lt;/PersonId&gt;
     *    &lt;AddressId&gt;84328&lt;/AddressId&gt;
     *    &lt;RowCount&gt;2&lt;/RowCount&gt;
     *    &lt;MaintType&gt;insert&lt;/MaintType&gt;
     * </pre>
     * <ol>
     *   <li><b>PersonId</b> - The internal id of the person entity processed.</li>
     *   <li><b>AddressId</b> - The internal id of the person's address entity processed.</li>
     *   <li><b>RowCount</b> - The total number of rows effected by the transaction.</li>
     *   <li><b>MaintType</b> - Indicates whether an insert or an update was performed to maintain the contact.</li>
     * </ol>
     * 
     * @param prop Proerties collecton containing request data.
     * @return XML document as the results of the service call.
     * @throws ActionHandlerException
     *            When the person id and/or the address id input is not found or 
     *            when person api produces an error when maintaining the contact.
     */
    protected String maintainContact(Properties prop) throws ActionHandlerException {
        Person per = PersonFactory.createPerson();
        Address addr = AddressFactory.createAddress();
        boolean newRec = false;
        
        try {
            String temp;
            int id;
            DataSourceFactory.packageBean(prop, per);
            DataSourceFactory.packageBean(prop, addr);
            
            // Ensure that the internal id's for the business 
            // and address objects are set from the request.
            try {
                temp = prop.getProperty("PersonId");
                id = Integer.parseInt(temp);
                newRec = (id <= 0);
                per.setPersonId(id);                
            }
            catch (NumberFormatException e) {
                throw new ActionHandlerException("A valid person id must be included as an input parameter");
            }
            
            try {
                temp = prop.getProperty("AddressId");
                id = Integer.parseInt(temp);
                addr.setAddrId(id);                
            }
            catch (NumberFormatException e) {
                throw new ActionHandlerException("A valid address id must be included as an input parameter");
            }
        }
        catch (SystemException e) {
            throw new ActionHandlerException(e.getMessage());
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
        PersonApi api = PersonFactory.createPersonXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        AddressApi addrApi = AddressFactory.createAddressApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        int rc1;
        int rc2;
        
        try {
            // Insert or update business.
            rc1 = api.maintainContact(per);
            
            // Ensure that person id is set for the address instance when inserting.
            if (newRec) {
                addr.setPersonId(per.getPersonId());    
            }
            // Insert of update address
            rc2 = addrApi.maintainContact(addr);
            
            // Setup return message
            StringBuffer xml = new StringBuffer(100);
            xml.append("<PersonId>");
            xml.append(per.getPersonId());
            xml.append("</PersonId>");
            xml.append("<AddressId>");
            xml.append(addr.getAddrId());
            xml.append("</AddressId>");
            xml.append("<RowCount>");
            xml.append(rc1 + rc2);
            xml.append("</RowCount>");
            xml.append("<MaintType>");
            xml.append((newRec ? "insert" : "update"));
            xml.append("</MaintType>");
            
            // Return results
            return xml.toString();
        }
        catch (ContactException e) {
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
    	    api.close();
    	    addrApi.close();
    	    tx.close();
    	    api = null;
    	    addrApi = null;
    	    tx = null;
    	}
    }
}

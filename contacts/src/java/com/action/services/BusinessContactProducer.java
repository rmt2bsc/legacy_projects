package com.action.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.api.ContactException;

import com.api.address.AddressApi;
import com.api.address.AddressFactory;

import com.api.business.BusinessApi;
import com.api.business.BusinessFactory;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceFactory;

import com.bean.Address;
import com.bean.Business;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * Business contact web service that performs various queries against 
 * the database by business id, business name, tax id, business type id, or 
 * service type id.  The result of each query is returned to the caller as 
 * a XML String as the service payload in the following format:
 * <p>   
 * The XML document returned will be in the following format:
 * <pre>
 * &lt;VwBusinessAddressView&gt;
 *  	&lt;vw_business_address&gt;
 *  		&lt;bus_type&gt;26&lt;/bus_type&gt;
 *  		&lt;addr4/&gt;
 *  		&lt;addr3&gt;Suite 111&lt;/addr3&gt;
 *  		&lt;bus_longname&gt;RMT2 Business Systems Corp&lt;/bus_longname&gt;
 *  		&lt;addr2&gt;Building #697&lt;/addr2&gt;
 *  		&lt;addr1&gt;2300 dana dr&lt;/addr1&gt;
 *  		&lt;addr_phone_work/&gt;
 *  		&lt;addr_business_id&gt;2&lt;/addr_business_id&gt;
 *  		&lt;bus_contact_phone&gt;9723457689&lt;/bus_contact_phone&gt;
 *  		&lt;business_id&gt;2&lt;/business_id&gt;
 *  		&lt;bus_contact_lastname&gt;terrell&lt;/bus_contact_lastname&gt;
 *  		&lt;addr_phone_home/&gt;
 *  		&lt;addr_zip&gt;75028&lt;/addr_zip&gt;
 *  		&lt;addr_phone_fax&gt;9726669840&lt;/addr_phone_fax&gt;
 *  		&lt;addr_person_id&gt;0&lt;/addr_person_id&gt;
 *  		&lt;bus_serv_type&gt;61&lt;/bus_serv_type&gt;
 *  		&lt;zip_state&gt;TX&lt;/zip_state&gt;
 *  		&lt;addr_phone_cell/&gt;
 *  		&lt;bus_contact_firstname&gt;Roy&lt;/bus_contact_firstname&gt;
 *  		&lt;addr_phone_pager/&gt;
 *  		&lt;zip_city&gt;FLOWER MOUND&lt;/zip_city&gt;
 *  		&lt;addr_id&gt;2&lt;/addr_id&gt;
 *  		&lt;addr_phone_ext/&gt;
 *  		&lt;bus_shortname/&gt;
 *  		&lt;addr_phone_main&gt;8888888888&lt;/addr_phone_main&gt;
 *  		&lt;bus_contact_ext/&gt;
 *  		&lt;bus_tax_id&gt;83-0394857&lt;/bus_tax_id&gt;
 *  		&lt;bus_website&gt;www.rmt2.net&lt;/bus_website&gt;
 *  		&lt;addr_zipext/&gt;
 *  	&lt;/vw_business_address&gt;
 *  &lt;/VwBusinessAddressView&gt;
 * </pre>
 * 
 * See {@link com.remoteservices.http.AbstractExternalServerAction AbstractExternalServerAction} 
 * for a detailed description of RSPayload format.
 * <p>
 * @author RTerrell
 * @deprecated Use relevant classes in the com.services package
 *
 */
public class BusinessContactProducer extends AbstractContactProducer {
    private static final String ARG_BUSID = "Arg_BusinessId";

    private static final String ARG_BUSTYPEID = "BusTypeId";

    private static final String ARG_SERVTYPEID = "ServTypeId";

    private static final String ARG_NAME = "Arg_Name";

    private static final String ARG_TAXID = "Arg_TaxId";

    private static final String ARG_PHONE = "Arg_PhoneMain";

    private static final String SRVC_BUS = "Services.getBusiness";

    private static final String SRVC_BUS_BY_ID = "Services.getBusById";

    private static final String SRVC_BUSADDR_BY_ID = "Services.getBusAddrById";

    private static final String SRVC_BUS_BY_NAME = "Services.getBusByName";

    private static final String SRVC_BUS_BY_BUSTYPE = "Services.getBusByBusType";

    private static final String SRVC_BUS_BY_SERVTYPE = "Services.getBusByServType";

    private static final String SRVC_BUS_BY_TAXID = "Services.getBusByTaxId";

    private static final String SRVC_MAINT = "Services.maintainBusiness";

    private static final String SRVC_DELETE = "Services.deleteBusiness";

    private Logger logger;

    /**
     * Default constructor.
     *  
     * @throws SystemException
     */
    public BusinessContactProducer() throws SystemException {
	super();
	this.logger = Logger.getLogger("BusinessContactService");
    }

    /**
     * Processes the user's request to retrieve business contact data filtered by 
     * business id, business name, tax id, business type id, or service type id.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
	String temp;
	String xml = null;

	Properties prop = (Properties) this.inData;

	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_BUS)) {
	    xml = this.getBusiness(prop);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_BUS_BY_ID)) {
	    temp = prop.getProperty(BusinessContactProducer.ARG_BUSID);
	    xml = this.getBusinessById(temp);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_BUSADDR_BY_ID)) {
	    xml = this.getBusinessAddressById(prop);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_BUS_BY_NAME)) {
	    temp = prop.getProperty(BusinessContactProducer.ARG_NAME);
	    xml = this.getBusinessByName(temp);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_BUS_BY_BUSTYPE)) {
	    temp = prop.getProperty(BusinessContactProducer.ARG_BUSTYPEID);
	    xml = this.getBusinessByBusType(temp);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_BUS_BY_SERVTYPE)) {
	    temp = prop.getProperty(BusinessContactProducer.ARG_SERVTYPEID);
	    xml = this.getBusinessByServType(temp);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_BUS_BY_TAXID)) {
	    temp = prop.getProperty(BusinessContactProducer.ARG_TAXID);
	    xml = this.getBusinessByTaxId(temp);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_MAINT)) {
	    xml = this.maintainContact(prop);
	}
	if (this.command.equalsIgnoreCase(BusinessContactProducer.SRVC_DELETE)) {
	    xml = this.deleteContact(prop);
	}
	this.outData = xml;
    }

    /**
     * Retrieve business address contact using longname, tax id, and/or main phone number as 
     * parameters for the service call.
     * 
     * @param props 
     *          A collection of properties which should contain values for the following keys: 
     *          Name, TaxId, and PhoneMain.
     * @return XML document
     * @throws ActionHandlerException  Genereal errors
     */
    protected String getBusiness( Properties props) throws ActionHandlerException {
	Object xml;
	StringBuffer sql = new StringBuffer();

	//  Attempt to add parmeters only if available.
	if (props != null || !props.isEmpty()) {
	    String value;
            value = props.getProperty(BusinessContactProducer.ARG_NAME);
            if (value != null) {
                sql.append("bus_longname like \'");
                sql.append(value);
                sql.append("%\'");
            }
            
            value = props.getProperty(BusinessContactProducer.ARG_TAXID);
            if (value != null) {
                if (sql.length() > 0) {
            	sql.append(" and ");
                }
                sql.append("bus_tax_id like \'");
                sql.append(value);
                sql.append("%\'");
            }
            
            value = props.getProperty(BusinessContactProducer.ARG_PHONE);
            if (value != null) {
                if (sql.length() > 0) {
            	sql.append(" and ");
                }
                sql.append("addr_phone_main like \'");
                sql.append(value);
                sql.append("%\'");
            }
	}

	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xml = api.findContact(sql.toString());
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
     * Retrieves business contact data by business id.
     * 
     * @param api The business api instance.
     * @param uidStr the business id.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getBusinessById(String uidStr) throws ActionHandlerException {
	Object xml;
	int uid = 0;
	if (uidStr == null) {
	    throw new ActionHandlerException("Business Id is required for service, " + this.command);
	}
	try {
	    uid = Integer.parseInt(uidStr);
	}
	catch (NumberFormatException e) {
	    this.msg = "Service call failed: " + this.command + ".  An invalid Business Id was passed: " + uidStr;
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
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
     * Retrieves a business and its address data using the internal business id.
     * 
     * @param api The business api that contains the service producer.
     * @param props Properties instance containing the business id(s).
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getBusinessAddressById(Properties props) throws ActionHandlerException {
        Object xml;
        if (props == null || props.isEmpty()) {
            throw new ActionHandlerException("The input data store is invalid for service, " + this.command);
        }
        String busIdList = props.getProperty(BusinessContactProducer.ARG_BUSID);
        if (busIdList == null) {
            throw new ActionHandlerException("Business Id is required for service, " + this.command);
        }
        
        DatabaseTransApi tx = DatabaseTransFactory.create();
    	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            xml = api.findByBusinessId(busIdList);
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
     * Retrieves business contact data using business name.
     * 
     * @param api The business api instance.
     * @param busName The name of the business.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getBusinessByName(String busName) throws ActionHandlerException {
	Object xml;
	if (busName == null) {
	    throw new ActionHandlerException("Business name is required for service, " + this.command);
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xml = api.findBusByLongName(busName);
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
     * Retreives business contact data using business type id.
     * 
     * @param api The business api instance.
     * @param busTypeIdStr The business type id.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getBusinessByBusType(String busTypeIdStr) throws ActionHandlerException {
	Object xml;
	if (busTypeIdStr == null) {
	    throw new ActionHandlerException("Business Type Id is required for service, " + this.command);
	}
	int busTypeId = 0;
	try {
	    busTypeId = Integer.parseInt(busTypeIdStr);
	}
	catch (NumberFormatException e) {
	    this.msg = "Service call failed: " + this.command + ".  An invalid Business Type Id was passed: " + busTypeIdStr;
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xml = api.findBusByBusType(busTypeId);
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
     * Retreives business contact data using business service type id.
     * 
     * @param api The business api instance.
     * @param servTypeIdStr The business service type id.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getBusinessByServType(String servTypeIdStr) throws ActionHandlerException {
	Object xml;
	if (servTypeIdStr == null) {
	    throw new ActionHandlerException("Business Service Type Id is required for service, " + this.command);
	}
	int servTypeId = 0;
	try {
	    servTypeId = Integer.parseInt(servTypeIdStr);
	}
	catch (NumberFormatException e) {
	    logger.log(Level.ERROR, "Service call failed: " + this.command + ".  An invalid Business Service Type Id was passed: " + servTypeIdStr);
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xml = api.findBusByServType(servTypeId);
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
     * Retreives business contact data using tax id.
     * 
     * @param api The business api instance.
     * @param taxId The tax id of the business.
     * @return XML String.
     * @throws ActionHandlerException
     */
    protected String getBusinessByTaxId(String taxId) throws ActionHandlerException {
	Object xml;
	if (taxId == null) {
	    throw new ActionHandlerException("Business Tax Id is required for service, " + this.command);
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xml = api.findBusByTaxId(taxId);
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
     * This service is responsible for creating and updating business contacts.
     * If Business id is less than or equal to zero then a business profile will
     * be created. Otherwise, update the database with business contact changes.
     * <p>
     * Upon successful completion of this service call, the transaction is either 
     * committed or rollbacked from within this method call since the client does 
     * not have any control over the database connection obtained for this application 
     * context, the results are returned to the caller as an element of <i>data</i> 
     * within the RSPayload XML document.
     * <p>  
     * The data layout of the results goes as follows:
     * <pre>
     *    &lt;BusinessId&gt;3212&lt;/BusinessId&gt;
     *    &lt;AddressId&gt;84328&lt;/AddressId&gt;
     *    &lt;RowCount&gt;2&lt;/RowCount&gt;
     *    &lt;MaintType&gt;insert&lt;/MaintType&gt;
     * </pre>
     * <ol>
     *   <li><b>PersonId</b> - The internal id of the business entity processed.</li>
     *   <li><b>AddressId</b> - The internal id of the business' address entity processed.</li>
     *   <li><b>RowCount</b> - The total number of rows effected by the transaction.</li>
     *   <li><b>MaintType</b> - Indicates whether an insert or an update was performed to maintain the contact.</li>
     * </ol>
     * 
     * @param api {@link com.api.business.BusinessApi BusinessApi}
     * @param prop Proerties collecton containing request data.
     * @return XML document as the results of the service call.
     * @throws ActionHandlerException 
     *            When the business id and/or the address id input is not found or 
     *            when business api produces an error when maintaining the contact. 
     */
    protected String maintainContact(Properties prop) throws ActionHandlerException {
	Business bus = BusinessFactory.createBusiness();
	Address addr = AddressFactory.createAddress();
	boolean newRec = false;

	try {
	    String temp;
	    int id;
	    DataSourceFactory.packageBean(prop, bus);
	    DataSourceFactory.packageBean(prop, addr);

	    // Ensure that the internal id's for the business 
	    // and address objects are set from the request.
	    try {
		temp = prop.getProperty("BusinessId2");
		if (temp != null) {
		    id = Integer.parseInt(temp);
		}
		else {
		    id = 0;
		}
		newRec = (id <= 0);
		bus.setBusinessId(id);
	    }
	    catch (NumberFormatException e) {
		throw new ActionHandlerException("A valid business id must be included as an input parameter");
	    }

	    try {
		temp = prop.getProperty("AddressId");
		if (temp != null) {
		    id = Integer.parseInt(temp);
		}
		else {
		    id = 0;
		}
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
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	AddressApi addrApi = AddressFactory.createAddressApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int rc1;
	int rc2;
	try {
	    // Insert or update business.
	    rc1 = api.maintainContact(bus);

	    // Ensure that business id is set for the address instance when inserting.
	    addr.setBusinessId(bus.getBusinessId());

	    // Insert of update address
	    rc2 = addrApi.maintainContact(addr);

	    // Setup return message
	    String xml = this.buildUpdateMessage(bus.getBusinessId(), addr.getAddrId(), rc1 + rc2, newRec);
	    tx.commitUOW();

	    // Return results
	    
	    bus = null;
	    addr = null;
	    return xml;
	}
	catch (ContactException e) {
	    tx.rollbackUOW();
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

    /**
     * Deletes a business contact and its address from the database.
     * 
     * @param api 
     *          An instance of {@link com.api.business.BusinessApi BusinessApi}.
     * @param prop 
     *          Properties object containing all the parameters and attributes 
     *          of the user's request.
     * @return The results of the call as a XML document.
     * @throws ActionHandlerException 
     *           Business id does not exist as an input property or its value 
     *           is invalid or general contact api errors.
     */
    protected String deleteContact(Properties prop) throws ActionHandlerException {
	Business bus = BusinessFactory.createBusiness();
	int id;
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessApi api = BusinessFactory.createBusinessXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    String temp;

	    DataSourceFactory.packageBean(prop, bus);

	    // Ensure that the internal id for the business object ise set from the request.
	    try {
		temp = prop.getProperty("BusinessId2");
		id = Integer.parseInt(temp);
		bus.setBusinessId(id);
	    }
	    catch (NumberFormatException e) {
		throw new ActionHandlerException("A valid business id must be included as an input parameter when deleting business contact");
	    }
	    int rows = api.deleteContact(bus);
	    return this.buildUpdateMessage(id, 0, rows, false);
	}
	catch (Exception e) {
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
     * Builds the results of an insert, update, or delete as XML operation which details 
     * the business id, address id, total count of records effected from the transaction, 
     * and whether the target data was new or existing.
     * 
     * @param businessId The contact's business id.
     * @param addressId The contact's address id.
     * @param count The count of rows effected from the transaction
     * @param newRec Is true for insert operations and false for update/delete operations.
     * @return String Xml document.&nbsp;&nbsp;The data layout of the results goes as follows:
     * <pre>
     *     &lt;BusinessId&gt;3212&lt;/BusinessId&gt;
     *     &lt;AddressId&gt;84328&lt;/AddressId&gt;
     *     &lt;RowCount&gt;2&lt;/RowCount&gt;
     *     &lt;MaintType&gt;insert&lt;/MaintType&gt;
     *          <ol>
     *            <li><b>PersonId</b> - The internal id of the business entity processed.</li>
     *            <li><b>AddressId</b> - The internal id of the business' address entity processed.</li>
     *            <li><b>RowCount</b> - The total number of rows effected by the transaction.</li>
     *            <li><b>MaintType</b> - Indicates whether an insert or an update/delete was performed to maintain the contact.</li>
     *          </ol>
     * </pre>
     */
    private String buildUpdateMessage(int businessId, int addressId, int count, boolean newRec) {
	// Setup return message
	StringBuffer xml = new StringBuffer(100);
	xml.append("<Error>");
	xml.append("<BusinessId>");
	xml.append(businessId);
	xml.append("</BusinessId>");
	xml.append("<AddressId>");
	xml.append(addressId);
	xml.append("</AddressId>");
	xml.append("<RowCount>");
	xml.append(count);
	xml.append("</RowCount>");
	xml.append("<MaintType>");
	xml.append((newRec ? "insert" : "update/delete"));
	xml.append("</MaintType>");
	xml.append("</Error>");
	return xml.toString();
    }
}

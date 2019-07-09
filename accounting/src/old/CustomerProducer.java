package com.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Properties;

import com.action.ActionHandlerException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;

import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerException;
import com.gl.customer.CustomerFactory;

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
 * @deprecated use {@link com.services.AccountingServiceAction}
 *
 */
public class CustomerProducer extends AbstractAccountingProducer {
    private static final String ARG_ACCTNO = "AccountNo";

    private static final String ARG_CUSTID = "CustomerId";

    private static final String ARG_CUSTIDLIST = "CustomerIdList";

    private static final String ARG_NAME = "Name";

    private static final String ARG_TAXID = "TaxId";

    private static final String ARG_PHONE = "PhoneMain";

    private static final String SRVC_CUSTOMER = "Services.getCustomer";

    private static final String SRVC_ALLCUSTOMERS = "Services.getAllCustomers";

    private Logger logger;

    /**
     * Default constructor.
     *  
     * @throws SystemException
     */
    public CustomerProducer() throws SystemException {
	super();
	this.logger = Logger.getLogger("CustomerProducer");
	this.logger.log(Level.INFO, "Logger Created");
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

	if (this.command.equalsIgnoreCase(CustomerProducer.SRVC_CUSTOMER)) {
	    xml = this.getCustomer(prop);
	}
	if (this.command.equalsIgnoreCase(CustomerProducer.SRVC_ALLCUSTOMERS)) {
	    xml = this.getAllCustomers(prop);
	}

	this.outData = xml;
    }

    /**
     * Retrieve customer/business contact data using either business contact criteria 
     * such as longname, tax id, and/or main phone number of customer criteria such as 
     * account number, customer id, or a list of customer id's. 
     * 
     * @param props 
     *          A collection of properties which should contain values for the following keys: 
     *          AccountNo, CustomerId, CustomerIdList, Name, TaxId, and PhoneMain.
     * @return XML document
     * @throws ActionHandlerException  Genereal errors
     */
    protected String getCustomer(Properties props) throws ActionHandlerException {
	Object xml;
	StringBuffer sql = new StringBuffer();

	//  Attempt to add parmeters only if available.
	if (props != null || !props.isEmpty()) {
	    Iterator iter = props.keySet().iterator();
	    while (iter.hasNext()) {
		String key = iter.next().toString();
		if (key.equals(CustomerProducer.ARG_NAME) || key.equals(CustomerProducer.ARG_TAXID) || key.equals(CustomerProducer.ARG_PHONE)
			|| key.equals(CustomerProducer.ARG_ACCTNO) || key.equals(CustomerProducer.ARG_CUSTID) || key.equals(CustomerProducer.ARG_CUSTIDLIST)) {

		    String value = props.getProperty(key);
		    if (sql.length() > 0) {
			sql.append(" and ");
		    }
		    sql.append("Arg_" + key);
		    sql.append(" = ");
		    sql.append(value);
		}
	    }
	}

	DatabaseTransApi tx = DatabaseTransFactory.create();
	CustomerApi api = CustomerFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xml = api.findCustomerBusiness(sql.toString());
	    if (xml != null) {
		return xml.toString();
	    }
	    return null;
	}
	catch (CustomerException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Retrieves all customer/business contacts. 
     * 
     * @param props Not used
     * @return XML document
     * @throws ActionHandlerException  Genereal errors
     */
    protected String getAllCustomers(Properties props) throws ActionHandlerException {
	Object xml;
	StringBuffer sql = new StringBuffer();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CustomerApi api = CustomerFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xml = api.findCustomerBusiness(sql.toString());
	    if (xml != null) {
		return xml.toString();
	    }
	    return null;
	}
	catch (CustomerException e) {
	    throw new ActionHandlerException(e);
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
    protected String buildUpdateMessage(int customerId, int count, boolean newRec) {
	// Setup return message
	StringBuffer xml = new StringBuffer(100);
	xml.append("<Error>");
	xml.append("<CustomerId>");
	xml.append(customerId);
	xml.append("</CustomerId>");
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

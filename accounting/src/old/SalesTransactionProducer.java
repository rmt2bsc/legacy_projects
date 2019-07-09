package com.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.action.ActionHandlerException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.xml.adapters.XmlAdapterFactory;
import com.api.xml.adapters.XmlBeanAdapter;
import com.bean.Customer;
import com.bean.SalesOrder;
import com.bean.SalesOrderExt;
import com.bean.Xact;
import com.bean.db.DatabaseConnectionBean;

import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerFactory;

import com.remoteservices.http.AbstractExternalServerAction;
import com.remoteservices.http.MessageFactory;
import com.remoteservices.http.ServiceReturnCode;
import com.util.SystemException;
import com.xact.XactFactory;
import com.xact.sales.SalesFactory;
import com.xact.sales.SalesOrderApi;
import com.xact.sales.SalesOrderException;

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
 * @deprecated use {@link com.services.SalesInvoiceService}
 *
 */
public class SalesTransactionProducer extends AbstractAccountingProducer {
    private static final String SRVC_CREATE = "Services.createSalesOrder";

    private static final String SRVC_INVOICE = "Services.invoiceSalesOrder";

    private static final String SRVC_INVOICENEWSALESORDER = "Services.invoiceNewSalesOrder";

    private static Logger logger;

    /**
     * Default constructor.
     *  
     * @throws SystemException
     */
    public SalesTransactionProducer() throws SystemException {
	super();
	SalesTransactionProducer.logger = Logger.getLogger(SalesTransactionProducer.class);
	SalesTransactionProducer.logger.log(Level.INFO, "Logger Created");
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

	if (this.command.equalsIgnoreCase(SalesTransactionProducer.SRVC_CREATE)) {
	    xml = this.createSalesOrder(prop);
	}
	if (this.command.equalsIgnoreCase(SalesTransactionProducer.SRVC_INVOICE)) {
	    xml = this.invoiceSalesOrder(prop);
	}
	if (this.command.equalsIgnoreCase(SalesTransactionProducer.SRVC_INVOICENEWSALESORDER)) {
	    xml = this.invoiceNewSalesOrder(prop);
	}

	this.outData = xml;
    }

    /**
     * Creates a sales order using data from a remote client application. 
     * 
     * @param props 
     *          A collection of properties which should contain values for the following keys: 
     *          AccountNo, CustomerId, CustomerIdList, Name, TaxId, and PhoneMain.
     * @return String 
     *          XML document
     */
    protected String createSalesOrder(Properties props) {
	String payLoad = props.getProperty(SalesTransactionProducer.ARG_PAYLOAD);
	XmlBeanAdapter util = XmlAdapterFactory.createNativeAdapter();
	SalesOrderExt soExt = null;

	try {
	    Object obj = util.xmlToBean(payLoad);
	    if (obj instanceof SalesOrderExt) {
		soExt = (SalesOrderExt) obj;
	    }
	    else {
		this.msg = "Service producer failed to create SalesOrderExt object from payload data";
		SalesTransactionProducer.logger.log(Level.ERROR, this.msg);
		return MessageFactory.buildReturnCode(-100, this.msg, null);
	    }
	}
	catch (Exception e) {
	    return MessageFactory.buildReturnCode(-101, e.getMessage(), null);
	}

	SalesOrder so = this.getSalesOrderInstance(soExt);
	Customer cust = this.getCustomerInstance(soExt);
	List items = this.getSalesOrderItemsInstance(soExt);

	DatabaseTransApi tx = DatabaseTransFactory.create();
	SalesOrderApi api = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    int soId = api.maintainSalesOrder(so, cust, items);
	    tx.commitUOW();
	    String rc = MessageFactory.buildReturnCode(soId, "Sales order was created successfully", null);
	    return rc;
	}
	catch (SalesOrderException e) {
	    tx.rollbackUOW();
	    return MessageFactory.buildReturnCode(-102, e.getMessage(), null);
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
     * @param props 
     *           The input arguments needed to make the service call.
     * @return String 
     *           XML document representing the service's return message.  The return 
     *           code of the message will represent the invoice number that was assigned 
     *           to the sales order.
     * @throws ActionHandlerException  Genereal errors
     */
    protected String invoiceSalesOrder(Properties props) {
	String payLoad = props.getProperty(SalesTransactionProducer.ARG_PAYLOAD);
	XmlBeanAdapter util = XmlAdapterFactory.createNativeAdapter();
	SalesOrderExt soExt = null;
	try {
	    Object obj = util.xmlToBean(payLoad);
	    if (obj instanceof SalesOrderExt) {
		soExt = (SalesOrderExt) obj;
	    }
	    else {
		this.msg = "Service producer failed to create SalesOrderExt object from payload data";
		SalesTransactionProducer.logger.log(Level.ERROR, this.msg);
		return MessageFactory.buildReturnCode(-100, this.msg, null);
	    }
	}
	catch (Exception e) {
	    return MessageFactory.buildReturnCode(-101, e.getMessage(), null);
	}

	SalesOrder so = this.getSalesOrderInstance(soExt);
	Xact xact = this.getXactInstance(soExt);

	DatabaseTransApi tx = DatabaseTransFactory.create();
	SalesOrderApi api = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    so = (SalesOrder) api.findSalesOrderById(so.getSoId());
	    int invId = api.invoiceSalesOrder(so, xact);
	    String xactXml = xact.toXml();
	    String rc = MessageFactory.buildReturnCode(invId, "Sales order was invoiced successfully", xactXml);
	    tx.commitUOW();
	    return rc;
	}
	catch (SalesOrderException e) {
	    tx.rollbackUOW();
	    return MessageFactory.buildReturnCode(-103, e.getMessage(), null);
	}
    }

    protected String invoiceNewSalesOrder(Properties props) {
	String payLoad = props.getProperty(SalesTransactionProducer.ARG_PAYLOAD);
	XmlBeanAdapter util = XmlAdapterFactory.createNativeAdapter();
	SalesOrderExt soExt = null;

	try {
	    Object obj = util.xmlToBean(payLoad);
	    if (obj instanceof SalesOrderExt) {
		soExt = (SalesOrderExt) obj;
	    }
	    else {
		this.msg = "Service producer failed to create SalesOrderExt object from payload data";
		SalesTransactionProducer.logger.log(Level.ERROR, this.msg);
		return MessageFactory.buildReturnCode(-100, this.msg, null);
	    }
	}
	catch (Exception e) {
	    return MessageFactory.buildReturnCode(-105, e.getMessage(), null);
	}

	try {
	    String results = this.createSalesOrder(props);
	    ServiceReturnCode rc = MessageFactory.getResults(results);
	    soExt.setSalesOrderId(rc.getCode());
	    String revisedXml = soExt.toXml();
	    props.setProperty(AbstractExternalServerAction.ARG_PAYLOAD, revisedXml);
	    results = this.invoiceSalesOrder(props);
	    return results;
	}
	catch (Exception e) {
	    return MessageFactory.buildReturnCode(-106, e.getMessage(), null);
	}
    }

    private SalesOrder getSalesOrderInstance(SalesOrderExt data) {
	SalesOrder so = SalesFactory.createSalesOrder();
	so.setSoId(data.getSalesOrderId());
	so.setCustomerId(data.getCustomerId());
	so.setInvoiced(data.getInvoiced());
	so.setOrderTotal(data.getOrderTotal());
	return so;
    }

    private Customer getCustomerInstance(SalesOrderExt data) {
	Customer cust = CustomerFactory.createCustomer();
	cust.setCustomerId(data.getCustomerId());
	return cust;
    }

    private List getSalesOrderItemsInstance(SalesOrderExt data) {
	List items = new ArrayList();
	items.addAll(data.getItems());
	return items;
    }

    private Xact getXactInstance(SalesOrderExt data) {
	Xact xact = XactFactory.createXact();
	xact.setReason(data.getReason());
	return xact;
    }

}

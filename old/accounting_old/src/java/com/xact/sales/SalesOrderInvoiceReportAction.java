package com.xact.sales;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.AbstractReportAction;
import com.action.ActionHandlerException;

import com.api.DataSourceApi;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceFactory;

import com.bean.RMT2TagQueryBean;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;

import com.gl.customer.CustomerException;
import com.gl.customer.CustomerFactory;
import com.gl.customer.CustomerHelper;

import com.util.HostCompanyManager;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * Action handler for generating a sales order invoice report.
 *
 * @author rterrell
 *
 */
public class SalesOrderInvoiceReportAction extends AbstractReportAction {
    /** The name of the field that represents customer id input control on the client */
    protected static final String ATTRIB_CUSTOMERID = "CustomerId";

    /** The name of the field that represents sales order id input control on the client */
    protected static final String ATTRIB_ORDERID = "OrderId";

    /** The name of the field that represents transaction id input control on the client */
    protected static final String ATTRIB_XACTID = "XactId";

    private Logger logger;

    /**
     * Default constructor.
     */
    public SalesOrderInvoiceReportAction() {
	super();
    }

    /**
     * Constructor responsible for initializing this object's servlet context and Http 
     * Request objects
     *
     * @param _context The servlet context passed by the servlet
     * @param _request The Http Servle Request object passed by the servlet
     * @throws SystemException
     */
    public SalesOrderInvoiceReportAction(Context _context, Request _request) throws SystemException {
	this();
	this.init(_context, _request);
    }

    /**
     * Initializes this object using _conext and _request.
     *
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	logger = Logger.getLogger("SalesOrderInvoiceReportAction");
    }

    /**
     * Obtains the customer id, order id, order status id, and transaction id from the 
     * request and stores these values to the session object as key values.  This method 
     * possesses the functionality to obtain these values from a list of records or from a 
     * single record.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	String temp;
	query.clearAllKeyValues();
	try {
	    temp = this.getInputValue(SalesOrderInvoiceReportAction.ATTRIB_CUSTOMERID, null);
	    query.addKeyValues(SalesOrderInvoiceReportAction.ATTRIB_CUSTOMERID, temp);
	    temp = this.getInputValue(SalesOrderInvoiceReportAction.ATTRIB_ORDERID, null);
	    query.addKeyValues(SalesOrderInvoiceReportAction.ATTRIB_ORDERID, temp);
	    temp = this.getInputValue(SalesOrderInvoiceReportAction.ATTRIB_XACTID, null);
	    query.addKeyValues(SalesOrderInvoiceReportAction.ATTRIB_XACTID, temp);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	return;
    }

    /**
     * Prepare the environment for executing the Sales Invoice report and obtain 
     * all data that is required to be displayed on the report.  The procees includes 
     * creating and/or identifying the user's profile work area, identifying the input 
     * and expected output data files, and gathering the report data needed to build 
     * the report's xml data file. This method is call prior to the actual generation 
     * of the report.
     *  
     * @return Sales invoice data as XML
     * @throws ActionHandlerException
     */
    protected String initializeReport() throws ActionHandlerException {
	String temp = null;
	int customerId;
	int orderId;
	int xactId;

	// Copy report input layout file to user's profile work area
	try {
	    this.setupReportLayout();
	}
	catch (SystemException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	//  Recover data fro mthe session
	RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.REPORT_BEAN);
	temp = (String) query.getKeyValues(SalesOrderInvoiceReportAction.ATTRIB_CUSTOMERID);
	customerId = Integer.parseInt(temp);
	temp = (String) query.getKeyValues(SalesOrderInvoiceReportAction.ATTRIB_ORDERID);
	orderId = Integer.parseInt(temp);
	temp = (String) query.getKeyValues(SalesOrderInvoiceReportAction.ATTRIB_XACTID);
	xactId = Integer.parseInt(temp);

	// Get Company Service provider data
	String data = this.fetchHostCompany(HostCompanyManager.getInstance());
	// Get customer data
	data += this.fetchCustomer(customerId);
	// Get Sales Order Invoice data
	data += this.fetchSalesOrder(orderId);
	// Get Transaction data
	data += this.fetchTransaction(xactId);
	// Get Sales order items
	data += this.fetchSalesOrderItems(orderId);
	return data;
    }

    /**
     * Generates the Sales Invoice Report.  The report layout, SalesOrderInvoiceReport.xsl, 
     * is transformed into the XSL-FO file which in turn will serve as input to the XSL-FO 
     * processor to render the Employee Timesheet report as a PDF.  The PDF results are 
     * streamed directly to the client's browser instead of sending an actual data file to 
     * the browser. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	try {
	    // Generate report.
	    RMT2XmlUtility xsl = RMT2XmlUtility.getInstance();
	    xsl.transformXslt(this.xslFileName, this.xmlFileName, this.outFileName);
	    xsl.renderPdf(this.outFileName, this.request, this.response);
	}
	catch (SystemException e) {
	    this.msg = "Error generating report in PDF format: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

    /**
     * Queries the database for customer data and returns the results to the caller 
     * in the form of XML.  The database tables used are sources used are customer, 
     * address, and zipcode.
     * 
     * @param customerId The customer id
     * @return Query results as XML
     * @throws ActionHandlerException General database access errors
     */
    protected String fetchCustomer(int customerId) throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CustomerHelper helper = CustomerFactory.createCustomerHelper(this.request, this.response, (DatabaseConnectionBean) tx.getConnector());
	String xml;
	try {
	    helper.fetchCustomer(customerId);
	    xml = (String) helper.getCustDetail();
	    return xml;
	}
	catch (CustomerException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Queries the database for base sales order data and returns the 
     * results to the caller in the form of XML.   The database table used is 
     * vw_sales_order_invoice.
     *   
     * @param salesOrderId Sales order id
     * @return Query results as XML
     * @throws ActionHandlerException General database access errors
     */
    protected String fetchSalesOrder(int salesOrderId) throws ActionHandlerException {
	StringBuilder query = new StringBuilder(100);
	String data = null;

	// Build query
	query.append("select * from vw_sales_order_invoice sales_invoice ");
	query.append(" where sales_invoice.sales_order_id = ");
	query.append(salesOrderId);

	// Get query results as XML
	DatabaseTransApi tx = DatabaseTransFactory.create();
	DataSourceApi dsApi = DataSourceFactory.create((DatabaseConnectionBean) tx.getConnector());
	try {
	    data = dsApi.executeXmlQuery(query.toString());
	    return data;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    dsApi.close();
	    dsApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Queries the database for all sales order items and returns the 
     * results to the caller in the form of XML. The database view used is  
     * vw_salesorder_items_by_salesorder.
     * 
     * @param salesOrderId The sales order id
     * @return Query results as XML
     * @throws ActionHandlerException General database access errors
     */
    protected String fetchSalesOrderItems(int salesOrderId) throws ActionHandlerException {
	StringBuilder query = new StringBuilder(100);
	String data = null;

	// Build query
	query.append("select sales_order_item_id, ");
	query.append("item_id,  ");
	query.append("creditor_id, ");
	query.append("item_type_id, ");
	query.append("item_name, ");
	query.append("item_name_override, ");
	query.append("vendor_item_no, ");
	query.append("item_serial_no, ");
	query.append("retail_price, ");
	query.append("retail_price * order_qty invoice_amount, ");
	query.append("order_qty, ");
	query.append("back_order_qty ");
	query.append(" from vw_salesorder_items_by_salesorder salesorder_items ");
	query.append(" where salesorder_items.so_id = ");
	query.append(salesOrderId);

	// Get query results as XML
	DatabaseTransApi tx = DatabaseTransFactory.create();
	DataSourceApi dsApi = DataSourceFactory.create((DatabaseConnectionBean) tx.getConnector());
	try {
	    data = dsApi.executeXmlQuery(query.toString());
	    return data;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    dsApi.close();
	    dsApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Queries the database for sales order transaction data and returns the results to 
     * the caller in the form of XML.   The database table used is xact.  
     * 
     * @param id The transaction id
     * @return Query results as XML
     * @throws ActionHandlerException General database access errors
     */
    protected String fetchTransaction(int xactId) throws ActionHandlerException {
	StringBuilder query = new StringBuilder(100);
	String data = null;

	// Build query
	query.append("select * from xact where xact_id = ");
	query.append(xactId);

	// Get query results as XML
	DatabaseTransApi tx = DatabaseTransFactory.create();
	DataSourceApi dsApi = DataSourceFactory.create((DatabaseConnectionBean) tx.getConnector());
	try {
	    data = dsApi.executeXmlQuery(query.toString());
	    return data;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    dsApi.close();
	    dsApi = null;
	    tx.close();
	    tx = null;
	}
    }
}
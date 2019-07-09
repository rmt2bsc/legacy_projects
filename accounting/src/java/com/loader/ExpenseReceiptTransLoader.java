package com.loader;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.api.security.authentication.AuthenticationConst;
import com.api.security.pool.AppPropertyPool;

import com.bean.Creditor;
import com.bean.Customer;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.Xact;
import com.bean.XactTypeItemActivity;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorConst;
import com.gl.creditor.CreditorException;
import com.gl.creditor.CreditorExt;
import com.gl.creditor.CreditorFactory;
import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerException;
import com.gl.customer.CustomerExt;
import com.gl.customer.CustomerFactory;

import com.loader.transactions.LoaderConst;
import com.loader.transactions.LoaderTrans;
import com.loader.transactions.LoaderTransType;

//import com.remoteservices.RemoteServicesConsumer;
//import com.remoteservices.ServiceConsumer;

import com.util.NotFoundException;
import com.util.RMT2Date;
import com.util.RMT2Exception;
import com.util.RMT2String;
import com.util.SystemException;
import com.util.RMT2SaxDocBase;

import com.xact.XactConst;
import com.xact.XactFactory;

import com.xact.disbursements.CashDisburseFactory;
import com.xact.disbursements.CashDisbursementsApi;
import com.xact.disbursements.CashDisbursementsException;
import com.xact.receipts.CashReceiptsApi;
import com.xact.receipts.CashReceiptsException;
import com.xact.receipts.ReceiptsFactory;

import com.xact.sales.SalesFactory;
import com.xact.sales.SalesOrderApi;
import com.xact.sales.SalesOrderException;
import com.xact.sales.SalesOrderItemHelper;

/**
 * This class builds a Map of {@link com.loader.LoaderTransType LoaderTransType} objects, 
 * which is keyed by LoaderTransType.name, by way of parsing an XML document using SAX 
 * technologies.
 * 
 * @author roy.terrell
 * @deprecated No longer needed since transactions have been migrated.  Will be removed in future versions
 *
 */
public class ExpenseReceiptTransLoader extends RMT2SaxDocBase {

    private Logger logger;

    private String code;

    private Date date;

    private String description;

    private Double amount;

    private int transEvalCount;

    private int colCount;

    private Map<String, LoaderTransType> transType;

    private DatabaseConnectionBean dbCon;

    private Request request;

    private int revenueTot;

    private int expenseTot;

    private int errorCount;

    /**
     * Creates a TransactLegendLoader object with the XML document containing 
     * a master list of transaction types, and subsequently invoke parsing of 
     * the XML document.
     * 
     * @param doc 
     *          The path and file name of the XML document to parse.
     * @throws SystemException 
     *          Parsing errors.
     */
    private ExpenseReceiptTransLoader(String doc) throws SystemException {
	super(doc);
	this.logger = Logger.getLogger("ExpenseReceiptTransLoader");
	this.text = null;
	this.transEvalCount = 0;
	this.colCount = 0;
	this.revenueTot = 0;
	this.expenseTot = 0;
	this.logger.log(Level.INFO, "Transaction loader started: " + new Date().toString());
    }

    /**
     * Created an ExpenseReceiptTransLoader object by intializing it with a database 
     * connection bean, the user's request, and the data file that is to be processed.
     *  
     * @param dbCon
     *          The database connection bean.
     * @param request
     *          The user's request.
     * @param doc
     *          The XML document that contains the revenue and expense transactions.
     * @throws SystemException
     *          Illeagal access or resource problems.
     */
    public ExpenseReceiptTransLoader(DatabaseConnectionBean dbCon, Request request, String doc) throws SystemException {
	this(doc);
	this.dbCon = dbCon;
	this.request = request;
    }

    /**
     * Initializes this instance by loading the transaction legend.
     *
     */
    private void init() {
	String legendFilename = AppPropertyPool.getProperty("XactLoaderLegendFilename");
	if (this.docPath == null) {
	    this.docPath = AppPropertyPool.getProperty("XactLoaderDataPath");
	}
	TransactLegendLoader legendLoad = new TransactLegendLoader(this.docPath + legendFilename);
	legendLoad.parseDocument();
	this.transType = legendLoad.getLegend();
	return;
    }

    /**
     * Obtains the SAX driver needed to parse document
     * 
     * @throws SystemException
     */
    protected void getDocResources() throws SystemException {
	this.init();
	this.docName = AppPropertyPool.getProperty("XactLoaderDataFilename");
	this.saxDriver = AppPropertyPool.getProperty("SAXDriver");
	this.docName = this.docPath + this.docName;
	return;
    }

    /**
     * Renders the total number of legend items loaded..
     * 
     * @throws SAXException
     */
    public void endDocument() throws SAXException {
	super.endDocument();
    }

    /**
     * Relies on the ancestor logic.
     * 
     * @param elementName the element to process
     * @param amap the attribute list data values for the elementName
     * @throws SAXException
     */
    public void startElement(String elementName, AttributeList amap) throws SAXException {
	super.startElement(elementName, amap);
    }

    /**
     * Creates a LoaderTransType instance from the element values <i>name</i> and 
     * <i>code</i> member variables and adds the instance to a Map collection key 
     * by <i>name</i>.  
     * 
     * @param elementName 
     *          the element to process which includes only <i>name</i> and <i>code</i>.
     * @throws SAXException
     */
    public void endElement(String elementName) throws SAXException {
	super.endElement(elementName);

	if (elementName.equalsIgnoreCase("code")) {
	    this.code = this.text;
	    this.colCount++;
	}

	if (elementName.equalsIgnoreCase("description")) {
	    this.description = this.text;
	    this.colCount++;
	}

	if (elementName.equalsIgnoreCase("date")) {
	    try {
		this.date = RMT2Date.stringToDate(this.text);
	    }
	    catch (Exception e) {
		this.date = null;
	    }
	    this.colCount++;
	}

	if (elementName.equalsIgnoreCase("amount")) {
	    try {
		this.amount = Double.parseDouble(this.text);
	    }
	    catch (NumberFormatException e) {
		this.amount = null;
	    }
	    this.colCount++;
	}

	if (this.colCount == 4) {
	    LoaderTrans trans = new LoaderTrans();

	    trans.setCode(this.code.trim());
	    trans.setDate(this.date);
	    trans.setDescription(this.description.trim());
	    trans.setAmount(this.amount);

	    try {
		this.processTransaction(trans);
		this.dbCon.getDbConn().commit();
	    }
	    catch (Exception e) {
		this.logger.log(Level.ERROR, e.getMessage());
		this.errorCount++;
		try {
		    this.dbCon.getDbConn().rollback();
		}
		catch (SQLException e1) {
		    e1.printStackTrace();
		}
	    }
	    finally {
		trans = null;
	    }

	    this.colCount = 0;
	}

    }

    /**
     * Processes both revenue and expense transactions.
     * 
     * @param trans
     *          The tarnasction object to process
     * @throws SystemException
     */
    private void processTransaction(LoaderTrans trans) throws TransactionLoaderException {
	String confirm = trans.getCode() + ", " + trans.getDate().toString() + ", " + trans.getDescription() + ", " + trans.getAmount();
	this.logger.log(Level.INFO, "Processing Transaction..." + confirm);

	String errorSuffix = " is invalid for transaction: Code=" + trans.getCode() + "Description=" + trans.getDescription();
	if (trans.getAmount() == null) {
	    throw new SystemException("Transaction Amount" + errorSuffix);
	}
	if (trans.getDate() == null) {
	    throw new SystemException("Transaction Date" + errorSuffix);
	}
	String descr = RMT2String.replaceAll(trans.getDescription(), "''", "'");
	trans.setDescription(descr);

	this.transEvalCount++;

	// Determine the type of transaction we are dealing with.
	if (trans.getCode().trim().equals("R")) {
	    this.createSalesOrderTrans(trans);
	    this.revenueTot++;
	}
	else {
	    this.createExpenseTrans(trans);
	    this.expenseTot++;
	}
	return;
    }

    /**
     * Drives the process of turing a revenue transaction into an invoiced sales order.
     * 
     * @param trans
     *          The source transaction.
     * @return int
     *           The transaction id of the invoiced sales order.
     * @throws DatabaseException
     *           General database errors.
     * @throws SalesOrderException
     *           General sales order errors.
     * @throws CashReceiptsException
     *           General cash receipts errors.
     */
    private int createSalesOrderTrans(LoaderTrans trans) throws TransactionLoaderException {
	// Identify existing or create new customer
	Customer cust = CustomerFactory.createCustomer();
	try {
	    try {
		CustomerExt custExt = this.getCustomer(trans.getDescription().trim());
		cust.setCustomerId(custExt.getCustomerId());
		cust.setAccountNo(custExt.getAccountNo());
		cust.setBusinessId(custExt.getBusinessId());
		cust.setAcctId(custExt.getGlAccountId());
		custExt = null;
	    }
	    catch (NotFoundException e) {
		cust = this.createCustomer(trans.getDescription());
	    }
	}
	catch (Exception e) {
	    throw new SystemException(e);
	}

	SalesOrderApi salesApi = null;
	CashReceiptsApi cashApi = null;
	SalesOrder so = null;
	List items = null;
	SalesOrderItems item = null;
	Xact xact = null;
	try {
	    // Create sales order
	    so = SalesFactory.createSalesOrder();
	    so.setCustomerId(cust.getCustomerId());
	    so.setOrderTotal(trans.getAmount());
	    so.setDateCreated(trans.getDate());
	    salesApi = SalesFactory.createApi(this.dbCon, this.request);

	    items = new ArrayList<SalesOrderItems>();
	    item = SalesFactory.createSalesOrderItem();
	    item.setItemId(SalesOrderItemHelper.SERVICE_ITEM_ID);
	    item.setOrderQty(1);
	    item.setInitMarkup(1);
	    item.setInitUnitCost(trans.getAmount());
	    items.add(item);
	    int salesOrderId = salesApi.maintainSalesOrder(so, cust, items);
	    so.setSoId(salesOrderId);

	    // Invoice sales order
	    xact = XactFactory.createXact();
	    xact.setXactDate(trans.getDate());
	    xact.setXactAmount(trans.getAmount());
	    xact.setReason(trans.getDescription());
	    int invId = salesApi.invoiceSalesOrder(so, xact);

	    // Create sales receipt 
	    xact.setXactId(0);
	    xact.setXactDate(trans.getDate());
	    xact.setXactAmount(trans.getAmount());
	    xact.setReason("Payment on invoice " + invId + " - " + trans.getDescription());
	    cashApi = ReceiptsFactory.createCashReceiptsApi(this.dbCon, this.request);
	    int xactid = cashApi.maintainCustomerPayment(xact, cust.getCustomerId());

	    return xactid;
	}
	catch (SalesOrderException e) {
	    throw new TransactionLoaderException(e.getMessage(), -101);
	}
	catch (CashReceiptsException e) {
	    throw new TransactionLoaderException(e.getMessage(), -201);
	}
	finally {
	    salesApi.close();
	    cashApi.close();
	    salesApi = null;
	    cashApi = null;
	    cust = null;
	    so = null;
	    items = null;
	    item = null;
	    xact = null;
	}
    }

    /**
     * Attempts to find customer profile by customer name, if it exist.
     * 
     * @param custName
     *          The name of the custome to find.
     * @return {@link com.bean.CustomerExt CustomerExt}
     *          Customer's profile include business contact and address 
     *          information.
     * @throws CustomerException
     *          Problem fetching customer's pofile or if more than one 
     *          profile was discovered.
     * @throws NotFoundException
     *          Customer's profile was not found using the customer's 
     *          name.
     */
    private CustomerExt getCustomer(String custName) throws CustomerException, NotFoundException {
	CustomerApi api = CustomerFactory.createApi(this.dbCon, this.request);
	List cust = null;
	try {
	    cust = (List) api.findCustomerBusiness("Arg_Name = " + custName);
	}
	catch (Exception e) {
	    throw new CustomerException("Fetch error regarding customer, " + custName + ".  Additional information: " + e.getMessage());
	}
	finally {
	    api.close();
	    api = null;
	}

	if (cust.size() > 1) {
	    throw new CustomerException("Found more the one customer with name, " + custName);
	}
	else {
	    if (cust.size() == 0) {
		throw new NotFoundException(custName + "was not found...will have to create");
	    }
	}
	return (CustomerExt) cust.get(0);
    }

    /**
     * Creates a customer profile in the contacts application context as well in 
     * this (accounting) context.  The only piece of contact information recorded 
     * in the contact application will be the customer's actual business name.  
     * 
     * @param custName
     *          the customer's business name.
     * @return {@link com.bean.Customer Customer}
     *          the customer profile from this (accounting) context.
     * @throws CustomerException    
     *          Prolem occurred creating busines contact profile in contacts 
     *          application.
     * @throws TransactionLoaderException
     *          Customer could not be created within local application context.
     *          
     */
    private Customer createCustomer(String custName) throws CustomerException, TransactionLoaderException {
	int businessId = this.createBusinessContact(custName);
	Customer cust = CustomerFactory.createCustomer();
	cust.setBusinessId(businessId);
	CustomerApi custApi = null;
	try {
	    custApi = CustomerFactory.createApi(this.dbCon, this.request);
	    custApi.maintainCustomer(cust, null);
	}
	catch (Exception e) {
	    throw new TransactionLoaderException(e);
	}
	finally {
	    custApi.close();
	    custApi = null;
	}
	return cust;
    }

    /**
     * Drives the process of creating expense transactions whether general or 
     * creditor in nature.  The following transaction types will need to be 
     * associated with creditor profiles: Car Leases, Computer Leases, Telephone 
     * Land, Telephone Mobile, Internet, Utilities Gas, Utilities Electric, and 
     * Utilities Water.
     *  
     * @param trans
     *          The source transaction.
     * @return int
     *          the transaction id.
     */
    private int createExpenseTrans(LoaderTrans trans) throws TransactionLoaderException {
	int xactId;

	if (trans.getCode().equals("CL") || trans.getCode().equals("CE") || trans.getCode().equals("TL") || trans.getCode().equals("TM") || trans.getCode().equals("ISP")
		|| trans.getCode().equals("UE") || trans.getCode().equals("UG") || trans.getCode().equals("UW")) {
	    xactId = this.createCreditorExpense(trans);
	}
	else {
	    xactId = this.createGeneralExpense(trans);
	}
	return xactId;
    }

    /**
     * Creates an expense transaction which is generally a payment to a creditor.
     * 
     * @param trans
     *          the source of the transaction.
     * @return
     *          the transaction id.
     * @throws TransactionLoaderException
     *          data access error in fetching the creditor's profile or problem 
     *          trying to create a cash disbursement transaction.
     *          
     */
    private int createCreditorExpense(LoaderTrans trans) throws TransactionLoaderException {
	CreditorExt credExt;

	// Identify existing or create new creditor
	Creditor cred = CreditorFactory.createCreditor();
	try {
	    try {
		credExt = this.getCreditor(trans.getDescription().trim());
		cred.setCreditorId(credExt.getCreditorId());
		cred.setAccountNumber(credExt.getAccountNo());
		cred.setBusinessId(credExt.getBusinessId());
		cred.setAcctId(credExt.getGlAccountId());
	    }
	    catch (NotFoundException e) {
		cred = this.createCreditor(trans.getDescription());
	    }
	}
	catch (CreditorException e) {
	    throw new TransactionLoaderException(e);
	}

	// Create creditor disbursement transaction
	Xact xact = XactFactory.createXact();
	xact.setXactDate(trans.getDate());
	xact.setXactAmount(trans.getAmount());
	xact.setTenderId(XactConst.TENDER_CASH);
	xact.setReason("Paid creditor, " + trans.getDescription());

	CashDisbursementsApi disbApi = CashDisburseFactory.createApi(this.dbCon, this.request);
	try {
	    int xactId = disbApi.maintainCashDisbursement(xact, new ArrayList(), cred.getCreditorId());
	    return xactId;
	}
	catch (CashDisbursementsException e) {
	    throw new TransactionLoaderException(e);
	}
	finally {
	    cred = null;
	    credExt = null;
	    xact = null;
	    disbApi = null;
	}
    }

    /**
     * Creates a general expense transaction.
     * 
     * @param trans
     *          the source of the transaction.
     * @return
     *          the transaction id.
     * @throws TransactionLoaderException
     *          problem creating the cash disbursment transaction.
     */
    private int createGeneralExpense(LoaderTrans trans) throws TransactionLoaderException {
	Xact xact = XactFactory.createXact();
	xact.setXactDate(trans.getDate());
	xact.setXactAmount(trans.getAmount());
	xact.setTenderId(XactConst.TENDER_CASH);
	xact.setReason("Paid for general expense item(s) at, " + trans.getDescription());

	// Create general disbursement 
	CashDisbursementsApi disbApi = CashDisburseFactory.createApi(this.dbCon, this.request);
	try {
	    List items = this.buildExpenseItems(trans);
	    int xactId = disbApi.maintainCashDisbursement(xact, items);
	    items = null;
	    return xactId;
	}
	catch (CashDisbursementsException e) {
	    throw new TransactionLoaderException(e);
	}
	finally {
	    xact = null;
	    disbApi = null;
	}
    }

    /**
     * Creates an expense item from the source expense transaction.
     * 
     * @param trans
     *          the source transaction.
     * @return
     *          List of {@link com.bean.XactTypeItemActivity XactTypeItemActivity}
     *          instances.
     * @throws NotFoundException
     *          if the transaction code does not exist.
     *          
     */
    private List buildExpenseItems(LoaderTrans trans) throws NotFoundException {

	List list = new ArrayList();

	XactTypeItemActivity xtia = XactFactory.createXactTypeItemActivity();
	Integer xactItemTypeId = LoaderConst.EXPITEM_TYPES.get(trans.getCode());
	if (xactItemTypeId == null) {
	    throw new NotFoundException("Problem creating expense transaction item due transaction code not found");
	}
	xtia.setXactItemId(xactItemTypeId.intValue());
	xtia.setAmount(trans.getAmount());
	xtia.setDescription(trans.getDescription());
	xtia.setDateCreated(trans.getDate());

	list.add(xtia);
	return list;
    }

    /**
     * Fetches the creditor's profile by its name.
     * 
     * @param credName
     *          the creditor's business name.
     * @return
     *          {@link com.bean.CreditorExt CreditorExt}
     * @throws CreditorException
     *          General database access errors or if more than one creditor's 
     *          profile was discovered using the creditor's name.
     * @throws NotFoundException
     *          Creditor was not found using <i>credName</i>.
     */
    private CreditorExt getCreditor(String credName) throws CreditorException, NotFoundException {
	CreditorApi api = CreditorFactory.createApi(this.dbCon, this.request);
	List cred = null;
	try {
	    cred = (List) api.findCreditorBusiness("Name = " + credName);
	}
	catch (Exception e) {
	    throw new CreditorException("Data acess error occurred trying to fetch creditor by name, " + credName);
	}
	if (cred.size() > 1) {
	    throw new CreditorException("Found more the one creditor with name, " + credName);
	}
	else {
	    if (cred.size() == 0) {
		throw new NotFoundException(credName + "was not found...will have to create");
	    }
	}
	api = null;
	return (CreditorExt) cred.get(0);
    }

    /**
     * Creates a creditor which includes the business contact information.
     * 
     * @param credName
     *          The creditor's business name.
     * @return
     *          {@link com.bean.Creditor Creditor}
     * @throws CreditorException
     *          creditor could not be created.
     * @throws TransactionLoaderException
     *          Problem occurred trying to create business contact profile.
     */
    private Creditor createCreditor(String credName) throws CreditorException, TransactionLoaderException {
	int businessId = this.createBusinessContact(credName);
	Creditor cred = CreditorFactory.createCreditor();
	cred.setBusinessId(businessId);
	cred.setCreditorTypeId(CreditorConst.CREDITORTYPE_CREDITOR);
	CreditorApi credApi = CreditorFactory.createApi(this.dbCon, this.request);
	credApi.maintainCreditor(cred, null);
	credApi = null;
	return cred;
    }

    /**
     * Added a business contact profile to the contacts system using only the 
     * business entity's name.  As a requirement, the user id and the originating 
     * application name should be included in the user's request.
     * 
     * @param contactName
     *          the name of the business entity.
     * @return
     *          the business id from the contacts system.
     * @throws TransactionLoaderException
     *          business rule was violated as a result of the service call, or 
     *          the service call produced an exception.  
     *          
     */
    private int createBusinessContact(String contactName) throws TransactionLoaderException {
	int businessId;

	// Gather all of the required parameters for the service.
	Properties args = new Properties();
	String loginId = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
	if (loginId == null || loginId.equals("")) {
	    loginId = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_USERID);
	}
	args.setProperty(AuthenticationConst.AUTH_PROP_USERID, loginId);

	String appName = this.request.getParameter(AuthenticationConst.AUTH_PROP_MAINAPP);
	if (appName == null || appName.equals("")) {
	    appName = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_MAINAPP);
	    if (appName == null) {
		try {
		    appName = AppPropertyPool.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
		}
		catch (Exception e) {
		    appName = null;
		}
	    }
	}
	else {
	    args.setProperty(AuthenticationConst.AUTH_PROP_MAINAPP, appName);
	}
	args.setProperty("Longname", contactName);

	// Invoke remote service from the contacts application to persist customer's 
	// business and address object data to the database.	
//	RemoteServicesConsumer srvc = null;
//	Object results = null;
//	try {
//	    srvc = new RemoteServicesConsumer("maintainBusiness", args);
//	    srvc.processRequest();
//	    results = srvc.getServiceResults();
//
//	    // Get the results of the service call.
//	    if (((ServiceConsumer) srvc).isError()) {
//		// Service is capable of returning a error in the form of an expception or as XML.
//		if (results instanceof RMT2Exception) {
//		    throw new TransactionLoaderException((RMT2Exception) results);
//		}
//		((ServiceConsumer) srvc).getXmlResults().retrieve("error");
//		((ServiceConsumer) srvc).getXmlResults().nextRow();
//		this.msg = ((ServiceConsumer) srvc).getXmlResults().getColumnValue("message");
//		this.logger.log(Level.ERROR, this.msg);
//		srvc = null;
//		throw new TransactionLoaderException(this.msg);
//	    }
//	    else {
//		((ServiceConsumer) srvc).getXmlResults().retrieve("Error");
//		((ServiceConsumer) srvc).getXmlResults().nextRow();
//		String temp = ((ServiceConsumer) srvc).getXmlResults().getColumnValue("BusinessId");
//		businessId = Integer.parseInt(temp);
//		return businessId;
//	    }
//	}
//	catch (Exception e) {
//	    srvc.close();
//	    srvc = null;
//	    throw new TransactionLoaderException(e);
//	}
	return 0;
    }

    /**
     * @return the transEvalCount
     */
    public int getTransEvalCount() {
	return transEvalCount;
    }

    /**
     * @return the expenseTot
     */
    public int getExpenseTot() {
	return expenseTot;
    }

    /**
     * @return the revenueTot
     */
    public int getRevenueTot() {
	return revenueTot;
    }

    /**
     * @return the errorCount
     */
    public int getErrorCount() {
	return errorCount;
    }
}

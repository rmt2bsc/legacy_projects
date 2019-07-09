package com.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Customer;
import com.bean.SalesOrder;
import com.bean.Xact;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.customer.CustomerFactory;
import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerException;

import com.util.SystemException;

import com.xact.XactException;
import com.xact.AbstractXactAction;
import com.xact.XactManagerApi;
import com.xact.XactConst;
import com.xact.XactFactory;

/**
 * This class provides action handlers needed to serve the request of the Customer Sales Order Console user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesConsoleAction extends AbstractXactAction {
    private static final String COMMAND_VEIWORDERS = "SalesCustomerConsole.SalesConsole.vieworders";

    private static final String COMMAND_NEWORDER = "SalesCustomerConsole.SalesConsole.neworder";

    private static final String COMMAND_PAYMENT = "SalesCustomerConsole.SalesConsole.acceptpayment";

    private static final String COMMAND_VIEWTRANSACTIONS = "SalesCustomerConsole.SalesConsole.view";

    private static final String COMMAND_BACK = "SalesCustomerConsole.SalesConsole.back";

    private Logger logger;

    private CustomerApi custApi;

    private SalesOrderApi salesApi;

    private XactManagerApi xactApi;

    private Customer cust;

    private Object custExt;

    private List<Object> custOrders;

    private List<Object> payments;

    private SalesOrderItemHelper itemHelper;

    private SalesOrder so;

    private Xact xact;

    /**
     * Default constructor
     *
     */
    public CustomerSalesConsoleAction() {
	super();
	logger = Logger.getLogger(CustomerSalesConsoleAction.class);
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CustomerSalesConsoleAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	logger = Logger.getLogger(CustomerSalesConsoleAction.class);
	this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);

	if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_VEIWORDERS)) {
	    this.getCustomerSalesOrders();
	}
	if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_NEWORDER)) {
	    this.setupNewSalesOrderSelection(0);
	}
	if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_PAYMENT)) {
	    this.acceptPaymentOnAccount();
	}
	if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_VIEWTRANSACTIONS)) {
	    this.getTransactions();
	}
	if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Retreives the customer data from the client's request.
     */
    protected void receiveClientData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.custApi = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Obtain Customer data from the request object.
	    this.cust = this.httpHelper.getHttpCustomer();
	    this.custExt = custApi.findCustomerBusiness(this.cust.getCustomerId());
	    try {
		this.xact = this.httpHelper.getHttpXact();
	    }
	    catch (Exception e) {
		this.xact = XactFactory.createXact();
	    }

	}
	catch (CustomerException e) {
	    logger.log(Level.ERROR, "GLAcctException occurred. " + e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.custApi.close();
	    tx.close();
	    this.custApi = null;
	    tx = null;
	}
    }

    /**
     * Sends the response of the client's request with an ArrayList of  CombinedSalesOrder objects and a 
     * CustomerWithName object which their attriute names are required to be set as "orderhist" and 
     * "customer", respectively.
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(SalesConst.CLIENT_DATA_ORDERLIST, this.custOrders);
	this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT, this.custExt);
	this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.so);
	if (this.itemHelper != null) {
	    this.request.setAttribute(SalesConst.CLIENT_DATA_SERVICE_ITEMS, this.itemHelper.getSrvcItems());
	    this.request.setAttribute(SalesConst.CLIENT_DATA_MERCHANDISE_ITEMS, this.itemHelper.getMerchItems());
	}
	this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.xact);
	this.request.setAttribute(XactConst.CLIENT_DATA_SALESPAYMENT_HIST, this.payments);
    }

    /**
     * Gathers a list of sales orders for the target customer found in the client's request and sends the results back to the client.
     * 
     * @throws ActionHandlerException
     */
    public void getCustomerSalesOrders() throws ActionHandlerException {
	this.receiveClientData();
	this.custOrders = this.getCustomerSalesOrders(this.cust);
	this.sendClientData();
    }

    /* Retrieves the customer's sales order history using _cust.   
     * 
     * @param _cust Customer object
     * @return An ArrayList of CombinedSalesOrder objects
     * @throws SalesOrderException
     */
    protected List<Object> getCustomerSalesOrders(Customer cust) throws ActionHandlerException {
	List<Object> list;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    list = (List<Object>) this.salesApi.findExtendedSalesOrderByCustomer(cust.getCustomerId());
	    return list;
	}
	catch (SalesOrderException e) {
	    logger.log(Level.ERROR, "SalesOrderExeption occurred. " + e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.salesApi.close();
	    tx.close();
	    this.salesApi = null;
	    tx = null;
	}
    }

    /* Retrieves the customer's sales order history using _cust.   
     * 
     * @param _cust Customer object
     * @return An ArrayList of CombinedSalesOrder objects
     * @throws SalesOrderException
     */
    protected List<Object> getCustomerInvoicedSalesOrders(Customer cust) throws ActionHandlerException {
	List<Object> list;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    list = (List<Object>) this.salesApi.findExtendedInvoicedSalesOrderByCustomer(cust.getCustomerId());
	    return list;
	}
	catch (SalesOrderException e) {
	    logger.log(Level.ERROR, "SalesOrderExeption occurred. " + e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.salesApi.close();
	    tx.close();
	    this.salesApi = null;
	    tx = null;
	}
    }

    /**
     * Uses <i>salesOrderId</i> to retrieve the all items that have not been associated 
     * with a sales order.
     * 
     * @param salesOrderId The sales order id
     * @throws ActionHandlerException System or Database errors.
     */
    public void setupNewSalesOrderSelection(int salesOrderId) throws ActionHandlerException {
	this.receiveClientData();
	try {
	    // Create new sales order object.
	    this.so = SalesFactory.createSalesOrder();
	    this.so.setSoId(salesOrderId);
	    // Get inventory items that are available for selection
	    this.itemHelper = new SalesOrderItemHelper(this.context, this.request, this.cust, this.so);
	    this.itemHelper.setupAvailSalesOrderItems();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    this.sendClientData();
	}
    }

    /**
     * Prepares the client for a cash receipt transaction.
     * 
     * @throws ActionHandlerException
     */
    protected void acceptPaymentOnAccount() throws ActionHandlerException {
	this.receiveClientData();
	if (this.xact.getXactId() == 0) {
	    this.xact.setXactTypeId(XactConst.XACT_TYPE_CASHPAY);
	}
	this.calcCustomerBalance();

	this.custOrders = this.getCustomerInvoicedSalesOrders(this.cust);

	this.sendClientData();
    }

    /**
     * Retrieves a customer's transaction history including purchases, payments, and reversals.
     * 
     * @return An array of all the customer's transactions
     * @throws SalesOrderException
     */
    public int getTransactions() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.receiveClientData();
	    this.payments = this.xactApi.findCustomerXactHist(this.cust.getCustomerId());
	    this.calcCustomerBalance();
	    this.sendClientData();
	    return this.payments.size();
	}
	catch (XactException e) {
	    logger.log(Level.ERROR, "XactException occurred. " + e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.xactApi.close();
	    tx.close();
	    this.xactApi = null;
	    tx = null;
	}

    }

    /**
     * Makes call to customer api to calculate and capture customer's balance.   The balance is added to the 
     * sales order member variable.   If sales orer varialbe is invalid it is instantiated.  
     * 
     */
    private void calcCustomerBalance() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.custApi = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    if (this.so == null) {
		// Create new sales order object.
		this.so = SalesFactory.createSalesOrder();
	    }
	    double balance = this.custApi.findCustomerBalance(this.cust.getCustomerId());
	    this.so.setOrderTotal(balance);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    this.custApi.close();
	    tx.close();
	    this.custApi = null;
	    tx = null;
	}
    }

    /**
     * Navigate back to the customer search page.
     *  
     * @throws ActionHandlerException.
     */
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	CustomerSalesSearchAction action = new CustomerSalesSearchAction();
	action.processRequest(this.request, this.response, CustomerSalesSearchAction.COMMAND_LIST);
    }

}
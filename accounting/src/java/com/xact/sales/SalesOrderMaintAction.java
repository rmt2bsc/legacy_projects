package com.xact.sales;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Customer;
import com.bean.SalesInvoice;
import com.bean.SalesOrder;
import com.bean.SalesOrderStatus;
import com.bean.SalesOrderStatusHist;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.BasicGLApi;
import com.gl.customer.CustomerFactory;
import com.gl.customer.CustomerException;
import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerHelper;

import com.util.SystemException;

import com.xact.AbstractXactAction;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

/**
 * This class provides action handler functionality that is common serving sales order requests.
 * 
 * @author Roy Terrell
 *
 */
public abstract class SalesOrderMaintAction extends AbstractXactAction {
    private static final int UI_MODE_EDIT = 1;

    private static final int UI_MODE_VIEW = 2;

    private static final int SO_INOVICED = 1;

    private static final int SO_NOTINOVICED = 0;

    private static final String URL_EDIT = "/forms/xact/sales/CustomerSalesOrderEdit.jsp";

    private static final String URL_VIEW = "/forms/xact/sales/CustomerSalesOrderView.jsp";

    private static Logger logger;

    private CustomerApi custApi;

    /** Sales Order API */
    protected SalesOrderApi salesApi;

    /** Transaction API */
    //    protected XactManagerApi xactApi;
    /** Customer object variable */
    protected Customer customer;

    /** Customer Extension object variable */
    protected Object customerExt;

    /** Sales Order object variable */
    protected SalesOrder salesOrder;

    /** Sales Invoice object variable */
    protected SalesInvoice si = null;

    /** Sales Order Status object variable */
    protected SalesOrderStatus sos;

    /** Sales order item helper object variable */
    protected SalesOrderItemHelper itemHelper;

    /** Variable for determining the mode of the sales order is displayed in the UI: edit or read-only */
    protected int uiMode;

    /**
     * Default constructor
     *
     */
    public SalesOrderMaintAction() {
	super();
	SalesOrderMaintAction.logger = Logger.getLogger(SalesOrderMaintAction.class);
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public SalesOrderMaintAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
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
	//	this.salesApi = SalesFactory.createApi(this.dbConn, _request);
	//	this.custApi = CustomerFactory.createApi(this.dbConn, _request);
	//	XactManagerApi xactApi = XactFactory.create(this.dbConn, this.request);
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
    }

    /**
     * Retreives bsic customer, sales order, and transactoin  data from the client's request.
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.custApi = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get data from client's page
	    this.customer = this.httpHelper.getHttpCustomer();
	    //this.customerExt = this.custApi.findCustomerWithName(this.customer.getId());
	    this.customerExt = this.custApi.findCustomerBusiness(this.customer.getCustomerId());

	    this.salesOrder = this.httpHelper.getHttpSalesOrder();
	    this.xact = this.httpHelper.getHttpXact();

	    // Get Invoice object
	    this.si = this.getSalesOrderInvoice(salesOrder.getSoId());

	    // Get Current sales order status
	    this.sos = this.getCurrentSalesOrderStatus(salesOrder.getSoId());

	    // Determine if sales order editable.
	    switch (salesOrder.getInvoiced()) {
	    case SalesOrderMaintAction.SO_INOVICED:
		this.uiMode = SalesOrderMaintAction.UI_MODE_VIEW; // View Only
		break;
	    case SalesOrderMaintAction.SO_NOTINOVICED:
		switch (this.sos.getSoStatusId()) {
		case SalesConst.STATUS_CODE_CLOSED:
		case SalesConst.STATUS_CODE_CANCELLED:
		case SalesConst.STATUS_CODE_REFUNDED:
		    this.uiMode = SalesOrderMaintAction.UI_MODE_VIEW;
		    break;
		default:
		    this.uiMode = SalesOrderMaintAction.UI_MODE_EDIT; // Editable
		    break;
		} // end switch
	    } // end switch

	    return;
	} // end try
	catch (Exception e) {
	    SalesOrderMaintAction.logger.log(Level.ERROR, e.getMessage());
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
     * Packages detail Sales Order data into the HttpServlerRequest object which is required to serve as the response to the client. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	double salesOrderTotal;
	// Call stored function to obtain sales order amount and return to the client.
	try {
	    salesOrderTotal = this.salesApi.getSalesOrderTotal(this.salesOrder.getSoId());
	    this.salesOrder.setOrderTotal(salesOrderTotal);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}

	// Prepare to send results to client
	this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT, this.customerExt);
	this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.salesOrder);
	this.request.setAttribute(SalesConst.CLIENT_DATA_STATUS, this.sos);
	this.request.setAttribute(SalesConst.CLIENT_DATA_INVOICE, this.si);
	this.request.setAttribute(SalesConst.CLIENT_DATA_XACT, this.xact);
	if (this.itemHelper != null) {
	    this.request.setAttribute(SalesConst.CLIENT_DATA_SERVICE_ITEMS, this.itemHelper.getSrvcItems());
	    this.request.setAttribute(SalesConst.CLIENT_DATA_MERCHANDISE_ITEMS, this.itemHelper.getMerchItems());
	}

	switch (this.uiMode) {
	case SalesOrderMaintAction.UI_MODE_EDIT:
	    this.request.setAttribute(RMT2ServletConst.RESPONSE_HREF, SalesOrderMaintAction.URL_EDIT);
	    break;

	case SalesOrderMaintAction.UI_MODE_VIEW:
	    this.request.setAttribute(RMT2ServletConst.RESPONSE_HREF, SalesOrderMaintAction.URL_VIEW);
	} // end switch
	this.request.setAttribute(GeneralConst.REQ_COMMAND, this.request.getAttribute(GeneralConst.REQ_CLIENTACTION));
    }

    /**
     * Setups the data necessary for navigating the user to the previous URL.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	try {
	    // Forward call to Sales Console action handler.
	    CustomerSalesConsoleAction console = new CustomerSalesConsoleAction(this.context, this.request);
	    console.getCustomerSalesOrders();
	    return;
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * Performs initializations needed for creating a sales order.   <p>The only thing that get initializes here is the sales 
     * order item helper class.   Override in order to add sales order items. 
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	super.add();
	this.receiveClientData();
	this.initItemHelper();
	return;
    }

    /**
     * Performs initializations needed for modifying an existing sales order.   <p>The only thing that get initializes here is the sales 
     * order item helper class.   Override in order to modify sales order items. 
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	super.edit();
	this.initItemHelper();
	return;
    }

    /**
     * Performs initializations needed for persisting changes to a sales order.   <p>The only thing that get initializes here is the sales 
     * order item helper class.   Override in order to modify sales order items. 
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	super.save();
	this.initItemHelper();
	return;
    }

    /**
     * Handles a delete action.   <p>The only thing that get initializes here is the sales 
     * order item helper class.   Override in order to modify sales order items. 
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	super.delete();
	this.initItemHelper();
	return;
    }

    /**
     * Closes an invoiced sales order.
     * 
     * @throws SalesOrderException
     */
    public void closeOrder() throws SalesOrderException {
	// This method call is required in order to be page compatible 
	// with the cancel and refund actions.
	this.initItemHelper();
	this.salesApi.changeSalesOrderStatus(this.salesOrder.getSoId(), SalesConst.STATUS_CODE_CLOSED);
    }

    /**
     * Cancels a sales order.
     * 
     * @return The transaction id of the cancelled sales order
     * @throws SalesOrderException
     */
    public int cancel() throws SalesOrderException {
	int xactId = 0;
	this.initItemHelper();

	// Cancel the order
	try {
	    xactId = this.salesApi.cancelSalesOrder(this.salesOrder.getSoId());
	    return xactId;
	}
	catch (SalesOrderException e) {
	    throw e;
	}
    }

    /**
     * Refunds a sales order.
     * 
     * @return The transaction id of the refunded sales order
     * @throws SalesOrderException
     */
    public int refund() throws SalesOrderException {
	int xactId = 0;
	this.initItemHelper();

	// Refund the order
	try {
	    xactId = this.salesApi.refundSalesOrder(this.salesOrder.getSoId());
	    return xactId;
	}
	catch (SalesOrderException e) {
	    throw e;
	}
    }

    /**
     * Setup a sales order item helper object.
     *
     */
    private void initItemHelper() {
	try {
	    // Get existing sales order items
	    this.itemHelper = new SalesOrderItemHelper(this.context, this.request, this.customer, this.salesOrder);
	}
	catch (SystemException e) {
	    SalesOrderMaintAction.logger.log(Level.ERROR, e.getMessage());
	}
	catch (DatabaseException e) {
	    SalesOrderMaintAction.logger.log(Level.ERROR, e.getMessage());
	}
	return;
    }

    /**
     * Retrieves the sales order invoice object using _slaesOrder.
     * 
     * @param _salesOrder
     * @return {@link SalesInvoice}
     */
    private SalesInvoice getSalesOrderInvoice(int _salesOrder) throws SalesOrderException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    SalesInvoice si = (SalesInvoice) this.salesApi.findSalesOrderInvoice(_salesOrder);
	    if (si == null) {
		si = SalesFactory.createSalesInvoice();
	    }
	    return si;
	}
	catch (SalesOrderException e) {
	    throw e;
	}
	finally {
	    this.salesApi.close();
	    tx.close();
	    this.salesApi = null;
	    tx = null;
	}
    }

    /**
     * Retrieves the current sales order status using _salesOrderId.
     * 
     * @param _salesOrderId
     * @return
     */
    protected SalesOrderStatus getCurrentSalesOrderStatus(int _salesOrderId) throws SalesOrderException {
	SalesOrderStatusHist sosh = null;
	SalesOrderStatus sos = null;
	boolean releaseTrans = false;
	DatabaseTransApi tx = null;

	if (this.salesApi == null) {
	    tx = DatabaseTransFactory.create();
	    this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	    releaseTrans = true;
	}
	try {
	    sosh = (SalesOrderStatusHist) this.salesApi.findCurrentSalesOrderStatusHist(_salesOrderId);
	    if (sosh == null) {
		sos = new SalesOrderStatus();
	    }
	    else {
		sos = (SalesOrderStatus) this.salesApi.findSalesOrderStatus(sosh.getSoStatusId());
		if (sos == null) {
		    sos = new SalesOrderStatus();
		}
	    }
	    return sos;
	}
	catch (SystemException e) {
	    SalesOrderMaintAction.logger.log(Level.ERROR, "SystemException occurred. " + e.getMessage());
	    throw new SalesOrderException(e);
	}
	finally {
	    if (releaseTrans) {
		this.salesApi.close();
		tx.close();
		this.salesApi = null;
		tx = null;
	    }
	}
    }

    /**
     * Gathers the data necessary to display the Customer Sales Order Console 
     * and sets the data onto the user's request.
     * 
     * @throws ActionHandlerException
     */
    protected void prepareCustomerSalesConsole() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CustomerHelper helper = CustomerFactory.createCustomerHelper(this.request, this.response, (DatabaseConnectionBean) tx.getConnector());
	if (helper == null) {
	    return;
	}
	this.receiveClientData();
	try {
	    helper.fetchCustomer(this.customer.getCustomerId());
	}
	catch (CustomerException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    tx.close();
	    tx = null;
	}
	Double balance = helper.getBalance();
	List customers = helper.getCustomers();
	Object cust = helper.getCust();
	Object custDetail = helper.getCustDetail();
	Object busTypes = helper.getBusTypes();
	Object busServTypes = helper.getBusServTypes();

	this.request.setAttribute(CustomerApi.CLIENT_DATA_CUSTOMER, cust);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, customers);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_BUSINESS, custDetail);
	this.request.setAttribute(BasicGLApi.CLIENT_CUSTOMERBAL, balance);
	this.request.setAttribute(BasicGLApi.CLIENT_BUSTYPES, busTypes);
	this.request.setAttribute(BasicGLApi.CLIENT_BUSSERVTYPES, busServTypes);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, msg);
    }

    /**
     * Makes call to customer api to calculate and capture customer's balance.  The balance 
     * is added to the sales order member variable.   If sales orer varialbe is invalid it 
     * is instantiated.
     * 
     * @param custId Customer Id
     * @return {@link SalesOrder}
     * @throws ActionHandlerException
     */
    protected SalesOrder calcCustomerBalance(int custId) throws ActionHandlerException {
	SalesOrder salesOrder = null;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.custApi = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    salesOrder = SalesFactory.createSalesOrder();
	    double balance = this.custApi.findCustomerBalance(custId);
	    salesOrder.setOrderTotal(balance);
	    return salesOrder;
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

}
package com.xact.receipts;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Customer;
import com.bean.SalesOrder;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.customer.CustomerException;
import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerFactory;

import com.util.SystemException;

import com.xact.XactException;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

import com.xact.receipts.CashReceiptsApi;

import com.xact.sales.SalesConst;
import com.xact.sales.CustomerSalesConsoleAction;
import com.xact.sales.SalesFactory;
import com.xact.sales.SalesOrderMaintAction;

/**
 * Action handler that responds to sales order payment reversal and display sales 
 * order console requests.
 * 
 * @author Roy Terrell
 *
 */
public class CashReceiptsReverseAction extends SalesOrderMaintAction {
    private static final String COMMAND_REVERSE = "CashReceipts.Reverse.reverse";

    private static final String COMMAND_BACK = "CashReceipts.Reverse.back";
    
    private static Logger logger;

    private CustomerApi custApi;

    private CashReceiptsApi crApi;

    private SalesOrder so;

    private Customer cust;

    private Object custExt;

    

    /**
     * Default constructor
     *
     */
    public CashReceiptsReverseAction() {
	super();
	CashReceiptsReverseAction.logger = Logger.getLogger(CashReceiptsReverseAction.class);
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CashReceiptsReverseAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request and setup a GlCustomerApi object.
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

	if (command.equalsIgnoreCase(CashReceiptsReverseAction.COMMAND_REVERSE)) {
	    this.reversePayment();
	}
	if (command.equalsIgnoreCase(CashReceiptsReverseAction.COMMAND_BACK)) {
	    this.getTransactions();
	}
    }

    /**
     * Retreives the customer data from the client's request.
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.custApi = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Obtain Customer data from the request object.
	    this.cust = this.httpHelper.getHttpCustomer();
	    this.custExt = custApi.findCustomerBusiness(this.cust.getCustomerId());
	}
	catch (CustomerException e) {
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
     * Sends the response to the client's request with customer, transaction, and sales order data. 
     * Response attribute names are requires as follows:  customer=SalesConst.CLIENT_DATA_CUSTOMER_EXT, 
     * transaction=XactConst.CLIENT_DATA_XACT, and sales order=SalesConst.CLIENT_DATA_SALESORDER. 
     */
    protected void sendClientData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    super.sendClientData();
	    this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT, this.custExt);
	    this.so = this.calcCustomerBalance(this.cust.getCustomerId());
	    this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.so);
	    this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
	    tx.commitUOW();
	}
	catch (ActionHandlerException e) {
	    tx.rollbackUOW();
	}
	finally {
	    this.salesApi.close();
	    this.salesApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Reverses a Sales Order payment.
     *  
     * @throws ActionHandlerException
     */
    protected void reversePayment() throws ActionHandlerException {
	this.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.crApi = ReceiptsFactory.createCashReceiptsApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    try {
		this.syncXact(this.xact);
		int xactId = this.crApi.maintainCustomerPayment(this.xact, this.cust.getCustomerId());
		tx.commitUOW();
		this.msg = "Transaction was reversed successfully";

		// Get combined transaction items
		this.xactItems = xactApi.findVwXactTypeItemActivityByXactId(xactId);
		this.msg = "Transaction was reversed successfully!";
		CashReceiptsReverseAction.logger.log(Level.INFO, this.msg);
		this.sendClientData();
		return;
	    }
	    catch (XactException e) {
		throw new ActionHandlerException("Cash receipt reversal failed", e);
	    }
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "General exception occurred during cash receipt reversal transaction";
	    throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    this.sendClientData();
	    this.crApi.close();
	    xactApi.close();
	    tx.close();
	    this.crApi = null;
	    xactApi = null;
	    tx = null;
	}
    }

    /**
     * Borrows the logic of {@link CustomerSalesConsoleAction#getTransactions()} to redisplay customer's 
     * transaction history.
     * 
     * @throws ActionHandlerException
     */
    protected void getTransactions() throws ActionHandlerException {
	try {
	    CustomerSalesConsoleAction console = new CustomerSalesConsoleAction(this.context, this.request);
	    console.getTransactions();
	}
	catch (Exception e) {
	    throw new ActionHandlerException("Cash Receipts operation failed to obtain customer's transaction history", e);
	}
    }
}
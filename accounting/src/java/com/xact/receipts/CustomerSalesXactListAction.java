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

import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerFactory;
import com.gl.customer.CustomerException;

import com.util.SystemException;

import com.xact.sales.SalesConst;
import com.xact.sales.SalesFactory;
import com.xact.sales.SalesOrderMaintAction;

/**
 * Action handler that responds to sales order payment reversal and display sales order console requests.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesXactListAction extends SalesOrderMaintAction {
    private static final String COMMAND_REVERSEPAYMENT = "SalesCustomerXactList.Edit.reversepaymentedit";

    private static final String COMMAND_BACK = "SalesCustomerXactList.Edit.back";

    private CustomerApi custApi;

    private SalesOrder so;

    private Customer cust;

    private Object custExt;

    private static Logger logger;

    /**
     * Default constructor
     *
     */
    public CustomerSalesXactListAction() {
	super();
	CustomerSalesXactListAction.logger = Logger.getLogger(CustomerSalesXactListAction.class);
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CustomerSalesXactListAction(Context _context, Request _request) throws SystemException, DatabaseException {
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

	if (command.equalsIgnoreCase(CustomerSalesXactListAction.COMMAND_REVERSEPAYMENT)) {
	    this.getClientTransaction();
	}
	if (command.equalsIgnoreCase(CustomerSalesXactListAction.COMMAND_BACK)) {
	    this.prepareCustomerSalesConsole();
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
	    this.so = this.calcCustomerBalance(this.cust.getCustomerId());
	    this.refreshXact();
	}
	catch (CustomerException e) {
	    CustomerSalesXactListAction.logger.log(Level.ERROR, "GLAcctException occurred. " + e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
		this.custApi.close();
		this.custApi = null;
		tx.close();
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
	    this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.so);
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
     * Gathers key data from the client which is used to obtain customer, transaction, 
     * and sales order data for the response.  The transaction reason is reset for 
     * editing on the target URL.
     *  
     * @throws ActionHandlerException
     */
    private void getClientTransaction() throws ActionHandlerException {
	this.receiveClientData();
	this.sendClientData();
	this.xact.setReason(null);
    }

}
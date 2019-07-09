package com.xact.sales;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;
import com.xact.XactException;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

/**
 * This class provides functionality to serve the requests of the Customer Sales Order List user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesOrderListAction extends SalesOrderMaintAction {

    private static final String COMMAND_EDIT = "SalesCustomerOrderList.OrderList.edit";

    private static final String COMMAND_PRINT = "SalesCustomerOrderList.OrderList.print";

    private static final String COMMAND_BACK = "SalesCustomerOrderList.OrderList.back";

    private static Logger logger;

    /**
     * Default constructor
     *
     */
    public CustomerSalesOrderListAction() {
	super();
	CustomerSalesOrderListAction.logger = Logger.getLogger(CustomerSalesOrderListAction.class);
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CustomerSalesOrderListAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	this.init(this.context, this.request);
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

	if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_PRINT)) {
	    this.doPrint();
	}
	if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Retreives bsic customer, sales order, and transaction  data from the client's request.
     * If transaction is founded to be invoiced, then the transaction is retrieved from the 
     * database instead.
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	if (this.si != null && this.si.getInvoiceId() > 0) {
	    DatabaseTransApi tx = DatabaseTransFactory.create();
	    XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	    try {
		this.xact = xactApi.findXactById(this.si.getXactId());
		this.xact.setConfirmNo("See Invoice No.");
	    }
	    catch (XactException e) {
		e.printStackTrace();
		this.xact.setReason("<Unobtainable>");
		this.xact.setConfirmNo("<Unobtainable>");
	    }
	    finally {
		xactApi.close();
		xactApi = null;
		tx.close();
		tx = null;
	    }
	}
    }

    /**
     * Packages detail Sales Order data into the HttpServlerRequest object which is required to serve as the response to the client. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    super.sendClientData();
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
     * Gathers data pertaining to sales order total, sales invoice, sales order status, 
     * sales order items, the mode which the sales order details interface will display data.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	List items;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.salesApi = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	super.edit();
	// Call stored function to obtain sales order amount and return to the client.
	try {
	    // Get existing sales order items
	    items = (List) this.salesApi.findSalesOrderItems(this.salesOrder.getSoId());
	    this.itemHelper.packageItemsByTypes(items);
	    return;
	}
	catch (SalesOrderException e) {
	    CustomerSalesOrderListAction.logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e);
	}
	catch (SystemException e) {
	    CustomerSalesOrderListAction.logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.salesApi.close();
	    this.salesApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Retrieves customer detail data which is needed for navigating the user back 
     * to the customer sales console.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	this.prepareCustomerSalesConsole();
    }

    /**
     * Prints the invoice.
     * 
     * @throws ActionHandlerException
     */
    protected void doPrint() throws ActionHandlerException {
	SalesOrderInvoiceReportAction printer = new SalesOrderInvoiceReportAction();
	String command = "Reports.SalesOrderInvoiceReport.print";
	printer.processRequest(this.request, this.response, command);
    }

}
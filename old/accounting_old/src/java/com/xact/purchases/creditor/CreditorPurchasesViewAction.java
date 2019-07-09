package com.xact.purchases.creditor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.util.SystemException;
import com.xact.XactFactory;
import com.xact.XactManagerApi;
import com.xact.disbursements.CashDisburseFactory;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.action.ActionHandlerException;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

/**
 * This class provides action handlers to manage Expense Creditor Purchase View page requests.
 * 
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesViewAction extends CreditorPurchasesAction {
    private static final String COMMAND_SEARCH = "PurchasesCreditor.View.search";

    private static final String COMMAND_REVERSE = "PurchasesCreditor.View.reverse";
    
    private static final String COMMAND_ADD = "PurchasesCreditor.View.add";

    private Logger logger;

    private int creditorId;

    /**
     * Default constructor
     *
     */
    public CreditorPurchasesViewAction() throws SystemException {
	super();
	logger = Logger.getLogger("CreditorPurchasesViewAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CreditorPurchasesViewAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);

    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
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
	if (command.equalsIgnoreCase(CreditorPurchasesViewAction.COMMAND_SEARCH)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesViewAction.COMMAND_REVERSE)) {
	    this.doReverse();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesViewAction.COMMAND_ADD)) {
	    this.doNewTransaction();
	}
    }

    /**
     * Drives the process of reversing an Expense Credit Purchase transaction.
     * 
     * @throws ActionHandlerException
     */
    public void doReverse() throws ActionHandlerException {
	this.receiveClientData();
	this.reverseXact();
	this.sendClientData();
    }

    /**
     * Reverses an expense credit purchase based on the current values of this 
     * class' internal properties: transaction, creditor, and transaction items.
     * 
     * @throws ActionHandlerException
     */
    private void reverseXact() throws ActionHandlerException {
	int xactId = 0;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.ccApi = CashDisburseFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    xactId = this.ccApi.maintainCashDisbursement(this.xact, this.xactItems, this.creditorId);
	    tx.commitUOW();
	    this.httpXactHelper.retrieveCreditOrder(xactId, this.creditorId);
	    this.msg = "Transaction reversed successfully";
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		this.ccApi.close();
		tx.close();
		this.ccApi = null;
		tx = null;
	}
    }

    /**
     * Obtains key data from the clients request pertaining to the creditor and expense credit 
     * purchase transaction.  The key data items are used to fetch existing data from the 
     * database.
     * 
     * @throws ActionHandlerException
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xact = this.httpXactHelper.getXact();
	    this.xact = xactApi.findXactById(this.xact.getXactId());
	    this.creditorId = this.httpXactHelper.getCreditor().getCreditorId();
	    this.xactItems = xactApi.findXactTypeItemsActivityByXactId(this.xact.getXactId());
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		xactApi.close();
		tx.close();
		xactApi = null;
		tx = null;
	}
    }

    /**
     * navigate back to search page.
     */
    public void doBack() throws ActionHandlerException {
	return;
    }
    
    /**
     * Setup for new Creditor Purcases Transaction
     * 
     * @throws ActionHandlerException
     */
    public void doNewTransaction() throws ActionHandlerException {
	CreditorPurchasesSearchAction action = new CreditorPurchasesSearchAction();
	action.processRequest(request, response, CreditorPurchasesSearchAction.COMMAND_ADD);
	action = null;
    }

}
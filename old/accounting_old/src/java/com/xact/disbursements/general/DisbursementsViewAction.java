package com.xact.disbursements.general;

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

import com.xact.AbstractXactAction;
import com.xact.XactException;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

import com.xact.disbursements.CashDisbursementsApi;
import com.xact.disbursements.CashDisburseFactory;
import com.xact.disbursements.CashDisbursementsException;

/**
 * This class provides functionality needed View Cash Disbursements transactions in read-only mode
 * 
 * @author Roy Terrell
 *
 */
public class DisbursementsViewAction extends AbstractXactAction {
    private static final String COMMAND_ADD = "DisbursementsGeneral.View.add";

    private static final String COMMAND_REVERSE = "DisbursementsGeneral.View.reverse";

    private static final String COMMAND_BACK = "DisbursementsGeneral.View.back";

    private CashDisbursementsApi disbApi;

    private Logger logger;

    /**
     * Default constructor
     *
     */
    public DisbursementsViewAction() {
	super();
	logger = Logger.getLogger("DisbursementsViewAction");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public DisbursementsViewAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
//	this.disbApi = CashDisburseFactory.createApi(this.dbConn, this.request);
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
	if (command.equalsIgnoreCase(DisbursementsViewAction.COMMAND_ADD)) {
	    this.doNewXact();
	}
	if (command.equalsIgnoreCase(DisbursementsViewAction.COMMAND_REVERSE)) {
	    this.doReverse();
	}
	if (command.equalsIgnoreCase(DisbursementsViewAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Instantiates the data objects needed to create a new cash 
     * disbursement transaction which the objects are sent across the wire 
     * to the client for presentation. 
     */
    public void doNewXact() throws ActionHandlerException {
	DisbursementsSearchAction action = new DisbursementsSearchAction();
	action.processRequest(this.request, this.response, DisbursementsSearchAction.COMMAND_ADD);
    }

    /**
     * Reverses a cash disbursement transaction which the transaction data that 
     * is to be reversed is obtained from the client.
     * 
     * @throws ActionHandlerException
     */
    public void doReverse() throws ActionHandlerException {
	this.receiveClientData();
	this.save();
	this.msg = "Transaction Reversed Successfully";
	this.sendClientData();
    }

    /**
     * Sends the user back to the cash disbursements page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	DisbursementsSearchAction action = new DisbursementsSearchAction();
	action.processRequest(this.request, this.response, DisbursementsSearchAction.COMMAND_LIST);
    }

    /**
     * Applies changes to transaction data to the database.  An insert is performed 
     * new transactions (id <= 0) and a reversal is performed for existing transactions.
     * 
     * @throws ActionHandlerException
     * @see AbstractXactAction#save()
     */
    public void save() throws ActionHandlerException {
	super.save();
	int xactId = 0;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.disbApi = CashDisburseFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xact = xactApi.findXactById(this.xact.getXactId());
	    xactId = this.disbApi.maintainCashDisbursement(this.xact, this.xactItems);
	    tx.commitUOW();
	    this.msg = "Transaction Saved Successfully";
	}
	catch (XactException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    this.msg = "Error: " + e.getMessage();
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	catch (CashDisbursementsException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    this.msg = "Error: " + e.getMessage();
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.refreshXact(xactId);
	    this.disbApi.close();
	    xactApi.close();
	    tx.close();
	    this.disbApi = null;
	    xactApi = null;
	    tx = null;
	}
    }

}
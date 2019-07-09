package com.xact.disbursements.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.Creditor;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorEditAction;
import com.gl.creditor.CreditorFactory;

import com.xact.XactConst;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

import com.util.SystemException;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorConsoleAction extends CreditorEditAction {
    private static final String COMMAND_PAYMENT = "DisbursementsCreditor.Console.payment";

    private static final String COMMAND_TRANSACTIONS = "DisbursementsCreditor.Console.transactions";

    private static final String COMMAND_BACK = "DisbursementsCreditor.Console.back";

    private Logger logger;

    /**
     * Default constructor
     * 
     */
    public DisbursementsCreditorConsoleAction() {
	super();
	this.logger = Logger.getLogger("DisbursementsCreditorConsoleAction");
	this.logger.log(Level.DEBUG, "Logger is setup");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param _context
     *            The servlet context to be associated with this action handler
     * @param _request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public DisbursementsCreditorConsoleAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
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
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	if (command.equalsIgnoreCase(DisbursementsCreditorConsoleAction.COMMAND_PAYMENT)) {
	    this.doPayment();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorConsoleAction.COMMAND_TRANSACTIONS)) {
	    this.doTransactions();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorConsoleAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    public void sendClientData() throws ActionHandlerException {
	// This line of code is needed for those pages that refer to 
	// Xact objects before the transaction is actually realized.
	this.request.setAttribute(XactConst.CLIENT_DATA_XACT, XactFactory.createXact());
	super.sendClientData();
    }

    /**
     * Handler method that responds to the client's request to display a new
     * creditor search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doPayment() throws ActionHandlerException {
	this.receiveClientData();
	this.sendClientData();
    }

    /**
     * Gathers Creditor/Vendor related cash disbursement transactions and sends the data to the client.
     * 
     * @throws ActionHandlerException
     */
    protected void doTransactions() throws ActionHandlerException {
	this.receiveClientData();
	// Get Creditor transactions	  	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    int creditorId = ((Creditor) this.cred).getCreditorId();
	    this.creditors = xactApi.findCreditorXactHist(creditorId);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    xactApi.close();
	    tx.close();
	    xactApi = null;
	    tx = null;
	}
	this.sendClientData();
    }

    /**
     * Sends the user back to the Creditor/Vendor Cash Disbursement Search page.  Uses the most recent SQL 
     * predicate that is stored in the user's session to perform the previous Creditor/Vendor search for 
     * Cash Disbursements.
     *  
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	super.doBack();
    }

    /**
     * Obtains key creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	int creditorId = 0;
	String credIdStr = this.request.getParameter("CreditorId");
	if (credIdStr != null) {
	    creditorId = Integer.parseInt(credIdStr);
	    ((Creditor) this.cred).setCreditorId(creditorId);
	}

	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi api = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.credExt = api.findCreditorBusiness(creditorId);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    api = null;
	    tx.close();
	    tx = null;
	}
	this.fetchCreditor(creditorId);
    }
}
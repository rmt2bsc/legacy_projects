package com.xact.disbursements.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.RMT2TagQueryBean;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

import com.xact.XactFactory;
import com.xact.XactManagerApi;
import com.xact.disbursements.CashDisburseFactory;

/**
 * This class provides functionality needed to serve the requests of the Cash Disbursements Search user interface
 * 
 * @author Roy Terrell
 *
 */
public class DisbursementsCreditorPaymentAction extends DisbursementsCreditorXactCommon {
    private static final String COMMAND_SAVE = "DisbursementsCreditor.Payment.save";

    private static final String COMMAND_BACK = "DisbursementsCreditor.Payment.back";

    private Logger logger;

    //private Business bus;

    /**
     * Default constructor
     *
     */
    public DisbursementsCreditorPaymentAction() {
	super();
	logger = Logger.getLogger("DisbursementsSearchAction");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public DisbursementsCreditorPaymentAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
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

	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);

	if (command.equalsIgnoreCase(DisbursementsCreditorPaymentAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorPaymentAction.COMMAND_BACK)) {
	    this.doBack();
	}
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
	this.disbApi = CashDisburseFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Set Confirmation number.   Confirmation number will be a value from an external source such 
	    // as the creditor/vendor's payment website or it will be null if direct  cash payment.
	    String confirmNo = this.request.getParameter("ConfirmNo");
	    this.xact.setConfirmNo(confirmNo);

	    // Set transaction date to today.
	    java.util.Date today = new java.util.Date();
	    if (this.xact.getXactDate() == null) {
	        this.xact.setXactDate(today);
	    }
	    // Get items just in case we are reversing a transaction
	    this.xactItems = xactApi.findXactTypeItemsActivityByXactId(this.xact.getXactId());
	    // Update transaction.
	    xactId = this.disbApi.maintainCashDisbursement(this.xact, this.xactItems, creditor.getCreditorId());
	    this.xact = xactApi.findXactById(xactId);
	    tx.commitUOW();
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
		this.disbApi.close();
		xactApi.close();
		tx.close();
		this.disbApi = null;
		xactApi = null;
		tx = null;
	}
    }

    /**
     * Sends the user back to the cash disbursements console page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	this.receiveClientData();
	this.credAction.fetchCreditor(this.creditor.getCreditorId());
	this.sendClientData();
    }

    /**
     * Obtains key creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
    }

    /**
     * Sends key transaction and creditor data to the client.
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
    }
}
package com.xact.disbursements.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.VwXactTypeItemActivity;

import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.xact.disbursements.CashDisburseFactory;
import com.xact.disbursements.CashDisbursementsException;

import com.util.SystemException;

import com.xact.XactException;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorXactViewAction extends DisbursementsCreditorXactCommon {
    private static final String COMMAND_REVERSE = "DisbursementsCreditor.XactView.reverse";

    private static final String COMMAND_BACK = "DisbursementsCreditor.XactView.back";

    private Logger logger;

    /**
     * Default constructor
     * 
     */
    public DisbursementsCreditorXactViewAction() {
	super();
	logger = Logger.getLogger("DisbursementsCreditorXactViewAction");
	this.logger.log(Level.DEBUG, "Logger setup");
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
    public DisbursementsCreditorXactViewAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
     * Processes the client's request to reverse a creditor cash disbursement transaction 
     * using request, response, and command.
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
	if (command.equalsIgnoreCase(DisbursementsCreditorXactViewAction.COMMAND_REVERSE)) {
	    this.doReverse();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorXactViewAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Handler method that responds to the client's request to reverse selected transaction.
     * 
     * @throws ActionHandlerException
     */
    protected void doReverse() throws ActionHandlerException {
	this.receiveClientData();
	this.reverseXact();
	this.msg = "Transaction reversed successfully";
	this.sendClientData();
    }

    /**
     * Reverses creditor/vendor cash disbursement transaction. 
     *  
     * @return int - the transaction id. 
     * @throws ActionHandlerException
     */
    private int reverseXact() throws ActionHandlerException {
	int xactId = 0;
	List<VwXactTypeItemActivity> items = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.disbApi = CashDisburseFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    items = xactApi.findXactTypeItemsActivityByXactId(this.xact.getXactId());
	    xactId = this.disbApi.maintainCashDisbursement(this.xact, items, this.creditor.getCreditorId());
	    tx.commitUOW();
	    return xactId;
	}
	catch (CashDisbursementsException e) {
	    this.msg = "Creditor/Vendor cash disbursement reversal failed";  
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg, e);
	}
	catch (XactException e) {
	    this.msg = "A general transaction exception occurred for Creditor/Vendor cash disbursement reversal operation";
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg, e);
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
     * Retrieves customer's transaction data and sends the data back to the client via the 
     * user's reqeust instance.
     */
    public void sendClientData() throws ActionHandlerException {
        super.sendClientData();
        this.trans = this.getCreditorTransactions(this.creditor.getCreditorId());
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.trans);
    }
    
}
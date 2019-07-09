package com.xact.disbursements.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Creditor;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorFactory;
import com.gl.creditor.CreditorSearchAction;

import com.util.SystemException;

import com.xact.disbursements.CashDisburseFactory;
import com.xact.disbursements.DisbursementsConst;

import com.xact.AbstractXactAction;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

import com.xact.disbursements.CashDisbursementsApi;

/**
 * This class provides common functionality needed to serve Creditor/Vendor cash 
 * disbursement request that involves combined transaction and creditor data from 
 * the user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorXactCommon extends AbstractXactAction {
    protected static final String COMMAND_BACK = "DisbursementsCreditor.XactView.back";

    private Logger logger;

    protected Creditor creditor;

    protected Object credExt;

    protected CreditorSearchAction credAction;

    protected List trans;

    protected CashDisbursementsApi disbApi;

    /**
     * Default constructor
     * 
     */
    public DisbursementsCreditorXactCommon() {
	super();
	logger = Logger.getLogger("DisbursementsCreditorXactCommon");
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
    public DisbursementsCreditorXactCommon(Context _context, Request _request) throws SystemException, DatabaseException {
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
	try {
	    this.credAction = new CreditorSearchAction(_context, _request);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SystemException(e.getMessage());
	}
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
    }

    /**
     * Returns the user to the Cash Disbursement Transaction List page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	this.receiveClientData();
	this.sendClientData();
    }

    /**
     * Obtains key transaction and creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	this.refreshXact();
	this.credAction.setResponse(this.response);
	this.credAction.receiveClientData();
	this.creditor = (Creditor) this.credAction.getCred();
	this.credAction.fetchCreditor(this.creditor.getCreditorId());
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi api = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.credExt = api.findCreditorBusiness(this.creditor.getCreditorId());
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
		api = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Sends general creditor data to the client.
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.credAction.sendClientData();
	this.creditor = (Creditor) this.credAction.getCred();
	this.request.setAttribute(DisbursementsConst.CLIENT_DATA_CREDEXTT, this.credExt);
    }

    protected List getCreditorTransactions(int creditorId) throws ActionHandlerException {
	List list = new ArrayList();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi api = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    list = api.findCreditorXactHist(creditorId);
	    return list;
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
		api.close();
		tx.close();
		api = null;
		tx = null;
	}
    }
}
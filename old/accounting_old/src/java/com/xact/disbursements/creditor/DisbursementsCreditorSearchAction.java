package com.xact.disbursements.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.creditor.CreditorSearchAction;

import com.util.SystemException;
import com.xact.HttpXactHelper;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorSearchAction extends CreditorSearchAction {
    private static final String COMMAND_NEWSEARCH = "DisbursementsCreditor.Search.newsearch";

    private static final String COMMAND_SEARCH = "DisbursementsCreditor.Search.search";

    private static final String COMMAND_LIST = "DisbursementsCreditor.Search.list";

    private static final String COMMAND_EDIT = "DisbursementsCreditor.Search.edit";

    private static final String COMMAND_BACK = "DisbursementsCreditor.Search.back";

    private Logger logger;

    /** Helper object used to obtain data from the client's request object */
    private HttpXactHelper httpHelper;

    /**
     * Default constructor
     * 
     */
    public DisbursementsCreditorSearchAction() {
	super();
	this.logger = Logger.getLogger("DisbursementsCreditorSearchAction");
	this.logger.log(Level.INFO, "Logger Created");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public DisbursementsCreditorSearchAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
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
	    this.httpHelper = new HttpXactHelper(this.context, this.request);
	    this.httpHelper.setSelectedRow(this.selectedRow);
	}
	catch (DatabaseException e) {
	    throw new SystemException(e);
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
	if (command.equalsIgnoreCase(DisbursementsCreditorSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorSearchAction.COMMAND_LIST)) {
	    this.listData();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(DisbursementsCreditorSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Obtains key creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	try {
	    this.cred = this.httpHelper.getHttpCreditor();
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
    }

}
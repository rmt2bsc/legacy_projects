package com.gl.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.Creditor;
import com.bean.bindings.JaxbAccountingFactory;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.MessagingException;

import com.api.security.authentication.RMT2SessionBean;

import com.util.SystemException;

import com.xml.schema.bindings.BusinessType;

/**
 * This class provides functionality needed to serve the requests for the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class CreditorEditAction extends CreditorAction {
    private static final String COMMAND_SAVE = "Creditor.Edit.save";

    private static final String COMMAND_BACK = "Creditor.Edit.back";

    private Logger logger;

    /**
     * Default constructor
     * 
     */
    public CreditorEditAction() {
	super();
	logger = Logger.getLogger("CreditorEditAction");
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
    public CreditorEditAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
	if (command.equalsIgnoreCase(CreditorEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(CreditorEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Applies changes made to the creditor and its address to the database.  A web 
     * service from the Contacts application is used to apply updates to the creditor's 
     * address information.  After successfully updating the changes, the creditor's 
     * profile is retrieved from the database as confirmation.  Special processing steps 
     * must occur when perfoming insert, update, and delete database operations using 
     * local and remote database contexts.   For inserts, the logic ensures that contact 
     * data (business and address) updated remotely is properly rollbacked in the event 
     * the base creditor insert operation fails.  For updates or deletes, the logic guarantees 
     * that the base creditor is successfully persisted before the business and address contact 
     * changes are persisted. 
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	int busId = 0;
	
	// Update creditor business contact data.
	JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory();
	BusinessType bt = jaxbUtil.createtBusinessType(request);
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	try {
	    busId = jaxbUtil.updateBusinessContactData(bt, userSession.getLoginId());
	}
	catch (MessagingException e) {
	    throw new ActionHandlerException(e);
	}
	
	// Update creditor data
	DatabaseTransApi tx = DatabaseTransFactory.create();
	Creditor creditor = (Creditor) this.cred;
	boolean error = false;
	boolean creditorNew = creditor.getCreditorId() == 0;
	try {
	    this.msg = "Creditor profile was updated successfully";
	    try {
		creditor.setBusinessId(busId);
		super.save();
	    }
	    catch (Exception e) {
		this.logger.log(Level.ERROR, e.getMessage());
		this.msg = e.getMessage();
		error = true;
	    }
	    if (error) {
		if (creditorNew) {
		    // Send request to the Contacts system to delete the contact record just added above when update for new customer fails.
    		   try {
    		       jaxbUtil.deleteBusinessContactData(busId, userSession.getLoginId());
    		    }
    		    catch (MessagingException e) {
    			this.logger.log(Level.ERROR, e.getMessage());
    			this.msg += ".  " + e.getMessage();
    			throw new ActionHandlerException(this.msg);
    		    }
		}
	    }
	    tx.commitUOW();
	    return;
	}
	catch (ActionHandlerException e) {
	    tx.rollbackUOW();
	    throw e;
	}
	finally {
	    tx.close();
	    tx = null;
	    this.edit();
	}
    }


    /**
     * Navigates the user to the Creditor/Vendor search page.   The selection criteria that was used 
     * to fetch creditor list during the previous search session is obtained from the web server session 
     * in order to perform the query.
     *  
     * @throws ActionHandlerException General database errors.
     */
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	CreditorSearchAction action = new CreditorSearchAction();
	action.processRequest(this.request, this.response, CreditorSearchAction.COMMAND_LIST);
    }
}
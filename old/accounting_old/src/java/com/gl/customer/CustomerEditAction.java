package com.gl.customer;


import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.Customer;
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
 * This class provides action handlers needed to serve Customer Maintenance 
 * Edit user interface requests.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerEditAction extends CustomerAction {
    private static final String COMMAND_SAVE = "Customer.Edit.save";

    private static final String COMMAND_BACK = "Customer.Edit.back";

    private Logger logger;
    
    

    /**
     * Default constructor
     *
     */
    public CustomerEditAction() {
	super();
	logger = Logger.getLogger("CustomerEditAction");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CustomerEditAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	logger = Logger.getLogger(CustomerEditAction.class);
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
     * Obtains key customer data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	
	
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request The HttpRequest object
     * @param response The HttpResponse object
     * @param command Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);

	if (command.equalsIgnoreCase(CustomerEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(CustomerEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Applies customer edit changes to the database.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	int busId = 0;
	
	// Update business contact data
	JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory();
	BusinessType bt = jaxbUtil.createtBusinessType(request);
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	try {
	    busId = jaxbUtil.updateBusinessContactData(bt, userSession.getLoginId());
	}
	catch (MessagingException e) {
	    throw new ActionHandlerException(e);
	}
	
	// Update Creditor data.
	DatabaseTransApi tx = DatabaseTransFactory.create();
	Customer customer = (Customer) this.cust;
	boolean error = false;
	boolean customerNew = customer.getCustomerId() == 0;

	try {
	    this.msg = "Customer profile was updated successfully";
	    try {
		customer.setBusinessId(busId);
		super.save();
	    }
	    catch (Exception e) {
		this.logger.log(Level.ERROR, e.getMessage());
		this.msg = e.getMessage();
		error = true;
	    }
	    if (error) {
		if (customerNew) {
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
     * Fetches the list customers from the database using the where clause criteria 
     * previously stored on the session during the phase of the request to builds 
     * the query predicate.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	CustomerSearchAction action = new CustomerSearchAction();
	action.processRequest(this.request, this.response, CustomerSearchAction.COMMAND_LIST);
    }
}
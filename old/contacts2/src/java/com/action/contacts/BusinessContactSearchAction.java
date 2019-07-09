package com.action.contacts;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.business.BusinessFactory;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.RMT2TagQueryBean;
import com.bean.VwBusinessAddress;

import com.bean.db.DatabaseConnectionBean;

import com.services.handlers.BusinessContactFetchHandler;

import com.util.SystemException;

/**
 * This action handler provides functionality to service the searching needs of 
 * business contact entities.
 * 
 * @author Roy Terrell
 * 
 */
public class BusinessContactSearchAction extends AbstractContactSearchAction implements ICommand {
    /** Command name for new contact search */
    protected static final String COMMAND_NEWSEARCH = "Business.Search.newsearch";

    /** Command name for contact search */
    protected static final String COMMAND_SEARCH = "Business.Search.search";

    /** Command name for add contact */
    protected static final String COMMAND_ADD = "Business.Search.add";

    /** Command name for edit contact */
    protected static final String COMMAND_EDIT = "Business.Search.edit";

    /** Command name for navigating back to home page */
    protected static final String COMMAND_BACK = "Business.Search.back";

    /** Command name for reseting the UI components. */
    protected static final String COMMAND_RESET = "Business.Search.reset";

    private Logger logger;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public BusinessContactSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("BusinessContactSearchAction");
    }

    
    /**
     * Initializes the personal contact api needed by this api.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	logger.log(Level.INFO, "Initializing Personal Contact Api's");
//	this.api = BusinessFactory.createBusinessApi(this.dbConn, this.request);
    }

    /**
     * Driver for processing the client's Business Contact Search page.   The following 
     * commands are serviced in this action handler: 
     * {@link com.action.contacts.BusinessContactSearchAction.COMMAND_NEWSEARCH New Search}, 
     * {@link com.action.contacts.BusinessContactSearchAction.COMMAND_SEARCH Search}, 
     * {@link com.action.contacts.BusinessContactSearchAction.COMMAND_ADD Add Contact}, and 
     * {@link com.action.contacts.BusinessContactSearchAction.COMMAND_EDIT Edit Contact}.
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
	if (command.equalsIgnoreCase(BusinessContactSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(BusinessContactSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(BusinessContactSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(BusinessContactSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(BusinessContactSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(BusinessContactSearchAction.COMMAND_RESET)) {
	    this.doReset();
	}
    }

    /**
     * Intializes the base view to <i>VwBusinessAddressView</i> for a business contact query 
     * and invokes ancestor functionality to establish a ContactCriteria instance from from 
     * the user's request. 
     * 
     * @return {@link UserCriteria}
     * @throws ActionHandlerException problem occurs creating the criteria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	Object criteriaObj = super.doCustomInitialization();

	// Set data source so that the build-criteria process can discover field names.
	this.baseView = "VwBusinessAddressView";
	this.logger.log(Level.DEBUG, "Contact base DataSource view was initialized to " + this.baseView);
	return criteriaObj;
    }

    /**
     * Force an empty result set by purposely constructing erroneous selection criteria.   
     * Set database columns, business_id, equal to -1 for the business contact query.
     * 
     * @param queryBean The Query Object.
     * @return The selection criteria.as a String
     * @throws ActionHandlerException
     */
    protected String doInitialCriteria(RMT2TagQueryBean queryBean) throws ActionHandlerException {
	String criteria = null;
	super.doInitialCriteria(queryBean);
	criteria = "business_id = -1";
	return criteria;
    }

    /**
     * Creates a new {@link com.bean.VwBusinessAddress VwBusinessAddress} instance used 
     * to service the add contact command. 
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	super.add();
	this.contact = BusinessFactory.createBusinessAddress();
	return;
    }

    /**
     * Ensures that the business contact instance is properly initialized in the event that 
     * the contact is not available from the database. 
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = BusinessFactory.createBusinessApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    super.edit();
	    if (this.contact == null) {
		this.contact = BusinessFactory.createBusinessAddress();
	    }    
	}
	catch (ActionHandlerException e) {
	    throw e;
	}
	finally {
	    this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null; 
	}
	
    }

    /**
     * Build selection criteria for business contact query.
     * 
     * @return Selection criteria as a String.
     */
    protected String createCotnactCriteria() {
	// Build selection criteria for person-contact query.
	StringBuffer criteria = new StringBuffer(100);
	if (this.getContactId() != null) {
	    criteria.append("business_id = " + this.getContactId());
	}
	if (this.getAddressId() != null) {
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append(" addr_id = " + this.getAddressId());
	}
	return criteria.toString();
    }
    
    /**
     * Sets the results of the business contact query on the request object in the form of 
     * a java object and XML as attributes, {@link com.api.ContactsConst#CLIENT_DATA CLIENT_DATA} and 
     * {@link com.constants.RMT2ServletConst.RESPONSE_NONJSP_DATA RESPONSE_NONJSP_DATA}, respectively. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        super.sendClientData();
        String xml = this.getXmlResults();
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
    }
    
    /**
     * Creates the response message, RS_business_contact_search, from the java results obtained from 
     * the query which returns a single business contact record.  Mimics a web service functionality 
     * by converting the business contact, which is a java object, to XML and appending the results 
     * of the conversion to the RS_business_contact_search message.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    protected String getXmlResults() throws ActionHandlerException {
	VwBusinessAddress busContact = null;
	if (this.contact instanceof VwBusinessAddress) {
	    busContact = (VwBusinessAddress) this.contact;
	}
	String responseMsgId = "RS_business_contact_search";
	BusinessContactFetchHandler srvc = new BusinessContactFetchHandler(null, this.request);
	srvc.setResponseServiceId(responseMsgId);
	return srvc.buildSingleItemResponsePayload(busContact, this.msg);
    }
    
}
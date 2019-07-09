package com.action.contacts;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.personal.PersonFactory;

import com.bean.RMT2TagQueryBean;
import com.bean.VwPersonAddress;

import com.bean.db.DatabaseConnectionBean;

import com.services.handlers.PersonContactFetchHandler;
import com.util.SystemException;




/**
 * This action handler provides functionality to service the searching needs of 
 * personal contact entities.
 * 
 * @author Roy Terrell
 * 
 */
public class PersonContactSearchAction extends AbstractContactSearchAction implements ICommand {
    /** Command name for new contact search */
    protected static final String COMMAND_NEWSEARCH = "Person.Search.newsearch";

    /** Command name for contact search */
    protected static final String COMMAND_SEARCH = "Person.Search.search";

    /** Command name for add contact */
    protected static final String COMMAND_ADD = "Person.Search.add";

    /** Command name for edit contact */
    protected static final String COMMAND_EDIT = "Person.Search.edit";

    /** Command name for navigating back to home page */
    protected static final String COMMAND_BACK = "Person.Search.back";

    /** Command name for reseting the UI components. */
    protected static final String COMMAND_RESET = "Person.Search.reset";

    private Logger logger;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public PersonContactSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("PersonContactSearchAction");
    }

    /**
     * Initializes the personal contact api needed by this api.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	logger.log(Level.INFO, "Initializing Personal Contact Api's");
	//	this.api = PersonFactory.createPersonApi(this.dbConn, this.request);
    }

    /**
     * Driver for processing the client's Personal Contact Search page.   The following 
     * commands are serviced in this action handler: 
     * {@link com.action.contacts.PersonContactSearchAction.COMMAND_NEWSEARCH New Search}, 
     * {@link com.action.contacts.PersonContactSearchAction.COMMAND_SEARCH Search}, 
     * {@link com.action.contacts.PersonContactSearchAction.COMMAND_ADD Add Contact}, and 
     * {@link com.action.contacts.PersonContactSearchAction.COMMAND_EDIT Edit Contact}.
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
	if (command.equalsIgnoreCase(PersonContactSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(PersonContactSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(PersonContactSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(PersonContactSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(PersonContactSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(PersonContactSearchAction.COMMAND_RESET)) {
	    this.doReset();
	}
    }

    /**
     * Intializes the base view to <i>VwPersonAddressView</i> for personal contact query 
     * and invokes ancestor functionality to establish a ContactCriteria instance from 
     * from the user's request. 
     * 
     * @return An arbitrary object containing criteria data.
     * @throws ActionHandlerException problem occurs creating the criteria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	Object criteriaObj = super.doCustomInitialization();

	// Set data source so that the build-criteria process can discover field names.
	this.baseView = "VwPersonAddressView";
	this.logger.log(Level.DEBUG, "Contact base DataSource view was initialized to " + this.baseView);
	return criteriaObj;
    }

    /**
     * Force an empty result set by purposely constructing erroneous selection criteria.   
     * Set database column, person_id, equal to -1 for the personal contact query.
     * 
     * @param queryBean The Query Object.
     * @return The selection criteria.as a String
     * @throws ActionHandlerException
     */
    protected String doInitialCriteria(RMT2TagQueryBean queryBean) throws ActionHandlerException {
	String criteria = null;
	super.doInitialCriteria(queryBean);
	criteria = "person_id = -1";
	return criteria;
    }

    /**
     * Creates a new {@link com.bean.VwPersonAddress VwPersonAddress} instance used 
     * to service the add contact command. 
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	super.add();
	this.contact = PersonFactory.createPersonAddress();
	return;
    }

    /**
     * Ensures that the contact instance is properly initialized in the event that 
     * the contact is not available from the database. 
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = PersonFactory.createPersonApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    super.edit();
	    if (this.contact == null) {
		this.contact = PersonFactory.createPersonAddress();
	    }
	    return;
	}
	catch (ActionHandlerException e) {
	    throw e;
	}
	finally {
	    this.api.close();
	    this.api = null;
	    tx.close();
	    tx = null;
	}

    }

    /**
     * Build selection criteria for personal contact query.
     * 
     * @return Selection criteria as a String.
     */
    protected String createCotnactCriteria() {
	// Build selection criteria for person-contact query.
	StringBuffer criteria = new StringBuffer(100);
	if (this.getContactId() != null) {
	    criteria.append("person_id = " + this.getContactId());
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
     * Sets the results of the personal contact query on the request object in the form of 
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
     * Creates the response message, RS_personal_contact_search, from the java results obtained from 
     * the query which returns a single person record.  Mimics a web service functionality by converting 
     * the personal contact, which is a java object, to XML and appending the results of the conversion 
     * to the RS_personal_contact_search message.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    protected String getXmlResults() throws ActionHandlerException {
        // Mimic web service to return response as XML!
        VwPersonAddress obj = null;
        if (this.contact instanceof VwPersonAddress) {
            obj = (VwPersonAddress) this.contact;
        }
        String responseId = "RS_personal_contact_search";
        PersonContactFetchHandler srvc = new PersonContactFetchHandler(null, this.request);
        return srvc.buildSingleItemResponsePayload(obj, this.msg, responseId);
    }
    
    
}
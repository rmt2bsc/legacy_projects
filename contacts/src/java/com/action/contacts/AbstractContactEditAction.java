package com.action.contacts;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.Contact;
import com.api.ContactException;
import com.api.ContactsConst;

import com.api.address.AddressFactory;
import com.api.address.AddressApi;

import com.api.db.orm.DataSourceAdapter;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.Address;
import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;

import com.util.SystemException;
import com.xml.schema.misc.PayloadFactory;


/**
 * This abstract action handler provides common functionality to respond 
 * to the requests originating from a contact edit page.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class AbstractContactEditAction extends AbstractActionHandler implements ICommand {
    /** Command name for saving a contact */
    protected static final String COMMAND_SAVE = "Contact.Search.save";

    /** Command name for deleting a contact */
    protected static final String COMMAND_DELETE = "Contact.Search.delete";

    /** Command name for navigating back to the previous page from contact edit page */
    protected static final String COMMAND_BACK = "Contact.Search.back";

    /** The current command */
    protected String command;

    /** Address api instance */
    protected AddressApi addrApi;

    /** Business api instance */
    protected Contact api;

    private Object contact;

    private Address addrObj;

    private Logger logger;
    
    private int contactId;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AbstractContactEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("AbstractContactEditAction");
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	logger.log(Level.INFO, "Initializing Common Contact Api's");
	this.addrObj = AddressFactory.createAddress();
    }

    /**
     * Driver for processing the client's Contact Search page.   The following commands are 
     * serviced in this action handler: {@link com.action.contacts.AbstractContactEditAction.COMMAND_SAVE Save Contact}, 
     * {@link com.action.contacts.AbstractContactEditAction.COMMAND_DELETE Delete Contact}, 
     * {@link com.action.contacts.AbstractContactEditAction.COMMAND_BACK Navigate Backwards}.
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
	try {
	    this.init(null, request);
	    this.init();
	    this.command = command;
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e);
	}

	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	if (command.equalsIgnoreCase(AbstractContactEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(AbstractContactEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(AbstractContactEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Contains generic logic that can persist the changes made to a {@link com.api.Contact Contact}.
     * THe update process involves validating the contact and its address, saving the contact, 
     * ensuring theat the contact and address are associated, and saving the contact's address.
     *   
     * @throws ActionHandlerException General database errors.
     */
    public void save() throws ActionHandlerException {
	try {
	    this.validateContact(this.contact, this.addrObj);
	    this.api.maintainContact(this.contact);
	    this.preAddressUpdate(this.addrObj, this.contact);
	    this.addrApi.maintainContact(this.addrObj);
	    this.msg = "Contact update completed successfully";
	}
	catch (Exception e) {
	    this.msg = "Contact update completed with errors";
	    throw new ActionHandlerException(e);
	}

    }

    /**
     * Queries the database for a single contact using the primary keys of the 
     * internal contact and address data members as selection criteria, if available.
     * It is of the developer's responsibility to code the logic needed to build custom
     * selection criteria at the descendent via the 
     * {@link com.action.contacts.AbstractContactEditAction#createCotnactCriteria() createCotnactCriteria()} 
     * method.
     * <p>
     * For contact modifications it is safe to assume that this refresh logic 
     * will guarantee that the contact will be retrieved from the database as a 
     * confirmation.  For situations when deleting a contact, the confirmation is 
     * dependent on the contact data stored internally which was initially used to 
     * display the page before invoking the delete operation.   
     * 
     * @throws ActionHandlerException 
     *           When the contact api is invalid or general database error when 
     *           attempting to retrieve the contact.
     */
    protected void refreshContact() throws ActionHandlerException {
	// No need to contiune if api is not initialized
	if (this.api == null) {
	    this.msg = "Refresh of contact failed.  Contact api is not initialized";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	// Get custom selection criteria.
	String criteria = this.createRefreshCriteria();

	// Query the database in order to create a contact object.
	try {
	    List<Object> contactList = (List<Object>) this.api.findContact(criteria);
	    if (contactList != null && contactList.size() > 0) {
		this.contact = contactList.get(0);
	    }
	    else {
		this.contact = null;
	    }
	}
	catch (ContactException e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Override this method to provide custom selection criteria for 
     * retrieving a single contact from an external data source.
     * 
     * @return null.
     */
    protected String createRefreshCriteria() {
	return null;
    }

    /**
     * Gathers contact's address data from the request. 
     * 
     * @throws ActionHandlerException 
     *           When the cotnact's address id is invalid or cannot be verified.
     */
    protected void receiveClientData() throws ActionHandlerException {
	try {
	    DataSourceAdapter.packageBean(this.request, this.addrObj);
	    // Ensure that adderss id is gathered.
	    String temp = this.request.getParameter("AddrId");
	    int addId = Integer.parseInt(temp);
	    this.addrObj.setAddrId(addId);
	}
	catch (SystemException e) {
	    throw new ActionHandlerException(e);
	}
	catch (NumberFormatException e) {
	    this.msg = "Problem identifying address id from the contact edit page";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	return;
    }

    
    /**
     * Sets the results of the personal conact update on the request object in the form of 
     * a java object and XML as attributes, {@link com.api.ContactsConst#CLIENT_DATA CLIENT_DATA} and 
     * {@link com.constants.RMT2ServletConst.RESPONSE_NONJSP_DATA RESPONSE_NONJSP_DATA}, respectively. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        String xml = this.getXmlResults();
        this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
        this.request.setAttribute(ContactsConst.CLIENT_DATA, this.contact);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }
    
    /**
     * Creates the response message, RS_common_reply, for the personal contact update call.  Mimics a 
     * web service functionality by by sending the results as XML.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    protected String getXmlResults() throws ActionHandlerException {
        // Mimic web service to return response as XML!
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	return PayloadFactory.buildCommonPayload(this.contactId, this.msg,  userSession.getLoginId());
    }
    

    /**
     * Stub implementation for responding to the <i>back</i> request.
     *
     */
    protected void doBack() {
	return;
    }

    /**
     * Sets the target contact.
     * 
     * @param contact the contact to set
     */
    public void setContact(Object contact) {
	this.contact = contact;
    }

    /**
     * Get the target contact.
     * 
     * @return An arbitary object representing the contact.
     */
    public Object getContact() {
	return this.contact;
    }

    /**
     * Get target address object.
     * 
     * @return the addrObj
     */
    public Address getAddrObj() {
	return addrObj;
    }

    /**
     * Set the target address object.
     * 
     * @param addrObj the addrObj to set
     */
    public void setAddrObj(Address addrObj) {
	this.addrObj = addrObj;
    }

    /**
     * Validate the contact and its address.
     * 
     * @param contact The contact.
     * @param address The contact's address.
     * @throws ContactException validation error.
     */
    protected abstract void validateContact(Object contact, Address address) throws ContactException;

    /**
     * This method is triggered just before the address instance is updated.
     * 
     * @param address The address instance to update
     * @param contact The cotnact that is to be assoicated with the address.
     */
    protected abstract void preAddressUpdate(Address address, Object contact);

    /**
     * Stub implementation for descendent.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	return;
    }

    /**
     * Stub implementation for descendent.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	return;
    }

    /**
     * Stub implementation for descendent.
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	return;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}
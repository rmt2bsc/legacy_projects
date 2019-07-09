package com.action.contacts;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.ContactException;

import com.api.address.AddressFactory;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;

import com.api.personal.PersonException;
import com.api.personal.PersonFactory;

import com.bean.Address;
import com.bean.Person;
import com.bean.VwPersonAddress;

import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2String;
import com.util.SystemException;


/**
 * Action handler for managing request coming from the Personal Contact Edit page.  
 * The handler responds to save, delete, and back commands.
 * 
 * @author RTerrell
 *
 */
public class PersonContactEditAction extends AbstractContactEditAction {
    /** Command name for saving changes to contact */
    protected static final String COMMAND_SAVE = "Person.Edit.save";

    /** Command name for deleting contact record */
    protected static final String COMMAND_DELETE = "Person.Edit.delete";

    /** Command name for navigating back to personal contact search page */
    protected static final String COMMAND_BACK = "Person.Edit.back";

    private Logger logger;

    /**
     * Creates an instance of PersonContactEditAction by initializing the logger.
     * 
     * @throws SystemException
     */
    public PersonContactEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("PersonContactEditAction");
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	logger.log(Level.INFO, "Initializing Personal Contact Edit Action handler");
    }

    /**
     * Driver for processing the client's Personal Contact Search page.   The following 
     * commands are serviced in this action handler: 
     * {@link com.action.contacts.PersonContactEditAction.COMMAND_NEWSEARCH New Search}, 
     * {@link com.action.contacts.PersonContactEditAction.COMMAND_SEARCH Search}, 
     * {@link com.action.contacts.PersonContactEditAction.COMMAND_ADD Add Contact}, and 
     * {@link com.action.contacts.PersonContactEditAction.COMMAND_EDIT Edit Contact}.
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
	if (command.equalsIgnoreCase(PersonContactEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(PersonContactEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(PersonContactEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Saves the modifications of contact which are persisted to the database.  After 
     * successfully saving the data, the model contact object, {@link com.bean.VwPersonAddress VwPersonAddress} 
     * is refreshed from the database so that it may be sent to the client for presentation. 
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.addrApi = AddressFactory.createAddressApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.api = PersonFactory.createPersonApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    super.save();
	    tx.commitUOW();
	    this.refreshContact();
	    this.msg = "Personal Contact was saved successfully";
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = e.getMessage();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.addrApi.close();
            this.api.close();
            tx.close();
            this.addrApi = null;
            this.api = null;
            tx = null;
        }
    }

    /**
     * Tries to guarantee the contact instance is properly initialized as a instance of 
     * {@link com.bean.VwPersonAddress VwPersonAddress} after an update or delete 
     * operation.
     * 
     * @throws ActionHandlerException
     */
    protected void refreshContact() throws ActionHandlerException {
	super.refreshContact();
	if (this.getContact() == null) {
	    this.setContact(PersonFactory.createPersonAddress());
	}
    }

    /**
     * Build selection criteria for personal contact query.
     * 
     * @return RDBMS selection criteria as a String.
     */
    protected String createRefreshCriteria() {
	int personId = ((Person) this.getContact()).getPersonId();
	int addressId = ((Address) this.getAddrObj()).getAddrId();
	StringBuffer criteria = new StringBuffer(100);
	criteria.append("person_id = " + personId);
	if (criteria.length() > 0) {
	    criteria.append(" and ");
	}
	criteria.append(" addr_id = " + addressId);
	return criteria.toString();
    }

    /**
     * Gathers person contact data from the request. 
     * 
     * @throws ActionHandlerException Error obtaining person data.
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	Person per = PersonFactory.createPerson();
	try {
	    DataSourceAdapter.packageBean(this.request, per);
	    this.setContact(per);
	    return;
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Deletes a personal contact from the database.
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = PersonFactory.createPersonApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	super.delete();
	try {
	    this.api.deleteContact(this.getContact());
	    String contactInfo = "id=" + ((Person)this.getContact()).getPersonId() + ", name=" + ((Person)this.getContact()).getFirstname() + " " + ((Person)this.getContact()).getLastname();
	    this.msg = "Person contact [" + contactInfo + "] was deleted successfully";
	    tx.commitUOW();
	}
	catch (ContactException e) {
	    this.msg = e.getMessage();
	    this.setError(true);
	    tx.rollbackUOW();
	}
	finally {
	    this.refreshContact();
            this.api.close();
            tx.close();
            this.api = null;
            tx = null;
	}
	return;
    }

    /**
     * Associates the personal contact id with <i>address</i>.
     * 
     * @param address The address instance to update
     * @param contact The cotnact that is to be assoicated with the address.
     */
    protected void preAddressUpdate(Address address, Object contact) {
	Person contactObj = null;
	// Associate person id with address if we are adding contact
	if (contact != null) {
	    contactObj = (Person) contact;
	}
	address.setPersonId(contactObj.getPersonId());
	return;
    }

    /**
     * Validates a instance of {@link com.api.Contact Contact} as personal contact.
     * 
     * @param address The address instance to update
     * @param contact The personal cotnact that is to be assoicated with the address.
     * @param contactId The id of the contact assoicated with the address.
     * @throws ContactException 
     *            if <i>contact</i> is not an instance of {@link com.bean.Person Person} 
     *            or is null, or <i>address</i> is null, or if either one of the id's of 
     *            <i>contact</i> or <i>address</i> are invalid, or if either the contact's 
     *            first name or last name are null. 
     */
    protected void validateContact(Object contact, Address address) throws ContactException {
	Person person = null;
	int intKey;
	String strKey;

	// Cast person object
	if (contact instanceof Person) {
	    person = (Person) contact;
	}

	// Ensure that person object is valid
	if (person == null) {
	    throw new PersonException("Person object is invalid");
	}

	// Ensure that address object is valid
	if (address == null) {
	    throw new PersonException("Address object of person contact is invalid");
	}

	//  Ensure that the person id value is set
	try {
	    strKey = this.request.getParameter("PersonId");
	    intKey = Integer.valueOf(strKey).intValue();
	    person.setPersonId(intKey);
	}
	catch (NumberFormatException e) {
	    throw new PersonException("Person Id is of an invalid type");
	}

	//  Ensure that the address id value is set
	try {
	    strKey = this.request.getParameter("AddrId");
	    intKey = Integer.valueOf(strKey).intValue();
	    address.setAddrId(intKey);
	}
	catch (NumberFormatException e) {
	    throw new PersonException("Address Id of the person contact is of an invalid type");
	}

	if (person.getFirstname() != null && person.getFirstname().length() > 0 && RMT2String.spaces(person.getFirstname().length()) != person.getFirstname()) {
	    // continue
	}
	else {
	    throw new PersonException("First name is required for Person object");
	}
	if (person.getLastname() != null && person.getLastname().length() > 0 && RMT2String.spaces(person.getLastname().length()) != person.getLastname()) {
	    // continue
	}
	else {
	    throw new PersonException("Last name is required for Person object");
	}
    }

    
    /**
     * Obtains the person id from the common contact instance, which at runtime is of type Person. and assigns 
     * it to the ancestor class as the contactId property.   This will allow the ancestor to build the common reply 
     * message with the appropriate person id.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    public String getXmlResults() throws ActionHandlerException {
	VwPersonAddress per = (VwPersonAddress) this.getContact();
	this.setContactId(per.getPersonId());
	return super.getXmlResults();
    }
    
  
}

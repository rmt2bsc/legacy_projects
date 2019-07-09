package com.action.contacts;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.api.ContactException;

import com.api.address.AddressFactory;
import com.api.business.BusinessException;
import com.api.business.BusinessFactory;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceAdapter;

import com.bean.Address;
import com.bean.Business;
import com.bean.VwBusinessAddress;
import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2String;
import com.util.SystemException;



/**
 * @author RTerrell
 *
 */
public class BusinessContactEditAction extends AbstractContactEditAction {
    /** Command name for saving changes to contact */
    protected static final String COMMAND_SAVE = "Business.Edit.save";

    /** Command name for deleting contact record */
    protected static final String COMMAND_DELETE = "Business.Edit.delete";

    /** Command name for navigating back to personal contact search page */
    protected static final String COMMAND_BACK = "Business.Edit.back";

    private Logger logger;

    /**
     * Creates an instance of BusinessContactEditAction by initializing the logger.
     * 
     * @throws SystemException
     */
    public BusinessContactEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("BusinessContactEditAction");
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	logger.log(Level.INFO, "Initializing Business Contact Edit Action handler");
	//        this.api =  BusinessFactory.createBusinessApi(this.dbConn, this.request);
    }

    /**
     * Driver for processing the client's Business Contact Search page.   The following 
     * commands are serviced in this action handler: 
     * {@link com.action.contacts.BusinessContactEditAction.COMMAND_NEWSEARCH New Search}, 
     * {@link com.action.contacts.BusinessContactEditAction.COMMAND_SEARCH Search}, 
     * {@link com.action.contacts.BusinessContactEditAction.COMMAND_ADD Add Contact}, and 
     * {@link com.action.contacts.BusinessContactEditAction.COMMAND_EDIT Edit Contact}.
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
	if (command.equalsIgnoreCase(BusinessContactEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(BusinessContactEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(BusinessContactEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Saves the modifications of a business contact which are persisted to the database.  
     * After successfully saving the data, the model contact object, 
     * {@link com.bean.VwPersonAddress VwPersonAddress} is refreshed from the database so 
     * that it may be sent to the client for presentation. 
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.addrApi = AddressFactory.createAddressApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.api = BusinessFactory.createBusinessApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    super.save();
	    tx.commitUOW();
	    this.refreshContact();
	    this.msg = "Business Contact was saved successfully";
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
     * Guarantees that the business contact instance is properly initialized as a 
     * instance of {@link com.bean.VwPersonAddress VwPersonAddress} after an update 
     * or delete operation.
     * 
     * @throws ActionHandlerException
     */
    protected void refreshContact() throws ActionHandlerException {
	super.refreshContact();
	if (this.getContact() == null) {
	    this.setContact(BusinessFactory.createBusinessAddress());
	}
    }

    /**
     * Build selection criteria for business contact query.
     * 
     * @return RDBMS selection criteria as a String.
     */
    protected String createRefreshCriteria() {
	int busId = ((Business) this.getContact()).getBusinessId();
	int addressId = ((Address) this.getAddrObj()).getAddrId();
	StringBuffer criteria = new StringBuffer(100);
	criteria.append("business_id = " + busId);
	if (criteria.length() > 0) {
	    criteria.append(" and ");
	}
	criteria.append(" addr_id = " + addressId);
	return criteria.toString();
    }

    /**
     * Gathers business contact data from the request. 
     * 
     * @throws ActionHandlerException Error obtaining business data.
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	Business bus = BusinessFactory.createBusiness();
	try {
	    DataSourceAdapter.packageBean(this.request, bus);
	    this.setContact(bus);
	    return;
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Deletes a business contact from the database.
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = BusinessFactory.createBusinessApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.deleteContact(this.getContact());
	    String contactInfo = "id=" + ((Business) this.getContact()).getBusinessId() + ", name=" + ((Business) this.getContact()).getLongname();
	    this.msg = "Business contact [" + contactInfo + "] was deleted successfully";
	    tx.commitUOW();
	}
	catch (ContactException e) {
	    this.msg = e.getMessage();
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
     * Associates the business contact id with <i>address</i>.
     * 
     * @param address The address instance to update
     * @param contact The cotnact that is to be assoicated with the address.
     */
    protected void preAddressUpdate(Address address, Object contact) {
	Business contactObj = null;

	// Associate business id with address if we are adding contact
	if (contact instanceof Business) {
	    contactObj = (Business) contact;
	}
	address.setBusinessId(contactObj.getBusinessId());
	return;
    }

    /**
     * Validates a instance of {@link com.api.Contact Contact} as a business contact.
     * 
     * @param address The address instance to update
     * @param contact The business cotnact that is to be assoicated with the address.
     * @param contactId The id of the contact assoicated with the address.
     * @throws ContactException 
     *            if <i>contact</i> is not an instance of {@link com.bean.Business Business} 
     *            or is null, or <i>address</i> is null, or if either one of the id's of 
     *            <i>contact</i> or <i>address</i> are invalid, or the business contact's 
     *            long name is null. 
     */
    protected void validateContact(Object contact, Address address) throws ContactException {
	Business bus = null;
	int intKey;
	String strKey;

	// Cast business object
	if (contact instanceof Business) {
	    bus = (Business) contact;
	}

	// Ensure that business object is valid
	if (bus == null) {
	    throw new BusinessException("Business contact object is invalid");
	}

	// Ensure that address object is valid
	if (address == null) {
	    throw new BusinessException("Address object of business contact is invalid");
	}

	//  Ensure that the business id value is set
	try {
	    strKey = this.request.getParameter("BusinessId");
	    intKey = Integer.valueOf(strKey).intValue();
	    bus.setBusinessId(intKey);
	}
	catch (NumberFormatException e) {
	    throw new BusinessException("Business Id is of an invalid type");
	}

	//  Ensure that the address id value is set
	try {
	    strKey = this.request.getParameter("AddrId");
	    intKey = Integer.valueOf(strKey).intValue();
	    address.setAddrId(intKey);
	}
	catch (NumberFormatException e) {
	    throw new BusinessException("Address Id of the business contact is of an invalid type");
	}

	if (bus.getLongname() != null && bus.getLongname().length() > 0 && RMT2String.spaces(bus.getLongname().length()) != bus.getLongname()) {
	    // continue
	}
	else {
	    throw new BusinessException("Business name is required");
	}
    }
    
    /**
     * Obtains the business id from the common contact instance, which at runtime is of type Business. and assigns 
     * it to the ancestor class as the contactId property.   This will allow the ancestor to build the common reply 
     * message with the appropriate business id.
     * 
     * @return The XML Message
     * @throws ActionHandlerException
     */
    public String getXmlResults() throws ActionHandlerException {
	VwBusinessAddress bus = (VwBusinessAddress) this.getContact();
	this.setContactId(bus.getBusinessId());
	return super.getXmlResults();
    }

}

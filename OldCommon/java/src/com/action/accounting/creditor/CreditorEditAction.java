package com.action.accounting.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.api.ContactBusinessApi;
import com.api.ContactAddressApi;
import com.api.db.DatabaseException;

import com.factory.ContactsApiFactory;
import com.factory.AcctManagerFactory;

import com.util.ContactAddressException;
import com.util.ContactBusinessException;
import com.util.CreditorException;
import com.util.SystemException;

/**
 * This class provides functionality needed to serve the requests for the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class CreditorEditAction extends CreditorAction {
	private static final String COMMAND_SAVE = "Accounting.CreditorEdit.save";
	private static final String COMMAND_BACK = "Accounting.CreditorEdit.back";
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
	public CreditorEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
		super(_context, _request);
		this.init(this.context, this.request);
	}

	/**
	 * Initializes this object using _conext and _request. This is needed in the
	 * event this object is inistantiated using the default constructor.
	 * 
	 * @throws SystemException
	 */
	protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
		super.init(_context, _request);
		try {
			api = AcctManagerFactory.createCreditor(this.dbConn, _request);
		} catch (Exception e) {
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
	public void processRequest(HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
		super.processRequest(request, response, command);
		if (command.equalsIgnoreCase(CreditorEditAction.COMMAND_SAVE)) {
			this.saveData();
		}
		if (command.equalsIgnoreCase(CreditorEditAction.COMMAND_BACK)) {
			this.doBack();
		}
	}

	
	/**
	 * Applies creditor edit changes to the database.
	 * 
	 * @throws ActionHandlerException
	 */
	public void save() throws ActionHandlerException {
	  	ContactBusinessApi busApi = null;
	  	ContactAddressApi addrApi = null;
	  	int key = 0;
	  	
	  	try {
	  		busApi = ContactsApiFactory.createBusinessApi(this.dbConn);
		  	addrApi = ContactsApiFactory.createAddressApi(this.dbConn);	
	  	}
	  	catch (Exception e) {
	  		throw new ActionHandlerException(e.getMessage());
	  	}
	  	
	  	// Ensure that the business id and the Address Id are properly associated with the Business and Address
	  	// objects since their key values could have been wrongfully obtain in the previos steps.
	  	try {
	  		//validate objects
	      	busApi.validateBusiness(this.bus);
	      	this.api.validateCreditor(this.cred, null);
	      	      	
			// Apply Business Updates to database
			key = busApi.maintainBusiness(this.bus);
			// Apply Address Updates to database
			this.addr.setBusinessId(key);
			key = addrApi.maintainAddress(this.addr);
			
			// Apply Creditor Updates to database
			this.cred.setBusinessId(this.bus.getId());
			key = api.maintainCreditor(this.cred, null);
			this.transObj.commitUOW();
			
			// Fetch saved data as confirmation to the client
			this.fetchCreditor();
			this.msg = "Creditor profile was updated successfully";
			return;
			
		}
		catch (NumberFormatException e) {
			this.msg = "Update Failed: could not properly obtain Business Id from form due to a NumberFormatException";
			throw new ActionHandlerException(this.msg);
		}
		catch (ContactBusinessException e) {
			this.msg = "ContactBusinessException: " + e.getMessage();
			logger.log(Level.ERROR, this.msg);
			this.transObj.rollbackUOW();
			throw new ActionHandlerException(e);
		}
		catch (ContactAddressException e) {
			this.msg = "ContactAddressException: " + e.getMessage();
			logger.log(Level.ERROR, this.msg);
			this.transObj.rollbackUOW();
			throw new ActionHandlerException(e);
		}
		catch (CreditorException e) {
			this.msg = "CreditorException: " + e.getMessage();
			logger.log(Level.ERROR, this.msg);
			this.transObj.rollbackUOW();
			throw new ActionHandlerException(e);
		}
	}
	
	private void doBack() throws ActionHandlerException {
		
	}
}
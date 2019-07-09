package com.action.accounting.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.GLCreditorApi;
import com.api.db.DatabaseException;
import com.api.DaoApi; 
import com.api.db.orm.DataSourceFactory;

import com.bean.Address;
import com.bean.Business;
import com.bean.Creditor;
import com.bean.RMT2TagQueryBean;
import com.bean.Zipcode;

import com.constants.RMT2ServletConst;
import com.constants.GeneralConst;
import com.constants.AccountingConst;

import com.factory.AcctManagerFactory;
import com.factory.ContactsFactory;

import com.util.CreditorException;
import com.util.SystemException;

/**
 * This class provides common functionality needed to serve various 
 * user interfaces pertaining to Creditor maintenance.
 * 
 * @author Roy Terrell
 * 
 */
public class CreditorAction extends AbstractActionHandler implements ICommand {
	private Logger logger;
	
	/** The creditor API object */
	protected GLCreditorApi api;
	/** Creditor's balance */
	protected Double balance;
	/** An ArrayList of Creditors */
	protected List creditors;
	/** Creditor */
	protected Creditor cred;
	/** Generic Creditor */
	protected Object genericCred;
	/** Creditor's address profile */
	protected Address addr;
	/** Creditor's Business profile */
	protected Business bus;
	/** Creditor's Zip code profile */
	protected Zipcode zip;
	/** Http Helper object */
	protected HttpCreditorHelper httpHelper;

	/**
	 * Default constructor
	 * 
	 */
	public CreditorAction() {
		super();
		logger = Logger.getLogger("CreditorAction");
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
	public CreditorAction(ServletContext _context, HttpServletRequest _request) throws SystemException,	DatabaseException {
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
			this.api = AcctManagerFactory.createCreditor(this.dbConn, _request);
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
		try {
			this.init(null, request);
			this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);			
		}
		catch (SystemException e) {
			throw new ActionHandlerException(e.getMessage());	
		}
	}

	
	/**
	 * Obtains key creditor data entered by the user from the request object
	 */
	public void receiveClientData() throws ActionHandlerException {
		try {
            this.httpHelper = new HttpCreditorHelper(this.context, this.request);
            this.httpHelper.setSelectedRow(this.selectedRow);
			this.httpHelper.getHttpCombined();
			this.cred = this.httpHelper.getCreditor();
			this.genericCred = this.cred;
			this.bus = this.httpHelper.getBusiness();
			this.addr = this.httpHelper.getAddress();
			this.zip = this.httpHelper.getZip();
		}
		catch (Exception e) {
			throw new ActionHandlerException(e.getMessage());
		}
	}

	/**
	 * <p>
	 * Gathers data for the following objects and returns the data to the client
	 * via the HttpServletRequest object to be handle at the discretion of the client:
	 * <p>
	 * <table width="100%" border="1" cellspacing="0" cellpadding="0">
	 * <tr>
	 * <td><strong>Attribute</strong></td>
	 * <td><strong>Type</strong></td>
	 * <td><strong>Description</strong></td>
	 * <td><strong>Attribute Name</strong></td>
	 * </tr>
	 * <tr>
	 * <td>CREDITOR LIST</td>
	 * <td>ArrayList of {@link VwCreditorBusiness}</td>
	 * <td>List of Creditors</td>
	 * <td>listdata</td>
	 * </tr>
	 * <tr>
	 * <td>ADDRESS</td>
	 * <td>{@link Address}</td>
	 * <td>Address data</td>
	 * <td>listdata</td>
	 * </tr>
	 * <tr>
	 * <td>BUSINESS</td>
	 * <td>{@link Business}</td>
	 * <td>Business data</td>
	 * <td>listdata</td>
	 * </tr>
	 * <tr>
	 * <td>CREDITOR</td>
	 * <td>{@link Creditor}</td>
	 * <td>Creditor data</td>
	 * <td>listdata</td>
	 * </tr>
	 * <tr>
	 * <td>ZIP</td>
	 * <td>{@link Zipcode}</td>
	 * <td>Address object</td>
	 * <td>listdata</td>
	 * </tr>
	 * </table>
	 * 
	 * @throws ActionHandlerException
	 */
	public void sendClientData() throws ActionHandlerException {
		this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.creditors);
		this.request.setAttribute(GeneralConst.CLIENT_DATA_ADDERSS, this.addr);
		this.request.setAttribute(GeneralConst.CLIENT_DATA_BUSINESS, this.bus);
 		this.request.setAttribute(AccountingConst.CLIENT_DATA_CREDITOR, this.genericCred);
 		this.request.setAttribute(GeneralConst.CLIENT_DATA_ZIP, this.zip);
 		this.request.setAttribute(AccountingConst.CLIENT_DATA_CREDITOR_BAL, this.balance);
 		this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
	}


	/**
	 * Retrieves creditor's detail data from from the database.   This method depends on the 
	 * the creditior, business, address, and zip code data objects to populated with key data
	 * values to successfully perform database retrievals. 
	 * 
	 * @throws ActionHandlerException
	 */
	public void fetchCreditor() throws ActionHandlerException {
	  	Object results[];
	  	DaoApi dao = DataSourceFactory.createDao(this.dbConn);
		
     	// Get Creditor object	  	
   	    this.cred.addCriteria("Id", this.cred.getId());
  	    try {
  	    	results = dao.retrieve(this.cred);
  	    	if (results.length > 0) {
  	    		this.cred = (Creditor) results[0];
  	    		this.genericCred = this.cred;
  	    	}
  	    }
  	    catch (Exception e) {
  	    	throw new ActionHandlerException(e.getMessage());
  	    }
  	    
    	// Get Business object	  	
  	    this.bus.addCriteria("Id", this.cred.getBusinessId());
  	    try {
  	    	results = dao.retrieve(this.bus);
  	    	if (results.length > 0) {
  	    		this.bus = (Business) results[0];
  	    	}
  	    }
  	    catch (DatabaseException e) {
  	    	throw new ActionHandlerException(e.getMessage());
  	    }	  	

    	// Get Address object	  	
  	    this.addr.addCriteria("BusinessId", this.bus.getId());
  	    try {
  	    	results = dao.retrieve(this.addr);
  	    	if (results.length > 0) {
  	    		this.addr = (Address) results[0];
  	    	}
  	    }
  	    catch (DatabaseException e) {
  	    	throw new ActionHandlerException(e.getMessage());
  	    }	  	
  	    
    	// Get Zip object	  	
  	    try {
  	    	this.zip.addCriteria("Zip", String.valueOf(this.addr.getZip()));
  	    	results = dao.retrieve(zip);
  	    	if (results.length > 0) {
  	    		this.zip = (Zipcode) results[0];
  	    	}
  	    }
  	    catch (Exception e) {
  	    	throw new ActionHandlerException(e.getMessage());
  	    }	  	  	  
  	    
		// Get Creditor's Balance
  	    this.balance = this.getBalance(cred.getId());
	}	

	
	
	/**
	 * Obtains the creditor balance.
	 * 
	 * @param id The creditor Id
	 * @return Creditor's balance
	 */
	protected Double getBalance(int id) {
		// Get Customer Balance
    	Double balance = null;
    	try {
    	   balance = new Double(api.findCreditorBalance(id));    
    	}
    	catch (CreditorException e) {
    	    logger.log(Level.INFO, "Customer balance could not be obtained...defaulting to zero.");
    	    balance = new Double(0); 
    	}
    	return balance;
	}
	
	/**
	 * Create the data entities needed to represent a new creditor. 
	 */
	public void add() throws ActionHandlerException {
		try {
            this.cred = AcctManagerFactory.createCreditor();
            this.bus = ContactsFactory.createBusiness();
            this.addr = ContactsFactory.createAddress();
            this.zip = new Zipcode();
            this.balance = new Double(0);
		}
		catch (Exception e) {
			throw new ActionHandlerException(e.getMessage());
		}
	}

	
	/**
	 * No Action
	 */
	public void edit() throws ActionHandlerException {
		return;
	}

	/**
	 * No Action
	 */
	public void save() throws ActionHandlerException {

	}

	/**
	 * No Action
	 */
	public void delete() throws ActionHandlerException {

	}
	
	/**
	 * Gets the creditor object that was obtained from the client's request.
	 * 
	 * @return {@link Creditor}
	 */
	public Creditor getCreditor() {
		return this.httpHelper.getCreditor();
	}
}
package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.accounting.creditor.CreditorAction;

import com.bean.RMT2TagQueryBean;
import com.bean.Zipcode;
import com.bean.criteria.CreditorCriteria;

import com.constants.RMT2ServletConst;

import com.factory.AcctManagerFactory;

import com.action.ActionHandlerException;
import com.util.SystemException;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorSearchAction extends CreditorAction {
	private static final String COMMAND_NEWSEARCH = "XactDisburse.DisbursementCreditorSearch.newsearch";
	private static final String COMMAND_SEARCH = "XactDisburse.DisbursementCreditorSearch.search";
	private static final String COMMAND_LIST = "XactDisburse.DisbursementCreditorSearch.list";
	private static final String COMMAND_EDIT = "XactDisburse.DisbursementCreditorSearch.edit";
	private Logger logger;

	/**
	 * Default constructor
	 * 
	 */
	public DisbursementsCreditorSearchAction() {
		super();
		logger = Logger.getLogger("DisbursementsCreditorSearchAction");
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
	public DisbursementsCreditorSearchAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
	}

	/**
	 * Returns selection criteria that is sure to retrun an empty result set
	 * once applied to the sql that pertains to the data source of the creditor
	 * search page.
	 */
	protected String doInitialCriteria(RMT2TagQueryBean _query)	throws ActionHandlerException {
		return "creditor_id = -1";
	}

	/**
	 * Creates an instance of CreditorCriteria, which is used to track the user's 
	 * selection criteria input.   This method uses introspection to gather user's 
	 * input into the cretieria object.
	 */
	protected Object doCustomInitialization() throws ActionHandlerException {
		CreditorCriteria criteriaObj = CreditorCriteria.getInstance();
		if (!this.isFirstTime()) {
			try {
				DataSourceAdapter.packageBean(this.request, criteriaObj);
				this.setBaseView("VwCreditorBusinessView");
			} 
			catch (SystemException e) {
				this.msg = "Problem gathering Creditor Search request parameters:  " + e.getMessage();
				logger.log(Level.ERROR, this.msg);
				throw new ActionHandlerException(this.msg);
			}
		}
		return criteriaObj;
	}

	/**
	 * Handler method that responds to the client's request to display a new
	 * creditor search page.
	 * 
	 * @throws ActionHandlerException
	 */
	protected void doNewSearch() throws ActionHandlerException {
		this.setFirstTime(true);
		this.startSearchConsole();
        this.creditors = new ArrayList();
        this.sendClientData();
	}

	/**
	 * Handler method that responds to the client's request to perform a
	 * creditor search using the selection criterai entered by the user.
	 * 
	 * @throws ActionHandlerException
	 */
	protected void doSearch() throws ActionHandlerException {
		this.setFirstTime(false);
		this.buildSearchCriteria();
		this.query.setQuerySource("VwCreditorBusinessView");
		this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	}

	/**
	 * Fetches the list creditors from the database using the where clause criteria 
	 * previously stored on the session during the phase of the request to builds 
	 * the query predicate.
	 * 
	 * @throws ActionHandlerException
	 */
	protected void listData() throws ActionHandlerException {
		String criteria = this.query.getWhereClause();
		this.creditors = new ArrayList();
		try {
			this.creditors = this.api.findCreditorBusiness(criteria);
		} 
		catch (Exception e) {
			throw new ActionHandlerException(e.getMessage());
		}
		this.sendClientData();
	}

	
	/**
	 * Prepares to send to the client newly initialized data objects needed 
	 * to create a creditor or vendor.
	 */
	public void add() throws ActionHandlerException {
		super.add();
	}

	
	/**
	 * Retrieves a row of data, which pertains to a creditor, from a list of 
	 * creditors that exist in the HTTP Request object.
	 * 
	 * @throws ActionHandlerException
	 */
	public void edit() throws ActionHandlerException {
     	// Get Creditor object	  	
   	    this.cred = this.httpHelper.getCreditor();
    	// Get Business object	  	
  	    this.bus = this.httpHelper.getBusiness();
    	// Get Address object	  	
  	    this.addr = this.httpHelper.getAddress();
    	// Get Zip object	  	
  	    try {
  	    	this.zip = new Zipcode();
  	    	String zipValue = String.valueOf(this.addr.getZip());
  	    	this.zip.setZip(zipValue);
  	    }
  	    catch (Exception e) {
  	    	throw new ActionHandlerException(e.getMessage());
  	    }
  	    // Get remaining data from the database.
  	    this.fetchCreditor();
	}
}
package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.action.accounting.creditor.CreditorAction;

import com.api.XactManagerApi;
import com.api.db.DatabaseException;

import com.factory.AcctManagerFactory;
import com.factory.XactFactory;

import com.util.SystemException;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorConsoleAction extends CreditorAction {
	private static final String COMMAND_PAYMENT = "XactDisburse.DisbursementCreditorConsole.payment";
	private static final String COMMAND_TRANSACTIONS = "XactDisburse.DisbursementCreditorConsole.transactions";
	private static final String COMMAND_BACK = "XactDisburse.DisbursementCreditorConsole.back";
	private Logger logger;
	protected XactManagerApi xactApi;

	/**
	 * Default constructor
	 * 
	 */
	public DisbursementsCreditorConsoleAction() {
		super();
		logger = Logger.getLogger("DisbursementsCreditorConsoleAction");
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
	public DisbursementsCreditorConsoleAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
			this.xactApi = XactFactory.create(this.dbConn, _request);
		} catch (Exception e) {
		    logger.log(Level.ERROR, e.getMessage());
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
		if (command.equalsIgnoreCase(DisbursementsCreditorConsoleAction.COMMAND_PAYMENT)) {
			this.doPayment();
		}
		if (command.equalsIgnoreCase(DisbursementsCreditorConsoleAction.COMMAND_TRANSACTIONS)) {
			this.doTransactions();
		}
		if (command.equalsIgnoreCase(DisbursementsCreditorConsoleAction.COMMAND_BACK)) {
			this.doBack();
		}
	}


	/**
	 * Handler method that responds to the client's request to display a new
	 * creditor search page.
	 * 
	 * @throws ActionHandlerException
	 */
	protected void doPayment() throws ActionHandlerException {
		this.receiveClientData();
        this.sendClientData();
	}

    /**
     * Gathers Creditor/Vendor related cash disbursement transactions and sends the data to the client.
     * 
     * @throws ActionHandlerException
     */
    protected void doTransactions() throws ActionHandlerException {
    	this.receiveClientData();
     	// Get Creditor transactions	  	
  	    try {
  	    	this.creditors = this.xactApi.findCreditorXactHist(this.cred.getId());
  	    }
  	    catch (Exception e) {
  	    	throw new ActionHandlerException(e.getMessage());
  	    }
        this.sendClientData();
    }
    
    /**
     * Sends the user back to the Creditor/Vendor Cash Disbursement Search page.  Uses the most recent SQL 
     * predicate that is stored in the user's session to perform the previous Creditor/Vendor search for 
     * Cash Disbursements.
     *  
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
        this.receiveClientData();
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
     * Obtains key creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionHandlerException {
        super.receiveClientData();
        this.fetchCreditor();
    }
}
package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.accounting.creditor.CreditorAction;

import com.factory.AcctManagerFactory;

import com.action.ActionHandlerException;

import com.util.SystemException;
import com.api.db.DatabaseException;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorConfirmationAction extends CreditorAction {
	private static final String COMMAND_BACK = "XactDisburse.DisbursementCreditorConfirm.back";
	private Logger logger;

	/**
	 * Default constructor
	 * 
	 */
	public DisbursementsCreditorConfirmationAction() {
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
	public DisbursementsCreditorConfirmationAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
		if (command.equalsIgnoreCase(DisbursementsCreditorConfirmationAction.COMMAND_BACK)) {
			this.doBack();
		}
	}

    
    protected void doBack() throws ActionHandlerException {
        this.receiveClientData();
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
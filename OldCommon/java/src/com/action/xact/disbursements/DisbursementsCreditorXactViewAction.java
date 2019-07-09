package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.util.CashDisbursementsException;
import com.util.SystemException;
import com.util.XactException;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class DisbursementsCreditorXactViewAction extends DisbursementsCreditorXactCommon {
	private static final String COMMAND_REVERSE = "XactDisburse.DisbursementCreditorXactView.reverse";
	private static final String COMMAND_BACK = "XactDisburse.DisbursementCreditorXactView.back";
	private Logger logger;

	/**
	 * Default constructor
	 * 
	 */
	public DisbursementsCreditorXactViewAction() {
		super();
		logger = Logger.getLogger("DisbursementsCreditorXactViewAction");
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
	public DisbursementsCreditorXactViewAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
	}

	/**
	 * Processes the client's request to reverse a creditor cash disbursement transaction 
	 * using request, response, and command.
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
		if (command.equalsIgnoreCase(DisbursementsCreditorXactViewAction.COMMAND_REVERSE)) {
			this.doReverse();
		}
		if (command.equalsIgnoreCase(DisbursementsCreditorXactViewAction.COMMAND_BACK)) {
			this.doBack();
		}
	}


	/**
	 * Handler method that responds to the client's request to reverse selected transaction.
	 * 
	 * @throws ActionHandlerException
	 */
	protected void doReverse() throws ActionHandlerException {
		this.receiveClientData();
		this.reverseXact();
  	    this.msg = "Transaction reversed successfully";
        this.sendClientData();
	}

	
	/**
	 * Reverses creditor/vendor cash disbursement transaction. 
	 *  
	 * @return int - the transaction id. 
	 * @throws ActionHandlerException
	 */
	  private int reverseXact() throws ActionHandlerException {
		  int xactId =0;
		  List items = null;
           
		  try {
			  items = this.baseApi.findXactTypeItemsActivityByXactId(this.xact.getId()); 
			  xactId = this.disbApi.maintainCashDisbursement(this.xact, items, this.creditor.getId());
			  this.transObj.commitUOW();
			  return xactId;
		  }
		  catch (CashDisbursementsException e) {
			  this.msg = "CashDisbursementsException: " + e.getMessage();
			  logger.log(Level.ERROR, msg);
			  this.transObj.rollbackUOW();
			  throw new ActionHandlerException(msg);
		  }
		  catch (XactException e) {
        	  this.msg = "XactException: " + e.getMessage();
              logger.log(Level.ERROR, msg);
              this.transObj.rollbackUOW();
              throw new ActionHandlerException(msg);
		  }
	  }

}
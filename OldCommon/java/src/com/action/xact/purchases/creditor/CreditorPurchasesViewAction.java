package com.action.xact.purchases.creditor;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.util.SystemException;

import com.api.db.DatabaseException;

import com.action.ActionHandlerException;


/**
 * This class provides action handlers to manage Expense Creditor Purchase View page requests.
 * 
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesViewAction extends CreditorPurchasesAction {
    private static final String COMMAND_SEARCH = "XactPurchase.CreditorView.search";
    private static final String COMMAND_REVERSE = "XactPurchase.CreditorView.reverse";
    private Logger logger;
    private int creditorId;

    /**
     * Default constructor
     *
     */
    public CreditorPurchasesViewAction() throws SystemException {
        super(); 
        logger = Logger.getLogger("CreditorPurchasesViewAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public CreditorPurchasesViewAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    super(_context, _request);
    this.init(this.context, this.request);
  
  }
  

  /**
   * Initializes this object using _conext and _request.  This is needed in the 
   * event this object is inistantiated using the default constructor.
   * 
   * @throws SystemException
   */
  protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
      super.init(_context, _request);
  }

  
  /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
	  super.processRequest(request, response, command);
      if (command.equalsIgnoreCase(CreditorPurchasesViewAction.COMMAND_SEARCH)) {
          this.doSearch();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesViewAction.COMMAND_REVERSE)) {
          this.doReverse();
      }
  }
  
  /**
   * Recalls Expense Creditor Purchases search results.
   * 
   * @throws ActionHandlerException
   */
  public void doSearch() throws ActionHandlerException {
      return;
  }
  
  
  /**
   * Drives the process of reversing an Expense Credit Purchase transaction.
   * 
   * @throws ActionHandlerException
   */
  public void doReverse() throws ActionHandlerException {
	  this.receiveClientData();
	  this.reverseXact();
	  this.sendClientData();
  }
  
  /**
   * Reverses an expense credit purchase based on the current values of this 
   * class' internal properties: transaction, creditor, and transaction items.
   * 
   * @throws ActionHandlerException
   */
  private void reverseXact() throws ActionHandlerException {
	  int xactId = 0;
	  try {
		  xactId = this.ccApi.maintainCreditCharge(this.xact, this.xactItems, this.creditorId);
		  this.transObj.commitUOW();
		  this.httpXactHelper.retrieveCreditOrder(xactId, this.creditorId);
		  this.msg = "Transaction reversed successfully";
	  }
	  catch (Exception e) {
		  this.msg = e.getMessage();
		  logger.log(Level.ERROR, this.msg);
		  throw new ActionHandlerException(this.msg);
	  }
  }
  
  /**
   * Obtains key data from the clients request pertaining to the creditor and expense credit 
   * purchase transaction.  The key data items are used to fetch existing data from the 
   * database.
   * 
   * @throws ActionHandlerException
   */
  public void receiveClientData() throws ActionHandlerException {
	  super.receiveClientData();
	  try {
		  this.xact = this.httpXactHelper.getXact();
		  this.xact = this.baseApi.findXactById(this.xact.getId());
		  this.creditorId = this.httpXactHelper.getCreditor().getCreditorId();
		  this.xactItems = this.baseApi.findXactTypeItemsActivityByXactId(this.xact.getId());  
	  }
	  catch (Exception e) {
		  this.msg = e.getMessage();
		  logger.log(Level.ERROR, this.msg);
		  throw new ActionHandlerException(this.msg);
	  }
  }

}
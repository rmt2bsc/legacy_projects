package com.action.xact.purchases.creditor;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.bean.VwCreditorBusiness;
import com.bean.Xact;

import com.constants.CreditChargesConst;
import com.constants.RMT2ServletConst;
import com.constants.XactConst;

import com.util.SystemException;

import com.util.XactException;





/**
 * This class provides action handlers to respond to an associated controller for searching, 
 * adding, deleting, and validating Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesEditAction extends CreditorPurchasesAction {
    private static final String COMMAND_NEW = "XactPurchase.CreditorEdit.new";
    private static final String COMMAND_SEARCH = "XactPurchase.CreditorEdit.search";
    private static final String COMMAND_ADDITEM = "XactPurchase.CreditorEdit.additem";
    private static final String COMMAND_SAVE = "XactPurchase.CreditorEdit.save";
    
    private Logger logger;
    private ArrayList xactList;

    /**
     * Default constructor
     *
     */
    public CreditorPurchasesEditAction() throws SystemException {
        super(); 
        logger = Logger.getLogger("CreditorPurchasesEditAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public CreditorPurchasesEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
      if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_ADDITEM)) {
          this.addData();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_NEW)) {
          this.doNewTransaction();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_SEARCH)) {
          this.doSearch();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_SAVE)) {
          this.saveData();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesEditAction.COMMAND_NEW)) {
          this.newTransaction();
      }

  }
  
  
  
  /**
   * Preapres the client for adding an item to creditor purchase order
   * 
   * @throws ActionHandlerException
   */
  public void add() throws ActionHandlerException {
      this.receiveClientData();
      this.httpXactHelper.retrieveCreditOrder();
      // Add new generic object to the transaction items array
      Object obj = this.createNewXactItemObject();
      this.httpXactHelper.getXactItems().add(obj);
      
      // Get all transaction type item entries by transaction type to be 
      // used generally as a UI Dropdown.
      try {
          this.xactItemTypes = this.baseApi.findXactTypeItemsByXactTypeId(this.httpXactHelper.getXactType().getId());  
      }
      catch (XactException e) {
          logger.log(Level.ERROR, e.getMessage());
          throw new ActionHandlerException(e);
      }
      return;
  }
  
    
  /**
   * Preapres the client for creating a creditor purchase order from the Expense 
   * Credit Purchase Edit page. As a requirement, this method expects the client 
   * to include the creditor's id in the HttpServletRequest parameter list as "CreditorId".
   *
   * @throws ActionHandlerException
   */
  public void newTransaction() throws ActionHandlerException {
	  this.receiveClientData();
   	  this.httpXactHelper.retrieveCreditOrder(0, this.httpXactHelper.getCreditor().getCreditorId());
   	  this.sendClientData();
      return;
  }
  
  
  /**
   * Saves the changes of an expense credit purchase transaction to the database.
   */
  public void save() throws ActionHandlerException {
      int xactId = 0;
      Xact xact = this.httpXactHelper.getXact();
      ArrayList items = (ArrayList) this.httpXactHelper.getXactItems();
      VwCreditorBusiness cred = this.httpXactHelper.getCreditor();
      try {
          xactId = this.ccApi.maintainCreditCharge(xact, items, cred.getCreditorId()); 
          this.transObj.commitUOW();
          this.httpXactHelper.retrieveCreditOrder(xactId, cred.getCreditorId());   
      }
      catch (Exception e) {
          logger.log(Level.ERROR, e.getMessage());
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e.getMessage());
      }
      
  }
  
  public void doSearch() throws ActionHandlerException {
      
  }

  public void doNewTransaction() throws ActionHandlerException {
    
  }
  
  
  protected void sendClientData() throws ActionHandlerException {
	  super.sendClientData();
	  
      // Set data on request object
      this.request.setAttribute(CreditChargesConst.CLIENT_DATA_XACTLIST, this.xactList);
      this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMTYPELIST, this.xactItemTypes);
      this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
  }
 
}
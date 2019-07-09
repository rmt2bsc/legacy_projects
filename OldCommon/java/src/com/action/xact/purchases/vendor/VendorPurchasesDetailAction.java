package com.action.xact.purchases.vendor;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.bean.PurchaseOrder;

import com.constants.PurchasesConst;

import com.util.SystemException;
import com.util.PurchaseOrderException;



/**
 * This class provides action handlers to respond to an associated controller for 
 * viewing, editing, finalization, and cancellation of a purchase order and its details. 
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesDetailAction extends VendorPurchasesAction {
    private static final String COMMAND_ADDITEM = "XactPurchase.VendorDetails.additem";
    private static final String COMMAND_SAVE = "XactPurchase.VendorDetails.save";
    private static final String COMMAND_SEARCH = "XactPurchase.VendorDetails.search";
    private static final String COMMAND_CANCEL = "XactPurchase.VendorDetails.cancel";
    private static final String COMMAND_FINALIZE = "XactPurchase.VendorDetails.finalize";
    
    private Logger logger;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesDetailAction()  {
        super(); 
        logger = Logger.getLogger("VendorPurchasesItemSelectAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public VendorPurchasesDetailAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
      if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_ADDITEM)) {
          this.addData();
      }
      if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_SAVE)) {
          this.saveData();
      }
      if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_FINALIZE)) {
          this.doFinalize();
      }
      if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_SEARCH)) {
          this.doSearch();
      }
      if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_CANCEL)) {
          this.doCancel();
      }
  }
  
  
  /**
   * Handler method that responds to the client's request to recall purchase 
   * order search page.
   * 
   * @throws ActionHandlerException
   */
  public void doSearch() throws ActionHandlerException {
	  this.doSearchList();
	  return;
  }
  
  
  /**
   * Saves the current state of the purchase order.  After successful save, this method 
   * gathers the credit/vendor items that are currently not assigned to the purchase 
   * order in preparation to invoke Purchase Order Item Select page.
   * 
   * @throws ActionHandlerException General database errors.
   */
  public void add() throws ActionHandlerException {
	  this.receiveClientData();
	  
	  // At this point, the following items should be valid.
	  PurchaseOrder po = this.httpPoHelper.getPo();
	  List items = this.httpPoHelper.getPoItems();
	  List availItems = null;
	  
	  // Save Purchase Order
	  try {
		  this.savePurchaseOrder(po, items);
		  this.transObj.commitUOW();
		  
		  // Get credit/vendor items that are currently not assigned to the purchase 
		  // order ino rder to be used on the Item Select page
		  availItems = this.getPoAvailItems(po.getId(), po.getVendorId());
		  this.request.setAttribute(PurchasesConst.CLIENT_DATA_AVAIL_ITEMS, availItems);
		  return;
	  }
	  catch (Exception e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e);
	  }
	  finally {
		  this.httpPoHelper.retrievePurchaseOrder(po.getId(), po.getVendorId());  
	  }
  }
  
  /**
   * Saves the current state of the purchase order.  
   * 
   * @throws ActionHandlerException General database errors.
   */
  public void save() throws ActionHandlerException {
	  // At this point, the following items should be valid.
	  PurchaseOrder po = this.httpPoHelper.getPo();
	  List items = this.httpPoHelper.getPoItems();
	  
	  // Save Purchase Order
	  try {
		  this.savePurchaseOrder(po, items);
		  this.transObj.commitUOW();
		  this.msg = "Purchase Order was updated successfully";
		  return;
	  }
	  catch (Exception e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e);
	  }
	  finally {
		  this.httpPoHelper.retrievePurchaseOrder(po.getId(), po.getVendorId());  
	  }
  }
  
  /**
   * Drives the process of finalizing a purchase order and creating the purchase 
   * order transaction.  Functionality included in this process would be retrieving 
   * client data, invoication of purchase order finalization, and sending the results 
   * back to the client.
   * 
   * @throws ActionHandlerException
   */
  public void doFinalize() throws ActionHandlerException {
	  this.receiveClientData();
	  // At this point, the following items should be valid.
	  PurchaseOrder po = this.httpPoHelper.getPo();
	  List items = this.httpPoHelper.getPoItems();
      this.finalizePurchaseOrder(po, items);
      // Prepare data to be displayed on confirmation page
      this.httpPoHelper.retrievePurchaseOrder(po.getId(), po.getVendorId()); 
      this.sendClientData();
  }
  
  /**
   * Drives the process of finalizing a purchase order and creating the purchase 
   * order transaction.
   * 
   * @throws ActionHandlerException
   */
  private void finalizePurchaseOrder(PurchaseOrder po, List items) throws ActionHandlerException {
	  try {
		  this.api.submitPurchaseOrder(po, items);
		  this.transObj.commitUOW();
		  this.msg = "Purchase Order was submitted successfully";
		  return;
	  }
	  catch (Exception e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e);
	  }
	  finally {
		  this.httpPoHelper.retrievePurchaseOrder(po.getId(), po.getVendorId());  
	  }
  }
  
  
  /**
   * Applies the changes of an existing purchase order and its detail items to the database 
   * using data obtained from the client's HttpServletRequest object.
   * 
   * @throws ActionHandlerException
   * @throws DatabaseException
   */
  private void savePurchaseOrder(PurchaseOrder po, List items)  throws ActionHandlerException {
      try {
          this.api.maintainPurchaseOrder(po, items);
          return;
      }
      catch (PurchaseOrderException e) {
          throw new ActionHandlerException(e);
      }
  }
  
  /**
   * Drives the Purchase Order Cancellation process.  After purchase order is 
   * successfull cancelled, the process refreshes purchase order data that is 
   * to be sent to the client. 
   * 
   * @throws ActionHandlerException
   */
  public void doCancel() throws ActionHandlerException {
      this.receiveClientData();
      PurchaseOrder po = this.httpPoHelper.getPo();
      this.cancelPurchaseOrder(po);
      // Prepare data to be displayed on confirmation page
      this.httpPoHelper.retrievePurchaseOrder(po.getId(), po.getVendorId()); 
      this.sendClientData();
  }
  
  /**
   * Cancels the purchase order.
   * 
   * @param po The purchase order to be cancelled.
   * @throws ActionHandlerException
   */
  private void cancelPurchaseOrder(PurchaseOrder po) throws ActionHandlerException {
      try {
          this.api.cancelPurchaseOrder(po.getId());
          this.transObj.commitUOW();
          this.msg = "Purchase Order was cancelled successfully";
          return;
      }
      catch (Exception e) {
          this.msg = e.getMessage();
          logger.log(Level.ERROR, e.getMessage());
          this.transObj.rollbackUOW();   
          throw new ActionHandlerException(this.msg);
      }
  }
 
}
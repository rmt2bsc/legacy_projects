package com.action.xact.purchases.vendor;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.api.db.DatabaseException;

import com.bean.Creditor;
import com.bean.PurchaseOrder;

import com.action.ActionHandlerException;

import com.util.SystemException;
import com.util.PurchaseOrderException;


/**
 * This class provides action handlers to respond to an associated controller for 
 * adding items to a purchase order. 
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesItemSelectAction extends VendorPurchasesAction {
    private static final String COMMAND_ITEMSELECT = "XactPurchase.VendorItemSelect.savenewitem";
    private static final String COMMAND_SEARCH = "XactPurchase.VendorItemSelect.search";
    
    private Logger logger;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesItemSelectAction()  {
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
  public VendorPurchasesItemSelectAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
      if (command.equalsIgnoreCase(VendorPurchasesItemSelectAction.COMMAND_ITEMSELECT)) {
          this.editData();
      }
      if (command.equalsIgnoreCase(VendorPurchasesItemSelectAction.COMMAND_SEARCH)) {
          this.doSearch();
      }
  }
  
  
  /**
   * Handler method that responds to the client's request to recall purchase order search page.
   * 
   * @throws ActionHandlerException
   */
  protected void doSearch() throws ActionHandlerException {
	  this.doSearchList();
	  return;
  }
  
  
  /**
   * Gathers key data (purchase order, vendor, and selected items) from the client's request 
   * and associates the new items to the purchase order.  This new associations is persisted
   * in the database for further processing.  
   * 
   * @throws ActionHandlerException General database errors.
   */
  public void edit() throws ActionHandlerException {
	  // At this point, the following items should be valid.
	  PurchaseOrder po = this.httpPoHelper.getPo();
	  Creditor vendor = this.httpPoHelper.getCreditor();
	  List newItems = this.httpPoHelper.getNewPoItems();
	  int poId = 0;
	  
	  // Call api method to add Items to purchase order
	  try {
		  poId = this.api.addItemsToPurchaseOrder(po, newItems);
		  this.transObj.commitUOW();
		  this.msg = "One or more items were added to the purchase order successfully";
		  return;
	  }
	  catch (PurchaseOrderException e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e);
	  }
	  finally {
		  this.httpPoHelper.retrievePurchaseOrder(poId, vendor.getId());  
	  }
  }
  
 
}
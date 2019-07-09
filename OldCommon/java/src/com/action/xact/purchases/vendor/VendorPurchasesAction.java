package com.action.xact.purchases.vendor;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletContext;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.xact.AbstractXactAction;

import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VwCreditorBusiness;
import com.bean.VwVendorItems;

import com.constants.PurchasesConst;
import com.constants.RMT2ServletConst;
import com.factory.PurchasesFactory;

import com.action.xact.purchases.HttpPurchasesHelper;
import com.api.PurchasesApi;
import com.api.db.DatabaseException;

import com.util.SystemException;



/**
 * This class provides common action handlers to manage Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesAction extends AbstractXactAction {
	protected static final String JSP_PATH = "/forms/xact/purchases/vendor/";
	
    private Logger logger;
    protected HttpPurchasesHelper httpPoHelper;
    protected PurchasesApi api;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesAction()  {
        super(); 
        logger = Logger.getLogger("VendorPurchasesAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public VendorPurchasesAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
      try {
    	  this.api = PurchasesFactory.createApi(this.dbConn, _request);
          httpPoHelper = new HttpPurchasesHelper(_context, _request);
      }
      catch (Exception e) {
    	  logger.log(Level.ERROR, e.getMessage());
    	  throw new SystemException(e.getMessage());
      }
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
  }
  
  
  /**
   * Stub function used to respond to the client's request for recalling the most 
   * recent result of the Vendor Purchase Order Search page. 
   * 
   * @throws ActionHandlerException
   */
  protected void doSearchList() throws ActionHandlerException {
	  return;
  }
  
  
  /**
   * Retrieves creditor/vendor purchase order data from the client's request.
   * 
   * @throws ActionHandlerException
   */
  protected void receiveClientData() throws ActionHandlerException {
	  super.receiveClientData();
	  try {
		  this.httpPoHelper.getHttpCombined();
	  }
	  catch (Exception e) {
		  logger.log(Level.ERROR, this.msg);
		  throw new ActionHandlerException(this.msg);
	  }
  }
  
  /**
   * Set all pertinent purchase order data onto the request object which 
   * is transmitted to the client.
   * 
   * <p>
   * <p>
   * The following list dipicts the java objects and their names that are 
   * stored in the HttpServletRequest object on the client:
   * <table border="1">
   *   <tr>
   *     <th align="left"><strong>Java Data Object</strong></th>
   *     <th><strong>Id on client</strong></th>
   *   </tr>
   *   <tr>
   *     <td>{@link PurchaseOrder}</td>
   *     <td>po</td>
   *   </tr>
   *   <tr>
   *     <td>ArrayList of {@link PurchaseOrderItems}</td>
   *     <td>poItems</td>
   *   </tr>
   *   <tr>
   *     <td>{@link PurchaseOrderStatus}</td>
   *     <td>pos</td>
   *   </tr>
   *   <tr>
   *     <td>{@link PurchaseOrderStatusHist}</td>
   *     <td>posh</td>
   *   </tr>
   *   <tr>
   *     <td>{@link VwCreditorBusiness}</td>
   *     <td>vendor</td>
   *   </tr>
   * </table>
   * 
   * @throws ActionHandlerException
   */
  protected void sendClientData() throws ActionHandlerException {
	  super.sendClientData();
	  
      // Set data on request object
      this.request.setAttribute(PurchasesConst.CLIENT_DATA_PO, this.httpPoHelper.getPo());
      this.request.setAttribute(PurchasesConst.CLIENT_DATA_VENODR, this.httpPoHelper.getVendor());
      this.request.setAttribute(PurchasesConst.CLIENT_DATA_ITEMS, this.httpPoHelper.getPoItems());
      this.request.setAttribute(PurchasesConst.CLIENT_DATA_STATUS, this.httpPoHelper.getPoStatus());
      this.request.setAttribute(PurchasesConst.CLIENT_DATA_STATUS_HIST, this.httpPoHelper.getPoStatusHist());
      if (this.httpPoHelper.getPo().getId() <= 0) {
    	  this.request.setAttribute("poTotal", "0.00");  
      }
      this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
  }
  
  
  /**
   * Retrieves one or more vendor inventory items which have not been assoicated 
   * with a purchase order.
   * 
   * @param _vendorId The id of the vendor.
   * @param _poId The id of the purchase order
   * @return ArrayList of {@link VwVendorItems}
   * @throws ActionHandlerException
   */
  protected List getPoAvailItems(int _vendorId, int _poId) throws ActionHandlerException {
	  StringBuffer criteria = new StringBuffer(100);
	  List list = null;
	  
	  this.api.setBaseView("VwVendorItemsView");
	  this.api.setBaseClass("com.bean.VwVendorItems");
	  criteria.append( " vendor_id = " + _vendorId);
	  criteria.append( " and item_master_id not in ( select item_master_id from purchase_order_items where purchase_order_id = ");
	  criteria.append(_poId);
	  criteria.append(")");
	  try {
		  list = this.api.findData(criteria.toString(), "description");
		  return list;
	  }
	  catch (SystemException e) {
		  logger.log(Level.ERROR, e.getMessage());
		  throw new ActionHandlerException(e.getMessage());
	  }
  }
}
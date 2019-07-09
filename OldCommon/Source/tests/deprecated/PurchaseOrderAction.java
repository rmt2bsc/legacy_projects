package com.action;

import javax.servlet.ServletContext;


import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import com.api.InventoryApi;
import com.api.PurchasesApi;
import com.api.GLCreditorApi;

import com.bean.RMT2TagQueryBean;
import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VwCreditorBusiness;
import com.bean.VwPurchaseOrderList;
import com.bean.ItemMaster;
import com.bean.ItemMasterStatusHist;
import com.bean.VendorItems;
import com.bean.VwVendorItems;

import com.bean.criteria.PurchaseOrderCriteria;

import com.constants.ItemConst;
import com.constants.PurchasesConst;
import com.constants.RMT2ServletConst;

import com.factory.InventoryFactory;
import com.factory.PurchasesFactory;
import com.factory.AcctManagerFactory;
import com.factory.DataSourceAdapter;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.NotFoundException;
import com.util.ItemMasterException;
import com.util.PurchaseOrderException;
import com.util.GLAcctException;
import com.util.ActionHandlerException;
import com.util.RMT2Utility;



/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating 
 * Purchase Order information.
 * 
 * @author Roy Terrell
 *
 */
public class PurchaseOrderAction extends AbstractActionHandler implements IRMT2ServletActionHandler {
    private PurchasesApi api;
    private InventoryApi invApi;
    private GLCreditorApi vendApi;
    private PurchaseOrder po;
    private PurchaseOrderItems poi;
    private boolean submtted;
    private ArrayList selPoItems;

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public PurchaseOrderAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    super(_context, _request);
    this.className = "PurchaseOrderAction";
    this.api = PurchasesFactory.createApi(this.dbConn, _request);
    this.invApi = InventoryFactory.createInventoryApi(this.dbConn, _request);
    this.vendApi = AcctManagerFactory.createCreditor(this.dbConn, _request);
  }
  

  /**
   * This method is responsible for gathering the user's input of purchase order selection criteria data from the request object.
   * 
   * @return Object which represents the custom object that is a member of {@link RMT2TagQueryBean}. 
   * @throws ActionHandlerException
   */
  protected Object doCustomInitialization() throws ActionHandlerException {
      String method = "[" + this.className + ".getCriteriaObject] ";
      this.setBaseView("VwPurchaseOrderListView");
      PurchaseOrderCriteria criteriaObj = PurchaseOrderCriteria.getInstance();
      if (!this.isFirstTime()) {
          try {
              DataSourceAdapter.packageBean(this.request, criteriaObj);    
          }
          catch (SystemException e) {
              System.out.println(method + "Problem gathering Item Master request parameters.");
              System.out.println(method + e.getMessage());
              throw new ActionHandlerException(e);
          }    
      }
      return criteriaObj;
  }

  /**
   * Preapres the client for creating a purchase order.   As a requirement, this  method is expecting the client to inclued the vendor's id in 
   * the HttpServletRequest parameter list.
   * <p>
   * The following objects are set on the request object identified as "item" and "itemhistory", respectively: 
   * {@link VwItemMaster} and {@link ItemMasterStatusHist}.  
   *
   */
  public void add()  throws ActionHandlerException {
	  String vendorIdProp = "qry_VendorId";
	  String temp = null;
	  int vendorId = 0;
	  int poId = 0;
	  ArrayList availItems = null;
	  
      try {
    	  temp = this.getPropertyValue(vendorIdProp);
    	  vendorId = Integer.parseInt(temp);
    	  this.retrievePurchaseOrder(0, vendorId);
    	  // A purchase has not been created at this point, so we should expect all the vendor's inventory items to be returned.
    	  availItems = this.getPoAvailItems(vendorId, poId);
    	  this.request.setAttribute("AvailItems", availItems);
          return;
      }
      catch (NotFoundException e) {
    	  System.out.println(e.getMessage());
    	  this.msg = "A vendor must be selected in order to begin an \'Add Purchase Order\' operation";
    	  throw new ActionHandlerException(this.msg);
      }
  }
  
  /**
   * 
   * @throws ActionHandlerException
   * @throws DatabaseException
   */
  public void addItem() throws ActionHandlerException, DatabaseException {
      ArrayList availItems = null;
      
      try {
          this.updatePurchaseOrder();
          this.transObj.commitTrans();
          this.msg = "Purchase Order was saved successfully";
          // Set data on the request object to be used by client JSP.s
          this.retrievePurchaseOrder(this.po.getId(), this.po.getVendorId());
          
          // Get items available for selection.
          availItems = this.getPoAvailItems(this.po.getVendorId(), this.po.getId());
          this.request.setAttribute("AvailItems", availItems);
          return;
      }
      catch (ActionHandlerException e) {
          this.msg = e.getMessage();
          this.transObj.rollbackTrans();   
          throw(e);
      }
  }

  
  /**
   * Retrieves one or more vendor inventory items which have not been assoicated with a purchase order.
   * 
   * @param _vendorId The id of the vendor.
   * @param _poId The id of the purchase order
   * @return ArrayList of {@link VwVendorItems}
   * @throws ActionHandlerException
   */
  private ArrayList getPoAvailItems(int _vendorId, int _poId) throws ActionHandlerException {
	  StringBuffer criteria = new StringBuffer(100);
	  ArrayList list = null;
	  
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
		  System.out.println(e.getMessage());
		  throw new ActionHandlerException(e.getMessage());
	  }
	  
  }

  /**
   * Uses data from the client's request object to retrieve target VwItemMaster and 
   * ItemMasterStatusHist objects from the databse for a single Item Master record edit session.
   * <p>
   * The following objects are set on the request object identified as "item" and "itemhistory", respectively: 
   * {@link VwItemMaster} and {@link ItemMasterStatusHist}. 
   * 
   * @throws ItemMasterException
   */
  public void edit() throws ActionHandlerException {
      int poId = 0;
      int vendorId = 0;
      int row = 0;
      String poIdProp = "Id";
      String vendorIdProp = "VendorId";
      String temp = null;
     
      try {
    	  row = this.getSelectedRow("selCbx"); 
    	  temp = this.getPropertyValue(poIdProp + row);
    	  poId = Integer.parseInt(temp);
    	  temp = this.getPropertyValue(vendorIdProp + row);
    	  vendorId = Integer.parseInt(temp);
      }
      catch (SystemException e) {
    	  System.out.println(e.getMessage());
    	  throw new ActionHandlerException(e);
      }
      catch (NotFoundException e) {
    	  System.out.println(e.getMessage());
    	  throw new ActionHandlerException(e);
      }
      
      this.retrievePurchaseOrder(poId, vendorId);
  }
  

  /**
   * Uses _poId and _vendorId to retrieve purchase order data and vendor data from the database.   After the data is retrieved, this 
   * method packages the data into various objects which are sent to the client via HttpServletRequest object for presentation.
   * <p>
   * <p>
   * The following list the java object created and the names used to identify each object from the HttpServletRequest object on the client:
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
   * @param _poId The id of the target purchase order.
   * @param _vendorId The id of the target purchase order.
   * @throws ActionHandlerException
   */
  protected void retrievePurchaseOrder(int _poId, int _vendorId) throws ActionHandlerException {
      String method = "[" + this.className + ".retrievePurchaseOrder] ";
      double orderTotal = 0;
      PurchaseOrder po = null;
      PurchaseOrderStatus pos = null;
      PurchaseOrderStatusHist posh = null;
      VwCreditorBusiness vendor = null;
      ArrayList poItems = null;
      ArrayList vendors = null;
      
      try {
    	  // Get vendor data.  Expect to receive a vendor id at all times since purchase orders created per vendor.
    	  vendors = this.vendApi.findCreditorBusiness("creditor_id = " + _vendorId);
    	  if (vendors.size() > 0) {
    		  vendor = (VwCreditorBusiness) vendors.get(0);
    		  this.request.setAttribute("vendor", vendor);
    	  }
    	  else {
    		  throw new ActionHandlerException("Vendor could not be found using id: " + _vendorId);
    	  }
    	  
           // Get base purchase order.
    	  po = this.api.findPurchaseOrder(_poId);
    	  if (po == null) {
    		  // This must be a new purchase order request, so ensure that all objects are created 
    		  //  and initialized to prevent null exceptions on the client.
    		  this.createNewPurchaseOrderData();
    	  }
    	  else {
              // Get purchase order items
              poItems = this.api.findVendorItemPurchaseOrderItems(_poId);
              // Get Purchase Order Total and store in po base object
              orderTotal = this.api.calcPurchaseOrderTotal(_poId);
              po.setTotal(orderTotal);
              
              // Get purchase order current status
              posh = this.api.findCurrentPurchaseOrderHistory(_poId);
              pos = this.api.findPurchaseOrderStatus(posh.getPurchaseOrderStatusId());    	
              this.request.setAttribute("po", po);
              this.request.setAttribute("poItems", poItems);
              this.request.setAttribute("pos", pos);
              this.request.setAttribute("posh", posh);
              this.request.setAttribute("msg", this.msg);
    	  }
      }
      catch (PurchaseOrderException e) {
    	  System.out.println(method + e.getMessage());
          throw new ActionHandlerException(e);
      }
      catch (GLAcctException e) {
    	  System.out.println(method + e.getMessage());
          throw new ActionHandlerException(e);
      }
      return;
  }
  
  
  /**
   * Creates various purchase order java objects, which are properly initialized but are not pointing to any related entities, and stores 
   * them on the HttpServleRequest object to be sent to client for presentation.
   * <p>
   * <p>
   * The following list dipicts the actual java object created and stored on the HttpServletRequest object:
   * <p>
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
   * </table>
   *
   */
  private void createNewPurchaseOrderData()  {
	  PurchaseOrder po = null;
      PurchaseOrderStatus pos = null;
      PurchaseOrderStatusHist posh;
      ArrayList poItems = null;
      
      po = PurchasesFactory.createPurchaseOrder();
	  poItems = new ArrayList();
	  posh = PurchasesFactory.createPurchaseOrderStatusHist();
	  pos = PurchasesFactory.createPurchaseOrderStatus();

	  this.request.setAttribute("poTotal", "0.00");
	  this.request.setAttribute("po", po);
      this.request.setAttribute("poItems", poItems);
      this.request.setAttribute("pos", pos);
      this.request.setAttribute("posh", posh);
      return;
  }
  

  /**
   * Handles the client's request to add items to a purchase order without performing a purchase order item refresh.   This method is 
   * generally invoked when the user has completed select one or more vendor inventory items to be linked to a purchase order.
   * 
   * @throws ActionHandlerException
   * @throws DatabaseException
   */
  public void saveNewItems() throws ActionHandlerException, DatabaseException {
	  String poIdProp = "PurchaseOrderId";
      String vendorIdProp = "VendorId";
      String itemIdProp = "ItemMasterId";
      int poId = 0;
      int vendorId = 0;
      int itemId = 0;
      int row = 0;
      String temp = null;
      String httpItemId[];
      PurchaseOrder po = null;
      PurchaseOrderItems poi = null;
      VwVendorItems vi = null;
      ArrayList items = new ArrayList();
     
      // Get Vendor Id and Purchase Order Id
      try {
          temp = this.getPropertyValue(vendorIdProp);
          vendorId = Integer.parseInt(temp);
    	  temp = this.getPropertyValue(poIdProp);
    	  poId = Integer.parseInt(temp);
    	  po = this.api.findPurchaseOrder(poId);
          
          // If purchase order does not exist, then establish a new one.
          if (po == null) {
              po = PurchasesFactory.createPurchaseOrder();
              po.setVendorId(vendorId);
          }
    	  
      }
      catch (NotFoundException e) {
    	  System.out.println(e.getMessage());
    	  this.msg = "Vendor id and/or purchase order id could not be found from client";
    	  throw new ActionHandlerException(e);
      }
      catch (PurchaseOrderException e) {
		  throw new ActionHandlerException(e);
	  }
      
      // Get selected items
      httpItemId = this.request.getParameterValues("selCbx");
	  if (httpItemId == null) {
          if (po.getId() == 0) {
              throw new ActionHandlerException("At least one item is required to be selected for a new purchase order");
          }
		  this.retrievePurchaseOrder(poId, vendorId);
		  return;
	  }
	  for (int ndx = 0; ndx < httpItemId.length; ndx++) {
		  try {
			  row = Integer.parseInt(httpItemId[ndx]);
              temp = this.getPropertyValue(itemIdProp + row);
              itemId = Integer.parseInt(temp);
			  vi = this.api.findCurrentItemByVendor(vendorId, itemId);
			  poi = PurchasesFactory.createPurchaseOrderItem(poId);
			  poi.setItemMasterId(itemId);
			  poi.setQty(1);
			  poi.setUnitCost(vi.getUnitCost());
			  items.add(poi);
		  }
		  catch (NumberFormatException e) {
			  this.msg = "The following item id could not be converted to a number during the process of adding selected items to purchase order" + poId;
			  System.out.println(this.msg);
	    	  throw new ActionHandlerException(this.msg);
		  }
          catch (NotFoundException e) {
              this.msg = "One of the selected items is not found to be associated with the target vendor";
              System.out.println(this.msg);
              throw new ActionHandlerException(this.msg);
          }
		  catch (PurchaseOrderException e) {
			  throw new ActionHandlerException(e);
		  }
	  } // end for
	  
	  // Call api method to add Items to purchase order
	  try {
		  poId = this.api.addItemsToPurchaseOrder(po, items);
		  this.transObj.commitTrans();
		  this.msg = "One or more items were added to the purchase order successfully";
		  return;
	  }
	  catch (PurchaseOrderException e) {
		  this.transObj.rollbackTrans();
		  throw new ActionHandlerException(e);
	  }
	  finally {
		  this.retrievePurchaseOrder(poId, vendorId);  
	  }
  }
  
  

  
  public void save() throws ActionHandlerException, DatabaseException {
      try {
          this.updatePurchaseOrder();
          this.transObj.commitTrans();
          this.msg = "Purchase Order was saved successfully";
          // Set data on the request object to be used by client JSP.s
          this.retrievePurchaseOrder(po.getId(), po.getVendorId());
          return;
      }
      catch (ActionHandlerException e) {
          this.msg = e.getMessage();
          this.transObj.rollbackTrans();   
          throw(e);
      }
  }
  
  /**
   * Applies the changes of an existing purchase order to the database using data obtained from the client's HttpServletRequest object.
   * 
   * @throws ActionHandlerException
   * @throws DatabaseException
   */
  protected void updatePurchaseOrder()  throws ActionHandlerException, DatabaseException {
      try {
          this.getPurchaseOrderEditPageData();
          this.api.maintainPurchaseOrder(this.po, this.selPoItems);
          return;
      }
      catch (PurchaseOrderException e) {
          throw new ActionHandlerException(e);
      }
  }
  
  
  public void submit() throws ActionHandlerException, DatabaseException {
      try {
    	  this.getPurchaseOrderEditPageData();
    	  this.api.submitPurchaseOrder(this.po, this.selPoItems);
          this.transObj.commitTrans();
          this.msg = "Purchase Order was submitted successfully";
          // Set data on the request object to be used by client JSP.s
          this.retrievePurchaseOrder(po.getId(), po.getVendorId());
          return;
      }
      catch (ActionHandlerException e) {
          this.msg = e.getMessage();
          this.transObj.rollbackTrans();   
          throw(e);
      }
      catch (PurchaseOrderException e) {
    	  this.transObj.rollbackTrans();
   		  throw new ActionHandlerException(e);
   	  }
  }
  
  
  public void cancel() throws ActionHandlerException, DatabaseException {
      try {
          this.getPurchaseOrderEditPageData();
          this.api.cancelPurchaseOrder(this.po.getId());
          this.transObj.commitTrans();
          this.msg = "Purchase Order was cancelled successfully";
          // Set data on the request object to be used by client JSP.s
          this.retrievePurchaseOrder(po.getId(), po.getVendorId());
          return;
      }
      catch (ActionHandlerException e) {
          this.msg = e.getMessage();
          this.transObj.rollbackTrans();   
          throw(e);
      }
      catch (PurchaseOrderException e) {
          this.transObj.rollbackTrans();
          throw new ActionHandlerException(e);
      }
  }
  
  public void delete() throws ActionHandlerException, DatabaseException {
      String method = "[" + this.className + ".delete] ";
      int row = 0;
      int poId = 0;
      String property = "Id";
      String temp = null;
      boolean deleteFailed = false;
      PurchaseOrder po = null;
      
      // Determine the row number selected.
      try {
          row = this.getSelectedRow("selCbx");
          // Get item master id.
          temp = this.request.getParameter(property + row);
      }
      catch (SystemException e) {
          temp = this.request.getParameter("Id");
          if (temp == null) {
              this.msg = method + "A Purchase Order must be selected in order to perform Delete operation";
              System.out.println(msg);
              throw new ActionHandlerException(this.msg, -1);              
          }
      }
      
      poId = Integer.parseInt(temp);
      try {
          this.api.deletePurchaseOrder(poId);
          this.transObj.commitTrans();
          this.msg = "Purchase Order was deleted from the database successfully";
      }
      catch (PurchaseOrderException e) {
    	  this.msg = "Purchase Order delete failed - " + e.getMessage();
          this.transObj.rollbackTrans();
          deleteFailed = true;
      }
      return;
  }
  
 
  /**
   * 
   * @throws ActionHandlerException
   */
  private void getPurchaseOrderEditPageData() throws ActionHandlerException {
	  String poIdProp = "PurchaseOrderId";
      String vendorIdProp = "VendorId";
      int poId = 0;
      int vendorId = 0;
      int itemId = 0;
      int row = 0;
      int qtyOrdered = 0;
      int qtyReceived = 0;
      double unitCost = 0;
      String temp = null;
      String httpRowId[];
      String httpSelRowId[];
     
      // Get Vendor Id and Purchase Order Id
      try {
    	  temp = this.getPropertyValue(poIdProp);
    	  poId = Integer.parseInt(temp);
    	  this.po = this.api.findPurchaseOrder(poId);
    	  temp = this.getPropertyValue(vendorIdProp);
    	  vendorId = Integer.parseInt(temp);
      }
      catch (NotFoundException e) {
    	  System.out.println(e.getMessage());
    	  this.msg = "Vendor id and/or purchase order id could not be found from client";
    	  throw new ActionHandlerException(e);
      }
      catch (PurchaseOrderException e) {
		  throw new ActionHandlerException(e);
	  }
      
      // Get selected items
      httpSelRowId = this.request.getParameterValues("selCbx");
      
      // Get all row id's
      httpRowId = this.request.getParameterValues("rowId");
	  if (httpRowId == null) {
		  // Bypass api update
		  this.retrievePurchaseOrder(poId, vendorId);
		  return;
	  }

	  this.selPoItems = new ArrayList();
	  for (int ndx = 0; ndx < httpRowId.length; ndx++) {
		  row = Integer.parseInt(httpRowId[ndx]);
		  
		  // Do not include item in update list if selected to be removed.
		  if (this.isItemSelected(httpSelRowId, row)) {
			  continue;
		  }
		  
		  temp = this.request.getParameter("ItemMasterId" + row);
		  itemId =  Integer.parseInt(temp);
		 
		  try {
			  temp = this.request.getParameter("QtyOrderd" + row);
			  qtyOrdered = Integer.parseInt(temp);
		  }
		  catch (NumberFormatException e) {
			  qtyOrdered = 0;
		  }
		  
		  try {
			  temp = this.request.getParameter("QtyReceived" + row);
			  qtyReceived = Integer.parseInt(temp);
		  }
		  catch (NumberFormatException e) {
			  qtyReceived = 0;
		  }
		  
		  try {
			  temp = this.request.getParameter("UnitCost" + row);
			  unitCost = Double.parseDouble(temp);
		  }
		  catch (NumberFormatException e) {
			  unitCost = 0;
		  }
		  
		  poi = PurchasesFactory.createPurchaseOrderItem(poId);
		  poi.setItemMasterId(itemId);
		  poi.setQty(qtyOrdered);
		  poi.setQtyRcvd(qtyReceived);
		  poi.setUnitCost(unitCost);
		  this.selPoItems.add(poi);
	  }
  }
  
  /**
   * 
   * @param _selectedRows
   * @param _rowId
   * @return
   */
  private boolean isItemSelected(String _selectedRows[], int _rowId) {
	  int selRowId = 0;
	  boolean rowSelected = false;
	  
	  if (_selectedRows == null || _selectedRows.length <= 0) {
		  return rowSelected;
	  }
	  
	  for (int ndx = 0; ndx < _selectedRows.length; ndx++) {
		  try {
			  selRowId = Integer.parseInt(_selectedRows[ndx]);
		  }
		  catch (NumberFormatException e) {
			  continue;
		  }
		  if (_rowId == selRowId) {
			  rowSelected = true;
			  break;
		  }
	  }
	  return rowSelected;
  }
  
  protected void receiveClientData() throws ActionHandlerException{}
  protected void sendClientData() throws ActionHandlerException{}

}
package com.action;

import javax.servlet.ServletContext;


import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import com.api.InventoryApi;
import com.api.GLCreditorApi;

import com.bean.VendorItems;
import com.bean.VwCreditorBusiness;
import com.bean.ItemMaster;

import com.factory.InventoryFactory;
import com.factory.AcctManagerFactory;
import com.factory.DataSourceAdapter;

import com.util.GLAcctException;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.ItemMasterException;
import com.util.ActionHandlerException;


/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and eidting 
 * Vendor/Item associations.
 * 
 * @author Roy Terrell
 *
 */
public class VendorItemAction extends AbstractActionHandler implements IRMT2ServletActionHandler {
    private InventoryApi api;
    

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public VendorItemAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    super(_context, _request);
    this.className = "VendorItemAction";
    this.api = InventoryFactory.createInventoryApi(this.dbConn, _request);
  }
  

  /**
   * Displays the Vendor-Item association maintenance page which retreives all items currently associated with the 
   * vendor and all items from inventory that are not assigned to the vendor.
   *
   *@throws ActionHandlerException  When a general database error occurs.
   */
  public void startSearchConsole() throws ActionHandlerException {
	  this.refreshConsole();
  }
  
  

  /**
   * Retreives the items that are available to the vendor and those items that assigned to the vendor which the data 
   * is passed to the clients HttpServletRequest object for presentation.
   * 
   * @throws ActionHandlerException
   */
  private void refreshConsole() throws ActionHandlerException {
	  int vendorId = this.getHttpVendorId(); 
	  ArrayList availList, asgnList = null;
	  VwCreditorBusiness vend = null;
	  
	  try {
		  // Get items assigned to Vendor
		  asgnList = this.api.findVendorAssignItems(vendorId);
          // Get items item not assigned to Vendor
		  availList = this.api.findVendorUnassignItems(vendorId);
		  // Getvendor details
		  vend = this.getVendorDetails(vendorId);
		  
		  this.request.setAttribute("vendor", vend);
		  this.request.setAttribute("availList", availList);
	      this.request.setAttribute("assignList", asgnList);
	  }
	  catch (ItemMasterException e) {
		  throw new ActionHandlerException(e);
	  }

  }

  /**
   * Begins the process of assigning one or more inventory items to a vendor.   This method is expecting the 
   * following client variables to be accessible from the HttpServletRequest object for processing:  "VendorId" and "AvailItems".
   * 
   * @return
   * @throws ActionHandlerException
   */
  protected int assignVendorItems() throws ActionHandlerException {
	  int rc = 0;
	  int vendorId = this.getHttpVendorId(); 
	  int items[] = this.getSelectedItems("AvailItems");
      try {
          try {
              rc = this.api.assignVendorItems(vendorId, items);
              this.transObj.commitTrans();
              this.startSearchConsole();
              return rc;
          }
          catch (ItemMasterException e) {
              this.transObj.rollbackTrans();
              throw new ActionHandlerException(e);
          }
      }
      catch (DatabaseException e) {
          throw new ActionHandlerException(e);
      }
  }
  
  /**
   * Begins the process of unassigning one or more inventory items from a vendor.   This method is expecting the 
   * following client variables to be accessible from the HttpServletRequest object for processing:  "VendorId" and "AssignItems".
   * 
   * @return
   * @throws ActionHandlerException
   */
  protected int removeVendorItems() throws ActionHandlerException {
	  int rc = 0;
	  int vendorId = this.getHttpVendorId(); 
	  int items[] = this.getSelectedItems("AssignItems");
      try {
          try {
              rc = this.api.removeVendorItems(vendorId, items);
              this.transObj.commitTrans();
              this.startSearchConsole();
              return rc;
          }
          catch (ItemMasterException e) {
              this.transObj.rollbackTrans();
              throw new ActionHandlerException(e);
          }
      }
      catch (DatabaseException e) {
          throw new ActionHandlerException(e);
      }
  }
  
  
  /**
   * Obtains the vendor's id from the http client.
   * 
   * @return vendor id
   * @throws ActionHandlerException When a vendor id is not supplied by the client or the vendor id was found 
   * to be invalid. 
   */
  private int getHttpVendorId() throws ActionHandlerException {
	  String strVendorId = this.request.getParameter("VendorId");
	  int vendorId = 0;
	  if (strVendorId == null) {
		  throw new ActionHandlerException("Vendor must be supplied in order to invoke Vendor/Item Association page");
	  }
	  
	  try {
		  vendorId = Integer.parseInt(strVendorId);
		  return vendorId;
	  }
	  catch (NumberFormatException e) {
		  throw new ActionHandlerException("An invalid Vendor number has been supplied.  Vendor/Item Association page cannot be displayed");
	  }
  }
  
  /**
   * Obtains the item master id from the http client.
   * 
   * @return The id of the inventory item
   * @throws ActionHandlerException
   */
  private int getHttpItemMasterId() throws ActionHandlerException {
	  String strItemId = this.request.getParameter("ItemMasterId");
	  int itemId = 0;
	  if (strItemId == null) {
		  throw new ActionHandlerException("Vendor Item Id is required");
	  }
	  
	  try {
		  itemId = Integer.parseInt(strItemId);
		  return itemId;
	  }
	  catch (NumberFormatException e) {
		  throw new ActionHandlerException("An invalid Item Master number has been supplied");
	  }
  }
  
  /**
   * Obtains one or more Inventory item id's from the client's HttpServletRequest object.
   * 
   * @param _property The name of the property containing the inventory item id's
   * @return An integer array of selected item id's
   * @throws SystemException If the client did not select one or more items, or one or more item id's are invalid.
   */
  private int [] getSelectedItems(String _property) throws ActionHandlerException {
	  String values[] = this.request.getParameterValues(_property);
	  
	  if (values == null) {
		  throw new ActionHandlerException("One or more items must be selected");
	  }
	  
	  int items[] = new int[values.length];
	  for (int ndx = 0; ndx < values.length; ndx++) {
		  try {
			  items[ndx] = Integer.parseInt(values[ndx]);
		  }
		  catch (NumberFormatException e) {
			  throw new ActionHandlerException("One or more of the selected items are invalid");
		  }
	  }
	  return items;
  }
  
  /**
   * Drives the process of editing an assigned vendor inventory item.      Only one item can be edited at a time. 
   * 
   * @throws ItemMasterException If more than one item is found for processing.
   */
  public void edit() throws ActionHandlerException {
      int items[];
      int vendorId = this.getHttpVendorId();

      items = this.getSelectedItems("AssignItems");
      if (items.length > 1) {
    	  throw new ActionHandlerException("Only one assigned vendor item can be edited at a time");
      }

      this.setUpVendorItemEdit(vendorId, items[0]);
      this.request.setAttribute("msg", this.msg);
      return;
  }
  
  /**
   * Uses _vendorId and _itemId to retrieve the vendor, item master, and vendor item data objects to be used by the client. 
   * <p>
   * The following objects are set on the request object identified as "vendor", "itemmaster", and "vendoritem", respectively: 
   * {@link VwCreditorBusiness}, {@link ItemMaster} and {@link VendorItems}. 
   * 
   * @param _vendorId The vendor id
   * @param _itemId The item master id
   * @throws ActionHandlerException
   */
  private void setUpVendorItemEdit(int _vendorId, int _itemId) throws ActionHandlerException {
      ItemMaster im = null;
      VendorItems vi = null;
      VwCreditorBusiness cred = null;
      
      try {
    	  cred = this.getVendorDetails(_vendorId);
    	  vi = this.api.findVendorItem(_vendorId, _itemId);
          im = this.api.findItemById(_itemId);  
      }
      catch(ItemMasterException e) {
    	  	throw new ActionHandlerException(e);
      }
      
      // Set data on the request object to be used by client JSP.s
      this.request.setAttribute("vendor", cred);
      this.request.setAttribute("itemmaster", im);
      this.request.setAttribute("vendoritem", vi);
  }
  
  
  /**
   * Obtains the vendor detail data derived from the creditor and business tables.
   * 
   * @param _vendorId The id of the vendor
   * @return {@link VwCreditorBusiness} or null if an error occurs.
   */
  private VwCreditorBusiness getVendorDetails(int _vendorId) {
	  GLCreditorApi credApi = null;
      VwCreditorBusiness cred = null;
      ArrayList credList = null;
      
      try {
    	  credApi = AcctManagerFactory.createCreditor(this.dbConn);
    	  credList = credApi.findCreditorBusiness("creditor_id = " + _vendorId);
    	  cred = (VwCreditorBusiness) credList.get(0);
    	  return cred;
      }
      catch (GLAcctException e) {
    	  	return null;
      }
      catch (DatabaseException e) {
   			return null;
      }
      catch (SystemException e) {
   	   		return null;
      }
  }
  
  /**
   * Drives the assignment of inventory items to vendors  
   *
   */
  public void add() throws ActionHandlerException {
	  this.assignVendorItems();
      return;
  }
  
  /**
   * Applies changes made to a {@link VendorItems} object to the database.   The changes are gathered from the client's request object
   * and packaged into a VendorItems object which is passed to the InventoryApi to update the database.   
   *  
   * @throws ItemMasterException
   */
  public void save() throws ActionHandlerException, DatabaseException {
      String method = "[" + this.className + ".save] ";
      VendorItems vi = null;
      
      try {
          vi = InventoryFactory.createVendorItem();
          DataSourceAdapter.packageBean(this.request, vi);    
      }
      catch (SystemException e) {
          System.out.println(method + "Problem gathering Vendor Item request parameters.");
          System.out.println(method + e.getMessage());
          throw new ActionHandlerException(e);
      }
      
      try {
          this.api.maintainVendorItem(vi);
          this.transObj.commitTrans();
          this.setUpVendorItemEdit(vi.getVendorId(), vi.getItemMasterId());
          this.msg = "Vendor Item changes were saved successfully";
          this.request.setAttribute("msg", this.msg);
          return;
      }
      catch (ItemMasterException e) {
          this.msg = e.getMessage();
          this.transObj.rollbackTrans();    
      }
  }
  
  /**
   * Drives the process of disassociating inventory items from vendors
   */
  public void delete() throws ActionHandlerException, DatabaseException {
	  this.removeVendorItems();
     return;
  }
  
  
  public void activateItemOverride() throws ActionHandlerException {
	  int vendorId = this.getHttpVendorId(); 
	  int items[] = this.getSelectedItems("AssignItems");
      try {
          try {
              this.api.addInventoryOverride(vendorId, items);  
              this.transObj.commitTrans();
              this.startSearchConsole();
          }
          catch (ItemMasterException e) {
              System.out.println(e.getMessage());
              this.transObj.rollbackTrans();
              throw new ActionHandlerException(e);
          }          
      }
      catch (DatabaseException e) {
          throw new ActionHandlerException(e);
      }

	  
     return;
  }
  
  
  public void deActivateItemOverride() throws ActionHandlerException {
	  int vendorId = this.getHttpVendorId(); 
	  int items[] = this.getSelectedItems("AssignItems");
      
      try {
          try {
              this.api.removeInventoryOverride(vendorId, items);
              this.transObj.commitTrans();
              this.startSearchConsole();
          }
          catch (ItemMasterException e) {
              System.out.println(e.getMessage());
              this.transObj.rollbackTrans();
              throw new ActionHandlerException(e);
          }    
      }
      catch (DatabaseException e) {
          throw new ActionHandlerException(e);
      }
     return;
  }
  
  protected void receiveClientData() throws ActionHandlerException{}
  protected void sendClientData() throws ActionHandlerException{}

}
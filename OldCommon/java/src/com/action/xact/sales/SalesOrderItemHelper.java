package com.action.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.ArrayList;

import com.api.InventoryApi;
import com.api.db.DatabaseException;

import com.bean.CustomerWithName;
import com.bean.ItemMaster;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.Customer;
import com.bean.VwSalesorderItemsBySalesorder;
import com.bean.Xact;

import com.constants.ItemConst;

import com.factory.InventoryFactory;

import com.action.ActionHandlerException;
import com.action.HttpBasicHelper;

import com.util.ItemMasterException;
import com.util.SystemException;


/**
 * Provides services to manaage the items of a sales order.   This crux of this class is obtaining all
 *  items of a sales order _so and grouping each item to either a list of service items or merchandise items.
 * <p>
 * The service and merchandise item lists are contained in their own respective collections.
 * 
 * @author Roy Terrell
 *
 */ 
public class SalesOrderItemHelper extends HttpBasicHelper {
	 private InventoryApi itemApi;
	 private Customer cust;
     private SalesOrder so;
     private List items;
     private List srvcItems;
     private List merchItems;
     private Xact xact;
     private Logger logger;
	  
      
	
	  /**
	   * Default constructor
	   *
	   */
	  public SalesOrderItemHelper()  {
		  super(); 
		  this.srvcItems = new ArrayList();
    	  this.merchItems = new ArrayList();
		  logger = Logger.getLogger(SalesOrderItemHelper.class);
	  }

	  /**
	   * Constructor used to initialize a SalesOrderHelper object using _context and _request.
	   * 
	   * @param _context The context of the web app.
	   * @param _request The HttpServletRequest object
	   * @throws SystemException
	   * @throws DatabaseException
	   */
	  public SalesOrderItemHelper(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
		  super(_context, _request);
		  this.itemApi = InventoryFactory.createInventoryApi(this.dbConn, _request);
	   }
	  
	  /**
	   * Constructor used to initialize a SalesOrderHelper object using _context, _request, _customer, and _so.
	   * 
	   * @param _context The context of the web app.
	   * @param _request The HttpServletRequest object
	   * @param _customer Customer object
	   * @param _so Sales Order object
	   * @throws SystemException
	   * @throws DatabaseException
	   */
	  public SalesOrderItemHelper(ServletContext _context, HttpServletRequest _request, Customer _customer, SalesOrder _so)   throws SystemException, DatabaseException {
		  this(_context, _request);
		  this.cust = _customer;
		  this.so = _so;
	  }
      
      
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super.init(_context, _request);
          this.srvcItems = new ArrayList();
          this.merchItems = new ArrayList();
          this.items = new ArrayList();
          logger = Logger.getLogger("SalesOrderItemHelper");
      }
	
      /**
       * Converts a collection of SalesOrderItems to an array of VwSalesorderItemsBySalesorder objects and separated 
       * each item to either a collection of service items or a collecion of merchandise items.   
	   * <p>
	   * The results of this operation can be obtained fro the following method calls: getSrvcItems() and getMerchItems().
	   *   
       * @param _items a list of {@link SalesOrderItems} objects
       * @return The total number of items converted.
       * @throws SystemException
       * @see getSrvcItems, getMerchItems
       */
    public int packageItemsByTypes(List _items) throws SystemException {
        this.items.addAll(_items);
        VwSalesorderItemsBySalesorder soi[] = this.convertSalesOrderItems(_items);
        return this.packageItemsByTypes(soi);
    }
    
    
	  /**
	   * Creates one or more VwSalesorderItemsBySalesorder objects from a list of item numbers.   
	   * <p>
	   * For each item number contained in the _itemNo array, a corresponding ItemMaster data is obtained.
	   * The results of this operation can be obtained fro the following method calls: getSrvcItems() and getMerchItems().
	   *  
	   * @param _itemNo - Item Master Id's as an Array of Strings.
	   * @return The total number of items created.
	   * @throws SystemException
	   * @see getSrvcItems, getMerchItems
	   */
	  public int packageItemsByTypes(String _itemNo[]) throws SystemException {
	      VwSalesorderItemsBySalesorder soi[] = null;
	      int tot = 0;
	      int itemId = 0;
	      
	      if (_itemNo == null) {
	          return tot;
	      }
	      
	      tot = _itemNo.length;
	      soi = new VwSalesorderItemsBySalesorder[tot];
	      for (int ndx = 0; ndx < tot; ndx++) {
	          try {
	              itemId = Integer.parseInt(_itemNo[ndx]);
	          }
	          catch (NumberFormatException e) {
	              logger.log(Level.ERROR, "Item Number " + _itemNo[ndx] + " could not be converted to an integer value.");
	              itemId = 0;
	          }
	          
	          // Create sales order item record using the item master.
              soi[ndx] = this.convertItemMaster(itemId);
              soi[ndx].setCustomerId(this.so.getCustomerId());
              soi[ndx].setSalesOrderItemId(0);
              soi[ndx].setSalesOrderId(0);

              // Default item quantity to 1
              soi[ndx].setOrderQty(1);
	      } // end for
	      return this.packageItemsByTypes(soi);
	  }
    
	  
	  /**
	   * Separates each item contained in _items to either a collection of service items or a collecion of merchandise items.
	   * <p>
	   * The results of this operation can be obtained fro the following method calls: getSrvcItems() and getMerchItems().
	   *     
	   * @param _items An array of {@link VwSalesorderItemsBySalesorder} objects
	   * @return The total number of items created.
	   * @throws SystemException
	   * @see getSrvcItems, getMerchItems
	   */
	    private int packageItemsByTypes(VwSalesorderItemsBySalesorder _items[]) throws SystemException {
	        int total = 0;
	        if (_items == null) {
	        	return total;
	        }
	          
	         // Package items in separate ArrayList collections based on theit item types
	         for (int ndx = 0; ndx < _items.length; ndx++) {
	             switch (_items[ndx].getItemTypeId()) {
	             case ItemConst.ITEM_TYPE_SRVC:
	            	 this.srvcItems.add(_items[ndx]);
	            	 break;
	             case ItemConst.ITEM_TYPE_MERCH:
	            	 this.merchItems.add(_items[ndx]);
	            	 break;
	             } // end switch
	             total += _items[ndx].getRetailPrice();
	         } // end for  
	         logger.log(Level.DEBUG, "Total of all items: " + total);
	         return total;
	    }
	  
	  
    /**
	   * Converts one or more {@link SalesOrderItems} objects to a {@link VwSalesorderItemsBySalesorder} object.
	   * 
	   * @return An array of VwSalesorderItemsBySalesorder objects.
	   */
	  private VwSalesorderItemsBySalesorder [] convertSalesOrderItems(List _items) throws SystemException {
	      VwSalesorderItemsBySalesorder vwSoi[] = null;
	      SalesOrderItems soi = null;
	      int tot = 0;
	      
	      if (_items == null) {
	          return null;
	      }
	      
	      tot = _items.size();
	      vwSoi = new VwSalesorderItemsBySalesorder[tot];
	      
	      try {
		      for (int ndx = 0; ndx < tot; ndx++) {
		    	  soi = (SalesOrderItems) _items.get(ndx);
		    	  vwSoi[ndx] = this.convertSalesOrderItems(soi);
		    	  vwSoi[ndx].setCustomerId(this.cust.getId());
		      } // end for
		      return vwSoi;	    	  
	      }
	      catch (ClassCastException e) {
	    	  this.msg = "ClassCastException: Only objects of type SalesOrderItems can be converted and separated by type";
	    	  logger.log(Level.ERROR, this.msg);
	    	  throw new SystemException(this.msg);
	      }
	  }
	  
	  
	
	  /**
	   * Converts a {@link SalesOrderItems} object to a VwSalesorderItemsBySalesorder
	   * 
	   * @param item Object of type SalesOrderItems
	   * @return {@link  VwSalesorderItemsBySalesorder}
	   * @throws SystemException
	   */
	  private VwSalesorderItemsBySalesorder convertSalesOrderItems(SalesOrderItems item) throws SystemException {
	      VwSalesorderItemsBySalesorder soi = null;
	      if (item == null) {
	          return null;
	      }
	      soi = this.convertItemMaster(item.getItemMasterId());
    	  soi.setSalesOrderItemId(item.getId());
    	  soi.setSalesOrderId(item.getSalesOrderId());
    	  if (item.getItemNameOverride() != null && !item.getItemNameOverride().equals("")) {
    		  soi.setItemName(item.getItemNameOverride());
    	  }
    	  soi.setOrderQty(item.getOrderQty());
    	  return soi;
	  }
	
	  /**
	   * Gets the VwSalesorderItemsBySalesorder equivalent to itemId.  <p>First itemId is used to obtain an 
	   * item master object, and the item master object is converted to a VwSalesorderItemsBySalesorder object.
	   * 
	   * @param itemId The id of the item master object.
	   * @return {@link VwSalesorderItemsBySalesorder}
	   * @throws SystemException
	   */
	  private VwSalesorderItemsBySalesorder convertItemMaster(int itemId) throws SystemException {
	      VwSalesorderItemsBySalesorder soi = null;
	      ItemMaster im = null;
	      try {
	    	  im = this.itemApi.findItemById(itemId);
	          soi = new VwSalesorderItemsBySalesorder();
	    	  soi.setItemMasterId(im.getId());
	    	  soi.setItemName(im.getDescription());
	    	  soi.setVendorId(im.getVendorId());
	    	  soi.setVendorItemNo(im.getVendorItemNo());
	    	  soi.setItemTypeId(im.getItemTypeId());
	    	  soi.setItemSerialNo(im.getItemSerialNo());
	    	  soi.setQtyOnHand(im.getQtyOnHand());
              soi.setMarkup(im.getMarkup());
              soi.setUnitCost(im.getUnitCost());
              soi.setRetailPrice(soi.getUnitCost() * soi.getMarkup());
		      return soi;
	      }
	      catch (ItemMasterException e) {
	    	  throw new SystemException(e.getMessage());
	      }
	  } 
	  
	  
	  /**
	   *  Retrieves all master items (service and merchandise) which have not been associated with an 
	   *  existing sales order.   All items are packaged and separated by type into an ArrayList of 
	   *  {@link VwSalesorderItemsBySalesorder} objecst and are transmitted to the client via the 
	   *  HttpServletRequest object identified as "service" and "merchandise".   {@link CustomerWithName} 
	   *   and {@link SalesOrder} data is sent to the client via the HttpServleRequest object identified as "customer" 
	   *   and "salesorder", respectively.
	   *     
	   * @throws SystemException
	   */
	  public void setupAvailSalesOrderItems() throws SystemException {
	      String criteria = null;
	      
	      try {
	          // Filter out items already selected if sales order has been assigned an id.
	          if (this.so.getId() > 0) {
	              criteria = " and id not in ((select item_master_id from sales_order_items where sales_order_id = " + this.so.getId() + "))";    
	          }
	          else {
	              criteria = "";
	          }
	          
	          // Get Service and Merchandise Items.
	          itemApi.setBaseClass("com.bean.ItemMaster");
	          itemApi.setBaseView("ItemMasterView");
	          this.srvcItems = itemApi.findItem("item_type_id = 1" + criteria, "description");
	          this.merchItems = itemApi.findItem("item_type_id = 2" + criteria, "description");
	          return;
	      }
           catch (ItemMasterException e) {
               System.out.println("[XactSalesOnAccountAction.getAvailJournalEntryItems] ItemMasterException occurred. " + e.getMessage());
               throw new SystemException(e);
           }
	  }
	  
	  
	  
    
    /**
     * Gets the customer object.
     * @return {@link Customer}
     */
       public Customer getCustomer() {
           return this.cust;
      }
       /**
        * Sets the customer member variable to value.
        * @param value
        */
      public void setCustomer(Customer value) {
          this.cust = value;
      }
      
      /**
       * Sets transaction object.
       * 
       * @param value
       */
      public void setXact(Xact value) {
          this.xact = value;
      }
      
      /**
       * Gets transaction object.
       * 
       * @return
       */
      public Xact getXact() {
          return this.xact;
      }
      
      /**
       * Gets Sales Order object.
       * 
       * @return {@link SalesOrder}
       */
      public SalesOrder getSalesOrder() {
          return this.so;
      }
      /**
       * Sets sales order member variable to value.
       * @param value
       */
      public void setSalesOrder(SalesOrder value) {
          this.so = value;
      }
      
      /**
       * Gets the list of sales order items.
       * 
       * @return A complete list of service and merchandise items of type, {@link alesOrderItems}.
       */
      public List getItems() {
          return this.items;
      }
      
      /**
       * Gets service items
       * @return ArrayList
       */
      public List getSrvcItems() {
          return this.srvcItems;
      }
      /**
       * Gets merchandise items
       * @return
       */
      public List getMerchItems() {
          return this.merchItems;
      }
      
      /**
       * Gets the list of sales order items.
       * 
       * @return A complete list of service and merchandise items of type, {@link VwSalesorderItemsBySalesorder}.
       */
      public List getExtItems() {
    	  ArrayList combinedItems = new ArrayList();
    	  combinedItems.addAll(this.srvcItems);
    	  combinedItems.addAll(this.merchItems);
    	  return combinedItems;
      }
  
      
      protected void receiveClientData() throws ActionHandlerException{}
      protected void sendClientData() throws ActionHandlerException{}
      public void add() throws ActionHandlerException{}
      public void edit() throws ActionHandlerException{}
      public void save() throws ActionHandlerException{}
      public void delete() throws ActionHandlerException{}
   
}
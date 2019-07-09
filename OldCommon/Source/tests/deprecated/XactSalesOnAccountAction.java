package com.action;

import java.sql.Types;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.bean.Customer;
import com.bean.CustomerActivity;
import com.bean.CustomerWithName;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.VwSalesorderItemsBySalesorder;
import com.bean.SalesInvoice;
import com.bean.SalesOrderStatus;
import com.bean.SalesOrderStatusHist;
import com.bean.CombinedSalesOrder;
import com.bean.ItemMaster;
import com.bean.Xact;

import com.api.GLCustomerApi;
import com.api.InventoryApi;
import com.api.SalesOrderApi;
import com.api.XactManagerApi;

import com.factory.AcctManagerFactory;
import com.factory.SalesFactory;
import com.factory.XactFactory;
import com.factory.InventoryFactory;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.XactException;
import com.util.SalesOrderException;
import com.util.GLAcctException;
import com.util.ItemMasterException;

import com.constants.SalesConst;
import com.constants.ItemConst;


/**
 * Concrete class that manages customer sales on account and cash receipts transactions only.   The functionality of this 
 * class is to serve the client's request to manage sales order transactions.   This includes the creation and viewing of salses orders 
 * and payment activities towards sales orders (Cash Receipts).
 * 
 * @author Roy Terrell
 *
 */
public class XactSalesOnAccountAction extends AbstractXactJournalEntryAction {
    protected final static String CLIENT_SRVC_ITEM_CODE = "Srvc"; 
    protected final static String CLIENT_MERCH_ITEM_CODE = "Merch";
    protected final static String CLIENT_ITEM_SELECTOR = "selCbx";
    protected final static String CLIENT_ITEM_ROWID = "rowId";
    protected GLCustomerApi  custApi;
	 protected SalesOrderApi    salesApi;
//	 protected XactManagerApi xactApi;
	 protected InventoryApi itemApi;
	
	/**
	 * Default constructor.
	 *
	 */
	 public XactSalesOnAccountAction() {
	     super();
	 }

	  /**
		* Main contructor for this action handler.
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	   public XactSalesOnAccountAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
	       super(_context, _request);
		    this.salesApi = SalesFactory.createApi(this.dbConn, _request);
//		    this.xactApi = XactFactory.create(this.dbConn, _request);
		    this.custApi = AcctManagerFactory.createCustomer(this.dbConn, _request);
		    this.itemApi = InventoryFactory.createInventoryApi(this.dbConn, _request);
	   }
	
	  /**
	   * This method is used to initialize this object.   It is mandatory to invoke this 
	   * method after instantiating this object via the default constructor.
	   * 
	   * @throws SystemException  
	   */
	  public void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
	      super.init(_context, _request);
	      try {
	          this.salesApi = SalesFactory.createApi(this.dbConn);
//	          this.xactApi = XactFactory.create(this.dbConn);    
	      }
	 	   catch (DatabaseException e) {
	 	       throw new SystemException(e);
	 	   }
	      System.out.println("XactSalesOnAccountAction.init(ServletContext, HttpServletRequest)");
	  }
	  
	  	  
	  /**
	   * Retrieves a list of transactions pertaining to either a customer's sales orders or payments on account.
	   *  
	   * @throws XactException
	   */
	  public void getJournalEntryList() throws XactException {
		  String custIdStr = null;
		  int custId = 0;
		  Customer cust = null;
		  
		  try {
			  // Obtain Customer data from the request object.
			  custIdStr = this.request.getParameter("Id");
			  custId = Integer.parseInt(custIdStr);  
			  cust = this.custApi.createCustomer();
			  cust.setId(custId);
			  
			  CustomerWithName cwn = custApi.findCustomerWithName(custId);
			  
			  if (cust == null) {
			      System.out.println("Customer is null");
			  }
			  
	          // Get Customer's Order histroy.
			  this.getOrderHist(cust);
			  
			  this.request.setAttribute("customer", cwn);
		  } // end try
		  catch (GLAcctException e) {
		      System.out.println("[XactSalesOnAccountAction.getXactList] GLAcctException occurred. " + e.getMessage());
		      throw new XactException(e);
		  }
		  catch (SalesOrderException e ) {
		      throw new XactException(e);
		  }
		  catch (SystemException e) {
			  System.out.println("[XactSalesOnAccountAction.getXactList] SystemException occurred. " + e.getMessage());
			  throw new XactException(e);
		  }
	  }
	  
	  /**
	   * Retrieves customer's  order history using _cust.
	   * 
	   * @param _cust Customer object
	   * @return An array of the customer orders.
	   * @throws SalesOrderException
	   */
	  private CombinedSalesOrder [] getOrderHist(Customer _cust) throws SalesOrderException {
		  CombinedSalesOrder cso[] = null;
		  ArrayList list = null;
		  try {
			  list = this.salesApi.findExtendedSalesOrderByCustomer(_cust.getId());
			  cso = new CombinedSalesOrder[list.size()];
			  return (CombinedSalesOrder []) list.toArray(cso);
		  }
		  catch (SalesOrderException e ) {
		      System.out.println("[XactSalesOnAccountAction.getOrderHist] SalesOrderExeption occurred. " + e.getMessage());
		      throw e;
		  }
		  finally {
		      this.request.setAttribute("OrderHist", list);
		  }
	  }
	  

	  /**
	   * Initiates the process of gathering all service and merchandise inventory items that
	   *  will be made available for the user to select from when establishing a new sales order. 
	   * 
	   * @throws XactException
	   */
	  public void getAvailJournalEntryItems() throws XactException {

	      String tempStr = null;
	      int custId = 0;
	      
	      // Get Customer Id from the Customer Edit Page
	      try {
	          tempStr = this.request.getParameter("Id");
	          custId = Integer.parseInt(tempStr);
	      }
	      catch (NumberFormatException e) {
	          custId = 0;
	      }
	      
	      // Get Available journal entry items using customer id
	      this.getAvailJournalEntryItems(custId, 0);
	  }

	  
	  
	  /**
	   *  Retrieves all master items (service and merchandise) which have not been associated with an 
	   *  existing sales order using _customerId and _salesOrderId.   All items are packaged and separated 
	   *  by type into an ArrayList of {@link VwSalesorderItemsBySalesorder} objecst and are transmitted 
	   *  to the client via the HttpServletRequest object identified as "service" and "merchandise".   {@link CustomerWithName} 
	   *   and {@link SalesOrder} data is sent to the client via the HttpServleRequest object identified as "customer" 
	   *   and "salesorder", respectively.
	   *     
	   * @param _customerId - The customer Id.
	   * @param _salesOrderId - The sales order Id.
	   * @throws XactException
	   */
	  public void getAvailJournalEntryItems(int _customerId, int _salesOrderId) throws XactException {
	      ArrayList srv = null;
	      ArrayList merch = null;
	      CustomerWithName cwn = null;
	      SalesOrder so = null;
	      String criteria = null;
	      
	      try {
	          this.custApi = AcctManagerFactory.createCustomer(this.dbConn);    
	          cwn = custApi.findCustomerWithName(_customerId);
              InventoryApi itemApi = InventoryFactory.createInventoryApi(this.dbConn);
	          
	          // Get sales order object
	          try {
	              so = this.salesApi.findSalesOrderById(_salesOrderId);    
	          }
	          catch (SalesOrderException e) {
	              System.out.println("[XactSalesOnAccountAction.getAvailJournalEntryItems] Sales Order could not be obtained");
	          }
	          
	          // Filter out items already selected if sales order has been assigned an id.
	          if (_salesOrderId > 0) {
	              criteria = " and id not in ((select item_master_id from sales_order_items where sales_order_id = " + _salesOrderId + "))";    
	          }
	          else {
	              criteria = "";
	          }
	          
	          // Get Service and Merchandise Items.
	          itemApi.setBaseClass("com.bean.ItemMaster");
	          itemApi.setBaseView("ItemMasterView");
	          srv = itemApi.findItem("item_type_id = 1" + criteria, "description");
	          merch = itemApi.findItem("item_type_id = 2" + criteria, "description");
	          
	          this.request.setAttribute("customer", cwn);
	          this.request.setAttribute("salesorder", so);
	          this.request.setAttribute("services", srv);
	          this.request.setAttribute("merchandise", merch);
	          return;
	      }
	      catch (GLAcctException e) {
	           System.out.println("[XactSalesOnAccountAction.getAvailJournalEntryItems] GLAcctException occurred. " + e.getMessage());
	  		   throw new XactException(e);
	      }
	 	   catch (DatabaseException e) {
	 		   System.out.println("[XactSalesOnAccountAction.getAvailJournalEntryItems] DatabaseException occurred. " + e.getMessage());
	 		   throw new XactException(e);
	 	   }
	 	   catch (SystemException e) {
	 		   System.out.println("[XactSalesOnAccountAction.getAvailJournalEntryItems] SystemException occurred. " + e.getMessage());
	 		   throw new XactException(e);
	 	   }
           catch (ItemMasterException e) {
               System.out.println("[XactSalesOnAccountAction.getAvailJournalEntryItems] ItemMasterException occurred. " + e.getMessage());
               throw new XactException(e);
           }
	  }
	  
	  /**
	   * Retrieves the sales order detail based on key Customer and Sales Order data found in the client's request and transmits 
	   * the results to the client.  The detailed results will be represented as either a new sales order or an existing sales order. 
	   * The objects that are packaged into the HttpServletRequest object are as follows:
	   * <p>
	   * {@link CustomerWithName} identified as "customer"
	   * {@link SalesOrder} identified as "salesorder".
	   * 
	   * @throws XactException
	   */
	  public int getJournalEntryDetails() throws XactException {
	      Customer cust = null;
	      CustomerWithName cwn = null;
	      SalesOrder salesOrder = null;
	      SalesInvoice si = null;
		 	SalesOrderStatus sos = null;
	      Xact xact = null;
  	      double orderTotal = 0;
  	      int rc = 0;
		   
		   try {
		 	   // Get Customer object from client's page
		 	   cust = this.getCustomerObjectFromPage();
		 	   cwn = this.custApi.findCustomerWithName(cust.getId());
		 	   salesOrder = this.getSalesOrderObjectFromPage();
		 	   
		 	   xact = this.getXactObjectFromPage();
		 	   
		 	   if (salesOrder == null) {
		 	       try {
		 	          salesOrder = SalesFactory.createSalesOrder();    
		 	       }
		 	       catch (SystemException e) {
		 	           System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] New sales order object  could not be created.");
		 	       }
		 	   }

		 	   // Get Invoice object
		 	   si = this.getSalesOrderInvoice(salesOrder.getId());
		 	   
		 	   // Get Current sales order status
		 	   sos = this.getCurrentSalesOrderStatus(salesOrder.getId());
		 	   
		 	   // Determine if we are working with an existing or new sales order.
		      if (salesOrder.getId() > 0) {
		           orderTotal = this.getSalesOrderItems(cust, salesOrder);
		 	   }
		 	   else {
		 	       orderTotal = this.createSalesOrder(cust, salesOrder);
		 	       System.out.println("New Order Total: " + orderTotal);
		 	   }    
 	         salesOrder.setOrderTotal(orderTotal);
 	         
		 	   // Determine if sales order editable.
		 	   switch (salesOrder.getInvoiced()) {
			 	   case 1:
			 	       rc = 2;  // View Only
			 	       break;
			 	   case 0:
			 	       switch (sos.getId()) {
				 	       case SalesConst.STATUS_CODE_CLOSED:
				 	       case SalesConst.STATUS_CODE_CANCELLED:
                           case SalesConst.STATUS_CODE_REFUNDED:
				 	           rc = 2;
				 	           break;
				 	       default:
				 	          rc = 1; // Editable
				 	          break;
  			 	       } // end switch
		 	   } // end switch
		      
		       // Prepare to send results to client
		       this.request.setAttribute("customer", cwn);
		       this.request.setAttribute("salesorder", salesOrder);
		       this.request.setAttribute("salesorderstatus", sos);
		       this.request.setAttribute("invoice", si);
		       this.request.setAttribute("xact", xact);
		       return rc;
		   }
		   catch (SalesOrderException e) {
		       System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] SalesOrderException occurred. " + e.getMessage()); 
		       throw new XactException(e);
		   }
		   catch (GLAcctException e) {
		       System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] SalesOrderException occurred. " + e.getMessage()); 
		       throw new XactException(e);
		   }
	  }
	  

	  /**
	   * Retrieves the sales order detail based on key _cust and _so and transmits the results to the client.  
	   * The detailed results will be represented as an existing sales order. 
	   * 
	   * @param _csow - {@link CombinedSalesOrderWorker}
	   * @throws XactException
	   */
	  public void refreshClient(CombinedSalesOrderWorker _csow) throws XactException {
	      CustomerWithName cwn = null;
		  SalesOrderStatus sos = null;
		  SalesInvoice si = null;
		   
		   try { 
		 	   // Get Customer object from client's page
		 	   cwn = this.custApi.findCustomerWithName(_csow.getCustomer().getId());
		 	   
		 	   // Get Transaction Data if available
		 	   //xact = _csow.getXact();
		 	   
		 	   // Get Invoice object
		 	   si = this.getSalesOrderInvoice(_csow.getSalesOrder().getId());
		 	   
		 	   // Get Current sales order status
		 	   sos = this.getCurrentSalesOrderStatus(_csow.getSalesOrder().getId());
		 	   
		 	   // Get sales order details
               this.getSalesOrderItems(_csow.getCustomer(), _csow.getSalesOrder());
		 	   
		       // Prepare to send results to client
		       this.request.setAttribute("customer", cwn);
		       this.request.setAttribute("salesorder", _csow.getSalesOrder());
		       this.request.setAttribute("salesorderstatus", sos);
		       this.request.setAttribute("invoice", si);
		       this.request.setAttribute("xact", _csow.getXact());
		   }
		   catch (GLAcctException e) {
		       System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] GLAcctException occurred. " + e.getMessage()); 
		       throw new XactException(e);
		   }
		   catch (SalesOrderException e) {
		       System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] SalesOrderException occurred. " + e.getMessage()); 
		       throw new XactException(e);
		   }
	  }

	  /**
	   * Retrieves the sales order invoice object using _slaesOrder.
	   * 
	   * @param _salesOrder
	   * @return {@link SalesInvoice}
	   */
	  private SalesInvoice getSalesOrderInvoice(int _salesOrder) throws SalesOrderException {
		   SalesInvoice si = this.salesApi.findSalesOrderInvoice(_salesOrder);
		   if (si == null) {
		       try {
		          si = SalesFactory.createSalesInvoice();    
		       }
		       catch (SystemException e) {
			       System.out.println("[XactSalesOnAccountAction.getSalesOrderInvoice] SystemException occurred. " + e.getMessage()); 
			       throw new SalesOrderException(e);
		       }
		   }
		   return si;
	  }

	  /**
	   * Retrieves the current sales order status using _salesOrderId.
	   * 
	   * @param _salesOrderId
	   * @return
	   */
	  private SalesOrderStatus getCurrentSalesOrderStatus(int _salesOrderId) throws SalesOrderException {
	      SalesOrderStatusHist sosh = null;
		 	SalesOrderStatus sos = null;

		   try {
			 	 sosh = this.salesApi.findCurrentSalesOrderStatusHist(_salesOrderId);
			 	 if (sosh == null) {
			 	     sos = new SalesOrderStatus();
			 	 }     
			 	 else {
			 	     sos = this.salesApi.findSalesOrderStatus(sosh.getSalesOrderStatusId());
			 	     if (sos == null) {
			 	        sos = new SalesOrderStatus();
			 	     }
			 	 }
			 	 return sos;
		   }
		  catch (SystemException e) {
		       System.out.println("[XactSalesOnAccountAction.getCurrentSalesOrderStatus] SystemException occurred. " + e.getMessage()); 
		       throw new SalesOrderException(e);
	     }   
	  }
	  
	  
	  /**
	   * This method is responsible for obtaining all items of a sales order using _cust and _so and grouping each item 
	   * to either a list of service items or merchandise items.   It also searches the HttpServletRequest object for any 
	   * new items that may have been selected and appends those items to the list of service or merchandise items 
	   * in the event this request originated from the Item Selection Page.
	   * <p>
	   * The service and merchandise item lists are transmitted to the client via the HttpServletRequest object as ArrayLists 
	   * of {@link VwSalesorderItemsBySalesorder} and can be identified by the client as "service" and "merchandise", respectively.  
	   *   
	   * @param _cust - Customer object.
	   * @param _so - Sales Order object
	   * @return double - Summed total of all sales order item amounts.
	   * @throws XactException
	   */
	  private double getSalesOrderItems(Customer _cust, SalesOrder _so) throws XactException {
	      System.out.println("Entering getSalesOrderDetails");
	      String msg = null;
	      SalesOrderItems soi[] = null;
	      ArrayList items = null;
	      double salesOrderTotal = 0;
	      VwSalesorderItemsBySalesorder csoi[] = null;
	      CombinedSalesOrderWorker cso = new CombinedSalesOrderWorker();
	      
	      try {
	    	  // Get existing sales order items
	    	  items = this.salesApi.findSalesOrderItems(_so.getId());  
	    	  soi = new SalesOrderItems[items.size()];
	    	  soi = (SalesOrderItems[]) items.toArray(soi);

	    	  // Convert current sales order items and separate them based on item type.	  
	    	  csoi = this.convertSalesOrderItems(_cust.getId(), soi);
	    	  cso.packageSalesOrderItemsByTypes(csoi);
	      }
	      catch (SalesOrderException e) {
	    	  msg = "[XactSalesOnAccountAction.getSalesOrderDetails] SalesOrderException occurred. " + e.getMessage();
	    	  System.out.println(msg);
	    	  throw new XactException(e);
	      }
	      
	      // Check if there are new items to add to the existing list of sales order items.
	      String selectedItems[] = this.getNewSelectedItems();
	      csoi = this.buildNewSalesOrderItems(_so, selectedItems);
	      
	      // Append new items, if available,  to the existing item list
	      cso.packageSalesOrderItemsByTypes(csoi);
	      
	      // Prepare to send results to client
	      this.request.setAttribute("services", cso.getSrvcItems());
	      this.request.setAttribute("merchandise", cso.getMerchItems());
	      
	      // Call stored function to obtain sales order amount and return to the client.
	      try {
	          salesOrderTotal = this.salesApi.getSalesOrderTotal(_so.getId());
	          _so.setOrderTotal(salesOrderTotal);
	      }
	      catch (SalesOrderException e) {
	          System.out.println(e.getMessage());
	          throw new XactException(e);
	      }
	      return salesOrderTotal;
	  }

	  /**
	   * This method is responsible for obtaining all items from the master item table and grouping each item 
	   * into either an ArrayList of service items or an ArrayList of merchandise items.   The items contained in each ArrayList 
	   * will be of type {@link VwSalesorderItemsBySalesorder} and will be transmitted to the client via the HttpServletRequest object.
	   * The client will identify the service item list as "service" and the merchandise item list as "merchandise".
	   * <p> 
	   * This method is generally called when the client's request originates from the Item Selection page.
	   * 
	   * @param _cust - Customer
	   * @param _so - Sales Order
	   * @return double - Generally is zero since this is a new sales order.
	   * @throws SalesOrderException
	   */
	  private double createSalesOrder(Customer _cust, SalesOrder _so) throws SalesOrderException {
		  System.out.println("Building new Sales Order");
	      _so = this.buildNewSalesOrder(_cust);
	      String selectedItems[] = this.getNewSelectedItems();
	      VwSalesorderItemsBySalesorder soi[] = this.buildNewSalesOrderItems(_so, selectedItems);
	      System.out.println("Total number of items obtained from Item Selection Page: " + soi.length);
	      
	      CombinedSalesOrderWorker cso = new CombinedSalesOrderWorker();
	      double total = cso.packageSalesOrderItemsByTypes(soi);
	      
          // Prepare to send results to client
	      this.request.setAttribute("services", cso.getSrvcItems());
	      this.request.setAttribute("merchandise", cso.getMerchItems());
	      return total;
	  }
	  
	  /**
	   * Queries the client's request (Item Selection Page) and obtains all service and merchandise items selected by the user.
	   * 
	   * @return String[] A list of Item Master id's as type String.
	   */
	  protected String[] getNewSelectedItems() {
	      ArrayList list = new ArrayList();
	      String temp[] = null;
	      
	      // Get selected service items
	      temp = this.request.getParameterValues(ItemConst.SEL_NEW_ITEM_SRVC);
	      if (temp != null) {
	 	      System.out.println("Total Service items selected: " + temp.length);
		      for (int ndx = 0; ndx < temp.length; ndx++) {
		          list.add(temp[ndx]);
		          System.out.println(" Selected Service item: " + temp[ndx]);
		      }	          
	      }
	      
	      // Get selected merchandise items.
	      temp = this.request.getParameterValues(ItemConst.SEL_NEW_ITEM_MERCH);
	      if (temp != null) {
	 	      System.out.println("Total Merchandise items selected: " + temp.length);
		      for (int ndx = 0; ndx < temp.length; ndx++) {
		          list.add(temp[ndx]);
		          System.out.println("Selected Merchandise item: " + temp[ndx]);
		      }	          
	      }
	      String items[] = new String[list.size()];
	      items = (String[]) list.toArray(items);
	      System.out.println("Total Items selected: " + items.length);
	      return items;
	  }
	  
	  /**
	   * Creates a new sales order object using _cust.
	   * 
	   * @param _cust - Customer object.
	   * @return {@link SalesOrder}
	   * @throws SalesOrderException
	   */
	  protected SalesOrder buildNewSalesOrder(Customer _cust) throws SalesOrderException {
	      String msg = null;
	      
	      try {
	          SalesOrder so = SalesFactory.createSalesOrder(_cust.getId());   
	          return so;
	      }
	      catch (SystemException e) {
	          msg = "Problem creating sales order object for customer.";
	          System.out.println("[XactSalesOnAccountAction.buildNewSalesOrder] " + msg);
	          throw new SalesOrderException(msg);
	      }
	  }
	  
	  /**
	   * Builds a list of inventory  items for a new sales order using _so and _itemNo.   For each item id contained in _itemNo, 
	   * corresponding ItemMaster data is obtained.
	   *  
	   * @param _so - Sales Order object.
	   * @param _itemNo - Item Master Id's as an Array of Strings.
	   * @return An array of items as {@link VwSalesorderItemsBySalesorder} objects
	   */
	  protected VwSalesorderItemsBySalesorder [] buildNewSalesOrderItems(SalesOrder _so, String _itemNo[]) {
	      VwSalesorderItemsBySalesorder soi[] = null;
	      ItemMaster im = null;
	      int tot = 0;
	      int itemId = 0;
	      
	      if (_itemNo == null) {
	          return null;
	      }
	      
	      tot = _itemNo.length;
	      soi = new VwSalesorderItemsBySalesorder[tot];
	      for (int ndx = 0; ndx < tot; ndx++) {
	          try {
	              itemId = Integer.parseInt(_itemNo[ndx]);
	          }
	          catch (NumberFormatException e) {
	              System.out.println("[XactSalesOnAccountAction.buildNewSalesOrderItems] Item Number " + _itemNo[ndx] + " could not be converted to an integer value.");
	              itemId = 0;
	          }
	          
	          // Create sales order item record using the item master.
	          try {
	              im = this.itemApi.findItemById(itemId);
	              soi[ndx] = new VwSalesorderItemsBySalesorder();
	              soi[ndx].setCustomerId(_so.getCustomerId());
	              soi[ndx].setSalesOrderItemId(0);
	              soi[ndx].setSalesOrderId(0);
	              soi[ndx].setItemMasterId(im.getId());
	              soi[ndx].setVendorId(im.getVendorId());
	              soi[ndx].setVendorItemNo(im.getVendorItemNo());
	              soi[ndx].setItemTypeId(im.getItemTypeId());
	              soi[ndx].setItemSerialNo(im.getItemSerialNo());
	              soi[ndx].setItemName(im.getDescription());
	              soi[ndx].setQtyOnHand(im.getQtyOnHand());
	              soi[ndx].setUnitCost(im.getUnitCost());
	              soi[ndx].setRetailPrice(im.getUnitCost() * im.getMarkup());

	              // Default item quantity to 1
	              soi[ndx].setOrderQty(1);
	          }
	          catch (ItemMasterException e) {
	              System.out.println("[XactSalesOnAccountAction.buildNewSalesOrderItems] Problem retrieving Item Master object from the database using Item Number " + _itemNo[ndx]);
	          }
	          catch (SystemException e) {
	              System.out.println("[XactSalesOnAccountAction.buildNewSalesOrderItems] Problem creating VwSalesorderItemsBySalesorder object");
	          }
	      } // end for
	      
	      return soi;
	  }
	
	  /**
	   * Translates each SalesOrderItems object in _soi as a {@link VwSalesorderItemsBySalesorder} object.
	   * 
	   * @param _custId - Customer Id
	   * @param _soi - An Array of {@link SalesOrderItems}
	   * @return An array of VwSalesorderItemsBySalesorder objects.
	   */
	  protected VwSalesorderItemsBySalesorder [] convertSalesOrderItems(int _custId, SalesOrderItems _soi[]) {
	      VwSalesorderItemsBySalesorder soi[] = null;
	      ItemMaster im = null;
	      int tot = 0;
          double unitCost = 0;
	      
	      if (_soi == null) {
	          return null;
	      }
	      
	      tot = _soi.length;
	      soi = new VwSalesorderItemsBySalesorder[tot];
	      for (int ndx = 0; ndx < tot; ndx++) {
	          // Create VwSalesorderItemsBySalesorder object using item master data and SalesOrderItems index.
	          try {
	              im = this.itemApi.findItemById(_soi[ndx].getItemMasterId());
	              soi[ndx] = new VwSalesorderItemsBySalesorder();
	              soi[ndx].setCustomerId(_custId);
	              soi[ndx].setSalesOrderItemId(_soi[ndx].getId());
	              soi[ndx].setSalesOrderId(_soi[ndx].getSalesOrderId());
	              soi[ndx].setItemMasterId(im.getId());
	              soi[ndx].setVendorId(im.getVendorId());
	              soi[ndx].setVendorItemNo(im.getVendorItemNo());
	              soi[ndx].setItemTypeId(im.getItemTypeId());
	              soi[ndx].setItemSerialNo(im.getItemSerialNo());
                  if (_soi[ndx].getItemNameOverride() != null && !_soi[ndx].getItemNameOverride().equals("")) {
                      soi[ndx].setItemName(_soi[ndx].getItemNameOverride());
                  }
                  else {
                      soi[ndx].setItemName(im.getDescription());    
                  }
	              soi[ndx].setQtyOnHand(im.getQtyOnHand());
	              soi[ndx].setUnitCost(_soi[ndx].getInitUnitCost());
	              soi[ndx].setRetailPrice(_soi[ndx].getInitUnitCost() * im.getMarkup());
	              soi[ndx].setOrderQty(_soi[ndx].getOrderQty());
	          }
	          catch (ItemMasterException e) {
	              System.out.println("[XactSalesOnAccountAction.convertSalesOrderItems] Problem retrieving Item Master object from the database using Item Number " + _soi[ndx].getItemMasterId());
	          }
	          catch (SystemException e) {
	              System.out.println("[XactSalesOnAccountAction.convertSalesOrderItems] Problem creating VwSalesorderItemsBySalesorder object");
	          }
	      } // end for
	      
	      return soi;
	  }
	  
	  /**
	   * Adds one or more items to a sales order.   Before an item is added to the sales order, the sales order is saved to the database
	   * to recognize any changes that may have occurred by the client.
	   * 
	   * @throws SalesOrderException
	   */
	  public void addSalesOrderItem() throws SalesOrderException {
	      String msg = null;
	      int rc = 0;
	      CombinedSalesOrderWorker cso = this.getSalesOrderPageDetails();
	      
	      try {
	 	      // Create/Update sales order
	 	      rc = this.saveJounalEntryDetails(cso);
	 	      System.out.println("[XactSalesOnAccountAction.addSalesOrderItem] Sales Order Id after Add Item Action:  " + cso.getSalesOrder().getId());
	 	      
	 	      // Call method to display new inventory selections
	         this.getAvailJournalEntryItems(cso.getCustomer().getId(), cso.getSalesOrder().getId());
	         this.transObj.commitTrans();
	      }
	      catch (XactException e) {
	          msg = "[XactSalesOnAccountAction.addSalesOrderItem] Problem displaying sales order item selection page for add item action";
	          System.out.println(msg);
		    	 try {
		    	     this.transObj.rollbackTrans();    
		    	 }
		    	 catch (DatabaseException ee) {
		    	 }
	          throw new SalesOrderException(msg);
	      }
	      catch (DatabaseException e) {
	          try {
	              this.transObj.rollbackTrans();    
		       }
		       catch (DatabaseException ee) {
	    	    }
	      }
	      
	  }
	  
	  
	  /**
	   * Saves the sales order to the database.
	   *  
	   * @throws SalesOrderException
	   */
	  public int updateSalesOrder() throws XactException {
	      int rc = 0;
	      CombinedSalesOrderWorker cso = null;
	      
	      try {
	          cso = this.getSalesOrderPageDetails();
	 	       // Create/Update sales order
	 	       rc = this.saveJounalEntryDetails(cso);
	          this.transObj.commitTrans();
	          return rc;
	      }
	      catch (DatabaseException e) {
	    	  try {
	    		  this.transObj.rollbackTrans();    
		     }
		     catch (DatabaseException ee) {
	    	  }
		     throw new XactException(e.getMessage()); 
	      }
	      catch (SalesOrderException e) {
	          try {
	              this.transObj.rollbackTrans();
			    }
			    catch (DatabaseException ee) {
		    	 }
			    throw new XactException(e.getMessage());
	      }
	      finally {
	          // Prepare to refresh client's page.
             this.refreshClient(cso);    
	      }
	  }
	  
	  
	  /**
	   * This method cancels a sales order.    The stept to cancel an invoiced sales order are  1) Get clien data, 
	   *  2) Reverse the transaction, 3) Reverse the customer activity entry, 4) Assign sales order items back to 
	   * inventroy, and 5) Change the order satus to cancel.   Conversely, quoted orders only requires the sales 
	   * order status to be changed to "Cancelled".
	   *  
	   * @throws SalesOrderException
	   */
	  public void cancelOrder() throws SalesOrderException {
	      CombinedSalesOrderWorker cso = null;
	      CustomerWithName cwn = null;
	      SalesInvoice si = null;
		  SalesOrderStatus sos = null;
	      Xact xact = null;
	      
	      System.out.println("Responding to Cancel Order request");
	      // Get Client Data
	      try {
	          cso = this.getSalesOrderPageDetails();
	          
	          // Get Current sales order status
	          sos = this.getCurrentSalesOrderStatus(cso.getSalesOrder().getId());
	          
              //A sales order must be in Invoice status to be eligible for cancellation
              if (sos.getId() != SalesConst.STATUS_CODE_INVOICED) {
            	  msg = "Cannot cancel a Sales Order that is not in Invoiced status";
			      System.out.println("[XactSalesOnAccountAction.cancelOrder] " + msg);
			      throw new SalesOrderException(msg);
              }

	          // Get Transaction Data if available
	          xact = this.getXactObjectFromPage();
	          System.out.println("Cancelled Xact Id: " + xact.getId());
	          
	          // Cancel sales order
	          this.salesApi.cancelSalesOrder(cso.getSalesOrder().getId());
	          this.transObj.commitTrans();
	          System.out.println("Sales Order cancelled successfully");
	          
	          // 	Get Customer object from client's page
	          cwn = this.custApi.findCustomerWithName(cso.getCustomer().getId());
	 	   
	          // Get Invoice object
	          si = this.getSalesOrderInvoice(cso.getSalesOrder().getId());
	 	   
	          // Get Current sales order status after change
	          sos = this.getCurrentSalesOrderStatus(cso.getSalesOrder().getId());
	 	   
	          // Get Sales Order details
	          this.getSalesOrderItems(cso.getCustomer(), cso.getSalesOrder());
	 	   
	          // Prepare to send results to client
	          this.request.setAttribute("customer", cwn);
	          this.request.setAttribute("salesorder", cso.getSalesOrder());
	          this.request.setAttribute("salesorderstatus", sos);
	          this.request.setAttribute("invoice", si);
	          this.request.setAttribute("xact", xact);
	      }
		   catch (GLAcctException e) {
 		       System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] GLAcctException occurred. " + e.getMessage()); 
                try {
                   this.transObj.rollbackTrans();
                 }
                 catch (DatabaseException ee) {
                  }
                 throw new SalesOrderException(e);
 		   }
	      catch (SalesOrderException e) {
	          try {
	        	  System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] SalesOrderException occurred. " + e.getMessage());
	              this.transObj.rollbackTrans();
			    }
			    catch (DatabaseException ee) {
		    	 }
			    throw e;
	      }
	      catch (XactException e) {
	          try {
	              this.transObj.rollbackTrans();
			    }
			    catch (DatabaseException ee) {
		    	 }
			    throw new SalesOrderException(e);
	      }
	      catch (DatabaseException e) {
	          try {
	              this.transObj.rollbackTrans();
			    }
			    catch (DatabaseException ee) {
		    	 }
			    throw new SalesOrderException(e);
	      }
	  }
	  
      
      public void refundOrder() throws SalesOrderException {
          CombinedSalesOrderWorker cso = null;
          CustomerWithName cwn = null;
          SalesInvoice si = null;
          SalesOrderStatus sos = null;
          Xact xact = null;
          String msg = null;
          
          System.out.println("********* Responding to Returns, Refunds, and Allowances Order request ****************");
          // Get Client Data
          try {
              cso = this.getSalesOrderPageDetails();
              
              // Get Current sales order status
              sos = this.getCurrentSalesOrderStatus(cso.getSalesOrder().getId());

              //Do not try to refund a sales order that is not in either invoiced or closed statuses.
              if (sos.getId() != SalesConst.STATUS_CODE_INVOICED && sos.getId() != SalesConst.STATUS_CODE_CLOSED) {
            	  msg = "Sales Orders can only be refunded when in Invoiced or Closed statuses";
			      System.out.println("[XactSalesOnAccountAction.refundOrder] " + msg);
			      throw new SalesOrderException(msg);
              }

              // Get Transaction Data if available
              xact = this.getXactObjectFromPage();
              System.out.println("Refunded Xact Id: " + xact.getId());
              
              // Refund sales order
              this.salesApi.refundSalesOrder(cso.getSalesOrder().getId());
              this.transObj.commitTrans();
              System.out.println("*******************  Sales Order refunded successfully ********************");
              
              //    Get Customer object from client's page
              cwn = this.custApi.findCustomerWithName(cso.getCustomer().getId());
           
              // Get Invoice object
              si = this.getSalesOrderInvoice(cso.getSalesOrder().getId());
           
              // Get Current sales order status after change
              sos = this.getCurrentSalesOrderStatus(cso.getSalesOrder().getId());
           
              // Get Sales Order details
              this.getSalesOrderItems(cso.getCustomer(), cso.getSalesOrder());
           
              // Prepare to send results to client
              this.request.setAttribute("customer", cwn);
              this.request.setAttribute("salesorder", cso.getSalesOrder());
              this.request.setAttribute("salesorderstatus", sos);
              this.request.setAttribute("invoice", si);
              this.request.setAttribute("xact", xact);
          }
           catch (GLAcctException e) {
               System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] GLAcctException occurred. " + e.getMessage());
               try {
                   this.transObj.rollbackTrans();
                 }
                 catch (DatabaseException ee) {
                  }
                 throw new SalesOrderException(e);
           }
          catch (SalesOrderException e) {
              try {
                  System.out.println("[XactSalesOnAccountAction.getJournalEntryDetails] SalesOrderException occurred. " + e.getMessage());
                  this.transObj.rollbackTrans();
                }
                catch (DatabaseException ee) {
                 }
                throw e;
          }
          catch (XactException e) {
              try {
                  this.transObj.rollbackTrans();
                }
                catch (DatabaseException ee) {
                 }
                throw new SalesOrderException(e);
          }
          catch (DatabaseException e) {
              try {
                  this.transObj.rollbackTrans();
                }
                catch (DatabaseException ee) {
                 }
                throw new SalesOrderException(e);
          }
      }
      
      
	  /**
	   * Locates Sales Order data by querying the clinet's request object that contains the JSP page that was subimtted.   
	   * The data can be chosen from a list of orders or from a single sales order.
	   *  
	   * @return {@link CombinedSalesOrderWorker}
	   * @throws SalesOrderException
	   */
	  protected CombinedSalesOrderWorker getSalesOrderPageDetails() throws SalesOrderException {
	      Customer cust = null;
	      SalesOrder so = null;
	      ArrayList items = null;
         String msg = null; 
	      
	      try {
	          // Get Customer object
	          cust = this.getCustomerObjectFromPage();
	          if (cust == null) {
	              cust = AcctManagerFactory.createCustomer(); 
	          }
	          
	          // Get Sales Order object
	          so = this.getSalesOrderObjectFromPage();
	 	       if (so == null) {
	 	           so = SalesFactory.createSalesOrder();
	 	       }
	      }
	      catch (GLAcctException e) {
	          msg = "[XactSalesOnAccountAction.getSalesOrderPageDetails] Problem obtaining Customer object";
	          System.out.println(msg);
	          throw new SalesOrderException(msg);
	      }
	      catch (SystemException e) {
	          msg = "[XactSalesOnAccountAction.getSalesOrderPageDetails] Problem obtaining Customer or Sales Order object";
	          System.out.println(msg);
	          throw new SalesOrderException(msg);
	      }
	      
	      // Get sales order items and group into Service and Merchandise lists.
	      items = new ArrayList();
	      this.getClientSalesOrderItems(so.getId(), XactSalesOnAccountAction.CLIENT_SRVC_ITEM_CODE, items);
	      this.getClientSalesOrderItems(so.getId(), XactSalesOnAccountAction.CLIENT_MERCH_ITEM_CODE, items);
      
	      
	      CombinedSalesOrderWorker cso = new CombinedSalesOrderWorker();
	      cso.setCustomer(cust);
	      cso.setSalesOrder(so);
	      cso.setItems(items);

	      return cso;
	  }
	  
	  /**
	   * Retrieves one or more sales order items from the request of the client and 
	   * packages each item into _items as an {@link SalesOrderItems} objects using
	   * _orderId and _itemTypeCode.    _items will instantiated in the event it is passed as 
	   * a null value.   Any items selected from the client UI will be excluded from the sales order
	   * at save time.
	   * <p>
	   *  In order for this method to work properly, the client's JSP must follow these rules:
	   * 1.  One HTML input control must be declared with a non-unique name in the following format "rowId" + _itemTypeCode.
	   *       The value of this column will be the row number.   The value of this column is used to identify all other columns
	   *       on the same row.
	   * 2.   Other HTML input cotrols must be decalred with unique names in the following format:
	   *             _itemTypeCode + "SlsOrdItemId" + <row number>.
	   *    
	   * @param _orderId - Sales Order Id
	   * @param _itemTypeCode - Portion of the HTML input control's name that represents the  Item Type.
	   * @param _items - ArrayList of {@link SalesOrderItems} that represents the list of items gathered as a result of this process.
	   */
	  private void getClientSalesOrderItems(int _orderId, String _itemTypeCode, ArrayList _items) {
	      SalesOrderItems soi = null;
	      String rowIndexes[] = null;
	      String msg = null;
	      String value = null;
	      String property = null;
	      int salesOrderItemId = 0;
	      int itemId = 0;
	      int orderQty = 0;
	      int row = 0;
	      
	      System.out.println("Retrieving sales order items from client UI");
	      property = XactSalesOnAccountAction.CLIENT_ITEM_ROWID +  _itemTypeCode;
	      rowIndexes = this.request.getParameterValues(property);
	      if (_items == null) {
	          _items = new ArrayList();    
	      }
	      
	      //  Return to caller if there are no items to process.
	      if (rowIndexes == null) {
	          return;
	      }
	      
	      for (int ndx = 0; ndx < rowIndexes.length; ndx++) {
	          try {
	              // Get row number
	              msg = "[XactSalesOnAccountAction.getClientSalesOrderItemsByType] Problem converting row value to integer.";
	              row = Integer.parseInt(rowIndexes[ndx]);
	              
	              // Was item selected by the user?
	              property = XactSalesOnAccountAction.CLIENT_ITEM_SELECTOR + _itemTypeCode + row;
	              value = this.request.getParameter(property);
	              if (value != null) {
	                  // Do not include this item if it is selected to be removed from the order
	                  property = _itemTypeCode + "ItemId" + row;
	 	               value = this.request.getParameter(property);
	 	               System.out.println("The following item was removed from sales order: " + value);
	                  continue;
	              }
	              
	              // Get sales order item id
	              msg = "[XactSalesOnAccountAction.getClientSalesOrderItemsByType] Problem converting sales order item id value to integer.";
	              property = _itemTypeCode + "SlsOrdItemId" + row;
	              value = this.request.getParameter(property);
	              salesOrderItemId = Integer.parseInt(value);
	              
	              // Get item master id
	              msg = "[XactSalesOnAccountAction.getClientSalesOrderItemsByType] Problem converting Item Master id value to integer.";
	              property = _itemTypeCode + "ItemId" + row;
	              value = this.request.getParameter(property);
	              itemId = Integer.parseInt(value);
	              
	              // Get Quantity order for item
	              msg = "[XactSalesOnAccountAction.getClientSalesOrderItemsByType] Problem converting Item Quantity ordered value to integer.";
	              property = _itemTypeCode + "OrderQty" + row;
	              value = this.request.getParameter(property);
	              orderQty = new Double(value).intValue();
	              System.out.println("Quantity Ordered for property, " + property + ", is : " + orderQty);
	          }
	          catch (NumberFormatException e) {
	              System.out.println(msg);
	              continue;
	          }
	          
	          // Get general item details from the database and store in array list.
	          try {
	              soi = SalesFactory.createSalesOrderItem(_orderId, itemId, orderQty);
	              soi.setId(salesOrderItemId);
	              _items.add(soi);
	          }
	          catch (SystemException e) {
	              System.out.println("[XactSalesOnAccountAction.getClientSalesOrderItemsByType] SystemException - " + e.getMessage());
	          }
	      } // end for
	  }
	  
	  
	  /**
	   * Locates customer data by querying the clinet's request object that contains the JSP page that was subimtted.   
	   * The data can be chosen from a list of customers or from a single customer record.   If customer cannot be 
	   * created using request data, then a new Customer object is created.
	   * 
	   * @return {@link Customer}
	   * @throws GLAcctException
	   */
	  /*
	  private Customer getCustomerObjectFromPage() throws GLAcctException {
	      Customer cust = null;
		   String temp = null;
		   String subMsg = null;
		   int custId = 0;
		   int row = 0;
		   boolean isOrderListPage = true;
	      
	      
	      // Determine if we are coming from a page that presents data as a list of orders or as single order.
		   try {
		       // Get selected row number from client page.  If this row number exist, then we 
		       // are coming from page that contains a list of orders.
			    row = this.getSelectedRow("selCbx");
			    temp = this.request.getParameter("CustomerId" + row);
			    custId = Integer.parseInt(temp);
		   }
		   catch (NumberFormatException e) {
		       subMsg = "Customer number could not be obtained from list of orders";
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
		       throw new GLAcctException(subMsg);
		   }
		   catch (SystemException e) {
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] Originated from single record form.");
		       isOrderListPage = false;
		   }
	
		   if (!isOrderListPage) {
		       try {
		           temp = this.request.getParameter("CustomerId");
		 		     custId = Integer.parseInt(temp);    
		       }
		 	    catch (NumberFormatException e) {
		 	        subMsg = "Customer number could not be obtained from single record form.";
			        System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
			        throw new GLAcctException(subMsg);
			    }  
		   }
		   
		   // Get Customer object
		   try {
		       cust = this.custApi.findCustomerById(custId);
		       if (cust == null) {
		           cust = this.custApi.createCustomer();
		       }
		   }
		   catch (GLAcctException e) {
		       subMsg = "Problem retrieving Customer information from the database using Customer Id: " + custId;
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
		       throw new GLAcctException(subMsg);
		 	}
		   catch (SystemException e) {
		       subMsg = "Problem creating new Customer object";
		       System.out.println("[XactSalesOnAccountAction.getCustomerObjectFromPage] " + subMsg + ".");
		       throw new GLAcctException(subMsg);
		   }
		   return cust;
	  }
	*/
	
	  /**
	   * Locates sales order data by querying the clinet's request object that contains the JSP page that was subimtted.   
	   * The data can be chosen from a list of sales orders or from a single sales order record.   If the sales order cannot be 
	   * created using request data, then a new Sales Order object is created.
	   * 
	   * @return {@link SalesOrder}
	   * @throws SalesOrderException
	   */
	  private SalesOrder getSalesOrderObjectFromPage() throws SalesOrderException {
	      SalesOrder so = null;
		   String temp = null;
		   String subMsg = null;
		   int orderId = 0;
		   int row = 0;
		   boolean isOrderListPage = true;
	      
	      
	      // Determine if we are coming from a page that presents data as a list of orders or as single order.
		   try {
		       // Get selected row number from client page.  If this row number exist, then we 
		       // are coming from page that contains a list of orders.
			    row = this.getSelectedRow("selCbx");
			    temp = this.request.getParameter("OrderId" + row);
			    subMsg = "Order number could not be obtained from list of orders";
			    orderId = Integer.parseInt(temp);
		   }
		   catch (NumberFormatException e) {
		       System.out.println("[XactSalesOnAccountAction.getSalesOrderObjectFromPage] " + subMsg + ".");
		       throw new SalesOrderException(subMsg);
		   }
		   catch (SystemException e) {
		       System.out.println("[XactSalesOnAccountAction.getSalesOrderObjectFromPage] Originated from single record form.");
		       isOrderListPage = false;
		   }
	
		   if (!isOrderListPage) {
		       try {
		    	   // Get order id for single row
		           temp = this.request.getParameter("OrderId");
		           subMsg = "Order number could not be obtained from single record form.  Must be creating an order.";
		           orderId = Integer.parseInt(temp);    
		       }
		 	   catch (NumberFormatException e) {
			       System.out.println("[XactSalesOnAccountAction.getSalesOrderObjectFromPage] " + subMsg + ".");
			       try {
			           return SalesFactory.createSalesOrder();    
			       }
		           catch (SystemException ee) {
			           subMsg = "Probelm creating new Sales order object";
			           System.out.println("[XactSalesOnAccountAction.getSalesOrderObjectFromPage] " + subMsg);
			           throw new SalesOrderException(subMsg);
			       }
			   }  
		   }
		   
		   // Get Sales Order object
		   try {
		       so = this.salesApi.findSalesOrderById(orderId);
		       if (so == null) {
		    	   so = SalesFactory.createSalesOrder();
		       }
		   }
		   catch (SalesOrderException e) {
			   System.out.println("[XactSalesOnAccountAction.getSalesOrderObjectFromPage] " + e.getMessage());
	           throw e;
		 	}
		   catch (SystemException e) {
			   System.out.println("[XactSalesOnAccountAction.getSalesOrderObjectFromPage] " + e.getMessage());
		       throw new SalesOrderException(e);
		   }
		   return so;
	  }
	  
	  
	  /**
	   * Validates a single sales order record.
	   */
	  public void validateJournalEntryDetails() throws XactException {
	      
	  }
	  
	  /**
	   * Applies sales order changes to the database.   If sales order is selected to be Invoiced, then the transaction
	   * is created and inventory is consumed.
	   * 
	   * @param _csow  {@link CombinedSalesOrderWorker}
	   * @return int -
	   * @throws SalesOrderException
	   */
	  public int saveJounalEntryDetails(CombinedSalesOrderWorker _csow) throws DatabaseException, SalesOrderException {
         String temp = null;
         Xact xact = null;
         int salesOrderId = 0;
         int xactId = 0;
         int rc = 0;
         boolean toBeInvoiced = false;
         boolean isOkToInvoice = false; 
	      
	      try {
	          // Create/Update sales order
	 	       salesOrderId = this.salesApi.maintainSalesOrder(_csow.getSalesOrder(), _csow.getCustomer(), _csow.getItems());
	 	       _csow.getSalesOrder().setId(salesOrderId);
	 	       
	 	       // Refresh the sales order object (_csow.getSalesOrder()) by retrieving latest sales order record
	 	       _csow.setSalesOrder( this.salesApi.findSalesOrderById(_csow.getSalesOrder().getId()) );
	 	       
	 	       //Get Invoiced indicator for single row page of client
	    	    temp = this.request.getParameter("Invoiced");
	    	    toBeInvoiced = (temp != null);  
	    	    
	    	    // Sales Order cannot be invoiced when client is requesting to add inventory items.
	    	    temp = this.request.getParameter("clientAction");
	    	    isOkToInvoice = !( temp.equalsIgnoreCase("additem"));
	    	    rc = 1;  // Not ready to be invoiced
	    	    if (isOkToInvoice) {
	    	       System.out.println("Sales Order is eligible to be invoice");
	    	       if (_csow.getSalesOrder().getInvoiced() == 0 && toBeInvoiced) {
	    	           System.out.println("Ready to invoice Sales Order: " + salesOrderId);
		              // Notify Sales API that database transactions are not to be committed at the ancestor level.
		              this.isOkToCommit = false;
		              
		              // Get transaction data from client
		        	     xact = this.getHttpXactBase();

		        	     // Invoice sales order.
		        	     this.salesApi.invoiceSalesOrder(_csow.getSalesOrder(), xact);
		        	     
		        	     rc = 2;  // Newly invoiced Sales Order
		        	     System.out.println("Sales Order successfully invoiced: " + salesOrderId);
		          }    
	    	    }
	    	    
       	     // Get Transaction object
    		     try {
    		         xact = this.api.findXactById(xactId);
    		         if (xact == null) {
    		             xact = XactFactory.createXact();    
    		         }
    		         _csow.setXact(xact);
    		     }
    		     catch (XactException e) {
    			      System.out.println("[XactSalesOnAccountAction.saveJounalEntryDetails] SalesOrderException " + e.getMessage());
    	            throw new SalesOrderException(e);
    		 	  }

	    	   return rc;
	      }
	      catch (XactException e) {
	    	    System.out.println(e.getMessage());
	    	    throw new SalesOrderException(e);
	      }
	      
	  }
	  
	  
	  /**
	   * Adds transaction journal entries. 
	   * 
	   * @throws XactException 
	   */
	   public void addXact() throws XactException {
		   super.addXact();
	       return;
	   }
	   
	   protected XactManagerApi getCustomXactApi() {
			  XactManagerApi xactApi = SalesFactory.createBaseXactApi(this.dbConn, this.request);
			  return xactApi;
	   }
	  
	  /**
	   * Creates a transaction when sales order is set to be invoiced. 
	   *
	   * @return int - the transaction id. 
	   * @throws XactException
	   * @throws DatabaseException
	   * 
	   */
		public int saveXact() throws XactException, DatabaseException {
			int xactId = 0;
			
			// Apply base transaction.
			xactId =  super.saveXact();
			return xactId;
		}
	
		
	   /**
		 * Action handler to drive the reversal of a transaction journal entry.
		 *  
		 * @return int - the transaction id. 
		 * @throws XactException
		 * @throws DatabaseException
		 * @deprecated
		 */
		  public int reverseXact() throws XactException, DatabaseException {
			  int newXactId = 0;
			  this.isOkToCommit = false;
			  newXactId =  super.reverseXact();
			  return newXactId;
		  }
	 
		  /**
		   * Reverses a customer activity entry.
		   * 
		   * @param _customerId - Customer Id.
		   * @param _oldXactId - Transaction Id that is associated with the transaction that is to be reversed.
		   * @param _newXactId - The transaction Id belonging to the results of the reversal. 
		   * @throws SalesOrderException
		   * @deprecated
		   */
		  private void reverseCustomerActivity(int _customerId, int _oldXactId, int _newXactId) throws SalesOrderException {
			  int newSalesOrderActivity = 0;
			  String criteria = null;
			  String msg = null;
			  ArrayList list = null;
			  CustomerActivity ca = null;
			  double reversedAmount = 0;
			  
			  // Get customer activity entry that will be revesed.
			  this.api.setBaseView("CustomerActivityView");
			  this.api.setBaseClass("com.bean.CustomerActivity");
			  criteria = "customer_id = " + _customerId + " and xact_id = " + _oldXactId;
			  try {
				  msg =  "Error occurred when trying to obtain custom activity entry.  ";
				  list = this.api.findXact(criteria, null);
				  if (list.size() > 0) {
					  ca = (CustomerActivity) list.get(0);
					  
					  // Create entry that will reverse the current customer activity. 
					  if (ca != null) {
						  reversedAmount = ca.getAmount() * -1;
						  msg =  "Error occurred when attempting to reverse a custom activity entry.  ";
						  this.api.createCustomerActivity(_customerId, _newXactId, reversedAmount);
					  }
				  }
			  }
			  catch (XactException e) {
				  System.out.println("[XactSalesOnAccountAction.reverseSalesOrder] " + msg + e.getMessage());
				  throw new SalesOrderException(e);
			  }
		  }
		  
		  /**
		   * Helper inner class that is used to manage Sales Order details.
		   * 
		   * @author Roy Terrell
		   *
		   */
		  class CombinedSalesOrderWorker {
		      private Customer cust;
		      private SalesOrder so;
		      private Xact xact;
		      private ArrayList items;
 			   private ArrayList srvcItems;
		      private ArrayList merchItems;
		      
		      /**
		       * Default constructor
		       */
		      public CombinedSalesOrderWorker() {
		          this.srvcItems = new ArrayList();
		    	    this.merchItems = new ArrayList();
		          return;
		      }

		      /**
		       * Groups each sales order item contained in soi into two separate ArrayLists designated for services items 
		       * and merchandise items.  If soi is null, then the routine is aborted.
		       *   
		       * @param soi
		       */
            public double packageSalesOrderItemsByTypes(VwSalesorderItemsBySalesorder soi[]) {
                double total = 0;
                if (soi == null) {
            		  return 0;
            	  }
            	  
                 // Package items in separate ArrayList collections based on theit item types
	    		     for (int ndx = 0; ndx < soi.length; ndx++) {
	    		         switch (soi[ndx].getItemTypeId()) {
		               case ItemConst.ITEM_TYPE_SRVC:
		                  this.srvcItems.add(soi[ndx]);
		                  break;
		               case ItemConst.ITEM_TYPE_MERCH:
		                  this.merchItems.add(soi[ndx]);
		                  break;
	    		         } // end switch
	    		         
	    		         total += soi[ndx].getRetailPrice();
	    		     } // end for  
	    		    System.out.println("[XactSalesOnAccountAction.CombinedSalesOrderWorker.packageSalesOrderItemsByTypes] Total of all items: " + total);
	    		     return total;
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
		       * Gets service items
		       * @return ArrayList
		       */
		      public ArrayList getSrvcItems() {
		    	  return this.srvcItems;
		      }
		      /**
		       * Gets merchandise items
		       * @return
		       */
		      public ArrayList getMerchItems() {
		    	  return this.merchItems;
		      }
		      /**
		       * Gets all items.
		       * 
		       * @return ArrayList
		       */
		      public ArrayList getItems() {
		          return this.items;
		      }
		      /**
		       * Sets variable that represents all items to value.
		       * 
		       * @param value
		       */
		      public void setItems(ArrayList value) {
		          this.items = value;
		      }
		  } // end inner class pageData
		  
		  
	} // end AbstractXactJournalEntryAction
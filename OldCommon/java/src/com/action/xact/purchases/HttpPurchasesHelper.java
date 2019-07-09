package com.action.xact.purchases;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.action.ActionHandlerException;
import com.action.HttpBasicHelper;

import com.api.GLCreditorApi;
import com.api.PurchasesApi;
import com.api.db.DatabaseException;

import com.bean.Creditor;
import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VwCreditorBusiness;
import com.bean.VwVendorItems;

import com.factory.AcctManagerFactory;
import com.factory.PurchasesFactory;

import com.util.NotFoundException;
import com.util.PurchaseOrderException;
import com.util.SystemException;

/**
 * This class provides functionality for obtaining Vendor Purchases related data
 * from the client's request object.
 * 
 * @author Roy Terrell
 * 
 */
public class HttpPurchasesHelper extends HttpBasicHelper {
	private PurchaseOrder po;
	private Creditor creditor;
	private VwCreditorBusiness vendor;
	private PurchaseOrderStatus pos;
	private PurchaseOrderStatusHist posh;
	private List poItems;
	private List newPoItems;
	private Logger logger;

	/**
	 * Default constructor
	 * 
	 */
	public HttpPurchasesHelper() {
		super();
		logger = Logger.getLogger("HttpPurchasesHelper");
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
	public HttpPurchasesHelper(ServletContext _context,	HttpServletRequest _request) throws SystemException, DatabaseException {
		super(_context, _request);
		logger = Logger.getLogger(HttpPurchasesHelper.class);
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
		this.creditor = null;
	}

	/**
	 * Combines the process of probing the client's HttpServletRequest object for 
	 * creditor/vendor, base purchase order, and newly selected purchase order item(s).  
	 * Automatically the creditor/vendor is associated with the base purchase order.  
	 * After invoking this method, be sure to call the appropriate getter methods to 
	 * obtain the results.
	 * 
	 * @return Transaction object.
	 * @throws XactException
	 */
	public void getHttpCombined() throws SystemException {
		super.getHttpCombined();
		this.creditor = this.getHttpCreditor();
		this.po = this.getHttpPurchaseOrder();
		this.po.setVendorId(this.creditor.getId());
		this.poItems = this.getHttpPurchaseOrderItems();
		this.newPoItems = this.getHttpNewItems(this.creditor.getId(), this.po.getId());
		return;
	}

	/**
	 * Obtains base pruchase order data from the client's request.
	 *  
	 * @return {@link PurchaseOrder}
	 * @throws SystemException
	 */
	public PurchaseOrder getHttpPurchaseOrder() throws SystemException {
		PurchaseOrder obj = PurchasesFactory.createPurchaseOrder();
		String temp = null;
		String subMsg = null;
		boolean listPage = true;
		int poId = 0;
		
		// Determine if we are coming from a page that presents data as a list
		// or as a single record.
		// Get selected row number from client page. If this row number exist,
		// then we
		// are coming from page that contains lists of records.
		subMsg = "Purchase Order id could not be identified from client";
		temp = this.request.getParameter("PurchaseOrderId" + this.selectedRow);
		if (temp == null) {
			listPage = false;
		}
		if (!listPage) {
			// Get order id for single row
			temp = this.request.getParameter("PurchaseOrderId");
		}

		// Validate value
		try {
			poId = Integer.parseInt(temp);
		} 
		catch (NumberFormatException e) {
			logger.log(Level.INFO, subMsg);
			this.po = obj;
			return obj;
		}

		// Get Purchase Order object
		try {
			PurchasesFactory.packageBean(this.request, obj);
			this.po = obj;
			this.po.setId(poId);
			return obj;
		} 
		catch (SystemException e) {
			logger.log(Level.ERROR, e.getMessage());
			throw e;
		}
	}
	
	
	/**
	 * Attempts to obtain basic data pertaining to a creditor/vendor from the
	 * client JSP. This method requires that the client identifies the creditor
	 * as either CreditorId, CreditorIdXXX, VendorId, or VendorIdXXX.
	 * 
	 * @return {@link Creditor} containing the data retrieved from the client or
	 *         a newly initialized Creditor object when the creditor id is not
	 *         found.
	 * @throws SalesOrderException
	 */
	public Creditor getHttpCreditor() throws SystemException {
		Creditor obj = AcctManagerFactory.createCreditor();
		String temp = null;
		String subMsg = null;
		boolean listPage = true;
		int creditorId = 0;

		// Determine if we are coming from a page that presents data as a list
		// or as a single record.
		// Get selected row number from client page. If this row number exist,
		// then we
		// are coming from page that contains lists of records.
		subMsg = "Creditor id could not be identified from client";
		temp = this.request.getParameter("CreditorId" + this.selectedRow);
		if (temp == null) {
			temp = this.request.getParameter("VendorId" + this.selectedRow);
		}
		if (temp == null) {
			listPage = false;
		}
		if (!listPage) {
			// Get order id for single row
			temp = this.request.getParameter("CreditorId");
			if (temp == null) {
				temp = this.request.getParameter("VendorId");
			}
		}

		// Validate value
		try {
			creditorId = Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			logger.log(Level.INFO, subMsg);
			this.creditor = obj;
			return obj;
		}

		// Get Creditor/Vendor object
		try {
			AcctManagerFactory.packageBean(this.request, obj);
			this.creditor = obj;
			this.creditor.setId(creditorId);
			return obj;
		} catch (SystemException e) {
			logger.log(Level.ERROR, e.getMessage());
			throw e;
		}
	}

	
	
	 /**
	   * Retrieves one or more selected items from the client's request with the 
	   * intent adding them to the taget purchase order.   All items are appended 
	   * to the existing list of purchase order items without performing an item 
	   * refresh.   This method is generally invoked when the user has completed 
	   * select one or more vendor inventory items to be linked to a purchase order.
	   *
	   * @return An ArrayList of {@link PurchaseOrderItems}
	   * @throws SystemException
	   */
	  public ArrayList getHttpNewItems(int vendorId, int poId) throws SystemException {
	      String itemIdProp = "ItemMasterId";
	      int itemId = 0;
	      int row = 0;
	      String temp = null;
	      String httpItemId[];
	      PurchaseOrderItems poi = null;
	      VwVendorItems vi = null;
	      ArrayList items = new ArrayList();
	      PurchasesApi api = null;
	     
	      // Get selected items
	      httpItemId = this.request.getParameterValues("selCbx");
		  if (httpItemId == null) {
			  return items;
		  }
		  
		  // Setup Purchases API and gather vendor and base purchase order data from client.
		  try {
			  api = PurchasesFactory.createApi(this.dbConn, this.request);
		  }
		  catch (Exception e) {
			// Do Nothing  
		  }

		  for (int ndx = 0; ndx < httpItemId.length; ndx++) {
			  try {
				  row = Integer.parseInt(httpItemId[ndx]);
                  try {
                      temp = this.getPropertyValue(itemIdProp + row);    
                  }
                  catch (NotFoundException e) {
                      // It is normal to get this error if we are not processing this type of data.
                      this.msg = "One of the selected items is not found to be associated with the target vendor";
                      logger.log(Level.ERROR, this.msg);
                      continue;
                  }
	              
	              itemId = Integer.parseInt(temp);
				  vi = api.findCurrentItemByVendor(vendorId, itemId);
				  poi = PurchasesFactory.createPurchaseOrderItem(poId);
				  poi.setItemMasterId(itemId);
				  poi.setQty(1);
				  poi.setUnitCost(vi.getUnitCost());
				  items.add(poi);
			  }
			  catch (NumberFormatException e) {
				  this.msg = "The following item id could not be converted to a number during the process of adding selected items to purchase order" + poId;
				  logger.log(Level.ERROR, this.msg);
		    	  throw new SystemException(this.msg);
			  }
			  catch (PurchaseOrderException e) {
				  this.msg = e.getMessage();
				  logger.log(Level.ERROR, this.msg);
				  throw new SystemException(this.msg);
			  }
		  } // end for
		  
		  return items;
	  }
	  
	  
	  
	  /**
	   * Retrieves the items associated to the purchase order from the client's 
	   * request into a collection.   Each item row should contain input fields 
	   * that follow the naming convention of: [PropertyName + row id].<p>
	   * <p>  
	   * The following input field base names are expected of the client:
	   * <ul>
	   *   <li>ItemMasterId</li>
	   *   <li>QtyOrderd</li>
	   *   <li>QtyReceived</li>
	   *   <li>UnitCost</li>
	   *   <li>All rows are expected to have a selectable input field such as a checkbox or radio button named, "selCbx", which its value will contain a row id.  This is for the purpose of identifying which row is selected.</li>
	   *   <li>For each row, a non-unique field named, "rowId", must exist which its value will be the row id, which is used to track indivual rows.</li>
	   * </ul>
	   * 
	   * @return An ArrayList of {@link PurchaseOrderItems}
	   */
	  public ArrayList getHttpPurchaseOrderItems() {
	      int poId = 0;
	      int itemId = 0;
	      int row = 0;
	      int qtyOrdered = 0;
	      int qtyReceived = 0;
	      double unitCost = 0;
	      String temp = null;
	      String httpRowId[];
	      String httpSelRowId[];
	      ArrayList items = new ArrayList();
	      PurchaseOrderItems poi;
	     
	      // Test if purchase order items exist on the client.  If false, bail out 
	      // of this method.  If we are processing the details of a purchase order, 
	      // then at least one purchase order item should exist containing an input 
	      // field named, ItemMasterId0.
	      if (this.request.getParameter("ItemMasterId" + 0) == null) {
	    	  return items;
	      }
	      
	      // Get Vendor Id and Purchase Order Id
	      if (this.po != null) {
	    	  poId = this.po.getId();
	      }
	      if (this.creditor != null) {
	    	  this.creditor.getId();
	      }
	      
	      // Get selected items
	      httpSelRowId = this.request.getParameterValues("selCbx");
	      
	      // Get all row id's
	      httpRowId = this.request.getParameterValues("rowId");
		  if (httpRowId == null) {
			  // Bypass api update
			  return items;
		  }

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
			  items.add(poi);
		  } // end for
		  return items;
	  }
	  
	  /**
	   * Verifies that an item identified as _rowId has been selected.
	   * 
	   * @param _selectedRows A list of selected row id's
	   * @param _rowId The target row id to query.
	   * @return true when row id is selected, and false when row id is not selected.
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
	
	/**
	 * Gets Creditor.
	 * 
	 * @return
	 */
	public Creditor getCreditor() {
		return this.creditor;
	}
	
	/**
	 * @return the Purchase Order
	 */
	public PurchaseOrder getPo() {
		return po;
	}

	/**
	 * @return the Purchase Order Items
	 */
	public List getPoItems() {
		return poItems;
	}
	
	/**
	 * @return the new purchase order items
	 */
	public List getNewPoItems() {
		return newPoItems;
	}

	/**
	 * @return the Purchase Order Status
	 */
	public PurchaseOrderStatus getPoStatus() {
		return pos;
	}

	/**
	 * @return the Purchase Order Status History
	 */
	public PurchaseOrderStatusHist getPoStatusHist() {
		return posh;
	}

	/**
	 * @return the vendor
	 */
	public VwCreditorBusiness getVendor() {
		return vendor;
	}	
	
	/**
	 * This method retrieves purchase order data including vendor data from an external 
	 * datasource.	It is assumed that the purchase order object and the creditor 
	 * object is valid and accounted for.  Otherwise, this operation bypasses the retrieval.
	 * 
	 * @throws ActionHandlerException General database error.
	 */
	public void retrievePurchaseOrder() throws ActionHandlerException {
		if (this.po != null && this.creditor != null) {
			this.retrievePurchaseOrder(this.po.getId(), this.creditor.getId());
		}
		return;
	}
	
	  /**
	   * Uses _poId and _vendorId to retrieve purchase order data and vendor data 
	   * from the database.   After the data is retrieved, this method packages 
	   * the data into various objects which are sent to the client via HttpServletRequest 
	   * object for presentation.
	   * 
	   * @param _poId The id of the target purchase order.
	   * @param _vendorId The id of the target purchase order.
	   * @throws ActionHandlerException
	   */
	  public void retrievePurchaseOrder(int _poId, int _vendorId) throws ActionHandlerException {
	      double orderTotal = 0;
	      List vendors = null;
	      GLCreditorApi vendApi;
	      PurchasesApi purchApi;
	      
	      try {
	    	  // Get vendor data.  Expect to receive a vendor id at all times since purchase orders created per vendor.
	    	  vendApi = AcctManagerFactory.createCreditor(this.dbConn, this.request);
	    	  vendors = vendApi.findCreditorBusiness("creditor_id = " + _vendorId);
	    	  if (vendors.size() > 0) {
	    		  this.vendor = (VwCreditorBusiness) vendors.get(0);
	    	  }
	    	  else {
	    		  this.msg = "Vendor could not be found using id: " + _vendorId;
	    		  logger.log(Level.ERROR, this.msg);
	    		  throw new ActionHandlerException(this.msg);
	    	  }
	    	  
	          // Get base purchase order.
	    	  purchApi = PurchasesFactory.createApi(this.dbConn, this.request);
	    	  this.po = purchApi.findPurchaseOrder(_poId);
	    	  if (this.po == null) {
	    		  // This might be a new purchase order request, so ensure that all objects 
	    		  // are created and initialized to prevent null exceptions on the client.
	    		  this.createNewPurchaseOrderData();
	    		  this.po.setVendorId(_vendorId);
                  this.po.setTotal(0.0);
                  return;
	    	  }
              // Get purchase order items
              this.poItems = purchApi.findVendorItemPurchaseOrderItems(_poId);
              // Get Purchase Order Total and store in po base object
              orderTotal = purchApi.calcPurchaseOrderTotal(_poId);
              this.po.setTotal(orderTotal);
	              
              // Get purchase order current status
              this.posh = purchApi.findCurrentPurchaseOrderHistory(_poId);
              this.pos = purchApi.findPurchaseOrderStatus(posh.getPurchaseOrderStatusId());
    	  }
	      catch (Exception e) {
	    	  logger.log(Level.ERROR, e.getMessage());
	          throw new ActionHandlerException(e);
	      }
	      return;
	  }

	  /**
	   * Creates various purchase order java objects, which are properly initialized but 
	   * are not pointing to any related entities, and stores them on the HttpServleRequest 
	   * object to be sent to client for presentation.
	   *
	   */
	  protected void createNewPurchaseOrderData()  {
	      this.po = PurchasesFactory.createPurchaseOrder();
	      this.poItems = new ArrayList();
	      this.posh = PurchasesFactory.createPurchaseOrderStatusHist();
	      this.pos = PurchasesFactory.createPurchaseOrderStatus();
	      return;
	  }



}
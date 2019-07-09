package com.apiimpl;


import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.api.PurchasesApi;
import com.api.GLCreditorApi;
import com.api.InventoryApi;
import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;

import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VendorItems;
import com.bean.VwVendorItems;
import com.bean.Xact;
import com.bean.VwPurchaseOrderList;
import com.bean.CreditorType;
import com.bean.Creditor;
import com.bean.ItemMaster;
import com.bean.ItemMasterType;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.constants.AccountingConst;
import com.constants.ItemConst;
import com.constants.PurchasesConst;
import com.constants.XactConst;

import com.factory.PurchasesFactory;
import com.factory.AcctManagerFactory;
import com.factory.InventoryFactory;
import com.factory.XactFactory;

import com.util.PurchaseOrderException;
import com.util.SystemException;
import com.util.NotFoundException;
import com.util.GLAcctException;
import com.util.ItemMasterException;
import com.util.XactException;
import com.util.RMT2Utility;

/**
 * Api Implementation that manages inventory purchase transactions on account.
 * <p>
 * <p>
 * When a purchase order is submitted, the base transaction amount is posted to the xact table as a positive value, and the 
 * creditor activity amount is posted as positive value which increases the value of the creditor's account.   Conversely, when 
 * a purchase order is cancelled,  the base transaction amount is posted to the xact table as a negative value, and the 
 * creditor activity amount is posted as negative value which decreases the value of the creditor's account. 
 * 
 * @author appdev
 *
 */
public class PurchasesManagerApiImpl extends XactManagerApiImpl implements PurchasesApi  {
	private String criteria;

   /**
    * Default Constructor
    * 
    * @throws DatabaseException
    * @throws SystemException
    */
   protected PurchasesManagerApiImpl() throws DatabaseException, SystemException   {
	   super();   
   }

   /**
    * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
    * 
    * @param dbConn
    * @throws DatabaseException
    * @throws SystemException
    */
    public PurchasesManagerApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
    	super(dbConn);
		this.setBaseView("PurchaseOrderView");
		this.setBaseClass("com.bean.PurchaseOrder");
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param _request
     * @throws DatabaseException
     * @throws SystemException
     */
    public PurchasesManagerApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
        super(dbConn);
        this.setRequest(_request);
		this.setBaseView("PurchaseOrderView");
		this.setBaseClass("com.bean.PurchaseOrder");
    }
    
    

    /**
     * 
     */
    public PurchaseOrder findPurchaseOrder(int _poId) throws PurchaseOrderException {
    	this.setBaseClass("com.bean.PurchaseOrder");
    	this.setBaseView("PurchaseOrderView");
    	this.criteria = "id  = " + _poId;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (PurchaseOrder) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
			throw new PurchaseOrderException(this.connector, 1000, this.msgArgs);
		}
	}

    public List findVendorItemPurchaseOrderItems(int poId) throws PurchaseOrderException {
    	String method = "findVendorItemPurchaseOrderItems";
 	    this.setBaseClass("com.bean.VwVendorItemPurchaseOrderItem");
        this.setBaseView("VwVendorItemPurchaseOrderItemView");
        this.criteria = " purchase_order_id = " + poId;
        try {
            List list = this.find(this.criteria);
            if (list.size() <= 0) {
         	   return null;
            }
            return list;
        }
        catch (SystemException e) {
            throw new PurchaseOrderException(e.getMessage(), e.getErrorCode(), this.className, method);
        }
    }
    
    
    
    public VendorItems findVendorItem(int vendorId, int itemId) throws PurchaseOrderException {
    	String method = "findVendorItem";
 	    this.setBaseClass("com.bean.VendorItems");
        this.setBaseView("VendorItemsView");
        this.criteria = " vendor_id = " + vendorId + " and item_master_id = " + itemId;
        try {
            List list = this.find(this.criteria);
            if (list.size() <= 0) {
         	   return null;
            }
            return (VendorItems) list.get(0);
        }
        catch (SystemException e) {
            throw new PurchaseOrderException(e.getMessage(), e.getErrorCode(), this.className, method);
        }
    }
    
    
    public VwVendorItems findCurrentItemByVendor(int _vendorId, int _itemId) throws PurchaseOrderException {
        String method = "findCurrentItemByVendor";
      
        this.setBaseView("VwVendorItemsView");
        this.setBaseClass("com.bean.VwVendorItems");
        this.criteria =  " vendor_id = " + _vendorId + " and  item_master_id = " + _itemId;
        try {
            List list = this.find(this.criteria);
            if (list.size() <= 0) {
                return null;
            }
            return (VwVendorItems) list.get(0);
        }
        catch (SystemException e) {
            throw new PurchaseOrderException(e.getMessage(), e.getErrorCode(), this.className, method);
        }
    }
    
    
    public PurchaseOrderItems findPurchaseOrderItem(int _poItemId) throws PurchaseOrderException {
    	this.setBaseClass("com.bean.PurchaseOrderItems");
    	this.setBaseView("PurchaseOrderItemsView");
    	this.criteria = "id  = " + _poItemId;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (PurchaseOrderItems) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
			throw new PurchaseOrderException(this.connector, 1000, this.msgArgs);
		}
    }
    
    
    public List findPurchaseOrderItems(int _poId) throws PurchaseOrderException {
    	this.setBaseClass("com.bean.PurchaseOrderItems");
    	this.setBaseView("PurchaseOrderItemsView");
    	this.criteria = "purchase_order_id  = " + _poId;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return list;
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
    		throw new PurchaseOrderException(this.connector, 1000, this.msgArgs);
		}
    }
    
    public VwPurchaseOrderList findCurrentPurchaseOrder(int _poId) throws PurchaseOrderException {
    	this.setBaseClass("com.bean.VwPurchaseOrderList");
    	this.setBaseView("VwPurchaseOrderListView");
    	this.criteria = "id  = " + _poId;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (VwPurchaseOrderList) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
			throw new PurchaseOrderException(this.connector, 1000, this.msgArgs);
		}
    }
    
    public PurchaseOrderStatus findPurchaseOrderStatus(int _poStatusId) throws PurchaseOrderException {
    	this.setBaseClass("com.bean.PurchaseOrderStatus");
    	this.setBaseView("PurchaseOrderStatusView");
    	this.criteria = "id  = " + _poStatusId ;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (PurchaseOrderStatus) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
    		throw new PurchaseOrderException(this.connector, 1000, this.msgArgs);
		}
    }
    
    
    public PurchaseOrderStatusHist findCurrentPurchaseOrderHistory(int _poId) throws PurchaseOrderException {
    	this.setBaseClass("com.bean.PurchaseOrderStatusHist");
    	this.setBaseView("PurchaseOrderStatusHistView");
    	this.criteria = "purchase_order_id  = " + _poId + " and  end_date is null ";
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (PurchaseOrderStatusHist) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
    		throw new PurchaseOrderException(this.connector, 1000, this.msgArgs);
		}
    }
    
    
    public List findPurchaseOrderHistory(int _poId) throws PurchaseOrderException {
    	this.setBaseClass("com.bean.PurchaseOrderStatusHist");
    	this.setBaseView("PurchaseOrderStatusHistView");
    	this.criteria = "purchase_order_id  = " + _poId;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return list;
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
    		throw new PurchaseOrderException(this.connector, 1000, this.msgArgs);
		}
    }
    
    public void doVendorPurchaseReturnAllow(int _purchaseOrderId) throws PurchaseOrderException {
    	return;
    }
    
    public int maintainPurchaseOrder(PurchaseOrder _po, List _items) throws PurchaseOrderException {
    	String method = "[" + this.className + ".maintainPurchaseOrder] ";
    	int rc = 0;
    	if (_po == null) {
    		throw new PurchaseOrderException(method + " - Base purchase order object is invalid.  maintainPurchaseOrder aborted.");
    	}
    	if (_items == null) {
    		throw new PurchaseOrderException(method + " - Purchase order items object is invalid.  maintainPurchaseOrder aborted.");
    	}
    	
    	if (_po.getId() == 0) {
    		// Returns new Purchase order id
    		rc = this.insertPurchaseOrder(_po, _items);
    	}
    	else {
    		// Returns total number of rows updated.
    		rc = this.updatePurchaseOrder(_po, _items, true);
    	}
    	return rc;
    }

    public int addItemsToPurchaseOrder(PurchaseOrder _po, List _items) throws PurchaseOrderException {
    	String method = "[" + this.className + ".maintainPurchaseOrder] ";
    	int rc = 0;
    	if (_po == null) {
    		throw new PurchaseOrderException(method + " - Base purchase order object is invalid.  addItemsToPurchaseOrder aborted.");
    	}
    	if (_items == null) {
    		throw new PurchaseOrderException(method + " - Purchase order items object is invalid.  addItemsToPurchaseOrder aborted.");
    	}
    	
    	if (_po.getId() == 0) {
    		// Returns new Purchase order id
    		rc = this.insertPurchaseOrder(_po, _items);
    		_po.setId(rc);
    	}
    	else {
    		// Returns total number of rows updated.
    		rc = this.updatePurchaseOrder(_po, _items, false);
    	}
    	return _po.getId();
    }
    
    
    
    /**
     * Adds a purchase order and its items to the databse.  A status of  "Quote" will be assigned to the new purchase order.
     * 
     * @param _po Base purchase order data.
     * @param _items All related purchase order items
     * @return The new purchase order id
     * @throws PurchaseOrderException
     */
    protected int insertPurchaseOrder(PurchaseOrder _po, List _items) throws PurchaseOrderException {
    	int poId = 0;
    	    	
    	// We must be working with a valid po at this point, so add to the database
    	poId = this.insertPurchaseOrderBase(_po);
    	this.insertPurchaseOrderItems(poId, _items);

    	// Always assign a status of "Quote" for a new purchase order.
        this.setPurchaseOrderStatus(poId, PurchasesConst.PURCH_STATUS_QUOTE);
        return poId;	
    }
    
    
    /**
     * Adds base purchase order data to the database.
     * 
     * @param _po Base purchase order data.
     * @return The new purchase order id
     * @throws PurchaseOrderException
     */
    private int insertPurchaseOrderBase(PurchaseOrder _po) throws PurchaseOrderException {
    	String method = "[" + this.className + ".insertPurchaseOrder] ";
    	int poId = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);

    	// We must be working with a valid po at this point, so add to the database
    	try {
    		// Setup DataSource object to apply database updates.
    		UserTimestamp ut = RMT2Utility.getUserTimeStamp(this.request);
    		
    		// Apply base purchase order
        	this.validatePurchaseOrder(_po);
            _po.setNull("status");
            _po.setNull("xactId");
    		_po.setDateCreated(ut.getDateCreated());
    		_po.setDateUpdated(ut.getDateCreated());
    		_po.setUserId(ut.getLoginId());
    		poId = dao.insertRow(_po, true);
    		return poId;	
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
        catch (SystemException e) {
            System.out.println(method + " SystemException - " + e.getMessage());
            throw new PurchaseOrderException(e);
        }
    }
    
    
    
    /**
     * Adds a purchase order's items to the databse 
     * 
     * @param _poId Id of the base purchase order.
     * @param _items All related purchase order items
     * @return The total number of rows effect by transaction.
     * @throws PurchaseOrderException
     */
    protected int insertPurchaseOrderItems(int _poId, List _items) throws PurchaseOrderException {
    	String method = "[" + this.className + ".insertPurchaseOrderItems] ";
    	int rc = 0;
        int existItemTotal = 0;
        List existingItems = null;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrderItems poi = null;

     	try {
           existingItems = this.findPurchaseOrderItems(_poId);
           existItemTotal = existingItems == null ? 0 : existingItems.size();
           
    		// Setup DataSource object to apply database updates.
    		UserTimestamp ut = RMT2Utility.getUserTimeStamp(this.request);
    		
    		// Apply all items belonging to the base purchase order.
            for (int ndx = 0; ndx < _items.size(); ndx++) {
            	poi = (PurchaseOrderItems) _items.get(ndx);
            	this.validatePurchaseOrderItem(poi);
                poi.setId(ndx + 1 + existItemTotal);
            	poi.setPurchaseOrderId(_poId);
            	poi.setDateCreated(ut.getDateCreated());
            	poi.setDateUpdated(ut.getDateCreated());
            	poi.setUserId(ut.getLoginId());
            	rc += dao.insertRow(poi, false);
            }
            return rc;	
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
        catch (SystemException e) {
            System.out.println(method + " SystemException - " + e.getMessage());
            throw new PurchaseOrderException(e);
        }
    }
    

    /**
     * Modifies an exisitng purchase order and its items.   If a purchase order has been received,  change its status 
     * to "Received" and update the purchase order with external reference number, if available. 
     * <p>
     * <b><u>NOTE</u></b>
     * <p>
     * A purchase order can only be updated when its status is either "Quote" or "Submitted".
     * 
     * @param _po Modified base purchase order data.
     * @param _items Modified Purchase order items.
     * @param __refresh boolean which determines whether existing purchase order items should be deleted prior to adding revisions.
     * @return -1 when purchase order is not in Quote or Submitted statuses.  
     * 1 indicating the successful update of a Quote purchase order.   0 indicating the purchase order has been completely fulfilled and is in "Received" status.
     * @throws PurchaseOrderException
     */
    protected int updatePurchaseOrder(PurchaseOrder _po, List _items, boolean _refresh) throws PurchaseOrderException {
    	String method = "[" + this.className + ".updatePurchaseOrder] ";
    	int rc = 0;
    	int poStatusId = 0;
    	PurchaseOrderStatusHist posh = null;

    	// Purchase order can only be updated when status is either Quote or Submitted.
    	posh = this.findCurrentPurchaseOrderHistory(_po.getId());
    	poStatusId = posh.getPurchaseOrderStatusId();
    	if (poStatusId != PurchasesConst.PURCH_STATUS_QUOTE &&	poStatusId != PurchasesConst.PURCH_STATUS_FINALIZE) {
    		System.out.println(method + " Purchase order #" + _po.getId() + " was denied updates.   Must be in Quote or Submitted status.");
    		return -1;
    	}
    	
    	// We must be working with a valid po at this point, so add to the database
   		this.updatePurchaseOrderBase(_po);
   		if (_refresh) {
   			rc = this.updatePurchaseOrderItems(_po.getId(), _items);	
   		}
   		else {
   			rc = this.insertPurchaseOrderItems(_po.getId(), _items);
   		}
   		
   		// Determine if purchase order is ready to be placed in "Received" status.  The purchase order quantity of all items 
   		// must have been received and the current purchase order status must be "Submitted".
   	    if (rc == PurchasesConst.PO_UPDATE_RECEIVED && poStatusId == PurchasesConst.PURCH_STATUS_FINALIZE) {
   	    	this.setPurchaseOrderStatus(_po.getId(), PurchasesConst.PURCH_STATUS_RECEIVED);
   	    }
        return rc;	
    }
    
    /**
     * Modifies an exisitng base purchase order
     * 
     * @param _po Modified base purchase order data.
     * @return total number of rows updated.  Returns a -1 if purchase order is not in Quote or Submitted statuses.
     * @throws PurchaseOrderException
     */
    private void updatePurchaseOrderBase(PurchaseOrder _po) throws PurchaseOrderException {
    	String method = "[" + this.className + ".updatePurchaseOrder] ";
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrder oldPo = null;
    	UserTimestamp ut = null;
    	
    	// We must be working with a valid po at this point, so add to the database
    	try {
    		// Setup DataSource object to apply database updates.
    		ut = RMT2Utility.getUserTimeStamp(this.request);
    		
            // Get old version of data and apply to new object.  This will ensure that unedited 
    		// data persists after applying database updates
    		oldPo = this.findPurchaseOrder(_po.getId());
            _po.setNull("status");

            // Transaction Id should be zero if purchase order is not being finalized.
            if (_po.getXactId() == 0) {
                _po.setNull("xactId");
                _po.setNull("total");
            }
            else {
                _po.removeNull("xactId");
                _po.removeNull("total");
            }
    		if (_po.getRefNo() != null) {
    			_po.setRefNo(oldPo.getRefNo());	
    		}
    		_po.setDateCreated(oldPo.getDateCreated());
        	this.validatePurchaseOrder(_po);
    		_po.setDateUpdated(ut.getDateCreated());
    		_po.setUserId(ut.getLoginId());
    		_po.addCriteria("Id", _po.getId());
    		dao.updateRow(_po);
            return;	
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
        catch (SystemException e) {
            System.out.println(method + " SystemException - " + e.getMessage());
            throw new PurchaseOrderException(e);
        }
    }

    
    /**
     * Modifies exisitng purchase order items and applys the changes to the database.   All existing items for a 
     * purchase order are deleted and replaced by _items when the purchase order's current status is "Quote".   When 
     * the current status is "Submitted", all exisiting items can no longer be deleted and are sequentially retrieved from and  
     * and updated to the database instead.  
     * 
     * @param _poId Id of the base purchase order
     * @param _items Modified Purchase order items.
     * @return  1 indicating the successful update of a "Quote" purchase order or "Submitted" purchase order that has not 
     * received its total order quantity.   0 indicating the "Submitted" purchase order has been completely received. 
     * @throws PurchaseOrderException
     */
    private int updatePurchaseOrderItems(int _poId, List _items) throws PurchaseOrderException {
    	String method = "[" + this.className + ".updatePurchaseOrderItems] ";
    	int rc = 0;
    	int poStatusId = 0;
    	int poUncollectCnt = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrderItems deltaPoi = null;
    	PurchaseOrderStatusHist posh = null;
    	UserTimestamp ut = null;

    	// Purchase order can only be updated when status is either Quote or Submitted.
    	posh = this.findCurrentPurchaseOrderHistory(_poId);
    	poStatusId = posh.getPurchaseOrderStatusId();
    	
    	// We must be working with a valid po at this point, so add to the database
    	try {
    		// Setup DataSource object to apply database updates.
    		ut = RMT2Utility.getUserTimeStamp(this.request);
    		

    		// Use the current purchase order status to determine if items are to be totally refreshed or 
    		// if each item should be retrieved, reconciled, and updated accordingly.
        	switch (poStatusId) {
        		case PurchasesConst.PURCH_STATUS_QUOTE:
	        		// Perform a complete refresh of all purchase order items.
	            	this.deleteAllItems(_poId);
	                for (int ndx = 0; ndx < _items.size(); ndx++) {
	                	deltaPoi = (PurchaseOrderItems) _items.get(ndx);
                        deltaPoi.setId(ndx + 1);
	                	this.validatePurchaseOrderItem(deltaPoi);
	                	deltaPoi.setDateCreated(ut.getDateCreated());
	                	deltaPoi.setDateUpdated(ut.getDateCreated());
	                	deltaPoi.setUserId(ut.getLoginId());
	                	rc = dao.insertRow(deltaPoi, false);
	                }
	                rc = PurchasesConst.PO_UPDATE_SUCCESSFUL;
	                break;
	                
        		case PurchasesConst.PURCH_STATUS_FINALIZE:
	                for (int ndx = 0; ndx < _items.size(); ndx++) {
	                	try {
		                	deltaPoi = (PurchaseOrderItems) _items.get(ndx);
		                	rc = this.updatePurchaseOrderItem(deltaPoi);
		                	// Add item's uncollected order quantity 
	                		poUncollectCnt += (deltaPoi.getQty() - deltaPoi.getQtyRcvd());
	                	}
	                	catch (NotFoundException e) {
	                		continue;
	                	}
	                }
	                if (poUncollectCnt == 0) {
	                	rc = PurchasesConst.PO_UPDATE_RECEIVED;	
	                }
	                if (poUncollectCnt >= 0) {
	                	rc = PurchasesConst.PO_UPDATE_SUCCESSFUL;	
	                }
	                break;
        	} // end switch
        	
            return rc;	
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
        catch (SystemException e) {
            System.out.println(method + " SystemException - " + e.getMessage());
            throw new PurchaseOrderException(e);
        }
    }
    

    /**
     * Applies the modifications of an exisitng purchase order item to the database and updates inventory with the quantity 
     * received for _deltaItem.   This method is generally used during the time of updating a "Submitted" purchase order for the 
     * purpose of reconciling the quantity ordered and quantity received totals for each item.
     * <p>
     * Basic processing scenario:
     * <p>
     * <ol>
     *    <li>Retrieve the orginal version of the purchase order</li>
     *    <li>Update item's received quantity with input</li>
     *    <li>Calculate difference in quantity ordered and quantity received</li>
     *    <li>Apply item update to the database</li>
     *    <li>Add the difference to uncollected order quantity count for the purchase order</li>
     *  </ol>
     *   
     * @param _deltaItem The purchase order item data.
     * @return 0 indicating the total order quantity for this item has beed received.   >0 indicating the item's uncollected order quantity.  
     * @throws PurchaseOrderException
     * @throws NotFoundException a problem accessing item data from the database.
     */
    private int updatePurchaseOrderItem(PurchaseOrderItems _deltaItem) throws NotFoundException, PurchaseOrderException {
    	String method = "[" + this.className + ".updatePurchaseOrderItem] ";
    	int rc = 0;
    	int adjQtyOnHand = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrderItems oldPoi = null;
    	UserTimestamp ut = null;

    	// We must be working with a valid po item at this point, so apply updates to the database
    	try {
    		// Setup DataSource object to apply database updates.
    		ut = RMT2Utility.getUserTimeStamp(this.request);

    		// Use the current purchase order status to determine if items are to be totally refreshed or 
    		// if each item should be retrieved and updated accordingly.
           	oldPoi = this.findPurchaseOrderItem(_deltaItem.getId());
	        if (oldPoi == null) {
	        	this.msg = method + " - Purchase order item, " + _deltaItem.getId() + ", was not found for PO #" + _deltaItem.getPurchaseOrderId();
	        	throw new NotFoundException(this.msg) ;
	        }
	        
           	// Apply Quantity Received and user update timestamp
           	oldPoi.setQtyRcvd(_deltaItem.getQtyRcvd() + oldPoi.getQtyRcvd());
           	oldPoi.setDateUpdated(ut.getDateCreated());
           	oldPoi.setUserId(ut.getLoginId());	                	
        	this.validatePurchaseOrderItem(oldPoi);
        	oldPoi.addCriteria("Id", oldPoi.getId());
        	rc = dao.updateRow(oldPoi);
        	
        	// Update inventory by adding quantity received to Quantity on hand for target item.
        	try {
        		InventoryApi invApi = InventoryFactory.createInventoryApi(this.connector);
        		ItemMaster im = invApi.findItemById(oldPoi.getItemMasterId());
        		adjQtyOnHand = im.getQtyOnHand() + this.calculateItemNetOrderQty(oldPoi); 
        		im.setQtyOnHand(adjQtyOnHand);
        		invApi.maintainItemMaster(im, null);
        	}
        	catch (ItemMasterException e) {
	        	this.msg = method + " - Problemu updating inventory for Purchase order item, " + _deltaItem.getId() + ", of PO #" + _deltaItem.getPurchaseOrderId() + ":  " + e.getMessage();
	        	throw new PurchaseOrderException(this.msg) ;
        	}
        	 
        	if (rc >= 1) {
        		// Set return code to equal the uncollected order quantity for this item.
        		rc = oldPoi.getQty() - oldPoi.getQtyRcvd();
        	}
            return rc;	
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
        catch (SystemException e) {
            System.out.println(method + " SystemException - " + e.getMessage());
            throw new PurchaseOrderException(e);
        }
    }

    
    public int deletePurchaseOrder(int _poId) throws PurchaseOrderException {
    	String method = "[" + this.className + ".deletePurchaseOrder] ";
    	int rc = 0;
    	int poStatusId = 0;
    	PurchaseOrder po = null;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrderStatusHist posh = null;

    	// Purchase order can only be deleted from the system when in Quote status.
    	posh = this.findCurrentPurchaseOrderHistory(_poId);
    	poStatusId = posh.getPurchaseOrderStatusId();
    	if (poStatusId != PurchasesConst.PURCH_STATUS_QUOTE) {
    		this.msg = method + " Purchase order #" + _poId + " cannot be deleted.   Must be in Quote status to perform deletes.";
    		System.out.println(this.msg);
    		throw new PurchaseOrderException(this.msg, -1);
    	}
    	
    	// Begin to delete data.
    	po = this.findPurchaseOrder(_poId);
    	try {
    	    po.addCriteria("Id", po.getId());
    		rc += dao.deleteRow(po);	
    		System.out.println("Purchase Order #" + po.getId() + " was successfully deleted");
    		rc += this.deleteAllItems(_poId);
    		return rc;
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
    }
    
    public int deleteItem(int _poItemId) throws PurchaseOrderException {
    	String method = "[" + this.className + ".deleteItem] ";
    	int rc = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrderItems poi = null;
    	
    	poi = this.findPurchaseOrderItem(_poItemId);
    	try {
    	    poi.addCriteria("Id", poi.getId());
    		rc = dao.deleteRow(poi);	
    		System.out.println("PO Item #" + poi.getId() + " was successfully deleted from PO #" + poi.getPurchaseOrderId());
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
    	return rc;
    }
    
   
    
    public int deleteAllItems(int _poId) throws PurchaseOrderException {
    	String method = "[" + this.className + ".deleteAllItems] ";
    	int rc = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrderItems poi = null;
    	List list = null;
    	
    	list = this.findPurchaseOrderItems(_poId);
    	if (list == null) {
    		return 0;
    	}
    	
    	try {
    		// Cycle through all items of the purchase order.
    		for (int ndx = 0; ndx < list.size(); ndx++) {
        		poi = (PurchaseOrderItems) list.get(ndx);
        		poi.addCriteria("Id", poi.getId());
        		rc += dao.deleteRow(poi);	    		
        		System.out.println("PO Item #" + poi.getId() + " was successfully deleted from PO #" + poi.getPurchaseOrderId());
    		}
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
    	return rc;
    }
    
    /**
     * Validates base purchase order data.
     * <p>
     * <p>
     * The following validations must be met:
     * <ul>
     *    <li>A purchase order's creditor id must be greater than zero</li>
     *    <li>Creditor must be a vendor type</li>
     * </ul> 
     * 
     * @param _po Purchase order object containing the base data.
     * @throws PurchaseOrderException if the creditor value is less than or equal to zero, or creditor does not exist, or creditor is not a vendor type.   
     */
    protected void validatePurchaseOrder(PurchaseOrder _po) throws PurchaseOrderException {
    	 String method = "[" + this.className + "." + "validatePurchaseOrder" + "] ";
    	 CreditorType ctVend = null;
    	 GLCreditorApi credApi = null;
    	 Creditor cred = null;
    	 
    	 // Get creditor data to used as validating metric
    	 try {
    		 credApi = AcctManagerFactory.createCreditor(this.connector);
    		 ctVend = credApi.findCreditorTypeById(AccountingConst.CREDITOR_TYPE_VENDOR);
    		 
        	 // Creditor id must be greater than zero.
        	 if (_po.getVendorId() <= 0) {
                 throw new PurchaseOrderException(this.connector, 432, null);
        	 }

        	 // Creditor must exist in the database.
        	 cred = credApi.findCreditorById(_po.getVendorId());
        	 if (cred == null) {
        		 throw new PurchaseOrderException(this.connector, 433, null);
        	 }
        	 
        	 // Creditor must be of type vendor.
        	 if (cred.getCreditorTypeId() != ctVend.getId()) {
        		 throw new PurchaseOrderException(this.connector, 434, null);
        	 }
    	 }
    	 catch (GLAcctException e) {
    		 this.msg = method + " GLAcctException - " + e.getMessage();
    		 System.out.println(this.msg);
    		 throw new PurchaseOrderException(this.msg);
    	 }
    	 catch (DatabaseException e) {
    		 this.msg = method + " DatabaseException - " + e.getMessage();
    		 System.out.println(this.msg);
    		 throw new PurchaseOrderException(this.msg);
    	 }
    	 catch (SystemException e) {
    		 this.msg = method + " SystemException - " + e.getMessage();
    		 System.out.println(this.msg);
    		 throw new PurchaseOrderException(this.msg);
    	 }
    }
    

    /**
     * Validates an item belonging to a purchase order.  
     * <p>
     * <p>
     * The following validations must be met:
     * <ul>
     *    <li>The item must contain a valid purchase order id which must be greater than zero.  This check is only performed for existing purchase order items</li>
     *    <li>Item Master Id must be a valid and is greater than zero</li>
     *    <li>The selected item master id must be a tangible and resalable inventory item</li>
     *    <li>The value of quantity orderd must be greater than or equal to zero</li>
     *    <li>The value of quantity received must be less than or equal to quantity ordered</li>
     * </ul>
     * 
     * @param _poi The purchase order item being evaluated.
     * @throws PurchaseOrderException
     */
    protected void validatePurchaseOrderItem(PurchaseOrderItems _poi) throws PurchaseOrderException {
    	String method = "[" + this.className + "." + "validatePurchaseOrderItem" + "] ";
    	PurchaseOrder po = null;
    	InventoryApi invApi = null;
    	ItemMaster im = null;
    	ItemMasterType imt  = null;
    	
    	try {
    		
    		if (_poi.getId() > 0) {
    			// Purchase order id must be greater than zero for existing PO items
    			if (_poi.getPurchaseOrderId() < 0) {
                    throw new PurchaseOrderException(this.connector, 435, null);
    			}
    			// Purchase order id must be exist in the system
    	        po = this.findPurchaseOrder(_poi.getPurchaseOrderId());
    	        if (po == null) {
                    throw new PurchaseOrderException(this.connector, 436, null);
    	        }
    	        
    		}
    		// Item master id must be valid and greater than zero.
    		if (_poi.getItemMasterId() <= 0) {
                throw new PurchaseOrderException(this.connector, 437, null);
    		}
            // Get Item Master item.
    		invApi = InventoryFactory.createInventoryApi(this.connector);
    		im = invApi.findItemById(_poi.getItemMasterId());
    		if (im == null) {
                throw new PurchaseOrderException(this.connector, 438, null);
    		}
    		
    		// Item must be a tangible resalable inventory item.
    		imt = invApi.findItemTypeById(ItemConst.ITEM_TYPE_MERCH);
    		if (im.getItemTypeId() != imt.getId()) {
                throw new PurchaseOrderException(this.connector, 439, null);
    		}
    		
    		// Qty ordered must be >= to zero.
    		if (_poi.getQty() < 0) {
                throw new PurchaseOrderException(this.connector, 440, null);
    		}
    		
    		// Qty received must be <= Qty ordered
    		if (_poi.getQtyRcvd() > _poi.getQty()) {
                throw new PurchaseOrderException(this.connector, 441, null);
    		}
    	}
    	catch (ItemMasterException e) {
   		 	this.msg = method + " ItemMasterException - " + e.getMessage();
   		 	System.out.println(this.msg);
   		 	throw new PurchaseOrderException(this.msg);
    	}
    	catch (DatabaseException e) {
    		this.msg = method + " DatabaseException - " + e.getMessage();
   		 	System.out.println(this.msg);
   		 	throw new PurchaseOrderException(this.msg);
   	 	}
   	 	catch (SystemException e) {
   	 		this.msg = method + " SystemException - " + e.getMessage();
   	 		System.out.println(this.msg);
   	 		throw new PurchaseOrderException(this.msg);
   	 	}
    }

    public void setPurchaseOrderStatus(int _poId, int _newStatusId) throws PurchaseOrderException {
    	UserTimestamp ut = null;
    	PurchaseOrderStatusHist posh = null;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	
    	posh = this.findCurrentPurchaseOrderHistory(_poId);
    	try {
        	if (posh != null) {
        		ut = RMT2Utility.getUserTimeStamp(this.request);	
        		posh.setEndDate(ut.getDateCreated());
        		posh.setUserId(ut.getLoginId());
        		posh.addCriteria("Id", posh.getId());
        		dao.updateRow(posh);
        	}
        	
        	// Add new status
        	posh = PurchasesFactory.createPurchaseOrderStatusHist(_poId, _newStatusId);
        	ut = RMT2Utility.getUserTimeStamp(this.request);
        	posh.setEffectiveDate(ut.getDateCreated());
            posh.setNull("endDate");
    		posh.setUserId(ut.getLoginId());
    		dao.insertRow(posh, true);
    	}
		catch (DatabaseException e) {
			throw new PurchaseOrderException(this.msg);
		}
		catch (SystemException e) {
			throw new PurchaseOrderException(this.msg);
		}
    }
    
    /**
     * Verifies that changing the status of the purchase order identified as _poId to the new status represented as _newStatusId is legal.
     * The change is considered legal only if an exception is not thrown.
     * <p>
     * The following sequence must be followed when changing the status of a purchase order:
     * <p>
     * <ul>
     * <li>The purchase order must be new in order to change the status to "Quote"</li>
     * <li>The purchase order must be in "Quote" status before changing to "Submitted".</li>
     * <li>The purchase order must be in "Submitted" status before changing to "Received".</li>
     * <li>The purchase order must be in "Quote" or "Submitted" status before changing to "Cancelled".</li>
     * </ul>
     * 
     * @param _poId Target purchase order id
     * @param _newStatusId The id of the status that is to be assigned to the purchase order.
     * @return The id of the old status.
     * @throws PurchaseOrderException When the prospective new status is not in sequence to the current status regarding 
     * changing the status of the purchase order.   The exception should give a detail explanation as to the reason why the 
     * status cannot be changed. 
     */
    protected int verifyStatusChange(int _poId, int _newStatusId)  throws PurchaseOrderException {
    	PurchaseOrderStatusHist posh = null;
    	int currentStatusId = 0;
    	
    	posh = this.findCurrentPurchaseOrderHistory(_poId);
    	currentStatusId = (posh == null ? PurchasesConst.NEW_PO_STATUS : posh.getPurchaseOrderStatusId());
    	switch (_newStatusId) {
    		case PurchasesConst.PURCH_STATUS_QUOTE:
    			if (currentStatusId != PurchasesConst.NEW_PO_STATUS) {
    				throw new PurchaseOrderException("Quote status can only be assigned when the purchase order is new");
    				//TODO: obtain message from the database.
    				//throw new PurchaseOrderException(this.dbo, 434, null);
    			}
    			break;
    			
    		case PurchasesConst.PURCH_STATUS_FINALIZE:
    			if (currentStatusId != PurchasesConst.PURCH_STATUS_QUOTE) {
    				throw new PurchaseOrderException("Purchase order must be in Quote status before changing to Submitted");
    				//TODO: obtain message from the database.
    				//throw new PurchaseOrderException(this.dbo, 434, null);
    			}
    			break;
    			
    		case PurchasesConst.PURCH_STATUS_RECEIVED:
    			if (currentStatusId != PurchasesConst.PURCH_STATUS_FINALIZE) {
    				throw new PurchaseOrderException("Purchase order must be in Submitted status before changing to Received");
    				//TODO: obtain message from the database.
    				//throw new PurchaseOrderException(this.dbo, 434, null);
    			}
    			break;
    			
    		case PurchasesConst.PURCH_STATUS_CANCEL:
    			switch (currentStatusId) {
    				case PurchasesConst.PURCH_STATUS_QUOTE:
    				case PurchasesConst.PURCH_STATUS_FINALIZE:
    					break;
    				
    				default:
    					throw new PurchaseOrderException("Purchase order must be in Quote or Submitted status before changing to Cancelled");
    					//TODO: obtain message from the database.
    					//throw new PurchaseOrderException(this.dbo, 434, null);
    			} // end inner switch
    			break;
    			
    		case PurchasesConst.PURCH_STATUS_RETURN:
    			switch (currentStatusId) {
    				case PurchasesConst.PURCH_STATUS_RECEIVED:
    				case PurchasesConst.PURCH_STATUS_PARTRET:
    					break;

    				default:
    					throw new PurchaseOrderException("Purchase order must be in Received status before changing to Returned");
    			} // end inner switch
				break;
    	} // end outer switch
    	
    	return currentStatusId;
    }

    
    public double calcPurchaseOrderTotal(int _poId) throws PurchaseOrderException {
    	double total = 0;
    	
    	total += this.calculateItemTotal(_poId);
    	total += this.calculateTaxes(_poId);
    	total += this.calculateOtherFees(_poId);
    	return total;
    }
    
    /**
     * Calculates the purchase order item total which is Qty * Unit Cost.
     * 
     * @param _poId The id of the target purchase order
     * @return Item Total
     * @throws PurchaseOrderException
     */
    private double calculateItemTotal(int _poId) throws PurchaseOrderException  {
        List items = null;
		PurchaseOrderItems poi = null;
		double total = 0;
		
		items = this.findPurchaseOrderItems(_poId);
		if (items == null) {
			return 0;
		}
		// Cycle through all items summing each as (qty * unit_price)
        for (int ndx = 0; ndx < items.size(); ndx++) {
        	poi = (PurchaseOrderItems) items.get(ndx);
        	total += poi.getUnitCost() * poi.getQty();
        }
       return total; 	
    }
    
    /**
     * Calculates the purchase order taxes.
     * 
     * @param _poId The id of the target purchase order
     * @return Purchase order tax amount.
     * @throws PurchaseOrderException
     */
    private double calculateTaxes(int _poId) throws PurchaseOrderException  {
    	return 0;
    }
    
    /**
     * Calculate other fees that may be applicable to the purchase order.
     * 
     * @param _poId The id of the target purchase order
     * @return Other fee total.
     * @throws PurchaseOrderException
     */
    private double calculateOtherFees(int _poId) throws PurchaseOrderException  {
    	return 0;
    }
    
    
    public int submitPurchaseOrder(PurchaseOrder _po, List _items) throws PurchaseOrderException {
    	double poTotal = 0;
    	int xactId = 0;
    	Xact xact = null;
		UserTimestamp ut = null;
		
    	this.verifyStatusChange(_po.getId(),  PurchasesConst.PURCH_STATUS_FINALIZE);
    	this.maintainPurchaseOrder(_po, _items);
    	poTotal = this.calcPurchaseOrderTotal(_po.getId());
    	try {
    		ut = RMT2Utility.getUserTimeStamp(this.request);
    		
    		// Setup Transaction
    		xact = XactFactory.createXact();
    		xact.setXactAmount(poTotal);
    		xact.setXactDate(ut.getDateCreated());
    		xact.setXactTypeId(XactConst.XACT_TYPE_INVPURCHASES);
    		xact.setReason("Submitted Inventory Purchase Order " + _po.getId());
            xact.setNull("tenderId");
    		xactId = this.maintainXact(xact, null);
    		
    		// Associate transaction with creditor
    		this.createCreditorActivity(_po.getVendorId(), xactId, poTotal);
    		
    		// Associate transaction with PO
    		_po.setXactId(xactId);
    		_po.setTotal(poTotal);
    		this.updatePurchaseOrderBase(_po);
    		
            // Change purchase order status to finalized
            this.setPurchaseOrderStatus(_po.getId(), PurchasesConst.PURCH_STATUS_FINALIZE);
            
    		return xactId;
    	}
    	catch (XactException e) {
            System.out.println(e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
    	catch (SystemException e) {
    		throw new PurchaseOrderException(e);
    	}
    }
    
    
    public void cancelPurchaseOrder(int _poId) throws PurchaseOrderException {
    	PurchaseOrder po = this.findPurchaseOrder(_poId);
    	Xact xact  = null;
    	int currentStatus = 0;
    	
    	// Check to see if it is okay to cancel this PO.  An exception will be thrown if chech fails!
    	currentStatus = this.verifyStatusChange(_poId, PurchasesConst.PURCH_STATUS_CANCEL);
    	
    	// Reverse transaction if purchase order has been submitted and a transaction has been assigned.
    	if (currentStatus == PurchasesConst.PURCH_STATUS_FINALIZE && po.getXactId() > 0) {
    		try {
    			xact = this.findXactById(po.getXactId());
    			this.reverseXact(xact, null);
    			this.createCreditorActivity(po.getVendorId(), xact.getId(), xact.getXactAmount());
    		}
    		catch (XactException e) {
    			throw new PurchaseOrderException(e);
    		}
    	}
    	
    	// Cancel Purchase Order by changing the status.
    	this.setPurchaseOrderStatus(_poId, PurchasesConst.PURCH_STATUS_CANCEL);    	
    }

    /**
     * 
     */
    protected void preReverseXact(Xact _xact, List _xactItems) {
        _xact.setNull("tenderId");
       return;
    }
    
    public void returnPurchaseOrder(int _poId, List _items) throws PurchaseOrderException {
    	PurchaseOrder po = this.findPurchaseOrder(_poId);
    	Xact xact  = null;
    	int rc = 0;
    	
    	// Check to see if it is okay to cancel this PO.  An exception will be thrown if chech fails!
    	this.verifyStatusChange(_poId, PurchasesConst.PURCH_STATUS_RETURN);
    	
    	// At this point it is evident that we have a transaction to reverse since this.verifyStatusChange did not bomb!
		try {
			xact = this.findXactById(po.getXactId());
			this.reverseXact(xact, null);
			// Associate transaction with creditor activity
			this.createCreditorActivity(po.getVendorId(), xact.getId(), xact.getXactAmount());
		}
		catch (XactException e) {
			throw new PurchaseOrderException(e);
		}
    
		// Pull applicable items from inventory
		rc = this.pullPurchaseOrderInventory(_items);
    	
    	// Flagg Purchase Order as either Returned or Partially Returned
		if (rc >= 1) {
			this.setPurchaseOrderStatus(_poId, PurchasesConst.PURCH_STATUS_PARTRET);	
		}
		else {
			this.setPurchaseOrderStatus(_poId, PurchasesConst.PURCH_STATUS_RETURN);
		}
    }
    
    
    /**
     * Pulls inventory for each purchase order item based on the requested item quantity return amount.
     * 
     * @param _items List of purchase order items accompanied with a quantity return value.
     * @return 0 when all items of the purchase order have been returned.  >= 1 when an order quantity exists for one or 
     * more items after the returns are applied.
     * @throws PurchaseOrderException When return quantity exceeds the order quantity, an item master error occurs, a database 
     * error occurs, or a system error occurrs.
     */
    private int pullPurchaseOrderInventory(List _items) throws PurchaseOrderException {
    	String method = "[" + this.className + ".insertPurchaseOrderItems] ";
    	int availQty = 0;
    	int qtyRtn = 0;
    	int totalOrderQty = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	PurchaseOrderItems deltaPoi = null;
    	PurchaseOrderItems oldPoi = null;

     	try {
    		// Setup DataSource object to apply database updates.
    		UserTimestamp ut = RMT2Utility.getUserTimeStamp(this.request);
    		
    		// Apply all items belonging to the base purchase order.
            for (int ndx = 0; ndx < _items.size(); ndx++) {
            	deltaPoi = (PurchaseOrderItems) _items.get(ndx);
            	oldPoi = this.findPurchaseOrderItem(deltaPoi.getId()); 
            	availQty = this.calculateItemNetOrderQty(oldPoi);
            	qtyRtn = deltaPoi.getQtyRtn();
            	if(availQty < qtyRtn) {
            		throw new PurchaseOrderException("Return Quantity (" + qtyRtn + ") cannot exceed Available Quantity(" + availQty + ") for purchase order");
            	}
            	
            	oldPoi.setQtyRtn(oldPoi.getQtyRtn() + qtyRtn);
            	oldPoi.setDateUpdated(ut.getDateCreated());
            	oldPoi.setUserId(ut.getLoginId());
            	oldPoi.addCriteria("Id", oldPoi.getId());
            	dao.updateRow(oldPoi);
            	
            	// Keep track of the order quantity for each item after thr return quantity is applied. 
            	totalOrderQty += this.calculateItemNetOrderQty(oldPoi);
            	
            	// Pull item from inventory based on the amount request (qtyRtn).
            	try {
            		InventoryApi invApi = InventoryFactory.createInventoryApi(this.connector);
            		invApi.pullInventory(oldPoi.getId(), qtyRtn);
            	}
            	catch (ItemMasterException e) {
    	        	this.msg = method + " - Problem pulling inventory for Purchase order item, " + oldPoi.getId() + ", of PO #" + oldPoi.getPurchaseOrderId() + ":  " + e.getMessage() + " - " + e.getMessage();
    	        	throw new PurchaseOrderException(this.msg) ;
            	}	
            } // end for
            return totalOrderQty;	
    	}
    	catch (DatabaseException e) {
    		System.out.println(method + " DatabaseException - " + e.getMessage());
    		throw new PurchaseOrderException(e);
    	}
        catch (SystemException e) {
            System.out.println(method + " SystemException - " + e.getMessage());
            throw new PurchaseOrderException(e);
        }
    }
    

    /**
     * Calculates the net order quantity of a purchase order item.   The net order quantity is basically the order quantity of a 
     * purchase order item available to be applied to inventory based on the current state of a purchase order item's 
     * order quantity, quantity received, and quantity returned.  The formula for this calculation is:
     * <p>
     * <p>
     *  Beginning order quantity - (beginning order quantity - quantity received) - quantity returned.
     *   
     * @param _poItem
     * @return The net order quantity
     */
    private int calculateItemNetOrderQty(PurchaseOrderItems _poItem) {
    	int begOrdQty = _poItem.getQty();
    	int returnQty = _poItem.getQtyRtn();
    	int recvQty = _poItem.getQtyRcvd();
    	int remainOrdQty = 0;
    	int adjOrdQty = 0;
    	int netOrdQty = 0;
    	
    	remainOrdQty = begOrdQty - recvQty;
    	adjOrdQty = begOrdQty - remainOrdQty;
    	netOrdQty = adjOrdQty - returnQty;
    	
    	return netOrdQty;
    }
}

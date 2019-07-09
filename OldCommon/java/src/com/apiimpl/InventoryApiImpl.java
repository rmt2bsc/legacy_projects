package com.apiimpl;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.api.InventoryApi;
import com.api.GLCreditorApi;
import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.factory.InventoryFactory;
import com.factory.AcctManagerFactory;

import com.util.SystemException;
import com.util.ItemMasterException;
import com.util.GLAcctException;
import com.util.RMT2Utility;

import com.bean.RMT2Base;
import com.bean.ItemMasterType;
import com.bean.ItemMaster;
import com.bean.ItemMasterStatus;
import com.bean.VwItemMaster;
import com.bean.VendorItems;
import com.bean.ItemMasterStatusHist;

import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.constants.ItemConst;


/**
 * Api Implementation that manages Inventory items.
 * 
 * @author RTerrell
 *
 */
public class InventoryApiImpl  extends RdbmsDataSourceImpl  implements InventoryApi {

   protected String criteria;

   /**
    * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
    * 
    * @param dbConn Database connection bean which is of type {@link DatabaseConnectionBean}
    * @throws SystemException
    * @throws DatabaseException
    */
   public InventoryApiImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException    {
			super(dbConn);
			this.className = "AcctManagerApiImpl";
			this.baseView = "GlAccountsView";
			this.baseBeanClass = "com.bean.GlAccounts";
   }

   /**
    * Constructor begins the initialization of the DatabaseConnectionBean and the HttpServletRequest object at the acestor level.
    * 
    * @param dbConn Database connection bean which is of type {@link DatabaseConnectionBean}
    * @param req HttpServletRequest object.
    * @throws SystemException
    * @throws DatabaseException
    */
   public InventoryApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest req) throws SystemException, DatabaseException    {
      super(dbConn, req);
   }

    /**
     *  Locates an Item from the item_master table.
     */
   public ItemMaster findItemById(int value) throws ItemMasterException {
       String method = "findItemById";
       this.setBaseClass("com.bean.ItemMaster");
       this.setBaseView("ItemMasterView");
       this.criteria = "id = " + value;
       try {
           List list = this.find(this.criteria);
           if (list.size() == 0) {
               return null;
           }
           return (ItemMaster) list.get(0);
       }
       catch (IndexOutOfBoundsException e) {
           return null;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }


   /**
    *  Locates an Item from the vw_item_master view
    *  
    * @param _itemId is the item master id.
    * @return item data
    * @throws ItemMasterException
    */
   public VwItemMaster findItemViewById(int _itemId) throws ItemMasterException {
       String method = "findItemById";
       this.setBaseClass("com.bean.VwItemMaster");
       this.setBaseView("VwItemMasterView");
       this.criteria = "id = " + _itemId;
       try {
           List list = this.find(this.criteria);
           if (list.size() == 0) {
               return null;
           }
           return (VwItemMaster) list.get(0);
       }
       catch (IndexOutOfBoundsException e) {
           return null;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   /**
    * Locates an Item from the item_master table using vendor id.
    */
   public List findItemByVendorId(int value) throws ItemMasterException {
       String method = "findItemByVendorId";
       this.setBaseClass("com.bean.ItemMaster");
       this.setBaseView("ItemMasterView");
       this.criteria = "vendor_id = " + value;
       try {
           List list = this.find(this.criteria);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }



   /**
    * Locates an Item from the item_master table using item type id.
    */
   public  List findItemByType(int _itemTypeId, String orderBy) throws ItemMasterException {
       String method = "findItemByType";
       this.setBaseClass("com.bean.ItemMaster");
       this.setBaseView("ItemMasterView");
       this.criteria = "item_type_id = " + _itemTypeId;
       try {
           List list = this.find(this.criteria, orderBy);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }       
   }


   /**
    * Locates an Item from the item_master table using vendor iitem number.
    */
   public List  findItemByVendorItemNo(String value) throws ItemMasterException {
       String method = "findItemByVendorItemNo";
       this.setBaseClass("com.bean.ItemMaster");
       this.setBaseView("ItemMasterView");
       this.criteria = "vendor_item_no like '" + value + "%' ";
       try {
           List list = this.find(this.criteria);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   /**
    * Locates an Item from the item_master table using item serial number.
    */
   public List findItemBySerialNo(String value) throws ItemMasterException {
       String method = "findItemBySerialNo";
       this.setBaseClass("com.bean.ItemMaster");
       this.setBaseView("ItemMasterView");
       this.criteria = "item_serial_no like '" + value + "%' ";
       try {
           List list = this.find(this.criteria);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   /**
    * Retrieves an ArrayList of of any inventory related object based on the base view, base class, and custom 
    * criteria  supplied by the user.  User is responsible for setting the base view and class so that the API will 
    * know what data to retrieve.
    * 
    */
   public List findItem(String criteria, String orderBy) throws ItemMasterException {
       String method = "findItem";
       this.criteria = criteria;
       try {
           List list = this.find(this.criteria, orderBy);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   
   public ItemMasterType findItemTypeById(int _id) throws ItemMasterException {
       String method = "findItemTypeById";
       this.setBaseClass("com.bean.ItemMasterType");
       this.setBaseView("ItemMasterTypeView");
       this.criteria = "id = " + _id;
       try {
           List list = this.find(this.criteria);
           if (list.size() == 0) {
               return null;
           }
           return (ItemMasterType) list.get(0);
       }
       catch (IndexOutOfBoundsException e) {
           return null;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }
   
   
   public List findItemTypes(String criteria, String orderBy) throws ItemMasterException {
       String method = "findItemTypes";
       this.setBaseClass("com.bean.ItemMasterType");
       this.setBaseView("ItemMasterTypeView");
       this.criteria = criteria;
       try {
           List list = this.find(this.criteria, orderBy);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }
   

   /**
    * Locates one or more Item Status types from the item_master_status table using selection criteria
    * which is built and supplied by the client.   This method expects that the  selection criteria is
    *  syntactically correct.
    */
   public List findItemStatus(String criteria) throws ItemMasterException {
       String method = "findItemStatus";
       this.setBaseClass("com.bean.ItemMasterStatus");
       this.setBaseView("ItemMasterStatusView");
       this.criteria = criteria;
       try {
           List list = this.find(this.criteria);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }
   
   /**
    * Locates Item master status object by primary key.
    * 
    * @param _itemStatusId
    * @return
    * @throws ItemMasterException
    */
   public ItemMasterStatus findItemStatusById(int _itemStatusId) throws ItemMasterException {
       String method = "findItemStatusById";
       this.setBaseClass("com.bean.ItemMasterStatus");
       this.setBaseView("ItemMasterStatusView");
       this.criteria = "id = " + _itemStatusId;
       try {
           List list = this.find(this.criteria);
           if (list.size() == 0) {
               return null;
           }
           return (ItemMasterStatus) list.get(0);
       }
       catch (IndexOutOfBoundsException e) {
           return null;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   /**
    * Locates one or more ItemMasterStatusHist Objects types from the  item_master_status_hist table using Item's id as "_id".
    */
   public List findItemStatusHistByItemId(int _id) throws ItemMasterException {
       String method = "findItemStatusHistByItemId";
       this.setBaseClass("com.bean.ItemMasterStatusHist");
       this.setBaseView("ItemMasterStatusHistView");
       this.criteria = "item_master_id = " + _id;
       try {
           List list = this.find(this.criteria);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   /**
    * Locates the current status of an item based on the item's id (_itemId).
    */
   public ItemMasterStatusHist findCurrentItemStatusHist(int _itemId) throws ItemMasterException {
       String method = "findCurrentItemStatusHist";
       this.setBaseClass("com.bean.ItemMasterStatusHist");
       this.setBaseView("ItemMasterStatusHistView");
       this.criteria = "item_master_id = " + _itemId + " and end_date is null";
       try {
           List list = this.find(this.criteria);
           if (list.size() == 0) {
               return null;
           }
           return (ItemMasterStatusHist) list.get(0);
       }
       catch (IndexOutOfBoundsException e) {
           return null;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   public VendorItems findVendorItem(int vendorId, int itemId) throws ItemMasterException {
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
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }
   
   
   public List findVendorAssignItems(int vendorId) throws ItemMasterException {
	   String method = "findVendorUnassignItems";
	   this.setBaseClass("com.bean.VwVendorItems");
       this.setBaseView("VwVendorItemsView");
       this.criteria = " vendor_id = " + vendorId;
       try {
           List list = this.find(this.criteria);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }
   
   public List findVendorUnassignItems(int vendorId) throws ItemMasterException {
	   String method = "findVendorUnassignItems";
	   this.setBaseClass("com.bean.ItemMaster");
       this.setBaseView("ItemMasterView");
       this.criteria = " id not in ( select item_master_id from vendor_items where vendor_id = " + vendorId + ") and item_type_id = " + ItemConst.ITEM_TYPE_MERCH;   
       try {
           List list = this.find(this.criteria);
           return list;
       }
       catch (SystemException e) {
           throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }
   
   public int maintainItemMaster(ItemMaster _itemBase, Object _itemExt)  throws  ItemMasterException {
	   if (_itemBase.getId() <= 0) {
		   this.insertItemMaster(_itemBase, _itemExt);
	   }
	   else {
		   this.updateItemMaster(_itemBase, _itemExt);
	   }
	   return RMT2Base.SUCCESS;
   }

  
   /**
    * Adds an Item Master to the database.    If Item Master Extension object is not null, an attempt will be
    * made to append the extension data to the base.   Upon successful completion,  the caller can
    * interrogate _base, _itemBase, and _itemExt objects for property values that were updated by
    * this method such as: gl account id, item Master id, item extension id, gl account number.
    * 
    * @param _itemBase Item Master object
    * @param _itemExt Item Master Extension object
    * @return The id of the new item.
    * @throws ItemMasterException
    */
   protected int insertItemMaster(ItemMaster _itemBase, Object _itemExt) throws ItemMasterException   {
       DaoApi dso = null;
	   UserTimestamp ut = null;
	   int newId;
	   int itemStatusId = 0;

       // Perform any client-side validations.
	   this.validateItemMaster(_itemBase);   
         
	   // calculate retail price
	   this.computeItemRetail(_itemBase);
	   
	   dso = DataSourceFactory.createDao(this.connector);
       try {
    	   ut = RMT2Utility.getUserTimeStamp(this.request);
    	   _itemBase.setId(0);
    	   _itemBase.setActive(ItemConst.ITEM_ACTIVE_YES);
    	   _itemBase.setDateCreated(ut.getDateCreated());
    	   _itemBase.setDateUpdated(ut.getDateCreated());
    	   _itemBase.setUserId(ut.getLoginId());
    	   newId = dso.insertRow(_itemBase, true);
    	   _itemBase.setId(newId);
    
    	   //   Add Item Master Extension, if applicable
    	   this.insertItemMasterExt(_itemBase,  _itemExt);
    	   
    	   // Indicate that item is in service
    	   this.changeItemStatus(_itemBase, ItemConst.ITEM_STATUS_INSRVC);
    	   
    	   // Determine Item has quantity for sale or is out of stock
    	   itemStatusId = _itemBase.getQtyOnHand() <= 0 ? ItemConst.ITEM_STATUS_OUTSTOCK : ItemConst.ITEM_STATUS_AVAIL;
    	   this.changeItemStatus(_itemBase, itemStatusId);
    	   
    	   return newId;
       }
       catch (DatabaseException e) {
    	   	throw new ItemMasterException(e.getMessage());
       }
       catch (SystemException e) {
			 throw new ItemMasterException(e.getMessage());
       }
   	} 

   /**
    * Stub method which serves to insert custom item master data into the database.  
    * 
    * @param _itemBase Base item master object
    * @param _itemExt Item master extension object.  Generally this object is null unless implemented by the developer.
    * @return 0
    * @throws ItemMasterException
    */
   protected int insertItemMasterExt(ItemMaster _itemBase, Object _itemExt) throws ItemMasterException   {
	   	return 0;
   }


   /**
    * Updates the base Item Master and the  Item Master Extension object.
    * 
    * @param _itemBase Item master base object.
    * @param _itemExt Item Master Extension
    * @return 1 for sucess
    * @throws ItemMasterException
    */
   protected int updateItemMaster(ItemMaster _itemBase, Object _itemExt) throws ItemMasterException   {
       String method = "updateItemMaster";
       DaoApi dso = null;
       ItemMasterStatusHist imsh = null;
	   UserTimestamp ut = null;
       ItemMaster oldIm = null;
       int newQty = 0;
	   int itemStatusId = 0;

	   //  Perform any client-side validations.
	   this.validateItemMaster(_itemBase);
	   // calculate retail price
	   this.computeItemRetail(_itemBase);
       
       // Get original copy of item 
       oldIm = this.findItemById(_itemBase.getId());
       
       // Calculate New Quantity On Hand
       newQty = oldIm.getQtyOnHand() + _itemBase.getQtyOnHand();
       
	   dso = DataSourceFactory.createDao(this.connector);
       try {
    	   ut = RMT2Utility.getUserTimeStamp(this.request);
           _itemBase.setQtyOnHand(newQty);
    	   _itemBase.setDateUpdated(ut.getDateCreated());
    	   _itemBase.setUserId(ut.getLoginId());
    	   _itemBase.addCriteria("Id", _itemBase.getId());
    	   dso.updateRow(_itemBase);
    
    	   //   Update Item Master Extension, if applicable
    	   this.updateItemMasterExt(_itemExt);
    	   
    	   imsh = this.findCurrentItemStatusHist(_itemBase.getId());
    	   if (imsh == null) {
    		   throw new ItemMasterException("Problem obtaining inventory item's status history.   History not available.");
    	   }
    	   
    	   // Change the most recent item status, which should be 'Replaced'
    	   imsh = this.changeItemStatus(_itemBase, ItemConst.ITEM_STATUS_REPLACE);

    	   // User has requested system to activate vendor item override.
    	   if (_itemBase.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_YES && oldIm.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_NO) {
    		   imsh = this.changeItemStatus(_itemBase, ItemConst.ITEM_STATUS_OVERRIDE_ACTIVE);
    	   }
    	   
           // User has requested system to deactivate vendor item override.
    	   if (_itemBase.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_NO && oldIm.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_YES) {
    		   imsh = this.changeItemStatus(_itemBase, ItemConst.ITEM_STATUS_OVERRIDE_INACTIVE);
    	   }
    	   
    	   // Place Item in "Available" if quantity is greater than zero.   Otherwise, 'Out of Stock'.
  		   itemStatusId = _itemBase.getQtyOnHand() <= 0 ? ItemConst.ITEM_STATUS_OUTSTOCK : ItemConst.ITEM_STATUS_AVAIL;
    	   imsh = this.changeItemStatus(_itemBase, itemStatusId);
    	   
    	   // If item is no longer active, then put in out servive status 
    	   if (_itemBase.getActive() == 0) {
    		   imsh = this.changeItemStatus(_itemBase, ItemConst.ITEM_STATUS_OUTSRVC);
    	   }
    	   return RMT2Base.SUCCESS;
       }
       catch (DatabaseException e) {
    	   throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
       catch (SystemException e) {
    	   throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
       }
   }

   

   /**
    * Stb method which serves as a place holder for updating a custom item master extension object.
    * 
    * @param _itemExt Item master extension object which the type is unknown until implemented.
    * @return 0
    * @throws ItemMasterException
    */
   protected int updateItemMasterExt(Object _itemExt) throws ItemMasterException   {
      return 0;
   }
  
   /**
    * Increases the count of an item in inventory.
    */
   public double pushInventory(int _itemId, int _qty) throws ItemMasterException {
	   DaoApi dso = null;
	   UserTimestamp ut = null;
	   double changeValue = 0;
	   int itemQty = 0;
	   ItemMaster im = null;
	   
	   dso = DataSourceFactory.createDao(this.connector);
	   try {
		   im = this.findItemById(_itemId);
		   itemQty = im.getQtyOnHand() + _qty;
		   changeValue = im.getUnitCost() * _qty;
		   im.setQtyOnHand(itemQty);
		   ut = RMT2Utility.getUserTimeStamp(this.request);	
		   im.setDateUpdated(ut.getDateCreated());
		   im.setUserId(ut.getLoginId());
		   im.addCriteria("Id", im.getId());
		   dso.updateRow(im);
		   return changeValue;
	   }
		catch (DatabaseException e) {
			throw new ItemMasterException(this.msg);
		}
		catch (SystemException e) {
			throw new ItemMasterException(this.msg);
		}
   }
   
   /**
    * Decreases the count of an item in inventory.
    */
   public double pullInventory(int _itemId, int _qty) throws ItemMasterException {
	   DaoApi dso = null;
	   UserTimestamp ut = null;
	   double changeValue = 0;
	   int itemQty = 0;
	   ItemMaster im = null;
	   
	   
	   dso = DataSourceFactory.createDao(this.connector);
	   try {
		   im = this.findItemById(_itemId);
		   itemQty = im.getQtyOnHand() - _qty;
		   changeValue = im.getUnitCost() * _qty;
		   im.setQtyOnHand(itemQty);
		   ut = RMT2Utility.getUserTimeStamp(this.request);	
		   im.setDateUpdated(ut.getDateCreated());
		   im.setUserId(ut.getLoginId());
		   im.addCriteria("Id", im.getId());
		   dso.updateRow(im);
		   
		   // If required, change item status
		   if (itemQty <= 0) {
			   this.changeItemStatus(im, ItemConst.ITEM_STATUS_OUTSTOCK);
		   }
		   return changeValue;
	   }
		catch (DatabaseException e) {
			throw new ItemMasterException(this.msg);
		}
		catch (SystemException e) {
			throw new ItemMasterException(this.msg);
		}
   }
   
   
   /**
    * Changes the status of an inventory item.
    *    
    * @param _im The item master object targeted for the satus change.
    * @param _newItemStatusId The id of the item status.
    * @return The {@link ItemMasterStatusHist} object which represents _newItemStatusId
    * @throws ItemMasterException If _newItemStatusId is out of sequence, if a database error occurs, or a system error occurs.
    */
   private ItemMasterStatusHist changeItemStatus(ItemMaster _im, int _newItemStatusId)  throws ItemMasterException {
		  DaoApi dso = DataSourceFactory.createDao(this.connector);
		  UserTimestamp ut = null;
		  ItemMasterStatusHist imsh = null;
		  ItemMasterStatus ims = null;
		  
		  ims = this.findItemStatusById(_newItemStatusId);
		  if (ims == null) {
			  throw new ItemMasterException("Problem changing Item status, because new item status id is invalid");
		  }
		  
		  try {
			  ut = RMT2Utility.getUserTimeStamp(this.request);
			  
			  // End current item status
			  imsh = this.findCurrentItemStatusHist(_im.getId());
			  if (imsh != null) {
				  imsh.setEndDate(ut.getDateCreated());
				  imsh.setUserId(ut.getLoginId());
				  imsh.addCriteria("Id", imsh.getId());
				  dso.updateRow(imsh);
			  }
			  
			  // Create new item status
			  imsh = InventoryFactory.createItemMasterStatusHist();
			  imsh.setItemMasterId(_im.getId());
			  imsh.setItemStatusId(_newItemStatusId);
			  imsh.setUnitCost(_im.getUnitCost());
			  imsh.setMarkup(_im.getMarkup());
			  imsh.setEffectiveDate(ut.getDateCreated());
			  imsh.setDateCreated(ut.getDateCreated());
			  imsh.setUserId(ut.getLoginId());
			  dso.insertRow(imsh, true);
			  return imsh;
		  }
		  catch (DatabaseException e) {
			  throw new ItemMasterException(e.getMessage());
		  }
		  catch (SystemException e) {
			  throw new ItemMasterException(e.getMessage());
		  }
   }
   
   
   /**
    * Removes ainventory item from the database.
    */
    public int deleteItemMaster(int _itemId) throws ItemMasterException {
        List items = null;
		String whereSql = null;
		DaoApi dso = DataSourceFactory.createDao(this.connector);
	    ItemMasterStatusHist imsh = null;
	    ItemMaster im = null;
		
		try {
			// Determine if item is tied to one or more sales orders.
			whereSql = "item_master_id = " + _itemId;
			this.setBaseClass("com.bean.SalesOrderItems");
		    this.setBaseView("SalesOrderItemsView");
			items = this.findItem(whereSql, null);
			if (items.size() > 0) {
				throw new ItemMasterException("Item, " + _itemId + ", cannot be deleted since it is associated with one or more sales orders");
			}
			
			// Remove all items from item master status history table
			imsh = InventoryFactory.createItemMasterStatusHist();
			imsh.addCriteria("ItemMasterId", _itemId);
			dso.deleteRow(imsh);
			
			// Remove item from inventory.
			im = InventoryFactory.createItemMaster();
			im.setId(_itemId);
			im.addCriteria("Id", im.getId());
			dso.deleteRow(im);
		    return RMT2Base.SUCCESS;
		}
		catch (DatabaseException e) {
		    throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
		}
		catch (SystemException e) {
		    throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
		}
    }


    /**
     * Deactivates an inventory item.
     */
    public int deactivateItemMaster(int _itemId) throws ItemMasterException {
    	String method = "deactivateItemMaster";
		DaoApi dso = DataSourceFactory.createDao(this.connector);
	    ItemMaster im = null;
		
		try {
			// Retreive Item
			im = this.findItemById(_itemId);
			if (im == null) {
				throw new ItemMasterException("Item, " + _itemId + ", cannot be deactivated since it does not exist");
			}
			
			// Remove all items from item master status history table
			this.changeItemStatus(im, ItemConst.ITEM_STATUS_OUTSRVC);
			
			// Flag item master as inacitve
			im.setActive(ItemConst.ITEM_ACTIVE_NO);
			im.addCriteria("Id", im.getId());
			dso.updateRow(im);
		    return RMT2Base.SUCCESS;
		}
		catch (DatabaseException e) {
		    throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
		}
    }
    
    /**
     * Activates an inventory item.
     */
    public int activateItemMaster(int _itemId) throws ItemMasterException {
    	String method = "deactivateItemMaster";
		int itemStatusId = 0;
		DaoApi dso = DataSourceFactory.createDao(this.connector);
	    ItemMaster im = null;
		
		try {
			// Retreive Item
			im = this.findItemById(_itemId);
			if (im == null) {
				throw new ItemMasterException("Item, " + _itemId + ", cannot be activated since it does not exist");
			}
			
			// Remove all items from item master status history table
			this.changeItemStatus(im, ItemConst.ITEM_STATUS_INSRVC);
			
			// Determine if item has quantity to sold or is out of stock.
			itemStatusId = im.getQtyOnHand() <= 0 ? ItemConst.ITEM_STATUS_OUTSTOCK : ItemConst.ITEM_STATUS_AVAIL;
			this.changeItemStatus(im, itemStatusId);
			
			// Flag item master as acitve
			im.setActive(ItemConst.ITEM_ACTIVE_YES);
			im.addCriteria("Id", im.getId());
			dso.updateRow(im);
		    return RMT2Base.SUCCESS;
		}
		catch (DatabaseException e) {
		    throw new ItemMasterException(e.getMessage(), e.getErrorCode(), this.className, method);
		}
    }

    
    /**
     * Validates base transaction, _itemBase.  
     * 
     * The following validations must be met:
     * <ul>
     *    <li>The vendor id is not required, but if its id is greateer than zero, then it must exist in the database.</li>
     *    <li>Item Description must no be null</li>
     *    <li>Item Type must be valid</li>
     *    <li>Item Markup must be greater than zero</li>
     *    <li>Service item types must have a quantity on hand equal to one, and a mark value equal to one.</li>
     * </ul>
     * 
     * @param _itemBase
     * @throws ItemMasterException
     */
   protected void validateItemMaster(ItemMaster _itemBase) throws ItemMasterException   {
       String msg = null;
       GLCreditorApi credApi = null;

       try {
           credApi = AcctManagerFactory.createCreditor(this.connector);
           if (_itemBase.getVendorId() > 0) {
        	   Object cred = credApi.findCreditorById(_itemBase.getVendorId());
        	   if (cred == null) {
        		   throw new ItemMasterException("Vendor does not exist: " + _itemBase.getVendorId());
        		   //throw new ItemMasterException(this.dbo, 426, null);
        	   }
           }
           else {
        	   _itemBase.setNull("vendorId");
           }    	   
       }
       catch (GLAcctException e) {
    	   throw new ItemMasterException(e);
       }
       catch (DatabaseException e) {
    	   throw new ItemMasterException(e);
       }
       catch (SystemException e) {
    	   throw new ItemMasterException(e);
       }
       
       // Common validations
       if (_itemBase.getDescription() == null || _itemBase.getDescription().equals("")) {
           msg = "Item Name must contain a value and cannot be null";
           System.out.println(msg);
           throw new ItemMasterException(this.connector, 426, null);
       }
       
       if (_itemBase.getItemTypeId() <= 0) {
           msg = "Item Type is invalid";
           System.out.println(msg);
           throw new ItemMasterException(this.connector, 427, null);
       }
        //  Mark Up must be greater than zero and cannot be null
        if (_itemBase.getMarkup() <= 0)  {
             throw new ItemMasterException(this.connector, 430, null);
        }

        /*  Not sure if unit cost should be required to have a value greater than zero.
       if (_itemBase.getUnitCost() <= 0)  {
           throw new ItemMasterException(this.dbo, 419, null);
         }
       */
        
         // Service item validations
         if (_itemBase.getItemTypeId() == ItemConst.ITEM_TYPE_SRVC) {
             // Quantity on Hand must be equal to 1 for service items
             if (_itemBase.getQtyOnHand() != 1)  {
                 throw new ItemMasterException(this.connector, 429, null);
             }
             // Mark Up must be equal to 1
             if (_itemBase.getMarkup() != 1)  {
                 throw new ItemMasterException(this.connector, 428, null);
             }
         }
		 return;
   }
   
   
   public int maintainVendorItem(VendorItems _vi) throws ItemMasterException {
	   this.updateVendorItem(_vi);
	   return RMT2Base.SUCCESS;
   }
   
   /**
    * Apply changes to _vi to the database.
    * 
    * @param _vi {@link VendorItems} object to be updated
    * @return Total number of rows effected by database transaction
    * @throws ItemMasterException
    */
   protected int updateVendorItem(VendorItems _vi) throws ItemMasterException {
	   int rc = 0;
	   DaoApi dso = DataSourceFactory.createDao(this.connector);
	   this.validateVendorItem(_vi);
	   try {
           _vi.addCriteria("VendorId", _vi.getVendorId());
           _vi.addCriteria("ItemMasterId", _vi.getItemMasterId());
		   rc = dso.updateRow(_vi);   
		   return rc;
	   }
	   catch (DatabaseException e) {
		   throw new ItemMasterException(e);
	   }
   }
   
   
   /**
    * Validate a vendor item object.
    * 
    * The following validations must be met:
     * <ul>
     *    <li>The vendor id is required and must exist in the database.</li>
     *    <li>The Vendor Item Number cannot be null</li>
     *    <li>The Item Serial Number cannot be null</li>
     * </ul>
     * 
    * @param _vi The {@link VendorItems} object
    * @throws ItemMasterException
    */
   protected void validateVendorItem(VendorItems _vi) throws ItemMasterException {
	   GLCreditorApi credApi = null;
	   
       try {
           credApi = AcctManagerFactory.createCreditor(this.connector);
           if (_vi.getVendorId() > 0) {
        	   Object cred = credApi.findCreditorById(_vi.getVendorId());
        	   if (cred == null) {
        		   throw new ItemMasterException("Vendor does not exist: " + _vi.getVendorId());
        		   //throw new ItemMasterException(this.dbo, 426, null);
        	   }
           }
           else {
        	   throw new ItemMasterException("Vendor Id is invalid: " + _vi.getVendorId());
           }    	   
       }
       catch (GLAcctException e) {
    	   throw new ItemMasterException(e);
       }
       catch (DatabaseException e) {
    	   throw new ItemMasterException(e);
       }
       catch (SystemException e) {
    	   throw new ItemMasterException(e);
       }
       if (_vi.getVendorItemNo() == null || _vi.getVendorItemNo().length() <= 0)  {
    	   throw new ItemMasterException(this.connector, 424, null);
       }
       if (_vi.getItemSerialNo() == null || _vi.getItemSerialNo().length() <= 0)  {
    	   throw new ItemMasterException(this.connector, 418, null);
       }
   }   

   
   /**
    * Calculates the retail price of an item based on its item type value (Merchandise or Service).  At this point all data components 
    * used for calculating retail should have been verified.   The retail price will be determined in one of two ways:   1) as is when the user
    * request that retail price calculations be ignored and just accept the user's input  or 2) apply the formula retail price = unit cost * mark up.
    * 
    * @param _itemBase
    * @throws ItemMasterException
    */
   private void computeItemRetail(ItemMaster _itemBase) throws ItemMasterException {
       double retailPrice = 0;

       // Determine if user requests us to override retail calculations.
       if (_itemBase.getOverrideRetail() == 0) {
           retailPrice =  _itemBase.getUnitCost() * _itemBase.getMarkup();
           _itemBase.setRetailPrice(retailPrice);    
       }
       return;
   }


   /**
    * Cycles through the list of inventory item id's contained in _items and assigns each one to a vendor identified as _vendorId.
    * 
    * @param _vendorId The id of the vendor
    * @param _items A list inventory item id's
    * @return The number of items assigned to the vendor.
    * @throws ItemMasterException
    */
   public int assignVendorItems(int _vendorId, int _items[]) throws ItemMasterException {
	   int rc = 0;
	   ItemMaster im = null;
	   VendorItems vi = null;
	   DaoApi dso = DataSourceFactory.createDao(this.connector);
	   
	   for (int ndx = 0; ndx < _items.length; ndx++) {
		   im = this.findItemById(_items[ndx]);
		   if (im == null) {
			   this.msg = "The following item id is not found in the database and the assignment of items to vendor is aborted: " + _items[ndx];
			   throw new ItemMasterException(this.msg);
		   }
		   vi = InventoryFactory.createVendorItem(_vendorId, _items[ndx], im.getItemSerialNo(), im.getVendorItemNo(), im.getUnitCost());
		   try {
			   rc += dso.insertRow(vi, false);   
		   }
		   catch (DatabaseException e) {
			   throw new ItemMasterException("Problem adding item to vendor_items table: " + e.getMessage());
		   }
	   }
	   return rc;
   }
   
   
   /**
    * This method disassociates all items contained in _items from a vendor.
    * 
    * @param _vendorId The id of the vendor
    * @param _items A list inventory item id's
    * @return The number of items unassigned from the vendor.
    * @throws ItemMasterException
    */
   public int removeVendorItems(int _vendorId, int _items[]) throws ItemMasterException {
	   int rc = 0;
	   ItemMaster im = null;
	   VendorItems vi = null;
	   DaoApi dso = DataSourceFactory.createDao(this.connector);
	   
	   for (int ndx = 0; ndx < _items.length; ndx++) {
		   im = this.findItemById(_items[ndx]);
		   if (im == null) {
			   this.msg = "The following item id is not found in the database and the assignment of items to vendor is aborted: " + _items[ndx];
			   throw new ItemMasterException(this.msg);
		   }
		   vi = InventoryFactory.createVendorItem();
		   vi.addCriteria("VendorId", _vendorId);
		   vi.addCriteria("ItemMasterId", _items[ndx]);
		   try {
			   rc += dso.deleteRow(vi);   
		   }
		   catch (DatabaseException e) {
			   throw new ItemMasterException("Problem adding item to vendor_items table: " + e.getMessage());
		   }
	   }
	   return rc;
   }
   
   public int addInventoryOverride(int _vendorId, int _itemId) throws ItemMasterException {
	   ItemMaster im = null;
	   
	   im = this.findItemById(_itemId);
	   if (im == null) {
		   this.msg = "The following item id is not found in the database and the vendor item inventory override activation is aborted: " + _itemId;
		   throw new ItemMasterException(this.msg);
	   }
	   // Do not attempt to update an item that is currently overriden.
	   if (im.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_YES) {
		   return 0;
	   }
	   im.setVendorId(_vendorId);
	   im.setOverrideRetail(ItemConst.ITEM_OVERRIDE_YES);
	   // Ensure that item's quantity on hand is not effected after updates are applied to the database.
	   im.setQtyOnHand(0);
	   this.maintainItemMaster(im, null);
	   return RMT2Base.SUCCESS;
   }
   
   public int addInventoryOverride(int _vendorId, int _items[]) throws ItemMasterException {
	   int rc = 0;
	   
	   for (int ndx = 0; ndx < _items.length; ndx++) {
		   rc += this.addInventoryOverride(_vendorId, _items[ndx]);
	   }
	   return rc;
   }
   
   
   public int removeInventoryOverride(int _vendorId, int _itemId) throws ItemMasterException {
	   ItemMaster im = null;
	   
	   im = this.findItemById(_itemId);
	   if (im == null) {
		   this.msg = "The following item id is not found in the database and the vendor item inventory override deactivation is aborted: " + _itemId;
		   throw new ItemMasterException(this.msg);
	   }
	   // Do not attempt to update an item that is currently not overriden.
	   if (im.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_NO) {
		   return 0;
	   }
	   im.setNull("vendorId");
	   im.setOverrideRetail(ItemConst.ITEM_OVERRIDE_NO);
	   // Ensure that item's quantity on hand is not effected after updates are applied to the database.
	   im.setQtyOnHand(0);
	   this.maintainItemMaster(im, null);
	   return RMT2Base.SUCCESS;
   }
   
   public int removeInventoryOverride(int _vendorId, int _items[]) throws ItemMasterException {
	   int rc = 0;
	   
	   for (int ndx = 0; ndx < _items.length; ndx++) {
		   rc += this.removeInventoryOverride(_vendorId, _items[ndx]);
	   }
	   return rc;
   }
   
   
}


package com.inventory;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.ItemMaster;
import com.bean.ItemMasterStatus;
import com.bean.ItemMasterStatusHist;
import com.bean.ItemMasterType;
import com.bean.VendorItems;
import com.bean.VwItemAssociations;
import com.bean.VwItemMaster;
import com.bean.VwVendorItems;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

/**
 * @author RTerrell
 *
 */
class InventoryXmlImpl extends RdbmsDaoImpl implements InventoryApi {
    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * @param dbConn
     * @throws SystemException
     * @throws DatabaseException
     */
    public InventoryXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param dbConn
     * @param req
     * @throws SystemException
     * @throws DatabaseException
     */
    public InventoryXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	// TODO Auto-generated constructor stub
    }

    /**
     * Creates the DAO helper and the logger.
     */
    protected void initApi() throws DatabaseException, SystemException {
	this.logger = Logger.getLogger("InventoryApiImpl");
	this.logger.log(Level.DEBUG, "logger setup");
    }

    /**
     * Retrieves an Item from the item_master table.
     * 
     * @param itemId The id of the item
     * @return {@link com.bean.ItemMaster ItemMaster} 
     * @throws ItemMasterException
     */
    public String findItemById(int itemId) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createXmlItemMaster();
	obj.addCriteria(ItemMaster.PROP_ITEMID, itemId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     *  Locates an Item from the vw_item_master view
     *  
     * @param itemId is the item master id.
     * @return {@link com.bean.VwItemMaster VwItemMaster}
     * @throws ItemMasterException
     */
    public String findItemViewById(int itemId) throws ItemMasterException {
	VwItemMaster obj = InventoryFactory.createXmlItemMasterView();
	obj.addCriteria(ItemMaster.PROP_ITEMID, itemId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves an Item from the item_master table using vendor id.
     * <p>
     * The result set is ordered by item description.
     * 
     * @param vendorId The id of Vendor
     * @return A List of {@link com.bean.ItemMaster ItemMaster} objects.
     * @throws ItemMasterException
     */
    public String findItemByVendorId(int vendorId) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createXmlItemMaster();
	obj.addCriteria(ItemMaster.PROP_CREDITORID, vendorId);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves one or more Items from the item_master table using item type id.
     * <p>
     * The result set is ordered by item description.
     * 
     * @param itemTypeId The item type id
     * @return A List of {@link com.bean.ItemMaster ItemMaster} objects.
     * @throws ItemMasterException
     */
    public String findItemByType(int itemTypeId) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createXmlItemMaster();
	obj.addCriteria(ItemMaster.PROP_ITEMTYPEID, itemTypeId);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves one or more Items from the item_master table using vendor iitem number.
     * <p>
     * The result set is ordered by item description.
     * 
     * @param vendItemNo The vendor's verison of the item number.
     * @return A List of {@link com.bean.ItemMaster ItemMaster} objects.
     * @throws ItemMasterException
     */
    public String findItemByVendorItemNo(String vendItemNo) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createXmlItemMaster();
	obj.addCriteria(ItemMaster.PROP_VENDORITEMNO, vendItemNo);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves one or more items from the item_master table using item serial number.
     * <p>
     * The result set is ordered by item description.
     * 
     * @param serialNo 
     *          The serial number of the item.  Its value can be partial which will result 
     *          in an incremental search based on "begins with" selection criteria.
     * @return A List of {@link com.bean.ItemMaster ItemMaster} objects.
     * @throws ItemMasterException
     */
    public String findItemBySerialNo(String serialNo) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createXmlItemMaster();
	obj.addCriteria(ItemMaster.PROP_ITEMSERIALNO, serialNo);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves an ArrayList of of any inventory related object based on the base view, 
     * base class, and custom criteria  supplied by the user.  User is responsible for 
     * setting the base view and class so that the API will know what data to retrieve.
     * <p>
     * The result set is ordered by item description.
     * 
     * @param criteria The selection criteria to apply to the query of data source.
     * @param orderBy The ordering to apply to the query of the data source.
     * @returnA List of {@link com.bean.ItemMaster ItemMaster} objects. 
     * @throws ItemMasterException
     */
    public String findItem(String criteria) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createXmlItemMaster();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }
    
    
    

    /**
     * Fetches information pertaining to sales orders and purchase orders 
     * which an item is assoicated.
     * 
     * @param itemId
     *          The id of the item to query.
     * @return
     *          An XML document resembling the format of class {@link com.bean.VwItemAssociations VwItemAssociations}.
     * @throws ItemMasterException
     *          if <i>itemId</i> is less than or equal to zero or for general database 
     *          access errors.
     */
    public String findItemAssociations(int itemId) throws ItemMasterException {
	if (itemId <= 0) {
	    throw new ItemMasterException("A valid item id must provided in order to perform an item assoication query");
	}
	VwItemAssociations obj = new VwItemAssociations();
	obj.addCriteria(VwItemAssociations.PROP_ITEMID, itemId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves Item Type data using item type id.
     * 
     * @param itemTypeId The id of the item type/
     * @return An {@link com.bean.ItemMasterType ItemMasterType}
     * @throws ItemMasterException
     */
    public String findItemTypeById(int itemTypeId) throws ItemMasterException {
	ItemMasterType obj = InventoryFactory.createXmlItemMasterType();
	obj.addCriteria(ItemMasterType.PROP_ITEMTYPEID, itemTypeId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieve one or more item type objects using custom selection criteria.
     * 
     * @param criteria Selection criteria used as part of the query's SQL where clause.
     * @return List of {@link com.bean.ItemMasterType ItemMasterType} objects. 
     * @throws ItemMasterException
     */
    public String findItemTypes(String criteria) throws ItemMasterException {
	ItemMasterType obj = InventoryFactory.createXmlItemMasterType();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(ItemMasterType.PROP_DESCRIPTION, ItemMasterType.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves one or more Item Status types from the item_master_status table using selection criteria
     * which is built and supplied by the client.   This method expects that the  selection criteria is
     *  syntactically correct.
     * 
     * @param criteria The custom selection criteria to be appended to the query's where clause.
     * @return List of {@link com.bean.ItemMasterStatus ItemMasterStatus}  objects representing one or more item statuses. 
     * @throws ItemMasterException
     */
    public String findItemStatus(String criteria) throws ItemMasterException {
	ItemMasterStatus obj = InventoryFactory.createXmlItemMasterStatus();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(ItemMasterStatus.PROP_DESCRIPTION, ItemMasterStatus.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves Item master status object by primary key.
     * 
     * @param itemStatusId The id of the item status
     * @return {@link com.bean.ItemMasterStatus ItemMasterStatus} object.
     * @throws ItemMasterException
     */
    public String findItemStatusById(int itemStatusId) throws ItemMasterException {
	ItemMasterStatus obj = InventoryFactory.createXmlItemMasterStatus();
	obj.addCriteria(ItemMasterStatus.PROP_ITEMSTATUSID, itemStatusId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves one or more ItemMasterStatusHist Objects types from the item_master_status_hist 
     * table using Item's id as <i>itemId</i>.
     * <p>
     * The result set is ordered in descending order by the create date.
     *  
     * @param id The id of the item to retrieve its statuses.
     * @return List of {@link com.bean.ItemMasterStatusHist ItemMasterStatusHist} objects 
     *         representing one or more item status history items. 
     * @throws ItemMasterException
     */
    public String findItemStatusHistByItemId(int itemId) throws ItemMasterException {
	ItemMasterStatusHist obj = InventoryFactory.createXmlItemMasterStatusHist();
	obj.addCriteria(ItemMasterStatusHist.PROP_ITEMID, itemId);
	obj.addOrderBy(ItemMasterStatus.PROP_DATECREATED, ItemMasterStatus.ORDERBY_DESCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves the current status of an item based on the item's id.
     * 
     * @param itemId The id of the item to retreive its current status.
     * @return An {@link com.bean.ItemMasterStatusHist ItemMasterStatusHist} object.
     * @throws ItemMasterException
     */
    public String findCurrentItemStatusHist(int itemId) throws ItemMasterException {
	ItemMasterStatusHist obj = InventoryFactory.createXmlItemMasterStatusHist();
	obj.addCriteria(ItemMasterStatusHist.PROP_ITEMID, itemId);
	obj.addCriteria(ItemMasterStatusHist.PROP_ENDDATE, ItemMasterStatusHist.DB_NULL);
	obj.addOrderBy(ItemMasterStatus.PROP_DESCRIPTION, ItemMasterStatus.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves one vendor item object using vendorId and itemId
     * 
     * @param vendorId The id of the vendor
     * @param itemId The id of the inventory item
     * @return An {@link com.bean.ItemMasterStatusHist ItemMasterStatusHist} object representing a vendor items item.
     * @throws ItemMasterException
     */
    public String findVendorItem(int vendorId, int itemId) throws ItemMasterException {
	VendorItems obj = InventoryFactory.createXmlVendorItem();
	obj.addCriteria(VendorItems.PROP_ITEMID, itemId);
	obj.addCriteria(VendorItems.PROP_CREDITORID, vendorId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves those inventory items that are assigned to a particular vendor from the database.
     * <p>
     * The result set is order in ascending order by item description.
     * 
     * @param vendorId The id of the target vendor.
     * @return List of {@link com.bean.VwVendorItemsView VwVendorItemsView} objects. 
     * @throws ItemMasterException
     */
    public String findVendorAssignItems(int vendorId) throws ItemMasterException {
	VwVendorItems obj = InventoryFactory.createXmlVwVendorItems();
	obj.addCriteria(VwVendorItems.PROP_CREDITORID, vendorId);
	obj.addOrderBy(VwVendorItems.PROP_DESCRIPTION, VwVendorItems.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Retrieves those inventory items that are not assigned to a particular vendor.
     * 
     * @param vendorId The id of the target vendor.
     * @return List of {@link com.bean.ItemMaster ItemMaster} objects
     * @throws ItemMasterException
     */
    public String findVendorUnassignItems(int vendorId) throws ItemMasterException {
	String criteria = " id not in ( select item_master_id from vendor_items where vendor_id = " + vendorId + ") and item_type_id = " + ItemConst.ITEM_TYPE_MERCH;
	return this.findItem(criteria);
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#activateItemMaster(int)
     */
    public int activateItemMaster(int itemId) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#addInventoryOverride(int, int)
     */
    public int addInventoryOverride(int vendorId, int itemId) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#addInventoryOverride(int, int[])
     */
    public int addInventoryOverride(int vendorId, int[] items) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#assignVendorItems(int, int[])
     */
    public int assignVendorItems(int vendorId, int[] items) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#deactivateItemMaster(int)
     */
    public int deactivateItemMaster(int itemId) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#deleteItemMaster(int)
     */
    public int deleteItemMaster(int id) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#maintainItemMaster(com.bean.ItemMaster, java.lang.Object)
     */
    public int maintainItemMaster(ItemMaster itemBase, Object itemExt) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#maintainVendorItem(com.bean.VendorItems)
     */
    public int maintainVendorItem(VendorItems vi) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#pullInventory(int, int)
     */
    public double pullInventory(int itemId, int qty) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#pushInventory(int, int)
     */
    public double pushInventory(int itemId, int qty) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#removeInventoryOverride(int, int)
     */
    public int removeInventoryOverride(int vendorId, int itemId) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#removeInventoryOverride(int, int[])
     */
    public int removeInventoryOverride(int vendorId, int[] items) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.inventory.InventoryApi#removeVendorItems(int, int[])
     */
    public int removeVendorItems(int vendorId, int[] items) throws ItemMasterException {
	// TODO Auto-generated method stub
	return 0;
    }

}

package com.inventory;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorFactory;

import com.util.RMT2Date;
import com.util.SystemException;

import com.bean.RMT2Base;
import com.bean.ItemMasterType;
import com.bean.ItemMaster;
import com.bean.ItemMasterStatus;
import com.bean.VwItemAssociations;
import com.bean.VwItemMaster;
import com.bean.VendorItems;
import com.bean.ItemMasterStatusHist;
import com.bean.VwVendorItems;

import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

/**
 * Api implementation of {@link InventoryApi} that manages inventory items.
 * 
 * @author RTerrell
 *
 */
public class InventoryApiImpl extends RdbmsDaoImpl implements InventoryApi {
    private static Logger logger = Logger.getLogger("InventoryApiImpl");

    private RdbmsDaoQueryHelper daoHelper;

    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn Database connection bean which is of type {@link DatabaseConnectionBean}
     * @throws SystemException
     * @throws DatabaseException
     */
    public InventoryApiImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and the HttpServletRequest object at the acestor level.
     * 
     * @param dbConn Database connection bean which is of type {@link DatabaseConnectionBean}
     * @param req THe User's requestobject.
     * @throws SystemException
     * @throws DatabaseException
     */
    public InventoryApiImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Creates the DAO helper and the logger.
     */
    protected void initApi() throws DatabaseException, SystemException {
	return;
    }

    /**
     * Retrieves an Item from the item_master table.
     * 
     * @param itemId The id of the item
     * @return {@link com.bean.ItemMaster ItemMaster} 
     * @throws ItemMasterException
     */
    public ItemMaster findItemById(int itemId) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createItemMaster();
	obj.addCriteria(ItemMaster.PROP_ITEMID, itemId);
	try {
	    return (ItemMaster) this.daoHelper.retrieveObject(obj);
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
    public VwItemMaster findItemViewById(int itemId) throws ItemMasterException {
	VwItemMaster obj = InventoryFactory.createItemMasterView();
	obj.addCriteria(VwItemMaster.PROP_ID, itemId);
	try {
	    return (VwItemMaster) this.daoHelper.retrieveObject(obj);
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
    public List findItemByVendorId(int vendorId) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createItemMaster();
	obj.addCriteria(ItemMaster.PROP_CREDITORID, vendorId);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findItemByType(int itemTypeId) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createItemMaster();
	obj.addCriteria(ItemMaster.PROP_ITEMTYPEID, itemTypeId);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findItemByVendorItemNo(String vendItemNo) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createItemMaster();
	obj.addCriteria(ItemMaster.PROP_VENDORITEMNO, vendItemNo);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findItemBySerialNo(String serialNo) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createItemMaster();
	obj.addCriteria(ItemMaster.PROP_ITEMSERIALNO, serialNo);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findItem(String criteria) throws ItemMasterException {
	ItemMaster obj = InventoryFactory.createItemMaster();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(ItemMaster.PROP_DESCRIPTION, ItemMaster.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
     *          An List of {@link com.bean.VwItemAssociations VwItemAssociations} objects
     * @throws ItemMasterException
     *          if <i>itemId</i> is less than or equal to zero or for general database 
     *          access errors.
     */
    public List findItemAssociations(int itemId) throws ItemMasterException {
	if (itemId <= 0) {
	    this.msg = "A valid item id must provided in order to perform an item assoication query";
	    logger.error(this.msg);
	    throw new ItemMasterException(this.msg);
	}
	VwItemAssociations obj = new VwItemAssociations();
	obj.addCriteria(VwItemAssociations.PROP_ITEMID, itemId);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public ItemMasterType findItemTypeById(int itemTypeId) throws ItemMasterException {
	ItemMasterType obj = InventoryFactory.createItemMasterType();
	obj.addCriteria(ItemMasterType.PROP_ITEMTYPEID, itemTypeId);
	try {
	    return (ItemMasterType) this.daoHelper.retrieveObject(obj);
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
    public List findItemTypes(String criteria) throws ItemMasterException {
	ItemMasterType obj = InventoryFactory.createItemMasterType();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(ItemMasterType.PROP_DESCRIPTION, ItemMasterType.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findItemStatus(String criteria) throws ItemMasterException {
	ItemMasterStatus obj = InventoryFactory.createItemMasterStatus();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(ItemMasterStatus.PROP_DESCRIPTION, ItemMasterStatus.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public ItemMasterStatus findItemStatusById(int itemStatusId) throws ItemMasterException {
	ItemMasterStatus obj = InventoryFactory.createItemMasterStatus();
	obj.addCriteria(ItemMasterStatus.PROP_ITEMSTATUSID, itemStatusId);
	try {
	    return (ItemMasterStatus) this.daoHelper.retrieveObject(obj);
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
    public List findItemStatusHistByItemId(int itemId) throws ItemMasterException {
	ItemMasterStatusHist obj = InventoryFactory.createItemMasterStatusHist();
	obj.addCriteria(ItemMasterStatusHist.PROP_ITEMSTATUSID, itemId);
	obj.addOrderBy(ItemMasterStatus.PROP_DATECREATED, ItemMasterStatus.ORDERBY_DESCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public ItemMasterStatusHist findCurrentItemStatusHist(int itemId) throws ItemMasterException {
	ItemMasterStatusHist obj = InventoryFactory.createItemMasterStatusHist();
	obj.addCriteria(ItemMasterStatusHist.PROP_ITEMID, itemId);
	obj.addCriteria(ItemMasterStatusHist.PROP_ENDDATE, ItemMasterStatusHist.DB_NULL);
	obj.addOrderBy(ItemMasterStatus.PROP_DESCRIPTION, ItemMasterStatus.ORDERBY_ASCENDING);
	try {
	    return (ItemMasterStatusHist) this.daoHelper.retrieveObject(obj);
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
    public VendorItems findVendorItem(int vendorId, int itemId) throws ItemMasterException {
	VendorItems obj = InventoryFactory.createVendorItem();
	obj.addCriteria(VendorItems.PROP_ITEMID, itemId);
	obj.addCriteria(VendorItems.PROP_CREDITORID, vendorId);
	try {
	    return (VendorItems) this.daoHelper.retrieveObject(obj);
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
    public List findVendorAssignItems(int vendorId) throws ItemMasterException {
	VwVendorItems obj = InventoryFactory.createVwVendorItems();
	obj.addCriteria(VwVendorItems.PROP_CREDITORID, vendorId);
	obj.addOrderBy(VwVendorItems.PROP_DESCRIPTION, VwVendorItems.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findVendorUnassignItems(int vendorId) throws ItemMasterException {
	String criteria = " item_id not in ( select item_id from vendor_items where creditor_id = " + vendorId + ") and item_type_id = " + ItemConst.ITEM_TYPE_MERCH;
	return this.findItem(criteria);
    }

    /**
     * Maintains an item master record by persisting changes as either a database 
     * insert or update operation.
     * 
     * @param item Base item object.
     * @param itemExt Extension item object.
     * @return the new item master id for insert operations and the total number of 
     *         rows effected for update opeartions.
     * @throws ItemMasterException
     */
    public int maintainItemMaster(ItemMaster item, Object itemExt) throws ItemMasterException {
	int rc;
	if (item.getItemId() <= 0) {
	    rc = this.insertItemMaster(item, itemExt);
	}
	else {
	    rc = this.updateItemMaster(item, itemExt);
	}
	return rc;
    }

    /**
     * Adds an Item Master to the database.    If Item Master Extension object is not null, an attempt will be
     * made to append the extension data to the base.   Upon successful completion,  the caller can
     * interrogate _base, itemBase, and itemExt objects for property values that were updated by
     * this method such as: gl account id, item Master id, item extension id, gl account number.
     * 
     * @param item Item Master object
     * @param itemExt Item Master Extension object
     * @return The id of the new item.
     * @throws ItemMasterException
     */
    protected int insertItemMaster(ItemMaster item, Object itemExt) throws ItemMasterException {
	DaoApi dso = null;
	UserTimestamp ut = null;
	int newId;
	int itemStatusId = 0;

	// Perform any client-side validations.
	this.validateItemMaster(item);

	// calculate retail price
	this.computeItemRetail(item);

	dso = DataSourceFactory.createDao(this.connector);
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    
	    // Set creditor to null if service item.  Otherwise, creditor will 
	    // equal zero and a SQL referential integrity error will occur.
	    if (item.getItemTypeId() == ItemConst.ITEM_TYPE_SRVC) {
		item.setNull(ItemMaster.PROP_CREDITORID);
		item.setOverrideRetail(1);
	    }
	    item.setActive(ItemConst.ITEM_ACTIVE_YES);
	    item.setDateCreated(ut.getDateCreated());
	    item.setDateUpdated(ut.getDateCreated());
	    item.setUserId(ut.getLoginId());
	    item.setIpCreated(ut.getIpAddr());
	    item.setIpUpdated(ut.getIpAddr());
	    newId = dso.insertRow(item, true);
	    item.setItemId(newId);

	    //   Add Item Master Extension, if applicable
	    this.insertItemMasterExt(item, itemExt);

	    // Indicate that item is in service
	    this.changeItemStatus(item, ItemConst.ITEM_STATUS_INSRVC);

	    // Determine Item has quantity for sale or is out of stock
	    itemStatusId = item.getQtyOnHand() <= 0 ? ItemConst.ITEM_STATUS_OUTSTOCK : ItemConst.ITEM_STATUS_AVAIL;
	    this.changeItemStatus(item, itemStatusId);

	    return newId;
	}
	catch (Exception e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Stub method which serves to insert custom item master data into the database.  
     * 
     * @param itemBase Base item master object
     * @param itemExt Item master extension object.  Generally this object is null unless implemented by the developer.
     * @return 0
     * @throws ItemMasterException
     */
    protected int insertItemMasterExt(ItemMaster itemBase, Object itemExt) throws ItemMasterException {
	return 0;
    }

    /**
     * Updates the base Item Master and the  Item Master Extension object.
     * 
     * @param item Item master base object.
     * @param itemExt Item Master Extension
     * @return The total number of items updated from transaction.
     * @throws ItemMasterException
     */
    protected int updateItemMaster(ItemMaster item, Object itemExt) throws ItemMasterException {
	DaoApi dso = null;
	ItemMasterStatusHist imsh = null;
	UserTimestamp ut = null;
	ItemMaster oldIm = null;
	int newQty = 0;
	int itemStatusId = 0;
	int rows;

	//  Perform any client-side validations.
	this.validateItemMaster(item);
	// calculate retail price
	this.computeItemRetail(item);

	// Get original copy of item 
	oldIm = this.findItemById(item.getItemId());

	// Calculate New Quantity On Hand
	newQty = item.getQtyOnHand();

	dso = DataSourceFactory.createDao(this.connector);
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    
	    // Ensure that creditor is set to null for service item.  Otherwise, creditor will 
	    // equal zero and a SQL referential integrity error will occur.
	    if (item.getItemTypeId() == ItemConst.ITEM_TYPE_SRVC) {
		item.setNull(ItemMaster.PROP_CREDITORID);
	    }
	    
	    item.setQtyOnHand(newQty);
	    item.setDateUpdated(ut.getDateCreated());
	    item.setUserId(ut.getLoginId());
	    item.setIpUpdated(ut.getIpAddr());
	    item.addCriteria(ItemMaster.PROP_ITEMID, item.getItemId());
	    rows = dso.updateRow(item);

	    //   Update Item Master Extension, if applicable
	    this.updateItemMasterExt(itemExt);

	    imsh = this.findCurrentItemStatusHist(item.getItemId());
	    if (imsh == null) {
	        this.msg = "Problem obtaining inventory item's status history.   History not available.";
	        logger.error(this.msg);
		throw new ItemMasterException(this.msg);
	    }

	    // Change the most recent item status, which should be 'Replaced'
	    imsh = this.changeItemStatus(item, ItemConst.ITEM_STATUS_REPLACE);

	    // User has requested system to activate vendor item override.
	    if (item.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_YES && oldIm.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_NO) {
		imsh = this.changeItemStatus(item, ItemConst.ITEM_STATUS_OVERRIDE_ACTIVE);
	    }

	    // User has requested system to deactivate vendor item override.
	    if (item.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_NO && oldIm.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_YES) {
		imsh = this.changeItemStatus(item, ItemConst.ITEM_STATUS_OVERRIDE_INACTIVE);
	    }

	    // Place Item in "Available" if quantity is greater than zero.   Otherwise, 'Out of Stock'.
	    itemStatusId = item.getQtyOnHand() <= 0 ? ItemConst.ITEM_STATUS_OUTSTOCK : ItemConst.ITEM_STATUS_AVAIL;
	    imsh = this.changeItemStatus(item, itemStatusId);

	    // If item is no longer active, then put in out servive status 
	    if (item.getActive() == 0) {
		imsh = this.changeItemStatus(item, ItemConst.ITEM_STATUS_OUTSRVC);
	    }
	    return rows;
	}
	catch (Exception e) {
	    throw new ItemMasterException(e);
	}
    }

    /**
     * Stb method which serves as a place holder for updating a custom item master extension object.
     * 
     * @param itemExt Item master extension object which the type is unknown until implemented.
     * @return 0
     * @throws ItemMasterException
     */
    protected int updateItemMasterExt(Object itemExt) throws ItemMasterException {
	return 0;
    }

    /**
     * Increases the count of an item in inventory.
     * 
     * @param itemId  The id of the target item
     * @param qty The quantity to increase the inventory item by. 
     * @return The dollar value which inventory is increased.
     * @throws ItemMasterException
     */
    public double pushInventory(int itemId, int qty) throws ItemMasterException {
	DaoApi dso = null;
	UserTimestamp ut = null;
	double changeValue = 0;
	int itemQty = 0;
	ItemMaster im = null;

	dso = DataSourceFactory.createDao(this.connector);
	try {
	    im = this.findItemById(itemId);
	    itemQty = im.getQtyOnHand() + qty;
	    changeValue = im.getUnitCost() * itemQty;
	    im.setQtyOnHand(itemQty);
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    im.setDateUpdated(ut.getDateCreated());
	    im.setUserId(ut.getLoginId());
	    im.addCriteria(ItemMaster.PROP_ITEMID, im.getItemId());
	    dso.updateRow(im);
	    return changeValue;
	}
	catch (DatabaseException e) {
	    this.msg = "Inventory push failed due to a Database error";
	    logger.error(this.msg);
	    throw new ItemMasterException(this.msg, e);
	}
	catch (SystemException e) {
	    this.msg = "Inventory push failed due to a System error";
            logger.error(this.msg);
	    throw new ItemMasterException(this.msg, e);
	}
    }

    /**
     * Decreases the count of an item in inventory.
     * 
     * @param itemId The id of the target item
     * @param qty The quantity to decrease the inventory item by.
     * @return The dollar value which inventory is decreased.
     * @throws ItemMasterException
     */
    public double pullInventory(int itemId, int qty) throws ItemMasterException {
	DaoApi dso = null;
	UserTimestamp ut = null;
	double changeValue = 0;
	int itemQty = 0;
	ItemMaster im = null;

	dso = DataSourceFactory.createDao(this.connector);
	try {
	    im = this.findItemById(itemId);
	    itemQty = im.getQtyOnHand() - qty;
	    changeValue = im.getUnitCost() * itemQty;
	    im.setQtyOnHand(itemQty);
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    im.setDateUpdated(ut.getDateCreated());
	    im.setUserId(ut.getLoginId());
	    im.addCriteria(ItemMaster.PROP_ITEMID, im.getItemId());
	    dso.updateRow(im);

	    // If required, change item status
	    if (itemQty <= 0) {
		this.changeItemStatus(im, ItemConst.ITEM_STATUS_OUTSTOCK);
	    }
	    return changeValue;
	}
	catch (DatabaseException e) {
            this.msg = "Inventory pull failed due to a Database error";
            logger.error(this.msg);
            throw new ItemMasterException(this.msg, e);
        }
        catch (SystemException e) {
            this.msg = "Inventory pull failed due to a System error";
            logger.error(this.msg);
            throw new ItemMasterException(this.msg, e);
        }
    }

    /**
     * Changes the status of an inventory item.
     *    
     * @param item The item master object targeted for the satus change.
     * @param newItemStatusId The id of the item status.
     * @return The {@link ItemMasterStatusHist} object which represents _newItemStatusId
     * @throws ItemMasterException If _newItemStatusId is out of sequence, if a database error occurs, or a system error occurs.
     */
    private ItemMasterStatusHist changeItemStatus(ItemMaster item, int newItemStatusId) throws ItemMasterException {
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;
	ItemMasterStatusHist imsh = null;
	ItemMasterStatus ims = null;

	ims = this.findItemStatusById(newItemStatusId);
	if (ims == null) {
	    this.msg = "Problem changing Item status, because new item status id is invalid";
	    logger.error(this.msg);
	    throw new ItemMasterException(this.msg);
	}

	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);

	    // End current item status
	    imsh = this.findCurrentItemStatusHist(item.getItemId());
	    if (imsh != null) {
		imsh.setEndDate(ut.getDateCreated());
		imsh.setUserId(ut.getLoginId());
		imsh.setIpUpdated(ut.getIpAddr());
		imsh.addCriteria(ItemMasterStatusHist.PROP_ITEMSTATUSHISTID, imsh.getItemStatusHistId());
		dso.updateRow(imsh);
	    }

	    // Create new item status
	    imsh = InventoryFactory.createItemMasterStatusHist();
	    imsh.setItemId(item.getItemId());
	    imsh.setItemStatusId(newItemStatusId);
	    imsh.setUnitCost(item.getUnitCost());
	    imsh.setMarkup(item.getMarkup());
	    imsh.setEffectiveDate(ut.getDateCreated());
	    imsh.setDateCreated(ut.getDateCreated());
	    imsh.setUserId(ut.getLoginId());
	    imsh.setIpCreated(ut.getIpAddr());
	    imsh.setIpUpdated(ut.getIpAddr());
	    dso.insertRow(imsh, true);
	    return imsh;
	}
	catch (DatabaseException e) {
            this.msg = "Inventory item status change failed due to a Database error";
            logger.error(this.msg);
            throw new ItemMasterException(this.msg, e);
        }
        catch (SystemException e) {
            this.msg = "Inventory item status change failed due to a System error";
            logger.error(this.msg);
            throw new ItemMasterException(this.msg, e);
        }
    }

    /**
     * Removes ainventory item from the database.
     * 
     * @param id The id of the item to delete
     * @return 1 for success
     * @throws ItemMasterException 
     *           when itemId is associated with one or more sales orders, or 
     *           a database error occurred.
     */
    public int deleteItemMaster(int itemId) throws ItemMasterException {
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	ItemMasterStatusHist imsh = null;
	ItemMaster im = null;

	try {
	    // Determine if item is tied to one or more sales orders.
	    List associations = this.findItemAssociations(itemId);
	    if (associations != null && associations.size() > 0) {
	        this.msg = "Item, " + itemId + ", cannot be deleted since it is associated with one or more sales orders and/or purchase orders";
	        logger.error(this.msg);
		throw new ItemMasterException(this.msg);
	    }

	    // Remove all items from item master status history table
	    imsh = InventoryFactory.createItemMasterStatusHist();
	    imsh.addCriteria(ItemMasterStatusHist.PROP_ITEMID, itemId);
	    dso.deleteRow(imsh);

	    // Remove item from inventory.
	    im = InventoryFactory.createItemMaster();
	    im.setItemId(itemId);
	    im.addCriteria(ItemMaster.PROP_ITEMID, im.getItemId());
	    dso.deleteRow(im);
	    return RMT2Base.SUCCESS;
	}
	catch (DatabaseException e) {
	    this.msg = "Unable to delete inventory item due to database error";
	    logger.error(this.msg);
	    throw new ItemMasterException(this.msg, e);
	}
    }

    /**
     * Deactivates an inventory item.
     * 
     * @param itemId The id of an inventory item.
     * @return 1 for success.
     * @throws ItemMasterException  
     *           itemId does not exist in the system or a database error occurred.
     */
    public int deactivateItemMaster(int itemId) throws ItemMasterException {
	String method = "deactivateItemMaster";
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	ItemMaster im = null;

	try {
	    // Retreive Item
	    im = this.findItemById(itemId);
	    if (im == null) {
	        this.msg = "Item, " + itemId + ", cannot be deactivated since it does not exist";
	        logger.error(this.msg);
		throw new ItemMasterException(this.msg);
	    }

	    // Remove all items from item master status history table
	    this.changeItemStatus(im, ItemConst.ITEM_STATUS_OUTSRVC);

	    // Flag item master as inacitve
	    im.setActive(ItemConst.ITEM_ACTIVE_NO);
	    im.addCriteria(ItemMaster.PROP_ITEMID, im.getItemId());
	    dso.updateRow(im);
	    return RMT2Base.SUCCESS;
	}
	catch (DatabaseException e) {
	    this.msg = "Deactivation of inventory item failed due to database error";
            logger.error(this.msg);
	    throw new ItemMasterException(this.msg, e);
	}
    }

    /**
     * Activates an inventory item.
     * 
     * @param itemId The id of an inventory item.
     * @return 1 for success.
     * @throws ItemMasterException  itemId does not exist in the system or a database error occurred.
     */
    public int activateItemMaster(int itemId) throws ItemMasterException {
	String method = "deactivateItemMaster";
	int itemStatusId = 0;
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	ItemMaster im = null;

	try {
	    // Retreive Item
	    im = this.findItemById(itemId);
	    if (im == null) {
	        this.msg = "Item, " + itemId + ", cannot be activated since it does not exist";
	        logger.error(this.msg);
		throw new ItemMasterException(this.msg);
	    }

	    // Remove all items from item master status history table
	    this.changeItemStatus(im, ItemConst.ITEM_STATUS_INSRVC);

	    // Determine if item has quantity to sold or is out of stock.
	    itemStatusId = im.getQtyOnHand() <= 0 ? ItemConst.ITEM_STATUS_OUTSTOCK : ItemConst.ITEM_STATUS_AVAIL;
	    this.changeItemStatus(im, itemStatusId);

	    // Flag item master as acitve
	    im.setActive(ItemConst.ITEM_ACTIVE_YES);
	    im.addCriteria(ItemMaster.PROP_ITEMID, im.getItemId());
	    dso.updateRow(im);
	    return RMT2Base.SUCCESS;
	}
	catch (DatabaseException e) {
	    this.msg = "Activation of inventory item failed due to database error";
            logger.error(this.msg);
            throw new ItemMasterException(this.msg, e);
	}
    }

    /**
     * Validates base transaction, itemBase.  
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
     * @param item
     * @throws ItemMasterException
     */
    protected void validateItemMaster(ItemMaster item) throws ItemMasterException {
	CreditorApi credApi = null;
	try {
	    credApi = CreditorFactory.createApi(this.connector);
	    if (item.getCreditorId() > 0) {
		Object cred = credApi.findById(item.getCreditorId());
		if (cred == null) {
		    this.msg = "Vendor does not exist: " + item.getCreditorId();
		    logger.log(Level.ERROR, this.msg);
		    throw new ItemMasterException(this.msg);
		}
	    }
	    else {
		item.setNull("vendorId");
	    }
	}
	catch (Exception e) {
	    throw new ItemMasterException(e);
	}

	// Common validations
	if (item.getDescription() == null || item.getDescription().equals("")) {
	    this.msg = "Item Name must contain a value and cannot be null";
	    logger.log(Level.ERROR, this.msg);
	    throw new ItemMasterException(this.msg);
	}

	if (item.getItemTypeId() <= 0) {
	    this.msg = "Item Type is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new ItemMasterException(this.msg);
	}
	//  Mark Up must be greater than zero and cannot be null
	if (item.getMarkup() <= 0) {
	    this.msg = "Mark Up must be greater than zero and cannot be null";
	    logger.log(Level.ERROR, this.msg);
	    throw new ItemMasterException(this.msg);
	}

	/*  Not sure if unit cost should be required to have a value greater than zero.
	 if (itemBase.getUnitCost() <= 0)  {
	 throw new ItemMasterException(this.dbo, 419, null);
	 }
	 */

	// Service item validations
	if (item.getItemTypeId() == ItemConst.ITEM_TYPE_SRVC) {
	    if (item.getCreditorId() > 0) {
		this.msg = "Service items cannot be assoicated with a Vendor";
		logger.log(Level.ERROR, this.msg);
		throw new ItemMasterException(this.msg);
	    }
	    // Quantity on Hand must be equal to 1 for service items
	    if (item.getQtyOnHand() != 1) {
		this.msg = "Quantity on Hand must be equal to 1 for service items";
		logger.log(Level.ERROR, this.msg);
		throw new ItemMasterException(this.msg);
	    }
	    // Mark Up must be equal to 1
	    if (item.getMarkup() != 1) {
		this.msg = "Mark Up must be equal to 1";
		logger.log(Level.ERROR, this.msg);
		throw new ItemMasterException(this.msg);
	    }
	}
	return;
    }

    /**
     * Initiates the maintenance of an Vendor Item Entity.   Only updates of an existing 
     * VendorItems object are performed via this method.  <i>vi</i> must pass all business 
     * rule validations before changes are successfully applied to the database.
     * 
     * @param vi The {@link VendorItems} object
     * @return 1 for success
     * @throws ItemMasterException
     */
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
    protected int updateVendorItem(VendorItems vi) throws ItemMasterException {
	int rc = 0;
	DaoApi dso = DataSourceFactory.createDao(this.connector);
	this.validateVendorItem(vi);
	try {
	    vi.addCriteria(VendorItems.PROP_CREDITORID, vi.getCreditorId());
	    vi.addCriteria(VendorItems.PROP_ITEMID, vi.getItemId());
	    rc = dso.updateRow(vi);
	    return rc;
	}
	catch (DatabaseException e) {
	    this.msg = "Vendor item update failed due to database error";
	    logger.error(this.msg);
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
    protected void validateVendorItem(VendorItems vi) throws ItemMasterException {
	CreditorApi credApi = null;

	try {
	    credApi = CreditorFactory.createApi(this.connector);
	    if (vi.getCreditorId() > 0) {
		Object cred = credApi.findById(vi.getCreditorId());
		if (cred == null) {
		    this.msg = "Vendor does not exist: " + vi.getCreditorId();
		    logger.error(this.msg);
		    throw new ItemMasterException(this.msg);
		}
	    }
	    else {
	        this.msg = "Vendor Id is invalid: " + vi.getCreditorId();
	        logger.error(this.msg);
		throw new ItemMasterException(this.msg);
	    }
	}
	catch (Exception e) {
	    throw new ItemMasterException(e);
	}
	if (vi.getVendorItemNo() == null || vi.getVendorItemNo().length() <= 0) {
	    this.msg = "New and existing merchandise items must have the vendor's version of an item numbe or part number";
            logger.error(this.msg);
	    throw new ItemMasterException(this.msg);
	}
	if (vi.getItemSerialNo() == null || vi.getItemSerialNo().length() <= 0) {
	    this.msg = "Merchandise items must have a vendor serial number";
            logger.error(this.msg);
	    throw new ItemMasterException(this.msg);
	}
    }

    /**
     * Calculates the retail price of an item based on its item type value (Merchandise or Service).  At this point all data components 
     * used for calculating retail should have been verified.   The retail price will be determined in one of two ways:   1) as is when the user
     * request that retail price calculations be ignored and just accept the user's input  or 2) apply the formula retail price = unit cost * mark up.
     * 
     * @param itemBase
     * @throws ItemMasterException
     */
    private void computeItemRetail(ItemMaster itemBase) throws ItemMasterException {
	double retailPrice = 0;

	// Determine if user requests us to override retail calculations.
	if (itemBase.getOverrideRetail() == 0) {
	    retailPrice = itemBase.getUnitCost() * itemBase.getMarkup();
	    itemBase.setRetailPrice(retailPrice);
	}
	return;
    }

    /**
     * Cycles through the list of inventory item id's contained in items and assigns each one to a vendor identified as vendorId.
     * 
     * @param vendorId The id of the vendor
     * @param items A list inventory item id's
     * @return The number of items assigned to the vendor.
     * @throws ItemMasterException
     */
    public int assignVendorItems(int vendorId, int items[]) throws ItemMasterException {
	int rc = 0;
	ItemMaster im = null;
	VendorItems vi = null;
	DaoApi dso = DataSourceFactory.createDao(this.connector);

	for (int ndx = 0; ndx < items.length; ndx++) {
	    im = this.findItemById(items[ndx]);
	    if (im == null) {
		this.msg = "The following item id is not found in the database and the assignment of items to vendor is aborted: " + items[ndx];
		logger.error(this.msg);
		throw new ItemMasterException(this.msg);
	    }
	    vi = InventoryFactory.createVendorItem(vendorId, items[ndx], im.getItemSerialNo(), im.getVendorItemNo(), im.getUnitCost());
	    try {
		rc += dso.insertRow(vi, false);
	    }
	    catch (DatabaseException e) {
	        this.msg = "Problem adding item to vendor_items table";
	        logger.error(this.msg);
		throw new ItemMasterException(this.msg, e);
	    }
	}
	return rc;
    }

    /**
     * This method disassociates all items contained in items from a vendor.
     * 
     * @param vendorId The id of the vendor
     * @param items A list inventory item id's
     * @return The number of items unassigned from the vendor.
     * @throws ItemMasterException
     */
    public int removeVendorItems(int vendorId, int items[]) throws ItemMasterException {
	int rc = 0;
	ItemMaster im = null;
	VendorItems vi = null;
	DaoApi dso = DataSourceFactory.createDao(this.connector);

	for (int ndx = 0; ndx < items.length; ndx++) {
	    im = this.findItemById(items[ndx]);
	    if (im == null) {
		this.msg = "The following item id is not found in the database and the assignment of items to vendor is aborted: " + items[ndx];
		logger.error(this.msg);
		throw new ItemMasterException(this.msg);
	    }
	    vi = InventoryFactory.createVendorItem();
	    vi.addCriteria("CreditorId", vendorId);
	    vi.addCriteria("ItemId", items[ndx]);
	    try {
		rc += dso.deleteRow(vi);
	    }
	    catch (DatabaseException e) {
	        this.msg = "Problem removing item from the vendor_items table";
		throw new ItemMasterException(this.msg, e);
	    }
	}
	return rc;
    }

    /**
     * This method activates a vendor-item override targeting the inventory item, itemId.   
     * An override instructs the system to obtain pricing information for an inventory 
     * item from the vendor_items table instead of the item_master table .   This method 
     * puts this concept into effect.
     * 
     * @param vendorId The id of the vendor that will be assoicated with an item in the item_master table.
     * @param itemId The target item.
     * @return The total number of rows effected by the database transaction.  This is ususally 1.
     * @throws ItemMasterException
     */
    public int addInventoryOverride(int vendorId, int itemId) throws ItemMasterException {
	ItemMaster im = null;

	im = this.findItemById(itemId);
	if (im == null) {
	    this.msg = "The following item id is not found in the database and the vendor item inventory override activation is aborted: " + itemId;
	    logger.error(this.msg);
	    throw new ItemMasterException(this.msg);
	}
	// Do not attempt to update an item that is currently overriden.
	if (im.getOverrideRetail() == ItemConst.ITEM_OVERRIDE_YES) {
	    return 0;
	}
	im.setCreditorId(vendorId);
	im.setOverrideRetail(ItemConst.ITEM_OVERRIDE_YES);
	// Ensure that item's quantity on hand is not effected after updates are applied to the database.
	im.setQtyOnHand(0);
	this.maintainItemMaster(im, null);
	return RMT2Base.SUCCESS;
    }

    /**
     * Activates a vendor-item override for all item id's stored in items collection.
     * 
     * @param vendorId The id of the vendor that will be assoicated with each item id.
     * @param items Collection containing one or more item_master id's to override.
     * @return The total number of rows effected by the database transaction.
     * @throws ItemMasterException
     */
    public int addInventoryOverride(int vendorId, int items[]) throws ItemMasterException {
	int rc = 0;

	for (int ndx = 0; ndx < items.length; ndx++) {
	    rc += this.addInventoryOverride(vendorId, items[ndx]);
	}
	return rc;
    }

    /**
     * This method deactivates a vendor-item override targeting the inventory item, itemId.   
     * An override instructs the system to obtain pricing information for an inventory item 
     * from the vendor_items table instead of the item_master table .   This method renders 
     * this concept ineffective.
     * 
     * @param vendorId The id of the vendor that will be disassoicated with the item id.
     * @param itemId The target item.
     * @return The total number of rows effected by the database transaction.  This is ususally 1.
     * @throws ItemMasterException
     */
    public int removeInventoryOverride(int vendorId, int itemId) throws ItemMasterException {
	ItemMaster im = null;

	im = this.findItemById(itemId);
	if (im == null) {
	    this.msg = "The following item id is not found in the database and the vendor item inventory override deactivation is aborted: " + itemId;
	    logger.error(this.msg);
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

    /**
     * Deactivates a vendor-item override for all item id's stored in items collection.
     * 
     * @param vendorId The id of the vendor that will be disassoicated with each item id.
     * @param items Collection containing one or more item_master id's to deactivate item overrides.
     * @return The total number of rows effected by the database transaction.
     * @throws ItemMasterException
     */
    public int removeInventoryOverride(int vendorId, int items[]) throws ItemMasterException {
	int rc = 0;

	for (int ndx = 0; ndx < items.length; ndx++) {
	    rc += this.removeInventoryOverride(vendorId, items[ndx]);
	}
	return rc;
    }

}

package com.inventory;

import com.bean.ItemMaster;
import com.bean.VendorItems;

/**
 * Interface that provides the method prototypes for querying, creating, modifying, 
 * deleting, and general management of inventory items.
 * 
 * @author appdev
 *
 */
public interface InventoryApi {

    /**
     * Retrieves an item object by item id.
     * 
     * @param itemId The id of the item
     * @return An arbitrary object representing an item.
     * @throws ItemMasterException
     */
    Object findItemById(int itemId) throws ItemMasterException;

    /**
     * Retrieves one or more items using item type id
     * 
     * @param itemTypeId The item type id
     * @return A List of arbitrary objects representing one or more items.
     * @throws ItemMasterException
     */
    Object findItemByType(int itemTypeId) throws ItemMasterException;

    /**
     * Retrieves one or more items using the id of the vendor.
     * 
     * @param vendorId The id of Vendor
     * @return A List of arbitrary objects representing one or more items.
     * @throws ItemMasterException
     */
    Object findItemByVendorId(int vendorId) throws ItemMasterException;

    /**
     * Retrieves one or more items using the vendor's item number
     * 
     * @param vendItemNo The vendor's verison of the item number.
     * @return A List of arbitrary objects representing one or more items.
     * @throws ItemMasterException
     */
    Object findItemByVendorItemNo(String vendItemNo) throws ItemMasterException;

    /**
     * Retrieves one or more items using the item's serial number
     * 
     * @param serialNo The serial number of the item.
     * @return A List of arbitrary objects representing one or more items.
     * @throws ItemMasterException
     */
    Object findItemBySerialNo(String serialNo) throws ItemMasterException;

    /**
     * Retrieves an ArrayList of of any inventory related object based on the base view, base class, and custom 
     * criteria  supplied by the user.  User is responsible for setting the base view and class so that the API will 
     * know what data to retrieve.
     * 
     * @param criteria The selection criteria to apply to the query of data source.
     * @returnA List of arbitrary objects representing one or more items. 
     * @throws ItemMasterException
     */
    Object findItem(String criteria) throws ItemMasterException;

    /**
     * Locates an Item from the vw_item_master view
     * 
     * @param itemId is the item master id.
     * @return An arbitrary object representing an item view.
     * @throws ItemMasterException
     */
    Object findItemViewById(int itemId) throws ItemMasterException;

    /**
     * Retrieves Item Type data using item type id.
     * 
     * @param id The id of the item type/
     * @return An arbitrary object representing an item type.
     * @throws ItemMasterException
     */
    Object findItemTypeById(int id) throws ItemMasterException;

    /**
     * Retrieve one or more item type objects using criteria and orderBy.
     * 
     * @param criteria Selection criteria used as part of the query's SQL where clause.
     * @return List of arbitrary objects representing one or more item types. 
     * @throws ItemMasterException
     */
    Object findItemTypes(String criteria) throws ItemMasterException;

    /**
     * Retrieves one or more item status object using custom selection criteria.
     * 
     * @param criteria The custom selection criteria to be appended to the query's where clause.
     * @return List of arbitrary objects representing one or more item statuses. 
     * @throws ItemMasterException
     */
    Object findItemStatus(String criteria) throws ItemMasterException;

    /**
     * Retrieves Item master status object by primary key.
     * 
     * @param itemStatusId
     * @return An arbitrary object representing an item status.
     * @throws ItemMasterException
     */
    Object findItemStatusById(int itemStatusId) throws ItemMasterException;

    /**
     * Retrieves one or more item statuses using an item id.
     * 
     * @param id The id of the item to retrieve its statuses.
     * @return List of arbitrary objects representing one or more item status history items. 
     * @throws ItemMasterException
     */
    Object findItemStatusHistByItemId(int id) throws ItemMasterException;

    /**
     * Retrieves the current status of an item based on the item's id.
     * 
     * @param itemId The id of the item to retreive its current status.
     * @return An arbitrary object representing an item status history item.
     * @throws ItemMasterException
     */
    Object findCurrentItemStatusHist(int itemId) throws ItemMasterException;

    /**
     * Retrieves one vendor item object using vendorId and itemId
     * 
     * @param vendorId The id of the vendor
     * @param itemId The id of the inventory item
     * @return An arbitrary object representing a vendor items item.
     * @throws ItemMasterException
     */
    Object findVendorItem(int vendorId, int itemId) throws ItemMasterException;

    /**
     * Retrieves those inventory items that are assigned to a particular vendor from the database.
     * 
     * @param vendorId The id of the target vendor.
     * @return List of arbitrary objects representing one or more vendor items. 
     * @throws ItemMasterException
     */
    Object findVendorAssignItems(int vendorId) throws ItemMasterException;

    /**
     * Retrieves those inventory items that are not assigned to a particular vendor.
     * 
     * @param vendorId The id of the target vendor.
     * @return List of arbitrary objects representing one or more unassigned vendor items.
     * @throws ItemMasterException
     */
    Object findVendorUnassignItems(int vendorId) throws ItemMasterException;
    
    /**
     * Fetch all the different entities an item is assoicated with.
     * 
     * @param itemId
     *          The id of the item to query.
     * @return
     *          An arbitrary item representing the assoications.
     * @throws ItemMasterException
     */
    Object findItemAssociations(int itemId) throws ItemMasterException;

    /**
     * Initiates the maintenance of an Item Master Entity.   If the id of the Item Master Object is zero, then
     * an Item master is created.    Otherwise,  the Item Master is updated.
     * 
     * @param itemBase Base item object.
     * @param itemExt Extension item object.
     * @return The id of the item maintained.
     * @throws ItemMasterException
     */
    int maintainItemMaster(ItemMaster itemBase, Object itemExt) throws ItemMasterException;

    /**
     * Initiates the maintenance of an Vendor Item Entity.   Only updates of an existing VendorItems object are performed via this method.
     * vi must pass all business rule validations before changes are successfully applied to the database.
     * 
     * @param vi The {@link VendorItems} object
     * @return 1 for success
     * @throws ItemMasterException
     */
    int maintainVendorItem(VendorItems vi) throws ItemMasterException;

    /**
     * Removes ainventory item from the database.
     * 
     * @param id The id of the item to delete
     * @return 1 for success
     * @throws ItemMasterException 
     *           when itemId is associated with one or more sales orders, or 
     *           a database error occurred.
     */
    int deleteItemMaster(int id) throws ItemMasterException;

    /**
     * Increases the count of an item in inventory.
     * 
     * @param itemId  The id of the target item
     * @param qty The quantity to increase the inventory item by. 
     * @return The dollar value which inventory is increased.
     * @throws ItemMasterException
     */
    double pushInventory(int itemId, int qty) throws ItemMasterException;

    /**
     * Decreases the count of an item in inventory.
     * 
     * @param itemId The id of the target item
     * @param qty The quantity to decrease the inventory item by.
     * @return The dollar value which inventory is decreased.
     * @throws ItemMasterException
     */
    double pullInventory(int itemId, int qty) throws ItemMasterException;

    /**
     * Deactivates an inventory item.
     * 
     * @param itemId The id of an inventory item.
     * @return 1 for success.
     * @throws ItemMasterException  itemId does not exist in the system or a database error occurred.
     */
    int deactivateItemMaster(int itemId) throws ItemMasterException;

    /**
     * Activates an inventory item.
     * 
     * @param itemId The id of an inventory item.
     * @return 1 for success.
     * @throws ItemMasterException  itemId does not exist in the system or a database error occurred.
     */
    int activateItemMaster(int itemId) throws ItemMasterException;

    /**
     * Cycles through the list of inventory item id's contained in items and assigns each one to a vendor identified as vendorId.
     * 
     * @param vendorId The id of the vendor
     * @param items A list inventory item id's
     * @return The number of items assigned to the vendor.
     * @throws ItemMasterException
     */
    int assignVendorItems(int vendorId, int items[]) throws ItemMasterException;

    /**
     * This method disassociates all items contained in items from a vendor.
     * 
     * @param vendorId The id of the vendor
     * @param items A list inventory item id's
     * @return The number of items unassigned from the vendor.
     * @throws ItemMasterException
     */
    int removeVendorItems(int vendorId, int items[]) throws ItemMasterException;

    /**
     * This method activates a vendor-item override targeting the inventory item, itemId.   An override instructs the system to obtain 
     * pricing information for an inventory item from the vendor_items table instead of the item_master table .   This method puts 
     * this concept into effect.
     * 
     * @param vendorId The id of the vendor that will be assoicated with an item in the item_master table.
     * @param itemId The target item.
     * @return The total number of rows effected by the database transaction.  This is ususally 1.
     * @throws ItemMasterException
     */
    int addInventoryOverride(int vendorId, int itemId) throws ItemMasterException;

    /**
     * Activates a vendor-item override for all item id's stored in items collection.
     * 
     * @param vendorId The id of the vendor that will be assoicated with each item id.
     * @param items Collection containing one or more item_master id's to override.
     * @return The total number of rows effected by the database transaction.
     * @throws ItemMasterException
     */
    int addInventoryOverride(int vendorId, int items[]) throws ItemMasterException;

    /**
     * This method deactivates a vendor-item override targeting the inventory item, itemId.   An override instructs the system to obtain 
     * pricing information for an inventory item from the vendor_items table instead of the item_master table .   This method renders 
     * this concept ineffective.
     * 
     * @param vendorId The id of the vendor that will be disassoicated with the item id.
     * @param itemId The target item.
     * @return The total number of rows effected by the database transaction.  This is ususally 1.
     * @throws ItemMasterException
     */
    int removeInventoryOverride(int vendorId, int itemId) throws ItemMasterException;

    /**
     * Deactivates a vendor-item override for all item id's stored in items collection.
     * 
     * @param vendorId The id of the vendor that will be disassoicated with each item id.
     * @param items Collection containing one or more item_master id's to deactivate item overrides.
     * @return The total number of rows effected by the database transaction.
     * @throws ItemMasterException
     */
    int removeInventoryOverride(int vendorId, int items[]) throws ItemMasterException;
}
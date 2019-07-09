
package com.api;

import java.util.List;

import com.util.ItemMasterException;

import com.bean.ItemMaster;
import com.bean.ItemMasterType;
import com.bean.ItemMasterStatusHist;
import com.bean.VwItemMaster;
import com.bean.VendorItems;;


public interface InventoryApi extends BaseDataSource {

   /**
    * Retrieves an item object by item id.
    * 
    * @param _itemId The id of the item
    * @return {@link ItemMaster}
    * @throws ItemMasterException
    */
    ItemMaster findItemById(int _itemId) throws ItemMasterException;
   
   /**
    * Retrieves one or more items using item type id
    * 
    * @param _itemTypeId The item type id
    * @param orderBy SQL order by clause.
    * @return An ArrayList of objects
    * @throws ItemMasterException
    */
    List findItemByType(int _itemTypeId, String orderBy) throws ItemMasterException;
   
   /**
    * Retrieves one or more items using the id of the vendor.
    * 
    * @param _vendorId The id of Vendor
    * @return An ArrayList of objects
    * @throws ItemMasterException
    */
    List findItemByVendorId(int _vendorId) throws ItemMasterException;
   
   /**
    * Retrieves one or more items using the vendor's item number
    * 
    * @param _vendItemNo The vendor's verison of the item number.
    * @return An ArrayList of objects.
    * @throws ItemMasterException
    */
    List  findItemByVendorItemNo(String _vendItemNo) throws ItemMasterException;
   
   /**
    * Retrieves one or more items using the item's serial number
    * 
    * @param _serialNo The serial number of the item.
    * @return An ArrayList of objects.
    * @throws ItemMasterException
    */
    List findItemBySerialNo(String _serialNo) throws ItemMasterException;
   
   /**
    * Retrieves an ArrayList of of any inventory related object based on the base view, base class, and custom 
    * criteria  supplied by the user.  User is responsible for setting the base view and class so that the API will 
    * know what data to retrieve.
    * 
    * @param criteria The selection criteria to apply to the query of data source.
    * @param orderBy The ordering to apply to the query of the data source.
    * @return ArrayList of objects.  The implementer of this method is responsible for applying the proper casting of 
    * each element contained in the returned results.  The array size will be >= 0.
    * @throws ItemMasterException
    */
    List findItem(String criteria, String orderBy) throws ItemMasterException;

   /**
    * Locates an Item from the vw_item_master view
    * 
    * @param _itemId is the item master id.
    * @return item data
    * @throws ItemMasterException
    */
   VwItemMaster findItemViewById(int _itemId) throws ItemMasterException;
   
   /**
    * Retrieves Item Type data using item type id.
    * 
    * @param _id The id of the item type/
    * @return {@link ItemMasterType} object
    * @throws ItemMasterException
    */
   ItemMasterType findItemTypeById(int _id) throws ItemMasterException;
   
   /**
    * Retrieve one or more item type objects using criteria and orderBy.
    * 
    * @param criteria Selection criteria used as part of the query's SQL where clause.
    * @param orderBy The criteria used to order data.
    * @return An ArrayList of "@link ItemMasterType) objects.
    * @throws ItemMasterException
    */
   List findItemTypes(String criteria, String orderBy) throws ItemMasterException;

   /**
    * Retrieves one or more item status object using custom selection criteria.
    * 
    * @param criteria The custom selection criteria to be appended to the query's where clause.
    * @return An ArrayList of {@link ItemMasterStatus} objects.
    * @throws ItemMasterException
    */
    List findItemStatus(String criteria) throws ItemMasterException;
   
   /**
    * Retrieves one or more item statuses using an item id.
    * 
    * @param _id The id of the item to retrieve its statuses.
    * @return An ArrayList of {@link ItemMasterStatusHist} objects.
    * @throws ItemMasterException
    */
    List findItemStatusHistByItemId(int _id) throws ItemMasterException;
   
   /**
    * Retrieves the current status of an item based on the item's id.
    * 
    * @param _itemId The id of the item to retreive its current status.
    * @return {@link ItemMasterStatusHist}
    * @throws ItemMasterException
    */
    ItemMasterStatusHist findCurrentItemStatusHist(int _itemId) throws ItemMasterException;
   
   /**
    * Retrieves one vendor item object using vendorId and itemId
    * 
    * @param vendorId The id of the vendor
    * @param itemId The id of the inventory item
    * @return {@link VendorItems} object
    * @throws ItemMasterException
    */
   VendorItems findVendorItem(int vendorId, int itemId) throws ItemMasterException;

   /**
    * Retrieves those inventory items that are assigned to a particular vendor from the database.
    * 
    * @param vendorId The id of the target vendor.
    * @return An ArrayList of {@link VwVendorItems} objects
    * @throws ItemMasterException
    */
   List findVendorAssignItems(int vendorId) throws ItemMasterException;
   
   /**
    * Retrieves those inventory items that are not assigned to a particular vendor.
    * 
    * @param vendorId The id of the target vendor.
    * @return An ArrayList of {@link ItemMaster} objects.
    * @throws ItemMasterException
    */
   List findVendorUnassignItems(int vendorId) throws ItemMasterException;


   /**
    * Initiates the maintenance of an Item Master Entity.   If the id of the Item Master Object is zero, then
    * an Item master is created.    Otherwise,  the Item Master is updated.
    * 
    * @param _itemBase Base item object.
    * @param _itemExt Extension item object.
    * @return The id of the item maintained.
    * @throws ItemMasterException
    */
   int maintainItemMaster(ItemMaster _itemBase, Object _itemExt) throws ItemMasterException;
   
   
   /**
    * Initiates the maintenance of an Vendor Item Entity.   Only updates of an existing VendorItems object are performed via this method.
    * _vi must pass all business rule validations before changes are successfully applied to the database.
    * 
    * @param _vi The {@link VendorItems} object
    * @return 1 for success
    * @throws ItemMasterException
    */
   int maintainVendorItem(VendorItems _vi) throws ItemMasterException;
   
   
   /**
    * Removes ainventory item from the database.
    * 
    * @param _id The id of the item to delete
    * @return 1 for success
    * @throws ItemMasterException when _itemId is associated with one or more sales orders, or a database error occurred.
    */
   int deleteItemMaster(int _id) throws ItemMasterException;
   
   /**
    * Increases the count of an item in inventory.
    * 
    * @param _itemId  The id of the target item
    * @param _qty The quantity to increase the inventory item by. 
    * @return The dollar value which inventory is increased.
    * @throws ItemMasterException
    */
   double pushInventory(int _itemId, int _qty) throws ItemMasterException;
   
   /**
    * Decreases the count of an item in inventory.
    * 
    * @param _itemId The id of the target item
    * @param _qty The quantity to decrease the inventory item by.
    * @return The dollar value which inventory is decreased.
    * @throws ItemMasterException
    */
   double pullInventory(int _itemId, int _qty) throws ItemMasterException;
   
   /**
    * Deactivates an inventory item.
    * 
    * @param _itemId The id of an inventory item.
    * @return 1 for success.
    * @throws ItemMasterException  _itemId does not exist in the system or a database error occurred.
    */
   int deactivateItemMaster(int _itemId) throws ItemMasterException;
   
   /**
    * Activates an inventory item.
    * 
    * @param _itemId The id of an inventory item.
    * @return 1 for success.
    * @throws ItemMasterException  _itemId does not exist in the system or a database error occurred.
    */
   int activateItemMaster(int _itemId) throws ItemMasterException;
   
   
   /**
    * Cycles through the list of inventory item id's contained in _items and assigns each one to a vendor identified as _vendorId.
    * 
    * @param _vendorId The id of the vendor
    * @param _items A list inventory item id's
    * @return The number of items assigned to the vendor.
    * @throws ItemMasterException
    */
   int assignVendorItems(int _vendorId, int _items[]) throws ItemMasterException;
   
   /**
    * This method disassociates all items contained in _items from a vendor.
    * 
    * @param _vendorId The id of the vendor
    * @param _items A list inventory item id's
    * @return The number of items unassigned from the vendor.
    * @throws ItemMasterException
    */
   int removeVendorItems(int _vendorId, int _items[]) throws ItemMasterException;
   
   /**
    * This method activates a vendor-item override targeting the inventory item, _itemId.   An override instructs the system to obtain 
    * pricing information for an inventory item from the vendor_items table instead of the item_master table .   This method puts 
    * this concept into effect.
    * 
    * @param _vendorId The id of the vendor that will be assoicated with an item in the item_master table.
    * @param _itemId The target item.
    * @return The total number of rows effected by the database transaction.  This is ususally 1.
    * @throws ItemMasterException
    */
   int addInventoryOverride(int _vendorId, int _itemId) throws ItemMasterException;
   
   /**
    * Activates a vendor-item override for all item id's stored in _items collection.
    * 
    * @param _vendorId The id of the vendor that will be assoicated with each item id.
    * @param _items Collection containing one or more item_master id's to override.
    * @return The total number of rows effected by the database transaction.
    * @throws ItemMasterException
    */
   int addInventoryOverride(int _vendorId, int _items[]) throws ItemMasterException;
   
   /**
    * This method deactivates a vendor-item override targeting the inventory item, _itemId.   An override instructs the system to obtain 
    * pricing information for an inventory item from the vendor_items table instead of the item_master table .   This method renders 
    * this concept ineffective.
    * 
    * @param _vendorId The id of the vendor that will be disassoicated with the item id.
    * @param _itemId The target item.
    * @return The total number of rows effected by the database transaction.  This is ususally 1.
    * @throws ItemMasterException
    */
   int removeInventoryOverride(int _vendorId, int _itemId) throws ItemMasterException;
   
   /**
    * Deactivates a vendor-item override for all item id's stored in _items collection.
    * 
    * @param _vendorId The id of the vendor that will be disassoicated with each item id.
    * @param _items Collection containing one or more item_master id's to deactivate item overrides.
    * @return The total number of rows effected by the database transaction.
    * @throws ItemMasterException
    */
   int removeInventoryOverride(int _vendorId, int _items[]) throws ItemMasterException;
}
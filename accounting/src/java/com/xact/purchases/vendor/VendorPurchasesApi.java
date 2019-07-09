package com.xact.purchases.vendor;

import java.util.List;

import com.api.BaseDataSource;
import com.bean.PurchaseOrder;

/**
 * Interface for managing Creditor/Vendor purchases data.  Contains method declaration 
 * pertaining to querying, creating, updating, and deleting of purchases.
 *  
 * @author RTerrell
 *
 */
public interface VendorPurchasesApi extends BaseDataSource {

    /**
     * Finds a purchase order that is associated with value.
     * 
     * @param poId The Id of the purchase order.
     * @return An arbitray object representing the purchase order
     * @throws VendorPurchasesException
     */
    Object findPurchaseOrder(int poId) throws VendorPurchasesException;

    /**
     * Finds one or more purchase order using custom selection criteria.
     * 
     * @param criteria A String representing custom criteria.
     * @return An arbitrary object representing one or more purchase orders.
     * @throws VendorPurchasesException
     */
    Object findPurchaseOrder(String criteria) throws VendorPurchasesException;

    /**
     * Finds a single purchase order item using _poItemId.
     * 
     * @param poId  The Id of the purchase order.
     * @param poItemId  The Id of the purchase order item.
     * @return An arbitray object representing a purchase order item
     * @throws VendorPurchasesException
     */
    Object findPurchaseOrderItem(int poId, int poItemId) throws VendorPurchasesException;

    /**
     * Retrieves one vendor item object using vendorId and itemId
     * 
     * @param vendorId The id of the vendor
     * @param itemId The id of the inventory item
     * @return An arbitray object representing a vendor item
     * @throws ItemMasterException
     */
    Object findVendorItem(int vendorId, int itemId) throws VendorPurchasesException;

    /**
     * Retreives either the item_master version or the vendor_item version of an 
     * inventory item using vendor id and item id.
     * 
     * @param vendorId  The vendor id
     * @param itemId The id of the item to retrieve
     * @return An arbitray object representing a vendor item
     * @throws VendorPurchasesException
     */
    Object findCurrentItemByVendor(int vendorId, int itemId) throws VendorPurchasesException;

    /**
     * Find all purchase order items pertaining to a given vendor.
     * 
     * @param vendorId The vendor id
     * @param poId The purchase order id
     * @return An arbitray object representing one or more vendor items
     * @throws VendorPurchasesException
     */
    Object findVendorItemPurchaseOrderItems(int vendorId, int poId) throws VendorPurchasesException;

    /**
     * Finds all items belonging to a purchase order identified as, value
     * 
     * @param poId The purchase order id
     * @return An arbitray object representing the items of q purchase order
     * @throws VendorPurchasesException
     */
    Object findPurchaseOrderItems(int poId) throws VendorPurchasesException;

    /**
     * Obtains the purchase order status based on the value of _poStatusId.
     * 
     * @param poStatusId The id of the purchase order status to retrieve.
     * @return An arbitray object representing the purchase order status
     * @throws VendorPurchasesException
     */
    Object findPurchaseOrderStatus(int poStatusId) throws VendorPurchasesException;

    /**
     * Obtains purchase order data pertaining to the current status using purchase 
     * order id identified as, value.
     * 
     * @param poId The purchase order id.
     * @return An arbitray object representing information pertaining to the current 
     *         purchase order.
     * @throws VendorPurchasesException
     */
    Object findCurrentPurchaseOrder(int poId) throws VendorPurchasesException;

    /**
     * Obtains the current status of a purchase order.
     * 
     * @param poId Is the id of the purchase order
     * @return An arbitray object representing the purchase order status history.
     * @throws VendorPurchasesException
     */
    Object findCurrentPurchaseOrderHistory(int poId) throws VendorPurchasesException;

    /**
     * Gathers the status history of a purchase order.
     * 
     * @param poId The purchase order id
     * @return An arbitray object representing one or more purchase order status 
     *         history entries.
     * @throws VendorPurchasesException
     */
    Object findPurchaseOrderHistory(int poId) throws VendorPurchasesException;

    /**
     * Retrieves one or more vendor inventory items which have not been assoicated 
     * with a purchase order.
     * 
     * @param vendorId The id of the vendor.
     * @param poId The id of the purchase order.
     * @return Arbitrary object representing one or more items as a result of the query.
     * @throws VendorPurchasesException
     */
    Object getPurchaseOrderAvailItems(int vendorId, int poId) throws VendorPurchasesException;

    /**
     * Maintains a purchase order.
     *     
     * @param po  Purchase Order object.
     * @param items list of purchase order items.
     * @return int
     * @throws VendorPurchasesException
     */
    int maintainPurchaseOrder(PurchaseOrder po, List items) throws VendorPurchasesException;

    /**
     * Adds items to a purchase order.
     * 
     * @param po Purchase Order object.
     * @param items a list of purchase order items.
     * @return int
     * @throws VendorPurchasesException
     */
    int addItemsToPurchaseOrder(PurchaseOrder po, List items) throws VendorPurchasesException;

    /**
     * Deletes a purchase order.
     * 
     * @param poId The purchase order id
     * @return int
     * @throws VendorPurchasesException
     */
    int deletePurchaseOrder(int poId) throws VendorPurchasesException;

    /**
     * Deletes one purchase order item.
     * 
     * @param poId Id of the purchase order which all items will be removed.
     * @param poItemId Id of the purchase order item
     * @return int
     * @throws VendorPurchasesException
     */
    int deleteItem(int poId, int poItemId) throws VendorPurchasesException;

    /**
     * Deletes all items belonging to a purchase order.
     * 
     * @param poId Id of the purchase order which all items will be removed.
     * @return int
     * @throws VendorPurchasesException
     */
    int deleteAllItems(int poId) throws VendorPurchasesException;

    /**
     * This method will perform a purchases, returns, and allowances transaction 
     * for an existing purchase order identified as, purchaseOrderId.
     * 
     * @param purchaseOrderId The id of a valid purchase order.
     * @throws VendorPurchasesException
     */
    void doVendorPurchaseReturnAllow(int purchaseOrderId) throws VendorPurchasesException;

    /**
     * Assigns a new status to a purchase order. 
     * 
     * @param poId The id of the target purchase order
     * @param newStatusId The id of status that is to be assigned to the purchase order.
     * @throws VendorPurchasesException
     */
    void setPurchaseOrderStatus(int poId, int newStatusId) throws VendorPurchasesException;

    /**
     * Creates a purchase order transaction.
     * 
     * @param po The purchase order
     * @param items Purchase order items
     * @return Transaction Id.
     * @throws VendorPurchasesException
     */
    int submitPurchaseOrder(PurchaseOrder po, List items) throws VendorPurchasesException;

    /**
     * Computes the total amount of a purchase order.
     * 
     * @param poId The Id of the target purchase order 
     * @return A decimal value.
     * @throws VendorPurchasesException
     */
    double calcPurchaseOrderTotal(int poId) throws VendorPurchasesException;

    /**
     * Cancels a purchase order.
     *    
     * @param poId The id of target purchase order
     * @throws VendorPurchasesException
     */
    void cancelPurchaseOrder(int poId) throws VendorPurchasesException;

    /**
     * Creates a purchases, returns, and allowances transaction for a purchase order.
     * 
     * @param poId The id of target purchase order
     * @param items The items that are to be returned.
     * @throws VendorPurchasesException  
     */
    void returnPurchaseOrder(int poId, List items) throws VendorPurchasesException;
}

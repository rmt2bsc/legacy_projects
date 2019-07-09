package com.api;

import java.util.List;

import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.VendorItems;
import com.bean.VwVendorItems;
import com.bean.VwPurchaseOrderList;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;

import com.util.PurchaseOrderException;

/**
 * 
 * @author RTerrell
 *
 */
public interface PurchasesApi extends BaseDataSource {
 
    /**
     * Finds a purchase order that is associated with value.
     * 
     * @param _poId
     * @return
     * @throws PurchaseOrderException
     */
    PurchaseOrder findPurchaseOrder(int _poId) throws PurchaseOrderException;
    
    /**
     * Finds a single purchase order item using _poItemId.
     * 
     * @param _poItemId  The Id of the purchase order item.
     * @return The purchase order item as {@link PurchaseOrderItems}
     * @throws PurchaseOrderException
     */
    PurchaseOrderItems findPurchaseOrderItem(int _poItemId) throws PurchaseOrderException;
    
    
    /**
     * Retrieves one vendor item object using vendorId and itemId
     * 
     * @param vendorId The id of the vendor
     * @param itemId The id of the inventory item
     * @return {@link VendorItems} object
     * @throws ItemMasterException
     */
    VendorItems findVendorItem(int vendorId, int itemId) throws PurchaseOrderException;
    

    /**
     * Retreives either the item_master version or the vendor_item version of an inventory item using vendor id and item id.
     * 
     * @param _vendorId  The vendor id
     * @param _itemId The id of the item to retrieve
     * @return {@link VwVendorItems}
     * @throws PurchaseOrderException
     */
    VwVendorItems findCurrentItemByVendor(int _vendorId, int _itemId) throws PurchaseOrderException;
    
    
    /**
     * 
     * @param poId
     * @return
     * @throws PurchaseOrderException
     */
    List findVendorItemPurchaseOrderItems(int poId) throws PurchaseOrderException;
    
    
    /**
     * Finds all items belonging to a purchase order identified as, value
     * 
     * @param _poId
     * @return An ArrayList of {@link PurchaseOrderItems}
     * @throws PurchaseOrderException
     */
    List findPurchaseOrderItems(int _poId) throws PurchaseOrderException;
    
    /**
     * Obtains the purchase order status based on the value of _poStatusId.
     * 
     * @param _poStatusId The id of the purchase order status to retrieve.
     * @return {@link PurchaseOrderStatus}
     * @throws PurchaseOrderException
     */
    PurchaseOrderStatus findPurchaseOrderStatus(int _poStatusId) throws PurchaseOrderException;
    
    /**
     * Obtains purchase order data pertaining to the current status using purchase order id identified as, value.
     * 
     * @param _poId
     * @return {@link VwPurchaseOrderList}
     * @throws PurchaseOrderException
     */
    VwPurchaseOrderList findCurrentPurchaseOrder(int _poId) throws PurchaseOrderException;

    /**
     * Obtains the current status of a purchase order.
     * 
     * @param _poId Is the id of the purchase order
     * @return Purchase order status  history object
     * @throws PurchaseOrderException
     */
    PurchaseOrderStatusHist findCurrentPurchaseOrderHistory(int _poId) throws PurchaseOrderException;
    
    
    /**
     * Gathers the status history of a purchase order.
     * 
     * @param _poId
     * @return ArrayList of {@link PurchaseOrderStatusHist}
     * @throws PurchaseOrderException
     */
    List findPurchaseOrderHistory(int _poId) throws PurchaseOrderException;
    /**
     * Creates a new or modifies an existing purchase order.  When _purchaseOrderId equals zero, a new purchase order is created.
     * When _purchaseOrderId greater than zero, an existing purchase order is modified.
     *     
     * @param _po  Purchase Order object.  Must be valid.
     * @param _items list of purchase order items.  Must be valid.
     * @return Id of a new purchase order when _po's id property equals zero or the total number of rows updated for existing 
     * purchase orders where _po's id property is greater thatn zero.
     * @throws PurchaseOrderException
     */
    int maintainPurchaseOrder(PurchaseOrder _po, List _items) throws PurchaseOrderException;
    
    /**
     * Adds _items to the existing list of purchase order items without performing a purcase order item refresh.
     * 
     * @param _po Purchase Order object.  Must be valid.
     * @param _items list of new purchase order items that will be appended to the existing list.  Must be valid.
     * @return Id of a new purchase order when _po's id property equals zero or the total number of rows updated for existing 
     * purchase orders where _po's id property is greater thatn zero.
     * @throws PurchaseOrderException
     */
    int addItemsToPurchaseOrder(PurchaseOrder _po, List _items) throws PurchaseOrderException;
    
    /**
     * Deletes a purchase order and all of its items from the database.  Purchase order can only be deleted 
     * from the system when in Quote status
     * 
     * @param _poId
     * @return Total number of rows effected by this transaction
     * @throws PurchaseOrderException If purchase order status is something other than "Quote".
     */
    int deletePurchaseOrder(int _poId) throws PurchaseOrderException;
    
    /**
     * Deletes one purchase order item from the database.
     * 
     * @param _poItemId Id of the purchase order item
     * @return The number of rows effected
     * @throws PurchaseOrderException
     */
    int deleteItem(int _poItemId) throws PurchaseOrderException;
    
    /**
     * Deletes all items belonging to a purchase order.
     * 
     * @param _poId Id of the purchase order which all items will be removed.
     * @return The total number of rows effected.
     * @throws PurchaseOrderException
     */
    int deleteAllItems(int _poId) throws PurchaseOrderException;
    
    /**
     * This method will perform a purchases, returns, and allowances transaction for an existing purchase order identified as, _purchaseOrderId.
     * 
     * @param _purchaseOrderId The id of a valid purchase order.
     * @throws PurchaseOrderException
     */
    void doVendorPurchaseReturnAllow(int _purchaseOrderId) throws PurchaseOrderException;

    /**
     * Assigns a new status to a purchase order and applies the changes to purchase order status history table in the database.   
     * Before the new status assignment, the current status is terminated by assigning an end date of the current day.   Since 
     * there is no logic implemented in this method to govern the movement of purchase order statuses, invoke method, 
     * verifyStatusChange(int, int), prior to this method in order to verify that moving to the new status is in alignment with the 
     * business rules of changing purchase order statuses. 
     * 
     * @param _poId The id of the target purchase order
     * @param __newStatusId The id of status that is to be assigned to the purchase order.
     * @throws PurchaseOrderException
     */
    void setPurchaseOrderStatus(int _poId, int _newStatusId) throws PurchaseOrderException;

 
    /**
     * This method begins the process of realizing the purchase order as a transaction.   Firstly, a purchase order transaction is 
     * created, secondly, the creditor is realizes the transaction, thirdly, the pusrchase order itself is associated with the 
     * transaction, and lastly, the status is changed to "Submitted".   The completion of the previous tasks are pending on the 
     * fact that the status change is truly legal base on current business rules.
     * creates a purchase order transaction.
     * 
     * @param _po
     * @param _items
     * @return Transaction Id.
     * @throws PurchaseOrderException
     */
    int submitPurchaseOrder(PurchaseOrder _po, List _items) throws PurchaseOrderException;
    
    
    /**
     * Obtains the total amount of a purchase order which may include 1) purchase order item total,  2) taxes, and 3) other fees.
     * 
     * @param _poId The Id of the target purchase order 
     * @return Total amount
     * @throws PurchaseOrderException
     */
    double calcPurchaseOrderTotal(int _poId) throws PurchaseOrderException;
    
    /**
     * This will cancel purchase orders.   The purchase order is required to be in "Quote" or "Submitted" status prior to 
     * cancellation, and cannot undergo a partial cancellation.   Inventory will not be pulled as a result of this transaction.
     *    
     * @param _poId The id of target purchase order
     * @throws PurchaseOrderException If current status cannot be cancelled/
     */
    void cancelPurchaseOrder(int _poId) throws PurchaseOrderException;
    
    /**
     * This method executes a purchases, returns, and allowances transaction which will allow one or more items of 
     * the purchase order to be returned.   It is required for the purchase order to be in "Received" or "Partially Received" 
     * status before a return is performed.   Inventory will be pulled for each item involved in this transaction.  The purchase 
     * order will be flagged with a status of "Returned" when the summation of quantity ordered minus quantity returned for 
     * each  item of the purchase order equals zero.   Otherwise, the purchase order will be placed in Partially Returned status. 
     * 
     * @param _poId The id of target purchase order
     * @param _items The items that are to be returned.
     * @throws PurchaseOrderException When the return quantity exceeds the quantity available for an item, database error occurs, 
     * or a system error occurs. 
     */
    void returnPurchaseOrder(int _poId, List _items) throws PurchaseOrderException;
}

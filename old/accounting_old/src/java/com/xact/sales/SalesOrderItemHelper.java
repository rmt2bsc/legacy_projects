package com.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
//import com.action.HttpBasicHelper;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

//import com.bean.CustomerWithName;
import com.bean.ItemMaster;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.Customer;
import com.bean.VwSalesorderItemsBySalesorder;
import com.bean.Xact;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;

import com.inventory.InventoryApi;
import com.inventory.ItemConst;
import com.inventory.InventoryFactory;
import com.inventory.ItemMasterException;

import com.util.SystemException;

/**
 * Provides services to manaage the items of a sales order.   This crux of this class is obtaining all
 *  items of a sales order _so and grouping each item to either a list of service items or merchandise items.
 * <p>
 * The service and merchandise item lists are contained in their own respective collections.
 * 
 * @author Roy Terrell
 *
 */
public class SalesOrderItemHelper extends AbstractActionHandler {
    public static final int SERVICE_ITEM_ID = 157;
    
    public static final int SERVICE_ITEM_TYPE_ID = 1;
    
    private InventoryApi itemApi;

    private Customer cust;

    private SalesOrder so;

    private List items;

    private List srvcItems;

    private List merchItems;

    private Xact xact;

    private Logger logger;

    /**
     * Default constructor
     *
     */
    public SalesOrderItemHelper() {
	super();
	this.srvcItems = new ArrayList();
	this.merchItems = new ArrayList();
	logger = Logger.getLogger(SalesOrderItemHelper.class);
    }

    /**
     * Constructor used to initialize a SalesOrderHelper object using _context and _request.
     * 
     * @param _context The context of the web app.
     * @param _request The HttpServletRequest object
     * @throws SystemException
     * @throws DatabaseException
     */
    public SalesOrderItemHelper(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
    }

    /**
     * Constructor used to initialize a SalesOrderHelper object using _context, _request, _customer, and _so.
     * 
     * @param _context The context of the web app.
     * @param _request The HttpServletRequest object
     * @param _customer Customer object
     * @param _so Sales Order object
     * @throws SystemException
     * @throws DatabaseException
     */
    public SalesOrderItemHelper(Context _context, Request _request, Customer _customer, SalesOrder _so) throws SystemException, DatabaseException {
	this(_context, _request);
	this.cust = _customer;
	this.so = _so;
    }

    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	this.srvcItems = new ArrayList();
	this.merchItems = new ArrayList();
	this.items = new ArrayList();
	logger = Logger.getLogger("SalesOrderItemHelper");
    }

    /**
     * Converts a collection of SalesOrderItems to an array of VwSalesorderItemsBySalesorder objects and separated 
     * each item to either a collection of service items or a collecion of merchandise items.   
     * <p>
     * The results of this operation can be obtained fro the following method calls: getSrvcItems() and getMerchItems().
     *   
     * @param _items a list of {@link SalesOrderItems} objects
     * @return The total number of items converted.
     * @throws SystemException
     * @see getSrvcItems, getMerchItems
     */
    public int packageItemsByTypes(List _items) throws SystemException {
	this.items.addAll(_items);
	VwSalesorderItemsBySalesorder soi[] = this.convertSalesOrderItems(_items);
	return this.packageItemsByTypes(soi);
    }

    /**
     * Creates one or more VwSalesorderItemsBySalesorder objects from a list of item numbers.   
     * <p>
     * For each item number contained in the _itemNo array, a corresponding ItemMaster data is obtained.
     * The results of this operation can be obtained fro the following method calls: getSrvcItems() and getMerchItems().
     *  
     * @param _itemNo - Item Master Id's as an Array of Strings.
     * @return The total number of items created.
     * @throws SystemException
     * @see getSrvcItems, getMerchItems
     */
    public int packageItemsByTypes(String itemNo[]) throws SystemException {
	VwSalesorderItemsBySalesorder soi[] = null;
	int tot = 0;
	int itemId = 0;

	if (itemNo == null) {
	    return tot;
	}

	tot = itemNo.length;
	soi = new VwSalesorderItemsBySalesorder[tot];
	for (int ndx = 0; ndx < tot; ndx++) {
	    try {
		itemId = Integer.parseInt(itemNo[ndx]);
	    }
	    catch (NumberFormatException e) {
		logger.log(Level.ERROR, "Item Number " + itemNo[ndx] + " could not be converted to an integer value.");
		itemId = 0;
	    }

	    // Create sales order item record using the item master.
	    soi[ndx] = this.convertItemMaster(itemId);
	    soi[ndx].setCustomerId(this.so.getCustomerId());
	    soi[ndx].setSalesOrderItemId(0);
	    soi[ndx].setSoId(0);

	    // Default item quantity to 1
	    soi[ndx].setOrderQty(1);
	} // end for
	return this.packageItemsByTypes(soi);
    }

    /**
     * Separates each item contained in _items to either a collection of service items or a collecion of merchandise items.
     * <p>
     * The results of this operation can be obtained fro the following method calls: getSrvcItems() and getMerchItems().
     *     
     * @param _items An array of {@link VwSalesorderItemsBySalesorder} objects
     * @return The total number of items created.
     * @throws SystemException
     * @see getSrvcItems, getMerchItems
     */
    private int packageItemsByTypes(VwSalesorderItemsBySalesorder _items[]) throws SystemException {
	int total = 0;
	if (_items == null) {
	    return total;
	}

	// Package items in separate ArrayList collections based on theit item types
	for (int ndx = 0; ndx < _items.length; ndx++) {
	    switch (_items[ndx].getItemTypeId()) {
	    case ItemConst.ITEM_TYPE_SRVC:
		this.srvcItems.add(_items[ndx]);
		break;
	    case ItemConst.ITEM_TYPE_MERCH:
		this.merchItems.add(_items[ndx]);
		break;
	    } // end switch
	    total += _items[ndx].getRetailPrice();
	} // end for  
	logger.log(Level.DEBUG, "Total of all items: " + total);
	return total;
    }

    /**
     * Converts one or more {@link SalesOrderItems} objects to a {@link VwSalesorderItemsBySalesorder} object.
     * 
     * @return An array of VwSalesorderItemsBySalesorder objects.
     */
    private VwSalesorderItemsBySalesorder[] convertSalesOrderItems(List _items) throws SystemException {
	VwSalesorderItemsBySalesorder vwSoi[] = null;
	SalesOrderItems soi = null;
	int tot = 0;

	if (_items == null) {
	    return null;
	}

	tot = _items.size();
	vwSoi = new VwSalesorderItemsBySalesorder[tot];

	try {
	    for (int ndx = 0; ndx < tot; ndx++) {
		soi = (SalesOrderItems) _items.get(ndx);
		vwSoi[ndx] = this.convertSalesOrderItems(soi);
		vwSoi[ndx].setCustomerId(this.cust.getCustomerId());
	    } // end for
	    return vwSoi;
	}
	catch (ClassCastException e) {
	    this.msg = "ClassCastException: Only objects of type SalesOrderItems can be converted and separated by type";
	    logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
    }

    /**
     * Converts a {@link SalesOrderItems} object to a VwSalesorderItemsBySalesorder
     * 
     * @param item Object of type SalesOrderItems
     * @return {@link  VwSalesorderItemsBySalesorder}
     * @throws SystemException
     */
    private VwSalesorderItemsBySalesorder convertSalesOrderItems(SalesOrderItems item) throws SystemException {
	VwSalesorderItemsBySalesorder soi = null;
	if (item == null) {
	    return null;
	}
	soi = this.convertItemMaster(item.getItemId());
	soi.setSalesOrderItemId(item.getSoItemId());
	soi.setSoId(item.getSoId());
	if (item.getItemNameOverride() != null && !item.getItemNameOverride().equals("")) {
	    soi.setItemName(item.getItemNameOverride());
	}
	// Use the initial mark up and unit cost values to caluclate retail for service items.
	if (soi.getItemTypeId() == SalesOrderItemHelper.SERVICE_ITEM_TYPE_ID) {
	    soi.setInitMarkup(item.getInitMarkup());
	    soi.setInitUnitCost(item.getInitUnitCost());
	    soi.setRetailPrice(soi.getInitMarkup() * soi.getInitUnitCost());
	}
	soi.setOrderQty(item.getOrderQty());
	return soi;
    }

    /**
     * Gets the VwSalesorderItemsBySalesorder equivalent to itemId.  <p>First itemId is used to obtain an 
     * item master object, and the item master object is converted to a VwSalesorderItemsBySalesorder object.
     * 
     * @param itemId The id of the item master object.
     * @return {@link VwSalesorderItemsBySalesorder}
     * @throws SystemException
     */
    private VwSalesorderItemsBySalesorder convertItemMaster(int itemId) throws SystemException {
	VwSalesorderItemsBySalesorder soi = null;
	ItemMaster im = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.itemApi = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    im = (ItemMaster) this.itemApi.findItemById(itemId);
	    soi = new VwSalesorderItemsBySalesorder();
	    soi.setItemId(im.getItemId());
	    soi.setItemName(im.getDescription());
	    soi.setCreditorId(im.getCreditorId());
	    soi.setVendorItemNo(im.getVendorItemNo());
	    soi.setItemTypeId(im.getItemTypeId());
	    soi.setItemSerialNo(im.getItemSerialNo());
	    soi.setQtyOnHand(im.getQtyOnHand());
	    soi.setMarkup(im.getMarkup());
	    soi.setUnitCost(im.getUnitCost());
	    soi.setRetailPrice(soi.getUnitCost() * soi.getMarkup());
	    return soi;
	}
	catch (ItemMasterException e) {
	    throw new SystemException(e.getMessage());
	}
	finally {
		tx.close();
		this.itemApi = null;
		tx = null;
	}
    }

    /**
     *  Retrieves all master items (service and merchandise) which have not been associated with an 
     *  existing sales order.   All items are packaged and separated by type into an ArrayList of 
     *  {@link VwSalesorderItemsBySalesorder} objecst and are transmitted to the client via the 
     *  HttpServletRequest object identified as "service" and "merchandise".   {@link CustomerWithName} 
     *   and {@link SalesOrder} data is sent to the client via the HttpServleRequest object identified as "customer" 
     *   and "salesorder", respectively.
     *     
     * @throws SystemException
     */
    public void setupAvailSalesOrderItems() throws SystemException {
	String criteria = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.itemApi = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Filter out items already selected if sales order has been assigned an id.
	    if (this.so.getSoId() > 0) {
		criteria = " and item_id not in ((select item_id from sales_order_items where so_id = " + this.so.getSoId() + "))";
	    }
	    else {
		criteria = "";
	    }

	    this.srvcItems = (List) itemApi.findItem("item_type_id = 1" + criteria);
	    this.merchItems = (List) itemApi.findItem("item_type_id = 2" + criteria);
	    if (this.srvcItems == null) {
		this.srvcItems = new ArrayList();
	    }
	    if (this.merchItems == null) {
		this.merchItems = new ArrayList();
	    }
	    return;
	}
	catch (ItemMasterException e) {
	    throw new SystemException(e);
	}
	finally {
		tx.close();
		this.itemApi = null;
		tx = null;
	}
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
     * Gets the list of sales order items.
     * 
     * @return A complete list of service and merchandise items of type, {@link alesOrderItems}.
     */
    public List getItems() {
	return this.items;
    }

    /**
     * Gets service items
     * @return ArrayList
     */
    public List getSrvcItems() {
	return this.srvcItems;
    }

    /**
     * Gets merchandise items
     * @return
     */
    public List getMerchItems() {
	return this.merchItems;
    }

    /**
     * Gets the list of sales order items.
     * 
     * @return A complete list of service and merchandise items of type, {@link VwSalesorderItemsBySalesorder}.
     */
    public List getExtItems() {
	ArrayList combinedItems = new ArrayList();
	combinedItems.addAll(this.srvcItems);
	combinedItems.addAll(this.merchItems);
	return combinedItems;
    }

    protected void receiveClientData() throws ActionHandlerException {
    }

    protected void sendClientData() throws ActionHandlerException {
    }

    public void add() throws ActionHandlerException {
    }

    public void edit() throws ActionHandlerException {
    }

    public void save() throws ActionHandlerException {
    }

    public void delete() throws ActionHandlerException {
    }

}
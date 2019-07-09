package com.inventory;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.ItemMaster;
import com.bean.ItemMasterStatusHist;
import com.bean.VwItemMaster;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;

import com.util.SystemException;

/**
 * This class provides functionality for obtaining Vendor Purchases related data
 * from the client's request object.
 * 
 * @author Roy Terrell
 * 
 */
public class HttpItemMasterHelper extends AbstractActionHandler {
    private VwItemMaster itemExt;

    private ItemMaster item;

    private ItemMasterStatusHist imsh;

    private List imshList;

    private Logger logger;

    /**
     * Default constructor
     * 
     */
    public HttpItemMasterHelper() {
	super();
	logger = Logger.getLogger("HttpItemMasterHelper");
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
    public HttpItemMasterHelper(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	logger = Logger.getLogger(HttpItemMasterHelper.class);
	this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Combines the process of probing the client's HttpServletRequest object for 
     * ItemMaster, VwItemMaster, and ItemMasterStatusHist data.  
     * 
     * @throws SystemException
     */
    public void getHttpCombined() throws SystemException {
	this.item = this.getHttpItem();
	this.imsh = this.getHttpCurrentItemStatusHist();
	this.itemExt = this.getHttpItemExt();
	return;
    }

    /**
     * Obtains base item master data from the client's request.
     *  
     * @return {@link ItemMaster}
     * @throws SystemException
     */
    public ItemMaster getHttpItem() throws SystemException {
	ItemMaster obj = InventoryFactory.createItemMaster();
	String temp = null;
	String subMsg = null;
	boolean listPage = true;
	int itemId = 0;

	// Determine if we are coming from a page that presents data as a list
	// or as a single record.
	// Get selected row number from client page. If this row number exist,
	// then we are coming from page that contains lists of records.
	subMsg = "Item master id could not be identified from client";
	temp = this.request.getParameter("ItemId" + this.selectedRow);
	if (temp == null) {
	    listPage = false;
	}
	if (!listPage) {
	    // Get order id for single row
	    temp = this.request.getParameter("ItemId");
	}

	// Validate value
	try {
	    itemId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    logger.log(Level.INFO, subMsg);
	    this.item = obj;
	    return obj;
	}

	// Get Purchase Order object
	try {
	    InventoryFactory.packageBean(this.request, obj);
	    this.item = obj;
	    this.item.setItemId(itemId);
	    return obj;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw e;
	}
    }

    /**
     * Obtains the current status history of an item master.
     * 
     * @return {@link ItemMasterStatusHist} c
     * @throws SystemException
     */
    public ItemMasterStatusHist getHttpCurrentItemStatusHist() throws SystemException {
	ItemMasterStatusHist obj = InventoryFactory.createItemMasterStatusHist();
	String temp = null;
	String subMsg = null;
	boolean listPage = true;
	int imshId = 0;

	// Determine if we are coming from a page that presents data as a list
	// or as a single record.
	// Get selected row number from client page. If this row number exist,
	// then we are coming from page that contains lists of records.
	subMsg = "Item Master Status History Id could not be identified from client";
	temp = this.request.getParameter("ItemStatusId" + this.selectedRow);
	if (temp == null) {
	    listPage = false;
	}
	if (!listPage) {
	    // Get order id for single row
	    temp = this.request.getParameter("ItemStatusId");
	}

	// Validate value
	try {
	    imshId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    logger.log(Level.INFO, subMsg);
	    this.imsh = obj;
	    return obj;
	}

	// Get Creditor/Vendor object
	try {
	    InventoryFactory.packageBean(this.request, obj);
	    this.imsh = obj;
	    this.imsh.setItemStatusHistId(imshId);
	    return obj;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw e;
	}
    }

    /**
     * Obtains base item master View Extension data from the client's request.
     * 
     * @return {@link VwItemMaster}
     * @throws SystemException
     */
    public VwItemMaster getHttpItemExt() throws SystemException {
	VwItemMaster obj = InventoryFactory.createItemMasterView();
	String temp = null;
	String subMsg = null;
	boolean listPage = true;
	int itemId = 0;

	// Determine if we are coming from a page that presents data as a list
	// or as a single record.
	// Get selected row number from client page. If this row number exist,
	// then we are coming from page that contains lists of records.
	subMsg = "Item master id could not be identified from client";
	temp = this.request.getParameter("ItemId" + this.selectedRow);
	if (temp == null) {
	    listPage = false;
	}
	if (!listPage) {
	    // Get order id for single row
	    temp = this.request.getParameter("ItemId");
	}

	// Validate value
	try {
	    itemId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    logger.log(Level.INFO, subMsg);
	    this.itemExt = obj;
	    return obj;
	}

	// Get Purchase Order object
	try {
	    InventoryFactory.packageBean(this.request, obj);
	    this.itemExt = obj;
	    this.itemExt.setId(itemId);
	    return obj;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw e;
	}
    }

    /**
     * Retrieves item master data from the database with the assumption that 
     * the key values internal to this class is appropriately initialized.
     * 
     * @throws ActionHandlerException
     */
    public void refreshData() throws ActionHandlerException {
	this.refreshData(this.item.getItemId());
	return;
    }

    /**
     * Retrieves item master data from the database using itemId.  A database 
     * retrieval is performed provided that itemId is greater than zero. 
     * Otherwise, new objects related to the item master module are instantiated.
     * 
     * @param itemId The id of the inventory item master to obtain.
     * @throws ActionHandlerException
     */
    public void refreshData(int itemId) throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	InventoryApi api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    if (itemId > 0) {
		// Get item master view record.
		this.item = (ItemMaster) api.findItemById(itemId);
		// Get item master view record.
		this.itemExt = (VwItemMaster) api.findItemViewById(itemId);
		// Get item master current status.
		this.imsh = (ItemMasterStatusHist) api.findCurrentItemStatusHist(itemId);
		// Get item's status history.
		this.imshList = (List) api.findItemStatusHistByItemId(itemId);
	    }
	    else {
		// Create item master view record.
		this.item = InventoryFactory.createItemMaster();
		// Create item master view record.
		this.itemExt = InventoryFactory.createItemMasterView();
		// Create item master current status.
		this.imsh = InventoryFactory.createItemMasterStatusHist();
		// Create item's status history.
		this.imshList = new ArrayList();
	    }
	    return;
	}
	catch (ItemMasterException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	finally {
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Gets item master history as a list of {@link ItemMasterStatusHist} objects
     * 
     * @return the ArrayList
     */
    public List getImshList() {
	return imshList;
    }

    /**
     * Gets the item master object.
     * 
     * @return the {@link ItemMaster}
     */
    public ItemMaster getItem() {
	return item;
    }

    /**
     * Gets the item master extension view object.
     * 
     * @return {@link VwItemMaster}
     */
    public VwItemMaster getItemExt() {
	return itemExt;
    }

    
    /**
     * @param itemExt the itemExt to set
     */
    public void setItemExt(VwItemMaster itemExt) {
        this.itemExt = itemExt;
    }

    /**
     * Gets the current item master status of the item master.
     * 
     * @return {@link ItemMasterStatusHist}
     */
    public ItemMasterStatusHist getImsh() {
	return imsh;
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
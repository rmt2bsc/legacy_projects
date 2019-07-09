package com.inventory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.ItemMaster;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

/**
 * This class provides action handlers to serve Item Master Search page requests. 
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterEditAction extends ItemMasterAction {
    private static final String COMMAND_SAVE = "Inventory.Edit.save";

    private static final String COMMAND_ADD = "Inventory.Edit.add";

    private static final String COMMAND_DELETE = "Inventory.Edit.delete";

    private static final String COMMAND_BACK = "Inventory.Edit.back";

    private Logger logger;

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public ItemMasterEditAction() throws SystemException {
	this.logger = Logger.getLogger("ItemMasterEditAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with 
     *   this action handler
     * @throws SystemException
     */
    public ItemMasterEditAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Prepares the user to navigate back to the search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	ItemMasterSearchAction action = new ItemMasterSearchAction();
	action.processRequest(this.request, this.response, ItemMasterSearchAction.COMMAND_LIST);
    }

    /**
     * Creates a new VwItemMaster view object and retrieves a list of vendors.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	super.add();
	this.itemHelper.setItemExt(InventoryFactory.createVwItemMaster());
	this.vendorList = this.getVendorList();
	return;
    }
    
    
    /**
     * Sends vendor list to the client.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.request.setAttribute(ItemConst.CLIENT_DATA_VENDORLIST, this.vendorList);
    }
    
    /**
     * Persist the changes made to an item master to the database.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	InventoryApi api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.maintainItemMaster(this.itemHelper.getItem(), null);
	    tx.commitUOW();
	    this.itemHelper.refreshData();
	    this.vendorList = this.getVendorList();
	    this.msg = "Item was saved successfully";
	    return;
	}
	catch (ItemMasterException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Deletes an item master from the system.   If the item targeted for deletion is 
     * linked to one or more dependent entities, then the item is marked inactive.
     * 
     * @throws ActionHandlerException general database errors
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	InventoryApi api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int itemId = 0;
	ItemMaster im = null;
	boolean deleteFailed = false;

	this.itemHelper.refreshData();
	this.vendorList = this.getVendorList();
	itemId = this.itemHelper.getItem().getItemId();
	im = this.itemHelper.getItem();

	try {
	    api.deleteItemMaster(itemId);
	    tx.commitUOW();
	    this.msg = "Item was deleted from the database successfully";
	}
	catch (ItemMasterException e) {
	    tx.rollbackUOW();
	    deleteFailed = true;
	}

	if (deleteFailed) {
	    try {
		im.setActive(0);
		api.maintainItemMaster(im, null);
		tx.commitUOW();
		this.msg = "Item was not deleted from the database.   Instead marked inactive since item is linked to one or more orders.";
	    }
	    catch (ItemMasterException e) {
		this.msg = e.getMessage();
		logger.log(Level.ERROR, this.msg);
		tx.rollbackUOW();
	    }
	}
	tx.close();
	api = null;
	tx = null;
	return;
    }
}
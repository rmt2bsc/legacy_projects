package com.xact.purchases.vendor.inventory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Creditor;
import com.bean.VendorItems;
import com.bean.ItemMaster;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorException;
import com.gl.creditor.CreditorExt;
import com.gl.creditor.CreditorFactory;

import com.inventory.InventoryApi;
import com.inventory.InventoryFactory;
import com.inventory.ItemMasterException;

import com.util.SystemException;

import com.xact.purchases.vendor.VendorPurchasesSearchAction;

/**
 * This class provides action handlers to respond to an associated 
 * controller for searching, adding, deleting, and eidting Vendor/Item 
 * associations.
 * 
 * @author Roy Terrell
 *
 */
public class VendorItemAssocAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_VENDORITEMS = "PurchasesVendor.Search.vendoritemview";

    private static final String COMMAND_ASSIGN = "PurchasesVendor.ItemAssoc.vendoritemassign";

    private static final String COMMAND_REMOVE = "PurchasesVendor.ItemAssoc.vendoritemunassign";

    private static final String COMMAND_EDIT = "PurchasesVendor.ItemAssoc.venditemedit";

    private static final String COMMAND_OVERIDE_ADD = "PurchasesVendor.ItemAssoc.venditemoverride";

    private static final String COMMAND_OVERIDE_REMOVE = "PurchasesVendor.ItemAssoc.removevenditemoverride";

    private static final String COMMAND_SEARCH = "PurchasesVendor.ItemAssoc.search";

    protected InventoryApi api;

    protected Logger logger;

    protected Creditor creditor;

    protected List availList;

    protected List asgnList;

    protected CreditorExt vendor;

    protected ItemMaster im;

    protected VendorItems vi;

    protected HttpVendorItemHelper httpHelper;

    protected String command;

    /**
     * Default constructor.
     * 
     * @throws SystemException
     */
    public VendorItemAssocAction() throws SystemException {
	super();
	logger = Logger.getLogger("VendorItemAssocAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public VendorItemAssocAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(context, request);
    }

    /**
     * Initializes this object using _conext and request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
	try {
//	    this.api = InventoryFactory.createApi(this.dbConn, request);
	    httpHelper = new HttpVendorItemHelper(context, request);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SystemException(e.getMessage());
	}
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
	if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_VENDORITEMS)) {
	    this.doVendorItems();
	}
	if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_ASSIGN)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_REMOVE)) {
	    this.unassignItems();
	}
	if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_OVERIDE_ADD)) {
	    this.activateItemOverride();
	}
	if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_OVERIDE_REMOVE)) {
	    this.deActivateItemOverride();
	}
	if (command.equalsIgnoreCase(VendorItemAssocAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
    }


    /**
     * Displays the Vendor-Item association maintenance page which retreives all items 
     * currently associated with the vendor and all items from inventory that are not 
     * assigned to the vendor.
     *
     *@throws ActionHandlerException  When a general database error occurs.
     */
    protected void doVendorItems() throws ActionHandlerException {
	String selectedVendor = this.request.getParameter("qry_CreditorId");
	if (selectedVendor == null || selectedVendor.equals("")) {
	    throw new ActionHandlerException("Vendor must be selected in order to manage/view vendor items");
	}
	this.receiveClientData();
	this.sendClientData();
    }
    
    /**
     * Retreives the items that are available to the vendor and those items that assigned 
     * to the vendor which the data is passed to the clients Request object for 
     * presentation.
     * 
     * @throws ActionHandlerException
     */
    private void refreshData() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	this.api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.creditor = this.httpHelper.getHttpVendor();
	    // Get items assigned to Vendor
	    this.asgnList = (List) this.api.findVendorAssignItems(this.creditor.getCreditorId());
	    if (this.asgnList == null) {
		this.asgnList = new ArrayList();
	    }
	    // Get items item not assigned to Vendor
	    this.availList = (List) this.api.findVendorUnassignItems(this.creditor.getCreditorId());
	    if (this.availList == null) {
		this.availList = new ArrayList();
	    }
	    // Getvendor details
	    this.vendor = this.getVendorDetails(this.creditor.getCreditorId());
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		tx.close();
		this.api = null;
		tx = null;
	}
    }

    /**
     * Begins the process of assigning one or more inventory items to a vendor.   This 
     * method is expecting the following client variables to be accessible from the 
     * Request object for processing:  "VendorId" and "AvailItems".
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	this.receiveClientData();
	this.creditor = this.httpHelper.getCreditor();
	int items[] = this.httpHelper.getUnassignItems();

	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.assignVendorItems(this.creditor.getCreditorId(), items);
	    tx.commitUOW();
	    return;
	}
	catch (ItemMasterException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
		this.api = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Begins the process of unassigning one or more inventory items from a vendor.   
     * This method is expecting the following client variables to be accessible from 
     * the Request object for processing:  "VendorId" and "AssignItems".
     * 
     * @return
     * @throws ActionHandlerException
     */
    protected void unassignItems() throws ActionHandlerException {
	this.receiveClientData();
	this.creditor = this.httpHelper.getCreditor();
	int items[] = this.httpHelper.getAssignItems();

	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.removeVendorItems(this.creditor.getCreditorId(), items);
	    tx.commitUOW();
	    return;
	}
	catch (ItemMasterException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.sendClientData();
	    this.api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Drives the process of editing an assigned vendor inventory item.      Only one item can be edited at a time. 
     * 
     * @throws ItemMasterException If more than one item is found for processing.
     */
    public void edit() throws ActionHandlerException {
	int items[];
	int vendorId = this.httpHelper.getCreditor().getCreditorId();
	items = this.httpHelper.getAssignItems();
	if (items.length > 1) {
	    throw new ActionHandlerException("Only one assigned vendor item can be edited at a time");
	}
	this.setUpVendorItemEdit(vendorId, items[0]);
	return;
    }

    /**
     * Uses _vendorId and _itemId to retrieve the vendor, item master, and vendor item 
     * data objects to be used by the client. 
     * <p>
     * The following objects are set on the request object identified as "vendor", 
     * "itemmaster", and "vendoritem", respectively: 
     * {@link VwCreditorBusiness}, {@link ItemMaster} and {@link VendorItems}. 
     * 
     * @param _vendorId The vendor id
     * @param _itemId The item master id
     * @throws ActionHandlerException
     */
    protected void setUpVendorItemEdit(int _vendorId, int _itemId) throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	this.api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.vendor = this.getVendorDetails(_vendorId);
	    this.vi = (VendorItems) this.api.findVendorItem(_vendorId, _itemId);
	    this.im = (ItemMaster) this.api.findItemById(_itemId);
	}
	catch (ItemMasterException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
		this.api = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Obtains the vendor detail data derived from the creditor and business tables.
     * 
     * @param _vendorId The id of the vendor
     * @return {@link CreditorExt} or null if an error occurs.
     */
    private CreditorExt getVendorDetails(int vendorId) {
	CreditorExt cred = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi credApi = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    cred = (CreditorExt) credApi.findCreditorBusiness(vendorId);
	    return cred;
	}
	catch (CreditorException e) {
	    return null;
	}
	finally {
		credApi = null;
		tx.close();
		tx = null;
	}
    }

    protected void activateItemOverride() throws ActionHandlerException {
	this.receiveClientData();
	int items[] = this.httpHelper.getAssignItems();

	if (items == null) {
	    throw new ActionHandlerException("At least one assigned item must be selected in order to activate item override");
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.addInventoryOverride(this.httpHelper.getCreditor().getCreditorId(), items);
	    tx.commitUOW();
	    return;
	}
	catch (ItemMasterException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.sendClientData();
	    this.api = null;
	    tx.close();
	    tx = null;
	}
    }

    protected void deActivateItemOverride() throws ActionHandlerException {
	this.receiveClientData();
	int items[] = this.httpHelper.getAssignItems();

	if (items == null) {
	    throw new ActionHandlerException("At least one assigned item must be selected in order to deactivate item override");
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = InventoryFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.removeInventoryOverride(this.httpHelper.getCreditor().getCreditorId(), items);
	    tx.commitUOW();
	    return;
	}
	catch (ItemMasterException e) {
	    System.out.println(e.getMessage());
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.sendClientData();
	    this.api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Stub method
     * 
     * @throws ActionHandlerException
     */
    protected void doSearch() throws ActionHandlerException {
	VendorPurchasesSearchAction action = new VendorPurchasesSearchAction();
	action.processRequest(this.request, this.response, VendorPurchasesSearchAction.COMMAND_LIST);
    }

    /**
     * Stub method.   
     *  
     * @throws ItemMasterException
     */
    public void delete() throws ActionHandlerException {
	return;
    }

    /**
     * Stub method.   
     *  
     * @throws ItemMasterException
     */
    public void save() throws ActionHandlerException {
	return;
    }

    /**
     * Obtains vendor item values from the UI components.
     */
    protected void receiveClientData() throws ActionHandlerException {
	try {
	    this.httpHelper.getHttpCombined();
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    protected void sendClientData() throws ActionHandlerException {
	this.refreshData();
	this.request.setAttribute("vendor", this.vendor);
	this.request.setAttribute("availList", this.availList);
	this.request.setAttribute("assignList", this.asgnList);
	this.request.setAttribute("itemmaster", this.im);
	this.request.setAttribute("vendoritem", this.vi);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

}
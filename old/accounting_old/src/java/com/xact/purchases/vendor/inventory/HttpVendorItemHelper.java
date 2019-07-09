package com.xact.purchases.vendor.inventory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.bean.Creditor;

import com.controller.Context;
import com.controller.Request;

import com.gl.creditor.CreditorFactory;

import com.util.SystemException;
import com.xact.HttpXactHelper;

/**
 * This class provides action handlers to respond to an associated 
 * controller for searching, adding, deleting, and eidting 
 * Vendor/Item associations.
 * 
 * @author Roy Terrell
 *
 */
public class HttpVendorItemHelper extends HttpXactHelper {
    private static Logger logger = Logger.getLogger(HttpVendorItemHelper.class);

    private Creditor creditor;

    private int assignItems[];

    private int unassignItems[];

    /**
     * Default constructor
     * 
     * @throws SystemException
     */
    public HttpVendorItemHelper() throws SystemException {
	super();
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public HttpVendorItemHelper(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.className = "VendorItemAction";
    }

    /**
     * Initializes this object using _conext and request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
    }

    /**
     * Retreives values from the UI pertaining to vendor, vendor selected unassigned items 
     * and vendor selected assigned items.    
     * 
     * @throws XactException
     */
    public void getHttpCombined() throws SystemException {
	super.getHttpCombined();
	this.creditor = this.getHttpVendor();
	this.unassignItems = this.getHttpSelectedItems("UnAssignedtems");
	this.assignItems = this.getHttpSelectedItems("AssignedItems");
    }

    /**
     * Attempts to obtain basic data pertaining to a vendor from the
     * client JSP. This method requires that the client identifies the creditor
     * as either qry_VendorId, qry_VendorIdXXX, VendorId, or VendorIdXXX.
     * 
     * @return {@link Creditor} containing the data retrieved from the client or
     *         a newly initialized Creditor object when the creditor id is not
     *         found.
     * @throws SalesOrderException
     */
    public Creditor getHttpVendor() throws SystemException {
	Creditor obj = CreditorFactory.createCreditor();
	String temp = null;
	String subMsg = null;
	boolean listPage = true;
	int creditorId = 0;

	// Determine if we are coming from a page that presents data as a list
	// or as a single record.
	// Get selected row number from client page. If this row number exist,
	// then we are coming from page that contains lists of records.
	subMsg = "Vendor id could not be identified from client";
	temp = this.request.getParameter("qry_CreditorId" + this.selectedRow);
	if (temp == null) {
	    temp = this.request.getParameter("CreditorId" + this.selectedRow);
	}
	if (temp == null) {
	    listPage = false;
	}
	if (!listPage) {
	    // Get order id for single row
	    temp = this.request.getParameter("qry_CreditorId");
	    if (temp == null) {
		temp = this.request.getParameter("CreditorId");
	    }
	}

	// Validate value
	try {
	    creditorId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    logger.log(Level.INFO, subMsg);
	    this.creditor = obj;
	    return obj;
	}

	// Get Creditor/Vendor object
	try {
	    CreditorFactory.packageBean(this.request, obj);
	    this.creditor = obj;
	    this.creditor.setCreditorId(creditorId);
	    return obj;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw e;
	}
    }

    /**
     * Obtains the item master id from the http client.
     * 
     * @return The id of the inventory item
     * @throws ActionHandlerException
     */
    protected int getHttpItemMasterId() throws ActionHandlerException {
	String strItemId = this.request.getParameter("ItemId");
	int itemId = 0;
	if (strItemId == null) {
	    throw new ActionHandlerException("Vendor Item Id is required");
	}

	try {
	    itemId = Integer.parseInt(strItemId);
	    return itemId;
	}
	catch (NumberFormatException e) {
	    throw new ActionHandlerException("An invalid Item Master number has been supplied");
	}
    }

    /**
     * Obtains one or more Inventory item id's from the client's Request object.
     * 
     * @param _property The name of the property containing the inventory item id's
     * @return An integer array of selected item id's
     * @throws SystemException If the client did not select one or more items, or one or more item id's are invalid.
     */
    public int[] getHttpSelectedItems(String _property) throws SystemException {
	String values[] = this.request.getParameterValues(_property);

	if (values == null) {
	    return null;
	    //throw new SystemException("One or more items must be selected");
	}

	int items[] = new int[values.length];
	for (int ndx = 0; ndx < values.length; ndx++) {
	    try {
		items[ndx] = Integer.parseInt(values[ndx]);
	    }
	    catch (NumberFormatException e) {
		throw new SystemException("One or more of the selected items are invalid");
	    }
	}
	return items;
    }

    /**
     * @return the creditor
     */
    public Creditor getCreditor() {
	return creditor;
    }

    /**
     * @return the selectedItems
     */
    public int[] getAssignItems() {
	return assignItems;
    }

    /**
     * @return the removeItems
     */
    public int[] getUnassignItems() {
	return unassignItems;
    }

}
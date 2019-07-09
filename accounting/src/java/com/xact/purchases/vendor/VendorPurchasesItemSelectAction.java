package com.xact.purchases.vendor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.Creditor;
import com.bean.PurchaseOrder;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

/**
 * This class provides action handlers to respond to an associated controller for 
 * adding items to a purchase order. 
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesItemSelectAction extends VendorPurchasesAction {
    private static final String COMMAND_ITEMSELECT = "PurchasesVendor.ItemSelect.savenewitem";

    private static final String COMMAND_SEARCH = "PurchasesVendor.ItemSelect.search";

    private Logger logger;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesItemSelectAction() {
	super();
	logger = Logger.getLogger("VendorPurchasesItemSelectAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public VendorPurchasesItemSelectAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);

    }

    /**
     * Initializes this object using _conext and request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
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
	if (command.equalsIgnoreCase(VendorPurchasesItemSelectAction.COMMAND_ITEMSELECT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(VendorPurchasesItemSelectAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
    }

    /**
     * Handler method that responds to the client's request to recall purchase order search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doSearch() throws ActionHandlerException {
	this.doSearchList();
	return;
    }

    /**
     * Gathers key data (purchase order, vendor, and selected items) from the client's request 
     * and associates the new items to the purchase order.  This new associations is persisted
     * in the database for further processing.  
     * 
     * @throws ActionHandlerException General database errors.
     */
    public void edit() throws ActionHandlerException {
	// At this point, the following items should be valid.
	PurchaseOrder po = this.httpPoHelper.getPo();
	Creditor vendor = this.httpPoHelper.getCreditor();
	List newItems = this.httpPoHelper.getNewPoItems();
	int poId = 0;

	// Call api method to add Items to purchase order
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    poId = this.api.addItemsToPurchaseOrder(po, newItems);
	    tx.commitUOW();
	    this.msg = "One or more items were added to the purchase order successfully";
	    this.logger.log(Level.DEBUG, this.msg);
	    return;
	}
	catch (VendorPurchasesException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.httpPoHelper.retrievePurchaseOrder(poId, vendor.getCreditorId());
	    this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null;
	}
    }

}
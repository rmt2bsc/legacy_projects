package com.xact.purchases.vendor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.PurchaseOrder;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;



/**
 * This class provides action handlers to respond to an associated controller for 
 * viewing, editing, finalization, and cancellation of a purchase order and its details. 
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesDetailAction extends VendorPurchasesAction {
    private static final String COMMAND_ADDITEM = "PurchasesVendor.Details.additem";

    private static final String COMMAND_SAVE = "PurchasesVendor.Details.save";

    private static final String COMMAND_SEARCH = "PurchasesVendor.Details.search";

    private static final String COMMAND_CANCEL = "PurchasesVendor.Details.cancel";

    private static final String COMMAND_FINALIZE = "PurchasesVendor.Details.finalize";

    private Logger logger;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesDetailAction() {
	super();
	logger = Logger.getLogger("VendorPurchasesDetailAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public VendorPurchasesDetailAction(Context context, Request request) throws SystemException, DatabaseException {
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
	if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_ADDITEM)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_FINALIZE)) {
	    this.doFinalize();
	}
	if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_SEARCH)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(VendorPurchasesDetailAction.COMMAND_CANCEL)) {
	    this.doCancel();
	}
    }

    /**
     * Saves the current state of the purchase order.  After successful save, this method 
     * gathers the credit/vendor items that are currently not assigned to the purchase 
     * order in preparation to invoke Purchase Order Item Select page.
     * 
     * @throws ActionHandlerException General database errors.
     */
    public void add() throws ActionHandlerException {
	this.receiveClientData();

	// At this point, the following items should be valid.
	PurchaseOrder po = this.httpPoHelper.getPo();
	List items = this.httpPoHelper.getPoItems();
	List availItems = null;

	// Save Purchase Order
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.savePurchaseOrder(po, items);
	    tx.commitUOW();

	    // Get credit/vendor items that are currently not assigned to the purchase 
	    // order ino rder to be used on the Item Select page
	    availItems = (List) this.api.getPurchaseOrderAvailItems(po.getCreditorId(), po.getPoId());
	    if (availItems == null) {
		availItems = new ArrayList();
	    }
	    this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_AVAIL_ITEMS, availItems);
	    return;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.httpPoHelper.retrievePurchaseOrder(po.getPoId(), po.getCreditorId());
	    this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null;
	}
    }

    /**
     * Saves the current state of the purchase order.  
     * 
     * @throws ActionHandlerException General database errors.
     */
    public void save() throws ActionHandlerException {
	// At this point, the following items should be valid.
	PurchaseOrder po = this.httpPoHelper.getPo();
	List items = this.httpPoHelper.getPoItems();

	// Save Purchase Order
	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.savePurchaseOrder(po, items);
	    tx.commitUOW();
	    this.msg = "Purchase Order was updated successfully";
	    return;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.httpPoHelper.retrievePurchaseOrder(po.getPoId(), po.getCreditorId());
	    this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null;
	}
    }

    /**
     * Drives the process of finalizing a purchase order and creating the purchase 
     * order transaction.  Functionality included in this process would be retrieving 
     * client data, invoication of purchase order finalization, and sending the results 
     * back to the client.
     * 
     * @throws ActionHandlerException
     */
    public void doFinalize() throws ActionHandlerException {
	this.receiveClientData();
	// At this point, the following items should be valid.
	PurchaseOrder po = this.httpPoHelper.getPo();
	List items = this.httpPoHelper.getPoItems();
	this.finalizePurchaseOrder(po, items);
	// Prepare data to be displayed on confirmation page
	this.httpPoHelper.retrievePurchaseOrder(po.getPoId(), po.getCreditorId());
	this.sendClientData();
    }

    /**
     * Drives the process of finalizing a purchase order and creating the purchase 
     * order transaction.
     * 
     * @throws ActionHandlerException
     */
    private void finalizePurchaseOrder(PurchaseOrder po, List items) throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.submitPurchaseOrder(po, items);
	    tx.commitUOW();
	    this.msg = "Purchase Order was submitted successfully";
	    return;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.httpPoHelper.retrievePurchaseOrder(po.getPoId(), po.getCreditorId());
	    this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null;
	}
    }

    /**
     * Applies the changes of an existing purchase order and its detail items to the database 
     * using data obtained from the client's HttpServletRequest object.
     * 
     * @throws ActionHandlerException
     * @throws DatabaseException
     */
    private void savePurchaseOrder(PurchaseOrder po, List items) throws ActionHandlerException {
	try {
	    this.api.maintainPurchaseOrder(po, items);
	    return;
	}
	catch (VendorPurchasesException e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Drives the Purchase Order Cancellation process.  After purchase order is 
     * successfull cancelled, the process refreshes purchase order data that is 
     * to be sent to the client. 
     * 
     * @throws ActionHandlerException
     */
    public void doCancel() throws ActionHandlerException {
	this.receiveClientData();
	PurchaseOrder po = this.httpPoHelper.getPo();
	this.cancelPurchaseOrder(po);
	// Prepare data to be displayed on confirmation page
	this.httpPoHelper.retrievePurchaseOrder(po.getPoId(), po.getCreditorId());
	this.sendClientData();
    }

    /**
     * Cancels the purchase order.
     * 
     * @param po The purchase order to be cancelled.
     * @throws ActionHandlerException
     */
    private void cancelPurchaseOrder(PurchaseOrder po) throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.api.cancelPurchaseOrder(po.getPoId());
	    tx.commitUOW();
	    this.msg = "Purchase Order was cancelled successfully";
	    return;
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, e.getMessage());
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		this.api.close();
	    tx.close();
	    this.api = null;
	    tx = null;
	}
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#doBack()
     */
    @Override
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	this.doSearchList();
    }

}
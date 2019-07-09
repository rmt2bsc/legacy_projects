package com.xact.purchases.vendor;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;
import com.xact.AbstractXactAction;
import com.xact.purchases.http.HttpVendorPurchasesHelper;

/**
 * This class provides common action handlers to manage Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesAction extends AbstractXactAction {
    protected static final String JSP_PATH = "/forms/xact/purchases/vendor/";

    private Logger logger;

    protected HttpVendorPurchasesHelper httpPoHelper;

    protected VendorPurchasesApi api;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesAction() {
	super();
	logger = Logger.getLogger("VendorPurchasesAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public VendorPurchasesAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);

    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
	try {
//	    this.api = VendorPurchasesFactory.createApi(this.dbConn, request);
	    httpPoHelper = new HttpVendorPurchasesHelper(context, request);
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
    }

    /**
     * Stub function used to respond to the client's request for recalling the most 
     * recent result of the Vendor Purchase Order Search page. 
     * 
     * @throws ActionHandlerException
     */
    protected void doSearchList() throws ActionHandlerException {
	VendorPurchasesSearchAction action = new VendorPurchasesSearchAction();
	action.processRequest(this.request, this.response, VendorPurchasesSearchAction.COMMAND_LIST);
    }

    /**
     * Retrieves creditor/vendor purchase order data from the client's request.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	try {
	    this.httpPoHelper.getHttpCombined();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

    /**
     * Set all pertinent purchase order data onto the request object which 
     * is transmitted to the client.
     * 
     * <p>
     * <p>
     * The following list dipicts the java objects and their names that are 
     * stored in the HttpServletRequest object on the client:
     * <table border="1">
     *   <tr>
     *     <th align="left"><strong>Java Data Object</strong></th>
     *     <th><strong>Id on client</strong></th>
     *   </tr>
     *   <tr>
     *     <td>{@link PurchaseOrder}</td>
     *     <td>po</td>
     *   </tr>
     *   <tr>
     *     <td>ArrayList of {@link PurchaseOrderItems}</td>
     *     <td>poItems</td>
     *   </tr>
     *   <tr>
     *     <td>{@link PurchaseOrderStatus}</td>
     *     <td>pos</td>
     *   </tr>
     *   <tr>
     *     <td>{@link PurchaseOrderStatusHist}</td>
     *     <td>posh</td>
     *   </tr>
     *   <tr>
     *     <td>{@link VwCreditorBusiness}</td>
     *     <td>vendor</td>
     *   </tr>
     * </table>
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	super.sendClientData();

	// Set data on request object
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_PO, this.httpPoHelper.getPo());
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_VENODR, this.httpPoHelper.getVendor());
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_ITEMS, this.httpPoHelper.getPoItems());
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_STATUS, this.httpPoHelper.getPoStatus());
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_STATUS_HIST, this.httpPoHelper.getPoStatusHist());
	if (this.httpPoHelper.getPo() == null || this.httpPoHelper.getPo().getPoId() <= 0) {
	    this.request.setAttribute("poTotal", "0.00");
	}
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * Retrieves one or more vendor inventory items which have not been assoicated 
     * with a purchase order.
     * 
     * @param _vendorId The id of the vendor.
     * @param _poId The id of the purchase order
     * @return ArrayList of {@link VwVendorItems}
     * @throws ActionHandlerException
     * @deprecated Use {@link com.xact.purchases.vendor.VendorPurchasesApi#getPurchaseOrderAvailItems(int, int) VendorPurchasesApi#getPurchaseOrderAvailItems(int, int)} instead.
     */
    protected List getPoAvailItems(int _vendorId, int _poId) throws ActionHandlerException {
	StringBuffer criteria = new StringBuffer(100);
	List list = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.api.setBaseView("VwVendorItemsView");
	this.api.setBaseClass("com.bean.VwVendorItems");
	criteria.append(" vendor_id = " + _vendorId);
	criteria.append(" and item_master_id not in ( select item_master_id from purchase_order_items where purchase_order_id = ");
	criteria.append(_poId);
	criteria.append(")");
	
	try {
	    list = this.api.findData(criteria.toString(), "description");
	    return list;
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
		this.api.close();
		tx.close();
		this.api = null;
		tx = null;
	}
    }
}
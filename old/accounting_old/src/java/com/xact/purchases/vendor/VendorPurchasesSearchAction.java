package com.xact.purchases.vendor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;

import com.bean.RMT2TagQueryBean;

import com.bean.criteria.CreditorCriteria;
import com.bean.criteria.PurchaseOrderCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorFactory;

import com.util.NotFoundException;
import com.util.SystemException;

/**
 * This class provides action handlers to respond to an associated controller for searching, 
 * adding, deleting, and validating Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesSearchAction extends VendorPurchasesAction {
    private static final String COMMAND_NEWSEARCH = "PurchasesVendor.Search.newsearch";

    private static final String COMMAND_SEARCH = "PurchasesVendor.Search.search";

    public static final String COMMAND_LIST = "PurchasesVendor.Search.list";

    private static final String COMMAND_EDIT = "PurchasesVendor.Search.edit";

    private static final String COMMAND_ADD = "PurchasesVendor.Search.add";

    private static final String COMMAND_DELETE = "PurchasesVendor.Search.delete";

    private static final String COMMAND_BACK = "PurchasesVendor.Search.back";

    private static Logger logger;

    private List availItems;

    private List <PurchaseOrderExt> poList;

    private List vendorList;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesSearchAction() {
	super();
	VendorPurchasesSearchAction.logger = Logger.getLogger(VendorPurchasesSearchAction.class);
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public VendorPurchasesSearchAction(Context context, Request request) throws SystemException, DatabaseException {
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
	if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_LIST)) {
	    this.listData();
	}

    }

    /**
     * Handler method that responds to the client's request to display the Vendor Purchases Maintenance 
     * Console for the first time which inclues an empty result set.
     * 
     * @throws ActionHandlerException
     */
    protected void doNewSearch() throws ActionHandlerException {
	this.setFirstTime(true);
	this.startSearchConsole();
	this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	this.query.setQuerySource("VwPurchaseOrderListView");
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	this.vendorList = this.getVendorList();
	this.poList = new ArrayList<PurchaseOrderExt>();
	this.sendClientData();
    }

    /**
     * Drives the process of building selection criteria using the client's HTTP request 
     * and storing the criteria onto the session object for later use. 
     * 
     * @throws ActionHandlerException
     */
    protected void doSearch() throws ActionHandlerException {
	this.setFirstTime(false);
	this.buildSearchCriteria();
	this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	this.query.setQuerySource("VwPurchaseOrderListView");
	this.query.setOrderByClause("id desc");
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	this.listData();
    }

    /**
     * Returns selection criteria that is sure to retrun an empty result set once 
     * applied to the sql that pertains to the data source of the vendor purchases maintenace console page.
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	return "id = -1";
    }

    /**
     * This method is responsible for gathering the user's input of purchase order selection criteria data 
     * from the request object.
     * 
     * @return Object which represents the custom object that is a member of {@link RMT2TagQueryBean}. 
     * @throws ActionHandlerException
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	this.setBaseView("VwPurchaseOrderListView");
	PurchaseOrderCriteria criteriaObj = PurchaseOrderCriteria.getInstance();
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
	    }
	    catch (SystemException e) {
		throw new ActionHandlerException(e);
	    }
	}
	return criteriaObj;
    }

    /**
     * Fetches the list customers from the database using the where clause criteria 
     * previously stored on the session during the phase of the request to builds 
     * the query predicate.
     * 
     * @throws ActionHandlerException
     */
    protected void listData() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    String criteria = this.query.getWhereClause();
	    this.poList = (List<PurchaseOrderExt>) this.api.findPurchaseOrder(criteria);

	    // Get comprehensive vendor listing
	    this.vendorList = this.getVendorList();
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
		this.api.close();
		tx.close();
		this.api = null;
		tx = null;
	}

	this.sendClientData();
    }

    /**
     * Retrieves a complete list of creditors where creditor id equals 1.
     * 
     * @return List of {@link com.bean.CreditorExt CreditorExt} objects
     * @throws ActionHandlerException
     */
    private List getVendorList() throws ActionHandlerException {
	List list;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi credApi = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get comprehensive vendor listing
	    // Use the creditor type id argument that is required by the creditor  
	    // api to retrieve business contact data.
	    CreditorCriteria criteria = CreditorCriteria.getInstance();
	    criteria.setQry_CreditorTypeId("1");
	    list = (List) credApi.findCreditorBusiness(criteria);
	    if (list == null) {
		list = new ArrayList();
	    }
	    return list;
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
		credApi = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Preapres the client for creating a purchase order. As a requirement, this method  
     * expects the client to include the vendor's id in the HttpServletRequest parameter 
     * list as "qry_VendorId".
     * <p>
     * The following objects are set on the request object identified as "item" and 
     * "itemhistory", respectively: {@link VwItemMaster} and {@link ItemMasterStatusHist}.  
     *
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	String vendorIdProp = "qry_CreditorId";
	String temp = null;
	int vendorId = 0;
	int poId = 0;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	this.api = VendorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    temp = this.getPropertyValue(vendorIdProp);
	    vendorId = Integer.parseInt(temp);
	    this.httpPoHelper.retrievePurchaseOrder(0, vendorId);
	    // Since the PO is new, we should expect a comprehensive list of vendor inventory items.
	    this.availItems = (List) this.api.getPurchaseOrderAvailItems(vendorId, poId);
	    if (this.availItems == null) {
		this.availItems = new ArrayList();
	    }
	    return;
	}
	catch (NotFoundException e) {
	    this.msg = "A vendor must be selected in order to begin an \'Add Purchase Order\' operation.  Detail message: " + (e.getMessage() == null ? "N/A" : e.getMessage());
	    VendorPurchasesSearchAction.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	catch (VendorPurchasesException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
		this.api.close();
		tx.close();
		this.api = null;
		tx = null;
	}
    }

    /**
     * Uses key data from the client's request object to retrieve purchase order 
     * information from an external datasource for editing.  The response URL is 
     * determine here by using the purchase order status id to determine whether 
     * the data should be displayed in read-only or edit mode.  The data is displayed 
     * in edit mode when the purchase order status is new or in quote mode.   All 
     * other statuses requires the data to be presented as read-only.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	// Obtain data fromthe database.
	this.httpPoHelper.retrievePurchaseOrder();

	// Use the purchase order status id to determine whether the client 
	// should display the data in read-only or edit mode.  
	String nextURL = null;
	int poStatusId = this.httpPoHelper.getPoStatus().getPoStatusId();
	if (poStatusId > VendorPurchasesConst.PURCH_STATUS_QUOTE) {
	    nextURL = VendorPurchasesAction.JSP_PATH + "PurchaseOrderView.jsp";
	}
	else {
	    nextURL = VendorPurchasesAction.JSP_PATH + "PurchaseOrderEdit.jsp";
	}
	this.request.setAttribute(RMT2ServletConst.RESPONSE_HREF, nextURL);
    }

    /**
     * Set the list of available items that are selectable for a vendor. 
     * The available items will be of type {@link com.bean.VwVendorItems VwVendorItems}.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_POLIST, this.poList);
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_VENODRLIST, this.vendorList);
	this.request.setAttribute(VendorPurchasesConst.CLIENT_DATA_AVAIL_ITEMS, this.availItems);
    }

}
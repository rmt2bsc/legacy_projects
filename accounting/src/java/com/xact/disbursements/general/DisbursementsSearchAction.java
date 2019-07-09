package com.xact.disbursements.general;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;

import com.bean.RMT2TagQueryBean;
import com.bean.VwXactList;
import com.bean.VwXactTypeItemActivity;
import com.bean.Xact;

import com.bean.criteria.XactCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.SystemException;

import com.xact.XactException;
import com.xact.XactConst;
import com.xact.AbstractXactAction;
import com.xact.XactFactory;
import com.xact.XactManagerApi;

import com.xact.disbursements.CashDisbursementsApi;
import com.xact.disbursements.CashDisburseFactory;
import com.xact.disbursements.DisbursementsConst;

/**
 * This class provides functionality needed to serve the requests of the Cash Disbursements Search user interface
 * 
 * @author Roy Terrell
 *
 */
public class DisbursementsSearchAction extends AbstractXactAction {
    private static final String COMMAND_NEWSEARCH = "DisbursementsGeneral.Search.newsearch";

    private static final String COMMAND_SEARCH = "DisbursementsGeneral.Search.search";

    public static final String COMMAND_LIST = "DisbursementsGeneral.Search.list";

    private static final String COMMAND_EDIT = "DisbursementsGeneral.Search.edit";

    public static final String COMMAND_ADD = "DisbursementsGeneral.Search.add";

    public static final String COMMAND_BACK = "DisbursementsGeneral.Search.back";

    private Logger logger;

    private List xactList;
    
    private List tenderList;
    
    private String presentType;
    
    private double resultTotal;
    
    private int resultCount;

    
    
    /**
     * Default constructor
     *
     */
    public DisbursementsSearchAction() {
	super();
	logger = Logger.getLogger("DisbursementsSearchAction");
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public DisbursementsSearchAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
	this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	
	// Determine the intended presentation type.
	this.presentType = this.request.getParameter("qry_PresentationType");
	if (this.presentType == null) {
	    if (this.query != null) {
		Object obj = this.query.getCustomObj();
		if (obj != null && obj instanceof  XactCriteria) {
		    XactCriteria criteriaObj = (XactCriteria) obj;
		    this.presentType = criteriaObj.getQry_PresentationType();
		}
	    }
	}
	if (this.presentType == null) {
	    this.presentType = XactCriteria.PRESENT_TYPE_TRANS;
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
	if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_LIST)) {
	    this.doList();
	}
	if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Returns selection criteria that is sure to retrun an empty result set once 
     * applied to the sql that pertains to the data source of the customer search page.
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	return "id = -1";
    }

    /**
     * Capture selection criteria data from the client.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	XactCriteria criteriaObj = XactCriteria.getInstance();
	// Default datasource view to transaction 
	this.setBaseView("VwXactListView");
	
	// See if view is required to be change to Item
	if (this.presentType != null && presentType.equals("2")) {
		this.setBaseView("VwXactTypeItemActivityView");
	}
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Cash Disbursment Search request parameters:  " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }

	    //  Get data items that were designed to be managed by custom routines and 
	    //  not to be picked up by the DataSourceAdapter above.
	    criteriaObj.setQryRelOp_XactDate_1(this.request.getParameter("qryRelOpXactDate1"));
	    criteriaObj.setQry_XactDate_1(this.request.getParameter("qry_XactDate1"));
	    criteriaObj.setQryRelOp_XactDate_2(this.request.getParameter("qryRelOpXactDate2"));
	    criteriaObj.setQry_XactDate_2(this.request.getParameter("qry_XactDate2"));
	    criteriaObj.setQryRelOp_XactAmount_1(this.request.getParameter("qryRelOpXactAmount1"));
	    criteriaObj.setQry_XactAmount_1(this.request.getParameter("qry_XactAmount1"));
	    criteriaObj.setQryRelOp_XactAmount_2(this.request.getParameter("qryRelOpXactAmount2"));
	    criteriaObj.setQry_XactAmount_2(this.request.getParameter("qry_XactAmount2"));
	    
	    criteriaObj.setQryRelOp_ItemAmount_1(this.request.getParameter("qryRelOpItemAmount1"));
	    criteriaObj.setQry_ItemAmount_1(this.request.getParameter("qry_ItemAmount1"));
	    criteriaObj.setQryRelOp_ItemAmount_2(this.request.getParameter("qryRelOpItemAmount2"));
	    criteriaObj.setQry_ItemAmount_2(this.request.getParameter("qry_ItemAmount2"));
	}
	return criteriaObj;
    }

    /**
     * Handler method  that responds to the client's request to display a new Customer Sales Order search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doNewSearch() throws ActionHandlerException {
	this.setFirstTime(true);
	this.startSearchConsole();
	this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	this.query.setQuerySource("VwXactListView");	
	this.xactList = new ArrayList();
	this.xactItemTypes = new ArrayList();
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get all transaction type item entries by transaction type to be used generally as a UI Dropdown.
	    this.xactItemTypes = xactApi.findXactTypeItemsByXactTypeId(XactConst.XACT_TYPE_CASHDISBEXP);
	}
	catch (XactException e) {
	    this.msg = "XactException: " + e.getMessage();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		xactApi.close();
		tx.close();
		xactApi = null;
		tx = null;
	}
	
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
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	this.sendClientData();
    }

    /**
     * Customizes selection criteria pertaining to transaction date and transaction amount.
     */
    protected String postBuildCustomClientCriteria() {
	XactCriteria criteriaObj = (XactCriteria) this.query.getCustomObj();
	try {
	    return this.processSpecialCriteria(criteriaObj);
	}
	catch (SystemException e) {
	    throw e;
	}
    }

    /**
     * Builds custom selection criteria pertaining to transaction date and amount 
     * which the results can yield compound statements ustilizing various relational 
     * operators.   
     * <p>
     * Example: xact_date >= '2/12/2006' and xact_date <= '3/12/2006' 
     *          and xact_amount >= 1234.55 and xact_amount <= 5000.00.
     *             
     * @param criteriaObj XactCriteria containing selection criteria values gathered 
     *        from the client's request.
     * @return Selection criteria
     * @throws SystemException
     */
    private String processSpecialCriteria(XactCriteria criteriaObj) throws SystemException {
	StringBuilder criteria = new StringBuilder(100);

	// Add transaction type cash disbursements
	criteria.append("xact_type_id = ");
	criteria.append(XactConst.XACT_TYPE_CASHDISBEXP);

	// Get selection criteria
	String xactDate1Val = criteriaObj.getQry_XactDate_1();   
	String xactDate1Op = criteriaObj.getQryRelOp_XactDate_1();
	String xactDate2Val = criteriaObj.getQry_XactDate_2();
	String xactDate2Op = criteriaObj.getQryRelOp_XactDate_2();
	String xactAmt1Val = criteriaObj.getQry_XactAmount_1();
	String xactAmt1Op = criteriaObj.getQryRelOp_XactAmount_1();
	String xactAmt2Val = criteriaObj.getQry_XactAmount_2();
	String xactAmt2Op = criteriaObj.getQryRelOp_XactAmount_2();
	String reason = criteriaObj.getQry_Reason();
	String xactItemType = criteriaObj.getQry_XactTypeItemId();
	String itemName = criteriaObj.getQry_ItemName();
	
	String itemAmt1Val = criteriaObj.getQry_ItemAmount_1();
	String itemAmt1Op = criteriaObj.getQryRelOp_ItemAmount_1();
	String itemAmt2Val = criteriaObj.getQry_ItemAmount_2();
	String itemAmt2Op = criteriaObj.getQryRelOp_ItemAmount_2();
	String confirmNo = criteriaObj.getQry_ConfirmNo();
	

	// Apply default selection criteria if none was entered
	if ((xactDate1Val == null || xactDate1Val.equals("")) && (xactDate1Val == null || xactDate2Val.equals("")) && 
	    (xactAmt1Val == null || xactAmt1Val.equals("")) && (xactAmt2Val == null || xactAmt2Val.equals("")) && 
	    (reason == null || reason.equals("")) && (xactItemType == null || xactItemType.equals("")) &&
	    (itemName == null || itemName.equals("")) && 
	    (itemAmt1Val == null || itemAmt1Val.equals("")) && (itemAmt2Val == null || itemAmt2Val.equals("")) &&
	    (confirmNo == null || confirmNo.equals("")) ) {
	    Date curDate = new Date();
	    long begDateTime = RMT2Date.decrementDate(curDate, Calendar.DATE, -30);
	    Date begDateNet30 = new Date(begDateTime);
	    String begDate = RMT2Date.formatDate(begDateNet30, "MM/dd/yyyy");
	    String endDate = RMT2Date.formatDate(curDate, "MM/dd/yyyy");
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append(" xact_date >= ");
	    criteria.append(" \'" + begDate + "\'");
	    criteria.append(" and xact_date <= ");
	    criteria.append(" \'" + endDate + "\'");
	    return criteria.toString();
	}
	
	//  Add transaction date #1 criteria
	if (xactDate1Val != null && xactDate1Op != null && !xactDate1Val.equals("") && !xactDate1Op.equals("")) {
	    try {
		RMT2Date.stringToDate(xactDate1Val);
	    }
	    catch (SystemException e) {
		throw e;
	    }
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append("xact_date ");
	    criteria.append(xactDate1Op);
	    criteria.append(" \'" + xactDate1Val + "\'");
	}

	//  Add transaction date #2 criteria
	if (xactDate2Val != null && xactDate2Op != null && !xactDate2Val.equals("") && !xactDate2Op.equals("")) {
	    try {
		RMT2Date.stringToDate(xactDate2Val);
	    }
	    catch (SystemException e) {
		throw e;
	    }
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append("xact_date ");
	    criteria.append(xactDate2Op);
	    criteria.append(" \'" + xactDate2Val + "\'");
	}

	//  Add transaction amount #1 criteria
	if (xactAmt1Val != null && xactAmt1Op != null && !xactAmt1Val.equals("") && !xactAmt1Op.equals("")) {
	    if (!RMT2Money.isNumeric(xactAmt1Val)) {
		this.msg = "Transaction amount criteria #1 must be a valid number [" + xactAmt1Val + "]";
		logger.log(Level.ERROR, this.msgArgs);
		throw new SystemException(this.msg);
	    }
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append("abs(xact_amount) ");
	    criteria.append(xactAmt1Op);
	    criteria.append(" " + xactAmt1Val);
	}

	//  Add transaction amount #2 criteria
	if (xactAmt2Val != null && xactAmt2Op != null && !xactAmt2Val.equals("") && !xactAmt2Op.equals("")) {
	    if (!RMT2Money.isNumeric(xactAmt2Val)) {
		this.msg = "Transaction amount criteria #2 must be a valid number [" + xactAmt2Val + "]";
		logger.log(Level.ERROR, this.msgArgs);
		throw new SystemException(this.msg);
	    }
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append("abs(xact_amount) ");
	    criteria.append(xactAmt2Op);
	    criteria.append(" " + xactAmt2Val);
	}
	
	//  Add item amount #1 criteria
	if (itemAmt1Val != null && itemAmt1Op != null && !itemAmt1Val.equals("") && !itemAmt1Op.equals("")) {
	    if (!RMT2Money.isNumeric(itemAmt1Val)) {
		this.msg = "Transaction amount criteria #1 must be a valid number [" + itemAmt1Val + "]";
		logger.log(Level.ERROR, this.msgArgs);
		throw new SystemException(this.msg);
	    }
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append("abs(item_amount) ");
	    criteria.append(itemAmt1Op);
	    criteria.append(" " + itemAmt1Val);
	}

	//  Add item amount #2 criteria
	if (itemAmt2Val != null && itemAmt2Op != null && !itemAmt2Val.equals("") && !itemAmt2Op.equals("")) {
	    if (!RMT2Money.isNumeric(itemAmt2Val)) {
		this.msg = "Transaction amount criteria #2 must be a valid number [" + itemAmt2Val + "]";
		logger.log(Level.ERROR, this.msgArgs);
		throw new SystemException(this.msg);
	    }
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append("abs(item_amount) ");
	    criteria.append(itemAmt2Op);
	    criteria.append(" " + itemAmt2Val);
	}
	
	return criteria.length() <= 0 ? null : criteria.toString();
    }

    /**
     * Handler method  that responds to the client's request to perform a cash disbursements 
     * search using the selection criteria entered by the user.
     * 
     * @throws ActionHandlerException
     *           Problem obtaining a list of cash disbursement transacations 
     *           from the database.
     */
    protected void doList() throws ActionHandlerException {
	this.xactList = new ArrayList();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CashDisbursementsApi api = CashDisburseFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    if (this.presentType.equals("1")) {
		// Query cash disbursements by transaction
		this.xactList = (List) api.findXactList(this.query.getWhereClause());	
	    }
	    if (this.presentType.equals("2")) {
		// Query cash disbursements by transaction item
		this.xactList = (List) api.findXactItems(this.query.getWhereClause());
	    }
	    if (this.xactList == null) {
		this.xactList = new ArrayList();
	    }
	    this.resultCount = this.xactList.size();
	    
	    // Get all transaction type item entries by transaction type to be used generally as a UI Dropdown.	    
	    XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	    this.xactItemTypes = xactApi.findXactTypeItemsByXactTypeId(XactConst.XACT_TYPE_CASHDISBEXP);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
		api.close();
		tx.close();
		api = null;
		tx = null;
	}
	
	try {
	    this.resultTotal = this.computeTotal(this.xactList);
	}
	catch (InvalidCashDisbursementInstanceException e) {
	    e.printStackTrace();
	    this.msg = e.getMessage();
	    this.logger.log(Level.ERROR, this.msg);
	}
	this.sendClientData();
    }

    /**
     * Responds to the Add action from the Cash Disbursement Search page.
     * Instantiates the data objects needed to create a new general cash 
     * disbursement transaction which the objects are sent across the wire 
     * to the client for presentation. 
     */
    public void add() throws ActionHandlerException {
	super.add();
	this.receiveClientData();
	if (this.genericXact instanceof Xact) {
	    ((Xact) this.genericXact).setXactId(0);    
	}
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Instantiate transaction and transaction item objects.
	    this.xactItems = new ArrayList();

	    // Get all transaction type item entries by transaction type to be used generally as a UI Dropdown.
	    this.xactItemTypes = xactApi.findXactTypeItemsByXactTypeId(this.xactType.getXactTypeId());
	    
	    // Get tender codes by group id
	    this.tenderList = xactApi.findXactCodeByGroupId(2);
	}
	catch (XactException e) {
	    this.msg = "XactException: " + e.getMessage();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		xactApi.close();
		tx.close();
		xactApi = null;
		tx = null;
	}
	this.sendClientData();
    }

    /**
     * Responds to the View action from the Cash Disbursement Search page.
     * Obtains key data peratining to the transaction fro the client and uses 
     * the key data to fetch related transaction data into member variables 
     * from the database.
     */
    public void edit() throws ActionHandlerException {
	super.edit();
	this.refreshXact(this.xact.getXactId());
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    if (this.xactType.getXactTypeId() == 0) {
		this.xactType = xactApi.findXactTypeById(XactConst.XACT_TYPE_CASHDISBEXP);
		this.httpHelper.getHttpXactType().setXactTypeId(XactConst.XACT_TYPE_CASHDISBEXP);
		this.httpHelper.getHttpXactType().setDescription(this.xactType.getDescription());
	    }
	}
	catch (XactException e) {
	    this.msg = "XactException: " + e.getMessage();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		xactApi.close();
		tx.close();
		xactApi = null;
		tx = null;
	}
    }


    /**
     * Calculates the total dollar amount from a list of cash disbursement.  The cash disbursements 
     * contained in the list must be of type VwXactList or VwXactTypeItemActivity.  Otherwise, an 
     * exception will be thrown.
     *  
     * @param list
     *          A List of either {@link com.bean.VwXactList VwXactList} or {@link com.bean.VwXactTypeItemActivity VwXactTypeItemActivity} 
     * @return double
     *          The summed dollar amount 
     * @throws InvalidCashDisbursementInstanceException
     *          When <i>list</i> contains an data other than what is required.
     */
    private double computeTotal(List list) throws InvalidCashDisbursementInstanceException {
	if (list == null) {
	    return 0;
	}
	double total = 0;
	for (Object item : list) {
	    if (item instanceof VwXactList) {
		total += ((VwXactList) item).getXactAmount();
	    }
	    else if (item instanceof VwXactTypeItemActivity) {
		total += ((VwXactTypeItemActivity) item).getItemAmount();
	    }
	    else {
		this.msg = "Error accessing cash disbursement amount.  Cannot interpret cash disbursement runtime type: " + item.getClass().getName();
		logger.log(Level.ERROR, this.msg);
		throw new InvalidCashDisbursementInstanceException(this.msg);
	    }
	}
	return total;
    }
    
    
    /**
     * Retrieves key data from the client pertaining to the transaction and uses the 
     * key data to fetch remaining data from the database to satisfy the client's request. 
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	XactManagerApi xactApi = XactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xactType = xactApi.findXactTypeById(XactConst.XACT_TYPE_CASHDISBEXP);
	    this.httpHelper.getHttpXactType().setXactTypeId(XactConst.XACT_TYPE_CASHDISBEXP);
	    this.httpHelper.getHttpXactType().setDescription(this.xactType.getDescription());
	}
	catch (XactException e) {
	    this.msg = "XactException: " + e.getMessage();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		xactApi.close();
		tx.close();
		xactApi = null;
		tx = null;
	}
    }

    /**
     * Sends transaction data to the client as a response to the client's general 
     * cash disbursement request.
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.request.setAttribute(DisbursementsConst.CLIENT_DATA_LIST, this.xactList);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.genericXact);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTTYPE, this.xactType);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMS, this.xactItems);
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMTYPELIST, this.xactItemTypes);
	this.request.setAttribute(XactConst.CLIENT_DATA_TENDERLIST, this.tenderList);
	this.request.setAttribute(XactConst.CLIENT_DATA_TOTAL, this.resultTotal);
	this.request.setAttribute(XactConst.CLIENT_DATA_COUNT, this.resultCount);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
	
	// Persist presentation type on criteria object
	XactCriteria criteriaObj = (XactCriteria) this.query.getCustomObj();
	criteriaObj.setQry_PresentationType(this.presentType);
    }

    /**
     * Removes XactCriteria instance from the session.
     *  
     * @throws ActionHandlerException.
     */
    @Override
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	this.request.getSession().removeAttribute(RMT2ServletConst.QUERY_BEAN);
    }

    
}
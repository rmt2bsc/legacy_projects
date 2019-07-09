package com.xact.generic;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;

import com.api.DaoApi;
import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;
import com.api.messaging.MessageManager;
import com.api.security.pool.AppPropertyPool;
import com.api.xml.XmlApiFactory;

import com.bean.RMT2TagQueryBean;
import com.bean.VwGenericXactList;

import com.bean.criteria.GenericXactCriteria; //import com.bean.criteria.XactCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerConst;
import com.gl.customer.CustomerExt;
import com.gl.customer.CustomerFactory;

import com.remoteservices.http.HttpRemoteServicesConsumer;

import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.RMT2String;
import com.util.RMT2Utility;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

import com.xact.AbstractXactAction;
import com.xact.XactException;

/**
 * This class provides functionality needed to serve the requests of the Cash Disbursements Search user interface
 * 
 * @author Roy Terrell
 *
 */
public class CopyOfGenericXactSearchAction extends AbstractXactAction {
    private static final String COMMAND_NEWSEARCH = "GenericTransaction.Search.newsearch";

    private static final String COMMAND_SEARCH = "GenericTransaction.Search.search";

    public static final String COMMAND_LIST = "GenericTransaction.Search.list";

    public static final String COMMAND_BACK = "GenericTransaction.Search.back";

    private static Logger logger = Logger.getLogger("GenericXactSearchAction");

    private Object criteriaXml;

    private Object xactXml;

    private Object xactTypeXml;

    private Object xactSubTypeXml;

    private double resultTotal;

    private int resultCount;

    private String appRoot;
    
    private String appFilePath;
    

    /**
     * Default constructor
     *
     */
    public CopyOfGenericXactSearchAction() {
	super();
    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CopyOfGenericXactSearchAction(Context _context, Request _request) throws SystemException, DatabaseException {
	super(_context, _request);
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
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	this.appRoot = RMT2Utility.getWebAppContext(request);
	this.appFilePath = System.getProperty(HttpSystemPropertyConfig.PROPNAME_WEB_DIR) + "\\" + AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_APP_NAME);
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
	if (command.equalsIgnoreCase(CopyOfGenericXactSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(CopyOfGenericXactSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(CopyOfGenericXactSearchAction.COMMAND_LIST)) {
	    this.doList();
	}
	if (command.equalsIgnoreCase(CopyOfGenericXactSearchAction.COMMAND_BACK)) {
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
	GenericXactCriteria criteriaObj = GenericXactCriteria.getInstance();
	// Default datasource view to transaction 
	this.setBaseView("VwGenericXactListView");

	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Generic transaction Search request parameters:  " + e.getMessage();
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

	    criteriaObj.setQry_ItemAmount_1(this.request.getParameter("qry_ItemAmount1"));
	    criteriaObj.setQry_ItemAmount_2(this.request.getParameter("qry_ItemAmount2"));
	    criteriaObj.setQryRelOp_ItemAmount_1(this.request.getParameter("qryRelOpItemAmount1"));
	    criteriaObj.setQryRelOp_ItemAmount_2(this.request.getParameter("qryRelOpItemAmount2"));
	    // Ensure that business name is set to null so that it may be managed in the doList() method.
	    criteriaObj.setQry_BusinessName(this.request.getParameter("BusinessNameTmp"));
	}
	this.criteriaXml = criteriaObj.toXml();
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
	this.query.setQuerySource("VwGenericXactListView");
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);

	DatabaseTransApi tx = DatabaseTransFactory.create();
	GenericXactManagerApi xactApi = GenericXactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get all transaction type entries to be used generally as a UI Dropdown.
	    this.xactTypeXml = xactApi.findXactType(null);
	    this.xactSubTypeXml = xactApi.findXactSubType(null);
	    this.sendClientData();
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
     * Drives the process of building selection criteria using the client's HTTP request 
     * and storing the criteria onto the session object for later use. 
     * 
     * @throws ActionHandlerException
     */
    protected void doSearch() throws ActionHandlerException {
	this.setFirstTime(false);
	this.buildSearchCriteria();
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
    }

    /**
     * Customizes selection criteria pertaining to transaction date and transaction amount.
     */
    protected String postBuildCustomClientCriteria() {
	GenericXactCriteria criteriaObj = (GenericXactCriteria) this.query.getCustomObj();
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
    private String processSpecialCriteria(GenericXactCriteria criteriaObj) throws SystemException {
	StringBuilder criteria = new StringBuilder(100);

	// Get selection criteria
	String xactDate1Val = criteriaObj.getQry_XactDate_1();
	String xactDate1Op = criteriaObj.getQryRelOp_XactDate_1();
	String xactDate2Val = criteriaObj.getQry_XactDate_2();
	String xactDate2Op = criteriaObj.getQryRelOp_XactDate_2();
	String xactAmt1Val = criteriaObj.getQry_XactAmount_1();
	String xactAmt1Op = criteriaObj.getQryRelOp_XactAmount_1();
	String xactAmt2Val = criteriaObj.getQry_XactAmount_2();
	String xactAmt2Op = criteriaObj.getQryRelOp_XactAmount_2();
	String reason = criteriaObj.getQry_XactReason();

	// Apply default selection criteria if none was entered
	if ((xactDate1Val == null || xactDate1Val.equals("")) && (xactDate1Val == null || xactDate2Val.equals("")) && (xactAmt1Val == null || xactAmt1Val.equals(""))
		&& (xactAmt2Val == null || xactAmt2Val.equals("")) && (reason == null || reason.equals(""))) {
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
	    criteria.append("xact_amount ");
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
	    criteria.append("xact_amount ");
	    criteria.append(xactAmt2Op);
	    criteria.append(" " + xactAmt2Val);
	}
	
	if (criteriaObj.getQry_XactReason_ADVSRCHOPTS() == null) {
	    criteriaObj.setQry_XactReason_ADVSRCHOPTS(AbstractActionHandler.ADV_SRCH_END);
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
	DatabaseTransApi tx = DatabaseTransFactory.create();
	GenericXactManagerApi api = GenericXactFactory.create((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.resultCount = 0;
	this.resultTotal = 0;
	
	String mainCriteria = this.query.getWhereClause();
	try {
	    // Get list of business contact busines id's based on business name criteria, if available
	    GenericXactCriteria criteriaObj = (GenericXactCriteria) this.query.getCustomObj();
	    String busNameCriteria = criteriaObj.getQry_BusinessName();
	    if (criteriaObj != null && busNameCriteria != null && busNameCriteria.length() > 0) {
		// Build business name selection criteria as "Arg_Name='<some business name>'
		StringBuffer contactCriteria = new StringBuffer();
		contactCriteria.append(CustomerConst.CRITERIATAGS_CONTACT[0]);
		contactCriteria.append("=");
		// No need to enclose in quotes because contact service will handle.
		contactCriteria.append(criteriaObj.getQry_BusinessName());
		CustomerApi custApi = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
		List<CustomerExt> contacts = (List<CustomerExt>) custApi.findCustomerBusiness(contactCriteria.toString());
		if (contacts != null && contacts.size() > 0) {
		    // build business id list
		    StringBuffer busIdList = new StringBuffer();
		    for (CustomerExt contact : contacts) {
			if (busIdList.length() > 0) {
			    busIdList.append(", ");
			}
			busIdList.append(contact.getBusinessId());
		    }
		    // Build SQL "IN" clause containing one or more contact business 
		    // id's and append to main query's where clause
		    String inClause = " business_id in(" + busIdList.toString() + ") ";
		    if (mainCriteria != null && mainCriteria.length() > 0) {
			mainCriteria += " and " + inClause;
		    }
		    else {
			mainCriteria = inClause;
		    }
		}
	    }
	    
	    logger.log(Level.INFO, "Where clause to apply to query: " + mainCriteria);
	    List<VwGenericXactList> list = (List<VwGenericXactList>) api.findGenericXactList(mainCriteria);
	    if (list != null && list.size() > 0) {
		Map<String, String> contactMap = this.getBusinessContactInfo(list);
		this.resultCount = list.size();
		StringBuffer xml = new StringBuffer();
		for (int ndx = 0; ndx < list.size(); ndx++) {
		    VwGenericXactList obj = (VwGenericXactList) list.get(ndx);
		    // Get business name
		    String busName = contactMap.get(String.valueOf(obj.getBusinessId()));
		    obj.setBusinessName(busName);
		    // Format transaction date
		    String xactDate = RMT2Date.formatDate(obj.getXactDate(), "MM/dd/yyyy");
		    obj.setXactDateStr(xactDate);
		    xml.append(obj.toXml());
		    this.resultTotal += obj.getXactAmount();
		}
		this.xactXml = xml.toString();
	    }
	    // try to restore criteria objects
	    this.criteriaXml = criteriaObj.toXml();
	    
	    // restore drop down lists
	    this.xactTypeXml = api.findXactType(null);
	    this.xactSubTypeXml = api.findXactSubType(null);
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

	this.sendClientData();
    }

    private Map<String, String> getBusinessContactInfo(List<VwGenericXactList> xact) throws GenericXactException {
	StringBuffer busIdList = new StringBuffer();
	// Build list of business id's
	for (VwGenericXactList obj : xact) {
	    if (busIdList.length() > 0) {
		busIdList.append(",");
	    }
	    busIdList.append(obj.getBusinessId());
	}

	try {
	    this.request.setAttribute("Arg_BusinessId", busIdList.toString());
	    HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer(this.request, this.response, "getBusAddrById");
	    srvc.processRequest();
	    Object results = srvc.getServiceResults();
	    if (results instanceof Exception) {
		this.msg = ((Exception) results).getMessage();
		throw new GenericXactException(this.msg);
	    }
	    String xml = results.toString();
	    return this.populateBusiness(xml);
	}
	catch (Exception e) {
	    throw new GenericXactException(e);
	}
    }

    private Map<String, String> populateBusiness(String xml) throws GenericXactException {
	DaoApi dao = XmlApiFactory.createXmlDao(xml);
	try {
	    Map<String, String> map = new java.util.HashMap<String, String>();
	    dao.retrieve("//VwBusinessAddressView/vw_business_address");
	    while (dao.nextRow()) {
		try {
		    String id = dao.getColumnValue("business_id");
		    String busName = dao.getColumnValue("bus_longname");
		    map.put(id, busName);
		}
		catch (Exception e) {
		    throw new GenericXactException(e);
		}
	    }
	    return map;
	}
	catch (DatabaseException e) {
	    throw new GenericXactException(e);
	}
    }

    /**
     * Retrieves key data from the client pertaining to the transaction and uses the 
     * key data to fetch remaining data from the database to satisfy the client's request. 
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
    }

    /**
     * Sends transaction data to the client as a response to the client's general 
     * cash disbursement request.
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	StringBuffer xmlBuf = new StringBuffer();
	xmlBuf.append(MessageManager.MSG_OPEN_TAG);
	xmlBuf.append(MessageManager.MSG_OPEN_APPROOT_TAG);
	xmlBuf.append(this.appRoot);
	xmlBuf.append(MessageManager.MSG_CLOSE_APPROOT_TAG);
	xmlBuf.append("<pageTitle>");
	xmlBuf.append("Combined Transaction Search");
	xmlBuf.append("</pageTitle>");
    	
	if (this.criteriaXml != null) {
	    xmlBuf.append(this.criteriaXml);
	}
	if (this.xactTypeXml != null) {
	    xmlBuf.append("<transactionTypes>");
	    xmlBuf.append(this.xactTypeXml);
	    xmlBuf.append("</transactionTypes>");
	}
	if (this.xactSubTypeXml != null) {
	    xmlBuf.append("<transactionSubTypes>");
	    xmlBuf.append(this.xactSubTypeXml);
	    xmlBuf.append("</transactionSubTypes>");
	}

	if (this.xactXml == null) {
	    this.xactXml = "";
	}
	xmlBuf.append("<transactions>");
	xmlBuf.append(this.xactXml);
	xmlBuf.append("</transactions>");

	// Get transaction count
	xmlBuf.append("<xactCount>");
	xmlBuf.append(this.resultCount);
	xmlBuf.append("</xactCount>");

	// Get transaction price total
	xmlBuf.append("<xactTotal>");
	xmlBuf.append(this.resultTotal);
	xmlBuf.append("</xactTotal>");

	if (this.msg != null) {
	    xmlBuf.append("<message>");
	    xmlBuf.append(this.msg);
	    xmlBuf.append("</message>");
	}
	xmlBuf.append(MessageManager.MSG_CLOSE_TAG);

	// Only use this line of code during development or debugging
//	RMT2File.createFile(xmlBuf.toString(), "c:\\tmp\\generic_xact.xml");
	
	// Transform XML to HTML document
	RMT2XmlUtility xsl = RMT2XmlUtility.getInstance();
//	String path = this.request.getSession().getContext().getRealPath("/");
	String xslFile = this.appFilePath + "\\resources\\forms\\xact\\GenericXactSearch.xsl";
	try {
	    xsl.transform(xslFile, xmlBuf.toString(), this.response.getWriter());
	}
	catch (SystemException e) {
	    this.msg = "XSL transformation failed for resource, " + xslFile + " due to a System error.  " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ActionHandlerException(e);
	}
	catch (IOException e) {
	    this.msg = "XSL transformation failed for resource, " + xslFile + " due to a general I/O error.  " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    e.printStackTrace();
	    throw new ActionHandlerException(e);
	}
	finally {
	    xsl = null;
	}
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
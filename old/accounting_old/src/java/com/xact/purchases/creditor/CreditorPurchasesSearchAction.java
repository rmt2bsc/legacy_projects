package com.xact.purchases.creditor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

import com.bean.bindings.JaxbAccountingFactory;
import com.bean.criteria.CreditChargeCriteria;
import com.bean.criteria.XactCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.AccountingConst;
import com.gl.creditor.CreditorExt;

import com.util.NotFoundException;
import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.SystemException;

import com.xact.XactConst;



/**
 * This class provides action handlers to respond to an associated 
 * controller for searching, adding, deleting, and validating 
 * Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesSearchAction extends CreditorPurchasesAction {
    public static final String COMMAND_NEWSEARCH = "PurchasesCreditor.Search.newsearch";

    public static final String COMMAND_SEARCH = "PurchasesCreditor.Search.search";

    public static final String COMMAND_LIST = "PurchasesCreditor.Search.list";

    public static final String COMMAND_VIEW = "PurchasesCreditor.Search.view";

    public static final String COMMAND_ADD = "PurchasesCreditor.Search.add";

    public static final String COMMAND_BACK = "PurchasesCreditor.Search.back";

    private Logger logger;

    private List xactList;




    /**
     * Default constructor
     *
     */
    public CreditorPurchasesSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("CreditorPurchasesSearchAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public CreditorPurchasesSearchAction(Context context, Request request) throws SystemException, DatabaseException {
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
//	CreditorPurchasesApi api = CreditorPurchasesFactory.createApi(this.dbConn, request);
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
	if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_LIST)) {
	    this.doList();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_VIEW)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /** 
     * Removes the creditor list from the user's session and sets the member creditor list 
     * variable to null.  This operation is the first step in preparing the user to return 
     * to the Creditor Purchases Console which performs a search based on the previous 
     * criteria stored in the session.
     * 
     * @throws ActionHandlerException
     */
    public void doBack() throws ActionHandlerException {
        super.doBack();
        this.request.getSession().removeAttribute(CreditorPurchasesConst.CLIENT_DATA_CREDIOTRLIST);
        CreditorPurchasesAction.CREDITOR_LIST = null;
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
	this.xactList = new ArrayList();
	this.getCreditorList();
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	this.sendClientData();
    }
    
    /**
     * Sets up a selection criteria that intentionally returns an empty result set by looking for 
     * rows where creditor id equals -1. 
     * 
     * @param _query 
     *          The Query Object.
     * @return String 
     *          The selection criteria, <i>creditor_id = -1</i>.
     * @throws ActionHandlerException
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	return "creditor_id = -1";
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
	this.query.setQuerySource(this.getBaseView());
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
    }

    /**
     * This method is responsible for gathering the user's input of credit charge 
     * selection criteria data from the request object.
     * 
     * @return Object which represents the custom object that is a member of 
     * {@link RMT2TagQueryBean}. 
     * @throws ActionHandlerException
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	this.setBaseView("VwXactCreditChargeListView");
	CreditChargeCriteria criteriaObj = CreditChargeCriteria.getInstance();
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Item Master request parameters.";
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	}
	return criteriaObj;
    }

    /**
     * Applies additional search criteria to _query before the _query is updated on 
     * the session object.   In addition to adding criteria, the wait page flag is 
     * set to true to indicate that the "Wait Please..." page has been displayed.
     * <p>
     * <p>
     * This method ensures that the following condition(s) are included in the 
     * selection criteria that will be used to retrieve credit charge transactions: 
     * <ol>
     *    <li>Each transaction must be associated with a creditor type of {@link AccountingConst.CREDITOR_TYPE_CREDITOR}</li>
     *    <li>The transaction type must be equal to {@link XactConst.XACT_TYPE_CREDITCHARGE}.</li>
     * </ol>
     *  
     * @param _query {@link RMT2TagQueryBean} object.
     * @param __searchMode Set to either {@link RMT2ServletConst.SEARCH_MODE_NEW} or {@link RMT2ServletConst.SEARCH_MODE_OLD}. 
     * @throws ActionHandlerException
     */
    protected void doPostCustomInitialization(RMT2TagQueryBean query, int searchMode) throws ActionHandlerException {
	String sql = null;
	String annex = null;

	if (searchMode == RMT2ServletConst.SEARCH_MODE_NEW) {
	    sql = query.getWhereClause();
	    if (sql != null) {
		annex = "creditor_type_id =  " + AccountingConst.CREDITOR_TYPE_CREDITOR + " and xact_type_id in ( " + XactConst.XACT_TYPE_CREDITCHARGE + ")";
		if (sql.length() > 0) {
		    sql += " and " + annex;
		}
		else {
		    sql = annex;
		}
		query.setWhereClause(sql);
	    }
	    query.setOrderByClause(" xact_id desc ");
	    //_query.setOrderByClause( " xact_date desc, xact_id desc ");
	}
	return;
    }
    
    /**
     * Customizes selection criteria pertaining to transaction date.
     */
    protected String postBuildCustomClientCriteria() {
	CreditChargeCriteria criteriaObj = (CreditChargeCriteria) this.query.getCustomObj();
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
     * @param criteriaObj CreditChargeCriteria containing selection criteria values gathered 
     *        from the client's request.
     * @return Selection criteria
     * @throws SystemException
     */
    private String processSpecialCriteria(CreditChargeCriteria criteriaObj) throws SystemException {
	StringBuilder criteria = new StringBuilder(100);

	// Get selection criteria
	String acctNo = criteriaObj.getQry_AccountNo();
	String creditorId = criteriaObj.getQry_CreditorId();
	String xactDate1Val = criteriaObj.getQry_XactDate();   
	String reason = criteriaObj.getQry_Reason();
	String taxId = criteriaObj.getQry_TaxId();
	String xactId = criteriaObj.getQry_XactId();
	String item = criteriaObj.getQry_Item();
	String itemDesc = criteriaObj.getQry_ItemDescription();

	// Apply default selection criteria if none was entered
	if ((xactDate1Val == null || xactDate1Val.equals("")) && (acctNo == null || acctNo.equals("")) && 
	    (creditorId == null || creditorId.equals("")) && (taxId == null || taxId.equals("")) && 
	    (reason == null || reason.equals("")) && (xactId == null || xactId.equals("")) &&
	    (item == null || item.equals("")) && (itemDesc == null || itemDesc.equals("")) ) {
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
	return criteria.length() <= 0 ? null : criteria.toString();
    }
    

    /**
     * Queries the databse for credit purchases transactions based on the SQL predicate 
     * stored in the session object.  The results of the query are assigned to the 
     * request object to be managed by teh client. 
     *  
     * @throws ActionHandlerException
     */
    public void doList() throws ActionHandlerException {
	this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	this.useExistingSearchCriteria();
	String criteria = this.query.getWhereClause();
	
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorPurchasesApi api = CreditorPurchasesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.xactList = (List) api.findCreditorPurchasesXact(criteria);
	    if (this.xactList == null) {
		this.xactList = new ArrayList();
	    }
            this.getCreditorList();    
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.sendClientData();
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Preapres the client for creating a creditor purchase order. As a requirement, this method  
     * expects the client to include the creditor's id in the HttpServletRequest parameter 
     * list as "qry_CreditorId".
     * <p>
     * The following objects are set on the request object identified as "creditor" and 
     * "xact", respectively: {@link CreditorExt} and {@link VwXactList}.  
     *
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	int creditorId = 0;
	try {
	    this.httpXactHelper.retrieveCreditOrder(0, creditorId);
	    this.getCreditorList();    
	    this.resultCount = 0;
	    this.resultTotal = 0;
	}
	catch (NotFoundException e) {
	    this.msg = "A creditor must be selected for an 'Add' request";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	return;
    }

    /**
     * Retrieves expsnes credit purchase data from the database and sends it to the client. 
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	// Obtain data from the database.
	this.httpXactHelper.retrieveCreditOrder();
    }

    protected void sendClientData() throws ActionHandlerException {
	super.sendClientData();

	// Set data on request object
	this.request.setAttribute(XactConst.CLIENT_DATA_XACTLIST, this.xactList);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    

}
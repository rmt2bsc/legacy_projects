package com.inventory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceAdapter;

import com.bean.RMT2TagQueryBean;

import com.bean.criteria.ItemMasterCriteria;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

/**
 * This class provides action handlers to server Item Master Search requests. 
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterSearchAction extends ItemMasterAction {
    private static final String COMMAND_NEWSEARCH = "Inventory.Search.newsearch";

    private static final String COMMAND_SEARCH = "Inventory.Search.search";

    private static final String COMMAND_EDIT = "Inventory.Search.edit";

    private static final String COMMAND_ADD = "Inventory.Search.add";

    public static final String COMMAND_LIST = "Inventory.Search.list";

    private Logger logger;

    

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public ItemMasterSearchAction() {
	super();
	this.logger = Logger.getLogger("ItemMasterSearchAction");
    }

    /**
     * Main contructor for this action handler.
     * 
     * @param _context The servlet context to be associated with this action handler
     * @param _request The request object sent by the client to be associated with 
     *   this action handler
     * @throws SystemException
     */
    public ItemMasterSearchAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
	if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_NEWSEARCH)) {
	    this.startConsole();
	}
	if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_LIST)) {
	    this.listData();
	}
    }

    /**
     * Displays the Item Master Search Console for the first time rendering the 
     * search criteria section with blank values and the Search Result Set section 
     * with an empty result set.
     *
     */
    public void startConsole() {

	ItemMasterCriteria imq = ItemMasterCriteria.getInstance();
	RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	query.setWhereClause(null);

	// Force an empty result set by purposely constructing erroneous  selection criteria.
	query.setWhereClause("id = -1");
	query.setCustomObj(imq);
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
	try {
	    this.listData();
	}
	catch (ActionHandlerException e) {
	    this.vendorList = new ArrayList();
	}
	return;
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
	this.query.setOrderByClause("description asc");
	this.listData();
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
    }

    /**
     * This method is responsible for gathering the user's input of selection criteria data from the request object.
     * 
     * @return Object which represents the custom object that is a member of {@link RMT2TagQueryBean}. 
     * @throws ActionHandlerException
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	this.setBaseView("VwItemMasterView");
	ItemMasterCriteria criteria = ItemMasterCriteria.getInstance();
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteria);
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Item Master request parameters.";
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(e);
	    }
	}
	return criteria;
    }

    /**
     *  This method signals to the caller to ignore the selection criteria that 
     *  was setup for _filedName in the ancestor script.   Typically, this is 
     *  true when _fieldName is pointing to the Active or Inactive fields.
     *  
     *  @return String - Return "" which means to ignore the criteria of 
     *                   _fieldName which was setup in the ancestor script.   
     *                   Returns null to indicate that there is no custom criteria 
     *                   to be applied and to apply that which was built in the ancestor. 
     */
    protected String buildCustomClientCriteria(String _fieldName) {
	if (_fieldName.equalsIgnoreCase("qry_Active") || _fieldName.equalsIgnoreCase("qry_Inactive")) {
	    return "";
	}
	return null;
    }

    /**
     * Includes the item master values, "Active" and "Inactive", into the SQL 
     * predicate, if applicable.
     * 
     * @return SQL predicate.
     */
    protected String postBuildCustomClientCriteria() {
	StringBuffer criteria = new StringBuffer(100);
	String temp = null;
	String temp2 = null;
	ArrayList activeInactive = new ArrayList();

	temp = this.request.getParameter("qry_Active");
	temp2 = this.request.getParameter("qry_Inactive");
	if (temp != null && !temp.equals("")) {
	    activeInactive.add(temp);
	}
	if (temp2 != null && !temp2.equals("")) {
	    activeInactive.add(temp2);
	}
	if (activeInactive.size() > 0) {
	    if (criteria.length() > 0) {
		criteria.append(" and ");
	    }
	    criteria.append(" active in(");
	    for (int ndx = 0; ndx < activeInactive.size(); ndx++) {
		if (ndx > 0) {
		    criteria.append(", ");
		}
		temp = (String) activeInactive.get(ndx);
		criteria.append(temp);
	    }
	    criteria.append(") ");
	}
	if (criteria.length() > 0) {
	    return criteria.toString();
	}
	return null;
    }
    
    /**
     * Preapres the client for adding an item to inventory and retrieves a list of vendors.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	super.add();
	this.vendorList = this.getVendorList();
	return;
    }

    /**
     * Uses data from the client's request object to retrieve target VwItemMaster and 
     * ItemMasterStatusHist objects from the databse for a single Item Master record edit session.
     * <p>
     * The following objects are set on the request object identified as "item" and "itemhistory", respectively: 
     * {@link VwItemMaster} and {@link ItemMasterStatusHist}. 
     * 
     * @throws ItemMasterException
     */
    public void edit() throws ActionHandlerException {
	this.itemHelper.refreshData();
	this.vendorList = this.getVendorList();
	return;
    }

    protected void listData() throws ActionHandlerException {
	this.vendorList = this.getVendorList();
	this.sendClientData();
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

}
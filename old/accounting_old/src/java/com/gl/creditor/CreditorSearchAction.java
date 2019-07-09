package com.gl.creditor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.db.orm.DataSourceAdapter;

import com.bean.Creditor;
import com.bean.RMT2TagQueryBean;

import com.bean.criteria.CreditorCriteria;

import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.util.SystemException;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class CreditorSearchAction extends CreditorAction {
    private static final String COMMAND_NEWSEARCH = "Creditor.Search.newsearch";

    private static final String COMMAND_SEARCH = "Creditor.Search.search";

    public static final String COMMAND_LIST = "Creditor.Search.list";

    private static final String COMMAND_EDIT = "Creditor.Search.edit";

    private static final String COMMAND_ADD = "Creditor.Search.add";

    private static final String COMMAND_BACK = "Creditor.Search.back";

    private static final int CRITERIATYPE_CONTACT = 100;

    private static final int CRITERIATYPE_CREDITOR = 200;

    private static final int CRITERIATYPE_ALL = 300;

    private static Logger logger;

    private int criteriaType;

    /**
     * Default constructor
     * 
     */
    public CreditorSearchAction() {
	super();
	CreditorSearchAction.logger = Logger.getLogger(CreditorSearchAction.class);
	this.criteriaType = CreditorSearchAction.CRITERIATYPE_ALL;
    }

    /**
     * Creates a CreditorSearchAction that is aware of the Application context and user's request.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     * @throws DatabaseException
     */
    public CreditorSearchAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);
    }
    
    
    /**
     * Set the response object.
     * 
     * @param response
     */
    public void setResponse(Response response) {
	this.response = response;
    }

    /**
     * Processes the client's request using request, response, and command.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_LIST)) {
	    this.listData();
	}
	if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    public void receiveClientData() throws ActionHandlerException {
	int credId;
	String temp;

	try {
	    temp = this.getInputValue("CreditorId", null);
	    credId = Integer.parseInt(temp);
	    this.cred = CreditorFactory.createCreditor();
	    ((Creditor) this.cred).setCreditorId(credId);
	}
	catch (NumberFormatException e) {
	    this.msg = "Problem Identifying Creditor Id from a list of creditors";
	    CreditorSearchAction.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.session.setAttribute(GeneralConst.CLIENT_DATA_CRITERIA, this.query.getCustomObj());
    }

    /**
     * Retrieves a single instance of creditor's detail data and its associated 
     * address from the database using the creditor's internal id. 
     * 
     * @param creditorId The creditor's internal id which is generally the primary key.
     * @throws ActionHandlerException
     */
    public void fetchCreditor(int creditorId) throws ActionHandlerException {
	super.fetchCreditor(creditorId);
    }

    /**
     * Returns selection criteria that is sure to retrun an empty result set
     * once applied to the sql that pertains to the data source of the creditor
     * search page.
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	return "creditor_id = -1";
    }

    /**
     * Creates an instance of CreditorCriteria, which is used to track the user's 
     * selection criteria input.   This method uses introspection to gather user's 
     * input into the cretieria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	CreditorCriteria criteriaObj = CreditorCriteria.getInstance();
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
		this.setBaseView("CreditorView");
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Creditor Search request parameters:  " + e.getMessage();
		CreditorSearchAction.logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	}
	this.validateCriteria(criteriaObj);

	return criteriaObj;
    }

    /**
     * Handler method that responds to the client's request to display a new
     * creditor search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doNewSearch() throws ActionHandlerException {
	this.setFirstTime(true);
	this.startSearchConsole();
	this.creditors = new ArrayList();
	this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	this.sendClientData();
    }

    /**
     * Handler method that responds to the client's request to perform a
     * creditor search using the selection criterai entered by the user.
     * 
     * @throws ActionHandlerException
     */
    protected void doSearch() throws ActionHandlerException {
	this.setFirstTime(false);
	this.buildSearchCriteria();
	this.query.setQuerySource("CreditorView");
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
    }

    /**
     * Builds selection criteria based on business contact data values, if available.  
     * Creditor criteria is represented by <i>creditor type id</i> and <i>account number</i>.   
     * <i>Name</i>, <i>tax id</i>, and <i>main phone number</i> are the only properties 
     * recognized as business contact data.  Creditor and business contact data are mutual 
     * exclusive when determining which criteria group is active.  
     * 
     * @param query 
     *           {@link RMT2TagQueryBean} object containing the key/value pair data items used to 
     *          build the service parameters.
     * @param searchMode 
     *          Set to either {@link RMT2ServletConst.SEARCH_MODE_NEW} or 
     *          {@link RMT2ServletConst.SEARCH_MODE_OLD} 
     * @throws ActionHandlerException 
     *          When creditor values and business contact values are discovered for the same 
     *          search transaction.
     */
    protected void doPostCustomInitialization(RMT2TagQueryBean query, int searchMode) throws ActionHandlerException {
	CreditorCriteria cc = (CreditorCriteria) query.getCustomObj();
	StringBuffer sql = new StringBuffer();
	if (this.criteriaType == CRITERIATYPE_CONTACT) {
	    if (!cc.getQry_Name().equals("")) {
		sql.append(CreditorConst.CRITERIATAGS_CONTACT[0]);
		sql.append(" = ");
		sql.append(cc.getQry_Name().trim());
	    }
	    if (!cc.getQry_PhoneMain().equals("")) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append(CreditorConst.CRITERIATAGS_CONTACT[1]);
		sql.append(" = ");
		sql.append(cc.getQry_PhoneMain().trim());
	    }
	    if (!cc.getQry_TaxId().equals("")) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append(CreditorConst.CRITERIATAGS_CONTACT[2]);
		sql.append(" = ");
		sql.append(cc.getQry_TaxId().trim());
	    }
	}
	if (this.criteriaType == CRITERIATYPE_CREDITOR) {
	    if (!cc.getQry_AccountNo().equals("")) {
		sql.append(CreditorConst.CRITERIATAGS_CREDITOR[0]);
		sql.append(" like ");
		sql.append(cc.getQry_AccountNo().trim());
	    }
	    if (!cc.getQry_CreditorTypeId().equals("")) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append(CreditorConst.CRITERIATAGS_CREDITOR[1]);
		sql.append(" = ");
		sql.append(cc.getQry_CreditorTypeId().trim());
	    }
	}
	query.setWhereClause(null);
	query.setWhereClause(sql.toString());
	return;
    }

    /**
     * Fetches the list creditors from the database using the where clause criteria 
     * previously stored on the session during the phase of the request to builds 
     * the query predicate.
     * 
     * @throws ActionHandlerException
     */
    protected void listData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi api = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    CreditorCriteria criteria = (CreditorCriteria) this.query.getCustomObj();
	    this.creditors = (List<CreditorExt>) api.findCreditorBusiness(criteria);

	    if (this.creditors == null) {
		this.creditors = new ArrayList<CreditorExt>();
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    tx.close();
	    api = null;
	    tx = null;
	}
	this.sendClientData();
    }

    /**
     * Determines if search is to be performed using business contact criteria or 
     * creditor criteria based on the data submitted by the client.  Criteria is 
     * only valid when the availability of contact or creditor criteria is mutually 
     * exclusive.  Otherwise, an error is thrown.
     * 
     * @param criteria 
     *           Business contact or creditor selection criteria data.
     * @throws ActionHandlerException 
     *           When bothe the creditor and contact criteria is present.
     */
    private void validateCriteria(CreditorCriteria criteria) throws ActionHandlerException {
	boolean useCredParms = false;
	boolean useContactParms = false;

	useCredParms = (!criteria.getQry_CreditorTypeId().equals("") || !criteria.getQry_AccountNo().equals(""));
	useContactParms = (!criteria.getQry_TaxId().equals("") || !criteria.getQry_Name().equals("") || !criteria.getQry_PhoneMain().equals(""));

	if (useCredParms && useContactParms) {
	    this.msg = "The availability of creditor and business contact criteria must be mutually exclusive";
	    CreditorSearchAction.logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	if (useCredParms) {
	    this.criteriaType = CreditorSearchAction.CRITERIATYPE_CREDITOR;
	}
	if (useContactParms) {
	    this.criteriaType = CreditorSearchAction.CRITERIATYPE_CONTACT;
	}
	if (!useContactParms && !useCredParms) {
	    this.criteriaType = CreditorSearchAction.CRITERIATYPE_ALL;
	}
    }
}
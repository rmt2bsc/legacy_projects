package com.employee;


import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceAdapter;

import com.bean.RMT2TagQueryBean;

import com.bean.criteria.EmployeeCriteria;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;


import com.util.NotFoundException;
import com.util.SystemException;

/**
 * This class provides functionality needed to serve the requests of the Employee 
 * Search user interface.   The majority of the its functionality lies within 
 * the ancestor object, EmployeeCommonAction, from the com.employee package. 
 * 
 * @author Roy Terrell
 *
 */
public class EmployeeSearchAction extends EmployeeCommonAction implements ICommand {
    private static final String COMMAND_NEWSEARCH = "EmployeeSearch.Search.newsearch";

    private static final String COMMAND_SEARCH = "EmployeeSearch.Search.search";

    public static final String COMMAND_LIST = "EmployeeSearch.Search.list";

    private static final String COMMAND_ADD = "EmployeeSearch.Search.add";
    
    private static final String COMMAND_EDIT = "EmployeeSearch.Search.edit";

    private static final String COMMAND_BACK = "EmployeeSearch.Search.back";

    private static Logger logger = Logger.getLogger(EmployeeSearchAction.class);

    private int empId;

    /**
     * Default constructor which creates a EmployeeSearchAction object 
     * and sets up the logger.
     *
     */
    public EmployeeSearchAction() {
	super();
	EmployeeSearchAction.logger.log(Level.INFO, "Logger initialized"); 
    }

    /**
     * Creates a EmployeeSearchAction object containing the application context 
     * and the user's request.   
     * 
     * @param context 
     *          The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public EmployeeSearchAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);
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

	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);

	if (command.equalsIgnoreCase(EmployeeSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(EmployeeSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(EmployeeSearchAction.COMMAND_LIST)) {
	    this.listData();
	}
	if (command.equalsIgnoreCase(EmployeeSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(EmployeeSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(EmployeeSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }
    
    /**
     * Retrieve the selected employee id from a list of employees.
     *  
     * @throws ActionHandlerException 
     *           Problem Identifying Employee Id
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
    }

    /**
     * Preserves the user's input values regarding the selection criteria. 
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	
    }

    /**
     * Returns selection criteria that is sure to retrun an empty result set
     * once applied to the sql that pertains to the data source of the customer
     * search page.
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	return "employee_id = -1";
    }

    /**
     * Creates an instance of {@link com.bean.criteria.EmployeeCriteria EmployeeCriteria}, which 
     * is used to track the user's selection criteria input.   This method uses 
     * introspection to gather user's input into the criteria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	EmployeeCriteria criteriaObj = EmployeeCriteria.getInstance();
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
		this.setBaseView("VwEmployeeExtView");
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Employee Search request parameters:  " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	}
	return criteriaObj;
    }

    /**
     * Handler method that responds to the client's request to display a new
     * customer search page.
     * 
     * @throws ActionHandlerException
     */
    protected void doNewSearch() throws ActionHandlerException {
	this.setFirstTime(true);
	this.startSearchConsole();
	this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	EmployeeCriteria criteriaObj = (EmployeeCriteria) this.query.getCustomObj();
	this.query.setCustomObj(criteriaObj.toXml());
	this.listData();
	this.sendClientData();
    }

    /**
     * Handler method that responds to the client's request to perform a
     * customer search using the selection criterai entered by the user.
     * 
     * @throws ActionHandlerException
     */
    protected void doSearch() throws ActionHandlerException {
	this.setFirstTime(false);
	this.buildSearchCriteria();
	EmployeeCriteria criteriaObj = (EmployeeCriteria) this.query.getCustomObj();
	this.query.setCustomObj(criteriaObj.toXml());
	this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
    }

  

    /**
     * Fetches the list customers from the database using the where clause criteria 
     * previously stored on the session during the phase of the request to builds 
     * the query predicate.
     * 
     * @throws ActionHandlerException
     */
    protected void listData() throws ActionHandlerException {
	super.listData();
	this.sendClientData();
    }
    
    
    /**
     * Fetches the profile of the selected employee from the database.  Requires that the selected employee id 
     * is mapped to the "SelCbx" UI property.
     * 
     * @throws ActionHandlerException
     *            When the employee id cannot be found, employee id is not mapped to "SelCbx" UI property, 
     *            or for general database access errors. 
     */
    public void edit() throws ActionHandlerException {
	String val;
	try {
	    val = this.getPropertyValue("SelCbx");
	}
	catch (NotFoundException e) {
	    val = null;
	}
	try {
	    this.empId = Integer.parseInt(val);
	}
	catch (NumberFormatException e) {
	    this.empId = 0;
	}
	if (this.empId == 0) {
	    this.msg = "An employee must be selected";
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	
	// Fetch Single employee record
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.employee = api.findEmployeeExt(this.empId);
	    if (this.employee == null) {
		// Create Empty result set
		this.employee = EmployeeCommonAction.EMPTY_EMP_DOC;
	    }
	    this.dropDownData = this.fetchLookupData();
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
    }

}
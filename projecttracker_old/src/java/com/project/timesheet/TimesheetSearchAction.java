package com.project.timesheet;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;
import com.project.timesheet.TimesheetApi;
import com.employee.EmployeeApi;
import com.employee.EmployeeFactory;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceAdapter;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.RMT2TagQueryBean;
import com.bean.VwClientTimesheetSummary;
import com.bean.VwEmployeeExt;

import com.bean.criteria.TimesheetCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.project.ProjectConst;
import com.project.ProjectException;

import com.util.RMT2Date;
import com.util.SystemException;

import com.employee.EmployeeException;

/**
 * This class provides action handlers to respond to the client's commands from
 * the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages.
 * 
 * @author Roy Terrell
 * 
 */
public class TimesheetSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "Timesheet.Search.add";

    private static final String COMMAND_EDIT = "Timesheet.Search.edit";

    private static final String COMMAND_SEARCH = "Timesheet.Search.search";

    private static final String COMMAND_NEWSEARCH = "Timesheet.Search.newsearch";

    private static final String COMMAND_OLDSEARCH = "Timesheet.Search.oldsearch";

    private static final String COMMAND_BULKINVOICE = "Timesheet.Search.bulkinvoice";

    public static final String COMMAND_LIST = "Timesheet.Search.list";

    private VwEmployeeExt emp;

    private Logger logger;

    private int timesheetId;

    private List timesheets;

    private List clients;

    private List employees;

    private List status;
    
    private Object tsSummary;

    private String mode; // M=Manager, E=Employee

    private boolean searchMode;

    RMT2SessionBean userSession;

    /**
     * Default constructor.
     * 
     */
    public TimesheetSearchAction() {
	logger = Logger.getLogger("TimesheetSearchAction");
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#init(com.controller.Context, com.controller.Request)
     */
    @Override
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
	this.userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

	// Get employee that is currently logged in
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi empApi = EmployeeFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.emp = (VwEmployeeExt) empApi.getLoggedInEmployee();
	}
	catch (EmployeeException e) {
	    throw new SystemException(e.getMessage());
	}
	finally {
		empApi.close();
		empApi = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Processes the Add, Edit, Delete, Save, Back, and List commands issued
     * from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages.
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
	try {
	    // Get timesheet maintenance mode
	    if (!command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_NEWSEARCH)) {
		this.initMode();
	    }
	    this.searchMode = false;
	    if (command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_NEWSEARCH)) {
		this.newSearch();
	    }
	    else if (command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_ADD)) {
		this.addData();
	    }
	    else if (command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_EDIT)) {
		this.editData();
	    }
	    else if (command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_SEARCH)) {
		this.doSearch();
	    }
	    else if (command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_OLDSEARCH)) {
		this.searchMode = true;
		this.getTimesheetList();
	    }
	    else if (command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_LIST)) {
		this.searchMode = true;
		this.getTimesheetList();
	    }
	    else if (command.equalsIgnoreCase(TimesheetSearchAction.COMMAND_BULKINVOICE)) {
		this.getClientTimesheetSummaryList();
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    // Ensure that any update made to the the query object is set on the session.
	    if (this.query != null) {
		this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	    }
	}
    }

    /**
     * Retrieves the mode from the client and assigns the value to an internal
     * variable.
     * 
     */
    private void initMode() throws SystemException {
	// If the timesheet maintenance mode exist on the request, then store
	// the mode on the session for later use
	this.mode = (String) this.request.getParameter(ProjectConst.CLIENT_DATA_MODE);
	if (this.mode == null) {
	    // Get mode from session since it has been already determined from
	    // previous action.
	    this.mode = (String) this.query.getKeyValues(ProjectConst.CLIENT_DATA_MODE);
	}
	else {
	    // This is the first invoication of this page...store the mode on
	    // the session.
	    this.query.addKeyValues(ProjectConst.CLIENT_DATA_MODE, this.mode);
	}
    }

    /**
     * Performs a search for timesheets related to the logged on user.
     * 
     * @throws ActionHandlerException
     */
    protected void newSearch() throws ActionHandlerException {
	this.mode = (String) this.request.getParameter(ProjectConst.CLIENT_DATA_MODE);
	this.startSearchConsole();
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	try {
	    this.initMode();
	}
	catch (Exception e) {
	    // Do nothing
	}
    }

    /**
     * Builds the search criteria that is to be used to retrieve the timesheets
     * requested by the client. Once the selection criteria is computed, it is
     * stored on the session object and is identified as
     * {@link RMT2ServletConst.QUERY_BEAN}.
     * 
     * @throws ActionHandlerException
     *             if a database access error occurs.
     */
    protected void doSearch() throws ActionHandlerException {
	this.buildSearchCriteria();
    }

    /**
     * Setup the default criteria that is required for the first invocation of
     * the Timesheet Search page. As of this version the default selection
     * criteria is said to be:
     * <ul>
     * <li>The timesheet end period range should be between the current date
     * and 60 days prior to the current date.</li>
     * <li>Only those timesheets allowable by the mode of the user are
     * displayed.</li>
     * </ul>
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	String endPeriod1 = null;
	String endPeriod2 = null;
	Date weekDates[];
	Calendar tempCal = null;

	try {
	    weekDates = RMT2Date.getWeekDates(new java.util.Date());
	    // Get ending period date value for the first and second relational
	    // expressions
	    endPeriod2 = RMT2Date.formatDate(weekDates[6], "MM/dd/yyyy");
	    // Get end period date value so that we can report the last 8 time
	    // periods.
	    tempCal = Calendar.getInstance();
	    tempCal.setTime(weekDates[6]);
	    tempCal.add(Calendar.DAY_OF_MONTH, -60);
	    endPeriod1 = RMT2Date.formatDate(tempCal.getTime(), "MM/dd/yyyy");

	    // Set default end period values in custom criteria object. At this
	    // point, custom object should be valid since
	    // doCustomInitialization()
	    // was executed prior to this call.
	    TimesheetCriteria criteriaObj = _query.getCustomObj() == null ? new TimesheetCriteria() : (TimesheetCriteria) _query.getCustomObj();
	    criteriaObj.setQry_EndPeriod1(endPeriod1);
	    criteriaObj.setQry_EndPeriod2(endPeriod2);
	    _query.setCustomObj(criteriaObj);

	    // Get SQL Predicate based on above values
	    String predicate = this.appendCustomPredicate(_query);
	    return predicate;

	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * Creates the {@link TimesheetCriteria} object and attempts to obtain the
     * criteria data from the client's request.
     * 
     * @return Object The criteria object
     * @throws ActionHandlerException
     *             Error transferring client data to the criteria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	this.setBaseClass("com.bean.VwTimesheetList");
	this.setBaseView("VwTimesheetListView");
	TimesheetCriteria criteriaObj = TimesheetCriteria.getInstance();
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Project Search request parameters:  " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	}
	return criteriaObj;
    }

    /**
     * Builds selection criteria using the timesheet's begin and end period date
     * values and the user mode. Once the the SQL predicate has been determine,
     * it is added the where clause property of the RMT2TagQueryBean object on
     * the user's session.
     * 
     * @param query
     *            {@link RMT2TagQueryBean} object.
     * @param _searchMode
     *            Set to either {@link RMT2ServletConst.SEARCH_MODE_NEW} or
     *            {@link RMT2ServletConst.SEARCH_MODE_OLD}. Not used for this
     *            implementation.
     * @throws ActionHandlerException
     */
    protected void doPostCustomInitialization(RMT2TagQueryBean query, int _searchMode) throws ActionHandlerException {
	try {
	    TimesheetCriteria criteriaObj = query.getCustomObj() == null ? new TimesheetCriteria() : (TimesheetCriteria) query.getCustomObj();
	    // Get input values from controls that are specially named.
	    String endPeriod1 = this.request.getParameter("qry_EndPeriod1");
	    criteriaObj.setQry_EndPeriod1(endPeriod1);
	    String endPeriod2 = this.request.getParameter("qry_EndPeriod2");
	    criteriaObj.setQry_EndPeriod2(endPeriod2);
	    query.setCustomObj(criteriaObj);

	    String predicate = this.appendCustomPredicate(query);
	    query.setWhereClause(predicate);
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

    /**
     * Builds the SQL predicates for the timesheet's end period. The resulting
     * predicate contain an expression involving a single end period date or a
     * date range for the end period date. Populate either date1 or date2 to
     * produce a single expression. For compound expressions, both dates must
     * have a value.
     * 
     * @param date1
     *            First end period date.
     * @param date2
     *            Second end period date.
     * @return SQL Predicate.
     */
    private String setupEndPeriodPredicate(String date1, String date2) {
	String criteria = null;
	// Ensure that dates have a value or is null.
	date1 = ("".equals(date1) ? null : date1);
	date2 = ("".equals(date2) ? null : date2);

	// Build SQl Predicate
	if (date1 != null && date2 == null) {
	    criteria = " end_period = \'" + date1 + "\'";
	}
	if (date1 == null && date2 != null) {
	    criteria = " end_period = \'" + date2 + "\'";
	}
	if (date1 != null && date2 != null) {
	    criteria = " end_period between \'" + date1 + "\' and \'" + date2 + "\'";
	}
	return criteria;
    }

    /**
     * Builds a SQL predicate based on the user mode. The user mode indicates if
     * the user is a normal employee or a manager. An employee is only allowed
     * to see his timesheets. A manager can view timesheets pertaining to
     * himself as well as the employees he manages.
     * 
     * @param query
     *            {@link RMT2TagQueryBean}
     * @return The SQL Predicate.
     */
    private String setupUserModePredicate(RMT2TagQueryBean query) {
	try {
	    // Set default values in custom criteria object. At this point,
	    // custom object should
	    // be valid since doCustomInitialization() was executed prior to
	    // this call.
	    TimesheetCriteria criteriaObj = query.getCustomObj() == null ? new TimesheetCriteria() : (TimesheetCriteria) query.getCustomObj();
	    String temp = this.request.getParameter("qry_EmpId");
	    criteriaObj.setQry_EmpId(String.valueOf(temp));
	    query.setCustomObj(criteriaObj);

	    String sql = null;
	    // Setup the SQL predicate for User Mode
	    if (this.mode.equalsIgnoreCase(ProjectConst.TIMESHEET_MODE_MANAGER)) {
		sql = " manager_id = " + this.emp.getEmployeeId();
	    }
	    else if (this.mode.equalsIgnoreCase(ProjectConst.TIMESHEET_MODE_EMPLOYEE)) {
		sql = " emp_id = " + this.emp.getEmployeeId();
		criteriaObj.setQry_EmpId(String.valueOf(String.valueOf(this.emp.getEmployeeId())));
	    }
	    return sql;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    return null;
	}
    }

    /**
     * Manages all custom predicate builders and combines the results of each.
     * Currently, the end period data and user mode are managed.
     * 
     * @param query
     *            {@link RMT2TagQueryBean}
     * @return SQL Predicate
     */
    protected String appendCustomPredicate(RMT2TagQueryBean query) {
	TimesheetCriteria criteriaObj = (TimesheetCriteria) query.getCustomObj();
	String date1 = criteriaObj.getQry_EndPeriod1();
	String date2 = criteriaObj.getQry_EndPeriod2();
	// Determine end period predicate
	String endPeriodSql = this.setupEndPeriodPredicate(date1, date2);
	// Determine user mode predicate
	String userModeSql = this.setupUserModePredicate(query);
	// Append end period sql predicate, if applicable.
	String predicate = query.getWhereClause();
	if (predicate != null && predicate.length() > 0) {
	    predicate += " and " + userModeSql;
	}
	else {
	    predicate = userModeSql;
	}
	if (endPeriodSql != null) {
	    if (predicate != null && predicate.length() > 0) {
		predicate += " and " + endPeriodSql;
	    }
	    else {
		predicate = endPeriodSql;
	    }
	}
	return predicate;
    }

    /**
     * Declares the results of the timesheet query are orderd by end period and
     * timesheet id, which both fields are in descending order.
     * 
     */
    protected void doOrderByClause(RMT2TagQueryBean query) {
	String orderBy = query.getOrderByClause();
	if (orderBy == null) {
	    orderBy = " end_period desc, timesheet_id desc ";
	    query.setOrderByClause(orderBy);
	}
	return;
    }

    /**
     * Queries the database using the selection criteria provided by the user to
     * obtain one or more timesheets. Various database fetches are performed to
     * gather data for list of timesheets, the customer, the employee, timesheet
     * status, and the timesheet user mode entities.
     * 
     * @throws ActionHandlerException
     *             General database errors
     */
    protected void getTimesheetList() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
    	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
    	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
    	EmployeeApi empApi = EmployeeFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
    	TimesheetStatusApi tssApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get Timesheets
	    String criteria = this.query.getWhereClause();
	    this.timesheets = (List) tsApi.findTimesheetExt(criteria);
	    if (this.timesheets == null) {
		this.timesheets = new ArrayList();
	    }

	    // Get employees
	    String emplCriteria = null;
	    if (this.mode.equalsIgnoreCase(ProjectConst.TIMESHEET_MODE_MANAGER)) {
		// Ensure that user can see the timesheets of all employees
		// he/she manages.
		emplCriteria = "manager_id = " + this.emp.getEmployeeId();
	    }
	    else if (this.mode.equalsIgnoreCase(ProjectConst.TIMESHEET_MODE_EMPLOYEE)) {
		// Ensure that user can see only his/her timesheets
		emplCriteria = "login_name = '" + userSession.getLoginId() + "'";
	    }
	    this.employees = (List) empApi.findEmployeeExt(emplCriteria);
	    if (this.employees == null) {
		this.employees = new ArrayList();
	    }

	    // Get clients
//	    this.clients = (List) api.findAllClients();
	    this.clients = (List) api.findClientExt();
	    if (this.clients == null) {
		this.clients = new ArrayList();
	    }

	    // Get timesheet status
	    this.status = tssApi.findTimesheetStatus();
	    if (this.status == null) {
		this.status = new ArrayList();
	    }

	    // Send data to client
	    this.sendClientData();
	    return;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
		tsApi.close();
		tsApi = null;
		ptsApi.close();
		ptsApi = null;
		empApi.close();
		empApi = null;
		tssApi.close();
		tssApi = null;
		tx.close();
		tx = null;
	}
    }

    
    /**
     * Handler for servicing client timesheet summary request.   Uses the 
     * Timesheet api to retrieve a List of VwClientTimesheetSummary instances.
     * 
     * @throws ActionHandlerException
     */
    protected void getClientTimesheetSummaryList()  throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
    	try {
    	    this.tsSummary = tsApi.getTimesheetSummary(null);
    	    if (this.tsSummary == null) {
    		this.tsSummary = new ArrayList();
    	    }
    	    this.sendClientData();
	}
	catch (ProjectException e) {
	    e.printStackTrace();
	    throw new ActionHandlerException(e);
	}
	finally {
		tsApi.close();
		tsApi = null;
		tx.close();
		tx = null;
	}
    }

    
    /**
     * Prepares to add a timesheet to the systems
     * 
     * @throws ActionHandlerException
     *             if a database access error occurs.
     */
    public void add() throws ActionHandlerException {
	this.timesheetId = 0;
	return;
    }

    /**
     * Obtains a {@link ProjProject} object from the using the project id
     * selected by the client and prepares to send the object as a response to
     * the client via the Requst object. The selected ProjProject object is
     * identified on the request object as, "project".
     * 
     * @throws ActionHandlerException
     *             If a database access error occurs or Project was not found
     *             using the selected project id.
     */
    public void edit() throws ActionHandlerException {
	String temp = null;
	try {
	    temp = this.getPropertyValue("Id");
	    this.timesheetId = Integer.parseInt(temp);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	return;
    }

    /**
     * Accepts the {@link ProjProject} object from the client and applies any
     * modifications to the database.
     * 
     * @throws ActionHandlerException
     *             if a problem occurred updating the Project.
     * @throws DatabaseException
     *             when the trasnaction changes fail to commit or rollback.
     */
    public void save() throws ActionHandlerException, DatabaseException {
	return;
    }

    /**
     * Stubbed
     * 
     * @throws ActionHandlerException
     *             if a problem occurred deleting the Project.
     * @throws DatabaseException
     *             when the trasnaction changes fail to commit or rollback.
     */
    public void delete() throws ActionHandlerException, DatabaseException {
	return;
    }

    /**
     * Stubbed
     * 
     */
    protected void receiveClientData() {
	return;
    }

    /**
     * Initialize request object with data to be sent to the client. When this
     * acion handler is in search mode, data is sent across the wire to render
     * the search page. Otherwise, data is sent to render a single timesheet. If
     * the target timesheet exist in the system, then the data is obtained from
     * the database using TimesheetEditAction. Otherwise, new data objects are
     * instaintiated and sent to the client.
     * 
     * The following list dipicts the java objects and their aossociated names
     * that are stored in the HttpServletRequest object when transit between the
     * server and the client: <table border="1">
     * <tr>
     * <th align="left"><strong>Java Data Object</strong></th>
     * <th><strong>Id on client</strong></th>
     * </tr>
     * <tr>
     * <td>List of {@link VwTimesheetList}</td>
     * <td>timesheets</td>
     * </tr>
     * <tr>
     * <td>{@link CustomerDetails}</td>
     * <td>clients</td>
     * </tr>
     * <tr>
     * <td>{@link VwEmployeeExt}</td>
     * <td>employees</td>
     * </tr>
     * <tr>
     * <td>{@link ProjTimesheetStatus}</td>
     * <td>status</td>
     * </tr>
     * <tr>
     * <td>List of {@link VwClientTimesheetSummary}</td>
     * <td>status</td>
     * </tr>
     * <tr>
     * <td>String</td>
     * <td>timesheetmode</td>
     * </tr>
     * </table>
     * 
     * @throws ActionHandlerException
     * @see {@link TimesheetEditAction#refreshClientData(int)}
     */
    protected void sendClientData() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	DatabaseConnectionBean con = (DatabaseConnectionBean) tx.getConnector();
    	SetupApi api = SetupFactory.createApi(con, this.request);
    	EmployeeApi empApi = EmployeeFactory.createApi(con);
    	try {
    		if (this.searchMode) {
    		    this.request.setAttribute(ProjectConst.CLIENT_DATA_TIMESHEETS, this.timesheets);
    		    this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENTS, this.clients);
    		    this.request.setAttribute(ProjectConst.CLIENT_DATA_EMPLOYEES, this.employees);
    		    this.request.setAttribute(ProjectConst.CLIENT_DATA_STATUSES, this.status);
    		    this.request.setAttribute(ProjectConst.CLIENT_DATA_MODE, this.mode);
    		}
    		else {
    		    // We are trying to edit an existing timesheet.
    		    if (this.timesheetId == 0) {
    			// Get all employee related clients
    			try {
    			    this.clients = (List) empApi.findClients(this.emp.getEmployeeId());
                            if (this.clients == null) {
                                this.clients = new ArrayList();
                            }
                        }
                        catch (EmployeeException e) {
                            throw new ActionHandlerException(e);
                        }
    			

    			//List list = this.getClientList();
    			String endingPeriodDates[] = this.getEndingPeriods();
    			this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENTS, this.clients);
    			this.request.setAttribute(ProjectConst.CLIENT_DATA_DATES, endingPeriodDates);
    		    }
    		    else if (this.timesheetId > 0) {
    			TimesheetEditAction editAction = new TimesheetEditAction(this.request, null, null);
    			editAction.refreshClientData(this.timesheetId);
    		    }
    		}
    		this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENT_TS_SUM, this.tsSummary);
    		return;		
    	}
    	catch (Exception e) {
    		
    	}
    	finally {
    		api.close();
    		api = null;
    		tx.close();
    		tx = null;
    	}
	
    }


    /**
     * Retrieves a list of available ending period dates to be used as a
     * dropdown on the Project Maintenance Edit page.
     * 
     * @return String array of dates or null if error occured
     */
    private String[] getEndingPeriods() {
	String endingPeriodDates[] = null;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
		endingPeriodDates = tsApi.getAvailableEndingPeriods();
		return endingPeriodDates;
	}
	catch (Exception e) {
		return null;
	}
	finally {
		tsApi.close();
		tsApi = null;
		tx.close();
		tx = null;	
	}
    }


    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#doBack()
     */
    @Override
    protected void doBack() throws ActionHandlerException {
	super.doBack();
    }

}
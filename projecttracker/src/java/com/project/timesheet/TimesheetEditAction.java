package com.project.timesheet;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Date;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.ProjProjectTask;
import com.bean.ProjProject;
import com.bean.ProjTask;
import com.bean.ProjTimesheet;
import com.bean.ProjClient;
import com.bean.VwEmployeeExt;
import com.bean.ProjTimesheetStatus;
import com.bean.ProjTimesheetHist;
import com.bean.ProjEvent;
import com.bean.VwTimesheetProjectTask;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.constants.RMT2ServletConst;

import com.employee.EmployeeException;
import com.employee.EmployeeApi;
import com.employee.EmployeeFactory;

import com.project.ProjectConst;
import com.project.ProjectException;

import com.project.invoice.InvoicingApi;
import com.project.invoice.InvoicingException;
import com.project.invoice.InvoicingFactory;

import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;

import com.project.timesheet.TimesheetApi;

import com.project.timesheet.TimesheetStatusApi;

import com.util.RMT2Date;
import com.util.SystemException;
import com.util.NotFoundException;

/**
 * This class provides action handlers to respond to the client's commands from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages. 
 * Handles requests pertaining to adding, deleting, saving, submitting (finalize), approving, declining and invoicing timehseets.
 * 
 * @author Roy Terrell
 *
 */
public class TimesheetEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "Timesheet.Edit.add";

    private static final String COMMAND_DELETE = "Timesheet.Edit.delete";

    private static final String COMMAND_SAVE = "Timesheet.Edit.save";

    private static final String COMMAND_FINALIZE = "Timesheet.Edit.finalize";

    private static final String COMMAND_APPROVE = "Timesheet.Edit.approve";

    private static final String COMMAND_DECLINE = "Timesheet.Edit.decline";

    private static final String COMMAND_INVOICE = "Timesheet.Edit.invoice";

    private static final String COMMAND_BACK = "Timesheet.Edit.back";

    private static final String COMMAND_BULKINVOICE = "Project.BulkTimesheetInvoice.bulkinvoicesubmit";

    private static final String COMMAND_BULKINVOICE2 = "Project.BulkTimesheetInvoice.bulkinvoicesubmit2";

    private Logger logger;

    private VwEmployeeExt employee;

    private ProjTimesheet timesheet;

    private ProjClient client;

    private ProjTimesheetStatus pts;

    private ProjTimesheetHist ptsh;

    private Hashtable tsProjTaskHrs;

    private Date dates[];

    private int selectedItems[];

    private String mode;

    /**
     * Default constructor.
     *
     */
    public TimesheetEditAction() {
	logger = Logger.getLogger("TimesheetEditAction");
    }

    /**
     * Constructor for instantiating a TimesheetEditAction object using request, response, and command.
     * 
     * @param request The HttpServletRequest containing the clinets data.
     * @param response The HttpServletResponse
     * @param command The clients command.
     * @throws ActionHandlerException
     */
    public TimesheetEditAction(Request request, Response response, String command) throws ActionHandlerException {
	this();
	try {
	    this.init(null, request);
	    this.init();
	}
	catch (Exception e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

    /**
     * Initializes the following API's: <code>SetupApi</code>, <code>TimesheetApi</code>, 
     * <code>EmployeeApi</code>, and <code>TimesheetStatusApi</code>.
     * 
     * @see com.bean.RMT2Base#init()
     */
    public void init() {
	super.init();
	return;
    }

    /**
     * Initializes the necessary api's needed make a TimesheetEditAction functional.  The 
     * following api's created are SetupApi, TimesheetApi, TimesheetStatusApi, and the 
     * EmployeeApi.
     * 
     * @param context the servet context
     * @param request the http servlet request
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);

	// Get employee that is currently logged in
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi empApi = EmployeeFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.employee = (VwEmployeeExt) empApi.getLoggedInEmployee();
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
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	try {
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_ADD)) {
		this.addData();
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_SAVE)) {
		this.saveData();
		this.refreshClientData(this.timesheet.getTimesheetId());
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_DELETE)) {
		this.delete();
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_FINALIZE)) {
		this.submit();
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_APPROVE)) {
		this.approve();
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_DECLINE)) {
		this.decline();
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_BACK)) {
		this.doBack();
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_INVOICE)) {
		this.doSingleTimesheetInvoice();
	    }
	    if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_BULKINVOICE) || command.equalsIgnoreCase(TimesheetEditAction.COMMAND_BULKINVOICE2)) {
		//		this.doBulkTimesheetInvoice();
	    }

	}
	catch (Exception e) {
	    //	    this.transObj.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    // Ensure that any updates made to the the query object is set on the session. 
	    if (this.query != null) {
		this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	    }
	}
    }

    /**
     * Adds a new project-task row to the client's presentation. 
     * 
     * @throws ActionHandlerException if a database access error occurs.
     */
    public void add() throws ActionHandlerException {
	String temp = null;
	int projectId = 0;
	int taskId = 0;

	// Get existing data from the client
	this.receiveClientData();

	// Add a project task to the timesheet.
	try {
	    temp = this.getPropertyValue("selectedProjectId");
	    projectId = Integer.parseInt(temp);
	    temp = this.getPropertyValue("selectedTaskId");
	    taskId = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    this.msg = "Invalid value exist for either the project id or the task id";
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	catch (NotFoundException e) {
	    this.msg = "Error: both project and task are required when adding a task to the timesheet.  " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	this.addProjectTask(projectId, taskId);

	return;
    }

    /**
     * Gathers the data needed to populate a {@link VwTimesheetProjectTask} and an ArrayList of project-task hours using _projectId and _taskId.s 
     * _projectId and _taskId are used to retrieve the {@link ProjProject} and {@link ProjTask} data objects, respectively, from 
     * the database and the data from each object appropriately copied to the VwTimesheetProjectTask object.   Afterwards, the method responsible for gathering project-task related hours 
     * is invoked, and lastly, all data is added to a hash collection using the <doce>VwTimesheetProjectTask</code> as the key and the project-task hours (an ArrayList) as the value.
     *     
     * @param _projectId The id of the Project object
     * @param _taskId The id of the Task object
     * @throws ActionHandlerException Database access error.
     */
    private void addProjectTask(int _projectId, int _taskId) throws ActionHandlerException {
	ProjProject pp = null;
	ProjTask pt = null;
	VwTimesheetProjectTask ppt = null;

	// Get project-task data
	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    ppt = TimesheetFactory.createProjectTaskExt();
	    pp = (ProjProject) api.findProject(_projectId);
	    if (pp == null) {
		this.msg = "Failed to identify project when adding task to timesheet";
		this.logger.log(Level.ERROR, this.msg);
		throw new ProjectException(this.msg);
	    }
	    pt = (ProjTask) api.findTask(_taskId);
	    ppt.setProjectTaskId(0);
	    ppt.setProjectId(pp.getProjId());
	    ppt.setTaskId(pt.getTaskId());
	    ppt.setProjectName(pp.getDescription());
	    ppt.setTaskName(pt.getDescription());
	}
	catch (ProjectException e) {
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}

	// Setup ProjEvents objects for Project-Task's weekdays
	List list = this.addProjectTaskHours();
	this.tsProjTaskHrs.put(ppt, list);
    }

    /**
     * Builds an Arraylsit of  {@link ProjEvent} objects for each date of the current week.   The ProjEvent properties, id, project task id, and hours, are 
     * initialized to zero and the event date is set to one of the dates of the current week.
     * 
     * @return An ArrayList of java.util.Date objects.
     * @throws ActionHandlerException
     */
    private List addProjectTaskHours() {
	List list = new ArrayList();
	ProjEvent pe = null;

	for (int ndx = 0; ndx < this.dates.length; ndx++) {
	    pe = TimesheetFactory.createProjEvent();
	    pe.setEventId(0);
	    pe.setProjectTaskId(0);
	    pe.setHours(0);
	    pe.setEventDate(this.dates[ndx]);

	    // Add event to queue.
	    list.add(pe);
	}
	return list;
    }

    /**
     * Drives the process of saving the data of a single  timesheet.   First the base timesheet data is processed followed by the 
     * processing of each task's time.   This method is also responsible for procesing those tasks that are selected for deletion. 
     * 
     * @throws ActionHandlerException 
     * @throws DatabaseException when a database access error occurs.
     */
    public void save() throws ActionHandlerException, DatabaseException {
	int tsId = 0;
	int ptId = 0;
	ProjProjectTask ppt = null;
	VwTimesheetProjectTask pptExt = null;
	List events = null;
	int projId = 0;

	// Determine if we need to set this timesheet in Draft status
	boolean timesheetEditable = (this.timesheet.getTimesheetId() == 0);

	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	
	try {
	    // Validate timesheet
	    tsApi.validateTimesheet(this.timesheet);
	    
	    // Save base timesheet
	    tsId = tsApi.maintainTimesheet(this.timesheet);

	    // Save Project Task data
	    Enumeration <VwTimesheetProjectTask> keys = this.tsProjTaskHrs.keys();
	    while (keys.hasMoreElements()) {
		pptExt = keys.nextElement();
		

		try {
		    if (this.isSelected(pptExt.getTaskId())) {
			// Delete project task and its events
			tsApi.deleteProjectTask(pptExt.getProjectTaskId());
		    }
		    else {
			// Ensure that the current project id property of the timesheet api is set in order to perform validations against each task.
			if (tsApi.getCurrentProjectId() == 0) {
			    // Since this is the first time, all remaining projects must equal that of the first occurrence.
			    tsApi.setCurrentProjectId(pptExt.getProjectId());
			    projId = pptExt.getProjectId();
			}
			
			// Validate project task
			tsApi.validateTask(pptExt);
			
			// Apply changes to project task and its events
			ppt = TimesheetFactory.createProjectTask();
	                ppt.setProjectTaskId(pptExt.getProjectTaskId());
	                ppt.setProjId(pptExt.getProjectId());
	                ppt.setTaskId(pptExt.getTaskId());
	                ppt.setTimesheetId(tsId);
			ptId = tsApi.maintainProjectTask(ppt);
			events = (List) this.tsProjTaskHrs.get(pptExt);
			tsApi.maintainEvent(ptId, events);
		    }
		}
		catch (ProjectException e) {
		    logger.log(Level.ERROR, e.getMessage());
		    tx.rollbackUOW();
		    throw new ActionHandlerException(e.getMessage());
		}
		catch (InvalidTimesheetTaskException e) {
		    this.msg = "Unable to update timesheet due to an invalid timesheet task";
	            logger.error(this.msg);
	            throw new ActionHandlerException(this.msg, e);
		}
	    } // end while

	    // Set timesheet status to Draft.
	    if (timesheetEditable) {
		ptsApi.setTimesheetStatus(tsId, ProjectConst.TIMESHEET_STATUS_DRAFT);
	    }
	    
	    // if needed, update timesheet header with project id
	    if ((this.timesheet.getProjId() == 0 && this.timesheet.getTimesheetId() > 0 && projId > 0) || (this.timesheet.getProjId() != projId)) {
		this.timesheet.setProjId(projId);
		tsId = tsApi.maintainTimesheet(this.timesheet);
	    }
	    tx.commitUOW();
	    this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, "Timesheet was saved successfully");
	    return;
	}
	catch (InvalidTimesheetException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	catch (ProjectException e) {
	    tx.rollbackUOW();
	    this.msg = e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(e);
	}
	finally {
	    tsApi.close();
	    tsApi = null;
	    ptsApi.close();
	    ptsApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Deletes a client selected timesheet from the system which includes the removal of any related statuses.
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get timesheet data from request
	    this.timesheet = TimesheetFactory.createTimesheet();
	    TimesheetFactory.packageBean(this.request, this.timesheet);
	    try {
		ptsApi.deleteTimesheetStatus(this.timesheet.getTimesheetId());
		tsApi.deleteTimesheet(this.timesheet.getTimesheetId());
		tx.commitUOW();
	    }
	    catch (ProjectException e) {
		logger.log(Level.ERROR, e.getMessage());
		tx.rollbackUOW();
	    }
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    tsApi.close();
	    tsApi = null;
	    ptsApi.close();
	    ptsApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Receives the Timesheet data that was input by the client, and attempts to package the data into a useable format.
     * 
     * @throws ActionHandlerException if a validation error occurs.
     */
    protected void receiveClientData() throws ActionHandlerException {
	try {
	    // Get timesheet data from request
	    this.timesheet = TimesheetFactory.createTimesheet();
	    TimesheetFactory.packageBean(this.request, this.timesheet);

	    // Get selected items
	    this.selectedItems = this.getSelectedItems();

	    // Get common timesheet data from the database using an existing timesheet.
	    this.receiveCommonDbData();

	    // Get all hours for each project-task occurrence.
	    this.tsProjTaskHrs = this.getHttpClientTimesheetDetails();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * Retrieves common timesheet data from the database using the timesheet credentials set for this object    
     * The following data items are obtained:
     * <p>
     * <ul>
     * <li>A string array of Date object representing the days of the current timesheet period.</li>
     * <li>An employee object of type {@link VwEmployeeExt}</li>
     * <li>A client object of type {@link CustomerDetails}</li>
     * <li>A timesheet status object of type {@link ProjTimesheetStatus}</li>
     * </ul>
     * 
     * @throws ActionHandlerException
     */
    private void receiveCommonDbData() throws ActionHandlerException {
	int tsStatusId = 0;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	EmployeeApi empApi = EmployeeFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get all dates of the week pertaining to the timesheet's ending period
	    this.dates = tsApi.getTimePeriodWeekDates(this.timesheet.getEndPeriod());

	    // Get employee
	    this.employee = (VwEmployeeExt) empApi.findEmployeeExt(this.timesheet.getEmpId());

	    // Get Client data
	    this.client = this.getClient();

	    // Get Timesheet's current status.
	    this.ptsh = ptsApi.findTimesheetCurrentStatus(this.timesheet.getTimesheetId());
	    tsStatusId = (this.ptsh == null ? ProjectConst.TIMESHEET_STATUS_DRAFT : this.ptsh.getTimesheetStatusId());
	    this.pts = ptsApi.findTimesheetStatus(tsStatusId);
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
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Obtains the detail data for a timesheet from the client's request.   The details consist of 
     * data pertaining to Project, Task, and the hours for each project-task.
     *  
     * @return Hashtable 
     * @throws ActionHandlerException
     */
    private Hashtable getHttpClientTimesheetDetails() throws ActionHandlerException {
	Hashtable tsDetails = new Hashtable();
	List hours = null;
	List list = null;
	VwTimesheetProjectTask ppt = null;
	ProjProject pp = null;
	ProjTask pt = null;
	int row = 0;
	int projectTaskId = 0;
	int projectId = 0;
	int taskId = 0;
	String temp = null;
	String criteria = null;
	boolean rowFound = true;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    do {
		try {
		    //  Get Project Task Data.
		    temp = this.getPropertyValue("ProjectTaskId" + row);
		    projectTaskId = Integer.parseInt(temp);
		    temp = this.getPropertyValue("ProjectId" + row);
		    projectId = Integer.parseInt(temp);
		    temp = this.getPropertyValue("TaskId" + row);
		    taskId = Integer.parseInt(temp);

		    // Package ProjProjectTask object.
		    try {
			if (projectTaskId == 0) {
			    // This is a new project task.
			    ppt = TimesheetFactory.createProjectTaskExt();
			    pp = (ProjProject) api.findProject(projectId);
			    pt = (ProjTask) api.findTask(taskId);
			    ppt.setProjectTaskId(projectTaskId);
			    ppt.setProjectId(pp.getProjId());
			    ppt.setTaskId(pt.getTaskId());
			    ppt.setProjectName(pp.getDescription());
			    ppt.setTaskName(pt.getDescription());
			}
			else {
			    // Data exist for this project task id
			    api.setBaseClass("com.bean.VwTimesheetProjectTask");
			    api.setBaseView("VwTimesheetProjectTaskView");
			    criteria = " project_task_id = " + projectTaskId;
			    list = api.findData(criteria, null);
			    if (list.size() > 0) {
				ppt = (VwTimesheetProjectTask) list.get(0);
			    }
			    else {
				throw new ActionHandlerException("Problem retrieving ProjProjectTask object using id: " + projectTaskId);
			    }
			}
		    }
		    catch (ProjectException e) {
			this.msg = "Error: " + e.getMessage();
			logger.log(Level.ERROR, this.msg);
			throw new ActionHandlerException(this.msg);
		    }
		    catch (SystemException e) {
			this.msg = "Error: " + e.getMessage();
			logger.log(Level.ERROR, this.msg);
			throw new ActionHandlerException(this.msg);
		    }
		    // Get Project Task Hours Data
		    hours = this.getHttpClientProjectTaskHours(row);

		    // Create hour entry
		    tsDetails.put(ppt, hours);

		    // Process next
		    row++;
		}
		catch (NotFoundException e) {
		    rowFound = false;
		}
	    } while (rowFound);
	    return tsDetails;
	}
	catch (ActionHandlerException e) {
	    throw e;
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Obtains the hours for a given project-task of a timesheet found in the client's request..
     *   
     * @param _row The row id used to identify the project- task hours.
     * @return An ArrayList of  {@link ProjEvent} objects
     * @throws ActionHandlerException
     */
    private ArrayList getHttpClientProjectTaskHours(int _row) throws ActionHandlerException {
	ArrayList list = new ArrayList();
	ProjEvent pe = null;
	String propEventHrs = "EventHours";
	String propEventId = "EventId";
	String temp = null;
	String dayProcessing = null;
	double eventHrs = 0;
	int eventId = 0;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    for (int ndx = 0; ndx < RMT2Date.DAYS_OF_WEEK.length; ndx++) {
		try {
		    dayProcessing = RMT2Date.DAYS_OF_WEEK[ndx];
		    //  Get hours of the index day for the target row
		    temp = this.getPropertyValue(dayProcessing + propEventHrs + _row);
		    try {
			eventHrs = Double.parseDouble(temp);
		    }
		    catch (NumberFormatException e) {
			eventHrs = 0;
		    }

		    // Get event id of the index day for the target row
		    temp = this.getPropertyValue(dayProcessing + propEventId + _row);
		    try {
			eventId = Integer.parseInt(temp);
		    }
		    catch (NumberFormatException e) {
			eventId = 0;
		    }
		}
		catch (NotFoundException e) {
		    this.msg = "Error: Invalid total number of days for a project-task.  Day, " + dayProcessing + ", was not available for processing on row " + (_row + 1);
		    logger.log(Level.ERROR, this.msg);
		    throw new ActionHandlerException(this.msg);
		}

		// Set Modified hours for current event.
		try {
		    if (eventId == 0) {
			pe = TimesheetFactory.createProjEvent();
			pe.setEventDate(this.dates[ndx]);
		    }
		    else {
			pe = (ProjEvent) tsApi.findEvent(eventId);
		    }
		    pe.setHours(eventHrs);
		}
		catch (ProjectException e) {
		    this.msg = "Error obtaining event: " + e.getMessage();
		    logger.log(Level.ERROR, this.msg);
		    throw new ActionHandlerException(this.msg);
		}

		// Add event to queue.
		list.add(pe);
	    } // end for	
	    return list;
	}
	catch (Exception e) {
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
     * Builds a list of project task id's which are selected by the client.
     * 
     * @return A list of project task id's as an array of int.
     */
    private int[] getSelectedItems() {
	String selCbx = "RowId";
	String propTaskId = "TaskId";
	String temp = null;
	int row = 0;

	String selValues[] = this.request.getParameterValues(selCbx);
	if (selValues == null) {
	    return null;
	}

	int items[] = new int[selValues.length];
	for (int ndx = 0; ndx < selValues.length; ndx++) {
	    row = Integer.parseInt(selValues[ndx]);
	    temp = this.request.getParameter(propTaskId + row);
	    if (temp == null) {
		continue;
	    }
	    items[ndx] = Integer.parseInt(temp);
	}
	return items;
    }

    /**
     * Verifies if _item is included in the list of selected project tasks.
     * 
     * @param _item The id of a project-task
     * @return true for selected.  Otherwise, false.
     */
    private boolean isSelected(int _item) {
	if (this.selectedItems == null) {
	    return false;
	}
	for (int ndx = 0; ndx < this.selectedItems.length; ndx++) {
	    if (_item == this.selectedItems[ndx]) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Drives the process of retrieving timesheet data from the database into objects which is useable by the client.    
     * This method is generally used to refresh the client's presentation with the data to confirm an changes made.
     * 
     * @param _timesheetId The id of the timesheet that is currently processed.
     * @throws ActionHandlerException
     */
    public void refreshClientData(int _timesheetId) throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get timesheet data from database
	    this.timesheet = (ProjTimesheet) tsApi.findTimesheet(_timesheetId);

	    // Get common timesheet data from the database using an existing timesheet.
	    this.receiveCommonDbData();

	    // Get all hours for each project-task occurrence.
	    this.tsProjTaskHrs = this.refreshTimesheetDetails(_timesheetId);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	}
	finally {
	    this.sendClientData();
	    tsApi.close();
	    tsApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Retrieves each project-task detail and its hours from the database for _timesheetId.
     * 
     * @param _timesheetId The id of the timesheet to obtain data.
     * @return 
     * @throws ActionHandlerException
     */
    private Hashtable refreshTimesheetDetails(int _timesheetId) throws ActionHandlerException {
	Hashtable tsDetails = new Hashtable();
	List ptList = null;
	List ptHrList = null;
	VwTimesheetProjectTask tpt = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    ptList = (List) tsApi.findProjectTaskExtByTimesheet(_timesheetId);
	    for (int ndx = 0; ndx < ptList.size(); ndx++) {
		tpt = (VwTimesheetProjectTask) ptList.get(ndx);
		ptHrList = this.refreshProjectTaskHours(tpt.getProjectTaskId());
		tsDetails.put(tpt, ptHrList);
	    }
	    return tsDetails;
	}
	catch (Exception e) {
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
     * Retrieves all hours related to _projectTaskId from the database.
     * 
     * @param _projectTaskId THe id of the project-task
     * @return An ArrayList of {@link ProjEvent) objects
     * @throws ActionHandlerException
     */
    private List refreshProjectTaskHours(int _projectTaskId) throws ActionHandlerException {
	List list = null;
	List hours = new ArrayList();
	ProjEvent pe = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    list = (List) tsApi.findEventByProjectTask(_projectTaskId);
	    for (int ndx = 0; ndx < list.size(); ndx++) {
		pe = (ProjEvent) list.get(ndx);
		hours.add(pe);
	    }
	    return hours;
	}
	catch (Exception e) {
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
     * Retrieves project details and packages the data into the request to be sent to the client.    If the project 
     * exist then the data is obtained from the database.    Otherwise, new data objects are instaintiated and sent to the clinet.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(ProjectConst.CLIENT_DATA_TIMESHEETS, this.timesheet);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_DATES, this.dates);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_STATUSES, this.pts);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENTS, this.client);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_EMPLOYEES, this.employee);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_TIME, this.tsProjTaskHrs);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_MODE, this.mode);
	return;
    }

    /**
     * Obtains client data fro the request.   
     * 
     * @return {@link CustomerDetails} object.
     * @throws ActionHandlerException when a database access error occurs.
     */
    private ProjClient getClient() throws ActionHandlerException {
	String temp = null;
	int clientId = 0;
	ProjClient pc;

	try {
	    if (this.timesheet.getClientId() > 0) {
		clientId = this.timesheet.getClientId();
	    }
	    else {
		temp = this.getPropertyValue("ClientId");
		clientId = Integer.parseInt(temp);
	    }
	}
	catch (NotFoundException e) {
	    this.msg = "Client's id is not available.";
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	catch (NumberFormatException e) {
	    this.msg = "Client's id is not parsable.   Check that the value is a numeric.";
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}

	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    pc = (ProjClient) api.findClient(clientId);
	    if (pc == null) {
		pc = SetupFactory.createClient();
	    }
	    return pc;
	}
	catch (Exception e) {
	    this.logger.log(Level.DEBUG, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Handles the request to submitt an employee's timesheet for manager approval.
     * 
     * @throws ActionHandlerException Problem occurred setting the status of the timesheet to 'submitted'.
     * @throws DatabaseException database access error
     */
    protected void submit() throws ActionHandlerException, DatabaseException {
	// Get timesheet data from request
	this.timesheet = TimesheetFactory.createTimesheet();
	TimesheetFactory.packageBean(this.request, this.timesheet);

	this.refreshClientData(this.timesheet.getTimesheetId());

	DatabaseTransApi tx = DatabaseTransFactory.create();
	// Set timesheet status to "Submitted".
	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	// Email Timesheet to manager
	TimesheetTransmissionApi tta = TimesheetFactory.createTransmissionApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    ptsApi.setTimesheetStatus(this.timesheet.getTimesheetId(), ProjectConst.TIMESHEET_STATUS_SUBMITTED);
	    try {
			tta.send(this.timesheet, this.employee, this.client, this.tsProjTaskHrs);
		} 
	    catch (TimesheetTransmissionException e) {
	    	this.msg = "SMTP ERROR: " + e.getMessage();
	    	logger.log(Level.ERROR, this.msg);
			e.printStackTrace();
		}
	    tx.commitUOW();
	}
	catch (ProjectException e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    ptsApi.close();
	    ptsApi = null;
	    tta = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Handles the request to approve an employee's timesheet.   This request is submitted by an employee of manager status.
     * 
     * @throws ActionHandlerException Problem occurred setting the status of the timesheet to 'approved'.
     * @throws DatabaseException database access error
     */
    protected void approve() throws ActionHandlerException, DatabaseException {
	this.receiveClientData();
	this.refreshClientData(this.timesheet.getTimesheetId());

	// Set timesheet status to "Approved".
	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    ptsApi.setTimesheetStatus(this.timesheet.getTimesheetId(), ProjectConst.TIMESHEET_STATUS_APPROVED);
	    tx.commitUOW();
	}
	catch (ProjectException e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    ptsApi.close();
	    ptsApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Handles a request to delcine an employee's timesheet..   This request is submitted by an employee of manager status.
     * 
     * @throws ActionHandlerException Problem occurred setting the status of the timesheet to 'declined'
     * @throws DatabaseException database access error
     */
    protected void decline() throws ActionHandlerException, DatabaseException {
	this.receiveClientData();
	this.refreshClientData(this.timesheet.getTimesheetId());

	// Set timesheet status to "Approved".
	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    ptsApi.setTimesheetStatus(this.timesheet.getTimesheetId(), ProjectConst.TIMESHEET_STATUS_DECLINED);
	    tx.commitUOW();
	}
	catch (ProjectException e) {
	    logger.log(Level.DEBUG, e.getMessage());
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    ptsApi.close();
	    ptsApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Action handler for returning the user back to the time sheet search page.
     *  
     * @throws ActionHandlerException.
     */
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	TimesheetSearchAction action = new TimesheetSearchAction();
	action.processRequest(this.request, this.response, TimesheetSearchAction.COMMAND_LIST);
	return;
    }

    /**
     * Invoices a single timesheet using the client data from the HTTP Request.
     * @throws ActionHandlerException
     * @throws DatabaseException
     */
    protected void doSingleTimesheetInvoice() throws ActionHandlerException, DatabaseException {
	this.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	InvoicingApi inv = InvoicingFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    inv.invoiceSingle(this.timesheet.getTimesheetId());
	    tx.commitUOW();
	    this.refreshClientData(this.timesheet.getTimesheetId());
	    this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, "Timesheet invoiced successfully");
	}
	catch (InvoicingException e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    inv = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * No Action
     */
    public void edit() throws ActionHandlerException {
	return;
    }

}
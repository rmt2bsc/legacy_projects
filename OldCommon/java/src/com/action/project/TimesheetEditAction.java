package com.action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.api.GLCustomerApi;
import com.api.ProjectSetupApi;
import com.api.ProjectTimesheetApi;
import com.api.EmployeeApi;
import com.api.ProjectTimesheetStatusApi;
import com.api.SalesOrderApi;
import com.api.db.DatabaseException;
import com.api.mail.EMailException;
import com.api.mail.EMailFactory;
import com.api.mail.EMailManagerApi;

import com.bean.ProjProjectTask;
import com.bean.ProjProject;
import com.bean.ProjTask;
import com.bean.ProjTimesheet;
//import com.bean.RMT2BaseBean;
import com.bean.OrmBean;
import com.bean.RMT2TagQueryBean;
import com.bean.VwCustomerName;
import com.bean.VwEmployeeExt;
import com.bean.ProjTimesheetStatus;
import com.bean.ProjTimesheetHist;
import com.bean.VwTimesheetList;
import com.bean.ProjEvent;
import com.bean.VwTimesheetProjectTask;
import com.bean.RMT2SessionBean;
import com.bean.Customer;
import com.bean.SalesOrder;
import com.bean.SalesOrderItems;
import com.bean.Xact;
import com.bean.mail.EMailBean;

import com.constants.ProjectConst;
import com.constants.XactConst;
import com.constants.RMT2ServletConst;

import com.factory.ProjectManagerFactory;
import com.factory.SalesFactory;
import com.factory.AcctManagerFactory;
import com.factory.XactFactory;

import com.util.EmployeeException;
import com.util.SystemException;
import com.util.NotFoundException;
import com.util.ProjectException;
import com.util.RMT2Utility;
import com.util.RMT2File;


/**
 * This class provides action handlers to respond to the client's commands from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages. 
 * Handles requests pertaining to adding, deleting, saving, submitting (finalize), approving, declining and invoicing timehseets.
 * 
 * @author Roy Terrell
 *
 */
public class TimesheetEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "Project.TimesheetEdit.add";
    private static final String COMMAND_DELETE = "Project.TimesheetEdit.delete";
    private static final String COMMAND_SAVE = "Project.TimesheetEdit.save";
    private static final String COMMAND_FINALIZE = "Project.TimesheetEdit.finalize";
    private static final String COMMAND_APPROVE = "Project.TimesheetEdit.approve";
    private static final String COMMAND_DECLINE = "Project.TimesheetEdit.decline";
    private static final String COMMAND_INVOICE = "Project.TimesheetEdit.invoice";
    private static final String COMMAND_BULKINVOICE = "Project.BulkTimesheetInvoice.bulkinvoicesubmit";
    private static final String COMMAND_BULKINVOICE2 = "Project.BulkTimesheetInvoice.bulkinvoicesubmit2";
    private static final String BULK_DATA = "BULKDATA";

    private ProjectSetupApi api;
    private ProjectTimesheetApi tsApi;
    private EmployeeApi empApi;
    private ProjectTimesheetStatusApi ptsApi;
    private Logger logger;

    private VwEmployeeExt employee;
    private ProjTimesheet timesheet;
	private VwCustomerName customer;
	private ProjTimesheetStatus pts;
	private ProjTimesheetHist ptsh;
	private Hashtable tsProjTaskHrs;
    private Date dates[];
    private int selectedItems[];
    private Object timesheets[];
    private double totalHours;
    private int invoiceMasterItemId;
    private double invoiceAmt;
    private String xactReason;
    private List timesheetId;
    	
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
    public TimesheetEditAction( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
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
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
	  try {
		  this.init(null, request);
          this.init();
          
          try {
              String temp = RMT2Utility.getPropertyValue("Timesheet", "invoice_item_id");
              this.invoiceMasterItemId = Integer.parseInt(temp);
              this.xactReason = RMT2Utility.getPropertyValue("Timesheet", "invoice_title");
          }
          catch (NumberFormatException e) {
              throw new Exception("Problem obtaining Item Master Id to be assoicated with Invoiced Timesheet");
          }
          catch (SystemException e) {
              throw new Exception(e.getMessage());
          }
          
		  if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_ADD)) {
			  this.addData();
		  }
          if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_SAVE)) {
              this.saveData();
              this.refreshClientData(this.timesheet.getId());
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
          if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_INVOICE)) {
              this.doSingleTimesheetInvoice();
          }
          if (command.equalsIgnoreCase(TimesheetEditAction.COMMAND_BULKINVOICE) || command.equalsIgnoreCase(TimesheetEditAction.COMMAND_BULKINVOICE2)) {
              this.doBulkTimesheetInvoice();
          }
          
	  }
	  catch (Exception e) {
          this.msg = e.getMessage();
          this.transObj.rollbackUOW();    
		  throw new ActionHandlerException(this.msg);
	  }
      finally {
          // Ensure that any updates made to the the query object is set on the session. 
          if (this.query != null) {
              this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
          }
      }
  }
  

  /**
   * Initializes the following API's: <code>ProjectSetupApi</code>, <code>ProjectTimesheetApi</code>, 
   * <code>EmployeeApi</code>, and <code>ProjectTimesheetStatusApi</code>.
   * 
   * @see com.bean.RMT2Base#init()
   */
  public void init() {
     super.init();
     this.api = ProjectManagerFactory.createProjectSetupApi(this.dbConn, this.request);  
     this.tsApi = ProjectManagerFactory.createProjectTimesheetApi(this.dbConn, this.request);
     this.empApi = ProjectManagerFactory.createEmployeeApi(this.dbConn, this.request);
     this.ptsApi = ProjectManagerFactory.createProjectTimesheetStatusApi(this.dbConn, this.request);
     this.invoiceAmt = 0;
     return;
  }
  
  
  
  /**
   * Adds a new project-task row to the client's presentation. 
   * 
   * @throws ActionHandlerException if a database access error occurs.
   */
  public void add()  throws ActionHandlerException {
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
      try {
          ppt = ProjectManagerFactory.createProjectTaskExt();
          pp = this.api.findProject(_projectId);
          pt = this.api.findTask(_taskId);
          ppt.setProjectTaskId(0);
          ppt.setProjectId(pp.getId());
          ppt.setTaskId(pt.getId());
          ppt.setProjectName(pp.getDescription());
          ppt.setTaskName(pt.getDescription());    
      }
      catch (ProjectException e) {
          this.msg = e.getMessage();
          logger.log(Level.ERROR, this.msg);
          throw new ActionHandlerException(this.msg);
      }
      
      // Setup ProjEvents objects for Project-Task's weekdays
      ArrayList list = this.addProjectTaskHours();    
      this.tsProjTaskHrs.put(ppt, list);
  }
  
  
  /**
   * Builds an Arraylsit of  {@link ProjEvent} objects for each date of the current week.   The ProjEvent properties, id, project task id, and hours, are 
   * initialized to zero and the event date is set to one of the dates of the current week.
   * 
   * @return An ArrayList of java.util.Date objects.
   * @throws ActionHandlerException
   */
  private ArrayList addProjectTaskHours() {
      ArrayList list = new ArrayList();
      ProjEvent pe = null;
    
      for (int ndx = 0; ndx < this.dates.length; ndx++) {
          pe = ProjectManagerFactory.createProjEvent();
          pe.setId(0);
          pe.setProjProjectTaskId(0);
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
      ArrayList events = null;

      // Determine if we need to set this timesheet in Draft status
      boolean timesheetEditable = (this.timesheet.getId() == 0);

      try {
          // Save base timesheet
          tsId = this.tsApi.maintainTimesheet(this.timesheet);
          
          // Save Project Task data
          Enumeration keys = this.tsProjTaskHrs.keys();
          while (keys.hasMoreElements()) {
              pptExt = (VwTimesheetProjectTask) keys.nextElement();
              ppt = ProjectManagerFactory.createProjectTask();
              ppt.setId(pptExt.getProjectTaskId());
              ppt.setProjProjectId(pptExt.getProjectId());
              ppt.setProjTaskId(pptExt.getTaskId());
              ppt.setProjTimesheetId(tsId);
              
              try {
            	  if (this.isSelected(pptExt.getProjectTaskId())) {
            		  // Delete project task and its events
                      this.tsApi.deleteProjectTask(pptExt.getProjectTaskId());
            	  }
            	  else {
            		  // Apply changes to project task and its events
            		  ptId = this.tsApi.maintainProjectTask(ppt);
                      events = (ArrayList) this.tsProjTaskHrs.get(pptExt);
                      this.tsApi.maintainEvent(ptId, events);  
            	  }
              }
              catch (ProjectException e) {
                  logger.log(Level.ERROR, e.getMessage());
                  this.transObj.rollbackUOW();
                  throw new ActionHandlerException(e.getMessage());
              }
          } // end while
          
          // Set timesheet status to Draft.
          if (timesheetEditable) {
              this.ptsApi.setTimesheetStatus(tsId, ProjectConst.TIMESHEET_STATUS_DRAFT);
          }
          this.transObj.commitUOW();
          this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, "Timesheet was saved successfully");
          return;          
      }
      catch (ProjectException e) {
          this.transObj.rollbackUOW();
          this.msg = e.getMessage();
          logger.log(Level.ERROR, this.msg);
          throw new ActionHandlerException(this.msg);
      }
  }
  
  /**
   * Deletes a client selected timesheet from the system which includes the removal of any related statuses.
   * 
   * @throws ActionHandlerException
   */
  public void delete() throws ActionHandlerException {
      try {
          // Get timesheet data from request
          this.timesheet = ProjectManagerFactory.createTimesheet();
          ProjectManagerFactory.packageBean(this.request, this.timesheet);
          try {
              this.ptsApi.deleteTimesheetStatus(this.timesheet.getId());
              this.tsApi.deleteTimesheet(this.timesheet.getId());    
              this.transObj.commitUOW();
          }
          catch (ProjectException e) {
              logger.log(Level.ERROR, e.getMessage());
              this.transObj.rollbackUOW();
          }
      }
      catch(SystemException e) {
          logger.log(Level.ERROR, e.getMessage());
          throw new ActionHandlerException(e.getMessage());
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
		  this.timesheet = ProjectManagerFactory.createTimesheet();
		  ProjectManagerFactory.packageBean(this.request, this.timesheet);
		  
		  // Get selected items
		  this.selectedItems = this.getSelectedItems();
		  
		  // Get common timesheet data from the database using an existing timesheet.
		  this.receiveCommonDbData();
          
          // Get all hours for each project-task occurrence.
          this.tsProjTaskHrs = this.getClientTimesheetDetails();
	  }
	  catch(Exception e) {
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
   * <li>A client object of type {@link VwCustomerName}</li>
   * <li>A timesheet status object of type {@link ProjTimesheetStatus}</li>
   * </ul>
   * 
   * @throws ActionHandlerException
   */
  private void receiveCommonDbData() throws ActionHandlerException {
	  int tsStatusId = 0;
	  
	  try {
		  // Get all dates of the week pertaining to the timesheet's ending period
          this.dates = this.tsApi.getTimePeriodWeekDates(this.timesheet.getEndPeriod());
		  
		  // Get employee
		  this.employee = this.empApi.findEmployeeExt(this.timesheet.getProjEmployeeId());
		  
		  // Get Client data
          this.customer = this.getClient();  
          
          // Get Timesheet's current status.
          this.ptsh = this.ptsApi.findTimesheetCurrentStatus(this.timesheet.getId());
          tsStatusId = (this.ptsh == null ? ProjectConst.TIMESHEET_STATUS_DRAFT : this.ptsh.getProjTimesheetStatusId());
          this.pts = this.ptsApi.findTimesheetStatus(tsStatusId);
	  }
	  catch(Exception e) {
		  logger.log(Level.ERROR, e.getMessage());
		  throw new ActionHandlerException(e.getMessage());
	  }
  }
  
  
  
  /**
   * Obtains the detail data for a timesheet from the client's request.   The details consist of 
   * data pertaining to Project, Task, and the hours for each project-task.
   *  
   * @return Hashtable 
   * @throws ActionHandlerException
   */
  private Hashtable getClientTimesheetDetails() throws ActionHandlerException {
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
					  ppt = ProjectManagerFactory.createProjectTaskExt();
					  pp = this.api.findProject(projectId);
					  pt = this.api.findTask(taskId);
					  ppt.setProjectTaskId(projectTaskId);
					  ppt.setProjectId(pp.getId());
					  ppt.setTaskId(pt.getId());
					  ppt.setProjectName(pp.getDescription());
					  ppt.setTaskName(pt.getDescription());
				  }
				  else {
					  // Data exist for this project task id
					  this.api.setBaseClass("com.bean.VwTimesheetProjectTask");
			          this.api.setBaseView("VwTimesheetProjectTaskView");
			          criteria = " project_task_id = " + projectTaskId;
					  list = this.api.findData(criteria, null);
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
			  hours = this.getClientProjectTaskHours(row);
			  
			  // Create hour entry
			  tsDetails.put(ppt, hours);
			  
			  // Process next
			  row++;
		  }
		  catch (NotFoundException e) {
			  rowFound = false;
		  }  
	  }	  while (rowFound) ;
	  
	  return tsDetails;
  }

  
  /**
   * Obtains the hours for a given project-task of a timesheet found in the client's request..
   *   
   * @param _row The row id used to identify the project- task hours.
   * @return An ArrayList of  {@link ProjEvent} objects
   * @throws ActionHandlerException
   */
  private ArrayList getClientProjectTaskHours(int _row) throws ActionHandlerException {
		ArrayList list = new ArrayList();
		ProjEvent pe = null;
		String propEventHrs = "EventHours";
		String propEventId = "EventId";
		String temp = null;
		String dayProcessing = null;
		double eventHrs = 0;
		int eventId = 0;
	
		for (int ndx = 0; ndx < RMT2Utility.DAYS_OF_WEEK.length; ndx++) {
			  try {
				  dayProcessing = RMT2Utility.DAYS_OF_WEEK[ndx];
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
					  pe = ProjectManagerFactory.createProjEvent();
                      pe.setEventDate(this.dates[ndx]);
				  }
				  else {
					  pe = this.tsApi.findEvent(eventId);
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


  /**
   * Builds a list of project task id's which are selected by the client.
   * 
   * @return A list of project task id's as an array of int.
   */
  private int [] getSelectedItems() {
	  String selCbx = "RowId";
	  String propProjTaskId ="ProjectTaskId";
	  String temp = null;
	  int row = 0;
	  
	  String selValues[] = this.request.getParameterValues(selCbx);
	  if (selValues == null) {
		  return null;
	  }
	  
	  int items[] = new int[selValues.length];
	  for (int ndx = 0; ndx < selValues.length; ndx++) {
		  row =  Integer.parseInt(selValues[ndx]);
          temp = this.request.getParameter(propProjTaskId + row);
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
	  try {
		  // Get timesheet data from database
		  this.timesheet = this.tsApi.findTimesheet(_timesheetId);
		  
		  // Get common timesheet data from the database using an existing timesheet.
		  this.receiveCommonDbData();
          
          // Get all hours for each project-task occurrence.
          this.tsProjTaskHrs = this.refreshTimesheetDetails(_timesheetId);
	  }
	  catch(Exception e) {
		  logger.log(Level.ERROR, e.getMessage());
	  }
      finally {
          this.sendClientData();
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
        String criteria = null;
        String orderBy = null;
        
        try {
            this.tsApi.setBaseClass("com.bean.VwTimesheetProjectTask");
            this.tsApi.setBaseView("VwTimesheetProjectTaskView");
            criteria = " timesheet_id = " + _timesheetId;
            orderBy = " project_name, task_name ";
            ptList = this.tsApi.findData(criteria, orderBy);
            for (int ndx = 0; ndx < ptList.size(); ndx++) {
                tpt = (VwTimesheetProjectTask) ptList.get(ndx);
                ptHrList = this.refreshProjectTaskHours(tpt.getProjectTaskId());
                tsDetails.put(tpt, ptHrList);
            }    
        }
        catch (SystemException e) {
            this.msg = "Error: " + e.getMessage(); 
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
		return tsDetails;
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
      String criteria = null;
      String orderBy = null;
      
      try {
          this.tsApi.setBaseClass("com.bean.ProjEvent");
          this.tsApi.setBaseView("ProjEventView");
          criteria = " proj_project_task_id = " + _projectTaskId;
          orderBy = " id ";
          list = this.tsApi.findData(criteria, orderBy);
          for (int ndx = 0; ndx < list.size(); ndx++) {
              pe = (ProjEvent) list.get(ndx);
              hours.add(pe);
          }    
      }
      catch (SystemException e) {
          this.msg = "Error: " + e.getMessage(); 
          logger.log(Level.ERROR, this.msg);
          throw new ActionHandlerException(this.msg);
      }
	  return hours;
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
	  this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENTS, this.customer);
	  this.request.setAttribute(ProjectConst.CLIENT_DATA_EMPLOYEES, this.employee);
	  this.request.setAttribute(ProjectConst.CLIENT_DATA_TIME, this.tsProjTaskHrs);
	  return;  
  }
  
  /**
   * Obtains client data fro the request.   
   * 
   * @return {@link VwCustomerName} object.
   * @throws ActionHandlerException when a database access error occurs.
   */
  private VwCustomerName getClient() throws ActionHandlerException {
      List list;
      String criteria = null;
      String temp = null;
      int clientId = 0;
      
      
      try {
          if (this.timesheet.getProjClientId() > 0) {
              clientId = this.timesheet.getProjClientId();
          }
          else {
              temp = this.getPropertyValue("ProjClientId");
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
      
      try {
          this.api.setBaseClass("com.bean.VwCustomerName");
          this.api.setBaseView("VwCustomerNameView");
          criteria = " customer_id = " + clientId;
          list = this.api.findData(criteria, null);
          if (list.size() > 0) {
        	  return (VwCustomerName) list.get(0);  
          }
          else {
        	  return  new VwCustomerName();
          }
      }
      catch (SystemException e) {
    	  logger.log(Level.DEBUG, e.getMessage());
          throw new ActionHandlerException(e.getMessage());
      }
  }
  
  /**
   * Handles the request to submitt an employee's timesheet for manager approval.
   * 
   * @throws ActionHandlerException Problem occurred setting the status of the timesheet to 'submitted'.
   * @throws DatabaseException database access error
   */
  protected void submit() throws ActionHandlerException, DatabaseException {
      this.receiveClientData();
      this.refreshClientData(this.timesheet.getId());
      
      // Set timesheet status to "Submitted".
      try {
          this.ptsApi.setTimesheetStatus(this.timesheet.getId(), ProjectConst.TIMESHEET_STATUS_SUBMITTED);
          this.transObj.commitUOW();
      }
      catch (ProjectException e) {
          logger.log(Level.DEBUG, e.getMessage());
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e.getMessage());
      }
       
      // Email Timesheet to manager
      try {
          this.emailTimesheet();    
      }
      catch (Exception e) {
          logger.log(Level.ERROR, "Problem notifying manager of employee\'s timesheet submital:  " + e.getMessage());
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
      this.refreshClientData(this.timesheet.getId());
      
      // Set timesheet status to "Approved".
      try {
          this.ptsApi.setTimesheetStatus(this.timesheet.getId(), ProjectConst.TIMESHEET_STATUS_APPROVED);
          this.transObj.commitUOW();
      }
      catch (ProjectException e) {
          logger.log(Level.DEBUG, e.getMessage());
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e.getMessage());
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
      this.refreshClientData(this.timesheet.getId());
      
      // Set timesheet status to "Approved".
      try {
          this.ptsApi.setTimesheetStatus(this.timesheet.getId(), ProjectConst.TIMESHEET_STATUS_DECLINED);
          this.transObj.commitUOW();
      }
      catch (ProjectException e) {
          logger.log(Level.DEBUG, e.getMessage());
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e.getMessage());
      }
  }

  
  /**
   * Handles the request of invoicing a single employee timesheet for a client..   This request is submitted by an employee of manager status.
   * 
   * @param timesheet The timesheet object to be processed.
   * @throws ActionHandlerException
   * @throws DatabaseException
   */
  private void invoiceTimesheet(ProjTimesheet timesheet) throws ActionHandlerException, DatabaseException {
      if (timesheet == null) {
          return;
      }
      this.timesheets = new Object[1];
      this.timesheets[0] = timesheet;
      ArrayList items;
      try {
          items = this.buildSalesOrderItem(this.timesheets);
          Xact xact = this.createInvoicedSalesOrder(this.customer.getCustomerId(), items);
          this.doPostTimesheetInvoice(this.timesheets, xact);
          this.transObj.commitUOW();
          this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, "Timesheet invoiced successfully");
      }
      catch (Exception e) {
          this.transObj.rollbackUOW();
          throw new ActionHandlerException(e.getMessage());
      }
  }
  
  /**
   * Handles the request of invoicing one or more approved timesheets pertaining to a client.  The invoices are sent to the sales order subsystems for processing.
   * This request is submitted by an employee of manager status.
   * 
   * @return int as the new sales order id.
   * @throws ActionHandlerException client validatio errors, problem gathering timesheets, or sales order processing errors.
   * @throws DatabaseException database access errors
   */
  private int invoiceClient(int _clientId) throws ActionHandlerException, DatabaseException {
      try { 
          // Get client approved timesheets
          VwTimesheetList tsView = new VwTimesheetList();
    	  tsView.addCriteria("ProjClientId", _clientId);
    	  tsView.addCriteria("ProjTimesheetStatusId", ProjectConst.TIMESHEET_STATUS_APPROVED);
    	  Object tsViews[] = this.tsApi.findData(tsView);
          if (tsViews == null) {
              return 0;
          }
          // Convert timesheet views to base timesheet objects.
          this.timesheets = new ProjTimesheet[tsViews.length];
          for (int ndx = 0; ndx < tsViews.length; ndx++) {
        	  ProjTimesheet baseTs = ProjectManagerFactory.createTimesheet();
              tsView = (VwTimesheetList) tsViews[ndx];
        	  baseTs.setId(tsView.getTimesheetId());
        	  baseTs.setDisplayValue(tsView.getDisplayValue());
        	  baseTs.setProjClientId(tsView.getProjClientId());
        	  baseTs.setProjEmployeeId(tsView.getProjEmployeeId());
        	  baseTs.setBeginPeriod(tsView.getBeginPeriod());
        	  baseTs.setEndPeriod(tsView.getEndPeriod());
        	  this.timesheets[ndx] = baseTs;
              
              // Collect timesheet id's for confirmation list
              this.timesheetId.add(String.valueOf(tsView.getTimesheetId()));
          }
      }
      catch (Exception e) {
    	  throw new ActionHandlerException("Error validating Client or gathering the client's timesheets: " + e.getMessage());
      }

      // Begin to create sales order and invoice.
      int xactId;
      ArrayList items = this.buildSalesOrderItem(this.timesheets);   
      try {
    	  Xact xact = this.createInvoicedSalesOrder(_clientId, items);
    	  this.doPostTimesheetInvoice(this.timesheets, xact);
          this.transObj.commitUOW();
          return xact.getId();
      }
      catch (ActionHandlerException e) {
          this.transObj.rollbackUOW();
          logger.log(Level.ERROR, e.getErrorStack());
          throw new ActionHandlerException(e.getMessage());
      }
  }



  /**
   * Performs post timesheet invoicing updates.  Updates status and reference number belonging to  each _timesheets element.
   * 
   * @param _timesheets An array of {@link ProjTimesheet} objects
   * @param _xact The transaction to associate the timesheet with.
   * @throws ActionHandlerException
   */
  private void doPostTimesheetInvoice(Object _timesheets[], Xact _xact) throws ActionHandlerException {
	  ProjTimesheet ts;
	  try {
	      for (int ndx = 0; ndx < _timesheets.length; ndx++) {
	          ts = (ProjTimesheet) _timesheets[ndx];
              // Change the status of timesheet to invoice.
              this.ptsApi.setTimesheetStatus(ts.getId(), ProjectConst.TIMESHEET_STATUS_INVOICED);
              // Setup reference number.
	          ts.setInvoiceRefNo(String.valueOf(_xact.getEntityRefNo()));
	          this.tsApi.maintainTimesheet(ts);
	      } 
	  }
	  catch (ProjectException e) {
		  logger.log(Level.ERROR, e.getMessage());
		  throw new ActionHandlerException(e);
	  }
  }
  
  /**
   * Creates a sales order for one or more timesheets pertaining to the client's project(s) and invoices each sales order.
   * 
   * @param __custId The id of the client that is to receive the invoice.
   * @param _items One or more sales order items derived from the timesheets of the employees assigned to the client's project(s).
   * @return The new transaction id.
   * @throws ActionHandlerException errors pertaining to Sales Order processing, Timesheet project processing, or general Systems errors.
   * @throws DatabaseException database access errors.
   */
  private Xact createInvoicedSalesOrder(int _custId, ArrayList _items)  throws ActionHandlerException, DatabaseException {
      SalesOrderApi soApi = null;
      GLCustomerApi custApi = null;
      Customer cust = null;
      
      int soId = 0;

      // Create base Customer object
      try {
          custApi = AcctManagerFactory.createCustomer(this.dbConn);
          cust = custApi.findCustomerById(_custId);
      }
      catch (Exception e) {
          throw new ActionHandlerException("Error validating Client: " + e.getMessage());
      }
      try {
          // Setup sales order object
          SalesOrder so = SalesFactory.createSalesOrder();
          so.setCustomerId(cust.getId());
          so.setOrderTotal(this.invoiceAmt);
          
          soApi = SalesFactory.createApi(this.dbConn, this.request);
          soId = soApi.maintainSalesOrder(so, cust, _items);
          so.setId(soId);
          
          // Obtain reference number
          long refNo = new Date().getTime();
          
          // Create Transaction
          Xact xact = XactFactory.createXact();
         
          xact.setXactAmount(this.invoiceAmt);
          xact.setXactTypeId(XactConst.XACT_TYPE_SALESONACCTOUNT);
          String xactReason = this.xactReason;
          xact.setReason(xactReason);
          xact.setEntityRefNo(String.valueOf(refNo));
          soApi.invoiceSalesOrder(so, xact);
          return xact;
      }
      catch (Exception e) {
          throw new ActionHandlerException(e.getMessage());
      }
  }
  
  

  /**
   * Converts one or more timesheets into sales order items.
   * 
   * @param timesheets The timesheet(s) that are to be converted.
   * @return ArrayList of timesheet objects
   * @throws ActionHandlerException
   */
  private ArrayList buildSalesOrderItem(Object timesheets[]) throws ActionHandlerException {
      ProjTimesheet ts;
      ArrayList items = new ArrayList();
      for (int ndx = 0; ndx < timesheets.length; ndx++) {
          ts = (ProjTimesheet) timesheets[ndx];
          SalesOrderItems soi = this.buildSalesOrderItem(ts);
          if (soi == null) {
              continue;
          }
          invoiceAmt += soi.getInitUnitCost() * soi.getOrderQty();
          items.add(soi);
      }
      return items;
  }
  
  /**
   * Creates a sales order item for a given timesheet.    The itme master description will be 
   * overriden by the description of the sales order item and will be formatted as such:
   * <blockquote>
   * <b> [Employee Lastname], [Employee Firstname] for ## hours</b>
   * </blockquote>
   * 
   * @param _ts The id of the timesheet.
   * @return {@link SalesOrderItems}
   * @throws ActionHandlerException
   */
  private SalesOrderItems buildSalesOrderItem(ProjTimesheet _ts) throws ActionHandlerException {
	  try {
          // Get employee
          this.employee = this.empApi.findEmployeeExt(_ts.getProjEmployeeId());
          // Calculate timesheet invoice amount
          this.invoiceAmt = this.tsApi.calculateInvoice(_ts.getId(), this.employee.getBillRate(), this.employee.getOtBillRate());
          double hours = this.tsApi.calculateTimehseetBillableHours(_ts.getId());
          
          ProjTimesheetHist status = this.ptsApi.findTimesheetCurrentStatus(_ts.getId());
          if (status == null) {
              return null;
          }
          if (status.getProjTimesheetStatusId() != ProjectConst.TIMESHEET_STATUS_APPROVED) {
              return null;
          }
          
          // Create Sales Order Item
          SalesOrderItems soi = SalesFactory.createSalesOrderItem();
          soi.setItemMasterId(this.invoiceMasterItemId);
          soi.setItemNameOverride(this.employee.getShortname() + " for " + hours + " hours");
          soi.setInitUnitCost(invoiceAmt);
          soi.setInitMarkup(1);
          soi.setOrderQty(1);
          return soi;
	  }
	  catch (Exception e) {
		  throw new ActionHandlerException(e.getMessage());
	  }
  }
  
  
  private void emailTimesheet() throws EMailException, SystemException, ActionHandlerException, DatabaseException {
      final String SUBJECT_PREFIX = "Timesheet Submission for "; 
      EMailManagerApi emailApi = null;
      EMailBean email  = null;
      String periodEnd = null;
      String html = null;
      VwEmployeeExt manager = null;

      // Get email address of the employee's manager.
      try {
          manager = this.empApi.findEmployeeExt(this.employee.getManagerId());
      }
      catch (EmployeeException e) {
          manager = new VwEmployeeExt();
      }
      try {
          periodEnd = RMT2Utility.formatDate(this.timesheet.getEndPeriod(), "MM/dd/yyyy");
      }
      catch (SystemException e) {
          periodEnd = "N/A";
      }
      email = EMailFactory.create();
      email.setToAddress(manager.getEmail());
      email.setFromAddress(this.employee.getEmail());
      email.setSubject(SUBJECT_PREFIX + " " + employee.getShortname() + " for period ending  " + periodEnd);    
      
      html = this.setupTimesheetEmail();
      
      email.setBody(html, EMailBean.HTML_CONTENT);
      
      emailApi = EMailFactory.createApi(email);
      emailApi.sendMail();
      emailApi.closeTransport();
      return;      
  }
  
  
  private String setupTimesheetEmail() throws ActionHandlerException {
      RMT2SessionBean sessionBean =  RMT2SessionBean.getInstance(this.request, this.session);
      String root = sessionBean.getScheme() + "://" + sessionBean.getServerName() + ":" + sessionBean.getServerPort();
      String uri = root + sessionBean.getContextPath() +  "/reqeustProcessor/Project.TimesheetProcessing";
      String uriParms = "?timesheetId=" + this.timesheet.getId() + "&clientAction=";
      String htmlContent = null;
      String formattedDate = null;
      
      try {
          String filePath = this.session.getServletContext().getRealPath("/forms/projects/TimesheetEmail.jsp");
          htmlContent = RMT2File.getTextFileContents(filePath);
          formattedDate = RMT2Utility.formatDate(this.timesheet.getEndPeriod(), "MM/dd/yyyy");
      }
      catch (SystemException e) {
          throw new ActionHandlerException(e);
      }
      
      String deltaContent = null;
      deltaContent = RMT2Utility.replaceAll(htmlContent, root, "$root$");
      deltaContent = RMT2Utility.replace(deltaContent, this.employee.getShortname(), "$employeename$");
      deltaContent = RMT2Utility.replace(deltaContent, this.employee.getEmployeeTitle(), "$employeetitle$");
      deltaContent = RMT2Utility.replace(deltaContent, this.customer.getName(), "$clientname$");
      deltaContent = RMT2Utility.replace(deltaContent, this.timesheet.getDisplayValue(), "$timesheetid$");
      deltaContent = RMT2Utility.replace(deltaContent, formattedDate, "$periodending$");
      
      // Get project-task hours
      String details = this.setupTimesheetEmailHours();
      deltaContent = RMT2Utility.replace(deltaContent, details, "$timesheetdetails$");
      deltaContent = RMT2Utility.replace(deltaContent, String.valueOf(this.totalHours), "$totalhours$");
      deltaContent = RMT2Utility.replace(deltaContent, String.valueOf(this.timesheet.getId()), "$timesheetid$");
      
      deltaContent = RMT2Utility.replace(deltaContent, uri + uriParms + "approve", "$approveURI$");
      deltaContent = RMT2Utility.replace(deltaContent, uri + uriParms + "decline", "$declineURI$");
      
      return deltaContent;
  }
      

  private String setupTimesheetEmailHours()  throws ActionHandlerException {
      String origHtmlContents = null;
      String htmlContents = null;
      String deltaContents = "";
      VwTimesheetProjectTask vtpt = null;
      ProjEvent pe = null;
      
      try {
          String filePath = this.session.getServletContext().getRealPath("/forms/projects/TimesheetEmailDetails.jsp");
          origHtmlContents = RMT2File.getTextFileContents(filePath);
      }
      catch (SystemException e) {
          throw new ActionHandlerException(e);
      }
      
      Enumeration keys = this.tsProjTaskHrs.keys();
      while (keys.hasMoreElements()) {
          htmlContents = origHtmlContents;
          vtpt = (VwTimesheetProjectTask) keys.nextElement();
          htmlContents = RMT2Utility.replace(htmlContents, vtpt.getProjectName(), "$projectname$");
          htmlContents = RMT2Utility.replace(htmlContents, vtpt.getTaskName(), "$taskname$");
          
          ArrayList list = (ArrayList) this.tsProjTaskHrs.get(vtpt);
          for (int ndx = 0; ndx < list.size(); ndx++) {
              pe = (ProjEvent) list.get(ndx);
              totalHours += pe.getHours();
              htmlContents = RMT2Utility.replace(htmlContents, String.valueOf(pe.getHours()), "$" + ndx + "hrs$");
          }
          
          deltaContents += htmlContents;
      }
      return deltaContents;
      
  }
  
  
  /**
   * Invoices a single timesheet using the client data from the HTTP Request.
   * @throws ActionHandlerException
   * @throws DatabaseException
   */
  protected void doSingleTimesheetInvoice() throws ActionHandlerException, DatabaseException {
      this.receiveClientData();
      this.invoiceTimesheet(this.timesheet);
      this.refreshClientData(this.timesheet.getId());
  }
  
  
  
  /**
   * Invoices the approved timesheets of all clients in the system using the client data from the HTTP Request.
   * 
   * @return The number of timesheets sucessfully processed.
   * @throws ActionHandlerException
   */
  protected int doBulkTimesheetInvoice() throws ActionHandlerException, DatabaseException {
      final int STATE_POLLING = -100;
      final int STATE_NO_DATA = 0;
      boolean firstTime = false;
      Object clients[];
      int count = 0;
      
      // Poll Bulk Timesheet Invoicing uisng "Wait Pleases..." JSP if this is the first time that the Project search screen is displayed.
      clients = (Object []) this.query.getKeyValues(TimesheetEditAction.BULK_DATA);
      if (clients == null) {
          firstTime = true;
      }
      else if ( !(clients instanceof Object[])) {
          firstTime = true;
      }
      else {
          firstTime = false;
      }
      this.setFirstTime(firstTime);
      
      if (firstTime) {
          clients = this.request.getParameterValues("selClients");
          try {
              this.query.addKeyValues(TimesheetEditAction.BULK_DATA, clients);    
          }
          catch (SystemException e) {
              // Do Nothing
          }
          return STATE_POLLING;
      }
      
      
      // At this point the JSP polling is in porgress and we can begin invoicing timesheets.
      int clientId = 0;
      if (clients == null) {
          return STATE_NO_DATA;
      }
      this.timesheetId = new ArrayList();
      for (int ndx = 0; ndx < clients.length; ndx++) {
          try {
              clientId = Integer.parseInt(clients[ndx].toString());
          }
          catch (NumberFormatException e) {
              logger.log(Level.ERROR, "Failed to identify client for bul invoice process.  Client Id: " + (clients[ndx] == null ? "Unknow Client id" : clients[ndx]) );
              continue;
          }
          
          try {
              this.invoiceClient(clientId);    
              count++;
          }
          catch (Exception e) {
              throw new ActionHandlerException(e.getMessage());
          }
      }
      
      // Fetch timesheet objects from the DB as confirmation
      Object tsViews[] = null;
      try {
          // Get the id's of all timesheets processed
          String tsId[] = (String []) this.timesheetId.toArray( new String[this.timesheetId.size()] );
          VwTimesheetList tsView = new VwTimesheetList();
          tsView.addCriteria("ProjTimesheetStatusId", ProjectConst.TIMESHEET_STATUS_INVOICED);
          tsView.addInClause("TimesheetId", tsId);
          tsView.addOrderBy("ClientName", OrmBean.ORDERBY_ASCENDING);
          tsView.addOrderBy("Shortname", OrmBean.ORDERBY_ASCENDING);
          tsView.addOrderBy("TimesheetId", OrmBean.ORDERBY_ASCENDING);  
          tsViews = this.tsApi.findData(tsView);
      }
      catch (DatabaseException e) {
          logger.log(Level.ERROR, e.getMessage());
          logger.log(Level.ERROR, e.getErrorStack());
          throw new ActionHandlerException(e);
      }
      catch (SystemException e) {
          logger.log(Level.ERROR, e.getMessage());
          logger.log(Level.ERROR, e.getErrorStack());
          throw new ActionHandlerException(e);
      }
      finally {
          this.request.setAttribute(ProjectConst.CLIENT_DATA_TIMESHEETS, tsViews);
      }
      
      
      this.query.removeKeyValues(TimesheetEditAction.BULK_DATA);
      return count;
  }
  
  
  public void edit()  throws ActionHandlerException {
	  return;
  }
  
}
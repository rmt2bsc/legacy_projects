package com.action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.Hashtable;
import java.util.Date;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.ProjectSetupApi;
import com.api.ProjectTimesheetApi;
import com.api.EmployeeApi;
import com.api.ProjectTimesheetStatusApi;
import com.api.db.DatabaseException;

import com.bean.ProjTimesheet;
import com.bean.VwCustomerName;
import com.bean.VwEmployeeExt;
import com.bean.ProjTimesheetStatus;

import com.constants.ProjectConst;

import com.factory.ProjectManagerFactory;

import com.util.SystemException;
import com.util.RMT2Utility;




/**
 * This class provides action handlers to respond to the client's 
 * commands from the ProjectMaintList.jsp and ProjectMaintEdit.jsp 
 * pages. 
 * 
 * @author Roy Terrell
 *
 */
public class TimesheetNewAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "Project.TimesheetNewSetup.add";
    private ProjectSetupApi api;
    private ProjectTimesheetApi tsApi;
    private EmployeeApi empApi;
    private ProjectTimesheetStatusApi ptsApi;
    private Logger logger;
    private VwEmployeeExt employee;
    private int selectClientId;
    private Date selectPeriod;
    private Date dates[];
    	
	/**
	 * Default constructor.
	 *
	 */
    public TimesheetNewAction() {
    	logger = Logger.getLogger("TimesheetNewAction");
    }
    
 
    /**
     * Processes the Add command issued from the NewTimesheetSetup.jsp page.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
	  try {
		  this.init(null, request);
          this.api = ProjectManagerFactory.createProjectSetupApi(this.dbConn, this.request);  
          this.tsApi = ProjectManagerFactory.createProjectTimesheetApi(this.dbConn, this.request);
          this.empApi = ProjectManagerFactory.createEmployeeApi(this.dbConn, this.request);
          this.ptsApi = ProjectManagerFactory.createProjectTimesheetStatusApi(this.dbConn);
          
		  if (command.equalsIgnoreCase(TimesheetNewAction.COMMAND_ADD)) {
			  this.addData();
		  }
	  }
	  catch (Exception e) {
		  throw new ActionHandlerException(e);
	  }
  }
  
  /**
   * Obtains a new {@link ProjProject} object and prepares to send the objects as a response to the client 
   * via the Requst object.  The new ProjProject object is identified on the request object as, "project".
  
   * @throws ActionHandlerException if a database access error occurs.
   */
  public void add()  throws ActionHandlerException {
	  // Get data from client
	  this.receiveClientData();  
	  return;
  }
  
  
  /**
   * Receives input data from the client, and attempts to validate the data.
   * 
   * @throws ActionHandlerException if a validation error occurs.
   */
  protected void receiveClientData() throws ActionHandlerException {
	  String tempDate = null;
	  String tempClient = null;
      Date periodDate = null;

      tempClient = this.request.getParameter("ProjClientId");
      tempDate = this.request.getParameter("EndPeriod");
            
	  try {
          // Get employee
          this.employee = this.empApi.getLoggedInEmployee();
          
		  // Get current period dates of the week
          periodDate = RMT2Utility.stringToDate(tempDate, "MM/dd/yyyy");
		  this.dates = RMT2Utility.getWeekDates(periodDate);
	  }
	  catch(Exception e) {
		  logger.log(Level.ERROR, e.getMessage());
		  this.selectClientId = 0;
	  }
      
      this.validateData(tempClient, tempDate);
  }
  
  /**
   * Verifies that the input data, end period and client id, are valid.    The following validations must be met:
   * <p>
   * <p>
   * <ul>
   *    <li>The period end date and client are required.</li>
   *    <li>The period end date must be a valid date.</li>
   *    <li>The Client Id must be a valid number.</li>
   *    <li>The period end date must not exceed the current week.</li>
   *    <li>The combination of the period end date, client, and currently logged in employee  must not exist.</li>
   * </ul>
   * 
   * @param _clientId The id of the client
   * @param _endPeriod The target ending period.
   * @throws ActionHandlerException if any one of the validation errors are not satisfied, or database access error.
   */
  private void validateData(String _clientId, String _endPeriod) throws ActionHandlerException {
	  int clientId = 0;
	  String criteria = null;
      String custName = null;
	  Date endPeriod = null;
	  Date weekDates[];
      com.bean.VwCustomerName cust = null;
	  
	  // Verify that client Id and end period was input by client.
	  if (_endPeriod == null || _endPeriod.length() <= 0) {
		  throw new ActionHandlerException("Timesheet ending period is required");
	  }
	  if (_clientId == null) {
		  throw new ActionHandlerException("Timesheet Client Id is required");
	  }
	  
	  // Verify that client Id is a valid number
	  try {
		  clientId = Integer.parseInt(_clientId);  
	  }
	  catch (NumberFormatException e) {
		  throw new ActionHandlerException("Client Id value cannot be converted to a number.   Value is invalid");
	  }
      
      // Get client name
      try {
          this.tsApi.setBaseClass("com.bean.VwCustomerName");
          this.tsApi.setBaseView("VwCustomerNameView");
          criteria = " customer_id  = " + clientId;
          List list = this.tsApi.findData(criteria, null);
          if (list.size() > 0) {
              cust = (VwCustomerName) list.get(0);
              custName = cust.getName();
          }
          else {
              custName = "Customer not found";
          }
      }
      catch (Exception e) {
          custName = "Problem getting Customer";
      }
      
	  
	  // Verify that the end period is a valid date.
	  try {
		  endPeriod = RMT2Utility.stringToDate(_endPeriod, "MM/dd/yyyy");  
	  }
	  catch (SystemException e) {
		  throw new ActionHandlerException(e.getMessage());
	  }
	  
	  // Verify that the ending period input does not exceed current timesheet period.
	  try {
		  weekDates = RMT2Utility.getWeekDates(new Date());
		  if (endPeriod.getTime() > weekDates[6].getTime()) {
			  throw new ActionHandlerException("End Period cannot exceed the end date of current timesheet period");
		  }
	  }
	  catch (SystemException e) {
		  throw new ActionHandlerException(e.getMessage());
	  }
	 
      // Verify that timeshet does not exist for said client and ending period.
	  try {
		  this.tsApi.setBaseClass("com.bean.ProjTimesheet");
		  this.tsApi.setBaseView("ProjTimesheetView");
	      criteria = " proj_client_id  = " + clientId + " and end_period = \'" + _endPeriod + "\'  and proj_employee_id = " + this.employee.getEmployeeId();
	      List list = this.tsApi.findData(criteria, null);
	      if (list.size() > 0) {
	    	  this.msg = "Timesheet already exist for Client [" +custName + "]  Period Ending [" + RMT2Utility.formatDate(endPeriod, "MM/dd/yyyy") + "]";
	    	  logger.log(Level.ERROR, this.msg);
	    	  throw new ActionHandlerException(this.msg);
	      }
	  }
	  catch (SystemException e) {
		  logger.log(Level.ERROR, e.getMessage());
		  throw new ActionHandlerException(e.getMessage());
	  }
	  
	  this.selectClientId = clientId;
	  this.selectPeriod = endPeriod;
	  return;
  }
  
  /**
   * Retrieves project details and packages the data into the request to be sent to the client.    If the project 
   * exist then the data is obtained from the database.    Otherwise, new data objects are instaintiated and sent to the clinet.
   * 
   * @throws ActionHandlerException
   */
  protected void sendClientData() throws ActionHandlerException {
	  ProjTimesheet timesheet = null;
	  VwCustomerName customer = null;
	  ProjTimesheetStatus pts = null;
	    
	  try {
		  // Get not submitted timesheet status object
		  pts = this.ptsApi.findTimesheetStatus(ProjectConst.TIMESHEET_STATUS_DRAFT);
		  
		  // Get selected client
		  customer = this.getClient(this.selectClientId);
		  
		  //  Setup new timesheet object
		  timesheet = ProjectManagerFactory.createTimesheet();
		  timesheet.setBeginPeriod(dates[0]);
		  timesheet.setEndPeriod(dates[6]);
		  timesheet.setProjClientId(customer.getCustomerId());
		  timesheet.setProjEmployeeId(employee.getEmployeeId());
		  
		  this.request.setAttribute(ProjectConst.CLIENT_DATA_TIMESHEETS, timesheet);
		  this.request.setAttribute(ProjectConst.CLIENT_DATA_DATES, this.dates);
		  this.request.setAttribute(ProjectConst.CLIENT_DATA_STATUSES, pts);
		  this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENTS, customer);
		  this.request.setAttribute(ProjectConst.CLIENT_DATA_EMPLOYEES, this.employee);
		  this.request.setAttribute(ProjectConst.CLIENT_DATA_TIME, new Hashtable());
		  
		  return;  
	  }
      catch (Exception e) {
          logger.log(Level.DEBUG, e.getMessage());
          throw new ActionHandlerException(e.getMessage());
      }
  }
  
  /**
   * Obtains client data using _clientId.
   * 
   * @param _clientId The id of the client to retireve
   * @return {@link VwCustomerName} object.
   * @throws ActionHandlerException when a database access error occurs.
   */
  private VwCustomerName getClient(int _clientId) throws ActionHandlerException {
      List list;
      String criteria = null;
      
      try {
          this.api.setBaseClass("com.bean.VwCustomerName");
          this.api.setBaseView("VwCustomerNameView");
          criteria = " customer_id = " + _clientId;
          list = this.api.findData(criteria, null);
          if (list.size() > 0) {
        	  return (VwCustomerName) list.get(0);  
          }
          else {
        	  return new VwCustomerName();
          }
      }
      catch (SystemException e) {
    	  logger.log(Level.DEBUG, e.getMessage());
          throw new ActionHandlerException(e.getMessage());
      }
  }

  public void edit()  throws ActionHandlerException {
	  return;
  }
  public void save() throws ActionHandlerException, DatabaseException {
	  return;
  }
  public void delete() throws ActionHandlerException, DatabaseException {
      return;
  }
}
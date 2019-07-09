package com.project.timesheet;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.Hashtable;
import java.util.Date;
import java.util.List;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;

import com.project.timesheet.TimesheetApi;

import com.employee.EmployeeApi;
import com.employee.EmployeeFactory;

import com.project.timesheet.TimesheetStatusApi;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.ProjClient;
import com.bean.ProjTimesheet;
import com.bean.VwEmployeeExt;
import com.bean.ProjTimesheetStatus;

import com.bean.db.DatabaseConnectionBean;

import com.project.ProjectConst;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * This class provides action handlers to respond to the client's 
 * commands from the ProjectMaintList.jsp and ProjectMaintEdit.jsp 
 * pages. 
 * 
 * @author Roy Terrell
 *
 */
public class TimesheetNewAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "Timesheet.NewSetup.add";

    private Logger logger;

    private VwEmployeeExt employee;

    private Object client;

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

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#init(com.controller.Context, com.controller.Request)
     */
    @Override
    protected void init(Context context, Request request) throws SystemException {
	super.init(null, request);
    }

    /**
     * Processes the Add command issued from the NewTimesheetSetup.jsp page.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	try {
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
    public void add() throws ActionHandlerException {
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

	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi empApi = EmployeeFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get employee
	    this.employee = (VwEmployeeExt) empApi.getLoggedInEmployee();

	    // Get current period dates of the week
	    periodDate = RMT2Date.stringToDate(tempDate, "MM/dd/yyyy");
	    this.dates = RMT2Date.getWeekDates(periodDate);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    this.selectClientId = 0;
	}
	finally {
		empApi.close();
		empApi = null;
		tx.close();
		tx = null;
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
    private void validateData(String clientIdStr, String endPeriodStr) throws ActionHandlerException {
	int clientId = 0;
	String criteria = null;
	String custName = null;
	Date endPeriod = null;
	Date weekDates[];

	// Verify that client Id and end period was input by client.
	if (endPeriodStr == null || endPeriodStr.length() <= 0) {
	    throw new ActionHandlerException("Timesheet ending period is required");
	}
	if (clientIdStr == null) {
	    throw new ActionHandlerException("Timesheet Client Id is required");
	}

	// Verify that client Id is a valid number
	try {
	    clientId = Integer.parseInt(clientIdStr);
	}
	catch (NumberFormatException e) {
	    throw new ActionHandlerException("Client Id value cannot be converted to a number.   Value is invalid");
	}

	// Get client name
	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
		try {
		    this.client = api.findClient(clientId);
		    if (this.client == null) {
			custName = "Customer not found";
		    }
		    else {
			custName = ((ProjClient) this.client).getName();
		    }
		}
		catch (Exception e) {
		    custName = "Problem getting Customer";
		}

		// Verify that the end period is a valid date.
		try {
		    endPeriod = RMT2Date.stringToDate(endPeriodStr, "MM/dd/yyyy");
		}
		catch (SystemException e) {
		    throw new ActionHandlerException(e.getMessage());
		}

		// Verify that the ending period input does not exceed current timesheet period.
		try {
		    weekDates = RMT2Date.getWeekDates(new Date());
		    if (endPeriod.getTime() > weekDates[6].getTime()) {
			throw new ActionHandlerException("End Period cannot exceed the end date of current timesheet period");
		    }
		}
		catch (SystemException e) {
		    throw new ActionHandlerException(e.getMessage());
		}

		// Verify that timesheet does not exist for said client and ending period.
		try {
		    criteria = " client_id  = " + clientId + " and end_period = \'" + endPeriodStr + "\'  and emp_id = " + this.employee.getEmployeeId();
		    List ts = (List) tsApi.findTimesheetExt(criteria);
		    if (ts != null && ts.size() > 0) {
			this.msg = "Timesheet already exist for Client [" + custName + "]  Period Ending [" + RMT2Date.formatDate(endPeriod, "MM/dd/yyyy") + "]";
			logger.log(Level.ERROR, this.msg);
			throw new ActionHandlerException(this.msg);
		    }
		}
		catch (Exception e) {
		    logger.log(Level.ERROR, e.getMessage());
		    throw new ActionHandlerException(e.getMessage());
		}

		this.selectClientId = clientId;
		this.selectPeriod = endPeriod;
		return;	
	}
	catch (ActionHandlerException e) {
		tx.rollbackUOW();
		throw e;
	}
	finally {
		tsApi.close();
		tsApi = null;
		api.close();
		api = null;
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
	ProjTimesheet timesheet = null;
	//	CustomerDetails customer = null;
	ProjTimesheetStatus pts = null;

	DatabaseTransApi tx = DatabaseTransFactory.create();
	TimesheetStatusApi ptsApi = TimesheetFactory.createStatusApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get not submitted timesheet status object
	    pts = ptsApi.findTimesheetStatus(ProjectConst.TIMESHEET_STATUS_DRAFT);

	    // Get selected client
	    //	    customer = this.getClient(this.selectClientId);

	    //  Setup new timesheet object
	    timesheet = TimesheetFactory.createTimesheet();
	    timesheet.setBeginPeriod(dates[0]);
	    timesheet.setEndPeriod(dates[6]);
	    timesheet.setClientId(this.selectClientId);
	    //	    timesheet.setClientId(customer.getCustomerId());
	    timesheet.setEmpId(employee.getEmployeeId());

	    this.request.setAttribute(ProjectConst.CLIENT_DATA_TIMESHEETS, timesheet);
	    this.request.setAttribute(ProjectConst.CLIENT_DATA_DATES, this.dates);
	    this.request.setAttribute(ProjectConst.CLIENT_DATA_STATUSES, pts);
	    this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENTS, this.client);
	    this.request.setAttribute(ProjectConst.CLIENT_DATA_EMPLOYEES, this.employee);
	    this.request.setAttribute(ProjectConst.CLIENT_DATA_TIME, new Hashtable());
	    return;
	}
	catch (Exception e) {
	    logger.log(Level.DEBUG, e.getMessage());
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
     * Obtains client data using _clientId.
     * 
     * @param _clientId The id of the client to retireve
     * @return {@link CustomerDetails} object.
     * @throws ActionHandlerException when a database access error occurs.
     */
    //    private CustomerDetails getClient(int _clientId) throws ActionHandlerException {
    //	List list;
    //	String criteria = null;
    //
    //	try {
    //	    this.api.setBaseClass("com.bean.CustomerDetails");
    //	    this.api.setBaseView("VwCustomerNameView");
    //	    criteria = " customer_id = " + _clientId;
    //	    list = this.api.findData(criteria, null);
    //	    if (list.size() > 0) {
    //		return (CustomerDetails) list.get(0);
    //	    }
    //	    else {
    //		return new CustomerDetails();
    //	    }
    //	}
    //	catch (SystemException e) {
    //	    logger.log(Level.DEBUG, e.getMessage());
    //	    throw new ActionHandlerException(e.getMessage());
    //	}
    //    }
    public void edit() throws ActionHandlerException {
	return;
    }

    public void save() throws ActionHandlerException, DatabaseException {
	return;
    }

    public void delete() throws ActionHandlerException, DatabaseException {
	return;
    }
}
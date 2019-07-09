package com.project.timesheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;

import com.api.mail.EMailFactory;
import com.api.mail.EMailManagerApi;

import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.ProjClient;
import com.bean.ProjEvent;
import com.bean.ProjTimesheet;
import com.bean.VwEmployeeExt;
import com.bean.VwTimesheetProjectTask;

import com.bean.db.DatabaseConnectionBean;

import com.bean.mail.EMailBean;

import com.controller.Request;

import com.employee.EmployeeApi;
import com.employee.EmployeeException;
import com.employee.EmployeeFactory;

import com.project.ProjectException;

import com.util.RMT2Date;
import com.util.RMT2File;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * This is an implementation of the TimesheetTransmissionApi interface using 
 * the SMTP protocol approach to send timesheets to some designated recipient.
 * 
 * @author RTerrell
 *
 */
public class SMTPTransmissionImpl extends RdbmsDaoImpl implements TimesheetTransmissionApi {

    private Logger logger;

    private double totalHours;

    /**
     * Default constructor
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    private SMTPTransmissionImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Creates a SMTPTransmissionImpl object requiring a DatabaseConnectionBean 
     * and the user's request.
     * 
     * @param dbConn {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @param request {@link com.controller.Request Request}
     * @throws DatabaseException 
     * @throws SystemException
     */
    public SMTPTransmissionImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
    }

    /**
     * Performs custom initialization of SMTPTransmissionImpl.
     */
    public void init() {
	super.init();
	this.logger = Logger.getLogger("SMTPTransmissionImpl");
    }

    /**
     * Emails a copy of an employee's timesheet to the employee's manager using
     * <i>timesheet</i>, <i>employee</i>, <i>client</i>, and <i>hours</i>.
     * 
     * @param timesheet 
     *          An instance of {@link com.bean.ProjTimesheet ProjTimesheet}
     * @param employee 
     *          An instance of {@link com.bean.VwEmployeeExt VwEmployeeExt}
     * @param client 
     *          An instance of {@link com.bean.ProjClient ProjClient}
     * @param hours 
     *          A List of Map instances which the key/value pairs represent 
     *          {@link com.bean.VwTimesheetProjectTask VwTimesheetProjectTask} and 
     *          {@link com.bean.ProjEvent VwTimesheetProjectTask}, respectively.
     * @return 1 when email is successfully sent and 0 when there are no project-task 
     *         hours to process.         
     * @throws ProjectException
     *           Validation errors
     * @throws TimesheetTransmissionException
     *           Error occurs sending timesheet data to its designated recipient 
     *           via the SMTP protocol.
     */
    public int send(ProjTimesheet timesheet, VwEmployeeExt employee, ProjClient client, Map hours) throws ProjectException, TimesheetTransmissionException {
	VwEmployeeExt manager = null;
	EMailManagerApi emailApi = null;
	EMailBean email = null;
	String periodEnd = null;
	String html = null;
	int rc = 0;

	// Validations
	this.validate(timesheet, employee, client, hours);

	// Get employee's manager
	manager = this.getEmployeeManager(employee.getManagerId());
	if (manager == null) {
	    this.msg = "Employee, " + employee.getEmployeeId() + ", does have a manager to send timesheet over SMTP protocol";
	    this.logger.log(Level.WARN, this.msg);
	}

	if (hours.isEmpty()) {
	    this.msg = "There are no project-task hours to process for timesheet, " + timesheet.getTimesheetId()
		    + ", when attempting to send timesheet to manager over SMTP protocol";
	    this.logger.log(Level.WARN, this.msg);
	    return 0;
	}

	try {
	    periodEnd = RMT2Date.formatDate(timesheet.getEndPeriod(), "MM/dd/yyyy");
	}
	catch (SystemException e) {
	    periodEnd = "N/A";
	}

	try {
	    // Setup basic email components
	    email = EMailFactory.create();
	    email.setToAddress(manager.getEmail());
	    email.setFromAddress(employee.getEmail());
	    email.setSubject(TimesheetTransmissionApi.SUBJECT_PREFIX + " " + employee.getFirstname() + " " + employee.getLastname() + " for period ending  " + periodEnd);

	    // Gather timesheet details to build the body of the email
	    html = this.setupTimesheetEmail(timesheet, employee, client, hours);
	    email.setBody(html, EMailBean.HTML_CONTENT);
	    emailApi = EMailFactory.createApi(email);

	    // Send the timesheet over the SMTP protocol.
	    rc = emailApi.sendMail();
	    emailApi.closeTransport();
	    return rc;
	}
	catch (Exception e) {
	    throw new TimesheetTransmissionException(e);
	}
    }

    /**
     * Verifies that <i>timesheet</i>, <i>employee</i>, <i>client</i>, and <i>hours</i> 
     * are valid instances.
     * 
     * @param timesheet 
     *          An instance of {@link com.bean.ProjTimesheet ProjTimesheet}
     * @param employee 
     *          An instance of {@link com.bean.VwEmployeeExt VwEmployeeExt}
     * @param client 
     *          An instance of {@link com.bean.ProjClient ProjClient}
     * @param hours 
     *          A List of Map instances which the key/value pairs represent 
     *          {@link com.bean.VwTimesheetProjectTask VwTimesheetProjectTask} and 
     *          {@link com.bean.ProjEvent VwTimesheetProjectTask}, respectively.
     * @throws ProjectException
     *           When either <i>timesheet</i>, <i>employee</i>, <i>client</i>, 
     *           or <i>hours</i> are invalid.  If <i>hours</i> does not contain any 
     *           entries.
     */
    private void validate(ProjTimesheet timesheet, VwEmployeeExt employee, ProjClient client, Map hours) throws ProjectException {
	// Validate timesheet
	if (timesheet == null) {
	    this.msg = "Timesheet instance is invalid when attempting to send timesheet to manager over SMTP protocol";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}

	// Validate employee
	if (employee == null) {
	    this.msg = "Employee instance is invalid when attempting to send timesheet to manager over SMTP protocol";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}

	// validate employee's client
	if (client == null) {
	    this.msg = "Client instance is invalid when attempting to send timesheet to manager over SMTP protocol";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}

	if (hours == null) {
	    this.msg = "Timesheet hours instance is invalid when attempting to send timesheet to manager over SMTP protocol";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}
    }

    /**
     * Retreives the employee's manager data using the employee id.
     * 
     * @param employeeId The id of the employee.
     * @return {@link com.bean.VwEmployeeExt VwEmployeeExt}
     * @throws ProjectException General database errors.
     */
    private VwEmployeeExt getEmployeeManager(int employeeId) throws ProjectException {
	VwEmployeeExt manager = null;
	// Get employee's manager.
	try {
	    EmployeeApi empApi = EmployeeFactory.createApi(this.connector);
	    manager = (VwEmployeeExt) empApi.findEmployeeExt(employeeId);
	    return manager;
	}
	catch (EmployeeException e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Builds HTML containing content that represents the details of a given timesheet.  
     * Uses <i>timesheet</i>, <i>employee</i>, <i>client</i>, and <i>hours</i> to devise 
     * the timesheet header, weekly hours for each project-task, and the totals.  Uses 
     * a timesheet header template file to build the HTML.
     * 
     * @param timesheet 
     *          An instance of {@link com.bean.ProjTimesheet ProjTimesheet}
     * @param employee 
     *          An instance of {@link com.bean.VwEmployeeExt VwEmployeeExt}
     * @param client 
     *          An instance of {@link com.bean.ProjClient ProjClient}
     * @param hours 
     *          A List of Map instances which the key/value pairs represent 
     *          {@link com.bean.VwTimesheetProjectTask VwTimesheetProjectTask} and 
     *          {@link com.bean.ProjEvent VwTimesheetProjectTask}, respectively.
     * @return Timesheet in HTML format.
     * @throws ProjectException
     *           User's session bean is unobtainable.  Problem obtaining timesheet's 
     *           project-task entries.  
     */
    private String setupTimesheetEmail(ProjTimesheet timesheet, VwEmployeeExt employee, ProjClient client, Map hours) throws ProjectException {
	RMT2SessionBean sessionBean = null;
	try {
	    sessionBean = AuthenticationFactory.getSessionBeanInstance(this.request, this.request.getSession());
	}
	catch (AuthenticationException e1) {
	    this.msg = "SessionBean instance could not be obtained";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}
	String root = sessionBean.getScheme() + "://" + sessionBean.getServerName() + ":" + sessionBean.getServerPort();
	String uri = root + sessionBean.getContextPath() + "/unsecureRequestProcessor/Timesheet.Send";
	String uriParms = "?timesheetId=" + timesheet.getTimesheetId() + "&clientAction=";
	String htmlContent = null;
	String formattedDate = null;

	try {
	    String filePath = this.request.getSession().getContext().getRealPath("/forms/timesheet/TimesheetEmail.jsp");
	    htmlContent = RMT2File.getTextFileContents(filePath);
	    formattedDate = RMT2Date.formatDate(timesheet.getEndPeriod(), "MM/dd/yyyy");
	}
	catch (SystemException e) {
	    throw new ProjectException(e);
	}

	String deltaContent = null;
	deltaContent = RMT2String.replaceAll(htmlContent, root, "$root$");
	deltaContent = RMT2String.replace(deltaContent, employee.getFirstname() + " " + employee.getLastname(), "$employeename$");
	deltaContent = RMT2String.replace(deltaContent, employee.getEmployeeTitle(), "$employeetitle$");
	deltaContent = RMT2String.replace(deltaContent, client.getName(), "$clientname$");
	deltaContent = RMT2String.replace(deltaContent, timesheet.getDisplayValue(), "$timesheetid$");
	deltaContent = RMT2String.replace(deltaContent, formattedDate, "$periodending$");

	// Get project-task hours
	String details = this.setupTimesheetEmailHours(hours);

	deltaContent = RMT2String.replace(deltaContent, details, "$timesheetdetails$");
	deltaContent = RMT2String.replace(deltaContent, String.valueOf(this.totalHours), "$totalhours$");
	deltaContent = RMT2String.replace(deltaContent, String.valueOf(timesheet.getTimesheetId()), "$timesheetid$");

	deltaContent = RMT2String.replace(deltaContent, uri + uriParms + "approve", "$approveURI$");
	deltaContent = RMT2String.replace(deltaContent, uri + uriParms + "decline", "$declineURI$");

	return deltaContent;
    }

    /**
     * Builds HTML that is to present the hours of one or more project-task instances.
     * Uses a timesheet details template file to build the HTML into rows an columns 
     * for each project-task and its hours.  Each project-task will report each day's 
     * hours for a total of seven days.
     * 
     * @param hours 
     *          A Map of project-task hours which the key exist as an instance of 
     *          {@link com.bean.VwTimesheetProjectTask VwTimesheetProjectTask} and 
     *          the value exist as a List of {@link com.bean.ProjEvent VwTimesheetProjectTask}, 
     *          respectively.
     * @return HTML table rows and columns hours for each project-task.
     * @throws ProjectException
     *           Unable to get the contents of the HTML template file used to build 
     *           project-task details.
     */
    private String setupTimesheetEmailHours(Map hours) throws ProjectException {
	String origHtmlContents = null;
	String htmlContents = null;
	String deltaContents = "";
	VwTimesheetProjectTask vtpt = null;
	ProjEvent pe = null;

	try {
	    String filePath = this.request.getSession().getContext().getRealPath("/forms/timesheet/TimesheetEmailDetails.jsp");
	    origHtmlContents = RMT2File.getTextFileContents(filePath);
	}
	catch (SystemException e) {
	    throw new ProjectException(e);
	}

	Iterator keys = hours.keySet().iterator();
	while (keys.hasNext()) {
	    htmlContents = origHtmlContents;
	    vtpt = (VwTimesheetProjectTask) keys.next();
	    htmlContents = RMT2String.replace(htmlContents, vtpt.getProjectName(), "$projectname$");
	    htmlContents = RMT2String.replace(htmlContents, vtpt.getTaskName(), "$taskname$");

	    ArrayList list = (ArrayList) hours.get(vtpt);
	    for (int ndx = 0; ndx < list.size(); ndx++) {
		pe = (ProjEvent) list.get(ndx);
		totalHours += pe.getHours();
		htmlContents = RMT2String.replace(htmlContents, String.valueOf(pe.getHours()), "$" + ndx + "hrs$");
	    }

	    deltaContents += htmlContents;
	}
	return deltaContents;

    }

}

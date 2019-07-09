package com.reports;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.bean.ProjClient;
import com.bean.ProjEmployee;
import com.bean.ProjTimesheet;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Context;

import com.employee.EmployeeApi;
import com.employee.EmployeeException;
import com.employee.EmployeeFactory;

import com.action.ActionHandlerException;

import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.MessageException;

import com.api.messaging.email.EmailMessageBean;
import com.api.messaging.email.smtp.SmtpApi;
import com.api.messaging.email.smtp.SmtpFactory;

import com.project.ProjectException;

import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;

import com.project.timesheet.TimesheetApi;
import com.project.timesheet.TimesheetFactory;

import com.util.InvalidDataException;
import com.util.RMT2Date;
import com.util.RMT2String;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * Action handler for generating an employee's consolidated weekly hours timesheet report to the Adobe 
 * Acrobat reader application.
 *
 * @author rterrell
 *
 */
public class SummaryTimesheetEmailAction extends AbstractSummaryTimesheetReportAction {
    
      private static Logger logger = Logger.getLogger("SummaryTimesheetReportAction");


    /**
     * Default constructor.
     */
    public SummaryTimesheetEmailAction() {
	super();
    }

    /**
     * Constructor responsible for initializing this object's servlet context and Http Request objects
     *
     * @param context The servlet context passed by the servlet
     * @param request The Http Servle Request object passed by the servlet
     * @throws SystemException
     */
    public SummaryTimesheetEmailAction(Context context, Request request) throws SystemException {
	super(context, request);
    }

  

    /**
     * Sends an emaill with the Summary Timesheet Report attached to the email message to one or more business contact emails.  
     * <p>
     * The report layout, EmployeeTimesheetReport.xsl, is transformed into the XSL-FO file which in turn will serve as input 
     * to the XSL-FO processor to render the Employee Timesheet report as a PDF.  The PDF results are streamed directly 
     * to the client's browser instead of sending an actual data file to the browser. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	try {
	    this.msg = this.sendReportAsEmail();
	    
	    ProjTimesheet ts = new ProjTimesheet();
	    ts.setTimesheetId(this.timesheetId);
	    this.request.setAttribute("timesheet", ts);
//	    this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, xml);
	    this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    
    /**
     * Generates the Summary Timesheet Report and renders the report visually to the user via Adobe Acrobat reader application.  
     * <p>
     * The report layout, EmployeeTimesheetReport.xsl, is transformed into the XSL-FO file which in turn will serve as input 
     * to the XSL-FO processor to render the Employee Timesheet report as a PDF.  The PDF results are streamed directly 
     * to the client's browser instead of sending an actual data file to the browser. 
     * 
     * @return Confimation message on successful transmission or null if SMTP api is not properly initialized.
     * @throws InvalidDataException
     * @throws SystemException
     */
    private String sendReportAsEmail() throws InvalidDataException, SystemException  {
	String opMsg = null;
	ProjClient client = null;
	ProjEmployee employee = null;
	ProjTimesheet ts = null;
	
	// Fetch Project Client record and validate if associated with one or more email addresses
	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	EmployeeApi empApi = EmployeeFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	TimesheetApi tsApi = TimesheetFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	
	try {
	    client = (ProjClient) api.findClient(this.clientId);
	    if (client.getContactEmail() == null || client.getContactEmail().length() == 0) {
		opMsg = "Timesheet summary report email operation failed due to client was not configured with a contact email address.   Client Name: [" + client.getName()	+ "],  Client Id: [" + client.getClientId() + "]";
		logger.error(opMsg);
		throw new InvalidDataException(opMsg);
	    }
	    employee = (ProjEmployee) empApi.findEmployee(this.empId);
	    ts = (ProjTimesheet) tsApi.findTimesheet(this.timesheetId);
	}
	catch (ProjectException e) {
	    opMsg = "Problem obtaining project client or timesheet record for timesheet summary report email operation";
	    logger.error(opMsg);
	    throw new InvalidDataException(opMsg, e);
	}
	catch (EmployeeException e) {
	    opMsg = "Problem obtaining project employee data for timesheet summary report email operation";
	    logger.error(opMsg);
	    throw new InvalidDataException(opMsg, e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
	
	// Generate report and store results as .pdf file in the user's work area
	 String pdfFileName = null;
	try {
	    RMT2XmlUtility xsl = RMT2XmlUtility.getInstance();
	    xsl.transformXslt(this.xslFileName, this.xmlFileName, this.outFileName);
	    
	    // Created PDF document as a file in the user's session work area.
	    pdfFileName = this.getUserWorkArea() + "\\" + this.getReportId() + ".pdf";
	    xsl.renderPdf(this.outFileName, pdfFileName);
	}
	catch (Exception e) {
	    opMsg = "Error generating PDF formatted timesheet report: " + e.getMessage();
	    logger.log(Level.ERROR, opMsg);
	    throw new SystemException(opMsg);
	}
	
	// Create email to send to company representative that is configured for the specified client.
	String company = System.getProperty(HttpSystemPropertyConfig.OWNER_NAME);
	String consultant = employee.getFirstname() + " " + employee.getLastname(); 
	String sender = System.getProperty(HttpSystemPropertyConfig.OWNER_EMAIL);
	String recipient = RMT2String.replace(client.getContactEmail(), ",", ";");
	String tsDate = RMT2Date.formatDate(ts.getEndPeriod(), "MM/dd/yyyy");
	StringBuffer emailTitle = new StringBuffer();
	StringBuffer emailBody = new StringBuffer();
	
	emailTitle.append(consultant);
	emailTitle.append("\'s Timesheet for week ending ");
	emailTitle.append(tsDate);
	
	emailBody.append("All,\n\n\nAttached is my timesheet for week ending ");
	emailBody.append(tsDate);
	emailBody.append(".\n\n\n");
	emailBody.append(consultant);
	emailBody.append("\n");
	emailBody.append(company);
	
	EmailMessageBean emailBean = new EmailMessageBean();
	emailBean.setFromAddress(sender);
	emailBean.setBCCAddress(sender);
	emailBean.setToAddress(recipient);
	emailBean.setSubject(emailTitle.toString());
	emailBean.setBody(emailBody.toString(), EmailMessageBean.TEXT_CONTENT);
	emailBean.setAttachments(pdfFileName);
	SmtpApi emailApi = SmtpFactory.getSmtpInstance();
	
	if (emailApi == null) {
	    return null;
	}
	try {
	    emailApi.sendMessage(emailBean);
	    emailApi.close();
	    return consultant + "\'s Timesheet Summary Report was sent successfully to " + emailBean.getRecipients() + " via SMTP";
	}
	catch (MessageException e) {
	    opMsg = "Problem sending Timesheet Summary Report via SMTP";
	    logger.error(opMsg);
	    throw new SystemException(opMsg, e);
	}
	
    }
    
    
}
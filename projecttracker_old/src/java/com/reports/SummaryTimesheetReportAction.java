package com.reports;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.controller.Request;
import com.controller.Context;

import com.action.ActionHandlerException;

import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * Action handler for generating an employee's consolidated weekly hours timesheet report to the Adobe 
 * Acrobat reader application.
 *
 * @author rterrell
 *
 */
public class SummaryTimesheetReportAction extends AbstractSummaryTimesheetReportAction {
    
      private static Logger logger = Logger.getLogger("SummaryTimesheetReportAction");


    /**
     * Default constructor.
     */
    public SummaryTimesheetReportAction() {
	super();
    }

    /**
     * Constructor responsible for initializing this object's servlet context and Http Request objects
     *
     * @param context The servlet context passed by the servlet
     * @param request The Http Servle Request object passed by the servlet
     * @throws SystemException
     */
    public SummaryTimesheetReportAction(Context context, Request request) throws SystemException {
	super(context, request);
    }

  

    /**
     * Generates the Summary Timesheet Report and renders the report visually to the user via Adobe Acrobat reader application.  
     * <p>
     * The report layout, EmployeeTimesheetReport.xsl, is transformed into the XSL-FO file which in turn will serve as input 
     * to the XSL-FO processor to render the Employee Timesheet report as a PDF.  The PDF results are streamed directly 
     * to the client's browser instead of sending an actual data file to the browser. 
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {

	try {
	    // Generate report.
	    RMT2XmlUtility xsl = RMT2XmlUtility.getInstance();
	    xsl.transformXslt(this.xslFileName, this.xmlFileName, this.outFileName);
	    xsl.renderPdf(this.outFileName, this.request, this.response);
	}
	catch (SystemException e) {
	    this.msg = "Error generating PDF formatted timesheet report: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
    }

}
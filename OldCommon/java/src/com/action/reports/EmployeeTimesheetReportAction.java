package com.action.reports;


import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.action.AbstractReportAction;

import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;

import com.bean.RMT2TagQueryBean;
import com.bean.VwClientExt;
import com.bean.VwTimesheetSummary;
import com.bean.OrmBean;

import com.constants.RMT2ServletConst;

import com.util.HostCompanyManager;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * Action handler for generating an employee's consolidated weekly hours timesheet report.
 *
 * @author rterrell
 *
 */
public class EmployeeTimesheetReportAction extends AbstractReportAction {
	/** The name of the field that represents timesheet id input control on the client */ 
    protected static final String ATTRIB_TIMESHEETID = "TimesheetId";
    /** The name of the field that represents client id input control on the client */
    protected static final String ATTRIB_CLIENTID = "ClientId";
    /** The name of the field that represents employee id input control on the client */
    protected static final String ATTRIB_EMPLOYEEID = "EmployeeId";
    
	private Logger logger;
    
    protected int timesheetId;
    protected int clientId;
	
      /**
       * Default constructor.
       */
      public EmployeeTimesheetReportAction() {
        super();
      }
    
      /**
       * Constructor responsible for initializing this object's servlet context and Http Request objects
       *
       * @param _context The servlet context passed by the servlet
       * @param _request The Http Servle Request object passed by the servlet
       * @throws SystemException
       */
      public EmployeeTimesheetReportAction(ServletContext _context, HttpServletRequest _request) throws SystemException {
          this();
          this.init(_context, _request);
      }
    
    
      /**
       * Initializes this object using _conext and _request.  Initializes this object to use 
       * the datasource, VwTimesheetSummaryView. 
       *
       * @throws SystemException
       */
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
    	  super.init(_context, _request);
    	  logger = Logger.getLogger("EmployeeTimesheetReportAction");
      }
    
      
      
 
    /**
     * Obtains the timesheet id, employee id, and client id from the request 
     * and sets the values on the session object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
        String temp;
        query.clearAllKeyValues();
        try {
            temp = this.request.getParameter("Id");
            query.addKeyValues(EmployeeTimesheetReportAction.ATTRIB_TIMESHEETID, temp);
            temp = this.request.getParameter("ProjClientId");
            query.addKeyValues(EmployeeTimesheetReportAction.ATTRIB_CLIENTID, temp);
            temp = this.request.getParameter("ProjEmployeeId");
            query.addKeyValues(EmployeeTimesheetReportAction.ATTRIB_EMPLOYEEID, temp);
        }
        catch (Exception e) {
            throw new ActionHandlerException(e.getMessage());
        }
       return;
    }
    
    
    /**
     * Prepare the environment for running the Employee Timesheet report.  The 
     * procees includes creating and/or identifying the user's profile work area, 
     * identifying the input and expected output data files, and gathering the report 
     * data needed to build the report xml data file. This method is call prior to the
     * actual generation of the report.
     *  
     * @return Timesheet Data as XML
     * @throws ActionHandlerException
     */
    protected String initializeReport() throws ActionHandlerException {
        String temp = null;

        // Copy report input layout file to user's profile work area
        try {
            this.setupReportLayout();    
        }
        catch (SystemException e) {
        	this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

        //  Recover data fro mthe session
        RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.REPORT_BEAN);
        temp = (String)query.getKeyValues(EmployeeTimesheetReportAction.ATTRIB_TIMESHEETID);
        this.timesheetId = Integer.parseInt(temp);
        temp = (String)query.getKeyValues(EmployeeTimesheetReportAction.ATTRIB_CLIENTID);
        this.clientId = Integer.parseInt(temp);
        
        // Build company data
        String xml = this.fetchHostCompany(HostCompanyManager.getInstance());
        
        DaoApi dao = DataSourceFactory.createDao(this.dbConn);
        // Get Client data
        try {
            VwClientExt client = new VwClientExt();
            client.addCriteria("ClientId", clientId);
            client.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            Object results[] = dao.retrieve(client);
            xml += (String) results[0];
        }
        catch (Exception e) {
        	this.msg = "Error building XML data from the VwClientExt data source";
        	logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
         
        // Get Timesheet data
        try {
            VwTimesheetSummary emp = new VwTimesheetSummary();
            emp.addCriteria("TimesheetId", timesheetId);
            emp.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            Object results[] = dao.retrieve(emp);
            xml += (String) results[0];
        }
        catch (Exception e) {
        	this.msg = "Error building XML data from the VwTimesheetSummary data source";
        	logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        return xml;
    }
    
    /**
     * Generates the Employee Timesheet Report.  The report layout, EmployeeTimesheetReport.xsl, is 
     * transformed into the XSL-FO file which in turn will serve as input to the XSL-FO processor to 
     * render the Employee Timesheet report as a PDF.  The PDF results are streamed directly to the 
     * client's browser instead of sending an actual data file to the browser. 
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
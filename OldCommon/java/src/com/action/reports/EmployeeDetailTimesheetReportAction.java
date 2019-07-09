package com.action.reports;


import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.api.DataSourceApi;
import com.api.db.orm.DataSourceFactory;


import com.util.SystemException;

/**
 * Action handler for generating an employee's consolidated weekly hours timesheet report.
 *
 * @author rterrell
 *
 */
public class EmployeeDetailTimesheetReportAction extends EmployeeTimesheetReportAction {
	  private Logger logger;
	
      /**
       * Default constructor.
       */
      public EmployeeDetailTimesheetReportAction() {
        super();
      }
    
      /**
       * Constructor responsible for initializing this object's servlet context and Http Request objects
       *
       * @param _context The servlet context passed by the servlet
       * @param _request The Http Servle Request object passed by the servlet
       * @throws SystemException
       */
      public EmployeeDetailTimesheetReportAction(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super();
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
    	  logger = Logger.getLogger("EmployeeDetailTimesheetReportAction");
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
    	String xml = super.initializeReport();
        DataSourceApi dsApi = DataSourceFactory.create(this.dbConn);
        try {
            String select = "select * from vw_timesheet_project_task, proj_event "; 
            String where = "where vw_timesheet_project_task.project_task_id = proj_event.proj_project_task_id "; 
            String orderBy = " order by vw_timesheet_project_task.timesheet_id, vw_timesheet_project_task.project_name,  vw_timesheet_project_task.task_name, proj_event.event_date ";
            String criteria = " and vw_timesheet_project_task.timesheet_id = " + this.timesheetId;
            String sql = select + where + criteria + orderBy;
            xml += dsApi.executeXmlQuery(sql);
        }
        catch (Exception e) {
            this.msg = "Error building XML data from the detail Project-Task-Hours Query";
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        return xml;
    }
    

}
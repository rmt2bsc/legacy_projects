package com.reports;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.controller.Context;

import com.action.ActionHandlerException;

import com.api.DataSourceApi;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceFactory;

import com.util.SystemException;

/**
 * Action handler for generating an employee's consolidated weekly hours timesheet report.
 *
 * @author rterrell
 *
 */
public class DetailTimesheetReportAction extends SummaryTimesheetReportAction {
    private Logger logger;

    /**
     * Default constructor.
     */
    public DetailTimesheetReportAction() {
	super();
    }

    /**
     * Constructor responsible for initializing this object's servlet context and Http Request objects
     *
     * @param context The servlet context passed by the servlet
     * @param request The Http Servle Request object passed by the servlet
     * @throws SystemException
     */
    public DetailTimesheetReportAction(Context context, Request request) throws SystemException {
	super();
	this.init(context, request);
    }

    /**
     * Initializes this object using _conext and _request.  Initializes this object to use 
     * the datasource, VwTimesheetSummaryView. 
     *
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
	super.init(context, request);
	logger = Logger.getLogger("DetailTimesheetReportAction");
    }

    /**
     * Prepare the environment for running the Employee Detail Timesheet report.  The 
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
	DatabaseTransApi tx = DatabaseTransFactory.create();
	DataSourceApi dsApi = DataSourceFactory.create((DatabaseConnectionBean) tx.getConnector());
	try {
	    String select = "select * from vw_timesheet_project_task, proj_event ";
	    String where = "where vw_timesheet_project_task.project_task_id = proj_event.project_task_id ";
	    String orderBy = " order by vw_timesheet_project_task.timesheet_id, vw_timesheet_project_task.project_name,  vw_timesheet_project_task.task_name, proj_event.event_date ";
	    String criteria = " and vw_timesheet_project_task.timesheet_id = " + this.timesheetId;
	    String sql = select + where + criteria + orderBy;
	    xml += dsApi.executeXmlQuery(sql);
	    return xml;
	}
	catch (Exception e) {
	    this.msg = "Error building XML data from the detail Project-Task-Hours Query";
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	finally {
		dsApi.close();
		dsApi = null;
		tx.close();
		tx = null;
	}
    }

}
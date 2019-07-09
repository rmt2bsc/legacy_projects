package com.project.timesheet;

import com.controller.Request;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.ProjTimesheet;
import com.bean.ProjProjectTask;
import com.bean.ProjEvent;
import com.bean.ProjTimesheetHist;
import com.bean.ProjTimesheetStatus;
import com.bean.VwClientTimesheetSummary;
import com.bean.VwTimesheetEventList;
import com.bean.VwTimesheetList;
import com.bean.VwTimesheetProjectTask;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

public class TimesheetFactory extends DataSourceAdapter {

    /**
     * Instantiates a TimesheetApi using the DatabaseConnectionBean
     * 
     * @param dbo
     * @return
     */
    public static TimesheetApi createApi(DatabaseConnectionBean dbo) {
	try {
	    TimesheetApi api = new TimesheetImpl(dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    
    /**
     * Instantiates a TimesheetApi using the DatabaseConnectionBean and user's Request
     *  
     * @param _dbo Database connection bean
     * @return {@link TimesheetApi}
     */
    public static TimesheetApi createApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    TimesheetApi api = new TimesheetImpl(dbo, request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }
    
    
    /**
     * Instantiates a TimesheetApi using the DatabaseConnectionBean,  user's Request, and the project id.
     *  
     * @param _dbo Database connection bean
     * @return {@link TimesheetApi}
     */
    public static TimesheetApi createApi(DatabaseConnectionBean dbo, Request request, int projectId) {
        try {
            TimesheetApi api = new TimesheetImpl(dbo, request);
            api.setCurrentProjectId(projectId);
            return api;
        }
        catch (DatabaseException e) {
            return null;
        }
        catch (SystemException e) {
            return null;
        }
    }

    
    /**
     * Instantiates a SMTP implementation of TimesheetTransmissionApi interface.
     * 
     * @param dbCon Database connection bean
     * @param request User's request
     * @return {@link com.project.timesheet.TimesheetTransmissionApi TimesheetTransmissionApi}
     */
    public static TimesheetTransmissionApi createTransmissionApi(DatabaseConnectionBean dbCon, Request request) {
        try {
            TimesheetTransmissionApi api = new SMTPTransmissionImpl(dbCon, request);
            return api;
        }
        catch (DatabaseException e) {
            return null;
        }
        catch (SystemException e) {
            return null;
        }
    }
    
    
    /**
     * Instantiates an project timesheet status Api using the Factory Method.
     *  
     * @param _dbo Database connection bean
     * @return {@link TimesheetStatusApi}
     */
    public static TimesheetStatusApi createStatusApi(DatabaseConnectionBean dbo) {
	try {
	    TimesheetStatusApi api = new TimesheetStatusImpl(dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Instantiates an project timesheet status Api using DatabaseConnectionBean and HttpServletRequest.
     * .
     * @param _dbo
     * @param _request
     * @return
     */
    public static TimesheetStatusApi createStatusApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    TimesheetStatusApi api = new TimesheetStatusImpl(dbo, request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new {@link ProjEvent} object.
     * 
     * @return {@link ProjEvent}
     */
    public static ProjEvent createProjEvent() {
	try {
	    return new ProjEvent();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new VwTimesheetList object.
     * 
     * @return {@link VwTimesheetList}
     */
    public static VwTimesheetList createVwTimesheetList() {
	try {
	    return new VwTimesheetList();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new XML verision of VwTimesheetEventList object.
     * 
     * @return {@link VwTimesheetList}
     */
    public static VwTimesheetList createXmlVwTimesheetList() {
	VwTimesheetList obj = TimesheetFactory.createVwTimesheet();
	obj.setResultsetType(VwTimesheetList.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }

    /**
     * Creates a new VwTimesheetEventList object.
     * 
     * @return {@link VwTimesheetEventList}
     */
    public static VwTimesheetEventList createVwTimesheetEventList() {
	try {
	    return new VwTimesheetEventList();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new Project Timesheet object.
     * 
     * @return {@link ProjTimesheet}
     */
    public static ProjTimesheet createTimesheet() {
	try {
	    return new ProjTimesheet();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new Project Timesheet View object.
     * 
     * @return {@link VwTimesheetList}
     */
    public static VwTimesheetList createVwTimesheet() {
	try {
	    return new VwTimesheetList();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new Project Timesheet View object.
     * 
     * @return {@link VwTimesheetProjectTask}
     */
    public static VwTimesheetProjectTask createVwTimesheetProjectTask() {
	try {
	    return new VwTimesheetProjectTask();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates a new Project Timesheet History object.
     * 
     * @return {@link ProjTimesheetHist}
     */
    public static ProjTimesheetHist createTimesheetHist() {
	try {
	    return new ProjTimesheetHist();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     Creates a new Project Task object.
     * 
     * @return {@link ProjProjectTask} 
     */
    public static ProjProjectTask createProjectTask() {
	try {
	    return new ProjProjectTask();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     Creates a new Project Task Extension object.
     * 
     * @return {@link VwTimesheetProjectTask} 
     */
    public static VwTimesheetProjectTask createProjectTaskExt() {
	try {
	    return new VwTimesheetProjectTask();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     Creates a new Project Timesheet Status object.
     * 
     * @return {@link ProjTimesheetStatus} 
     */
    public static ProjTimesheetStatus createTimesheetStatus() {
	try {
	    return new ProjTimesheetStatus();
	}
	catch (SystemException e) {
	    return null;
	}
    }

    
    /**
    * Creates a new XML Project Timesheet Status object.
    * 
    * @return {@link ProjTimesheetStatus} 
    */
   public static ProjTimesheetStatus createXmlTimesheetStatus() {
       ProjTimesheetStatus obj = TimesheetFactory.createTimesheetStatus();
       obj.setResultsetType(VwTimesheetList.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
   }
   
   /**
    * Creates a new VwClientTimesheetSummary object.
    * @return {@link VwClientTimesheetSummary} 
    */
   public static VwClientTimesheetSummary createVwClientTimesheetSummary() {
	   VwClientTimesheetSummary obj = new VwClientTimesheetSummary();
	   return obj;
   }
   
   /**
    * Creates a new XML VwClientTimesheetSummary object.
    * 
    * @return {@link VwClientTimesheetSummary} 
    */
   public static VwClientTimesheetSummary createXmlVwClientTimesheetSummary() {
	   VwClientTimesheetSummary obj = TimesheetFactory.createVwClientTimesheetSummary();
       obj.setResultsetType(VwTimesheetList.RESULTSET_TYPE_XML);
       obj.setSerializeXml(false);
       return obj;
   }
}

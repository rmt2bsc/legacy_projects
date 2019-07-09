package com.project;



import com.controller.Request;

import com.project.invoice.InvoicingApi;
import com.project.invoice.TimesheetInvoicingImpl;

import com.api.db.orm.DataSourceAdapter;

import com.bean.ProjClient;
import com.bean.ProjTimesheet;
import com.bean.ProjProjectTask;
import com.bean.ProjEvent;
import com.bean.ProjTimesheetHist;
import com.bean.VwTimesheetProjectTask;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

/**
 * 
 * @author RTerrell 
 * @deprecated No longer needed.   Use com.project.timesheet.TimesheetFactory.java
 */
public class ProjectManagerFactory extends DataSourceAdapter  {

    /**
     * 
     * @param _dbo
     * @return
     */
  public static ProjectQueryApi createProjectQueryApi(DatabaseConnectionBean _dbo) {
    try {
        ProjectQueryApi api = new ProjectManagerApiImpl(_dbo);
        return api;
    }
    catch (Exception e) {
        return null;
    }
  }
  
  
  /**
   * 
   * @param _dbo
   * @return
   */
  public static ProjectMaintenanceApi createProjectMaintApi(DatabaseConnectionBean _dbo) {
     try {
         ProjectMaintenanceApi api = new ProjectManagerApiImpl(_dbo);
         return api;
     }
     catch (Exception e) {
         return null;
     }
   }
  
        
        /**
         * Creates a InvoicingApi using time sheet invoicing implementation which 
         * initializes the database connection bean and the user's request.
         * 
         * @param dbo The database connection bean instance
         * @param request The user's request object.
         * @return {@link com.project.invoice.InvoicingApi InvoicingApi}
         */
        public static InvoicingApi createProjectInvoiceApi(DatabaseConnectionBean dbo, Request request) {
            try {
                InvoicingApi api = new TimesheetInvoicingImpl(dbo, request);
                return api;    
            }
            catch (Exception e) {
                return null;
            }
        }
        
	

	
		
	/**
	 * Creates a new {@link ProjEvent} object.
	 * 
	 * @return {@link ProjEvent}
	 */
	public static ProjEvent createProjEvent()  {
		try {
			return new ProjEvent();
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
	
}



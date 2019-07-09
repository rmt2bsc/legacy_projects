package com.factory;

import javax.servlet.http.HttpServletRequest;

import com.api.ProjectManagerApi;
import com.api.EmployeeApi;
import com.api.ProjectTimesheetStatusApi;
import com.api.ProjectTimesheetApi;
import com.api.ProjectSetupApi;
import com.api.ProjectBillingApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.ProjectManagerApiImpl;
import com.apiimpl.EmployeeImpl;
import com.apiimpl.ProjectSetupImpl;
import com.apiimpl.ProjectTimesheetImpl;
import com.apiimpl.ProjectTimesheetStatusImpl;

import com.bean.ProjEmployee;
import com.bean.ProjClient;
import com.bean.ProjTimesheet;
import com.bean.ProjProject;
import com.bean.ProjTask;
import com.bean.ProjProjectTask;
import com.bean.ProjEvent;
import com.bean.ProjTimesheet;
import com.bean.ProjTimesheetHist;
import com.bean.VwTimesheetProjectTask;
import com.bean.VwEmployeeExt;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;


public class ProjectManagerFactory extends DataSourceAdapter  {

  public static ProjectManagerApi createApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     ProjectManagerApi api = new ProjectManagerApiImpl(_dbo);
     api.setBaseView("ProjEventView");
     api.setBaseClass("com.bean.ProjEvent");
     return api;
  }
  
  /**
   * Instantiates an employee Api using the Factory Method.
   *  
   * @param _dbo Database connection bean
   * @return {@link EmployeeApi}
   */
  	public static EmployeeApi createEmployeeApi(DatabaseConnectionBean _dbo) {
  		try {
  			EmployeeApi api = new EmployeeImpl(_dbo);
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
  	 * Uses the Factory Method to instantiate employee api.
  	 * 
  	 * @param _dbo Database connection bean
  	 * @param _request Servlet Http Request object.
  	 * @return
  	 */
  	public static EmployeeApi createEmployeeApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
  		try {
  			EmployeeApi api = new EmployeeImpl(_dbo, _request);
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
     * Instantiates an project setup Api using RMT2DBConectionBean
     *  
     * @param _dbo Database connection bean
     * @return {@link ProjectSetupApi}
     */
	public static ProjectSetupApi createProjectSetupApi(DatabaseConnectionBean _dbo) {
		try {
			ProjectSetupApi api = new ProjectSetupImpl(_dbo);
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
     * Instantiates an project setup Api using RMT2DBConectionBean and HttpServletRequest
     * 
     * @param _dbo
     * @param _request
     * @return {@link ProjectSetupApi}
     */
    public static ProjectSetupApi createProjectSetupApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
        try {
            ProjectSetupApi api = new ProjectSetupImpl(_dbo, _request);
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
	 * Instantiates an project timesheet Api using the Factory Method.
	 *  
	 * @param _dbo Database connection bean
	 * @return {@link ProjectTimesheetApi}
	 */
	public static ProjectTimesheetApi createProjectTimesheetApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
		try {
			ProjectTimesheetApi api = new ProjectTimesheetImpl(_dbo, _request);
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
	 * @return {@link ProjectTimesheetStatusApi}
	 */
		public static ProjectTimesheetStatusApi createProjectTimesheetStatusApi(DatabaseConnectionBean _dbo) {
			try {
				ProjectTimesheetStatusApi api = new ProjectTimesheetStatusImpl(_dbo);
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
        public static ProjectTimesheetStatusApi createProjectTimesheetStatusApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
            try {
                ProjectTimesheetStatusApi api = new ProjectTimesheetStatusImpl(_dbo, _request);
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
	   * Cerates an employee object.
	   * 
	   * @return {@link ProjEmployee}
	   * @throws SystemException
	   * 
	   */
	  public static ProjEmployee createEmployee() throws SystemException {
		  try {
			  return new ProjEmployee();
		  }
		  catch (SystemException e) {
			  return null;
		  }
	  }
	  
	  
	  /**
	   * Creates a VwEmployeeExt object.
	   * 
	   * @return VwEmployeeExt
	   */
	  public static VwEmployeeExt createEmployeeExt()  {
	      try {
	          return new VwEmployeeExt();  
	      }
	      catch (SystemException e) {
	          return null;
	     }
	  }
	
	  /**
	   * Creates a new {@link ProjClient} object.
	   * 
	   * @return {@link ProjClient}
	   */
	  public static ProjClient createClient()  {
		  try {
			  return new ProjClient();
		  }
		  catch (SystemException e) {
			  return null;
		  }
	  }
	
	  /**
	   * Creaes a new {@link ProjProject} object
	   * 
	   * @return {@link ProjProject}
	   */
	public static ProjProject createProject()  {
		try {
			return new ProjProject();
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
	 * Creates a new Task object.
	 * 
	 * @return {@link ProjTask}
	 */
	public static ProjTask createTask() {
		try {
			return new ProjTask();
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



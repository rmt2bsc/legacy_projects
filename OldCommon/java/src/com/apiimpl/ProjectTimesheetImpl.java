package com.apiimpl;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import com.api.ProjectTimesheetApi;
import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.ProjClient;
import com.bean.ProjEvent;
import com.bean.ProjProjectTask;
import com.bean.ProjTimesheet;
import com.bean.VwTimesheetEventList;
import com.bean.VwTimesheetHours;
import com.bean.VwTimesheetList;
import com.bean.VwTimesheetProjectTask;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;


import com.factory.ProjectManagerFactory;

import com.util.ProjectException;

import com.util.SystemException;
import com.util.RMT2Utility;

/**
 * Implementation of ProjectTimesheetApi that manages an employee's timesheet activities.
 * 
 * @author appdev
 *
 */
public class ProjectTimesheetImpl extends RdbmsDataSourceImpl implements ProjectTimesheetApi  {
    
	private String criteria;
	private Logger logger;

   /**
    * Default Constructor
    * 
    * @throws DatabaseException
    * @throws SystemException
    */
   protected ProjectTimesheetImpl() throws DatabaseException, SystemException   {
	   super();   
	   logger = Logger.getLogger("ProjectTimesheetImpl");
   }

   /**
    * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
    * 
    * @param dbConn
    * @throws DatabaseException
    * @throws SystemException
    */
    public ProjectTimesheetImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
    	super(dbConn);
        logger = Logger.getLogger("ProjectTimesheetImpl");
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param _request
     * @throws DatabaseException
     * @throws SystemException
     */
    public ProjectTimesheetImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
        super(dbConn);
        this.setRequest(_request);
        logger = Logger.getLogger("ProjectTimesheetImpl");
    }
    

    /**
     * No purpose
     */
    public Object[] findData(Object ormBean) throws DatabaseException {
        return null;
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findTimesheet(int) 
     */
    public ProjTimesheet findTimesheet(int _timesheetId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjTimesheet");
    	this.setBaseView("ProjTimesheetView");
    	this.criteria = "id  = " + _timesheetId;
    	try {
    	    List list = this.find(this.criteria);
    		if (list == null || list.size() <= 0) {
    			return null;
    		}
    		return (ProjTimesheet) list.get(0);
    	}
    	catch (SystemException e) {
    		this.msgArgs.clear();
    		this.msgArgs.add(e.getMessage());
			throw new ProjectException(this.connector, 1000, this.msgArgs);
		}
	}

    
    /**
     * @see com.api.ProjectTimesheetApi#findTimesheetExt(int)
     */
    public VwTimesheetList findTimesheetExt(int _timesheetId) throws ProjectException {
        this.setBaseClass("com.bean.VwTimesheetList");
        this.setBaseView("VwTimesheetListView");
        this.criteria = "timesheet_id  = " + _timesheetId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (VwTimesheetList) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findTimesheetByClient(int)
     */
    public Object[]  findTimesheetByClient(int _clientId) throws ProjectException {
        ProjTimesheet ts = null;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        try {
            ts = ProjectManagerFactory.createTimesheet();
            ts.addCriteria("ProjClientId", _clientId);
            return dao.retrieve(ts);
        }
        catch (DatabaseException e) {
            throw new ProjectException(e.getMessage());
        }
    }
        
    /**
     * @see com.api.ProjectTimesheetApi#findTimesheetExtByClient(int)
     */
    public List findTimesheetExtByClient(int _clientId) throws ProjectException {
        this.setBaseClass("com.bean.VwTimesheetList");
        this.setBaseView("VwTimesheetListView");
        this.criteria = "proj_client_id  = " + _clientId;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findTimesheetExtByEmployee(int)
     */
    public List findTimesheetExtByEmployee(int _employeeId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetList");
        this.setBaseView("VwTimesheetListView");
        this.criteria = "proj_employee_id  = " + _employeeId;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    public Object[] findTimesheetHours(int _timesheetId) throws ProjectException {
        VwTimesheetHours vth = null;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
        try {
            vth = new VwTimesheetHours();
            vth.addCriteria("TimesheetId", _timesheetId);
            return dao.retrieve(vth);
        }
        catch (DatabaseException e) {
            throw new ProjectException(e.getMessage());
        }
        catch (SystemException e) {
            throw new ProjectException(e.getMessage());
        }
    }
    
   
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTask(int)
     */
    public ProjProjectTask findProjectTask(int _projectTaskId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjProjectTask");
        this.setBaseView("ProjProjectTaskView");
        this.criteria = "id  = " + _projectTaskId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (ProjProjectTask) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTaskExt(int)
     */
    public VwTimesheetProjectTask findProjectTaskExt(int _projectTaskId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetProjectTask");
        this.setBaseView("VwTimesheetProjectTaskView");
        this.criteria = "project_task_id  = " + _projectTaskId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (VwTimesheetProjectTask) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTaskExtByClient(int)
     */
    public List findProjectTaskExtByClient(int _clientId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetProjectTask");
        this.setBaseView("VwTimesheetProjectTaskView");
        this.criteria = "client_id  = " + _clientId;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTaskByTimesheet(int)
     */
    public List findProjectTaskByTimesheet(int _timesheetId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjProjectTask");
        this.setBaseView("ProjProjectTaskView");
        this.criteria = "proj_timesheet_id  = " + _timesheetId;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTaskExtByTimesheet(int)
     */
    public List findProjectTaskExtByTimesheet(int _timesheetId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetProjectTask");
        this.setBaseView("VwTimesheetProjectTaskView");
        this.criteria = "timesheet_id  = " + _timesheetId;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTaskExtByProject(int)
     */
    public List findProjectTaskExtByProject(int _projectId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetProjectTask");
        this.setBaseView("VwTimesheetProjectTaskView");
        this.criteria = "project_id  = " + _projectId;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTaskExtByTask(int)
     */
    public  List findProjectTaskExtByTask(int _taskId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetProjectTask");
        this.setBaseView("VwTimesheetProjectTaskView");
        this.criteria = "task_id  = " + _taskId;
        this.criteria = null;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findProjectTaskExtByProjectTask(int, int)
     */
    public List findProjectTaskExtByProjectTask(int _projectId, int _taskId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetProjectTask");
        this.setBaseView("VwTimesheetProjectTaskView");
        this.criteria = "project_id  = " + _projectId + " and task_id  = " + _taskId;
        this.criteria = null;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findEvent(int)
     */
    public ProjEvent findEvent(int _eventId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjEvent");
        this.setBaseView("ProjEventView");
        this.criteria = "id  = " + _eventId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (ProjEvent) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventExt(int)
     */
    public VwTimesheetEventList findEventExt(int _eventId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "event_id  = " + _eventId;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (VwTimesheetEventList) list.get(0);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventByEventDate(Date)
     */
    public List findEventExtByEventDate(Date _eventIDate) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "event_date  = " + _eventIDate;
        this.criteria = null;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventExtByEventDate(Date)
     */
    public List findEventExtByEventDate(Date _beginDate, Date _endDate) throws ProjectException {
    	String beginDate = null;
    	String endDate = null;
    	String msg = null;
    	
    	if (_beginDate != null && _endDate != null) {
    		try {
    			beginDate = RMT2Utility.formatDate(_beginDate, "MM/dd/yyyy HH:mm:ss");
        		endDate = RMT2Utility.formatDate(_endDate, "MM/dd/yyyy HH:mm:ss");	
    		}
    		catch (SystemException e) {
    			logger.log(Level.ERROR, e.getMessage());
        		throw new ProjectException(e.getMessage());
    		}
    	}
    	else {
    		msg = "Begin date and/or end date cannot be null";
    		logger.log(Level.ERROR, msg);
    		throw new ProjectException(msg); 
    	}
    	
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "event_date  between (\'" + beginDate + "\' and \'" + endDate + "\')";
        this.criteria = null;
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventByProjectTask(int)
     */
    public List findEventByProjectTask(int _projectTaskId) throws ProjectException {
    	this.setBaseClass("com.bean.ProjEvent");
        this.setBaseView("ProjEventView");
        this.criteria = "proj_project_task_id  = " + _projectTaskId;
        
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventExtByProjectTask(int)
     */
    public List findEventExtByProjectTask(int _projectTaskId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "project_task_id  = " + _projectTaskId;
        
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventExtByClient(int)
     */
    public List findEventExtByClient(int _clientId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "client_id  = " + _clientId;
        this.criteria = null;
        try {
            List list = this.find(this.criteria);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return list;
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventExtByTimesheet(int)
     */
    public List findEventExtByTimesheet(int _timesheetId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "timesheet_id  = " + _timesheetId;

        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventExtByProject(int)
     */
    public List findEventExtByProject(int _projectId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "project_id  = " + _projectId;
        
        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#findEventExtByTask(Date)
     */
    public List findEventExtByTask(int _taskId) throws ProjectException {
    	this.setBaseClass("com.bean.VwTimesheetEventList");
        this.setBaseView("VwTimesheetEventListView");
        this.criteria = "task_id  = " + _taskId;

        try {
        	return this.findData(this.criteria, null);
        }
        catch (SystemException e) {
            this.msgArgs.clear();
            this.msgArgs.add(e.getMessage());
            throw new ProjectException(this.connector, 1000, this.msgArgs);
        }
    }
    

    /**
     * @see com.api.ProjectTimesheetApi#getTimePeriodWeekDates(Date)
     */
    public Date[] getTimePeriodWeekDates(Date _timePeriod) {
        try {
            return RMT2Utility.getWeekDates(_timePeriod);    
        }
        catch (SystemException e) {
            return null;
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#getAvailableEndingPeriods()
     */
    public String[] getAvailableEndingPeriods() {
       String periods[] = null;
       Date dow[] = null;
       Date curEndDate = null;
       Calendar calTemp = Calendar.getInstance();
       int maxPeriods = 0;
       int weekDecrement = -7;
       
       try {
           dow = RMT2Utility.getWeekDates();
           curEndDate = dow[6];
           maxPeriods = this.getMaxPeriodCount();
       }
       catch (Exception e) {
           logger.log(Level.DEBUG, "Problem obtaining available timesheet ending periods");
           logger.log(Level.DEBUG, e.getMessage());
           return null;
       }
       
       try {
           periods = new String[maxPeriods];
           calTemp.setTime(curEndDate);
           for (int ndx = 0; ndx < maxPeriods; ndx++) {
               if (ndx == 0) {
                   periods[ndx] = RMT2Utility.formatDate(curEndDate, "MM/dd/yyyy");
                   logger.log(Level.DEBUG, periods[ndx]);
                   continue;
               }
                
               calTemp.add(Calendar.DATE, (weekDecrement));
               periods[ndx] = RMT2Utility.formatDate(calTemp.getTime(), "MM/dd/yyyy");
               logger.log(Level.DEBUG, periods[ndx]);
           }
           return periods;    
       }
       catch (SystemException e) {
           return null;
           
       }
       
    }

    /**
     * Get value that indicates the total number of numeircal digits that composes the timesheet's displayValue.
     * 
     * @return Maximum size as an int.  Returns 5 if a problem occurred converting the property value to a number.
     * @throws ProjectException Prooperty file access errors.
     */
    public int getMaxDisplayValueDigits() throws ProjectException {
        int sheetIdSize  = 0;
        String temp = null;
        try {
            temp = RMT2Utility.getPropertyValue("Timesheet", "sheet_id_size");
            sheetIdSize = Integer.parseInt(temp);    
            return sheetIdSize;
        }
        catch (NumberFormatException e) {
            logger.log(Level.DEBUG, "Problem converting " + temp + " to a number");
            return 5;
        }
        catch (SystemException e) {
            throw new ProjectException(e.getMessage());
        }
    }
    
    /**
     * Returns the value that indicates the total number of ending periods to handle.
     * 
     * @return int representing maximum periods
     * @throws ProjectException Prooperty file access errors.
     */
    private int getMaxPeriodCount() throws ProjectException {
        int periodCount = 0;
        String temp = null;
        try {
            temp = RMT2Utility.getPropertyValue("Timesheet", "max_endperiods");
            periodCount = Integer.parseInt(temp);    
            return periodCount;
        }
        catch (NumberFormatException e) {
            logger.log(Level.DEBUG, "Problem converting " + temp + " to a number");
            return 4;
        }
        catch (SystemException e) {
            throw new ProjectException(e.getMessage());
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#maintainTimesheet(ProjTimesheet)
     */
    public int maintainTimesheet(ProjTimesheet _timesheet) throws ProjectException {
    	int rc = 0;
    	
    	if (_timesheet == null) {
            this.msg = "Base timesheet object is invalid and could not be processed";
    		logger.log(Level.ERROR, this.msgArgs);
    		throw new ProjectException(this.msg);
    	}
    	
    	if (_timesheet.getId() == 0) {
    		rc = this.createTimesheet(_timesheet);
    	}
    	else {
    		rc = this.updateTimesheet(_timesheet);
    	}
    	return rc;
    }
  

    /**
     * Creates a timesheet and saves changes to the database.
     * 
     * @param _ts The timesheet data.
     * @return New timesheet id.
     * @throws ProjectException if client id is not provided, or if employee id is not provided, or a database access error.
     */
    protected int createTimesheet(ProjTimesheet _ts) throws ProjectException {
    	int rc = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	UserTimestamp ut = null;
        ProjClient client = null;
        List list = null;
        
    	this.validateTimesheet(_ts);
    	 try {
             ut = RMT2Utility.getUserTimeStamp(this.request);
             
             // If client does not exist in the Proj_Client table then add
             this.setBaseView("ProjClientView");
             this.setBaseClass("com.bean.ProjClient");
             criteria = " id = " + _ts.getProjClientId();
             list = this.findData(criteria, null);
             if (list.size() <= 0) {
                 client = ProjectManagerFactory.createClient();
                 client.setId(_ts.getProjClientId());
                 client.setDateCreated(ut.getDateCreated());
                 client.setDateUpdated(ut.getDateCreated());
                 client.setUserId(ut.getLoginId());
                 dao.insertRow(client, false);
             }
             
             // Add base timesheet
             _ts.setDateCreated(ut.getDateCreated());
             _ts.setDateUpdated(ut.getDateCreated());
             _ts.setUserId(ut.getLoginId());
             rc = dao.insertRow(_ts, true);
             _ts.setId(rc);
             
             // Set the defaultDisplay property since its value is primary key based, and the primary key's vlue is generated during previous insert
             _ts.setDisplayValue(RMT2Utility.padInt(rc, this.getMaxDisplayValueDigits(), RMT2Utility.PAD_LEADING));
             _ts.setId(rc);
             _ts.addCriteria("Id", _ts.getId());
             dao.updateRow(_ts);
             
             return rc;
         }
         catch (DatabaseException e) {
             this.msg = e.getMessage();
             logger.log(Level.DEBUG, this.msg);
             throw new ProjectException(this.msg);
         }
         catch (SystemException e) {
        	 logger.log(Level.ERROR, e.getMessage());
             throw new ProjectException(e);
         }
    }
    
    /**
     * Applies changes to an existing timesheet to the database.
     * 
     * @param _ts The m odified timesheet.
     * @return The existing timesheet id.
     * @throws ProjectException if client id is not provided, or if employee id is not provided, or a database access error.
     */
    protected int updateTimesheet(ProjTimesheet _ts) throws ProjectException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	UserTimestamp ut = null;
    	
    	this.validateTimesheet(_ts);
    	try {
            ut = RMT2Utility.getUserTimeStamp(this.request);
            _ts.setDateUpdated(ut.getDateCreated());
            _ts.setUserId(ut.getLoginId());
            if (_ts.getDisplayValue() == null) {
            	_ts.setDisplayValue(RMT2Utility.padInt(_ts.getId(), this.getMaxDisplayValueDigits(), RMT2Utility.PAD_LEADING));
            }
            _ts.addCriteria("Id", _ts.getId());
            dao.updateRow(_ts);
            return _ts.getId();
        }
        catch (DatabaseException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ProjectException(e);
        }
        catch (SystemException e) {
       	 	logger.log(Level.ERROR, e.getMessage());
            throw new ProjectException(e);
        }
    }
    
    /**
     * Verifies that a client and an employee is associated with the timesheet.
     * 
     * @param _ts The timesheet to be validated.
     * @throws ProjectException if the client id and/or the employee id is found not to be associated with the timesheet.
     */
    protected void validateTimesheet(ProjTimesheet _ts) throws ProjectException {
    	if (_ts.getProjClientId() <= 0) {
    		this.msg ="Timesheet must be associated with a client";
    		logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
    	}
    	if (_ts.getProjEmployeeId() <= 0) {
    		this.msg ="Timesheet must be associated with an employee";
    		logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
    	}
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#maintainProjectTask(ProjProjectTask)
     */
    public int maintainProjectTask(ProjProjectTask _projectTask) throws ProjectException {
    	int rc = 0;
    	
    	if (_projectTask == null) {  
            this.msg = "Project-Task object is invalid and could not be processed";
    		logger.log(Level.ERROR, this.msgArgs);
    		throw new ProjectException(this.msg);
    	}
    	
    	if (_projectTask.getId() == 0) {
    		rc = this.createProjectTask(_projectTask);
            _projectTask.setId(rc);
    	}
    	return _projectTask.getId();
    }
    
    /**
     * Creates a project-task record and saves changes to the database.
     * 
     * @param _pt Project Task data
     * @return The id of the project task.
     * @throws ProjectException if a validation error occurs.
     */
    protected int createProjectTask(ProjProjectTask _pt) throws ProjectException {
    	int rc = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	    	
    	this.validateProjectTask(_pt);
    	 try {
             rc = dao.insertRow(_pt, true);
             _pt.setId(rc);
             return rc;
         }
         catch (DatabaseException e) {
             logger.log(Level.ERROR, e.getMessage());
             throw new ProjectException(e);
         }
    }
    
    /**
     * Verifies that individual values for timesheet, project, and task have be set.
     * 
     * @param _pt The project-task record that is to be validated.
     * @throws ProjectException if the values for timesheet, project, and/or task are not set.
     */
    protected void validateProjectTask(ProjProjectTask _pt) throws ProjectException {
    	if (_pt.getProjProjectId() <= 0) {
    		this.msg ="Proejct Id is required when creating Project-Task";
    		logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
    	}
    	if (_pt.getProjTaskId() <= 0) {
    		this.msg ="Task Id is required when creating Project-Task";
    		logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
    	}
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#maintainEvent(int, ArrayList)
     */
    public int maintainEvent(int _projectTaskId, List _events) throws ProjectException {
    	int count = 0;
    	
    	if (_projectTaskId <= 0) {
    		this.msg = "A project-task id must be valid in order to process a multiple event transaction";
    		logger.log(Level.ERROR, this.msgArgs);
    		throw new ProjectException(this.msg);
    	}
    	if (this.findProjectTask(_projectTaskId) == null) {
    		this.msg = "A project-task id must exist in the system in order to process a multiple event transaction";
    		logger.log(Level.ERROR, this.msgArgs);
    		throw new ProjectException(this.msg);
    	}
    	if (_events == null) {
    		this.msg = "The collection containing the lists of timesheet events must be valid in order to process a mulitiple event transaction";
    		logger.log(Level.ERROR, this.msgArgs);
    		throw new ProjectException(this.msg);
    	}
    	
    	// Begin processing each item in the event ArrayList
    	ProjEvent event = null;
    	for (int ndx = 0; ndx < _events.size(); ndx++) {
    		event = (ProjEvent) _events.get(ndx);
            event.setProjProjectTaskId(_projectTaskId);
    		this.maintainEvent(event);
    		count++;
    	}
    	return count;
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#maintainEvent(ProjEvent)
     */
    public int maintainEvent(ProjEvent _event) throws ProjectException {
    	if (_event == null) {
            this.msg = "Timesheet event object is invalid and could not be processed";
    		logger.log(Level.ERROR, this.msgArgs);
    		throw new ProjectException(this.msg);
    	}
    	
    	if (_event.getId() == 0) {
    		this.createEvent(_event);
    	}
    	else {
    		this.updateEvent(_event);
    	}
    	return _event.getId();
    }
    
    /**
     * Creates a timesheet event.
     * 
     * @param _event The event data.
     * @return The new event id.
     * @throws ProjectException
     */
    protected int createEvent(ProjEvent _event) throws ProjectException {
    	int rc = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	UserTimestamp ut = null;
    	
    	this.validateEvent(_event);
    	 try {
             ut = RMT2Utility.getUserTimeStamp(this.request);
             _event.setDateCreated(ut.getDateCreated());
             _event.setDateUpdated(ut.getDateCreated());
             _event.setUserId(ut.getLoginId());
             if (_event.getHours() <= 0) {
            	 _event.setNull("hours");
             }
             rc = dao.insertRow(_event, true);
             _event.setId(rc);
             return rc;
         }
         catch (DatabaseException e) {
             logger.log(Level.ERROR, e.getMessage());
             throw new ProjectException(e);
         }
         catch (SystemException e) {
        	 logger.log(Level.ERROR, e.getMessage());
             throw new ProjectException(e);
         }
    }

    /**
     * Processes the changes made to an existing event and applies to the database.
     * 
     * @param _event The event data.
     * @return The total number of rows effected by this transaction.
     * @throws ProjectException
     */
    protected int updateEvent(ProjEvent _event) throws ProjectException {
    	int rc = 0;
    	ProjEvent delta = null;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	UserTimestamp ut = null;
    	
    	// Get original event and apply changes to it
    	delta = this.findEvent(_event.getId());
    	if (delta == null) {
    		this.msg = "Update failed for " + _event.getEventDate().toString() + " event.   Original verison of the event does not exist.";
    		logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
    	}
    	delta.setHours(_event.getHours());
    	
    	// Validate delta event.
    	this.validateEvent(delta);
    	 try {
             ut = RMT2Utility.getUserTimeStamp(this.request);
             delta.setDateUpdated(ut.getDateCreated());
             delta.setUserId(ut.getLoginId());
             if (delta.getHours() <= 0) {
            	 delta.setNull("hours");
             }
             delta.addCriteria("Id", delta.getId());
             rc = dao.updateRow(delta);
             return rc;
         }
         catch (DatabaseException e) {
             logger.log(Level.ERROR, e.getMessage());
             throw new ProjectException(e);
         }
         catch (SystemException e) {
        	 logger.log(Level.ERROR, e.getMessage());
             throw new ProjectException(e);
         }
    }

    /**
     * Verifies that the event object contains proper data.
     * 
     * @param _event The event object that is to be validated.
     * @throws ProjectException if project task id and the event date does not have values.
     */
    protected void validateEvent(ProjEvent _event) throws ProjectException {
    	if (_event.getProjProjectTaskId() <= 0) {
    		this.msg = "A project-task id is required";
    		logger.log(Level.ERROR, this.msgArgs);
    		throw new ProjectException(this.msg);
    	}
    	if (_event.getEventDate() == null) {
    		this.msg ="Event date is required";
    		logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
    	}
    }

    /**
     * @see com.api.ProjectTimesheetApi#deleteTimesheet(int)
     */
    public int deleteTimesheet(int _timesheetId) throws ProjectException {
    	int rc = 0;
    	
    	// Delete all project tasks assoicated with _timesheetId
    	rc = this.deleteProjectTasks(_timesheetId);
    	if (rc <= 0) {
    		return 0;
    	}
    	
       // At this point it is okay to delete the timesheet.
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	ProjTimesheet ts = ProjectManagerFactory.createTimesheet();
    	ts.setId(_timesheetId);
    	try {
    	    ts.addCriteria("Id", ts.getId());
    		rc = dao.deleteRow(ts);	
    		return rc;
    	}
    	catch (DatabaseException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
        }
    }

    
    /**
     * @see com.api.ProjectTimesheetApi#deleteProjectTasks(int)
     */
    public int deleteProjectTasks(int _timesheetId) throws ProjectException {
        List list = null;
    	int count = 0;
    	
    	list = this.findProjectTaskByTimesheet(_timesheetId) ;
    	if (list == null) {
    		return 0;
    	}
    	for (int ndx = 0; ndx < list.size(); ndx++) {
    		ProjProjectTask pt = (ProjProjectTask) list.get(ndx);
    		this.deleteProjectTask(pt.getId());
    		count++;
    	}
    	return count;
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#deleteProjectTask(int)
     */
    public int deleteProjectTask(int _projectTaskId) throws ProjectException {
    	int rc = 0;
    	
    	// Delete project-task related events
    	rc = this.deleteEvents(_projectTaskId);
    	if (rc <= 0) {
    		return 0;
    	}
    	
    	// At this point it is okay to delete the project task.
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	ProjProjectTask pt = ProjectManagerFactory.createProjectTask();
    	pt.setId(_projectTaskId);
    	try {
    	    pt.addCriteria("Id", pt.getId());
    		rc = dao.deleteRow(pt);	
    		return rc;
    	}
    	catch (DatabaseException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
        }
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#deleteEvents(int)
     */
    public int deleteEvents(int _projectTaskId) throws ProjectException {
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	ProjEvent pe = ProjectManagerFactory.createProjEvent();
    	pe.addCriteria("ProjProjectTaskId", _projectTaskId);
    	try {
    		int rc = dao.deleteRow(pe);	
    		return rc;
    	}
    	catch (DatabaseException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
        }
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#deleteEvent(int)
     */
    public int deleteEvent(int _eventId) throws ProjectException {
    	int rc = 0;
        DaoApi dao = DataSourceFactory.createDao(this.connector);
    	ProjEvent pe = ProjectManagerFactory.createProjEvent();
    	pe.setId(_eventId);
    	try {
    	    pe.addCriteria("Id", pe.getId());
    		rc = dao.deleteRow(pe);	
    		return rc;
    	}
    	catch (DatabaseException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ProjectException(this.msg);
        }
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#calculateTimehseetBillableHours(int)
     */
    public double calculateTimehseetBillableHours(int timesheetId)  throws ProjectException {
        Object hours[] = this.findTimesheetHours(timesheetId);    
        if (hours == null) {
            return 0;
        }
        
        VwTimesheetHours vth = null;
        double totHrs = 0;
        int hrCount = hours.length;
        
        for (int ndx = 0; ndx < hrCount; ndx++) {
            vth = (VwTimesheetHours) hours[ndx];
            // Calcuate only billable hours
            if (vth.getBillable() == ProjectTimesheetApi.HOUR_TYPE_NONBILLABLE) {
                continue;
            }
            totHrs += vth.getHours();
        }
        return totHrs;
    }
    
    
    /**
     * @see com.api.ProjectTimesheetApi#calculateTimehseetNonBillableHours(int)
     */
    public double calculateTimehseetNonBillableHours(int timesheetId)  throws ProjectException {
        Object hours[] = this.findTimesheetHours(timesheetId);    
        if (hours == null) {
            return 0;
        }
        
        VwTimesheetHours vth = null;
        double totHrs = 0;
        int hrCount = hours.length;
        
        for (int ndx = 0; ndx < hrCount; ndx++) {
            vth = (VwTimesheetHours) hours[ndx];
            // Calcuate only non-billable hours
            if (vth.getBillable() == ProjectTimesheetApi.HOUR_TYPE_BILLABLE) {
                continue;
            }
            totHrs += vth.getHours();
        }
        return totHrs;
    }
    
    /**
     * @see com.api.ProjectTimesheetApi#calculateTimehseetHours(int)
     */
    public double calculateTimehseetHours(int timesheetId)  throws ProjectException {
        Object hours[] = this.findTimesheetHours(timesheetId);    
        if (hours == null) {
            return 0;
        }
        
        VwTimesheetHours vth = null;
        double totHrs = 0;
        int hrCount = hours.length;
        
        for (int ndx = 0; ndx < hrCount; ndx++) {
            vth = (VwTimesheetHours) hours[ndx];
            totHrs += vth.getHours();
        }
        return totHrs;
    }

    
    /**
     * @see com.api.ProjectTimesheetApi#calculateInvoice(int, double, double)
     */
    public double calculateInvoice(int timesheetId, double regRate, double otRate)  throws ProjectException {
        Object hours[] = this.findTimesheetHours(timesheetId);    
        if (hours == null) {
            return 0;
        }
        
        VwTimesheetHours vth = null;
        double invoiceAmt = 0;
        double totHrs = 0;
        int hrCount = hours.length;
        
        for (int ndx = 0; ndx < hrCount; ndx++) {
            vth = (VwTimesheetHours) hours[ndx];
            totHrs += vth.getHours();
            // Calcuate only billable hours
            if (vth.getBillable() == ProjectTimesheetApi.HOUR_TYPE_NONBILLABLE) {
                continue;
            }

            // Calculate invoice pay
            if (totHrs <= ProjectTimesheetApi.REG_PAY_HOURS) {
                // Calculate regular hours
                invoiceAmt += vth.getHours() * regRate;
            }
            else {
                // Calculate overtime hours
                double overtimeHrs = vth.getHours();
                if ( overtimeHrs > (totHrs - ProjectTimesheetApi.REG_PAY_HOURS) ) {
                    // Calculate remaining regular hours
                    invoiceAmt += (overtimeHrs - (totHrs - ProjectTimesheetApi.REG_PAY_HOURS)) * regRate;
                    // Apply overtime to a portion of the hours
                    invoiceAmt += ((totHrs - ProjectTimesheetApi.REG_PAY_HOURS) * otRate);
                }
                else {
                    invoiceAmt += (overtimeHrs * otRate);
                }
            } // end else
        } // end for
        return invoiceAmt;
    }
    
}

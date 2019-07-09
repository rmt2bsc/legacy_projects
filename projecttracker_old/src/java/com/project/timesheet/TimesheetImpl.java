package com.project.timesheet;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import com.api.DaoApi;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.security.pool.AppPropertyPool;

import com.bean.OrmBean;
import com.bean.ProjClient;
import com.bean.ProjEmployeeProject;
import com.bean.ProjEvent;
import com.bean.ProjProjectTask;
import com.bean.ProjTimesheet;
import com.bean.VwClientTimesheetSummary;
import com.bean.VwTimesheetEventList;
import com.bean.VwTimesheetHours;
import com.bean.VwTimesheetList;
import com.bean.VwTimesheetProjectTask;

import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.employee.EmployeeApi;
import com.employee.EmployeeException;
import com.employee.EmployeeFactory;

import com.project.ProjectException;

import com.project.setup.SetupFactory;

import com.util.RMT2Date;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * Implementation of TimesheetApi that manages an employee's timesheet activities.
 * 
 * @author appdev
 *
 */
class TimesheetImpl extends RdbmsDaoImpl implements TimesheetApi {
    private Logger logger;

    private RdbmsDaoQueryHelper daoHelper;
    
    private int currentProjectId;
    
    /**
     * Default Constructor
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected TimesheetImpl() throws DatabaseException, SystemException {
	super();
	logger = Logger.getLogger("TimesheetImpl");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public TimesheetImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
	logger = Logger.getLogger("TimesheetImpl");
    }

    /**
     * Constructor that uses dbConn and _request.
     * 
     * @param dbConn
     * @param _request
     * @throws DatabaseException
     * @throws SystemException
     */
    public TimesheetImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(dbConn);
	this.setRequest(request);
	logger = Logger.getLogger("TimesheetImpl");
    }

    /**
     * Summarizes timesheets by client and timesheet status.  Selection criteria 
     * can be added to filter the results.  When the selection criteria is null, 
     * the result set will contain a List of all timesheet summaries for all 
     * clients, by all timesheet statues.  By default, the result set is ordered by 
     * client name and time sheet status name in ascending order. 
     * 
     * @param criteria
     *          selection criteria that will filter result set beyond client 
     *          and timesheet status.  A value of null will return all timesheet 
     *          summaries for all clients, by all timesheet statues. 
     * @return List of {@link com.bean.VwClientTimesheetSummary VwClientTimesheetSummary}
     * @throws ProjectException
     */
    public List<VwClientTimesheetSummary> getTimesheetSummary(String criteria) throws ProjectException {
    	VwClientTimesheetSummary tsSum = TimesheetFactory.createVwClientTimesheetSummary();
    	if (criteria != null) {
    	    tsSum.addCustomCriteria(criteria);	
    	}
    	tsSum.addOrderBy(VwClientTimesheetSummary.PROP_NAME, VwClientTimesheetSummary.ORDERBY_ASCENDING);
    	tsSum.addOrderBy(VwClientTimesheetSummary.PROP_STATUSNAME, VwClientTimesheetSummary.ORDERBY_ASCENDING);
    	return this.daoHelper.retrieveList(tsSum);
    }
    
    /**
     * Gets base timesheet record.
     * 
     * @param timesheetId The id of the timesheet to retireve.
     * @return {@link ProjTimesheet}
     * @throws ProjectException
     */
    public ProjTimesheet findTimesheet(int timesheetId) throws ProjectException {
	ProjTimesheet obj = TimesheetFactory.createTimesheet();
	obj.addCriteria(ProjTimesheet.PROP_TIMESHEETID, timesheetId);
	try {
	    return (ProjTimesheet) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Gets extended timesheets using custom selection criteria.
     * 
     * @param criteria Customer selection criteria.
     * @return A List of {@link com.bean.VwTimesheetList VwTimesheetList}
     * @throws ProjectException
     */
    public List<VwTimesheetList> findTimesheetExt(String criteria) throws ProjectException {
	VwTimesheetList ts = TimesheetFactory.createVwTimesheet();
	ts.addCustomCriteria(criteria);
	ts.addOrderBy(VwTimesheetList.PROP_ENDPERIOD, VwTimesheetList.ORDERBY_DESCENDING);
	ts.addOrderBy(VwTimesheetList.PROP_TIMESHEETID, VwTimesheetList.ORDERBY_DESCENDING);
	try {
	    return this.daoHelper.retrieveList(ts);
	}
	catch (DatabaseException e) {
	    return null;
	}

    }

    /**
     * Gets extended base timesheet record.
     * 
     * @param timesheetId The id of the timesheet to retireve.
     * @return {@link VwTimesheetList}
     * @throws ProjectException
     */
    public VwTimesheetList findTimesheetExt(int timesheetId) throws ProjectException {
	VwTimesheetList obj = TimesheetFactory.createVwTimesheet();
	obj.addCriteria(VwTimesheetList.PROP_TIMESHEETID, timesheetId);
	try {
	    return (VwTimesheetList) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Gets one or more base timesheet objects using _clientId.
     * 
     * @param clientId The id of the client to retreive timesheets.
     * @return {@link ProjTimesheet}
     * @throws ProjectException
     */
    public List<ProjTimesheet> findTimesheetByClient(int clientId) throws ProjectException {
	ProjTimesheet obj = TimesheetFactory.createTimesheet();
	obj.addCriteria(ProjTimesheet.PROP_CLIENTID, clientId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Gets one or more extended timesheets records related to a clinet
     * 
     * @param clientId The id of the client to retrieve all related timesheets.
     * @return {@link VwTimesheetList}
     * @throws ProjectException
     */
    public List<VwTimesheetList> findTimesheetExtByClient(int clientId) throws ProjectException {
	VwTimesheetList obj = TimesheetFactory.createVwTimesheet();
	obj.addCriteria(VwTimesheetList.PROP_CLIENTID, clientId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Gets one or more extended timesheets records related to an employee
     * 
     * @param employeeId The id of the employee to retrieve all related timesheets.
     * @return {@link VwTimesheetList}
     * @throws ProjectException
     */
    public List<VwTimesheetList> findTimesheetExtByEmployee(int employeeId) throws ProjectException {
	VwTimesheetList obj = TimesheetFactory.createVwTimesheet();
	obj.addCriteria(VwTimesheetList.PROP_EMPID, employeeId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves a list of timesheet records which the id of each timesheet 
     * retrieved exist in <i>timesheetId</i> and its status equals <i>status</i>.
     * 
     * @param timesheetId 
     *          An array of String representing id's of the timesheets to obtain.
     * @param statusId 
     *          The status id of the timesheet to search.
     * @return A List of arbitrary objects.
     * @throws ProjectException
     */
    public List<VwTimesheetList> findTimesheetExtByStatus(String timesheetId[], int statusId) throws ProjectException {
	VwTimesheetList obj = TimesheetFactory.createVwTimesheet();
	obj.addCriteria(VwTimesheetList.PROP_TIMESHEETSTATUSID, statusId);
	obj.addInClause(VwTimesheetList.PROP_TIMESHEETID, timesheetId);
	obj.addOrderBy(VwTimesheetList.PROP_CLIENTNAME, OrmBean.ORDERBY_ASCENDING);
	obj.addOrderBy(VwTimesheetList.PROP_LASTNAME, OrmBean.ORDERBY_ASCENDING);
	obj.addOrderBy(VwTimesheetList.PROP_FIRSTNAME, OrmBean.ORDERBY_ASCENDING);
	obj.addOrderBy(VwTimesheetList.PROP_TIMESHEETID, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves timesheet hours base on the project and task using _timesheetId.
     * 
     * @param timesheetId  The id of the timesheet
     * @return An Object array of {@link VwTimesheetHours}
     * @throws ProjectException  If a database access error occurs.
     */
    public Object[] findTimesheetHours(int timesheetId) throws ProjectException {
	VwTimesheetHours vth = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	try {
	    vth = new VwTimesheetHours();
	    vth.addCriteria(VwTimesheetHours.PROP_TIMESHEETID, timesheetId);
	    return dao.retrieve(vth);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves a project task record
     * 
     * @param projectTaskId The id of the project-task record
     * @return {@link ProjProjectTask}
     * @throws ProjectException
     */
    public ProjProjectTask findProjectTask(int projectTaskId) throws ProjectException {
	ProjProjectTask obj = TimesheetFactory.createProjectTask();
	obj.addCriteria(ProjProjectTask.PROP_PROJECTTASKID, projectTaskId);
	try {
	    return (ProjProjectTask) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves an extended project task record
     * 
     * @param projectTaskId The id of the project-task record
     * @return {@link VwTimesheetProjectTask}
     * @throws ProjectException
     */
    public VwTimesheetProjectTask findProjectTaskExt(int projectTaskId) throws ProjectException {
	VwTimesheetProjectTask obj = TimesheetFactory.createVwTimesheetProjectTask();
	obj.addCriteria(VwTimesheetProjectTask.PROP_PROJECTTASKID, projectTaskId);
	try {
	    return (VwTimesheetProjectTask) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves one or more extended project task records by client
     * 
     * @param clientId The id of the client
     * @return A List of {@link VwTimesheetProjectTask}
     * @throws ProjectException
     */
    public List <VwTimesheetProjectTask> findProjectTaskExtByClient(int clientId) throws ProjectException {
	VwTimesheetProjectTask obj = TimesheetFactory.createVwTimesheetProjectTask();
	obj.addCriteria(VwTimesheetProjectTask.PROP_CLIENTID, clientId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves one or more extended project task records by timesheet
     * 
     * @param timesheetId The id of the timesheet
     * @return A List of {@link ProjProjectTask}
     * @throws ProjectException
     */
    public List <ProjProjectTask> findProjectTaskByTimesheet(int timesheetId) throws ProjectException {
	ProjProjectTask obj = TimesheetFactory.createProjectTask();
	obj.addCriteria(ProjProjectTask.PROP_TIMESHEETID, timesheetId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves one or more extended project task records by timesheet.  Result set is 
     * ordered by project name and task name in asending order, respectively.
     * 
     * @param timesheetId The id of the timesheet
     * @return A List of {@link VwTimesheetProjectTask}
     * @throws ProjectException
     */
    public List<VwTimesheetProjectTask>  findProjectTaskExtByTimesheet(int timesheetId) throws ProjectException {
	VwTimesheetProjectTask obj = TimesheetFactory.createVwTimesheetProjectTask();
	obj.addCriteria(VwTimesheetProjectTask.PROP_TIMESHEETID, timesheetId);
	obj.addOrderBy(VwTimesheetProjectTask.PROP_PROJECTNAME, VwTimesheetProjectTask.ORDERBY_ASCENDING);
	obj.addOrderBy(VwTimesheetProjectTask.PROP_TASKNAME, VwTimesheetProjectTask.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves one or more extended project task records by project
     * 
     * @param projectId The id of the timesheet
     * @return A List of {@link VwTimesheetProjectTask}
     * @throws ProjectException
     */
    public List <VwTimesheetProjectTask> findProjectTaskExtByProject(int projectId) throws ProjectException {
	VwTimesheetProjectTask obj = TimesheetFactory.createVwTimesheetProjectTask();
	obj.addCriteria(VwTimesheetProjectTask.PROP_PROJECTID, projectId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves one or more extended project task records by task
     * 
     * @param taskId The id of the task
     * @return A List of {@link VwTimesheetProjectTask}
     * @throws ProjectException
     */
    public List<VwTimesheetProjectTask>  findProjectTaskExtByTask(int taskId) throws ProjectException {
	VwTimesheetProjectTask obj = TimesheetFactory.createVwTimesheetProjectTask();
	obj.addCriteria(VwTimesheetProjectTask.PROP_TASKID, taskId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves one or more extended project task records by project and task
     * 
     * @param projectId The id of the project
     * @param taskId _taskId The id of the task
     * @return A List of {@link VwTimesheetProjectTask}
     * @throws ProjectException
     */
    public List<VwTimesheetProjectTask>  findProjectTaskExtByProjectTask(int projectId, int taskId) throws ProjectException {
	VwTimesheetProjectTask obj = TimesheetFactory.createVwTimesheetProjectTask();
	obj.addCriteria(VwTimesheetProjectTask.PROP_TASKID, taskId);
	obj.addCriteria(VwTimesheetProjectTask.PROP_PROJECTID, projectId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves a timesheet event record
     * 
     * @param eventId The id of the timesheet event.
     * @return {@link ProjEvent}
     * @throws ProjectException
     */
    public ProjEvent findEvent(int eventId) throws ProjectException {
	ProjEvent obj = TimesheetFactory.createProjEvent();
	obj.addCriteria(ProjEvent.PROP_EVENTID, eventId);
	try {
	    return (ProjEvent) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves extended timesheet event records
     * 
     * @param eventId The id of the timesheet event.
     * @return {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public VwTimesheetEventList findEventExt(int eventId) throws ProjectException {
	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	obj.addCriteria(VwTimesheetEventList.PROP_EVENTID, eventId);
	try {
	    return (VwTimesheetEventList) this.daoHelper.retrieveObject(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves an extended timesheet event records by event date
     * 
     * @param eventDate The event date to filter data
     * @return A List of {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public List<VwTimesheetEventList>  findEventExtByEventDate(Date eventDate) throws ProjectException {
	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	obj.addCriteria(VwTimesheetEventList.PROP_EVENTDATE, eventDate);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves extended timesheet event records that exist between a range of dates.
     * 
     * @param beginDate The beginning of the date range.
     * @param endDate The ending of the date range.
     * @return A List of {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public List<VwTimesheetEventList> findEventExtByEventDate(Date beginDate, Date endDate) throws ProjectException {
	String date1 = null;
	String date2 = null;
	String msg = null;

	if (beginDate != null && endDate != null) {
	    try {
		date1 = RMT2Date.formatDate(beginDate, "MM/dd/yyyy HH:mm:ss");
		date2 = RMT2Date.formatDate(endDate, "MM/dd/yyyy HH:mm:ss");
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

	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	String criteria = "event_date  between (\'" + date1 + "\' and \'" + date2 + "\')";
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves one or more timesheet event records using Project-Task
     * 
     * @param projectTaskId The id of the project-task
     * @return A List of {@link ProjEvent}
     * @throws ProjectException
     */
    public List <ProjEvent> findEventByProjectTask(int projectTaskId) throws ProjectException {
	ProjEvent obj = TimesheetFactory.createProjEvent();
	obj.addCriteria(ProjEvent.PROP_PROJECTTASKID, projectTaskId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves extended timesheet event records by Project-Task
     * 
     * @param projectTaskId The id of the project-task
     * @return A List of {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public List<VwTimesheetEventList> findEventExtByProjectTask(int projectTaskId) throws ProjectException {
	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	obj.addCriteria(VwTimesheetEventList.PROP_PROJECTTASKID, projectTaskId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves extended timesheet event records by client
     * 
     * @param clientId The id of the client
     * @return A List of {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public List<VwTimesheetEventList> findEventExtByClient(int clientId) throws ProjectException {
	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	obj.addCriteria(VwTimesheetEventList.PROP_CLIENTID, clientId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves extended timesheet event records by timesheet
     * 
     * @param timesheetId The id of the timesheet
     * @return A List of {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public List <VwTimesheetEventList> findEventExtByTimesheet(int timesheetId) throws ProjectException {
	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	obj.addCriteria(VwTimesheetEventList.PROP_TIMESHEETID, timesheetId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves extended timesheet event records by project
     * 
     * @param projectId The id of the project
     * @return A List of {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public List <VwTimesheetEventList> findEventExtByProject(int projectId) throws ProjectException {
	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	obj.addCriteria(VwTimesheetEventList.PROP_PROJECTID, projectId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Retrieves extended timesheet event records by task
     * 
     * @param taskId The id of the task
     * @return A List of {@link VwTimesheetEventList}
     * @throws ProjectException
     */
    public List<VwTimesheetEventList>  findEventExtByTask(int taskId) throws ProjectException {
	VwTimesheetEventList obj = TimesheetFactory.createVwTimesheetEventList();
	obj.addCriteria(VwTimesheetEventList.PROP_TASKID, taskId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (Exception e) {
	    throw new ProjectException(e);
	}
    }

    /**
     * Obtains the week days as java.util.Date objects for a particular time period.
     * 
     * @param timePeriod The time target period. 
     * @return An array of java.util.Date objects.
     */
    public Date[] getTimePeriodWeekDates(Date timePeriod) {
	try {
	    return RMT2Date.getWeekDates(timePeriod);
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Gets the available ending time period as Date objects.   Each date will represent 
     * the last day of the time period...in most cases Saturday.  The number of dates 
     * return is governed by AVAIL_ENDPERIODS_MAX
     * 
     * @return An String array of dates in the format MM/dd/yyyy.
     */
    public String[] getAvailableEndingPeriods() {
	String periods[] = null;
	Date dow[] = null;
	Date curEndDate = null;
	Calendar calTemp = Calendar.getInstance();
	int maxPeriods = 0;
	int weekDecrement = -7;

	try {
	    dow = RMT2Date.getWeekDates();
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
		    periods[ndx] = RMT2Date.formatDate(curEndDate, "MM/dd/yyyy");
		    logger.log(Level.DEBUG, periods[ndx]);
		    continue;
		}

		calTemp.add(Calendar.DATE, (weekDecrement));
		periods[ndx] = RMT2Date.formatDate(calTemp.getTime(), "MM/dd/yyyy");
		logger.log(Level.DEBUG, periods[ndx]);
	    }
	    return periods;
	}
	catch (SystemException e) {
	    return null;

	}

    }

    /**
     * Get value that indicates the total number of numeircal digits that composes the 
     * timesheet's displayValue.
     * 
     * @return Maximum size as an int.  Returns 5 if a problem occurred converting the 
     *         property value to a number.
     * @throws ProjectException Prooperty file access errors.
     */
    public int getMaxDisplayValueDigits() throws ProjectException {
	int sheetIdSize = 0;
	String temp = null;
	try {
	    temp = AppPropertyPool.getProperty("sheet_id_size");
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
	    temp = AppPropertyPool.getProperty("max_endperiods");
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
     * Creates or updates a timesheet record by applying data changes to the database.
     * 
     * @param timesheet Timesheet data that is to be saved
     * @return The id of the timesheet applied to the database.
     * @throws ProjectException
     */
    public int maintainTimesheet(ProjTimesheet timesheet) throws ProjectException {
	int rc = 0;

	if (timesheet == null) {
	    this.msg = "Base timesheet object is invalid and could not be processed";
	    logger.log(Level.ERROR, this.msgArgs);
	    throw new ProjectException(this.msg);
	}

	if (timesheet.getTimesheetId() == 0) {
	    rc = this.createTimesheet(timesheet);
	}
	else {
	    rc = this.updateTimesheet(timesheet);
	}
	return rc;
    }

    /**
     * Creates a timesheet and saves changes to the database.
     * 
     * @param ts The timesheet data.
     * @return New timesheet id.
     * @throws ProjectException if client id is not provided, or if employee id is not provided, or a database access error.
     */
    protected int createTimesheet(ProjTimesheet ts) throws ProjectException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;
	ProjClient client = null;
	//	List list = null;

	
	try {
	    this.validateTimesheet(ts);
	    ut = RMT2Date.getUserTimeStamp(this.request);

	    // If client does not exist in the Proj_Client table then add
	    client = SetupFactory.createClient();
	    client.addCriteria(ProjClient.PROP_CLIENTID, ts.getClientId());
	    client = (ProjClient) this.daoHelper.retrieveObject(client);
	    if (client == null) {
		client = SetupFactory.createClient();
		client.setClientId(ts.getClientId());
		client.setDateCreated(ut.getDateCreated());
		client.setDateUpdated(ut.getDateCreated());
		client.setUserId(ut.getLoginId());
		dao.insertRow(client, false);
	    }

	    // Add base timesheet
	    if (ts.getProjId() == 0) {
		ts.setNull(ProjTimesheet.PROP_PROJID);
	    }
	    ts.setDateCreated(ut.getDateCreated());
	    ts.setDateUpdated(ut.getDateCreated());
	    ts.setUserId(ut.getLoginId());
	    ts.setIpCreated(ut.getIpAddr());
	    ts.setIpUpdated(ut.getIpAddr());
	    rc = dao.insertRow(ts, true);
	    ts.setTimesheetId(rc);

	    // Set the defaultDisplay property since its value is primary key based, and the primary key's value is generated during previous insert
	    ts.setDisplayValue(RMT2String.padInt(rc, this.getMaxDisplayValueDigits(), RMT2String.PAD_LEADING));
	    ts.setTimesheetId(rc);
	    ts.addCriteria(ProjTimesheet.PROP_TIMESHEETID, ts.getTimesheetId());
	    dao.updateRow(ts);

	    return rc;
	}
	catch (DatabaseException e) {
	    this.msg = "Unable to create timesheet due to database errors";
	    logger.error(this.msg);
	    throw new ProjectException(this.msg, e);
	}
	catch (SystemException e) {
	    this.msg = "Unable to create timesheet due to general system errors";
	    logger.error(this.msg);
            throw new ProjectException(this.msg, e);
	}
	catch (InvalidTimesheetException e) {
	    this.msg = "Unable to create timesheet due to validation errors";
	    logger.error(this.msg);
            throw new ProjectException(this.msg, e);
	}
    }

    /**
     * Applies changes to an existing timesheet to the database.
     * 
     * @param ts The m odified timesheet.
     * @return The existing timesheet id.
     * @throws ProjectException 
     *           If client id is not provided, or if employee id is not provided, or 
     *           a database access error.
     */
    protected int updateTimesheet(ProjTimesheet ts) throws ProjectException {
	UserTimestamp ut = null;

	
	try {
	    this.validateTimesheet(ts);
	    if (ts.getProjId() == 0) {
		ts.setNull(ProjTimesheet.PROP_PROJID);
	    }
	    else {
		ts.removeNull(ProjTimesheet.PROP_PROJID);
	    }
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    ts.setDateUpdated(ut.getDateCreated());
	    ts.setUserId(ut.getLoginId());
	    ts.setIpUpdated(ut.getIpAddr());
	    if (ts.getDisplayValue() == null) {
		ts.setDisplayValue(RMT2String.padInt(ts.getTimesheetId(), this.getMaxDisplayValueDigits(), RMT2String.PAD_LEADING));
	    }
	    ts.addCriteria(ProjTimesheet.PROP_TIMESHEETID, ts.getTimesheetId());
	    this.updateRow(ts);
	    return ts.getTimesheetId();
	}
	catch (DatabaseException e) {
            this.msg = "Unable to update timesheet due to database errors";
            logger.error(this.msg);
            throw new ProjectException(this.msg, e);
        }
        catch (SystemException e) {
            this.msg = "Unable to update timesheet due to general system errors";
            logger.error(this.msg);
            throw new ProjectException(this.msg, e);
        }
        catch (InvalidTimesheetException e) {
            this.msg = "Unable to update timesheet due to validation errors";
            logger.error(this.msg);
            throw new ProjectException(this.msg, e);
        }
    }

   

    /**
     * Creates a project-task record by applying data changes to the database.   
     * Modifications to an existing ProjectTask record is not allowed.
     * 
     * @param projectTask Project-task data that is to be saved
     * @return The id of the project-task applied to the database
     * @throws ProjectException
     */
    public int maintainProjectTask(ProjProjectTask projectTask) throws ProjectException {
	int rc = 0;

	if (projectTask == null) {
	    this.msg = "Project-Task object is invalid and could not be processed";
	    logger.log(Level.ERROR, this.msgArgs);
	    throw new ProjectException(this.msg);
	}

	if (projectTask.getProjectTaskId() == 0) {
	    rc = this.createProjectTask(projectTask);
	    projectTask.setProjectTaskId(rc);
	}
	return projectTask.getProjectTaskId();
    }

    /**
     * Creates a project-task record and saves changes to the database.
     * 
     * @param pt Project Task data
     * @return The id of the project task.
     * @throws ProjectException if a validation error occurs.
     */
    protected int createProjectTask(ProjProjectTask pt) throws ProjectException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	this.validateProjectTask(pt);
	try {
	    rc = dao.insertRow(pt, true);
	    pt.setProjectTaskId(rc);
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
     * @param pt The project-task record that is to be validated.
     * @throws ProjectException if the values for timesheet, project, and/or task are not set.
     */
    protected void validateProjectTask(ProjProjectTask pt) throws ProjectException {
	if (pt.getProjId() <= 0) {
	    this.msg = "Proejct Id is required when creating Project-Task";
	    logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}
	if (pt.getTaskId() <= 0) {
	    this.msg = "Task Id is required when creating Project-Task";
	    logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}
    }

    /**
     * Creates or modifies all events belonging to a Project-Task group.
     * 
     * @param projectTaskId The id of the project-task group.
     * @param events An ArrayList of (@link ProjEvent} objects to process
     * @return Total number of events processed.
     * @throws ProjectException
     */
    public int maintainEvent(int projectTaskId, List events) throws ProjectException {
	int count = 0;

	if (projectTaskId <= 0) {
	    this.msg = "A project-task id must be valid in order to process a multiple event transaction";
	    logger.log(Level.ERROR, this.msgArgs);
	    throw new ProjectException(this.msg);
	}
	if (this.findProjectTask(projectTaskId) == null) {
	    this.msg = "A project-task id must exist in the system in order to process a multiple event transaction";
	    logger.log(Level.ERROR, this.msgArgs);
	    throw new ProjectException(this.msg);
	}
	if (events == null) {
	    this.msg = "The collection containing the lists of timesheet events must be valid in order to process a mulitiple event transaction";
	    logger.log(Level.ERROR, this.msgArgs);
	    throw new ProjectException(this.msg);
	}

	// Begin processing each item in the event ArrayList
	ProjEvent event = null;
	for (int ndx = 0; ndx < events.size(); ndx++) {
	    event = (ProjEvent) events.get(ndx);
	    event.setProjectTaskId(projectTaskId);
	    this.maintainEvent(event);
	    count++;
	}
	return count;
    }

    /**
     * Creates or updates a timesheet event record by applying data changes to the database.
     * 
     * @param event The event related data that is to be saved.
     * @return The id of the timesheet event applied to the database.
     * @throws ProjectException
     */
    public int maintainEvent(ProjEvent event) throws ProjectException {
	if (event == null) {
	    this.msg = "Timesheet event object is invalid and could not be processed";
	    logger.log(Level.ERROR, this.msgArgs);
	    throw new ProjectException(this.msg);
	}

	if (event.getEventId() == 0) {
	    this.createEvent(event);
	}
	else {
	    this.updateEvent(event);
	}
	return event.getEventId();
    }

    /**
     * Creates a timesheet event.
     * 
     * @param event The event data.
     * @return The new event id.
     * @throws ProjectException
     */
    protected int createEvent(ProjEvent event) throws ProjectException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	this.validateEvent(event);
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    event.setDateCreated(ut.getDateCreated());
	    event.setDateUpdated(ut.getDateCreated());
	    event.setUserId(ut.getLoginId());
	    if (event.getHours() <= 0) {
		event.setNull("hours");
	    }
	    rc = dao.insertRow(event, true);
	    event.setEventId(rc);
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
     * @param event The event data.
     * @return The total number of rows effected by this transaction.
     * @throws ProjectException
     */
    protected int updateEvent(ProjEvent event) throws ProjectException {
	int rc = 0;
	ProjEvent delta = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	// Get original event and apply changes to it
	delta = this.findEvent(event.getEventId());
	if (delta == null) {
	    this.msg = "Update failed for " + event.getEventDate().toString() + " event.   Original verison of the event does not exist.";
	    logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}
	delta.setHours(event.getHours());

	// Validate delta event.
	this.validateEvent(delta);
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    delta.setDateUpdated(ut.getDateCreated());
	    delta.setUserId(ut.getLoginId());
	    if (delta.getHours() <= 0) {
		delta.setNull("hours");
	    }
	    delta.addCriteria(ProjEvent.PROP_EVENTID, delta.getEventId());
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
     * @param event The event object that is to be validated.
     * @throws ProjectException if project task id and the event date does not have values.
     */
    protected void validateEvent(ProjEvent event) throws ProjectException {
	if (event.getProjectTaskId() <= 0) {
	    this.msg = "A project-task id is required";
	    logger.log(Level.ERROR, this.msgArgs);
	    throw new ProjectException(this.msg);
	}
	if (event.getEventDate() == null) {
	    this.msg = "Event date is required";
	    logger.log(Level.ERROR, this.msg);
	    throw new ProjectException(this.msg);
	}
    }

    /**
     * Deletes a single timesheet from the database using its primary key.
     * 
     * @param timesheetId The id of the target timesheet.
     * @return The number of rows effected.
     * @throws ProjectException if a database access error occurs.
     */
    public int deleteTimesheet(int timesheetId) throws ProjectException {
	int rc = 0;

	// Delete all project tasks assoicated with _timesheetId
	rc = this.deleteProjectTasks(timesheetId);
	if (rc <= 0) {
	    return 0;
	}

	// At this point it is okay to delete the timesheet.
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	ProjTimesheet ts = TimesheetFactory.createTimesheet();
	ts.setTimesheetId(timesheetId);
	try {
	    ts.addCriteria(ProjTimesheet.PROP_TIMESHEETID, ts.getTimesheetId());
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
     * Deletes all project-tasks that are related to a particular timesheet.
     * 
     * @param timesheetId The id of the timesheet to delete project-tasks
     * @return The number of project-tasks deleted.
     * @throws ProjectException if a database access error occurs.
     */
    public int deleteProjectTasks(int timesheetId) throws ProjectException {
	List<ProjProjectTask>  list = null;
	int count = 0;

	list = this.findProjectTaskByTimesheet(timesheetId);
	if (list == null) {
	    return 0;
	}
	for (int ndx = 0; ndx < list.size(); ndx++) {
	    ProjProjectTask pt =  list.get(ndx);
	    this.deleteProjectTask(pt.getProjectTaskId());
	    count++;
	}
	return count;
    }

    /**
     * Deletes a single project-task by its primary key.
     * 
     * @param projectTaskId The id of the project-task to delete.
     * @return The total number of rows effected.
     * @throws ProjectException if a database access error occurs.
     */
    public int deleteProjectTask(int projectTaskId) throws ProjectException {
	int rc = 0;

	// Delete project-task related events
	rc = this.deleteEvents(projectTaskId);
	if (rc <= 0) {
	    return 0;
	}

	// At this point it is okay to delete the project task.
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	ProjProjectTask pt = TimesheetFactory.createProjectTask();
	pt.setProjectTaskId(projectTaskId);
	try {
	    pt.addCriteria(ProjProjectTask.PROP_PROJECTTASKID, pt.getProjectTaskId());
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
     * Deletes all events that are related to a project-task.
     * 
     * @param projectTaskId The id of the project-task to delete all events
     * @return The total number of rows effected.
     * @throws ProjectException if a database access error occurs.
     */
    public int deleteEvents(int projectTaskId) throws ProjectException {
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	ProjEvent pe = TimesheetFactory.createProjEvent();
	pe.addCriteria(ProjEvent.PROP_PROJECTTASKID, projectTaskId);
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
     * Delete a single event using its primary key.
     * 
     * param _eventId The id of the event to delete.
     * @return The total number of rows effected.
     * @throws ProjectException if a database access error occurs.
     */
    public int deleteEvent(int eventId) throws ProjectException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	ProjEvent pe = TimesheetFactory.createProjEvent();
	pe.setEventId(eventId);
	try {
	    pe.addCriteria(ProjEvent.PROP_EVENTID, pe.getEventId());
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
     * Sums billable hours for a given timesheet.
     * 
     * @param timesheetId The id of the timesheet
     * @return Total billable hours
     * @throws ProjectException Problem occurred gathering the timesheet data.
     */
    public double calculateTimehseetBillableHours(int timesheetId) throws ProjectException {
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
	    if (vth.getBillable() == TimesheetApi.HOUR_TYPE_NONBILLABLE) {
		continue;
	    }
	    totHrs += vth.getHours();
	}
	return totHrs;
    }

    /**
     * Sums non-billable hours for a given timesheet.
     * 
     * @param timesheetId The id of the timesheet
     * @return Total non-billable hours
     * @throws ProjectException Problem occurred gathering the timesheet data.
     */
    public double calculateTimehseetNonBillableHours(int timesheetId) throws ProjectException {
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
	    if (vth.getBillable() == TimesheetApi.HOUR_TYPE_BILLABLE) {
		continue;
	    }
	    totHrs += vth.getHours();
	}
	return totHrs;
    }

    /**
     * Sums billable and non-billable hours for a given timesheet.
     * 
     * @param timesheetId The id of the timesheet
     * @return Total of billable and non-billable hours
     * @throws ProjectException Problem occurred gathering the timesheet data.
     */
    public double calculateTimehseetHours(int timesheetId) throws ProjectException {
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
     * Calculates the invoice amount of the timesheet using regRate and otRate to determine regular pay and overtime pay , respectively.
     * 
     * @param timesheetId The id of the timesheet that is to be calculated.
     * @param regRate The employee's bill rate.
     * @param otRate The employee's overtime bill rate.
     * @return The invoice amount.
     * @throws ProjectException Problem occurred gathering the timesheet data.
     */
//    public double calculateInvoice(int timesheetId, double regRate, double otRate) throws ProjectException {
    public double calculateInvoice(int timesheetId) throws ProjectException {
	Object hours[] = this.findTimesheetHours(timesheetId);
	if (hours == null) {
	    return 0;
	}

	ProjEmployeeProject pep = null;
	VwTimesheetHours vth = null;
	double invoiceAmt = 0;
	double totHrs = 0;
	int hrCount = hours.length;

	for (int ndx = 0; ndx < hrCount; ndx++) {
	    vth = (VwTimesheetHours) hours[ndx];
	    
	    // Get employee bill rates for target project
	    if (ndx == 0) {
		EmployeeApi empApi = EmployeeFactory.createApi(this.connector);
		try {
		    pep = (ProjEmployeeProject) empApi.findProject(vth.getEmployeeId(), vth.getProjectId());
		}
		catch (EmployeeException e) {
		    throw new ProjectException(e);
		}
	    }
	    
	    totHrs += vth.getHours();
	    // Calcuate only billable hours
	    if (vth.getBillable() == TimesheetApi.HOUR_TYPE_NONBILLABLE) {
		continue;
	    }

	    // Calculate invoice pay
	    if (totHrs <= TimesheetApi.REG_PAY_HOURS) {
		// Calculate regular hours
		invoiceAmt += vth.getHours() * pep.getHourlyRate();
	    }
	    else {
		// Calculate overtime hours
		double overtimeHrs = vth.getHours();
		if (overtimeHrs > (totHrs - TimesheetApi.REG_PAY_HOURS)) {
		    // Calculate remaining regular hours
		    invoiceAmt += (overtimeHrs - (totHrs - TimesheetApi.REG_PAY_HOURS)) * pep.getHourlyRate();
		    // Apply overtime to a portion of the hours
		    invoiceAmt += ((totHrs - TimesheetApi.REG_PAY_HOURS) * pep.getHourlyOverRate());
		}
		else {
		    invoiceAmt += (overtimeHrs * pep.getHourlyOverRate());
		}
	    } // end else
	} // end for
	return invoiceAmt;
    }


    /**
     * Verifies that a client and an employee is associated with the timesheet.
     * 
     * @param ts The timesheet to be validated.
     * @throws InvalidTimesheetException 
     *           If the client id and/or the employee id is found not to be associated 
     *           with the timesheet.
     */
    public void validateTimesheet(ProjTimesheet ts) throws InvalidTimesheetException {
        if (ts == null) {
            this.msg = "The base timesheet instance is invalid or null.";
            logger.error(this.msg);
            throw new InvalidTimesheetException(this.msg);
        }
        
        if (ts.getClientId() <= 0) {
            this.msg = "Timesheet must be associated with a client";
            logger.log(Level.ERROR, this.msg);
            throw new InvalidTimesheetException(this.msg);
        }
        if (ts.getEmpId() <= 0) {
            this.msg = "Timesheet must be associated with an employee";
            logger.log(Level.ERROR, this.msg);
            throw new InvalidTimesheetException(this.msg);
        }
    }
    
    
    /* (non-Javadoc)
     * @see com.project.timesheet.TimesheetApi#validateTask(com.bean.VwTimesheetProjectTask)
     */
    public void validateTask(VwTimesheetProjectTask task) throws InvalidTimesheetTaskException {
        if (task == null) {
            this.msg = "Thimesheet task is invalid or null.";
            logger.error(this.msg);
            throw new InvalidTimesheetTaskException(this.msg);
        }
        
        if (task.getProjectId() == 0) {
            this.msg = "Timesheet is required to have an assigned project";
            logger.error(this.msg);
            throw new InvalidTimesheetTaskException(this.msg);
        }
        
        if (this.currentProjectId == 0) {
            this.msg = "Timesheet API is required to have a master project id assigned so that each task's project id can be compared";
            logger.error(this.msg);
            throw new InvalidTimesheetTaskException(this.msg);
        }
        
        if (task.getProjectId() != this.currentProjectId) {
            this.msg = "Found a conflicting project assinged to timesheet task named, " + task.getTaskName() + ".  Be sure that all tasks are associated with the same project for the timesheet";
            logger.error(this.msg);
            throw new InvalidTimesheetTaskException(this.msg);
        }
        
    }

    public int getCurrentProjectId() {
        return this.currentProjectId;
    }

    public void setCurrentProjectId(int projId) {
        this.currentProjectId  = projId;
    }

  
}

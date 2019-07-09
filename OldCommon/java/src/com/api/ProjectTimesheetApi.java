package com.api;

import java.util.List;
import java.util.Date;

import com.api.db.DatabaseException;

import com.bean.ProjTimesheet;
import com.bean.ProjProjectTask;
import com.bean.ProjEvent;
import com.bean.VwTimesheetList;
import com.bean.VwTimesheetProjectTask;
import com.bean.VwTimesheetEventList;

import com.util.ProjectException;

/**
 * The ProjectTimesheetApi interface defines methods that are responsible 
 * for managing the time an employee spends working on projects as well as 
 * the life cycle of the timesheet itself.    Also included in this interface 
 * are methods which function to provide various alternatives for querying 
 * the database for tmesheet data.   Data can be queried at various levels 
 * such as the client, timesheet, project, or task.
 *  
 * @author Roy Terrell
 *
 */
public interface ProjectTimesheetApi extends BaseDataSource {
    
    /**  A constant indicating the limit on regular hours per week. */
    public static final int REG_PAY_HOURS = 40;
    /** A constant indicating that the hour amount is billable */
    public static final int HOUR_TYPE_BILLABLE = 1;
    /** A constant indicating that the hour amount is not billable */
    public static final int HOUR_TYPE_NONBILLABLE = 0;

   /**
    * Retrieves pertaining to the datasource that ormBean is associated with.   User is 
    * expected to setup the appropriate selection and ordering criteria from within ormBean.
    * 
    * @param ormBean The ORM bean that is used to obtain data from the database.
    * @return One or more ormBean objects.
    * @throws DatabaseException A database access error occurs.
    */
    Object[] findData(Object ormBean) throws DatabaseException;

   
   /**
    * Gets base timesheet record.
    * 
    * @param _timesheetId The id of the timesheet to retireve.
    * @return {@link ProjTimesheet}
    * @throws ProjectException
    */
   ProjTimesheet findTimesheet(int _timesheetId) throws ProjectException;
   
   /**
    * Gets one or more base timesheet objects using _clientId.
    * 
    * @param _clientId The id of the client to retreive timesheets.
    * @return An array of Object
    * @throws ProjectException
    */
   Object[]  findTimesheetByClient(int _clientId) throws ProjectException;
   
   /**
    * Gets extended base timesheet record.
    * 
    * @param _timesheetId The id of the timesheet to retireve.
    * @return {@link VwTimesheetList}
    * @throws ProjectException
    */
   VwTimesheetList findTimesheetExt(int _timesheetId) throws ProjectException;
   
   /**
    * Retrieves timesheet hours base on the project and task using _timesheetId.
    * 
    * @param _timesheetId  The id of the timesheet
    * @return Object[]
    * @throws ProjectException  If a database access error occurs.
    */
   Object[] findTimesheetHours(int _timesheetId) throws ProjectException;
   
   /**
    * Gets one or more extended timesheets records related to a clinet
    * 
    * @param _clientId The id of the client to retrieve all related timesheets.
    * @return An ArrayList of {@link VwTimesheetList}
    * @throws ProjectException
    */
   List findTimesheetExtByClient(int _clientId) throws ProjectException;
   
   /**
    * Gets one or more extended timesheets records related to an employee
    * 
    * @param _employeeId The id of the employee to retrieve all related timesheets.
    * @return An ArrayList of {@link VwTimesheetList}
    * @throws ProjectException
    */
   List findTimesheetExtByEmployee(int _employeeId) throws ProjectException;
   
   
   /**
    * Retrieves a project task record
    * 
    * @param _projectTaskId The id of the project-task record
    * @return {@link ProjProjectTask}
    * @throws ProjectException
    */
   ProjProjectTask findProjectTask(int _projectTaskId) throws ProjectException;
   
   /**
    * Retrieves an extended project task record
    * 
    * @param _projectTaskId The id of the project-task record
    * @return {@link VwTimesheetProjectTask}
    * @throws ProjectException
    */
   VwTimesheetProjectTask findProjectTaskExt(int _projectTaskId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by client
    * 
    * @param _clientId The id of the client
    * @return An ArrayList of {@link VwTimesheetProjectTask}
    * @throws ProjectException
    */
   List findProjectTaskExtByClient(int _clientId) throws ProjectException;
   
   
   /**
    * Retrieves all project-tasks that are related to a particular timesheet.
    * 
    * @param _timesheetId The id of the timesheet to retrieve project-tasks
    * @return ArrayList of {@link ProjProjectTask} objects
    * @throws ProjectException
    */
   List findProjectTaskByTimesheet(int _timesheetId) throws ProjectException;
   
   
   /**
    * Retrieves one or more extended project task records by timesheet
    * 
    * @param _timesheetId The id of the timesheet
    * @return An ArrayList of {@link VwTimesheetProjectTask}
    * @throws ProjectException
    */
   List findProjectTaskExtByTimesheet(int _timesheetId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by project
    * 
    * @param _projectId The id of the timesheet
    * @return An ArrayList of {@link VwTimesheetProjectTask}
    * @throws ProjectException
    */
   List findProjectTaskExtByProject(int _projectId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by task
    * 
    * @param _taskId The id of the task
    * @return An ArrayList of {@link VwTimesheetProjectTask}
    * @throws ProjectException
    */
   List findProjectTaskExtByTask(int _taskId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by project and task
    * 
    * @param _projectId The id of the project
    * @param _taskId _taskId The id of the task
    * @return An ArrayList of {@link VwTimesheetProjectTask}
    * @throws ProjectException
    */
   List findProjectTaskExtByProjectTask(int _projectId, int _taskId) throws ProjectException;
   

   /**
    * Retrieves a timesheet event record
    * 
    * @param _eventId The id of the timesheet event.
    * @return {@link ProjEvent}
    * @throws ProjectException
    */
   ProjEvent findEvent(int _eventId) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records
    * 
    * @param _eventId The id of the timesheet event.
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   VwTimesheetEventList findEventExt(int _eventId) throws ProjectException;
   
   /**
    * Retrieves an extended timesheet event records by event date
    * 
    * @param _eventIDate The event date to filter data
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   List findEventExtByEventDate(Date _eventIDate) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records by beginning and ending event dates
    * 
    * @param _beginDate The beginning of the date range.
    * @param _endDate The ending of the date range.
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   List findEventExtByEventDate(Date _beginDate, Date _endDate) throws ProjectException;
   
   
   /**
    * Retrieves one or more timesheet event records using Project-Task
    * 
    * @param _projectTaskId The id of the project-task
    * @return An ArrayList {@link ProjEvent} objects
    * @throws ProjectException
    */
   List findEventByProjectTask(int _projectTaskId) throws ProjectException;
   
   
   /**
    * Retrieves extended timesheet event records by Project-Task
    * 
    * @param _projectTaskId The id of the project-task
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   List findEventExtByProjectTask(int _projectTaskId) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records by client
    * 
    * @param _clientId The id of the client
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   List findEventExtByClient(int _clientId) throws ProjectException;
   
   
   
   /**
    * Retrieves extended timesheet event records by timesheet
    * 
    * @param _timesheetId The id of the timesheet
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   List findEventExtByTimesheet(int _timesheetId) throws ProjectException;

   /**
    * Retrieves extended timesheet event records by project
    * 
    * @param _projectId The id of the project
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   List findEventExtByProject(int _projectId) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records by task
    * 
    * @param _taskId The id of the task
    * @return An ArrayList {@link VwTimesheetEventList}
    * @throws ProjectException
    */
   List findEventExtByTask(int _taskId) throws ProjectException;
   
   /**
    * Obtains the week days as java.util.Date objects for a particular time period.
    * 
    * @param timePeriod The time target period. 
    * @return An array of java.util.Date objects.
    */
   Date[] getTimePeriodWeekDates(Date _timePeriod) ;
   
   /**
    * Gets the available ending time period as Date objects.   Each date will represent the last day of the time period...in most cases Saturday.
    * The number of dates return is governed by AVAIL_ENDPERIODS_MAX
    * 
    * @return An String array of dates in the format MM/dd/yyyy.
    */
   String[] getAvailableEndingPeriods();
   
  /**
   * Creates or updates a timesheet record by apllying data changes to the database.
   * 
   * @param _timesheet Timesheet data that is to be saved
   * @return The id of the timesheet applied to the database.
   * @throws ProjectException
   */
   int maintainTimesheet(ProjTimesheet _timesheet) throws ProjectException;
     
   /**
    * Creates a project-task record by applying data changes to the database.   Modifications to an existing 
    * ProjectTask record is not allowed.
    * 
    * @param _projectTask Project-task data that is to be saved
    * @return The id of the project-task applied to the database
    * @throws ProjectException
    */
   int maintainProjectTask(ProjProjectTask _projectTask) throws ProjectException;
      
   /**
    * Creates or modifies all events belonging to a Project-Task group.
    * 
    * @param _projectTaskId The id of the project-task group.
    * @param _events An ArrayList of (@link ProjEvent} objects to process
    * @return Total number of events processed.
    * @throws ProjectException
    */
   int maintainEvent(int _projectTaskId, List _events) throws ProjectException;
   
   
   /**
    * Creates or updates a timesheet event record by applying data changes to the database.
    * 
    * @param _event The event related data that is to be saved.
    * @return The id of the timesheet event applied to the database.
    * @throws ProjectException
    */
   int maintainEvent(ProjEvent _event) throws ProjectException;

   /**
    * Deletes a single timesheet from the database using its primary key.
    * 
    * @param _timesheetId The id of the target timesheet.
    * @return The number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteTimesheet(int _timesheetId) throws ProjectException;
   
   /**
    * Deletes all project-tasks that are related to a particular timesheet.
    * 
    * @param _timesheetId The id of the timesheet to delete project-tasks
    * @return The number of project-tasks deleted.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteProjectTasks(int _timesheetId) throws ProjectException;
   
   /**
    * Deletes a single project-task by its primary key.
    * 
    * @param _projectTaskId The id of the project-task to delete.
    * @return The total number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteProjectTask(int _projectTaskId) throws ProjectException;
   
   /**
    * Deletes all project events that are related to a project-task.
    * 
    * @param _projectTaskId The id of the project-task to delete all events
    * @return The total number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteEvents(int _projectTaskId) throws ProjectException;
   
   /**
    * Delete a single event using its primary key.
    * 
    * param _eventId The id of the event to delete.
    * @return The total number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteEvent(int _eventId) throws ProjectException;
   
   /**
    * Get value that indicates the total number of numeircal digits that composes the timesheet's displayValue.
    * 
    * @return Maximum size as an int.  Returns 5 if a problem occurred converting the property value to a number.
    * @throws ProjectException Prooperty file access errors.
    */
   int getMaxDisplayValueDigits() throws ProjectException;

   /**
    * Calculates the invoice amount of the timesheet using regRate and otRate to determine regular pay and overtime pay , respectively.
    * 
    * @param timesheetId The id of the timesheet that is to be calculated.
    * @param regRate The employee's bill rate.
    * @param otRate The employee's overtime bill rate.
    * @return The invoice amount.
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateInvoice(int timesheetId, double regRate, double otRate)  throws ProjectException;
   
   /**
    * Sums billable hours for a given timesheet.
    * 
    * @param timesheetId The id of the timesheet
    * @return Total billable hours
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateTimehseetBillableHours(int timesheetId)  throws ProjectException;
   
   /**
    * Sums non-billable hours for a given timesheet.
    * 
    * @param timesheetId The id of the timesheet
    * @return Total non-billable hours
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateTimehseetNonBillableHours(int timesheetId)  throws ProjectException;
   
   /**
    * Sums billable and non-billable hours for a given timesheet.
    * 
    * @param timesheetId The id of the timesheet
    * @return Total of billable and non-billable hours
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateTimehseetHours(int timesheetId)  throws ProjectException;
}

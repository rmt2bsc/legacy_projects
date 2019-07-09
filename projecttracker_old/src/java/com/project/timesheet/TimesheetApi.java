package com.project.timesheet;

import java.util.List;
import java.util.Date;
import java.util.Map;

import com.api.BaseDataSource;

import com.bean.ProjTimesheet;
import com.bean.ProjProjectTask;
import com.bean.ProjEvent;
import com.bean.VwTimesheetProjectTask;

import com.project.ProjectException;

/**
 * The TimesheetApi interface defines methods that are responsible 
 * for managing the time an employee spends working on projects as well as 
 * the life cycle of the timesheet itself.    Also included in this interface 
 * are methods which function to provide various alternatives for querying 
 * the database for tmesheet data.   Data can be queried at various levels 
 * such as the client, timesheet, project, or task.
 *  
 * @author Roy Terrell
 *
 */
public interface TimesheetApi extends BaseDataSource {
    
    /**  A constant indicating the limit on regular hours per week. */
    public static final int REG_PAY_HOURS = 40;
    /** A constant indicating that the hour amount is billable */
    public static final int HOUR_TYPE_BILLABLE = 1;
    /** A constant indicating that the hour amount is not billable */
    public static final int HOUR_TYPE_NONBILLABLE = 0;

     
    /**
     * Summarizes timesheets by client and timesheet status.
     * 
     * @param criteria
     *          selection criteria that will filter result set beyond client 
     *          and timesheet status
     * @return an arbitrary object as the result set
     * @throws ProjectException
     */
    Object getTimesheetSummary(String criteria) throws ProjectException;
    
   /**
    * Gets base timesheet record.
    * 
    * @param timesheetId The id of the timesheet to retireve.
    * @return An arbitrary object.
    * @throws ProjectException
    */
   Object findTimesheet(int timesheetId) throws ProjectException;
   
   /**
    * Gets one or more base timesheet objects using _clientId.
    * 
    * @param clientId The id of the client to retreive timesheets.
    * @return An arbitrary Object
    * @throws ProjectException
    */
   Object  findTimesheetByClient(int clientId) throws ProjectException;
   
   /**
    * Gets extended base timesheet record.
    * 
    * @param timesheetId The id of the timesheet to retireve.
    * @return An arbitrary object
    * @throws ProjectException
    */
   Object findTimesheetExt(int timesheetId) throws ProjectException;
   
   /**
    * Gets extended timesheets using custom selection criteria.
    * 
    * @param criteria Customer selection criteria.
    * @return An arbitrary object
    * @throws ProjectException
    */
   Object findTimesheetExt(String criteria) throws ProjectException;
   
   /**
    * Retrieves timesheet hours base on the project and task using _timesheetId.
    * 
    * @param timesheetId  The id of the timesheet
    * @return Object[]
    * @throws ProjectException  If a database access error occurs.
    */
   Object[] findTimesheetHours(int timesheetId) throws ProjectException;
   
   /**
    * Gets one or more extended timesheets records related to a clinet
    * 
    * @param clientId The id of the client to retrieve all related timesheets.
    * @return A List of arbitrary objects.
    * @throws ProjectException
    */
   Object findTimesheetExtByClient(int clientId) throws ProjectException;
   
   /**
    * Gets one or more extended timesheets records related to an employee
    * 
    * @param employeeId The id of the employee to retrieve all related timesheets.
    * @return A List of arbitrary objects.
    * @throws ProjectException
    */
   Object findTimesheetExtByEmployee(int employeeId) throws ProjectException;
   
   /**
    * Gets one or more extended timesheets records which the timesheet id exist in 
    * <i>timesheetId</i> and is of <i>status</i>.
    * 
    * @param timesheetId An array of timesheet id's.
    * @param statusId The status id of the timesheet to search.
    * @return A List of arbitrary objects.
    * @throws ProjectException
    */
   Object findTimesheetExtByStatus(String timesheetId[], int statusId) throws ProjectException;
   
   
   /**
    * Retrieves a project task record
    * 
    * @param projectTaskId The id of the project-task record
    * @return An arbitrary object
    * @throws ProjectException
    */
   Object findProjectTask(int projectTaskId) throws ProjectException;
   
   /**
    * Retrieves an extended project task record
    * 
    * @param projectTaskId The id of the project-task record
    * @return An arbitrary object.
    * @throws ProjectException
    */
   Object findProjectTaskExt(int projectTaskId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by client
    * 
    * @param clientId The id of the client
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findProjectTaskExtByClient(int clientId) throws ProjectException;
   
   
   /**
    * Retrieves all project-tasks that are related to a particular timesheet.
    * 
    * @param _timesheetId The id of the timesheet to retrieve project-tasks
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findProjectTaskByTimesheet(int _timesheetId) throws ProjectException;
   
   
   /**
    * Retrieves one or more extended project task records by timesheet
    * 
    * @param timesheetId The id of the timesheet
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findProjectTaskExtByTimesheet(int timesheetId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by project
    * 
    * @param projectId The id of the timesheet
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findProjectTaskExtByProject(int projectId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by task
    * 
    * @param taskId The id of the task
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findProjectTaskExtByTask(int taskId) throws ProjectException;
   
   /**
    * Retrieves one or more extended project task records by project and task
    * 
    * @param projectId The id of the project
    * @param taskId _taskId The id of the task
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findProjectTaskExtByProjectTask(int projectId, int taskId) throws ProjectException;
   

   /**
    * Retrieves a timesheet event record
    * 
    * @param eventId The id of the timesheet event.
    * @return An arbitary object.
    * @throws ProjectException
    */
   Object findEvent(int eventId) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records
    * 
    * @param eventId The id of the timesheet event.
    * @return An arbitary object.
    * @throws ProjectException
    */
   Object findEventExt(int eventId) throws ProjectException;
   
   /**
    * Retrieves an extended timesheet event records by event date
    * 
    * @param eventDate The event date to filter data
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventExtByEventDate(Date eventDate) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records by beginning and ending event dates
    * 
    * @param beginDate The beginning of the date range.
    * @param endDate The ending of the date range.
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventExtByEventDate(Date beginDate, Date endDate) throws ProjectException;
   
   
   /**
    * Retrieves one or more timesheet event records using Project-Task
    * 
    * @param projectTaskId The id of the project-task
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventByProjectTask(int projectTaskId) throws ProjectException;
   
   
   /**
    * Retrieves extended timesheet event records by Project-Task
    * 
    * @param projectTaskId The id of the project-task
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventExtByProjectTask(int projectTaskId) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records by client
    * 
    * @param clientId The id of the client
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventExtByClient(int clientId) throws ProjectException;
   
   
   
   /**
    * Retrieves extended timesheet event records by timesheet
    * 
    * @param timesheetId The id of the timesheet
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventExtByTimesheet(int timesheetId) throws ProjectException;

   /**
    * Retrieves extended timesheet event records by project
    * 
    * @param projectId The id of the project
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventExtByProject(int projectId) throws ProjectException;
   
   /**
    * Retrieves extended timesheet event records by task
    * 
    * @param taskId The id of the task
    * @return A List of arbitrary objects
    * @throws ProjectException
    */
   Object findEventExtByTask(int taskId) throws ProjectException;
   
   /**
    * Obtains the week days as java.util.Date objects for a particular time period.
    * 
    * @param timePeriod The time target period. 
    * @return An array of java.util.Date objects.
    */
   Date[] getTimePeriodWeekDates(Date timePeriod) ;
   
   /**
    * Gets the available ending time period as Date objects.   Each date will represent 
    * the last day of the time period...in most cases Saturday.  The number of dates 
    * return is governed by AVAIL_ENDPERIODS_MAX
    * 
    * @return An String array of dates in the format MM/dd/yyyy.
    */
   String[] getAvailableEndingPeriods();
   
  /**
   * Applies the changes of a timesheet record via some external data provider.
   * 
   * @param timesheet Timesheet data that is to be saved
   * @return The id of the timesheet applied to the database.
   * @throws ProjectException
   */
   int maintainTimesheet(ProjTimesheet timesheet) throws ProjectException;
     
   /**
    * Applies the changes of a project-task record via some external data provider.
    * 
    * @param projectTask Project-task data that is to be saved
    * @return The id of the project-task applied to the database
    * @throws ProjectException
    */
   int maintainProjectTask(ProjProjectTask projectTask) throws ProjectException;
      
   /**
    * Applies the changes of a timesheet event via some external data provider 
    * using projectTaskId and events. 
    * 
    * @param projectTaskId The id of the project-task group.
    * @param events An ArrayList of (@link ProjEvent} objects to process
    * @return Total number of events processed.
    * @throws ProjectException
    */
   int maintainEvent(int projectTaskId, List events) throws ProjectException;
   
   
   /**
    * Applies the changes of a timesheet event via some external data provider.
    * 
    * @param event The event related data that is to be saved.
    * @return The id of the timesheet event applied to the database.
    * @throws ProjectException
    */
   int maintainEvent(ProjEvent event) throws ProjectException;

   /**
    * Deletes a single timesheet via some external data provider.
    * 
    * @param timesheetId The id of the target timesheet.
    * @return The number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteTimesheet(int timesheetId) throws ProjectException;
   
   /**
    * Deletes all project-tasks that are related to a particular timesheet.
    * 
    * @param timesheetId The id of the timesheet to delete project-tasks
    * @return The number of project-tasks deleted.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteProjectTasks(int timesheetId) throws ProjectException;
   
   /**
    * Deletes a single project-task via some external data provider.
    * 
    * @param projectTaskId The id of the project-task to delete.
    * @return The total number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteProjectTask(int projectTaskId) throws ProjectException;
   
   /**
    * Delete all events that are related to a project-task via some external 
    * data provider.
    * 
    * @param projectTaskId The id of the project-task to delete all events
    * @return The total number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteEvents(int projectTaskId) throws ProjectException;
   
   /**
    * Delete a single event via some external data provider.
    * 
    * param _eventId The id of the event to delete.
    * @return The total number of rows effected.
    * @throws ProjectException if a database access error occurs.
    */
   int deleteEvent(int eventId) throws ProjectException;
   
   /**
    * Get value that indicates the total number of numeircal digits that composes 
    * the timesheet's displayValue.
    * 
    * @return Maximum size as an int.  Returns 5 if a problem occurred converting 
    *         the property value to a number.
    * @throws ProjectException Prooperty file access errors.
    */
   int getMaxDisplayValueDigits() throws ProjectException;

   /**
    * Calculates the invoice amount of the timesheet.
    * 
    * @param timesheetId The id of the timesheet that is to be calculated.
    * @return double
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateInvoice(int timesheetId)  throws ProjectException;
   
   /**
    * Calculates billable hours for a given timesheet.
    * 
    * @param timesheetId The id of the timesheet
    * @return Total billable hours
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateTimehseetBillableHours(int timesheetId)  throws ProjectException;
   
   /**
    * Calculate non-billable hours for a given timesheet.
    * 
    * @param timesheetId The id of the timesheet
    * @return Total non-billable hours
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateTimehseetNonBillableHours(int timesheetId)  throws ProjectException;
   
   /**
    * Calculate billable and non-billable hours for a given timesheet.
    * 
    * @param timesheetId The id of the timesheet
    * @return Total of billable and non-billable hours
    * @throws ProjectException Problem occurred gathering the timesheet data.
    */
   double calculateTimehseetHours(int timesheetId)  throws ProjectException;
   
   /**
    * Validates a timesheet instance and its associated tasks.
    * 
    * @param base
    *         The high level base timesheet object to validate.
    * @param tasks
    *         A Map of one or more timesheet tasks that are realted to the base timesheet object.
    * @throws InvalidTimesheetException
    */
   void validateTimesheet(ProjTimesheet base) throws InvalidTimesheetException;
   
   /**
    * Validates a timesheet task.
    * 
    * @param task
    * @throws InvalidTimesheetTaskException
    */
   void validateTask(VwTimesheetProjectTask task) throws InvalidTimesheetTaskException;

   /**
    * Assignss the project id which this timesheet will be governed by
    * 
    * @param projId
    */
   void setCurrentProjectId(int projId);
   
   /**
    * Returns the project id that governs this timesheet.
    * 
    * @return
    */
   int getCurrentProjectId();
}

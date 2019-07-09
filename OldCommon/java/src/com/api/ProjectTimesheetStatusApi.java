package com.api;

import java.util.List;

import com.bean.ProjTimesheetStatus;
import com.bean.ProjTimesheetHist;

import com.util.ProjectException;

/**
 * The ProjectTimesheetStatusApi interface defines methods that 
 * are responsible for managing timesheet statuses.
 *  
 * @author Roy Terrell
 *
 */
public interface ProjectTimesheetStatusApi extends BaseDataSource {
	
	/** Timesheet status for New */
	public static final int STATUS_NEW = 0;
	/** Timesheet status for Not Submitted */
	public static final int STATUS_NOTSUBMITTED = 1;
	/** Timesheet status for Submitted */
	public static final int STATUS_SUBMITTED = 2;
	/** Timesheet status for Received */
	public static final int STATUS_RECVD = 3;
	/** Timesheet status for Approved */
	public static final int STATUS_APPROVED = 4;
	/** Timesheet status for Declined */
	public static final int STATUS_DECLINED = 5;


   /**
    * Gets the record pertaining to a particular timesheet status
    * 
    * @param _statusId The id of the timesheet status.
    * @return {@link ProjTimesheetStatus}
    * @throws ProjectException
    */
   ProjTimesheetStatus findTimesheetStatus(int _statusId) throws ProjectException;
   
   /**
    * Retrieves the entire master list of timesheet statuses
    * 
    * @return An ArrayList of {@link ProjTimesheetStatus}
    * @throws ProjectException
    */
   List findTimesheetStatus() throws ProjectException;
   
   /**
    * Retreives the current status record of a timesheet
    * 
    * @param _timesheetId The Id of the timesheet to retreive the status.
    * @return {@link ProjTimesheetHist}
    * @throws ProjectException
    */
   ProjTimesheetHist findTimesheetCurrentStatus(int _timesheetId) throws ProjectException;
   
   /**
    * Retreives the status history records for a particular timesheet
    * 
    * @param _timesheetId The Id of the timesheet to retreive the status history.
    * @return An ArrayList of {@link ProjTimesheetHist}
    * @throws ProjectException
    */
   List findTimesheetStatusHist(int _timesheetId) throws ProjectException;
   

   /**
    * Assigns a new status to a timesheet and applies the changes to timesheet status history table in the database.   
    * Before the new status assignment, the current status is terminated by assigning an end date of the current day.   Since 
    * there is no logic implemented in this method to govern the movement of timesheet statuses, invoke method, 
    * verifyStatusChange(int, int), prior to this method in order to verify that moving to the new status is in alignment with the 
    * business rules of changing statuses for timesheets. 
    * 
    * @param __timesheetId The id of the target timesheet
    * @param __newStatusId The id of status that is to be assigned to the purchase order.
    * @throws ProjectException
    */
    void setTimesheetStatus(int _timesheetId, int _newStatusId) throws ProjectException;
    
    
    /**
     * Deletes the history pertaining to a timesheet.
     * 
     * @param _timesheetId The id of the timesheet to remove history.
     * @return The count of all rows deleted.
     * @throws ProjectException
     */
    int deleteTimesheetStatus(int _timesheetId) throws ProjectException;
}

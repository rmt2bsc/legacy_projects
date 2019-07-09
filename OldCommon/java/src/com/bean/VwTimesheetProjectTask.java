package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_timesheet_project_task database table/view.
 *
 * @author Roy Terrell.
 */
public class VwTimesheetProjectTask extends OrmBean {

/** The javabean property equivalent of database column vw_timesheet_project_task.project_task_id */
  private int projectTaskId;
/** The javabean property equivalent of database column vw_timesheet_project_task.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column vw_timesheet_project_task.project_id */
  private int projectId;
/** The javabean property equivalent of database column vw_timesheet_project_task.task_id */
  private int taskId;
/** The javabean property equivalent of database column vw_timesheet_project_task.client_id */
  private int clientId;
/** The javabean property equivalent of database column vw_timesheet_project_task.project_name */
  private String projectName;
/** The javabean property equivalent of database column vw_timesheet_project_task.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column vw_timesheet_project_task.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column vw_timesheet_project_task.task_name */
  private String taskName;
/** The javabean property equivalent of database column vw_timesheet_project_task.billable */
  private int billable;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwTimesheetProjectTask() throws SystemException {
  }
/**
 * Sets the value of member variable projectTaskId
 *
 * @author Roy Terrell.
 */
  public void setProjectTaskId(int value) {
    this.projectTaskId = value;
  }
/**
 * Gets the value of member variable projectTaskId
 *
 * @author Roy Terrell.
 */
  public int getProjectTaskId() {
    return this.projectTaskId;
  }
/**
 * Sets the value of member variable timesheetId
 *
 * @author Roy Terrell.
 */
  public void setTimesheetId(int value) {
    this.timesheetId = value;
  }
/**
 * Gets the value of member variable timesheetId
 *
 * @author Roy Terrell.
 */
  public int getTimesheetId() {
    return this.timesheetId;
  }
/**
 * Sets the value of member variable projectId
 *
 * @author Roy Terrell.
 */
  public void setProjectId(int value) {
    this.projectId = value;
  }
/**
 * Gets the value of member variable projectId
 *
 * @author Roy Terrell.
 */
  public int getProjectId() {
    return this.projectId;
  }
/**
 * Sets the value of member variable taskId
 *
 * @author Roy Terrell.
 */
  public void setTaskId(int value) {
    this.taskId = value;
  }
/**
 * Gets the value of member variable taskId
 *
 * @author Roy Terrell.
 */
  public int getTaskId() {
    return this.taskId;
  }
/**
 * Sets the value of member variable clientId
 *
 * @author Roy Terrell.
 */
  public void setClientId(int value) {
    this.clientId = value;
  }
/**
 * Gets the value of member variable clientId
 *
 * @author Roy Terrell.
 */
  public int getClientId() {
    return this.clientId;
  }
/**
 * Sets the value of member variable projectName
 *
 * @author Roy Terrell.
 */
  public void setProjectName(String value) {
    this.projectName = value;
  }
/**
 * Gets the value of member variable projectName
 *
 * @author Roy Terrell.
 */
  public String getProjectName() {
    return this.projectName;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
  }
/**
 * Sets the value of member variable taskName
 *
 * @author Roy Terrell.
 */
  public void setTaskName(String value) {
    this.taskName = value;
  }
/**
 * Gets the value of member variable taskName
 *
 * @author Roy Terrell.
 */
  public String getTaskName() {
    return this.taskName;
  }
/**
 * Sets the value of member variable billable
 *
 * @author Roy Terrell.
 */
  public void setBillable(int value) {
    this.billable = value;
  }
/**
 * Gets the value of member variable billable
 *
 * @author Roy Terrell.
 */
  public int getBillable() {
    return this.billable;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_timesheet_hours database table/view.
 *
 * @author Roy Terrell.
 */
public class VwTimesheetHours extends OrmBean {

/** The javabean property equivalent of database column vw_timesheet_hours.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column vw_timesheet_hours.client_id */
  private int clientId;
/** The javabean property equivalent of database column vw_timesheet_hours.employee_id */
  private int employeeId;
/** The javabean property equivalent of database column vw_timesheet_hours.display_value */
  private String displayValue;
/** The javabean property equivalent of database column vw_timesheet_hours.timesheet_begin_period */
  private java.util.Date timesheetBeginPeriod;
/** The javabean property equivalent of database column vw_timesheet_hours.timesheet_end_period */
  private java.util.Date timesheetEndPeriod;
/** The javabean property equivalent of database column vw_timesheet_hours.invoice_ref_no */
  private String invoiceRefNo;
/** The javabean property equivalent of database column vw_timesheet_hours.project_id */
  private int projectId;
/** The javabean property equivalent of database column vw_timesheet_hours.task_id */
  private int taskId;
/** The javabean property equivalent of database column vw_timesheet_hours.event_id */
  private int eventId;
/** The javabean property equivalent of database column vw_timesheet_hours.proj_task_id */
  private int projTaskId;
/** The javabean property equivalent of database column vw_timesheet_hours.project_name */
  private String projectName;
/** The javabean property equivalent of database column vw_timesheet_hours.task_name */
  private String taskName;
/** The javabean property equivalent of database column vw_timesheet_hours.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column vw_timesheet_hours.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column vw_timesheet_hours.billable */
  private int billable;
/** The javabean property equivalent of database column vw_timesheet_hours.event_date */
  private java.util.Date eventDate;
/** The javabean property equivalent of database column vw_timesheet_hours.hours */
  private double hours;
/** The javabean property equivalent of database column vw_timesheet_hours.date_created */
  private java.util.Date dateCreated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwTimesheetHours() throws SystemException {
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
 * Sets the value of member variable employeeId
 *
 * @author Roy Terrell.
 */
  public void setEmployeeId(int value) {
    this.employeeId = value;
  }
/**
 * Gets the value of member variable employeeId
 *
 * @author Roy Terrell.
 */
  public int getEmployeeId() {
    return this.employeeId;
  }
/**
 * Sets the value of member variable displayValue
 *
 * @author Roy Terrell.
 */
  public void setDisplayValue(String value) {
    this.displayValue = value;
  }
/**
 * Gets the value of member variable displayValue
 *
 * @author Roy Terrell.
 */
  public String getDisplayValue() {
    return this.displayValue;
  }
/**
 * Sets the value of member variable timesheetBeginPeriod
 *
 * @author Roy Terrell.
 */
  public void setTimesheetBeginPeriod(java.util.Date value) {
    this.timesheetBeginPeriod = value;
  }
/**
 * Gets the value of member variable timesheetBeginPeriod
 *
 * @author Roy Terrell.
 */
  public java.util.Date getTimesheetBeginPeriod() {
    return this.timesheetBeginPeriod;
  }
/**
 * Sets the value of member variable timesheetEndPeriod
 *
 * @author Roy Terrell.
 */
  public void setTimesheetEndPeriod(java.util.Date value) {
    this.timesheetEndPeriod = value;
  }
/**
 * Gets the value of member variable timesheetEndPeriod
 *
 * @author Roy Terrell.
 */
  public java.util.Date getTimesheetEndPeriod() {
    return this.timesheetEndPeriod;
  }
/**
 * Sets the value of member variable invoiceRefNo
 *
 * @author Roy Terrell.
 */
  public void setInvoiceRefNo(String value) {
    this.invoiceRefNo = value;
  }
/**
 * Gets the value of member variable invoiceRefNo
 *
 * @author Roy Terrell.
 */
  public String getInvoiceRefNo() {
    return this.invoiceRefNo;
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
 * Sets the value of member variable eventId
 *
 * @author Roy Terrell.
 */
  public void setEventId(int value) {
    this.eventId = value;
  }
/**
 * Gets the value of member variable eventId
 *
 * @author Roy Terrell.
 */
  public int getEventId() {
    return this.eventId;
  }
/**
 * Sets the value of member variable projTaskId
 *
 * @author Roy Terrell.
 */
  public void setProjTaskId(int value) {
    this.projTaskId = value;
  }
/**
 * Gets the value of member variable projTaskId
 *
 * @author Roy Terrell.
 */
  public int getProjTaskId() {
    return this.projTaskId;
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
 * Sets the value of member variable eventDate
 *
 * @author Roy Terrell.
 */
  public void setEventDate(java.util.Date value) {
    this.eventDate = value;
  }
/**
 * Gets the value of member variable eventDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEventDate() {
    return this.eventDate;
  }
/**
 * Sets the value of member variable hours
 *
 * @author Roy Terrell.
 */
  public void setHours(double value) {
    this.hours = value;
  }
/**
 * Gets the value of member variable hours
 *
 * @author Roy Terrell.
 */
  public double getHours() {
    return this.hours;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
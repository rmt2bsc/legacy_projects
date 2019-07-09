package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_timesheet_hours database table/view.
 *
 * @author auto generated.
 */
public class VwTimesheetHours extends OrmBean {




	// Property name constants that belong to respective DataSource, VwTimesheetHoursView

/** The property name constant equivalent to property, TimesheetId, of respective DataSource view. */
  public static final String PROP_TIMESHEETID = "TimesheetId";
/** The property name constant equivalent to property, ClientId, of respective DataSource view. */
  public static final String PROP_CLIENTID = "ClientId";
/** The property name constant equivalent to property, EmployeeId, of respective DataSource view. */
  public static final String PROP_EMPLOYEEID = "EmployeeId";
/** The property name constant equivalent to property, DisplayValue, of respective DataSource view. */
  public static final String PROP_DISPLAYVALUE = "DisplayValue";
/** The property name constant equivalent to property, TimesheetBeginPeriod, of respective DataSource view. */
  public static final String PROP_TIMESHEETBEGINPERIOD = "TimesheetBeginPeriod";
/** The property name constant equivalent to property, TimesheetEndPeriod, of respective DataSource view. */
  public static final String PROP_TIMESHEETENDPERIOD = "TimesheetEndPeriod";
/** The property name constant equivalent to property, InvoiceRefNo, of respective DataSource view. */
  public static final String PROP_INVOICEREFNO = "InvoiceRefNo";
/** The property name constant equivalent to property, ExtRef, of respective DataSource view. */
  public static final String PROP_EXTREF = "ExtRef";
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, ProjectId, of respective DataSource view. */
  public static final String PROP_PROJECTID = "ProjectId";
/** The property name constant equivalent to property, TaskId, of respective DataSource view. */
  public static final String PROP_TASKID = "TaskId";
/** The property name constant equivalent to property, EventId, of respective DataSource view. */
  public static final String PROP_EVENTID = "EventId";
/** The property name constant equivalent to property, ProjTaskId, of respective DataSource view. */
  public static final String PROP_PROJTASKID = "ProjTaskId";
/** The property name constant equivalent to property, ProjectName, of respective DataSource view. */
  public static final String PROP_PROJECTNAME = "ProjectName";
/** The property name constant equivalent to property, TaskName, of respective DataSource view. */
  public static final String PROP_TASKNAME = "TaskName";
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";
/** The property name constant equivalent to property, Billable, of respective DataSource view. */
  public static final String PROP_BILLABLE = "Billable";
/** The property name constant equivalent to property, EventDate, of respective DataSource view. */
  public static final String PROP_EVENTDATE = "EventDate";
/** The property name constant equivalent to property, Hours, of respective DataSource view. */
  public static final String PROP_HOURS = "Hours";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";



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
/** The javabean property equivalent of database column vw_timesheet_hours.ext_ref */
  private String extRef;
/** The javabean property equivalent of database column vw_timesheet_hours.document_id */
  private int documentId;
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
 * @author auto generated.
 */
  public VwTimesheetHours() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable timesheetId
 *
 * @author auto generated.
 */
  public void setTimesheetId(int value) {
    this.timesheetId = value;
  }
/**
 * Gets the value of member variable timesheetId
 *
 * @author atuo generated.
 */
  public int getTimesheetId() {
    return this.timesheetId;
  }
/**
 * Sets the value of member variable clientId
 *
 * @author auto generated.
 */
  public void setClientId(int value) {
    this.clientId = value;
  }
/**
 * Gets the value of member variable clientId
 *
 * @author atuo generated.
 */
  public int getClientId() {
    return this.clientId;
  }
/**
 * Sets the value of member variable employeeId
 *
 * @author auto generated.
 */
  public void setEmployeeId(int value) {
    this.employeeId = value;
  }
/**
 * Gets the value of member variable employeeId
 *
 * @author atuo generated.
 */
  public int getEmployeeId() {
    return this.employeeId;
  }
/**
 * Sets the value of member variable displayValue
 *
 * @author auto generated.
 */
  public void setDisplayValue(String value) {
    this.displayValue = value;
  }
/**
 * Gets the value of member variable displayValue
 *
 * @author atuo generated.
 */
  public String getDisplayValue() {
    return this.displayValue;
  }
/**
 * Sets the value of member variable timesheetBeginPeriod
 *
 * @author auto generated.
 */
  public void setTimesheetBeginPeriod(java.util.Date value) {
    this.timesheetBeginPeriod = value;
  }
/**
 * Gets the value of member variable timesheetBeginPeriod
 *
 * @author atuo generated.
 */
  public java.util.Date getTimesheetBeginPeriod() {
    return this.timesheetBeginPeriod;
  }
/**
 * Sets the value of member variable timesheetEndPeriod
 *
 * @author auto generated.
 */
  public void setTimesheetEndPeriod(java.util.Date value) {
    this.timesheetEndPeriod = value;
  }
/**
 * Gets the value of member variable timesheetEndPeriod
 *
 * @author atuo generated.
 */
  public java.util.Date getTimesheetEndPeriod() {
    return this.timesheetEndPeriod;
  }
/**
 * Sets the value of member variable invoiceRefNo
 *
 * @author auto generated.
 */
  public void setInvoiceRefNo(String value) {
    this.invoiceRefNo = value;
  }
/**
 * Gets the value of member variable invoiceRefNo
 *
 * @author atuo generated.
 */
  public String getInvoiceRefNo() {
    return this.invoiceRefNo;
  }
/**
 * Sets the value of member variable extRef
 *
 * @author auto generated.
 */
  public void setExtRef(String value) {
    this.extRef = value;
  }
/**
 * Gets the value of member variable extRef
 *
 * @author atuo generated.
 */
  public String getExtRef() {
    return this.extRef;
  }
/**
 * Sets the value of member variable documentId
 *
 * @author auto generated.
 */
  public void setDocumentId(int value) {
    this.documentId = value;
  }
/**
 * Gets the value of member variable documentId
 *
 * @author atuo generated.
 */
  public int getDocumentId() {
    return this.documentId;
  }
/**
 * Sets the value of member variable projectId
 *
 * @author auto generated.
 */
  public void setProjectId(int value) {
    this.projectId = value;
  }
/**
 * Gets the value of member variable projectId
 *
 * @author atuo generated.
 */
  public int getProjectId() {
    return this.projectId;
  }
/**
 * Sets the value of member variable taskId
 *
 * @author auto generated.
 */
  public void setTaskId(int value) {
    this.taskId = value;
  }
/**
 * Gets the value of member variable taskId
 *
 * @author atuo generated.
 */
  public int getTaskId() {
    return this.taskId;
  }
/**
 * Sets the value of member variable eventId
 *
 * @author auto generated.
 */
  public void setEventId(int value) {
    this.eventId = value;
  }
/**
 * Gets the value of member variable eventId
 *
 * @author atuo generated.
 */
  public int getEventId() {
    return this.eventId;
  }
/**
 * Sets the value of member variable projTaskId
 *
 * @author auto generated.
 */
  public void setProjTaskId(int value) {
    this.projTaskId = value;
  }
/**
 * Gets the value of member variable projTaskId
 *
 * @author atuo generated.
 */
  public int getProjTaskId() {
    return this.projTaskId;
  }
/**
 * Sets the value of member variable projectName
 *
 * @author auto generated.
 */
  public void setProjectName(String value) {
    this.projectName = value;
  }
/**
 * Gets the value of member variable projectName
 *
 * @author atuo generated.
 */
  public String getProjectName() {
    return this.projectName;
  }
/**
 * Sets the value of member variable taskName
 *
 * @author auto generated.
 */
  public void setTaskName(String value) {
    this.taskName = value;
  }
/**
 * Gets the value of member variable taskName
 *
 * @author atuo generated.
 */
  public String getTaskName() {
    return this.taskName;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author auto generated.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author auto generated.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
  }
/**
 * Sets the value of member variable billable
 *
 * @author auto generated.
 */
  public void setBillable(int value) {
    this.billable = value;
  }
/**
 * Gets the value of member variable billable
 *
 * @author atuo generated.
 */
  public int getBillable() {
    return this.billable;
  }
/**
 * Sets the value of member variable eventDate
 *
 * @author auto generated.
 */
  public void setEventDate(java.util.Date value) {
    this.eventDate = value;
  }
/**
 * Gets the value of member variable eventDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEventDate() {
    return this.eventDate;
  }
/**
 * Sets the value of member variable hours
 *
 * @author auto generated.
 */
  public void setHours(double value) {
    this.hours = value;
  }
/**
 * Gets the value of member variable hours
 *
 * @author atuo generated.
 */
  public double getHours() {
    return this.hours;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author auto generated.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
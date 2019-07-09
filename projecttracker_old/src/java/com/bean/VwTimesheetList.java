package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_timesheet_list database table/view.
 *
 * @author auto generated.
 */
public class VwTimesheetList extends OrmBean {




	// Property name constants that belong to respective DataSource, VwTimesheetListView

/** The property name constant equivalent to property, TimesheetId, of respective DataSource view. */
  public static final String PROP_TIMESHEETID = "TimesheetId";
/** The property name constant equivalent to property, ClientId, of respective DataSource view. */
  public static final String PROP_CLIENTID = "ClientId";
/** The property name constant equivalent to property, EmpId, of respective DataSource view. */
  public static final String PROP_EMPID = "EmpId";
/** The property name constant equivalent to property, DisplayValue, of respective DataSource view. */
  public static final String PROP_DISPLAYVALUE = "DisplayValue";
/** The property name constant equivalent to property, BeginPeriod, of respective DataSource view. */
  public static final String PROP_BEGINPERIOD = "BeginPeriod";
/** The property name constant equivalent to property, EndPeriod, of respective DataSource view. */
  public static final String PROP_ENDPERIOD = "EndPeriod";
/** The property name constant equivalent to property, InvoiceRefNo, of respective DataSource view. */
  public static final String PROP_INVOICEREFNO = "InvoiceRefNo";
/** The property name constant equivalent to property, ExtRef, of respective DataSource view. */
  public static final String PROP_EXTREF = "ExtRef";
/** The property name constant equivalent to property, ProjId, of respective DataSource view. */
  public static final String PROP_PROJID = "ProjId";
/** The property name constant equivalent to property, Comments, of respective DataSource view. */
  public static final String PROP_COMMENTS = "Comments";
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, TimesheetStatusId, of respective DataSource view. */
  public static final String PROP_TIMESHEETSTATUSID = "TimesheetStatusId";
/** The property name constant equivalent to property, StatusEffectiveDate, of respective DataSource view. */
  public static final String PROP_STATUSEFFECTIVEDATE = "StatusEffectiveDate";
/** The property name constant equivalent to property, StatusEndDate, of respective DataSource view. */
  public static final String PROP_STATUSENDDATE = "StatusEndDate";
/** The property name constant equivalent to property, ProjTimesheetHistId, of respective DataSource view. */
  public static final String PROP_PROJTIMESHEETHISTID = "ProjTimesheetHistId";
/** The property name constant equivalent to property, StatusName, of respective DataSource view. */
  public static final String PROP_STATUSNAME = "StatusName";
/** The property name constant equivalent to property, StatusDescription, of respective DataSource view. */
  public static final String PROP_STATUSDESCRIPTION = "StatusDescription";
/** The property name constant equivalent to property, TypeId, of respective DataSource view. */
  public static final String PROP_TYPEID = "TypeId";
/** The property name constant equivalent to property, Firstname, of respective DataSource view. */
  public static final String PROP_FIRSTNAME = "Firstname";
/** The property name constant equivalent to property, Lastname, of respective DataSource view. */
  public static final String PROP_LASTNAME = "Lastname";
/** The property name constant equivalent to property, ManagerId, of respective DataSource view. */
  public static final String PROP_MANAGERID = "ManagerId";
/** The property name constant equivalent to property, HourlyOverRate, of respective DataSource view. */
  public static final String PROP_HOURLYOVERRATE = "HourlyOverRate";
/** The property name constant equivalent to property, HourlyRate, of respective DataSource view. */
  public static final String PROP_HOURLYRATE = "HourlyRate";
/** The property name constant equivalent to property, LastFirstName, of respective DataSource view. */
  public static final String PROP_LASTFIRSTNAME = "LastFirstName";
/** The property name constant equivalent to property, ClientName, of respective DataSource view. */
  public static final String PROP_CLIENTNAME = "ClientName";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, BillHrs, of respective DataSource view. */
  public static final String PROP_BILLHRS = "BillHrs";
/** The property name constant equivalent to property, NonBillHrs, of respective DataSource view. */
  public static final String PROP_NONBILLHRS = "NonBillHrs";



	/** The javabean property equivalent of database column vw_timesheet_list.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column vw_timesheet_list.client_id */
  private int clientId;
/** The javabean property equivalent of database column vw_timesheet_list.emp_id */
  private int empId;
/** The javabean property equivalent of database column vw_timesheet_list.display_value */
  private String displayValue;
/** The javabean property equivalent of database column vw_timesheet_list.begin_period */
  private java.util.Date beginPeriod;
/** The javabean property equivalent of database column vw_timesheet_list.end_period */
  private java.util.Date endPeriod;
/** The javabean property equivalent of database column vw_timesheet_list.invoice_ref_no */
  private String invoiceRefNo;
/** The javabean property equivalent of database column vw_timesheet_list.ext_ref */
  private String extRef;
/** The javabean property equivalent of database column vw_timesheet_list.proj_id */
  private int projId;
/** The javabean property equivalent of database column vw_timesheet_list.comments */
  private String comments;
/** The javabean property equivalent of database column vw_timesheet_list.document_id */
  private int documentId;
/** The javabean property equivalent of database column vw_timesheet_list.timesheet_status_id */
  private int timesheetStatusId;
/** The javabean property equivalent of database column vw_timesheet_list.status_effective_date */
  private java.util.Date statusEffectiveDate;
/** The javabean property equivalent of database column vw_timesheet_list.status_end_date */
  private java.util.Date statusEndDate;
/** The javabean property equivalent of database column vw_timesheet_list.proj_timesheet_hist_id */
  private int projTimesheetHistId;
/** The javabean property equivalent of database column vw_timesheet_list.status_name */
  private String statusName;
/** The javabean property equivalent of database column vw_timesheet_list.status_description */
  private String statusDescription;
/** The javabean property equivalent of database column vw_timesheet_list.type_id */
  private int typeId;
/** The javabean property equivalent of database column vw_timesheet_list.firstname */
  private String firstname;
/** The javabean property equivalent of database column vw_timesheet_list.lastname */
  private String lastname;
/** The javabean property equivalent of database column vw_timesheet_list.manager_id */
  private int managerId;
/** The javabean property equivalent of database column vw_timesheet_list.hourly_over_rate */
  private double hourlyOverRate;
/** The javabean property equivalent of database column vw_timesheet_list.hourly_rate */
  private double hourlyRate;
/** The javabean property equivalent of database column vw_timesheet_list.last_first_name */
  private String lastFirstName;
/** The javabean property equivalent of database column vw_timesheet_list.client_name */
  private String clientName;
/** The javabean property equivalent of database column vw_timesheet_list.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_timesheet_list.bill_hrs */
  private double billHrs;
/** The javabean property equivalent of database column vw_timesheet_list.non_bill_hrs */
  private double nonBillHrs;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwTimesheetList() throws SystemException {
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
 * Sets the value of member variable empId
 *
 * @author auto generated.
 */
  public void setEmpId(int value) {
    this.empId = value;
  }
/**
 * Gets the value of member variable empId
 *
 * @author atuo generated.
 */
  public int getEmpId() {
    return this.empId;
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
 * Sets the value of member variable beginPeriod
 *
 * @author auto generated.
 */
  public void setBeginPeriod(java.util.Date value) {
    this.beginPeriod = value;
  }
/**
 * Gets the value of member variable beginPeriod
 *
 * @author atuo generated.
 */
  public java.util.Date getBeginPeriod() {
    return this.beginPeriod;
  }
/**
 * Sets the value of member variable endPeriod
 *
 * @author auto generated.
 */
  public void setEndPeriod(java.util.Date value) {
    this.endPeriod = value;
  }
/**
 * Gets the value of member variable endPeriod
 *
 * @author atuo generated.
 */
  public java.util.Date getEndPeriod() {
    return this.endPeriod;
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
 * Sets the value of member variable projId
 *
 * @author auto generated.
 */
  public void setProjId(int value) {
    this.projId = value;
  }
/**
 * Gets the value of member variable projId
 *
 * @author atuo generated.
 */
  public int getProjId() {
    return this.projId;
  }
/**
 * Sets the value of member variable comments
 *
 * @author auto generated.
 */
  public void setComments(String value) {
    this.comments = value;
  }
/**
 * Gets the value of member variable comments
 *
 * @author atuo generated.
 */
  public String getComments() {
    return this.comments;
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
 * Sets the value of member variable timesheetStatusId
 *
 * @author auto generated.
 */
  public void setTimesheetStatusId(int value) {
    this.timesheetStatusId = value;
  }
/**
 * Gets the value of member variable timesheetStatusId
 *
 * @author atuo generated.
 */
  public int getTimesheetStatusId() {
    return this.timesheetStatusId;
  }
/**
 * Sets the value of member variable statusEffectiveDate
 *
 * @author auto generated.
 */
  public void setStatusEffectiveDate(java.util.Date value) {
    this.statusEffectiveDate = value;
  }
/**
 * Gets the value of member variable statusEffectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getStatusEffectiveDate() {
    return this.statusEffectiveDate;
  }
/**
 * Sets the value of member variable statusEndDate
 *
 * @author auto generated.
 */
  public void setStatusEndDate(java.util.Date value) {
    this.statusEndDate = value;
  }
/**
 * Gets the value of member variable statusEndDate
 *
 * @author atuo generated.
 */
  public java.util.Date getStatusEndDate() {
    return this.statusEndDate;
  }
/**
 * Sets the value of member variable projTimesheetHistId
 *
 * @author auto generated.
 */
  public void setProjTimesheetHistId(int value) {
    this.projTimesheetHistId = value;
  }
/**
 * Gets the value of member variable projTimesheetHistId
 *
 * @author atuo generated.
 */
  public int getProjTimesheetHistId() {
    return this.projTimesheetHistId;
  }
/**
 * Sets the value of member variable statusName
 *
 * @author auto generated.
 */
  public void setStatusName(String value) {
    this.statusName = value;
  }
/**
 * Gets the value of member variable statusName
 *
 * @author atuo generated.
 */
  public String getStatusName() {
    return this.statusName;
  }
/**
 * Sets the value of member variable statusDescription
 *
 * @author auto generated.
 */
  public void setStatusDescription(String value) {
    this.statusDescription = value;
  }
/**
 * Gets the value of member variable statusDescription
 *
 * @author atuo generated.
 */
  public String getStatusDescription() {
    return this.statusDescription;
  }
/**
 * Sets the value of member variable typeId
 *
 * @author auto generated.
 */
  public void setTypeId(int value) {
    this.typeId = value;
  }
/**
 * Gets the value of member variable typeId
 *
 * @author atuo generated.
 */
  public int getTypeId() {
    return this.typeId;
  }
/**
 * Sets the value of member variable firstname
 *
 * @author auto generated.
 */
  public void setFirstname(String value) {
    this.firstname = value;
  }
/**
 * Gets the value of member variable firstname
 *
 * @author atuo generated.
 */
  public String getFirstname() {
    return this.firstname;
  }
/**
 * Sets the value of member variable lastname
 *
 * @author auto generated.
 */
  public void setLastname(String value) {
    this.lastname = value;
  }
/**
 * Gets the value of member variable lastname
 *
 * @author atuo generated.
 */
  public String getLastname() {
    return this.lastname;
  }
/**
 * Sets the value of member variable managerId
 *
 * @author auto generated.
 */
  public void setManagerId(int value) {
    this.managerId = value;
  }
/**
 * Gets the value of member variable managerId
 *
 * @author atuo generated.
 */
  public int getManagerId() {
    return this.managerId;
  }
/**
 * Sets the value of member variable hourlyOverRate
 *
 * @author auto generated.
 */
  public void setHourlyOverRate(double value) {
    this.hourlyOverRate = value;
  }
/**
 * Gets the value of member variable hourlyOverRate
 *
 * @author atuo generated.
 */
  public double getHourlyOverRate() {
    return this.hourlyOverRate;
  }
/**
 * Sets the value of member variable hourlyRate
 *
 * @author auto generated.
 */
  public void setHourlyRate(double value) {
    this.hourlyRate = value;
  }
/**
 * Gets the value of member variable hourlyRate
 *
 * @author atuo generated.
 */
  public double getHourlyRate() {
    return this.hourlyRate;
  }
/**
 * Sets the value of member variable lastFirstName
 *
 * @author auto generated.
 */
  public void setLastFirstName(String value) {
    this.lastFirstName = value;
  }
/**
 * Gets the value of member variable lastFirstName
 *
 * @author atuo generated.
 */
  public String getLastFirstName() {
    return this.lastFirstName;
  }
/**
 * Sets the value of member variable clientName
 *
 * @author auto generated.
 */
  public void setClientName(String value) {
    this.clientName = value;
  }
/**
 * Gets the value of member variable clientName
 *
 * @author atuo generated.
 */
  public String getClientName() {
    return this.clientName;
  }
/**
 * Sets the value of member variable accountNo
 *
 * @author auto generated.
 */
  public void setAccountNo(String value) {
    this.accountNo = value;
  }
/**
 * Gets the value of member variable accountNo
 *
 * @author atuo generated.
 */
  public String getAccountNo() {
    return this.accountNo;
  }
/**
 * Sets the value of member variable billHrs
 *
 * @author auto generated.
 */
  public void setBillHrs(double value) {
    this.billHrs = value;
  }
/**
 * Gets the value of member variable billHrs
 *
 * @author atuo generated.
 */
  public double getBillHrs() {
    return this.billHrs;
  }
/**
 * Sets the value of member variable nonBillHrs
 *
 * @author auto generated.
 */
  public void setNonBillHrs(double value) {
    this.nonBillHrs = value;
  }
/**
 * Gets the value of member variable nonBillHrs
 *
 * @author atuo generated.
 */
  public double getNonBillHrs() {
    return this.nonBillHrs;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
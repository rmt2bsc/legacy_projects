package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_timesheet_list database table/view.
 *
 * @author Roy Terrell.
 */
public class VwTimesheetList extends OrmBean {

/** The javabean property equivalent of database column vw_timesheet_list.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column vw_timesheet_list.proj_client_id */
  private int projClientId;
/** The javabean property equivalent of database column vw_timesheet_list.proj_employee_id */
  private int projEmployeeId;
/** The javabean property equivalent of database column vw_timesheet_list.display_value */
  private String displayValue;
/** The javabean property equivalent of database column vw_timesheet_list.begin_period */
  private java.util.Date beginPeriod;
/** The javabean property equivalent of database column vw_timesheet_list.end_period */
  private java.util.Date endPeriod;
/** The javabean property equivalent of database column vw_timesheet_list.invoice_ref_no */
  private String invoiceRefNo;
/** The javabean property equivalent of database column vw_timesheet_list.proj_timesheet_status_id */
  private int projTimesheetStatusId;
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
/** The javabean property equivalent of database column vw_timesheet_list.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_timesheet_list.manager_id */
  private int managerId;
/** The javabean property equivalent of database column vw_timesheet_list.ot_bill_rate */
  private double otBillRate;
/** The javabean property equivalent of database column vw_timesheet_list.bill_rate */
  private double billRate;
/** The javabean property equivalent of database column vw_timesheet_list.generation */
  private String generation;
/** The javabean property equivalent of database column vw_timesheet_list.birth_date */
  private java.util.Date birthDate;
/** The javabean property equivalent of database column vw_timesheet_list.last_first_name */
  private String lastFirstName;
/** The javabean property equivalent of database column vw_timesheet_list.client_name */
  private String clientName;
/** The javabean property equivalent of database column vw_timesheet_list.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_timesheet_list.balance */
  private double balance;
/** The javabean property equivalent of database column vw_timesheet_list.bill_hrs */
  private double billHrs;
/** The javabean property equivalent of database column vw_timesheet_list.non_bill_hrs */
  private double nonBillHrs;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwTimesheetList() throws SystemException {
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
 * Sets the value of member variable projClientId
 *
 * @author Roy Terrell.
 */
  public void setProjClientId(int value) {
    this.projClientId = value;
  }
/**
 * Gets the value of member variable projClientId
 *
 * @author Roy Terrell.
 */
  public int getProjClientId() {
    return this.projClientId;
  }
/**
 * Sets the value of member variable projEmployeeId
 *
 * @author Roy Terrell.
 */
  public void setProjEmployeeId(int value) {
    this.projEmployeeId = value;
  }
/**
 * Gets the value of member variable projEmployeeId
 *
 * @author Roy Terrell.
 */
  public int getProjEmployeeId() {
    return this.projEmployeeId;
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
 * Sets the value of member variable beginPeriod
 *
 * @author Roy Terrell.
 */
  public void setBeginPeriod(java.util.Date value) {
    this.beginPeriod = value;
  }
/**
 * Gets the value of member variable beginPeriod
 *
 * @author Roy Terrell.
 */
  public java.util.Date getBeginPeriod() {
    return this.beginPeriod;
  }
/**
 * Sets the value of member variable endPeriod
 *
 * @author Roy Terrell.
 */
  public void setEndPeriod(java.util.Date value) {
    this.endPeriod = value;
  }
/**
 * Gets the value of member variable endPeriod
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndPeriod() {
    return this.endPeriod;
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
 * Sets the value of member variable projTimesheetStatusId
 *
 * @author Roy Terrell.
 */
  public void setProjTimesheetStatusId(int value) {
    this.projTimesheetStatusId = value;
  }
/**
 * Gets the value of member variable projTimesheetStatusId
 *
 * @author Roy Terrell.
 */
  public int getProjTimesheetStatusId() {
    return this.projTimesheetStatusId;
  }
/**
 * Sets the value of member variable statusEffectiveDate
 *
 * @author Roy Terrell.
 */
  public void setStatusEffectiveDate(java.util.Date value) {
    this.statusEffectiveDate = value;
  }
/**
 * Gets the value of member variable statusEffectiveDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getStatusEffectiveDate() {
    return this.statusEffectiveDate;
  }
/**
 * Sets the value of member variable statusEndDate
 *
 * @author Roy Terrell.
 */
  public void setStatusEndDate(java.util.Date value) {
    this.statusEndDate = value;
  }
/**
 * Gets the value of member variable statusEndDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getStatusEndDate() {
    return this.statusEndDate;
  }
/**
 * Sets the value of member variable projTimesheetHistId
 *
 * @author Roy Terrell.
 */
  public void setProjTimesheetHistId(int value) {
    this.projTimesheetHistId = value;
  }
/**
 * Gets the value of member variable projTimesheetHistId
 *
 * @author Roy Terrell.
 */
  public int getProjTimesheetHistId() {
    return this.projTimesheetHistId;
  }
/**
 * Sets the value of member variable statusName
 *
 * @author Roy Terrell.
 */
  public void setStatusName(String value) {
    this.statusName = value;
  }
/**
 * Gets the value of member variable statusName
 *
 * @author Roy Terrell.
 */
  public String getStatusName() {
    return this.statusName;
  }
/**
 * Sets the value of member variable statusDescription
 *
 * @author Roy Terrell.
 */
  public void setStatusDescription(String value) {
    this.statusDescription = value;
  }
/**
 * Gets the value of member variable statusDescription
 *
 * @author Roy Terrell.
 */
  public String getStatusDescription() {
    return this.statusDescription;
  }
/**
 * Sets the value of member variable typeId
 *
 * @author Roy Terrell.
 */
  public void setTypeId(int value) {
    this.typeId = value;
  }
/**
 * Gets the value of member variable typeId
 *
 * @author Roy Terrell.
 */
  public int getTypeId() {
    return this.typeId;
  }
/**
 * Sets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public void setShortname(String value) {
    this.shortname = value;
  }
/**
 * Gets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public String getShortname() {
    return this.shortname;
  }
/**
 * Sets the value of member variable managerId
 *
 * @author Roy Terrell.
 */
  public void setManagerId(int value) {
    this.managerId = value;
  }
/**
 * Gets the value of member variable managerId
 *
 * @author Roy Terrell.
 */
  public int getManagerId() {
    return this.managerId;
  }
/**
 * Sets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public void setOtBillRate(double value) {
    this.otBillRate = value;
  }
/**
 * Gets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public double getOtBillRate() {
    return this.otBillRate;
  }
/**
 * Sets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public void setBillRate(double value) {
    this.billRate = value;
  }
/**
 * Gets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public double getBillRate() {
    return this.billRate;
  }
/**
 * Sets the value of member variable generation
 *
 * @author Roy Terrell.
 */
  public void setGeneration(String value) {
    this.generation = value;
  }
/**
 * Gets the value of member variable generation
 *
 * @author Roy Terrell.
 */
  public String getGeneration() {
    return this.generation;
  }
/**
 * Sets the value of member variable birthDate
 *
 * @author Roy Terrell.
 */
  public void setBirthDate(java.util.Date value) {
    this.birthDate = value;
  }
/**
 * Gets the value of member variable birthDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getBirthDate() {
    return this.birthDate;
  }
/**
 * Sets the value of member variable lastFirstName
 *
 * @author Roy Terrell.
 */
  public void setLastFirstName(String value) {
    this.lastFirstName = value;
  }
/**
 * Gets the value of member variable lastFirstName
 *
 * @author Roy Terrell.
 */
  public String getLastFirstName() {
    return this.lastFirstName;
  }
/**
 * Sets the value of member variable clientName
 *
 * @author Roy Terrell.
 */
  public void setClientName(String value) {
    this.clientName = value;
  }
/**
 * Gets the value of member variable clientName
 *
 * @author Roy Terrell.
 */
  public String getClientName() {
    return this.clientName;
  }
/**
 * Sets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public void setAccountNo(String value) {
    this.accountNo = value;
  }
/**
 * Gets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public String getAccountNo() {
    return this.accountNo;
  }
/**
 * Sets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public void setBalance(double value) {
    this.balance = value;
  }
/**
 * Gets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public double getBalance() {
    return this.balance;
  }
/**
 * Sets the value of member variable billHrs
 *
 * @author Roy Terrell.
 */
  public void setBillHrs(double value) {
    this.billHrs = value;
  }
/**
 * Gets the value of member variable billHrs
 *
 * @author Roy Terrell.
 */
  public double getBillHrs() {
    return this.billHrs;
  }
/**
 * Sets the value of member variable nonBillHrs
 *
 * @author Roy Terrell.
 */
  public void setNonBillHrs(double value) {
    this.nonBillHrs = value;
  }
/**
 * Gets the value of member variable nonBillHrs
 *
 * @author Roy Terrell.
 */
  public double getNonBillHrs() {
    return this.nonBillHrs;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
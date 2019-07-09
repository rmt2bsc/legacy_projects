package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_timesheet database table/view.
 *
 * @author Roy Terrell.
 */
public class ProjTimesheet extends OrmBean {

/** The javabean property equivalent of database column proj_timesheet.id */
  private int id;
/** The javabean property equivalent of database column proj_timesheet.proj_client_id */
  private int projClientId;
/** The javabean property equivalent of database column proj_timesheet.proj_employee_id */
  private int projEmployeeId;
/** The javabean property equivalent of database column proj_timesheet.display_value */
  private String displayValue;
/** The javabean property equivalent of database column proj_timesheet.begin_period */
  private java.util.Date beginPeriod;
/** The javabean property equivalent of database column proj_timesheet.end_period */
  private java.util.Date endPeriod;
/** The javabean property equivalent of database column proj_timesheet.invoice_ref_no */
  private String invoiceRefNo;
/** The javabean property equivalent of database column proj_timesheet.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column proj_timesheet.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column proj_timesheet.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ProjTimesheet() throws SystemException {
      super();
  }
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public int getId() {
    return this.id;
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
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
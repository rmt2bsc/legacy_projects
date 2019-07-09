package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_timesheet database table/view.
 *
 * @author auto generated.
 */
public class ProjTimesheet extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjTimesheetView

/** The property name constant equivalent to property, TimesheetId, of respective DataSource view. */
  public static final String PROP_TIMESHEETID = "TimesheetId";
/** The property name constant equivalent to property, ClientId, of respective DataSource view. */
  public static final String PROP_CLIENTID = "ClientId";
/** The property name constant equivalent to property, ProjId, of respective DataSource view. */
  public static final String PROP_PROJID = "ProjId";
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
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, Comments, of respective DataSource view. */
  public static final String PROP_COMMENTS = "Comments";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, IpCreated, of respective DataSource view. */
  public static final String PROP_IPCREATED = "IpCreated";
/** The property name constant equivalent to property, IpUpdated, of respective DataSource view. */
  public static final String PROP_IPUPDATED = "IpUpdated";



	/** The javabean property equivalent of database column proj_timesheet.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column proj_timesheet.client_id */
  private int clientId;
/** The javabean property equivalent of database column proj_timesheet.proj_id */
  private int projId;
/** The javabean property equivalent of database column proj_timesheet.emp_id */
  private int empId;
/** The javabean property equivalent of database column proj_timesheet.display_value */
  private String displayValue;
/** The javabean property equivalent of database column proj_timesheet.begin_period */
  private java.util.Date beginPeriod;
/** The javabean property equivalent of database column proj_timesheet.end_period */
  private java.util.Date endPeriod;
/** The javabean property equivalent of database column proj_timesheet.invoice_ref_no */
  private String invoiceRefNo;
/** The javabean property equivalent of database column proj_timesheet.ext_ref */
  private String extRef;
/** The javabean property equivalent of database column proj_timesheet.document_id */
  private int documentId;
/** The javabean property equivalent of database column proj_timesheet.comments */
  private String comments;
/** The javabean property equivalent of database column proj_timesheet.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column proj_timesheet.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column proj_timesheet.user_id */
  private String userId;
/** The javabean property equivalent of database column proj_timesheet.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column proj_timesheet.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjTimesheet() throws SystemException {
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
 * Sets the value of member variable dateUpdated
 *
 * @author auto generated.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author auto generated.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author atuo generated.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Sets the value of member variable ipCreated
 *
 * @author auto generated.
 */
  public void setIpCreated(String value) {
    this.ipCreated = value;
  }
/**
 * Gets the value of member variable ipCreated
 *
 * @author atuo generated.
 */
  public String getIpCreated() {
    return this.ipCreated;
  }
/**
 * Sets the value of member variable ipUpdated
 *
 * @author auto generated.
 */
  public void setIpUpdated(String value) {
    this.ipUpdated = value;
  }
/**
 * Gets the value of member variable ipUpdated
 *
 * @author atuo generated.
 */
  public String getIpUpdated() {
    return this.ipUpdated;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
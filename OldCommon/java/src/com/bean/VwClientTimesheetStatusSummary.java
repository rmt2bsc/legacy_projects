package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_client_timesheet_status_summary database table/view.
 *
 * @author Roy Terrell.
 */
public class VwClientTimesheetStatusSummary extends OrmBean {

/** The javabean property equivalent of database column vw_client_timesheet_status_summary.client_id */
  private int clientId;
/** The javabean property equivalent of database column vw_client_timesheet_status_summary.perbus_id */
  private int perbusId;
/** The javabean property equivalent of database column vw_client_timesheet_status_summary.name */
  private String name;
/** The javabean property equivalent of database column vw_client_timesheet_status_summary.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_client_timesheet_status_summary.ts_status_id */
  private int tsStatusId;
/** The javabean property equivalent of database column vw_client_timesheet_status_summary.ts_status_name */
  private String tsStatusName;
/** The javabean property equivalent of database column vw_client_timesheet_status_summary.ts_status_descr */
  private String tsStatusDescr;
/** The javabean property equivalent of database column vw_client_timesheet_status_summary.timesheet_count */
  private int timesheetCount;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwClientTimesheetStatusSummary() throws SystemException {
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
 * Sets the value of member variable perbusId
 *
 * @author Roy Terrell.
 */
  public void setPerbusId(int value) {
    this.perbusId = value;
  }
/**
 * Gets the value of member variable perbusId
 *
 * @author Roy Terrell.
 */
  public int getPerbusId() {
    return this.perbusId;
  }
/**
 * Sets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public String getName() {
    return this.name;
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
 * Sets the value of member variable tsStatusId
 *
 * @author Roy Terrell.
 */
  public void setTsStatusId(int value) {
    this.tsStatusId = value;
  }
/**
 * Gets the value of member variable tsStatusId
 *
 * @author Roy Terrell.
 */
  public int getTsStatusId() {
    return this.tsStatusId;
  }
/**
 * Sets the value of member variable tsStatusName
 *
 * @author Roy Terrell.
 */
  public void setTsStatusName(String value) {
    this.tsStatusName = value;
  }
/**
 * Gets the value of member variable tsStatusName
 *
 * @author Roy Terrell.
 */
  public String getTsStatusName() {
    return this.tsStatusName;
  }
/**
 * Sets the value of member variable tsStatusDescr
 *
 * @author Roy Terrell.
 */
  public void setTsStatusDescr(String value) {
    this.tsStatusDescr = value;
  }
/**
 * Gets the value of member variable tsStatusDescr
 *
 * @author Roy Terrell.
 */
  public String getTsStatusDescr() {
    return this.tsStatusDescr;
  }
/**
 * Sets the value of member variable timesheetCount
 *
 * @author Roy Terrell.
 */
  public void setTimesheetCount(int value) {
    this.timesheetCount = value;
  }
/**
 * Gets the value of member variable timesheetCount
 *
 * @author Roy Terrell.
 */
  public int getTimesheetCount() {
    return this.timesheetCount;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
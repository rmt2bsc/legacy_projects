package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_client_timesheet_summary database table/view.
 *
 * @author auto generated.
 */
public class VwClientTimesheetSummary extends OrmBean {




	// Property name constants that belong to respective DataSource, VwClientTimesheetSummaryView

/** The property name constant equivalent to property, ClientId, of respective DataSource view. */
  public static final String PROP_CLIENTID = "ClientId";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, StatusId, of respective DataSource view. */
  public static final String PROP_STATUSID = "StatusId";
/** The property name constant equivalent to property, StatusName, of respective DataSource view. */
  public static final String PROP_STATUSNAME = "StatusName";
/** The property name constant equivalent to property, StatusDescr, of respective DataSource view. */
  public static final String PROP_STATUSDESCR = "StatusDescr";
/** The property name constant equivalent to property, TimesheetCount, of respective DataSource view. */
  public static final String PROP_TIMESHEETCOUNT = "TimesheetCount";



	/** The javabean property equivalent of database column vw_client_timesheet_summary.client_id */
  private int clientId;
/** The javabean property equivalent of database column vw_client_timesheet_summary.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_client_timesheet_summary.name */
  private String name;
/** The javabean property equivalent of database column vw_client_timesheet_summary.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_client_timesheet_summary.status_id */
  private int statusId;
/** The javabean property equivalent of database column vw_client_timesheet_summary.status_name */
  private String statusName;
/** The javabean property equivalent of database column vw_client_timesheet_summary.status_descr */
  private String statusDescr;
/** The javabean property equivalent of database column vw_client_timesheet_summary.timesheet_count */
  private int timesheetCount;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwClientTimesheetSummary() throws SystemException {
	super();
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
 * Sets the value of member variable businessId
 *
 * @author auto generated.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author atuo generated.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
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
 * Sets the value of member variable statusId
 *
 * @author auto generated.
 */
  public void setStatusId(int value) {
    this.statusId = value;
  }
/**
 * Gets the value of member variable statusId
 *
 * @author atuo generated.
 */
  public int getStatusId() {
    return this.statusId;
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
 * Sets the value of member variable statusDescr
 *
 * @author auto generated.
 */
  public void setStatusDescr(String value) {
    this.statusDescr = value;
  }
/**
 * Gets the value of member variable statusDescr
 *
 * @author atuo generated.
 */
  public String getStatusDescr() {
    return this.statusDescr;
  }
/**
 * Sets the value of member variable timesheetCount
 *
 * @author auto generated.
 */
  public void setTimesheetCount(int value) {
    this.timesheetCount = value;
  }
/**
 * Gets the value of member variable timesheetCount
 *
 * @author atuo generated.
 */
  public int getTimesheetCount() {
    return this.timesheetCount;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
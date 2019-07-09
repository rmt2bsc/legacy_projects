package com.bean.criteria;

import com.util.SystemException;

/**
 * Criteria data object to be used along side the VWTimesheetListView datasource.
 *
 * @author Roy Terrell.
 */
public class TimesheetCriteria extends AncestorQueryCriteria {

/** The javabean property equivalent of database column vw_purchase_order_list.id */
  private String qry_ProjTimesheetStatusId;
/** The javabean property equivalent of database column vw_purchase_order_list.vendor_id */
  private String qry_ProjEmployeeId;
/** The javabean property equivalent of database column vw_purchase_order_list.ref_no */
  private String qry_ProjClientId;
/** The javabean property equivalent of database column vw_purchase_order_list.effective_date */
  private String qry_BeginPeriod;
/** The javabean property equivalent of database column vw_purchase_order_list.end_date */
  private String qry_EndPeriod;
  
  /** The javabean property equivalent of database column vw_purchase_order_list.end_date */
  private String qry_EndPeriod1;
  /** The javabean property equivalent of database column vw_purchase_order_list.end_date */
  private String qry_EndPeriod2;
  
  
/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public TimesheetCriteria() throws SystemException {
	  super();
  }
  
  public static TimesheetCriteria getInstance() {
      try {
          return new TimesheetCriteria();
      }
      catch (SystemException e) {
          return null;
      }
  }
  
  /**
   * Initializes all properties to spaces.
   *
   * @author Roy Terrell.
   */
  	public void initBean() throws SystemException {
  		super.initBean();
  		this.qry_ProjTimesheetStatusId = "";
  		this.qry_ProjEmployeeId = "";
  		this.qry_ProjClientId = "";
  		this.qry_BeginPeriod = "";
  		this.qry_EndPeriod = "";
  	}
    
  
/**
 * Sets the value of status
 *
 * @author Roy Terrell.
 */
  public void setQry_ProjTimesheetStatusId(String value) {
    this.qry_ProjTimesheetStatusId = value;
  }
/**
 * Gets the value of status
 *
 * @author Roy Terrell.
 */
  public String getQry_ProjTimesheetStatusId() {
    return this.qry_ProjTimesheetStatusId == null ? "" : this.qry_ProjTimesheetStatusId;
  }
/**
 * Sets the value employee id
 *
 * @author Roy Terrell.
 */
  public void setQry_ProjEmployeeId(String value) {
    this.qry_ProjEmployeeId = value;
  }
/**
 * Gets the value of employee id
 *
 * @author Roy Terrell.
 */
  public String qetQry_ProjEmployeeId() {
    return this.qry_ProjEmployeeId == null ? "" : this.qry_ProjEmployeeId;
  }
  
/**
 * Sets the value of client id
 *
 * @author Roy Terrell.
 */
  public void setQry_ProjClientId(String value) {
    this.qry_ProjClientId = value;
  }
/**
 * Gets the value of client id
 *
 * @author Roy Terrell.
 */
  public String getQry_ProjClientId() {
    return this.qry_ProjClientId == null ? "" : this.qry_ProjClientId;
  }
  
/**
 * Sets the value of the beginning timesheet period
 *
 * @author Roy Terrell.
 */
  public void setQry_BeginPeriod(String value) {
    this.qry_BeginPeriod = value;
  }
/**
 * Gets the value of the beginning timesheet period
 *
 * @author Roy Terrell.
 */
  public String getQry_BeginPeriod() {
    return this.qry_BeginPeriod == null ? "" : this.qry_BeginPeriod;
  }
  
/**
 * Sets the value of the ending timesheet period
 *
 * @author Roy Terrell.
 */
  public void setQry_EndPeriod(String value) {
    this.qry_EndPeriod = value;
  }
/**
 * Gets the value of the ending timesheet period
 *
 * @author Roy Terrell.
 */
  public String getQry_EndPeriod() {
    return this.qry_EndPeriod == null ? "" : this.qry_EndPeriod; 
  }
  
  /**
   * Sets the value of the ending timesheet period #1
   *
   * @author Roy Terrell.
   */
    public void setQry_EndPeriod1(String value) {
      this.qry_EndPeriod1 = value;
    }
  /**
   * Gets the value of the ending timesheet period #1
   *
   * @author Roy Terrell.
   */
    public String getQry_EndPeriod1() {
      return this.qry_EndPeriod1 == null ? "" : this.qry_EndPeriod1; 
    }
    
    /**
     * Sets the value of the ending timesheet period #2
     *
     * @author Roy Terrell.
     */
      public void setQry_EndPeriod2(String value) {
        this.qry_EndPeriod2 = value;
      }
    /**
     * Gets the value of the ending timesheet period #2
     *
     * @author Roy Terrell.
     */
      public String getQry_EndPeriod2() {
        return this.qry_EndPeriod2 == null ? "" : this.qry_EndPeriod2; 
      } 
    
}
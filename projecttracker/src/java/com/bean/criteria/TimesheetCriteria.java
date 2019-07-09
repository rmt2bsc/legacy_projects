package com.bean.criteria;

import com.util.SystemException;

/**
 * Criteria data object to be used along side the VWTimesheetListView datasource.
 *
 * @author Roy Terrell.
 */
public class TimesheetCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = 8650546935742556653L;
    
/** The javabean property equivalent of database column vw_purchase_order_list.id */
  private String qry_TimesheetStatusId;
/** The javabean property equivalent of database column vw_purchase_order_list.vendor_id */
  private String qry_EmpId;
/** The javabean property equivalent of database column vw_purchase_order_list.ref_no */
  private String qry_ClientId;
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
  		this.qry_TimesheetStatusId = "";
  		this.qry_EmpId = "";
  		this.qry_ClientId = "";
  		this.qry_BeginPeriod = "";
  		this.qry_EndPeriod = "";
  	}
    
  

/**
 * @return the qry_ClientId
 */
public String getQry_ClientId() {
    return qry_ClientId;
}

/**
 * @param qry_ClientId the qry_ClientId to set
 */
public void setQry_ClientId(String qry_ClientId) {
    this.qry_ClientId = qry_ClientId;
}

/**
 * @return the qry_EmpId
 */
public String getQry_EmpId() {
    return qry_EmpId;
}

/**
 * @param qry_EmpId the qry_EmpId to set
 */
public void setQry_EmpId(String qry_EmpId) {
    this.qry_EmpId = qry_EmpId;
}

/**
 * @return the qry_TimesheetStatusId
 */
public String getQry_TimesheetStatusId() {
    return qry_TimesheetStatusId;
}

/**
 * @param qry_TimesheetStatusId the qry_TimesheetStatusId to set
 */
public void setQry_TimesheetStatusId(String qry_TimesheetStatusId) {
    this.qry_TimesheetStatusId = qry_TimesheetStatusId;
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
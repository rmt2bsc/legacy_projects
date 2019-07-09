package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the sales_order_status_hist database table/view.
 *
 * @author auto generated.
 */
public class SalesOrderStatusHist extends OrmBean {




	// Property name constants that belong to respective DataSource, SalesOrderStatusHistView

/** The property name constant equivalent to property, SoStatusHistId, of respective DataSource view. */
  public static final String PROP_SOSTATUSHISTID = "SoStatusHistId";
/** The property name constant equivalent to property, SoId, of respective DataSource view. */
  public static final String PROP_SOID = "SoId";
/** The property name constant equivalent to property, SoStatusId, of respective DataSource view. */
  public static final String PROP_SOSTATUSID = "SoStatusId";
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";
/** The property name constant equivalent to property, Reason, of respective DataSource view. */
  public static final String PROP_REASON = "Reason";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, IpCreated, of respective DataSource view. */
  public static final String PROP_IPCREATED = "IpCreated";
/** The property name constant equivalent to property, IpUpdated, of respective DataSource view. */
  public static final String PROP_IPUPDATED = "IpUpdated";



	/** The javabean property equivalent of database column sales_order_status_hist.so_status_hist_id */
  private int soStatusHistId;
/** The javabean property equivalent of database column sales_order_status_hist.so_id */
  private int soId;
/** The javabean property equivalent of database column sales_order_status_hist.so_status_id */
  private int soStatusId;
/** The javabean property equivalent of database column sales_order_status_hist.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column sales_order_status_hist.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column sales_order_status_hist.reason */
  private String reason;
/** The javabean property equivalent of database column sales_order_status_hist.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column sales_order_status_hist.user_id */
  private String userId;
/** The javabean property equivalent of database column sales_order_status_hist.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column sales_order_status_hist.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public SalesOrderStatusHist() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable soStatusHistId
 *
 * @author auto generated.
 */
  public void setSoStatusHistId(int value) {
    this.soStatusHistId = value;
  }
/**
 * Gets the value of member variable soStatusHistId
 *
 * @author atuo generated.
 */
  public int getSoStatusHistId() {
    return this.soStatusHistId;
  }
/**
 * Sets the value of member variable soId
 *
 * @author auto generated.
 */
  public void setSoId(int value) {
    this.soId = value;
  }
/**
 * Gets the value of member variable soId
 *
 * @author atuo generated.
 */
  public int getSoId() {
    return this.soId;
  }
/**
 * Sets the value of member variable soStatusId
 *
 * @author auto generated.
 */
  public void setSoStatusId(int value) {
    this.soStatusId = value;
  }
/**
 * Gets the value of member variable soStatusId
 *
 * @author atuo generated.
 */
  public int getSoStatusId() {
    return this.soStatusId;
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
 * Sets the value of member variable reason
 *
 * @author auto generated.
 */
  public void setReason(String value) {
    this.reason = value;
  }
/**
 * Gets the value of member variable reason
 *
 * @author atuo generated.
 */
  public String getReason() {
    return this.reason;
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
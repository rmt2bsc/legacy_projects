package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the sales_order_status_hist database table/view.
 *
 * @author Roy Terrell.
 */
public class SalesOrderStatusHist extends OrmBean {

/** The javabean property equivalent of database column sales_order_status_hist.id */
  private int id;
/** The javabean property equivalent of database column sales_order_status_hist.sales_order_id */
  private int salesOrderId;
/** The javabean property equivalent of database column sales_order_status_hist.sales_order_status_id */
  private int salesOrderStatusId;
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



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public SalesOrderStatusHist() throws SystemException {
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
 * Sets the value of member variable salesOrderId
 *
 * @author Roy Terrell.
 */
  public void setSalesOrderId(int value) {
    this.salesOrderId = value;
  }
/**
 * Gets the value of member variable salesOrderId
 *
 * @author Roy Terrell.
 */
  public int getSalesOrderId() {
    return this.salesOrderId;
  }
/**
 * Sets the value of member variable salesOrderStatusId
 *
 * @author Roy Terrell.
 */
  public void setSalesOrderStatusId(int value) {
    this.salesOrderStatusId = value;
  }
/**
 * Gets the value of member variable salesOrderStatusId
 *
 * @author Roy Terrell.
 */
  public int getSalesOrderStatusId() {
    return this.salesOrderStatusId;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
  }
/**
 * Sets the value of member variable reason
 *
 * @author Roy Terrell.
 */
  public void setReason(String value) {
    this.reason = value;
  }
/**
 * Gets the value of member variable reason
 *
 * @author Roy Terrell.
 */
  public String getReason() {
    return this.reason;
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
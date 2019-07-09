package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the sales_order database table/view.
 *
 * @author Roy Terrell.
 */
public class SalesOrder extends OrmBean {

/** The javabean property equivalent of database column sales_order.id */
  private int id;
/** The javabean property equivalent of database column sales_order.customer_id */
  private int customerId;
/** The javabean property equivalent of database column sales_order.invoiced */
  private int invoiced;
/** The javabean property equivalent of database column sales_order.order_total */
  private double orderTotal;
/** The javabean property equivalent of database column sales_order.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column sales_order.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column sales_order.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public SalesOrder() throws SystemException {
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
 * Sets the value of member variable customerId
 *
 * @author Roy Terrell.
 */
  public void setCustomerId(int value) {
    this.customerId = value;
  }
/**
 * Gets the value of member variable customerId
 *
 * @author Roy Terrell.
 */
  public int getCustomerId() {
    return this.customerId;
  }
/**
 * Sets the value of member variable invoiced
 *
 * @author Roy Terrell.
 */
  public void setInvoiced(int value) {
    this.invoiced = value;
  }
/**
 * Gets the value of member variable invoiced
 *
 * @author Roy Terrell.
 */
  public int getInvoiced() {
    return this.invoiced;
  }
/**
 * Sets the value of member variable orderTotal
 *
 * @author Roy Terrell.
 */
  public void setOrderTotal(double value) {
    this.orderTotal = value;
  }
/**
 * Gets the value of member variable orderTotal
 *
 * @author Roy Terrell.
 */
  public double getOrderTotal() {
    return this.orderTotal;
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
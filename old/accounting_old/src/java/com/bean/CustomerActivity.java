package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the customer_activity database table/view.
 *
 * @author auto generated.
 */
public class CustomerActivity extends OrmBean {




	// Property name constants that belong to respective DataSource, CustomerActivityView

/** The property name constant equivalent to property, CustomerActvId, of respective DataSource view. */
  public static final String PROP_CUSTOMERACTVID = "CustomerActvId";
/** The property name constant equivalent to property, CustomerId, of respective DataSource view. */
  public static final String PROP_CUSTOMERID = "CustomerId";
/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, Amount, of respective DataSource view. */
  public static final String PROP_AMOUNT = "Amount";
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



	/** The javabean property equivalent of database column customer_activity.customer_actv_id */
  private int customerActvId;
/** The javabean property equivalent of database column customer_activity.customer_id */
  private int customerId;
/** The javabean property equivalent of database column customer_activity.xact_id */
  private int xactId;
/** The javabean property equivalent of database column customer_activity.amount */
  private double amount;
/** The javabean property equivalent of database column customer_activity.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column customer_activity.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column customer_activity.user_id */
  private String userId;
/** The javabean property equivalent of database column customer_activity.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column customer_activity.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public CustomerActivity() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable customerActvId
 *
 * @author auto generated.
 */
  public void setCustomerActvId(int value) {
    this.customerActvId = value;
  }
/**
 * Gets the value of member variable customerActvId
 *
 * @author atuo generated.
 */
  public int getCustomerActvId() {
    return this.customerActvId;
  }
/**
 * Sets the value of member variable customerId
 *
 * @author auto generated.
 */
  public void setCustomerId(int value) {
    this.customerId = value;
  }
/**
 * Gets the value of member variable customerId
 *
 * @author atuo generated.
 */
  public int getCustomerId() {
    return this.customerId;
  }
/**
 * Sets the value of member variable xactId
 *
 * @author auto generated.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author atuo generated.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable amount
 *
 * @author auto generated.
 */
  public void setAmount(double value) {
    this.amount = value;
  }
/**
 * Gets the value of member variable amount
 *
 * @author atuo generated.
 */
  public double getAmount() {
    return this.amount;
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
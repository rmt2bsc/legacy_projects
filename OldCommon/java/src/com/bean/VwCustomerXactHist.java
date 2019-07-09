package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_customer_xact_hist database table/view.
 *
 * @author Roy Terrell.
 */
public class VwCustomerXactHist extends OrmBean {

/** The javabean property equivalent of database column vw_customer_xact_hist.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_customer_xact_hist.customer_id */
  private int customerId;
/** The javabean property equivalent of database column vw_customer_xact_hist.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_customer_xact_hist.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_customer_xact_hist.account_name */
  private String accountName;
/** The javabean property equivalent of database column vw_customer_xact_hist.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_customer_xact_hist.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_customer_xact_hist.active */
  private int active;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_customer_xact_hist.reason */
  private String reason;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_type_name */
  private String xactTypeName;
/** The javabean property equivalent of database column vw_customer_xact_hist.customer_activity_id */
  private int customerActivityId;
/** The javabean property equivalent of database column vw_customer_xact_hist.customer_activity_amount */
  private double customerActivityAmount;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwCustomerXactHist() throws SystemException {
  }
/**
 * Sets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public int getXactId() {
    return this.xactId;
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
 * Sets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public int getPersonId() {
    return this.personId;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Sets the value of member variable accountName
 *
 * @author Roy Terrell.
 */
  public void setAccountName(String value) {
    this.accountName = value;
  }
/**
 * Gets the value of member variable accountName
 *
 * @author Roy Terrell.
 */
  public String getAccountName() {
    return this.accountName;
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
 * Sets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public void setCreditLimit(double value) {
    this.creditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public double getCreditLimit() {
    return this.creditLimit;
  }
/**
 * Sets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public int getActive() {
    return this.active;
  }
/**
 * Sets the value of member variable xactAmount
 *
 * @author Roy Terrell.
 */
  public void setXactAmount(double value) {
    this.xactAmount = value;
  }
/**
 * Gets the value of member variable xactAmount
 *
 * @author Roy Terrell.
 */
  public double getXactAmount() {
    return this.xactAmount;
  }
/**
 * Sets the value of member variable xactDate
 *
 * @author Roy Terrell.
 */
  public void setXactDate(java.util.Date value) {
    this.xactDate = value;
  }
/**
 * Gets the value of member variable xactDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getXactDate() {
    return this.xactDate;
  }
/**
 * Sets the value of member variable xactTypeId
 *
 * @author Roy Terrell.
 */
  public void setXactTypeId(int value) {
    this.xactTypeId = value;
  }
/**
 * Gets the value of member variable xactTypeId
 *
 * @author Roy Terrell.
 */
  public int getXactTypeId() {
    return this.xactTypeId;
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
 * Sets the value of member variable xactTypeName
 *
 * @author Roy Terrell.
 */
  public void setXactTypeName(String value) {
    this.xactTypeName = value;
  }
/**
 * Gets the value of member variable xactTypeName
 *
 * @author Roy Terrell.
 */
  public String getXactTypeName() {
    return this.xactTypeName;
  }
/**
 * Sets the value of member variable customerActivityId
 *
 * @author Roy Terrell.
 */
  public void setCustomerActivityId(int value) {
    this.customerActivityId = value;
  }
/**
 * Gets the value of member variable customerActivityId
 *
 * @author Roy Terrell.
 */
  public int getCustomerActivityId() {
    return this.customerActivityId;
  }
/**
 * Sets the value of member variable customerActivityAmount
 *
 * @author Roy Terrell.
 */
  public void setCustomerActivityAmount(double value) {
    this.customerActivityAmount = value;
  }
/**
 * Gets the value of member variable customerActivityAmount
 *
 * @author Roy Terrell.
 */
  public double getCustomerActivityAmount() {
    return this.customerActivityAmount;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_customer_name database table/view.
 *
 * @author Roy Terrell.
 */
public class VwCustomerName extends OrmBean {

/** The javabean property equivalent of database column vw_customer_name.customer_id */
  private int customerId;
/** The javabean property equivalent of database column vw_customer_name.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_customer_name.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column vw_customer_name.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_customer_name.active */
  private int active;
/** The javabean property equivalent of database column vw_customer_name.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_customer_name.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column vw_customer_name.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_customer_name.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_customer_name.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_customer_name.name */
  private String name;
/** The javabean property equivalent of database column vw_customer_name.balance */
  private double balance;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwCustomerName() throws SystemException {
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
 * Sets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
/**
 * Gets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public int getGlAccountId() {
    return this.glAccountId;
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
 * Sets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public void setBalance(double value) {
    this.balance = value;
  }
/**
 * Gets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public double getBalance() {
    return this.balance;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
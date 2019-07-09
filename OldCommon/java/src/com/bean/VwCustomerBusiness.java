package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_customer_business database table/view.
 *
 * @author Roy Terrell.
 */
public class VwCustomerBusiness extends OrmBean {

/** The javabean property equivalent of database column vw_customer_business.customer_id */
  private int customerId;
/** The javabean property equivalent of database column vw_customer_business.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_customer_business.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column vw_customer_business.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_customer_business.active */
  private int active;
/** The javabean property equivalent of database column vw_customer_business.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_customer_business.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column vw_customer_business.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_customer_business.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_customer_business.longname */
  private String longname;
/** The javabean property equivalent of database column vw_customer_business.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_customer_business.serv_type */
  private int servType;
/** The javabean property equivalent of database column vw_customer_business.bus_type */
  private int busType;
/** The javabean property equivalent of database column vw_customer_business.contact_firstname */
  private String contactFirstname;
/** The javabean property equivalent of database column vw_customer_business.contact_lastname */
  private String contactLastname;
/** The javabean property equivalent of database column vw_customer_business.contact_phone */
  private String contactPhone;
/** The javabean property equivalent of database column vw_customer_business.contact_ext */
  private String contactExt;
/** The javabean property equivalent of database column vw_customer_business.tax_id */
  private String taxId;
/** The javabean property equivalent of database column vw_customer_business.website */
  private String website;
/** The javabean property equivalent of database column vw_customer_business.name */
  private String name;
/** The javabean property equivalent of database column vw_customer_business.balance */
  private double balance;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwCustomerBusiness() throws SystemException {
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
 * Sets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public void setLongname(String value) {
    this.longname = value;
  }
/**
 * Gets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public String getLongname() {
    return this.longname;
  }
/**
 * Sets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public void setShortname(String value) {
    this.shortname = value;
  }
/**
 * Gets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public String getShortname() {
    return this.shortname;
  }
/**
 * Sets the value of member variable servType
 *
 * @author Roy Terrell.
 */
  public void setServType(int value) {
    this.servType = value;
  }
/**
 * Gets the value of member variable servType
 *
 * @author Roy Terrell.
 */
  public int getServType() {
    return this.servType;
  }
/**
 * Sets the value of member variable busType
 *
 * @author Roy Terrell.
 */
  public void setBusType(int value) {
    this.busType = value;
  }
/**
 * Gets the value of member variable busType
 *
 * @author Roy Terrell.
 */
  public int getBusType() {
    return this.busType;
  }
/**
 * Sets the value of member variable contactFirstname
 *
 * @author Roy Terrell.
 */
  public void setContactFirstname(String value) {
    this.contactFirstname = value;
  }
/**
 * Gets the value of member variable contactFirstname
 *
 * @author Roy Terrell.
 */
  public String getContactFirstname() {
    return this.contactFirstname;
  }
/**
 * Sets the value of member variable contactLastname
 *
 * @author Roy Terrell.
 */
  public void setContactLastname(String value) {
    this.contactLastname = value;
  }
/**
 * Gets the value of member variable contactLastname
 *
 * @author Roy Terrell.
 */
  public String getContactLastname() {
    return this.contactLastname;
  }
/**
 * Sets the value of member variable contactPhone
 *
 * @author Roy Terrell.
 */
  public void setContactPhone(String value) {
    this.contactPhone = value;
  }
/**
 * Gets the value of member variable contactPhone
 *
 * @author Roy Terrell.
 */
  public String getContactPhone() {
    return this.contactPhone;
  }
/**
 * Sets the value of member variable contactExt
 *
 * @author Roy Terrell.
 */
  public void setContactExt(String value) {
    this.contactExt = value;
  }
/**
 * Gets the value of member variable contactExt
 *
 * @author Roy Terrell.
 */
  public String getContactExt() {
    return this.contactExt;
  }
/**
 * Sets the value of member variable taxId
 *
 * @author Roy Terrell.
 */
  public void setTaxId(String value) {
    this.taxId = value;
  }
/**
 * Gets the value of member variable taxId
 *
 * @author Roy Terrell.
 */
  public String getTaxId() {
    return this.taxId;
  }
/**
 * Sets the value of member variable website
 *
 * @author Roy Terrell.
 */
  public void setWebsite(String value) {
    this.website = value;
  }
/**
 * Gets the value of member variable website
 *
 * @author Roy Terrell.
 */
  public String getWebsite() {
    return this.website;
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
package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the creditor database table/view.
 *
 * @author auto generated.
 */
public class Creditor extends OrmBean {




	// Property name constants that belong to respective DataSource, CreditorView

/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, CreditorTypeId, of respective DataSource view. */
  public static final String PROP_CREDITORTYPEID = "CreditorTypeId";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, AccountNumber, of respective DataSource view. */
  public static final String PROP_ACCOUNTNUMBER = "AccountNumber";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, Apr, of respective DataSource view. */
  public static final String PROP_APR = "Apr";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, ExtAccountNumber, of respective DataSource view. */
  public static final String PROP_EXTACCOUNTNUMBER = "ExtAccountNumber";
/** The property name constant equivalent to property, IpCreated, of respective DataSource view. */
  public static final String PROP_IPCREATED = "IpCreated";
/** The property name constant equivalent to property, IpUpdated, of respective DataSource view. */
  public static final String PROP_IPUPDATED = "IpUpdated";



	/** The javabean property equivalent of database column creditor.creditor_id */
  private int creditorId;
/** The javabean property equivalent of database column creditor.acct_id */
  private int acctId;
/** The javabean property equivalent of database column creditor.creditor_type_id */
  private int creditorTypeId;
/** The javabean property equivalent of database column creditor.business_id */
  private int businessId;
/** The javabean property equivalent of database column creditor.account_number */
  private String accountNumber;
/** The javabean property equivalent of database column creditor.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column creditor.apr */
  private double apr;
/** The javabean property equivalent of database column creditor.active */
  private int active;
/** The javabean property equivalent of database column creditor.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column creditor.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column creditor.user_id */
  private String userId;
/** The javabean property equivalent of database column creditor.ext_account_number */
  private String extAccountNumber;
/** The javabean property equivalent of database column creditor.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column creditor.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Creditor() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable creditorId
 *
 * @author auto generated.
 */
  public void setCreditorId(int value) {
    this.creditorId = value;
  }
/**
 * Gets the value of member variable creditorId
 *
 * @author atuo generated.
 */
  public int getCreditorId() {
    return this.creditorId;
  }
/**
 * Sets the value of member variable acctId
 *
 * @author auto generated.
 */
  public void setAcctId(int value) {
    this.acctId = value;
  }
/**
 * Gets the value of member variable acctId
 *
 * @author atuo generated.
 */
  public int getAcctId() {
    return this.acctId;
  }
/**
 * Sets the value of member variable creditorTypeId
 *
 * @author auto generated.
 */
  public void setCreditorTypeId(int value) {
    this.creditorTypeId = value;
  }
/**
 * Gets the value of member variable creditorTypeId
 *
 * @author atuo generated.
 */
  public int getCreditorTypeId() {
    return this.creditorTypeId;
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
 * Sets the value of member variable accountNumber
 *
 * @author auto generated.
 */
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }
/**
 * Gets the value of member variable accountNumber
 *
 * @author atuo generated.
 */
  public String getAccountNumber() {
    return this.accountNumber;
  }
/**
 * Sets the value of member variable creditLimit
 *
 * @author auto generated.
 */
  public void setCreditLimit(double value) {
    this.creditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 *
 * @author atuo generated.
 */
  public double getCreditLimit() {
    return this.creditLimit;
  }
/**
 * Sets the value of member variable apr
 *
 * @author auto generated.
 */
  public void setApr(double value) {
    this.apr = value;
  }
/**
 * Gets the value of member variable apr
 *
 * @author atuo generated.
 */
  public double getApr() {
    return this.apr;
  }
/**
 * Sets the value of member variable active
 *
 * @author auto generated.
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 *
 * @author atuo generated.
 */
  public int getActive() {
    return this.active;
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
 * Sets the value of member variable extAccountNumber
 *
 * @author auto generated.
 */
  public void setExtAccountNumber(String value) {
    this.extAccountNumber = value;
  }
/**
 * Gets the value of member variable extAccountNumber
 *
 * @author atuo generated.
 */
  public String getExtAccountNumber() {
    return this.extAccountNumber;
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
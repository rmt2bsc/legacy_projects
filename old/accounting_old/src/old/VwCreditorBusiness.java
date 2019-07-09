package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_creditor_business database table/view.
 *
 * @author auto generated.
 */
public class VwCreditorBusiness extends OrmBean {




	// Property name constants that belong to respective DataSource, VwCreditorBusinessView

/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, CreditorTypeId, of respective DataSource view. */
  public static final String PROP_CREDITORTYPEID = "CreditorTypeId";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, GlAccountId, of respective DataSource view. */
  public static final String PROP_GLACCOUNTID = "GlAccountId";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, Apr, of respective DataSource view. */
  public static final String PROP_APR = "Apr";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, CreditorTypeDescription, of respective DataSource view. */
  public static final String PROP_CREDITORTYPEDESCRIPTION = "CreditorTypeDescription";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Shortname, of respective DataSource view. */
  public static final String PROP_SHORTNAME = "Shortname";
/** The property name constant equivalent to property, ServType, of respective DataSource view. */
  public static final String PROP_SERVTYPE = "ServType";
/** The property name constant equivalent to property, BusType, of respective DataSource view. */
  public static final String PROP_BUSTYPE = "BusType";
/** The property name constant equivalent to property, ContactFirstname, of respective DataSource view. */
  public static final String PROP_CONTACTFIRSTNAME = "ContactFirstname";
/** The property name constant equivalent to property, ContactLastname, of respective DataSource view. */
  public static final String PROP_CONTACTLASTNAME = "ContactLastname";
/** The property name constant equivalent to property, ContactPhone, of respective DataSource view. */
  public static final String PROP_CONTACTPHONE = "ContactPhone";
/** The property name constant equivalent to property, ContactExt, of respective DataSource view. */
  public static final String PROP_CONTACTEXT = "ContactExt";
/** The property name constant equivalent to property, TaxId, of respective DataSource view. */
  public static final String PROP_TAXID = "TaxId";
/** The property name constant equivalent to property, Website, of respective DataSource view. */
  public static final String PROP_WEBSITE = "Website";
/** The property name constant equivalent to property, Balance, of respective DataSource view. */
  public static final String PROP_BALANCE = "Balance";



	/** The javabean property equivalent of database column vw_creditor_business.creditor_id */
  private int creditorId;
/** The javabean property equivalent of database column vw_creditor_business.creditor_type_id */
  private int creditorTypeId;
/** The javabean property equivalent of database column vw_creditor_business.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_creditor_business.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column vw_creditor_business.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_creditor_business.active */
  private int active;
/** The javabean property equivalent of database column vw_creditor_business.apr */
  private double apr;
/** The javabean property equivalent of database column vw_creditor_business.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_creditor_business.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column vw_creditor_business.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_creditor_business.creditor_type_description */
  private String creditorTypeDescription;
/** The javabean property equivalent of database column vw_creditor_business.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_creditor_business.name */
  private String name;
/** The javabean property equivalent of database column vw_creditor_business.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_creditor_business.serv_type */
  private int servType;
/** The javabean property equivalent of database column vw_creditor_business.bus_type */
  private int busType;
/** The javabean property equivalent of database column vw_creditor_business.contact_firstname */
  private String contactFirstname;
/** The javabean property equivalent of database column vw_creditor_business.contact_lastname */
  private String contactLastname;
/** The javabean property equivalent of database column vw_creditor_business.contact_phone */
  private String contactPhone;
/** The javabean property equivalent of database column vw_creditor_business.contact_ext */
  private String contactExt;
/** The javabean property equivalent of database column vw_creditor_business.tax_id */
  private String taxId;
/** The javabean property equivalent of database column vw_creditor_business.website */
  private String website;
/** The javabean property equivalent of database column vw_creditor_business.balance */
  private double balance;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwCreditorBusiness() throws SystemException {
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
 * Sets the value of member variable accountNo
 *
 * @author auto generated.
 */
  public void setAccountNo(String value) {
    this.accountNo = value;
  }
/**
 * Gets the value of member variable accountNo
 *
 * @author atuo generated.
 */
  public String getAccountNo() {
    return this.accountNo;
  }
/**
 * Sets the value of member variable glAccountId
 *
 * @author auto generated.
 */
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
/**
 * Gets the value of member variable glAccountId
 *
 * @author atuo generated.
 */
  public int getGlAccountId() {
    return this.glAccountId;
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
 * Sets the value of member variable creditorTypeDescription
 *
 * @author auto generated.
 */
  public void setCreditorTypeDescription(String value) {
    this.creditorTypeDescription = value;
  }
/**
 * Gets the value of member variable creditorTypeDescription
 *
 * @author atuo generated.
 */
  public String getCreditorTypeDescription() {
    return this.creditorTypeDescription;
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
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable shortname
 *
 * @author auto generated.
 */
  public void setShortname(String value) {
    this.shortname = value;
  }
/**
 * Gets the value of member variable shortname
 *
 * @author atuo generated.
 */
  public String getShortname() {
    return this.shortname;
  }
/**
 * Sets the value of member variable servType
 *
 * @author auto generated.
 */
  public void setServType(int value) {
    this.servType = value;
  }
/**
 * Gets the value of member variable servType
 *
 * @author atuo generated.
 */
  public int getServType() {
    return this.servType;
  }
/**
 * Sets the value of member variable busType
 *
 * @author auto generated.
 */
  public void setBusType(int value) {
    this.busType = value;
  }
/**
 * Gets the value of member variable busType
 *
 * @author atuo generated.
 */
  public int getBusType() {
    return this.busType;
  }
/**
 * Sets the value of member variable contactFirstname
 *
 * @author auto generated.
 */
  public void setContactFirstname(String value) {
    this.contactFirstname = value;
  }
/**
 * Gets the value of member variable contactFirstname
 *
 * @author atuo generated.
 */
  public String getContactFirstname() {
    return this.contactFirstname;
  }
/**
 * Sets the value of member variable contactLastname
 *
 * @author auto generated.
 */
  public void setContactLastname(String value) {
    this.contactLastname = value;
  }
/**
 * Gets the value of member variable contactLastname
 *
 * @author atuo generated.
 */
  public String getContactLastname() {
    return this.contactLastname;
  }
/**
 * Sets the value of member variable contactPhone
 *
 * @author auto generated.
 */
  public void setContactPhone(String value) {
    this.contactPhone = value;
  }
/**
 * Gets the value of member variable contactPhone
 *
 * @author atuo generated.
 */
  public String getContactPhone() {
    return this.contactPhone;
  }
/**
 * Sets the value of member variable contactExt
 *
 * @author auto generated.
 */
  public void setContactExt(String value) {
    this.contactExt = value;
  }
/**
 * Gets the value of member variable contactExt
 *
 * @author atuo generated.
 */
  public String getContactExt() {
    return this.contactExt;
  }
/**
 * Sets the value of member variable taxId
 *
 * @author auto generated.
 */
  public void setTaxId(String value) {
    this.taxId = value;
  }
/**
 * Gets the value of member variable taxId
 *
 * @author atuo generated.
 */
  public String getTaxId() {
    return this.taxId;
  }
/**
 * Sets the value of member variable website
 *
 * @author auto generated.
 */
  public void setWebsite(String value) {
    this.website = value;
  }
/**
 * Gets the value of member variable website
 *
 * @author atuo generated.
 */
  public String getWebsite() {
    return this.website;
  }
/**
 * Sets the value of member variable balance
 *
 * @author auto generated.
 */
  public void setBalance(double value) {
    this.balance = value;
  }
/**
 * Gets the value of member variable balance
 *
 * @author atuo generated.
 */
  public double getBalance() {
    return this.balance;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
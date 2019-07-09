package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_customer_person database table/view.
 *
 * @author auto generated.
 */
public class VwCustomerPerson extends OrmBean {




	// Property name constants that belong to respective DataSource, VwCustomerPersonView

/** The property name constant equivalent to property, CustomerId, of respective DataSource view. */
  public static final String PROP_CUSTOMERID = "CustomerId";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, GlAccountId, of respective DataSource view. */
  public static final String PROP_GLACCOUNTID = "GlAccountId";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, PersonId, of respective DataSource view. */
  public static final String PROP_PERSONID = "PersonId";
/** The property name constant equivalent to property, Title, of respective DataSource view. */
  public static final String PROP_TITLE = "Title";
/** The property name constant equivalent to property, Firstname, of respective DataSource view. */
  public static final String PROP_FIRSTNAME = "Firstname";
/** The property name constant equivalent to property, Midname, of respective DataSource view. */
  public static final String PROP_MIDNAME = "Midname";
/** The property name constant equivalent to property, Lastname, of respective DataSource view. */
  public static final String PROP_LASTNAME = "Lastname";
/** The property name constant equivalent to property, Maidenname, of respective DataSource view. */
  public static final String PROP_MAIDENNAME = "Maidenname";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Generation, of respective DataSource view. */
  public static final String PROP_GENERATION = "Generation";
/** The property name constant equivalent to property, BirthDate, of respective DataSource view. */
  public static final String PROP_BIRTHDATE = "BirthDate";
/** The property name constant equivalent to property, GenderId, of respective DataSource view. */
  public static final String PROP_GENDERID = "GenderId";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
/** The property name constant equivalent to property, Ssn, of respective DataSource view. */
  public static final String PROP_SSN = "Ssn";
/** The property name constant equivalent to property, MaritalStatus, of respective DataSource view. */
  public static final String PROP_MARITALSTATUS = "MaritalStatus";
/** The property name constant equivalent to property, RaceId, of respective DataSource view. */
  public static final String PROP_RACEID = "RaceId";
/** The property name constant equivalent to property, Balance, of respective DataSource view. */
  public static final String PROP_BALANCE = "Balance";



	/** The javabean property equivalent of database column vw_customer_person.customer_id */
  private int customerId;
/** The javabean property equivalent of database column vw_customer_person.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_customer_person.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column vw_customer_person.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_customer_person.active */
  private int active;
/** The javabean property equivalent of database column vw_customer_person.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_customer_person.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column vw_customer_person.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_customer_person.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_customer_person.title */
  private int title;
/** The javabean property equivalent of database column vw_customer_person.firstname */
  private String firstname;
/** The javabean property equivalent of database column vw_customer_person.midname */
  private String midname;
/** The javabean property equivalent of database column vw_customer_person.lastname */
  private String lastname;
/** The javabean property equivalent of database column vw_customer_person.maidenname */
  private String maidenname;
/** The javabean property equivalent of database column vw_customer_person.name */
  private String name;
/** The javabean property equivalent of database column vw_customer_person.generation */
  private String generation;
/** The javabean property equivalent of database column vw_customer_person.birth_date */
  private java.util.Date birthDate;
/** The javabean property equivalent of database column vw_customer_person.gender_id */
  private int genderId;
/** The javabean property equivalent of database column vw_customer_person.email */
  private String email;
/** The javabean property equivalent of database column vw_customer_person.ssn */
  private String ssn;
/** The javabean property equivalent of database column vw_customer_person.marital_status */
  private int maritalStatus;
/** The javabean property equivalent of database column vw_customer_person.race_id */
  private int raceId;
/** The javabean property equivalent of database column vw_customer_person.balance */
  private double balance;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwCustomerPerson() throws SystemException {
	super();
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
 * Sets the value of member variable personId
 *
 * @author auto generated.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author atuo generated.
 */
  public int getPersonId() {
    return this.personId;
  }
/**
 * Sets the value of member variable title
 *
 * @author auto generated.
 */
  public void setTitle(int value) {
    this.title = value;
  }
/**
 * Gets the value of member variable title
 *
 * @author atuo generated.
 */
  public int getTitle() {
    return this.title;
  }
/**
 * Sets the value of member variable firstname
 *
 * @author auto generated.
 */
  public void setFirstname(String value) {
    this.firstname = value;
  }
/**
 * Gets the value of member variable firstname
 *
 * @author atuo generated.
 */
  public String getFirstname() {
    return this.firstname;
  }
/**
 * Sets the value of member variable midname
 *
 * @author auto generated.
 */
  public void setMidname(String value) {
    this.midname = value;
  }
/**
 * Gets the value of member variable midname
 *
 * @author atuo generated.
 */
  public String getMidname() {
    return this.midname;
  }
/**
 * Sets the value of member variable lastname
 *
 * @author auto generated.
 */
  public void setLastname(String value) {
    this.lastname = value;
  }
/**
 * Gets the value of member variable lastname
 *
 * @author atuo generated.
 */
  public String getLastname() {
    return this.lastname;
  }
/**
 * Sets the value of member variable maidenname
 *
 * @author auto generated.
 */
  public void setMaidenname(String value) {
    this.maidenname = value;
  }
/**
 * Gets the value of member variable maidenname
 *
 * @author atuo generated.
 */
  public String getMaidenname() {
    return this.maidenname;
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
 * Sets the value of member variable generation
 *
 * @author auto generated.
 */
  public void setGeneration(String value) {
    this.generation = value;
  }
/**
 * Gets the value of member variable generation
 *
 * @author atuo generated.
 */
  public String getGeneration() {
    return this.generation;
  }
/**
 * Sets the value of member variable birthDate
 *
 * @author auto generated.
 */
  public void setBirthDate(java.util.Date value) {
    this.birthDate = value;
  }
/**
 * Gets the value of member variable birthDate
 *
 * @author atuo generated.
 */
  public java.util.Date getBirthDate() {
    return this.birthDate;
  }
/**
 * Sets the value of member variable genderId
 *
 * @author auto generated.
 */
  public void setGenderId(int value) {
    this.genderId = value;
  }
/**
 * Gets the value of member variable genderId
 *
 * @author atuo generated.
 */
  public int getGenderId() {
    return this.genderId;
  }
/**
 * Sets the value of member variable email
 *
 * @author auto generated.
 */
  public void setEmail(String value) {
    this.email = value;
  }
/**
 * Gets the value of member variable email
 *
 * @author atuo generated.
 */
  public String getEmail() {
    return this.email;
  }
/**
 * Sets the value of member variable ssn
 *
 * @author auto generated.
 */
  public void setSsn(String value) {
    this.ssn = value;
  }
/**
 * Gets the value of member variable ssn
 *
 * @author atuo generated.
 */
  public String getSsn() {
    return this.ssn;
  }
/**
 * Sets the value of member variable maritalStatus
 *
 * @author auto generated.
 */
  public void setMaritalStatus(int value) {
    this.maritalStatus = value;
  }
/**
 * Gets the value of member variable maritalStatus
 *
 * @author atuo generated.
 */
  public int getMaritalStatus() {
    return this.maritalStatus;
  }
/**
 * Sets the value of member variable raceId
 *
 * @author auto generated.
 */
  public void setRaceId(int value) {
    this.raceId = value;
  }
/**
 * Gets the value of member variable raceId
 *
 * @author atuo generated.
 */
  public int getRaceId() {
    return this.raceId;
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
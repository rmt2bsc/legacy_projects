package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_customer_person database table/view.
 *
 * @author Roy Terrell.
 */
public class VwCustomerPerson extends OrmBean {

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
/** The javabean property equivalent of database column vw_customer_person.shortname */
  private String shortname;
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
/** The javabean property equivalent of database column vw_customer_person.name */
  private String name;
/** The javabean property equivalent of database column vw_customer_person.balance */
  private double balance;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwCustomerPerson() throws SystemException {
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
 * Sets the value of member variable title
 *
 * @author Roy Terrell.
 */
  public void setTitle(int value) {
    this.title = value;
  }
/**
 * Gets the value of member variable title
 *
 * @author Roy Terrell.
 */
  public int getTitle() {
    return this.title;
  }
/**
 * Sets the value of member variable firstname
 *
 * @author Roy Terrell.
 */
  public void setFirstname(String value) {
    this.firstname = value;
  }
/**
 * Gets the value of member variable firstname
 *
 * @author Roy Terrell.
 */
  public String getFirstname() {
    return this.firstname;
  }
/**
 * Sets the value of member variable midname
 *
 * @author Roy Terrell.
 */
  public void setMidname(String value) {
    this.midname = value;
  }
/**
 * Gets the value of member variable midname
 *
 * @author Roy Terrell.
 */
  public String getMidname() {
    return this.midname;
  }
/**
 * Sets the value of member variable lastname
 *
 * @author Roy Terrell.
 */
  public void setLastname(String value) {
    this.lastname = value;
  }
/**
 * Gets the value of member variable lastname
 *
 * @author Roy Terrell.
 */
  public String getLastname() {
    return this.lastname;
  }
/**
 * Sets the value of member variable maidenname
 *
 * @author Roy Terrell.
 */
  public void setMaidenname(String value) {
    this.maidenname = value;
  }
/**
 * Gets the value of member variable maidenname
 *
 * @author Roy Terrell.
 */
  public String getMaidenname() {
    return this.maidenname;
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
 * Sets the value of member variable generation
 *
 * @author Roy Terrell.
 */
  public void setGeneration(String value) {
    this.generation = value;
  }
/**
 * Gets the value of member variable generation
 *
 * @author Roy Terrell.
 */
  public String getGeneration() {
    return this.generation;
  }
/**
 * Sets the value of member variable birthDate
 *
 * @author Roy Terrell.
 */
  public void setBirthDate(java.util.Date value) {
    this.birthDate = value;
  }
/**
 * Gets the value of member variable birthDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getBirthDate() {
    return this.birthDate;
  }
/**
 * Sets the value of member variable genderId
 *
 * @author Roy Terrell.
 */
  public void setGenderId(int value) {
    this.genderId = value;
  }
/**
 * Gets the value of member variable genderId
 *
 * @author Roy Terrell.
 */
  public int getGenderId() {
    return this.genderId;
  }
/**
 * Sets the value of member variable email
 *
 * @author Roy Terrell.
 */
  public void setEmail(String value) {
    this.email = value;
  }
/**
 * Gets the value of member variable email
 *
 * @author Roy Terrell.
 */
  public String getEmail() {
    return this.email;
  }
/**
 * Sets the value of member variable ssn
 *
 * @author Roy Terrell.
 */
  public void setSsn(String value) {
    this.ssn = value;
  }
/**
 * Gets the value of member variable ssn
 *
 * @author Roy Terrell.
 */
  public String getSsn() {
    return this.ssn;
  }
/**
 * Sets the value of member variable maritalStatus
 *
 * @author Roy Terrell.
 */
  public void setMaritalStatus(int value) {
    this.maritalStatus = value;
  }
/**
 * Gets the value of member variable maritalStatus
 *
 * @author Roy Terrell.
 */
  public int getMaritalStatus() {
    return this.maritalStatus;
  }
/**
 * Sets the value of member variable raceId
 *
 * @author Roy Terrell.
 */
  public void setRaceId(int value) {
    this.raceId = value;
  }
/**
 * Gets the value of member variable raceId
 *
 * @author Roy Terrell.
 */
  public int getRaceId() {
    return this.raceId;
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
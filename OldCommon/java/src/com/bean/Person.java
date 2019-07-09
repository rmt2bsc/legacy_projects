package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the person database table/view.
 *
 * @author Roy Terrell.
 */
public class Person extends OrmBean {

/** The javabean property equivalent of database column person.id */
  private int id;
/** The javabean property equivalent of database column person.firstname */
  private String firstname;
/** The javabean property equivalent of database column person.midname */
  private String midname;
/** The javabean property equivalent of database column person.lastname */
  private String lastname;
/** The javabean property equivalent of database column person.maidenname */
  private String maidenname;
/** The javabean property equivalent of database column person.generation */
  private String generation;
/** The javabean property equivalent of database column person.shortname */
  private String shortname;
/** The javabean property equivalent of database column person.title */
  private int title;
/** The javabean property equivalent of database column person.gender_id */
  private int genderId;
/** The javabean property equivalent of database column person.marital_status */
  private int maritalStatus;
/** The javabean property equivalent of database column person.birth_date */
  private java.util.Date birthDate;
/** The javabean property equivalent of database column person.race_id */
  private int raceId;
/** The javabean property equivalent of database column person.ssn */
  private String ssn;
/** The javabean property equivalent of database column person.email */
  private String email;
/** The javabean property equivalent of database column person.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column person.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column person.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public Person() throws SystemException {
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
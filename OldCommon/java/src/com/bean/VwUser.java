package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_user database table/view.
 *
 * @author Roy Terrell.
 */
public class VwUser extends OrmBean {

/** The javabean property equivalent of database column vw_user.login_description */
  private String loginDescription;
/** The javabean property equivalent of database column vw_user.employee_id */
  private int employeeId;
/** The javabean property equivalent of database column vw_user.id */
  private int id;
/** The javabean property equivalent of database column vw_user.login */
  private String login;
/** The javabean property equivalent of database column vw_user.password */
  private String password;
/** The javabean property equivalent of database column vw_user.total_logons */
  private int totalLogons;
/** The javabean property equivalent of database column vw_user.active */
  private int active;
/** The javabean property equivalent of database column vw_user.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_user.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column vw_user.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_user.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_user.start_date */
  private java.util.Date startDate;
/** The javabean property equivalent of database column vw_user.termination_date */
  private java.util.Date terminationDate;
/** The javabean property equivalent of database column vw_user.title_id */
  private int titleId;
/** The javabean property equivalent of database column vw_user.type_id */
  private int typeId;
/** The javabean property equivalent of database column vw_user.bill_rate */
  private double billRate;
/** The javabean property equivalent of database column vw_user.ot_bill_rate */
  private double otBillRate;
/** The javabean property equivalent of database column vw_user.firstname */
  private String firstname;
/** The javabean property equivalent of database column vw_user.midname */
  private String midname;
/** The javabean property equivalent of database column vw_user.lastname */
  private String lastname;
/** The javabean property equivalent of database column vw_user.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_user.generation */
  private String generation;
/** The javabean property equivalent of database column vw_user.maidenname */
  private String maidenname;
/** The javabean property equivalent of database column vw_user.title */
  private int title;
/** The javabean property equivalent of database column vw_user.per_title_name */
  private String perTitleName;
/** The javabean property equivalent of database column vw_user.email */
  private String email;
/** The javabean property equivalent of database column vw_user.gender_id */
  private int genderId;
/** The javabean property equivalent of database column vw_user.gender_name */
  private String genderName;
/** The javabean property equivalent of database column vw_user.marital_status */
  private int maritalStatus;
/** The javabean property equivalent of database column vw_user.marital_status_name */
  private String maritalStatusName;
/** The javabean property equivalent of database column vw_user.race_id */
  private int raceId;
/** The javabean property equivalent of database column vw_user.race_name */
  private String raceName;
/** The javabean property equivalent of database column vw_user.ssn */
  private String ssn;
/** The javabean property equivalent of database column vw_user.birth_date */
  private java.util.Date birthDate;
/** The javabean property equivalent of database column vw_user.employee_title */
  private String employeeTitle;
/** The javabean property equivalent of database column vw_user.employee_type */
  private String employeeType;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwUser() throws SystemException {
  }
/**
 * Sets the value of member variable loginDescription
 *
 * @author Roy Terrell.
 */
  public void setLoginDescription(String value) {
    this.loginDescription = value;
  }
/**
 * Gets the value of member variable loginDescription
 *
 * @author Roy Terrell.
 */
  public String getLoginDescription() {
    return this.loginDescription;
  }
/**
 * Sets the value of member variable employeeId
 *
 * @author Roy Terrell.
 */
  public void setEmployeeId(int value) {
    this.employeeId = value;
  }
/**
 * Gets the value of member variable employeeId
 *
 * @author Roy Terrell.
 */
  public int getEmployeeId() {
    return this.employeeId;
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
 * Sets the value of member variable login
 *
 * @author Roy Terrell.
 */
  public void setLogin(String value) {
    this.login = value;
  }
/**
 * Gets the value of member variable login
 *
 * @author Roy Terrell.
 */
  public String getLogin() {
    return this.login;
  }
/**
 * Sets the value of member variable password
 *
 * @author Roy Terrell.
 */
  public void setPassword(String value) {
    this.password = value;
  }
/**
 * Gets the value of member variable password
 *
 * @author Roy Terrell.
 */
  public String getPassword() {
    return this.password;
  }
/**
 * Sets the value of member variable totalLogons
 *
 * @author Roy Terrell.
 */
  public void setTotalLogons(int value) {
    this.totalLogons = value;
  }
/**
 * Gets the value of member variable totalLogons
 *
 * @author Roy Terrell.
 */
  public int getTotalLogons() {
    return this.totalLogons;
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
 * Sets the value of member variable startDate
 *
 * @author Roy Terrell.
 */
  public void setStartDate(java.util.Date value) {
    this.startDate = value;
  }
/**
 * Gets the value of member variable startDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getStartDate() {
    return this.startDate;
  }
/**
 * Sets the value of member variable terminationDate
 *
 * @author Roy Terrell.
 */
  public void setTerminationDate(java.util.Date value) {
    this.terminationDate = value;
  }
/**
 * Gets the value of member variable terminationDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getTerminationDate() {
    return this.terminationDate;
  }
/**
 * Sets the value of member variable titleId
 *
 * @author Roy Terrell.
 */
  public void setTitleId(int value) {
    this.titleId = value;
  }
/**
 * Gets the value of member variable titleId
 *
 * @author Roy Terrell.
 */
  public int getTitleId() {
    return this.titleId;
  }
/**
 * Sets the value of member variable typeId
 *
 * @author Roy Terrell.
 */
  public void setTypeId(int value) {
    this.typeId = value;
  }
/**
 * Gets the value of member variable typeId
 *
 * @author Roy Terrell.
 */
  public int getTypeId() {
    return this.typeId;
  }
/**
 * Sets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public void setBillRate(double value) {
    this.billRate = value;
  }
/**
 * Gets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public double getBillRate() {
    return this.billRate;
  }
/**
 * Sets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public void setOtBillRate(double value) {
    this.otBillRate = value;
  }
/**
 * Gets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public double getOtBillRate() {
    return this.otBillRate;
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
 * Sets the value of member variable perTitleName
 *
 * @author Roy Terrell.
 */
  public void setPerTitleName(String value) {
    this.perTitleName = value;
  }
/**
 * Gets the value of member variable perTitleName
 *
 * @author Roy Terrell.
 */
  public String getPerTitleName() {
    return this.perTitleName;
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
 * Sets the value of member variable genderName
 *
 * @author Roy Terrell.
 */
  public void setGenderName(String value) {
    this.genderName = value;
  }
/**
 * Gets the value of member variable genderName
 *
 * @author Roy Terrell.
 */
  public String getGenderName() {
    return this.genderName;
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
 * Sets the value of member variable maritalStatusName
 *
 * @author Roy Terrell.
 */
  public void setMaritalStatusName(String value) {
    this.maritalStatusName = value;
  }
/**
 * Gets the value of member variable maritalStatusName
 *
 * @author Roy Terrell.
 */
  public String getMaritalStatusName() {
    return this.maritalStatusName;
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
 * Sets the value of member variable raceName
 *
 * @author Roy Terrell.
 */
  public void setRaceName(String value) {
    this.raceName = value;
  }
/**
 * Gets the value of member variable raceName
 *
 * @author Roy Terrell.
 */
  public String getRaceName() {
    return this.raceName;
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
 * Sets the value of member variable employeeTitle
 *
 * @author Roy Terrell.
 */
  public void setEmployeeTitle(String value) {
    this.employeeTitle = value;
  }
/**
 * Gets the value of member variable employeeTitle
 *
 * @author Roy Terrell.
 */
  public String getEmployeeTitle() {
    return this.employeeTitle;
  }
/**
 * Sets the value of member variable employeeType
 *
 * @author Roy Terrell.
 */
  public void setEmployeeType(String value) {
    this.employeeType = value;
  }
/**
 * Gets the value of member variable employeeType
 *
 * @author Roy Terrell.
 */
  public String getEmployeeType() {
    return this.employeeType;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
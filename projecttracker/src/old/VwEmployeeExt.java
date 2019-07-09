package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_employee_ext database table/view.
 *
 * @author Roy Terrell.
 */
public class VwEmployeeExt extends OrmBean {




	// Property name constants that belong to respective DataSource, VwEmployeeExtView

/** The property name constant equivalent to property, EmployeeId, of respective DataSource view. */
  public static final String PROP_EMPLOYEEID = "EmployeeId";
/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, PersonId, of respective DataSource view. */
  public static final String PROP_PERSONID = "PersonId";
/** The property name constant equivalent to property, StartDate, of respective DataSource view. */
  public static final String PROP_STARTDATE = "StartDate";
/** The property name constant equivalent to property, TerminationDate, of respective DataSource view. */
  public static final String PROP_TERMINATIONDATE = "TerminationDate";
/** The property name constant equivalent to property, TitleId, of respective DataSource view. */
  public static final String PROP_TITLEID = "TitleId";
/** The property name constant equivalent to property, TypeId, of respective DataSource view. */
  public static final String PROP_TYPEID = "TypeId";
/** The property name constant equivalent to property, ManagerId, of respective DataSource view. */
  public static final String PROP_MANAGERID = "ManagerId";
/** The property name constant equivalent to property, BillRate, of respective DataSource view. */
  public static final String PROP_BILLRATE = "BillRate";
/** The property name constant equivalent to property, OtBillRate, of respective DataSource view. */
  public static final String PROP_OTBILLRATE = "OtBillRate";
/** The property name constant equivalent to property, Firstname, of respective DataSource view. */
  public static final String PROP_FIRSTNAME = "Firstname";
/** The property name constant equivalent to property, Midname, of respective DataSource view. */
  public static final String PROP_MIDNAME = "Midname";
/** The property name constant equivalent to property, Lastname, of respective DataSource view. */
  public static final String PROP_LASTNAME = "Lastname";
/** The property name constant equivalent to property, Shortname, of respective DataSource view. */
  public static final String PROP_SHORTNAME = "Shortname";
/** The property name constant equivalent to property, Generation, of respective DataSource view. */
  public static final String PROP_GENERATION = "Generation";
/** The property name constant equivalent to property, Maidenname, of respective DataSource view. */
  public static final String PROP_MAIDENNAME = "Maidenname";
/** The property name constant equivalent to property, Title, of respective DataSource view. */
  public static final String PROP_TITLE = "Title";
/** The property name constant equivalent to property, PerTitleName, of respective DataSource view. */
  public static final String PROP_PERTITLENAME = "PerTitleName";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
/** The property name constant equivalent to property, GenderId, of respective DataSource view. */
  public static final String PROP_GENDERID = "GenderId";
/** The property name constant equivalent to property, GenderName, of respective DataSource view. */
  public static final String PROP_GENDERNAME = "GenderName";
/** The property name constant equivalent to property, MaritalStatus, of respective DataSource view. */
  public static final String PROP_MARITALSTATUS = "MaritalStatus";
/** The property name constant equivalent to property, MaritalStatusName, of respective DataSource view. */
  public static final String PROP_MARITALSTATUSNAME = "MaritalStatusName";
/** The property name constant equivalent to property, RaceId, of respective DataSource view. */
  public static final String PROP_RACEID = "RaceId";
/** The property name constant equivalent to property, RaceName, of respective DataSource view. */
  public static final String PROP_RACENAME = "RaceName";
/** The property name constant equivalent to property, Ssn, of respective DataSource view. */
  public static final String PROP_SSN = "Ssn";
/** The property name constant equivalent to property, BirthDate, of respective DataSource view. */
  public static final String PROP_BIRTHDATE = "BirthDate";
/** The property name constant equivalent to property, EmployeeTitle, of respective DataSource view. */
  public static final String PROP_EMPLOYEETITLE = "EmployeeTitle";
/** The property name constant equivalent to property, EmployeeType, of respective DataSource view. */
  public static final String PROP_EMPLOYEETYPE = "EmployeeType";



	/** The javabean property equivalent of database column vw_employee_ext.employee_id */
  private int employeeId;
/** The javabean property equivalent of database column vw_employee_ext.login_id */
  private String loginId;
/** The javabean property equivalent of database column vw_employee_ext.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_employee_ext.start_date */
  private java.util.Date startDate;
/** The javabean property equivalent of database column vw_employee_ext.termination_date */
  private java.util.Date terminationDate;
/** The javabean property equivalent of database column vw_employee_ext.title_id */
  private int titleId;
/** The javabean property equivalent of database column vw_employee_ext.type_id */
  private int typeId;
/** The javabean property equivalent of database column vw_employee_ext.manager_id */
  private int managerId;
/** The javabean property equivalent of database column vw_employee_ext.bill_rate */
  private double billRate;
/** The javabean property equivalent of database column vw_employee_ext.ot_bill_rate */
  private double otBillRate;
/** The javabean property equivalent of database column vw_employee_ext.firstname */
  private String firstname;
/** The javabean property equivalent of database column vw_employee_ext.midname */
  private String midname;
/** The javabean property equivalent of database column vw_employee_ext.lastname */
  private String lastname;
/** The javabean property equivalent of database column vw_employee_ext.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_employee_ext.generation */
  private String generation;
/** The javabean property equivalent of database column vw_employee_ext.maidenname */
  private String maidenname;
/** The javabean property equivalent of database column vw_employee_ext.title */
  private int title;
/** The javabean property equivalent of database column vw_employee_ext.per_title_name */
  private String perTitleName;
/** The javabean property equivalent of database column vw_employee_ext.email */
  private String email;
/** The javabean property equivalent of database column vw_employee_ext.gender_id */
  private int genderId;
/** The javabean property equivalent of database column vw_employee_ext.gender_name */
  private String genderName;
/** The javabean property equivalent of database column vw_employee_ext.marital_status */
  private int maritalStatus;
/** The javabean property equivalent of database column vw_employee_ext.marital_status_name */
  private String maritalStatusName;
/** The javabean property equivalent of database column vw_employee_ext.race_id */
  private int raceId;
/** The javabean property equivalent of database column vw_employee_ext.race_name */
  private String raceName;
/** The javabean property equivalent of database column vw_employee_ext.ssn */
  private String ssn;
/** The javabean property equivalent of database column vw_employee_ext.birth_date */
  private java.util.Date birthDate;
/** The javabean property equivalent of database column vw_employee_ext.employee_title */
  private String employeeTitle;
/** The javabean property equivalent of database column vw_employee_ext.employee_type */
  private String employeeType;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwEmployeeExt() throws SystemException {
	super();
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
 * Sets the value of member variable loginId
 *
 * @author Roy Terrell.
 */
  public void setLoginId(String value) {
    this.loginId = value;
  }
/**
 * Gets the value of member variable loginId
 *
 * @author Roy Terrell.
 */
  public String getLoginId() {
    return this.loginId;
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
 * Sets the value of member variable managerId
 *
 * @author Roy Terrell.
 */
  public void setManagerId(int value) {
    this.managerId = value;
  }
/**
 * Gets the value of member variable managerId
 *
 * @author Roy Terrell.
 */
  public int getManagerId() {
    return this.managerId;
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
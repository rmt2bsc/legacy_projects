package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_login database table/view.
 *
 * @author auto generated.
 */
public class UserLogin extends OrmBean {




	// Property name constants that belong to respective DataSource, UserLoginView

/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, GrpId, of respective DataSource view. */
  public static final String PROP_GRPID = "GrpId";
/** The property name constant equivalent to property, Username, of respective DataSource view. */
  public static final String PROP_USERNAME = "Username";
/** The property name constant equivalent to property, Firstname, of respective DataSource view. */
  public static final String PROP_FIRSTNAME = "Firstname";
/** The property name constant equivalent to property, Lastname, of respective DataSource view. */
  public static final String PROP_LASTNAME = "Lastname";
/** The property name constant equivalent to property, BirthDate, of respective DataSource view. */
  public static final String PROP_BIRTHDATE = "BirthDate";
/** The property name constant equivalent to property, Ssn, of respective DataSource view. */
  public static final String PROP_SSN = "Ssn";
/** The property name constant equivalent to property, StartDate, of respective DataSource view. */
  public static final String PROP_STARTDATE = "StartDate";
/** The property name constant equivalent to property, TerminationDate, of respective DataSource view. */
  public static final String PROP_TERMINATIONDATE = "TerminationDate";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, Password, of respective DataSource view. */
  public static final String PROP_PASSWORD = "Password";
/** The property name constant equivalent to property, TotalLogons, of respective DataSource view. */
  public static final String PROP_TOTALLOGONS = "TotalLogons";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, LoggedIn, of respective DataSource view. */
  public static final String PROP_LOGGEDIN = "LoggedIn";



	/** The javabean property equivalent of database column user_login.login_id */
  private int loginId;
/** The javabean property equivalent of database column user_login.grp_id */
  private int grpId;
/** The javabean property equivalent of database column user_login.username */
  private String username;
/** The javabean property equivalent of database column user_login.firstname */
  private String firstname;
/** The javabean property equivalent of database column user_login.lastname */
  private String lastname;
/** The javabean property equivalent of database column user_login.birth_date */
  private java.util.Date birthDate;
/** The javabean property equivalent of database column user_login.ssn */
  private String ssn;
/** The javabean property equivalent of database column user_login.start_date */
  private java.util.Date startDate;
/** The javabean property equivalent of database column user_login.termination_date */
  private java.util.Date terminationDate;
/** The javabean property equivalent of database column user_login.description */
  private String description;
/** The javabean property equivalent of database column user_login.password */
  private String password;
/** The javabean property equivalent of database column user_login.total_logons */
  private int totalLogons;
/** The javabean property equivalent of database column user_login.email */
  private String email;
/** The javabean property equivalent of database column user_login.active */
  private int active;
/** The javabean property equivalent of database column user_login.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column user_login.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column user_login.user_id */
  private String userId;
/** The javabean property equivalent of database column user_login.logged_in */
  private int loggedIn;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public UserLogin() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable loginId
 *
 * @author auto generated.
 */
  public void setLoginId(int value) {
    this.loginId = value;
  }
/**
 * Gets the value of member variable loginId
 *
 * @author atuo generated.
 */
  public int getLoginId() {
    return this.loginId;
  }
/**
 * Sets the value of member variable grpId
 *
 * @author auto generated.
 */
  public void setGrpId(int value) {
    this.grpId = value;
  }
/**
 * Gets the value of member variable grpId
 *
 * @author atuo generated.
 */
  public int getGrpId() {
    return this.grpId;
  }
/**
 * Sets the value of member variable username
 *
 * @author auto generated.
 */
  public void setUsername(String value) {
    this.username = value;
  }
/**
 * Gets the value of member variable username
 *
 * @author atuo generated.
 */
  public String getUsername() {
    return this.username;
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
 * Sets the value of member variable startDate
 *
 * @author auto generated.
 */
  public void setStartDate(java.util.Date value) {
    this.startDate = value;
  }
/**
 * Gets the value of member variable startDate
 *
 * @author atuo generated.
 */
  public java.util.Date getStartDate() {
    return this.startDate;
  }
/**
 * Sets the value of member variable terminationDate
 *
 * @author auto generated.
 */
  public void setTerminationDate(java.util.Date value) {
    this.terminationDate = value;
  }
/**
 * Gets the value of member variable terminationDate
 *
 * @author atuo generated.
 */
  public java.util.Date getTerminationDate() {
    return this.terminationDate;
  }
/**
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable password
 *
 * @author auto generated.
 */
  public void setPassword(String value) {
    this.password = value;
  }
/**
 * Gets the value of member variable password
 *
 * @author atuo generated.
 */
  public String getPassword() {
    return this.password;
  }
/**
 * Sets the value of member variable totalLogons
 *
 * @author auto generated.
 */
  public void setTotalLogons(int value) {
    this.totalLogons = value;
  }
/**
 * Gets the value of member variable totalLogons
 *
 * @author atuo generated.
 */
  public int getTotalLogons() {
    return this.totalLogons;
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
 * Sets the value of member variable loggedIn
 *
 * @author auto generated.
 */
  public void setLoggedIn(int value) {
    this.loggedIn = value;
  }
/**
 * Gets the value of member variable loggedIn
 *
 * @author atuo generated.
 */
  public int getLoggedIn() {
    return this.loggedIn;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
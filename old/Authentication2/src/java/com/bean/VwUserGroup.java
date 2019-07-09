package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_user_group database table/view.
 *
 * @author auto generated.
 */
public class VwUserGroup extends OrmBean {




	// Property name constants that belong to respective DataSource, VwUserGroupView

/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
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
/** The property name constant equivalent to property, UserDescription, of respective DataSource view. */
  public static final String PROP_USERDESCRIPTION = "UserDescription";
/** The property name constant equivalent to property, StartDate, of respective DataSource view. */
  public static final String PROP_STARTDATE = "StartDate";
/** The property name constant equivalent to property, TerminationDate, of respective DataSource view. */
  public static final String PROP_TERMINATIONDATE = "TerminationDate";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, GroupId, of respective DataSource view. */
  public static final String PROP_GROUPID = "GroupId";
/** The property name constant equivalent to property, GroupDescription, of respective DataSource view. */
  public static final String PROP_GROUPDESCRIPTION = "GroupDescription";



	/** The javabean property equivalent of database column vw_user_group.login_id */
  private int loginId;
/** The javabean property equivalent of database column vw_user_group.username */
  private String username;
/** The javabean property equivalent of database column vw_user_group.firstname */
  private String firstname;
/** The javabean property equivalent of database column vw_user_group.lastname */
  private String lastname;
/** The javabean property equivalent of database column vw_user_group.birth_date */
  private java.util.Date birthDate;
/** The javabean property equivalent of database column vw_user_group.ssn */
  private String ssn;
/** The javabean property equivalent of database column vw_user_group.user_description */
  private String userDescription;
/** The javabean property equivalent of database column vw_user_group.start_date */
  private java.util.Date startDate;
/** The javabean property equivalent of database column vw_user_group.termination_date */
  private java.util.Date terminationDate;
/** The javabean property equivalent of database column vw_user_group.email */
  private String email;
/** The javabean property equivalent of database column vw_user_group.active */
  private int active;
/** The javabean property equivalent of database column vw_user_group.group_id */
  private int groupId;
/** The javabean property equivalent of database column vw_user_group.group_description */
  private String groupDescription;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwUserGroup() throws SystemException {
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
 * Sets the value of member variable userDescription
 *
 * @author auto generated.
 */
  public void setUserDescription(String value) {
    this.userDescription = value;
  }
/**
 * Gets the value of member variable userDescription
 *
 * @author atuo generated.
 */
  public String getUserDescription() {
    return this.userDescription;
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
 * Sets the value of member variable groupId
 *
 * @author auto generated.
 */
  public void setGroupId(int value) {
    this.groupId = value;
  }
/**
 * Gets the value of member variable groupId
 *
 * @author atuo generated.
 */
  public int getGroupId() {
    return this.groupId;
  }
/**
 * Sets the value of member variable groupDescription
 *
 * @author auto generated.
 */
  public void setGroupDescription(String value) {
    this.groupDescription = value;
  }
/**
 * Gets the value of member variable groupDescription
 *
 * @author atuo generated.
 */
  public String getGroupDescription() {
    return this.groupDescription;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
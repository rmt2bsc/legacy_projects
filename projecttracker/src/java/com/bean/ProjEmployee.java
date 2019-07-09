package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_employee database table/view.
 *
 * @author auto generated.
 */
public class ProjEmployee extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjEmployeeView

/** The property name constant equivalent to property, EmpId, of respective DataSource view. */
  public static final String PROP_EMPID = "EmpId";
/** The property name constant equivalent to property, EmpTypeId, of respective DataSource view. */
  public static final String PROP_EMPTYPEID = "EmpTypeId";
/** The property name constant equivalent to property, ManagerId, of respective DataSource view. */
  public static final String PROP_MANAGERID = "ManagerId";
/** The property name constant equivalent to property, EmpTitleId, of respective DataSource view. */
  public static final String PROP_EMPTITLEID = "EmpTitleId";
/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, StartDate, of respective DataSource view. */
  public static final String PROP_STARTDATE = "StartDate";
/** The property name constant equivalent to property, TerminationDate, of respective DataSource view. */
  public static final String PROP_TERMINATIONDATE = "TerminationDate";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, Firstname, of respective DataSource view. */
  public static final String PROP_FIRSTNAME = "Firstname";
/** The property name constant equivalent to property, Lastname, of respective DataSource view. */
  public static final String PROP_LASTNAME = "Lastname";
/** The property name constant equivalent to property, Ssn, of respective DataSource view. */
  public static final String PROP_SSN = "Ssn";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
/** The property name constant equivalent to property, LoginName, of respective DataSource view. */
  public static final String PROP_LOGINNAME = "LoginName";
/** The property name constant equivalent to property, CompanyName, of respective DataSource view. */
  public static final String PROP_COMPANYNAME = "CompanyName";
/** The property name constant equivalent to property, IsManager, of respective DataSource view. */
  public static final String PROP_ISMANAGER = "IsManager";



	/** The javabean property equivalent of database column proj_employee.emp_id */
  private int empId;
/** The javabean property equivalent of database column proj_employee.emp_type_id */
  private int empTypeId;
/** The javabean property equivalent of database column proj_employee.manager_id */
  private int managerId;
/** The javabean property equivalent of database column proj_employee.emp_title_id */
  private int empTitleId;
/** The javabean property equivalent of database column proj_employee.login_id */
  private int loginId;
/** The javabean property equivalent of database column proj_employee.start_date */
  private java.util.Date startDate;
/** The javabean property equivalent of database column proj_employee.termination_date */
  private java.util.Date terminationDate;
/** The javabean property equivalent of database column proj_employee.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column proj_employee.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column proj_employee.user_id */
  private String userId;
/** The javabean property equivalent of database column proj_employee.firstname */
  private String firstname;
/** The javabean property equivalent of database column proj_employee.lastname */
  private String lastname;
/** The javabean property equivalent of database column proj_employee.ssn */
  private String ssn;
/** The javabean property equivalent of database column proj_employee.email */
  private String email;
/** The javabean property equivalent of database column proj_employee.login_name */
  private String loginName;
/** The javabean property equivalent of database column proj_employee.company_name */
  private String companyName;
/** The javabean property equivalent of database column proj_employee.is_manager */
  private int isManager;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjEmployee() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable empId
 *
 * @author auto generated.
 */
  public void setEmpId(int value) {
    this.empId = value;
  }
/**
 * Gets the value of member variable empId
 *
 * @author atuo generated.
 */
  public int getEmpId() {
    return this.empId;
  }
/**
 * Sets the value of member variable empTypeId
 *
 * @author auto generated.
 */
  public void setEmpTypeId(int value) {
    this.empTypeId = value;
  }
/**
 * Gets the value of member variable empTypeId
 *
 * @author atuo generated.
 */
  public int getEmpTypeId() {
    return this.empTypeId;
  }
/**
 * Sets the value of member variable managerId
 *
 * @author auto generated.
 */
  public void setManagerId(int value) {
    this.managerId = value;
  }
/**
 * Gets the value of member variable managerId
 *
 * @author atuo generated.
 */
  public int getManagerId() {
    return this.managerId;
  }
/**
 * Sets the value of member variable empTitleId
 *
 * @author auto generated.
 */
  public void setEmpTitleId(int value) {
    this.empTitleId = value;
  }
/**
 * Gets the value of member variable empTitleId
 *
 * @author atuo generated.
 */
  public int getEmpTitleId() {
    return this.empTitleId;
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
 * Sets the value of member variable loginName
 *
 * @author auto generated.
 */
  public void setLoginName(String value) {
    this.loginName = value;
  }
/**
 * Gets the value of member variable loginName
 *
 * @author atuo generated.
 */
  public String getLoginName() {
    return this.loginName;
  }
/**
 * Sets the value of member variable companyName
 *
 * @author auto generated.
 */
  public void setCompanyName(String value) {
    this.companyName = value;
  }
/**
 * Gets the value of member variable companyName
 *
 * @author atuo generated.
 */
  public String getCompanyName() {
    return this.companyName;
  }
/**
 * Sets the value of member variable isManager
 *
 * @author auto generated.
 */
  public void setIsManager(int value) {
    this.isManager = value;
  }
/**
 * Gets the value of member variable isManager
 *
 * @author atuo generated.
 */
  public int getIsManager() {
    return this.isManager;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
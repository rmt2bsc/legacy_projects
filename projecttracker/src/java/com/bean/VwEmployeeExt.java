package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_employee_ext database table/view.
 *
 * @author auto generated.
 */
public class VwEmployeeExt extends OrmBean {




	// Property name constants that belong to respective DataSource, VwEmployeeExtView

/** The property name constant equivalent to property, EmployeeId, of respective DataSource view. */
  public static final String PROP_EMPLOYEEID = "EmployeeId";
/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, LoginName, of respective DataSource view. */
  public static final String PROP_LOGINNAME = "LoginName";
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
/** The property name constant equivalent to property, Firstname, of respective DataSource view. */
  public static final String PROP_FIRSTNAME = "Firstname";
/** The property name constant equivalent to property, Lastname, of respective DataSource view. */
  public static final String PROP_LASTNAME = "Lastname";
/** The property name constant equivalent to property, Shortname, of respective DataSource view. */
  public static final String PROP_SHORTNAME = "Shortname";
/** The property name constant equivalent to property, CompanyName, of respective DataSource view. */
  public static final String PROP_COMPANYNAME = "CompanyName";
/** The property name constant equivalent to property, Ssn, of respective DataSource view. */
  public static final String PROP_SSN = "Ssn";
/** The property name constant equivalent to property, IsManager, of respective DataSource view. */
  public static final String PROP_ISMANAGER = "IsManager";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
/** The property name constant equivalent to property, EmployeeTitle, of respective DataSource view. */
  public static final String PROP_EMPLOYEETITLE = "EmployeeTitle";
/** The property name constant equivalent to property, EmployeeType, of respective DataSource view. */
  public static final String PROP_EMPLOYEETYPE = "EmployeeType";



	/** The javabean property equivalent of database column vw_employee_ext.employee_id */
  private int employeeId;
/** The javabean property equivalent of database column vw_employee_ext.login_id */
  private int loginId;
/** The javabean property equivalent of database column vw_employee_ext.login_name */
  private String loginName;
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
/** The javabean property equivalent of database column vw_employee_ext.firstname */
  private String firstname;
/** The javabean property equivalent of database column vw_employee_ext.lastname */
  private String lastname;
/** The javabean property equivalent of database column vw_employee_ext.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_employee_ext.company_name */
  private String companyName;
/** The javabean property equivalent of database column vw_employee_ext.ssn */
  private String ssn;
/** The javabean property equivalent of database column vw_employee_ext.is_manager */
  private int isManager;
/** The javabean property equivalent of database column vw_employee_ext.email */
  private String email;
/** The javabean property equivalent of database column vw_employee_ext.employee_title */
  private String employeeTitle;
/** The javabean property equivalent of database column vw_employee_ext.employee_type */
  private String employeeType;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwEmployeeExt() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable employeeId
 *
 * @author auto generated.
 */
  public void setEmployeeId(int value) {
    this.employeeId = value;
  }
/**
 * Gets the value of member variable employeeId
 *
 * @author atuo generated.
 */
  public int getEmployeeId() {
    return this.employeeId;
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
 * Sets the value of member variable titleId
 *
 * @author auto generated.
 */
  public void setTitleId(int value) {
    this.titleId = value;
  }
/**
 * Gets the value of member variable titleId
 *
 * @author atuo generated.
 */
  public int getTitleId() {
    return this.titleId;
  }
/**
 * Sets the value of member variable typeId
 *
 * @author auto generated.
 */
  public void setTypeId(int value) {
    this.typeId = value;
  }
/**
 * Gets the value of member variable typeId
 *
 * @author atuo generated.
 */
  public int getTypeId() {
    return this.typeId;
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
 * Sets the value of member variable employeeTitle
 *
 * @author auto generated.
 */
  public void setEmployeeTitle(String value) {
    this.employeeTitle = value;
  }
/**
 * Gets the value of member variable employeeTitle
 *
 * @author atuo generated.
 */
  public String getEmployeeTitle() {
    return this.employeeTitle;
  }
/**
 * Sets the value of member variable employeeType
 *
 * @author auto generated.
 */
  public void setEmployeeType(String value) {
    this.employeeType = value;
  }
/**
 * Gets the value of member variable employeeType
 *
 * @author atuo generated.
 */
  public String getEmployeeType() {
    return this.employeeType;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_employee_projects database table/view.
 *
 * @author auto generated.
 */
public class VwEmployeeProjects extends OrmBean {




	// Property name constants that belong to respective DataSource, VwEmployeeProjectsView

/** The property name constant equivalent to property, EmpProjId, of respective DataSource view. */
  public static final String PROP_EMPPROJID = "EmpProjId";
/** The property name constant equivalent to property, EmpId, of respective DataSource view. */
  public static final String PROP_EMPID = "EmpId";
/** The property name constant equivalent to property, ProjId, of respective DataSource view. */
  public static final String PROP_PROJID = "ProjId";
/** The property name constant equivalent to property, ProjempEffectiveDate, of respective DataSource view. */
  public static final String PROP_PROJEMPEFFECTIVEDATE = "ProjempEffectiveDate";
/** The property name constant equivalent to property, ProjempEndDate, of respective DataSource view. */
  public static final String PROP_PROJEMPENDDATE = "ProjempEndDate";
/** The property name constant equivalent to property, PayRate, of respective DataSource view. */
  public static final String PROP_PAYRATE = "PayRate";
/** The property name constant equivalent to property, OtPayRate, of respective DataSource view. */
  public static final String PROP_OTPAYRATE = "OtPayRate";
/** The property name constant equivalent to property, FlatRate, of respective DataSource view. */
  public static final String PROP_FLATRATE = "FlatRate";
/** The property name constant equivalent to property, Comments, of respective DataSource view. */
  public static final String PROP_COMMENTS = "Comments";
/** The property name constant equivalent to property, ClientId, of respective DataSource view. */
  public static final String PROP_CLIENTID = "ClientId";
/** The property name constant equivalent to property, ProjectName, of respective DataSource view. */
  public static final String PROP_PROJECTNAME = "ProjectName";
/** The property name constant equivalent to property, ProjEffectiveDate, of respective DataSource view. */
  public static final String PROP_PROJEFFECTIVEDATE = "ProjEffectiveDate";
/** The property name constant equivalent to property, ProjEndDate, of respective DataSource view. */
  public static final String PROP_PROJENDDATE = "ProjEndDate";
/** The property name constant equivalent to property, ClientName, of respective DataSource view. */
  public static final String PROP_CLIENTNAME = "ClientName";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, ClientBillRate, of respective DataSource view. */
  public static final String PROP_CLIENTBILLRATE = "ClientBillRate";
/** The property name constant equivalent to property, ClientOtBillRate, of respective DataSource view. */
  public static final String PROP_CLIENTOTBILLRATE = "ClientOtBillRate";



	/** The javabean property equivalent of database column vw_employee_projects.emp_proj_id */
  private int empProjId;
/** The javabean property equivalent of database column vw_employee_projects.emp_id */
  private int empId;
/** The javabean property equivalent of database column vw_employee_projects.proj_id */
  private int projId;
/** The javabean property equivalent of database column vw_employee_projects.projemp_effective_date */
  private java.util.Date projempEffectiveDate;
/** The javabean property equivalent of database column vw_employee_projects.projemp_end_date */
  private java.util.Date projempEndDate;
/** The javabean property equivalent of database column vw_employee_projects.pay_rate */
  private double payRate;
/** The javabean property equivalent of database column vw_employee_projects.ot_pay_rate */
  private double otPayRate;
/** The javabean property equivalent of database column vw_employee_projects.flat_rate */
  private double flatRate;
/** The javabean property equivalent of database column vw_employee_projects.comments */
  private String comments;
/** The javabean property equivalent of database column vw_employee_projects.client_id */
  private int clientId;
/** The javabean property equivalent of database column vw_employee_projects.project_name */
  private String projectName;
/** The javabean property equivalent of database column vw_employee_projects.proj_effective_date */
  private java.util.Date projEffectiveDate;
/** The javabean property equivalent of database column vw_employee_projects.proj_end_date */
  private java.util.Date projEndDate;
/** The javabean property equivalent of database column vw_employee_projects.client_name */
  private String clientName;
/** The javabean property equivalent of database column vw_employee_projects.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_employee_projects.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_employee_projects.client_bill_rate */
  private double clientBillRate;
/** The javabean property equivalent of database column vw_employee_projects.client_ot_bill_rate */
  private double clientOtBillRate;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwEmployeeProjects() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable empProjId
 *
 * @author auto generated.
 */
  public void setEmpProjId(int value) {
    this.empProjId = value;
  }
/**
 * Gets the value of member variable empProjId
 *
 * @author atuo generated.
 */
  public int getEmpProjId() {
    return this.empProjId;
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
 * Sets the value of member variable projId
 *
 * @author auto generated.
 */
  public void setProjId(int value) {
    this.projId = value;
  }
/**
 * Gets the value of member variable projId
 *
 * @author atuo generated.
 */
  public int getProjId() {
    return this.projId;
  }
/**
 * Sets the value of member variable projempEffectiveDate
 *
 * @author auto generated.
 */
  public void setProjempEffectiveDate(java.util.Date value) {
    this.projempEffectiveDate = value;
  }
/**
 * Gets the value of member variable projempEffectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getProjempEffectiveDate() {
    return this.projempEffectiveDate;
  }
/**
 * Sets the value of member variable projempEndDate
 *
 * @author auto generated.
 */
  public void setProjempEndDate(java.util.Date value) {
    this.projempEndDate = value;
  }
/**
 * Gets the value of member variable projempEndDate
 *
 * @author atuo generated.
 */
  public java.util.Date getProjempEndDate() {
    return this.projempEndDate;
  }
/**
 * Sets the value of member variable payRate
 *
 * @author auto generated.
 */
  public void setPayRate(double value) {
    this.payRate = value;
  }
/**
 * Gets the value of member variable payRate
 *
 * @author atuo generated.
 */
  public double getPayRate() {
    return this.payRate;
  }
/**
 * Sets the value of member variable otPayRate
 *
 * @author auto generated.
 */
  public void setOtPayRate(double value) {
    this.otPayRate = value;
  }
/**
 * Gets the value of member variable otPayRate
 *
 * @author atuo generated.
 */
  public double getOtPayRate() {
    return this.otPayRate;
  }
/**
 * Sets the value of member variable flatRate
 *
 * @author auto generated.
 */
  public void setFlatRate(double value) {
    this.flatRate = value;
  }
/**
 * Gets the value of member variable flatRate
 *
 * @author atuo generated.
 */
  public double getFlatRate() {
    return this.flatRate;
  }
/**
 * Sets the value of member variable comments
 *
 * @author auto generated.
 */
  public void setComments(String value) {
    this.comments = value;
  }
/**
 * Gets the value of member variable comments
 *
 * @author atuo generated.
 */
  public String getComments() {
    return this.comments;
  }
/**
 * Sets the value of member variable clientId
 *
 * @author auto generated.
 */
  public void setClientId(int value) {
    this.clientId = value;
  }
/**
 * Gets the value of member variable clientId
 *
 * @author atuo generated.
 */
  public int getClientId() {
    return this.clientId;
  }
/**
 * Sets the value of member variable projectName
 *
 * @author auto generated.
 */
  public void setProjectName(String value) {
    this.projectName = value;
  }
/**
 * Gets the value of member variable projectName
 *
 * @author atuo generated.
 */
  public String getProjectName() {
    return this.projectName;
  }
/**
 * Sets the value of member variable projEffectiveDate
 *
 * @author auto generated.
 */
  public void setProjEffectiveDate(java.util.Date value) {
    this.projEffectiveDate = value;
  }
/**
 * Gets the value of member variable projEffectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getProjEffectiveDate() {
    return this.projEffectiveDate;
  }
/**
 * Sets the value of member variable projEndDate
 *
 * @author auto generated.
 */
  public void setProjEndDate(java.util.Date value) {
    this.projEndDate = value;
  }
/**
 * Gets the value of member variable projEndDate
 *
 * @author atuo generated.
 */
  public java.util.Date getProjEndDate() {
    return this.projEndDate;
  }
/**
 * Sets the value of member variable clientName
 *
 * @author auto generated.
 */
  public void setClientName(String value) {
    this.clientName = value;
  }
/**
 * Gets the value of member variable clientName
 *
 * @author atuo generated.
 */
  public String getClientName() {
    return this.clientName;
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
 * Sets the value of member variable clientBillRate
 *
 * @author auto generated.
 */
  public void setClientBillRate(double value) {
    this.clientBillRate = value;
  }
/**
 * Gets the value of member variable clientBillRate
 *
 * @author atuo generated.
 */
  public double getClientBillRate() {
    return this.clientBillRate;
  }
/**
 * Sets the value of member variable clientOtBillRate
 *
 * @author auto generated.
 */
  public void setClientOtBillRate(double value) {
    this.clientOtBillRate = value;
  }
/**
 * Gets the value of member variable clientOtBillRate
 *
 * @author atuo generated.
 */
  public double getClientOtBillRate() {
    return this.clientOtBillRate;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
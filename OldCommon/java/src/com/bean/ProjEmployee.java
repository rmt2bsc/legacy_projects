package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_employee database table/view.
 *
 * @author Roy Terrell.
 */
public class ProjEmployee extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjEmployeeView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, PersonId, of respective DataSource view. */
  public static final String PROP_PERSONID = "PersonId";
/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, TitleId, of respective DataSource view. */
  public static final String PROP_TITLEID = "TitleId";
/** The property name constant equivalent to property, TypeId, of respective DataSource view. */
  public static final String PROP_TYPEID = "TypeId";
/** The property name constant equivalent to property, ManagerId, of respective DataSource view. */
  public static final String PROP_MANAGERID = "ManagerId";
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
/** The property name constant equivalent to property, BillRate, of respective DataSource view. */
  public static final String PROP_BILLRATE = "BillRate";
/** The property name constant equivalent to property, OtBillRate, of respective DataSource view. */
  public static final String PROP_OTBILLRATE = "OtBillRate";



	/** The javabean property equivalent of database column proj_employee.ID */
  private int id;
/** The javabean property equivalent of database column proj_employee.person_id */
  private int personId;
/** The javabean property equivalent of database column proj_employee.login_id */
  private String loginId;
/** The javabean property equivalent of database column proj_employee.title_id */
  private int titleId;
/** The javabean property equivalent of database column proj_employee.TYPE_ID */
  private int typeId;
/** The javabean property equivalent of database column proj_employee.manager_id */
  private int managerId;
/** The javabean property equivalent of database column proj_employee.START_DATE */
  private java.util.Date startDate;
/** The javabean property equivalent of database column proj_employee.TERMINATION_DATE */
  private java.util.Date terminationDate;
/** The javabean property equivalent of database column proj_employee.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column proj_employee.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column proj_employee.user_id */
  private String userId;
/** The javabean property equivalent of database column proj_employee.bill_rate */
  private double billRate;
/** The javabean property equivalent of database column proj_employee.ot_bill_rate */
  private double otBillRate;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ProjEmployee() throws SystemException {
	super();
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
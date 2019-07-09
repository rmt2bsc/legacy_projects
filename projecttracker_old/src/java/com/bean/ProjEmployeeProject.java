package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_employee_project database table/view.
 *
 * @author auto generated.
 */
public class ProjEmployeeProject extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjEmployeeProjectView

/** The property name constant equivalent to property, EmpProjId, of respective DataSource view. */
  public static final String PROP_EMPPROJID = "EmpProjId";
/** The property name constant equivalent to property, EmpId, of respective DataSource view. */
  public static final String PROP_EMPID = "EmpId";
/** The property name constant equivalent to property, ProjId, of respective DataSource view. */
  public static final String PROP_PROJID = "ProjId";
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";
/** The property name constant equivalent to property, HourlyRate, of respective DataSource view. */
  public static final String PROP_HOURLYRATE = "HourlyRate";
/** The property name constant equivalent to property, HourlyOverRate, of respective DataSource view. */
  public static final String PROP_HOURLYOVERRATE = "HourlyOverRate";
/** The property name constant equivalent to property, FlatRate, of respective DataSource view. */
  public static final String PROP_FLATRATE = "FlatRate";
/** The property name constant equivalent to property, Comments, of respective DataSource view. */
  public static final String PROP_COMMENTS = "Comments";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, IpCreated, of respective DataSource view. */
  public static final String PROP_IPCREATED = "IpCreated";
/** The property name constant equivalent to property, IpUpdated, of respective DataSource view. */
  public static final String PROP_IPUPDATED = "IpUpdated";



	/** The javabean property equivalent of database column proj_employee_project.emp_proj_id */
  private int empProjId;
/** The javabean property equivalent of database column proj_employee_project.emp_id */
  private int empId;
/** The javabean property equivalent of database column proj_employee_project.proj_id */
  private int projId;
/** The javabean property equivalent of database column proj_employee_project.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column proj_employee_project.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column proj_employee_project.hourly_rate */
  private double hourlyRate;
/** The javabean property equivalent of database column proj_employee_project.hourly_over_rate */
  private double hourlyOverRate;
/** The javabean property equivalent of database column proj_employee_project.flat_rate */
  private double flatRate;
/** The javabean property equivalent of database column proj_employee_project.comments */
  private String comments;
/** The javabean property equivalent of database column proj_employee_project.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column proj_employee_project.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column proj_employee_project.user_id */
  private String userId;
/** The javabean property equivalent of database column proj_employee_project.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column proj_employee_project.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjEmployeeProject() throws SystemException {
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
 * Sets the value of member variable effectiveDate
 *
 * @author auto generated.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author auto generated.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
  }
/**
 * Sets the value of member variable hourlyRate
 *
 * @author auto generated.
 */
  public void setHourlyRate(double value) {
    this.hourlyRate = value;
  }
/**
 * Gets the value of member variable hourlyRate
 *
 * @author atuo generated.
 */
  public double getHourlyRate() {
    return this.hourlyRate;
  }
/**
 * Sets the value of member variable hourlyOverRate
 *
 * @author auto generated.
 */
  public void setHourlyOverRate(double value) {
    this.hourlyOverRate = value;
  }
/**
 * Gets the value of member variable hourlyOverRate
 *
 * @author atuo generated.
 */
  public double getHourlyOverRate() {
    return this.hourlyOverRate;
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
 * Sets the value of member variable ipCreated
 *
 * @author auto generated.
 */
  public void setIpCreated(String value) {
    this.ipCreated = value;
  }
/**
 * Gets the value of member variable ipCreated
 *
 * @author atuo generated.
 */
  public String getIpCreated() {
    return this.ipCreated;
  }
/**
 * Sets the value of member variable ipUpdated
 *
 * @author auto generated.
 */
  public void setIpUpdated(String value) {
    this.ipUpdated = value;
  }
/**
 * Gets the value of member variable ipUpdated
 *
 * @author atuo generated.
 */
  public String getIpUpdated() {
    return this.ipUpdated;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
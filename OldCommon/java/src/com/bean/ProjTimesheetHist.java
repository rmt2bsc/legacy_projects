package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_timesheet_hist database table/view.
 *
 * @author Roy Terrell.
 */
public class ProjTimesheetHist extends OrmBean {

/** The javabean property equivalent of database column proj_timesheet_hist.id */
  private int id;
/** The javabean property equivalent of database column proj_timesheet_hist.proj_timesheet_id */
  private int projTimesheetId;
/** The javabean property equivalent of database column proj_timesheet_hist.proj_timesheet_status_id */
  private int projTimesheetStatusId;
/** The javabean property equivalent of database column proj_timesheet_hist.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column proj_timesheet_hist.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column proj_timesheet_hist.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ProjTimesheetHist() throws SystemException {
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
 * Sets the value of member variable projTimesheetId
 *
 * @author Roy Terrell.
 */
  public void setProjTimesheetId(int value) {
    this.projTimesheetId = value;
  }
/**
 * Gets the value of member variable projTimesheetId
 *
 * @author Roy Terrell.
 */
  public int getProjTimesheetId() {
    return this.projTimesheetId;
  }
/**
 * Sets the value of member variable projTimesheetStatusId
 *
 * @author Roy Terrell.
 */
  public void setProjTimesheetStatusId(int value) {
    this.projTimesheetStatusId = value;
  }
/**
 * Gets the value of member variable projTimesheetStatusId
 *
 * @author Roy Terrell.
 */
  public int getProjTimesheetStatusId() {
    return this.projTimesheetStatusId;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
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
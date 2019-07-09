package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_pref database table/view.
 *
 * @author Roy Terrell.
 */
public class ProjPref extends OrmBean {

/** The javabean property equivalent of database column proj_pref.end_period_day */
  private String endPeriodDay;
/** The javabean property equivalent of database column proj_pref.timesheet_base */
  private String timesheetBase;
/** The javabean property equivalent of database column proj_pref.email_confirm */
  private String emailConfirm;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ProjPref() throws SystemException {
  }
/**
 * Sets the value of member variable endPeriodDay
 *
 * @author Roy Terrell.
 */
  public void setEndPeriodDay(String value) {
    this.endPeriodDay = value;
  }
/**
 * Gets the value of member variable endPeriodDay
 *
 * @author Roy Terrell.
 */
  public String getEndPeriodDay() {
    return this.endPeriodDay;
  }
/**
 * Sets the value of member variable timesheetBase
 *
 * @author Roy Terrell.
 */
  public void setTimesheetBase(String value) {
    this.timesheetBase = value;
  }
/**
 * Gets the value of member variable timesheetBase
 *
 * @author Roy Terrell.
 */
  public String getTimesheetBase() {
    return this.timesheetBase;
  }
/**
 * Sets the value of member variable emailConfirm
 *
 * @author Roy Terrell.
 */
  public void setEmailConfirm(String value) {
    this.emailConfirm = value;
  }
/**
 * Gets the value of member variable emailConfirm
 *
 * @author Roy Terrell.
 */
  public String getEmailConfirm() {
    return this.emailConfirm;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
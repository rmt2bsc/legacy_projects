package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_timesheet_summary database table/view.
 *
 * @author Roy Terrell.
 */
public class VwTimesheetSummary extends OrmBean {

/** The javabean property equivalent of database column vw_timesheet_summary.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column vw_timesheet_summary.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_timesheet_summary.display_value */
  private String displayValue;
/** The javabean property equivalent of database column vw_timesheet_summary.end_period */
  private java.util.Date endPeriod;
/** The javabean property equivalent of database column vw_timesheet_summary.day1_hrs */
  private double day1Hrs;
/** The javabean property equivalent of database column vw_timesheet_summary.day2_hrs */
  private double day2Hrs;
/** The javabean property equivalent of database column vw_timesheet_summary.day3_hrs */
  private double day3Hrs;
/** The javabean property equivalent of database column vw_timesheet_summary.day4_hrs */
  private double day4Hrs;
/** The javabean property equivalent of database column vw_timesheet_summary.day5_hrs */
  private double day5Hrs;
/** The javabean property equivalent of database column vw_timesheet_summary.day6_hrs */
  private double day6Hrs;
/** The javabean property equivalent of database column vw_timesheet_summary.day7_hrs */
  private double day7Hrs;
/** The javabean property equivalent of database column vw_timesheet_summary.total_hrs */
  private double totalHrs;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwTimesheetSummary() throws SystemException {
  }
/**
 * Sets the value of member variable timesheetId
 *
 * @author Roy Terrell.
 */
  public void setTimesheetId(int value) {
    this.timesheetId = value;
  }
/**
 * Gets the value of member variable timesheetId
 *
 * @author Roy Terrell.
 */
  public int getTimesheetId() {
    return this.timesheetId;
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
 * Sets the value of member variable displayValue
 *
 * @author Roy Terrell.
 */
  public void setDisplayValue(String value) {
    this.displayValue = value;
  }
/**
 * Gets the value of member variable displayValue
 *
 * @author Roy Terrell.
 */
  public String getDisplayValue() {
    return this.displayValue;
  }
/**
 * Sets the value of member variable endPeriod
 *
 * @author Roy Terrell.
 */
  public void setEndPeriod(java.util.Date value) {
    this.endPeriod = value;
  }
/**
 * Gets the value of member variable endPeriod
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndPeriod() {
    return this.endPeriod;
  }
/**
 * Sets the value of member variable day1Hrs
 *
 * @author Roy Terrell.
 */
  public void setDay1Hrs(double value) {
    this.day1Hrs = value;
  }
/**
 * Gets the value of member variable day1Hrs
 *
 * @author Roy Terrell.
 */
  public double getDay1Hrs() {
    return this.day1Hrs;
  }
/**
 * Sets the value of member variable day2Hrs
 *
 * @author Roy Terrell.
 */
  public void setDay2Hrs(double value) {
    this.day2Hrs = value;
  }
/**
 * Gets the value of member variable day2Hrs
 *
 * @author Roy Terrell.
 */
  public double getDay2Hrs() {
    return this.day2Hrs;
  }
/**
 * Sets the value of member variable day3Hrs
 *
 * @author Roy Terrell.
 */
  public void setDay3Hrs(double value) {
    this.day3Hrs = value;
  }
/**
 * Gets the value of member variable day3Hrs
 *
 * @author Roy Terrell.
 */
  public double getDay3Hrs() {
    return this.day3Hrs;
  }
/**
 * Sets the value of member variable day4Hrs
 *
 * @author Roy Terrell.
 */
  public void setDay4Hrs(double value) {
    this.day4Hrs = value;
  }
/**
 * Gets the value of member variable day4Hrs
 *
 * @author Roy Terrell.
 */
  public double getDay4Hrs() {
    return this.day4Hrs;
  }
/**
 * Sets the value of member variable day5Hrs
 *
 * @author Roy Terrell.
 */
  public void setDay5Hrs(double value) {
    this.day5Hrs = value;
  }
/**
 * Gets the value of member variable day5Hrs
 *
 * @author Roy Terrell.
 */
  public double getDay5Hrs() {
    return this.day5Hrs;
  }
/**
 * Sets the value of member variable day6Hrs
 *
 * @author Roy Terrell.
 */
  public void setDay6Hrs(double value) {
    this.day6Hrs = value;
  }
/**
 * Gets the value of member variable day6Hrs
 *
 * @author Roy Terrell.
 */
  public double getDay6Hrs() {
    return this.day6Hrs;
  }
/**
 * Sets the value of member variable day7Hrs
 *
 * @author Roy Terrell.
 */
  public void setDay7Hrs(double value) {
    this.day7Hrs = value;
  }
/**
 * Gets the value of member variable day7Hrs
 *
 * @author Roy Terrell.
 */
  public double getDay7Hrs() {
    return this.day7Hrs;
  }
/**
 * Sets the value of member variable totalHrs
 *
 * @author Roy Terrell.
 */
  public void setTotalHrs(double value) {
    this.totalHrs = value;
  }
/**
 * Gets the value of member variable totalHrs
 *
 * @author Roy Terrell.
 */
  public double getTotalHrs() {
    return this.totalHrs;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
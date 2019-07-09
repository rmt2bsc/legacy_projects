package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_timesheet_summary database table/view.
 *
 * @author auto generated.
 */
public class VwTimesheetSummary extends OrmBean {




	// Property name constants that belong to respective DataSource, VwTimesheetSummaryView

/** The property name constant equivalent to property, TimesheetId, of respective DataSource view. */
  public static final String PROP_TIMESHEETID = "TimesheetId";
/** The property name constant equivalent to property, Shortname, of respective DataSource view. */
  public static final String PROP_SHORTNAME = "Shortname";
/** The property name constant equivalent to property, DisplayValue, of respective DataSource view. */
  public static final String PROP_DISPLAYVALUE = "DisplayValue";
/** The property name constant equivalent to property, EndPeriod, of respective DataSource view. */
  public static final String PROP_ENDPERIOD = "EndPeriod";
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, Day1Hrs, of respective DataSource view. */
  public static final String PROP_DAY1HRS = "Day1Hrs";
/** The property name constant equivalent to property, Day2Hrs, of respective DataSource view. */
  public static final String PROP_DAY2HRS = "Day2Hrs";
/** The property name constant equivalent to property, Day3Hrs, of respective DataSource view. */
  public static final String PROP_DAY3HRS = "Day3Hrs";
/** The property name constant equivalent to property, Day4Hrs, of respective DataSource view. */
  public static final String PROP_DAY4HRS = "Day4Hrs";
/** The property name constant equivalent to property, Day5Hrs, of respective DataSource view. */
  public static final String PROP_DAY5HRS = "Day5Hrs";
/** The property name constant equivalent to property, Day6Hrs, of respective DataSource view. */
  public static final String PROP_DAY6HRS = "Day6Hrs";
/** The property name constant equivalent to property, Day7Hrs, of respective DataSource view. */
  public static final String PROP_DAY7HRS = "Day7Hrs";
/** The property name constant equivalent to property, TotalHrs, of respective DataSource view. */
  public static final String PROP_TOTALHRS = "TotalHrs";



	/** The javabean property equivalent of database column vw_timesheet_summary.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column vw_timesheet_summary.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_timesheet_summary.display_value */
  private String displayValue;
/** The javabean property equivalent of database column vw_timesheet_summary.end_period */
  private java.util.Date endPeriod;
/** The javabean property equivalent of database column vw_timesheet_summary.document_id */
  private int documentId;
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
 * @author auto generated.
 */
  public VwTimesheetSummary() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable timesheetId
 *
 * @author auto generated.
 */
  public void setTimesheetId(int value) {
    this.timesheetId = value;
  }
/**
 * Gets the value of member variable timesheetId
 *
 * @author atuo generated.
 */
  public int getTimesheetId() {
    return this.timesheetId;
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
 * Sets the value of member variable displayValue
 *
 * @author auto generated.
 */
  public void setDisplayValue(String value) {
    this.displayValue = value;
  }
/**
 * Gets the value of member variable displayValue
 *
 * @author atuo generated.
 */
  public String getDisplayValue() {
    return this.displayValue;
  }
/**
 * Sets the value of member variable endPeriod
 *
 * @author auto generated.
 */
  public void setEndPeriod(java.util.Date value) {
    this.endPeriod = value;
  }
/**
 * Gets the value of member variable endPeriod
 *
 * @author atuo generated.
 */
  public java.util.Date getEndPeriod() {
    return this.endPeriod;
  }
/**
 * Sets the value of member variable documentId
 *
 * @author auto generated.
 */
  public void setDocumentId(int value) {
    this.documentId = value;
  }
/**
 * Gets the value of member variable documentId
 *
 * @author atuo generated.
 */
  public int getDocumentId() {
    return this.documentId;
  }
/**
 * Sets the value of member variable day1Hrs
 *
 * @author auto generated.
 */
  public void setDay1Hrs(double value) {
    this.day1Hrs = value;
  }
/**
 * Gets the value of member variable day1Hrs
 *
 * @author atuo generated.
 */
  public double getDay1Hrs() {
    return this.day1Hrs;
  }
/**
 * Sets the value of member variable day2Hrs
 *
 * @author auto generated.
 */
  public void setDay2Hrs(double value) {
    this.day2Hrs = value;
  }
/**
 * Gets the value of member variable day2Hrs
 *
 * @author atuo generated.
 */
  public double getDay2Hrs() {
    return this.day2Hrs;
  }
/**
 * Sets the value of member variable day3Hrs
 *
 * @author auto generated.
 */
  public void setDay3Hrs(double value) {
    this.day3Hrs = value;
  }
/**
 * Gets the value of member variable day3Hrs
 *
 * @author atuo generated.
 */
  public double getDay3Hrs() {
    return this.day3Hrs;
  }
/**
 * Sets the value of member variable day4Hrs
 *
 * @author auto generated.
 */
  public void setDay4Hrs(double value) {
    this.day4Hrs = value;
  }
/**
 * Gets the value of member variable day4Hrs
 *
 * @author atuo generated.
 */
  public double getDay4Hrs() {
    return this.day4Hrs;
  }
/**
 * Sets the value of member variable day5Hrs
 *
 * @author auto generated.
 */
  public void setDay5Hrs(double value) {
    this.day5Hrs = value;
  }
/**
 * Gets the value of member variable day5Hrs
 *
 * @author atuo generated.
 */
  public double getDay5Hrs() {
    return this.day5Hrs;
  }
/**
 * Sets the value of member variable day6Hrs
 *
 * @author auto generated.
 */
  public void setDay6Hrs(double value) {
    this.day6Hrs = value;
  }
/**
 * Gets the value of member variable day6Hrs
 *
 * @author atuo generated.
 */
  public double getDay6Hrs() {
    return this.day6Hrs;
  }
/**
 * Sets the value of member variable day7Hrs
 *
 * @author auto generated.
 */
  public void setDay7Hrs(double value) {
    this.day7Hrs = value;
  }
/**
 * Gets the value of member variable day7Hrs
 *
 * @author atuo generated.
 */
  public double getDay7Hrs() {
    return this.day7Hrs;
  }
/**
 * Sets the value of member variable totalHrs
 *
 * @author auto generated.
 */
  public void setTotalHrs(double value) {
    this.totalHrs = value;
  }
/**
 * Gets the value of member variable totalHrs
 *
 * @author atuo generated.
 */
  public double getTotalHrs() {
    return this.totalHrs;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
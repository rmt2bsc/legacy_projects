package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_timesheet_status database table/view.
 *
 * @author auto generated.
 */
public class ProjTimesheetStatus extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjTimesheetStatusView

/** The property name constant equivalent to property, TimesheetStatusId, of respective DataSource view. */
  public static final String PROP_TIMESHEETSTATUSID = "TimesheetStatusId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column proj_timesheet_status.timesheet_status_id */
  private int timesheetStatusId;
/** The javabean property equivalent of database column proj_timesheet_status.name */
  private String name;
/** The javabean property equivalent of database column proj_timesheet_status.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjTimesheetStatus() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable timesheetStatusId
 *
 * @author auto generated.
 */
  public void setTimesheetStatusId(int value) {
    this.timesheetStatusId = value;
  }
/**
 * Gets the value of member variable timesheetStatusId
 *
 * @author atuo generated.
 */
  public int getTimesheetStatusId() {
    return this.timesheetStatusId;
  }
/**
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_timesheet_status database table/view.
 *
 * @author Roy Terrell.
 */
public class ProjTimesheetStatus extends OrmBean {

/** The javabean property equivalent of database column proj_timesheet_status.id */
  private int id;
/** The javabean property equivalent of database column proj_timesheet_status.name */
  private String name;
/** The javabean property equivalent of database column proj_timesheet_status.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ProjTimesheetStatus() throws SystemException {
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
 * Sets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
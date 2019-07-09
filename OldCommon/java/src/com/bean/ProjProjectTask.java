package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_project_task database table/view.
 *
 * @author Roy Terrell.
 */
public class ProjProjectTask extends OrmBean {

/** The javabean property equivalent of database column proj_project_task.id */
  private int id;
/** The javabean property equivalent of database column proj_project_task.proj_timesheet_id */
  private int projTimesheetId;
/** The javabean property equivalent of database column proj_project_task.proj_project_id */
  private int projProjectId;
/** The javabean property equivalent of database column proj_project_task.proj_task_id */
  private int projTaskId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ProjProjectTask() throws SystemException {
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
 * Sets the value of member variable projProjectId
 *
 * @author Roy Terrell.
 */
  public void setProjProjectId(int value) {
    this.projProjectId = value;
  }
/**
 * Gets the value of member variable projProjectId
 *
 * @author Roy Terrell.
 */
  public int getProjProjectId() {
    return this.projProjectId;
  }
/**
 * Sets the value of member variable projTaskId
 *
 * @author Roy Terrell.
 */
  public void setProjTaskId(int value) {
    this.projTaskId = value;
  }
/**
 * Gets the value of member variable projTaskId
 *
 * @author Roy Terrell.
 */
  public int getProjTaskId() {
    return this.projTaskId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_project_task database table/view.
 *
 * @author auto generated.
 */
public class ProjProjectTask extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjProjectTaskView

/** The property name constant equivalent to property, ProjectTaskId, of respective DataSource view. */
  public static final String PROP_PROJECTTASKID = "ProjectTaskId";
/** The property name constant equivalent to property, TaskId, of respective DataSource view. */
  public static final String PROP_TASKID = "TaskId";
/** The property name constant equivalent to property, TimesheetId, of respective DataSource view. */
  public static final String PROP_TIMESHEETID = "TimesheetId";
/** The property name constant equivalent to property, ProjId, of respective DataSource view. */
  public static final String PROP_PROJID = "ProjId";



	/** The javabean property equivalent of database column proj_project_task.project_task_id */
  private int projectTaskId;
/** The javabean property equivalent of database column proj_project_task.task_id */
  private int taskId;
/** The javabean property equivalent of database column proj_project_task.timesheet_id */
  private int timesheetId;
/** The javabean property equivalent of database column proj_project_task.proj_id */
  private int projId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjProjectTask() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable projectTaskId
 *
 * @author auto generated.
 */
  public void setProjectTaskId(int value) {
    this.projectTaskId = value;
  }
/**
 * Gets the value of member variable projectTaskId
 *
 * @author atuo generated.
 */
  public int getProjectTaskId() {
    return this.projectTaskId;
  }
/**
 * Sets the value of member variable taskId
 *
 * @author auto generated.
 */
  public void setTaskId(int value) {
    this.taskId = value;
  }
/**
 * Gets the value of member variable taskId
 *
 * @author atuo generated.
 */
  public int getTaskId() {
    return this.taskId;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
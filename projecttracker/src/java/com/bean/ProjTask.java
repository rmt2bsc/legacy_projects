package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_task database table/view.
 *
 * @author auto generated.
 */
public class ProjTask extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjTaskView

/** The property name constant equivalent to property, TaskId, of respective DataSource view. */
  public static final String PROP_TASKID = "TaskId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, Billable, of respective DataSource view. */
  public static final String PROP_BILLABLE = "Billable";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column proj_task.task_id */
  private int taskId;
/** The javabean property equivalent of database column proj_task.description */
  private String description;
/** The javabean property equivalent of database column proj_task.billable */
  private int billable;
/** The javabean property equivalent of database column proj_task.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column proj_task.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column proj_task.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjTask() throws SystemException {
	super();
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
 * Sets the value of member variable billable
 *
 * @author auto generated.
 */
  public void setBillable(int value) {
    this.billable = value;
  }
/**
 * Gets the value of member variable billable
 *
 * @author atuo generated.
 */
  public int getBillable() {
    return this.billable;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_subsidiary database table/view.
 *
 * @author Roy Terrell.
 */
public class GlSubsidiary extends OrmBean {

/** The javabean property equivalent of database column gl_subsidiary.id */
  private String id;
/** The javabean property equivalent of database column gl_subsidiary.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column gl_subsidiary.activity_table */
  private String activityTable;
/** The javabean property equivalent of database column gl_subsidiary.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public GlSubsidiary() throws SystemException {
  }
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setId(String value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public String getId() {
    return this.id;
  }
/**
 * Sets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
/**
 * Gets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public int getGlAccountId() {
    return this.glAccountId;
  }
/**
 * Sets the value of member variable activityTable
 *
 * @author Roy Terrell.
 */
  public void setActivityTable(String value) {
    this.activityTable = value;
  }
/**
 * Gets the value of member variable activityTable
 *
 * @author Roy Terrell.
 */
  public String getActivityTable() {
    return this.activityTable;
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
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_group_forms database table/view.
 *
 * @author Roy Terrell.
 */
public class UserGroupForms extends OrmBean {

/** The javabean property equivalent of database column user_group_forms.id */
  private int id;
/** The javabean property equivalent of database column user_group_forms.group_id */
  private int groupId;
/** The javabean property equivalent of database column user_group_forms.access_code */
  private String accessCode;
/** The javabean property equivalent of database column user_group_forms.description */
  private String description;
/** The javabean property equivalent of database column user_group_forms.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column user_group_forms.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column user_group_forms.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public UserGroupForms() throws SystemException {
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
 * Sets the value of member variable groupId
 *
 * @author Roy Terrell.
 */
  public void setGroupId(int value) {
    this.groupId = value;
  }
/**
 * Gets the value of member variable groupId
 *
 * @author Roy Terrell.
 */
  public int getGroupId() {
    return this.groupId;
  }
/**
 * Sets the value of member variable accessCode
 *
 * @author Roy Terrell.
 */
  public void setAccessCode(String value) {
    this.accessCode = value;
  }
/**
 * Gets the value of member variable accessCode
 *
 * @author Roy Terrell.
 */
  public String getAccessCode() {
    return this.accessCode;
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
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
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
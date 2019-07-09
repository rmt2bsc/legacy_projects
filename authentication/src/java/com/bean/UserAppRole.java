package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_app_role database table/view.
 *
 * @author auto generated.
 */
public class UserAppRole extends OrmBean {




	// Property name constants that belong to respective DataSource, UserAppRoleView

/** The property name constant equivalent to property, UserAppRoleId, of respective DataSource view. */
  public static final String PROP_USERAPPROLEID = "UserAppRoleId";
/** The property name constant equivalent to property, AppRoleId, of respective DataSource view. */
  public static final String PROP_APPROLEID = "AppRoleId";
/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column user_app_role.user_app_role_id */
  private int userAppRoleId;
/** The javabean property equivalent of database column user_app_role.app_role_id */
  private int appRoleId;
/** The javabean property equivalent of database column user_app_role.login_id */
  private int loginId;
/** The javabean property equivalent of database column user_app_role.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column user_app_role.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column user_app_role.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public UserAppRole() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable userAppRoleId
 *
 * @author auto generated.
 */
  public void setUserAppRoleId(int value) {
    this.userAppRoleId = value;
  }
/**
 * Gets the value of member variable userAppRoleId
 *
 * @author atuo generated.
 */
  public int getUserAppRoleId() {
    return this.userAppRoleId;
  }
/**
 * Sets the value of member variable appRoleId
 *
 * @author auto generated.
 */
  public void setAppRoleId(int value) {
    this.appRoleId = value;
  }
/**
 * Gets the value of member variable appRoleId
 *
 * @author atuo generated.
 */
  public int getAppRoleId() {
    return this.appRoleId;
  }
/**
 * Sets the value of member variable loginId
 *
 * @author auto generated.
 */
  public void setLoginId(int value) {
    this.loginId = value;
  }
/**
 * Gets the value of member variable loginId
 *
 * @author atuo generated.
 */
  public int getLoginId() {
    return this.loginId;
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
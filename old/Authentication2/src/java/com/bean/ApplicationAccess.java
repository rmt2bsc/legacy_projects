package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the application_access database table/view.
 *
 * @author auto generated.
 */
public class ApplicationAccess extends OrmBean {




	// Property name constants that belong to respective DataSource, ApplicationAccessView

/** The property name constant equivalent to property, AppAccessId, of respective DataSource view. */
  public static final String PROP_APPACCESSID = "AppAccessId";
/** The property name constant equivalent to property, AppId, of respective DataSource view. */
  public static final String PROP_APPID = "AppId";
/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, LoginDate, of respective DataSource view. */
  public static final String PROP_LOGINDATE = "LoginDate";
/** The property name constant equivalent to property, LastLoginDate, of respective DataSource view. */
  public static final String PROP_LASTLOGINDATE = "LastLoginDate";
/** The property name constant equivalent to property, SessionId, of respective DataSource view. */
  public static final String PROP_SESSIONID = "SessionId";



	/** The javabean property equivalent of database column application_access.app_access_id */
  private int appAccessId;
/** The javabean property equivalent of database column application_access.app_id */
  private int appId;
/** The javabean property equivalent of database column application_access.login_id */
  private int loginId;
/** The javabean property equivalent of database column application_access.login_date */
  private java.util.Date loginDate;
/** The javabean property equivalent of database column application_access.last_login_date */
  private java.util.Date lastLoginDate;
/** The javabean property equivalent of database column application_access.session_id */
  private String sessionId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ApplicationAccess() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable appAccessId
 *
 * @author auto generated.
 */
  public void setAppAccessId(int value) {
    this.appAccessId = value;
  }
/**
 * Gets the value of member variable appAccessId
 *
 * @author atuo generated.
 */
  public int getAppAccessId() {
    return this.appAccessId;
  }
/**
 * Sets the value of member variable appId
 *
 * @author auto generated.
 */
  public void setAppId(int value) {
    this.appId = value;
  }
/**
 * Gets the value of member variable appId
 *
 * @author atuo generated.
 */
  public int getAppId() {
    return this.appId;
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
 * Sets the value of member variable loginDate
 *
 * @author auto generated.
 */
  public void setLoginDate(java.util.Date value) {
    this.loginDate = value;
  }
/**
 * Gets the value of member variable loginDate
 *
 * @author atuo generated.
 */
  public java.util.Date getLoginDate() {
    return this.loginDate;
  }
/**
 * Sets the value of member variable lastLoginDate
 *
 * @author auto generated.
 */
  public void setLastLoginDate(java.util.Date value) {
    this.lastLoginDate = value;
  }
/**
 * Gets the value of member variable lastLoginDate
 *
 * @author atuo generated.
 */
  public java.util.Date getLastLoginDate() {
    return this.lastLoginDate;
  }
/**
 * Sets the value of member variable sessionId
 *
 * @author auto generated.
 */
  public void setSessionId(String value) {
    this.sessionId = value;
  }
/**
 * Gets the value of member variable sessionId
 *
 * @author atuo generated.
 */
  public String getSessionId() {
    return this.sessionId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_app_roles database table/view.
 *
 * @author auto generated.
 */
public class VwAppRoles extends OrmBean {




	// Property name constants that belong to respective DataSource, VwAppRolesView

/** The property name constant equivalent to property, AppRoleId, of respective DataSource view. */
  public static final String PROP_APPROLEID = "AppRoleId";
/** The property name constant equivalent to property, AppRoleCode, of respective DataSource view. */
  public static final String PROP_APPROLECODE = "AppRoleCode";
/** The property name constant equivalent to property, AppRoleName, of respective DataSource view. */
  public static final String PROP_APPROLENAME = "AppRoleName";
/** The property name constant equivalent to property, AppRoleDescription, of respective DataSource view. */
  public static final String PROP_APPROLEDESCRIPTION = "AppRoleDescription";
/** The property name constant equivalent to property, RoleId, of respective DataSource view. */
  public static final String PROP_ROLEID = "RoleId";
/** The property name constant equivalent to property, RoleName, of respective DataSource view. */
  public static final String PROP_ROLENAME = "RoleName";
/** The property name constant equivalent to property, AppName, of respective DataSource view. */
  public static final String PROP_APPNAME = "AppName";
/** The property name constant equivalent to property, ApplicationId, of respective DataSource view. */
  public static final String PROP_APPLICATIONID = "ApplicationId";



	/** The javabean property equivalent of database column vw_app_roles.app_role_id */
  private int appRoleId;
/** The javabean property equivalent of database column vw_app_roles.app_role_code */
  private String appRoleCode;
/** The javabean property equivalent of database column vw_app_roles.app_role_name */
  private String appRoleName;
/** The javabean property equivalent of database column vw_app_roles.app_role_description */
  private String appRoleDescription;
/** The javabean property equivalent of database column vw_app_roles.role_id */
  private int roleId;
/** The javabean property equivalent of database column vw_app_roles.role_name */
  private String roleName;
/** The javabean property equivalent of database column vw_app_roles.app_name */
  private String appName;
/** The javabean property equivalent of database column vw_app_roles.application_id */
  private int applicationId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwAppRoles() throws SystemException {
	super();
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
 * Sets the value of member variable appRoleCode
 *
 * @author auto generated.
 */
  public void setAppRoleCode(String value) {
    this.appRoleCode = value;
  }
/**
 * Gets the value of member variable appRoleCode
 *
 * @author atuo generated.
 */
  public String getAppRoleCode() {
    return this.appRoleCode;
  }
/**
 * Sets the value of member variable appRoleName
 *
 * @author auto generated.
 */
  public void setAppRoleName(String value) {
    this.appRoleName = value;
  }
/**
 * Gets the value of member variable appRoleName
 *
 * @author atuo generated.
 */
  public String getAppRoleName() {
    return this.appRoleName;
  }
/**
 * Sets the value of member variable appRoleDescription
 *
 * @author auto generated.
 */
  public void setAppRoleDescription(String value) {
    this.appRoleDescription = value;
  }
/**
 * Gets the value of member variable appRoleDescription
 *
 * @author atuo generated.
 */
  public String getAppRoleDescription() {
    return this.appRoleDescription;
  }
/**
 * Sets the value of member variable roleId
 *
 * @author auto generated.
 */
  public void setRoleId(int value) {
    this.roleId = value;
  }
/**
 * Gets the value of member variable roleId
 *
 * @author atuo generated.
 */
  public int getRoleId() {
    return this.roleId;
  }
/**
 * Sets the value of member variable roleName
 *
 * @author auto generated.
 */
  public void setRoleName(String value) {
    this.roleName = value;
  }
/**
 * Gets the value of member variable roleName
 *
 * @author atuo generated.
 */
  public String getRoleName() {
    return this.roleName;
  }
/**
 * Sets the value of member variable appName
 *
 * @author auto generated.
 */
  public void setAppName(String value) {
    this.appName = value;
  }
/**
 * Gets the value of member variable appName
 *
 * @author atuo generated.
 */
  public String getAppName() {
    return this.appName;
  }
/**
 * Sets the value of member variable applicationId
 *
 * @author auto generated.
 */
  public void setApplicationId(int value) {
    this.applicationId = value;
  }
/**
 * Gets the value of member variable applicationId
 *
 * @author atuo generated.
 */
  public int getApplicationId() {
    return this.applicationId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
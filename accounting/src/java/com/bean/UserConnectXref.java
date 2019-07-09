package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_connect_xref database table/view.
 *
 * @author auto generated.
 */
public class UserConnectXref extends OrmBean {




	// Property name constants that belong to respective DataSource, UserConnectXrefView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, ConId, of respective DataSource view. */
  public static final String PROP_CONID = "ConId";
/** The property name constant equivalent to property, ConUserId, of respective DataSource view. */
  public static final String PROP_CONUSERID = "ConUserId";
/** The property name constant equivalent to property, AppUserId, of respective DataSource view. */
  public static final String PROP_APPUSERID = "AppUserId";



	/** The javabean property equivalent of database column user_connect_xref.id */
  private int id;
/** The javabean property equivalent of database column user_connect_xref.con_id */
  private String conId;
/** The javabean property equivalent of database column user_connect_xref.con_user_id */
  private String conUserId;
/** The javabean property equivalent of database column user_connect_xref.app_user_id */
  private String appUserId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public UserConnectXref() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable id
 *
 * @author auto generated.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author atuo generated.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable conId
 *
 * @author auto generated.
 */
  public void setConId(String value) {
    this.conId = value;
  }
/**
 * Gets the value of member variable conId
 *
 * @author atuo generated.
 */
  public String getConId() {
    return this.conId;
  }
/**
 * Sets the value of member variable conUserId
 *
 * @author auto generated.
 */
  public void setConUserId(String value) {
    this.conUserId = value;
  }
/**
 * Gets the value of member variable conUserId
 *
 * @author atuo generated.
 */
  public String getConUserId() {
    return this.conUserId;
  }
/**
 * Sets the value of member variable appUserId
 *
 * @author auto generated.
 */
  public void setAppUserId(String value) {
    this.appUserId = value;
  }
/**
 * Gets the value of member variable appUserId
 *
 * @author atuo generated.
 */
  public String getAppUserId() {
    return this.appUserId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
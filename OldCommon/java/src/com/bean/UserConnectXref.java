package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_connect_xref database table/view.
 *
 * @author Roy Terrell.
 */
public class UserConnectXref extends OrmBean {

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
 * @author Roy Terrell.
 */
  public UserConnectXref() throws SystemException {
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
 * Sets the value of member variable conId
 *
 * @author Roy Terrell.
 */
  public void setConId(String value) {
    this.conId = value;
  }
/**
 * Gets the value of member variable conId
 *
 * @author Roy Terrell.
 */
  public String getConId() {
    return this.conId;
  }
/**
 * Sets the value of member variable conUserId
 *
 * @author Roy Terrell.
 */
  public void setConUserId(String value) {
    this.conUserId = value;
  }
/**
 * Gets the value of member variable conUserId
 *
 * @author Roy Terrell.
 */
  public String getConUserId() {
    return this.conUserId;
  }
/**
 * Sets the value of member variable appUserId
 *
 * @author Roy Terrell.
 */
  public void setAppUserId(String value) {
    this.appUserId = value;
  }
/**
 * Gets the value of member variable appUserId
 *
 * @author Roy Terrell.
 */
  public String getAppUserId() {
    return this.appUserId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
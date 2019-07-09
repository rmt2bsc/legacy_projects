package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the general_codes database table/view.
 *
 * @author Roy Terrell.
 */
public class GeneralCodes extends OrmBean {

/** The javabean property equivalent of database column general_codes.id */
  private int id;
/** The javabean property equivalent of database column general_codes.group_id */
  private int groupId;
/** The javabean property equivalent of database column general_codes.shortdesc */
  private String shortdesc;
/** The javabean property equivalent of database column general_codes.longdesc */
  private String longdesc;
/** The javabean property equivalent of database column general_codes.gen_ind_value */
  private String genIndValue;
/** The javabean property equivalent of database column general_codes.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column general_codes.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column general_codes.user_id */
  private String userId;
/** The javabean property equivalent of database column general_codes.permcol */
  private String permcol;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public GeneralCodes() throws SystemException {
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
 * Sets the value of member variable shortdesc
 *
 * @author Roy Terrell.
 */
  public void setShortdesc(String value) {
    this.shortdesc = value;
  }
/**
 * Gets the value of member variable shortdesc
 *
 * @author Roy Terrell.
 */
  public String getShortdesc() {
    return this.shortdesc;
  }
/**
 * Sets the value of member variable longdesc
 *
 * @author Roy Terrell.
 */
  public void setLongdesc(String value) {
    this.longdesc = value;
  }
/**
 * Gets the value of member variable longdesc
 *
 * @author Roy Terrell.
 */
  public String getLongdesc() {
    return this.longdesc;
  }
/**
 * Sets the value of member variable genIndValue
 *
 * @author Roy Terrell.
 */
  public void setGenIndValue(String value) {
    this.genIndValue = value;
  }
/**
 * Gets the value of member variable genIndValue
 *
 * @author Roy Terrell.
 */
  public String getGenIndValue() {
    return this.genIndValue;
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
 * Sets the value of member variable permcol
 *
 * @author Roy Terrell.
 */
  public void setPermcol(String value) {
    this.permcol = value;
  }
/**
 * Gets the value of member variable permcol
 *
 * @author Roy Terrell.
 */
  public String getPermcol() {
    return this.permcol;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
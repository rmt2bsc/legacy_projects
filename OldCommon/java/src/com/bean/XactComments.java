package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_comments database table/view.
 *
 * @author Roy Terrell.
 */
public class XactComments extends OrmBean {

/** The javabean property equivalent of database column xact_comments.id */
  private int id;
/** The javabean property equivalent of database column xact_comments.xact_id */
  private int xactId;
/** The javabean property equivalent of database column xact_comments.note */
  private String note;
/** The javabean property equivalent of database column xact_comments.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column xact_comments.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column xact_comments.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public XactComments() throws SystemException {
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
 * Sets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable note
 *
 * @author Roy Terrell.
 */
  public void setNote(String value) {
    this.note = value;
  }
/**
 * Gets the value of member variable note
 *
 * @author Roy Terrell.
 */
  public String getNote() {
    return this.note;
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
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_posting database table/view.
 *
 * @author Roy Terrell.
 */
public class XactPosting extends OrmBean {

/** The javabean property equivalent of database column xact_posting.id */
  private int id;
/** The javabean property equivalent of database column xact_posting.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column xact_posting.xact_id */
  private int xactId;
/** The javabean property equivalent of database column xact_posting.period */
  private String period;
/** The javabean property equivalent of database column xact_posting.period_type_id */
  private int periodTypeId;
/** The javabean property equivalent of database column xact_posting.post_amount */
  private double postAmount;
/** The javabean property equivalent of database column xact_posting.post_date */
  private java.util.Date postDate;
/** The javabean property equivalent of database column xact_posting.post_ref_code */
  private String postRefCode;
/** The javabean property equivalent of database column xact_posting.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column xact_posting.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column xact_posting.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public XactPosting() throws SystemException {
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
 * Sets the value of member variable period
 *
 * @author Roy Terrell.
 */
  public void setPeriod(String value) {
    this.period = value;
  }
/**
 * Gets the value of member variable period
 *
 * @author Roy Terrell.
 */
  public String getPeriod() {
    return this.period;
  }
/**
 * Sets the value of member variable periodTypeId
 *
 * @author Roy Terrell.
 */
  public void setPeriodTypeId(int value) {
    this.periodTypeId = value;
  }
/**
 * Gets the value of member variable periodTypeId
 *
 * @author Roy Terrell.
 */
  public int getPeriodTypeId() {
    return this.periodTypeId;
  }
/**
 * Sets the value of member variable postAmount
 *
 * @author Roy Terrell.
 */
  public void setPostAmount(double value) {
    this.postAmount = value;
  }
/**
 * Gets the value of member variable postAmount
 *
 * @author Roy Terrell.
 */
  public double getPostAmount() {
    return this.postAmount;
  }
/**
 * Sets the value of member variable postDate
 *
 * @author Roy Terrell.
 */
  public void setPostDate(java.util.Date value) {
    this.postDate = value;
  }
/**
 * Gets the value of member variable postDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getPostDate() {
    return this.postDate;
  }
/**
 * Sets the value of member variable postRefCode
 *
 * @author Roy Terrell.
 */
  public void setPostRefCode(String value) {
    this.postRefCode = value;
  }
/**
 * Gets the value of member variable postRefCode
 *
 * @author Roy Terrell.
 */
  public String getPostRefCode() {
    return this.postRefCode;
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
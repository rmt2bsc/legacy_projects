package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_comments database table/view.
 *
 * @author auto generated.
 */
public class XactComments extends OrmBean {




	// Property name constants that belong to respective DataSource, XactCommentsView

/** The property name constant equivalent to property, XactCommentId, of respective DataSource view. */
  public static final String PROP_XACTCOMMENTID = "XactCommentId";
/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, Note, of respective DataSource view. */
  public static final String PROP_NOTE = "Note";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column xact_comments.xact_comment_id */
  private int xactCommentId;
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
 * @author auto generated.
 */
  public XactComments() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable xactCommentId
 *
 * @author auto generated.
 */
  public void setXactCommentId(int value) {
    this.xactCommentId = value;
  }
/**
 * Gets the value of member variable xactCommentId
 *
 * @author atuo generated.
 */
  public int getXactCommentId() {
    return this.xactCommentId;
  }
/**
 * Sets the value of member variable xactId
 *
 * @author auto generated.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author atuo generated.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable note
 *
 * @author auto generated.
 */
  public void setNote(String value) {
    this.note = value;
  }
/**
 * Gets the value of member variable note
 *
 * @author atuo generated.
 */
  public String getNote() {
    return this.note;
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
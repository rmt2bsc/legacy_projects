package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_type_item_activity database table/view.
 *
 * @author Roy Terrell.
 */
public class XactTypeItemActivity extends OrmBean {

/**
     * 
     */
    private static final long serialVersionUID = 2124190852072060867L;
/** The javabean property equivalent of database column xact_type_item_activity.Id */
  private int id;
/** The javabean property equivalent of database column xact_type_item_activity.xact_id */
  private int xactId;
/** The javabean property equivalent of database column xact_type_item_activity.xact_type_item_id */
  private int xactTypeItemId;
/** The javabean property equivalent of database column xact_type_item_activity.amount */
  private double amount;
/** The javabean property equivalent of database column xact_type_item_activity.description */
  private String description;
/** The javabean property equivalent of database column xact_type_item_activity.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column xact_type_item_activity.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column xact_type_item_activity.user_Id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public XactTypeItemActivity() throws SystemException {
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
 * Sets the value of member variable xactTypeItemId
 *
 * @author Roy Terrell.
 */
  public void setXactTypeItemId(int value) {
    this.xactTypeItemId = value;
  }
/**
 * Gets the value of member variable xactTypeItemId
 *
 * @author Roy Terrell.
 */
  public int getXactTypeItemId() {
    return this.xactTypeItemId;
  }
/**
 * Sets the value of member variable amount
 *
 * @author Roy Terrell.
 */
  public void setAmount(double value) {
    this.amount = value;
  }
/**
 * Gets the value of member variable amount
 *
 * @author Roy Terrell.
 */
  public double getAmount() {
    return this.amount;
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
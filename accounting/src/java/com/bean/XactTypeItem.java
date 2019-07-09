package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_type_item database table/view.
 *
 * @author auto generated.
 */
public class XactTypeItem extends OrmBean {




	// Property name constants that belong to respective DataSource, XactTypeItemView

/** The property name constant equivalent to property, XactItemId, of respective DataSource view. */
  public static final String PROP_XACTITEMID = "XactItemId";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column xact_type_item.xact_item_id */
  private int xactItemId;
/** The javabean property equivalent of database column xact_type_item.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column xact_type_item.name */
  private String name;
/** The javabean property equivalent of database column xact_type_item.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column xact_type_item.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column xact_type_item.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public XactTypeItem() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable xactItemId
 *
 * @author auto generated.
 */
  public void setXactItemId(int value) {
    this.xactItemId = value;
  }
/**
 * Gets the value of member variable xactItemId
 *
 * @author atuo generated.
 */
  public int getXactItemId() {
    return this.xactItemId;
  }
/**
 * Sets the value of member variable xactTypeId
 *
 * @author auto generated.
 */
  public void setXactTypeId(int value) {
    this.xactTypeId = value;
  }
/**
 * Gets the value of member variable xactTypeId
 *
 * @author atuo generated.
 */
  public int getXactTypeId() {
    return this.xactTypeId;
  }
/**
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
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
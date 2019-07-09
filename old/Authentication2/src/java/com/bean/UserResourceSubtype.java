package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_resource_subtype database table/view.
 *
 * @author auto generated.
 */
public class UserResourceSubtype extends OrmBean {




	// Property name constants that belong to respective DataSource, UserResourceSubtypeView

/** The property name constant equivalent to property, RsrcSubtypeId, of respective DataSource view. */
  public static final String PROP_RSRCSUBTYPEID = "RsrcSubtypeId";
/** The property name constant equivalent to property, RsrcTypeId, of respective DataSource view. */
  public static final String PROP_RSRCTYPEID = "RsrcTypeId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column user_resource_subtype.rsrc_subtype_id */
  private int rsrcSubtypeId;
/** The javabean property equivalent of database column user_resource_subtype.rsrc_type_id */
  private int rsrcTypeId;
/** The javabean property equivalent of database column user_resource_subtype.name */
  private String name;
/** The javabean property equivalent of database column user_resource_subtype.description */
  private String description;
/** The javabean property equivalent of database column user_resource_subtype.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column user_resource_subtype.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column user_resource_subtype.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public UserResourceSubtype() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable rsrcSubtypeId
 *
 * @author auto generated.
 */
  public void setRsrcSubtypeId(int value) {
    this.rsrcSubtypeId = value;
  }
/**
 * Gets the value of member variable rsrcSubtypeId
 *
 * @author atuo generated.
 */
  public int getRsrcSubtypeId() {
    return this.rsrcSubtypeId;
  }
/**
 * Sets the value of member variable rsrcTypeId
 *
 * @author auto generated.
 */
  public void setRsrcTypeId(int value) {
    this.rsrcTypeId = value;
  }
/**
 * Gets the value of member variable rsrcTypeId
 *
 * @author atuo generated.
 */
  public int getRsrcTypeId() {
    return this.rsrcTypeId;
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
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
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
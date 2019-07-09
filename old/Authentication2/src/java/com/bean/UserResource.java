package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the user_resource database table/view.
 *
 * @author auto generated.
 */
public class UserResource extends OrmBean {




	// Property name constants that belong to respective DataSource, UserResourceView

/** The property name constant equivalent to property, RsrcId, of respective DataSource view. */
  public static final String PROP_RSRCID = "RsrcId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, RsrcTypeId, of respective DataSource view. */
  public static final String PROP_RSRCTYPEID = "RsrcTypeId";
/** The property name constant equivalent to property, RsrcSubtypeId, of respective DataSource view. */
  public static final String PROP_RSRCSUBTYPEID = "RsrcSubtypeId";
/** The property name constant equivalent to property, Url, of respective DataSource view. */
  public static final String PROP_URL = "Url";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, Secured, of respective DataSource view. */
  public static final String PROP_SECURED = "Secured";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, Host, of respective DataSource view. */
  public static final String PROP_HOST = "Host";



	/** The javabean property equivalent of database column user_resource.rsrc_id */
  private int rsrcId;
/** The javabean property equivalent of database column user_resource.name */
  private String name;
/** The javabean property equivalent of database column user_resource.rsrc_type_id */
  private int rsrcTypeId;
/** The javabean property equivalent of database column user_resource.rsrc_subtype_id */
  private int rsrcSubtypeId;
/** The javabean property equivalent of database column user_resource.url */
  private String url;
/** The javabean property equivalent of database column user_resource.description */
  private String description;
/** The javabean property equivalent of database column user_resource.secured */
  private int secured;
/** The javabean property equivalent of database column user_resource.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column user_resource.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column user_resource.user_id */
  private String userId;
/** The javabean property equivalent of database column user_resource.host */
  private String host;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public UserResource() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable rsrcId
 *
 * @author auto generated.
 */
  public void setRsrcId(int value) {
    this.rsrcId = value;
  }
/**
 * Gets the value of member variable rsrcId
 *
 * @author atuo generated.
 */
  public int getRsrcId() {
    return this.rsrcId;
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
 * Sets the value of member variable url
 *
 * @author auto generated.
 */
  public void setUrl(String value) {
    this.url = value;
  }
/**
 * Gets the value of member variable url
 *
 * @author atuo generated.
 */
  public String getUrl() {
    return this.url;
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
 * Sets the value of member variable secured
 *
 * @author auto generated.
 */
  public void setSecured(int value) {
    this.secured = value;
  }
/**
 * Gets the value of member variable secured
 *
 * @author atuo generated.
 */
  public int getSecured() {
    return this.secured;
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
 * Sets the value of member variable host
 *
 * @author auto generated.
 */
  public void setHost(String value) {
    this.host = value;
  }
/**
 * Gets the value of member variable host
 *
 * @author atuo generated.
 */
  public String getHost() {
    return this.host;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
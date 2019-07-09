package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the photo_album database table/view.
 *
 * @author auto generated.
 */
public class PhotoAlbum extends OrmBean {




	// Property name constants that belong to respective DataSource, PhotoAlbumView

/** The property name constant equivalent to property, AlbumId, of respective DataSource view. */
  public static final String PROP_ALBUMID = "AlbumId";
/** The property name constant equivalent to property, AlbumName, of respective DataSource view. */
  public static final String PROP_ALBUMNAME = "AlbumName";
/** The property name constant equivalent to property, AlbumDate, of respective DataSource view. */
  public static final String PROP_ALBUMDATE = "AlbumDate";
/** The property name constant equivalent to property, Location, of respective DataSource view. */
  public static final String PROP_LOCATION = "Location";
/** The property name constant equivalent to property, Servername, of respective DataSource view. */
  public static final String PROP_SERVERNAME = "Servername";
/** The property name constant equivalent to property, Sharename, of respective DataSource view. */
  public static final String PROP_SHARENAME = "Sharename";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column photo_album.album_id */
  private int albumId;
/** The javabean property equivalent of database column photo_album.album_name */
  private String albumName;
/** The javabean property equivalent of database column photo_album.album_date */
  private java.util.Date albumDate;
/** The javabean property equivalent of database column photo_album.location */
  private String location;
/** The javabean property equivalent of database column photo_album.servername */
  private String servername;
/** The javabean property equivalent of database column photo_album.sharename */
  private String sharename;
/** The javabean property equivalent of database column photo_album.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column photo_album.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column photo_album.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public PhotoAlbum() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable albumId
 *
 * @author auto generated.
 */
  public void setAlbumId(int value) {
    this.albumId = value;
  }
/**
 * Gets the value of member variable albumId
 *
 * @author atuo generated.
 */
  public int getAlbumId() {
    return this.albumId;
  }
/**
 * Sets the value of member variable albumName
 *
 * @author auto generated.
 */
  public void setAlbumName(String value) {
    this.albumName = value;
  }
/**
 * Gets the value of member variable albumName
 *
 * @author atuo generated.
 */
  public String getAlbumName() {
    return this.albumName;
  }
/**
 * Sets the value of member variable albumDate
 *
 * @author auto generated.
 */
  public void setAlbumDate(java.util.Date value) {
    this.albumDate = value;
  }
/**
 * Gets the value of member variable albumDate
 *
 * @author atuo generated.
 */
  public java.util.Date getAlbumDate() {
    return this.albumDate;
  }
/**
 * Sets the value of member variable location
 *
 * @author auto generated.
 */
  public void setLocation(String value) {
    this.location = value;
  }
/**
 * Gets the value of member variable location
 *
 * @author atuo generated.
 */
  public String getLocation() {
    return this.location;
  }
/**
 * Sets the value of member variable servername
 *
 * @author auto generated.
 */
  public void setServername(String value) {
    this.servername = value;
  }
/**
 * Gets the value of member variable servername
 *
 * @author atuo generated.
 */
  public String getServername() {
    return this.servername;
  }
/**
 * Sets the value of member variable sharename
 *
 * @author auto generated.
 */
  public void setSharename(String value) {
    this.sharename = value;
  }
/**
 * Gets the value of member variable sharename
 *
 * @author atuo generated.
 */
  public String getSharename() {
    return this.sharename;
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
package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the photo_event database table/view.
 *
 * @author auto generated.
 */
public class PhotoEvent extends OrmBean {




	// Property name constants that belong to respective DataSource, PhotoEventView

/** The property name constant equivalent to property, EventId, of respective DataSource view. */
  public static final String PROP_EVENTID = "EventId";
/** The property name constant equivalent to property, AlbumId, of respective DataSource view. */
  public static final String PROP_ALBUMID = "AlbumId";
/** The property name constant equivalent to property, EventName, of respective DataSource view. */
  public static final String PROP_EVENTNAME = "EventName";
/** The property name constant equivalent to property, Location, of respective DataSource view. */
  public static final String PROP_LOCATION = "Location";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column photo_event.event_id */
  private int eventId;
/** The javabean property equivalent of database column photo_event.album_id */
  private int albumId;
/** The javabean property equivalent of database column photo_event.event_name */
  private String eventName;
/** The javabean property equivalent of database column photo_event.location */
  private String location;
/** The javabean property equivalent of database column photo_event.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column photo_event.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column photo_event.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public PhotoEvent() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable eventId
 *
 * @author auto generated.
 */
  public void setEventId(int value) {
    this.eventId = value;
  }
/**
 * Gets the value of member variable eventId
 *
 * @author atuo generated.
 */
  public int getEventId() {
    return this.eventId;
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
 * Sets the value of member variable eventName
 *
 * @author auto generated.
 */
  public void setEventName(String value) {
    this.eventName = value;
  }
/**
 * Gets the value of member variable eventName
 *
 * @author atuo generated.
 */
  public String getEventName() {
    return this.eventName;
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
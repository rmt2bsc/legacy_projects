package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the av_tracks database table/view.
 *
 * @author auto generated.
 */
public class AvTracks extends OrmBean {




	// Property name constants that belong to respective DataSource, AvTracksView

/** The property name constant equivalent to property, TrackId, of respective DataSource view. */
  public static final String PROP_TRACKID = "TrackId";
/** The property name constant equivalent to property, ProjectId, of respective DataSource view. */
  public static final String PROP_PROJECTID = "ProjectId";
/** The property name constant equivalent to property, TrackNumber, of respective DataSource view. */
  public static final String PROP_TRACKNUMBER = "TrackNumber";
/** The property name constant equivalent to property, TrackTitle, of respective DataSource view. */
  public static final String PROP_TRACKTITLE = "TrackTitle";
/** The property name constant equivalent to property, TrackHours, of respective DataSource view. */
  public static final String PROP_TRACKHOURS = "TrackHours";
/** The property name constant equivalent to property, TrackMinutes, of respective DataSource view. */
  public static final String PROP_TRACKMINUTES = "TrackMinutes";
/** The property name constant equivalent to property, TrackSeconds, of respective DataSource view. */
  public static final String PROP_TRACKSECONDS = "TrackSeconds";
/** The property name constant equivalent to property, TrackDisc, of respective DataSource view. */
  public static final String PROP_TRACKDISC = "TrackDisc";
/** The property name constant equivalent to property, TrackProducer, of respective DataSource view. */
  public static final String PROP_TRACKPRODUCER = "TrackProducer";
/** The property name constant equivalent to property, TrackComposer, of respective DataSource view. */
  public static final String PROP_TRACKCOMPOSER = "TrackComposer";
/** The property name constant equivalent to property, TrackLyricist, of respective DataSource view. */
  public static final String PROP_TRACKLYRICIST = "TrackLyricist";
/** The property name constant equivalent to property, LocServername, of respective DataSource view. */
  public static final String PROP_LOCSERVERNAME = "LocServername";
/** The property name constant equivalent to property, LocSharename, of respective DataSource view. */
  public static final String PROP_LOCSHARENAME = "LocSharename";
/** The property name constant equivalent to property, LocRootPath, of respective DataSource view. */
  public static final String PROP_LOCROOTPATH = "LocRootPath";
/** The property name constant equivalent to property, LocPath, of respective DataSource view. */
  public static final String PROP_LOCPATH = "LocPath";
/** The property name constant equivalent to property, LocFilename, of respective DataSource view. */
  public static final String PROP_LOCFILENAME = "LocFilename";
/** The property name constant equivalent to property, Comments, of respective DataSource view. */
  public static final String PROP_COMMENTS = "Comments";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column av_tracks.track_id */
  private int trackId;
/** The javabean property equivalent of database column av_tracks.project_id */
  private int projectId;
/** The javabean property equivalent of database column av_tracks.track_number */
  private int trackNumber;
/** The javabean property equivalent of database column av_tracks.track_title */
  private String trackTitle;
/** The javabean property equivalent of database column av_tracks.track_hours */
  private int trackHours;
/** The javabean property equivalent of database column av_tracks.track_minutes */
  private int trackMinutes;
/** The javabean property equivalent of database column av_tracks.track_seconds */
  private int trackSeconds;
/** The javabean property equivalent of database column av_tracks.track_disc */
  private String trackDisc;
/** The javabean property equivalent of database column av_tracks.track_producer */
  private String trackProducer;
/** The javabean property equivalent of database column av_tracks.track_composer */
  private String trackComposer;
/** The javabean property equivalent of database column av_tracks.track_lyricist */
  private String trackLyricist;
/** The javabean property equivalent of database column av_tracks.loc_servername */
  private String locServername;
/** The javabean property equivalent of database column av_tracks.loc_sharename */
  private String locSharename;
/** The javabean property equivalent of database column av_tracks.loc_root_path */
  private String locRootPath;
/** The javabean property equivalent of database column av_tracks.loc_path */
  private String locPath;
/** The javabean property equivalent of database column av_tracks.loc_filename */
  private String locFilename;
/** The javabean property equivalent of database column av_tracks.comments */
  private String comments;
/** The javabean property equivalent of database column av_tracks.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column av_tracks.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column av_tracks.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public AvTracks() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable trackId
 *
 * @author auto generated.
 */
  public void setTrackId(int value) {
    this.trackId = value;
  }
/**
 * Gets the value of member variable trackId
 *
 * @author atuo generated.
 */
  public int getTrackId() {
    return this.trackId;
  }
/**
 * Sets the value of member variable projectId
 *
 * @author auto generated.
 */
  public void setProjectId(int value) {
    this.projectId = value;
  }
/**
 * Gets the value of member variable projectId
 *
 * @author atuo generated.
 */
  public int getProjectId() {
    return this.projectId;
  }
/**
 * Sets the value of member variable trackNumber
 *
 * @author auto generated.
 */
  public void setTrackNumber(int value) {
    this.trackNumber = value;
  }
/**
 * Gets the value of member variable trackNumber
 *
 * @author atuo generated.
 */
  public int getTrackNumber() {
    return this.trackNumber;
  }
/**
 * Sets the value of member variable trackTitle
 *
 * @author auto generated.
 */
  public void setTrackTitle(String value) {
    this.trackTitle = value;
  }
/**
 * Gets the value of member variable trackTitle
 *
 * @author atuo generated.
 */
  public String getTrackTitle() {
    return this.trackTitle;
  }
/**
 * Sets the value of member variable trackHours
 *
 * @author auto generated.
 */
  public void setTrackHours(int value) {
    this.trackHours = value;
  }
/**
 * Gets the value of member variable trackHours
 *
 * @author atuo generated.
 */
  public int getTrackHours() {
    return this.trackHours;
  }
/**
 * Sets the value of member variable trackMinutes
 *
 * @author auto generated.
 */
  public void setTrackMinutes(int value) {
    this.trackMinutes = value;
  }
/**
 * Gets the value of member variable trackMinutes
 *
 * @author atuo generated.
 */
  public int getTrackMinutes() {
    return this.trackMinutes;
  }
/**
 * Sets the value of member variable trackSeconds
 *
 * @author auto generated.
 */
  public void setTrackSeconds(int value) {
    this.trackSeconds = value;
  }
/**
 * Gets the value of member variable trackSeconds
 *
 * @author atuo generated.
 */
  public int getTrackSeconds() {
    return this.trackSeconds;
  }
/**
 * Sets the value of member variable trackDisc
 *
 * @author auto generated.
 */
  public void setTrackDisc(String value) {
    this.trackDisc = value;
  }
/**
 * Gets the value of member variable trackDisc
 *
 * @author atuo generated.
 */
  public String getTrackDisc() {
    return this.trackDisc;
  }
/**
 * Sets the value of member variable trackProducer
 *
 * @author auto generated.
 */
  public void setTrackProducer(String value) {
    this.trackProducer = value;
  }
/**
 * Gets the value of member variable trackProducer
 *
 * @author atuo generated.
 */
  public String getTrackProducer() {
    return this.trackProducer;
  }
/**
 * Sets the value of member variable trackComposer
 *
 * @author auto generated.
 */
  public void setTrackComposer(String value) {
    this.trackComposer = value;
  }
/**
 * Gets the value of member variable trackComposer
 *
 * @author atuo generated.
 */
  public String getTrackComposer() {
    return this.trackComposer;
  }
/**
 * Sets the value of member variable trackLyricist
 *
 * @author auto generated.
 */
  public void setTrackLyricist(String value) {
    this.trackLyricist = value;
  }
/**
 * Gets the value of member variable trackLyricist
 *
 * @author atuo generated.
 */
  public String getTrackLyricist() {
    return this.trackLyricist;
  }
/**
 * Sets the value of member variable locServername
 *
 * @author auto generated.
 */
  public void setLocServername(String value) {
    this.locServername = value;
  }
/**
 * Gets the value of member variable locServername
 *
 * @author atuo generated.
 */
  public String getLocServername() {
    return this.locServername;
  }
/**
 * Sets the value of member variable locSharename
 *
 * @author auto generated.
 */
  public void setLocSharename(String value) {
    this.locSharename = value;
  }
/**
 * Gets the value of member variable locSharename
 *
 * @author atuo generated.
 */
  public String getLocSharename() {
    return this.locSharename;
  }
/**
 * Sets the value of member variable locRootPath
 *
 * @author auto generated.
 */
  public void setLocRootPath(String value) {
    this.locRootPath = value;
  }
/**
 * Gets the value of member variable locRootPath
 *
 * @author atuo generated.
 */
  public String getLocRootPath() {
    return this.locRootPath;
  }
/**
 * Sets the value of member variable locPath
 *
 * @author auto generated.
 */
  public void setLocPath(String value) {
    this.locPath = value;
  }
/**
 * Gets the value of member variable locPath
 *
 * @author atuo generated.
 */
  public String getLocPath() {
    return this.locPath;
  }
/**
 * Sets the value of member variable locFilename
 *
 * @author auto generated.
 */
  public void setLocFilename(String value) {
    this.locFilename = value;
  }
/**
 * Gets the value of member variable locFilename
 *
 * @author atuo generated.
 */
  public String getLocFilename() {
    return this.locFilename;
  }
/**
 * Sets the value of member variable comments
 *
 * @author auto generated.
 */
  public void setComments(String value) {
    this.comments = value;
  }
/**
 * Gets the value of member variable comments
 *
 * @author atuo generated.
 */
  public String getComments() {
    return this.comments;
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
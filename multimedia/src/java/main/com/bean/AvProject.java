package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the av_project database table/view.
 *
 * @author auto generated.
 */
public class AvProject extends OrmBean {




	// Property name constants that belong to respective DataSource, AvProjectView

/** The property name constant equivalent to property, ProjectId, of respective DataSource view. */
  public static final String PROP_PROJECTID = "ProjectId";
/** The property name constant equivalent to property, ArtistId, of respective DataSource view. */
  public static final String PROP_ARTISTID = "ArtistId";
/** The property name constant equivalent to property, ProjectTypeId, of respective DataSource view. */
  public static final String PROP_PROJECTTYPEID = "ProjectTypeId";
/** The property name constant equivalent to property, GenreId, of respective DataSource view. */
  public static final String PROP_GENREID = "GenreId";
/** The property name constant equivalent to property, MediaTypeId, of respective DataSource view. */
  public static final String PROP_MEDIATYPEID = "MediaTypeId";
/** The property name constant equivalent to property, Title, of respective DataSource view. */
  public static final String PROP_TITLE = "Title";
/** The property name constant equivalent to property, Year, of respective DataSource view. */
  public static final String PROP_YEAR = "Year";
/** The property name constant equivalent to property, MasterDupId, of respective DataSource view. */
  public static final String PROP_MASTERDUPID = "MasterDupId";
/** The property name constant equivalent to property, Ripped, of respective DataSource view. */
  public static final String PROP_RIPPED = "Ripped";
/** The property name constant equivalent to property, Cost, of respective DataSource view. */
  public static final String PROP_COST = "Cost";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column av_project.project_id */
  private int projectId;
/** The javabean property equivalent of database column av_project.artist_id */
  private int artistId;
/** The javabean property equivalent of database column av_project.project_type_id */
  private int projectTypeId;
/** The javabean property equivalent of database column av_project.genre_id */
  private int genreId;
/** The javabean property equivalent of database column av_project.media_type_id */
  private int mediaTypeId;
/** The javabean property equivalent of database column av_project.title */
  private String title;
/** The javabean property equivalent of database column av_project.year */
  private int year;
/** The javabean property equivalent of database column av_project.master_dup_id */
  private int masterDupId;
/** The javabean property equivalent of database column av_project.ripped */
  private int ripped;
/** The javabean property equivalent of database column av_project.cost */
  private double cost;
/** The javabean property equivalent of database column av_project.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column av_project.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column av_project.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public AvProject() throws SystemException {
	super();
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
 * Sets the value of member variable artistId
 *
 * @author auto generated.
 */
  public void setArtistId(int value) {
    this.artistId = value;
  }
/**
 * Gets the value of member variable artistId
 *
 * @author atuo generated.
 */
  public int getArtistId() {
    return this.artistId;
  }
/**
 * Sets the value of member variable projectTypeId
 *
 * @author auto generated.
 */
  public void setProjectTypeId(int value) {
    this.projectTypeId = value;
  }
/**
 * Gets the value of member variable projectTypeId
 *
 * @author atuo generated.
 */
  public int getProjectTypeId() {
    return this.projectTypeId;
  }
/**
 * Sets the value of member variable genreId
 *
 * @author auto generated.
 */
  public void setGenreId(int value) {
    this.genreId = value;
  }
/**
 * Gets the value of member variable genreId
 *
 * @author atuo generated.
 */
  public int getGenreId() {
    return this.genreId;
  }
/**
 * Sets the value of member variable mediaTypeId
 *
 * @author auto generated.
 */
  public void setMediaTypeId(int value) {
    this.mediaTypeId = value;
  }
/**
 * Gets the value of member variable mediaTypeId
 *
 * @author atuo generated.
 */
  public int getMediaTypeId() {
    return this.mediaTypeId;
  }
/**
 * Sets the value of member variable title
 *
 * @author auto generated.
 */
  public void setTitle(String value) {
    this.title = value;
  }
/**
 * Gets the value of member variable title
 *
 * @author atuo generated.
 */
  public String getTitle() {
    return this.title;
  }
/**
 * Sets the value of member variable year
 *
 * @author auto generated.
 */
  public void setYear(int value) {
    this.year = value;
  }
/**
 * Gets the value of member variable year
 *
 * @author atuo generated.
 */
  public int getYear() {
    return this.year;
  }
/**
 * Sets the value of member variable masterDupId
 *
 * @author auto generated.
 */
  public void setMasterDupId(int value) {
    this.masterDupId = value;
  }
/**
 * Gets the value of member variable masterDupId
 *
 * @author atuo generated.
 */
  public int getMasterDupId() {
    return this.masterDupId;
  }
/**
 * Sets the value of member variable ripped
 *
 * @author auto generated.
 */
  public void setRipped(int value) {
    this.ripped = value;
  }
/**
 * Gets the value of member variable ripped
 *
 * @author atuo generated.
 */
  public int getRipped() {
    return this.ripped;
  }
/**
 * Sets the value of member variable cost
 *
 * @author auto generated.
 */
  public void setCost(double value) {
    this.cost = value;
  }
/**
 * Gets the value of member variable cost
 *
 * @author atuo generated.
 */
  public double getCost() {
    return this.cost;
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
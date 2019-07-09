package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_photo database table/view.
 *
 * @author auto generated.
 */
public class VwPhoto extends OrmBean {




	// Property name constants that belong to respective DataSource, VwPhotoView

/** The property name constant equivalent to property, AlbumId, of respective DataSource view. */
  public static final String PROP_ALBUMID = "AlbumId";
/** The property name constant equivalent to property, AlbumName, of respective DataSource view. */
  public static final String PROP_ALBUMNAME = "AlbumName";
/** The property name constant equivalent to property, AlbumDate, of respective DataSource view. */
  public static final String PROP_ALBUMDATE = "AlbumDate";
/** The property name constant equivalent to property, EventId, of respective DataSource view. */
  public static final String PROP_EVENTID = "EventId";
/** The property name constant equivalent to property, EventName, of respective DataSource view. */
  public static final String PROP_EVENTNAME = "EventName";
/** The property name constant equivalent to property, ImageId, of respective DataSource view. */
  public static final String PROP_IMAGEID = "ImageId";
/** The property name constant equivalent to property, DirPath, of respective DataSource view. */
  public static final String PROP_DIRPATH = "DirPath";
/** The property name constant equivalent to property, FileName, of respective DataSource view. */
  public static final String PROP_FILENAME = "FileName";
/** The property name constant equivalent to property, FileSize, of respective DataSource view. */
  public static final String PROP_FILESIZE = "FileSize";
/** The property name constant equivalent to property, FileExt, of respective DataSource view. */
  public static final String PROP_FILEEXT = "FileExt";
/** The property name constant equivalent to property, MimeTypeId, of respective DataSource view. */
  public static final String PROP_MIMETYPEID = "MimeTypeId";
/** The property name constant equivalent to property, MimeTypeFileExt, of respective DataSource view. */
  public static final String PROP_MIMETYPEFILEEXT = "MimeTypeFileExt";
/** The property name constant equivalent to property, MediaType, of respective DataSource view. */
  public static final String PROP_MEDIATYPE = "MediaType";



	/** The javabean property equivalent of database column vw_photo.album_id */
  private int albumId;
/** The javabean property equivalent of database column vw_photo.album_name */
  private String albumName;
/** The javabean property equivalent of database column vw_photo.album_date */
  private java.util.Date albumDate;
/** The javabean property equivalent of database column vw_photo.event_id */
  private int eventId;
/** The javabean property equivalent of database column vw_photo.event_name */
  private String eventName;
/** The javabean property equivalent of database column vw_photo.image_id */
  private int imageId;
/** The javabean property equivalent of database column vw_photo.dir_path */
  private String dirPath;
/** The javabean property equivalent of database column vw_photo.file_name */
  private String fileName;
/** The javabean property equivalent of database column vw_photo.file_size */
  private int fileSize;
/** The javabean property equivalent of database column vw_photo.file_ext */
  private String fileExt;
/** The javabean property equivalent of database column vw_photo.mime_type_id */
  private int mimeTypeId;
/** The javabean property equivalent of database column vw_photo.mime_type_file_ext */
  private String mimeTypeFileExt;
/** The javabean property equivalent of database column vw_photo.media_type */
  private String mediaType;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwPhoto() throws SystemException {
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
 * Sets the value of member variable imageId
 *
 * @author auto generated.
 */
  public void setImageId(int value) {
    this.imageId = value;
  }
/**
 * Gets the value of member variable imageId
 *
 * @author atuo generated.
 */
  public int getImageId() {
    return this.imageId;
  }
/**
 * Sets the value of member variable dirPath
 *
 * @author auto generated.
 */
  public void setDirPath(String value) {
    this.dirPath = value;
  }
/**
 * Gets the value of member variable dirPath
 *
 * @author atuo generated.
 */
  public String getDirPath() {
    return this.dirPath;
  }
/**
 * Sets the value of member variable fileName
 *
 * @author auto generated.
 */
  public void setFileName(String value) {
    this.fileName = value;
  }
/**
 * Gets the value of member variable fileName
 *
 * @author atuo generated.
 */
  public String getFileName() {
    return this.fileName;
  }
/**
 * Sets the value of member variable fileSize
 *
 * @author auto generated.
 */
  public void setFileSize(int value) {
    this.fileSize = value;
  }
/**
 * Gets the value of member variable fileSize
 *
 * @author atuo generated.
 */
  public int getFileSize() {
    return this.fileSize;
  }
/**
 * Sets the value of member variable fileExt
 *
 * @author auto generated.
 */
  public void setFileExt(String value) {
    this.fileExt = value;
  }
/**
 * Gets the value of member variable fileExt
 *
 * @author atuo generated.
 */
  public String getFileExt() {
    return this.fileExt;
  }
/**
 * Sets the value of member variable mimeTypeId
 *
 * @author auto generated.
 */
  public void setMimeTypeId(int value) {
    this.mimeTypeId = value;
  }
/**
 * Gets the value of member variable mimeTypeId
 *
 * @author atuo generated.
 */
  public int getMimeTypeId() {
    return this.mimeTypeId;
  }
/**
 * Sets the value of member variable mimeTypeFileExt
 *
 * @author auto generated.
 */
  public void setMimeTypeFileExt(String value) {
    this.mimeTypeFileExt = value;
  }
/**
 * Gets the value of member variable mimeTypeFileExt
 *
 * @author atuo generated.
 */
  public String getMimeTypeFileExt() {
    return this.mimeTypeFileExt;
  }
/**
 * Sets the value of member variable mediaType
 *
 * @author auto generated.
 */
  public void setMediaType(String value) {
    this.mediaType = value;
  }
/**
 * Gets the value of member variable mediaType
 *
 * @author atuo generated.
 */
  public String getMediaType() {
    return this.mediaType;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
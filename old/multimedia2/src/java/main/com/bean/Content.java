package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the content database table/view.
 *
 * @author auto generated.
 */
public class Content extends OrmBean {




	// Property name constants that belong to respective DataSource, ContentView

/** The property name constant equivalent to property, ContentId, of respective DataSource view. */
  public static final String PROP_CONTENTID = "ContentId";
/** The property name constant equivalent to property, MimeTypeId, of respective DataSource view. */
  public static final String PROP_MIMETYPEID = "MimeTypeId";
/** The property name constant equivalent to property, ImageData, of respective DataSource view. */
  public static final String PROP_IMAGEDATA = "ImageData";
/** The property name constant equivalent to property, TextData, of respective DataSource view. */
  public static final String PROP_TEXTDATA = "TextData";
/** The property name constant equivalent to property, AppCode, of respective DataSource view. */
  public static final String PROP_APPCODE = "AppCode";
/** The property name constant equivalent to property, ModuleCode, of respective DataSource view. */
  public static final String PROP_MODULECODE = "ModuleCode";
/** The property name constant equivalent to property, Filepath, of respective DataSource view. */
  public static final String PROP_FILEPATH = "Filepath";
/** The property name constant equivalent to property, Filename, of respective DataSource view. */
  public static final String PROP_FILENAME = "Filename";
/** The property name constant equivalent to property, Size, of respective DataSource view. */
  public static final String PROP_SIZE = "Size";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column content.content_id */
  private int contentId;
/** The javabean property equivalent of database column content.mime_type_id */
  private int mimeTypeId;
/** The javabean property equivalent of database column content.image_data */
  private Object imageData;
/** The javabean property equivalent of database column content.text_data */
  private String textData;
/** The javabean property equivalent of database column content.app_code */
  private String appCode;
/** The javabean property equivalent of database column content.module_code */
  private String moduleCode;
/** The javabean property equivalent of database column content.filepath */
  private String filepath;
/** The javabean property equivalent of database column content.filename */
  private String filename;
/** The javabean property equivalent of database column content.size */
  private int size;
/** The javabean property equivalent of database column content.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column content.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Content() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable contentId
 *
 * @author auto generated.
 */
  public void setContentId(int value) {
    this.contentId = value;
  }
/**
 * Gets the value of member variable contentId
 *
 * @author atuo generated.
 */
  public int getContentId() {
    return this.contentId;
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
 * Sets the value of member variable imageData
 *
 * @author auto generated.
 */
  public void setImageData(Object value) {
    this.imageData = value;
  }
/**
 * Gets the value of member variable imageData
 *
 * @author atuo generated.
 */
  public Object getImageData() {
    return this.imageData;
  }
/**
 * Sets the value of member variable textData
 *
 * @author auto generated.
 */
  public void setTextData(String value) {
    this.textData = value;
  }
/**
 * Gets the value of member variable textData
 *
 * @author atuo generated.
 */
  public String getTextData() {
    return this.textData;
  }
/**
 * Sets the value of member variable appCode
 *
 * @author auto generated.
 */
  public void setAppCode(String value) {
    this.appCode = value;
  }
/**
 * Gets the value of member variable appCode
 *
 * @author atuo generated.
 */
  public String getAppCode() {
    return this.appCode;
  }
/**
 * Sets the value of member variable moduleCode
 *
 * @author auto generated.
 */
  public void setModuleCode(String value) {
    this.moduleCode = value;
  }
/**
 * Gets the value of member variable moduleCode
 *
 * @author atuo generated.
 */
  public String getModuleCode() {
    return this.moduleCode;
  }
/**
 * Sets the value of member variable filepath
 *
 * @author auto generated.
 */
  public void setFilepath(String value) {
    this.filepath = value;
  }
/**
 * Gets the value of member variable filepath
 *
 * @author atuo generated.
 */
  public String getFilepath() {
    return this.filepath;
  }
/**
 * Sets the value of member variable filename
 *
 * @author auto generated.
 */
  public void setFilename(String value) {
    this.filename = value;
  }
/**
 * Gets the value of member variable filename
 *
 * @author atuo generated.
 */
  public String getFilename() {
    return this.filename;
  }
/**
 * Sets the value of member variable size
 *
 * @author auto generated.
 */
  public void setSize(int value) {
    this.size = value;
  }
/**
 * Gets the value of member variable size
 *
 * @author atuo generated.
 */
  public int getSize() {
    return this.size;
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
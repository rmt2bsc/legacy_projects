package com.bean;


import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the mime_types database table/view.
 *
 * @author auto generated.
 */
public class MimeTypes extends OrmBean {

    private static final long serialVersionUID = -9066188506202824054L;
	// Property name constants that belong to respective DataSource, MimeTypesView

/** The property name constant equivalent to property, MimeTypeId, of respective DataSource view. */
  public static final String PROP_MIMETYPEID = "MimeTypeId";
/** The property name constant equivalent to property, FileExt, of respective DataSource view. */
  public static final String PROP_FILEEXT = "FileExt";
/** The property name constant equivalent to property, MediaType, of respective DataSource view. */
  public static final String PROP_MEDIATYPE = "MediaType";



	/** The javabean property equivalent of database column mime_types.mime_type_id */
  private int mimeTypeId;
/** The javabean property equivalent of database column mime_types.file_ext */
  private String fileExt;
/** The javabean property equivalent of database column mime_types.media_type */
  private String mediaType;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public MimeTypes() throws SystemException {
	super();
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
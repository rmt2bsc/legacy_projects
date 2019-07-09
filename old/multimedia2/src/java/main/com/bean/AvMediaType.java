package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the av_media_type database table/view.
 *
 * @author auto generated.
 */
public class AvMediaType extends OrmBean {




	// Property name constants that belong to respective DataSource, AvMediaTypeView

/** The property name constant equivalent to property, MediaTypeId, of respective DataSource view. */
  public static final String PROP_MEDIATYPEID = "MediaTypeId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column av_media_type.media_type_id */
  private int mediaTypeId;
/** The javabean property equivalent of database column av_media_type.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public AvMediaType() throws SystemException {
	super();
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
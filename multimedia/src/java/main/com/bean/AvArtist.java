package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the av_artist database table/view.
 *
 * @author auto generated.
 */
public class AvArtist extends OrmBean {




	// Property name constants that belong to respective DataSource, AvArtistView

/** The property name constant equivalent to property, ArtistId, of respective DataSource view. */
  public static final String PROP_ARTISTID = "ArtistId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";



	/** The javabean property equivalent of database column av_artist.artist_id */
  private int artistId;
/** The javabean property equivalent of database column av_artist.name */
  private String name;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public AvArtist() throws SystemException {
	super();
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
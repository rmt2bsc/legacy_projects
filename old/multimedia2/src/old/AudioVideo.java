package com.audiovideo;


import java.util.Date;
import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * 
 * @author appdev
 * @deprecated refer to {@link com.bean.AvProject}
 */
public class AudioVideo extends RMT2BaseBean {

  private int id;
  private int glAccountId;
  private int artistId;
  private String title;
  private int genreId;
  private int mediaTypeId;
  private int yearRecorded;
  private int masterDupId;
  private java.util.Date dateCreated;
  private java.util.Date dateUpdated;
  private String userId;
  private int ripped;



	// Getter/Setter Methods

  public AudioVideo() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
  public int getGlAccountId() {
    return this.glAccountId;
  }
  public void setArtistId(int value) {
    this.artistId = value;
  }
  public int getArtistId() {
    return this.artistId;
  }
  public void setTitle(String value) {
    this.title = value;
  }
  public String getTitle() {
    return this.title;
  }
  public void setGenreId(int value) {
    this.genreId = value;
  }
  public int getGenreId() {
    return this.genreId;
  }
  public void setMediaTypeId(int value) {
    this.mediaTypeId = value;
  }
  public int getMediaTypeId() {
    return this.mediaTypeId;
  }
  public void setYearRecorded(int value) {
    this.yearRecorded = value;
  }
  public int getYearRecorded() {
    return this.yearRecorded;
  }
  public void setMasterDupId(int value) {
    this.masterDupId = value;
  }
  public int getMasterDupId() {
    return this.masterDupId;
  }
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
  public void setUserId(String value) {
    this.userId = value;
  }
  public String getUserId() {
    return this.userId;
  }
  public int getRipped() {
  	return this.ripped;
  }
  public void setRipped(int value) {
  	this.ripped = value;
  }
  public void initBean() throws SystemException {}
}
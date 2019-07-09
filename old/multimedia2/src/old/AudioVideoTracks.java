package com.audiovideo;


import java.util.Date;

import com.audiovideo.AudioVideoException;

import com.bean.RMT2BaseBean;

import com.util.SystemException;

/**
 * 
 * @author appdev
 *@deprecated refer to {@link com.bean.AvTracks}
 */
public class AudioVideoTracks extends RMT2BaseBean {

  private int id;
  private int audioVideoId;
  private int trackNumber;
  private String trackTitle;
  private int trackHours;
  private int trackMinutes;
  private int trackSeconds;
  private String trackDisc;
  private String trackProducer;
  private String trackComposer;
  private String trackLyricist;
  private String locServername;
  private String locSharename;
  private String locRootPath;
  private String locPath;
  private String locFilename;
  private String comments;
  private java.util.Date dateCreated;
  private java.util.Date dateUpdated;
  private String userId;



	// Getter/Setter Methods

  public AudioVideoTracks() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setAudioVideoId(int value) {
    this.audioVideoId = value;
  }
  public int getAudioVideoId() {
    return this.audioVideoId;
  }
  public void setTrackNumber(int value) {
    this.trackNumber = value;
  }
  public int getTrackNumber() {
    return this.trackNumber;
  }
  public void setTrackTitle(String value) {
    this.trackTitle = value;
  }
  public String getTrackTitle() {
    return this.trackTitle;
  }
  public void setTrackHours(int value) {
    this.trackHours = value;
  }
  public int getTrackHours() {
    return this.trackHours;
  }
  public void setTrackMinutes(int value) {
    this.trackMinutes = value;
  }
  public int getTrackMinutes() {
    return this.trackMinutes;
  }
  public void setTrackSeconds(int value) {
    this.trackSeconds = value;
  }
  public int getTrackSeconds() {
    return this.trackSeconds;
  }
  public void setTrackDisc(String value) {
    this.trackDisc = value;
  }
  public String getTrackDisc() {
    return this.trackDisc;
  }
  public void setTrackProducer(String value) {
    this.trackProducer = value;
  }
  public String getTrackProducer() {
    return this.trackProducer;
  }
  public void setTrackComposer(String value) {
    this.trackComposer = value;
  }
  public String getTrackComposer() {
    return this.trackComposer;
  }
  public void setTrackLyricist(String value) {
    this.trackLyricist = value;
  }
  public String getTrackLyricist() {
    return this.trackLyricist;
  }
  public void setLocServername(String value) {
    this.locServername = value;
  }
  public String getLocServername() {
    return this.locServername;
  }
  public void setLocSharename(String value) {
    this.locSharename = value;
  }
  public String getLocSharename() {
    return this.locSharename;
  }
  public void setLocRootPath(String value) {
    this.locRootPath = value;
  }
  public String getLocRootPath() {
    return this.locRootPath;
  }
  public void setLocPath(String value) {
    this.locPath = value;
  }
  public String getLocPath() {
    return this.locPath;
  }
  public void setLocFilename(String value) {
    this.locFilename = value;
  }
  public String getLocFilename() {
    return this.locFilename;
  }
  public void setComments(String value) {
    this.comments = value;
  }
  public String getComments() {
    return this.comments;
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
  public void initBean() throws SystemException {}
}
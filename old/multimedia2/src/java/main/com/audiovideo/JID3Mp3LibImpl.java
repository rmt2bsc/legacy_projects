package com.audiovideo;

import java.io.File;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.farng.mp3.MP3File;
import org.farng.mp3.id3.FrameBodyTALB;
import org.farng.mp3.id3.FrameBodyTCON;
import org.farng.mp3.id3.FrameBodyTIT2;
import org.farng.mp3.id3.FrameBodyTLEN;
import org.farng.mp3.id3.FrameBodyTPE1;
import org.farng.mp3.id3.FrameBodyTRCK;
import org.farng.mp3.id3.FrameBodyTYER;
import org.farng.mp3.id3.ID3v2_2;
import org.farng.mp3.id3.ID3v2_2Frame;

import com.bean.RMT2Base;

import com.util.SystemException;

/**
 * @author appdev
 *
 */
public class JID3Mp3LibImpl extends RMT2Base implements MP3Reader {

    private static Logger logger = Logger.getLogger(JID3Mp3LibImpl.class);

    private MP3File mp3file;

    private ID3v2_2 tag;

    private String value;

    /**
     * 
     */
    JID3Mp3LibImpl() {
	return;
    }

    JID3Mp3LibImpl(File mp3Source) {
	try {
	    this.mp3file = new MP3File(mp3Source);
	    this.tag = (ID3v2_2) mp3file.getID3v2Tag();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SystemException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getAlbum()
     */
    public String getAlbum() {
	// Get Album
	ID3v2_2Frame frame = null;
	this.value = null;
	frame = (ID3v2_2Frame) tag.getFrame("TALB");
	if (frame != null) {
	    value = ((FrameBodyTALB) frame.getBody()).getText();
	}
	return this.value;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getArtist()
     */
    public String getArtist() {
	// Get Artist
	ID3v2_2Frame frame = null;
	this.value = null;
	frame = (ID3v2_2Frame) tag.getFrame("TPE1");
	if (frame != null) {
	    this.value = ((FrameBodyTPE1) frame.getBody()).getText();
	}
	return this.value;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getComment()
     */
    public String getComment() {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getComposer()
     */
    public String getComposer() {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getDiscNumber()
     */
    public int getDiscNumber() {
	return 1;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getDurationSeconds()
     */
    public List<Integer> getDuration() {
	// Get Recording Time
	long ms;
	int hr;
	int min;
	int sec;
	GregorianCalendar tm;
	ID3v2_2Frame frame = null;
	this.value = null;
	List<Integer> list = new ArrayList<Integer>();
	frame = (ID3v2_2Frame) tag.getFrame("TLEN");
	if (frame != null) {
	    value = ((FrameBodyTLEN) frame.getBody()).getText();
	    try {
		ms = new Long(value).longValue();
	    }
	    catch (NumberFormatException e) {
		ms = 0;
	    }
	    tm = new GregorianCalendar();
	    tm.setTime(new java.util.Date(ms));
	    hr = tm.get(Calendar.HOUR);
	    min = tm.get(Calendar.MINUTE);
	    sec = tm.get(Calendar.SECOND);

	    list.add(hr);
	    list.add(min);
	    list.add(sec);
	    return list;
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getGenre()
     */
    public String getGenre() {
	// Get Genre
	ID3v2_2Frame frame = null;
	this.value = null;
	frame = (ID3v2_2Frame) tag.getFrame("TCON");
	if (frame != null) {
	    this.value = ((FrameBodyTCON) frame.getBody()).getText();
	    this.value = (value == null ? "Unknown" : value);
	}
	return this.value;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getLyricist()
     */
    public String getLyricist() {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getProducer()
     */
    public String getProducer() {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getTrack()
     */
    public int getTrack() {
	// Get Track Number
	ID3v2_2Frame frame = null;
	this.value = null;
	int track = 0;
	frame = (ID3v2_2Frame) tag.getFrame("TRCK");
	if (frame != null) {
	    value = ((FrameBodyTRCK) frame.getBody()).getText();
	    try {
		track = new Integer(value).intValue();
	    }
	    catch (NumberFormatException e) {
		track = 0;
	    }
	}
	return track;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getTrackCount()
     */
    public int getTrackCount() {
	throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getTrackTitle()
     */
    public String getTrackTitle() {
	// Get Track Title
	ID3v2_2Frame frame = null;
	this.value = null;
	frame = (ID3v2_2Frame) tag.getFrame("TIT2");
	if (frame != null) {
	    this.value = ((FrameBodyTIT2) frame.getBody()).getText();
	    this.value = (value == null ? "Unable to Access Title Track Name" : value);
	}
	return this.value;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getYear()
     */
    public int getYear() {
	// Get Year Released
	ID3v2_2Frame frame = null;
	this.value = null;
	int year = 0;
	frame = (ID3v2_2Frame) tag.getFrame("TYER");
	if (frame != null) {
	    value = ((FrameBodyTYER) frame.getBody()).getText();
	    try {
		year = new Integer(value).intValue();
	    }
	    catch (NumberFormatException e) {
		year = 0;
	    }
	}
	return year;
    }

}

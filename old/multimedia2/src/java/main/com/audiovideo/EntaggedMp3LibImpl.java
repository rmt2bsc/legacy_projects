package com.audiovideo;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;
import java.util.List;

import com.bean.RMT2Base;
import com.util.RMT2Date;
import com.util.SystemException;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;

/**
 * @author appdev
 *
 */
public class EntaggedMp3LibImpl extends RMT2Base implements MP3Reader {

    private static Logger logger = Logger.getLogger(EntaggedMp3LibImpl.class);
    
    private AudioFile mp3;
    
    
    /**
     * 
     */
    EntaggedMp3LibImpl() {
	return;
    }

    EntaggedMp3LibImpl(File source) {
	try {
	    this.mp3 = AudioFileIO.read(source);
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
	return this.mp3.getTag().getFirstAlbum();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getArtist()
     */
    public String getArtist() {
	return this.mp3.getTag().getFirstArtist();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getComment()
     */
    public String getComment() {
	List<String> list = this.mp3.getTag().getComment();
	StringBuffer buf = new StringBuffer();
	if (list != null && list.size() > 0) {
	    for (String item : list) {
		buf.append(item);
	    }
	    return buf.toString();
	}
	return null; 
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
    public List <Integer> getDuration() {
	int seconds = this.mp3.getLength();
	List <Integer> list = RMT2Date.convertSecondsToList(seconds);
	return list;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getGenre()
     */
    public String getGenre() {
	List<String> list = this.mp3.getTag().getGenre();
	StringBuffer buf = new StringBuffer();
	if (list != null && list.size() > 0) {
	    for (String item : list) {
		return item;
	    }
	}
	return null; 
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
	String strTrack = this.mp3.getTag().getFirstTrack();
	int track ;
	try {
	    track = Integer.parseInt(strTrack);
	}
	catch (NumberFormatException e) {
	    track = 0;
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
	return this.mp3.getTag().getFirstTitle();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getYear()
     */
    public int getYear() {
	String strYear = this.mp3.getTag().getFirstYear();
	int year;
	try {
	    year = Integer.parseInt(strYear);
	}
	catch (NumberFormatException e) {
	    year = 0;
	}
	return year;
    }

}

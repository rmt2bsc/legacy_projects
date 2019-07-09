package com.audiovideo;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.File;
import java.util.List;

import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import com.bean.RMT2Base;
import com.util.RMT2Date;
import com.util.SystemException;

/**
 * @author appdev
 *
 */
public class MyMp3LibImpl extends RMT2Base implements MP3Reader {

    private static Logger logger = Logger.getLogger(MyMp3LibImpl.class);
    
    private MusicMetadataSet metaDataSet;
    
    private IMusicMetadata mp3Data;
    
    
    
    
    /**
     * 
     */
    MyMp3LibImpl() {
	return;
    }

    MyMp3LibImpl(File source) {
	if (source == null) {
	    this.msg = "Source audio/video file is invalid or null";
	    logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
	try {
	    System.out.println("Processing: " + source.getPath());
	    this.metaDataSet = new MyID3().read(source);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SystemException(e);
	}
	if (this.metaDataSet == null) {
	    this.msg = "MP3 source file does not contain any meta data";
	    logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
	this.mp3Data = this.metaDataSet.getSimplified();
    }
    
    
    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getAlbum()
     */
    public String getAlbum() {
	return this.mp3Data.getAlbum();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getArtist()
     */
    public String getArtist() {
	return this.mp3Data.getArtist();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getComment()
     */
    public String getComment() {
	List<String> list = this.mp3Data.getComments();
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
	return this.mp3Data.getComposer();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getDiscNumber()
     */
    public int getDiscNumber() {
	Number obj = this.mp3Data.getDiscNumber();
	if (obj == null) {
	    return 1;
	}
	int discNo = obj.intValue();
	return discNo;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getDurationSeconds()
     */
    public List <Integer> getDuration() {
	Number obj = this.mp3Data.getDurationSeconds();
	if (obj == null) {
	    return null;
	}
	int seconds = obj.intValue();
	List <Integer> list = RMT2Date.convertSecondsToList(seconds);
	return list;
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getGenre()
     */
    public String getGenre() {
	return this.mp3Data.getGenreName();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getLyricist()
     */
    public String getLyricist() {
	return this.mp3Data.getLyricist();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getProducer()
     */
    public String getProducer() {
	return this.mp3Data.getProducer();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getTrack()
     */
    public int getTrack() {
	return this.mp3Data.getTrackNumberNumeric().intValue();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getTrackCount()
     */
    public int getTrackCount() {
	return this.mp3Data.getTrackCount().intValue();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getTrackTitle()
     */
    public String getTrackTitle() {
	return this.mp3Data.getSongTitle();
    }

    /* (non-Javadoc)
     * @see com.audiovideo.MP3Reader#getYear()
     */
    public int getYear() {
	return this.mp3Data.getYear().intValue();
    }

}

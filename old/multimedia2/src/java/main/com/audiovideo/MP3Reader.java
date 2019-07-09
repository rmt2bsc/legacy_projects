package com.audiovideo;

import java.util.List;

/**
 * @author appdev
 *
 */
public interface MP3Reader {
    
    /**
     * 
     * @return
     */
    String getArtist();
    
    /**
     * 
     * @return
     */
    String getAlbum();
    
    /**
     * 
     * @return
     */
    String getTrackTitle();
    
    /**
     * 
     * @return
     */
    int getTrack();
    
    /**
     * 
     * @return
     */
    int getYear();
    
    /**
     * 
     * @return
     */
    List <Integer> getDuration();
    
    /**
     * 
     * @return
     */
    String getGenre();
    
    /**
     * 
     * @return
     */
    String getComment();
    
    /**
     * 
     * @return
     */
    String getComposer();
    
    /**
     * 
     * @return
     */
    String getLyricist();
    
    /**
     * 
     * @return
     */
    String getProducer();
    
    /**
     * 
     * @return
     */
    int getDiscNumber();
    
    /**
     * 
     * @return
     */
    int getTrackCount();
    
}

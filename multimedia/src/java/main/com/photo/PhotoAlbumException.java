package com.photo;

import com.util.RMT2Exception;


/**
 * 
 * @author rterrell
 *
 */
public class PhotoAlbumException extends RMT2Exception {
    private static final long serialVersionUID = -4204042569634960159L;

    /**
     * 
     */
    public PhotoAlbumException() {
	super();
    }

    /**
     * 
     * @param msg
     */
    public PhotoAlbumException(String msg) {
	super(msg);
    }

    /**
     * 
     * @param e
     */
    public PhotoAlbumException(Exception e) {
	super(e);
    }
    
    /**
     * 
     * @param msg
     * @param e
     */
    public PhotoAlbumException(String msg, Throwable e) {
	super(msg, e);
    }

    
}

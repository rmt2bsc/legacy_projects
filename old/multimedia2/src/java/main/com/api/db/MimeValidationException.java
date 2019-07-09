package com.api.db;



import com.util.RMT2RuntimeException;

/**
 * An exception thrown when the filename of the MIME data file is found to be invalid.
 * 
 * @author appdev
 *
 */
public class MimeValidationException extends RMT2RuntimeException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public MimeValidationException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public MimeValidationException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }

    public MimeValidationException(String msg, int errorCode) {
	this(msg);
	this.errorCode = errorCode;
    } 
    
    /**
     * @param e
     */
    public MimeValidationException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public MimeValidationException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

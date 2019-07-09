package com.api.filehandler;



import com.util.RMT2RuntimeException;

/**
 * An exception thrown when the format of the source data file's filename is incorrect.
 * 
 * @author appdev
 *
 */
public class InvalidMimeFileFormatException extends RMT2RuntimeException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public InvalidMimeFileFormatException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public InvalidMimeFileFormatException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }

   
    /**
     * @param e
     */
    public InvalidMimeFileFormatException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public InvalidMimeFileFormatException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

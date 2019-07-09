package com.api.filehandler;



import com.util.RMT2RuntimeException;

/**
 * An exception thrown when the target record of the home application is not found  
 * by a given primary key value.
 * 
 * @author appdev
 *
 */
public class HomeApplicationRecordNotFoundException extends RMT2RuntimeException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public HomeApplicationRecordNotFoundException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public HomeApplicationRecordNotFoundException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }

   
    /**
     * @param e
     */
    public HomeApplicationRecordNotFoundException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public HomeApplicationRecordNotFoundException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

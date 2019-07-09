package com.api.filehandler;



import com.util.RMT2RuntimeException;

/**
 * An exception thrown when the target record of the home application is found to 
 * be already associated with MIME content.
 * 
 * @author appdev
 *
 */
public class HomeApplicationRecordCommittedException extends RMT2RuntimeException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public HomeApplicationRecordCommittedException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public HomeApplicationRecordCommittedException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }

   
    /**
     * @param e
     */
    public HomeApplicationRecordCommittedException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public HomeApplicationRecordCommittedException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

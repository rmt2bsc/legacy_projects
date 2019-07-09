package com.api.filehandler;



import com.util.RMT2RuntimeException;

/**
 * An exception thrown when an error occurs during the File Drop Report creation and/or transmission.
 * 
 * @author appdev
 *
 */
public class FileDropReportException extends RMT2RuntimeException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public FileDropReportException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public FileDropReportException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }

   
    /**
     * @param e
     */
    public FileDropReportException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public FileDropReportException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

package com.audiovideo;



import com.util.RMT2RuntimeException;

/**
 * An exception thrown when an error occurs during the File Drop Report creation and/or transmission.
 * 
 * @author appdev
 *
 */
public class AvBatchReportException extends RMT2RuntimeException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public AvBatchReportException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public AvBatchReportException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }

   
    /**
     * @param e
     */
    public AvBatchReportException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public AvBatchReportException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

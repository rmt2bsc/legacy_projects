package com.api.db;


import com.api.BatchFileException;

/**
 * An exception thrown for general MIME exceptions.
 * 
 * @author appdev
 *
 */
public class MimeException extends BatchFileException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public MimeException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public MimeException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param code
     */
    public MimeException(int code) {
	super(code);
	// TODO Auto-generated constructor stub
    }

  
    /**
     * @param e
     */
    public MimeException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public MimeException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

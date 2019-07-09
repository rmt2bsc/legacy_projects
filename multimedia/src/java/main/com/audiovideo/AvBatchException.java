package com.audiovideo;


import com.api.BatchFileException;

/**
 * 
 * @author appdev
 *
 */
public class AvBatchException extends BatchFileException {
    private static final long serialVersionUID = -4204042569634960159L;

    public AvBatchException() {
	super();
    }

    public AvBatchException(String msg) {
	super(msg);
    }

   
    public AvBatchException(Exception e) {
	super(e);
    }
}

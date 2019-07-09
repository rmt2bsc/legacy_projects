package com.photo;


import com.api.BatchFileException;

/**
 * 
 * @author appdev
 *
 */
public class PhotoBatchLoaderException extends BatchFileException {
    private static final long serialVersionUID = -4204042569634960159L;

    public PhotoBatchLoaderException() {
	super();
    }

    public PhotoBatchLoaderException(String msg) {
	super(msg);
    }

   
    public PhotoBatchLoaderException(Exception e) {
	super(e);
    }
    
    public PhotoBatchLoaderException(String msg, Throwable e) {
	super(msg, e);
    }
}

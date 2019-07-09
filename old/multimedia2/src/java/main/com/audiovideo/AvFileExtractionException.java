package com.audiovideo;

import com.util.RMT2RuntimeException;


/**
 * 
 * @author appdev
 *
 */
public class AvFileExtractionException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public AvFileExtractionException() {
	super();
    }

    public AvFileExtractionException(String msg) {
	super(msg);
    }

    public AvFileExtractionException(String msg, int code) {
	super(msg, code);
    }

    public AvFileExtractionException(Exception e) {
	super(e);
    }
}

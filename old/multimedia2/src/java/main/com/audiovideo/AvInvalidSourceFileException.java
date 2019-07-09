package com.audiovideo;

import com.util.RMT2RuntimeException;


/**
 * 
 * @author appdev
 *
 */
public class AvInvalidSourceFileException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public AvInvalidSourceFileException() {
	super();
    }

    public AvInvalidSourceFileException(String msg) {
	super(msg);
    }

    public AvInvalidSourceFileException(String msg, int code) {
	super(msg, code);
    }

    public AvInvalidSourceFileException(Exception e) {
	super(e);
    }
}

package com.audiovideo;

import com.util.RMT2RuntimeException;


/**
 * 
 * @author appdev
 *
 */
public class AvSourceNotADirectoryException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public AvSourceNotADirectoryException() {
	super();
    }

    public AvSourceNotADirectoryException(String msg) {
	super(msg);
    }

    public AvSourceNotADirectoryException(String msg, int code) {
	super(msg, code);
    }

    public AvSourceNotADirectoryException(Exception e) {
	super(e);
    }
}

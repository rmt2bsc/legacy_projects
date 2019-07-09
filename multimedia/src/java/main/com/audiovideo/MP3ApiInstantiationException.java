package com.audiovideo;

import com.util.RMT2RuntimeException;


/**
 * 
 * @author appdev
 *
 */
public class MP3ApiInstantiationException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public MP3ApiInstantiationException() {
	super();
    }

    public MP3ApiInstantiationException(String msg) {
	super(msg);
    }

    public MP3ApiInstantiationException(String msg, int code) {
	super(msg, code);
    }

    public MP3ApiInstantiationException(Exception e) {
	super(e);
    }
    
    public MP3ApiInstantiationException(Throwable e) {
	super(e);
    }
}

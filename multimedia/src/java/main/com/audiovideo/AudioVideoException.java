package com.audiovideo;


import com.util.RMT2Exception;

public class AudioVideoException extends RMT2Exception {
    private static final long serialVersionUID = -4204042569634960159L;

    public AudioVideoException() {
	super();
    }

    public AudioVideoException(String msg) {
	super(msg);
    }

    public AudioVideoException(int code) {
	super(code);
    }

    public AudioVideoException(String msg, int code) {
	super(msg, code);
    }

    public AudioVideoException(Exception e) {
	super(e);
    }
}

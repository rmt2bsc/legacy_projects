package com.audiovideo;

import com.util.RMT2RuntimeException;



public class AvTrackDataValidationException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public AvTrackDataValidationException() {
	super();
    }

    public AvTrackDataValidationException(String msg) {
	super(msg);
    }

    public AvTrackDataValidationException(String msg, int code) {
	super(msg, code);
    }

    public AvTrackDataValidationException(Exception e) {
	super(e);
    }
}

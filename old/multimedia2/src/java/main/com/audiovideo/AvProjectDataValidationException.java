package com.audiovideo;

import com.util.RMT2RuntimeException;



public class AvProjectDataValidationException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public AvProjectDataValidationException() {
	super();
    }

    public AvProjectDataValidationException(String msg) {
	super(msg);
    }

    public AvProjectDataValidationException(String msg, int code) {
	super(msg, code);
    }

    public AvProjectDataValidationException(Exception e) {
	super(e);
    }
}

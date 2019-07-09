package com.audiovideo;

import com.util.RMT2RuntimeException;



public class AvBatchValidationException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public AvBatchValidationException() {
	super();
    }

    public AvBatchValidationException(String msg) {
	super(msg);
    }

    public AvBatchValidationException(String msg, int code) {
	super(msg, code);
    }

    public AvBatchValidationException(Exception e) {
	super(e);
    }
}

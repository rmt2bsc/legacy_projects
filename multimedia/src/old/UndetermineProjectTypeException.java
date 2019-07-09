package com.audiovideo;

import com.util.RMT2RuntimeException;



public class UndetermineProjectTypeException extends RMT2RuntimeException {
    private static final long serialVersionUID = -4204042569634960159L;

    public UndetermineProjectTypeException() {
	super();
    }

    public UndetermineProjectTypeException(String msg) {
	super(msg);
    }

    public UndetermineProjectTypeException(String msg, int code) {
	super(msg, code);
    }

    public UndetermineProjectTypeException(Exception e) {
	super(e);
    }
}

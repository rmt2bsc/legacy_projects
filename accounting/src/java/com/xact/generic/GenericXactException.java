package com.xact.generic;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * 
 * @author rterrell
 *
 */
public class GenericXactException extends RMT2Exception {
    private static final long serialVersionUID = -1884703323759924257L;

    public GenericXactException() {
	super();
    }

    public GenericXactException(String msg) {
	super(msg);
    }

    public GenericXactException(int code) {
	super(code);
    }

    public GenericXactException(String msg, int code) {
	super(msg, code);
    }

    public GenericXactException(Object _con, int _code, ArrayList _args) {
	super(_con, _code, _args);
    }

    public GenericXactException(String msg, int code, String objname, String methodname) {
	super(msg, code, objname, methodname);
    }

    public GenericXactException(Exception e) {
	super(e);
    }
}

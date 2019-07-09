package com.api.security.user;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * Manages resource exceptions.
 * 
 * @author RTerrell
 *
 */
public class ResourceException extends RMT2Exception {
    private static final long serialVersionUID = 2969536074770899864L;

    public ResourceException() {
        super();
    }

    public ResourceException(String msg) {
        super(msg);
    }

    public ResourceException(int code) {
        super(code);
    }

    public ResourceException(String msg, int code) {
        super(msg, code);
    }

    public ResourceException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }

    public ResourceException(String msg, int code, String objname,
            String methodname) {
        super(msg, code, objname, methodname);
    }

    public ResourceException(Exception e) {
        super(e);
    }
}

package com.api.security.user;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * Manages user maintenance exceptions.
 * 
 * @author RTerrell
 *
 */
public class ApplicationException extends RMT2Exception {
    private static final long serialVersionUID = 2969536074770899864L;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(int code) {
        super(code);
    }

    public ApplicationException(String msg, int code) {
        super(msg, code);
    }

    public ApplicationException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }

    public ApplicationException(String msg, int code, String objname,
            String methodname) {
        super(msg, code, objname, methodname);
    }

    public ApplicationException(Exception e) {
        super(e);
    }
}

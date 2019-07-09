package com.api.security.user;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * Manages user maintenance exceptions.
 * 
 * @author RTerrell
 *
 */
public class UserMaintException extends RMT2Exception {
    private static final long serialVersionUID = 2969536074770899864L;

    public UserMaintException() {
        super();
    }

    public UserMaintException(String msg) {
        super(msg);
    }

    public UserMaintException(int code) {
        super(code);
    }

    public UserMaintException(String msg, int code) {
        super(msg, code);
    }

    public UserMaintException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }

    public UserMaintException(String msg, int code, String objname,
            String methodname) {
        super(msg, code, objname, methodname);
    }

    public UserMaintException(Exception e) {
        super(e);
    }
}

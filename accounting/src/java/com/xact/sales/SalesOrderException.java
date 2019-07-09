package com.xact.sales;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * Exception class for Sales Order errors.
 * 
 * @author appdev
 *
 */
public class SalesOrderException extends RMT2Exception {
    private static final long serialVersionUID = 1980932255849792979L;

    public SalesOrderException() {
	super();
    }

    public SalesOrderException(String msg) {
	super(msg);
    }

    public SalesOrderException(int code) {
	super(code);
    }

    public SalesOrderException(String msg, int code) {
	super(msg, code);
    }

    public SalesOrderException(Object _con, int _code, ArrayList _args) {
	super(_con, _code, _args);
    }

    public SalesOrderException(String msg, int code, String objectname, String methodname) {
	super(msg, code, objectname, methodname);
    }

    public SalesOrderException(Exception e) {
	super(e);
    }
    
    public SalesOrderException(String msg, Throwable e) {
        super(msg, e);
    }
}

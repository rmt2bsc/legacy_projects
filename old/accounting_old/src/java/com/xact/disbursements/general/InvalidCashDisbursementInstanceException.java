package com.xact.disbursements.general;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * Thrown to indicate that a cash disbursement transaction failed.
 * 
 * @author RTerrell
 *
 */
public class InvalidCashDisbursementInstanceException extends RMT2Exception {
    private static final long serialVersionUID = -1550910394823201923L;

    public InvalidCashDisbursementInstanceException() {
	super();
    }

    public InvalidCashDisbursementInstanceException(String msg) {
	super(msg);
    }

    public InvalidCashDisbursementInstanceException(int code) {
	super(code);
    }

    public InvalidCashDisbursementInstanceException(String msg, int code) {
	super(msg, code);
    }

    public InvalidCashDisbursementInstanceException(Object _con, int _code, ArrayList _args) {
	super(_con, _code, _args);
    }

    public InvalidCashDisbursementInstanceException(String msg, int code, String objname, String methodname) {
	super(msg, code, objname, methodname);
    }

    public InvalidCashDisbursementInstanceException(Exception e) {
	super(e);
    }
}

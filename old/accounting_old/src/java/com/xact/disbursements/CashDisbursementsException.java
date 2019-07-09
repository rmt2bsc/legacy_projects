package com.xact.disbursements;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * Thrown to indicate that a cash disbursement transaction failed.
 * 
 * @author RTerrell
 *
 */
public class CashDisbursementsException extends RMT2Exception {
    private static final long serialVersionUID = -1550910394823201923L;

    public CashDisbursementsException() {
	super();
    }

    public CashDisbursementsException(String msg) {
	super(msg);
    }

    public CashDisbursementsException(int code) {
	super(code);
    }

    public CashDisbursementsException(String msg, int code) {
	super(msg, code);
    }

    public CashDisbursementsException(Object _con, int _code, ArrayList _args) {
	super(_con, _code, _args);
    }

    public CashDisbursementsException(String msg, int code, String objname, String methodname) {
	super(msg, code, objname, methodname);
    }

    public CashDisbursementsException(Exception e) {
	super(e);
    }
}

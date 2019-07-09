package com.xact.purchases.creditor;

import com.util.RMT2Exception;

/**
 * Used to in the event an error occurs pertaining to creditor purchases.
 * 
 * @author appdev
 *
 */
public class CreditorPurchasesException extends RMT2Exception {
    private static final long serialVersionUID = -4461456978044416415L;

    /**
     * Constructs a VendorPurchasesException instance where the message defaults 
     * to null and the error code is zero.
     *
     */
    public CreditorPurchasesException() {
	super();
    }

    /**
     * Constructs a CreditorPurchasesException by setting the error message as <i>msg</i>.
     * 
     * @param msg The error message.
     */
    public CreditorPurchasesException(String msg) {
	super(msg);
    }

    /**
     * Constructs a CreditorPurchasesException by setting the error code to <i>code</i>.
     * 
     * @param code the error code.
     */
    public CreditorPurchasesException(int code) {
	super(code);
    }

    /**
     * Constructs a CreditorPurchasesException by assigning the error message and code.
     * 
     * @param msg The error message
     * @param code the error code.
     */
    public CreditorPurchasesException(String msg, int code) {
	super(msg, code);
    }

    /**
     * Constructs a CreditorPurchasesException with message and code derived from another 
     * Exception.
     * 
     * @param e An Exception instance.
     */
    public CreditorPurchasesException(Exception e) {
	super(e);
    }
}

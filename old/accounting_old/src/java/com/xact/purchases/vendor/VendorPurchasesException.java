package com.xact.purchases.vendor;

import com.util.RMT2Exception;

/**
 * Thrown when vendor purchase errors occur.
 * 
 * @author RTerrell
 *
 */
public class VendorPurchasesException extends RMT2Exception {
    private static final long serialVersionUID = 8518919679371045734L;

    /**
     * Constructs a VendorPurchasesException instance where the message defaults 
     * to null and the error code is zero.
     *
     */
    public VendorPurchasesException() {
	super();
    }

    /**
     * Constructs a VendorPurchasesException by setting the error message as <i>msg</i>.
     * 
     * @param msg The error message.
     */
    public VendorPurchasesException(String msg) {
	super(msg);
    }

    /**
     * Constructs a VendorPurchasesException by setting the error code to <i>code</i>.
     * 
     * @param code the error code.
     */
    public VendorPurchasesException(int code) {
	super(code);
    }

    /**
     * Constructs a VendorPurchasesException by assigning the error message and code.
     * 
     * @param msg The error message
     * @param code the error code.
     */
    public VendorPurchasesException(String msg, int code) {
	super(msg, code);
    }

    /**
     * Constructs a VendorPurchasesException with message and code derived from another 
     * Exception.
     * 
     * @param e An Exception instance.
     */
    public VendorPurchasesException(Exception e) {
	super(e);
    }
}

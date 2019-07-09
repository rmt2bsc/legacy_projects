package com.gl.creditor;

import com.util.RMT2Exception;

/**
 * Thrown when an operation pertaining to general ledger creditor fails.
 * 
 * @author RTerrell
 *
 */
public class CreditorException extends RMT2Exception {
    private static final long serialVersionUID = 9044803744305593874L;

    /**
     * Default constructor for creating a CreditorException instance.
     *
     */
    public CreditorException() {
	super();
    }

    /**
     * Creates CreditorException with an error message.
     * 
     * @param msg The error message.
     */
    public CreditorException(String msg) {
	super(msg);
    }

    /**
     * Creates CreditorException with an error code.
     * 
     * @param code The error code.
     */
    public CreditorException(int code) {
	super(code);
    }

    /**
     * Creates CreditorException with an error message and error code.
     * 
     * @param msg The error message.
     * @param code The error code.
     */
    public CreditorException(String msg, int code) {
	super(msg, code);
    }

    /**
     * Creates CreditorException with a generic Exception object.
     * 
     * @param e The Exception object.
     */
    public CreditorException(Exception e) {
	super(e);
    }
}

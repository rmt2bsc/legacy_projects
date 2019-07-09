package com.gl.customer;

import com.util.RMT2Exception;

/**
 * Thrown when an operation pertaining to general ledger customer fails.
 * 
 * @author RTerrell
 *
 */
public class CustomerException extends RMT2Exception {
    private static final long serialVersionUID = 9044803744305593874L;

    /**
     * Default constructor for creating a CustomerException instance.
     *
     */
    public CustomerException() {
	super();
    }

    /**
     * Creates CustomerException with an error message.
     * 
     * @param msg The error message.
     */
    public CustomerException(String msg) {
	super(msg);
    }

    /**
     * Creates CustomerException with an error code.
     * 
     * @param code The error code.
     */
    public CustomerException(int code) {
	super(code);
    }

    /**
     * Creates CustomerException with an error message and error code.
     * 
     * @param msg The error message.
     * @param code The error code.
     */
    public CustomerException(String msg, int code) {
	super(msg, code);
    }

    /**
     * Creates CustomerException with a generic Exception object.
     * 
     * @param e The Exception object.
     */
    public CustomerException(Exception e) {
	super(e);
    }
}

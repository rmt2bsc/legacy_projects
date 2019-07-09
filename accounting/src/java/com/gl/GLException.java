package com.gl;

import com.util.RMT2Exception;

/**
 * Thrown when a general ledger opertaion fails.
 * 
 * @author RTerrell
 *
 */
public class GLException extends RMT2Exception {
    private static final long serialVersionUID = 9044803744305593874L;

    /**
     * Default constructor for creating a GLException instance.
     *
     */
    public GLException() {
	super();
    }

    /**
     * Creates GLException with an error message.
     * 
     * @param msg The error message.
     */
    public GLException(String msg) {
	super(msg);
    }

    /**
     * Creates GLException with an error code.
     * 
     * @param code The error code.
     */
    public GLException(int code) {
	super(code);
    }

    /**
     * Creates GLException with an error message and error code.
     * 
     * @param msg The error message.
     * @param code The error code.
     */
    public GLException(String msg, int code) {
	super(msg, code);
    }

    /**
     * Creates GLException with a generic Exception object.
     * 
     * @param e The Exception object.
     */
    public GLException(Exception e) {
	super(e);
    }
}

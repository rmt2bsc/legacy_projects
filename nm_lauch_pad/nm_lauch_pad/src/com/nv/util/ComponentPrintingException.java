package com.nv.util;

/**
 * An exception class for handling Swing component printing errors.
 * 
 * @author rterrell
 *
 */
public class ComponentPrintingException extends RuntimeException {

    private static final long serialVersionUID = -7667239669740064955L;

    /**
     * 
     */
    public ComponentPrintingException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public ComponentPrintingException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public ComponentPrintingException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public ComponentPrintingException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

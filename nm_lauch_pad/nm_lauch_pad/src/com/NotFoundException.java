package com;

/**
 * Exception class for identifying situations where a resource or data item does
 * not exists.
 * 
 * @author rterrell
 *
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3561623095185613369L;

    /**
     * 
     */
    public NotFoundException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public NotFoundException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public NotFoundException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

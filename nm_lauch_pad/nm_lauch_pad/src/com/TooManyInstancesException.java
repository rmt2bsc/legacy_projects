package com;

/**
 * Exception class for identifying situations when the Singleton pattern is
 * violated.
 * 
 * @author rterrell
 *
 */
public class TooManyInstancesException extends RuntimeException {

    private static final long serialVersionUID = -3561623095185613369L;

    /**
     * 
     */
    public TooManyInstancesException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public TooManyInstancesException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public TooManyInstancesException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public TooManyInstancesException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

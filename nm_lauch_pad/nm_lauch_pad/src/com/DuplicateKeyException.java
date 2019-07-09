package com;

/**
 * Exception class for identifying situations where a resource or data item is
 * in violation of duplicating itself.
 * 
 * @author rterrell
 *
 */
public class DuplicateKeyException extends Exception {

    private static final long serialVersionUID = -3561623095185613369L;

    /**
     * 
     */
    public DuplicateKeyException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public DuplicateKeyException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public DuplicateKeyException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

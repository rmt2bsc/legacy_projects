package com;

/**
 * Exception class for identifying invalid data errors.
 * 
 * @author rterrell
 *
 */
public class InvalidDataException extends RuntimeException {

    private static final long serialVersionUID = -3561623095185613369L;

    /**
     * 
     */
    public InvalidDataException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public InvalidDataException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public InvalidDataException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

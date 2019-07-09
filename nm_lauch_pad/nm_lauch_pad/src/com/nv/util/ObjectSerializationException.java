package com.nv.util;

/**
 * Handles object serializaton errors
 * 
 * @author rterrell
 *
 */
public class ObjectSerializationException extends Exception {
    private static final long serialVersionUID = -1667377962143517874L;

    /**
     * 
     */
    public ObjectSerializationException() {
        super();
    }

    /**
     * @param message
     */
    public ObjectSerializationException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ObjectSerializationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ObjectSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

}

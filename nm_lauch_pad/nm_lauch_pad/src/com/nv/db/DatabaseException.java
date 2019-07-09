package com.nv.db;

/**
 * An Exception class for handling database related errors.
 * 
 * @author rterrell
 *
 */
public class DatabaseException extends Exception {

    private static final long serialVersionUID = 2740138447830935974L;

    /**
     * 
     */
    public DatabaseException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public DatabaseException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public DatabaseException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

package com.nv.security;

/**
 * Exception class for security related errors.
 * 
 * @author rterrell
 *
 */
public class UserSecurityException extends Exception {

    private static final long serialVersionUID = 6250160411343754913L;

    /**
     * 
     */
    public UserSecurityException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public UserSecurityException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public UserSecurityException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public UserSecurityException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

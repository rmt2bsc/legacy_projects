package com.nv.util;

/**
 * An exception class for handling application intialization errors.
 * 
 * @author rterrell
 *
 */
public class AppInitException extends Exception {

    private static final long serialVersionUID = -7667239669740064955L;

    /**
     * 
     */
    public AppInitException() {
        super();
    }

    /**
     * @param message
     */
    public AppInitException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public AppInitException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public AppInitException(String message, Throwable cause) {
        super(message, cause);
    }

}

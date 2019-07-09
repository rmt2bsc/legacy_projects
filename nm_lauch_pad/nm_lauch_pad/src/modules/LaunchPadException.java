/**
 * 
 */
package modules;

/**
 * Exception class for general Launch Pad application errors.
 * 
 * @author rterrell
 *
 */
public class LaunchPadException extends Exception {

    private static final long serialVersionUID = 7481208839439022897L;

    /**
     * 
     */
    public LaunchPadException() {
        super();
    }

    /**
     * @param message
     */
    public LaunchPadException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public LaunchPadException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public LaunchPadException(String message, Throwable cause) {
        super(message, cause);
    }

}

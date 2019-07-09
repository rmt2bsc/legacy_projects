package modules.authentication;

/**
 * Exception class for identifying situations where a resource or data item does
 * not exists.
 * 
 * @author rterrell
 *
 */
public class UserAuthenticationException extends Exception {

    private static final long serialVersionUID = -3561623095185613369L;

    /**
     * 
     */
    public UserAuthenticationException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public UserAuthenticationException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public UserAuthenticationException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public UserAuthenticationException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}

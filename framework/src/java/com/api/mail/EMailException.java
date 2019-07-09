package com.api.mail;

import com.util.RMT2Exception;

/**
 * This exception is thrown by classes using the EMailManagerApi interface.
 * 
 * @author roy.terrell
 * @deprecated Will be replaced by {@link com.api.messaging.email.EmailException} in future versions.
 */
public class EMailException extends RMT2Exception {
    private static final long serialVersionUID = 2469045739436149290L;

    /**
     * Default constructor that creates an EMailException object with a null message.
     *
     */
    public EMailException() {
        super();
    }

    /**
     * Creates an EMailException with a message.
     * 
     * @param msg The text message.
     */
    public EMailException(String msg) {
        super(msg);
    }

    /**
     * Creates an EMailException with a code and no message.
     * 
     * @param code The integer code.
     */
    public EMailException(int code) {
        super(code);
    }

    /**
     * Creates an EMailException with a message and a code.
     * 
     * @param msg The text message.
     * @param code The integer code.
     */
    public EMailException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Creates an EMailException using an Exception.
     * 
     * @param e An Exception object.
     */
    public EMailException(Exception e) {
        super(e);
    }
}

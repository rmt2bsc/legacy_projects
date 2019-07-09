package com.remoteservices;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * An exception that is thrown by descendent classes of AbstractActionHandler.
 * 
 * @author roy.terrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 * 
 */
public class ServiceHandlerException extends RMT2Exception {
    private static final long serialVersionUID = 7943498223213588761L;

    /**
     * Default constructor which its message is null.
     * 
     */
    public ServiceHandlerException() {
        super();
    }

    /**
     * Constructs an ServiceHandlerException object with a message.
     * 
     * @param msg
     *            The exception's message.
     */
    public ServiceHandlerException(String msg) {
        super(msg);
    }

    /**
     * Constructs an ServiceHandlerException object with an integer code and the
     * message is null.
     * 
     * @param code
     *            The exception code
     */
    public ServiceHandlerException(int code) {
        super(code);
    }

    /**
     * Constructs an ServiceHandlerException object with a message and a code.
     * 
     * @param msg
     *            The exception message.
     * @param code
     *            The exception code.
     */
    public ServiceHandlerException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Constructs an ServiceHandlerException using an Exception object.
     * 
     * @param e
     *            Exception
     */
    public ServiceHandlerException(Exception e) {
        super(e);
    }
}

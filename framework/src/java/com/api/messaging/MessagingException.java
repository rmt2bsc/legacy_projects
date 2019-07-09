package com.api.messaging;

import com.util.RMT2Exception;

/**
 * This exception is thrown by messaging based classes
 * 
 * @author RTerrell
 * 
 */
public class MessagingException extends RMT2Exception {

    private static final long serialVersionUID = 2469045739436149290L;

    public MessagingException() {
        super();
    }

    public MessagingException(String msg) {
        super(msg);
    }

    public MessagingException(String msg, int code) {
        super(msg, code);
    }

    public MessagingException(Exception cause) {
        super(cause);
    }

    public MessagingException(String message, Throwable cause) {
        super(message);
    }
}

package com.api.messaging;

import com.util.RMT2Exception;

/**
 * This exception is thrown by messaging handler classes
 * 
 * @author RTerrell
 * 
 */
public class MessagingHandlerException extends RMT2Exception {

    private static final long serialVersionUID = 2469045739436149290L;

    public MessagingHandlerException() {
        super();
    }

    public MessagingHandlerException(String msg) {
        super(msg);
    }

    public MessagingHandlerException(String msg, int code) {
        super(msg, code);
    }

    public MessagingHandlerException(Exception cause) {
        super(cause);
    }

    public MessagingHandlerException(String message, Throwable cause) {
        super(message);
    }
}

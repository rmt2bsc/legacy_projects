package com.api.messaging;

import com.util.RMT2Exception;

/**
 * This exception is thrown when a connection cannot be obtained from 
 * the target message provider. 
 * 
 * @author RTerrell
 * 
 */
public class ProviderConnectionException extends RMT2Exception {
    private static final long serialVersionUID = 2469045739436149290L;

    public ProviderConnectionException() {
        super();
    }

    public ProviderConnectionException(String msg) {
        super(msg);
    }

    public ProviderConnectionException(String msg, int code) {
        super(msg, code);
    }

    public ProviderConnectionException(Exception cause) {
        super(cause);
    }

    public ProviderConnectionException(String message, Throwable cause) {
        super(message);
    }
}

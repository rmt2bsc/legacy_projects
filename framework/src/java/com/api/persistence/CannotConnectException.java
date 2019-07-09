package com.api.persistence;

import com.util.RMT2Exception;

/**
 * This exception inherits the Exception class and is used in
 * cases where the Persistence Storage cannot be connected to
 * for any purpose.
 * 
 * @author Roy Terrell
 */

public class CannotConnectException extends RMT2Exception {

    private static final long serialVersionUID = -4562728054413124733L;

    public CannotConnectException(String msg) {
        super(msg);
    }
    
    public CannotConnectException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

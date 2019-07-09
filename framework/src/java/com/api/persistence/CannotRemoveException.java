package com.api.persistence;

import com.util.RMT2Exception;

/**
 * This exception inherits the Exception class and is used in
 * cases where the Persistence Storage cannot remove a pair
 * from its storage mechanism (leading to permannt errors)
 * 
 * @author Roy Terrell
 */

public class CannotRemoveException extends RMT2Exception {

    private static final long serialVersionUID = 1896713880522208538L;

    public CannotRemoveException(String msg) {
        super(msg);
    }
    
    public CannotRemoveException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

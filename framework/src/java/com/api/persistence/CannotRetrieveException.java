package com.api.persistence;

import com.util.RMT2Exception;

/**
 * This exception inherits the Exception class and is used in
 * cases where the Persistence Storage cannot retrieve a pair
 * from its storage mechanism (leading to failure of mechanism)
 * 
 * @author Roy Terrell
 */

public class CannotRetrieveException extends RMT2Exception {

    private static final long serialVersionUID = -5989244896539149738L;

    public CannotRetrieveException(String msg) {
        super(msg);
    }
    
    public CannotRetrieveException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

package com.api.persistence;

import com.util.RMT2BeanUtility;
import com.util.RMT2Exception;

/**
 * This exception inherits the Exception class and is used in
 * cases where the Persistence Storage cannot persist a pair
 * from its storage mechanism (leading to fatal errors)
 * 
 * @author Roy Terrell
 */

public class CannotPersistException extends RMT2Exception {

    private static final long serialVersionUID = -8908049552892867317L;

    public CannotPersistException(String msg) {
        super(msg);
    }
    
    public CannotPersistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

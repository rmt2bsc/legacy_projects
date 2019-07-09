package com.api.persistence;

import com.util.RMT2Exception;

/**
 * This exception inherits the Exception class and is used in
 * cases where the Persistence Manager cannot be created.
 * 
 * @author Roy Terrell
 */

public class CannotCreateSchemaException extends RMT2Exception {

    private static final long serialVersionUID = 5849320232601197106L;
    
    public CannotCreateSchemaException(String msg) {
        super(msg);
    }
    
    public CannotCreateSchemaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

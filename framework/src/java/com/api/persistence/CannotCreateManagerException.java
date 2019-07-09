package com.api.persistence;

import com.util.RMT2Exception;

/**
 * This exception inherits the Exception class and is used in
 * cases where the Persistence Storage cannot create schema to
 * use the API as required. At this point, top level use needs to
 * decide whether to continue or abort.
 * 
 * @author Roy Terrell
 */

public class CannotCreateManagerException extends RMT2Exception {

    private static final long serialVersionUID = 5849320232601197106L;
    
    public CannotCreateManagerException(String msg) {
        super(msg);
    }
    
    public CannotCreateManagerException(Exception e) {
        super(e);
    }
    
    public CannotCreateManagerException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

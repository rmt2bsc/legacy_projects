package com.services;

import com.api.ContactException;


/**
 * An exception that provides data and information pertaining 
 * to postal search selection criteria information.
 * 
 * @author RTerrell
 *
 */
public class CommonContactlSearchCriteriaException extends ContactException {
    private static final long serialVersionUID = 3776403355177086870L;

    /**
     * Constructs a PostalSearchCriteriaException object with a null message.
     *
     */
    public CommonContactlSearchCriteriaException() {
        super();
    }

    /**
     * Creates a PostalSearchCriteriaException object by assigning the error message.
     * 
     * @param msg The error message.
     */
    public CommonContactlSearchCriteriaException(String msg) {
        super(msg);
    }

   
       /**
     * Creates a PostalSearchCriteriaException object by using data contained in 
     * an existing exception object.
     * 
     * @param e The exception object.
     */
    public CommonContactlSearchCriteriaException(Exception e) {
        super(e);
    }
    
    public CommonContactlSearchCriteriaException(String msg, Exception e) {
        super(msg, e);
    }
}

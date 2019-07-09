package com.services;

import com.api.ContactException;


/**
 * An exception that provides data and information pertaining 
 * to postal search selection criteria information.
 * 
 * @author RTerrell
 *
 */
public class PostalSearchCriteriaException extends ContactException {
    private static final long serialVersionUID = 3776403355177086870L;

    /**
     * Constructs a PostalSearchCriteriaException object with a null message.
     *
     */
    public PostalSearchCriteriaException() {
        super();
    }

    /**
     * Creates a PostalSearchCriteriaException object by assigning the error message.
     * 
     * @param msg The error message.
     */
    public PostalSearchCriteriaException(String msg) {
        super(msg);
    }

   
       /**
     * Creates a PostalSearchCriteriaException object by using data contained in 
     * an existing exception object.
     * 
     * @param e The exception object.
     */
    public PostalSearchCriteriaException(Exception e) {
        super(e);
    }
    
    public PostalSearchCriteriaException(String msg, Exception e) {
        super(msg, e);
    }
}

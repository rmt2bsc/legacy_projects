package com.project.invoice;

import com.util.RMT2Exception;

/**
 * Exception class for catching errors originating from the invoicing of a business entity.
 * 
 * @author appdev
 *
 */
public class InvoicingException extends RMT2Exception {
    private static final long serialVersionUID = -8851874846044566159L;

    public InvoicingException() {
	super();
    }

    public InvoicingException(String msg) {
	super(msg);
    }

    public InvoicingException(Exception e) {
	super(e);
    }
}

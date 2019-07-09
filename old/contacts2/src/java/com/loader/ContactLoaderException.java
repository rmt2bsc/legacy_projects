package com.loader;

import com.util.RMT2Exception;

/**
 * Handles exceptions generated from the transaction loader module.
 * 
 * @author appdev
 *
 */
public class ContactLoaderException extends RMT2Exception {
    private static final long serialVersionUID = -8274128092353299279L;

    /**
     * 
     */
    public ContactLoaderException() {
    }

    /**
     * @param msg
     */
    public ContactLoaderException(String msg) {
	super(msg);
    }

    /**
     * @param e
     */
    public ContactLoaderException(Exception e) {
	super(e);
    }

    /**
     * @param _msg
     * @param _code
     */
    public ContactLoaderException(String _msg, int _code) {
	super(_msg, _code);
    }

}

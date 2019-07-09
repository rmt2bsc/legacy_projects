package com.loader;

import com.util.RMT2Exception;

/**
 * Handles exceptions generated from the transaction loader module.
 * 
 * @author appdev
 *
 */
public class TransactionLoaderException extends RMT2Exception {
    private static final long serialVersionUID = -8274128092353299279L;

    /**
     * 
     */
    public TransactionLoaderException() {
    }

    /**
     * @param msg
     */
    public TransactionLoaderException(String msg) {
	super(msg);
    }

    /**
     * @param e
     */
    public TransactionLoaderException(Exception e) {
	super(e);
    }

    /**
     * @param _msg
     * @param _code
     */
    public TransactionLoaderException(String _msg, int _code) {
	super(_msg, _code);
    }

}

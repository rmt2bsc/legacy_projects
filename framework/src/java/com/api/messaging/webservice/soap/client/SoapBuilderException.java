package com.api.messaging.webservice.soap.client;

import com.api.messaging.MessageException;

/**
 * @author appdev
 *
 */
public class SoapBuilderException extends MessageException {

    private static final long serialVersionUID = 9181149461315422237L;

    /**
     * 
     */
    public SoapBuilderException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public SoapBuilderException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param _msg
     * @param _code
     */
    public SoapBuilderException(String _msg, int _code) {
        super(_msg, _code);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public SoapBuilderException(Exception e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public SoapBuilderException(String msg, Throwable cause) {
        super(msg, cause);
        // TODO Auto-generated constructor stub
    }

}

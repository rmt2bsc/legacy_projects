package com.api.messaging.webservice.soap.engine;

import com.api.messaging.MessageException;

/**
 * @author appdev
 *
 */
public class SoapMessageRouterException extends MessageException {

    private static final long serialVersionUID = 9181149461315422237L;

    /**
     * 
     */
    public SoapMessageRouterException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public SoapMessageRouterException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param _msg
     * @param _code
     */
    public SoapMessageRouterException(String _msg, int _code) {
        super(_msg, _code);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public SoapMessageRouterException(Exception e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public SoapMessageRouterException(String msg, Throwable cause) {
        super(msg, cause);
        // TODO Auto-generated constructor stub
    }

}

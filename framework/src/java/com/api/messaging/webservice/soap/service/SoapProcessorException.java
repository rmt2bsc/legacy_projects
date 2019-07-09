package com.api.messaging.webservice.soap.service;

import com.api.messaging.MessageException;

/**
 * @author appdev
 * @deprecated Use SoapServiceException
 *
 */
public class SoapProcessorException extends MessageException {

    private static final long serialVersionUID = 9181149461315422237L;

    /**
     * 
     */
    public SoapProcessorException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public SoapProcessorException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param _msg
     * @param _code
     */
    public SoapProcessorException(String _msg, int _code) {
        super(_msg, _code);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public SoapProcessorException(Exception e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public SoapProcessorException(String msg, Throwable cause) {
        super(msg, cause);
        // TODO Auto-generated constructor stub
    }

}

package com.api.messaging.webservice.soap;

import com.api.messaging.MessageException;

/**
 * @author appdev
 *
 */
public class SoapRequestException extends MessageException {

    private static final long serialVersionUID = 9181149461315422237L;

    /**
     * 
     */
    public SoapRequestException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public SoapRequestException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param _msg
     * @param _code
     */
    public SoapRequestException(String _msg, int _code) {
        super(_msg, _code);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public SoapRequestException(Exception e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public SoapRequestException(String msg, Throwable cause) {
        super(msg, cause);
        // TODO Auto-generated constructor stub
    }

}

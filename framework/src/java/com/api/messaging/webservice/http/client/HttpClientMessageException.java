package com.api.messaging.webservice.http.client;

import com.api.messaging.MessageException;

/**
 * @author appdev
 *
 */
public class HttpClientMessageException extends MessageException {

    private static final long serialVersionUID = 9181149461315422237L;

    /**
    * @param msg
    */
    public HttpClientMessageException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public HttpClientMessageException(Exception e) {
        super(e);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public HttpClientMessageException(String msg, Throwable cause) {
        super(msg, cause);
        // TODO Auto-generated constructor stub
    }

}

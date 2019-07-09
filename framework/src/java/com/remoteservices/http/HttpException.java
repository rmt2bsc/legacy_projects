package com.remoteservices.http;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * 
 * @author appdev
 * @deprecated Will be removed in future releases.   Use {@link com.api.messaging.webservice.http.HttpException HttpException}.
 *
 */
public class HttpException extends RMT2Exception {
    private static final long serialVersionUID = -6716347267991012444L;

    /**
     * Default constructor that creates an HttpException object with a null message.
     *
     */
    public HttpException() {
        super();
    }

    /**
     * Creates an HttpException with a message.
     * 
     * @param msg The text message.
     */
    public HttpException(String msg) {
        super(msg);
    }

    /**
     * Creates an HttpException with a code and no message.
     * 
     * @param code The integer code.
     */
    public HttpException(int code) {
        super(code);
    }

    /**
     * Creates an HttpException with a message and a code.
     * 
     * @param msg The text message.
     * @param code The integer code.
     */
    public HttpException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Creates an HttpException using an Exception.
     * 
     * @param e An Exception object.
     */
    public HttpException(Exception e) {
        super(e);
    }

    /**
     * Constructs an HttpException object using a connection object, message
     * id, and an ArrayList of messages. A template message is retrieved from an
     * external data source which is identified by its message id, _code. The
     * list of messages are used to populate any place holders in the template
     * message in the order which they exist in the collection.
     * 
     * @param _con
     *            Connection to an external data source.
     * @param _code
     *            The id of the message.
     * @param _args
     *            One or more messages used to populate the place holders in the
     *            template message.
     */
    public HttpException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }
}

package com.api.security.authentication;

import java.util.ArrayList;

import com.util.RMT2Exception;

public class LogoutException extends RMT2Exception {
    private static final long serialVersionUID = -2320582233990240863L;

    /**
     * Default constructor that creates an UserAuthenticationException object
     * with a null message.
     * 
     */
    public LogoutException() {
        super();
    }

    /**
     * Creates an UserAuthenticationException with a message.
     * 
     * @param msg
     *            The text message.
     */
    public LogoutException(String msg) {
        super(msg);
    }

    /**
     * Creates an UserAuthenticationException with a code and no message.
     * 
     * @param code
     *            The integer code.
     */
    public LogoutException(int code) {
        super(code);
    }

    /**
     * Creates an UserAuthenticationException with a message and a code.
     * 
     * @param msg
     *            The text message.
     * @param code
     *            The integer code.
     */
    public LogoutException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Constructs an UserAuthenticationException object using a connection object, message
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
    public LogoutException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }

    /**
     * Creates an UserAuthenticationException using an Exception.
     * 
     * @param e
     *            An Exception object.
     */
    public LogoutException(Exception e) {
        super(e);
    }

    /**
     * Creates an UserAuthenticationException using an custom message and the cause instance.
     * @param msg
     * @param e
     */
    public LogoutException(String msg, Throwable e) {
        super(msg, e);
    }
}

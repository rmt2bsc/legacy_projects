package com.api.security.authentication;

import java.util.ArrayList;

import com.util.RMT2Exception;

public class LoginException extends RMT2Exception {
    private static final long serialVersionUID = 3146419736970380825L;

    /**
     * Default constructor that creates an LoginException object with a null message.
     *
     */
    public LoginException() {
        super();
    }

    /**
     * Creates an LoginException with a message.
     * 
     * @param msg The text message.
     */
    public LoginException(String msg) {
        super(msg);
    }

    /**
     * Creates an LoginException with a code and no message.
     * 
     * @param code The integer code.
     */
    public LoginException(int code) {
        super(code);
    }

    /**
     * Creates an LoginException with a message and a code.
     * 
     * @param msg The text message.
     * @param code The integer code.
     */
    public LoginException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Creates an LoginException using an Exception.
     * 
     * @param e An Exception object.
     */
    public LoginException(Exception e) {
        super(e);
    }

    /**
     * 
     * @param msg
     * @param e
     */
    public LoginException(String msg, Exception e) {
        super(msg, e);
    }

    /**
     * Constructs an LoginException object using a connection object, message
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
    public LoginException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }
}

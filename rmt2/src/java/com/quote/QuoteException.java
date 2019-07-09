package com.quote;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * Handles errors pertaining to the Quote api.
 * 
 * @author appdev
 *
 */
public class QuoteException extends RMT2Exception {

    private static final long serialVersionUID = -7486939614242484868L;

    /**
     * Default constructor that creates an QuoteException object
     * with a null message.
     */
    public QuoteException() {
	super();
    }

    /**
     * Creates an QuoteException with a message.
     * 
     * @param msg
     *            The text message.
     */
    public QuoteException(String msg) {
	super(msg);
    }

    /**
     * Creates an QuoteException with a code and no message.
     * 
     * @param code
     *            The integer code.
     */
    public QuoteException(int code) {
	super(code);
    }

    /**
     * Creates an QuoteException with a message and a code.
     * 
     * @param msg
     *            The text message.
     * @param code
     *            The integer code.
     */
    public QuoteException(String msg, int code) {
	super(msg, code);
    }

    /**
     * Constructs an QuoteException object using a connection object, message
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
    public QuoteException(Object _con, int _code, ArrayList _args) {
	super(_con, _code, _args);
    }

    /**
     * Creates an QuoteException using an Exception.
     * 
     * @param e
     *            An Exception object.
     */
    public QuoteException(Exception e) {
	super(e);
    }

}

package com.members;

import java.util.ArrayList;

import com.util.RMT2Exception;

/**
 * @author appdev
 *
 */
public class MemberException extends RMT2Exception {

    private static final long serialVersionUID = -7486939614242484868L;

    /**
     * Default constructor that creates an MemberException object
     * with a null message.
     */
    public MemberException() {
	super();
    }

    /**
	 * Creates an MemberException with a message.
	 * 
	 * @param msg
	 *            The text message.
	 */
	public MemberException(String msg) {
		super(msg);
	}

	/**
	 * Creates an MemberException with a code and no message.
	 * 
	 * @param code
	 *            The integer code.
	 */
	public MemberException(int code) {
		super(code);
	}

	/**
	 * Creates an MemberException with a message and a code.
	 * 
	 * @param msg
	 *            The text message.
	 * @param code
	 *            The integer code.
	 */
	public MemberException(String msg, int code) {
		super(msg, code);
	}

	/**
	 * Constructs an MemberException object using a connection object, message
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
	public MemberException(Object _con, int _code, ArrayList _args) {
		super(_con, _code, _args);
	}
	
	/**
	 * Creates an MemberException using an Exception.
	 * 
	 * @param e
	 *            An Exception object.
	 */
	public MemberException(Exception e) {
		super(e);
	}

}

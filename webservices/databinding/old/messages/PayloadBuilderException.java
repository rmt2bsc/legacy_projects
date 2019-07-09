package com.xml.schema.misc;


import com.api.messaging.MessageException;

/**
 * @author appdev
 *
 */
public class PayloadBuilderException extends MessageException {

    private static final long serialVersionUID = 9181149461315422237L;

    /**
     * 
     */
    public PayloadBuilderException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public PayloadBuilderException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }


    /**
     * @param _msg
     * @param _code
     */
    public PayloadBuilderException(String _msg, int _code) {
	super(_msg, _code);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public PayloadBuilderException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     * @param cause
     */
    public PayloadBuilderException(String msg, Throwable cause) {
	super(msg, cause);
	// TODO Auto-generated constructor stub
    }

}

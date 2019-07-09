package com.xml.schema.messages;


import com.api.ProductBuilderException;
import com.api.messaging.MessageException;

/**
 * @author appdev
 *
 */
public class WSMessageBuilderException extends ProductBuilderException {

    private static final long serialVersionUID = 9181149461315422237L;

    /**
     * 
     */
    public WSMessageBuilderException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public WSMessageBuilderException(String msg) {
	super(msg);
	// TODO Auto-generated constructor stub
    }


    /**
     * @param _msg
     * @param _code
     */
    public WSMessageBuilderException(String _msg, int _code) {
	super(_msg, _code);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param e
     */
    public WSMessageBuilderException(Exception e) {
	super(e);
	// TODO Auto-generated constructor stub
    }

   

}

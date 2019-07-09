package com.api.postal;

import com.api.ContactException;

import java.util.ArrayList;

/**
 * An exception that provides data and information pertaining 
 * to zip code operations.
 * 
 * @author RTerrell
 *
 */
public class ZipcodeException extends ContactException {
    private static final long serialVersionUID = 3776403355177086870L;

    /**
     * Constructs a ZipcodeException object with a null message.
     *
     */
    public ZipcodeException() {
        super();
    }

    /**
     * Creates a ZipcodeException object by assigning the error message.
     * 
     * @param msg The error message.
     */
    public ZipcodeException(String msg) {
        super(msg);
    }

    /**
     * Creates a ZipcodeException object with a null message and 
     * an assigned error code.
     * 
     * @param code The error code.
     */
    public ZipcodeException(int code) {
        super(code);
    }

    /**
     * Creates a ZipcodeException object with a error message and error code.
     * 
     * @param msg The error text.
     * @param code The error code.
     */
    public ZipcodeException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Creates a ZipcodeException object using an arbitrary object as a 
     * connection to an external data source, an error code, and a list 
     * of error message place holder fillers to retreive the error text 
     * from the data source.   The list of error messages are used to 
     * fill in any place holders with values.
     * 
     * @param _con  A connection to some external data source.
     * @param _code The error code
     * @param _args 
     *          A list of String values used to fill in any place holders.
     */
    public ZipcodeException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }

    /**
     * Creates a ZipcodeException object by reporting the error text, error 
     * code, originating object and method name pertaining to the error.
     * 
     * @param msg Error text
     * @param code Error code
     * @param objectname Class name where error originated.
     * @param methodname The name of the method which error occurred.
     */
    public ZipcodeException(String msg, int code, String objectname, String methodname) {
        super(msg, code, objectname, methodname);
    }

    /**
     * Creates a ZipcodeException object by using data contained in 
     * an existing exception object.
     * 
     * @param e The exception object.
     */
    public ZipcodeException(Exception e) {
        super(e);
    }
    
    /**
     * 
     * @param msg
     * @param e
     */
    public ZipcodeException(String msg, Throwable e) {
        super(msg, e);
    }
}

package com.api.address;

import com.api.ContactException;

import java.util.ArrayList;

/**
 * An exception that provides data and information pertaining 
 * to personal and business contact mailing address operations.
 * 
 * @author RTerrell
 *
 */
public class AddressException extends ContactException {
    private static final long serialVersionUID = 3776403355177086870L;

    /**
     * Constructs a AddressException object with a null message.
     *
     */
    public AddressException() {
        super();
    }

    /**
     * Creates a AddressException object by assigning the error message.
     * 
     * @param msg The error message.
     */
    public AddressException(String msg) {
        super(msg);
    }

    /**
     * Creates a AddressException object with a null message and 
     * an assigned error code.
     * 
     * @param code The error code.
     */
    public AddressException(int code) {
        super(code);
    }

    /**
     * Creates a AddressException object with a error message and error code.
     * 
     * @param msg The error text.
     * @param code The error code.
     */
    public AddressException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Creates a AddressException object using an arbitrary object as a 
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
    public AddressException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }

    /**
     * Creates a AddressException object by reporting the error text, error 
     * code, originating object and method name pertaining to the error.
     * 
     * @param msg Error text
     * @param code Error code
     * @param objectname Class name where error originated.
     * @param methodname The name of the method which error occurred.
     */
    public AddressException(String msg, int code, String objectname, String methodname) {
        super(msg, code, objectname, methodname);
    }

    /**
     * Creates a AddressException object by using data contained in 
     * an existing exception object.
     * 
     * @param e The exception object.
     */
    public AddressException(Exception e) {
        super(e);
    }
}
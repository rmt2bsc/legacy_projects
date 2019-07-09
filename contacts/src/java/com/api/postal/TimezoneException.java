package com.api.postal;

import com.api.ContactException;

import java.util.ArrayList;

/**
 * An exception that provides data and information pertaining 
 * to time zone operations.
 * 
 * @author RTerrell
 *
 */
public class TimezoneException extends ContactException {
    private static final long serialVersionUID = 3776403355177086870L;

    /**
     * Constructs a TimezoneException object with a null message.
     *
     */
    public TimezoneException() {
        super();
    }

    /**
     * Creates a TimezoneException object by assigning the error message.
     * 
     * @param msg The error message.
     */
    public TimezoneException(String msg) {
        super(msg);
    }

    /**
     * Creates a TimezoneException object with a null message and 
     * an assigned error code.
     * 
     * @param code The error code.
     */
    public TimezoneException(int code) {
        super(code);
    }

    /**
     * Creates a TimezoneException object with a error message and error code.
     * 
     * @param msg The error text.
     * @param code The error code.
     */
    public TimezoneException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Creates a TimezoneException object using an arbitrary object as a 
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
    public TimezoneException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
    }

    /**
     * Creates a TimezoneException object by reporting the error text, error 
     * code, originating object and method name pertaining to the error.
     * 
     * @param msg Error text
     * @param code Error code
     * @param objectname Class name where error originated.
     * @param methodname The name of the method which error occurred.
     */
    public TimezoneException(String msg, int code, String objectname, String methodname) {
        super(msg, code, objectname, methodname);
    }

    /**
     * Creates a TimezoneException object by using data contained in 
     * an existing exception object.
     * 
     * @param e The exception object.
     */
    public TimezoneException(Exception e) {
        super(e);
    }
}

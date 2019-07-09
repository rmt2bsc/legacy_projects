package com.api.ip;

import com.api.ContactException;


/**
 * An exception that provides data and information pertaining 
 * to personal and business contact mailing address operations.
 * 
 * @author RTerrell
 *
 */
public class IpException extends ContactException {
    private static final long serialVersionUID = 3776403355177086870L;

    /**
     * Constructs a IpException object with a null message.
     *
     */
    public IpException() {
        super();
    }

    /**
     * Creates a IpException object by assigning the error message.
     * 
     * @param msg The error message.
     */
    public IpException(String msg) {
        super(msg);
    }

    /**
     * Creates a IpException object with a null message and 
     * an assigned error code.
     * 
     * @param code The error code.
     */
    public IpException(int code) {
        super(code);
    }

    /**
     * Creates a IpException object with a error message and error code.
     * 
     * @param msg The error text.
     * @param code The error code.
     */
    public IpException(String msg, int code) {
        super(msg, code);
    }

  

   /**
    * Creates a IpException object by reporting the error text and causing throwable.
     * 
    * @param msg
    * @param e
    */
    public IpException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * Creates a IpException object by using data contained in 
     * an existing exception object.
     * 
     * @param e The exception object.
     */
    public IpException(Exception e) {
        super(e);
    }
}

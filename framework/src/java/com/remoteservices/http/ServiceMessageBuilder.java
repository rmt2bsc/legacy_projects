package com.remoteservices.http;

/**
 * Interface provides the methods needed to implement a remote service message builder.
 * 
 * @author RTerrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 *
 */
public interface ServiceMessageBuilder {
    /**
     * Builds the message suitable for transporting between disparate systems.
     * 
     * @return An arbitrary object representing the message
     * @throws HttpException
     */
    Object build(Object message) throws HttpException;

    /**
     * Converts the message to String form.
     *  
     * @return String
     */
    String toString();

    /**
     * Validate the input source message.
     * 
     * @throws HttpException
     */
    void validate() throws HttpException;
}

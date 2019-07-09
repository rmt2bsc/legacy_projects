package com.api.messaging.webservice.http.client;

import com.api.DaoApi;

import com.api.messaging.MessageManager;

/**
 * Interface provides the contract needed for a client to consume web services using HTTP URL Protocol.
 *  
 * @author appdev
  *
 */
public interface HttpMessageSender extends MessageManager {
    /**
     * Attribute that identifies the data results of a service call.
     */
    static final String SERVICE_RESULTS = "serviceresults";

    /**
     * Build the URL parameter list from the key/value pairs that exist in the 
     * user's request object.  Basically, the key/value pairs of the Request 
     * object are copied to a Properties collection. The key/value pairs can 
     * exist within the request's parameter list and/or its attribute list.  
     * The only key value pair that is required is <i>clientAction</i> which 
     * will serve as the service id.
     * <p>
     * This method depends on the Request object being available to obtain the 
     * data parameters needed to satisfy the service call.  In the event that
     * the Request object is invalid or unavailable, an exception should be thrown.
     * 
     * @throws HttpClientMessageException
     *          Should throw an exception when the user's Request instance 
     *          is invalid or when the service id is not found on the Request.
     * @deprecated Will be removed in future releases.    The functionality this method provides should be privately implemented.          
     */
    void receiveRequest() throws HttpClientMessageException;

    /**
     * Drives the process of handling the client's request by consuming a remote
     * service identified by its command. This method requires that
     * the descendent implements the receiveRequest, packageResults, and sendReply
     * methods.
     * 
     * @throws HttpClientMessageException
     * @deprecated Will be removed in future versions.    Use {@link HttpMessageSender#sendMessage(java.io.Serializable) sendMessage(Serializable) } as the entry point
     */
    void processRequest() throws HttpClientMessageException;

    
    /**
     * THe purpose of this method is to process and manage the results that were 
     * returned from the service invocation, if applicable.
     * 
     * @param data Arbitrary data returned from the service call.
     * @throws ServiceHandlerException
     */
    void packageResults(Object data) throws HttpClientMessageException;

    /**
     * Returns the XML results of the service call as a DaoApi instance.
     * 
     * @return {@link com.api.DaoApi DaoApi}
     */
    DaoApi getXmlResults();

    /**
     * Indicates whether an error occurred.
     * 
     * @return Returns true for service call failure and false for success.
     */
    boolean isError();

    /**
     * Releases any allocated resources.
     *
     */
    void close();
}

package com.remoteservices;

import com.action.ActionHandlerException;

/**
 * Interface provides the methods needed to create a producer of services.
 *  
 * @author appdev
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 *
 */
public interface ServiceProducer {
    static final String SERVICE_RESULTS = "serviceresults";

    /**
     * The purpose of this method is to retrieve data from some arbitrary input 
     * source and package the data into a form that is useable as input data for 
     * the service implementation.
     * 
     * @throws ActionHandlerException
     */
    void receiveClientData() throws ActionHandlerException;

    /**
     * The purpose of this method is to send the results to the caller.
     * 
     * @throws ActionHandlerException
     */
    public void sendClientData() throws ActionHandlerException;

    /**
     * The purpose of this method is to use the input data, if applicable, to process 
     * the request of a given service.
     *  
     * @throws ActionHandlerException
     */
    void processData() throws ActionHandlerException;
}

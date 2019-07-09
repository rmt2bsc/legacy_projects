package com.remoteservices;

import com.action.ActionHandlerException;
import com.api.DaoApi;

/**
 * Interface provides the methods needed for a client to consume remote service calls.
 *  
 * @author appdev
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 *
 */
public interface ServiceConsumer {
    /**
     * Attribute that identifies the data results of a service call.
     */
    static final String SERVICE_RESULTS = "serviceresults";

    /**
     * Attribute that identifies the service id erquested by the user. 
     */
    static final String SERVICE_ID = "serviceId";

    /**
     * Remote Authentication service name
     */
    static final String REMOTE_AUTH_SERVICE = "authremoteuser";

    /**
     * Application User Login service name
     */
    static final String CHECK_APP_LOGIN_SERVICE = "checkapp";

    /**
     * User Resource Verification service name
     */
    static final String CHECK_SERVICE = "checkservice";

    /**
     * User Resource Access Verification service name
     */
    static final String CHECK_SERVICE_ACCESS = "checkserviceaccess";

    /** RPC Service Type */
    static final int SRVCTYPE_RPC = 1;

    /** CORBA Service Type */
    static final int SRVCTYPE_CORBA = 2;

    /** COM Service Type */
    static final int SRVCTYPE_COM = 3;

    /** DCOM Service Type */
    static final int SRVCTYPE_DCOM = 4;

    /** RMI Service Type */
    static final int SRVCTYPE_RMI = 5;

    /** HTTPURL Service Type */
    static final int SRVCTYPE_HTTPURL = 6;

    /** SOA Service Type */
    static final int SRVCTYPE_SOA = 7;

    /** SOCKET Service Type */
    static final int SRVCTYPE_SOCKET = 8;

    /**
     * The purpose of this method is to retrieve data from some arbitrary input 
     * source and package the data into a form that is useable by the target 
     * service.
     * 
     * @throws ActionHandlerException
     */
    void receiveClientData() throws ActionHandlerException;

    /**
     * The purpose of this method is to send the results of the service invocation 
     * to the caller.
     * 
     * @throws ActionHandlerException
     */
    public void sendClientData() throws ActionHandlerException;

    /**
     * The purpose of the method is to call the intended service.
     * 
     * @param url the URL of the remote service.
     * @return The results of the service call as an arbitrary object.
     * @throws ServiceHandlerException
     */
    Object invokeService(String url) throws ServiceHandlerException;

    /**
     * THe purpose of this method is to process and manage the results that were 
     * returned from the service invocation, if applicable.
     * 
     * @param data Arbitrary data returned from the service call.
     * @throws ServiceHandlerException
     */
    void processResults(Object data) throws ServiceHandlerException;

    /**
     * Returns the XML results of the service call as a DaoApi instance.
     * 
     * @return {@link com.api.DaoApi DaoApi}
     */
    //DaoApi getXmlResults();
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

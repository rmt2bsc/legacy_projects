package com.api.messaging.webservice.http.client;

import com.api.messaging.webservice.http.HttpResourceFactory;
import com.api.messaging.webservice.http.client.HttpMessageSender;


/**
 * Assists clients as a factory for creating various HTTP Client resources necessary for
 * creating and managing HTTP URL messaging related connections.
 * 
 * @author RTerrell
 * 
 */
public class HttpClientResourceFactory extends HttpResourceFactory {

 
    /**
     * Creates an instance of HttpMessageSender using SimpleHttpClientMessageImpl implementation
     * 
     * @param inParameters
     *                an arbitrary object implementing the Serializable interface.
     * @return
     */
    public static final HttpMessageSender getHttpInstance() {
	HttpMessageSender obj = new SimpleHttpClientMessageImpl();
        return obj;
    }
}

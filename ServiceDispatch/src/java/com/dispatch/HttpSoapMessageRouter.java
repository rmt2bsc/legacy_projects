package com.dispatch;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.api.config.HttpSystemPropertyConfig;

import com.api.messaging.webservice.ServiceRoutingInfo;
import com.api.messaging.webservice.http.HttpClient;
import com.api.messaging.webservice.http.HttpException;

import com.api.messaging.webservice.soap.engine.SoapMessageRouter;
import com.api.messaging.webservice.soap.engine.SoapMessageRouterException;

import com.util.SystemException;

/**
 * Implementation of SoapMessageRouter that determines how a message is supposed to be 
 * routed via HTTP protocol.
 * 
 * @author Roy Terrell
 *
 */
public class HttpSoapMessageRouter implements SoapMessageRouter {
    
    private static final Logger logger = Logger.getLogger(HttpSoapMessageRouter.class);

    /**
     * Default constructor.
     */
    public HttpSoapMessageRouter() {
        return;
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.engine.SoapMessageRouter#routeMessage(com.api.messaging.webservice.ServiceRoutingInfo, javax.xml.soap.SOAPMessage, javax.servlet.http.HttpServletRequest)
     */
    public SOAPMessage routeMessage(ServiceRoutingInfo srvc, SOAPMessage soapObj, HttpServletRequest request) throws SoapMessageRouterException {
	String msg = null;
	
        // Get host server where the intended web service implementation can be found.
        String server = srvc.getHost();
        if (server == null) {
            // The web service's implementation was not specified in resource configuration...
            // assume implementation lives on the same server as the SOAP router.
            server = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVER);
        }
        // Ensure that the protocol is prefixed to host name
        if (server.indexOf("http://") < 0) {
            server = "http://" + server;
        }

        // Tack on the client action to the URL since we are posting a 
        // SOAP message to the request and not Properties collection.
        String url = server + "/" + srvc.getUrl() + "?clientAction=" + srvc.getName();

        try {
            HttpClient client = new HttpClient(url);
            SOAPMessage response = client.sendPostMessage(soapObj);
            client.close();
            return response;
        }
        catch (Exception e) {
            msg = "Problem routing SOAP request to its destination.  The URL in error: " + url;
            logger.error(msg);
            throw new SystemException(msg, e);
        }
    }

}

package com.api.messaging.webservice.soap.engine;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPMessage;

import com.api.messaging.webservice.ServiceRoutingInfo;

/**
 * Contract for routing a SOAP message from the SOAP Engine to its intended 
 * destination where the web service implementation, which is responsible for processing 
 * the SOAP message, lives.
 * 
 * @author Roy Terrell
 *
 */
public interface SoapMessageRouter {

    /**
     * Sends the SOAP message to its intended destination
     * 
     * @param srvc
     *         the routing information pertaining to the SOAP message
     * @param soapObj
     *         the SOAP message instance
     * @param request
     *         the user's request
     * @return
     *         an instance of SOAPMessage
     * @throws SoapMessageRouterException
     */
    SOAPMessage routeMessage(ServiceRoutingInfo srvc, SOAPMessage soapObj, HttpServletRequest request) throws SoapMessageRouterException;

}

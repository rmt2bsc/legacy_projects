package com.api.messaging.webservice.soap.service;

import javax.xml.soap.SOAPMessage;

import com.action.ICommand;

import com.util.InvalidDataException;

/**
 * @author appdev
 * @deprecated Use SoapService interface
 *
 */
public interface SoapProcessor extends ICommand {

    /**
     * Drives the process of handling the user's request as a SOAP oriented web service.  
     * The presence of the this method allows the SoapProcessor instance to operate 
     * standalone or within the sandbox of some other container such as a servlet or EJB.  
     * A good best practice example would be an AbstractActionHandler derived hanlder 
     * used in a servlet environment where the processing of the request is driven by 
     * the method, processRequest(Request req, Response resp, String command) in the 
     * web service.
     * <pre>
     *    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
     *       String payload = request.getParameter(HttpConstants.XML_PAYLOAD);
     * 	     this.processRequest(payload);
     *         ...
     *    }      
     * </pre>
     * <p>
     * It is suggested that the implementation of this method follow the template pattern.   
     * The template pattern logic should invoke the following instance methods in the order 
     * stated: <i>acceptMessage(String)</i>, <i>processMessage()</i>, and <i>sendMessage()</i>.
     *  
     * @param xml
     *          an instance of SOAPMessage
     * @return String          
     * @throws SoapProcessorException
     */
    SOAPMessage processRequest(SOAPMessage soap) throws SoapProcessorException;

    /**
     * Accepts the SOAP message and cnverts the incomeing message into a SOAP message.
     * 
     * @param xml
     *           a String representing the raw XML message that will be converted to 
     *           the SOAP message object.
     * @return SOAPMessage
     *           an instance of SOAPMessage          
     * @throws InvalidDataException
     * @throws SoapProcessorException
     * @deprecated no longer in use since return type is SOAPMessage.
     */
    SOAPMessage acceptMessage(String xml) throws InvalidDataException, SoapProcessorException;

    /**
     * Disassemble the SOAP message that is passed to it by the servlet and process its contents. 
     * Processing the message involves accessing the parts of a SOAP message. If there are 
     * problems in the processing of the message, the service needs to create a SOAP fault 
     * object and send it back to the client.
     * 
     * @param soap
     *          an instance of SOAPMessage
     * @return SOAPMessage
     *          the response SOAP instance
     * @throws SoapProcessorException
     */
    SOAPMessage processMessage(SOAPMessage soap) throws SoapProcessorException;

}

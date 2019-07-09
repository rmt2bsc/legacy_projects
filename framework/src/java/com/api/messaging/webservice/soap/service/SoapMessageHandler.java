package com.api.messaging.webservice.soap.service;

import java.util.List;

import com.api.messaging.MessageHandler;

/**
 * Message handler interface for receiving and managing SOAP request messages.
 * 
 * @author rterrell
 *
 */
public interface SoapMessageHandler extends MessageHandler {

    /**
     * Get the attachments that are assoicated with the SOAP message that is 
     * in the process of being prepared for sent to the client.
     * 
     * @return  A List of abitrary objects.
     */
    List<Object> getAttachments();
    
}

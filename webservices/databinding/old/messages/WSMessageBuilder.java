package com.xml.schema.messages;


import com.api.ProductBuilder;



/**
 * Provides the functionality needed to handle a document-oriented XML messages, mainly 
 * SOAP type messages.  Primarily, this class is used to package XML SOAP content into 
 * SOAP message instances and vice versa.
 * 
 * @author appdev
 *
 */
public interface WSMessageBuilder extends ProductBuilder {
    /**
     * 
     */
    public static final String MSGTYPE_REQUEST = "REQUEST";
    
    /**
     * 
     */
    public static final String MSGTYPE_RESPONSE = "RESPONSE";

  
    
   
    void addHeader(String msgId, String delivMode, String delivDate, String msgType, String userId) throws WSMessageBuilderException;
    
   
    void addReplyStatus(String status, String msg, String detailMsg, int returnCode) throws WSMessageBuilderException;
    
    
    void addPayload(Object data) throws WSMessageBuilderException;
    
    void addMessage(Object message);
    
    String getHeaderMessageId();
    
    String getHeaderMessageType();
    
    String getHeaderDeliverDate();
    
    String getHeaderDeliveryMode();
    
    String getHeaderUserId();
    
    String getPaylod();
    
    String getReplyReturnStatus();
    
    String getReplyMessage();
    
    String getReplpyExtMessage();
    
    int getReplyReturnCode();
}

package com.xml.schema.misc;


import com.api.ProductBuilder;

import com.api.db.DatabaseException;

/**
 * Provides the functionality needed to handle a document-oriented XML messages, mainly 
 * SOAP type messages.  Primarily, this class is used to package XML SOAP content into 
 * SOAP message instances and vice versa.
 * 
 * @author appdev
 *
 */
public interface PayloadBuilder extends ProductBuilder {
    /**
     * 
     */
    public static final String MSGTYPE_REQUEST = "REQUEST";
    
    public static final String MSGTYPE_RESPONSE = "RESPONSE";

    /**
     * 
     */
    public static final String HEADER_NS = "rmt2-hns";

    /**
     * 
     */
    public static final String FAULT_NS = "rmt2-fns";
    
    /**
     * 
     */
    public static final String FAULT_DETAIL_NAME = "faultdetails";

    /**
     * 
     */
    public static final String SERVICEID_NAME = "serviceId";

    /**
     * 
     */
    public static final String QUEUE_NAME = "queueName";
    
    
   
    void addHeader(String msgId, String delivMode, String delivDate, String msgType, String userId) throws PayloadBuilderException;
    
   
    void addReplyStatus(String status, String msg, String detailMsg, int returnCode) throws PayloadBuilderException;
    
    
    void addMessageData(Object data) throws PayloadBuilderException;
    
  
}

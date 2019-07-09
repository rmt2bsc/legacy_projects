package com.api.messaging.webservice.soap.service;

import java.util.List;

import com.api.messaging.CommonMessageHandlerImpl;
import com.bean.db.DatabaseConnectionBean;
import com.controller.Request;

/**
 * An implementation fo SoapMessageHandler interface that provides house keeping 
 * functionality for tracking attachments for the outgoing SOAP message.
 * 
 * @author rterrell
 *
 */
public class SoapMessageHandlerImpl extends CommonMessageHandlerImpl implements SoapMessageHandler {

    protected List<Object> attachments;
    
    /**
     * 
     */
    public SoapMessageHandlerImpl() {
	super();
    }

    /**
     * @param con
     * @param request
     */
    public SoapMessageHandlerImpl(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.service.SoapMessageHandler#getAttachments()
     */
    public List<Object> getAttachments() {
	return this.attachments;
    }

}

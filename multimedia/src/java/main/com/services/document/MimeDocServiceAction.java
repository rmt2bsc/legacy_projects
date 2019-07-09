package com.services.document;


import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceHandler;
import com.bean.db.DatabaseConnectionBean;



/**
 * A web service receiver designed to dispatch MIME content related requests.
 * 
 * @author Roy Terrell
 * @deprecated Use MimeDocSoapService class.
 *
 */
public class MimeDocServiceAction extends AbstractSoapServiceHandler {

    private static Logger logger = Logger.getLogger(MimeDocServiceAction.class);

 
    /**
     * Default constructor
     *
     */
    public MimeDocServiceAction() {
	super();
    }

    /**
     * Invokes the process designed to serve the <i>RQ_content_search</i> request.
     *  
     *  @param inMsg
     *          the request message.
     * @return String
     *          the web service's response.
     * @throws ActionHandlerException
     */
    @Override 
    protected SOAPMessage doService(SOAPMessage inMsg) throws ActionHandlerException {
	MimeDocServiceAction.logger.info("Prepare to call Fetch Content Producer");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	MimeDocService srvc = new MimeDocService((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    return outSoapMsg;    
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
	
    }
 }
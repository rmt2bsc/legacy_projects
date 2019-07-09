package com.services;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.SoapMessageHelper;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceHandler;

import com.bean.db.DatabaseConnectionBean;

/**
 * A web service receiver designed to dispatch general code related requests to the 
 * appropriate Codes service implementation.
 * 
 * @author Roy Terrell
 * @deprecated Use PostalSoapService
 *
 */
public class PostalServiceAction extends AbstractSoapServiceHandler {
    private static final String COMMAND_FETCH = "RQ_postal_search";

    private static Logger logger = Logger.getLogger(PostalServiceAction.class);

    /**
     * Default constructor
     *
     */
    public PostalServiceAction() {
	super();
    }

    /**
     * Invokes the process designed to serve the <i>RQ_code_detail_search</i> request.
     *  
     *  @param inMsg
     *          the request message.
     * @return String
     *          the web service's response.
     * @throws ActionHandlerException
     */
    @Override
    protected SOAPMessage doService(SOAPMessage inMsg) throws ActionHandlerException {
	SOAPMessage resp = null;
	SoapMessageHelper helper = new SoapMessageHelper();
	
	try {
            String serviceId = helper.getHeaderValue("message_id", inMsg);
            if (serviceId.equalsIgnoreCase(PostalServiceAction.COMMAND_FETCH)) {
                resp = this.retrieveData(inMsg);
            }  
        }
	catch (SOAPException e) {
            throw new ActionHandlerException(e);
        }
	return resp;
    }

   
    
    
    private SOAPMessage retrieveData(SOAPMessage inMsg) throws ActionHandlerException {
	PostalServiceAction.logger.info("Prepare to fetch general code details Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	PostalSearchService srvc = new PostalSearchService((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }

}
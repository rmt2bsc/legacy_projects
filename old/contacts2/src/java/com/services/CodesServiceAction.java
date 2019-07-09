package com.services;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceHandler;

import com.bean.db.DatabaseConnectionBean;

/**
 * A web service receiver designed to dispatch general code related requests to the 
 * appropriate Codes service implementation.
 * 
 * @author Roy Terrell
 * @deprecated Use ContactSoapService class
 *
 */
public class CodesServiceAction extends AbstractSoapServiceHandler {
    private static final String COMMAND_FETCH_CODES = "Services.RQ_code_detail_search";

    private static Logger logger = Logger.getLogger(CodesServiceAction.class);

    /**
     * Default constructor
     *
     */
    public CodesServiceAction() {
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
	if (CodesServiceAction.COMMAND_FETCH_CODES.equalsIgnoreCase(this.command)) {
	    resp = this.fetchCodeDetails(inMsg);
	}

	return resp;
    }

    private SOAPMessage fetchCodeDetails(SOAPMessage inMsg) throws ActionHandlerException {
	CodesServiceAction.logger.info("Prepare to fetch general code details Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CodeDetailsFetchService srvc = new CodeDetailsFetchService((DatabaseConnectionBean) tx.getConnector(), this.request);
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
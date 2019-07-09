package com.services.soap;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.MessageHandler;
import com.api.messaging.MessagingHandlerException;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceImpl;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.db.DatabaseConnectionBean;
import com.services.handlers.CodeDetailsFetchHandler;

/**
 * A web service receiver designed to dispatch general code related requests to the 
 * appropriate Codes service implementation.
 * 
 * @author Roy Terrell
 *
 */
public class CodesSoapService extends AbstractSoapServiceImpl {
    private static final String COMMAND_FETCH_CODES = "Services.RQ_code_detail_search";

    private static Logger logger = Logger.getLogger(CodesSoapService.class);

    /**
     * Default constructor
     *
     */
    public CodesSoapService() {
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
    protected String invokeSoapHandler(Properties parms) throws SoapProcessorException {
	String resp = null;
	try {
	    if (CodesSoapService.COMMAND_FETCH_CODES.equalsIgnoreCase(this.command)) {
		resp = this.fetchCodeDetails(parms);
	    }
	    return resp;
	}
	catch (MessagingHandlerException e) {
	    throw new SoapProcessorException(e);
	}

    }

    private String fetchCodeDetails(Properties parms) throws MessagingHandlerException {
	CodesSoapService.logger.info("Prepare to fetch general code details Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CodeDetailsFetchHandler srvc = new CodeDetailsFetchHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RS_code_lookup";
	srvc.setResponseServiceId(this.responseMsgId);
	return this.executeHandler(srvc, parms, tx);
    }

    private String executeHandler(MessageHandler handler, Properties parms, DatabaseTransApi tx) throws MessagingHandlerException {
	try {
	    String result = (String) handler.execute(parms);
	    tx.commitUOW();
	    return result;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Database transaction for General Codes SOAP Service failed";
	    logger.error(this.msg);
	    throw new MessagingHandlerException(this.msg, e);
	}
	finally {
	    handler = null;
	    tx.close();
	    tx = null;
	}
    }

}
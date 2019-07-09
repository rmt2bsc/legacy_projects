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
import com.services.handlers.BusinessContactDeleteHandler;
import com.services.handlers.BusinessContactFetchHandler;
import com.services.handlers.BusinessContactUpdateHandler;
import com.services.handlers.CommonContactFetchHandler;

/**
 * A web service receiver designed to dispatch business contact related requests to the 
 * appropriate business contact service implementation.
 * 
 * @author Roy Terrell
 *
 */
public class ContactSoapService extends AbstractSoapServiceImpl {

    private static final String COMMAND_FETCHCOMMON = "Services.RQ_common_contact_search";

    private static final String COMMAND_FETCH = "Services.RQ_business_contact_search";

    private static final String COMMAND_UPDATE = "Services.RQ_business_contact_update";

    private static final String COMMAND_DELETE = "Services.RQ_business_contact_delete";

    private static Logger logger = Logger.getLogger(ContactSoapService.class);

    /**
     * Default constructor
     *
     */
    public ContactSoapService() {
	super();
    }

    /**
     * Invokes the process designed to serve the <i>RQ_business_contact_search</i> request.
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
	    if (ContactSoapService.COMMAND_FETCHCOMMON.equalsIgnoreCase(this.command)) {
		resp = this.fetchCommonContact(parms);
	    }
	    if (ContactSoapService.COMMAND_FETCH.equalsIgnoreCase(this.command)) {
		resp = this.fetchBusinessContact(parms);
	    }
	    else if (ContactSoapService.COMMAND_UPDATE.equalsIgnoreCase(this.command)) {
		resp = this.updateBusinessContact(parms);
	    }
	    else if (ContactSoapService.COMMAND_DELETE.equalsIgnoreCase(this.command)) {
		resp = this.deleteBusinessContact(parms);
	    }
	    return resp;
	}
	catch (MessagingHandlerException e) {
	    throw new SoapProcessorException(e);
	}
    }

    /**
     * 
     * @param inMsg
     * @return
     * @throws ActionHandlerException
     */
    private String fetchCommonContact(Properties parms) throws MessagingHandlerException {
	ContactSoapService.logger.info("Prepare to call Common Contact Fetch Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CommonContactFetchHandler srvc = new CommonContactFetchHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RS_common_contact_search";
	srvc.setResponseServiceId(this.responseMsgId);
	return this.executeHandler(srvc, parms, tx);
    }

    private String fetchBusinessContact(Properties parms) throws MessagingHandlerException {
	ContactSoapService.logger.info("Prepare to call Business Contact Fetch Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessContactFetchHandler srvc = new BusinessContactFetchHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RS_business_contact_search";
	srvc.setResponseServiceId(this.responseMsgId);
	return this.executeHandler(srvc, parms, tx);
    }

    private String updateBusinessContact(Properties parms) throws MessagingHandlerException {
	ContactSoapService.logger.info("Prepare to call Business Contact Update Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessContactUpdateHandler srvc = new BusinessContactUpdateHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RS_business_contact_update";
	srvc.setResponseServiceId(this.responseMsgId);
	return this.executeHandler(srvc, parms, tx);
    }

    private String deleteBusinessContact(Properties parms) throws MessagingHandlerException {
	ContactSoapService.logger.info("Prepare to call Business Contact Delete Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessContactDeleteHandler srvc = new BusinessContactDeleteHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RS_business_contact_delete";
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
	    this.msg = "Database transaction for Contact SOAP Service failed";
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
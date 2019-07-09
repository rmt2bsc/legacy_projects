package com.services;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceHandler;

import com.bean.db.DatabaseConnectionBean;

/**
 * A web service receiver designed to dispatch business contact related requests to the 
 * appropriate business contact service implementation.
 * 
 * @author Roy Terrell
 * @deprecated User ContactSoapService class
 *
 */
public class ContactServiceAction extends AbstractSoapServiceHandler {
    
    private static final String COMMAND_FETCHCOMMON = "Services.RQ_common_contact_search";
    
    private static final String COMMAND_FETCH = "Services.RQ_business_contact_search";

    private static final String COMMAND_UPDATE = "Services.RQ_business_contact_update";

    private static final String COMMAND_DELETE = "Services.RQ_business_contact_delete";

    private static Logger logger = Logger.getLogger(ContactServiceAction.class);

    /**
     * Default constructor
     *
     */
    public ContactServiceAction() {
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
    protected SOAPMessage doService(SOAPMessage inMsg) throws ActionHandlerException {
	SOAPMessage resp = null;
	if (ContactServiceAction.COMMAND_FETCHCOMMON.equalsIgnoreCase(this.command)) {
            resp = this.fetchCommonContact(inMsg);
        }
	if (ContactServiceAction.COMMAND_FETCH.equalsIgnoreCase(this.command)) {
	    resp = this.fetchBusinessContact(inMsg);
	}
	else if (ContactServiceAction.COMMAND_UPDATE.equalsIgnoreCase(this.command)) {
	    resp = this.updateBusinessContact(inMsg);
	}
	else if (ContactServiceAction.COMMAND_DELETE.equalsIgnoreCase(this.command)) {
	    resp = this.deleteBusinessContact(inMsg);
	}
	return resp;
    }

    /**
     * 
     * @param inMsg
     * @return
     * @throws ActionHandlerException
     */
    private SOAPMessage fetchCommonContact(SOAPMessage inMsg) throws ActionHandlerException {
        ContactServiceAction.logger.info("Prepare to call Common Contact Fetch Service");
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CommonContactFetchService srvc = new CommonContactFetchService((DatabaseConnectionBean) tx.getConnector(), this.request);
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
    
    
    private SOAPMessage fetchBusinessContact(SOAPMessage inMsg) throws ActionHandlerException {
	ContactServiceAction.logger.info("Prepare to call Business Contact Fetch Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessContactFetchService srvc = new BusinessContactFetchService((DatabaseConnectionBean) tx.getConnector(), this.request);
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

    
    private SOAPMessage updateBusinessContact(SOAPMessage inMsg) throws ActionHandlerException {
	ContactServiceAction.logger.info("Prepare to call Business Contact Update Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessContactUpdateService srvc = new BusinessContactUpdateService((DatabaseConnectionBean) tx.getConnector(), this.request);
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

    private SOAPMessage deleteBusinessContact(SOAPMessage inMsg) throws ActionHandlerException {
	ContactServiceAction.logger.info("Prepare to call Business Contact Delete Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	BusinessContactDeleteService srvc = new BusinessContactDeleteService((DatabaseConnectionBean) tx.getConnector(), this.request);
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
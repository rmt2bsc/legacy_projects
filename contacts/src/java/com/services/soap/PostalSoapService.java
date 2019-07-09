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
import com.services.handlers.PostalSearchHandler;



/**
 * A web service handler designed to retrieve postal result sets pertaining to Country,
IP Address, State/Province, Time Zones, and Zip code related queries. 
 * 
 * @author Roy Terrell
 *
 */
public class PostalSoapService extends AbstractSoapServiceImpl {
    private static final String COMMAND_FETCH = "RQ_postal_search";

    private static Logger logger = Logger.getLogger(PostalSoapService.class);

    /**
     * Default constructor
     *
     */
    public PostalSoapService() {
	super();
    }

    /**
     * Invokes the process designed to serve the <i>RQ_code_detail_search</i> request.
     *  
     *  @param inMsg
     *          the request message.
     * @return String
     *          the web service's response.
     * @throws SoapProcessorException
     */
    @Override
    public String invokeSoapHandler(Properties parms) throws SoapProcessorException {
	String resp = null;
	try {
	    if (PostalSoapService.COMMAND_FETCH.equalsIgnoreCase(this.command)) {
		resp = this.doFetch(parms);
	    }
	    return resp;
        }
	catch (MessagingHandlerException e) {
            throw new SoapProcessorException(e);
        }
    }

   
    
    
    private String doFetch(Properties parms) throws MessagingHandlerException {
	PostalSoapService.logger.info("Prepare to fetch data from Postal SOAP Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	PostalSearchHandler srvc = new PostalSearchHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RS_postal_search";
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
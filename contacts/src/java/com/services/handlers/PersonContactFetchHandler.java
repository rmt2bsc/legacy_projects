package com.services.handlers;


import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.ContactException;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.CommonMessageHandlerImpl;
import com.api.messaging.MessagingHandlerException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.personal.PersonApi;
import com.api.personal.PersonFactory;

import com.bean.VwPersonAddress;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.PersonContactCriteria;
import com.xml.schema.bindings.PersonType;
import com.xml.schema.bindings.RQPersonalContactSearch;
import com.xml.schema.bindings.RSPersonalContactSearch;
import com.xml.schema.bindings.ReplyStatusType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching a
 * single record from the person table using various forms of selection criteria.  
 * The incoming and outgoing data is expected to be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class PersonContactFetchHandler extends CommonMessageHandlerImpl {

    private static Logger logger = Logger.getLogger(PersonContactFetchHandler.class);

    private RQPersonalContactSearch reqMessage;
    

    /**
     * Default constructor
     *
     */
    protected PersonContactFetchHandler() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public PersonContactFetchHandler(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }
    

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    public String execute(Properties parms) throws MessagingHandlerException {
	super.execute(parms);
	String xml = null;
	this.reqMessage = (RQPersonalContactSearch) parms.get(SoapProductBuilder.PAYLOADINSTANCE);
	try {
	    xml = this.doFetch(this.reqMessage.getCriteriaData());
	    return xml;
	}
	catch (ContactException e) {
	    throw new MessagingHandlerException(e);
	}
    }

    /**
     * Fetch a single record from the content table using the primary key (content id).
     * 
     * @param contentId
     *          the primary key
     * @return String
     *           the raw XML data representing the single content record.
     * @throws ContactException
     *           when the content record is not found.
     */
    protected String doFetch(PersonContactCriteria criteria) throws ContactException {
	PersonApi api = PersonFactory.createPersonApi(this.con, this.request);

	//Send content to Browser
	try {
	    String sql = this.buildSelectionCriteria(criteria);
	    List<VwPersonAddress> results = (List<VwPersonAddress>) api.findContact(sql);
	    return this.buildMutiItemResponsePayload(results, null);
	}
	catch (Exception e) {
	    this.msg = "Unable to execute Person Contact Fetch service due to a genreal contact related error";
	    logger.error(this.msg);
	    throw new ContactException(this.msg, e);
	}
	finally {
	    api = null;
	}
    }

    /**
     * 
     * @param criteria
     * @return
     */
    private String buildSelectionCriteria(PersonContactCriteria criteria) {
	StringBuffer buf = new StringBuffer();
	return buf.toString();
    }

    /**
     * 
     * @param results
     * @return
     */
    public String buildMutiItemResponsePayload(List<VwPersonAddress> results, String message) {
	ObjectFactory f = new ObjectFactory();
	RSPersonalContactSearch ws = f.createRSPersonalContactSearch();

	JaxbContactsFactory jaxbUtil = new JaxbContactsFactory(this.con);
        PersonType pt = null;
        if (results != null) {
            for (VwPersonAddress contact : results) {
                pt = jaxbUtil.createPersonalTypeInstance(contact);
                ws.getPersonList().add(pt);
            }
        }
        
	HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.getUserId());
	ws.setHeader(header);
	message = (message == null ? results.size() + " total records found" : message);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, (results == null ? 0 : results.size()));
	ws.setReplyStatus(rst);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    /**
     * 
     * @param contact
     * @param message
     * @param responseId
     * @return
     */
    public String buildSingleItemResponsePayload(VwPersonAddress contact, String message, String responseId)  {
	this.setResponseServiceId(responseId);
	return this.buildSingleItemResponsePayload(contact, message);
    }
    
    /**
     * 
     * @param contact
     * @param message
     * @return
     */
    public String buildSingleItemResponsePayload(VwPersonAddress contact, String message)  {
	ObjectFactory f = new ObjectFactory();
	RSPersonalContactSearch ws = f.createRSPersonalContactSearch();

	HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), "SYNC", "RESPONSE", this.getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, contact == null ? 0 : 1);
	ws.setReplyStatus(rst);

	PersonType pt = null;
	if (contact == null) {
	    pt = f.createPersonType();
	}
	else {
	    DatabaseTransApi tx = null;
	    try {
		tx = DatabaseTransFactory.create();
		VwPersonAddress obj = (VwPersonAddress) contact;
		JaxbContactsFactory cf = new JaxbContactsFactory((DatabaseConnectionBean) tx.getConnector());
		pt = cf.createPersonalTypeInstance(obj);
	    }
	    catch (Exception e) {
		throw new DatabaseException(e);
	    }
	    finally {
		tx.close();
		tx = null;
	    }
	}
	// Append business contact to message
	ws.getPersonList().add(pt);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

}
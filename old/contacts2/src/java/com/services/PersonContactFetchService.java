package com.services;


import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;
import com.api.personal.PersonApi;
import com.api.personal.PersonFactory;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.VwPersonAddress;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
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
 * @deprecated Use PersonContactFetchHandler.
 *
 */
public class PersonContactFetchService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger(PersonContactFetchService.class);

    private RQPersonalContactSearch reqMessage;

    /**
     * Default constructor
     *
     */
    protected PersonContactFetchService() {
	super();
	this.responseMsgId = "RS_personal_contact_search";
    }

    /**
     * 
     * @param con
     * @param request
     */
    public PersonContactFetchService(DatabaseConnectionBean con, Request request) {
	super(con, request);
	this.responseMsgId = "RS_personal_contact_search";
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
	String xml = null;
	this.reqMessage = (RQPersonalContactSearch) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);
	xml = this.doFetch(this.reqMessage.getCriteriaData());
	return xml;
    }

    /**
     * Fetch a single record from the content table using the primary key (content id).
     * 
     * @param contentId
     *          the primary key
     * @return String
     *           the raw XML data representing the single content record.
     * @throws SoapProcessorException
     *           when the content record is not found.
     */
    protected String doFetch(PersonContactCriteria criteria) throws SoapProcessorException {
	PersonApi api = PersonFactory.createPersonApi(this.con, this.request);

	//Send content to Browser
	try {
	    String sql = this.buildSelectionCriteria(criteria);
	    List<VwPersonAddress> results = (List<VwPersonAddress>) api.findContact(sql);
	    return this.buildMutiItemResponsePayload(results, null);
	}
	catch (Exception e) {
	    // SOAP engine router will handle the creation of the SOAP Fault from the exception
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SoapProcessorException(e);
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
        
	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	message = (message == null ? results.size() + " total records found" : message);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, (results == null ? 0 : results.size()));
	ws.setReplyStatus(rst);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    public String buildSingleItemResponsePayload(VwPersonAddress contact, String message)  {
	ObjectFactory f = new ObjectFactory();
	RSPersonalContactSearch ws = f.createRSPersonalContactSearch();
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
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
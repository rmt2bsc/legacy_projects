package com.services;

import java.math.BigInteger;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.business.BusinessApi;
import com.api.business.BusinessFactory;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.VwBusinessAddress;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;

import com.xml.schema.bindings.BusinessContactCriteria;
import com.xml.schema.bindings.BusinessType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQBusinessContactSearch;
import com.xml.schema.bindings.RSBusinessContactSearch;
import com.xml.schema.bindings.ReplyStatusType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching a
 * single record from the business table using various forms of selection criteria.  
 * The incoming and outgoing data is expected to be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 * @deprecated use Business ContactFetchHandler
 *
 */
public class BusinessContactFetchService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger(BusinessContactFetchService.class);

    private RQBusinessContactSearch reqMessage;

    /**
     * Default constructor
     *
     */
    protected BusinessContactFetchService() {
	super();
	this.responseMsgId = "RS_business_contact_search";
    }

    /**
     * 
     * @param con
     * @param request
     */
    public BusinessContactFetchService(DatabaseConnectionBean con, Request request) {
	super(con, request);
	this.responseMsgId = "RS_business_contact_search";
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
	String xml = null;
	this.reqMessage = (RQBusinessContactSearch) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);
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
    protected String doFetch(BusinessContactCriteria criteria) throws SoapProcessorException {
	BusinessApi api = BusinessFactory.createBusinessApi(this.con, this.request);

	//Send content to Browser
	try {
	    String sql = this.buildSelectionCriteria(criteria);
	    List<VwBusinessAddress> results = (List<VwBusinessAddress>) api.findContact(sql);
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
    private String buildSelectionCriteria(BusinessContactCriteria criteria) {
	StringBuffer buf = new StringBuffer();

	// handle business id
	if (criteria.getBusinessId().size() > 1) {
	    buf.append(" business_id in(");
	    int count = 0;
	    for (BigInteger busId : criteria.getBusinessId()) {

		if (count > 0) {
		    buf.append(", ");
		}
		buf.append(busId.intValue());
		count++;
	    }
	    buf.append(") ");
	}
	else if (criteria.getBusinessId().size() == 1) {
	    buf.append(" business_id = ");
	    buf.append(criteria.getBusinessId().get(0));
	}
	// handle long name
	if (criteria.getBusinessName() != null && !criteria.getBusinessName().equals("")) {
	    if (buf.length() > 0) {
		buf.append(" and ");
	    }
	    buf.append(" bus_longname like \'");
	    buf.append(criteria.getBusinessName());
	    buf.append("%\' ");
	}

	// handle phone number
	if (criteria.getMainPhone() != null && !criteria.getMainPhone().equals("")) {
	    if (buf.length() > 0) {
		buf.append(" and ");
	    }
	    buf.append(" addr_phone_main like \'");
	    buf.append(criteria.getMainPhone());
	    buf.append("%\' ");
	}

	// handle tax id
	if (criteria.getTaxId() != null && !criteria.getTaxId().equals("")) {
	    if (buf.length() > 0) {
		buf.append(" and ");
	    }
	    buf.append(" bus_tax_id like \'");
	    buf.append(criteria.getTaxId());
	    buf.append("%\' ");
	}
	return buf.toString();
    }

    /**
     * 
     * @param results
     * @return
     */
    public String buildMutiItemResponsePayload(List<VwBusinessAddress> results, String message) {
	ObjectFactory f = new ObjectFactory();
	RSBusinessContactSearch ws = f.createRSBusinessContactSearch();

	JaxbContactsFactory jaxbUtil = new JaxbContactsFactory(this.con);
        BusinessType bt = null;
        int recCount = 0;
        if (results != null) {
            recCount = results.size();
            for (VwBusinessAddress contact : results) {
                bt = jaxbUtil.createBusinessTypeInstance(contact);
                ws.getBusinessList().add(bt);
            }
        }
        
	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	message = (message == null ? recCount + " total records found" : message);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", message, recCount);
	ws.setReplyStatus(rst);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

    public String buildSingleItemResponsePayload(VwBusinessAddress contact, String message)  {
	ObjectFactory f = new ObjectFactory();
	RSBusinessContactSearch ws = f.createRSBusinessContactSearch();
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, "SYNC", "RESPONSE", userSession.getLoginId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", null, contact == null ? 0 : 1);
	ws.setReplyStatus(rst);

	BusinessType bt = null;
	if (contact == null) {
	    bt = f.createBusinessType();
	}
	else {
	    DatabaseTransApi tx = null;
	    try {
		tx = DatabaseTransFactory.create();
		VwBusinessAddress obj = (VwBusinessAddress) contact;
		JaxbContactsFactory cf = new JaxbContactsFactory((DatabaseConnectionBean) tx.getConnector());
		bt = cf.createBusinessTypeInstance(obj);
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
	ws.getBusinessList().add(bt);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

}
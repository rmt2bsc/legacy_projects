package com.services.handlers;

import java.math.BigInteger;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.ContactException;
import com.api.business.BusinessApi;
import com.api.business.BusinessFactory;
import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.CommonMessageHandlerImpl;
import com.api.messaging.MessagingHandlerException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.bean.VwBusinessAddress;

import com.bean.bindings.JaxbContactsFactory;
import com.bean.db.DatabaseConnectionBean;

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
 *
 */
public class BusinessContactFetchHandler extends CommonMessageHandlerImpl {

    private static Logger logger = Logger.getLogger(BusinessContactFetchHandler.class);

    private RQBusinessContactSearch reqMessage;

    /**
     * Default constructor
     *
     */
    protected BusinessContactFetchHandler() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public BusinessContactFetchHandler(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    public String execute(Properties parms) throws MessagingHandlerException {
	super.execute(parms);
	String xml = null;
	this.reqMessage = (RQBusinessContactSearch) parms.get(SoapProductBuilder.PAYLOADINSTANCE);
	try {
	    xml = this.doFetch(this.reqMessage.getCriteriaData());
	    return xml;
	}
	catch (Exception e) {
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
     * @throws SoapProcessorException
     *           when the content record is not found.
     */
    protected String doFetch(BusinessContactCriteria criteria) throws ContactException {
	BusinessApi api = BusinessFactory.createBusinessApi(this.con, this.request);

	//Send content to Browser
	try {
	    String sql = this.buildSelectionCriteria(criteria);
	    List<VwBusinessAddress> results = (List<VwBusinessAddress>) api.findContact(sql);
	    return this.buildMutiItemResponsePayload(results, null);
	}
	catch (ContactException e) {
	    // SOAP engine router will handle the creation of the SOAP Fault from the exception
	    this.msg = "The Business contact fetch service failed with general errors";
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
        
	HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.getUserId());
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

	HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), "SYNC", "RESPONSE", this.getUserId());
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
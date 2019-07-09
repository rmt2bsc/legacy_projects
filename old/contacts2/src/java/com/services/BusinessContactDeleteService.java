package com.services;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.business.BusinessApi;
import com.api.business.BusinessFactory;

import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.Business;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQBusinessContactDelete;
import com.xml.schema.bindings.RSBusinessContactDelete;
import com.xml.schema.bindings.ReplyStatusType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of deleteing 
 * a single record from the business table.  The incoming and outgoing data 
 * is expected to be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 * @deprecated Use BusinessContactDeleteHandler
 *
 */
public class BusinessContactDeleteService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger(BusinessContactDeleteService.class);

    private RQBusinessContactDelete reqMessage;

    /**
     * Default constructor
     *
     */
    protected BusinessContactDeleteService() {
	super();
    }

    public BusinessContactDeleteService(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
	String xml = null;
	this.reqMessage = (RQBusinessContactDelete) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);
	xml = this.doDelete(this.reqMessage.getBusinessId().intValue());
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
    protected String doDelete(int busId) throws SoapProcessorException {
	int rows = 0;
	BusinessApi api = BusinessFactory.createBusinessApi(this.con, this.request);
	Business bus = BusinessFactory.createBusiness();
	bus.setBusinessId(busId);

	try {
	    rows = api.deleteContact(bus);
	    return this.buildResponsePayload(rows);
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

    private String buildResponsePayload(int rows) {
	this.responseMsgId = "RS_business_contact_delete";
	ObjectFactory f = new ObjectFactory();
	RSBusinessContactDelete ws = f.createRSBusinessContactDelete();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Business Contact delete was successful", null, rows);
	ws.setReplyStatus(rst);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }
}
package com.services.handlers;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.ContactException;
import com.api.business.BusinessApi;
import com.api.business.BusinessFactory;

import com.api.messaging.CommonMessageHandlerImpl;
import com.api.messaging.MessagingHandlerException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

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
 *
 */
public class BusinessContactDeleteHandler extends CommonMessageHandlerImpl {

    private static Logger logger = Logger.getLogger(BusinessContactDeleteHandler.class);

    private RQBusinessContactDelete reqMessage;

    /**
     * Default constructor
     *
     */
    protected BusinessContactDeleteHandler() {
	super();
	return;
    }

    public BusinessContactDeleteHandler(DatabaseConnectionBean con, Request request) {
	super(con, request);
	return;
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    public String execute(Properties parms) throws MessagingHandlerException {
	String xml = null;
	this.reqMessage = (RQBusinessContactDelete) parms.get(SoapProductBuilder.PAYLOADINSTANCE);
	try {
	    xml = this.doDelete(this.reqMessage.getBusinessId().intValue());
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
    protected String doDelete(int busId) throws ContactException {
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
	    this.msg = "The business contact delete service failed due to general errors";
	    logger.error(this.msg);
	    throw new ContactException(this.msg, e);
	}
	finally {
	    api = null;
	}
    }

    private String buildResponsePayload(int rows) {
	ObjectFactory f = new ObjectFactory();
	RSBusinessContactDelete ws = f.createRSBusinessContactDelete();

	HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Business Contact delete was successful", null, rows);
	ws.setReplyStatus(rst);

	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }
}
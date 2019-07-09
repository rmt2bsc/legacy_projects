package com.services.handlers;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;

import com.api.messaging.CommonMessageHandlerImpl;
import com.api.messaging.MessageException;
import com.api.messaging.MessagingHandlerException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.bean.bindings.JaxbAccountingFactory;

import com.bean.criteria.CustomerCriteria;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.gl.customer.CustomerApi;
import com.gl.customer.CustomerExt;
import com.gl.customer.CustomerFactory;

import com.xml.schema.bindings.CustomerCriteriaType;
import com.xml.schema.bindings.CustomerType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQCustomerSearch;
import com.xml.schema.bindings.RSCustomerSearch;
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
public class CustomerFetchServiceHandler extends CommonMessageHandlerImpl {

    private static Logger logger = Logger.getLogger("CustomerFetchServiceHandler");

    private RQCustomerSearch reqMessage;
    
    

    /**
     * Default constructor
     *
     */
    protected CustomerFetchServiceHandler() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public CustomerFetchServiceHandler(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    public String execute(Properties reqParms) throws MessagingHandlerException {
	super.execute(reqParms);
	String xml = null;
	this.reqMessage = (RQCustomerSearch) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);
	try {
	    xml = this.doFetch(this.reqMessage.getCriteriaData());
	    return xml;
	}
	catch (DatabaseException e) {
	    this.msg = "Unble to execute service to fetch customer information";
	    logger.error(this.msg);
	    throw new MessagingHandlerException(this.msg);
	}
	
    }

    /**
     * Fetch a customer records based on selection criteria.  The results will include both 
     * customer data and business contact data.
     * 
     * @param custCriteria
     *          the customer selection criteria
     * @return String
     *           the raw XML data representing one or more customer records
     * @throws DatabaseException
     *           when the customer record is not found.
     */
    protected String doFetch(CustomerCriteriaType custCriteria) throws DatabaseException {
	CustomerApi api = CustomerFactory.createApi(this.con, this.request);
	
	// Get customer data
	try {
	    CustomerCriteria criteria = this.buildSelectionCriteria(custCriteria);
	    List<CustomerExt> custList = (List<CustomerExt>) api.findCustomerBusiness(criteria);
	    JaxbAccountingFactory jf = new JaxbAccountingFactory();
	    List <CustomerType> results = new ArrayList<CustomerType>();
	    for (CustomerExt cust : custList) {
		CustomerType jaxbObj = jf.createCustomerType(cust);
		results.add(jaxbObj);
	    }
	    return this.buildResponsePayload(results);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new DatabaseException(e);
	}
	finally {
	    api = null;
	}
    }

    /**
     * 
     * @param custCriteria
     * @return
     */
    private CustomerCriteria buildSelectionCriteria(CustomerCriteriaType custCriteria) throws MessageException {
	if (custCriteria.getCustomerId().size() > 0 && custCriteria.getBusinessId().size() > 0) {
	    this.msg = "Customer Id and Business Id criteria values must be mutually exclusive for SOAP customer criteria";
	    logger.error(this.msg);
	    throw new MessageException(this.msg);
	}
	
	CustomerCriteria criteria = CustomerCriteria.getInstance();
	
	// handle customer id
	if (custCriteria.getCustomerId().size() > 0) {
	    StringBuffer valList = new StringBuffer();
	    int count = 0;
	    for (BigInteger custId : custCriteria.getCustomerId()) {
		if (count > 0) {
		    valList.append(", ");
		}
		valList.append(custId.intValue());
		count++;
	    }
	    criteria.setQry_CustomerIdList(valList.toString());
	}
	else if (custCriteria.getCustomerId().size() == 1) {
	    criteria.setQry_CustomerId(String.valueOf(custCriteria.getCustomerId().get(0)));
	}
	
	// handle business id
	if (custCriteria.getBusinessId().size() > 1) {
	    StringBuffer valList = new StringBuffer();
	    int count = 0;
	    for (BigInteger busId : custCriteria.getBusinessId()) {
		if (count > 0) {
		    valList.append(", ");
		}
		valList.append(busId.intValue());
		count++;
	    }
	    criteria.setQry_BusinessIdList(valList.toString());
	}
	else if (custCriteria.getBusinessId().size() == 1) {
	    criteria.setQry_BusinessId(String.valueOf(custCriteria.getBusinessId().get(0)));
	}
	
	// handle account number
	if (custCriteria.getAccountNo() != null && !custCriteria.getAccountNo().equals("")) {
	    criteria.setQry_AccountNo(custCriteria.getAccountNo());
	}
	return criteria;
    }

    /**
     * 
     * @param results
     * @return
     */
    private String buildResponsePayload(List<CustomerType> results) {
	String responseMsgId = "RS_customer_search";
	ObjectFactory f = new ObjectFactory();
	RSCustomerSearch ws = f.createRSCustomerSearch();

	HeaderType header = PayloadFactory.createHeader(responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", null, (results == null ? 0 : results.size()));
	ws.setReplyStatus(rst);
	ws.getCustomerList().addAll(results);
	
	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

}
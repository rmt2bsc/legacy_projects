package com.gl;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.RMT2Base;


/**
 * Helper class that provides common or utility functionality to be used by the 
 * accounting and transaction modules.
 * 
 * @author RTerrell
 * @deprecated Functioanlity has been refactored to {@link com.bean.bindings.JaxbAccountingFactory JaxbAccountingFactory}.
 *
 */
public class BasicAccountingHelper extends RMT2Base {
    private Logger logger;

    /**
     * Default constructor that creates a BasicAccountingHelper object 
     * by initializing the logger.
     */
    public BasicAccountingHelper() {
	super();
	this.logger = Logger.getLogger("BasicAccountingHelper");
	this.logger.log(Level.DEBUG, "Logger created");
    }

//    /**
//     * Invoke remote service from the contacts application to obtain business 
//     * entity and its address.
//     * 
//     * @param request
//     * @param response
//     * @return
//     * @throws SystemException
//     * @deprecated Use {@link com.bean.bindings.JaxbAccountingFactory#getCustomerContactData(com.xml.schema.bindings.BusinessContactCriteria, String) getCustomerContactData}
//     */
//    public Object getBusinessContactInfo(Request request, Response response) throws SystemException {
//	if (request == null) {
//	    this.msg = "Business contact is not obtainable due to an invalid user request object";
//	    this.logger.log(Level.ERROR, this.msg);
//	    throw new SystemException(this.msg);
//	}
//
//	// Business id must exists in the request.
//	Properties props = RMT2Utility.getRequestData(request);
//	String reqBusId = props.getProperty("Arg_BusinessId");
//	if (reqBusId == null) {
//	    this.msg = "Business id must exists in the request in order to perform business contact service call";
//	    this.logger.log(Level.ERROR, this.msg);
//	    throw new SystemException(this.msg);
//	}
//
//	Object results;
//	HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer();
//	try {
//	    srvc.processRequest(request, response, "getBusAddrById");
//	    results = srvc.getServiceResults();
//	    if (results == null) {
//		results = "";
//	    }
//	}
//	catch (ActionHandlerException e) {
//	    throw new SystemException(e);
//	}
//	return results;
//    }
}

package com.gl.codes;


import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.messaging.MessagingException;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.RMT2Base;
import com.bean.bindings.JaxbAccountingFactory;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;



/**
 * Provides general code common functionality that can be used throughout the application.
 * 
 * @author RTerrell
 *
 */
public class GeneralCodesHelper extends RMT2Base {
    private Logger logger;

    protected Request request;

    protected Response response;

    /**
     * 
     */
    public GeneralCodesHelper() {
	this.logger = Logger.getLogger("GeneralCodesHelper");
    }

    public GeneralCodesHelper(Request request, Response response) throws SystemException {
	this();
	if (request == null) {
	    this.msg = "Request object is invalid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
	if (response == null) {
	    this.msg = "Response object is invalid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new SystemException(this.msg);
	}
	this.request = request;
	this.response = response;
    }

    /**
     * Obtain list of business type code records as XML document.
     * 
     * @return String
     *          XML Payload for the SOAP body in the form of RS_code_detail_search message
     * @throws SystemException
     */
    public String getBusinessTypeCodes() throws MessagingException {
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	JaxbAccountingFactory f = new JaxbAccountingFactory();
	return f.getBusinessTypeCodes(userSession.getLoginId());
    }
    
    
    /**
     * Obtain list of business service type code records as XML document.
     * 
     * @return String
     *          XML Payload for the SOAP body in the form of RS_code_detail_search message
     * @throws SystemException
     */
    public String getBusinessServiceTypeCodes() throws MessagingException {
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	JaxbAccountingFactory f = new JaxbAccountingFactory();
	return f.getBusinessServiceTypeCodes(userSession.getLoginId());
    }
}

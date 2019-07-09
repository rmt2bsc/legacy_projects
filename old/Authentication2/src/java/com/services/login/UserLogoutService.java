package com.services.login;


import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;
import com.api.security.SecurityFactory;
import com.api.security.UserSecurity;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.LogoutException;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationLogin;
import com.xml.schema.bindings.RQAuthenticationLogout;
import com.xml.schema.bindings.RSAuthenticationLogout;
import com.xml.schema.bindings.ReplyStatusType;

import com.xml.schema.misc.PayloadFactory;




/**
 * Back end web service implementation that serves the request of loggin a user
 * out of the system.  The incoming and outgoing data is expected to be in 
 * the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class UserLogoutService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("UserLogoutService");

    private RQAuthenticationLogout reqMessage;
    

    /**
     * Default constructor
     *
     */
    protected UserLogoutService() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public UserLogoutService(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }

    
    /**
     * Get login information and add to Properties collection.
     * 
     * @param soap
     * @return
     * @throws SoapProcessorException
     */
    protected Properties doGetRequestData(SOAPMessage soap) throws SoapProcessorException {
	Properties props = super.doGetRequestData(soap);
	this.reqMessage = (RQAuthenticationLogout) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	props.put(AuthenticationConst.AUTH_PROP_USERID, this.reqMessage.getLogoutCredentials().getUid());
	props.put(AuthenticationConst.AUTH_PROP_SESSIONID, this.reqMessage.getLogoutCredentials().getSessionId());
	return props;
    }
    
    
    /**
     * Fetches the general code records based on selection criteria contained in <i>reqParms</i>.  
     * 
     * @param reqParms
     * @return String
     *          XML Payload for the response message.
     * @throws SoapProcessorException
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
	String xml = null;
	String msg = null;
	int rc = 0;
	UserSecurity api = SecurityFactory.getDatabaseAuthenticationApi(this.request, reqParms);
	try {
	    rc = api.doLogout();
	    msg = "User, " + reqParms.getProperty(AuthenticationConst.AUTH_PROP_USERID) + ", was logged out of " + rc + " applications!";
	    logger.info(msg);
	    xml = this.buildResponsePayload(rc, msg);
	}
	catch (LogoutException e) {
	    // SOAP engine router will handle the creation of the SOAP Fault from the exception
	    throw new SoapProcessorException(e);
	}
	return xml;
    }

      

    /**
     * Builds the payload for the  RS_authentication_logout response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(int totalApps, String msg) {
	this.responseMsgId = "RS_authentication_logout";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationLogout ws = f.createRSAuthenticationLogout();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, msg, null, totalApps);
	ws.setReplyStatus(rst);
	
        // Marshall XML message isntance.
	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }
 
}
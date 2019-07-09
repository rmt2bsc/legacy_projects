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
import com.api.security.authentication.LoginException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.bindings.JaxbAuthenticationFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationLogin;
import com.xml.schema.bindings.RSAuthenticationLogin;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.UserSessionType;

import com.xml.schema.misc.PayloadFactory;




/**
 * Back end web service implementation that serves the request of Logging a 
 * user into the system.  The incoming and outgoing data is expected to be in 
 * the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class UserLoginService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("UserLoginService");

    private RQAuthenticationLogin reqMessage;
    

    /**
     * Default constructor
     *
     */
    protected UserLoginService() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public UserLoginService(DatabaseConnectionBean con, Request request) {
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
	this.reqMessage = (RQAuthenticationLogin) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	String val = null;
	// Get and validate user id
	val = this.reqMessage.getLoginCredentials().getUid();
	if (val == null) {
	    this.msg = "User id is required when logging into system";
	    logger.error(this.msg);
	    throw new SoapProcessorException(this.msg);
	}
	props.put(AuthenticationConst.AUTH_PROP_USERID, val);
	// Get and validate password
	val = this.reqMessage.getLoginCredentials().getPw();
	if (val == null) {
	    this.msg = "Password is required when logging into system";
            logger.error(this.msg);
            throw new SoapProcessorException(this.msg);
        }
	props.put(AuthenticationConst.AUTH_PROP_PASSWORD, val);
	// Get and validate application code
	val = this.reqMessage.getLoginCredentials().getAppCode();
	if (val == null) {
	    this.msg = "Application code is required when logging into system";
            logger.error(this.msg);
            throw new SoapProcessorException(this.msg);
        }
	props.put(AuthenticationConst.AUTH_PROP_MAINAPP, val);
	// Get and validate session id.
	val = this.reqMessage.getLoginCredentials().getSessionId();
	if (val == null) {
            this.msg = "Session Id is required when logging into system";
            logger.error(this.msg);
            throw new SoapProcessorException(this.msg);
        }
	props.put(AuthenticationConst.AUTH_PROP_SESSIONID, val);

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
	UserSecurity api = SecurityFactory.getDatabaseAuthenticationApi(this.request, reqParms);
	RMT2SessionBean session = null;
	int rc = 0;
	String msg = null;
	String userId = reqParms.getProperty(AuthenticationConst.AUTH_PROP_USERID);
	try {
	    session = (RMT2SessionBean) api.doLogin();
	    msg = "User, " + userId + ", was logged in successfully";
	    logger.info(msg);
	    rc = 1;
	    xml = this.buildResponsePayload(session, rc, msg);
	    return xml;
	}
	catch (LoginException e) {
	    // SOAP engine router will handle the creation of the SOAP Fault from the exception
	    this.msg = "Login Failed failed for user id, " + userId;
	    logger.error(this.msg);
	    throw new SoapProcessorException(e);
	}
    }

      

    /**
     * Builds the payload for the  RS_authentication_login response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(RMT2SessionBean session, int rc, String msg) {
	this.responseMsgId = "RS_authentication_login";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationLogin ws = f.createRSAuthenticationLogin();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, msg, null, rc);
	ws.setReplyStatus(rst);
	
	// Adapt RMT2SessionBean to UserSessionType
	if (session != null) {
	    UserSessionType ust = JaxbAuthenticationFactory.getUserSessionType(session);
	    ws.setSessionToken(ust);    
	}
	
        // Marshall XML message isntance.
	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }
 
}
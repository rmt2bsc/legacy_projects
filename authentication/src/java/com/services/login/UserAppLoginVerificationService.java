package com.services.login;

import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.services.RemoteUserAuthenticaterProducer;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;
import com.api.security.SecurityFactory;
import com.api.security.UserSecurity;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.LoginException;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.bindings.JaxbAuthenticationAdapter;
import com.bean.bindings.JaxbAuthenticationFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationLogin;
import com.xml.schema.bindings.RQAuthenticationUserAppcheck;
import com.xml.schema.bindings.RSAuthenticationLogin;
import com.xml.schema.bindings.RSAuthenticationUserAppcheck;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.UserAppLoginCheckType;
import com.xml.schema.bindings.UserSessionType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of checking whether or not a user 
 * is logged in to at least one application.  The incoming and outgoing data is expected to be in 
 * the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class UserAppLoginVerificationService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger(UserAppLoginVerificationService.class);

    private RQAuthenticationUserAppcheck reqMessage;

    /**
     * Default constructor
     *
     */
    protected UserAppLoginVerificationService() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public UserAppLoginVerificationService(DatabaseConnectionBean con, Request request) {
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
	this.reqMessage = (RQAuthenticationUserAppcheck) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	if (this.reqMessage.getUserCredentials().getUid() != null) {
	    props.put(AuthenticationConst.AUTH_PROP_USERID, this.reqMessage.getUserCredentials().getUid());    
	}
	if (this.reqMessage.getUserCredentials().getAppCode() != null) {
	    props.put(AuthenticationConst.AUTH_PROP_MAINAPP, this.reqMessage.getUserCredentials().getAppCode());    
	}
	if (this.reqMessage.getUserCredentials().getSessionId() != null) {
	    props.put(AuthenticationConst.AUTH_PROP_SESSIONID, this.reqMessage.getUserCredentials().getSessionId());    
	}
	
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
	try {
	    String loginId = reqParms.getProperty(AuthenticationConst.AUTH_PROP_USERID);
	    String appName = reqParms.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
	    String srcSessionId = reqParms.getProperty(AuthenticationConst.AUTH_PROP_SESSIONID);
	    RMT2SessionBean session = api.getAuthentication(loginId, appName, srcSessionId);
	    if (session == null) {
	        this.msg = "There are no active sessions for the user...User must login into system";
	        logger.warn(this.msg);
	        throw new LoginException(this.msg);
	    }
	    else {
		this.msg = "User is currently signed on to at least one system...Authentication verified!";
	    }
	    xml = this.buildResponsePayload(session);
	    return xml;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SoapProcessorException(e);
	}
    }

    /**
     * Builds the payload for the  RS_authentication_login response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(RMT2SessionBean session) {
	this.responseMsgId = "RS_authentication_user_appcheck";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationUserAppcheck ws = f.createRSAuthenticationUserAppcheck();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	
	ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "RS_authentication_user_appcheck call invoked", this.msg, (session == null ? -1 : 1));
        ws.setReplyStatus(rst);

	// Adapt RMT2SessionBean to UserSessionType
	if (session != null) {
	    UserAppLoginCheckType rc = f.createUserAppLoginCheckType();
	    rc.setAppCode(session.getOrigAppId());
	    rc.setSessionId(session.getSessionId());
	    rc.setUid(session.getLoginId());
	    rc.setSignedOn(true);
	    
	    // Add Session bean as XML
	    UserSessionType ust = JaxbAuthenticationFactory.getUserSessionType(session);
	    rc.setSessionToken(ust);
	    ws.setUserAuth(rc);
	}

	// Marshall XML message isntance.
	String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

}
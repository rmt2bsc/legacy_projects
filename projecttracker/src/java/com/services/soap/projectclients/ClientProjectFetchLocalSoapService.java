package com.services.soap.projectclients;

import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.MessageHandler;
import com.api.messaging.MessagingHandlerException;


import com.api.messaging.webservice.soap.service.AbstractSoapServiceImpl;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.services.handler.ClientProjectsHandler;
import com.services.projectclients.ClientProjectsService;


/**
 * A web service receiver designed to dispatch project tracker related requests to the 
 * appropriate SOAP base web service implementation.  The action handler is capable of 
 * processing incoming requests which the message content can be either SOAP based or HTTP 
 * URL query string based.
 * <p>
 * This class would benefit more from a RESTful web service implementation since it is 
 * designed to be used within the same application context.
 * 
 * @author Roy Terrell
 *
 */
public class ClientProjectFetchLocalSoapService extends AbstractSoapServiceImpl {
    private static final String COMMAND_CLIENTPROJ_FETCH = "Services.Services.RQ_projecttracker_clinet_projects";

    private static Logger logger = Logger.getLogger(ClientProjectFetchLocalSoapService.class);

    /**
     * Identifies whether the origin of the request is of the local application context.    
     */
    private boolean localRequest;

    private Properties parms;
    
    private String xmlResult;

    /**
     * Default constructor
     *
     */
    public ClientProjectFetchLocalSoapService() {
	super();
    }

    /**
     * Invokes the process designed to serve the <i>RQ_business_contact_search</i> request.
     *  
     *  @param inMsg
     *          the request message.
     * @return String
     *          the web service's response.
     * @throws ActionHandlerException
     */
    @Override
    public String invokeSoapHandler(Properties parms) throws SoapProcessorException {
	String resp = null;
	try {
	    if (ClientProjectFetchLocalSoapService.COMMAND_CLIENTPROJ_FETCH.equalsIgnoreCase(this.command)) {
		resp = this.fetchClientProjects(this.parms);
	    }
	    this.xmlResult = resp;
	    return resp;
	}
	catch (MessagingHandlerException e) {
	    throw new SoapProcessorException(e);
	}
    }

    /**
     * Retreives the input parameters from the user's request instance.  
     * <p>
     * The request SOAP message will not exist since the request originates locally in terms of 
     * application context.  This is the case the request made from a non-SOAP client such as 
     * an AJAX call within a JSP.  If the request is issued from this project, then the SOAP 
     * message will be invalid, and one will need to be created before proceeding.  Otherwise, 
     * a valid SOAP message should be obtained successfully.
     */
    protected void receiveClientData() throws ActionHandlerException {
	// Request must of been made from within this application context which a SOAP message 
	// must be created on the fly before proceeding.
	this.parms = this.createInternalInputParms();
	this.localRequest = true;
    }

    /**
     * Uses values from the user's request in order to build a SOAP message instance.
     * 
     * @return SOAPMessage instance.
     */
    private Properties createInternalInputParms() {
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	String userId = userSession.getLoginId();
	String clientId = this.request.getParameter(ClientProjectsService.PARM_CLIENTID);
	String serviceId = "RQ_projecttracker_clinet_projects";

	Properties props = new Properties();
	props.setProperty(MessageHandler.MSG_USERID_TAG, userId);
	props.setProperty(MessageHandler.MSG_REQUESTID_TAG, serviceId);
	props.setProperty(ClientProjectsHandler.PARM_CLIENTID, clientId);
	return props;
    }

    protected Properties doGetRequestData(SOAPMessage soap) throws SoapProcessorException {
	if (this.localRequest) {
	    return null;
	}
	return super.doGetRequestData(soap);
    }

    private String fetchClientProjects(Properties parms) throws MessagingHandlerException {
	ClientProjectFetchLocalSoapService.logger.info("Prepare to fetch client project data");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ClientProjectsHandler srvc = new ClientProjectsHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RQ_projecttracker_clinet_projects";
	srvc.setResponseServiceId(this.responseMsgId);
	return this.executeHandler(srvc, parms, tx);
    }

    private String executeHandler(MessageHandler handler, Properties parms, DatabaseTransApi tx) throws MessagingHandlerException {
	try {
	    String result = (String) handler.execute(parms);
	    tx.commitUOW();
	    return result;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.msg = "Database transaction for Contact SOAP Service failed";
	    logger.error(this.msg);
	    throw new MessagingHandlerException(this.msg, e);
	}
	finally {
	    handler = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Overrides ancestor in order to bypass the process that sends response back to the 
     * client directly via the HttpServletResponse.  The response is sent via the HttpServletRequest 
     * instance to prevent redundant calls to obtain the writer from the the reponse instance 
     * since this constitutes an I/O error.  For example,  a non-SOAP client such as an AJAX call 
     * embedded in a JSP.  If the request is of remote origins, then the ancestor logic is executed 
     * so that the SOAP response is sent to the SOAP client.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	if (this.localRequest) {
	    this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, this.xmlResult);
	}
	else {
	    super.sendClientData();
	}
    }

}
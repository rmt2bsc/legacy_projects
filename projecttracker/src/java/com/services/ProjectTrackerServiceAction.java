package com.services;

import java.math.BigInteger;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.MessageException;

import com.api.messaging.webservice.soap.SoapMessageHelper;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceHandler;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.services.projectclients.ClientProjectsService;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQProjecttrackerClinetProjects;

import com.xml.schema.misc.PayloadFactory;



/**
 * A web service receiver designed to dispatch project tracker related requests to the 
 * appropriate SOAP base web service implementation.  The action handler is capable of 
 * processing incoming requests which the message content can be either SOAP based or HTTP 
 * URL query string based.
 * 
 * @author Roy Terrell
 * @deprecated Use ClientProjectFetchLocalSoapService class.
 *
 */
public class ProjectTrackerServiceAction extends AbstractSoapServiceHandler {
    private static final String COMMAND_CLIENTPROJ_FETCH = "Services.Services.RQ_projecttracker_clinet_projects";
    
    private static Logger logger = Logger.getLogger(ProjectTrackerServiceAction.class);
    
    /**
     * Identifies whether the origin of the request is of the local application context.    
     */
    private boolean localRequest;

    /**
     * Default constructor
     *
     */
    public ProjectTrackerServiceAction() {
	super();
    }

    /**
     * Retreives the client's SOAP request from the user's request instance.  The SOAP message 
     * may not exist depending on the application context from which the request was made.  This
     * is the case the request made from a non-SOAP client such as an AJAX call within a JSP.  If 
     * the request is issued from this project, then the SOAP message will be invalid, and one will 
     * need to be created before proceeding.  Otherwise, a valid SOAP message should be obtained 
     * successfully.
     */
    protected void receiveClientData() throws ActionHandlerException {
        try {
            // Try to obtain the SOAP message from the request instance.   
            super.receiveClientData();
        }
        catch (Exception e) {
            // Request must of been made from within this application context which a SOAP message 
            // must be created on the fly before proceeding.
            this.inMsg = this.createInternalSoapMessageInstance();
            this.localRequest = true;
        }       
    }
    
    /**
     * Uses values from the user's request in order to build a SOAP message instance.
     * 
     * @return SOAPMessage instance.
     * @throws ActionHandlerException
     */
    private SOAPMessage createInternalSoapMessageInstance() throws ActionHandlerException {
        String serviceId = "RQ_projecttracker_clinet_projects";
        SOAPMessage sm = null;
        if (ProjectTrackerServiceAction.COMMAND_CLIENTPROJ_FETCH.equalsIgnoreCase(this.command)) {
            ObjectFactory f = new ObjectFactory();
            try {
                RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
                RQProjecttrackerClinetProjects ws = f.createRQProjecttrackerClinetProjects();
                HeaderType header = PayloadFactory.createHeader(serviceId, "SYNC", "REQUEST", userSession.getLoginId());
                ws.setHeader(header);
                
                String clientId = this.request.getParameter(ClientProjectsService.PARM_CLIENTID);
                if (clientId != null) {
                    ws.setClientId(BigInteger.valueOf(Integer.parseInt(clientId)));
                }
                String msg = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
                SoapMessageHelper helper = new SoapMessageHelper();
                String soapMsgStr = helper.createRequest(serviceId, null, msg);
                sm = helper.getSoapInstance(soapMsgStr);
            }
            catch (MessageException e) {
                throw new ActionHandlerException(e);
            }
        }
        return sm;
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
    protected SOAPMessage doService(SOAPMessage inMsg) throws ActionHandlerException {
	SOAPMessage resp = null;
	if (ProjectTrackerServiceAction.COMMAND_CLIENTPROJ_FETCH.equalsIgnoreCase(this.command)) {
	    resp = this.fetchClientProjects(inMsg);
	}
	return resp;
    }

    private SOAPMessage fetchClientProjects(SOAPMessage inMsg) throws ActionHandlerException {
	ProjectTrackerServiceAction.logger.info("Prepare to fetch client project data");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ClientProjectsService srvc = new ClientProjectsService((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Overrides ancestor in order to bypass the process that sends SOAP response back to the SOAP 
     * client when the request was originated from a non-SOAP client such as an AJAX call embedded 
     * in a JSP.  If the request is of remote origins, then the ancestor logic is executed so that 
     * the SOAP response is sent to the SOAP client.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        if (this.localRequest) {
            SoapMessageHelper helper = new SoapMessageHelper();
            try {
                String body = helper.getBody(this.outMsg);
                this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, body);
            }
            catch (SoapProcessorException e) {
                throw new ActionHandlerException(e);
            }
        }
        else {
            super.sendClientData();
        }
    }

}
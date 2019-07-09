package com.services.projectclients;

import java.util.List;
import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.ProjProject;
import com.bean.bindings.JaxbProjectTrackerFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.project.ProjectException;
import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;

import com.xml.schema.bindings.ClientProjectType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQProjecttrackerClinetProjects;
import com.xml.schema.bindings.RSProjecttrackerClinetProjects;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching granted and revokded 
 * resource records as related to a given user.  The incoming and outgoing data is expected to 
 * be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 * @deprecated use ClientProjectsHandler class.
 *
 */
public class ClientProjectsService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("ClientProjectsService");
    
    public static final String PARM_CLIENTID = "ClientId";

    private RQProjecttrackerClinetProjects reqMessage;


    /**
     * Default constructor
     *
     */
    protected ClientProjectsService() {
	super();
	logger.info("Logger initialized");
    }

    /**
     * 
     * @param con
     * @param request
     */
    public ClientProjectsService(DatabaseConnectionBean con, Request request) {
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
	this.reqMessage = (RQProjecttrackerClinetProjects) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	if (this.reqMessage.getClientId() != null) {
	    props.setProperty(ClientProjectsService.PARM_CLIENTID, this.reqMessage.getClientId().toString());
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
        List <ProjProject> list;
        DatabaseTransApi tx = DatabaseTransFactory.create();
        SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);

        int clientId;
        try {
            String val = reqParms.getProperty(ClientProjectsService.PARM_CLIENTID);
            clientId = Integer.parseInt(val);
        }
        catch (NumberFormatException e) {
            clientId = 0;
        }
        try {
            list = (List <ProjProject>) api.findProjectByClientId(clientId);
            String xml = this.buildResponsePayload(list);
            return xml;
        }
        catch (ProjectException e) {
            throw new SoapProcessorException(e);
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
    }

    /**
     * Builds the payload for the  RS_projecttracker_clinet_projects response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(List <ProjProject> items) {
	this.responseMsgId = "RS_projecttracker_clinet_projects";
	ObjectFactory f = new ObjectFactory();
	RSProjecttrackerClinetProjects ws = f.createRSProjecttrackerClinetProjects();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);

	// Adapt ProjProject instances to ClientProjectType JAXB instances.
	List <ClientProjectType> newList = JaxbProjectTrackerFactory.getClientProjectTypeListInstance(items);
	ws.getProjectInfo().addAll(newList);
	// Marshall XML message isntance.
        String rawXml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }

}
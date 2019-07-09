package com.services.resources;

import java.util.List;
import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.security.user.ResourceApi;
import com.api.security.user.ResourceFactory;

import com.bean.UserResource;

import com.bean.bindings.JaxbAuthenticationFactory;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationResource;
import com.xml.schema.bindings.RSAuthenticationResource;
import com.xml.schema.bindings.ResourceType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching granted and revokded 
 * resource records as related to a given user.  The incoming and outgoing data is expected to 
 * be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class ResourceService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("UserResourceService");

    private static String RSRC_TYPE_ID = "rsrcTypeId";

    private static String RSRC_SUBTYPE_ID = "rsrcSubTypeId";

    private static String RSRC_NAME = "rsrcName";

    private static String RSRC_ID = "rsrcId";

    private RQAuthenticationResource reqMessage;

    private int rsrcId;
    
    private int rsrcTypeId;

    private int rsrcSubTypeId;
    
    private String rsrcName;

    /**
     * Default constructor
     *
     */
    protected ResourceService() {
	super();
	logger.info("Logger for ResourceService is initialized");
    }

    /**
     * 
     * @param con
     * @param request
     */
    public ResourceService(DatabaseConnectionBean con, Request request) {
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
	this.reqMessage = (RQAuthenticationResource) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	if (this.reqMessage.getRsrcId() != null) {
	    props.setProperty(ResourceService.RSRC_ID, this.reqMessage.getRsrcId().toString());
	}
	if (this.reqMessage.getRsrcTypeId() != null) {
	    props.setProperty(ResourceService.RSRC_TYPE_ID, this.reqMessage.getRsrcTypeId().toString());
	}
	if (this.reqMessage.getRsrcSubtypeId() != null) {
	    props.setProperty(ResourceService.RSRC_SUBTYPE_ID, this.reqMessage.getRsrcSubtypeId().toString());
	}
	if (this.reqMessage.getRsrcName() != null) {
	    props.setProperty(ResourceService.RSRC_NAME, this.reqMessage.getRsrcName());
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
	// Setup database transaction object and api's
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	UserResource ur = ResourceFactory.createUserResource();
	
	this.rsrcId = reqParms.getProperty(ResourceService.RSRC_ID) == null ? 0 : Integer.parseInt(reqParms.getProperty(ResourceService.RSRC_ID));
	this.rsrcTypeId = reqParms.getProperty(ResourceService.RSRC_TYPE_ID) == null ? 0 : Integer.parseInt(reqParms.getProperty(ResourceService.RSRC_TYPE_ID));
	this.rsrcSubTypeId = reqParms.getProperty(ResourceService.RSRC_SUBTYPE_ID) == null ? 0 : Integer.parseInt(reqParms.getProperty(ResourceService.RSRC_SUBTYPE_ID));
	this.rsrcName = reqParms.getProperty(ResourceService.RSRC_NAME);
	ur.setRsrcId(this.rsrcId);
	ur.setRsrcTypeId(this.rsrcTypeId);
	ur.setRsrcSubtypeId(this.rsrcSubTypeId);
	ur.setName(this.rsrcName);
	
	try {
	    List<UserResource> list = (List<UserResource>) api.get(ur);
	    String xml = this.buildResponsePayload(list);
	    return xml;
	} 
	catch (Exception e) {
	    throw new SoapProcessorException(e);
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Builds the payload for the  RS_authentication_user_resource_access response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(List<UserResource> items) {
	this.responseMsgId = "RS_authentication_resource";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationResource ws = f.createRSAuthenticationResource();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);

	// Adapt user instance, list of granted resources, and list of revoked resources to JAXB instance
	List<ResourceType> list = null;
	list = JaxbAuthenticationFactory.getResourceTypesInstance(items);
	ws.getItems().addAll(list);

	// Marshall XML message instance.
	String rawXml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }

}
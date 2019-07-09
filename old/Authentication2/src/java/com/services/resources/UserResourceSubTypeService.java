package com.services.resources;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.security.user.ResourceApi;
import com.api.security.user.ResourceFactory;

import com.bean.UserResourceSubtype;
import com.bean.bindings.JaxbAuthenticationFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationResourceSubtype;
import com.xml.schema.bindings.RSAuthenticationResourceSubtype;
import com.xml.schema.bindings.ResourceSubtypeType;

import com.xml.schema.misc.PayloadFactory;




/**
 * Back end web service implementation that serves the request of fetching user 
 * resource sub type records.  The incoming and outgoing data is expected to be in 
 * the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class UserResourceSubTypeService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("UserResourceSubTypeService");
    
    private static String RSRC_TYPE_ID = "rsrcTypeId";
    
    private static String RSRC_SUBTYPE_ID = "rsrcSubTypeId";
    
    private static String RSRC_SUBTYPE_NAME = "rsrcTypeName";

    private RQAuthenticationResourceSubtype reqMessage;
    

    /**
     * Default constructor
     *
     */
    protected UserResourceSubTypeService() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public UserResourceSubTypeService(DatabaseConnectionBean con, Request request) {
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
	this.reqMessage = (RQAuthenticationResourceSubtype) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	if (this.reqMessage.getRsrcTypeId() != null) {
	    props.setProperty(UserResourceSubTypeService.RSRC_TYPE_ID, this.reqMessage.getRsrcTypeId().toString());
	}
	if (this.reqMessage.getRsrcSubtypeId() != null) {
	    props.setProperty(UserResourceSubTypeService.RSRC_SUBTYPE_ID, this.reqMessage.getRsrcSubtypeId().toString());
	}
	if (this.reqMessage.getRsrcSubtypeName() != null) {
	    props.setProperty(UserResourceSubTypeService.RSRC_SUBTYPE_NAME, this.reqMessage.getRsrcSubtypeName());
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
	DatabaseTransApi tx = DatabaseTransFactory.create();
        ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector());
        UserResourceSubtype urs = ResourceFactory.createUserResourceSubtype();
	
        // Apply data to the service which the user requested to serve as
        // selection criteria.
        if (reqParms != null) {
            urs.setRsrcTypeId(reqParms.getProperty(UserResourceSubTypeService.RSRC_TYPE_ID) == null ? 0 : Integer.parseInt(reqParms.getProperty(UserResourceSubTypeService.RSRC_TYPE_ID)));
            urs.setRsrcSubtypeId(reqParms.getProperty(UserResourceSubTypeService.RSRC_SUBTYPE_ID) == null ? 0 : Integer.parseInt(reqParms.getProperty(UserResourceSubTypeService.RSRC_SUBTYPE_ID)));
            urs.setName(reqParms.getProperty(UserResourceSubTypeService.RSRC_SUBTYPE_NAME));
        }
	
        
        try {
            List <UserResourceSubtype> data = (List <UserResourceSubtype>) api.getSubType(urs);
            if (data == null) {
        	data = new ArrayList<UserResourceSubtype>();
        	this.msg= "No records were found";
            }
            else {
        	this.msg = data.size() + " records were found";
            }
            xml = this.buildResponsePayload(data, this.msg);
	    return xml;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new SoapProcessorException(e.getMessage());
        }
        finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

      

    /**
     * Builds the payload for the  RS_authentication_login response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(List <UserResourceSubtype> data, String msg) {
	this.responseMsgId = "RS_authentication_resource_subtype";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationResourceSubtype ws = f.createRSAuthenticationResourceSubtype();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);
	
	// Adapt list of User Resource bean instances to JAXB
	List <ResourceSubtypeType> list = JaxbAuthenticationFactory.getUserResourceSubTypeInstance(data);
	ws.getItems().addAll(list);   
	
        // Marshall XML message isntance.
	String rawXml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	return rawXml;
    }
 
}
package com.services.resources;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.http.service.AbstractHttpUrlWebServiceImpl;

import com.api.security.user.ResourceFactory;
import com.api.security.user.ResourceApi;

import com.bean.UserResource;
import com.bean.VwUserResourceAccess;

import com.bean.bindings.JaxbAuthenticationFactory;

import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RSAuthenticationResource;
import com.xml.schema.bindings.ResourceType;

import com.xml.schema.misc.PayloadFactory;

/**
 * HTTP URL Web Service for retrieving one or more user resources from the database as XML
 * based on custom selection criteria input from the client. Optionally, the client to
 * send the following input key/value pairs to build selection criteria:
 * <ul>
 * <li>Name</li>:&nbsp;The name of the resource to obtain.
 * <li>ResourceTypeId</li>:&nbsp;The resource type id to obtain.
 * <li>ResourceSubtypeId</li>:&nbsp;The resource sub type id to obtain.
 * </ul>
 * <p>
 * 
 * @author RTerrell
 * 
 */
public class HttpResourceService extends AbstractHttpUrlWebServiceImpl {
    
    private static final String SERVICE_GETSERVICES = "Services.RQ_authentication_resource";

    private static Logger logger = Logger.getLogger("HttpResourceService");

    /**
     * Inner class tha manages user resource and resource accessibility data.
     * 
     * @author appdev
     *
     */
    protected class UserProfileCombo {
	private UserResource res;
	private VwUserResourceAccess access;
	
	private UserProfileCombo(UserResource resource, VwUserResourceAccess accessibility) {
	    res = resource;
	    access = accessibility;
	}
	
	protected UserResource getResource() {
	    return res;
	}
	protected VwUserResourceAccess getAccess() {
	    return access;
	}
    }
    

    /**
     * Default constructor which instantiates the logger.
     * 
     * @throws SystemException
     */
    public HttpResourceService() throws SystemException {
        logger.info("Logger for HttpResourceService is initialized");
    }

    /**
     * Accepts the selection criteria from the request which will be used to
     * retrieve one or more user resources.  Examine the user's request object
     * for the following parameters to obtain selection criteria data that 
     * can be applied to the query:
     * 
     * <ul>
     *    <li>Name</li>:&nbsp;The name of the resource to obtain.
     *    <li>ResourceTypeId</li>:&nbsp;The resource type id to obtain.
     *    <li>ResourceSubtypeId</li>:&nbsp;The resource sub type id to obtain.
     * </ul>     
     * 
     * 
     * @throws AbstractActionHandler
     */
    public void receiveClientData() throws ActionHandlerException {
        UserResource resource = ResourceFactory.createXmlUserResource();
        VwUserResourceAccess access = ResourceFactory.createXmlUserAccess();
        try {
            ResourceFactory.packageBean(this.request, resource);
            ResourceFactory.packageBean(this.request, access);
            UserProfileCombo resCombo = new UserProfileCombo(resource, access);
            this.inObjData = resCombo;            
        }
        catch (SystemException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves user resource data based on the user's input criteria. The
     * results of the query are stored internally as XML.
     * 
     * @throws ActionHandlerException
     */
    public void processData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	UserProfileCombo combo = (UserProfileCombo) this.inObjData;
	
	if (combo == null) {
	    this.msg = "HttpResourceService service, " + this.command + ", failed.  Input data was not supplied";
	    logger.log(Level.ERROR, this.msg);
	    this.outData = new ActionHandlerException(this.msg);
	    return;
	}
	
	try {
            if (HttpResourceService.SERVICE_GETSERVICES.equalsIgnoreCase(this.command)) {
                List <UserResource> items = (List <UserResource>) api.get(combo.getResource());
                String xml = this.buildResponsePayload(items);
                this.outData = xml;
            }
	}
	catch (Exception e) {
	    this.outData = e;
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
	return;
    }
    
    
    /**
     * Builds the payload for the  RS_authentication_user_resource_access response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(List <UserResource> items) {
	String responseMsgId = "RS_authentication_resource";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationResource ws = f.createRSAuthenticationResource();

	HeaderType header = PayloadFactory.createHeader(responseMsgId, "http", "RESPONSE", "");
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

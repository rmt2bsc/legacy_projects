package com.services.resources;

import java.util.List;
import java.util.Properties;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.security.UserAuthenticationException;

import com.api.security.user.ResourceApi;
import com.api.security.user.ResourceFactory;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.UserLogin;
import com.bean.UserResource;
import com.bean.VwUserResourceAccess;
import com.bean.bindings.JaxbAuthenticationFactory;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.InvalidDataException;
import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAuthenticationUserResourceAccess;
import com.xml.schema.bindings.RSAuthenticationUserResourceAccess;
import com.xml.schema.bindings.ResourceAccessType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of fetching granted and revokded 
 * resource records as related to a given user.  The incoming and outgoing data is expected to 
 * be in the form of SOAP XML. 
 * 
 * @author Roy Terrell
 *
 */
public class UserResourceService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("UserResourceService");

    private static String RSRC_TYPE_ID = "rsrcTypeId";

    private static String RSRC_SUBTYPE_ID = "rsrcSubTypeId";

    private static String LOGIN_ID = "loginId";

    private RQAuthenticationUserResourceAccess reqMessage;

    private int rsrcTypeId;

    private int rsrcSubTypeId;

    private String loginId;

    /**
     * Default constructor
     *
     */
    protected UserResourceService() {
	super();
    }

    /**
     * 
     * @param con
     * @param request
     */
    public UserResourceService(DatabaseConnectionBean con, Request request) {
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
	this.reqMessage = (RQAuthenticationUserResourceAccess) props.get(SoapProductBuilder.PAYLOADINSTANCE);
	if (this.reqMessage.getRsrcTypeId() != null) {
	    props.setProperty(UserResourceService.RSRC_TYPE_ID, this.reqMessage.getRsrcTypeId().toString());
	}
	if (this.reqMessage.getRsrcSubtypeId() != null) {
	    props.setProperty(UserResourceService.RSRC_SUBTYPE_ID, this.reqMessage.getRsrcSubtypeId().toString());
	}
	if (this.reqMessage.getLoginId() != null) {
	    props.setProperty(UserResourceService.LOGIN_ID, this.reqMessage.getLoginId());
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
	// Perform validations
	try {
	    this.validateInputData(reqParms);
	}
	catch (InvalidDataException e) {
	    throw new SoapProcessorException(e);
	}

	// Setup database transaction object and api's
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	VwUserResourceAccess criteria = ResourceFactory.createUserAccess();
	List<VwUserResourceAccess> assignedData = null;
	List<UserResource> revokedData = null;
	UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector());
	UserLogin user = null;
	try {
	    // Setup serach criteria
	    user = (UserLogin) userApi.findUserByUserName(this.loginId);
	    criteria.setUserUid(user.getLoginId());
	    criteria.setRsrcTypeId(this.rsrcTypeId);
	    criteria.setRsrcSubtypeId(this.rsrcSubTypeId);
	    //      criteria.setResrcSecured(1);

	    // Get granted resources
	    try {
		assignedData = (List<VwUserResourceAccess>) api.getUserResourceAssigned(criteria);
	    }
	    catch (Exception e) {
		assignedData = null;
	    }

	    // Get revoked resources
	    try {
		revokedData = (List<UserResource>) api.getUserResourceRevoked(criteria);
	    }
	    catch (Exception e) {
		revokedData = null;
	    }
	    String xml = this.buildResponsePayload(user, assignedData, revokedData);
	    return xml;
	}
	catch (UserAuthenticationException e) {
	    throw new SoapProcessorException(e);
	}
	catch (DatabaseException e) {
	    throw new SoapProcessorException(e);
	}
	finally {
	    userApi.close();
	    userApi = null;
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Validate input data.
     * 
     * @param reqParms
     * @throws InvalidDataException
     */
    private void validateInputData(Properties reqParms) throws InvalidDataException {
	this.rsrcTypeId = reqParms.getProperty(UserResourceService.RSRC_TYPE_ID) == null ? 0 : Integer.parseInt(reqParms.getProperty(UserResourceService.RSRC_TYPE_ID));
	this.rsrcSubTypeId = reqParms.getProperty(UserResourceService.RSRC_SUBTYPE_ID) == null ? 0 : Integer.parseInt(reqParms.getProperty(UserResourceService.RSRC_SUBTYPE_ID));
	this.loginId = reqParms.getProperty(UserResourceService.LOGIN_ID);

	if (this.loginId == null) {
	    this.msg = "The User Resource Login id must be supplied";
	    logger.log(Level.ERROR, this.msg);
	    throw new InvalidDataException(this.msg);
	}

	// Get resource type id from client
	if (this.rsrcTypeId == 0) {
	    this.msg = "Resource Type Id must be supplied";
	    logger.log(Level.ERROR, this.msg);
	    throw new InvalidDataException(this.msg);
	}

	// Get resource sub type id from client.
	if (this.rsrcSubTypeId == 0) {
	    this.msg = "Resource Sub Type Id must be supplied";
	    logger.log(Level.ERROR, this.msg);
	    throw new InvalidDataException(this.msg);
	}
    }

    /**
     * Builds the payload for the  RS_authentication_user_resource_access response message.
     *  
     * @param results
     * @return
     */
    private String buildResponsePayload(UserLogin user, List<VwUserResourceAccess> granted, List<UserResource> revoked) {
	this.responseMsgId = "RS_authentication_user_resource_access";
	ObjectFactory f = new ObjectFactory();
	RSAuthenticationUserResourceAccess ws = f.createRSAuthenticationUserResourceAccess();

	HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
	ws.setHeader(header);

	// Adapt user instance, list of granted resources, and list of revoked resources to JAXB instance
	ResourceAccessType rat;
	try {
	    rat = JaxbAuthenticationFactory.getUserResourceAccessTypeInstance(user, granted, revoked);
	    ws.setRsrcAccessProfile(rat);

	    // Marshall XML message isntance.
	    String rawXml = com.api.messaging.ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    return rawXml;
	}
	catch (InvalidDataException e) {
	    throw new SystemException(e);
	}
    }

}
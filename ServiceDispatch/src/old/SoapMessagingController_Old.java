package com.dispatch;

import java.io.InputStream;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.DaoApi;

import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseException;

import com.api.messaging.webservice.ServiceRegistryManager;
import com.api.messaging.webservice.ServiceRoutingInfo;

import com.api.messaging.webservice.http.HttpClient;
import com.api.messaging.webservice.http.HttpException;

import com.api.messaging.webservice.soap.SoapMessageHelper;

import com.api.messaging.webservice.soap.engine.AbstractSoapEngine;
import com.api.messaging.webservice.soap.engine.SoapMessageRouterException;

import com.api.xml.XmlApiFactory;

import com.constants.GeneralConst;

import com.util.NotFoundException;
import com.util.RMT2File;
import com.util.SystemException;

/**
 * Servlet for accepting SOAP based web service requests and determining the routing information 
 * necessary for getting the SOAP message to its destination via HTTP protocol.
 * 
 * @author appdev
 *
 */
public class SoapMessagingController_Old extends AbstractSoapEngine {

    private static final long serialVersionUID = 5433010431085175539L;

    private static Logger logger = Logger.getLogger(SoapMessagingController.class);

    /**
     * @deprecated
     */
    private static final String SERV_RESOURCE_TYPE_ID = "3";
    /**
     * @deprecated
     */
    private static final String SERV_RESOURCE_TYPE = "ResourceTypeId";
    /**
     * @deprecated
     */
    private static final String SERV_RESOURCE_SUBTYPE_ID = "7";
    /**
     * @deprecated
     */
    private static final String SERV_RESOURCE_SUBTYPE = "ResourceSubTypeId";

    private static final String SERVICES_URL = "authentication/unsecureRequestProcessor/Services?clientAction=";

    private static Map<String, ServiceRoutingInfo> SERVICES;

    /**
     * 
     */
    public SoapMessagingController_Old() {
	return;
    }

    /**
     * Drives the initialization process when the SoapMessagingController is first loaded.
     */
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
    }

    /**
     * Initializes the router API with the HTTP router implementation and loads the services routing table.
     */
    public void initServlet() throws ServletException {
	super.initServlet();
	this.router = new HttpSoapMessageRouter();
	
	if (SoapMessagingController_Old.SERVICES == null) {
	    ServiceRegistryManager regMgr = new ServiceRegistryManager();
	    SoapMessagingController_Old.SERVICES = regMgr.loadServices(ServiceRegistryManager.SERV_RESOURCE_SUBTYPE_SOAP);
	}
	
//	this.dataSource = this.loadServices();
	return;
    }

    /**
     * Load services map into memory if this is the first time this servlet has been invoked.
     * 
     * @param request The request containing the data items needed to fetch desired services.
     * @return Map<String, ServiceFields>
     * @throws SystemException General and database access errors.
     * @deprecated Use {@link com.api.messaging.webservice.ServiceRegistryManager ServiceRegistryManager}
     */
    private Map<String, ServiceRoutingInfo> loadServices() {
	if (SoapMessagingController_Old.SERVICES == null) {
	    try {
		String data = (String) this.fetchAvailableServices();
		SoapMessagingController_Old.SERVICES = (Map<String, ServiceRoutingInfo>) this.createServiceList(data);
	    }
	    catch (Exception e) {
		throw new SystemException(e);
	    }
	}
	return SoapMessagingController_Old.SERVICES;
    }

    /**
     * Invokes the "getservices" remote service in order to obtain a list 
     * user resources that are of services type.  This method is able to 
     * determine the host application slated to fetch the lsit of services 
     * by looking up the values in its system configuation file.
     * 
     * @param request The user's request.
     * @return The results of the service call which is usuall a XML document 
     *         in the form of a String.
     * @throws ServletException 
     *           When a problem arises constructing the Service URL or invoking 
     *           the service.
     */
    //    private Object fetchAvailableServices() {
    //	Properties prop = new Properties();
    //	String url = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVER);
    //
    //	// Get the services host and the servlet to contact.
    //	url = url + "/" + System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_HOST);
    //	// Get the command module we are targeting.
    //	url = url + System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_COMMAND);
    //	// Get Service Id
    //	String serviceId = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_SERVICEID);
    //	// Recognize the client action as the service id.
    //	if (serviceId == null) {
    //	    return null;
    //	}
    //	url += "?clientAction=" + serviceId;
    //
    //	// Build SOAP Body XML 
    //	SoapMessageHelper helper = new SoapMessageHelper();
    //	ObjectFactory f = new ObjectFactory();
    //	RQAuthenticationUserResourceAccess ws = f.createRQAuthenticationUserResourceAccess();
    //	HeaderType header = PayloadFactory.createHeader("RQ_authentication_resource", "SYNC", "REQUEST", "admin");
    //	ws.setHeader(header);
    //	ws.setRsrcTypeId(BigInteger.valueOf(Integer.parseInt(SoapMessagingController.SERV_RESOURCE_TYPE_ID)));
    //	String reqSoapBody = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
    //	SOAPMessage sm = null;
    //	try {
    //	    String soapMessage = helper.createRequest(serviceId, reqSoapBody);
    //	    sm = helper.toInstance(soapMessage);
    //	}
    //	catch (SoapRequestException e) {
    //	    e.printStackTrace();
    //	}
    //	catch (SoapBuilderException e) {
    //	    e.printStackTrace();
    //	}
    //
    //	try {
    //	    HttpClient http = new HttpClient(url);
    //	    SOAPMessage response = http.sendPostMessage(sm);
    //	    if (helper.isError(response)) {
    //		String errMsg = helper.getErrorMessage(response);
    //		logger.error(errMsg);
    //		return null;
    //	    }
    //	    RSAuthenticationResource soapResp = (RSAuthenticationResource) helper.getBodyInstance(response);
    //	    return soapResp;
    //	}
    //	catch (HttpException e) {
    //	    throw new SystemException(e);
    //	}
    //	catch (SoapProcessorException e) {
    //	    e.printStackTrace();
    //	    throw new SystemException(e);
    //	}
    //    }
    
    /**
     * @deprecated Use {@link com.api.messaging.webservice.ServiceRegistryManager ServiceRegistryManager}
     */
    private Object fetchAvailableServices() {
	Properties prop = new Properties();
	String url = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVER);

	// Get the services host and the servlet to contact.
	url = url + "/" + System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_HOST);
	// Get the command module we are targeting.
	url = url + System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_COMMAND);
	// Get Service Id
	String serviceId = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_SERVICEID);
	// Recognize the client action as the service id.
	if (serviceId == null) {
	    return null;
	}
	prop.setProperty(GeneralConst.REQ_CLIENTACTION, serviceId);
	// Setup other parameters needed to make a successful service call. 
	prop.setProperty(SoapMessagingController_Old.SERV_RESOURCE_TYPE, SoapMessagingController_Old.SERV_RESOURCE_TYPE_ID);
	prop.setProperty(SoapMessagingController_Old.SERV_RESOURCE_SUBTYPE, SoapMessagingController_Old.SERV_RESOURCE_SUBTYPE_ID);

	try {
	    HttpClient http = new HttpClient(url);
	    InputStream in = (InputStream) http.sendPostMessage(prop);
	    Object data = null;
	    data = RMT2File.getStreamObjectData(in);
	    return data;
	}
	catch (HttpException e) {
	    throw new SystemException(e);
	}
    }

    /**
     * Creates a list of available services in the form of key/value pairs and persist 
     * the list in memory for repetive usage.  The key represents the service name, and 
     * the value represents the service URL.  The list is used as a lookup mechanism for 
     * service requests.  The query method used to extract the data is XPath, which targets 
     * each user_resource tag element as a row of data. 
     * 
     * @param data 
     *          A XML document representing data from the user_resource table.  Each row of 
     *          data will be enclosed by the xml tag, user_resource.
     * @return Hashtable of service name/service URL key/value pairs.
     * @throws DatabaseException General database errors
     * @throws SystemException General system errors.
     * @deprecated @deprecated Use {@link com.api.messaging.webservice.ServiceRegistryManager ServiceRegistryManager}
     */
    private Hashtable <String, ServiceRoutingInfo> createServiceList(String data) throws DatabaseException, SystemException {
	String msg;
	int loadCount = 0;

	if (data == null) {
	    return null;
	}
	System.out.println(data);
	DaoApi api = XmlApiFactory.createXmlDao(data);
	try {
	    Hashtable<String, ServiceRoutingInfo> srvHash = null;
	    // Get all user_resource elements
	    Object result[] = api.retrieve("//RS_authentication_resource/items");
	    int rows = ((Integer) result[0]).intValue();
	    if (rows <= 0) {
		return srvHash;
	    }

	    // Begin to build services map
	    while (api.nextRow()) {
		// Instantiate service map for the first interation.
		if (srvHash == null) {
		    srvHash = new Hashtable<String, ServiceRoutingInfo>();
		}
		String name;
		String url;
		String secured;
		String host;
		try {
		    name = api.getColumnValue("rsrc_name");
		    url = api.getColumnValue("url");
		    secured = api.getColumnValue("secured");
		    host = api.getColumnValue("host");
		    ServiceRoutingInfo srvc = new ServiceRoutingInfo();
		    srvc.setName(name);
		    srvc.setUrl(url);
		    srvc.setSecured(secured == "1");
		    srvc.setHost(host == null || host.equals("") ? null : host);
		    srvHash.put(name, srvc);
		    loadCount++;
		}
		catch (NotFoundException e) {
		    msg = "Could not locate remote service data: " + e.getMessage();
		    logger.log(Level.WARN, msg);
		}
	    } // Go to next element
	    return srvHash;
	}
	catch (DatabaseException e) {
	    throw e;
	}
	catch (SystemException e) {
	    throw e;
	}
	finally {
	    logger.log(Level.INFO, "Total number of services loaded: " + loadCount);
	}
    }

    @Override
    protected ServiceRoutingInfo getMessageRoutingInfo(SOAPMessage sm, Object dataSource) throws SoapMessageRouterException {
	if (dataSource == null || !(dataSource instanceof Map<?, ?>)) {
	    return null;
	}
	SoapMessageHelper helper = new SoapMessageHelper();
	try {
	    ServiceRoutingInfo srvc = helper.validateSoapMessage(sm, (Map<String, ServiceRoutingInfo>) dataSource);
	    return srvc;
	}
	catch (Exception e) {
	    throw new SoapMessageRouterException(e);
	}
    }

}

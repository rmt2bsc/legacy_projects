package com.api.messaging.webservice.soap;

import java.io.InputStream;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.DaoApi;
import com.api.Product;
import com.api.ProductBuilderException;
import com.api.ProductDirector;

import com.api.config.HttpSystemPropertyConfig;

import com.api.db.DatabaseException;


import com.api.messaging.webservice.http.HttpClient;
import com.api.messaging.webservice.http.HttpConstants;
import com.api.messaging.webservice.http.HttpException;
//import com.api.messaging.webservice.http.client.ServiceFields;

import com.api.xml.XmlApiFactory;

import com.bean.RMT2Base;

import com.constants.GeneralConst;

import com.util.InvalidDataException;
import com.util.NotFoundException;
import com.util.RMT2File;
import com.util.SystemException;

/**
 * 
 * @author appdev
 *
 */
public class SoapValidator extends RMT2Base {
    private static Logger logger = Logger.getLogger(SoapValidator.class);

//    private static final String SERV_RESOURCE_TYPE_ID = "3";
//
//    private static final String SERV_RESOURCE_TYPE = "ResourceTypeId";

//    private static Map SERVICES;

    /**
     * 
     */
    public SoapValidator() {
	return;
//	this.loadServices();
    }

//    public Map getServices() {
//	if (SoapValidator.SERVICES == null) {
//	    try {
//		this.loadServices();
//	    }
//	    catch (Exception e) {
//		throw new SystemException(e);
//	    }
//	}
//	return SoapValidator.SERVICES;
//    }

//    /**
//     * Load services map into memory if this is the first time this servlet has been invoked.
//     * 
//     * @param request The request containing the data items needed to fetch desired services.
//     * @throws SystemException General and database access errors.
//     */
//    public void loadServices() {
//	if (SoapValidator.SERVICES == null) {
//	    try {
//		String data = (String) this.fetchAvailableServices();
//		SoapValidator.SERVICES = (Map) this.createServiceList(data);
//	    }
//	    catch (Exception e) {
//		throw new SystemException(e);
//	    }
//	}
//    }

//    /**
//     * Invokes the "getservices" remote service in order to obtain a list 
//     * user resources that are of services type.  This method is able to 
//     * determine the host application slated to fetch the lsit of services 
//     * by looking up the values in its system configuation file.
//     * 
//     * @param request The user's request.
//     * @return The results of the service call which is usuall a XML document 
//     *         in the form of a String.
//     * @throws ServletException 
//     *           When a problem arises constructing the Service URL or invoking 
//     *           the service.
//     */
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
//	prop.setProperty(GeneralConst.REQ_CLIENTACTION, serviceId);
//	// Setup other parameters needed to make a successful service call. 
//	prop.setProperty(SoapValidator.SERV_RESOURCE_TYPE, SoapValidator.SERV_RESOURCE_TYPE_ID);
//
//	try {
//	    HttpClient http = new HttpClient(url);
//	    InputStream in = (InputStream) http.sendPostMessage(prop);
//	    Object data = null;
//	    data = RMT2File.getStreamObjectData(in);
//	    return data;
//	    //	    try {
//	    //		data = RMT2File.getStreamObjectData(in);
//	    //		return data;
//	    //	    }
//	    //	    catch (SystemException e) {
//	    //		throw new SystemException(e);
//	    //	    }
//	}
//	catch (HttpException e) {
//	    throw new SystemException(e);
//	}
//    }

//    /**
//     * Creates a list of available services in the form of key/value pairs and persist 
//     * the list in memory for repetive usage.  The key represents the service name, and 
//     * the value represents the service URL.  The list is used as a lookup mechanism for 
//     * service requests.  The query method used to extract the data is XPath, which targets 
//     * each user_resource tag element as a row of data. 
//     * 
//     * @param data 
//     *          A XML document representing data from the user_resource table.  Each row of 
//     *          data will be enclosed by the xml tag, user_resource.
//     * @return Hashtable of service name/service URL key/value pairs.
//     * @throws DatabaseException General database errors
//     * @throws SystemException General system errors.
//     */
//    private Hashtable createServiceList(String data) throws DatabaseException, SystemException {
//	String msg;
//	int loadCount = 0;
//
//	if (data == null) {
//	    return null;
//	}
//	System.out.println(data);
//	DaoApi api = XmlApiFactory.createXmlDao(data);
//	try {
//	    Hashtable srvHash = null;
//	    // Get all user_resource elements
//	    Object result[] = api.retrieve("user_resource");
//	    int rows = ((Integer) result[0]).intValue();
//	    if (rows <= 0) {
//		return srvHash;
//	    }
//
//	    // Begin to build services map
//	    while (api.nextRow()) {
//		// Instantiate service map for the first interation.
//		if (srvHash == null) {
//		    srvHash = new Hashtable();
//		}
//		String name;
//		String url;
//		String secured;
//		try {
//		    name = api.getColumnValue("name");
//		    url = api.getColumnValue("url");
//		    secured = api.getColumnValue("secured");
//		    ServiceFields srvc = new ServiceFields();
//		    srvc.setName(name);
//		    srvc.setUrl(url);
//		    srvc.setSecured(secured == "1");
//		    srvHash.put(name, srvc);
//		    loadCount++;
//		}
//		catch (NotFoundException e) {
//		    msg = "Could not locate remote service data: " + e.getMessage();
//		    logger.log(Level.WARN, msg);
//		}
//	    } // Go to next element
//	    return srvHash;
//	}
//	catch (DatabaseException e) {
//	    throw e;
//	}
//	catch (SystemException e) {
//	    throw e;
//	}
//	finally {
//	    logger.log(Level.INFO, "Total number of services loaded: " + loadCount);
//	}
//    }

//    /**
//     * Verify that soap results is not null and it is actually a soap message
//     * 
//     * @param soapXml
//     * @return SOAPMessage
//     * @throws NotFoundException
//     * @throws InvalidDataException
//     */
//    public SOAPMessage verify(String soapXml) throws NotFoundException, InvalidDataException {
//	String msg;
//	if (soapXml == null) {
//	    msg = "Parameter, " + GeneralConst.REQ_SOAPMESSAGE + ", was not found in user's request";
//	    SoapValidator.logger.log(Level.ERROR, msg);
//	    throw new NotFoundException(msg);
//	}
//	// Test if SOAPMessage instance can be created from the incoming message.
//	try {
//	    SOAPMessage sm = null;
//	    SoapProductBuilder builder = SoapResourceFactory.getSoapBuilderInstance(soapXml);
//	    Product xmlProd = ProductDirector.construct(builder);
//	    sm = (SOAPMessage) xmlProd.toObject();
//	    sm.getSOAPPart().getEnvelope();
//	    return sm;
//	}
//	catch (SOAPException e) {
//	    throw new InvalidDataException(e);
//	}
//	catch (ProductBuilderException e) {
//	    throw new InvalidDataException(e);
//	}
//    }

//    /**
//     * 
//     * 
//     * @param soapXml
//     * @return
//     * @throws NotFoundException
//     * @throws InvalidDataException
//     * @throws SoapBuilderException
//     */
//    public ServiceFields validate(SOAPMessage soapObj) throws NotFoundException, InvalidDataException, SoapBuilderException {
//	String msg;
//	String serviceId = null;
//	try {
////	    Iterator<SOAPHeaderElement> iter = soapObj.getSOAPHeader().extractAllHeaderElements();
//	    String qualifiedName = SoapProductBuilder.HEADER_NS + ":" + SoapProductBuilder.SERVICEID_NAME;
//	    serviceId = this.getHeaderValue(qualifiedName, soapObj);
////	    while (iter.hasNext()) {
////		SOAPHeaderElement item = iter.next();
////		Name n = item.getElementName();
////		String elementName = n.getQualifiedName();
////		if (qualifiedName.equals(elementName)) {
////		    serviceId = item.getTextContent();
////		    break;
////		}
////	    }
//	}
//	catch (SOAPException e) {
//	    throw new InvalidDataException(e);
//	}
//	catch (NotFoundException e) {
//	    // Do Nothing.   Let following logic discover this state and report the error.
//	}
//
//	// Validate the existence of Service Id
//	if (serviceId == null) {
//	    msg = "SOAP request is required to have declared a service id element";
//	    SoapValidator.logger.log(Level.ERROR, msg);
//	    throw new InvalidDataException(msg);
//	}
////	ServiceFields srvc = (ServiceFields) SoapValidator.SERVICES.get(serviceId);
//	ServiceFields srvc = (ServiceFields) this.getServices().get(serviceId);
//	if (srvc == null) {
//	    msg = "Requested service does not exist or is invalid: " + serviceId;
//	    SoapValidator.logger.log(Level.ERROR, msg);
//	    throw new InvalidDataException(msg);
//	}
//	if (!serviceId.equalsIgnoreCase(srvc.getName())) {
//	    msg = "A naming conflict exist between the service id input [" + serviceId + "] and the service data retrieved from list of services [" + srvc.getName() + "]";
//	    SoapValidator.logger.log(Level.ERROR, msg);
//	    throw new InvalidDataException(msg);
//	}
//	if (srvc.getUrl() == null) {
//	    msg = "The URL property of the requested service, " + serviceId + ", does not exist or is invalid: ";
//	    SoapValidator.logger.log(Level.ERROR, msg);
//	    throw new InvalidDataException(msg);
//	}
//	return srvc;
//    }

//    /**
//     * Obtains the value of an SOAP header element using its fully qualified header name.
//     *  
//     * @param qualifiedName
//     *          qualified header name
//     * @param soapObj
//     *          the SOAPMessage instance containing the headers to search
//     * @return String
//     *          the value of the qualified header
//     * @throws SOAPException
//     *          if a SOAP error occurs
//     * @throws NotFoundException
//     *          if qualifiedName does not exist.
//     */
//    public String getHeaderValue(String qualifiedName, SOAPMessage soapObj) throws SOAPException {
//	String value = null;
//	Iterator<SOAPHeaderElement> iter = soapObj.getSOAPHeader().examineAllHeaderElements();
//	while (iter.hasNext()) {
//	    SOAPHeaderElement item = iter.next();
//	    Name n = item.getElementName();
//	    String elementName = n.getQualifiedName();
//	    if (qualifiedName.equals(elementName)) {
//		value = item.getTextContent();
//		return value;
//	    }
//	}
//	throw new NotFoundException("Value for SOAP Header element, " + qualifiedName + ", was not found");
//    }

//    /**
//     * Gets the SOAP message contained in the user's request.
//     * 
//     * @param request
//     *          the user's request
//     * @return String
//     *          the SOAP message in String format.
//     *  
//     */
//    public String getSoapRequest(HttpServletRequest request) {
//	return request.getParameter(HttpConstants.XML_PAYLOAD);
//    }

//    /**
//     * Get SOAP message as a String from the Object Input Stream.
//     * 
//     * @param in
//     * @return
//     */
//    public String getSoapRequest(InputStream in) {
//	// Get SOAP message as a String from the Object Input Stream of the request
//	String soapXml = null;
//	try {
//	    soapXml = RMT2File.getStreamStringData(in);
//	    SoapValidator.logger.log(Level.DEBUG, "Size of SOAP Message String: " + soapXml.length());
//	    return soapXml;
//	}
//	catch (Exception e) {
//	    msg = e.getMessage();
//	    logger.log(Level.ERROR, msg);
//	    throw new SystemException(msg);
//	}
//    }

//    /**
//     * Get SOAP message from a String.
//     * 
//     * @param in
//     * @return
//     */
//    public String getSoapRequest(String in) {
//	return in;
//    }
//
//    public String createSoapFault(String code, String msgTxt, String actor, Map details) {
////	SOAPMessage sm = null;
//	try {
//	    SoapProductBuilder builder = SoapResourceFactory.getSoapBuilderInstance(null, null, code, msgTxt, actor, details);
//	    Product xmlProd = ProductDirector.construct(builder);
////	    sm = (SOAPMessage) xmlProd.toObject();
////	    xmlProd = ProductDirector.deConstruct(builder);
//	    String xml2 = xmlProd.toString();
//	    return xml2;
//	}
//	catch (Exception e) {
//	    throw new SystemException(e);
//	}
//    }
}

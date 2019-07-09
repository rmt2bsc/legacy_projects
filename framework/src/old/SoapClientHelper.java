package com.api.messaging.webservice.soap;



import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.Product;
import com.api.ProductDirector;


/**
 * A factory for creating objects related to the SOAP Api.
 * 
 * @author appdev
 * @deprecated no longer needed.
 *
 */
public class SoapClientHelper {

    private static Logger logger = Logger.getLogger(SoapClientHelper.class);
    
    /**
     * 
     */
    public SoapClientHelper() {
	return;
    }
    

    /**
     * 
     * @param serviceId
     * @param soapBody
     * @return
     * @throws SoapRequestException
     */
    public static final String createRequest(String serviceId, String soapBody) throws SoapRequestException {
	if (soapBody == null || soapBody.length() == 0) {
	    String message = "Creation of SOAP request failed due to SOAP body String is null or invalid";
	    logger.log(Level.ERROR, message);
	    throw new SoapRequestException(message);
	}
	// Create enclosing tag name for request body
	String outerTagName = serviceId + "_UserRequest";
	// Enclose main SOAP body within outer tag
	StringBuffer xmlBody = new StringBuffer();
	xmlBody.append("<");
	xmlBody.append(outerTagName);
	xmlBody.append(">");
	xmlBody.append(soapBody);
	xmlBody.append("</");
	xmlBody.append(outerTagName);
	xmlBody.append(">");
	
	// Construct SOAP request as an object
	return SoapClientHelper.setupStringEnvelope(serviceId, xmlBody.toString());
    }

    /**
     * 
     * @param serviceId
     * @param soapBody
     * @return
     * @throws SoapRequestException
     */
    public static final String createResponse(String serviceId, String soapBody) throws SoapRequestException {
	if (soapBody == null || soapBody.length() == 0) {
	    String message = "Creation of SOAP request failed due to SOAP body String is null or invalid";
	    Logger.getLogger(SoapClientHelper.class).log(Level.ERROR, message);
	    throw new SoapRequestException(message);
	}
	// Create enclosing tag name for request body
	String outerTagName = serviceId + "_UserResponse";
	// Enclose main SOAP body within outer tag
	StringBuffer xmlBody = new StringBuffer();
	xmlBody.append("<");
	xmlBody.append(outerTagName);
	xmlBody.append(">");
	xmlBody.append(soapBody);
	xmlBody.append("</");
	xmlBody.append(outerTagName);
	xmlBody.append(">");
	
	// Construct SOAP request as an object
	return SoapClientHelper.setupStringEnvelope(serviceId, xmlBody.toString());
    }
    
    
    /**
     * 
     * @param serviceId
     * @param soapBody
     * @return
     * @throws SoapRequestException
     */
    private static final String setupStringEnvelope(String serviceId, String soapBody) throws SoapRequestException {
	SOAPMessage sm = null;
	SoapProductBuilder builder = SoapResourceFactory.getSoapBuilderInstance();
	Product xmlProd = null;
	try {
	    builder.addHeaderElement("serviceId", serviceId, null, true, null);
	    builder.addBody(soapBody);
	    xmlProd = ProductDirector.construct(builder);
	    sm = (SOAPMessage) xmlProd.toObject();
	}
	catch (Exception e) {
	    throw new SoapRequestException(e);
	}
	
	// Convert the SOAP message instance to String form
	String xml = null;
	try {
	    builder = SoapResourceFactory.getSoapBuilderInstance(sm);
	    xmlProd = ProductDirector.deConstruct(builder);
	    xml = xmlProd.toString();
	}
	catch (Exception e) {
	    throw new SoapRequestException(e);
	}
	return xml;
    }
}

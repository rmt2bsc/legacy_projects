package com.api.messaging.webservice.soap;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import javax.xml.transform.Source;

import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.w3c.dom.Document;

import com.api.Product;
import com.api.ProductBuilderException;
import com.api.ProductDirector;

import com.api.messaging.MessageBinder;
import com.api.messaging.MessageException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.http.HttpConstants;
import com.api.messaging.webservice.soap.client.SoapBuilderException;
import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.client.SoapClientFactory;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.RMT2Base;

import com.constants.GeneralConst;
import com.controller.Request;
import com.controller.Response;

import com.util.InvalidDataException;
import com.util.NotFoundException;
import com.util.RMT2File;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * Contains common SOAP Messaging functionality that both the SOAP client 
 * and server processes can benefit from.
 *  
 * @author appdev
 *
 */
public class SoapMessageHelper extends RMT2Base {

    private static Logger logger = Logger.getLogger(SoapMessageHelper.class);

    /**
     * 
     */
    public SoapMessageHelper() {
        super();
    }

    /**
     * 
     * @param serviceId
     * @param soapBody
     * @return
     * @throws SoapRequestException
     */
    public String createRequest(String serviceId, String targetAction, String soapBody) throws SoapRequestException {
        if (serviceId == null || serviceId.length() == 0) {
            String message = "Creation of SOAP request failed due to SOAP Service Id is null or invalid";
            logger.log(Level.ERROR, message);
            throw new SoapRequestException(message);
        }
        if (soapBody == null || soapBody.length() == 0) {
            String message = "Creation of SOAP request failed due to SOAP body is null or invalid";
            logger.log(Level.ERROR, message);
            throw new SoapRequestException(message);
        }

        // Construct SOAP request as an object
        try {
            return this.setupEnvelopeString(serviceId, targetAction, soapBody);
        }
        catch (SoapBuilderException e) {
            throw new SoapRequestException(e);
        }
    }

    /**
     * 
     * @param serviceId
     * @param soapBody
     * @return
     * @throws SoapResponseException
     */
    public String createResponse(String serviceId, String soapBody) throws SoapResponseException {
        if (serviceId == null || serviceId.length() == 0) {
            String message = "Creation of SOAP response failed due to SOAP Service Id is null or invalid";
            logger.error(message);
            throw new SoapResponseException(message);
        }
        if (soapBody == null || soapBody.length() == 0) {
            String message = "Creation of SOAP response failed due to SOAP XML body is null or invalid";
            logger.log(Level.ERROR, message);
            throw new SoapResponseException(message);
        }

        // Construct SOAP response as an object
        try {
            return this.setupEnvelopeString(serviceId, null, soapBody);
        }
        catch (SoapBuilderException e) {
            throw new SoapResponseException(e);
        }
    }

    /**
     * Uses the service id and the raw XML to construct a SOAP message as a String.
     * 
     * @param serviceId
     * @param soapBody
     * @return
     * @throws SoapBuilderException
     */
    private String setupEnvelopeString(String serviceId, String targetAction, String soapBody) throws SoapBuilderException {
        // Convert the SOAP message instance to String form
        String xml = null;
        try {
            SOAPMessage sm = this.setupEnvelopeInstance(serviceId, targetAction, soapBody);
            
            // Deconstruct revised SOAP Message instance,  that now contains the payload , back to a String.
            SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance();
            Product xmlProd = null;
            builder = SoapClientFactory.getSoapBuilderInstance(sm);
            xmlProd = ProductDirector.deConstruct(builder);
            xml = xmlProd.toString();
        }
        catch (Exception e) {
            throw new SoapBuilderException(e);
        }
        return xml;
    }

    /**
     * Uses the service id and the raw XML to construct a SOAP message instance.
     * 
     * @param serviceId
     * @param soapBody
     * @return
     * @throws SoapBuilderException
     */
    private SOAPMessage setupEnvelopeInstance(String serviceId, String targetAction, String soapBody) throws SoapBuilderException {
        SOAPMessage sm = null;
        SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance();
        Product xmlProd = null;
        try {
            builder.addHeaderElement("serviceId", serviceId, null, true, null);
            if (targetAction != null) {
                builder.addHeaderElement("target_action", targetAction, null, false, null);
            }
            builder.addBody(soapBody);
            xmlProd = ProductDirector.construct(builder);
            sm = (SOAPMessage) xmlProd.toObject();
            return sm;
        }
        catch (Exception e) {
            throw new SoapBuilderException(e);
        }
    }

    /**
     * Get SOAP message as a String from the Object Input Stream.
     * 
     * @param in
     * @return
     */
    public String getSoapRequest(InputStream in) {
        // Get SOAP message as a String from the Object Input Stream of the request
        String soapXml = null;
        try {
            soapXml = RMT2File.getStreamStringData(in);
            SoapMessageHelper.logger.log(Level.DEBUG, "Size of SOAP Message String: " + soapXml.length());
            return soapXml;
        }
        catch (Exception e) {
            msg = e.getMessage();
            logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Verify that XML document String is not null and it is actually a soap message
     * 
     * @param soapXml
     * @return SOAPMessage
     * @throws InvalidDataException
     */
    public SOAPMessage verifySoapMessage(String soapXml) throws InvalidDataException {
        String msg;
        if (soapXml == null) {
            msg = "Parameter, " + GeneralConst.REQ_SOAPMESSAGE + ", is invalid or was not found in user's request";
            logger.log(Level.ERROR, msg);
            throw new InvalidDataException(msg);
        }
        // Test if SOAPMessage instance can be created from the incoming message.
        try {
            SOAPMessage sm = null;
            SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance(soapXml);
            Product xmlProd = ProductDirector.construct(builder);
            sm = (SOAPMessage) xmlProd.toObject();
            sm.getSOAPPart().getEnvelope();
            return sm;
        }
        catch (SOAPException e) {
            throw new InvalidDataException(e);
        }
        catch (ProductBuilderException e) {
            throw new InvalidDataException(e);
        }
    }

    /**
     * Obtains the value of an SOAP header element by header element name.
     *  
     * @param targetElementName
     *          The element name to search.  Can be a qualified or unqualified header name.
     * @param soapObj
     *          the SOAPMessage instance containing the headers to search
     * @return String
     *          the value of the header element
     * @throws SOAPException
     *          if a SOAP error occurs
     * @throws NotFoundException
     *          if targetElementName does not exist.
     */
    public String getHeaderValue(String targetElementName, SOAPMessage soapObj) throws SOAPException {
        String value = null;
        Iterator<SOAPHeaderElement> iter = soapObj.getSOAPHeader().examineAllHeaderElements();
        while (iter.hasNext()) {
            SOAPHeaderElement item = iter.next();
            Name n = item.getElementName();
            String elementName = n.getQualifiedName();
            // Try qualified name first.
            if (targetElementName.equals(elementName)) {
                value = item.getTextContent();
                return value;
            }
            // Look for unqualified element name
            String splitHeadName[] = elementName.split(":");
            if (splitHeadName != null && splitHeadName.length == 2) {
                String unqualElementName = splitHeadName[1];
                if (targetElementName.equals(unqualElementName)) {
                    value = item.getTextContent();
                    return value;
                }
            }
        }
        throw new NotFoundException("Value for SOAP Header element, " + targetElementName + ", was not found");
    }

    /**
     * Helper method for creating a SOAP Fault message
     * 
     * @param code
     * @param msgTxt
     * @param actor
     * @param details
     * @return
     */
    public SOAPMessage createSoapFault(String code, String msgTxt, String actor, Map<String, String> details) {
        try {
            SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance(null, null, code, msgTxt, actor, details);
            Product xmlProd = ProductDirector.construct(builder);
            return (SOAPMessage) xmlProd.toObject();
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
    }

    /**
     * Gets the SOAP message contained in the user's request.
     * 
     * @param request
     *          the user's request
     * @return String
     *          the SOAP message in String format.
     *  
     */
    public String getSoapRequest(HttpServletRequest request) {
        return request.getParameter(HttpConstants.XML_PAYLOAD);
    }

    /**
     * Creates a XML String from an instance of SOAPMessage.
     * 
     * @param soapObj
     * @return
     * @throws SoapBuilderException
     */
    public String toString(SOAPMessage soapObj) throws SoapBuilderException {
        String xml;
        try {
            xml = this.getSoap(soapObj);
            return xml;
        }
        catch (SoapProcessorException e) {
            throw new SoapBuilderException(e);
        }
    }

    /**
     * Creates a SOAPMessage instance from a String SOAP message.
     * 
     * @param soapMessage
     * @return
     * @throws SoapBuilderException
     */
    public SOAPMessage toInstance(String soapMessage) throws SoapBuilderException {
        SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance(soapMessage);
        Product xmlProd;
        try {
            xmlProd = ProductDirector.construct(builder);
            SOAPMessage sm = (SOAPMessage) xmlProd.toObject();
            return sm;
        }
        catch (ProductBuilderException e) {
            throw new SoapBuilderException(e);
        }
    }

    /**
     * Creates a byte array from an instance of SOAPMessage
     * 
     * @param obj
     * @return
     * @throws SoapBuilderException
     */
    public byte[] toBytes(SOAPMessage obj) throws SoapBuilderException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        byte data[] = null;
        try {
            obj.writeTo(baos);
            data = baos.toByteArray();
            return data;
        }
        catch (SOAPException e) {
            logger.error("SOAPException: " + e.getMessage());
            throw new SoapBuilderException(e);
        }
        catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
            throw new SoapBuilderException(e);
        }
    }

    /**
     * Returns the entire SOAP envelope message instance as a String.
     * 
     * @param soapObj
     * @return
     * @throws SoapProcessorException
     */
    public String getSoap(SOAPMessage soapObj) throws SoapProcessorException {
        String msg = null;
        String xml = null;
        RMT2XmlUtility xmlUtil = RMT2XmlUtility.getInstance();
        try {
            Source source = soapObj.getSOAPPart().getContent();
            xml = xmlUtil.transform(source);
            return xml;
        }
        catch (SOAPException e) {
            msg = "SOAPException while parsing soap body to string: " + e.getMessage();
            logger.error(msg);
            logger.debug("Stack Trace: ", e);
            throw new SoapProcessorException(e);
        }
    }

    /**
     * Obtains the payload of the SOAP message as an instance.
     * 
     * @param soapObj
     *         the SOAP message instance.
     * @return
     *         a generic object repsenting the SOAP body.   User is responsible for applying proper casting.
     * @throws SoapProcessorException
     */
    public Object getBodyInstance(SOAPMessage soapObj) throws SoapProcessorException {
        SoapMessageHelper helper = new SoapMessageHelper();
        String payload = helper.getBody(soapObj);
        MessageBinder binder = ResourceFactory.getJaxbMessageBinder();
        Object jaxbPayload = binder.unMarshalMessage(payload);
        return jaxbPayload;
    }

    /**
     * Returns the body of the SOAP envelope instance as a String.
     *  
     * @param soapObj
     *          the SOAP message instance.
     * @return String
     *          the raw XML data which serves as the payload of the SOAP message.
     * @throws SoapProcessorException
     */
    public String getBody(SOAPMessage soapObj) throws SoapProcessorException {
        String msg = null;
        String strBody = null;
        RMT2XmlUtility xmlUtil = RMT2XmlUtility.getInstance();
        try {
            SOAPBody body = soapObj.getSOAPBody();
            Document doc = body.extractContentAsDocument();
            Source source = new DOMSource(doc);
            strBody = xmlUtil.transform(source);
            return strBody;
        }
        catch (SOAPException e) {
            msg = "SOAPException while parsing soap body to string: " + e.getMessage();
            logger.error(msg);
            logger.debug("Stack Trace: ", e);
            throw new SoapProcessorException(e);
        }
    }

    /**
     * Determines if SOAP message contains a Fault
     * 
     * @param soapObj
     * @return
     */
    public boolean isError(SOAPMessage soapObj) {
        try {
            return (soapObj.getSOAPBody().getFault() != null);
        }
        catch (SOAPException e) {
            e.printStackTrace();
            throw new SystemException(e);
        }
    }

    /**
     * Obtains the Fault's message text.
     * 
     * @param soapObj
     * @return
     *       String text when Fault is present or null if Fault does not extis 
     */
    public String getErrorMessage(SOAPMessage soapObj) {
        SOAPFault fault;
        try {
            fault = soapObj.getSOAPBody().getFault();
            if (fault != null) {
                return fault.getFaultString();
            }
            return null;
        }
        catch (SOAPException e) {
            e.printStackTrace();
            throw new SystemException(e);
        }
    }

    /**
     * Get SOAP message object from String.
     * 
     * @param msg
     * @return
     * @throws MessageException
     */
    public SOAPMessage getSoapInstance(String msg) throws MessageException {
        return this.toInstance(msg);
    }

    /**
     * Get SOAP message object from the Input Stream of the user's request.
     * 
     * @param request
     * @return
     * @throws MessageException
     */
    public SOAPMessage getSoapInstance(Request request) throws SoapRequestException {
        System.out.println(request.getContentType());
        Enumeration<Object> en = request.getHeaderNames();
        MimeHeaders mh = new MimeHeaders();
        while (en.hasMoreElements()) {
            String name = en.nextElement().toString();
            String val = request.getHeader(name);
            mh.addHeader(name, val);
        }
        SOAPMessage sm = null;
        try {
            MessageFactory f = MessageFactory.newInstance();
            sm = f.createMessage(mh, request.getInputStream());
            logger.info("Total SOAP Attachments found: " + sm.countAttachments());
            return sm;
        }
        catch (Exception e) {
            throw new SoapRequestException(e);
        }
    }

    /**
     * Creates a SOAPMessage instance using a raw XML String and with zero or more SOAP Attachments.
     * 
     * @param msg
     *         The raw XML to be used as the body of the SOAP message.
     * @param attachments
     *         a list of one or more attachments
     * @return
     *      an instance of SOAP MEssage instance.
     * @throws MessageException
     */
    public SOAPMessage getSoapInstance(Serializable msg, List<Object> attachments) throws MessageException {
        String rawXml = null;
        if (msg instanceof String) {
            rawXml = msg.toString();
        }
        else {
            this.msg = "SOAP message send opertaion failed.  Incoming SOAP message must be a Serializable String";
            logger.error(this.msg);
            throw new MessageException(this.msg);
        }
        try {
            SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance(rawXml);
            Product xmlProd = ProductDirector.construct(builder);
            builder.createAttachments(attachments);
            SOAPMessage sm = (SOAPMessage) xmlProd.toObject();
            return sm;
        }
        catch (Exception e) {
            throw new MessageException(e);
        }
    }

    /**
     * Sends an instance of SOAPMessage from one processes to another process as a byte stream.
     * 
     * @param response
     * @param soapMsg
     * @throws SoapResponseException
     */
    public void sendSoapInstance(Response response, SOAPMessage soapMsg) throws SoapResponseException {
        try {
            // This condition is critical in enabling the receiving process to 
            // created the SOAPMessage instance from the request's input stream correctly.
            if (soapMsg.saveRequired()) {
                soapMsg.saveChanges();
            }

            // Identify and setup response headers
            MimeHeaders headers = soapMsg.getMimeHeaders();
            Iterator<MimeHeader> it = headers.getAllHeaders();
            boolean hasAuth = false; // true if we find explicit Auth header
            while (it.hasNext()) {
                MimeHeader header = it.next();

                String[] values = headers.getHeader(header.getName());

                if (values.length == 1) {
                    response.setHeader(header.getName(), header.getValue());
                }
                else {
                    StringBuffer concat = new StringBuffer();
                    int i = 0;
                    while (i < values.length) {
                        if (i != 0)
                            concat.append(',');
                        concat.append(values[i]);
                        i++;
                    }
                    response.setHeader(header.getName(), header.getValue());
                }

                if ("Authorization".equals(header.getName())) {
                    hasAuth = true;
                    logger.info("SAAJ0091.p2p.https.auth.in.POST.true");
                }
                if (hasAuth) {

                }
            }
            OutputStream os = (OutputStream) response.getOutputStream();
            DataOutputStream out = new DataOutputStream(os);
            soapMsg.writeTo(out);
            out.flush();
            out.close();
            return;
        }
        catch (Exception e) {
            throw new SoapResponseException(e);
        }
    }

}

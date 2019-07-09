package com.remoteservices.http;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.RMT2Base;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * This is a SOAP implementation of the ServiceMessageBuilder interface.  Basically, it 
 * builds a SOAP envelope including the header, body and fault elements, and 
 * encapsulates the raw XML document produced from some web service as the body 
 * of the SOAP messaege. 
 * 
 * @author RTerrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 *
 */
class SOAPMessageImpl extends RMT2Base implements ServiceMessageBuilder {
    private Logger logger;

    private Object inputXml;

    private String faultcode;

    private String faultstring;

    private String faultactor;

    private String faultdetail;

    /**
     * Creates a SOAPMessageImpl object
     *
     */
    protected SOAPMessageImpl() {
        super();
        this.logger = Logger.getLogger("SOAPMessageImpl");
    }

    /**
     * Builds the message suitable for transporting between disparate systems.
     * 
     * @return The SOAP message which encapsulates the XML body returned from 
     *         the service producer.
     * @throws HttpException
     */
    public Object build(Object message) throws HttpException {
        this.inputXml = message;
        try {
            this.validate();
        }
        catch (Exception e) {
            faultcode = "soap:Client";
        }

        return this.buildSOAPEnvelope();
    }

    /**
     * Converts the message to String form.
     *  
     * @return String
     */
    public String toString() {
        return super.toString();
    }

    /**
     * Determines if the document is well-formed.
     * 
     * @throws HttpException When document is not well-formed.
     */
    public void validate() throws HttpException {
        if (this.inputXml == null) {
            this.msg = "XML input data is null";
            this.logger.log(Level.ERROR, this.msg);
            throw new HttpException(this.msg);
        }

        try {
            RMT2XmlUtility xmlUtil = RMT2XmlUtility.getInstance();
            xmlUtil.checkDocument(this.inputXml.toString(), false);
        }
        catch (SystemException e) {
            throw new HttpException(e);
        }
    }

    private String buildSOAPEnvelope() {
        StringBuffer buf = new StringBuffer(100);

        // Start the envelope
        buf.append("<?xml version=\"1.0\"?>");
        buf.append("<soap:Envelope ");
        buf.append("xmlns:soap=\"http://www.w3.org/2001/12/soap-envelope\" ");
        buf.append("soap:encodingStyle=\"http://www.w3.org/2001/12/soap-encoding\"> ");

        // Build the header;
        buf.append(this.getHeader());

        // Build the body
        buf.append(this.getBody());

        buf.append("</soap:Envelope>");
        return buf.toString();

    }

    /**
     * Builds the SOAP header.
     * 
     * @return String as the SOAP header.
     */
    private String getHeader() {
        long transId = new java.util.Date().getTime();
        StringBuffer buf = new StringBuffer(100);
        buf.append("<soap:Header>");
        buf.append("<m:Trans ");
        buf.append("xmlns:m=\"http://www.rmt2.net/transaction/\" ");
        buf.append("soap:actor=\"http://www.w3schools.com/appml/\"> ");
        buf.append(transId);
        buf.append("</m:Trans>");
        buf.append("</soap:Header>");
        return buf.toString();
    }

    /**
     * Builds the SOAP body which may or may not include the Fault element.
     * 
     * @return String as the SOAP body.
     */
    private String getBody() {
        StringBuffer buf = new StringBuffer(100);
        buf.append("<soap:Body>");
        buf.append(this.inputXml.toString());
        buf.append(this.getFault());
        buf.append("</soap:Body>");
        return buf.toString();
    }

    /**
     * Builds the SOAP fault.
     * 
     * @return String as the fault when an error exist or empty String when 
     *         a error does not exist.
     */
    private String getFault() {
        if (this.faultcode == null) {
            return "";
        }
        StringBuffer buf = new StringBuffer(100);
        buf.append("<soap:Fault>");
        buf.append("<faultcode>");
        buf.append(this.faultcode);
        buf.append("</faultcode>");

        buf.append("<faultstring>");
        buf.append(this.faultstring);
        buf.append("</faultstring>");

        buf.append("<faultactor>");
        buf.append(this.faultactor);
        buf.append("</faultactor>");

        buf.append("<detail>");
        buf.append(this.faultdetail);
        buf.append("</detail>");

        buf.append("</soap:Fault>");
        return buf.toString();
    }
}

package com.api.messaging.webservice.soap;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.InputStream;
import java.io.Serializable;

import java.net.MalformedURLException;
import java.net.URL;

import com.api.Product;
import com.api.ProductBuilderException;
import com.api.ProductDirector;

import com.api.messaging.AbstractMessagingImpl;
import com.api.messaging.MessageException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ProviderConnectionException;

import com.api.messaging.webservice.http.HttpClient;

import com.util.RMT2File;
import com.util.SystemException;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;


/**
 * Functions to send one-way SOAP messages from one endpoint to another endopoint 
 * using a JMS provider, hence msessages can be sent in asynchronously.
 * 
 * @author rterrell
 * 
 */
class SoapAsyncTransportImpl extends AbstractSoapClientImpl implements SoapClient {

    private static Logger logger = Logger.getLogger(SoapAsyncTransportImpl.class);

    private SOAPConnection connection;
    
    private SOAPMessage soapMessage;

    private URL endpoint;
    
    private ProviderConfig config;

    /**
     * 
     */
    protected SoapAsyncTransportImpl() throws SOAPException {
	super();
	return;
    }


    /**
     * 
     * @param config
     * @return
     * @throws ProviderConnectionException
     */
    public SOAPConnection connect(ProviderConfig config) throws ProviderConnectionException {
	this.config = config;
	try {
	    SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
	    this.connection = sfc.createConnection();
	    this.endpoint = new URL(config.getHost());
	    return this.connection;
	}
	catch (SOAPException e) {
	    throw new ProviderConnectionException(e);
	}
	catch (MalformedURLException e) {
	    this.msg = "Problem establishing a SOAP connection to host.  The following URL is malformed or incorrect: " + config.getHost();
	    SoapAsyncTransportImpl.logger.log(Level.ERROR, this.msg);
	    throw new ProviderConnectionException(this.msg);
	}
    }
    
    /**
     * 
     * @throws SystemException
     */
    public void close() throws SystemException {
	try {
	    this.connection.close();
	    this.connection = null;
	    this.endpoint = null;
	    return;
	}
	catch (Exception e) {
	    throw new SystemException(e);
	}
    }
    
    /**
     * 
     * @param data
     * @return
     * @throws MessageException
     */
    public SOAPMessage sendMessage(Serializable data) throws MessageException {
	String xml = null;
	if (data instanceof String) {
	    xml = data.toString();
	}
	else {
	    this.msg = "SOAP message send opertaion failed.  Incoming SOAP message must be a Serializable String";
	    SoapAsyncTransportImpl.logger.log(Level.ERROR, this.msg);
	    throw new MessageException(this.msg);
	}
	try {
	    SoapProductBuilder builder = SoapResourceFactory.getSoapBuilderInstance(xml);
	    Product xmlProd = ProductDirector.construct(builder);
	    SOAPMessage sm = (SOAPMessage) xmlProd.toObject();
	    SOAPMessage response = this.connection.call(sm, this.endpoint);
	    this.soapMessage = response;
	    return response;
	}
	catch (Exception e) {
	    throw new SoapBuilderException(e);
	}
    }
    
    /**
     * Sends a SOAP message via the HttpClient instance.
     * Currently not used.   Will put to use in the near future.
     * 
     * @param data
     *          the XML to transport and process.
     * @return SOAPMessage
     * @throws MessageException
     */
    public SOAPMessage sendStringMessage(Serializable data) throws MessageException {
	try {
	    HttpClient client = new HttpClient(config.getHost());
	    InputStream in = client.sendPostMessage(data);
	    String responseXml = RMT2File.getStreamStringData(in);
	    
	    // Deserialize response
	    SoapProductBuilder builder = SoapResourceFactory.getSoapBuilderInstance(responseXml);
	    Product xmlProd = ProductDirector.construct(builder);
	    this.soapMessage = (SOAPMessage) xmlProd.toObject();
	    return this.soapMessage;
	}
	catch (Exception e) {
	    throw new SoapBuilderException(e);
	}
    }

    /**
     * 
     * @return
     * @throws MessageException
     */
    public String getMessage() throws MessageException {
	if (this.soapMessage == null) {
	    return null;
	}
	try {
	    SoapProductBuilder builder = SoapResourceFactory.getSoapBuilderInstance(this.soapMessage);
	    Product xmlProd = ProductDirector.deConstruct(builder);
	    return xmlProd.toString();
	}
	catch (ProductBuilderException e) {
	    throw new MessageException(e);
	}
    }


    /* (non-Javadoc)
     * @see com.api.messaging.MessageManager#getConfig()
     */
    public ProviderConfig getConfig() throws MessageException {
	return this.config;
    }

    /* (non-Javadoc)
     * @see com.api.messaging.MessageManager#getHost()
     */
    public String getHost() {
	return this.config.getHost();
    }

    /* (non-Javadoc)
     * @see com.api.messaging.MessageManager#isAuthRequired()
     */
    public boolean isAuthRequired() {
	throw new UnsupportedOperationException("isAuthRequired method is not supported at this time");
    }

}

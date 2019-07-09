package com.services;



import javax.xml.soap.SOAPMessage;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.JdbcFactory;
import com.api.messaging.MessageException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapClientWrapper;

import com.util.RMT2File;
import com.xml.schema.bindings.CustomerCriteriaType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.IpCriteriaType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQCustomerSearch;
import com.xml.schema.bindings.RQPostalSearch;
import com.xml.schema.bindings.RSCustomerSearch;
import com.xml.schema.bindings.RSPostalSearch;

import com.xml.schema.misc.PayloadFactory;

/**
 * @author SG0903711
 *
 */
public class WebServicesTest {
    
//    private static final String SOAP_HOST = "http://localhost:7777/ServiceDispatch/soapRouter";
//    private static String SOAP_HOST = "http://localhost:8080/ServiceDispatch/soapRouter";
    private static String SOAP_HOST;
    
    private ObjectFactory f;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        RMT2File.loadSystemProperties("TestSystemParms");
        SOAP_HOST = System.getProperty("soaphost");
	f = new ObjectFactory();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

  
    
    @Test
    public void testCustomerFetchAllWebService() {
	SoapClientWrapper client = new SoapClientWrapper();
	// This condition is for the test environment since configuration data is 
	// loaded at server start up and this test does not execute within the same 
	// JVM as the Web Server
	ProviderConfig config = ResourceFactory.getSoapConfigInstance();
	if (config.getHost() == null) {
	    config.setHost(SOAP_HOST);
	}
	try {
	    client.setConfig(config);
	    RQCustomerSearch ws = f.createRQCustomerSearch();
	    CustomerCriteriaType cct = f.createCustomerCriteriaType();
	    HeaderType header = PayloadFactory.createHeader("RQ_customer_search", "SYNC", "REQUEST", "rterrell");
	    ws.setHeader(header);
	    ws.setCriteriaData(cct);
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		System.out.println(errMsg);
		return;
	    }
	    RSCustomerSearch soapResp = (RSCustomerSearch) client.getSoapResponsePayload();
	    msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
	}
	catch (MessageException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}

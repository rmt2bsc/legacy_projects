package com.services;



import javax.xml.soap.SOAPMessage;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.messaging.MessageException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapClientWrapper;

import com.xml.schema.bindings.CountryCriteriaType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.IpCriteriaType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQPostalSearch;
import com.xml.schema.bindings.RSPostalSearch;

import com.xml.schema.misc.PayloadFactory;

/**
 * @author SG0903711
 *
 */
public class WebServicesTest {
//    private static final String SOAP_HOST = "http://localhost:7777/ServiceDispatch/soapRouter";
    private static final String SOAP_HOST = "http://localhost:8080/ServiceDispatch/soapRouter";
    
    private ObjectFactory f;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	f = new ObjectFactory();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPostalChoiceSelectionCriteria() {
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
	    RQPostalSearch ws = f.createRQPostalSearch();
	    IpCriteriaType ict = f.createIpCriteriaType();
	    HeaderType header = PayloadFactory.createHeader("RQ_postal_search", "SYNC", "REQUEST", "rterrell");
	    header.setTargetAction("IP_CRITERIA");
	    ws.setHeader(header);
	    ict.setIpStandard("71.252.210.138");
	    RQPostalSearch.PostalCriteria c = f.createRQPostalSearchPostalCriteria();
	    ws.setPostalCriteria(c);
	    ws.getPostalCriteria().setIpAddr(ict);
	    
	    CountryCriteriaType cct = f.createCountryCriteriaType();
	    ws.getPostalCriteria().setCountry(cct);
	    
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    Assert.assertNotNull(msg);
	}
	catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    @Test
    public void testIpAddressFetchWebService() {
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
	    RQPostalSearch ws = f.createRQPostalSearch();
	    IpCriteriaType ict = f.createIpCriteriaType();
	    HeaderType header = PayloadFactory.createHeader("RQ_postal_search", "SYNC", "REQUEST", "rterrell");
	    header.setTargetAction("IP_CRITERIA");
	    ws.setHeader(header);
	    ict.setIpStandard("71.252.210.138");
	    RQPostalSearch.PostalCriteria c = f.createRQPostalSearchPostalCriteria();
	    ws.setPostalCriteria(c);
	    ws.getPostalCriteria().setIpAddr(ict);
//	    ws.setIpCriteria(ict);
//	    ws.setTargetCriteria("IP_CRITERIA");
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		System.out.println(errMsg);
		return;
	    }
	    RSPostalSearch soapResp = (RSPostalSearch) client.getSoapResponsePayload();
	    msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
	    Assert.assertNotNull(msg);
	}
	catch (MessageException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}

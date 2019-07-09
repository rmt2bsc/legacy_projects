package ws;

import java.math.BigInteger;

import javax.xml.soap.SOAPMessage;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.messaging.ProviderConfig;
import com.api.messaging.ResourceFactory;
import com.api.messaging.webservice.soap.client.SoapClientWrapper;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQContentSearch;
import com.xml.schema.bindings.RSContentSearch;

import com.xml.schema.misc.PayloadFactory;



public class ContentWSTest {
    private static final String SOAP_HOST = "http://localhost:8080/ServiceDispatch/soapRouter";

    //        private static final String SOAP_HOST = "http://s90371130040682:7777/ServiceDispatch/soapRouter";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testContentFound() {
	ObjectFactory f = new ObjectFactory();
	// Create Payload instance
	RQContentSearch ws = f.createRQContentSearch();
	HeaderType header = PayloadFactory.createHeader("RQ_content_search", "SYNC", "REQUEST", "rterrell");
	ws.setHeader(header);
	ws.setContentId(BigInteger.valueOf(283));
	String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);

	SoapClientWrapper client = new SoapClientWrapper();
	// This condition is for the test environment since configuration data is 
	// loaded at server start up and this test does not execute within the same 
	// JVM as the Web Server
	ProviderConfig config = ResourceFactory.getSoapConfigInstance();
	if (config.getHost() == null) {
	    config.setHost(ContentWSTest.SOAP_HOST);
	}
	try {
	    client.setConfig(config);
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		throw new Exception(errMsg);
	    }
	    
	    RSContentSearch content = (RSContentSearch) client.getSoapResponsePayload();
	    Assert.assertTrue(content.getContent().size() > 0);
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
    @Test
    public void testContentNotFound() {
	ObjectFactory f = new ObjectFactory();
	// Create Payload instance
	RQContentSearch ws = f.createRQContentSearch();
	HeaderType header = PayloadFactory.createHeader("RQ_content_search", "SYNC", "REQUEST", "rterrell");
	ws.setHeader(header);
	ws.setContentId(BigInteger.valueOf(9999));
	String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);

	SoapClientWrapper client = new SoapClientWrapper();
	// This condition is for the test environment since configuration data is 
	// loaded at server start up and this test does not execute within the same 
	// JVM as the Web Server
	ProviderConfig config = ResourceFactory.getSoapConfigInstance();
	if (config.getHost() == null) {
	    config.setHost(ContentWSTest.SOAP_HOST);
	}
	try {
	    client.setConfig(config);
	    SOAPMessage resp = client.callSoap(msg);
	    Assert.assertTrue(client.isSoapError(resp));
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }
}

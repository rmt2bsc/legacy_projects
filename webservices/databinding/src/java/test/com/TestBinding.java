package com;

import org.apache.log4j.Logger;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.messaging.MessageBinder;
import com.api.messaging.ResourceFactory;

import com.xml.schema.bindings.MimeContentType;
import com.xml.schema.bindings.MimeType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RSContentSearch;

/**
 * @author appdev
 *
 */
public class TestBinding {
    private static Logger logger = Logger.getLogger(TestBinding.class);
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    
    
    @Test
    public void testBinding() {
	ObjectFactory factory = new ObjectFactory();
	MimeContentType mime = factory.createMimeContentType();
	mime.setContentId(BigInteger.valueOf(123));
	MimeType mt = factory.createMimeType();
	mt.setMimeTypeId(BigInteger.valueOf(12));
	mt.setFileExt(".aif");
	mt.setMediaType("audio/x-aiff");

	mime.setAppCode("accounting");
	mime.setModuleCode("AR");
	mime.setFilename("testfile.jpg");
	mime.setFilepath("c:/tmp/");
	mime.setFilesize(BigInteger.valueOf(8432938));
	mime.setTextData("kjfldsajkfdsakdkfjdlsajfkdlsjakfdjsakfdsakdjf;dsjkdds");
	
	RSContentSearch resp = factory.createRSContentSearch();
	resp.getContent().add(mime);
	
	MessageBinder b = ResourceFactory.getJaxbMessageBinder();
	String xml = b.marshalMessage(resp);
	
	Object obj = b.unMarshalMessage(xml);
	TestBinding.logger.debug(obj.getClass().getName());
	
    }
    
//    @Test
//    public void testRequestBinding() {
//	ObjectFactory factory = new ObjectFactory();
//	RequestMessage req = factory.createRequestMessage();
//	HeaderType ht = factory.createHeaderType();
//	ht.setMessageId("test.message");
//	ht.setDeliveryMode("SYNC");
//	ht.setDeliveryDate("6/23/2009");
//	ht.setMessageMode("REQUEST");
//	ht.setSessionId("jkfdsla9r0932043kjfjds34032jkfds");
//	ht.setUserId("rterrell");
//	req.setHeader(ht);
//	req.setPayload("<test><fname>roy</fname><lnamme>terrell</lname></test>");
//	
//	
//	MessageBinder b = ResourceFactory.getJaxbMessageBinder("com.xml.schema.bindings");
//	String xml = b.marshalMessage(req);
//	
//	Object obj = b.unMarshalMessage(xml);
//	TestBinding.logger.debug(obj.getClass().getName());
//	
//    }
//    
//    @Test
//    public void testResponseBinding() {
//	ObjectFactory factory = new ObjectFactory();
//	ResponseMessage req = factory.createResponseMessage();
//	HeaderType ht = factory.createHeaderType();
//	ReplyStatusType rst = factory.createReplyStatusType();
//	
//	ht.setMessageId("test.message");
//	ht.setDeliveryMode("SYNC");
//	ht.setDeliveryDate("6/23/2009");
//	ht.setMessageMode("RESPONSE");
//	ht.setSessionId("jkfdsla9r0932043kjfjds34032jkfds");
//	ht.setUserId("rterrell");
//	
//	rst.setReturnStatus("SUCCESS");
//	rst.setMessage(factory.createReplyStatusTypeMessage("Input failure"));
//	rst.setExtMessage(factory.createReplyStatusTypeExtMessage("User first name does not exist"));
//	rst.setReturnCode(factory.createReplyStatusTypeReturnCode(BigInteger.valueOf(-120)));
//	req.setHeader(ht);
//	req.setReplyStatus(rst);
//	req.setPayload("<test><fname>roy</fname><lnamme>terrell</lname></test>");
//	
//	MessageBinder b = ResourceFactory.getJaxbMessageBinder("com.xml.schema.bindings");
//	String xml = b.marshalMessage(req);
//		
//	Object obj = b.unMarshalMessage(xml);
//	TestBinding.logger.debug(obj.getClass().getName());
//    }
    
    
    
  
}

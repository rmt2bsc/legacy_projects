package com.messaging.jms;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.api.messaging.MessageException;
import com.api.messaging.jms.Consumer;
import com.api.messaging.jms.JmsResourceFactory;
import com.api.messaging.jms.Producer;


/**
 * @author appdev
 *
 */
public class JmsTest {
    private Context context;

    private ConnectionFactory factory;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	System.setProperty("jms.providerurl", "tcp://localhost:3035/");
	System.setProperty("jms.contextclass", "org.exolab.jms.jndi.InitialContextFactory");
	System.setProperty("jms.connectionfactory", "ConnectionFactory");
	//    	System.setProperty("jms.jndisource", "direct");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	System.clearProperty("jms.providerurl");
	System.clearProperty("jms.contextclass");
	System.clearProperty("jms.connectionfactory");
	System.clearProperty("jms.jndisource");
    }

    @Test
    public void testLookup() {
	String lookupVal = null;
	try {
	    // Get JNDI environment from jndi.properties file
	    Properties prop = new Properties();
	    prop.setProperty("java.naming.factory.initial", "com.sun.jndi.fscontext.RefFSContextFactory");
	    prop.setProperty("java.naming.provider.url", "file:/tmp");

	    this.context = new InitialContext();
	    // NamingEnumeration list = this.context.list("");
	    lookupVal = (String) this.context.lookup("authenticator");
	    System.out.println("Authenticator = " + lookupVal);
	}
	catch (NamingException e) {
	    e.printStackTrace();
	}
    }
    
    @Test
    public void testAllInOneAsyncMessage() {
	// Setup context
	Hashtable properties = new Hashtable();
	properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
	properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");

	try {
	    this.context = new InitialContext(properties);
	}
	catch (NamingException e) {
	    e.printStackTrace();
	    return;
	}

	// Obtain Connection factory
	try {
	    this.factory = (ConnectionFactory) this.context.lookup("ConnectionFactory");
	}
	catch (NamingException e) {
	    e.printStackTrace();
	}

	Connection connection = null;
	Session session = null;
	Destination destination = null;
	try {
	    // Setup destination
	    connection = factory.createConnection();
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    destination = (Destination) context.lookup("queue1");

	    // Send message
	    //            connection.start();
	    MessageProducer sender = session.createProducer(destination);
	    TextMessage message = session.createTextMessage("Hello World!");
	    sender.send(message);

	    MessageConsumer receiver = session.createConsumer(destination);
	    connection.start();

	    TextMessage message2 = (TextMessage) receiver.receive();
	    System.out.println("Received message: " + message.getText());
	}
	catch (JMSException e) {
	    e.printStackTrace();
	}
	catch (NamingException e) {
	    e.printStackTrace();
	}
    }
    
    
    @Test
    public void testJmsProducer() {
	Producer p = null;
	try {
	    p = JmsResourceFactory.createProducer("queue1");
	    p.sendMessage("This is a test message");
	}
	catch (MessageException e1) {
	    e1.printStackTrace();
	}
	catch (Exception e1) {
	    e1.printStackTrace();
	}
	finally {
	    if (p != null) {
		p.close();
		p = null;
	    }
	}
    }

    @Test
    public void testAsyncJmsConsumer() {
	Consumer c = null;
	try {
	    c = JmsResourceFactory.createConsumer("queue1", null);
	    //			Serializable msg = c.getMessage();
	}
	catch (MessageException e1) {
	    e1.printStackTrace();
	}
	catch (Exception e1) {
	    e1.printStackTrace();
	}
	finally {
	    if (c != null) {
		c.close();
		c = null;
	    }
	}
    }
    
    @Test
    public void testSyncRequestReply() {
	Producer p = null;
	try {
	    p = JmsResourceFactory.createProducer("queue1", "queue2");
	    p.sendMessage("This is a test message");
	    Message reply = p.getReply(10, null);
	    if (reply instanceof ObjectMessage) {
		Object msg = ((ObjectMessage) reply).getObject();
		System.out.println("Request/Reply call: Message was retrieved: " + msg.toString());
	    }
	}
	catch (MessageException e1) {
	    e1.printStackTrace();
	}
	catch (Exception e1) {
	    e1.printStackTrace();
	}
	finally {
	    if (p != null) {
		p.close();
		p = null;
	    }
	}
    }
    
}

package com.api.messaging.jms;

import java.util.*;
import java.io.*;

import javax.jms.*;
import javax.naming.*;

/*
 * The JMSManager class is a facade in that it provides a very simple
 * interface for using JMS.   The destination, whether a topic or queue, is 
 * hidden as an implementation detail. 
 *
 * @author Roy Terrell
 */
public class JMSManager {
    protected static boolean fLog = false;

    // JNDI related data, loaded once for all class instances
    protected static Context jndi = null;

    // Connection to the JMS provider is set up once 
    // for all class instances
    protected static Connection connection = null;

    protected static boolean connected = false;

    // Destinations maintained in this hashtable
    protected Hashtable jmsDestinations = new Hashtable();

    /**
     * Nest class encapsulates JMS Destination objects
     * 
     * @author appdev
     *
     */
    class JMSDestination {
        Destination destination = null;

        Session session = null;

        MessageProducer producer = null;

        MessageConsumer consumer = null;

        public JMSDestination(Destination destination, Session session, MessageProducer producer, MessageConsumer consumer) {
            this.destination = destination;
            this.session = session;
            this.producer = producer;
            this.consumer = consumer;
        }
    }

    public JMSManager() throws Exception {
        this.connectToJMS();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public API methods
    ///////////////////////////////////////////////////////////////////////////
    public void createDestination(String name, Class type) throws Exception {
        this.createDestination(name, type, false, Session.AUTO_ACKNOWLEDGE);
    }

    public void createDestination(String name, Class type, boolean fTransacted, int ackMode) throws Exception {
        // If the destination already exists, just return
        //
        if (jmsDestinations.get(name) != null)
            return;

        // Create the new destination and store it
        //
        Session session = connection.createSession(fTransacted, ackMode);

        // Look up the destination otherwise create it
        //
        Destination destination = (Destination) jndiLookup(name);
        if (destination == null) {
            // Create a topic or queue as specified
            //
            if (type.getName().equals("javax.jms.Queue")) {
                log("setupDestination() - creating Queue");
                destination = session.createQueue(name);
            }
            else {
                log("setupDestination() - creating Topic");
                destination = session.createTopic(name);
            }
        }

        JMSDestination jmsDest = new JMSDestination(destination, session, null, null);

        jmsDestinations.put(name, jmsDest);
    }

    //
    // This is an asynchronous listen. The caller provides a JMS callback
    // interface reference, and any messages received for the given destination
    // are provided through the onMessage() callback method
    //
    public void listen(String destName, MessageListener callback) throws Exception {
        log("JMSManager.listen(" + destName + ", " + callback + ")");

        JMSDestination jmsDest = getJMSDestination(destName);

        // Set the caller as a topic subcriber or queue receiver as appropriate
        //
        setupAsynchConsumer(jmsDest, callback);

        log("listen() - Asynchronous listen on destination " + destName);
    }

    //
    // This is a synchronous listen. The caller will block until
    // a message is received for the given destination
    //
    public Message listen(String destName) throws Exception {
        log("listen() - Synchronous listen on destination " + destName);

        JMSDestination jmsDest = getJMSDestination(destName);

        // Setup the consumer and block until a
        // message arrives for this destination
        //
        return setupSynchConsumer(jmsDest, 0);
    }

    //
    // This is a synchronous listen. The caller will block until
    // a message is received for the given destination OR the
    // timeout value (in milliseconds) has been reached
    //
    public Message listen(String destName, int timeout) throws Exception {
        log("listen() - Synchronous listen on destination " + destName);

        JMSDestination jmsDest = getJMSDestination(destName);

        // Setup the consumer and block until a
        // message arrives for this destination
        //
        return setupSynchConsumer(jmsDest, timeout);
    }

    public void listen(Destination dest, MessageListener callback) throws Exception {
        Session s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer c = s.createConsumer(dest);
        c.setMessageListener(callback);
    }

    public Message listen(Destination dest) throws Exception {
        Session s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer c = s.createConsumer(dest);
        Message msg = c.receive();
        s.close();
        return msg;
    }

    //
    // The following allows the caller to send a Message object to a destination
    //
    public void send(String destName, Message msg) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);

        // Make sure we have a message producer created for this destination
        //
        setupProducer(jmsDest);

        // Send the message for this destination
        //
        jmsDest.producer.send(msg);
        log("send() - message sent");
    }

    public void send(Destination dest, Message msg) throws Exception {
        Session s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer p = s.createProducer(dest);
        p.send(msg);
        s.close();
    }

    //
    // The following allows the caller to send a Serializable object to a destination
    //
    public void send(String destName, Serializable obj) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);

        // Make sure we have a message producer created for this destination
        //
        setupProducer(jmsDest);

        // Send the message for this destination
        //
        Message msg = createJMSMessage(obj, jmsDest.session);
        jmsDest.producer.send(msg);
        log("send() - message sent");
    }

    //
    // The following allows the caller to send text to a destination
    //
    public void send(String destName, String messageText) throws Exception {
        this.send(destName, (Serializable) messageText);
    }

    public void stop(String destName) {
        try {
            // Look for an existing destination for the given destination
            //
            JMSDestination jmsDest = (JMSDestination) jmsDestinations.get(destName);
            if (jmsDest != null) {
                // Close out all JMS related state
                //
                if (jmsDest.producer != null)
                    jmsDest.producer.close();
                if (jmsDest.consumer != null)
                    jmsDest.consumer.close();
                if (jmsDest.session != null)
                    jmsDest.session.close();

                jmsDest.destination = null;
                jmsDest.session = null;
                jmsDest.producer = null;
                jmsDest.consumer = null;

                // Remove the JMS client entry
                //
                jmsDestinations.remove(destName);
                jmsDest = null;
            }
        }
        catch (Exception e) {
        }
    }

    public MapMessage createMapMessage(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.session.createMapMessage();
    }

    public TextMessage createTextMessage(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.session.createTextMessage();
    }

    public ObjectMessage createObjectMessage(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.session.createObjectMessage();
    }

    public BytesMessage createBytesMessage(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.session.createBytesMessage();
    }

    public Session getSession(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.session;
    }

    public Destination getDestination(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.destination;
    }

    public MessageProducer getProducer(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.producer;
    }

    public MessageConsumer getConsumer(String destName) throws Exception {
        JMSDestination jmsDest = getJMSDestination(destName);
        return jmsDest.consumer;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal worker methods

    protected void connectToJMS() {
        // Check to see if already connected
        //
        if (JMSManager.connected == true)
            return;

        try {
            // Use jndi.properties for configuration 
            //
            jndi = new InitialContext();

            // Log the JNDI properties
            Hashtable props = jndi.getEnvironment();
            Enumeration keys = props.keys();
            log("JNDI properties:");
            log("----------------");
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                log(key.toString() + "=" + props.get(key));
            }
            log("----------------");

            // Get a JMS Connection
            //
            connection = getConnectionFactory().createConnection();
            connection.start();

            connected = true;

            log("connectToJMS - connected");
        }
        catch (Exception e) {
            JMSManager.connected = false;
            log("JMSManager.connectToJMS - Exception occured:");
            e.printStackTrace();
        }
    }

    protected JMSDestination getJMSDestination(String name) throws Exception {
        // Look for an existing Destination for the given name
        //
        JMSDestination jmsDest = (JMSDestination) jmsDestinations.get(name);

        // If not found, create it now
        //
        if (jmsDest == null) {
            this.createDestination(name, Topic.class);
            jmsDest = (JMSDestination) jmsDestinations.get(name);
        }

        return jmsDest;
    }

    protected void setupProducer(JMSDestination jmsDest) throws Exception {
        if (jmsDest.producer != null)
            return;

        jmsDest.producer = jmsDest.session.createProducer(jmsDest.destination);
    }

    protected void setupAsynchConsumer(JMSDestination jmsDest, MessageListener callback) throws Exception {
        if (jmsDest.consumer == null) {
            jmsDest.consumer = jmsDest.session.createConsumer(jmsDest.destination);
        }

        jmsDest.consumer.setMessageListener(callback);
    }

    protected Message setupSynchConsumer(JMSDestination jmsDest, int timeout) throws Exception {
        if (jmsDest.consumer == null) {
            jmsDest.consumer = jmsDest.session.createConsumer(jmsDest.destination);
        }

        if (timeout > 0)
            return jmsDest.consumer.receive(timeout);
        else
            return jmsDest.consumer.receive();
    }

    protected Message createJMSMessage(Serializable obj, Session session) throws Exception {
        if (obj instanceof String) {
            TextMessage textMsg = session.createTextMessage();
            textMsg.setText((String) obj);
            return textMsg;
        }
        else {
            ObjectMessage objMsg = session.createObjectMessage();
            objMsg.setObject(obj);
            return objMsg;
        }
    }

    protected ConnectionFactory getConnectionFactory() throws Exception {
        return (ConnectionFactory) jndiLookup("ConnectionFactory");
    }

    protected Object jndiLookup(String name) {
        try {
            if (jndi == null)
                jndi = new InitialContext();

            return jndi.lookup(name);
        }
        catch (Exception e) {
            return null;
        }
    }

    protected static void log(String s) {
        if (fLog)
            System.out.println(s);
    }
}

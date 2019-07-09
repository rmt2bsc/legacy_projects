package com.api.messaging.jms;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.api.messaging.MessageException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ProviderConnectionException;
import com.api.messaging.ResourceFactory;

/**
 * Assists clients as a factory for creating various JMS resources necessary for
 * connections, sessions, destinations and etc.
 * 
 * @author RTerrell
 * 
 */
public class JmsResourceFactory extends ResourceFactory {

    public static final String JAXB_DEFAULT_PKG = System.getProperty("jms.jaxb.defaultpackage");

    public static final String JNDI_SRC_LOCAL = "local";

    public static final String JNDI_SRC_LDAP = "ldap";

    public static final String JNDI_SRC_DNS = "dns";

    public static final String JNDI_SRC_DIRECT = "direct";

    public static String CONNECTION_FACTORY = "jms/ConnectionFactory";

    private static Logger logger = Logger.getLogger(JmsResourceFactory.class);

    private static final String JNDI_SRC = System.getProperty("jms.jndisource");

    private static Context jndiContext = null;

    private static ConnectionFactory connectionFactory;

    //    public static final String QUEUE_CONNECTION_FACTORY = System.getProperty("jms.queuemanagername");

    //    public static final String TOPIC_CONNECTION_FACTORY = "jms/TopicConnectionFactory";

    /**
     * Returns a ConnectionFactory object.
     * 
     * @return a ConnectionFactory object
     * @throws MessageException
     *             (or other exception) if name cannot be found
     */
    public static ConnectionFactory getConnectionFactory() throws MessageException {
        if (JmsResourceFactory.connectionFactory == null) {
            logger.log(Level.INFO, "Initializing JMS ConnectionFactory");
            JmsResourceFactory.connectionFactory = (ConnectionFactory) jndiLookup(CONNECTION_FACTORY);
        }
        return JmsResourceFactory.connectionFactory;
    }

    public static Connection getConnection() throws MessageException {
        try {
            return JmsResourceFactory.getConnectionFactory().createConnection();
        }
        catch (JMSException e) {
            e.printStackTrace();
            throw new MessageException(e);
        }
    }

    /**
     * Initializes the JMS session assoicated with this class.
     * 
     * @return javax.jms.Session
     * @throws MessageException
     */
    public static Session getSession() throws MessageException {
        try {
            logger.log(Level.INFO, "Initializing JMS Connection");
            Connection connection = JmsResourceFactory.connectionFactory.createConnection();
            return JmsResourceFactory.getSession(connection);
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
    }

    public static Session getSession(Connection con) throws MessageException {
        try {
            logger.log(Level.INFO, "Initializing JMS Session");
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return session;
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
    }

    /**
     * Returns a Destination object.
     * 
     * @param name
     *            String specifying destination name
     * @return a Destination object
     * @throws MessageException
     *             (or other exception) if name cannot be found
     */
    public static Destination getDestination(String name) throws MessageException {
        return (Destination) jndiLookup(name);
    }

    public static Producer createProducer(String destName) throws MessageException {
        ProviderConfig config = ResourceFactory.getJmsConfigInstance();
        setupContext(config);
        Producer api = new ProducerImpl(destName);
        try {
            api.connect(config);
        }
        catch (ProviderConnectionException e) {
            e.printStackTrace();
            throw new MessageException(e);
        }
        return api;
    }

    public static Producer createProducer(String reqDestName, String respDestName) throws MessageException {
        ProviderConfig config = ResourceFactory.getJmsConfigInstance();
        setupContext(config);
        Producer api = new ProducerImpl(reqDestName, respDestName);
        try {
            api.connect(config);
        }
        catch (ProviderConnectionException e) {
            e.printStackTrace();
            throw new MessageException(e);
        }
        return api;
    }

    public static Consumer createConsumer(String destName, ConsumerMessageHandler handler) throws MessageException {
        ProviderConfig config = ResourceFactory.getJmsConfigInstance();
        setupContext(config);
        Consumer api = new ConsumerImpl(destName, handler);
        try {
            api.connect(config);
        }
        catch (ProviderConnectionException e) {
            e.printStackTrace();
            throw new MessageException(e);
        }
        return api;
    }

    public static Consumer createConsumer(String reqDestName, ConsumerMessageHandler handler, String selector) throws MessageException {
        ProviderConfig config = ResourceFactory.getJmsConfigInstance();
        setupContext(config);
        Consumer api = new ConsumerImpl(reqDestName, handler, selector);
        try {
            api.connect(config);
        }
        catch (ProviderConnectionException e) {
            e.printStackTrace();
            throw new MessageException(e);
        }
        return api;
    }

    /**
     * Creates a JNDI API InitialContext object if none exists yet. Then looks
     * up the string argument and returns the associated object.
     * 
     * @param name
     *            the name of the object to be looked up
     * @return the object bound to name
     * @throws MessageException
     *             (or other exception) if name cannot be found
     */
    private static Object jndiLookup(String name) throws MessageException {
        JmsResourceFactory.getContext();
        try {
            return jndiContext.lookup(name);
        }
        catch (NamingException e) {
            throw new MessageException(e);
        }
    }

    /**
     * Verifies that a valid JNDI context exist or attempts to setup the context in the event it is 
     * found to not be initialized. Curerntly, this implementation only supports the <i>local</i> 
     * and <i>direct</i> JNDI source types (see application.properties).  Establishes the connection 
     * factory as it is determined by ProviderConfig
     * 
     * @return javax.naming.Context
     * @throws MessageException
     */
    public static Context setupContext(ProviderConfig config) throws MessageException {
        Hashtable<String, String> props = new Hashtable<String, String>();
        try {
            if (jndiContext == null) {
                if (JmsResourceFactory.JNDI_SRC != null && JmsResourceFactory.JNDI_SRC.equalsIgnoreCase(JmsResourceFactory.JNDI_SRC_LOCAL)) {
                    jndiContext = new InitialContext();
                }
                if (JmsResourceFactory.JNDI_SRC == null || JmsResourceFactory.JNDI_SRC.equalsIgnoreCase(JmsResourceFactory.JNDI_SRC_DIRECT)) {
                    props.put(Context.PROVIDER_URL, config.getHost());
                    props.put(Context.INITIAL_CONTEXT_FACTORY, config.getJndiContextName());
                    jndiContext = new InitialContext(props);
                }
                JmsResourceFactory.CONNECTION_FACTORY = config.getConnectionFactory();
                JmsResourceFactory.getConnectionFactory();
            }
            return jndiContext;
        }
        catch (NamingException e) {
            throw new MessageException(e);
        }
    }

    public static Context getContext() throws MessageException {
        if (jndiContext == null) {
            String msg = "JMS JNDI context is invalid";
            logger.log(Level.ERROR, msg);
            throw new MessageException(msg);
        }
        return jndiContext;
    }

}

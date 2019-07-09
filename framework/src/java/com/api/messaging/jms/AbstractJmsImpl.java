package com.api.messaging.jms;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.messaging.AbstractMessagingImpl;
import com.api.messaging.MessageBinder;
import com.api.messaging.MessageException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ProviderConnectionException;
import com.api.messaging.ResourceFactory;
import com.util.SystemException;

/**
 * Provides common implementation for JMS producer and consumer clients.
 * 
 * @author RTerrell
 *
 */
public abstract class AbstractJmsImpl extends AbstractMessagingImpl {

    private static Logger logger = Logger.getLogger(AbstractJmsImpl.class);

    private Connection connection;

    private Session session;

    private Destination initDest;

    protected MessageBinder binder;

    protected Properties props = new Properties();

    public AbstractJmsImpl() {

    }

    /**
     * Constructs a JMS client which is initialized with a connection and 
     * session and usesg <i>destName</i> to determine the initial destination 
     * it will be communincating to.  It also creates a message binder by the 
     * default JAXB package.
     * 
     * @param destName
     * @throws MessageException
     */
    protected AbstractJmsImpl(String destName) throws MessageException {
        super();
        this.initDest = JmsResourceFactory.getDestination(destName);
        try {
            this.binder = ResourceFactory.getJaxbMessageBinder(JmsResourceFactory.JAXB_DEFAULT_PKG);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
        }

    }

    /**
     * Releases the JMS resources.
     * 
     * @throws SystemException
     */
    @Override
    public void close() throws SystemException {
        super.close();
        this.initDest = null;
        try {
            this.session.close();
            this.session = null;
            this.connection.close();
            this.connection = null;
        }
        catch (JMSException e) {
            e.printStackTrace();
            throw new SystemException(e);
        }

    }

    /**
     * Establishes a connection to the messaging provider and a session 
     * that is bound to the connection.  This method relies on the 
     * existence of the ProviderConfig instance stored internally.
     * 
     * @param config
     *         a {@link ProviderConfig} instance containing the necessary
     *         information needed to configure, connect, and use a messaging
     *         provider.
     * @return Object
     *         an object which its runtime type is a JMS Session instance
     * @throws ProviderConnectionException
     * @see {@link com.api.messaging.ResourceFactory ResourceFactory}
     */
    @Override
    public Object connect(ProviderConfig config) throws ProviderConnectionException {
        try {
            this.connection = JmsResourceFactory.getConnection();
            this.session = JmsResourceFactory.getSession(connection);
            return session;
        }
        catch (MessageException e) {
            e.printStackTrace();
            throw new ProviderConnectionException(e);
        }
    }

    /**
    * Start the JMS Connection
    * 
    * @throws MessageException
    */
    public void startConnection() throws MessageException {
        try {
            if (this.connection == null) {
                throw new SystemException("JMS connection object has not been properly initialized");
            }
            this.connection.start();
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
    }

    /**
     * Serves as a stub for implementations that have no use for this method
     * 
     * @return Always returns null
     */
    @Override
    public Serializable getMessage() throws MessageException {
        return null;
    }

    /**
     * Serves as a stub for implementations that have no use for this method
     * 
     * @return Always returns null
     */
    @Override
    public Object sendMessage(Serializable data) throws MessageException {
        return null;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @return the initDest
     */
    public Destination getInitDest() {
        return initDest;
    }

}

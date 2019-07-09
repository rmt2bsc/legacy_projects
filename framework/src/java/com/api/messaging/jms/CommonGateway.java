package com.api.messaging.jms;

import javax.jms.JMSException;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.messaging.MessageException;
import com.util.SystemException;

/**
 * This class provides an additional level of abstraction of the underlying JMS 
 * messaging system so messages can be managed without any concern of the type 
 * of destination intended for the message or the messaging domain employed to 
 * manage the message.   
 * <p>
 * One of the main purposes of this framework is to further abstract the implementation 
 * details of the underlying JMS API.  This includes:  
 * <ul>
 *   <li>Establishing a connection to the JMS provider</li>
 *   <li>Create sessions </li>
 *   <li>Looking up destinations </li>
 * </ul>
 *    
 * 
 * @author RTerrell
 * @deprecated
 * 
 */
public class CommonGateway {

    private static Logger logger = Logger.getLogger(CommonGateway.class);

    private static ConnectionFactory connectionFactory;

    private Connection connection;

    protected Session session;

    protected Destination dest;

    public static final String DEF_SELECTOR_NAME = "default_selector";

    /**
     * Prepares the JMS environment for sending and receiving messages. The
     * basic steps performed are as follows:
     * <ul>
     * <li>Get a ConnectionFactory</li>
     * <li>Create a Connection.</li>
     * <li>Create Session</li>
     * <li>Look up the intended destination</li>
     * <li>Return the destination object to the caller</li>
     * 
     * @param destinationName
     *            name of the destination to lookup
     * @param Destination
     *            The destination that will be sending and receiving messages.
     * @throws MessageException
     */
    public Destination initializeDestination(String destinationName) throws MessageException {
        this.initializeSession();
        Destination dest = JmsResourceFactory.getDestination(destinationName);
        return dest;
    }

    /**
     * Initializes the JMS session assoicated with this class.
     * 
     * @return javax.jms.Session
     * @throws MessageException
     */
    protected Session initializeSession() throws MessageException {
        if (this.session != null) {
            logger.log(Level.INFO, "JMS Session already initialized");
            return this.session;
        }
        try {
            logger.log(Level.INFO, "Initializing JMS Session");
            // We only want to create the connection factory once
            if (CommonGateway.connectionFactory == null) {
                CommonGateway.connectionFactory = JmsResourceFactory.getConnectionFactory();
            }
            this.connection = CommonGateway.connectionFactory.createConnection();
            this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return this.session;
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
    }

    /**
     * Start the JMS Connection
     * 
     * @throws MessageException
     * @deprecated
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
     * Close the JMS Session, stops the connection from receiving any messages
     * provided they are not null, and closes the connection.
     * 
     * @throws MessageException
     *             general JMS errors.
     */
    public void close() throws MessageException {
        try {
            if (this.session != null) {
                this.session.close();
            }
            if (this.connection != null) {
                this.connection.close();
            }
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
        //	this.closeConnection();
    }

    /**
     * Close the connection.
     * 
     * @throws MessageException
     */
    public void closeConnection() throws MessageException {
        try {
            if (this.connection != null) {
                this.connection.stop();
                this.connection.close();
                this.connection = null;
            }
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
    }

}

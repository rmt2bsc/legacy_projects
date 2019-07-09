package com.api.messaging.jms;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.messaging.MessageException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ProviderConnectionException;

import com.util.RMT2RandomGUID;
import com.util.SystemException;

/**
 * This class is designed to send messages to its intended destination in a JMS
 * environment. It encapsulates the JMS api for looking up destinations and posting 
 * messages to a specified destination.  This implementation also has the ability to 
 * employ the Request-and-Reply form of messaging (synchronously).  The user of this 
 * implementation can  post a message to the request destination and optionally receive 
 * the reply from the destination specified in the message that was initially sent.
 * 
 * @author RTerrell
 * 
 */
class ProducerImpl extends AbstractJmsImpl implements Producer {
    private static Logger logger = Logger.getLogger(ProducerImpl.class);

    private Destination replyDest;

    protected MessageProducer producer;

    /**
     * Creates a {@link ProducerImpl} object that relies on its ancestor to 
     * initialize itself and create a Destination object for sending messages 
     * asynchronously.
     * 
     * @param destName
     * @throws MessageException
     */
    protected ProducerImpl(String destName) throws MessageException {
        super(destName);
        return;
    }

    /**
     * Creates a {@link ProducerImpl} object for sending messages synchronously.
     * 
     * @param reqDestName
     *          the name of the destination which messages will be sent.
     * @param respDestName
     *          the name of the destination to receive the response.
     * @throws MessageException
     */
    protected ProducerImpl(String reqDestName, String respDestName) throws MessageException {
        super(reqDestName);
        this.replyDest = JmsResourceFactory.getDestination(respDestName);
        return;
    }

    /**
     * Releases memory allocated for senders, any internal receivers, and the
     * session related to this producer.
     */
    public void close() throws SystemException {
        this.replyDest = null;
        if (this.producer != null) {
            try {
                this.producer.close();
                this.producer = null;
            }
            catch (JMSException e) {
                throw new SystemException(e);
            }
        }
        super.close();
        return;
    }

    /**
     * Creates a session and a producer usiing information contained in <i>config</i>.
     * 
     * @param config
     *         a {@link ProviderConfig} instance containing the necessary
     *         information needed to configure, connect, and use a messaging
     *         provider.
     * @return Session
     *           the JMS session instance used to create the internal JMS producer.
     * @throws ProviderConnectionException
     * @see {@link com.api.messaging.ResourceFactory ResourceFactory}
     */
    @Override
    public Session connect(ProviderConfig config) throws ProviderConnectionException {
        Session ses = (Session) super.connect(config);
        try {
            this.producer = ses.createProducer(this.getInitDest());
            return ses;
        }
        catch (JMSException e) {
            logger.log(Level.ERROR, "JMSException occurred.  " + e.getMessage());
            throw new ProviderConnectionException(e);
        }
    }

    /**
    * Creates a JMS ObjectMessage object using <i>data</i> as the source.
    * 
    * @param data
    *            The message content.
    */
    public Message createMessage(Serializable data) {
        ObjectMessage m = null;
        if (this.getSession() == null) {
            String msg = "ProducerImpl messaging object is not properly intialized";
            logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        try {
            m = this.getSession().createObjectMessage();
            m.setObject(data);
            return m;
        }
        catch (JMSException e) {
            throw new SystemException(e);
        }
    }

    /**
     * Creates an ObjectMessage from <i>data</i> and indicates the destination
     * where the reply should be sent. This type of message is generally
     * expecting a response which is usually transmitted synchronously.  The 
     * reply destination, which is where the recipient is requested to send the 
     * response message, is expected to be initialized before this method is
     * invoked.  This method normally serves as the starting point (the request) 
     * in the request/response communication chain.
     * 
     * @param data
     *            The message content that is to be sent
     * @param correlate
     *            Indicates that a unique GUID should be generated as a
     *            JMSCorrelatedID header field. This id will be used to
     *            correlate a message from one destination to another.
     * @return String
     *            A valid GUID id when correlated message is sent or null if message is not 
     *            correlated.             
     * @throws MessageException     
     *            When reply destination is invalid, message content is invalid, reply 
     *            destination could not be assoiciated with the inteneded message, or 
     *            a problem occurred assigning the correlated id to the message.      
     */
    public Message createMessage(Serializable data, boolean correlate) throws MessageException {
        if (this.replyDest != null) {
            throw new MessageException("Message could not be created, because the reply destination is invalid");
        }
        String errMsg = null;
        Message m = this.createMessage(data);
        if (m == null) {
            errMsg = "Message content is invalid";
            logger.log(Level.ERROR, errMsg);
            throw new MessageException(errMsg);
        }

        // Try to set the reply destination
        try {
            m.setJMSReplyTo(this.replyDest);
        }
        catch (JMSException e) {
            errMsg = "Problem occurred assigning the reply destination to the intended message.  " + e.getMessage();
            logger.log(Level.ERROR, errMsg);
            throw new MessageException(e);
        }

        // Try to correlate the message.
        String guid = null;
        if (correlate) {
            try {
                RMT2RandomGUID guidUtil = new RMT2RandomGUID();
                guid = guidUtil.toString();
                m.setJMSCorrelationID(guid);
                logger.log(Level.INFO, "Created correlated message for the current destination.  Correlated Id: " + guid);
            }
            catch (JMSException e) {
                errMsg = "The setJMSCorrelationID is invalid for synchronous messaging operation.  " + e.getMessage();
                logger.log(Level.ERROR, errMsg);
                throw new MessageException(e);
            }
        }
        else {
            logger.log(Level.INFO, "Created a non-correlated message for the current destination");
        }
        return m;
    }

    /**
     * Creates a message that is intended to be sent as a reply.  This method is generally 
     * used for synchronous messaging where the destination is specified in <i>origMsg</i>.  
     * This method is used to send a reply to producer of the message, <i>origMsg</i>.
     * 
     * @param replyData
     *          the data that is to serve as the reply message
     * @param origMsg
     *          the original message containing a reference to the destination 
     *          where the reply is to be sent.
     * @return ObjectMessage
     *          the message acting as the reply
     * @throws MessageException
     *          When JMS Reply To destination is unobtainable, an invalid destination was specified 
     *          when creating the message producer, the JMS correlation id could not be set, internal 
     *          error obtaining JMS session, or if error occurred setting message property. 
     */
    public Message createReplyMessage(Serializable replyData, Message origMsg) throws MessageException {
        String jmsCorrelatedId = null;
        String errMsg;
        ObjectMessage m = null;
        try {
            m = this.getSession().createObjectMessage();
            m.setObject(replyData);

            this.replyDest = origMsg.getJMSReplyTo();
            this.producer = this.getSession().createProducer(this.replyDest);

            // Attempt to create JMSCorrelationID predicate for selector
            jmsCorrelatedId = origMsg.getJMSCorrelationID();
            if (jmsCorrelatedId != null) {
                m.setJMSCorrelationID(jmsCorrelatedId);
                this.createMessageProperty(m, "correlationId", jmsCorrelatedId);
            }
            logger.log(Level.INFO, "Sending response message to its destination using correlated id: " + jmsCorrelatedId);
            return m;
        }
        catch (InvalidDestinationException e) {
            errMsg = e.getMessage();
            if (errMsg == null) {
                errMsg = "An invalid destination was specified when trying to create JMS Producer";
            }
            logger.log(Level.ERROR, "JMS error: " + errMsg);
            throw new MessageException(e);
        }
        catch (JMSException e) {
            errMsg = "JMS error: " + e.getMessage();
            logger.log(Level.ERROR, errMsg);
            throw new MessageException(e);
        }
    }

    /**
     * Adds a property to the current ObjectMessage instance.
     * 
     * @param propName
     *            The name of the property
     * @param propVal
     *            The value of the property
     * @throws MessageException
     */
    public void createMessageProperty(Message m, String propName, Object propVal) throws MessageException {
        MessageSelectorBuilder builder = new MessageSelectorBuilder();
        builder.createMessageProperty(m, propName, propVal);
        return;
    }

    /**
     * Sends a message identified as <i>data</i> to a particular the JMS destination.
     * 
     * @param message
     *            String the message data that is transformed into a JMS
     *            ObjectMessage type.
     * @return always returns null.
     * @throws MessageException
     *             general JMS errors
     */
    public Object sendMessage(Serializable message) throws MessageException {
        if (message == null) {
            throw new MessageException("Internal JMS message is invalid");
        }
        Message jmsMsg = this.createMessage(message);
        try {
            if (this.replyDest != null) {
                jmsMsg.setJMSReplyTo(this.replyDest);
            }
            this.producer.send(jmsMsg);
            return null;
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
    }

    /**
     * Retrieves the message from a destination in synchronous mode for a specified 
     * block time.  The destination is specified in a call to method, 
     * #createResponseMessage(Serializable, Message). Optionally the amount of time 
     * the client waits for the message to arrive can be specified via <i>duration</i>.  
     * The caller of the this method will block until the message arrives on the 
     * Destination or <i>duration</i> has passed.
     * 
     * @param duration 
     *          wait time in milliseconds when the value is greater than zero.  
     *          Otherwise, the process blocks until the message is received. 
     * @param selector
     *          a SQL like predicate used to filter incoming messages that the 
     *          internal consumer is interested. 
     * @return Message
     *          response object or null if the wait time has expired
     * @throws MessageException
     */
    public Message getReply(int waitTime, String selector) throws MessageException {
        if (this.replyDest == null) {
            return null;
        }
        MessageConsumer replyConsumer = null;
        try {
            if (selector != null) {
                replyConsumer = this.getSession().createConsumer(this.replyDest, selector);
            }
            else {
                replyConsumer = this.getSession().createConsumer(this.replyDest);
            }

            this.startConnection();

            Message message = null;
            if (waitTime <= 0) {
                message = replyConsumer.receive();
            }
            else {
                message = replyConsumer.receive(waitTime);
            }

            return message;
        }
        catch (JMSException e) {
            throw new MessageException(e);
        }
    }

}

package com.api.messaging.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.messaging.MessageException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ProviderConnectionException;
import com.util.SystemException;

/**
 * This class is used to receive messages from some destination in a JMS environment.  
 * It is also responsible for creating the JMS destination and registering any 
 * message listeners. The class is to be used by the client that is not deployed in 
 * the J2EE server.
 * 
 * @author RTerrell
 *
 */
class ConsumerImpl extends AbstractJmsImpl implements Consumer, MessageListener {
    private static Logger logger = Logger.getLogger(ConsumerImpl.class);

    private MessageConsumer consumer;

    private String selector;

    private Object m;

    protected ConsumerMessageHandler processor;

    /**
     * Default constructor which is not accessible by the public.
     * 
     * @throws MessageException
     */
    private ConsumerImpl() throws MessageException {
        return;
    }

    /**
     * Creates a ConsumerImpl object equipped with a Destination.
     * @param destName
     *          the name of the JMS destination this consumer is to receive 
     *          the message.
     * @param handler
     *          a instance of {@link ConsumerMessageHandler} that is used to 
     *          process the incoming message.
     * @throws MessageException
     */
    protected ConsumerImpl(String destName, ConsumerMessageHandler handler) throws MessageException {
        super(destName);
        this.processor = handler;
    }

    /**
     * Creates a ConsumerImpl object which is equipped with a Destination 
     * and a selector for filtering incoming messages at the destination.
     * 
     * @param destName
     *          the name of the JMS destination this consumer is to receive 
     *          the message.
     * @param handler
     *          a instance of {@link ConsumerMessageHandler} that is used to 
     *          process the incoming message.         
     * @param selectorValue
     *          value of the JMS selector to be applied to the consumer.
     * @throws MessageException
     */
    protected ConsumerImpl(String destName, ConsumerMessageHandler handler, String selectorValue) throws MessageException {
        this(destName, handler);
        this.selector = selectorValue;
    }

    /**
     *  Releases the consumer.
     */
    public void close() throws SystemException {
        try {
            if (this.consumer != null) {
                this.consumer.close();
                this.consumer = null;
            }
        }
        catch (JMSException e) {
            throw new SystemException(e);
        }
        super.close();
    }

    /**
     * Creates a session and a consumer using information contained in <i>config</i> and activates 
     * the consumer to listening for messages. 
     * 
     * @param config
     *         a {@link ProviderConfig} instance containing the necessary
     *         information needed to configure, connect, and use a messaging
     *         provider.
     * @return Session
     *          the JMS session instance used to create the internal JMS producer
     * @throws ProviderConnectionException
     * @see {@link com.api.messaging.ResourceFactory ResourceFactory}
     */
    @Override
    public Session connect(ProviderConfig config) throws ProviderConnectionException {
        Session ses = (Session) super.connect(config);
        try {
            if (this.selector != null) {
                this.consumer = ses.createConsumer(this.getInitDest(), this.selector);
                logger.log(Level.DEBUG, "Consumer is setup with a selector");
            }
            else {
                this.consumer = ses.createConsumer(this.getInitDest());
                logger.log(Level.DEBUG, "Consumer is setup without a selector");
            }
            this.consumer.setMessageListener(this);
            this.startConnection();
            return ses;
        }
        catch (JMSException e) {
            logger.log(Level.ERROR, "JMSException occurred.  " + e.getMessage());
            throw new ProviderConnectionException(e);
        }
        catch (MessageException e) {
            logger.log(Level.ERROR, "MessageException occurred.  " + e.getMessage());
            throw new ProviderConnectionException(e);
        }
    }

    /**
     * Attempts to store internally the returned JMS message as an Message 
     * type and invokes the method, {@link ConsumerMessageHandler#processMessage(Message)},
     * which contains specific message processing functionality.  A ConsumerMessageHandler 
     * implementation should have been assigned to the member variable, "processor", 
     * when this listener was first created.
     *  
     * @param msg
     *          The message retreived from a queue.  The message is 
     *          required to evaluate to a type of ObjectMessage.
     */
    public void onMessage(Message msg) {
        if (msg instanceof ObjectMessage) {
            try {
                this.m = ((ObjectMessage) msg).getObject();
                System.out.println("onMessage: Message was retrieved: " + this.m.toString());
            }
            catch (JMSException e) {
                e.printStackTrace();
            }
        }

        try {
            if (this.processor != null) {
                this.processor.processMessage(msg);
            }
        }
        catch (MessageException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.api.messaging.jms.AbstractJmsImpl#getMessage()
     */
    @Override
    public Serializable getMessage() throws MessageException {
        Object message = super.getMessage();
        message = this.m;
        return (Serializable) message;
    }

    /**
     * Assigns a message handler for this consumer
     * 
     * @param handler
     */
    public void setMessageHandler(ConsumerMessageHandler handler) {
        this.processor = handler;
    }
}

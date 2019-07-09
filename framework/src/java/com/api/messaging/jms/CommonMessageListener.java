package com.api.messaging.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.util.SystemException;

/**
 * A common MessageListener implementation which is used to asynchronously
 * receive and manage delivered messages of type ObjectMessage. This class uses
 * the Template pattern to allow the user to optionally provide logic to process
 * the incoming message in order to satisfy a specific business requirement
 * during the invocation of the <i>onMessage()</i> method.
 * 
 * @author RTerrell
 * @deprecated
 * 
 */
public class CommonMessageListener implements MessageListener {

    private ObjectMessage message;

    /**
     * Default constructor
     */
    public CommonMessageListener() {
        return;
    }

    /**
     * Deallocate any resources that are no longer used.
     */
    public void close() {
        return;
    }

    /**
     * Attempts to store internally the returned JMS message as an ObjectMessage
     * type and invokes the method,
     * {@link CommonMessageListener#processMessage processMessage}, which the
     * user can optionally provide specific message processing functionality at
     * the descendent.
     * 
     * @param msg
     *            The message retreived from a queue. The message is required to
     *            evaluate to a type of ObjectMessage.
     */
    public void onMessage(Message msg) {
        if (msg instanceof ObjectMessage) {
            this.message = (ObjectMessage) msg;
        }
        this.processMessage();
    }

    /**
     * Stub method used to provide message processing logic this method at the
     * descendent.
     */
    protected void processMessage() {
        return;
    }

    /**
     * Returns the internal ObjectMessage as a String value.
     * 
     * @return String The message data. Returns null when the message is null or
     *         the message is not of type String.
     * @throws SystemException
     *             JMS provider fails to get the text due to some internal
     *             error.
     */
    public String toString() {
        if (this.message == null) {
            return null;
        }
        try {
            Object obj = message.getObject();
            if (obj instanceof String) {
                return (String) message.getObject();
            }
            return null;
        }
        catch (JMSException e) {
            throw new SystemException(e);
        }
    }

}

package com.api.messaging.jms;

import java.io.Serializable;

import javax.jms.Message;

import com.api.messaging.MessageException;

/**
 * @author RTerrell
 *
 */
public interface ConsumerMessageHandler {

    /**
     * Processes a JMS message.
     * 
     * @param message
     *          The JMS message to process
     * @return Serializable
     *          The results of the message being processed.
     * @throws MessageException
     */
    Serializable processMessage(Message message) throws MessageException;

}

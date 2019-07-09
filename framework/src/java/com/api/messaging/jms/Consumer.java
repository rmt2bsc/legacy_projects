package com.api.messaging.jms;

import com.api.messaging.MessageManager;

/**
 * @author RTerrell
 *
 */
public interface Consumer extends MessageManager {

    /**
     * Assigns a message handler for this consumer.
     * 
     * @param handler
     *          an instance of ConsumerMessageHandler
     */
    void setMessageHandler(ConsumerMessageHandler handler);

}

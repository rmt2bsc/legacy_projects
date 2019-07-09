package com.api.messaging.jms;

import java.io.Serializable;

import javax.jms.Message;

import com.api.messaging.MessageException;
import com.api.messaging.MessageManager;

/**
 * @author RTerrell
 *
 */
public interface Producer extends MessageManager {

    Message createMessage(Serializable data);

    Message createMessage(Serializable data, boolean correlate) throws MessageException;

    Message createReplyMessage(Serializable replyData, Message origMsg) throws MessageException;

    void createMessageProperty(Message m, String propName, Object propVal) throws MessageException;

    Message getReply(int waitTime, String selector) throws MessageException;
}

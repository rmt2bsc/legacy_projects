package com.xml.schema.misc;

import java.math.BigInteger;
import java.util.Date;

import com.action.ActionHandlerException;

import com.api.messaging.ResourceFactory;

import com.util.RMT2Date;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RSCommonReply;
import com.xml.schema.bindings.ReplyStatusType;



/**
 * A factory for creating message payload components.
 * 
 * @author appdev
 *
 */
public class PayloadFactory {
    
    /**
     * Creates a PayloadFactory object.
     */
    private PayloadFactory() {
	return;
    }
 
    
    /**
     * Creates a JAXB HeaderType using <i>msgId</i>, <i>delivMode</i>, <i>msgType</i>, and <i>userId</i>.
     * 
     * @param msgId
     *          the message id
     * @param delivMode
     *          the deliver mode.  Valid values are "SYNC" and "ASYNC".
     * @param msgType
     *          the message mode.  Valid values are "REQUEST" and "RESPONSE".
     * @param userId
     *          the user id.
     * @return an instance of {@link com.xml.schema.bindings.HeaderType HeaderType}
     */
    public static HeaderType createHeader(String msgId, String delivMode, String msgType, String userId) {
	ObjectFactory f = new ObjectFactory();
	HeaderType header = f.createHeaderType();
	header.setMessageId(msgId);
	header.setDeliveryMode(delivMode);
	header.setMessageMode(msgType);
	String delivDate = RMT2Date.formatDate(new Date(), "MM/dd/yyyy HH:mm:ss");
	header.setDeliveryDate(delivDate);
	header.setUserId(userId);
	return header;
    }
    
    /**
     * Creates a JAXB ReplyStatusType using <i>successfull</i>, <i>msg</i>, <i>detailMsg</i>, and <i>returnCode</i>.
     * 
     * @param successfull
     *         boolean indicating true for SUCCESS and false for ERROR.
     * @param msg
     *         the message text
     * @param detailMsg
     *         extended message text
     * @param returnCode
     *         an int value which its meaning is significant to the implementor.
     * @return an instance of {@link com.xml.schema.bindings.ReplyStatusType ReplyStatusType}
     */
    public static ReplyStatusType createReplyStatus(boolean successfull, String msg, String detailMsg, int returnCode) {
	ObjectFactory f = new ObjectFactory();
	ReplyStatusType replyStat = f.createReplyStatusType();
	String status = null;
	
	if (successfull) {
	    status = "SUCCESS";    
	}
	else {
	    status = "ERROR";
	}
	replyStat.setReturnStatus(status);
	
	if (msg == null) {
	    msg = status;
	}
	if (detailMsg == null) {
	    detailMsg = status;
	}
	replyStat.setMessage(f.createReplyStatusTypeMessage(msg));
	replyStat.setExtMessage(f.createReplyStatusTypeExtMessage(detailMsg));
	replyStat.setReturnCode(f.createReplyStatusTypeReturnCode(BigInteger.valueOf(returnCode)));
	return replyStat;
    }
  
    /**
     * Produces a common web service reply representing a successful invocation.
     * 
     * @param entityId
     *                    a unique id representing the the entity impacted.
     * @param message
     *                    the message text describing the successful invocation
     * @param userId
     *                    the login id of the responsible user.
     * @return
     *                    XML document as the reply
     * @throws ActionHandlerException
     *                   general errors.
     */
    public static final String buildCommonPayload(int entityId, String message, String userId) throws ActionHandlerException {
        ObjectFactory f = new ObjectFactory();
        RSCommonReply ws = f.createRSCommonReply();

        HeaderType header = PayloadFactory.createHeader("RS_common_reply", "SYNC", "RESPONSE", userId);
        ws.setHeader(header);
        ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Web service invocation was successful", message, entityId);
        ws.setReplyStatus(rst);

        String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }
    
    /**
     * Produces a common web service reply representing a failed invocation.
     * 
     * @param errorCode
     *                    the error code
     * @param message
     *                    the message text describing why the web service failed
     * @param userId
     *                    the login id of the responsible user.
     * @return
     *                    XML document as the reply
     * @throws ActionHandlerException
     *                    general errors
     */
    public static final String buildCommonErrorPayload(int errorCode, String message, String userId) throws ActionHandlerException {
        ObjectFactory f = new ObjectFactory();
        RSCommonReply ws = f.createRSCommonReply();

        HeaderType header = PayloadFactory.createHeader("RS_common_reply", "SYNC", "RESPONSE", userId);
        ws.setHeader(header);
        ReplyStatusType rst = PayloadFactory.createReplyStatus(false, "Web service invocation failed", message, errorCode);
        ws.setReplyStatus(rst);

        String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
        return rawXml;
    }
}

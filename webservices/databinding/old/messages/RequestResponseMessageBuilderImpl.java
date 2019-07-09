package com.xml.schema.messages;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.api.Product;
import com.api.ProductBuilderException;
import com.api.messaging.MessageBinder;
import com.api.messaging.ResourceFactory;
import com.api.messaging.webservice.soap.SoapClientHelper;
import com.api.messaging.webservice.soap.SoapProcessorException;
import com.api.xml.XmlApiFactory;
import com.api.xml.XmlDao;

import com.bean.RMT2Base;

import com.util.NotFoundException;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.RequestMessage;
import com.xml.schema.bindings.ResponseMessage;

/**
 * 
 * @author appdev
 *
 */
class RequestResponseMessageBuilderImpl extends RMT2Base implements WSMessageBuilder {

    private static Logger logger = Logger.getLogger(RequestResponseMessageBuilderImpl.class);

    private static final String REQRESP_COMP_HEADER = "header";

    private static final String REQRESP_COMP_PAYLOAD = "payload";

    private static final String REQRESP_COMP_REPLYSTATUS = "replystatus";

    private static final String REQRESP_HEADER_MSGID = "messageId";

    private static final String REQRESP_HEADER_MSGTYPE = "messageType";

    private static final String REQRESP_HEADER_DELIVERDATE = "deliverDate";

    private static final String REQRESP_HEADER_DELIVERMODE = "deliverMode";

    private static final String REQRESP_HEADER_USERID = "userId";

    private static final String REPLY_RETURNSTATUS = "returnStatus";

    private static final String REPLY_MSG = "message";

    private static final String REPLY_EXTMSG = "extMessage";

    private static final String REPLY_RETCODE = "returncode";

    private Product product;

    private ObjectFactory f;

    private MessageBinder bindCntx;

    private Object aMessage;

    private Map<String, Object> dMessage;

    private HeaderType header;

    private ReplyStatusType replyStat;

    private String messageData;

    /**
     * Create a SoapProductBuilderImpl object requiring the user to customize the construction 
     * for the SOAP aMessage using its various methods.  
     */
    protected RequestResponseMessageBuilderImpl() {
	super();
	return;
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2Base#init()
     */
    @Override
    public void init() {
	super.init();
	this.f = new ObjectFactory();

	this.bindCntx = ResourceFactory.getJaxbMessageBinder();

	this.aMessage = null;

	// Create Request Header
	this.header = f.createHeaderType();

	// Create Reply status
	this.replyStat = f.createReplyStatusType();

	this.product = Product.getInstance();

    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#addHeader(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void addHeader(String msgId, String delivMode, String delivDate, String msgType, String userId) throws WSMessageBuilderException {
	this.header.setMessageId(msgId);
	this.header.setDeliveryMode(delivMode);
	this.header.setMessageMode(msgType);
	this.header.setDeliveryDate(delivDate);
	this.header.setUserId(userId);
	return;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#addMessageData(java.lang.String)
     */
    public void addPayload(Object data) throws WSMessageBuilderException {
	String payload = this.bindCntx.marshalMessage(data);
	this.messageData = payload;

    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#addReplyStatus(java.lang.String, java.lang.String, java.lang.String, int)
     */
    public void addReplyStatus(String status, String msg, String detailMsg, int returnCode) throws WSMessageBuilderException {
	this.replyStat.setReturnStatus(status);
	this.replyStat.setMessage(this.f.createReplyStatusTypeMessage(msg));
	this.replyStat.setExtMessage(this.f.createReplyStatusTypeExtMessage(detailMsg));
	this.replyStat.setReturnCode(this.f.createReplyStatusTypeReturnCode(BigInteger.valueOf(returnCode)));
	return;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#addMessage(java.lang.Object)
     */
    public void addMessage(Object message) {
	this.aMessage = message;

    }

    /* (non-Javadoc)
     * @see com.api.ProductBuilder#assemble()
     */
    public void assemble() throws ProductBuilderException {
	Object msgObj = null;
	if (WSMessageBuilder.MSGTYPE_REQUEST.equalsIgnoreCase(this.header.getMessageMode())) {
	    msgObj = this.assembleRequest();
	}
	if (WSMessageBuilder.MSGTYPE_RESPONSE.equalsIgnoreCase(this.header.getMessageMode())) {
	    msgObj = this.assembleResponse();
	}

	// Marshall entire aMessage
	String msgStr = this.bindCntx.marshalMessage(msgObj);

	// Set product with the aMessage object just created.
	this.product.setObjectVal(msgObj);
	this.product.setStringVal(msgStr);
	this.aMessage = msgObj;
    }

    private RequestMessage assembleRequest() {
	RequestMessage reqMsg = this.f.createRequestMessage();
	reqMsg.setHeader(this.header);
	reqMsg.setPayload(this.messageData);
	return reqMsg;
    }

    private ResponseMessage assembleResponse() {
	ResponseMessage resMsg = f.createResponseMessage();
	resMsg.setHeader(this.header);
	resMsg.setReplyStatus(this.replyStat);
	resMsg.setPayload(this.messageData);
	return resMsg;
    }

    /* (non-Javadoc)
     * @see com.api.ProductBuilder#disAssemble()
     */
    public void disAssemble() throws ProductBuilderException {
	if (this.aMessage == null) {
	    this.msg = "Message instance is null.  Be sure to assemble a message instance or set the message instance via the addMessage(Object) method befor invoking disassembe method";
	    RequestResponseMessageBuilderImpl.logger.error(this.msg);
	    throw new WSMessageBuilderException(this.msg);
	}

	// Unmarshall request/response to an instnace in the event its type is String
	boolean strInstance = (this.aMessage instanceof String);
	if (strInstance) {
	    String messageStr = this.aMessage.toString();
	    this.aMessage = this.bindCntx.unMarshalMessage(messageStr);
	    // We have obtain and set the payload from the XML string manually due encoding issues.
	    ResponseMessage rm = (ResponseMessage) this.aMessage;
	    String payload = this.getPayload(messageStr, rm.getHeader().getMessageId());
	    rm.setPayload(payload);
	}

	// Now determine if we are dealing with a request or a response message and manage appropriately
	boolean reqInstance = (this.aMessage instanceof RequestMessage);
	boolean respInstance = (this.aMessage instanceof ResponseMessage);
	if (reqInstance) {
	    this.dMessage = this.disassembleRequest();
	}
	else if (respInstance) {
	    this.dMessage = this.disassembleResponse();
	}
	else {
	    this.msg = "Message instance is of an invalid data type.  Must be of type RequestMessage or ResponseMessage";
	    RequestResponseMessageBuilderImpl.logger.error(this.msg);
	    throw new WSMessageBuilderException(this.msg);
	}
	// Set product with the disassembled message just created.  The String version is set to null.
	this.product.setObjectVal(this.dMessage);
	this.product.setStringVal(null);
    }

    private Map<String, Object> disassembleRequest() {
	RequestMessage rm = (RequestMessage) this.aMessage;
	Map<String, Object> components = this.disassebleCommonComponents(rm.getHeader(), rm.getPayload());
	return components;
    }

    private Map<String, Object> disassembleResponse() {
	ResponseMessage rm = (ResponseMessage) this.aMessage;

	Map<String, Object> components = this.disassebleCommonComponents(rm.getHeader(), rm.getPayload());
	Map<String, Object> replyStat = this.disassembleReplyStatus(rm.getReplyStatus());
	components.put(RequestResponseMessageBuilderImpl.REQRESP_COMP_REPLYSTATUS, replyStat);
	return components;
    }

    private Map<String, Object> disassebleCommonComponents(HeaderType ht, String payload) {
	Map<String, Object> components = new HashMap<String, Object>();
	Map<String, String> header = new HashMap<String, String>();
	header.put("messageId", ht.getMessageId());
	header.put("messageType", ht.getMessageMode());
	header.put("deliverDate", ht.getDeliveryDate());
	header.put("deliverMode", ht.getDeliveryMode());
	header.put("userId", ht.getUserId());
	components.put(RequestResponseMessageBuilderImpl.REQRESP_COMP_HEADER, header);
	components.put(RequestResponseMessageBuilderImpl.REQRESP_COMP_PAYLOAD, payload);
	return components;
    }

    private Map<String, Object> disassembleReplyStatus(ReplyStatusType rst) {
	Map<String, Object> reply = new HashMap<String, Object>();
	reply.put("returnStatus", rst.getReturnStatus());
	if (rst.getMessage().getValue() != null) {
	    reply.put("message", rst.getMessage().getValue());
	}
	if (rst.getExtMessage().getValue() != null) {
	    reply.put("extMessage", rst.getExtMessage().getValue());
	}
	if (rst.getReturnCode().getValue() != null) {
	    reply.put("returncode", rst.getReturnCode().getValue().intValue());
	}
	return reply;
    }

    private String getPayload(String xml, String messageId) throws ProductBuilderException {
	XmlDao dao = XmlApiFactory.createXmlDao(xml);
	String payload = null;
	try {
	    // TODO:  check content request XSD schema for appropriate data layout.
	    dao.retrieve("//response_message/payload/" + messageId);
	    if (dao.nextRow()) {
		//		    payload = dao.getColumnValue("payload");
		payload = dao.getColumnValue(messageId);
	    }
	    return payload;
	}
	catch (NotFoundException e) {
	    RequestResponseMessageBuilderImpl.logger.warn(e.getMessage());
	    return null;
	}
	catch (Exception e) {
	    RequestResponseMessageBuilderImpl.logger.error(e.getMessage());
	    throw new ProductBuilderException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.api.ProductBuilder#getProduct()
     */
    public Product getProduct() {
	return this.product;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getHeaderDeliverDate()
     */
    public String getHeaderDeliverDate() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, String> header = (Map<String, String>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_HEADER);
	if (header != null) {
	    return header.get(RequestResponseMessageBuilderImpl.REQRESP_HEADER_DELIVERDATE);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getHeaderDeliveryMode()
     */
    public String getHeaderDeliveryMode() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, String> header = (Map<String, String>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_HEADER);
	if (header != null) {
	    return header.get(RequestResponseMessageBuilderImpl.REQRESP_HEADER_DELIVERMODE);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getHeaderMessageId()
     */
    public String getHeaderMessageId() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, String> header = (Map<String, String>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_HEADER);
	if (header != null) {
	    return header.get(RequestResponseMessageBuilderImpl.REQRESP_HEADER_MSGID);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getHeaderMessageType()
     */
    public String getHeaderMessageType() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, String> header = (Map<String, String>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_HEADER);
	if (header != null) {
	    return header.get(RequestResponseMessageBuilderImpl.REQRESP_HEADER_MSGTYPE);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getHeaderUserId()
     */
    public String getHeaderUserId() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, String> header = (Map<String, String>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_HEADER);
	if (header != null) {
	    return header.get(RequestResponseMessageBuilderImpl.REQRESP_HEADER_USERID);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getPaylod()
     */
    public String getPaylod() {
	if (this.dMessage == null) {
	    return null;
	}
	String payload = (String) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_PAYLOAD);
	return payload;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getReplpyExtMessage()
     */
    public String getReplpyExtMessage() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, Object> reply = (Map<String, Object>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_REPLYSTATUS);
	if (reply != null) {
	    return (String) reply.get(RequestResponseMessageBuilderImpl.REPLY_EXTMSG);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getReplyMessage()
     */
    public String getReplyMessage() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, String> reply = (Map<String, String>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_REPLYSTATUS);
	if (reply != null) {
	    return (String) reply.get(RequestResponseMessageBuilderImpl.REPLY_MSG);
	}
	return null;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getReplyReturnCode()
     */
    public int getReplyReturnCode() {
	if (this.dMessage == null) {
	    return 0;
	}
	Map<String, Object> reply = (Map<String, Object>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_REPLYSTATUS);
	if (reply != null) {
	    Integer rc = (Integer) reply.get(RequestResponseMessageBuilderImpl.REPLY_RETCODE);
	    return rc.intValue();
	}
	return 0;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.messages.WSMessageBuilder#getReplyReturnStatus()
     */
    public String getReplyReturnStatus() {
	if (this.dMessage == null) {
	    return null;
	}
	Map<String, String> reply = (Map<String, String>) this.dMessage.get(RequestResponseMessageBuilderImpl.REQRESP_COMP_REPLYSTATUS);
	if (reply != null) {
	    return (String) reply.get(RequestResponseMessageBuilderImpl.REPLY_RETURNSTATUS);
	}
	return null;
    }

}

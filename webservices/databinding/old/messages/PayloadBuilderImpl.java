package com.xml.schema.misc;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.api.Product;
import com.api.ProductBuilderException;
import com.api.messaging.MessageBinder;
import com.api.messaging.ResourceFactory;

import com.bean.RMT2Base;

import com.util.NotFoundException;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

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
class PayloadBuilderImpl extends RMT2Base implements PayloadBuilder {

    private static Logger logger = Logger.getLogger(PayloadBuilderImpl.class);

    private Product product;

    private ObjectFactory f;
    
    private MessageBinder bindCntx;

//    private RequestMessage reqMsg;
//
//    private ResponseMessage resMsg;

    private HeaderType header;

    private ReplyStatusType replyStat;

    private String messageData;

    /**
     * Create a SoapProductBuilderImpl object requiring the user to customize the construction 
     * for the SOAP message using its various methods.  
     */
    protected PayloadBuilderImpl() {
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
	
	// Create Request
//	this.reqMsg = f.createRequestMessage();

	// Create Request Header
	this.header = f.createHeaderType();

	// Create Reply status
	this.replyStat = f.createReplyStatusType();

	// Create response message.
//	this.resMsg = f.createResponseMessage();
	
	this.product = Product.getInstance();

    }

    /* (non-Javadoc)
     * @see com.xml.schema.misc.PayloadBuilder#addHeader(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void addHeader(String msgId, String delivMode, String delivDate, String msgType, String userId) throws PayloadBuilderException {
	this.header.setMessageId(msgId);
	this.header.setDeliveryMode(delivMode);
	this.header.setMessageMode(msgType);
	this.header.setDeliveryDate(delivDate);
	this.header.setUserId(userId);
	return;
    }

    /* (non-Javadoc)
     * @see com.xml.schema.misc.PayloadBuilder#addMessageData(java.lang.String)
     */
    public void addMessageData(Object data) throws PayloadBuilderException {
	String payload = this.bindCntx.marshalMessage(data);
	this.messageData = payload;

    }

    /* (non-Javadoc)
     * @see com.xml.schema.misc.PayloadBuilder#addReplyStatus(java.lang.String, java.lang.String, java.lang.String, int)
     */
    public void addReplyStatus(String status, String msg, String detailMsg, int returnCode) throws PayloadBuilderException {
	this.replyStat.setReturnStatus(status);
	this.replyStat.setMessage(this.f.createReplyStatusTypeMessage(msg));
	this.replyStat.setExtMessage(this.f.createReplyStatusTypeExtMessage(detailMsg));
	this.replyStat.setReturnCode(this.f.createReplyStatusTypeReturnCode(BigInteger.valueOf(returnCode)));
	return;
    }

    /* (non-Javadoc)
     * @see com.api.ProductBuilder#assemble()
     */
    public void assemble() throws ProductBuilderException {
	Object msgObj = null;
	if (PayloadBuilder.MSGTYPE_REQUEST.equalsIgnoreCase(this.header.getMessageMode())) {
	    msgObj = this.assembleRequest();
	}
	if (PayloadBuilder.MSGTYPE_RESPONSE.equalsIgnoreCase(this.header.getMessageMode())) {
	    msgObj = this.assembleResponse();
	}
	
	// Marshall entire message
	String msgStr = this.bindCntx.marshalMessage(msgObj);
	
	// Set product with the message object just created.
	this.product.setObjectVal(msgObj);
	this.product.setStringVal(msgStr);
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
	
	
    }

    /* (non-Javadoc)
     * @see com.api.ProductBuilder#getProduct()
     */
    public Product getProduct() {
	return this.product;
    }

}

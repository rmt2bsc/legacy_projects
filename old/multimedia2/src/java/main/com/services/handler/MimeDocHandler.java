package com.services.handler;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;

import org.apache.log4j.Logger;

import com.api.db.MimeContentApi;
import com.api.db.MimeException;
import com.api.db.MimeFactory;

import com.api.messaging.MessagingHandlerException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.SoapMessageHandlerImpl;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.Content;
import com.bean.MimeTypes;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.MimeContentType;
import com.xml.schema.bindings.MimeType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQContentSearch;
import com.xml.schema.bindings.RSContentSearch;
import com.xml.schema.bindings.ReplyStatusType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of retrieving 
 * a single record from the content table of the MIME database.  The incoming 
 * and outgoing data is expected to be in the form of XML. 
 * 
 * @author Roy Terrell
 *
 */
public class MimeDocHandler extends SoapMessageHandlerImpl {

    private static Logger logger = Logger.getLogger(MimeDocHandler.class);

    private RQContentSearch reqMessage;

    /**
     * Default constructor
     *
     */
    protected MimeDocHandler() {
	super();
    }

    public MimeDocHandler(DatabaseConnectionBean con, Request request) {
	super(con, request);
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    public String execute(Properties parms) throws MessagingHandlerException {
	String xml = null;
	this.reqMessage = (RQContentSearch) parms.get(SoapProductBuilder.PAYLOADINSTANCE);
	try {
	    xml = this.doFetch(this.reqMessage.getContentId().intValue());
	    return xml;
	}
	catch (MimeException e) {
	    throw new MessagingHandlerException(e);
	}

    }

    /**
     * Fetch a single record from the content table using the primary key (content id).
     * 
     * @param contentId
     *          the primary key
     * @return String
     *           the raw XML data representing the single content record.
     * @throws SoapProcessorException
     *           when the content record is not found.
     */
    protected String doFetch(int contentId) throws MimeException {
	MimeContentApi api = MimeFactory.getMimeContentApiInstance("com.api.db.DefaultSybASABinaryImpl");
	api.initApi(this.con);

	//Send content to Browser
	try {
	    Content content = (Content) api.fetchContent(contentId);
	    // copy DAO data to XML instance
	    ObjectFactory f = new ObjectFactory();
	    MimeContentType mct = f.createMimeContentType();
	    mct.setContentId(BigInteger.valueOf(content.getContentId()));
	    mct.setAppCode(content.getAppCode());
	    mct.setModuleCode(content.getModuleCode());
	    mct.setFilename(content.getFilename());
	    mct.setFilepath(content.getFilepath());
	    mct.setFilesize(BigInteger.valueOf(content.getSize()));
	    mct.setTextData(content.getTextData());

	    // Manaage MIME Type/Sub Type details
	    String filename = content.getFilename();
	    if (filename != null) {
		String fileExt = filename.substring(filename.indexOf("."));
		List<MimeTypes> list = (List<MimeTypes>) api.getMimeType(fileExt);
		if (list != null && list.size() > 0) {
		    // In our database, a MIME filename extension could exist multiple times
		    for (MimeTypes item : list) {
			MimeType mt = f.createMimeType();
			mt.setMimeTypeId(BigInteger.valueOf(item.getMimeTypeId()));
			mt.setFileExt(item.getFileExt());
			mt.setMediaType(item.getMediaType());
			mct.getMimeTypeDetails().add(mt);
		    }
		}
		else {
		    MimeType mt = f.createMimeType();
		    mct.getMimeTypeDetails().add(mt);
		}
	    }
	    else {
		MimeType mt = f.createMimeType();
		mct.getMimeTypeDetails().add(mt);
	    }

	    // Set up attachment.  Force content type to be text/plain since image will be base64 encoded.
	    DataHandler dh = new DataHandler(content.getImageData(), "text/plain");
	    this.attachments = new ArrayList<Object>();
	    this.attachments.add(dh);

	    // Set response message id so that ancestor can include the correct service identifier for SOAP response.
	    //	    this.responseMsgId = "RS_content_search";

	    // Create Payload instance for response SOAP message body
	    RSContentSearch ws = f.createRSContentSearch();
	    ws.getContent().add(mct);
	    HeaderType header = PayloadFactory.createHeader(this.getRespMsgId(), this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.getUserId());
	    ws.setHeader(header);
	    ReplyStatusType rst = PayloadFactory.createReplyStatus(true, null, null, ws.getContent().size());
	    ws.setReplyStatus(rst);
	    String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    return rawXml;
	}
	catch (Exception e) {
	    this.msg = "Unable to execute MIME Document Handler due to MIME Content error";
	    logger.error(this.msg);
	    throw new MimeException(this.msg, e);
	}
    }
}
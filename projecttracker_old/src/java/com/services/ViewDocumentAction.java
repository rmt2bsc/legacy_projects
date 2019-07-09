package com.services;

import java.io.DataOutputStream;
import java.io.IOException;

import java.math.BigInteger;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;

import com.api.messaging.MessageException;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapClientWrapper;
import com.api.security.authentication.RMT2SessionBean;

import com.constants.RMT2ServletConst;
import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.RMT2Base64Decoder;
import com.util.SystemException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQContentSearch;

import com.xml.schema.misc.PayloadFactory;

/**
 * This class provides action handlers needed to serve the request of the Customer Sales Order Console user interface.
 * 
 * @author Roy Terrell
 *
 */
public class ViewDocumentAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_FETCH = "MimeFile.Request.fetch";

    private static Logger logger = Logger.getLogger(ViewDocumentAction.class);

    private int uid;

    /**
     * Default constructor
     *
     */
    public ViewDocumentAction() {
	super();

    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public ViewDocumentAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(context, request);
    }

    /**
     * Initializes this object using _conext and _request.  This is needed in the 
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	if (command.equalsIgnoreCase(ViewDocumentAction.COMMAND_FETCH)) {
	    this.doFetch();
	}
    }

    /**
     * Retreives the customer data from the client's request.
     */
    protected void receiveClientData() throws ActionHandlerException {
	String contentId = request.getParameter("contentId");
	try {
	    this.uid = Integer.parseInt(contentId);
	}
	catch (NumberFormatException e) {
	    this.uid = 0;
	}
    }

    /**
     * Sends the response of the client's request with an ArrayList of  CombinedSalesOrder objects and a 
     * CustomerWithName object which their attriute names are required to be set as "orderhist" and 
     * "customer", respectively.
     */
    protected void sendClientData() throws ActionHandlerException {
	return;
    }

    /**
     * Navigate back to the customer search page.
     *  
     * @throws ActionHandlerException.
     */
    protected void doFetch() throws ActionHandlerException {
	this.receiveClientData();
	
	try {
	    ObjectFactory f = new ObjectFactory();
	    // Create Payload instance
	    RQContentSearch ws = f.createRQContentSearch();
	    RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	    HeaderType header = PayloadFactory.createHeader("RQ_content_search", "SYNC", "REQUEST", userSession.getLoginId());
	    ws.setHeader(header);
	    ws.setContentId(BigInteger.valueOf(this.uid));
	    String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	    
	    SoapClientWrapper client = new SoapClientWrapper();
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		this.msg = client.getSoapErrorMessage(resp);
		logger.error(this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	    
	    // Retrieve the image from the SOAP Message as an attachment and decode 
	    // the image contents from  base64 to an array of bytes.   Image will be 
	    // sent back to the caller via the Response's OutputStream.
	    if (resp.countAttachments() > 0) {
		Iterator <AttachmentPart> iter = resp.getAttachments();
		while (iter.hasNext()) {
		    AttachmentPart ap = iter.next();
		    try {
			Object  data = ap.getContent();
			byte image[] = null;
			if (data != null) {
			    image = RMT2Base64Decoder.decodeToBytes(data.toString());
			}
			DataOutputStream dos = new DataOutputStream((ServletOutputStream) response.getOutputStream());
			dos.write(image);
			dos.flush();
			dos.close();
			break;
		    }
		    catch (SOAPException e) {
			throw new SystemException(e);
		    }
		}
	    }
	}
	catch (SystemException e) {
	    this.msg = "SystemException occurred.  " + e.getMessage();
	    logger.error(this.msg);
	    throw new ActionHandlerException(e);
	}
	catch (MessageException e) {
	    this.msg = "MessageException occurred.  " + e.getMessage();
	    logger.error(this.msg);
	    throw new ActionHandlerException(e);
	}
	catch (IOException e) {
	    this.msg = "IOException occurred.  " + e.getMessage();
	    logger.error(this.msg);
	    throw new ActionHandlerException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#add()
     */
    public void add() throws ActionHandlerException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#save()
     */
    public void save() throws ActionHandlerException, DatabaseException {
	// TODO Auto-generated method stub

    }

}
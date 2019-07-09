package com.services.audiovideo;

import java.io.DataInputStream;
import java.io.InputStream;

import java.util.Properties;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;

import com.api.messaging.MessageManager;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.http.client.HttpClientResourceFactory;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.RMT2File;
import com.util.SystemException;

/**
 * This class provides action handlers needed to serve the request for audio/video content.
 * 
 * @author Roy Terrell
 *
 */
public class AudioVideoAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_FETCH = "MimeFile.Request.fetch";

    private static Logger logger = Logger.getLogger(AudioVideoAction.class);

    private int uid;

    /**
     * Default constructor
     *
     */
    public AudioVideoAction() {
	super();

    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public AudioVideoAction(Context context, Request request) throws SystemException, DatabaseException {
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
	if (command.equalsIgnoreCase(AudioVideoAction.COMMAND_FETCH)) {
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
     * fetches the audiou/video content based on content id.
     *  
     * @throws ActionHandlerException.
     */
    protected void doFetch() throws ActionHandlerException {
	this.receiveClientData();
	ProviderConfig config = ResourceFactory.getConfigInstance();
	config.setHost("http://localhost:8080/multimedia/ipcThreadServlet");
	Properties props = new Properties();
	props.setProperty("clientAction", "fetch");
	props.setProperty("contentId", String.valueOf(this.uid));
	MessageManager api = HttpClientResourceFactory.getHttpInstance();

	try {
	    //Send content to Browser
	    api.connect(config);
	    InputStream is = (InputStream) api.sendMessage(props);
	    DataInputStream dis = new DataInputStream(is);

	    this.response.setContentType("image/jpeg");
	    byte bytes[] = RMT2File.getStreamByteData(dis);
	    this.response.setContentLength(bytes.length);
	    ((ServletOutputStream) response.getOutputStream()).write(bytes);
	    ((ServletOutputStream) response.getOutputStream()).flush();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    return;
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
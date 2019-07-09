package com.services.document;

import java.io.DataOutputStream;

import javax.activation.DataHandler;
import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.JdbcFactory;
import com.api.db.MimeContentApi;
import com.api.db.MimeFactory;

import com.api.filehandler.InboundFileFactory;

import com.bean.Content;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.RMT2File;
import com.util.SystemException;

/**
 * This class provides action handlers needed to serve the request of the Customer Sales Order Console user interface.
 * 
 * @author Roy Terrell
 *
 */
public class LocalMimeDocAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_FETCH = "MimeFile.Request.fetch";

    private static Logger logger = Logger.getLogger(LocalMimeDocAction.class);

    private int uid;

    /**
     * Default constructor
     *
     */
    public LocalMimeDocAction() {
	super();

    }

    /**
     * Main contructor for this action handler.  
     * 
     * @param context The servlet context to be associated with this action handler
     * @param request The request object sent by the client to be associated with this action handler
     * @throws SystemException
     */
    public LocalMimeDocAction(Context context, Request request) throws SystemException, DatabaseException {
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
	if (command.equalsIgnoreCase(LocalMimeDocAction.COMMAND_FETCH)) {
	    this.doFetchLocal();
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
    protected void doFetchLocal() throws ActionHandlerException {
	this.receiveClientData();
	String configFile = InboundFileFactory.ENV + "_" + InboundFileFactory.configFile;
	String dbUrl =  RMT2File.getPropertyValue(configFile, "mime.dbURL");
	DatabaseConnectionBean con = JdbcFactory.getConnection(dbUrl, configFile);
	MimeContentApi api = MimeFactory.getMimeContentApiInstance("com.api.db.DefaultFileSystemContentImpl");
	api.initApi(con);

	//Send content to Browser
	try {
	    Content content = (Content) api.fetchContent(this.uid);
	    DataHandler dh = null;
	    if (content.getImageData() != null && content.getImageData() instanceof DataHandler) {
		dh = (DataHandler) content.getImageData();
	    }
	    else {
		this.msg = "MIME image fetch failed due to its data type was not of DataHandler";
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	    response.setContentType(dh.getContentType());
	    DataOutputStream dos = new DataOutputStream((ServletOutputStream) response.getOutputStream());
	    byte imgBytes[] = RMT2File.getStreamByteData(dh.getInputStream());
	    dos.write(imgBytes);
	    dos.flush();
	    dh.getInputStream().close();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
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
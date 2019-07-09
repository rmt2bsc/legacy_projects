package com.action.app;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.db.DatabaseConnectionBean;
import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.user.ApplicationApi;
import com.api.security.user.UserFactory;

import com.constants.GeneralConst;
import com.constants.RMT2SystemExceptionConst;

import com.util.SystemException;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the application list page.  The following request types are 
 * serviced: list applications, add and application, and edit an application.
 * 
 * @author Roy Terrell
 * 
 */
public class AppSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LIST = "App.Search.list";

    private static final String COMMAND_EDIT = "App.Search.edit";

    private static final String COMMAND_ADD = "App.Search.add";

    private Logger logger;

//    private ApplicationApi api;

    private Object data;
    
    private int selectedAppId;

    private boolean selectionRequired;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AppSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("AppSearchAction");
    }

    /**
     * Performs post initialization and sets up an Application Api reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's request using request, response, and command.  
     * Add, edit, delete, and list application are the commands recognized.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	this.selectionRequired = false;
	if (command.equalsIgnoreCase(AppSearchAction.COMMAND_LIST)) {
	    this.search();
	}
	if (command.equalsIgnoreCase(AppSearchAction.COMMAND_EDIT)) {
	    this.selectionRequired = true;
	    this.editData();
	}
	if (command.equalsIgnoreCase(AppSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
    }

    /**
     * Retrieve all application records from the database.
     * 
     * @throws ActionHandlerException
     */
    protected void search() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all application records from the database using unique id.
	    this.data = api.getAll();
	    this.sendClientData();
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Creates a new Application object which is used to add an application entry to the 
     * system via the Application Maintenance Edit page.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	this.data = UserFactory.createApplication();
	return;
    }

    /**
     * Obtains the input data from the request and maps the data to an 
     * {@link com.bean.Application Application} object.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve application from the database using unique id.
	    this.data = api.findApp(this.selectedAppId);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
	return;
    }

    /**
     * Obtains the application unique id data from the request which was selected 
     * by the user.   The unique id is used to obtain the application record from 
     * the database and to map the data to its assoicated object. 
     * 
     * @throws ActionHandlerException 
     *          When a required selection was not made or an error occurrence during data retrieval. 
     */
    protected void receiveClientData() throws ActionHandlerException {
	if (this.selectionRequired) {
	    // Client must select a row to edit.	    
	    if (this.selectedRow < 0) {
		logger.log(Level.ERROR, RMT2SystemExceptionConst.MSG_ITEM_NOT_SELECTED);
		throw new ActionHandlerException(RMT2SystemExceptionConst.MSG_ITEM_NOT_SELECTED, RMT2SystemExceptionConst.RC_ITEM_NOT_SELECTED);
	    }
	    String temp = this.getInputValue("AppId", null);
	    try {
		this.selectedAppId = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		this.selectedAppId = -1;
	    }
	}
    }

    /**
     * Sends an application object to the client via the request object.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.data);
    }

    /**
     * No Action
     */
    public void save() throws ActionHandlerException {
    }

    /**
     * No Action
     */
    public void delete() throws ActionHandlerException {
	return;
    }
}
package com.action.app;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.user.ApplicationApi;
import com.api.security.user.ApplicationException;
import com.api.security.user.UserFactory;

import com.bean.Application;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the application edit page.  The following request types are 
 * serviced: save application, delete an application, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class AppEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "App.Edit.save";
    
    private static final String COMMAND_DELETE = "App.Edit.delete";

    private static final String COMMAND_BACK = "App.Edit.back";

    private Logger logger;

    private Object data;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AppEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("UserEditAction");
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
     * Processes the client's requests to save changes made to an application profile, 
     * delete an application profile, and to navigate back to Application List page.
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
	if (command.equalsIgnoreCase(AppEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(AppEditAction.COMMAND_DELETE)) {
 	    this.deleteData();
	}
	if (command.equalsIgnoreCase(AppEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Updates an application profile by persiting changes to the database. This
     * method is used for adding and modifying application profiles.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	Application app = (Application) this.data;
	try {
	    // Update application profile.
	    int key = api.maintainApp(app);
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = "Application configuration saved successfully";
	}
	catch (ApplicationException e) {
	    this.msg = e.getMessage();
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }


    /**
     * Navigates the user to the Application List page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all application records from the database using unique id.
	    this.data = api.getAll();
	    this.sendClientData();
	    this.request.removeAttribute(RMT2ServletConst.REQUEST_MSG_INFO);
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
     * Gathers data from the user's request and packages the data into a
     * Application object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	try {
	    // Retrieve application from the database using unique id.
	    this.data = UserFactory.createApplication();
	    // Update application object with user input.
	    UserFactory.packageBean(this.request, this.data);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
    }
    
    
    /**
     * Delete an application from the database using the id of the application.
     * 
     * @param appId  The id of the application
     * @return Total number of rows deleted.
     * @throws ApplicationException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int rc;
	Application app = (Application) this.data;
	try {
	    // Update application profile.
	    rc = api.deleteApp(app.getAppId());
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = rc + " application configuration(s) deleted successfully";
	}
	catch (ApplicationException e) {
	    this.msg = e.getMessage();
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * Sends an {@link com.bean.Application Application} data object and any server messages 
     * to the user via the request object.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.data);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }



    /**
     * No Action
     */
    public void add() throws ActionHandlerException {
	return;
    }

    /**
     * No Action
     */
    public void edit() throws ActionHandlerException {
	return;
    }

}
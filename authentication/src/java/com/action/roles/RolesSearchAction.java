package com.action.roles;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2SystemExceptionConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.util.SystemException;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the security role list page.  The following request types are 
 * serviced: list, add, and edit a role.
 * 
 * @author Roy Terrell
 * 
 */
public class RolesSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LIST = "Roles.Search.list";

    private static final String COMMAND_EDIT = "Roles.Search.edit";

    private static final String COMMAND_ADD = "Roles.Search.add";

    private Logger logger;

    private Object data;

    private int selectedRoleId;

    private boolean selectionRequired;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public RolesSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("RolesSearchAction");
    }

    /**
     * Performs post initialization and sets up an User Api reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's request using request, response, and command.  
     * Add, edit, and list are the user group commands recognized.
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
	if (command.equalsIgnoreCase(RolesSearchAction.COMMAND_LIST)) {
	    this.search();
	}
	if (command.equalsIgnoreCase(RolesSearchAction.COMMAND_EDIT)) {
	    this.selectionRequired = true;
	    this.editData();
	}
	if (command.equalsIgnoreCase(RolesSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
    }

    /**
     * Retrieve all security role records from the database.
     * 
     * @throws ActionHandlerException
     */
    protected void search() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all security roles from the database using unique id.
	    this.data = api.getAllRoles();
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
     * Creates a new security role object which is used to add an entry to the 
     * system via the Roles Maintenance Edit page.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	this.data = UserFactory.createRole();
	return;
    }

    /**
     * Prepares a selected role for editing.  Obtains the input data from the 
     * request and maps the data to a {@link com.bean.Roles Roles} instance.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve role from the database using unique id.
	    this.data = api.getRole(this.selectedRoleId);
	    return;
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
     * Obtains the selected role from the user's request.   The role is identified by its unique id  
     * and is used to obtain the complete record of the security role from the database.
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
	    String temp = this.getInputValue("RoleId", null);
	    try {
		this.selectedRoleId = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		this.selectedRoleId = -1;
	    }
	}
    }

    /**
     * Sends a {@link com.bean.Roles Roles} object to the client via the request object.
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
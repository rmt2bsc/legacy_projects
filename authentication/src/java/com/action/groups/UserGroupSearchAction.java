package com.action.groups;

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
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.constants.GeneralConst;
import com.constants.RMT2SystemExceptionConst;

import com.util.SystemException;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the user group list page.  The following request types are 
 * serviced: list user groups, add user group, and edit user group.
 * 
 * @author Roy Terrell
 * 
 */
public class UserGroupSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LIST = "Group.Search.list";

    private static final String COMMAND_EDIT = "Group.Search.edit";

    private static final String COMMAND_ADD = "Group.Search.add";

    private Logger logger;

//    private UserApi api;

    private Object data;
    
    private int selectedGrpId;

    private boolean selectionRequired;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public UserGroupSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("UserGroupSearchAction");
    }

    /**
     * Performs post initialization and sets up an User Api reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
//	this.api = UserFactory.createApi(this.dbConn);
    }

    /**
     * Processes the client's request using request, response, and command.  
     * Add, edit, delete, and list are the user group commands recognized.
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
	if (command.equalsIgnoreCase(UserGroupSearchAction.COMMAND_LIST)) {
	    this.search();
	}
	if (command.equalsIgnoreCase(UserGroupSearchAction.COMMAND_EDIT)) {
	    this.selectionRequired = true;
	    this.editData();
	}
	if (command.equalsIgnoreCase(UserGroupSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
    }

    /**
     * Retrieve all user group records from the database.
     * 
     * @throws ActionHandlerException
     */
    protected void search() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all user group records from the database using unique id.
	    this.data = api.getAllGroups();
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
     * Creates a new user group object which is used to add an user group entry to the 
     * system via the User Group Maintenance Edit page.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	this.data = UserFactory.createUserGroup();
	return;
    }

    /**
     * Obtains the input data from the request and maps the data to an user group instance.
     * 
     * {@link com.bean.Application Application} object.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            // Retrieve user group from the database using unique id.
            this.data = api.getGroup(this.selectedGrpId);
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
     * Obtains the user group's unique id data from the request which was selected 
     * by the user.   The unique id is used to obtain the user group record from 
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

	    String temp = this.getInputValue("GrpId", null);
	    try {
		this.selectedGrpId = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		this.selectedGrpId = -1;
	    }
	}
    }

    /**
     * Sends an user group object to the client via the request object.
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
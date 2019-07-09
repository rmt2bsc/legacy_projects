package com.action.resource;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.user.ResourceApi;
import com.api.security.user.ResourceFactory;
import com.bean.UserResourceType;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2SystemExceptionConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.util.SystemException;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the resource type list page.  The following request types are 
 * serviced: list, add, and edit a role.
 * 
 * @author Roy Terrell
 * 
 */
public class TypeSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_LIST = "ResourceType.Search.list";

    private static final String COMMAND_EDIT = "ResourceType.Search.edit";

    private static final String COMMAND_ADD = "ResourceType.Search.add";

    private Logger logger;

    private Object data;

    private boolean selectionRequired;

    /**
     * Default class constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public TypeSearchAction() throws SystemException {
	super();
	logger = Logger.getLogger("TypeSearchAction");
    }

    /**
     * Performs post initialization and sets up an Resource Api reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's request using request, response, and command.  
     * Add, edit, and list are the resource type commands recognized.
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
	if (command.equalsIgnoreCase(TypeSearchAction.COMMAND_LIST)) {
	    this.search();
	}
	if (command.equalsIgnoreCase(TypeSearchAction.COMMAND_EDIT)) {
	    this.selectionRequired = true;
	    this.editData();
	}
	if (command.equalsIgnoreCase(TypeSearchAction.COMMAND_ADD)) {
	    this.addData();
	}
    }

    /**
     * Retrieve all resource type records from the database.
     * 
     * @throws ActionHandlerException
     */
    protected void search() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all resource types from the database.
	    this.data = api.getType(null);
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
     * Creates a new resource type object which is used to add an entry to the 
     * system via the Resource Type Maintenance Edit page.
     * 
     * @throws ActionHandlerException
     */
    public void add() throws ActionHandlerException {
	this.data = ResourceFactory.createUserResourceType();
	return;
    }

    /**
     * Prepares a selected resource type for editing.  Obtains the input data from the 
     * request and maps the data to a {@link com.bean.UserResourceType UserResourceType} instance.
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	if (this.data == null) {
	    return;
	}
	
	// Since resource api returns resource types in a List collection, isolate single element.
	if (this.data instanceof List) {
	    List list = (List) this.data;
	    if (list.size() > 1) {
		this.msg = "Edit Error: More than one occurrence of the selected item was found to be assoicated with the user\'s selection";
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	    this.data = list.get(0);
	}
	
	// Update object with user's changes.
	try {
	    ResourceFactory.packageBean(this.request, this.data, this.selectedRow);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	return;
    }

    /**
     * Obtains the selected resource type from the user's request.   The resource type 
     * is identified by its unique id and is used to obtain the complete record of the 
     * resource type from the database.
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
	    int uid;
	    String temp = this.getInputValue("RsrcTypeId", null);
	    try {
		uid = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		uid = -1;
	    }

	    DatabaseTransApi tx = DatabaseTransFactory.create();
	    ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	    try {
		// Retrieve role from the database using unique id.
		UserResourceType type = ResourceFactory.createUserResourceType();
		type.setRsrcTypeId(uid);
		this.data = api.getType(type);
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
    }

    /**
     * Sends a {@link com.bean.UserResourceType UserResourceType} object to the client 
     * via the request object.
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
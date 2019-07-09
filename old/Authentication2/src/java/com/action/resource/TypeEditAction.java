package com.action.resource;

import java.util.List;

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
import com.api.security.user.ResourceApi;
import com.api.security.user.ResourceFactory;

import com.bean.UserResourceType;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the Resource Type edit page.  The following request types are serviced: save 
 * and delete a resource type, and to navigate back to the role list page.
 * 
 * @author Roy Terrell
 * 
 */
public class TypeEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "ResourceType.Edit.save";

    private static final String COMMAND_DELETE = "ResourceType.Edit.delete";

    private static final String COMMAND_BACK = "ResourceType.Edit.back";

    private Logger logger;

    private Object data;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public TypeEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("TypeEditAction");
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
     * Processes the client's requests to save and delete changes made to 
     * a resource type profile, and to navigate back to roles list page.
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
	if (command.equalsIgnoreCase(TypeEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(TypeEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(TypeEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     *  This method is used for adding and modifying resource type profiles.  
     *  Resource type changes are persisted to the database.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	UserResourceType type = (UserResourceType) this.data;
	try {
	    // Update application profile.
	    api.maintainType(type);
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = "Resource Type configuration saved successfully";
	}
	catch (Exception e) {
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
     * Navigates the user to the Resource Type List page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all resource type records from the database using unique id.
	    this.data = api.getType(null);
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
     * Obtains the selected resource type from the user's request.   The resource 
     * type is identified by its unique id and is used to obtain the complete 
     * record of the security role from the database.
     * 
     * @throws ActionHandlerException 
     *          When a required selection was not made or an error occurrence 
     *          during data retrieval. 
     */
    protected void receiveClientData() throws ActionHandlerException {
        try {
            this.data = ResourceFactory.createUserResourceType();
            ResourceFactory.packageBean(this.request, this.data);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionHandlerException(e.getMessage());
        }
    }

    /**
     * Delete a resource type from the database using its unique id.
     * 
     * @return Total number of rows deleted.
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int rc;
	UserResourceType type = (UserResourceType) this.data;
	try {
	    // Update resource type profile.
	    rc = api.deleteType(type.getRsrcTypeId());
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = rc + " securiy role configuration(s) deleted successfully";
	}
	catch (Exception e) {
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
     * Sends a {@link com.bean.UserResourceType UserResourceType} and any server messages 
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
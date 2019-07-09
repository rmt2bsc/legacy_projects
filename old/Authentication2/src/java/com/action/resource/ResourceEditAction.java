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
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.UserResource;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the Resource edit page.  The following request types are serviced: save 
 * and delete a resource, and to navigate back to the resource list page.
 * 
 * @author Roy Terrell
 * 
 */
public class ResourceEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "Resource.Edit.save";

    private static final String COMMAND_DELETE = "Resource.Edit.delete";

    private static final String COMMAND_BACK = "Resource.Edit.back";

    private Logger logger;

    private Object data;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public ResourceEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("ResourceEditAction");
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
     * a resource profile, and to navigate back to resource list page.
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
	if (command.equalsIgnoreCase(ResourceEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(ResourceEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(ResourceEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     *  This method is used for adding and modifying resource profiles.  
     *  Resource changes are persisted to the database.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int key;
	UserResource res = (UserResource) this.data;
	try {
	    // Update application profile.
	    key = api.maintainResource(res);
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = "Resource configuration saved successfully";
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
     * Navigates the user to the Resource List page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all resource records from the database using unique id.
	    this.data = api.getExt(null);
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
     * Obtains the selected resource from the user's request.   The resource 
     * is identified by its unique id and is used to obtain the complete 
     * record of the resource from the database.
     * 
     * @throws ActionHandlerException 
     *          When a required selection was not made or an error occurrence 
     *          during data retrieval. 
     */
    protected void receiveClientData() throws ActionHandlerException {
	int uid;
	String temp = this.getInputValue("Id", null);
	try {
	    uid = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    uid = -1;
	}

	DatabaseTransApi tx = DatabaseTransFactory.create();
        ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            UserResource res = ResourceFactory.createUserResource();
            if (uid > 0) {
                // Retrieve resource from the database using unique id.
        	res.setRsrcId(uid);
                this.data = api.get(res);
                
                // Since resource api returns resource in a List collection, isolate single element.
                if (this.data instanceof List) {
        	    	List list = (List) this.data;
        	    	if (list.size() > 1) {
        	    	    this.msg = "Edit Error: More than one occurrence of the selected item was found to be assoicated with the user\'s selection";
        	    	    logger.log(Level.ERROR, this.msg);
        	    	    throw new ActionHandlerException(this.msg);
        	    	}
        	    	this.data = list.get(0);
                }        	
            }
            else {
        	this.data = res;
            }
            // Created UserResource delta from the request.
            ResourceFactory.packageBean(this.request, this.data);
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
     * Delete a resource from the database using its unique id.
     * 
     * @return Total number of rows deleted.
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
        ResourceApi api = ResourceFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int rc;
	UserResource res = (UserResource) this.data;
	try {
	    // Update resource profile.
	    rc = api.deleteResource(res.getRsrcId());
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = rc + " resource configuration(s) deleted successfully";
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
     * Sends a {@link com.bean.UserResource UserResource} and any 
     * server messages to the user via the request object.
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
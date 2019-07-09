package com.action.groups;

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
import com.api.security.UserAuthenticationException;

import com.api.security.user.ApplicationApi;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.UserGroup;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the user group edit page.  The following request types are 
 * serviced: save user group, delete an user group, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class UserGroupEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "Group.Edit.save";
    
    private static final String COMMAND_DELETE = "Group.Edit.delete";

    private static final String COMMAND_BACK = "Group.Edit.back";

    private Logger logger;

//    private UserApi api;

    private Object data;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public UserGroupEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("UserGroupEditAction");
    }

    /**
     * Performs post initialization and sets up an User Api reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
//	this.api = UserFactory.createApi(this.dbConn, this.request);
    }

    /**
     * Processes the client's requests to save changes made to an user group profile, 
     * delete an user group profile, and to navigate back to User Group List page.
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
	if (command.equalsIgnoreCase(UserGroupEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(UserGroupEditAction.COMMAND_DELETE)) {
 	    this.deleteData();
	}
	if (command.equalsIgnoreCase(UserGroupEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Updates an user group profile by persiting changes to the database. This
     * method is used for adding and modifying user group profiles.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	UserGroup grp = (UserGroup) this.data;
	try {
	    // Update application profile.
	    int key = api.maintainGroup(grp);
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = "User Group configuration saved successfully";
	}
	catch (UserAuthenticationException e) {
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
     * Navigates the user to the User Group List page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Retrieve all application records from the database using unique id.
	    this.data = api.getAllGroups();
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
     * User Group object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	this.data = UserFactory.createUserGroup();
	try {
	    // Update user group object with user input.
	    UserFactory.packageBean(this.request, this.data);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
    }
    
    
    /**
     * Delete an user group from the database using the id of the user group.
     * 
     * @param appId  The id of the user group
     * @return Total number of rows deleted.
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi api = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int rc;
	UserGroup grp = (UserGroup) this.data;
	try {
	    // Update user group profile.
	    rc = api.deleteGroup(grp.getGrpId());
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = rc + " application configuration(s) deleted successfully";
	}
	catch (UserAuthenticationException e) {
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
     * Sends an {@link com.bean.UserGroup UserGroup} data object and any server messages 
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
package com.action.approle;

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
import com.api.security.UserAuthenticationException;
import com.api.security.user.ApplicationApi;
import com.api.security.user.ApplicationException;
import com.api.security.user.SecurityConst;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.bean.AppRole;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * Action handler provides functionality to respond to requests pertaining 
 * to the Applicaton Roles edit page.  The following request types are 
 * serviced: save, delete an application role, and to navigate back to the 
 * application role list page.
 * 
 * @author Roy Terrell
 * 
 */
public class AppRoleEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "AppRole.Edit.save";

    private static final String COMMAND_DELETE = "AppRole.Edit.delete";

    private Logger logger;

//    private ApplicationApi api;

    private Object data;

    private List appList;

    private List roleList;
    
    private Object selectedRole;
    
    private Object selectedApp;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AppRoleEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("AppRoleEditAction");
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
     * Processes the client's requests to save and delete changes made to 
     * an application role profile, and to navigate back to application 
     * roles list page.
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
	if (command.equalsIgnoreCase(AppRoleEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(AppRoleEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
    }

    /**
     *  Saves an application role to the database.
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int key;
	AppRole obj = (AppRole) this.data;
	try {
	    // Update application profile.
	    key = api.maintainAppRole(obj);
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = "Application Role configuration saved successfully";
	}
	catch (ApplicationException e) {
	    this.msg = e.getMessage();
	    tx.rollbackUOW();
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Obtains the selected application role from the user's request.   
     * The application role is identified by its unique id and is used 
     * to obtain the complete record of the application role from the 
     * database.
     * 
     * @throws ActionHandlerException 
     *          When a required selection was not made or an error occurrence 
     *          during data retrieval. 
     */
    protected void receiveClientData() throws ActionHandlerException {
	int uid;
	String temp = this.getInputValue("AppRoleId", null);
	try {
	    uid = Integer.parseInt(temp);
	}
	catch (NumberFormatException e) {
	    uid = -1;
	}

	DatabaseTransApi tx = null;
        ApplicationApi api = null;
	try {
	    // Retrieve an application role from the database using unique id.
	    tx = DatabaseTransFactory.create();
	    api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	    this.data = api.getAppRole(uid);
	    if (this.data == null) {
		this.data = UserFactory.createAppRole();
	    }
	    // Update an application role object with user input.
	    UserFactory.packageBean(this.request, this.data);
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Retreives instances of the selected {@link com.bean.Application Application} and 
     * {@link com.bean.Role Role}
     * 
     * @throws ActionHandlerException
     */
    private void getCodeData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    AppRole appRole = (AppRole) this.data;
	    this.selectedApp = api.findApp(appRole.getAppId());
	    this.selectedRole = userApi.getRole(appRole.getRoleId());
	}
	catch (UserAuthenticationException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    api = null;
	    userApi.close();
	    userApi = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Delete an application role from the database using its unique id.
     * 
     * @param appId  The id of the user group
     * @return Total number of rows deleted.
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int rc;
	AppRole obj = (AppRole) this.data;
	try {
	    // Update user group profile.
	    rc = api.deleteAppRole(obj.getAppRoleId());
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = rc + " application role configuration(s) deleted successfully";
	}
	catch (ApplicationException e) {
	    this.msg = "Problem occurred deleting application role profile.  Additional information: " + e.getMessage();
	    tx.rollbackUOW();
	}
	finally {
	    api.close();
	    api = null;
	    tx.close();
	    tx = null;
	}
    }

    /**
     * Sends a {@link com.bean.AppRole AppRole} and any server messages 
     * to the user via the request object.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.getCodeData();
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.data);
	this.request.setAttribute(SecurityConst.APP_LIST, this.appList);
	this.request.setAttribute(SecurityConst.ROLE_LIST, this.roleList);
	this.request.setAttribute("selectedApp", this.selectedApp);
	this.request.setAttribute("selectedRole", this.selectedRole);
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
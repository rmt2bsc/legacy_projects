package com.action.user;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.security.UserAuthenticationException;
import com.api.security.user.ApplicationApi;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.api.security.user.UserMaintException;

import com.bean.AppRole;
import com.bean.Application;
import com.bean.RMT2TagQueryBean;
import com.bean.UserLogin;
import com.bean.db.DatabaseConnectionBean;

import com.constants.UserConst;

import com.controller.Request;
import com.controller.Response;
import com.controller.Context;

import com.util.SystemException;

import com.constants.RMT2ServletConst;

/**
 * This class provides action handlers to respond to User add and edit requests.
 * 
 * @author Roy Terrell
 * 
 */
public class UserEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "User.Edit.save";

    private static final String COMMAND_BACK = "User.Edit.back";

    private static final String COMMAND_DELETE = "User.Edit.delete";

    private static final String COMMAND_APPROLE = "User.Edit.approle";
    
    private static final String COMMAND_RESOURCE = "User.Edit.resources";

    private Logger logger;

    private UserLogin user;

    private Object apps;

    private Object assignedRoles;

    private Object revokedRoles;
    
    private String selectedApp;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public UserEditAction() throws SystemException {
	super();
	logger = Logger.getLogger("UserEditAction");
    }

    /**
     * Initializes the UserApi assoicated with this class.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
	super.init(_context, _request);
    }

    /**
     * Processes the client's requests to save the changes made to a user's
     * profile, delete a user's profile, and to navigate back to the User Search
     * page.
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
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);

	if (command.equalsIgnoreCase(UserEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(UserEditAction.COMMAND_DELETE)) {
	    this.deleteData();
	}
	if (command.equalsIgnoreCase(UserEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(UserEditAction.COMMAND_APPROLE)) {
	    this.doAppRole();
	}
	if (command.equalsIgnoreCase(UserEditAction.COMMAND_RESOURCE)) {
	    this.doResources();
	}
    }

    /**
     * Updates the user's profile by persiting changes to the database. This
     * method is used for adding users as well as modifying users.
     * <p>
     * Psuedo edit logic to replace existing code.
     * <ol>
     * <li>Get Key values from the http request object for each entity to be
     * used with its respectie API.</li>
     * <li>Retrieve data from the database into each entity object based on the
     * key value(s) representing each entity.</li>
     * <li>Set the properties of each object to the corressponding values from
     * the http request object.</li>
     * <li>Apply changes to the database for each object using its own API.</li>
     * <li>Package any data needed to be returned to the client.</li>
     * <li>Return control back to the Servlet.</li>
     * </ol>
     * 
     * @throws ActionHandlerException
     */
    public void save() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	int key;
	try {
	    this.validateUser(user);
	    key = userApi.maintainUser(this.user);
	    this.user.setLoginId(key);
	    // Commit Changes to the database
	    tx.commitUOW();
	    this.msg = "User saved successfully";
	}
	catch (UserMaintException e) {
	    this.msg = "UserMaintException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	catch (UserAuthenticationException e) {
	    this.msg = "UserAuthenticationException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	 finally {
	     userApi.close();
	     tx.close();
	     userApi = null;
	     tx = null;
	 }
    }

    /**
     * Deletes a user from the database.
     * 
     * @throws ActionHandlerException
     */
    public void delete() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    userApi.inActivateUser(this.user);
	    tx.commitUOW();
	    this.msg = "User active status is cancelled";
	}
	catch (UserAuthenticationException e) {
	    this.msg = "UserAuthenticationException: " + e.getMessage();
	    logger.log(Level.ERROR, this.msg);
	    tx.rollbackUOW();
	    throw new ActionHandlerException(this.msg);
	}
	 finally {
            userApi.close();
	    tx.close();
	    userApi = null;
	    tx = null;
	}
	return;
    }

    /**
     * Navigates the user to the previous page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
	return;
    }

    /**
     * Gathers all data needed to populate User Application-Role page.
     * 
     * @throws ActionHandlerException
     */
    protected void doAppRole() throws ActionHandlerException {
	this.receiveClientData();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi appApi = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.apps = appApi.getAllApps();

	    // setup user criteria
	    UserLogin userCriteria = UserFactory.createUserLogin();
	    userCriteria.setLoginId(this.user.getLoginId());

	    // Setup application-role criteria
	    AppRole appRoleCriteria = UserFactory.createAppRole();
	    if (this.apps != null) {
		Application app = (Application) ((List) this.apps).get(0);
		appRoleCriteria.setAppId(app.getAppId());
		this.selectedApp = String.valueOf(app.getAppId());
	    }
	    this.assignedRoles = appApi.getAppRoleAssigned(userCriteria, appRoleCriteria);
	    this.revokedRoles = appApi.getAppRoleRevoked(userCriteria, appRoleCriteria);
	}
	catch (UserAuthenticationException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.sendClientData();
	    appApi.close();
	    tx.close();
	    appApi = null;
	    tx = null;
	}
	return;
    }
    
    
    protected void doResources() throws ActionHandlerException {
	try {
	    UserResourceAccessEditAction delegate = new UserResourceAccessEditAction();
	    delegate.processRequest(this.request, this.response, UserResourceAccessEditAction.COMMAND_FIRSTLOAD);
	}
	catch (SystemException e) {
	    this.logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e);
	}
	
    }

    /**
     * Gathers data from the user's request and packages the data into a
     * UserLogin object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	try {
	    // Retrieve values from the request object into the User object.
	    this.user = UserFactory.createUserLogin();
	    UserFactory.packageBean(this.request, this.user);
	}
	catch (SystemException e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new ActionHandlerException(e.getMessage());
	}
    }

    /**
     * Sends a UserLogin data object and any server messages to the user via the
     * request object.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(UserConst.CLIENT_DATA_USER, this.user);
	this.request.setAttribute("apps", this.apps);
	this.request.setAttribute("selectedApp", this.selectedApp);
	this.request.setAttribute("assignedRoles", this.assignedRoles);
	this.request.setAttribute("revokedRoles", this.revokedRoles);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * This method is responsble for validating a user's profile. The login id,
     * first name, last name, birth date, start date, and social security number
     * are required to have values. Also, the user's login Id is only validated
     * when the user is new.
     * 
     * @param user
     *            {@link User} - user's credentials
     * @throws UserMaintException
     */
    protected void validateUser(UserLogin user) throws UserMaintException {
	// User's login code only needs to be validated if user is new
	if (user.getLoginId() == 0) {
	    this.msg = "User Maintenance: Login cannot be blank";
	    if (user.getUsername() == null || user.getUsername().length() <= 0) {
		throw new UserMaintException(this.msg);
	    }
	}
	// Validate First Name
	if (user.getFirstname() == null || user.getFirstname().length() <= 0) {
	    this.msg = "User Maintenance: First Name cannot be blank";
	    throw new UserMaintException(this.msg);
	}

	// Validate Last Name
	if (user.getLastname() == null || user.getLastname().length() <= 0) {
	    this.msg = "User Maintenance: Last Name cannot be blank";
	    throw new UserMaintException(this.msg);
	}

	// Validate Title
	if (user.getSsn() == null || user.getSsn().length() <= 0) {
	    this.msg = "User Maintenance: Social Security Number cannot be blank";
	    throw new UserMaintException(this.msg);
	}

	if (user.getBirthDate() == null) {
	    this.msg = "User Maintenance: Birth date cannot be blank";
	    throw new UserMaintException(this.msg);
	}

	if (user.getStartDate() == null) {
	    this.msg = "User Maintenance: Start date cannot be blank";
	    throw new UserMaintException(this.msg);
	}
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
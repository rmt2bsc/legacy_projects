package com.action.user;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.messaging.MessageException;
import com.api.messaging.MessagingException;
import com.api.messaging.ProviderConfig;
import com.api.messaging.ProviderConnectionException;
import com.api.messaging.ResourceFactory;
import com.api.messaging.webservice.http.client.HttpClientMessageException;
import com.api.messaging.webservice.http.client.HttpClientResourceFactory;
import com.api.messaging.webservice.http.client.HttpMessageSender;
import com.api.security.UserAuthenticationException;
import com.api.security.user.ApplicationApi;
import com.api.security.user.ApplicationException;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

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
public class UserAppRoleEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "UserAppRole.Edit.save";

    private static final String COMMAND_BACK = "UserAppRole.Edit.back";
    
    private static final String COMMAND_USER_APP_ROLES = "UserAppRole.Edit.RQ_authentication_user_app_roles";
    
    private Logger logger;

    private UserLogin user;

    private Object apps;

    private int appId;

    private String revokedRoles[];

    private String assignedRoles[];

    private Object assignedRoleObjs;

    private Object revokedRoleObjs;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public UserAppRoleEditAction() throws SystemException {
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

        if (command.equalsIgnoreCase(UserAppRoleEditAction.COMMAND_SAVE)) {
            this.saveData();
        }
        if (command.equalsIgnoreCase(UserAppRoleEditAction.COMMAND_USER_APP_ROLES)) {
            this.getUserRoleData();
        }
        if (command.equalsIgnoreCase(UserAppRoleEditAction.COMMAND_BACK)) {
            this.doBack();
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
        ApplicationApi appApi = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        int rows;
        try {
            rows = appApi.maintainUserAppRole(this.user.getUsername(), this.appId, this.assignedRoles, this.revokedRoles);
            // Commit Changes to the database
            tx.commitUOW();
            this.msg = rows + "User Application Roles were added successfully";
            // Refresh client presentation data
            this.getAllUserAppRoles();
        }
        catch (ApplicationException e) {
            tx.rollbackUOW();
            throw new ActionHandlerException(e);
        }
        finally {
            appApi.close();
	    tx.close();
	    appApi = null;
	    tx = null;
	}
    }

    /**
     * Navigates the user to the previous page.
     * 
     * @throws ActionHandlerException
     */
    protected void doBack() throws ActionHandlerException {
        this.receiveClientData();
        this.sendClientData();
        return;
    }

    /**
     * Gathers data from the user's request and packages the data into a
     * UserLogin object.
     * 
     * @throws ActionHandlerException
     */
    protected void receiveClientData() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        // Retrieve values from the request object into the User object.
        try {
            this.user = UserFactory.createUserLogin();
            UserFactory.packageBean(this.request, this.user);
            // Get all data pertaining to user from the database.
            this.user = (UserLogin) userApi.findUserByUserName(this.user.getUsername());
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionHandlerException(e.getMessage());
        }
        finally {
            userApi.close();
	    tx.close();
	    userApi = null;
	    tx = null;
	}

        // Get application id.
        try {
            this.appId = Integer.parseInt(this.request.getParameter("ApplicationId"));
        }
        catch (NumberFormatException e) {
            this.appId = 0;
        }

        // Get role selections
        this.revokedRoles = this.request.getParameterValues("RevokedRoleId");
        this.assignedRoles = this.request.getParameterValues("AssignedRoleId");
    }

    /**
     * Sends a UserLogin data object and any server messages to the user via the
     * request object.
     * 
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
        this.request.setAttribute(UserConst.CLIENT_DATA_USER, this.user);
        this.request.setAttribute("selectedApp", String.valueOf(this.appId));
        this.request.setAttribute("apps", this.apps);
        this.request.setAttribute("assignedRoles", this.assignedRoleObjs);
        this.request.setAttribute("revokedRoles", this.revokedRoleObjs);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    
    /**
     * Gathers all data needed to populate User Application-Role page 
     * as a confirmation to the <i>save</i> request.
     * 
     * @throws ActionHandlerException
     */
    private void getAllUserAppRoles() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi appApi = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            this.apps = appApi.getAllApps();

            // setup user criteria
            UserLogin userCriteria = UserFactory.createUserLogin();
            userCriteria.setLoginId(this.user.getLoginId());

            // Setup application-role criteria
            AppRole appRoleCriteria = UserFactory.createAppRole();
    	    // Get object that matches the current selected application            
            if (this.apps != null) {
        	// Initialize app to be the first element in the event 
        	// the select application object is not found.
        	Application app = (Application) ((List <Application>) this.apps).get(0);
        	int ndx = 0;
        	for (Object obj : (List <Application>) this.apps) {
        	    if (((Application) obj).getAppId() == this.appId) {
        		app = (Application) ((List <Application>) this.apps).get(ndx);
        		break;
        	    }
        	    ndx++;
        	}
                appRoleCriteria.setAppId(app.getAppId());
            }
            this.assignedRoleObjs = appApi.getAppRoleAssigned(userCriteria, appRoleCriteria);
            this.revokedRoleObjs = appApi.getAppRoleRevoked(userCriteria, appRoleCriteria);
        }
        catch (UserAuthenticationException e) {
            throw new ActionHandlerException(e);
        }
        finally {
            appApi.close();
	    tx.close();
	    appApi = null;
	    tx = null;
	}
        return;
    }

    private void getUserRoleData() throws ActionHandlerException {
      HttpMessageSender client = HttpClientResourceFactory.getHttpInstance();
      ProviderConfig config;
      try {
          config = ResourceFactory.getHttpConfigInstance();
          client.connect(config);
          Object result = client.sendMessage(this.request);
          this.request.setAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA, result);
          return;
      }
       catch (MessagingException e) {
           e.printStackTrace();
           throw new ActionHandlerException(e);
       }
       catch (ProviderConnectionException e) {
           e.printStackTrace();
           throw new ActionHandlerException(e);
       }
       catch (HttpClientMessageException e) {
           e.printStackTrace();
           throw new ActionHandlerException(e);
       }
       catch (MessageException e) {
           e.printStackTrace();
           throw new ActionHandlerException(e);
       }
  }
    
    /**
     * No Action
     */
    public void delete() throws ActionHandlerException {
        return;
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
package com.action.approle;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.List;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceAdapter;

import com.bean.AppRole;
import com.bean.RMT2TagQueryBean;

import com.bean.criteria.AppRoleCriteria;
import com.bean.db.DatabaseConnectionBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;

import com.api.security.UserAuthenticationException;
import com.api.security.authentication.RMT2SessionBean;

import com.api.security.user.ApplicationApi;
import com.api.security.user.SecurityConst;
import com.api.security.user.UserApi;
import com.api.security.user.UserFactory;

import com.util.SystemException;

/**
 * This class provides action handlers to respond to the client's commands from
 * the AppRoleSearch.jsp.   The actions implemented are new search, search, list, 
 * add, and edit.
 * 
 * @author Roy Terrell
 * 
 */
public class AppRoleSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_NEW = "AppRole.Search.new";

    private static final String COMMAND_SEARCH = "AppRole.Search.search";

    private static final String COMMAND_LIST = "AppRole.Search.list";

    private static final String COMMAND_ADD = "AppRole.Search.add";

    private static final String COMMAND_EDIT = "AppRole.Search.edit";

//    private ApplicationApi api;

    private AppRole appRole;

    private Logger logger;

    private List appRoleList;

    private List appList;

    private List roleList;
    
    private Object selectedRole;
    
    private Object selectedApp;

    RMT2SessionBean userSession;

    /**
     * Default constructor.
     * 
     */
    public AppRoleSearchAction() {
	logger = Logger.getLogger("AppRoleSearchAction");
    }

    /**
     * Processes the New Search, Add, Edit, and List commands issued
     * from the AppRoleSearch.jsp
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
	try {
	    super.processRequest(request, response, command);
//	    this.api = UserFactory.createAppApi(this.dbConn, this.request);
	    this.userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

	    if (command.equalsIgnoreCase(AppRoleSearchAction.COMMAND_NEW)) {
		this.doNewSearch();
	    }
	    else if (command.equalsIgnoreCase(AppRoleSearchAction.COMMAND_SEARCH)) {
		this.doSearch();
	    }
	    else if (command.equalsIgnoreCase(AppRoleSearchAction.COMMAND_LIST)) {
		this.doList();
	    }
	    else if (command.equalsIgnoreCase(AppRoleSearchAction.COMMAND_ADD)) {
		this.addData();
	    }
	    else if (command.equalsIgnoreCase(AppRoleSearchAction.COMMAND_EDIT)) {
		this.editData();
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    // Ensure that any update made to the the query object is set on the
	    // session.
	    if (this.query != null) {
		this.request.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
	    }
	}
    }

    /**
     * Performs a search for application roles for the first time which 
     * will purposely yield an empty result set .
     * 
     * @throws ActionHandlerException
     */
    protected void doNewSearch() throws ActionHandlerException {
	this.startSearchConsole();
	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	this.getCodeData();
	this.appRoleList = new ArrayList();
	this.sendClientData();
    }

    /**
     * Force the an empty result set by adding the SQL predicate of app_role_id = -1. 
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
	return "app_role_id = -1";
    }

    /**
     * Builds the search criteria that is to be used to retrieve the timesheets
     * requested by the client. Once the selection criteria is computed, it is
     * stored on the session object and is identified as
     * {@link RMT2ServletConst.QUERY_BEAN}.
     * 
     * @throws ActionHandlerException
     *             if a database access error occurs.
     */
    protected void doSearch() throws ActionHandlerException {
	this.buildSearchCriteria();
    }

    /**
     * Creates the {@link com.bean.criteria.AppRoleCriteria AppRoleCriteria} object 
     * and attempts to obtain the criteria data from the client's request.
     * 
     * @return Object The criteria object
     * @throws ActionHandlerException
     *             Error transferring client data to the criteria object.
     */
    protected Object doCustomInitialization() throws ActionHandlerException {
	this.setBaseClass("com.bean.VwAppRoles");
	this.setBaseView("VwAppRolesView");
	AppRoleCriteria criteriaObj = AppRoleCriteria.getInstance();
	if (!this.isFirstTime()) {
	    try {
		DataSourceAdapter.packageBean(this.request, criteriaObj);
	    }
	    catch (SystemException e) {
		this.msg = "Problem gathering Application-Role Search request parameters:  " + e.getMessage();
		logger.log(Level.ERROR, this.msg);
		throw new ActionHandlerException(this.msg);
	    }
	}
	return criteriaObj;
    }

    /**
     * Declares the results of the timesheet query are orderd by end period and
     * timesheet id, which both fields are in descending order.
     * 
     */
    protected void doOrderByClause(RMT2TagQueryBean query) {
	String orderBy = query.getOrderByClause();
	if (orderBy == null) {
	    orderBy = " app_name, role_name, app_role_code ";
	    query.setOrderByClause(orderBy);
	}
	return;
    }

    /**
     * Queries the database using the selection criteria provided by the user to
     * obtain one or more application roles.
     * 
     * @throws ActionHandlerException General database errors
     */
    protected void doList() throws ActionHandlerException {
	String criteria = null;
	String orderBy = null;

	// Get selection criteria from session object
	criteria = this.query.getWhereClause();
	orderBy = this.query.getOrderByClause();

	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.setBaseClass("com.bean.VwAppRoles");
	    api.setBaseView("VwAppRolesView");
	    this.appRoleList = api.findData(criteria, orderBy);
	    this.getCodeData();

	    // Send data to client
	    this.sendClientData();
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
     * Gather master data lists for Roles and Applications to be used by the client.
     * 
     * @throws ActionHandlerException
     */
    private void getCodeData() throws ActionHandlerException {
	if (command.equalsIgnoreCase(AppRoleSearchAction.COMMAND_NEW) || command.equalsIgnoreCase(AppRoleSearchAction.COMMAND_LIST)) {
	    DatabaseTransApi tx = DatabaseTransFactory.create();
	    ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	    try {
		this.appList = (List) api.getAllApps();
		this.roleList = (List) api.getAllRoles();
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
	return;
    }

    /**
     * Prepares to send a new {@link com.bean.AppRole AppRole} to the assist 
     * the client in gathering data for an add transaction. 
     * 
     * @throws ActionHandlerException if a database access error occurs.
     */
    public void add() throws ActionHandlerException {
	this.appRole = UserFactory.createAppRole();
	this.getCodeData();
	return;
    }

    /**
     * Retreives instances of the selected {@link com.bean.Application Application} and 
     * {@link com.bean.Role Role}
     * 
     * @throws ActionHandlerException
     */
    public void edit() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	UserApi userApi = UserFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.selectedApp = api.findApp(this.appRole.getAppId());
	    this.selectedRole = userApi.getRole(this.appRole.getRoleId());
	    return;
	}
	catch (UserAuthenticationException e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    /**
     * No action.
     * 
     * @throws ActionHandlerException
     * @throws DatabaseException
     */
    public void save() throws ActionHandlerException, DatabaseException {
	return;
    }

    /**
     * Stubbed
     * 
     * @throws ActionHandlerException
     *             if a problem occurred deleting the Project.
     * @throws DatabaseException
     *             when the trasnaction changes fail to commit or rollback.
     */
    public void delete() throws ActionHandlerException, DatabaseException {
	return;
    }

    /**
     * Obtains the selected application role from the user's request for editing.
     * 
     */
    protected void receiveClientData() throws ActionHandlerException {
	String temp = null;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	ApplicationApi api = UserFactory.createAppApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    temp = this.getInputValue("AppRoleId", null);
	    int uid;
	    try {
		uid = Integer.parseInt(temp);
	    }
	    catch (NumberFormatException e) {
		uid = 0;
	    }
	    this.appRole = (AppRole) api.getAppRole(uid);
	}
	catch (Exception e) {
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
     * Initialize request object with data to be sent to the client.  The following 
     * list dipicts the java objects and their aossociated names that are stored in 
     * the HttpServletRequest object when transit between the server and the client: 
     * <table border="1">
     * <tr>
     * <th align="left"><strong>Java Data Object</strong></th>
     * <th><strong>Id on client</strong></th>
     * </tr>
     * <tr>
     * <td>Application Role object</td>
     * <td>GeneralConst.CLIENT_DATA_RECORD</td>
     * </tr>
     * <tr>
     * <td>A list of application role objects</td>
     * <td>GeneralConst.CLIENT_DATA_LIST</td>
     * </tr>
     * <tr>
     * <td>A list of applications</td>
     * <td>SecurityConst.APP_LIST</td>
     * </tr>
     * <tr>
     * <td>A list of roles</td>
     * <td>SecurityConst.ROLE_LIST</td>
     * </tr>
     * </table>
     * 
     * @throws ActionHandlerException
     * @see {@link TimesheetEditAction#refreshClientData(int)}
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.appRole);
	this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.appRoleList);
	this.request.setAttribute(SecurityConst.APP_LIST, this.appList);
	this.request.setAttribute(SecurityConst.ROLE_LIST, this.roleList);
	this.request.setAttribute("selectedApp", this.selectedApp);
	this.request.setAttribute("selectedRole", this.selectedRole);
	return;
    }

}
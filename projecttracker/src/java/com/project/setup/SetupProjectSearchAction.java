package com.project.setup;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.ProjProject;
import com.bean.ProjClient;
import com.bean.db.DatabaseConnectionBean;

import com.project.ProjectConst;
import com.project.ProjectException;

/**
 * This class provides action handlers to respond to the client's commands from 
 * the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages. 
 * 
 * @author Roy Terrell
 *
 */
public class SetupProjectSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "ProjectMaint.List.add";

    private static final String COMMAND_EDIT = "ProjectMaint.List.edit";

    private static final String COMMAND_DELETE = "ProjectMaint.List.delete";

    private static final String COMMAND_LIST = "ProjectMaint.List.list";

    private static final String COMMAND_BACK = "ProjectMaint.List.back";

    private Logger logger;

    private ProjProject project;

    private ProjClient client;

    private List clients;

    private List projects;

    private int projectId;

    /**
     * Default constructor.
     *
     */
    public SetupProjectSearchAction() {
	this.logger = Logger.getLogger("SetupProjectSearchAction");
	this.logger.log(Level.INFO, "Logger created");
	this.project = null;
	this.client = null;
    }

    /**
     * Processes the Add, Edit, Delete, Save, Back, and List commands issued from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	try {
	    this.init(null, request);
	    if (command.equalsIgnoreCase(SetupProjectSearchAction.COMMAND_ADD)) {
		this.addData();
	    }
	    else if (command.equalsIgnoreCase(SetupProjectSearchAction.COMMAND_EDIT)) {
		this.editData();
	    }
	    else if (command.equalsIgnoreCase(SetupProjectSearchAction.COMMAND_DELETE)) {
		this.deleteData();
	    }
	    else if (command.equalsIgnoreCase(SetupProjectSearchAction.COMMAND_BACK)) {
		this.doBack();
	    }
	    else if (command.equalsIgnoreCase(SetupProjectSearchAction.COMMAND_LIST)) {
		this.listAllProjects();
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Obtains a new {@link ProjProject} object and prepares to send the objects as a response to the client 
     * via the Requst object.  The new ProjProject object is identified on the request object as, "project".
     
     * @throws ActionHandlerException if a database access error occurs.
     */
    public void add() throws ActionHandlerException {
	// Create new data objects and send to client.
	this.projectId = 0;
	this.project = SetupFactory.createProject();
	this.clients = this.getClientExtList();
	return;
    }

    /**
     * Obtains a {@link ProjProject} object from the using the project id selected by the client and prepares to send the object as a response 
     * to the client via the Requst object.  The selected ProjProject object is identified on the request object as, "project".
     * 
     * @throws ActionHandlerException If a database access error occurs or Project was not found using the selected project id.
     */
    public void edit() throws ActionHandlerException {
	try {
	    this.project = this.getExistingProject(this.projectId);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}
	return;
    }

    /**
     * Attempts to delete a project from the database.
     * 
     * @throws ActionHandlerException if a problem occurred deleting the Project.
     * @throws DatabaseException when the trasnaction changes fail to commit or rollback.
     */
    public void delete() throws ActionHandlerException, DatabaseException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.deleteProject(this.projectId);
	    tx.commitUOW();
	    this.msg = "Project deleted successfully";
	    this.listAllProjects();
	}
	catch (Exception e) {
	    tx.rollbackUOW();
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
     * Retreives all project data that pertains to the selected row from the client's 
     * request and packages the data into the following objects to be processed: 
     * {@link ProjProject}, {@link ProjClient},  and an ArrayList of 
     * {@link ProjProjectEmployee}.
     *
     */
    protected void receiveClientData() throws ActionHandlerException {
	// Get Project data
	try {
	    this.projectId = this.getSelectedRow("ProjId");
	    this.project = this.getExistingProject(this.projectId);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e.getMessage());
	}

	// Get Client data
	this.client = SetupFactory.createClient();
	this.client.setClientId(this.project.getClientId());

	// Get list of clients
	this.clients = this.getClientExtList();
    }

    /**
     * Retrieves project details and packages the data into the request to be sent to the 
     * client.    If the project exist then the data is obtained from the database.    
     * Otherwise, new data objects are instaintiated and sent to the clinet.
     * 
     * @param _projectId  >0 for existing projects and =0 for nex projects
     * @throws ActionHandlerException
     */
    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(ProjectConst.CLIENT_DATA_PROJECTS, this.projects);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_PROJECT, this.project);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENTS, this.clients);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_CLIENT, this.client);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
	return;
    }

    /**
     * Retreives all project data from the database and packages the data into the request 
     * with the intent of sending to the client as a response.   {@link ProjProject}, 
     * {@link CustomerDetails},  and an ArrayList of {@link ProjProject} are set on the 
     * request as attributes and are identified as "project" and "customer", respectively.
     *   
     * @param projectId The id of the project to retrieve data.
     * @throws ActionHandlerException when the project is found not to exist in the system 
     * using projectId, or the project fails to demonstrate an assoication with a client, 
     * or the occurrence of a database access error.
     */
    private ProjProject getExistingProject(int projectId) throws ActionHandlerException {
	ProjProject pp = null;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    // Get project data
	    pp = (ProjProject) api.findProject(projectId);
	    if (pp == null) {
		throw new ActionHandlerException("Project does not exist in the system");
	    }
	    return pp;
	}
	catch (ProjectException e) {
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
     * Obtains a master list of Task objects and prepares to send the objects as a response to the client 
     * via the Requst object.  The list of objects are identified on the request object as, "list".
     *  
     * @throws ActionHandlerException if a database access error occurs.
     */
    protected void listAllProjects() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.projects = (List) api.findAllProjects();
	    if (this.projects == null) {
		this.projects = new ArrayList();
	    }
	    this.sendClientData();
	}
	catch (Exception e) {
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
     * Retrieves a master list of clients to be used as a dropdown on the Project Maintenance Edit page.
     * 
     * @return An ArrayList of {@link CustomerDetails} objects
     * @throws ActionHandlerException
     */
    private List getClientList() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    List list = (List) api.findAllClients();
	    return list;
	}
	catch (ProjectException e) {
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
     * 
     * @return
     * @throws ActionHandlerException
     */
    private List <ProjClient> getClientExtList() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    List <ProjClient> list = (List<ProjClient>) api.findClientExt();
	    return list;
	}
	catch (ProjectException e) {
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
     * Stub method.
     * 
     * @throws ActionHandlerException
     * @throws DatabaseException
     */
    public void save() throws ActionHandlerException, DatabaseException {
	return;
    }

}
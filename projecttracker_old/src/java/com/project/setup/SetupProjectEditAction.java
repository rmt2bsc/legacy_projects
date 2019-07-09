package com.project.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.ProjClient;
import com.bean.ProjProject;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;

import com.project.ProjectConst;
import com.project.ProjectException;




/**
 * This class provides action handlers to respond to the client's commands from
 * the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages.
 * 
 * @author Roy Terrell
 * 
 */
public class SetupProjectEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "ProjectMaint.Edit.save";

    private static final String COMMAND_BACK = "ProjectMaint.Edit.back";

    private Logger logger;

    private ProjProject project;

    private ProjClient client;

    private List<ProjClient> clients;

    private List projects;


    /**
     * Default constructor.
     * 
     */
    public SetupProjectEditAction() {
	this.logger = Logger.getLogger("SetupProjectEditAction");
	this.logger.log(Level.INFO, "Logger created");
    }

    /**
     * Processes the Add, Edit, Delete, Save, Back, and List commands issued
     * from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages.
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
	    this.init(null, request);
	    if (command.equalsIgnoreCase(SetupProjectEditAction.COMMAND_SAVE)) {
		this.saveData();
	    }
	    else if (command.equalsIgnoreCase(SetupProjectEditAction.COMMAND_BACK)) {
		this.doBack();
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Obtains a master list of Task objects and prepares to send the objects as
     * a response to the client via the Requst object. The list of objects are
     * identified on the request object as, "list".
     * 
     * @throws ActionHandlerException
     *             if a database access error occurs.
     */
    private void listAllProjects() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.projects = (List) api.findAllProjects();
	    if (this.projects == null) {
		this.projects = new ArrayList();
	    }
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
     * Accepts the {@link ProjProject} object from the client and applies any
     * modifications to the database.
     * 
     * @throws ActionHandlerException
     *             if a problem occurred updating the Project.
     * @throws DatabaseException
     *             when the trasnaction changes fail to commit or rollback.
     */
    public void save() throws ActionHandlerException, DatabaseException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.maintainProject(this.project, this.client);
	    tx.commitUOW();
	    this.msg = "Project saved successfully";
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
		api.close();
		api = null;
		tx.close();
		tx = null;
	}
    }

    /**
     * Obtains a listing of all projects and navigates the user back to 
     * the Project List page.
     *  
     * @throws ActionHandlerException.
     */
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	this.listAllProjects();
	this.sendClientData();
    }

    /**
     * Retreives all project data from the client's request and packages the
     * data into the following objects to be processed:* {@link ProjProject},
     * {@link ProjClient}, and an ArrayList of {@link ProjProjectEmployee}.
     * 
     * @throws ActionHandlerException.
     */
    protected void receiveClientData() throws ActionHandlerException {
	// Get Project data
	this.project = SetupFactory.createProject();
	try {
	    SetupFactory.packageBean(this.request, this.project);
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}

	// Get Client data
	this.client = SetupFactory.createClient();
	this.client.setClientId(this.project.getClientId());

	// Get list of clients
	this.clients = this.getClientList();
    }

    /**
     * Retrieves project details and packages the data into the request to be
     * sent to the client. If the project exist then the data is obtained from
     * the database. Otherwise, new data objects are instaintiated and sent to
     * the clinet.
     * 
     * @param _projectId
     *            >0 for existing projects and =0 for nex projects
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
     * Retrieves a master list of clients to be used as a dropdown on the
     * Project Maintenance Edit page.
     * 
     * @return An List of {@link ProjClient} objects
     * @throws ActionHandlerException
     */
    private List<ProjClient> getClientList() throws ActionHandlerException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    List<ProjClient> list = (List<ProjClient>) api.findClientExt();
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
    


    /*
     * (non-Javadoc)
     * 
     * @see com.action.ICommonAction#add()
     */
    public void add() throws ActionHandlerException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
	// TODO Auto-generated method stub

    }

}
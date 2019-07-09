package com.action.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.ProjectSetupApi;
import com.api.db.DatabaseException;

import com.bean.ProjProject;
import com.bean.ProjClient;
import com.bean.VwCustomerName;

import com.factory.ProjectManagerFactory;

import com.util.ProjectException;
import com.util.SystemException;



/**
 * This class provides action handlers to respond to the client's commands from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages. 
 * 
 * @author Roy Terrell
 *
 */
public class ProjectAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "Project.ProjectMaintList.add";
    private static final String COMMAND_EDIT = "Project.ProjectMaintList.edit";
    private static final String COMMAND_DELETE = "Project.ProjectMaintList.delete";
    private static final String COMMAND_LIST = "Project.ProjectMaintList.list";
    private static final String COMMAND_SAVE = "Project.ProjectMaintEdit.save";
    private static final String COMMAND_BACK = "Project.ProjectMaintEdit.back";
    
    private ProjectSetupApi api;
    private Logger logger;
    private ProjProject project;
    private ProjClient client;
    private VwCustomerName customer;
    private int projectId;
    
	
	/**
	 * Default constructor.
	 *
	 */
    public ProjectAction() {
    	logger = Logger.getLogger("ProjectAction");
    	this.project = null;
    	this.client = null;
    	this.customer = null;
    }
    
 
    /**
     * Processes the Add, Edit, Delete, Save, Back, and List commands issued from the ProjectMaintList.jsp and ProjectMaintEdit.jsp pages.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
	  try {
		  this.init(null, request);
          this.api = ProjectManagerFactory.createProjectSetupApi(this.dbConn, this.request);  
		  if (command.equalsIgnoreCase(ProjectAction.COMMAND_ADD)) {
			  this.addData();
		  }
		  else if (command.equalsIgnoreCase(ProjectAction.COMMAND_EDIT)) {
			  this.editData();
		  }
		  else if (command.equalsIgnoreCase(ProjectAction.COMMAND_DELETE)) {
			  this.delete();
		  }
		  else if (command.equalsIgnoreCase(ProjectAction.COMMAND_SAVE)) {
			  this.saveData();
		  }	  
		  else if (command.equalsIgnoreCase(ProjectAction.COMMAND_BACK)) {
			  this.listAllProjects();
		  }
		  else if (command.equalsIgnoreCase(ProjectAction.COMMAND_LIST)) {
			  this.listAllProjects();
          }  
	  }
	  catch (Exception e) {
		  throw new ActionHandlerException(e);
	  }
  }
  
  /**
   * Obtains a master list of Task objects and prepares to send the objects as a response to the client 
   * via the Requst object.  The list of objects are identified on the request object as, "list".
   *  
   * @throws ActionHandlerException if a database access error occurs.
   */
  protected void listAllProjects() throws ActionHandlerException {
      List list = null;
	  
      try {
    	  list = this.api.findProjects();    
          if (list == null) {
        	  list = new ArrayList();
          }
          this.request.setAttribute("list", list);
      }
      catch (Exception e) {
          throw new ActionHandlerException(e.getMessage());
      }  
  }
  
  /**
   * Obtains a new {@link ProjProject} object and prepares to send the objects as a response to the client 
   * via the Requst object.  The new ProjProject object is identified on the request object as, "project".
  
   * @throws ActionHandlerException if a database access error occurs.
   */
  public void add()  throws ActionHandlerException {
	  // Create new data objects and send to client.
	  this.projectId = 0;
	  //this.sendClientData();
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
		  this.projectId = this.getSelectedRow("Id");
	  }
	  catch (Exception e) {
		  throw new ActionHandlerException(e.getMessage());
	  }
	  
	  // Retrieve data from the database using the project id from the request and send to client.
	  //this.sendClientData();
	  return;
  }
  
  /**
   * Accepts the {@link ProjProject} object from the client and applies any modifications to the database.
   * 
   * @throws ActionHandlerException if a problem occurred updating the Project.
   * @throws DatabaseException when the trasnaction changes fail to commit or rollback.
   */
  public void save() throws ActionHandlerException, DatabaseException {
      
	  try {
		  //this.receiveClientData();
          this.projectId =this.api.maintainProject(this.project, this.client);
		  this.transObj.commitUOW();
		  //this.sendClientData();
	  }
	  catch (Exception e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e.getMessage());
	  }
  }

  /**
   * Attempts to delete a project from the database.
   * 
   * @throws ActionHandlerException if a problem occurred deleting the Project.
   * @throws DatabaseException when the trasnaction changes fail to commit or rollback.
   */
  public void delete() throws ActionHandlerException, DatabaseException {
	  int projectId = 0;
	  
	  try {
		  projectId = this.getSelectedRow("Id");
		  this.api.deleteProject(projectId);
		  this.transObj.commitUOW();
          this.listAllProjects();
	  }
	  catch (Exception e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e.getMessage());
	  }
  }
  

  /**
   * Retreives all project data from the client's request and packages the data into the following objects to be processed: 
   * {@link ProjProject}, {@link ProjClient},  and an ArrayList of {@link ProjProjectEmployee}.
   *
   */
  protected void receiveClientData()  throws ActionHandlerException {
	  this.project = this.receiveProjectData();
	  this.client = this.getProjectClient(this.project);
  }
  
  /**
   * Obtains data related to a project from the client's request and packages the data into a ProjProject object.
   * 
   * @return {@link ProjProject}
   */
  private ProjProject receiveProjectData() {
	  ProjProject pp = ProjectManagerFactory.createProject();
	  try {
		  ProjectManagerFactory.packageBean(this.request, pp);  
		  return pp;
	  }
	  catch (SystemException e) {
		  return null;
	  }
  }
  
  /**
   * Forms the association of the Client and Project.   
   * 
   * @param __project The project which client belongs.
   * @return {@link ProjClient}
   */
  private ProjClient getProjectClient(ProjProject _project) {
	  ProjClient pc = null;
      pc = ProjectManagerFactory.createClient();
      pc.setId(_project.getProjClientId());
      return pc;
  }
  
   
  /**
   * Retrieves project details and packages the data into the request to be sent to the client.    If the project 
   * exist then the data is obtained from the database.    Otherwise, new data objects are instaintiated and sent to the clinet.
   * 
   * @param _projectId  >0 for existing projects and =0 for nex projects
   * @throws ActionHandlerException
   */
  protected void sendClientData() throws ActionHandlerException {
	  if (this.projectId == 0) {
		  this.sendNewProject();
	  }
	  else if (this.projectId > 0) {
		  this.sendExistingProject(this.projectId);
	  }
      this.getClientList();
	  return;
  }
  
  
  /**
   * Instantiates new {@link ProjProject}, {@link VwCustomerName}, and an ArrayList of {@link } objects and 
   * prepares to send to the client as a new project. 
   *
   */
  private void sendNewProject() {
	  ProjProject pp = null;
	  pp = ProjectManagerFactory.createProject();
	  this.request.setAttribute("project", pp);
	  return;
  }
  
  /**
   * Retreives all project data from the database and packages the data into the request with the intent of 
   * sending to the client as a response.   {@link ProjProject}, {@link VwCustomerName},  and an ArrayList of
   * {@link ProjProject} are set on the request as attributes and are identified as "project" and "customer", respectively.
   *   
   * @param _projectId The id of the project to retrieve data.
   * @throws ActionHandlerException when the project is found not to exist in the system using _projectId, or 
   * the project fails to demonstrate an assoication with a client, or the occurrence of a database access error.
   */
  private void sendExistingProject(int _projectId) throws ActionHandlerException {
	  ProjProject pp = null;
	  
	  try {
		  // Get project data
		  pp = this.api.findProject(_projectId);
		  if (pp == null) {
			  throw new ActionHandlerException("Project does not exist in the system");
		  }

          // Prep request object to send data to the client.
		  this.request.setAttribute("project", pp);
		  return;
	  }
	  catch (ProjectException e) {
		  throw new ActionHandlerException(e.getMessage());
	  }
  }
  
  /**
   * Retrieves a master list of clients to be used as a dropdown on the Project Maintenance Edit page.
   * 
   * @return An ArrayList of {@link VwCustomerName} objects
   * @throws ActionHandlerException
   */
  private List getClientList() throws ActionHandlerException {
      List list;
      try {
          list = this.api.findClient();
          this.request.setAttribute("clients", list);
          return list;
      }
      catch (ProjectException e) {
          throw new ActionHandlerException(e.getMessage());
      }
  }
  
}
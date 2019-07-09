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

import com.bean.ProjTask;

import com.factory.ProjectManagerFactory;


/**
 * This class provides action handlers to respond to the client's commands from the TaskMaintList.jsp and TaskMaintEdit.jsp pages. 
 * 
 * @author Roy Terrell
 *
 */
public class TaskAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "Project.TaskMaintList.add";
    private static final String COMMAND_EDIT = "Project.TaskMaintList.edit";
    private static final String COMMAND_DELETE = "Project.TaskMaintList.delete";
    private static final String COMMAND_LIST = "Project.TaskMaintList.list";
    private static final String COMMAND_SAVE = "Project.TaskMaintEdit.save";
    private static final String COMMAND_BACK = "Project.TaskMaintEdit.back";
    
    private ProjectSetupApi api;
    private Logger logger;
    
    public TaskAction() {
    	logger = Logger.getLogger("TaskAction");
    }
    
 
    /**
     * Processes the Add, Edit, Delete, Save, Back, and List commands issued from the TaskMaintList.jsp and TaskMaintEdit.jsp pages.
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
		  if (command.equalsIgnoreCase(TaskAction.COMMAND_ADD)) {
			  this.add();
		  }
		  else if (command.equalsIgnoreCase(TaskAction.COMMAND_EDIT)) {
			  this.edit();
		  }
		  else if (command.equalsIgnoreCase(TaskAction.COMMAND_DELETE)) {
			  this.delete();
		  }
		  else if (command.equalsIgnoreCase(TaskAction.COMMAND_SAVE)) {
			  this.save();
		  }	  
		  else if (command.equalsIgnoreCase(TaskAction.COMMAND_BACK)) {
			  this.listAllTasks();
		  }
		  else if (command.equalsIgnoreCase(TaskAction.COMMAND_LIST)) {
			  this.listAllTasks();
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
  protected void listAllTasks() throws ActionHandlerException {
      List tasks = null;
	  
      try {
          tasks = this.api.findTasks();    
          if (tasks == null) {
              tasks = new ArrayList();
          }
          this.request.setAttribute("list", tasks);
      }
      catch (Exception e) {
          throw new ActionHandlerException(e.getMessage());
      }  
  }
  
  /**
   * Obtains a new {@link ProjTask} object and prepares to send the objects as a response to the client 
   * via the Requst object.  The new ProjTask object is identified on the request object as, "task".
  
   * @throws ActionHandlerException if a database access error occurs.
   */
  public void add()  throws ActionHandlerException {
	  ProjTask pt = ProjectManagerFactory.createTask();
	  this.request.setAttribute("task", pt);
	  return;
  }
  
  /**
   * Obtains a {@link ProjTask} object from the using the task id selected by the client and prepares to send the object as a response 
   * to the client via the Requst object.  The selected ProjTask object is identified on the request object as, "task".
   * 
   * @throws ActionHandlerException If a database access error occurs or Task was not found using the selected task id.
   */
  public void edit() throws ActionHandlerException {
	  ProjTask pt = null;
	  int taskId = 0;
	  
	  try {
		  taskId = this.getSelectedRow("Id");
		  pt = this.api.findTask(taskId);
	  }
	  catch (Exception e) {
		  throw new ActionHandlerException(e.getMessage());
	  }
	  
	  // Verify that Task was found.
	  if (pt == null) {
		  throw new ActionHandlerException("Selected Task does not exist in the database.  Task Id=" + taskId);
	  }
	  this.request.setAttribute("task", pt);
	  return;
  }
  
  /**
   * Accepts the {@link ProjTask} object from the client and applies any modifications to the database.
   * 
   * @throws ActionHandlerException if a problem occurred updating the Task.
   * @throws DatabaseException when the trasnaction changes fail to commit or rollback.
   */
  public void save() throws ActionHandlerException, DatabaseException {
	  ProjTask pt = ProjectManagerFactory.createTask();
	  try {
		  ProjectManagerFactory.packageBean(this.request, pt);  
		  this.api.maintainTask(pt);
		  this.transObj.commitUOW();
          this.listAllTasks();
	  }
	  catch (Exception e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e.getMessage());
	  }
  }

  /**
   * Attempts to delete a task from the database.
   * 
   * @throws ActionHandlerException if a problem occurred deleting the Task.
   * @throws DatabaseException when the trasnaction changes fail to commit or rollback.
   */
  public void delete() throws ActionHandlerException, DatabaseException {
	  int taskId = 0;
	  
	  try {
		  taskId = this.getSelectedRow("Id");
		  this.api.deleteTask(taskId);
		  this.transObj.commitUOW();
          this.listAllTasks();
	  }
	  catch (Exception e) {
		  this.transObj.rollbackUOW();
		  throw new ActionHandlerException(e.getMessage());
	  }
  }
  
  protected void receiveClientData() throws ActionHandlerException{}
  
  protected void sendClientData() throws ActionHandlerException{}
}
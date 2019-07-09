package com.project.setup;

import com.constants.RMT2ServletConst;
import com.controller.Context;
import com.controller.Request;
import com.controller.Response;
import com.project.ProjectConst;
import com.util.SystemException;

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

import com.bean.ProjTask;
import com.bean.db.DatabaseConnectionBean;

/**
 * This class provides action handlers to respond to the client's commands from the 
 * TaskMaintList.jsp page. 
 * 
 * @author Roy Terrell
 *
 */
public class SetupTaskSearchAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ADD = "TaskMaint.List.add";

    private static final String COMMAND_EDIT = "TaskMaint.List.edit";

    private static final String COMMAND_DELETE = "TaskMaint.List.delete";

    private static final String COMMAND_LIST = "TaskMaint.List.list";

    private static final String COMMAND_BACK = "TaskMaint.List.back";

    private Logger logger;

    private List tasks;

    private ProjTask task;

    private int taskId;

    public SetupTaskSearchAction() {
	this.logger = Logger.getLogger("SetupTaskSearchAction");
	this.logger.log(Level.INFO, "Logger created");
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
        super.init(context, request);
    }
    
    /**
     * Processes the Add, Edit, Delete, Save, Back, and List commands issued from the TaskMaintList.jsp and TaskMaintEdit.jsp pages.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);
	try {
	    if (command.equalsIgnoreCase(SetupTaskSearchAction.COMMAND_ADD)) {
		this.addData();
	    }
	    else if (command.equalsIgnoreCase(SetupTaskSearchAction.COMMAND_EDIT)) {
		this.editData();
	    }
	    else if (command.equalsIgnoreCase(SetupTaskSearchAction.COMMAND_DELETE)) {
		this.deleteData();
	    }
	    else if (command.equalsIgnoreCase(SetupTaskSearchAction.COMMAND_BACK)) {
		this.doBack();
	    }
	    else if (command.equalsIgnoreCase(SetupTaskSearchAction.COMMAND_LIST)) {
		this.listAllTasks();
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
    }

    /**
     * Obtains a master list of Task objects and prepares to send the objects as a 
     * response to the client via the Requst object.  The list of objects are identified 
     * on the request object as, "list".
     *  
     * @throws ActionHandlerException if a database access error occurs.
     */
    protected void listAllTasks() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.tasks = (List) api.findAllTasks();
	    if (this.tasks == null) {
		this.tasks = new ArrayList();
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
     * Obtains a new {@link ProjTask} object and prepares to send the objects as a response 
     * to the client via the Requst object.  The new ProjTask object is identified on the 
     * request object as, "task".
     
     * @throws ActionHandlerException if a database access error occurs.
     */
    public void add() throws ActionHandlerException {
	this.task = SetupFactory.createTask();
	return;
    }

    /**
     * Obtains a {@link ProjTask} object from the using the task id selected by the client 
     * and prepares to send the object as a response to the client via the Requst object.  
     * The selected ProjTask object is identified on the request object as, "task".
     * 
     * @throws ActionHandlerException 
     *           If a database access error occurs or Task was not found using the selected 
     *           task id.
     */
    public void edit() throws ActionHandlerException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.task = (ProjTask) api.findTask(this.taskId);
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

	// Verify that Task was found.
	if (this.task == null) {
	    throw new ActionHandlerException("Selected Task does not exist in the database.  Task Id=" + taskId);
	}
	return;
    }


    /**
     * Attempts to delete a task from the database.
     * 
     * @throws ActionHandlerException if a problem occurred deleting the Task.
     * @throws DatabaseException when the trasnaction changes fail to commit or rollback.
     */
    public void delete() throws ActionHandlerException, DatabaseException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.deleteTask(this.taskId);
	    tx.commitUOW();
	    this.listAllTasks();
	    this.msg = "Task deleted successfully";
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    this.logger.log(Level.ERROR, "Task failed to delete");
	    throw new ActionHandlerException(e);
	}
	finally {
		api.close();
		api = null;
		tx.close();
		tx = null;
	}
    }

    protected void receiveClientData() throws ActionHandlerException {
	try {
	    this.taskId = this.getSelectedRow("Id");
	}
	catch (SystemException e) {
	    this.taskId = 0;
	}
    }

    protected void sendClientData() throws ActionHandlerException {
	this.request.setAttribute(ProjectConst.CLIENT_DATA_TASK, this.task);
	this.request.setAttribute(ProjectConst.CLIENT_DATA_TASKS, this.tasks);
	this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#save()
     */
    public void save() throws ActionHandlerException, DatabaseException {
	return;
    }
    
    
    
}
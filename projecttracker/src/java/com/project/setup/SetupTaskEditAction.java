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
 * TaskMaintEdit.jsp page. 
 * 
 * @author Roy Terrell
 *
 */
public class SetupTaskEditAction extends AbstractActionHandler implements ICommand {

    private static final String COMMAND_SAVE = "TaskMaint.Edit.save";

    private static final String COMMAND_BACK = "TaskMaint.Edit.back";

    private Logger logger;

    private List tasks;

    private ProjTask task;

    private int taskId;

    public SetupTaskEditAction() {
	this.logger = Logger.getLogger("SetupTaskEditAction");
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
	    if (command.equalsIgnoreCase(SetupTaskEditAction.COMMAND_SAVE)) {
		this.saveData();
	    }
	    else if (command.equalsIgnoreCase(SetupTaskEditAction.COMMAND_BACK)) {
		this.doBack();
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
    private void listAllTasks() throws ActionHandlerException {
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
     * Accepts the {@link ProjTask} object from the client and applies any modifications to the database.
     * 
     * @throws ActionHandlerException if a problem occurred updating the Task.
     * @throws DatabaseException when the trasnaction changes fail to commit or rollback.
     */
    public void save() throws ActionHandlerException, DatabaseException {
    	DatabaseTransApi tx = DatabaseTransFactory.create();
    	SetupApi api = SetupFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.maintainTask(this.task);
	    tx.commitUOW();
	    this.msg = "Task saved successfully";
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

    
    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#doBack()
     */
    @Override
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	this.listAllTasks();
    }

 
    protected void receiveClientData() throws ActionHandlerException {
	this.task = SetupFactory.createTask();
	try {
	    SetupFactory.packageBean(this.request, this.task);
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
     * @see com.action.ICommonAction#add()
     */
    public void add() throws ActionHandlerException {
	return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
	return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
	return;
    }
}
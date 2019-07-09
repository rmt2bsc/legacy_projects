package com.employee;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.bean.RMT2TagQueryBean;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

/**
 * Serves the user's command to manage an existing employee.  The majority of the its 
 * functionality lies within the ancestor object, {@link com.employee.EmployeeCommonAction EmployeeCommonAction}. 
 * 
 * @author Roy Terrell
 *
 */
public class EmployeeConsoleAction extends EmployeeCommonAction implements ICommand {

    private static final String COMMAND_BACK = "EmployeeConsole.Edit.back";

    private static final String COMMAND_SAVE = "EmployeeConsole.Edit.save";

    private static final String COMMAND_PROJECTS = "EmployeeConsole.Edit.projects";

    private static Logger logger = Logger.getLogger(EmployeeConsoleAction.class);

    protected static String EMPTY_EMPPROJ_DOC = "<VwEmployeeProjectsView></VwEmployeeProjectsView>";

    private Object empProjs;


    /**
     * Default constructor which creates a EmployeeConsoleAction object 
     * and sets up the logger.
     *
     */
    public EmployeeConsoleAction() {
	super();
	EmployeeConsoleAction.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Creates a EmployeeConsoleAction object containing the application context 
     * and the user's request.   
     * 
     * @param context 
     *          The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public EmployeeConsoleAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request   The HttpRequest object
     * @param response  The HttpResponse object
     * @param command  Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);

	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);

	if (command.equalsIgnoreCase(EmployeeConsoleAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(EmployeeConsoleAction.COMMAND_BACK)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(EmployeeConsoleAction.COMMAND_PROJECTS)) {
	    this.getProjects();
	}
    }


    /**
     * Preserves the user's input values regarding the selection criteria. 
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.request.setAttribute("employeeProjects", this.empProjs);
    }

    public void getProjects() throws ActionHandlerException {
	this.receiveClientData();

	// Fetch Single employee record
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.empProjs = api.findProject(this.emp.getEmpId());
	    if (this.empProjs == null) {
		// Create Empty result set
		this.empProjs = EmployeeConsoleAction.EMPTY_EMPPROJ_DOC;
	    }
	    this.employee = api.findEmployeeExt(this.emp.getEmpId());
	    if (this.employee == null) {
		// Create Empty result set
		this.employee = EmployeeCommonAction.EMPTY_EMP_DOC;
	    }
	}
	catch (Exception e) {
	    throw new ActionHandlerException(e);
	}
	finally {
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	    this.sendClientData();
	}
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#doBack()
     */
    @Override
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	this.listData();
	this.sendClientData();
    }

    /* (non-Javadoc)
     * @see com.employee.EmployeeCommonAction#save()
     */
    @Override
    public void save() throws ActionHandlerException, DatabaseException {
	super.save();
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    api.maintainEmployee(this.emp);
	    this.msg = "Employee profile saved successfully";
	    tx.commitUOW();
	}
	catch (EmployeeException e) {
	    tx.rollbackUOW();
	    this.msg = e.getMessage();
	    throw new ActionHandlerException(e);
	}
	finally {
	    this.refreshEmployee(this.emp.getEmpId());
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}

    }
}
package com.employee;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.orm.DataSourceAdapter;

import com.bean.ProjEmployeeProject;
import com.bean.RMT2TagQueryBean;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

/**
 * Serves the user's command to manage list of employee projects.  The majority of the its 
 * functionality lies within the ancestor object, {@link com.employee.EmployeeCommonAction EmployeeCommonAction}. 
 * 
 * @author Roy Terrell
 *
 */
public class EmployeeProjectEditAction extends EmployeeProjectListAction implements ICommand {

    private static final String COMMAND_BACK = "EmployeeProjectEdit.Edit.back";

    private static final String COMMAND_SAVE = "EmployeeProjectEdit.Edit.save";

    private static Logger logger = Logger.getLogger(EmployeeProjectEditAction.class);

    protected Object empProj;

    protected Object empProjs;

    /**
     * Default constructor which creates a EmployeeProjectEditAction object 
     * and sets up the logger.
     *
     */
    public EmployeeProjectEditAction() {
	super();
	EmployeeProjectEditAction.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Creates a EmployeeProjectEditAction object containing the application context 
     * and the user's request.   
     * 
     * @param context 
     *          The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public EmployeeProjectEditAction(Context context, Request request) throws SystemException, DatabaseException {
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

	if (command.equalsIgnoreCase(EmployeeProjectEditAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(EmployeeProjectEditAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

    /**
     * Retrieve the selected employee id from a list of employees.
     *  
     * @throws ActionHandlerException 
     *           Problem Identifying Employee Id
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	this.empProj = EmployeeFactory.createEmployeeProject();
	DataSourceAdapter.packageBean(this.request, this.empProj);
    }

    /**
     * Preserves the user's input values regarding the selection criteria. 
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.request.setAttribute("employeeProject", this.empProj);
	this.request.setAttribute("employeeProjects", this.empProjs);
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
	    api.maintainEmployeeProject((ProjEmployeeProject) this.empProj);
	    this.msg = "Employee Project profile saved successfully";
	    tx.commitUOW();
	}
	catch (EmployeeException e) {
	    tx.rollbackUOW();
	    this.msg = "Unable to save employee project";
	    logger.error(this.msg);
	    throw new ActionHandlerException(this.msg, e);
	}
	finally {
	    this.refreshEditPage((ProjEmployeeProject) this.empProj);
	    api.close();
	    tx.close();
	    api = null;
	    tx = null;
	}
    }

    private void refreshEditPage(ProjEmployeeProject empProj) throws ActionHandlerException {
	// Fetch Single employee record
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.empProj = api.findEmployeeProject(empProj.getEmpProjId());
	    if (this.empProj == null) {
		// Create Empty result set
		this.empProj = EmployeeProjectEditAction.EMPTY_EMPPROJ_DOC;
	    }
	    this.employee = api.findEmployeeExt(empProj.getEmpId());
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
	}
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#doBack()
     */
    @Override
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	this.getProjects();
	this.sendClientData();
    }

    private void getProjects() throws ActionHandlerException {
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
	}
    }

}
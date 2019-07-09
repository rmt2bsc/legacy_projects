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

import com.project.setup.SetupApi;
import com.project.setup.SetupFactory;
import com.util.NotFoundException;
import com.util.SystemException;

/**
 * Serves the user's command to manage list of employee projects. The majority
 * of the its functionality lies within the ancestor object,
 * {@link com.employee.EmployeeCommonAction EmployeeCommonAction}.
 * 
 * @author Roy Terrell
 * 
 */
public class EmployeeProjectListAction extends EmployeeCommonAction implements ICommand {

    private static final String COMMAND_BACK = "EmployeeProjectList.List.back";

    private static final String COMMAND_ADD = "EmployeeProjectList.List.add";

    private static final String COMMAND_EDIT = "EmployeeProjectList.List.edit";

    private static Logger logger = Logger.getLogger(EmployeeProjectListAction.class);

    protected static String EMPTY_EMPPROJ_DOC = "<VwEmployeeProjectsView><vw_employee_projects><pay_rate></pay_rate><client_name></client_name><flat_rate></flat_rate><client_ot_bill_rate></client_ot_bill_rate><business_id></business_id><emp_proj_id></emp_proj_id><projemp_effective_date></projemp_effective_date><account_no></account_no><projemp_end_date></projemp_end_date><comments></comments><ot_pay_rate></ot_pay_rate><emp_id></emp_id><proj_effective_date></proj_effective_date><client_bill_rate>0.0</client_bill_rate><proj_id>16</proj_id><client_id>7</client_id><proj_end_date></proj_end_date><project_name></project_name></vw_employee_projects></VwEmployeeProjectsView>";

    protected static String EMPTY_CLIENTS_DOC = "<ProjClientView></ProjClientView>";

    private Object empProj;

    private Object clients;

    private int empProjId;

    /**
     * Default constructor which creates a EmployeeProjectListAction object and
     * sets up the logger.
     * 
     */
    public EmployeeProjectListAction() {
	super();
	EmployeeProjectListAction.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Creates a EmployeeProjectListAction object containing the application
     * context and the user's request.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public EmployeeProjectListAction(Context context, Request request) throws SystemException, DatabaseException {
	super(context, request);
	this.init(this.context, this.request);
    }

    /**
     * Processes the client's request using request, response, and command.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
	super.processRequest(request, response, command);

	this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);

	if (command.equalsIgnoreCase(EmployeeProjectListAction.COMMAND_ADD)) {
	    this.addData();
	}
	if (command.equalsIgnoreCase(EmployeeProjectListAction.COMMAND_BACK)) {
	    this.doBack();
	}
	if (command.equalsIgnoreCase(EmployeeProjectListAction.COMMAND_EDIT)) {
	    this.editData();
	}
    }

    /**
     * Retrieve the selected employee id from a list of employees.
     * 
     * @throws ActionHandlerException
     *             Problem Identifying Employee Id
     */
    public void receiveClientData() throws ActionHandlerException {
	super.receiveClientData();
	
	String val;
	try {
	    val = this.getPropertyValue("SelCbx");    
	}
	catch (NotFoundException e) {
	    val = null;
	    this.msg = null;
	}
	try {
	    
	    this.empProjId = Integer.parseInt(val);
	}
	catch (Exception e) {
	    this.empProjId = 0;
	}
    }

    /**
     * Preserves the user's input values regarding the selection criteria.
     */
    public void sendClientData() throws ActionHandlerException {
	super.sendClientData();
	this.request.setAttribute("employeeProject", this.empProj);
	this.request.setAttribute("clientList", this.clients);

    }

    @Override
    public void add() throws ActionHandlerException {
	super.add();
	this.receiveClientData();
	this.empProj = EmployeeProjectListAction.EMPTY_EMPPROJ_DOC;

	// Fetch single employee record and client list
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	SetupApi setupXmlApi = SetupFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
//	    this.clients = setupApi.findAllClients();
	    this.clients = setupXmlApi.findClientExt();
	    if (this.clients == null) {
		// Create Empty result set
		this.clients = EmployeeProjectListAction.EMPTY_CLIENTS_DOC;
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

    public void edit() throws ActionHandlerException {
	if (this.empProjId == 0) {
	    this.msg = "An employee project must be selected";
	    logger.log(Level.ERROR, this.msg);
	    throw new ActionHandlerException(this.msg);
	}
	// Fetch Single employee record
	DatabaseTransApi tx = DatabaseTransFactory.create();
	EmployeeApi api = EmployeeFactory.createXmlApi((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    this.empProj = api.findEmployeeProject(this.empProjId);
	    if (this.empProj == null) {
		// Create Empty result set
		this.empProj = EmployeeProjectListAction.EMPTY_EMPPROJ_DOC;
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

    /*
     * (non-Javadoc)
     * 
     * @see com.action.AbstractActionHandler#doBack()
     */
    @Override
    protected void doBack() throws ActionHandlerException {
	super.doBack();
	this.receiveClientData();
	this.refreshEmployee(this.emp.getEmpId());
	this.sendClientData();
    }

}
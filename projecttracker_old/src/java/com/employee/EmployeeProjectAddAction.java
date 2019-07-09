package com.employee;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

/**
 * Serves the user's command to manage requests to add employee projects.  The 
 * majority of the its functionality lies within the ancestor object, 
 * {@link com.employee.EmployeeProjectEditAction EmployeeProjectEditAction}. 
 * 
 * @author Roy Terrell
 *
 */
public class EmployeeProjectAddAction extends EmployeeProjectEditAction {

    private static final String COMMAND_BACK = "EmployeeProjectAdd.Add.back";

    private static final String COMMAND_SAVE = "EmployeeProjectAdd.Add.save";

    private static Logger logger = Logger.getLogger(EmployeeProjectAddAction.class);

    /**
     * Default constructor which creates a EmployeeProjectAddAction object 
     * and sets up the logger.
     *
     */
    public EmployeeProjectAddAction() {
	super();
	EmployeeProjectAddAction.logger.log(Level.INFO, "Logger initialized");
    }

    /**
     * Creates a EmployeeProjectAddAction object containing the application context 
     * and the user's request.   
     * 
     * @param context 
     *          The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public EmployeeProjectAddAction(Context context, Request request) throws SystemException, DatabaseException {
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

	if (command.equalsIgnoreCase(EmployeeProjectAddAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(EmployeeProjectAddAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }

}
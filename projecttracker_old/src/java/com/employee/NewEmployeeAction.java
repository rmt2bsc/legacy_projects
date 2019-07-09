package com.employee;


import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;

import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;


import com.util.SystemException;

/**
 * Serves the user's command to create an employee.   The majority of the its functionality lies within 
 * the ancestor object, {@link com.employee.EmployeeConsoleAction EmployeeConsoleAction} 
 * 
 * @author Roy Terrell
 *
 */
public class NewEmployeeAction extends EmployeeConsoleAction implements ICommand {

    private static final String COMMAND_BACK = "NewEmployee.Edit.back";
    
    private static final String COMMAND_SAVE = "NewEmployee.Edit.save";

    private static Logger logger = Logger.getLogger(NewEmployeeAction.class);
    
    

    /**
     * Default constructor which creates a CustomerSalesSearchAction object 
     * and sets up the logger.
     *
     */
    public NewEmployeeAction() {
	super();
	NewEmployeeAction.logger.log(Level.INFO, "Logger initialized"); 
    }

    /**
     * Creates a CustomerSalesSearchAction object containing the application context 
     * and the user's request.   
     * 
     * @param context 
     *          The servlet context to be associated with this action handler
     * @param request 
     *          The request object sent by the client to be associated with this 
     *          action handler
     * @throws SystemException
     */
    public NewEmployeeAction(Context context, Request request) throws SystemException, DatabaseException {
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

	if (command.equalsIgnoreCase(NewEmployeeAction.COMMAND_SAVE)) {
	    this.saveData();
	}
	if (command.equalsIgnoreCase(NewEmployeeAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }
    

    /* (non-Javadoc)
     * @see com.employee.EmployeeCommonAction#save()
     */
    @Override
    public void save() throws ActionHandlerException, DatabaseException {
    	try {
    		super.save();		
    		this.msg = "Employee profile created successfully";
    	}
    	catch (Exception e) {
    		
    	}
    }
    
}
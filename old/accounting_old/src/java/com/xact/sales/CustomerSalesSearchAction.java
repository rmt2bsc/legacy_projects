package com.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.db.DatabaseException;

import com.bean.RMT2TagQueryBean;

import com.constants.RMT2ServletConst;

import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.gl.customer.CustomerSearchAction;

import com.util.SystemException;

/**
 * This class provides functionality needed to serve the requests of the Customer 
 * Sales Search user interface.   The majority of the its functionality lies within 
 * the ancestor object, CustomerSearchAction, from the com.gl.customer package. 
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesSearchAction extends CustomerSearchAction {
    private static final String COMMAND_NEWSEARCH = "SalesCustomerSearch.Search.newsearch";

    private static final String COMMAND_SEARCH = "SalesCustomerSearch.Search.search";

    public static final String COMMAND_LIST = "SalesCustomerSearch.Search.list";

    private static final String COMMAND_EDIT = "SalesCustomerSearch.Search.edit";

    private static final String COMMAND_BACK = "SalesCustomerSearch.Search.back";

    private Logger logger;

    /**
     * Default constructor which creates a CustomerSalesSearchAction object 
     * and sets up the logger.
     *
     */
    public CustomerSalesSearchAction() {
	super();
	logger = Logger.getLogger("CustomerSalesSearchAction");
	this.logger.log(Level.INFO, "Logger initialized");
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
    public CustomerSalesSearchAction(Context context, Request request) throws SystemException, DatabaseException {
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

	if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_NEWSEARCH)) {
	    this.doNewSearch();
	}
	if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_SEARCH)) {
	    this.doSearch();
	}
	if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_LIST)) {
	    this.listData();
	}
	if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_EDIT)) {
	    this.editData();
	}
	if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_BACK)) {
	    this.doBack();
	}
    }
}
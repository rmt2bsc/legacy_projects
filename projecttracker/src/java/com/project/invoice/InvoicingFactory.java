package com.project.invoice;

import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceAdapter;

import com.bean.db.DatabaseConnectionBean;



public class InvoicingFactory extends DataSourceAdapter {

    /**
     * Creates a InvoicingApi using time sheet invoicing implementation which 
     * initializes the database connection bean and the user's request.
     * 
     * @param dbo The database connection bean instance
     * @param request The user's request object.
     * @return {@link com.project.invoice.InvoicingApi InvoicingApi}
     */
    public static InvoicingApi createApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    InvoicingApi api = new TimesheetInvoicingImpl(dbo, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    
    /**
     * 
     * @param dbo
     * @param request
     * @param response
     * @return
     */
    public static InvoicingApi createApi(DatabaseConnectionBean dbo, Request request, Response response) {
	try {
	    InvoicingApi api = new TimesheetInvoicingImpl(dbo, request, response);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }
}

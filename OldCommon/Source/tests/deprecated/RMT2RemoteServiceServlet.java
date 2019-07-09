package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.Hashtable;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.NotFoundException;
import com.util.RemoteServiceException;

import com.constants.RMT2ServletConst;
import com.constants.RMT2SqlConst;

import com.action.RMT2RemoteServiceAction;
import com.api.RMT2DBConnectionApi;

import com.bean.RMT2DBConnectionBean;
import com.bean.Zipcode;

import com.factory.ContactsFactory;

import com.servlet.RMT2Servlet;



public class RMT2RemoteServiceServlet extends RMT2Servlet {

	/** This is the service code request by client */
    protected String requestedService;
    /** Holds the results of the request and is returned to the client.   Results can be in the
     *   format of xml or plain text.   Plain text values will be separated by the "~" character.
     */
    protected StringBuffer results;
    
     
    private static final String SERVICE_CODE = "SERVICE";
    private static final String SRVC_ZIPCODE = "zipcode";

  /////////////////////////////////////
  //     Constructor
  /////////////////////////////////////
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.results = new StringBuffer(100);
  }
  public void initServlet() throws SystemException {
    return;
  }

  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
    	super.processRequest(request, response);   
    }
    catch (ServletException e) {
    	// TODO: Add logic at ancestor to determin what caused the ServletException to occur.   This would be helpful when wanting to 
    	//             bypass the "Action not determined error" and continue processing.
    }
    
    this.requestedService = request.getParameter(RMT2RemoteServiceServlet.SERVICE_CODE);

    // Recognize request.
    try {
    	if (this.requestedService.equalsIgnoreCase(RMT2RemoteServiceServlet.SRVC_ZIPCODE)) {
      	  RMT2RemoteServiceAction handler = new RMT2RemoteServiceAction(this.context, request);
      	  String results = handler.getZipCodeData();
      	  this.out.println(results);
        }	
    }
    catch (RemoteServiceException e) {
    	System.out.println("[RMT2RemoteScriptService.processRequest] " + e.getMessage());
    	 throw new ServletException(e.getMessage());
    }
	  catch (DatabaseException e) {
		  throw new ServletException(e.getMessage());
	  }
	  catch (SystemException e) {
		  throw new ServletException(e.getMessage());
	  }
      
  }

}  // End Class
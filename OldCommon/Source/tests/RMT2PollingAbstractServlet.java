package com.servlet;

import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.constants.XactConst;

import com.util.SystemException;
import com.util.DatabaseException;

import com.servlet.RMT2Servlet;



public abstract class RMT2PollingAbstractServlet extends RMT2Servlet {

  private static final String POLLING = "polling";
  protected HashMap dataMap;
  
  
  /////////////////////////////////////
  //     Constructor
  /////////////////////////////////////
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    dataMap = new HashMap();
  }
  public abstract void initServlet() throws SystemException;
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //       Method: setPollKey
  //    Prototype: String _key
  //                    Object _value
  //      Returns:  Object 
  //       Throws: none
  //  Description: Adds polling key/value pair to hash map.   Returns the key's previous value, if any.
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public Object setPollKey(String _key, Object _value) {
     if (this.dataMap == null) {
     	this.dataMap = new HashMap();
     }
     return this.dataMap.put(_key, _value);
  }
  
  ////////////////////////////////////////////
  // Process GET/POST Re-directed request
  ////////////////////////////////////////////
  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Object data = null;
	  String pollKey = null;
	  String pollService = null;
	  String successPagePath = null;
	  String successPage = null;
	  String errorMsg = null;

     super.processRequest(request, response);
     this.clientAction = request.getParameter("clientAction");
     
     System.out.println("[RMT2PollingAbstractServlet.processRequest] Determining action");
     System.out.println("[RMT2PollingAbstractServlet.processRequest] Action is: " + this.clientAction);
     if (this.clientAction.equalsIgnoreCase(RMT2PollingAbstractServlet.POLLING)) {
         // Get request data
		 pollKey = request.getParameter("pollKey");
	     pollService = request.getParameter("pollService");
	  	 successPagePath = request.getParameter("successPagePath");
	  	 successPage = request.getParameter("successPage");
	  	 errorMsg = request.getParameter("error");

	  	 // Display error messsage if poll key not available from JSP page.
		 if (pollKey == null || pollKey.equals("")) {
             System.out.println("[RMT2PollingAbstractServlet.processRequest] poll key not available from JSP page");
			 // TODO:  Create error exception regarding polling JSP page not containg apoll key
			 this.redirectPage = "/polling_wait_page.jsp";
			 this.redirect(this.redirectPage, request, response);
		 }
			
		 //  Display error message if dataMap has not been initialized.
		 if (this.dataMap == null) {
             System.out.println("[RMT2PollingAbstractServlet.processRequest] dataMap has not been initialized.");
             // TODO:  Create error exception regarding data map not initialized
			 this.redirectPage = "/error_page.jsp";
	     }
			
		 if (this.dataMap.containsKey(pollKey)) {
             System.out.println("[RMT2PollingAbstractServlet.processRequest] Poll Key was found in Data Map");
			 data = this.dataMap.get(pollKey);
				
			 //  Check to see if thread is still processing request
			 if (data == null && errorMsg == null) {
				System.out.println("[RMT2PollingAbstractServlet.processRequest] Thread is still processing request");
				// Continue to display "wait" page if thread is still working.
	    		this.redirectPage = "/polling_wait_page.jsp";
	    		request.setAttribute("pollKey", pollKey);
	    		request.setAttribute("pollService", pollService);
	    		request.setAttribute("successPagePath", successPagePath);
	    		request.setAttribute("successPage", successPage);
			 }
			 else {
                 //  Display success page which is ususally responsible for presenting the data results
				System.out.println("[RMT2PollingAbstractServlet.processRequest] Thread has successfully completed processing request");
				this.redirectPage = successPagePath + successPage;
				request.setAttribute("data", data);
				request.setAttribute("error", errorMsg);
				request.removeAttribute("pollKey");
				request.removeAttribute("pollService");
				request.removeAttribute("successPagePath");
				request.removeAttribute("successPage");
				this.dataMap.remove(pollKey);
			 }
		 }
		 //  Poll key has not been set by the handler
		 else {
             // TODO:  Create error exception regarding poll key has not been set by handler
			 System.out.println("[RMT2PollingAbstractServlet.processRequest] poll key has not been set by handler");
  			 this.redirectPage = "/polling_wait_page.jsp";
		 }
			
		 // Display result page
		this.redirect(this.redirectPage, request, response);
	  }
  }
    	
}  // End Class
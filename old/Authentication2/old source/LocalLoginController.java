package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.util.RMT2Utility;

import com.constants.GeneralConst;

import com.remoteservices.http.AbstractInternalServerAction;

import com.action.userlogin.LocalLoginAction;

import com.controller.Request;
import com.controller.Response;

import com.controller.stateless.AbstractServlet;
import com.controller.stateless.scope.HttpVariableScopeFactory;

/**
 * Authentication Servlet that process local user logins and logouts
 * 
 * @author appdev
 * @deprecated Will be removed.  The framework class, com.controller.stateless.AbstractCommandServlet, 
 *             will serve as controller for local authentication.
 * 
 */
public class LocalLoginController extends AbstractServlet {
    private static final long serialVersionUID = 8295709423196681300L;

    /**
     * Initializer
     */
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
    }

    /**
     * Servlet initializer
     */
    public void initServlet() throws ServletException {
	return;
    }

  

    /**
     * Processes user authentication requests and redirects the user to the home 
     * page provided the user is successfully authenticated.
     * 
     * @param request
     *                The request object
     * @param response
     *                The response object
     * @throws ServletException
     * @throws IOException  
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	super.processRequest(request, response);
	String clientAction = request.getParameter(GeneralConst.REQ_CLIENTACTION);
	String root = RMT2Utility.getWebAppContext(request);
	String url = root + "/login.jsp";
	try {
	    Request genericRequest = HttpVariableScopeFactory.createHttpRequest(request);
	    Response genericResponse = HttpVariableScopeFactory.createHttpResponse(response);
	    AbstractInternalServerAction actionHandler = new LocalLoginAction(genericRequest, genericResponse);
	    actionHandler.processRequest(genericRequest, genericResponse, clientAction);
	    url = root + "/home.jsp";
	    this.clientRedirect(url, request, response);
	}
	catch (Exception e) {
	    this.clientRedirect(url, request, response);
	}
    }

} // End Class

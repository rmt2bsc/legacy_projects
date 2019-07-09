package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.util.SystemException;

import com.constants.GeneralConst;

import com.action.ActionHandlerException;

import com.remoteservices.http.AbstractInternalServerAction;

import com.action.userlogin.ExternalLoginAction;

import com.api.db.DatabaseException;

import com.controller.Request;
import com.controller.Response;

import com.controller.stateless.AbstractServlet;
import com.controller.stateless.scope.HttpVariableScopeFactory;

/**
 * Authentication Servlet that process local and remote user logins and system
 * logouts
 * 
 * @author appdev
 * @deprecated Remote requests for user logins will be channeled through JavaResponseController servlet.
 * 
 */
public class ExternalLoginController extends AbstractServlet {
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
     * Processes user authentication requests requiring the
     * UserAthenticationAction handler.
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

	AbstractInternalServerAction actionHandler = null;
	try {
	    Request genericRequest = HttpVariableScopeFactory.createHttpRequest(request);
	    Response genericResponse = HttpVariableScopeFactory.createHttpResponse(response);
	    actionHandler = new ExternalLoginAction(genericRequest, genericResponse);
	    actionHandler.processRequest(genericRequest, genericResponse, clientAction);
	}
	catch (SystemException e) {
	    throw new ServletException(e.getMessage());
	}
	catch (DatabaseException e) {
	    throw new ServletException(e.getMessage());
	}
	catch (ActionHandlerException e) {
	    throw new ServletException(e.getMessage());
	}
	finally {
	    try {
		actionHandler.close();
	    }
	    catch (ActionHandlerException e) {
		e.printStackTrace();
	    }
	}
    }
    

} // End Class

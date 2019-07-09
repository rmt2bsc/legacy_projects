package com.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.stateless.AbstractCommandServlet;
import com.controller.stateless.JavaResponseController;

import com.util.SystemException;

/**
 * The purpose of this servlet is to waive the requirement of which the user's session must be 
 * a logged in session before server-side resources are accessed.
 * 
 * @author appdev
 *
 */
public class SessionBypassServlet extends JavaResponseController {
    private static final long serialVersionUID = -6567312630505828002L;

    /**
     * Overides ancestor logic which will always force the user to appear to be assoicated with a logged in session.
     * 
     * @param request
     * @param response
     * @return int1
     *           always returns 1 indicating  alogged in session exists.
     * @throws SystemException
     */
    protected int doPreExecutionCheck(HttpServletRequest request, HttpServletResponse response) throws SystemException {
        return AbstractCommandServlet.SESSION_LOGGED_IN;
    }
}

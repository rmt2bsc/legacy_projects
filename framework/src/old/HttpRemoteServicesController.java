package com.controller.stateless;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.remoteservices.ServiceConsumer;
import com.remoteservices.http.HttpRemoteServicesConsumer;

import com.action.ActionHandlerException;

import com.util.RMT2Exception;

import com.constants.GeneralConst;
import com.controller.Request;
import com.controller.Response;
import com.controller.stateless.scope.HttpVariableScopeFactory;


/**
 * Routes the client's remote http service request via the appropriate 
 * remote http service consumer.
 * 
 * @author roy.terrell
 * 
 */
public class HttpRemoteServicesController extends AbstractServlet {
    private static final long serialVersionUID = 5839983858208457891L;
    private Logger logger;

    /**
     * Initialize instance and context variables at start up.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.logger = Logger.getLogger("HttpRemoteServicesController");
        return;
    }

    /**
     * Clean up instance and context variables.
     */
    public void destroy() {
        return;
    }

    /**
     * Process the HTTP Get request.
     */
    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.processRequest(request, response);
        }
        catch (ServletException e) {
            throw new ServletException(e.getMessage() + " - Could not process GET request");
        }
    }

    /**
     * Process the HTTP Post request.
     */
    public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.processRequest(request, response);
        }
        catch (ServletException e) {
            throw new ServletException(e.getMessage() + " - Could not process Post request");
        }
    }

    /**
     * Process Post/Get request
     */
    public void processRequest(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        super.processRequest(request, response);
        String serviceId = this.getAction(request);
        HttpRemoteServicesConsumer consumer = new HttpRemoteServicesConsumer();
        try {
            Request genericRequest = HttpVariableScopeFactory.createHttpRequest(request);
            Response genericResponse = HttpVariableScopeFactory.createHttpResponse(response);
            consumer.processRequest(genericRequest, genericResponse, serviceId);
        }
        catch (ActionHandlerException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            this.logger.log(Level.ERROR,msg);
            throw new ServletException(msg); 
        }
        Object results = request.getAttribute(ServiceConsumer.SERVICE_RESULTS);
        if (results instanceof RMT2Exception) {
            RMT2Exception e = (RMT2Exception) results;
            String errorXml = "<error><errorcode>" + e.getErrorCode() + "</errorcode>";
            errorXml += "<errorText>" + e.getMessage() + "</errorText>";
            errorXml += "</error>";
            results = errorXml;
        }
        
        // Send results to client
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        out.println(results);
        out.close();
    }
    
    /**
     * Identifies what service to invoke base on the client's selection.  The service 
     * name is identified as either <i>clientAction</i> or <i>serviceId</i> from 
     * within the user's request.
     * 
     * @param request
     *            The request object
     * @return The service id or null if action cannot be identified.
     * @throws ServletException
     */
    protected String getAction(HttpServletRequest request) {
        String action = super.getAction(request);
        if (action == null) {
            action = request.getParameter(GeneralConst.REQ_SERVICEID); 
        }
        return action;
    }
}
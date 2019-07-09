package com.controller.stateless;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.security.authentication.RMT2SessionBean;
import com.bean.RMT2TagQueryBean;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.stateless.scope.HttpVariableScopeFactory;

import com.util.RMT2Utility;

/**
 * Abstract Servlet that is the ancestor of all non-command servlets of this
 * system.
 * 
 * @author roy.terrell
 */
public abstract class AbstractServlet extends HttpServlet {

    protected ServletContext context;

    protected PrintWriter out;

    protected RMT2SessionBean sessionBean;

    protected RMT2TagQueryBean queryBean;

    protected String appCtx;

    private static Logger logger = Logger.getLogger("AbstractServlet");;

    /**
     * Default initializer
     */
    public synchronized void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.context = getServletContext();
        this.appCtx = this.context.getServletContextName();
    }

    /**
     * Releases any resources.
     */
    public void destroy() {
        return;
    }

    /**
     * Process the HTTP Get request.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.processRequest(request, response);
        }
        catch (ServletException e) {
            throw new ServletException(e.getMessage() + " - Could not process Post request");
        }
    }

    /**
     * Process GET/POST Re-directed request
     * 
     * @param request
     *            The request object
     * @param response
     *            The response object
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer url = request.getRequestURL();
        logger.log(Level.INFO, "Requesting URL: " + url.toString());

        Hashtable hash = this.getCookies(request);
        if (hash != null) {
            hash.get("userId");
        }
    }

    /**
     * Creates cookies to estaablish a form of session tracking a specific web applications.
     * 
     * @param response
     * @param obj An arbitrary object containg user-session data.
     * @throws ServletException
     */
    protected void createCookies(HttpServletResponse response, Object obj) throws ServletException {
        RMT2SessionBean sessionBean = null;

        if (obj == null) {
            String msg = "Cookie could not be created.  The user-session object is invalid";
            logger.log(Level.ERROR, msg);
            return;
        }
        if (obj instanceof RMT2SessionBean) {
            sessionBean = (RMT2SessionBean) obj;
        }

        Cookie userIdCookie = new Cookie("userId", sessionBean.getLoginId());
        userIdCookie.setMaxAge(1800);

        Cookie sessionIdCookie = new Cookie("sessionId", sessionBean.getSessionId());
        sessionIdCookie.setMaxAge(1800);

        // Add cookies.
        response.addCookie(userIdCookie);
        response.addCookie(sessionIdCookie);
    }

    /**
     * Retrieves all cookies related to <i>request</i> and packages the cookies into a Hashtable.
     * 
     * @param request The request pertaining to the cookies
     * @return Hashtable containing each cookie key/value found.
     */
    protected Hashtable getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Hashtable hash = new Hashtable();
        for (int ndx = 0; ndx < cookies.length; ndx++) {
            String name = cookies[ndx].getName();
            String value = cookies[ndx].getValue();
            hash.put(name, value);
        }
        return hash;
    }

    /**
     * Identifies what action to take base on the client's response.
     * 
     * @param request
     *            The request object
     * @return The action.
     * @throws ServletException
     */
    protected String getAction(HttpServletRequest request) {
        return request.getParameter(GeneralConst.REQ_CLIENTACTION);
    }

    /**
     * Packages the request's parameter data into a Properties collection.   The key/value 
     * pair of the property collection will resemble that of the request parameter key/value 
     * arrangement.
     * 
     * @param request HttpServletRequest
     * @return The request's parameters as a Properties object.
     */
    protected Properties getRequestParms(HttpServletRequest request) {
        Request genericRequest = HttpVariableScopeFactory.createHttpRequest(request);
        return RMT2Utility.getRequestData(genericRequest);
    }

    /**
     * Redirect control to another process via a forward call.
     * 
     * @param url
     *            The destination URL
     * @param request
     *            The request object
     * @param response
     *            The response object
     * @throws ServletException
     * @throws IOException
     */
    public void redirect(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Redirect control to another process vis a sendRedirect call.
     * 
     * @param url
     *            The destination URL
     * @param request
     *            The request object
     * @param response
     *            The response object
     * @throws ServletException
     * @throws IOException
     */
    public void clientRedirect(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(url);
    }

    /**
     * Sends response data as a text stream to the client.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void sendAsciiStream(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = (String) request.getAttribute(RMT2ServletConst.RESPONSE_NONJSP_DATA);
        // Send results to client
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        out.println(data);
        out.close();
    }

} // End Class

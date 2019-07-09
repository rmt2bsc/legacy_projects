package com.remoteservices.http;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.Properties;

import com.action.ActionHandlerException;

import com.api.config.HttpSystemPropertyConfig;

import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.RMT2SessionBean;

import com.api.security.pool.AppPropertyPool;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;

import com.controller.Request;
import com.controller.Response;

import com.remoteservices.AbstractClientAction;
import com.remoteservices.ServiceConsumer;
import com.remoteservices.ServiceHandlerException;

import com.util.RMT2Exception;
import com.util.RMT2Utility;
import com.util.SystemException;

/**
 * Provides basic client functionality to invoke a remote service using the user's  
 * request object, response object, and the service id (command).  The request object
 * should contain all the necessary URL parmaters as it's key/value pairs.  
 * HttpRemoteServicesConsumer is capable of only communicating with Http URL's.
 * <p>
 * An example of invoking a remote service named, "userprofile", using a descendent 
 * of AbstractClientAction (UsersConsumer):
 * <blockquote>
 * // Use the user's <i>Request</i> and <i>Response</i> objects to initialize the consumer<br>
 * UsersConsumer service = new UsersConsumer(request, response, "userprofile");<br>
 * service.processRequest();<br><br>
 * // Get results of service call and store on the user's request<br>
 * Object data = (String) request.getAttribute(ServiceConsumer.SERVICE_RESULTS);<br>
 * </blockquote>
 * 
 * @author RTerrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 * 
 */
public class HttpRemoteServicesConsumer extends AbstractClientAction implements ServiceConsumer {
    private Properties prop;

    private Logger logger;

    /**
     * Creates an instance of HttpRemoteServicesConsumer and initializes the 
     * logger.
     * 
     */
    public HttpRemoteServicesConsumer() {
        super();
        this.logger = Logger.getLogger("HttpRemoteServicesConsumer");
        this.logger.log(Level.INFO, "HttpRemoteServicesConsumer created.");
    }

    /**
     * Creates an instance of HttpRemoteServicesConsumer which is assoicated with a 
     * request and response.   The request shoud contain the necessary parameters
     * 
     * @param request
     *          The user's request object.
     * @param response
     *          The user's response object.
     * @param command
     *          The name of the service to invoke
     */
    public HttpRemoteServicesConsumer(Request request, Response response, String command) {
        this();
        this.request = request;
        this.response = response;
        this.command = command;
        this.init(null, request);
    }

    /* (non-Javadoc)
     * @see com.remoteservices.AbstractClientAction#close()
     */
    @Override
    public void close() {
        this.prop = null;
        this.logger = null;
        super.close();
    }

    /**
     * Drives the process of consuming a remote service.  This method requires that 
     * the request, response, and command values are setup before invocation.  The 
     * user is required to implement the receiveClientData and sendClientData methods.
     * 
     * @param request
     *          The user's request object.
     * @param response
     *          The user's response object.
     * @param command
     *          The name of the service to invoke
     * @throws RuntimeException
     * @deprecated Will be removed from project in future revisions.  For backward compatibility.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        this.request = request;
        this.response = response;
        this.command = command;
        this.init(null, request);
        this.processRequest();
    }

    /**
     * Drives the process of handling the client's request by consuming a remote
     * service using the user'request object, response object, and service id.
     */
    public void processRequest() throws SystemException {
        try {
            super.processRequest();
        }
        catch (ActionHandlerException e) {
            throw new SystemException(e.getMessage(), e.getErrorCode());
        }
    }

    /**
     * Build the URL parameter list from the key/value pairs that exist in the 
     * user's request object.  Basically, the key/value pairs of the Request 
     * object are copied to a Properties collection. The key/value pairs can 
     * exist within the request's parameter list and/or its attribute list.
     * <p>
     * This method depends on the Request object being available to obtain the 
     * data parameters needed to satisfy the service call.  In the event that
     * the Request object is invalid or unavailable, this logic is skipped.
     * 
     * @throws ActionHandlerException
     */
    public void receiveClientData() throws ActionHandlerException {
        if (this.request != null) {
            this.prop = this.receiveBasicClientData();

            // Copy the remaining request parameter and attribute key/value pairs. 
            this.prop = RMT2Utility.getRequestData(this.request, this.prop);
            this.setServiceData(this.prop);
        }
    }

    /**
     * Obtains basic input from client.  Each data item retrieved is used to 
     * build a list of key/value pairs that are appended to the service's URL 
     * as a parameter list.  The key value pairs are will include the service 
     * id, user's login, password and the target application name.   These data values 
     * can be identified as follows:
     *   <ul>
     *     <li>{@link ServiceConsumer.SERVICE_ID} or {@link GeneralConst.REQ_CLIENTACTION} as id of the service to invoke.</li>
     *     <li>{@link AuthenticationConst.AUTH_PROP_USERID AUTH_PROP_USERID} or SessionBean.getLoginId() as user's login id.</li>
     *     <li>{@link AuthenticationConst.AUTH_PROP_PASSWORD AUTH_PROP_PASSWORD}as user's password.</li>
     *     <li>{@link AuthenticationConst.AUTH_PROP_MAINAPP AUTH_PROP_MAINAPP} or SessionBean.getOrigAppId() as the target application.</li>
     *     <li>{@link AuthenticationConst.AUTH_PROP_MAINAPP AUTH_PROP_SESSIONID AUTH_PROP_SESSIONID} as the session id.</li>
     *   </ul>
     * 
     * All parameter values are obtained via the request object which are identified as 
     * either a parameter or an attribute.  The application configuration file will be 
     * used to locate the application id in the event it is not found in the request object. 
     * <p>
     * This method depends on the Request object being available to obtain the 
     * data parameters needed to satisfy the service call.  In the event that
     * the Request object is invalid or unavailable, this logic is skipped.
     * @throws ActionHandlerException
     */
    protected Properties receiveBasicClientData() throws ActionHandlerException {
        Properties prop = new Properties();

        // Set the client action which actually is the service id.
        String servId = this.getServiceId();
        if (servId != null) {
            prop.setProperty(GeneralConst.REQ_SERVICEID, this.getServiceId());
        }

        String loginServId = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_LOGINSRC);
        String loginId;
        String pw;
        String appName;
        RMT2SessionBean sessionBean = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);

        // Nullify sessionBean when logging into an application
        if (servId.equalsIgnoreCase(loginServId)) {
            sessionBean = null;
        }

        // Look for the login id and application id from the request 
        // as either a parameter or an attribute in the stated order.
        if (sessionBean == null) {
            loginId = this.request.getParameter(AuthenticationConst.AUTH_PROP_USERID);
            if (loginId == null || loginId.equals("")) {
                loginId = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_USERID);
            }
            pw = this.request.getParameter(AuthenticationConst.AUTH_PROP_PASSWORD);
            if (pw == null || pw.equals("")) {
                pw = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_PASSWORD);
            }
            appName = this.request.getParameter(AuthenticationConst.AUTH_PROP_MAINAPP);
            if (appName == null || appName.equals("")) {
                appName = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_MAINAPP);
                if (appName == null) {
                    try {
                        appName = AppPropertyPool.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
                    }
                    catch (Exception e) {
                        appName = null;
                    }
                }
            }
        }
        else {
            loginId = sessionBean.getLoginId();
            appName = sessionBean.getOrigAppId();
            pw = this.request.getParameter(AuthenticationConst.AUTH_PROP_PASSWORD);
            if (pw == null || pw.equals("")) {
                pw = (String) this.request.getAttribute(AuthenticationConst.AUTH_PROP_PASSWORD);
            }
        }

        // Set user's login id      
        if (loginId != null) {
            prop.setProperty(AuthenticationConst.AUTH_PROP_USERID, loginId);
        }

        // Set user's password, if available        
        if (pw != null) {
            prop.setProperty(AuthenticationConst.AUTH_PROP_PASSWORD, pw);
        }

        // Get source application name
        if (appName != null) {
            prop.setProperty(AuthenticationConst.AUTH_PROP_MAINAPP, appName);
        }

        // Set session id
        String temp = this.request.getSession().getId();
        prop.put(AuthenticationConst.AUTH_PROP_SESSIONID, temp);

        return prop;
    }

    /**
     * Send the final results of the service call to the client via the request
     * object. Override this method to send extra data items.
     * <p>
     * This method depends on the Request object being available in order to send 
     * the results of the service to the caller.  In the event that the Request 
     * object is invalid or unavailable, this logic is skipped.
     * 
     * @throws ServiceHandlerException
     */
    public void sendClientData() throws ActionHandlerException {
        if (this.request != null) {
            this.request.setAttribute(ServiceConsumer.SERVICE_RESULTS, this.serviceResults);
        }
        return;
    }

    /**
     * Invokes a remote authentication service using by <i>message</i> as the source.
     * 
     * @param message 
     *          The id of the service to call.
     * @return Object 
     *          An arbitrary object specific to the service.
     * @throws SystemException 
     */
    public static final Object callService(String message, Request request, Response response) throws SystemException {
        HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer();
        try {
            // Call service
            srvc.processRequest(request, response, message);
            Object results = srvc.getServiceResults();
            return results;
        }
        catch (RMT2Exception e) {
            throw new SystemException(e.getMessage(), e.getErrorCode());
        }
        finally {
            srvc.close();
            srvc = null;
        }
    }

}

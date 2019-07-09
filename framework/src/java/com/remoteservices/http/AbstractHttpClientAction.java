package com.remoteservices.http;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.Serializable;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Properties;
import java.util.List;

import com.action.ActionHandlerException;
import com.action.ICommand;
import com.action.AbstractActionHandler;

import com.api.DaoApi;
import com.api.config.HttpSystemPropertyConfig;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.RMT2SessionBean;
import com.api.security.pool.AppPropertyPool;
import com.api.xml.XmlApiFactory;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;
import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.remoteservices.ServiceConsumer;
import com.remoteservices.ServiceHandlerException;

import com.util.RMT2File;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * This abstract class provides the template needed to allow clients, who are
 * responding to a request, to invoke or consume remote services over the Http
 * protocol. AbstractHttpClientAction is capable of only communicating with Http
 * URL's.   The results of the service call can be found on the request as an 
 * attribute named, {@link ServiceConsumer.SERVICE_RESULTS SERVICE_RESULTS}.
 * 
 * An example of invoking a remote service named, "userprofile", using a descendent 
 * of AbstractHttpClientAction (UsersConsumer):
 * <blockquote>
 * UsersConsumer service = new UsersConsumer();<br>
 * service.processRequest(request, response, "userprofile");<br><br>
 * // Get results of service call <br>
 * Object data = (String) request.getAttribute(ServiceConsumer.SERVICE_RESULTS);<br>
 * </blockquote>
 * 
 * @author Roy Terrell
 * @deprecated Replaced by com.remoteservices.AbstractClientAction. 
 * 
 */
public abstract class AbstractHttpClientAction extends AbstractActionHandler implements ICommand, ServiceConsumer {

    private Logger logger;

    private Object serviceData;

    private Object serviceResults;

    private String serviceId;

    private DaoApi xmlApi;

    private boolean error;

    private static Properties SERVICES;

    private static Properties SERVICE_TYPES;

    /**
     * Default constructor which initializes className.
     * 
     */
    public AbstractHttpClientAction() {
        super();
        logger = Logger.getLogger("AbstractHttpClientAction");
    }

    /**
     * Creates an AbstractHttpClientAction by initializing the request and
     * response variables.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param command
     *            The user's command
     * @throws ServiceHandlerException
     */
    public AbstractHttpClientAction(Request request, Response response, String command) throws ActionHandlerException {
        this();
        try {
            this.init(null, request);
        }
        catch (SystemException e) {
            this.logger.log(Level.ERROR, e.getMessage());
            throw new ActionHandlerException(e.getMessage());
        }
    }

    /**
     * Initializes an AbstractHttpClientAction object with a servlet context and 
     * the client's request.   Attempts to load a list of web services fro the 
     * database.
     * 
     * @param _context
     *            the servet context
     * @param _request
     *            the http servlet request
     * @throws SystemException 
     *           General initialization errors occuring at the ancestor or the 
     *           loading of web service failed.
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
        this.logger = Logger.getLogger("AbstractHttpClientAction");
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#close()
     */
    @Override
    public void close() {
        this.serviceData = null;
        this.serviceId = null;
        this.xmlApi.close();
        this.xmlApi = null;
        this.serviceResults = null;
        try {
            super.close();
        }
        catch (ActionHandlerException e) {
            this.logger.log(Level.ERROR, e.getMessage());
        }
        this.logger = null;
    }

    /**
     * Drives the process of handling the client's request by consuming a remote
     * service using request, response, and command. This method requires that
     * the descendent implements the prepareInputData and processResults
     * methods.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        super.processRequest(request, response, command);

        // Service id must be stated.
        if (command == null) {
            this.msg = "Service Id failed to be identified";
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }

        this.serviceId = this.getServiceId(command);
        // Get URL of remote service application.
        String serviceUrl = this.getServiceUrl();

        // Prepare to invoke remote service
        try {
            this.receiveClientData();
            Object results = this.invokeService(serviceUrl);
            this.processResults(results);
            this.sendClientData();
        }
        catch (ServiceHandlerException e) {
            throw new ActionHandlerException(e);
        }
    }

    /**
     * Implement this method to perform the following process: <br>
     * <ol>
     * <li>Obtain data from the client's request.</li>
     * <li>Build service URL's parameter list as either a Properties object or
     * a Serializable object.</li>
     * <li>Assign the parameter object via a call to
     * {@link #setServiceData(Object) setServiceData}</li>
     * </ol>
     */
    public abstract void receiveClientData() throws ActionHandlerException;

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
     * 
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
     * Invokes the targeted remote service using data gathered fromt the user's
     * request. The input data must be of type Properties or some other
     * Serializable object.
     * 
     * @param url
     *            the URL of the remote service.
     * @return An InputStream
     * @throws ServiceHandlerException
     */
    public InputStream invokeService(String url) throws ServiceHandlerException {
        Serializable serialObject;
        Properties prop;
        InputStream in = null;

        if (url == null) {
            throw new ServiceHandlerException("Consumption of service failed.  Service URL is null or invalid");
        }

        try {
            HttpClient http = new HttpClient(url);
            if (this.serviceData == null) {
                in = (InputStream) http.sendPostMessage();
            }
            else if (this.serviceData instanceof Properties) {
                prop = (Properties) this.serviceData;
                in = (InputStream) http.sendPostMessage(prop);
            }
            else if (this.serviceData instanceof Serializable) {
                serialObject = (Serializable) this.serviceData;
                in = (InputStream) http.sendPostMessage(serialObject);
            }
            return in;
        }
        catch (Exception e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ServiceHandlerException(this.msg);
        }
    }

    /**
     * Processes the results of the service call and packages the processed
     * results into a member variable designated as the service results. By
     * default, the final results of the service call exists as String. Override
     * this method to provide more of a customized implemntation. If a custom
     * implementation is provided, ensure that a call is made to
     * {@link setServiceResults(Object)  setServiceResults} passing the final
     * data results as a generic parameter.
     * <p>
     * If this method id overridden, be sure to call the getServiceResults() 
     * method at the descendent level in order to further process the value 
     * returned from the remote call.
     * 
     * @param results InputStream
     * @throws ServiceHandlerException
     */
    public void processResults(Object results) throws ServiceHandlerException {
        InputStream in;

        if (results == null) {
            this.setServiceResults(null);
            return;
        }
        if (results instanceof InputStream) {
            in = (InputStream) results;
        }
        else {
            return;
        }

        Object data;
        try {
            data = RMT2File.getStreamObjectData(in);
            this.setServiceResults(data);
            this.verifyServiceResults(data);
            return;
        }
        catch (SystemException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ServiceHandlerException(e);
        }
    }

    /**
     * Determines the success or failure of the service call based on the return type 
     * of the results.  The service is considered to have failed if the data type of 
     * the result is of type Exception or of type String which the value is a XML 
     * document where the token, <i>"<error>"</i>, can be found.  
     * @see com.util.RMT2Exception#getMessageAsXml() getMessageAsXml() for a full 
     * explanation of the format for an exception message as XML.
     * <p>
     * When the data type of <i>data</i> is a String, it is safe to assume, regardless 
     * of the success or failure of the service call, that <i>data</i> is a XML document 
     * and this method creates a XML DaoApi member instance from the document.  
     * 
     * @param data 
     *          An arbitrary object representing the results of the service call.   Its 
     *          runtime type will be either an Exception or a String.
     */
    private void verifyServiceResults(Object data) {
        this.error = false;
        if (data == null) {
            return;
        }
        // Determine if error occurred.
        if (data instanceof Exception) {
            this.error = true;
        }
        else if (data instanceof String) {
            this.xmlApi = XmlApiFactory.createXmlDao(data.toString());
            if (data.toString().indexOf("<error>") > -1) {
                this.error = true;
            }
        }
        return;
    }

    /**
     * Send the final results of the service call to the client via the request
     * object. Override this method to send extra data items.
     * 
     * @throws ServiceHandlerException
     */
    public void sendClientData() throws ActionHandlerException {
        this.request.setAttribute(ServiceConsumer.SERVICE_RESULTS, this.serviceResults);
        return;
    }

    /**
     * Get the service id
     * 
     * @return service id
     */
    public String getServiceId() {
        return this.serviceId;
    }

    /**
     * Extracts the actual service id from a JSP client action command.
     * 
     * @param command 
     *          The client action command string which is delimited by periods.   This value 
     *          is usually sent from the JSP in the format: 
     *          [Application Module].[Page Name].[Client Action Name].  <i>Client Action Name</i>
     *          is the value targetd as the service id. 
     * @return the service id.
     */
    public String getServiceId(String command) {
        List list = RMT2String.getTokens(command, ".");
        if (list.size() >= 1) {
            return (String) list.get(list.size() - 1);
        }
        return command;
    }

    /**
     * Builds the base part of the HTTP URL which will function to identify and 
     * invoke the requested web service.  The componentes that make up the base 
     * part of the URL are the <i>server</i> + <i>services app name</i> + <i>servlet</i>.
     * These components are configured as the web containter environment variables and
     * are assigned to the System properties collection during server startup.
     * 
     * @return URL as a String.
     * @throws ActionHandlerException 
     *           When the URL is not found, general error loading or obtaining 
     *           list of services, or if the URL is syntactically incorrect. 
     * @deprecated Will be removed in future versions.   Instead, use static method, 
     *           {@link com.api.messaging.webservice.http.HttpResourceFactory#getServiceDispatchUrl() getServiceDispatchUrl()}           
     */
    protected String getServiceUrl() throws ActionHandlerException {
        String url;
        String server;
        String servicesApp;
        String servicesServlet;

        // Build service URL
        server = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVER);
        servicesApp = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICE_APP);
        servicesServlet = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICE_SERVLET);
        url = server + "/" + servicesApp + "/" + servicesServlet;

        // Validate syntax of URL
        try {
            new URL(url);
            return url;
        }
        catch (MalformedURLException e) {
            this.msg = "Remote Service URL, " + url + ", is syntactically incorrect.  Other Message: " + e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
    }

    /**
     * Builds a XML  service message using the user's login id, the 
     * return code of the service call, server message, and the name 
     * of the originating application.  The XML format of the message 
     * goes as follows:
     * 
     * {@code <ServiceMessageBuilder>}<br>
     *   {@code <LoginId></LoginId>}<br>
     *   {@code <ReturnCode></ServiceReturnCode>}<br>
     *   {@code <Message></Message>}<br>
     *   {@code <OrigApp></OrigApp>}<br>
     * {@code </ServiceMessageBuilder>}<br>
     * 
     * @param loginId  The user's login id
     * @param rc The return code of the service call.
     * @param msg A text message as a result of the service call.
     * @param origApp The name of the application that made the call.
     * @return The message.
     */
    protected String buildClientMessage(String loginId, int rc, String msg, String origApp) {
        StringBuffer xml = new StringBuffer(100);
        loginId = (loginId == null ? "" : loginId);
        msg = (msg == null ? "" : msg);
        origApp = (origApp == null ? "" : origApp);
        xml.append("<ServiceMessageBuilder>");
        // Setup login id
        xml.append("<LoginId>");
        xml.append(loginId);
        xml.append("</LoginId>");

        // Setup return code
        xml.append("<ServiceReturnCode>");
        xml.append(rc);
        xml.append("</ServiceReturnCode>");

        // Setup Text Message
        xml.append("<Message>");
        xml.append(msg);
        xml.append("</Message>");

        // Setup The name of the original application
        xml.append("<OrigApp>");
        xml.append(origApp);
        xml.append("</OrigApp>");
        xml.append("</ServiceMessageBuilder>");
        return xml.toString();
    }

    /**
     * Get complete list of remote services that are configured for the system.
     * 
     * @return Properties
     * @throws SystemException  if the services could not be loaded and/or obtained.
     */
    public synchronized static Properties getServices() throws SystemException {
        return AbstractHttpClientAction.SERVICES;
    }

    /**
     * Get complete list of remote service types that are configured for the system.
     * 
     * @return Properties
     * @throws SystemException  if the services could not be loaded and/or obtained.
     */
    public synchronized static Properties getServiceTypes() throws SystemException {
        return AbstractHttpClientAction.SERVICE_TYPES;
    }

    /**
     * No Action
     */
    public void edit() throws ActionHandlerException {
        return;
    }

    /**
     * No Action
     */
    public void add() throws ActionHandlerException {
        return;
    }

    /**
     * No Action
     */
    public void save() throws ActionHandlerException {
        return;
    }

    /**
     * No Action
     */
    public void delete() throws ActionHandlerException {
        return;
    }

    /**
     * Get the input service data.
     * 
     * @return Data as an Object
     */
    public Object getServiceData() {
        return serviceData;
    }

    /**
     * Set the service input data.
     * 
     * @param serviceData
     *            the data from the client's request.
     */
    public void setServiceData(Object serviceData) {
        this.serviceData = serviceData;
    }

    /**
     * Get the results of the service call.
     * 
     * @return the data that was returned from the service call.
     */
    public Object getServiceResults() {
        return serviceResults;
    }

    /**
     * Set the data that was returned from a call to the service.
     * 
     * @param serviceResults
     *            the data produced from the service.
     */
    public void setServiceResults(Object serviceResults) {
        this.serviceResults = serviceResults;
    }

    /**
     * Returns the XML results of the service call as a DaoApi instance.
     * 
     * @return {@link com.api.DaoApi DaoApi}
     */
    //    public DaoApi getXmlResults() {
    //        return this.xmlApi;
    //    }
    /**
     * Indicates whether an error occurred.
     * 
     * @return Returns true for service call failure and false for success.
     */
    public boolean isError() {
        return this.error;
    }
}

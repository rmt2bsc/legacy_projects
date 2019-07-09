package com.dispatch;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Map;
import java.util.Hashtable;
import java.util.Properties;

import com.api.DaoApi;

import com.api.config.HttpSystemPropertyConfig;
import com.api.db.DatabaseException;

import com.api.xml.XmlApiFactory;

import com.util.NotFoundException;
import com.util.RMT2File;
import com.util.SystemException;

import com.constants.GeneralConst;

import com.api.messaging.webservice.ServiceRoutingInfo;
import com.api.messaging.webservice.http.HttpClient;
import com.api.messaging.webservice.http.HttpException;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.pool.AppPropertyPool;

import com.controller.stateless.AbstractServlet;

/**
 * Identifies, invokes and dispatches remote service requests using URL connection 
 * communication methods via the HTTP protocol.   This servlet acts as a liason 
 * between and serves as a hub for the application of the requester and the remote 
 * services.
 * <p>
 * To invoke a remote service, the user must build a URL that targets the service 
 * such as: <br> 
 *   <code>
 *      <protocol>://<host computer><:port>/<ServiceDispatcher>/<remoteservices>/?<serviceId=xxxx&UID=xxxx&appcode=xxxxx>
 *   </code>.
 * <p>   
 * This servlet is responsible for creating a list of avaiable services that can be 
 * accessed by the public.  This list will reside in memory for the life of the 
 * applicaltin and serves as a lookup mechanism for validating service id's and for 
 * obtaiing the associated URL of the service id.  The following keys are configured 
 * in the ServiceDispatcher's configuration file, SystemParms.properties, to provide 
 * values for some of the sections of the URL:
 * <ul>
 *   <li>service_host - identifies the host computer, web application, and servlet 
 *       separated by "/".
 *   </li>
 *   <li>service_module - identifies the command module configured in the applications's 
 *       AppCommandMappings.properties.  The value of the command module should be 
 *       prefixed by "/".
 *   </li>
 * </ul>   
 *     
 * @author appdev
 * @deprecated Use {@link com.api.messaging.webservice.http.engine.HttpServiceController HttpServiceController} from the framework API.  Will be removed in future versions.
 * 
 */
public class ServiceController extends AbstractServlet {
    private static final long serialVersionUID = 8295709423196681300L;

    /**
     * User Resource Access Verification service name
     */
    private static final String CHECK_SERVICE_ACCESS = "checkserviceaccess";

    private static final String SERV_RESOURCE_TYPE_ID = "3";

    private static final String SERV_RESOURCE_TYPE = "ResourceTypeId";

    private static Map SERVICES;

    private Logger logger;

    /**
     * Drives the process of initializing the ServiceController.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.initServlet();
    }

    /**
     * Initializes the ServiceController member variables.
     */
    public void initServlet() throws ServletException {
        this.logger = Logger.getLogger("ServiceController");
    }

    /**
     * Load services map into memory if this is the first time this servlet has been invoked.
     * 
     * @param request The request containing the data items needed to fetch desired services.
     * @throws SystemException General and database access errors.
     */
    protected void loadServices(HttpServletRequest request) throws SystemException {
        if (ServiceController.SERVICES == null) {
            try {
                String data = (String) this.getServices(request);
                ServiceController.SERVICES = (Map) this.createServiceList(data);
            }
            catch (Exception e) {
                throw new SystemException(e);
            }
        }
    }

    /**
     * Invokes the "getservices" remote service in order to obtain a list 
     * user resources that are of services type.  This method is able to 
     * determine the host application slated to fetch the lsit of services 
     * by looking up the values in its system configuation file.
     * 
     * @param request The user's request.
     * @return The results of the service call which is usuall a XML document 
     *         in the form of a String.
     * @throws ServletException 
     *           When a problem arises constructing the Service URL or invoking 
     *           the service.
     */
    private Object getServices(HttpServletRequest request) throws ServletException {

        Properties prop = new Properties();
        String url = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVER);
        String loginId = request.getParameter(AuthenticationConst.AUTH_PROP_USERID);

        // Get the services host and the servlet to contact.
        url = url + "/" + System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_HOST);
        // Get the command module we are targeting.
        url = url + System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_COMMAND);
        // Get Service Id
        String serviceId = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVICELOAD_SERVICEID);
        // Recognize the client action as the service id.
        prop.setProperty(GeneralConst.REQ_CLIENTACTION, serviceId);
        // Setup other parameters needed to make a successful service call. 
        prop.setProperty(ServiceController.SERV_RESOURCE_TYPE, ServiceController.SERV_RESOURCE_TYPE_ID);

        try {
            prop.setProperty(AuthenticationConst.AUTH_PROP_USERID, loginId);
        }
        catch (Exception e) {
            String msg = "Service registry was not loaded due to invalid or null user login";
            logger.warn(msg);
            throw new ServletException(msg);
        }

        try {
            HttpClient http = new HttpClient(url);
            InputStream in = (InputStream) http.sendPostMessage(prop);
            Object data = null;
            try {
                data = RMT2File.getStreamObjectData(in);
                return data;
            }
            catch (SystemException e) {
                throw new ServletException(e);
            }
            finally {
        	http.close();
            }
        }
        catch (HttpException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Creates a list of available services in the form of key/value pairs and persist 
     * the list in memory for repetive usage.  The key represents the service name, and 
     * the value represents the service URL.  The list is used as a lookup mechanism for 
     * service requests.  The query method used to extract the data is XPath, which targets 
     * each user_resource tag element as a row of data. 
     * 
     * @param data 
     *          A XML document representing data from the user_resource table.  Each row of 
     *          data will be enclosed by the xml tag, user_resource.
     * @return Hashtable of service name/service URL key/value pairs.
     * @throws DatabaseException General database errors
     * @throws SystemException General system errors.
     */
    private Hashtable createServiceList(String data) throws DatabaseException, SystemException {
        Hashtable srvHash = null;
        String msg;
        int loadCount = 0;

        if (data == null) {
            return null;
        }
        System.out.println(data);
        DaoApi api = XmlApiFactory.createXmlDao(data);
        try {
            // Get all user_resource elements
            Object result[] = api.retrieve("user_resource");
            int rows = ((Integer) result[0]).intValue();
            if (rows <= 0) {
                return srvHash;
            }

            // Begin to build services map
            while (api.nextRow()) {
                // Instantiate service map for the first interation.
                if (srvHash == null) {
                    srvHash = new Hashtable();
                }
                String name;
                String url;
                String secured;
                try {
                    name = api.getColumnValue("name");
                    url = api.getColumnValue("url");
                    secured = api.getColumnValue("secured");
                    ServiceRoutingInfo srvc = new ServiceRoutingInfo();
                    srvc.setName(name);
                    srvc.setUrl(url);
                    srvc.setSecured(secured == "1");
                    srvHash.put(name, srvc);
                    loadCount++;
                }
                catch (NotFoundException e) {
                    msg = "Could not locate remote service data: " + e.getMessage();
                    logger.log(Level.WARN, msg);
                }
            } // Go to next element
            return srvHash;
        }
        catch (DatabaseException e) {
            throw e;
        }
        catch (SystemException e) {
            throw e;
        }
        finally {
            logger.log(Level.INFO, "Total number of services loaded: " + loadCount);
        }
    }

    /**
     * Process the HTTP Get request by forwarding the request to processRequest method.
     */
    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.processRequest(request, response);
        }
        catch (ServletException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Process the HTTP Post request by forwarding the request to processRequest method.
     */
    public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.processRequest(request, response);
        }
        catch (ServletException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Processes the request to invoke a particular remote service.  The following 
     * sequence of events occurs when a remote service is requested:
     * <ol>
     *   <li>Load list of services into memory during the first time of servlet invocation.</li>
     *   <li>Collect request parameters into a Properties collection.</li>
     *   <li>Validate the request parameters.</li>
     *   <li>Validate the service id by obtaining its related URL.</li>
     *   <li>Invoke the service.</li>
     * </ol>
     * 
     * @param request
     *            The request object
     * @param response
     *            The response object
     * @throws ServletException 
     *            When a problem arises loading services list, validating input 
     *            parameters, or invoking the service. 
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.processRequest(request, response);
        String msg;

        // Load Services
        try {
            this.loadServices(request);
        }
        catch (SystemException e) {
            throw new ServletException(e);
        }

        // Gather request parameters into a Properties collection
        Properties prop = this.getRequestParms(request);

        try {
            // Verify that requrestor sent required parameters.            
            this.validateRequestParameters(prop);

            // Validate Service id by attempting to obtain its assoicated URL
            String serviceId = request.getParameter(GeneralConst.REQ_SERVICEID);
            ServiceRoutingInfo srvc = (ServiceRoutingInfo) ServiceController.SERVICES.get(serviceId);
            if (srvc == null) {
                msg = "Requested service does not exist or is invalid: " + serviceId;
                this.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            }
            if (!serviceId.equalsIgnoreCase(srvc.getName())) {
                msg = "A naming conflict exist between the service id input [" + serviceId + "] and the service data retrieved from list of services [" + srvc.getName() + "]";
                this.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            }
            if (srvc.getUrl() == null) {
                msg = "The URL property of the requested service, " + serviceId + ", does not exist or is invalid: ";
                this.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            }

            // Invoke the service
            Object results = this.invokeService(response, srvc, prop);

            // Return the results of the service invocation to the requestor.
            this.sendResponse(response, results);
        }
        catch (SystemException e) {
            this.sendResponse(response, e);
        }
    }

    /**
     * Verifies that the requestor of a service has supplied the minimum data items 
     * needed to invoke a remote service.  The required data items are: service id, 
     * user's login id, and the user's source application id.
     * 
     * @param parms Properties collection of the request parameters.
     * @throws SystemException If one of the parameters is invalid or null.
     */
    protected void validateRequestParameters(Properties parms) throws SystemException {
        String temp;
        String msg;

        // Service Id is required. 
        temp = parms.getProperty(GeneralConst.REQ_SERVICEID);
        if (temp == null) {
            msg = "Remote service cannot be invoked without a Service Id input parameter";
            this.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        // User login id is required
        temp = parms.getProperty(AuthenticationConst.AUTH_PROP_USERID);
        if (temp == null) {
            msg = "Remote service cannot be invoked without the user\'s Login Id input parameter";
            this.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        // User's application id is required.
        temp = parms.getProperty(AuthenticationConst.AUTH_PROP_MAINAPP);
        if (temp == null) {
            msg = "Remote service cannot be invoked without the user\'s Application Id input parameter";
            this.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
    }

    /**
     * Performs the invocation of a remote service and returns the results to the caller.
     * 
     * @param response 
     *          The HttpServletResponse object used to send the results of the service 
     *          call back to the requestor.  The results are passed back as an Object 
     *          via the ObjectOutputStream. 
     * @param url 
     *          The URL of the remote service to invoke.
     * @param parms 
     *          The parameters that are required to be assoicated with the remote service 
     *          URL. 
     * @throws SystemException 
     *          When login id cannot be determined for secured services, http client errors, 
     *          general I/O errors, or a class not found error pertaining to the object returned 
     *          by the remote service. 
     */
    protected Object invokeService(HttpServletResponse response, ServiceRoutingInfo srvc, Properties parms) throws SystemException {
        String msg;
        String loginId;

        // Determine if user is authorized to access this service provided that this is a secured service.
        if (srvc.isSecured()) {
            Properties srvcCheckProps = new Properties();
            srvcCheckProps.setProperty("ResrcName", srvc.getName());

            // Determine if the user's login id was supplied
            loginId = parms.getProperty(AuthenticationConst.AUTH_PROP_USERID);
            if (loginId == null) {
                msg = "Service invocation failed: Service, " + srvc.getName() + ", requires a user login id in order to determine authorization";
                this.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            }
            srvcCheckProps.setProperty("UserLoginId", loginId);
            ServiceRoutingInfo authSrvc = new ServiceRoutingInfo();
            authSrvc.setName(ServiceController.CHECK_SERVICE_ACCESS);
            this.authorizeUserAccess(authSrvc, srvcCheckProps);
        }

        parms.setProperty(GeneralConst.REQ_CLIENTACTION, srvc.getName());

        // Complete the format of the service's URL
        String server = System.getProperty(HttpSystemPropertyConfig.PROPNAME_SERVER);
        String url = server + "/" + srvc.getUrl();

        try {
            // Prepare to invoke the service.            
            HttpClient http = new HttpClient(url);
            InputStream in = (InputStream) http.sendPostMessage(parms);
            // Obtain the results of the service invocation.            
            ObjectInputStream ois = new ObjectInputStream(in);
            Object data = null;
            if (ois != null) {
                data = ois.readObject();
            }
            http.close();
            return data;
        }
        catch (EOFException e) {
            msg = "EOF I/O error: End of input stream reached unexpectedly or input URL, " + url + ", does not exist";
            this.logger.log(Level.ERROR, msg);
            throw new SystemException(msg);
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
    }

    /**
     * Determines whether or not the user has access to a particular service.
     * 
     * @param srvc {@link com.dispatch.ServiceController.ServiceRoutingInfo ServiceFields}
     * @param prop 
     * 		A Hash collection containing key/value pairs for DataSource view 
     * 		properties "ResrcName" and "UserLoginId".
     * @throws SystemException When user fails authorization or for general communication errors.
     */
    protected void authorizeUserAccess(ServiceRoutingInfo srvc, Properties prop) throws SystemException {
        String msg = null;

        // Complete the format of the service's URL
        String server = AppPropertyPool.getProperty(HttpSystemPropertyConfig.PROPNAME_ENV);
        String url = server + srvc.getUrl();

        try {
            // Prepare to invoke the service.            
            HttpClient http = new HttpClient(url);
            InputStream in = (InputStream) http.sendPostMessage(prop);
            // Obtain the results of the service invocation.            
            ObjectInputStream ois = new ObjectInputStream(in);
            Object data = null;
            if (ois != null) {
                data = ois.readObject();
            }
            http.close();
            // User is considered authorized if data is valid
            if (data == null || data instanceof Exception) {
                msg = "User, " + prop.getProperty(AuthenticationConst.AUTH_PROP_USERID) + ", is not authorized to access service, " + srvc.getName();
                this.logger.log(Level.ERROR, msg);
                throw new SystemException(msg);
            }
            return;
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
    }

    /**
     * Returns the data back to the client as a response to a service call.  The results will 
     * either be some arbitrary object or an Exception which should contain message text 
     * explaining the cause of the error.
     * 
     * @param response 
     * 		The servlet response object used for transmitting the data back to the client. 
     * @param data An arbitrary object representing the data to be transmitted.
     * @throws IOException For general I/O errors with the output stream.
     */
    private void sendResponse(HttpServletResponse response, Object data) throws IOException {
        // Return the exception to the requestor.
        String content = "application/x-java-serialized-object";
        response.setContentType(content);
        ObjectOutputStream os = new ObjectOutputStream(response.getOutputStream());
        os.writeObject(data);
        os.flush();
        os.close();
        return;
    }

} // End Class

package com.api.messaging.webservice.soap.engine;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.api.messaging.MessagingException;
import com.api.messaging.webservice.ServiceRoutingInfo;
import com.api.messaging.webservice.soap.SoapMessageHelper;
import com.api.messaging.webservice.soap.SoapResponseException;
import com.api.security.pool.AppPropertyPool;

import com.controller.Request;
import com.controller.Response;
import com.controller.stateless.AbstractServlet;
import com.controller.stateless.scope.HttpVariableScopeFactory;

import com.util.RMT2BeanUtility;
import com.util.SystemException;

/**
 * Abstract Servlet for accepting SOAP based web service requests, dispatching the web service request to its intended 
 * destination, packaging the SOAP response, and sending the SOAP response to the intended caller.
 * <p>
 * <b><u>Configuration notes</u></b><br>
 * <ul>
 *    <li>It is highly important that the concrete class implements method, {@link com.api.messaging.webservice.soap.engine.AbstractSoapEngine#getMessageRoutingInfo(SOAPMessage, Object) getMessageRoutingInfo} so that web service routing information is properly obtained for the SOAP message.</li>
 *    <li>It is required that the application defining the concrete implemenation of this class must identify the class name of the SOAP message router in the AppParms.properties.   The key property that is mapped to the SOAP message router class name should be named, <i>soapMessageRouter</i>.
 *  </u>
 * 
 * 
 * @author appdev
 *
 */
public abstract class AbstractSoapEngine extends AbstractServlet {

    private static final long serialVersionUID = 5433010431085175539L;

    private static Logger logger = Logger.getLogger("AbstractSoapEngine");

    protected static final String SOAP_MSG_ROUTER_KEY = "soapMessageRouter";

    private Object dataSource;

    protected SoapMessageRouter router;

    /**
     * 
     */
    public AbstractSoapEngine() {
        return;
    }

    /**
     * Drives the initialization process when the SoapMessagingController is first loaded.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.initServlet();
    }

    /**
     * Initializes the router API with the HTTP router implementation and loads the web services routing table.
     */
    public void initServlet() throws ServletException {
        try {
            RMT2BeanUtility beanUtil = new RMT2BeanUtility();
            String routerClass = AppPropertyPool.getProperty(AbstractSoapEngine.SOAP_MSG_ROUTER_KEY);
            this.router = (SoapMessageRouter) beanUtil.createBean(routerClass);
        }
        catch (Exception e) {
            throw new ServletException("Unable to instantiate SOAP Message Router Api", e);
        }
        return;
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
     *   <li>Invoke the service.  It is expected of the service to send its reply as a valid SOAP String.</li>
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
        SOAPMessage results;

        ServiceRoutingInfo srvc = null;

        Request genericRequest = HttpVariableScopeFactory.createHttpRequest(request);
        Response genericResponse = HttpVariableScopeFactory.createHttpResponse(response);

        SoapMessageHelper helper = new SoapMessageHelper();

        SOAPMessage sm = null;
        try {
            // Get SOAP message instance from the Object Input Stream of the request
            sm = helper.getSoapInstance(genericRequest);
            // Validate SOAP message and get routing information for message 
            srvc = this.getMessageRoutingInfo(sm, this.dataSource);
            // Invoke the service.   The consumer is required to send the response as a valid SOAP String.
            results = this.invokeService(srvc, sm, request);
            // Return the results of the service invocation to the requestor.
            this.sendResponse(genericResponse, results);
        }
        catch (Exception e) {
            this.sendErrorResponse(response, e.getMessage());
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    /**
     * Obtain routing information for the SOAP message that is to be processed.   The routing information is basically network details 
     * on how to contact the appropriate web service that is engineered to process the SOAP message.
     * 
     * @param sm
     *                 an instance of SOAPMessage.
     * @param dataSource
     *                 an arbitrary object that serves as the data source used to perform the web service routing informatin lookup.    
     *                 It is up to the implementor to provide the appropriate casting in order to access and manipulate this object.    
     * @return an instance of {@link com.api.messaging.webservice.ServiceRoutingInfo ServiceRoutingInfo}
     * @throws SoapMessageRouterException
     *                 when the routing information is unobtainable due to key not found or the input of invalid data to the routing information 
     *                 search process.
     */
    protected abstract ServiceRoutingInfo getMessageRoutingInfo(SOAPMessage sm, Object dataSource) throws SoapMessageRouterException;

    /**
     * Performs the invocation of a remote service and returns the results to the caller.
     * 
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
    private SOAPMessage invokeService(ServiceRoutingInfo srvc, SOAPMessage soapObj, HttpServletRequest request) throws SoapMessageRouterException, MessagingException {
        SOAPMessage reslults = null;
        String msg = null;
        if (this.router == null) {
            msg = "The SOAP engine router object is invalid or null";
            logger.error(msg);
            throw new SoapMessageRouterException(msg);
        }
        else {
            try {
                SoapMessageHelper helper = new SoapMessageHelper();
                String soapStr = helper.toString(soapObj);
                logger.info("SOAP Request: ");
                logger.info(soapStr);
                reslults = this.router.routeMessage(srvc, soapObj, request);
                soapStr = helper.toString(reslults);
                logger.info("SOAP Response: ");
                logger.info(soapStr);
                return reslults;
            }
            catch (Exception e) {
                throw new MessagingException(e);
            }
        }
    }

    /**
     * Sends a response to the client indicating that web service invocation was a success.  The results will 
     * either be a SOAP message with a normal payload or one with a Fault body derived from text of some Exception.   
     * Both types of errors should be recognized as business errors as it relates to the web service invoked. 
     * 
     * @param response 
     * 		The servlet response object used for transmitting the data back to the client. 
     * @param soapObj 
     *          An arbitrary object representing the data to be transmitted.
     * @throws IOException 
     *          For general I/O errors with the output stream.
     */
    protected void sendResponse(Response response, SOAPMessage soapObj) throws SoapResponseException {
        SoapMessageHelper helper = new SoapMessageHelper();
        helper.sendSoapInstance(response, soapObj);
        return;
    }

    /**
     * Sends a response to the client indicating that the web service invocation failed.   Generally this type of error occurs 
     * within the context of the SOAP engine and are typically considered to be system failures.
     *   
     * @param response
     * @param errorMessage
     * @throws SoapResponseException
     */
    protected void sendErrorResponse(HttpServletResponse response, String errorMessage) throws SystemException {
        Response genericResponse = HttpVariableScopeFactory.createHttpResponse(response);
        SoapMessageHelper helper = new SoapMessageHelper();
        errorMessage = "SOAP Engine Failure.   " + errorMessage + ".   Please contact Technical Support!";
        SOAPMessage err = helper.createSoapFault("Server", errorMessage, null, null);
        try {
            this.sendResponse(genericResponse, err);
        }
        catch (SoapResponseException e) {
            throw new SystemException(e);
        }
        return;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(Object dataSource) {
        this.dataSource = dataSource;
    }

}

package com.remoteservices;

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

import com.api.xml.XmlApiFactory;

import com.remoteservices.http.HttpClient;

import com.util.RMT2Exception;
import com.util.RMT2File;
import com.util.RMT2String;
import com.util.SystemException;

/**
 * This abstract lass provides basic client functionality to establish a connection 
 * to a remote service using Http URL connections.  The sequence of communication 
 * goes as follows when connecting to a remote service: 1) the client connects to 
 * the service dispatcher, 2) the service dispatcher connects to the target service, 
 * 3) the service processes the request and sends results back to the dispatcher, 
 * and 4) the dispatcher sends the results back to the requester.  It usual for this 
 * client to contact the service dispatcher over a unsecurred channel.
 * <p>
 * The following data values are required to be included in the service call as URL 
 * parameters which the source of the parameters is a Properties collection:
 * <ul>
 * <li>The service id</li>
 * <li>The user's login id</li>
 * <li>The name of the application the user originally signed onto the system.</li>
 * </ul>
 * <p>
 * The following data values can be optionally included in the service call as URL 
 * parameters:
 * <ul>
 * <li>{@link com.remoteservices.http.AbstractExternalServerAction.ARG_SENDSOAP ARG_SENDSOAP - instructs the service to send results in the form of a SOAP message.  Valid values are <i>true</i> and <i>false</i>}</li>
 * <li>{@link com.remoteservices.http.AbstractExternalServerAction.ARG_SENDPAYLOAD ARG_SENDPAYLOAD - instructs the service to enclose XML results within a RSPayload structure.  Valid values are <i>true</i> and <i>false</i>}</li>
 * </ul>
 * 
 * @author RTerrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 * 
 */
public abstract class AbstractClientAction extends AbstractActionHandler implements ICommand, ServiceConsumer {
    private Logger logger;

    private Object serviceData;

    private String serviceId;

    private DaoApi xmlApi;

    private boolean error;

    protected Object serviceResults;

    /**
     * Default constructor that is solely used by subclass constructors.
     * 
     */
    protected AbstractClientAction() {
        super();
        logger = Logger.getLogger("AbstractHttpClientAction");
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#close()
     */
    @Override
    public void close() {
        this.serviceData = null;
        this.serviceId = null;
        if (this.xmlApi != null) {
            this.xmlApi.close();
            this.xmlApi = null;
        }
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
     * service identified by its command. This method requires that
     * the descendent implements the receiveClientData and sendClientData
     * methods.
     * 
     * @throws ActionHandlerException
     */
    public void processRequest() throws ActionHandlerException {
        this.serviceId = this.getServiceId(this.command);
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
            throw new ActionHandlerException(e.getMessage(), e.getErrorCode());
        }
    }

    /**
     * Build the URL parameter list that will be used to retrieve the user's
     * profile.  Frist, basic properties are located and assigned specific but 
     * common key names for the service id, user id, user password, appication id, 
     * and session id.  Lastly, the remaining properties from the user's request 
     * parameter and attribute lists are loaded in order to be made availabe to the 
     * service call.  Only those request attribute values that evaluate as type String 
     * are added to the URL parameter list.  The property values assoicated with the common 
     * key values in the first step may exist as duplicates but under another key name.  
     * It will be left to the implementation of the targeted service to pick and choose the 
     * required properties needed to successfully execute the service call. 
     * 
     * @throws ActionHandlerException 
     */
    public abstract void receiveClientData() throws ActionHandlerException;

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
            if (data instanceof RMT2Exception) {
                this.msg = ((RMT2Exception) data).getMessage();
                throw new SystemException(this.msg, ((RMT2Exception) data).getErrorCode());
            }
            if (data instanceof Exception) {
                this.msg = ((Exception) data).getMessage();
                throw new SystemException(this.msg);
            }
            this.setServiceResults(data);
            this.verifyServiceResults(data);
            return;
        }
        catch (SystemException e) {
            this.msg = e.getMessage();
            logger.log(Level.ERROR, this.msg);
            throw new ServiceHandlerException(e.getMessage(), e.getErrorCode());
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
    public String getXmlResults() {
        return null;
    }

    //    public DaoApi getXmlResults() {
    //	return this.xmlApi;
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

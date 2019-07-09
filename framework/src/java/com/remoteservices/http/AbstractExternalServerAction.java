package com.remoteservices.http;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.controller.Context;
import com.controller.Request;
import com.controller.Response;

import com.util.RMT2Exception;
import com.util.SystemException;

/**
 * Abstract producer of remote services which the results returned to the client 
 * as a XML String.   This class is more platform independent and its data can be    
 * managed by any type application that is capable of communicating over the HTTP 
 * protocol.  This class provides the template needed to process a client's service 
 * request over the Http protocol.
 * <p>
 * The XML doucment, Remote Service Payload, will either contain the intended data 
 * the user requested or an error message.  The format of the payload goes as
 * follows:
 * <p><pre>
 *    &lt;RSPayload&gt;
 *       &lt;status&gt;      - 1=success, -1=error
 *       &lt;servicename&gt; - The name of the service that was invoked by the client
 *       &lt;caller&gt;      - The caller of the service
 *       &lt;data&gt;        - The actual data served by the service per user request
 *       &lt;starttime&gt;   - Start date and time of service invocation
 *       &lt;endtime&gt;     - End date and time of service invocation
 *    &lt;/RSPayload&gt;
 *  </pre>
 *      
 * The Remote Service Payload format can be enabled/disabled by including <i>sendaspayload</i> 
 * parameter in the request.  To enable the payload data format, set its value to "true", "yes", 
 * or "y".  To disable the payload data format, set its value to "false", "no", or "n".  These 
 * values are case insensitive.
 *     
 * @author Roy Terrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 * 
 */

public abstract class AbstractExternalServerAction extends AbstractHttpServerAction {
    /** Argument name for XML data payload sent back and forth between client and server */
    public static final String ARG_PAYLOAD = "payload";

    /** Argument name for custom selection criteria */
    public static final String ARG_SELECTCRITERIA = "WhereCriteria";

    /** Argument name for custom ordering criteria */
    public static final String ARG_ORDERCRITERIA = "OrderCriteria";

    /** Indicates whether or not service should return XML results witnin a RSPayload structure */
    public static final String ARG_SENDPAYLOAD = "sendaspayload";

    /** Indicates whether or not service should return XML results as a SOAP message */
    public static final String ARG_SENDSOAP = "sendassoap";

    private Logger logger;

    private boolean errorOccurred;

    private Date starttime;

    private Date endtime;

    private boolean payload;

    private boolean soap;

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public AbstractExternalServerAction() throws SystemException {
        super();
        this.logger = Logger.getLogger("AbstractInternalServerAction");
    }

    /**
     * Contructs a AbstractExternalServerAction object by initializing the servlet
     * context and the user's request.  Determines if XML results are to be enclosed
     * within a RSPayload structure and/or represented as a SOAP message.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public AbstractExternalServerAction(Context context, Request request) throws SystemException, DatabaseException {
        super(context, request);

        // Determine if we are to include XML results within a payload structure
        String value = request.getParameter(AbstractExternalServerAction.ARG_SENDPAYLOAD);
        if (value == null) {
            this.payload = false;
        }
        else {
            this.payload = (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("y"));
        }

        // Determine if we are to represent XML results as a SOAP message.
        value = request.getParameter(AbstractExternalServerAction.ARG_SENDSOAP);
        if (value == null) {
            this.soap = false;
        }
        else {
            this.soap = (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("y"));
        }
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is inistantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
    }

    /**
     * Drives the process of servicing the client's request and returns any data to client 
     * as XML regardless if the operation completed successfully or erroneously. 
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @throws SystemException
     *             when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        try {
            this.starttime = new Date();
            super.processRequest(request, response, command);
            this.errorOccurred = false;
        }
        catch (RMT2Exception e) {
            this.outData = e.getMessageAsXml();
            this.errorOccurred = true;
        }
        if (this.payload) {
            this.outData = this.buildPayLoad((String) this.outData);
        }
        if (this.soap) {
            this.outData = this.buildSOAPMessage((String) this.outData);
        }
        this.sendClientData();
    }

    /**
     * Creates the Remote Service data payload to enclose <i>message</i> within.  The 
     * combination of the two are returned to the client.   The payload exist in XML 
     * format which contains information about the service call as well as the actual 
     * data rendered by the service.
     * 
     * @param message 
     *          The core data returned from the service invocation which the user expects 
     *          manipulate.  This data will be enclosed in the Remote Service Payload and 
     *          is identified as, <i>data</i>.
     * @return String XML document.
     * @deprecated Use buildReturnCode(int, String, String) method.
     */
    protected String buildPayLoad(String message) {
        StringBuffer buf = new StringBuffer(100);
        buf.append("<RSPayload>");

        // Set status
        buf.append("<status>");
        if (this.errorOccurred) {
            buf.append("-1");
        }
        else {
            buf.append("1");
        }
        buf.append("</status>");

        // set service name
        buf.append("<servicename>");
        buf.append(this.command);
        buf.append("</servicename>");

        // set caller
        buf.append("<caller>");
        buf.append("</caller>");

        // set data
        buf.append("<data>");
        buf.append(message);
        buf.append("</data>");

        // set start time
        buf.append("<starttime>");
        buf.append(this.starttime.toString());
        buf.append("</starttime>");

        // set end time
        buf.append("<endtime>");
        this.endtime = new Date();
        buf.append(this.endtime.toString());
        buf.append("</endtime>");

        // end payload
        buf.append("</RSPayload>");
        logger.log(Level.INFO, buf.toString());
        return buf.toString();
    }

    /**
     * Surrounds <i>message</i> within SOAP Envelope element.
     * 
     * @param message The message to include within the envelope.
     * @return String.
     */
    protected String buildSOAPMessage(String message) {
        ServiceMessageBuilder smb = MessageFactory.getSOAPMessageBuilder();
        try {
            Object results = smb.build(message);
            return results.toString();
        }
        catch (HttpException e) {
            return "SOAP ERROR OCCURRED... Do Not know how to handle yet!";
        }
    }

    /**
     * Builds a message containing only the return code and message.   The message format 
     * goes as follows:<br>
     * <pre>
     *   &lt;ServiceResults&gt;
     *     &lt;RtCode&gt;returnCode&lt;/RtCode&gt;
     *     &lt;RtMessage&gt;msg&lt;/RtMessage&gt;
     *   &lt;ServiceResults&gt;
     * </pre>
     * 
     * @param returnCode The return code of the service call
     * @param msg The message associated with the return code.
     * @return XML String.
     * @deprecated Use buildReturnCode(int, String)
     */
    protected String buildSimpleReturnValue(int returnCode, String msg) {
        StringBuffer buf = new StringBuffer(100);
        buf.append("<ServiceResults>");

        buf.append("<RtCode>");
        buf.append(returnCode);
        buf.append("</RtCode>");

        buf.append("<RtMessage>");
        buf.append(msg);
        buf.append("</RtMessage>");

        buf.append("</ServiceResults>");
        return buf.toString();
    }

}
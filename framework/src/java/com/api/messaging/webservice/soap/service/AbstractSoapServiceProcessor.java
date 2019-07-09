package com.api.messaging.webservice.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.api.Product;
import com.api.ProductBuilderException;
import com.api.ProductDirector;

import com.api.messaging.MessageBinder;
import com.api.messaging.ResourceFactory;
import com.api.messaging.webservice.soap.SoapMessageHelper;
import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.client.SoapClientFactory;
import com.api.security.authentication.AuthenticationConst;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.RMT2Base;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import com.util.InvalidDataException;
import com.util.NotFoundException;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

/**
 * This class provides a template implementation of the SoapProcessor interface to minimize the work 
 * required to implement this interface for any particular SOAP based web service.  Minimally, the 
 * programmer only needs to extend this class and provide an implementation for the 
 * doExecuteRequest(Properties) method.
 *  
 * @author appdev
 * @deprecated Use AbstractsoapServiceImpl
 *
 */
public abstract class AbstractSoapServiceProcessor extends RMT2Base implements SoapProcessor {

    private static final Logger logger = Logger.getLogger(AbstractSoapServiceProcessor.class);

    protected Request request;

    protected String requestMsgId;

    protected String responseMsgId;

    protected Map<String, String> reqHeaders;

    protected SoapMessageHelper helper;

    protected DatabaseConnectionBean con;

    private List<DataHandler> inAttachments;

    protected List<Object> outAttachments;
    
    protected String currentUser;

    /**
     * Default constructor which creates an empty AbstractSoapServiceProcessor object.
     */
    public AbstractSoapServiceProcessor() {
        super();
        AbstractSoapServiceProcessor.logger.log(Level.INFO, "An empty instance of AbstractSoapServiceProcessor has been created");
        this.helper = new SoapMessageHelper();
    }

    public AbstractSoapServiceProcessor(DatabaseConnectionBean con, Request request) {
        this();
        this.request = request;
        this.con = con;
        RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
        this.currentUser = (userSession == null ? "" : userSession.getLoginId());
    }

    /**
     * Closes the XML DAO instance.
     * 
     * @throws ActionHandlerException
     */
    public void close() throws ActionHandlerException {
        if (this.con != null) {
            this.con.close();
            this.con = null;
        }
        return;
    }

    /**
     * This method is not supported for SOAP oreiented web services which 
     * no implementation is provided.
     *
     * @param request   
     *          N/A
     * @param response  
     *          N/A
     * @param command  
     *          N/A
     * @throws UnsupportedOperationException 
     *          when method is invoked
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        throw new UnsupportedOperationException();
    }

    /**
     * Accepts an XML document and uses the document to process the client's request.  The XML 
     * document is required to be in SOAP format.   This method utilizes the template pattern 
     * to process the XML message as requested by the client.  It is suggested that the 
     * implementation of this method follow the template pattern.  The template pattern logic 
     * invokes the following instance methods in the order stated: 
     * <ol>
     *   <li>{@link com.api.messaging.webservice.soap.service.http.soap.SoapProcessor#acceptMessage(String) acceptMessage(String)}</li>
     *   <li>{@link com.api.messaging.webservice.soap.service.http.soap.SoapProcessor#processMessage() processMessage()}</li>
     *   <li>{@link com.api.messaging.webservice.soap.service.http.soap.SoapProcessor#sendResponse(String) sendResponse(String)}</li>
     * </ol>
     * These methods contain base logic and can be extended at the descendent.
     *  
     * @param soap
     *          an instance of SOAPMessage
     * @throws SoapProcessorException
     */
    public SOAPMessage processRequest(SOAPMessage soap) {
        SOAPMessage response = null;
        try {
            response = this.processMessage(soap);
            return response;
        }
        catch (Exception e) {
            this.msg = "SOAP Web Service call failed for service id, " + (this.requestMsgId == null ? "[Unknown SOAP Web Service Id]" : this.requestMsgId);
            this.msg += "  Error Message: " + e.getMessage();
            logger.error(this.msg);
            response = helper.createSoapFault("Server", e.getMessage(), null, null);
        }
        return response;
    }

    /* (non-Javadoc)
     * @see com.api.messaging.http.soap.SoapProcessor#acceptMessage(java.lang.String)
     */
    public SOAPMessage acceptMessage(String xml) throws InvalidDataException, SoapProcessorException {
        SOAPMessage sm = null;
        try {
            SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance(xml);
            Product xmlProd = ProductDirector.construct(builder);
            sm = (SOAPMessage) xmlProd.toObject();
            // Test validity of SOAP message
            sm.getSOAPPart().getEnvelope();
            return sm;
        }
        catch (ProductBuilderException e) {
            throw new SoapProcessorException(e);
        }
        catch (SOAPException e) {
            this.msg = e.getMessage();
            AbstractSoapServiceProcessor.logger.log(Level.ERROR, this.msgArgs);
            throw new InvalidDataException(e);
        }
    }

    /**
     * Interprets the body content of the SOAP envelope and performs the actual execution 
     * of the client request.  Operates on the body contents of the SOAP envelope by 
     * creating a Properties mapping for each element of the SOAP body, invokes the main 
     * process that is repsonsible for carrying out the intent of the request, and packages 
     * the response into a new SOAP envelope.  If a problem occurs during the processing of 
     * the message, the service will create a SOAP fault and send it back to the client.
     * <p> 
     * This method utilizes the template pattern to execute client's request invoking the 
     * following instance methods in the order stated: 
     * <ol>
     *   <li>{@link com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor.soap.AbstractSoapService#doGetRequestData(SOAPMessage) doGetRequestData(SOAPMessage)}</li>
     *   <li>{@link com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor.soap.AbstractSoapService#doExecuteRequest(Properties) doExecuteRequest(Properties)}</li>
     *   <li>{@link com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor.soap.AbstractSoapService#doPackageResponse(SOAPMessage, String) doPackageResponse(SOAPMessage, String)}</li>
     * </ol>
     * These methods contain base logic with the exception of <i>doExecuteRequest</i>, 
     * which is abstract, and can be extended at the descendent.
     * 
     * @param soap
     *          an instance of SOAPMessage
     * @return String
     *          the response XML
     * @throws SoapProcessorException
     */
    public SOAPMessage processMessage(SOAPMessage soap) throws SoapProcessorException {
        Properties parms = this.doGetRequestData(soap);
        String response = this.doExecuteRequest(parms);
        SOAPMessage soapResp = this.doPackageResponse(soap, response);
        return soapResp;
    }

    /**
     * Extracts the XML details from the SOAP envelope's body and creates an instance 
     * of {@link com.api.xml.XmlDao XmlDao}.  The Dao is put in place as a helper so 
     * that the implementer can use it to obtain the required data items from the Body 
     * part of the SOAP message in order to process the client's request.
     * 
     * @param soap
     * @return
     * @throws SoapProcessorException
     */
    protected Properties doGetRequestData(SOAPMessage soap) throws SoapProcessorException {
        Properties parms = new Properties();
        SoapMessageHelper helper = new SoapMessageHelper();

        // Get outAttachments, if applicable
        if (soap.countAttachments() > 0) {
            this.loadAttachments(soap);
        }
        // get payload, serviceId and queueName and populate into properties collection.
        String xmlPayload = helper.getBody(soap);
        MessageBinder jaxbCtx = ResourceFactory.getJaxbMessageBinder();
        Object req = jaxbCtx.unMarshalMessage(xmlPayload);
        parms.put(SoapProductBuilder.PAYLOADINSTANCE, req);

        // Capture actual user id.   This logic is needed to comply with the fact that some 
        // areas of the framework requires a user id, which is indicative of the user 
        // possessing a session.  One case is constructing the user timestamp from RMT2Date 
        // utility class.
        //
        //  New Business Requirement:  User Id is not required at this point of authentication 
        //  since we are now authenticating using the session id as an alternative for when the 
        //  user id is not available.
        try {
            String userId = RMT2XmlUtility.getElementValue("user_id", "//header", xmlPayload);
            this.request.setAttribute(AuthenticationConst.AUTH_PROP_USERID, userId);
        }
        catch (Exception e) {
            // DO nothing...we will try to authenticate by user's session id since the user id is not available.
        }

        String serviceId = null;
        try {
            serviceId = helper.getHeaderValue(SoapProductBuilder.HEADER_NS + ":" + SoapProductBuilder.SERVICEID_NAME, soap);
            parms.setProperty(SoapProductBuilder.SERVICEID_NAME, serviceId);
        }
        catch (SOAPException e) {
            this.msg = "A problem occurred obtaining Service Id header element";
            AbstractSoapServiceProcessor.logger.log(Level.ERROR, this.msg);
            throw new SoapProcessorException(this.msg, -3003);
        }
        catch (NotFoundException e) {
            this.msg = "The value of Service Id is required and was not found as part of the SOAP header element";
            AbstractSoapServiceProcessor.logger.log(Level.ERROR, this.msg);
            throw new SoapProcessorException(this.msg, -3004);
        }
        String queueName = null;
        try {
            queueName = helper.getHeaderValue(SoapProductBuilder.HEADER_NS + ":" + SoapProductBuilder.QUEUE_NAME, soap);
            parms.setProperty(SoapProductBuilder.QUEUE_NAME, queueName);
        }
        catch (SOAPException e) {
            this.msg = "A problem occurred obtaining Queue Name header element";
            AbstractSoapServiceProcessor.logger.log(Level.ERROR, this.msg);
            throw new SoapProcessorException(this.msg, -3003);
        }
        catch (NotFoundException e) {
            // Queue Name is not required.
        }
        this.requestMsgId = serviceId;
        return parms;
    }

    /**
     * Creates a List of DataHandlers using the outAttachments found in <i>soap</i>.
     * 
     * @param soap
     *         an iinstance of SOAPMessage.
     * @return int
     *     the total number of inAttachments processed.
     */
    private int loadAttachments(SOAPMessage soap) {
        this.inAttachments = new ArrayList<DataHandler>();
        int count = 0;
        Iterator<AttachmentPart> iter = soap.getAttachments();
        while (iter.hasNext()) {
            AttachmentPart data = iter.next();
            try {
                DataHandler dataHandler = data.getDataHandler();
                count++;
                this.inAttachments.add(dataHandler);
            }
            catch (SOAPException e) {
                throw new SystemException(e);
            }
        }
        return count;
    }

    /**
     * Implementation should include logic that will fulfil the client's request based 
     * on the parmaters gathered in <i>reqParms</i>.
     * 
     * @param reqParms
     * @return
     * @throws SoapProcessorException
     */
    abstract protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException;

    /**
     * Creates a new SOAP message including <i>soapResponse</i> as the data for the SOAP Body part. 
     * 
     * @param origMsg
     *          the SOAP message that is assoicated with the inital request.
     * @param payload
     *          the results of executing the service request.
     * @return SOAPMessage
     *          The response SOAP message
     * @throws SoapProcessorException
     */
    protected SOAPMessage doPackageResponse(SOAPMessage origMsg, String payload) throws SoapProcessorException {
        // Create new SOAP message by adding the SOAP response as part of the SOAP body.
        SOAPMessage sm = null;
        try {
            SoapProductBuilder builder = SoapClientFactory.getSoapBuilderInstance(this.responseMsgId, null, payload);
            Product xmlProd = ProductDirector.construct(builder);
            builder.createAttachments(this.outAttachments);
            sm = (SOAPMessage) xmlProd.toObject();
            return sm;
        }
        catch (Exception e) {
            throw new SoapProcessorException(e);
        }
    }
}

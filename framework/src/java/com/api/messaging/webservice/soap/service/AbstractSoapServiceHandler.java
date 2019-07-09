package com.api.messaging.webservice.soap.service;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.db.DatabaseException;
import com.api.messaging.MessageException;
import com.api.messaging.webservice.soap.SoapMessageHelper;
import com.api.messaging.webservice.soap.SoapResponseException;

import com.controller.Request;
import com.controller.Response;

/**
 * This common template abstract class providing functionality for the application end point 
 * to accept the web service request, dispatch the request to the appropriate web service 
 * implementation in the scope of application, and send the web service response back to the 
 * Servlet that was called by the ServiceDispatcher application.
 * 
 * @author Roy Terrell
 * @deprecated No Longer used.
 * 
 */
public abstract class AbstractSoapServiceHandler extends AbstractActionHandler implements ICommand {
    private static Logger logger = Logger.getLogger(AbstractSoapServiceHandler.class);

    protected SOAPMessage inMsg;

    protected SOAPMessage outMsg;

    /**
     * Default constructor that is required by the calling servlet in order to create an 
     * object of this class via reflection.
     */
    public AbstractSoapServiceHandler() {
        super();

    }

    /**
     * Processes the incoming web service request using request, response, and command.
     *
     * @param request   
     *          The HttpRequest object that is stored as an instance variable.
     * @param response  
     *          The HttpResponse object that is stored as an instance variable.
     * @param command  
     *          The name of the web service which is identified as the element, <i>serviceId</i>, 
     *          in the SOAP header and as the element, <i>message_id</i>, in the header section 
     *          of the SOAP body.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionHandlerException {
        super.processRequest(request, response, command);
        this.receiveClientData();
        this.outMsg = this.doService(this.inMsg);
        logger.info("Response Message: " + this.outMsg);
        this.sendClientData();
    }

    /**
     * Retreives the SOAP message from the user's request instance.
     * 
     * @throws ActionHandlerException
     *           when the SOAP message is unable to be obtained from the user's request instance 
     *           or the SOAP message is of an invalid structure.
     */
    protected void receiveClientData() throws ActionHandlerException {
        try {
            SoapMessageHelper helper = new SoapMessageHelper();
            this.inMsg = helper.getSoapInstance(this.request);
            // Test if SOAP message is of valid structure
            this.inMsg.getSOAPBody();
        }
        catch (Exception e) {
            throw new ActionHandlerException(e);
        }
    }

    /**
     * Implement this method to provide the actual invocation of the web service that will serve 
     * the request.  If the implementing class is required to manage multiple web services based 
     * on the incoming request, code this method to conditionally invoke the web service needed 
     * based on the instance property, <i>command</i>.   This method is required to return the
     * web service's response as a String.
     *  
     *  @param soapMsg
     *          the request SOAP message.
     * @return String
     *          the web service's response.
     * @throws ActionHandlerException
     */
    protected abstract SOAPMessage doService(SOAPMessage soapMsg) throws ActionHandlerException;

    /**
     * Sends the response of the target web service back to the calling servlet via the 
     * {@link com.controller.Response Response} instance.
     */
    protected void sendClientData() throws ActionHandlerException {
        SoapMessageHelper helper = new SoapMessageHelper();
        try {
            helper.sendSoapInstance(this.response, this.outMsg);
        }
        catch (SoapResponseException e) {
            throw new ActionHandlerException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#add()
     */
    public void add() throws ActionHandlerException {
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#delete()
     */
    public void delete() throws ActionHandlerException, DatabaseException {
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#edit()
     */
    public void edit() throws ActionHandlerException {
        return;
    }

    /* (non-Javadoc)
     * @see com.action.ICommonAction#save()
     */
    public void save() throws ActionHandlerException, DatabaseException {
        return;
    }
}
package com.remoteservices;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.Properties;

import com.action.ActionHandlerException;
import com.constants.GeneralConst;

/**
 * Provides functionality to invoke a remote service producer using a service 
 * id and Properties hash.  The Properties hash instance's key/value pairs represent 
 * the URL paramters for the service call.  RemoteServicesConsumer is capable of 
 * only communicating with Http URL's.  Upon successful completion of the service 
 * call, the results of the service call can be obtained via a call to the 
 * getServiceResults() method.  
 * <p>
 * An example of invoking a remote service named, "userprofile", using a descendent 
 * of AbstractClientAction (UsersConsumer):
 * <blockquote>
 * Properties props = new Properties();<br>
 *  // add values to props here...<br>
 * UsersConsumer service = new UsersConsumer("userprofile", props);<br>
 * service.processRequest();<br><br>
 * // Get results of service call <br>
 * Object data = service.getServiceResults();<br>
 * </blockquote>
 * 
 * @author Roy Terrell
 * @deprecated Will be removed in future releases.   Use package, com.api.messaging.http.
 * 
 */
public class RemoteServicesConsumer extends AbstractClientAction implements ServiceConsumer {
    private Properties prop;

    private Logger logger;

    /**
     * Creates an instance of RemoteServicesConsumer and initializes the logger.
     * 
     */
    public RemoteServicesConsumer() {
        super();
        this.logger = Logger.getLogger("RemoteServicesConsumer");
        this.logger.log(Level.INFO, "RemoteServicesConsumer created.");
    }

    /**
     * Creates an instance of RemoteServicesConsumer by identifying the name of the service 
     * to invoke and its URL parameter name/values. 
     * 
     * @param command
     *          The name of the service to invoke
     * @param urlArgs
     *          A Properties instance containing the URL parameters for <i>command</i> 
     *          as key/value pairs.
     * @throws ActionHandlerException
     */
    public RemoteServicesConsumer(String command, Properties urlArgs) throws ActionHandlerException {
        this();
        if (command == null) {
            this.msg = "Service Id failed to be identified";
            logger.log(Level.ERROR, this.msg);
            throw new ActionHandlerException(this.msg);
        }
        this.command = command;
        this.prop = urlArgs;
    }

    /* (non-Javadoc)
     * @see com.action.AbstractActionHandler#close()
     */
    @Override
    public void close() {
        this.serviceResults = null;
        this.logger = null;
        super.close();
    }

    /**
     * Drives the process of consuming a remote service.  This method requires that 
     * the command and URL parameter values are setup before invocation.  The 
     * user is required to implement the receiveClientData and sendClientData methods.
     * 
     * @throws RuntimeException
     */
    public void processRequest() throws RuntimeException {
        try {
            super.processRequest();
        }
        catch (ActionHandlerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Assigns the service data as a Properties collection which its key/values 
     * pairs represent the actual URL parameter list for the remote service.
     * 
     * @throws ActionHandlerException
     */
    public void receiveClientData() throws ActionHandlerException {
        if (this.command != null) {
            this.prop.setProperty(GeneralConst.REQ_SERVICEID, this.getServiceId());
        }
        this.setServiceData(this.prop);
    }

    /**
     * Method is stubbed to satisfy abstract class implementation.
     * 
     * @throws ServiceHandlerException
     */
    public void sendClientData() throws ActionHandlerException {
        return;
    }
}

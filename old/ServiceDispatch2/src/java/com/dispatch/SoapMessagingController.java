package com.dispatch;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.api.messaging.webservice.ServiceRegistryManager;
import com.api.messaging.webservice.ServiceRoutingInfo;

import com.api.messaging.webservice.soap.SoapMessageHelper;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.engine.AbstractSoapEngine;
import com.api.messaging.webservice.soap.engine.SoapMessageRouterException;
import com.util.InvalidDataException;
import com.util.NotFoundException;

/**
 * This AbstractSoapEngine implementation functions to validate and obtain the web service routing information for a SOAP message 
 * by using a Map collection to serve as the registry of SOAP web service metadata.   The key/value pairs of the Map are expected 
 * to be of type String and {@link com.api.messaging.webservice.ServiceRoutingInfo ServiceRoutingInfo}, respectively.  
 * 
 * @author appdev
 *
 */
public class SoapMessagingController extends AbstractSoapEngine {

    private static final long serialVersionUID = 5433010431085175539L;

    private static Logger logger = Logger.getLogger(SoapMessagingController.class);

    //       private static final String SERVICES_URL = "authentication/unsecureRequestProcessor/Services?clientAction=";

    private static Map<String, ServiceRoutingInfo> SERVICES;

    /**
     * 
     */
    public SoapMessagingController() {
        return;
    }

    /**
     * Drives the initialization process when the SoapMessagingController is first loaded.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("Logger initialized");
    }

    /**
     * Loads the web services registry that is used to lookup all SOAP related web services and proceeds to processs 
     * the SOAP message at the ancestor.
     */
    public void initServlet() throws ServletException {
        super.initServlet();
        try {
            // Verifiy if service registry is loaded with SOAP based web services.   If not, then load
            if (SoapMessagingController.SERVICES == null) {
                ServiceRegistryManager regMgr = new ServiceRegistryManager();
                SoapMessagingController.SERVICES = regMgr.loadServices(ServiceRegistryManager.SERV_RESOURCE_SUBTYPE_SOAP);
                this.setDataSource(SoapMessagingController.SERVICES);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.fatal("Failed to load web services registry", e);
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
     *                 a Map collection of SOAP web service routing data serving as the data source used to perform the web service routing 
     *                 informatin lookup.   The Map key should be of type String representing the service id, and the Map value is required to 
     *                 be of type ServiceRoutingInfo which represents the actual routing data.    
     * @return an instance of {@link com.api.messaging.webservice.ServiceRoutingInfo ServiceRoutingInfo}
     * @throws SoapMessageRouterException
     *                  when the routing information is unobtainable due to key not found or the input of invalid data to the routing information 
     *                  search process.
     */
    @Override
    protected ServiceRoutingInfo getMessageRoutingInfo(SOAPMessage sm, Object dataSource) throws SoapMessageRouterException {
        if (dataSource == null || !(dataSource instanceof Map<?, ?>)) {
            return null;
        }
        try {
            ServiceRoutingInfo srvc = this.getRoutingInfoForSoapMessage(sm, (Map<String, ServiceRoutingInfo>) dataSource);
            return srvc;
        }
        catch (Exception e) {
            throw new SoapMessageRouterException(e);
        }
    }

    /**
     * Returns the routing data for a specified SOAP message.    The SOAP message instance, <i>soapObj</i>, must contain an XML 
     * element in the SOAP header named, <i>serviceId</i>, which is the id of the web service used to look up the routing information 
     * in the web service registry for the specified web service.     
     * <p>
     * Before looking up the routing information, this method verifies that the SOAP message instance contains all the necessary data 
     * and components needed to satisfy a web service call in the RMT2 engineered environment.  The message must meet the following
     * criteria before it is considered to be valid:
     * <ol>
     *   <li>The SOAP-ENV:Header must contain a service id element which its name, "serviceId".</li>
     *   <li>The service id name must be fully qualified by the namespace, "rmt2_hns".  For example, "rmt2-hns:serviceId".</li>
     *   <li>The value of serviceId must be registered in the authentication application.</li>
     * </ol> 
     * 
     * @param soapXml
     *          the SOAP message that is to be validated.
     * @return {@link com.api.messaging.jms.ServiceRoutingInfo ServiceFields}
     *          the details of the registered service
     * @throws NotFoundException
     *          Requested service does not exist or is invalid or a naming conflict exist between the 
     *          service id and the data obtained from the service registry. 
     * @throws InvalidDataException
     *           <i>soapObj</i> and/or <i>services</i> is null, service id cannot be obtained from 
     *           the SOAP message, or the service's URL is invalid or is not assoicated with the service 
     *           id in the registry record.
     */
    private ServiceRoutingInfo getRoutingInfoForSoapMessage(SOAPMessage soapObj, Map<String, ServiceRoutingInfo> services) throws NotFoundException, InvalidDataException {
        String msg;
        String serviceId = null;
        SoapMessageHelper helper = new SoapMessageHelper();

        if (soapObj == null) {
            msg = "SOAP message could not be validated due to null SOAP message instance";
            logger.error(msg);
            throw new InvalidDataException(msg);
        }
        if (services == null || services.isEmpty()) {
            msg = "SOAP message could not be validated due to internal web services collection is invalid or empty";
            logger.error(msg);
            throw new InvalidDataException(msg);
        }
        try {
            String qualifiedName = SoapProductBuilder.HEADER_NS + ":" + SoapProductBuilder.SERVICEID_NAME;
            serviceId = helper.getHeaderValue(qualifiedName, soapObj);
        }
        catch (SOAPException e) {
            throw new InvalidDataException(e);
        }
        catch (NotFoundException e) {
            // Do Nothing.   Let following logic discover this state and report the error.
        }

        // Validate the existence of Service Id
        if (serviceId == null) {
            msg = "SOAP web service request is required to have declared a service id element";
            logger.log(Level.ERROR, msg);
            throw new InvalidDataException(msg);
        }
        ServiceRoutingInfo srvc = (ServiceRoutingInfo) services.get(serviceId);
        if (srvc == null) {
            msg = "Requested SOAP web service does not exist or is invalid: " + serviceId;
            logger.log(Level.ERROR, msg);
            throw new NotFoundException(msg);
        }
        if (!serviceId.equalsIgnoreCase(srvc.getName())) {
            msg = "A naming conflict exist between the SOAP web service id input [" + serviceId + "] and the SOAP registry service data [" + srvc.getName() + "]";
            logger.log(Level.ERROR, msg);
            throw new NotFoundException(msg);
        }
        if (srvc.getUrl() == null) {
            msg = "The URL property of the requested SOAP web service, " + serviceId + ", does not exist or is invalid: ";
            logger.log(Level.ERROR, msg);
            throw new InvalidDataException(msg);
        }
        return srvc;
    }

}

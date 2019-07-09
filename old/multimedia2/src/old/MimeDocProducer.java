package com.services.document;

//import java.io.InputStream;
import java.util.Properties;

//import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
//import org.w3c.dom.Node;

//import com.action.ActionHandlerException;

//import com.api.Product;
//import com.api.ProductDirector;
//import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;
import com.api.db.MimeContentApi;
import com.api.db.MimeFactory;
import com.api.messaging.webservice.soap.AbstractSoapService;
import com.api.messaging.webservice.soap.SoapClientHelper;
import com.api.messaging.webservice.soap.SoapProcessorException;
import com.api.messaging.webservice.soap.SoapProductBuilder;
//import com.api.messaging.webservice.soap.SoapResourceFactory;
//import com.api.xml.XmlApiFactory;
//import com.api.xml.XmlDao;

import com.bean.Content;

import com.bean.db.DatabaseConnectionBean;

//import com.controller.Context;
//import com.controller.Request;

//import com.remoteservices.http.AbstractExternalServerAction;

//import com.util.RMT2File;
//import com.util.SystemException;

/**
 * This class provides action handlers needed to serve the request of the Customer Sales Order Console user interface.
 * 
 * @author Roy Terrell
 * @deprecated Use MimeDocFetchProducer
 *
 */
public class MimeDocProducer extends AbstractSoapService {

    private static Logger logger = Logger.getLogger(MimeDocAction.class);
    
    private static String SRV_FETCH = "Services.FETCH_DOCUMENT";

    
    
    /**
     * Default constructor
     *
     */
    public MimeDocProducer() {
	super();

    }

   
    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
	String serviceId = reqParms.getProperty(SoapProductBuilder.SERVICEID_NAME);
	String xml = null;

	if (serviceId.equalsIgnoreCase(MimeDocProducer.SRV_FETCH)) {
	    int contentId = 0;
	    try {
		contentId = Integer.parseInt(reqParms.getProperty("contentId"));
	    }
	    catch (NumberFormatException e) {
		contentId = -1;
	    }
	    xml = this.doFetch(contentId);
	}
	return xml;
    }
    
    
    /**
     * Navigate back to the customer search page.
     *  
     * @throws ActionHandlerException.
     */
    protected String doFetch(int contentId) throws SoapProcessorException {
	DatabaseTransApi tx = DatabaseTransFactory.create();
	DatabaseConnectionBean con = (DatabaseConnectionBean) tx.getConnector();
	MimeContentApi api = MimeFactory.getMimeContentApiInstance("com.api.db.DefaultSybASABinaryImpl");
	api.initApi(con);

	//Send content to Browser
	try {
	    Content content = (Content) api.fetchContent(contentId);
	    String soapXml = content.toXml();
	    SoapClientHelper soapHelper = new SoapClientHelper();
	    soapXml = soapHelper.createResponse("FETCH_DOCUMENT", soapXml);
	    return soapXml;
	}
	catch (Exception e) {
	    logger.log(Level.ERROR, e.getMessage());
	    throw new SoapProcessorException(e);
	}
    }
    
//    /**
//     * Retreives the customer data from the client's request.
//     */
//    public void receiveClientData() throws ActionHandlerException {
//	String xml = null;
//	int contentId = 0;
//	String serviceId = null;
//	Properties parms = new Properties();
//	
//	try {
//	    InputStream is = this.request.getInputStream();
//	    xml = RMT2File.getStreamStringData(is);
//
//	    SoapProductBuilder builder = SoapResourceFactory.getSoapBuilderInstance(xml);
//	    Product xmlProd = ProductDirector.construct(builder);
//	    SOAPMessage sm = (SOAPMessage) xmlProd.toObject();
//
//	    Node root = sm.getSOAPBody().getFirstChild();
//	    String temp = null;
//	    XmlDao xmlDao = XmlApiFactory.createXmlDao(root);
//	    xmlDao.retrieve("FETCH_DOCUMENT_UserRequest");
//	    while (xmlDao.nextRow()) {
//		temp = xmlDao.getColumnValue("contentId");
//	    }
//	    contentId = Integer.parseInt(temp);
//	    parms.setProperty("contentId", String.valueOf(contentId));
//	    parms.setProperty("serviceId", serviceId);
//	    this.inData = parms;
//	}
//	catch (Exception e) {
//	    throw new ActionHandlerException(e);
//	}
//    }
//    
//    /**
//     * Processes the user's request to retrieve business contact data filtered by 
//     * business id, business name, tax id, business type id, or service type id.
//     * 
//     * @throws ActionHandlerException
//     */
//    public void processData() throws ActionHandlerException {
//	Properties prop = (Properties) this.inData;
//	String serviceId = prop.getProperty("serviceId");
//
//	if (serviceId.equalsIgnoreCase(MimeDocProducer.SRV_FETCH)) {
//	    int contentId = Integer.parseInt(prop.getProperty("contentId"));
//	    this.xml = this.doFetch(contentId);
//	}
//    }
    
 
    
    

//    /**
//     * Sends the response of the client's request with an ArrayList of  CombinedSalesOrder objects and a 
//     * CustomerWithName object which their attriute names are required to be set as "orderhist" and 
//     * "customer", respectively.
//     */
//    public void sendClientData() throws ActionHandlerException {
//	this.outData = this.xml;
//	return;
//    }



    
    
    
    
    
//    /* (non-Javadoc)
//     * @see com.action.ICommonAction#add()
//     */
//    public void add() throws ActionHandlerException {
//	// TODO Auto-generated method stub
//
//    }
//
//    /* (non-Javadoc)
//     * @see com.action.ICommonAction#delete()
//     */
//    public void delete() throws ActionHandlerException, DatabaseException {
//	// TODO Auto-generated method stub
//
//    }
//
//    /* (non-Javadoc)
//     * @see com.action.ICommonAction#edit()
//     */
//    public void edit() throws ActionHandlerException {
//	// TODO Auto-generated method stub
//
//    }
//
//    /* (non-Javadoc)
//     * @see com.action.ICommonAction#save()
//     */
//    public void save() throws ActionHandlerException, DatabaseException {
//	// TODO Auto-generated method stub
//
//    }

  

}
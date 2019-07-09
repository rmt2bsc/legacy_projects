package com.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.action.ActionHandlerException;

import com.api.DaoApi;

import com.api.db.DatabaseException;
import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.ServiceReturnCode;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.api.xml.XmlApiFactory;

import com.bean.Customer;
import com.bean.SalesOrder;
import com.bean.SalesOrderExt;
import com.bean.SalesOrderItems;
import com.bean.Xact;

import com.bean.bindings.JaxbAccountingFactory;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.gl.customer.CustomerFactory;

import com.util.NotFoundException;
import com.util.SystemException;

import com.xact.XactFactory;

import com.xact.sales.SalesFactory;
import com.xact.sales.SalesOrderApi;
import com.xact.sales.SalesOrderException;

import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQAccountingInvoiceSalesOrder;
import com.xml.schema.bindings.RSAccountingInvoiceSalesOrder;
import com.xml.schema.bindings.SalesOrderInvoiceResultType;
import com.xml.schema.bindings.SalesOrderType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of invoicing a
 * single sales order.  The incoming and outgoing data is expected to be in the 
 * form of SOAP XML. 
 * 
 * @author Roy Terrell
 * @deprecated User com.services.handler.SalesInvoiceServiceHandler
 *
 */
public class SalesInvoiceService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("SalesInvoiceService");
    
    private RQAccountingInvoiceSalesOrder reqMessage;

    /**
     * Default constructor.
     *  
     * @throws SystemException
     */
    public SalesInvoiceService() throws SystemException {
	super();
	SalesInvoiceService.logger.info("Logger Created");
    }
    
    /**
     * 
     * @param con
     * @param request
     */
    public SalesInvoiceService(DatabaseConnectionBean con, Request request) {
        super(con, request);
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
        String xml = null;
        this.reqMessage = (RQAccountingInvoiceSalesOrder) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);
        xml = this.invoiceSalesOrder(this.reqMessage.getSalesOrder());
        return xml;
    }
    
  
    protected String invoiceSalesOrder(SalesOrderType sot) throws SoapProcessorException {
        try {
            SalesOrderExt soExt = JaxbAccountingFactory.getDtoSalesOrderInstance(sot);
            int newSoId = this.createSalesOrder(soExt);
            soExt.setSalesOrderId(newSoId);
            ServiceReturnCode invRc = this.invoiceSalesOrder(soExt);
            return this.buildResponsePayload(soExt, invRc);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new SoapProcessorException(e);
        }
    }
    
    
    /**
     * Retrieves all customer/business contacts. 
     * 
     * @param props 
     *           The input arguments needed to make the service call.
     * @return String 
     *           XML document representing the service's return message.  The return 
     *           code of the message will represent the invoice number that was assigned 
     *           to the sales order.
     * @throws ActionHandlerException  Genereal errors
     */
    protected ServiceReturnCode invoiceSalesOrder(SalesOrderExt soExt) {
        SalesOrder so = this.getSalesOrderInstance(soExt);
        Xact xact = this.getXactInstance(soExt);

        DatabaseTransApi tx = DatabaseTransFactory.create();
        SalesOrderApi api = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            so = (SalesOrder) api.findSalesOrderById(so.getSoId());
            int invId = api.invoiceSalesOrder(so, xact);
            String xactXml = xact.toXml();
            String rcMsg = this.buildReturnCode(invId, "Sales order was invoiced successfully", xactXml);
            ServiceReturnCode rc = this.buildReturnCodeInstance(rcMsg);
            tx.commitUOW();
            return rc;
        }
        catch (SalesOrderException e) {
            tx.rollbackUOW();
            String errMsg = this.buildReturnCode(-103, e.getMessage(), null);
            ServiceReturnCode rc = this.buildReturnCodeInstance(errMsg);
            return rc;
        }
        finally {
            api.close();
            api = null;
        }
    }
    
    /**
     * Creates a sales order using data from a remote client application. 
     * 
     * @param props 
     *          A collection of properties which should contain values for the following keys: 
     *          AccountNo, CustomerId, CustomerIdList, Name, TaxId, and PhoneMain.
     * @return String 
     *          XML document
     */
    protected int createSalesOrder(SalesOrderExt soExt) throws SalesOrderException {
        SalesOrder so = this.getSalesOrderInstance(soExt);
        Customer cust = this.getCustomerInstance(soExt);
        List<SalesOrderItems> items = this.getSalesOrderItemsInstance(soExt);

        DatabaseTransApi tx = DatabaseTransFactory.create();
        SalesOrderApi api = SalesFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            int soId = api.maintainSalesOrder(so, cust, items);
            tx.commitUOW();
            String rcMsg = this.buildReturnCode(soId, "Sales order was created successfully", null);
            ServiceReturnCode rc = this.buildReturnCodeInstance(rcMsg);
            return rc.getCode();
        }
        catch (SalesOrderException e) {
            throw e;
        }
        finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
    }
    

//  protected String invoiceNewSalesOrder(SalesOrderExt soExt) {
//  try {
//      String results = this.createSalesOrder(soExt);
//      ServiceReturnCode rc = this.getResults(results);
//      soExt.setSalesOrderId(rc.getCode());
//      String revisedXml = soExt.toXml();
//      props.setProperty(AbstractExternalServerAction.ARG_PAYLOAD, revisedXml);
//      results = this.invoiceSalesOrder(props);
//      return results;
//  }
//  catch (Exception e) {
//      return this.buildReturnCode(-106, e.getMessage(), null);
//  }
//}


    

    
    
//    /**
//     * Processes the user's request to retrieve business contact data filtered by 
//     * business id, business name, tax id, business type id, or service type id.
//     * 
//     * @throws ActionHandlerException
//     */
//    public void processData() throws ActionHandlerException {
//	String xml = null;
//
//	Properties prop = (Properties) this.inData;
//
//	if (this.command.equalsIgnoreCase(SalesInvoiceService.SRVC_CREATE)) {
//	    xml = this.createSalesOrder(prop);
//	}
//	if (this.command.equalsIgnoreCase(SalesInvoiceService.SRVC_INVOICE)) {
//	    xml = this.invoiceSalesOrder(prop);
//	}
//	if (this.command.equalsIgnoreCase(SalesInvoiceService.SRVC_INVOICENEWSALESORDER)) {
//	    xml = this.invoiceNewSalesOrder(prop);
//	}
//
//	this.outData = xml;
//    }

   

    private SalesOrder getSalesOrderInstance(SalesOrderExt data) {
	SalesOrder so = SalesFactory.createSalesOrder();
	so.setSoId(data.getSalesOrderId());
	so.setCustomerId(data.getCustomerId());
	so.setInvoiced(data.getInvoiced());
	so.setOrderTotal(data.getOrderTotal());
	return so;
    }

    private Customer getCustomerInstance(SalesOrderExt data) {
	Customer cust = CustomerFactory.createCustomer();
	cust.setCustomerId(data.getCustomerId());
	return cust;
    }

    private List <SalesOrderItems> getSalesOrderItemsInstance(SalesOrderExt data) {
	List <SalesOrderItems> items = new ArrayList <SalesOrderItems> ();
	items.addAll(data.getItems());
	return items;
    }

    private Xact getXactInstance(SalesOrderExt data) {
	Xact xact = XactFactory.createXact();
	xact.setReason(data.getReason());
	return xact;
    }
    
    /**
     * Builds a simple XML document that is based on the layout of a ServiceReturnCode 
     * class from a XML messagethat details the results of service call. The message 
     * is comprised of a return code, outcome message text, and resulting dataset, if 
     * applicable.
     * <pre>
     *  &lt;ServiceReturnCode&gt;
     *     &lt;code&gt;1&lt;/code&gt;
     *     &lt;message&gt;An error occured&lt;/message&gt;
     *     &lt;data&gt;(XML document)&lt;/data&gt;
     *  &lt;/ServiceReturnCode&gt;
     * </pre> 
     * 
     * @param code 
     *          The return code of service which is service specific.
     * @param message 
     *          The actual message related to the return code.
     * @param data
     *          XML document returned from the service which can serve 
     *          as data results from the service call.          
     * @return String 
     *          Xml document.&nbsp;&nbsp; as described in the above method description.
     */
    private String buildReturnCode(int code, String message, String data) {
        StringBuffer xml = new StringBuffer(100);
        xml.append("<ServiceReturnCode>");
        xml.append("<code>");
        xml.append(code);
        xml.append("</code>");
        xml.append("<message>");
        xml.append(message);
        xml.append("</message>");
        if (data != null && data.length() > 0) {
            xml.append("<data>");
            xml.append(data);
            xml.append("</data>");
        }
        xml.append("</ServiceReturnCode>");
        return xml.toString();
    }
    
    /**
     * Builds a ServiceReturnCode message instance from a XML message.  The XML message is 
     * required to be in the following format:
     *  <pre>
     *  &lt;ServiceReturnCode&gt;
     *     &lt;code&gt;1&lt;/code&gt;
     *     &lt;message&gt;An error occured&lt;/message&gt;
     *     &lt;data&gt;(XML document)&lt;/data&gt;
     *  &lt;/ServiceReturnCode&gt;
     * </pre> 
     * 
     * @param msg 
     *          XML message containing the return code and message text.
     * @return {@link com.remoteservices.http.}
     * @throws DatabaseException 
     *          Message property cannot be found from within <i>msg</i> or general data access error. 
     * @throws SystemException 
     *          Problem creating the ServiceReturnCode object.
     */
    public ServiceReturnCode buildReturnCodeInstance(String msg) throws DatabaseException, SystemException {
        // Process XML result set.
        DaoApi dao = XmlApiFactory.createXmlDao(msg);
        try {
            dao.retrieve("//ServiceReturnCode");
            String code = null;
            String message = null;
//            String data = null;
            ServiceReturnCode rc = new ServiceReturnCode();
            if (dao.nextRow()) {
                code = dao.getColumnValue("code");
                message = dao.getColumnValue("message");
                rc.setCode(Integer.parseInt(code));
                rc.setMessage(message);
                try {
//                    data = dao.getColumnValue("data");
                    // Assigne the entire XML document to the "data" property.
                    rc.setData(msg);
                }
                catch (NotFoundException e) {
                    // Soft error.  Okay
                }
                
            }
            return rc;
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
        finally {
            dao.close();
            dao = null;
        }
    }

    
  /**
  * 
  * @param results
  * @return
  */
 private String buildResponsePayload(SalesOrderExt so, ServiceReturnCode invResults) {
     this.responseMsgId = "RS_accounting_invoice_sales_order";
     ObjectFactory f = new ObjectFactory();
     RSAccountingInvoiceSalesOrder ws = f.createRSAccountingInvoiceSalesOrder();

     HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
     ws.setHeader(header);
     JaxbAccountingFactory jf = new JaxbAccountingFactory();
     SalesOrderInvoiceResultType soirt = jf.createSalesOrderInvoiceResultType(so, invResults);
     ws.setInvoiceResult(soirt);
     String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
     return rawXml;
 }
}

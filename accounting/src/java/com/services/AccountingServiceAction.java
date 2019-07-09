package com.services;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.action.ActionHandlerException;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceHandler;

import com.bean.db.DatabaseConnectionBean;

/**
 * A web service receiver designed to dispatch accounting related requests to the 
 * appropriate accounting web service implementation.
 * 
 * @author Roy Terrell
 * @deprecated User AccountingSoapService
 *
 */
public class AccountingServiceAction extends AbstractSoapServiceHandler {
    private static final String COMMAND_FETCH = "Services.RQ_customer_search";
    
    private static final String COMMAND_INVOICE_SALESORDER = "Services.RQ_accounting_invoice_sales_order";
    
    private static Logger logger = Logger.getLogger("AccountingServiceAction");

    /**
     * Default constructor
     *
     */
    public AccountingServiceAction() {
	super();
    }

    /**
     * Invokes the process designed to serve the <i>RQ_customer_search</i> request.
     *  
     *  @param inMsg
     *          the request message.
     * @return String
     *          the web service's response.
     * @throws ActionHandlerException
     */
    @Override
    protected SOAPMessage doService(SOAPMessage inMsg) throws ActionHandlerException {
	SOAPMessage resp = null;
	if (AccountingServiceAction.COMMAND_FETCH.equalsIgnoreCase(this.command)) {
	    resp = this.fetchCustomer(inMsg);
	}

	if (AccountingServiceAction.COMMAND_INVOICE_SALESORDER.equalsIgnoreCase(this.command)) {
            resp = this.invoiceSalesOrder(inMsg);
        }
	
	return resp;
    }

    private SOAPMessage fetchCustomer(SOAPMessage inMsg) throws ActionHandlerException {
	AccountingServiceAction.logger.info("Prepare to call Customer Fetch Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CustomerFetchService srvc = new CustomerFetchService((DatabaseConnectionBean) tx.getConnector(), this.request);
	try {
	    SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
	    tx.commitUOW();
	    return outSoapMsg;
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new ActionHandlerException(e);
	}
	finally {
	    srvc.close();
	    tx.close();
	    tx = null;
	}
    }

    
    private SOAPMessage invoiceSalesOrder(SOAPMessage inMsg) throws ActionHandlerException {
        AccountingServiceAction.logger.info("Prepare to call service to invoice sales order");
        DatabaseTransApi tx = DatabaseTransFactory.create();
        SalesInvoiceService srvc = new SalesInvoiceService((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            SOAPMessage outSoapMsg = srvc.processRequest(inMsg);
            tx.commitUOW();
            return outSoapMsg;
        }
        catch (Exception e) {
            tx.rollbackUOW();
            throw new ActionHandlerException(e);
        }
        finally {
            srvc.close();
            tx.close();
            tx = null;
        }
    }
  
}
package com.services.soap;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.webservice.soap.service.AbstractSoapServiceImpl;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.db.DatabaseConnectionBean;

import com.services.handlers.CustomerFetchServiceHandler;
import com.services.handlers.SalesInvoiceServiceHandler;

/**
 * A web service receiver designed to dispatch accounting related requests to the 
 * appropriate accounting web service implementation.
 * 
 * @author Roy Terrell
 *
 */
public class AccountingSoapService extends AbstractSoapServiceImpl {
    private static final String COMMAND_FETCH = "Services.RQ_customer_search";
    
    private static final String COMMAND_INVOICE_SALESORDER = "Services.RQ_accounting_invoice_sales_order";
    
    private static Logger logger = Logger.getLogger("AccountingSoapService");

    /**
     * Default constructor
     *
     */
    public AccountingSoapService() {
	super();
    }
    
    
    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor#invokeSoapHandler(java.util.Properties)
     */
    @Override
    protected String invokeSoapHandler(Properties reqParms) throws SoapProcessorException {
	String resp = null;
	if (AccountingSoapService.COMMAND_FETCH.equalsIgnoreCase(this.command)) {
	    resp = this.fetchCustomer(reqParms);
	}

	if (AccountingSoapService.COMMAND_INVOICE_SALESORDER.equalsIgnoreCase(this.command)) {
            resp = this.invoiceSalesOrder(reqParms);
        }
	
	return resp;
    }
    
    

   
    private String fetchCustomer(Properties reqParms) throws SoapProcessorException {
	AccountingSoapService.logger.info("Prepare to call Customer Fetch Service");
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CustomerFetchServiceHandler srvc = new CustomerFetchServiceHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
	this.responseMsgId = "RS_customer_search";
	srvc.setResponseServiceId(this.responseMsgId);
	try {
	    Object results = srvc.execute(reqParms);
	    tx.commitUOW();
	    return results.toString();
	}
	catch (Exception e) {
	    tx.rollbackUOW();
	    throw new SoapProcessorException(e);
	}
	finally {
	    srvc = null;
	    tx.close();
	    tx = null;
	}
    }

    
    private String invoiceSalesOrder(Properties reqParms) throws SoapProcessorException {
        AccountingSoapService.logger.info("Prepare to call service to invoice sales order");
        DatabaseTransApi tx = DatabaseTransFactory.create();
        SalesInvoiceServiceHandler srvc = new SalesInvoiceServiceHandler((DatabaseConnectionBean) tx.getConnector(), this.request);
        this.responseMsgId = "RS_accounting_invoice_sales_order";
        srvc.setResponseServiceId(this.responseMsgId);
        try {
            Object results = srvc.execute(reqParms);
            tx.commitUOW();
            return results.toString();
        }
        catch (Exception e) {
            tx.rollbackUOW();
            throw new SoapProcessorException(e);
        }
        finally {
            srvc = null;
            tx.close();
            tx = null;
        }
    }

}
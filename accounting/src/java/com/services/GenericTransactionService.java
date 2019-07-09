package com.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import com.api.messaging.ResourceFactory;

import com.api.messaging.webservice.soap.client.SoapProductBuilder;
import com.api.messaging.webservice.soap.service.AbstractSoapServiceProcessor;
import com.api.messaging.webservice.soap.service.SoapProcessorException;

import com.bean.VwGenericXactList;
import com.bean.XactType;

import com.bean.bindings.JaxbAccountingFactory;
import com.bean.criteria.GenericXactCriteria;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

import com.xml.schema.bindings.GenericXactCriteriaType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQGenreicTransactionSearch;
import com.xml.schema.bindings.RSGenreicTransactionSearch;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.XacttypeType;

import com.xml.schema.misc.PayloadFactory;

/**
 * Back end web service implementation that serves the request of invoicing a
 * single sales order.  The incoming and outgoing data is expected to be in the 
 * form of SOAP XML. 
 * 
 * @author Roy Terrell
 * @deprecated use com.services.handlers.GenericTransactionServicehandler
 *
 */
public class GenericTransactionService extends AbstractSoapServiceProcessor {

    private static Logger logger = Logger.getLogger("SalesInvoiceService");
    
    private RQGenreicTransactionSearch reqMessage;

    /**
     * Default constructor.
     *  
     * @throws SystemException
     */
    public GenericTransactionService() throws SystemException {
	super();
	GenericTransactionService.logger.info("Logger Created");
    }
    
    /**
     * 
     * @param con
     * @param request
     */
    public GenericTransactionService(DatabaseConnectionBean con, Request request) {
        super(con, request);
    }

    /* (non-Javadoc)
     * @see com.api.messaging.webservice.soap.AbstractSoapService#doExecuteRequest(java.util.Properties)
     */
    @Override
    protected String doExecuteRequest(Properties reqParms) throws SoapProcessorException {
        String xml = null;
        this.reqMessage = (RQGenreicTransactionSearch) reqParms.get(SoapProductBuilder.PAYLOADINSTANCE);
        xml = this.fetchTransactions(this.reqMessage.getXactCriteria());
        return xml;
    }
    
  
    protected String fetchTransactions(GenericXactCriteriaType criteria) throws SoapProcessorException {
        try {
            // TODO: call method that actually fetches the data
            return this.buildResponsePayload(null, null, null, null, null, null, 0, null, null);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new SoapProcessorException(e);
        }
    }
    
    
   
    
  /**
  * 
  * @param results
  * @return
  */
 public String buildResponsePayload(String pageTitle, String appRoot, GenericXactCriteria criteria, List<XactType> xactTypes, List<XactType> xactSubTypes, List<VwGenericXactList> xactList, double xactTotal, String msg, String userId) {
     ObjectFactory f = new ObjectFactory();
     RSGenreicTransactionSearch ws = f.createRSGenreicTransactionSearch();
    
     this.responseMsgId = "RS_genreic_transaction_search";

     // Request message instance will be null if process is called within the application context.
     if (this.reqMessage == null) {
	 this.reqMessage = f.createRQGenreicTransactionSearch();
	 this.reqMessage.setHeader(f.createHeaderType());
	 this.reqMessage.getHeader().setDeliveryMode("SYNC");
	 this.reqMessage.getHeader().setUserId(userId);
     }
     
     HeaderType header = PayloadFactory.createHeader(this.responseMsgId, this.reqMessage.getHeader().getDeliveryMode(), "RESPONSE", this.reqMessage.getHeader().getUserId());
     ws.setHeader(header);
     
     int recCount = (xactList == null ? 0 : xactList.size());
     ReplyStatusType rst = PayloadFactory.createReplyStatus(true, "Invocation was successful", msg, recCount);
     ws.setReplyStatus(rst);
     ws.setTransactionCount(BigInteger.valueOf(recCount));
     ws.setTransactionTotal(BigDecimal.valueOf(xactTotal));
     ws.setPageTitle(pageTitle);
     ws.setAppRoot(appRoot);
     
     JaxbAccountingFactory af = new JaxbAccountingFactory();
     GenericXactCriteriaType gxct = af.createGenreicXactCriteriaInstance(criteria);
     List <XacttypeType> xt = af.createXacttypeTypeListInstance(xactTypes);
     List <XacttypeType> xst = af.createXacttypeTypeListInstance(xactSubTypes);
     List <com.xml.schema.bindings.XactType> x = af.createXactListInstance(xactList);
     
     ws.setTransactions(f.createRSGenreicTransactionSearchTransactions());
     ws.setTransactionTypes(f.createRSGenreicTransactionSearchTransactionTypes());
     ws.setTransactionSubTypes(f.createRSGenreicTransactionSearchTransactionSubTypes());
     
     ws.setSearchCriteria(gxct);
     
     if (xt != null) {
	 ws.getTransactionTypes().getItem().addAll(xt); 
     }
     if (xst != null) {
	 ws.getTransactionSubTypes().getItem().addAll(xst); 
     }
     if (x != null) {
	 ws.getTransactions().getTransaction().addAll(x); 
     }
     String rawXml = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
     return rawXml;
 }
}

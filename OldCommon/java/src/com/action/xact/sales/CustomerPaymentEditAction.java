package com.action.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;

import com.api.CashReceiptsApi;

import com.api.db.DatabaseException;

import com.constants.RMT2ServletConst;
import com.constants.XactConst;

import com.factory.SalesFactory;

import com.util.SystemException;
import com.util.CashReceiptsException;


/**
 * This class provides functionality to serve the requests of the Customer New Sales Order Item Selection user interface.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerPaymentEditAction extends SalesOrderMaintAction {
	
	
	private static final String COMMAND_ACCEPTPAYMENT = "XactSales.CustomerPaymentEdit.acceptpayment";
    private static final String COMMAND_BACK = "XactSales.CustomerPaymentEdit.back";
    
	private Logger logger;
	
	protected CashReceiptsApi   crApi;
    
	
      /**
       * Default constructor
       *
       */
      public CustomerPaymentEditAction()  {
          super(); 
          logger = Logger.getLogger("CustomerPaymentEditAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerPaymentEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          this.init(this.context, this.request);
      }
      
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super.init(_context, _request);
          this.crApi = SalesFactory.createCashReceiptsApi(this.dbConn, _request);
      }
      
      /**
         * Processes the client's request using request, response, and command.
         *
         * @param request   The HttpRequest object
         * @param response  The HttpResponse object
         * @param command  Comand issued by the client.
         * @Throws SystemException when an error needs to be reported.
         */
      public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
    	  super.processRequest(request, response, command);
          
    	  if (command.equalsIgnoreCase(CustomerPaymentEditAction.COMMAND_ACCEPTPAYMENT)) {
              this.saveData();
          }
          if (command.equalsIgnoreCase(CustomerPaymentEditAction.COMMAND_BACK)) {
              this.recallCustomerSalesConsole();
          }
      }
      
      
      /**
       * Retreives bsic customer, sales order, and transactin  data from the client's request.
       */
      protected void receiveClientData() throws ActionHandlerException {
    	  super.receiveClientData();     
	  }
      
      
      /**
       * Packages detail Sales Order data into the HttpServlerRequest object which is required to serve as the response to the client. 
       * 
       * @throws ActionHandlerException
       */
      protected void sendClientData() throws ActionHandlerException {
    	  super.sendClientData();
    	  this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
      }
   
      /**
       * Applies customer payment to which will reduce the customer's balance by the payment. 
       * 
       * @throws ActionHandlerException
       */
      public void save() throws ActionHandlerException {
    	  super.save();
    	  //Xact xact = this.getXact();
    	  this.xact.setXactTypeId(XactConst.XACT_TYPE_CASHPAY);
    	  try {
    		  int xactId = this.crApi.maintainCustomerPayment(this.xact, this.customer.getId());
    		  logger.log(Level.DEBUG, "New Transaction id for cash payment: " + xactId);
    		  this.transObj.commitUOW();
    		  this.msg = "Customer payment was applied successfully";
    	  }
    	  catch (CashReceiptsException e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  this.transObj.rollbackUOW();
    		  this.msg = "Customer payment failed";
    		  throw new ActionHandlerException(e.getMessage());
    	  }
      }
      

}
package com.action.xact.sales;


import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.RMT2ServletConst;

import com.factory.SalesFactory;

import com.action.ActionHandlerException;

import com.util.SalesOrderException;
import com.util.SystemException;

import com.api.db.DatabaseException;



/**
 * This class provides functionality to serve the requests of the Customer Sales Order View user interface.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerSalesOrderViewAction extends SalesOrderMaintAction {
	
    private static final String COMMAND_BACK = "XactSales.CustomerSalesOrderView.vieworders";
    private static final String COMMAND_CANCEL = "XactSales.CustomerSalesOrderView.cancelorder";
    private static final String COMMAND_REFUND = "XactSales.CustomerSalesOrderView.salesrefund";
   
	private Logger logger;
	
	
      /**
       * Default constructor
       *
       */
      public CustomerSalesOrderViewAction()  {
          super(); 
          logger = Logger.getLogger("CustomerSalesOrderViewAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerSalesOrderViewAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          this.init(this.context, this.request);
      }
      
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super.init(_context, _request);
           
          // Create transaction api
          try {
        	  this.salesApi = SalesFactory.createApi(this.dbConn,  this.request);
	      }
	      catch (DatabaseException e) { 
	          throw new SystemException(e);
	      }
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
          
          if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_BACK)) {
              this.doBack();
          }
          if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_CANCEL)) {
              this.doCancel();
          }
          if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_REFUND)) {
              this.doRefund();
          }
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
       * Cancels a sales order using sales order data originating from the user's request.
       * 
       * @return The transaction id of the cancelled sales order
       * @throws ActionHandlerException
       */
      public int doCancel() throws ActionHandlerException {
    	  int xactId = 0;
    	  this.receiveClientData();
    	  
    	  // Cancel the order
    	  try {
    		  xactId = super.cancel();  
    		  this.transObj.commitUOW();
    		  this.sendClientData();
    		  return xactId;
    	  }
    	  catch (SalesOrderException e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  this.transObj.rollbackUOW();
    		  throw new ActionHandlerException(e.getMessage());
    	  }
      }
      
      /**
       * Refunds a sales order using sales order data originating from the user's request.
       * 
       * @return The transaction id of the cancelled sales order
       * @throws ActionHandlerException
       */
      public int doRefund() throws ActionHandlerException {
    	  int xactId = 0;
    	  this.receiveClientData();
    	  
    	  // Cancel the order
    	  try {
    		  xactId = super.refund();  
    		  this.transObj.commitUOW();
    		  this.sendClientData();
    		  return xactId;
    	  }
    	  catch (SalesOrderException e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  this.transObj.rollbackUOW();
    		  throw new ActionHandlerException(e.getMessage());
    	  }
      }
     
}
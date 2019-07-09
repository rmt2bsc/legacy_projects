package com.action.xact.sales;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.util.SalesOrderException;
import com.util.SystemException;


/**
 * This class provides functionality to serve the requests of the Customer Sales Order List user interface.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerSalesOrderListAction extends SalesOrderMaintAction {
	
    private static final String COMMAND_EDIT = "XactSales.CustomerSalesViewOrders.edit";
    private static final String COMMAND_BACK = "XactSales.CustomerSalesViewOrders.back";
    
	private Logger logger;
    
	
      /**
       * Default constructor
       *
       */
      public CustomerSalesOrderListAction()  {
          super(); 
          logger = Logger.getLogger("CustomerSalesOrderListAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerSalesOrderListAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          this.init(this.context, this.request);
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
          
          if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_EDIT)) {
              this.editData();
          }
          if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_BACK)) {
              this.recallCustomerSalesConsole();
          }
      }
      
      
      /**
       * Retreives bsic customer, sales order, and transactin  data from the client's request.
       */
      protected void receiveClientData() throws ActionHandlerException {
    	  super.receiveClientData();      }
      
      
      /**
       * Packages detail Sales Order data into the HttpServlerRequest object which is required to serve as the response to the client. 
       * 
       * @throws ActionHandlerException
       */
      protected void sendClientData() throws ActionHandlerException {
    	  super.sendClientData();
      }
      
      /**
       * Gathers data pertaining to sales order total, sales invoice, sales order status, 
       * sales order items, the mode which the sales order details interface will display data.
       * 
       * @throws ActionHandlerException
       */
      public void edit() throws ActionHandlerException {
          List items;
    	  
    	  super.edit();
    	  // Call stored function to obtain sales order amount and return to the client.
	      try {
	    	  // Get existing sales order items
	    	  items = this.salesApi.findSalesOrderItems(this.salesOrder.getId());
	    	  this.itemHelper.packageItemsByTypes(items);
	      }
	      catch (SalesOrderException e) {
	          logger.log(Level.ERROR, e.getMessage());
	          throw new ActionHandlerException(e);
	      }
	      catch (SystemException e) {
	    	  logger.log(Level.ERROR, e.getMessage());
	          throw new ActionHandlerException(e);
	      }
	 	   return;
      }
      
      
	 
}
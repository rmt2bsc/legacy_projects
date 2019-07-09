package com.action.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.util.SystemException;


/**
 * This class provides functionality to serve the requests of the Customer New Sales Order Item Selection user interface.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerNewSalesItemSelectAction extends SalesOrderMaintAction {
	
    private static final String COMMAND_ADD = "XactSales.CustomerNewSalesItemSelect.next";
    private static final String COMMAND_BACK = "XactSales.CustomerNewSalesItemSelect.back";
    
	private Logger logger;
    
	
      /**
       * Default constructor
       *
       */
      public CustomerNewSalesItemSelectAction()  {
          super(); 
          logger = Logger.getLogger("CustomerNewSalesItemSelectAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerNewSalesItemSelectAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          this.init(this.context, this.request);
      }
      
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super.init(_context, _request);
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
          
          if (command.equalsIgnoreCase(CustomerNewSalesItemSelectAction.COMMAND_ADD)) {
              this.addData();
          }
          if (command.equalsIgnoreCase(CustomerNewSalesItemSelectAction.COMMAND_BACK)) {
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
      }
      
      /**
       * Gathers sales order item selections, which exist as a String array of item numbers, from the user's request and builds a 
       * collection of sales order item objects to be associated with the new sales order.
       * 
       * @throws ActionHandlerException
       */
      public void add() throws ActionHandlerException {
          List existItems;
    	  super.add();
          logger.log(Level.DEBUG, "Building original Sales Order");
          
          // Convert and add existing sales order items to the sales order item helper
          if (this.salesOrder.getId() > 0) {
              try {
                  existItems = this.salesApi.findSalesOrderItems(this.salesOrder.getId());
                  this.itemHelper.packageItemsByTypes(existItems);
              }
              catch (Exception e) {
                  throw new ActionHandlerException(e.getMessage());
              }
          }
          
    	  logger.log(Level.DEBUG, "Building new Sales Order");
    	  String selectedItems[] = this.httpHelper.getHttpNewItemSelections();

	      try {
	    	  // Convert and add newly selected item numbers to the sales order item helper
	    	  this.itemHelper.packageItemsByTypes(selectedItems);
	      }
	      catch (SystemException e) {
	    	  logger.log(Level.ERROR, e.getMessage());
	          throw new ActionHandlerException(e);
	      }
	 	   return;
      }
	 
}
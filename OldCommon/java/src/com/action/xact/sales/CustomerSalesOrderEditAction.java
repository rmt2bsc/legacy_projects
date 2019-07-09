package com.action.xact.sales;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.bean.Xact;

import com.constants.ItemConst;
import com.constants.RMT2ServletConst;

import com.factory.XactFactory;

import com.util.SalesOrderException;
import com.util.SystemException;
import com.util.XactException;


/**
 * This class provides functionality to serve the requests of the Customer New Sales Order Item Selection user interface.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerSalesOrderEditAction extends SalesOrderMaintAction {
	
    private static final String COMMAND_ADD = "XactSales.CustomerSalesOrderEdit.add";
    private static final String COMMAND_SAVE = "XactSales.CustomerSalesOrderEdit.save";
    private static final String COMMAND_BACK = "XactSales.CustomerSalesOrderEdit.vieworders";
    private static final String COMMAND_DELETE = "XactSales.CustomerSalesOrderEdit.delete";
    
    private boolean invoiceRequested;
    
	private Logger logger;
	
	
      /**
       * Default constructor
       *
       */
      public CustomerSalesOrderEditAction()  {
          super(); 
          logger = Logger.getLogger("CustomerSalesOrderEditAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerSalesOrderEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
          
          if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_ADD)) {
              this.add();
          }
          if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_SAVE)) {
              this.saveData();
          }
          if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_BACK)) {
              this.doBack();
          }
          if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_DELETE)) {
              this.deleteData();
              this.doBack();
          }
      }
      
      
      /**
       * Retreives sales order data from the client's UI page.
       */
      protected void receiveClientData() throws ActionHandlerException {
    	  String temp;
    	  super.receiveClientData();
	      
	      // Get Invoiced indicator for single row page of client
   	      temp = this.request.getParameter("Invoiced");
   	      this.invoiceRequested = (temp != null);  
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
       * Drives the process of adding items to an existing sales order.
       * 
       * @throws ActionHandlerException
       */
      public void add()  throws ActionHandlerException {
          super.add();
          try {
              this.getServiceAndMerchandiseItems();
              this.updateSalesOrder();
              this.transObj.commitUOW();
              
              // Forward call to Sales Console action handler.
              CustomerSalesConsoleAction console = new CustomerSalesConsoleAction(this.context, this.request);
              console.setupNewSalesOrderSelection();
              return;              
          }
          catch (Exception e) {
              this.transObj.rollbackUOW();
              throw new ActionHandlerException(e.getMessage());
          }
          
      }
      
      /**
       * Drives the process of persisting sales order data derived fromthe client's request.
       * 
       * @throws ActionHandlerException
       */
      public void save() throws ActionHandlerException {
    	  super.save();
          this.getServiceAndMerchandiseItems();       
    	  try {
    		  this.xact = this.updateSalesOrder();
              this.transObj.commitUOW();
              this.msg = "Sales Order was saved successfully";
    	  }
    	  catch (Exception e) {
              this.msg = "Failure: " + e.getMessage();
              this.transObj.rollbackUOW();
    		  throw new ActionHandlerException(e.getMessage());
    	  }
	 	   return;
      }
      
      
      
      /**
       * Drives the process of deleting sales order data derived from the client's request.
       * 
       * @throws ActionHandlerException
       */
      public void delete() throws ActionHandlerException {
    	  try {
              super.delete();
    		  this.salesApi.deleteSalesOrder(this.salesOrder);  
    		  this.transObj.commitUOW();
    	  }
    	  catch (SalesOrderException e) {
    		  this.transObj.rollbackUOW();
    		  throw new ActionHandlerException(e);
    	  }
      }
      
      
      
      /**
       * Initializes the sales order item helper object with service and merchandise item objects.
       * 
       * @throws ActionHandlerException
       */
      private void getServiceAndMerchandiseItems() throws ActionHandlerException {
          List items;

          // Get existing sales order items       
          try {
              items = this.httpHelper.getHttpXactItems(this.salesOrder, ItemConst.ITEMTYPE_SRVC);
              this.itemHelper.packageItemsByTypes(items);
              items = this.httpHelper.getHttpXactItems(this.salesOrder, ItemConst.ITEMTYPE_MERCH);
              this.itemHelper.packageItemsByTypes(items);
          }
          catch (SystemException e) {
              logger.log(Level.ERROR, e.getMessage());
              throw new ActionHandlerException(e);
          }
      }
      
      /**
       * Applies Sales Order changes to the database.  <p>This method requires that the mandatory request 
       * data (Customer, sales order base, sales order items, and etc)  are gathered before its invocation.   
       * The invoicing of the sales order is handle here provided that the this method was invoked from the 
       * user's request to explicitly save the sales order.
       *   
       * @return A valid transaction object when sales order has been invoiced.  Otherwise, null is returned.
       * @throws ActionHandlerException
       * @throws DatabaseException
       * @throws SalesOrderException
       */
      private Xact updateSalesOrder() throws ActionHandlerException, DatabaseException, SalesOrderException {
          Xact xact = null;
          int salesOrderId = 0;
          boolean isOkToInvoice = false; 
 	      
 	      try {
 	          // Create/Update sales order
 	          ArrayList items = (ArrayList) this.itemHelper.getItems();
 	          this.xact = XactFactory.createXact();
 	          salesOrderId = this.salesApi.maintainSalesOrder(this.salesOrder, this.customer, items);
 	 	       
 	          // Refresh the sales order object by retrieving latest sales order record
 	          this.salesOrder =  this.salesApi.findSalesOrderById(salesOrderId);
 	    	    
 	          // Sales Order cannot be invoiced when client is requesting to add inventory items.
 	          isOkToInvoice = (this.command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_SAVE));
 	          xact = XactFactory.createXact();
 	          if (isOkToInvoice) {
 	              logger.log(Level.DEBUG, "Sales Order is eligible to be invoice");
 	              if (this.salesOrder.getInvoiced() == 0 && this.invoiceRequested) {
 	                  logger.log(Level.DEBUG, "Ready to invoice Sales Order: " + salesOrderId);
 		              
 	                  // Get transaction data from client, mainly the transaction reason
 	                  this.httpHelper.getHttpCombined();
 	                  xact =  this.httpHelper.getXact();
                    
 	                  // Invoice sales order.
 	                  this.salesApi.invoiceSalesOrder(this.salesOrder, xact);
 	                  logger.log(Level.INFO, "Sales Order successfully invoiced: " + salesOrderId);
                     
 	                  // Get Transaction object for the inovice
 	                  try {
 	                      xact = this.xactApi.findXactById(xact.getId());
 	                      if (xact == null) {
 	                          xact = XactFactory.createXact();    
 	                      }
 	                  }
 	                  catch (XactException e) {
 	                      logger.log(Level.ERROR,  "SalesOrderException " + e.getMessage());
 	                      throw new SalesOrderException(e);
 	                  }
 	              }    
 	          }
 	          return xact;
 	      }
 	      catch (Exception e) {
 	    	 logger.log(Level.ERROR, e.getMessage());
 	    	    throw new SalesOrderException(e);
 	      }
      }
      
}
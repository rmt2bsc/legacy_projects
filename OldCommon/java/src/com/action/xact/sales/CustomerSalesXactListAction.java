package com.action.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;

import com.api.GLCustomerApi;
import com.api.db.DatabaseException;

import com.bean.Customer;
import com.bean.CustomerWithName;
import com.bean.SalesOrder;

import com.constants.SalesConst;

import com.factory.AcctManagerFactory;

import com.util.GLAcctException;
import com.util.SystemException;


/**
 * Action handler that responds to sales order payment reversal and display sales order console requests.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerSalesXactListAction extends SalesOrderMaintAction {
	private static final String COMMAND_REVERSEPAYMENT = "XactSales.CustomerSalesXactList.reversepaymentedit";
    private static final String COMMAND_BACK = "XactSales.CustomerSalesXactList.back";
    private GLCustomerApi  custApi;
    private SalesOrder so;
    private Customer cust;
    private CustomerWithName custExt;
	private Logger logger;
    
	
      /**
       * Default constructor
       *
       */
      public CustomerSalesXactListAction()  {
          super(); 
          logger = Logger.getLogger("CustomerSalesXactListAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerSalesXactListAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          this.init(this.context, this.request);
      }
      
      
      /**
       * Initializes this object using _conext and _request and setup a GlCustomerApi object.
       * 
       * @throws SystemException
       */
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super.init(_context, _request);
          try {
        	  this.custApi = AcctManagerFactory.createCustomer(this.dbConn, _request);  
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
          
          if (command.equalsIgnoreCase(CustomerSalesXactListAction.COMMAND_REVERSEPAYMENT)) {
              this.getClientTransaction();
          }
          if (command.equalsIgnoreCase(CustomerSalesXactListAction.COMMAND_BACK)) {
              this.recallCustomerSalesConsole();
          }
      }
      
      
      /**
       * Retreives the customer data from the client's request.
       */
      protected void receiveClientData() throws ActionHandlerException {
          super.receiveClientData();
          try {
              // Obtain Customer data from the request object.
              this.cust = this.httpHelper.getHttpCustomer();
              this.custExt = custApi.findCustomerWithName(this.cust.getId());
              this.so = this.calcCustomerBalance(this.cust.getId());
              this.refreshXact();
          } 
          catch (GLAcctException e) {
              logger.log(Level.ERROR, "GLAcctException occurred. " + e.getMessage());
              throw new ActionHandlerException(e);
          }
      }
      
      /**
       * Sends the response to the client's request with customer, transaction, and sales order data. 
       * Response attribute names are requires as follows:  customer=SalesConst.CLIENT_DATA_CUSTOMER_EXT, 
       * transaction=XactConst.CLIENT_DATA_XACT, and sales order=SalesConst.CLIENT_DATA_SALESORDER. 
       */
      protected void sendClientData() throws ActionHandlerException {
    	  super.sendClientData();
          this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT, this.custExt);
          this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.so);
      }
      
      
      
      /**
       * Gathers key data from the client which is used to obtain customer, transaction, 
       * and sales order data for the response.
       *  
       * @throws ActionHandlerException
       */
      private void getClientTransaction() throws ActionHandlerException {
    	  this.receiveClientData();
    	  this.sendClientData();
      }
  
}
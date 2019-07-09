package com.action.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.action.xact.AbstractXactAction;

import com.api.GLCustomerApi;
import com.api.SalesOrderApi;
import com.api.XactManagerApi;
import com.api.db.DatabaseException;

import com.bean.Customer;
import com.bean.CustomerWithName;
import com.bean.SalesOrder;
import com.bean.Xact;

import com.constants.SalesConst;
import com.constants.XactConst;

import com.factory.AcctManagerFactory;
import com.factory.SalesFactory;
import com.factory.XactFactory;

import com.util.GLAcctException;
import com.util.SalesOrderException;
import com.util.SystemException;
import com.util.XactException;


/**
 * This class provides action handlers needed to serve the request of the Customer Sales Order Console user interface.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerSalesConsoleAction extends AbstractXactAction {
    private static final String COMMAND_VEIWORDERS = "XactSales.CustomerSalesConsole.vieworders";
    private static final String COMMAND_NEWORDER = "XactSales.CustomerSalesConsole.neworder";
    private static final String COMMAND_PAYMENT = "XactSales.CustomerSalesConsole.acceptpayment";
    private static final String COMMAND_VIEWTRANSACTIONS = "XactSales.CustomerSalesConsole.view";
    
	private Logger logger;
    private GLCustomerApi  custApi;
    private SalesOrderApi salesApi;
    private XactManagerApi xactApi;
    private Customer cust;
    private CustomerWithName custExt;
    private List custOrders;
    private List payments;
    private SalesOrderItemHelper itemHelper;
    private SalesOrder so;
    private Xact xact;
	
	
      /**
       * Default constructor
       *
       */
      public CustomerSalesConsoleAction()  {
          super(); 
          logger = Logger.getLogger("CustomerSalesConsoleAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerSalesConsoleAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          logger = Logger.getLogger(CustomerSalesConsoleAction.class);
          this.init(this.context, this.request);
      }
      
      
      
      /**
       * Initializes this object using _conext and _request.  This is needed in the 
       * event this object is inistantiated using the default constructor.
       * 
       * @throws SystemException
       */
      protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
          super.init(_context, _request);
          try {
              this.salesApi = SalesFactory.createApi(this.dbConn);
              this.custApi = AcctManagerFactory.createCustomer(this.dbConn, _request);    
              this.xactApi = XactFactory.create(this.dbConn, _request);
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
          
          if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_VEIWORDERS)) {
              this.getCustomerSalesOrders();
          }
          if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_NEWORDER)) {
              this.setupNewSalesOrderSelection();
          }
          if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_PAYMENT)) {
              this.acceptPaymentOnAccount();
          }
          if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_VIEWTRANSACTIONS)) {
              this.getTransactions();
          }
      }
      
      
      /**
       * Retreives the customer data from the client's request.
       */
      protected void receiveClientData() throws ActionHandlerException {
          
          try {
              // Obtain Customer data from the request object.
              this.cust = this.httpHelper.getHttpCustomer();
              this.custExt = custApi.findCustomerWithName(this.cust.getId());
              try {
                  this.xact = this.httpHelper.getHttpXact();    
              }
              catch (Exception e) {
                  this.xact = XactFactory.createXact();
              }
              
          } 
          catch (GLAcctException e) {
              logger.log(Level.ERROR, "GLAcctException occurred. " + e.getMessage());
              throw new ActionHandlerException(e);
          }
      }
      
      
      /**
       * Sends the response of the client's request with an ArrayList of  CombinedSalesOrder objects and a 
       * CustomerWithName object which their attriute names are required to be set as "orderhist" and 
       * "customer", respectively.
       */
      protected void sendClientData() throws ActionHandlerException {
          this.request.setAttribute(SalesConst.CLIENT_DATA_ORDERLIST, this.custOrders);
          this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT, this.custExt);
          this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.so);
          if (this.itemHelper != null) {
              this.request.setAttribute(SalesConst.CLIENT_DATA_SERVICE_ITEMS, this.itemHelper.getSrvcItems());
              this.request.setAttribute(SalesConst.CLIENT_DATA_MERCHANDISE_ITEMS, this.itemHelper.getMerchItems());              
          }
	      this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.xact);
	      this.request.setAttribute(XactConst.CLIENT_DATA_SALESPAYMENT_HIST, this.payments);
      }
      
      
      
      /**
       * Gathers a list of sales orders for the target customer found in the client's request and sends the results back to the client.
       * 
       * @throws ActionHandlerException
       */
      public void getCustomerSalesOrders() throws ActionHandlerException {
          this.receiveClientData();
          this.custOrders = this.getCustomerSalesOrders(this.cust);
          this.sendClientData();
      }
      
      
      /* Retrieves the customer's sales order history using _cust.   
       * 
       * @param _cust Customer object
       * @return An ArrayList of CombinedSalesOrder objects
       * @throws SalesOrderException
       */
      protected List getCustomerSalesOrders(Customer _cust) throws ActionHandlerException {

          List list;
          try {
              list = this.salesApi.findExtendedSalesOrderByCustomer(_cust.getId());
              //CombinedSalesOrder cso[] = new CombinedSalesOrder[list.size()];
              //return (CombinedSalesOrder []) this.custOrders.toArray(cso);
              return list;
          }
          catch (SalesOrderException e ) {
              logger.log(Level.ERROR, "SalesOrderExeption occurred. " + e.getMessage());
              throw new ActionHandlerException(e);
          }
      }
      
      
      /**
       * 
       * @throws ActionHandlerException
       */
     public void setupNewSalesOrderSelection() throws ActionHandlerException {
    	 this.receiveClientData();
    	 try {
        	 // Create new sales order object.
    		 this.so = SalesFactory.createSalesOrder();
             // See if we are dealing with an existing sales order or not.  so.getId() >= 1 for existing.
             String strOrderId = this.request.getParameter("OrderId");
             int orderId = 0;
             try {
                 orderId = Integer.parseInt(strOrderId);
             }
             catch (NumberFormatException e) {
                 orderId = 0;
             }
             this.so.setId(orderId);
              // Get inventory items that are available for selection
	    	  this.itemHelper = new SalesOrderItemHelper(this.context, this.request, this.cust, this.so);
	    	  this.itemHelper.setupAvailSalesOrderItems();
    	 }
    	 catch (Exception e) {
    		 logger.log(Level.ERROR, e.getMessage());
    		 throw new ActionHandlerException(e.getMessage());
    	 }
    	 finally {
    		 this.sendClientData();
    	 }
     }
     
     
     /**
      * Prepares the client for a cash receipt transaction.
      * 
      * @throws ActionHandlerException
      */
     protected void acceptPaymentOnAccount() throws ActionHandlerException {
         this.receiveClientData();
    	 if (this.xact.getId() == 0) {
    		 this.xact.setXactTypeId(XactConst.XACT_TYPE_CASHPAY);
    	 }
         this.calcCustomerBalance();
         this.sendClientData();
     }
     
     
     /**
	   * Retrieves a customer's payment history
	   * 
	   * @return An array of all the customer's transactions
	   * @throws SalesOrderException
	   */
	  public int getTransactions() throws ActionHandlerException {
		  try {
              this.receiveClientData();
			  this.payments = this.xactApi.findCustomerXactHist(this.cust.getId());
              this.calcCustomerBalance();
              this.sendClientData();
              return this.payments.size();
		  }
		  catch (XactException e) {
			  logger.log(Level.ERROR, "XactException occurred. " + e.getMessage());
		      throw new ActionHandlerException(e);
		  }
	  }
      
      /**
       * Makes call to customer api to calculate and capture customer's balance.   The balance is added to the 
       * sales order member variable.   If sales orer varialbe is invalid it is instantiated.  
       * 
       */
      private void calcCustomerBalance() throws ActionHandlerException {
          try {
              if (this.so == null) {
                  // Create new sales order object.
                  this.so = SalesFactory.createSalesOrder();                  
              }
              double balance = this.custApi.findCustomerBalance(this.cust.getId());
              this.so.setOrderTotal(balance);
          }
          catch (Exception e) {
              throw new ActionHandlerException(e.getMessage());
          }
      }
      
}
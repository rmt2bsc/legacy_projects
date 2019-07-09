package com.action.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.action.xact.AbstractXactAction;
import com.action.accounting.customer.CustomerAction;
import com.action.accounting.customer.CustomerSearchAction;

import com.api.SalesOrderApi;
import com.api.XactManagerApi;
import com.api.GLCustomerApi;
import com.api.db.DatabaseException;

import com.bean.Customer;
import com.bean.CustomerWithName;
import com.bean.SalesInvoice;
import com.bean.SalesOrder;
import com.bean.SalesOrderStatus;
import com.bean.SalesOrderStatusHist;

import com.constants.GeneralConst;
import com.constants.RMT2ServletConst;
import com.constants.SalesConst;

import com.factory.AcctManagerFactory;
//import com.factory.ContactsFactory;
import com.factory.SalesFactory;
import com.factory.XactFactory;

import com.util.GLAcctException;
import com.util.SalesOrderException;
import com.util.SystemException;
import com.util.XactException;


/**
 * This class provides action handler functionality that is common serving sales order requests.
 * 
 * @author Roy Terrell
 *
 */ 
public abstract class SalesOrderMaintAction extends AbstractXactAction {
	private static final int UI_MODE_EDIT = 1;
	private static final int UI_MODE_VIEW = 2;
	private static final int SO_INOVICED = 1;
	private static final int SO_NOTINOVICED = 0;
	private static final String URL_EDIT = "/forms/xact/sales/CustomerSalesOrderEdit.jsp";
	private static final String URL_VIEW = "/forms/xact/sales/CustomerSalesOrderView.jsp";
    
	private Logger logger;
    private GLCustomerApi  custApi;
	
    /** Sales Order API */
    protected SalesOrderApi    salesApi;
    /** Transaction API */
    protected XactManagerApi xactApi;
    /** Customer object variable */
    protected Customer customer;
    /** Customer Extension object variable */
    protected CustomerWithName customerExt;
    /** Sales Order object variable */
    protected SalesOrder salesOrder;
    /** Sales Invoice object variable */
    protected SalesInvoice si = null;
    /** Sales Order Status object variable */
    protected SalesOrderStatus sos;
    /** Sales order item helper object variable */
    protected SalesOrderItemHelper itemHelper;
    /** Variable for determining the mode of the sales order is displayed in the UI: edit or read-only */
    protected int uiMode;
	
	
      /**
       * Default constructor
       *
       */
      public SalesOrderMaintAction()  {
          super(); 
          logger = Logger.getLogger("SalesOrderMaintAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public SalesOrderMaintAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
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
              this.salesApi = SalesFactory.createApi(this.dbConn, _request);
              this.custApi = AcctManagerFactory.createCustomer(this.dbConn, _request);
              this.xactApi = XactFactory.create(this.dbConn,  this.request);
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
      }
      
      
      /**
       * Retreives bsic customer, sales order, and transactoin  data from the client's request.
       */
      protected void receiveClientData() throws ActionHandlerException {
          super.receiveClientData();
    	  try {
    		  // Get data from client's page
		 	  this.customer = this.httpHelper.getHttpCustomer();
		 	  this.customerExt = this.custApi.findCustomerWithName(this.customer.getId());
		 	  this.salesOrder = this.httpHelper.getHttpSalesOrder();
		 	  this.xact = this.httpHelper.getHttpXact();

	          // Get Invoice object
		 	  this.si = this.getSalesOrderInvoice(salesOrder.getId());
				 	   
		 	  // Get Current sales order status
		 	  this.sos = this.getCurrentSalesOrderStatus(salesOrder.getId());

	    	  // Determine if sales order editable.
	    	  switch (salesOrder.getInvoiced()) {
	    	  case SalesOrderMaintAction.SO_INOVICED:
	    		  this.uiMode = SalesOrderMaintAction.UI_MODE_VIEW;  // View Only
		 	       break;
		 	  case SalesOrderMaintAction.SO_NOTINOVICED:
		 		  switch (this.sos.getId()) {
		 		  case SalesConst.STATUS_CODE_CLOSED:
		 		  case SalesConst.STATUS_CODE_CANCELLED:
		 		  case SalesConst.STATUS_CODE_REFUNDED:
		 			  this.uiMode = SalesOrderMaintAction.UI_MODE_VIEW;
		 			  break;
		 		  default:
		 			  this.uiMode = SalesOrderMaintAction.UI_MODE_EDIT; // Editable
          			  break;
		 		  } // end switch
	    	  } // end switch
			 	   
	    	  return;
    	  } // end try
    	  catch (SalesOrderException e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  throw new ActionHandlerException(e);
    	  }
    	  catch (GLAcctException e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  throw new ActionHandlerException(e);
    	  }
    	  catch (XactException e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  throw new ActionHandlerException(e);
    	  }
      }
      
      
      /**
       * Packages detail Sales Order data into the HttpServlerRequest object which is required to serve as the response to the client. 
       * 
       * @throws ActionHandlerException
       */
      protected void sendClientData() throws ActionHandlerException {
          super.sendClientData();
          double salesOrderTotal;
          // Call stored function to obtain sales order amount and return to the client.
          try {
              salesOrderTotal = this.salesApi.getSalesOrderTotal(this.salesOrder.getId());
              this.salesOrder.setOrderTotal(salesOrderTotal);              
          }
          catch (Exception e) {
              throw new ActionHandlerException(e.getMessage());
          }

    	  // Prepare to send results to client
    	  this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT, this.customerExt);
    	  this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.salesOrder);
    	  this.request.setAttribute(SalesConst.CLIENT_DATA_STATUS, this.sos);
    	  this.request.setAttribute(SalesConst.CLIENT_DATA_INVOICE, this.si);
    	  this.request.setAttribute(SalesConst.CLIENT_DATA_XACT, this.xact);
          if (this.itemHelper != null) {
              this.request.setAttribute(SalesConst.CLIENT_DATA_SERVICE_ITEMS, this.itemHelper.getSrvcItems());
              this.request.setAttribute(SalesConst.CLIENT_DATA_MERCHANDISE_ITEMS, this.itemHelper.getMerchItems());              
          }
	       
	      switch (this.uiMode) {
	      case SalesOrderMaintAction.UI_MODE_EDIT:
	    	  this.request.setAttribute(RMT2ServletConst.REQUEST_DELAYED_RESPONSE, SalesOrderMaintAction.URL_EDIT);
	    	  break;
	    	  
	      case SalesOrderMaintAction.UI_MODE_VIEW:
	    	  this.request.setAttribute(RMT2ServletConst.REQUEST_DELAYED_RESPONSE, SalesOrderMaintAction.URL_VIEW);
	      } // end switch
	      this.request.setAttribute(GeneralConst.REQ_COMMAND, this.request.getAttribute(GeneralConst.REQ_CLIENTACTION));
      }
      
      /**
       * Setups the data necessary for navigating the user to the previous URL.
       * 
       * @throws ActionHandlerException
       */
      protected void doBack() throws ActionHandlerException {
          try {
              // Forward call to Sales Console action handler.
              CustomerSalesConsoleAction console = new CustomerSalesConsoleAction(this.context, this.request);
              console.getCustomerSalesOrders();
              return;              
          }
          catch (Exception e) {
              throw new ActionHandlerException(e.getMessage());
          }      
      }
      
      
      /**
       * Performs initializations needed for creating a sales order.   <p>The only thing that get initializes here is the sales 
       * order item helper class.   Override in order to add sales order items. 
       * 
       * @throws ActionHandlerException
       */
      public void add() throws ActionHandlerException {
          super.add();
    	  this.receiveClientData();
    	  this.initItemHelper();
    	  return;
      }
      
      /**
       * Performs initializations needed for modifying an existing sales order.   <p>The only thing that get initializes here is the sales 
       * order item helper class.   Override in order to modify sales order items. 
       * 
       * @throws ActionHandlerException
       */
      public void edit() throws ActionHandlerException {
          super.edit();
    	  this.initItemHelper();
    	  return;
      }
      
      /**
       * Performs initializations needed for persisting changes to a sales order.   <p>The only thing that get initializes here is the sales 
       * order item helper class.   Override in order to modify sales order items. 
       * 
       * @throws ActionHandlerException
       */
      public void save() throws ActionHandlerException {
    	  super.save();
          this.initItemHelper();
          return;
      }
      
      /**
       * Handles a delete action.   <p>The only thing that get initializes here is the sales 
       * order item helper class.   Override in order to modify sales order items. 
       * 
       * @throws ActionHandlerException
       */
      public void delete() throws ActionHandlerException {
          super.delete();
          this.initItemHelper();
          return;
      }
      
      /**
       * Cancels a sales order.
       * 
       * @return The transaction id of the cancelled sales order
       * @throws SalesOrderException
       */
      public int cancel() throws SalesOrderException {
    	  int xactId = 0;
    	  this.initItemHelper();
    	  
    	  // Cancel the order
   		  xactId = this.salesApi.cancelSalesOrder(this.salesOrder.getId());
   		  return xactId;
      }
      
      /**
       * Refunds a sales order.
       * 
       * @return The transaction id of the refunded sales order
       * @throws SalesOrderException
       */
      public int refund() throws SalesOrderException {
    	  int xactId = 0;
    	  this.initItemHelper();
    	  
    	  // Refund the order
		  xactId = this.salesApi.refundSalesOrder(this.salesOrder.getId());
		  return xactId;
      }
      
      /**
       * Setup a sales order item helper object.
       *
       */
      private void initItemHelper() {
 	      try {
	    	  // Get existing sales order items
	    	  this.itemHelper = new SalesOrderItemHelper(this.context, this.request, this.customer, this.salesOrder);
	      }
	      catch (SystemException e) {
	          logger.log(Level.ERROR, e.getMessage());
	      }
	      catch (DatabaseException e) {
	    	  logger.log(Level.ERROR, e.getMessage());
	      }
	 	   return;    	  
      }
	  
	  /**
	   * Retrieves the sales order invoice object using _slaesOrder.
	   * 
	   * @param _salesOrder
	   * @return {@link SalesInvoice}
	   */
	  private SalesInvoice getSalesOrderInvoice(int _salesOrder) throws SalesOrderException {
		   SalesInvoice si = this.salesApi.findSalesOrderInvoice(_salesOrder);
		   if (si == null) {
		       try {
		          si = SalesFactory.createSalesInvoice();    
		       }
		       catch (SystemException e) {
			       logger.log(Level.ERROR, "SystemException occurred. " + e.getMessage()); 
			       throw new SalesOrderException(e);
		       }
		   }
		   return si;
	  }
 
	  
	  /**
	   * Retrieves the current sales order status using _salesOrderId.
	   * 
	   * @param _salesOrderId
	   * @return
	   */
	  private SalesOrderStatus getCurrentSalesOrderStatus(int _salesOrderId) throws SalesOrderException {
	      SalesOrderStatusHist sosh = null;
 	 	  SalesOrderStatus sos = null;

		   try {
			 	 sosh = this.salesApi.findCurrentSalesOrderStatusHist(_salesOrderId);
			 	 if (sosh == null) {
			 	     sos = new SalesOrderStatus();
			 	 }     
			 	 else {
			 	     sos = this.salesApi.findSalesOrderStatus(sosh.getSalesOrderStatusId());
			 	     if (sos == null) {
			 	        sos = new SalesOrderStatus();
			 	     }
			 	 }
			 	 return sos;
		   }
		  catch (SystemException e) {
		       logger.log(Level.ERROR, "SystemException occurred. " + e.getMessage()); 
		       throw new SalesOrderException(e);
	     }   
	  }
	 
      
      /**
       * Gathers the data necessary to display the Customer Sales Order Console
       * 
       * @throws ActionHandlerException
       */
      protected void recallCustomerSalesConsole() throws ActionHandlerException {
    	  // Borrow functionality from CustomerSearchAction class
    	  CustomerSearchAction action;
    	  try {
    		  action = new CustomerSearchAction(this.context, this.request);  
    		  action.processRequest(this.request, this.response, CustomerAction.COMMAND_PUBLICFETCH);
    		  return;
    	  }
    	  catch (Exception e) {
		       logger.log(Level.ERROR, e.getMessage()); 
		       throw new ActionHandlerException(e.getMessage());
    	  }
      }
      
      /**
       * Makes call to customer api to calculate and capture customer's balance.  The balance 
       * is added to the sales order member variable.   If sales orer varialbe is invalid it 
       * is instantiated.
       * 
       * @param custId Customer Id
       * @return {@link SalesOrder}
       * @throws ActionHandlerException
       */
      protected SalesOrder calcCustomerBalance(int custId) throws ActionHandlerException {
          SalesOrder salesOrder = null;
          try {
              salesOrder = SalesFactory.createSalesOrder(); 
              double balance = this.custApi.findCustomerBalance(custId);
              salesOrder.setOrderTotal(balance);
              return salesOrder;
          }
          catch (Exception e) {
              throw new ActionHandlerException(e.getMessage());
          }
      }

}
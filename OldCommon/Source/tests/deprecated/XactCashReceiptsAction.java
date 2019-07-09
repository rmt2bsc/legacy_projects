package com.action;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.bean.Customer;
import com.bean.CustomerWithName;
import com.bean.SalesOrder;
import com.bean.VwSalesorderItemsBySalesorder;
import com.bean.VwCustomerXactHist;
import com.bean.Xact;

import com.api.CashReceiptsApi;
import com.api.GLCustomerApi;
import com.api.InventoryApi;
import com.api.SalesOrderApi;
import com.api.XactManagerApi;

import com.constants.XactConst;

import com.factory.AcctManagerFactory;
import com.factory.InventoryFactory;
import com.factory.SalesFactory;
import com.factory.XactFactory;

import com.util.CustomerException;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.NotFoundException;
import com.util.XactException;
import com.util.SalesOrderException;
import com.util.GLAcctException;
import com.util.CashReceiptsException;

import com.constants.ItemConst;
   

/**
 * Concrete class that manages customer sales on account and cash receipts transactions only.   The functionality of this 
 * class is to serve the client's request to manage sales order transactions.   This includes the creation and viewing of salses orders 
 * and payment activities towards sales orders (Cash Receipts).
 * 
 * @author Roy Terrell
 *
 */
public class XactCashReceiptsAction extends AbstractXactJournalEntryAction {
     protected GLCustomerApi  custApi;
	 protected SalesOrderApi    salesApi;
	 protected CashReceiptsApi   crApi;
	 protected InventoryApi itemApi;
	
	/**
	 * Default constructor.
	 *
	 */
	 public XactCashReceiptsAction() {
	     super();
	 }  

	  /**
		* Main contructor for this action handler.
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	   public XactCashReceiptsAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
	       super(_context, _request);
		    this.salesApi = SalesFactory.createApi(this.dbConn, _request);
		    this.custApi = AcctManagerFactory.createCustomer(this.dbConn, _request);
		    this.itemApi = InventoryFactory.createInventoryApi(this.dbConn, _request);
		    this.crApi = SalesFactory.createCashReceiptsApi(this.dbConn, _request);
	   }
	
	  /**
	   * This method is used to initialize this object.   It is mandatory to invoke this 
	   * method after instantiating this object via the default constructor.
	   * 
	   * @throws SystemException  
	   */
	  public void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
	      super.init(_context, _request);
	      try {
	          this.salesApi = SalesFactory.createApi(this.dbConn);
	          this.api = XactFactory.create(this.dbConn);    
	      }
	 	   catch (DatabaseException e) {
	 	       throw new SystemException(e);
	 	   }
	      System.out.println("XactCashReceiptsAction.init(ServletContext, HttpServletRequest)");
	  }
	  
	  	  
	  /**
	   * Retrieves a list of transactions pertaining to a customer's account.
	   *  
	   * @throws XactException
	   */
	  public void getJournalEntryList() throws XactException {
    	  Customer cust = null;
		   try {
			   // Get Customer header information and store results in the the equest object for the client.
			   cust = this.setupCustomerHeaderData();
			  
			   // Get Customer's account transactions  and store results in the the equest object for the client.
			   this.getPaymentHist(cust);
		   }
		   catch (SalesOrderException e ) {
		       throw new XactException(e);
		   }
	  }
	  
	  /**
	   * Retrieves a customer's payment history using _cust.
	   * 
	   * @param _cust Customer object
	   * @return An array of all the customer's transactions
	   * @throws SalesOrderException
	   */
	  private VwCustomerXactHist[] getPaymentHist(Customer _cust) throws SalesOrderException {
		  VwCustomerXactHist cxh[] = null;
		  ArrayList list = null;
		  try {
			  list = this.api.findCustomerXactHist(_cust.getId());  
			  cxh = new VwCustomerXactHist[list.size()];
			  cxh = (VwCustomerXactHist[]) list.toArray(cxh);
			  return cxh;
		  }
		  catch (XactException e) {
			  System.out.println("[XactCashReceiptsAction.getPaymentHist] XactException occurred. " + e.getMessage());
		      throw new SalesOrderException(e);
		  }
		  finally {
			  this.request.setAttribute("PaymentHist", list);
		  }
	  }
	  
	  

	  /**
	   * This method is responsible for gathering customer data that is to be used as header information 
	   * when displaying cash receipts related pages for the client.
	   * <p>
	   * In order to use this method, the client is required to provide the customer id identified as "Id" or "CustomerId".   Otherwise, an
	   * SalesOrderException is thrown.    The processed data will be returned to the client via the HttpServletRequest object and 
	   * can be identified as follows:
	   * <p>
	   * <blockquote>
	   * "customer" - {@link CustomerWithName}
	   * "balance" - Double object representing the customer's current balance.
	   * </blockquote>
	   * 
	   * @return {@link Customer}
	   * @throws SalesOrderException
	   */
	  protected Customer setupCustomerHeaderData() throws SalesOrderException {
		  String custIdStr = null;
		  String msg = null;
		  int custId = 0;
		  Customer cust = null;
		  
		  try {
			  // Obtain Customer data from the request object.
			  try {
				  custIdStr = this.request.getParameter("Id");
				  if (custIdStr == null) {
				      custIdStr = this.request.getParameter("CustomerId");
				  }
				  custId = Integer.parseInt(custIdStr);  				  
			  }
			  catch (NumberFormatException e) {
				  msg = "NumberFormatException - Problem converting customer id to a number:  " + custIdStr;
				  System.out.println("[XactCashReceiptsAction.setupCustomerHeaderData] " + msg);
			      throw new SalesOrderException(e);
			  }
			    
			  cust = this.custApi.createCustomer();
			  cust.setId(custId);
			  CustomerWithName cwn = custApi.findCustomerWithName(custId);
			  if (cust == null) {
			      System.out.println("Customer is null");
			      return null;
			  }
			  
			  // Get Account balance
	    	  Double balance = this.getCustomerBalance(cust.getId());
	    	  SalesOrder so = null;
	    	  try {
	 	          so = SalesFactory.createSalesOrder();
	 	          so.setOrderTotal(balance.doubleValue());
	 	     }
	 	     catch (SystemException e) {
	 	          System.out.println("[XactCashReceiptsAction.getJournalEntryDetails] New sales order object  could not be created.");
	 	     }
			  
			  this.request.setAttribute("customer", cwn);
			  this.request.setAttribute("salesorder", so);
			  return cust;
		  } // end try
		  catch (GLAcctException e) {
		      System.out.println("[XactCashReceiptsAction.getXactList] GLAcctException occurred. " + e.getMessage());
		      throw new SalesOrderException(e);
		  }
		  catch (SystemException e) {
			  System.out.println("[XactCashReceiptsAction.getXactList] SystemException occurred. " + e.getMessage());
			  throw new SalesOrderException(e);
		  }
	  }
	  
	  

	  /**
	   * Obtins the customer balance.
	   * 
	   * @param _id The customer Id
	   * @return Double - The customer's current balance.
	   */
	  protected Double getCustomerBalance(int _id) {
	    	// Get Customer Balance
	    	Double balance = null;
	    	try {
	    	   balance = new Double(this.custApi.findCustomerBalance(_id));    
	    	}
	    	catch (CustomerException e) {
	    	    System.out.println("[XactCashReceiptsAction.getCustomerBalance] Customer balance could not be obtained...defaulting to zero.");
	    	    balance = new Double(0); 
	    	}
	    	return balance;
	  }  

	  /**
	   * Not applicable for this class. 
	   * 
	   * @throws XactException
	   */
	  public void getAvailJournalEntryItems() throws XactException {
		  return;
	  }
	
	  
	  /**
	   * This method is N/A
	   * 
	   * @throws XactException
	   */
	  public int getJournalEntryDetails() throws XactException {
	      return 1;
 	  }


	  /**
	   * Validates a cash receipt transaction.
	   */
	  public void validateJournalEntryDetails() throws XactException {
	      
	  }
	  
	  /**
	   * This method services the client's request to display the Customer Payment on Account, Sales Refund, or the
	   * Sales Transaction Reversal page for the client based on the client's submit action code (identified as "clientAction" 
	   * in the HttpServletReuest object).
	   * <p>.
	   * A transaction type of customer payment, sales returns (refund), or transaction reversal will be determined
	   * and set to the target JSP page that is to be presented on the client.   Since the target JSP is capable 
	   * processing all of the above transaction types, this helps the server-side process to  identify the type of 
	   * transaction it is to handle after the client submits the target JSP.  
	   * <p>
	   * Once the transaction type is determined, it is stored in and transimitted to the client via the HttpServletRequest 
	   * object as a String and can be identified as, "XactTypeId".
	   * 
	   * @throws XactException if the transactio type cannot be determined.
	   */
	   public void addXact() throws XactException {
	       int requestedXactTypeId = 0;
	       int xactId = 0;
	       Xact xact = null;
	       String msg = null;
	       Customer cust = null;
	       
 		    try {
 		        // Get Customer data
 		        cust = this.setupCustomerHeaderData();
 		        
 		        // Get Transaction data
 		        xactId = this.getXactIdFromClient();
 		        xact = this.api.findXactById(xactId);
 		        
 		        // Determine the requested transaction type
				  if (this.clientAction.equalsIgnoreCase(XactConst.CR_CUSTOMERPAYMENT)) {
                      requestedXactTypeId = XactConst.XACT_TYPE_CASHPAY;
				  }
				  if (this.clientAction.equalsIgnoreCase(XactConst.CR_ADDREVERSE)) {
                      requestedXactTypeId = XactConst.XACT_TYPE_REVERSE;
                      
                      // We can only reverse cash receipts (Customer Payments).
                      if (xact.getXactTypeId() != XactConst.XACT_TYPE_CASHPAY) {
                    	  msg = "Only cash receipts transactions can be reversed from the Customer Transaction History page";
    				      System.out.println("[XactCashReceiptsAction.addXact] " + msg);
    				      this.transObj.rollbackTrans();
    				      throw new XactException(msg);
                      }
				  }
				   
				  // Throw exception if transaction type could not be figured out.
				  if (requestedXactTypeId == 0) {
				      msg = "Problem setting the transaction type for cash receipt transaction since the client's submit action could not be identified";
				      System.out.println("[XactCashReceiptsAction.addXact] " + msg);
				      throw new XactException(msg);
				  }
				  
				  // Set the transaction type in the request object.
				  this.request.setAttribute("XactTypeId", String.valueOf(requestedXactTypeId));
				  this.request.setAttribute("xact", xact);
				  return;
 		    }
			 catch (SalesOrderException e) {
			     System.out.println("[XactCashReceiptsAction.addXact] SalesOrderException occurred. " + e.getMessage()); 
			     throw new XactException(e);
			 }
			 catch (DatabaseException e) {
				 System.out.println("[XactCashReceiptsAction.addXact] DatabaseException occurred. " + e.getMessage()); 
			     throw new XactException(e);
			 }
	   }
	   

	     /**
	      * This method is responsible for setting the confirmation number of the transaction and negating the transaction amount
	      * 
	      * @deprecated
	      */
       protected void preSaveXact() {
           double amount = 0;
           
           if (this.xactBean == null) {
               return;
           }
           if (this.xactTypeBean == null) {
               return;
           }
           
           // Negate cutomer's transaction amount since this is a cash receipt transaction.   Will apply for the reversal of cash receips as well.
           amount = this.xactBean.getXactAmount();
           amount *= -1;
           this.xactBean.setXactAmount(amount);    
           
           // Set Confirmation number.
           String confirmNo =  String.valueOf(new java.util.Date().getTime());
           this.xactBean.setConfirmNo(confirmNo);
           return;
       }
       

       /**
        * Creates a xact manager api using the {@link SalesOrderManagerApiImpl} implementation.
        */
       protected XactManagerApi getCustomXactApi() {
              XactManagerApi xactApi = SalesFactory.createBaseXactApi(this.dbConn, this.request);
              return xactApi;
       }
       
	  /**
	   * Creates cash receipts transaction journal entries.    This method will perform one of the following transaction based on value 
	   * of the client's "requestType":  sales order refund, payment on account reversal, or  payment on account.  
       * <p>
       * Since this class is designed for s specific transaction type, the processing of credit sales, cash sales, sales returns and 
       * allowances, refunds, and sales reversal transactions are managed via this method. 
	   *
	   * @return int - the transaction id. 
	   * @throws XactException
	   * @throws DatabaseException
	   * 
	   */
		public int saveXact() throws XactException, DatabaseException {
			String msg = null;
    	    int custId = 0;
    	    int xactId =0;
            Xact xact = null;
    	    CustomerWithName cwn = null;
    	   
    	    // Get Customer Id
    	    try {
    	        custId = this.custApi.getHttpCustomerIdentifier();    
    	    }
    	    catch (NotFoundException e) {
    		    throw new XactException(e);
    	    }
    	    catch (SystemException e) {
    		    throw new XactException(e);
    	    }
    
    	    try {
        	    try {
        	    	// Create base transaction
        	    	System.out.println("********  Begin Saving Cash Receipts transaction  ********");
        		    xact = this.getHttpXactBase();
        		    xactId = this.crApi.maintainCustomerPayment(xact, custId); 
        		    xact = this.api.findXactById(xactId);
        		    cwn = this.custApi.findCustomerWithName(custId);
        		   
        			// Get Account balance
        	    	Double balance = this.getCustomerBalance(custId);
        	    	SalesOrder so = null;
        	    	try {
        	 	       so = SalesFactory.createSalesOrder();
                       so.setOrderTotal(balance.doubleValue());
        	 	    }
        	 	   catch (SystemException e) {
        	 	       System.out.println("[XactCashReceiptsAction.saveXact] New sales order object  could not be created.");
        	 	   }
        		     
        	 	   //  Get customer payment history
        	 	   this.custApi.setBaseView("CustomerView");
        	 	   this.custApi.setBaseClass("com.bean.Customer");
        	 	   Customer cust = this.custApi.findCustomerById(custId);
        	 	   this.getPaymentHist(cust);
        		 	  
        	 	   // Get transaction data
        		   this.transObj.commitTrans();
        		   
        		   this.request.setAttribute("customer", cwn);
        		   this.request.setAttribute("salesorder", so);
        		   this.request.setAttribute("xact", xact);
                   System.out.println("********  Saving Cash Receipts transaction successful  ********");
                   return xactId;
        	   }
        	  catch (DatabaseException e) {
        		  msg = e.getMessage();
        		  System.out.println("[XactCashReceiptsAction.saveXact] DatabaseException -  " + msg);
       			  this.transObj.rollbackTrans();  
        		  throw new XactException(msg);  
        	  } 
        	  catch (GLAcctException e) {
        	      System.out.println("[XactCashReceiptsAction.saveXact] GLAcctException -  " + e.getMessage());
                  this.transObj.rollbackTrans();  
        	      throw new XactException(e);
        	  }
        	  catch (SalesOrderException e) {
                  this.transObj.rollbackTrans();  
        	      throw new XactException(e);
        	  }
              catch (XactException e) {
                  this.transObj.rollbackTrans();  
                  throw e;
              }
              catch (CashReceiptsException e) {
                  this.transObj.rollbackTrans();  
                  throw new XactException(e);
              }
    	   } // end outer try
    	   catch (DatabaseException e) {
     		  msg = e.getMessage();
     		  System.out.println("[XactCashReceiptsAction.saveXact] DatabaseException -  " + msg);
     		  throw new XactException(msg);  
     	   } 
		}

		
		/**
		 * 
		 * @param _custId
		 * @param _xactId
		 * @param _amount
		 * @return
		 * @throws XactException
		 * @deprecated
		 */
	   protected int creditAccount(int _custId, int _xactId, double _amount) throws XactException {
	      this.api.createCustomerActivity(_custId, _xactId, _amount);
		   return 1;
	   }		
		
	   /**
		 * Reverses a cash receipt transaction.
		 *  
		 * @return int - the transaction id. 
		 * @throws XactException
		 * @throws DatabaseException
		 * 
		 */
		  public int reverseXact() throws XactException, DatabaseException {
               String msg = null;
               int custId = 0;
               int xactId =0;
               Xact xact = null;
               CustomerWithName cwn = null;
               
               System.out.println("******* Begin Reversing Cash Receipts transaction **********");
               
               // Get Customer Id
               try {
                   custId = this.custApi.getHttpCustomerIdentifier();    
               }
               catch (NotFoundException e) {
                    throw new XactException(e);
               }
               catch (SystemException e) {
                    throw new XactException(e);
               }
        
               xact = this.getXactObjectFromPage();
               try {
                   try {
                	   xactId = this.crApi.maintainCustomerPayment(xact, custId);
                	   xact = this.api.findXactById(xactId);
                       cwn = this.custApi.findCustomerWithName(custId);
                       
                        // Get Account balance
                        Double balance = this.getCustomerBalance(custId);
                        SalesOrder so = null;
                        try {
                           so = SalesFactory.createSalesOrder();
                           so.setOrderTotal(balance.doubleValue());
                        }
                       catch (SystemException e) {
                           System.out.println("[XactCashReceiptsAction.saveXact] New sales order object  could not be created.");
                       }
                         
                       //  Get customer payment history
                       this.custApi.setBaseView("CustomerView");
                       this.custApi.setBaseClass("com.bean.Customer");
                       Customer cust = this.custApi.findCustomerById(custId);
                       this.getPaymentHist(cust);
                          
                       // Get transaction data
                       this.transObj.commitTrans();
                       
                       this.request.setAttribute("customer", cwn);
                       this.request.setAttribute("salesorder", so);
                       this.request.setAttribute("xact", xact);
                       System.out.println("******  Reversing Cash Receipts transaction successful *******");
                       return xactId;
                   }
                  catch (DatabaseException e) {
                      msg = e.getMessage();
                      System.out.println("[XactCashReceiptsAction.reverseXact] DatabaseException -  " + msg);
                      this.transObj.rollbackTrans();  
                      throw new XactException(msg);  
                  } // end try
                  
                  catch (GLAcctException e) {
                      System.out.println("[XactCashReceiptsAction.reverseXact] GLAcctException -  " + e.getMessage());
                      this.transObj.rollbackTrans();  
                      throw new XactException(e);
                  }
                  catch (SalesOrderException e) {
                	  this.transObj.rollbackTrans();  
                      throw new XactException(e);
                  }
                  catch (CashReceiptsException e) {
                	  this.transObj.rollbackTrans();  
                      throw new XactException(e);
                  }            	   
               }
               catch (DatabaseException e) {
                   msg = e.getMessage();
                   System.out.println("[XactCashReceiptsAction.reverseXact] DatabaseException -  " + msg);
                   throw new XactException(msg);  
               } // end try
 		  }
	 
            
		  /**
		   * Helper inner class that is used to manage Sales Order details.
		   * 
		   * @author Roy Terrell
		   *
		   */
		  class CombinedSalesOrderWorker {
		      private Customer cust;
		      private SalesOrder so;
		      private Xact xact;
		      private ArrayList items;
 			   private ArrayList srvcItems;
		      private ArrayList merchItems;
		      
		      /**
		       * Default constructor
		       */
		      public CombinedSalesOrderWorker() {
		          this.srvcItems = new ArrayList();
		    	    this.merchItems = new ArrayList();
		          return;
		      }

		      /**
		       * Groups each sales order item contained in soi into two separate ArrayLists designated for services items 
		       * and merchandise items.  If soi is null, then the routine is aborted.
		       *   
		       * @param soi
		       */
            public void packageSalesOrderItemsByTypes(VwSalesorderItemsBySalesorder soi[]) {
                if (soi == null) {
            		  return;
            	  }
            	  
                 // Package items in separate ArrayList collections based on theit item types
	    		     for (int ndx = 0; ndx < soi.length; ndx++) {
	    		         switch (soi[ndx].getItemTypeId()) {
		               case ItemConst.ITEM_TYPE_SRVC:
		                  this.srvcItems.add(soi[ndx]);
		                  break;
		               case ItemConst.ITEM_TYPE_MERCH:
		                  this.merchItems.add(soi[ndx]);
		                  break;
	    		         } // end switch
	    		     } // end for  
            }
            
            /**
             * Gets the customer object.
             * @return {@link Customer}
             */
			   public Customer getCustomer() {
			       return this.cust;
		      }
			   /**
			    * Sets the customer member variable to value.
			    * @param value
			    */
		      public void setCustomer(Customer value) {
		          this.cust = value;
		      }
		      
		      /**
		       * Sets transaction object.
		       * 
		       * @param value
		       */
		      public void setXact(Xact value) {
		          this.xact = value;
		      }
		      
		      /**
		       * Gets transaction object.
		       * 
		       * @return
		       */
		      public Xact getXact() {
		          return this.xact;
		      }
		      
		      /**
		       * Gets Sales Order object.
		       * 
		       * @return {@link SalesOrder}
		       */
		      public SalesOrder getSalesOrder() {
		          return this.so;
		      }
		      /**
		       * Sets sales order member variable to value.
		       * @param value
		       */
		      public void setSalesOrder(SalesOrder value) {
		          this.so = value;
		      }
		      /**
		       * Gets service items
		       * @return ArrayList
		       */
		      public ArrayList getSrvcItems() {
		    	  return this.srvcItems;
		      }
		      /**
		       * Gets merchandise items
		       * @return
		       */
		      public ArrayList getMerchItems() {
		    	  return this.merchItems;
		      }
		      /**
		       * Gets all items.
		       * 
		       * @return ArrayList
		       */
		      public ArrayList getItems() {
		          return this.items;
		      }
		      /**
		       * Sets variable that represents all items to value.
		       * 
		       * @param value
		       */
		      public void setItems(ArrayList value) {
		          this.items = value;
		      }
		  } // end inner class pageData
		  
		  
	} // end AbstractXactJournalEntryAction
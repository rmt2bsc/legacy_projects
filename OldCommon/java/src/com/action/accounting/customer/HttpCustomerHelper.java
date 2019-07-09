package com.action.accounting.customer;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.bean.Customer;

import com.factory.AcctManagerFactory;

import com.action.ActionHandlerException;
import com.action.HttpBasicHelper;
import com.api.db.DatabaseException;

import com.util.GLAcctException;
import com.util.SystemException;
import com.util.XactException;



/**
 * This abstract class provides functionality obtaining basic Customer relateted 
 * data from the client's request object
 * 
 * @author Roy Terrell
 *
 */ 
public class HttpCustomerHelper extends HttpBasicHelper {
      private String custType;
      private Customer customer;
      private Logger logger;
	
	  /**
	   * Default constructor
	   *
	   */
	  public HttpCustomerHelper()  {
		  super(); 
		  logger = Logger.getLogger("HttpCustomerHelper");
	  }
	  
	  /**
		* Main contructor for this action handler.  
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	  public HttpCustomerHelper(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
		  super(_context, _request);
          logger = Logger.getLogger(HttpCustomerHelper.class);
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
          // Create base transaction API object.
	      this.customer = null;
	  }

      
      
		/**
		 * Combines the process of probing the client's HttpServletRequest object for basic transaction 
         * data (transaction base, transaction type, transaction category, and transaction detail items).    
		 * After invoking this method, be sure to call the methods, getXact, getXactType, getXactCatg, 
         * and getXactItems to obtain the results.  
		 * 
		 * @return Transaction object.
		 * @throws XactException
		 */
	  public void getHttpCombined() throws SystemException {
		  super.getHttpCombined();
		  try {
			  this.customer = this.getHttpCustomer();
			  this.custType = this.getHttpCustomerType();
		  }
		  catch (GLAcctException e) {
			  throw new SystemException(e.getMessage());
		  }
		  return;
	  }
	  
	  
      
      /**
       * Locates customer data by querying the clinet's request object.   <p>The data can be chosen from a list of 
       * customers or from a single customer record.   If customer cannot be created using request data, then a 
       * new Customer object is created.
       * <p>
	   * <p>
	   * It is required that the input control representing the customer id is named, "CustomerId", regardless if it exist within a list of 
	   * customers or as a single customer.  In order for the target customer to be retrieved from a list, the user interface 
	   * is required to be setup in such a way where the customer id input control is uniquely named.   A checkbox or radio button 
	   * should exist to contain the row id, and its name should be labeled as "selCbx".   The unique naming of the customer id control 
	   * should follow the following format: ("CustomerId" + row id).   Optionally, the customer id control can be named ("CustId" + row id) 
	   * when processing a list of customers.
	   * 
       * @return {@link Customer}
       * @throws GLAcctException
       */
      public Customer getHttpCustomer() throws GLAcctException {
    	   Customer cust = AcctManagerFactory.createCustomer();
           String temp = null;
           int custId = 0;
           boolean listData = true;
           String subMsg = null;
          
          // Determine if we are coming from a page that presents data as a list of orders or as single order.
          // Get selected row number from client page.  If this row number exist, then we 
          // are coming from page that contains a list of orders.
          temp = this.request.getParameter("CustomerId" + this.selectedRow);
          if (temp == null) {
        	  listData = false;
          }
          
          if (!listData) {
        	  temp = this.request.getParameter("CustomerId");
        	  if (temp == null) {
        		  temp = this.request.getParameter("CustId");    
        	  }
          }
          // validate value
          try {
        	  custId = Integer.parseInt(temp);    
          }
          catch (NumberFormatException e) {
        	  logger.log(Level.INFO, "Customer id could not be identified from client");
        	  this.customer = cust;
        	  return cust;
          }  
                     
           // Get Customer object
           try {
        	   AcctManagerFactory.packageBean(this.request, cust);
               this.customer = cust;
               this.customer.setId(custId);
               return cust;
           }
           catch (SystemException e) {
        	   subMsg = "Problem packaging new Customer object from request";
               logger.log(Level.ERROR, subMsg);
               throw new GLAcctException(subMsg);
           }
      }
      
      /**
       * Obtains the value of cusstomer type from the client's JSP page.   This method expects 
       * the name of the control that houses the customer type value to be either "CustomerType" 
       * or "qry_CustomerType".
       * 
       * @return The value of Customer Type
       */
      public String getHttpCustomerType() {
          this.custType = this.request.getParameter("qry_CustomerType");
          if (this.custType != null) {
              return this.custType;
          }
    	  this.custType = this.request.getParameter("CustomerType");
    	  return this.custType;
      }
     
      
      /**
       * Gets Customer object.
       * 
       * @return
       */
      public Customer getCustomer() {
          return this.customer;
      }
      
      /**
       * Get Customer Type
       * 
       * @return String
       */
      public String getCustType() {
    	  return this.custType;
      }
}
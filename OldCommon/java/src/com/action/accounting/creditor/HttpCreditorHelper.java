package com.action.accounting.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.bean.Creditor;

import com.factory.AcctManagerFactory;

import com.action.HttpBasicHelper;
import com.api.db.DatabaseException;

import com.util.SystemException;


/**
 * This class provides functionality for obtaining Creditor related data from 
 * the client's request object.
 * 
 * @author Roy Terrell
 *
 */ 
public class HttpCreditorHelper extends HttpBasicHelper {
      private Creditor creditor;
      private Logger logger;
	  
	  /**
	   * Default constructor
	   *
	   */
	  public HttpCreditorHelper()  {
		  super(); 
		  logger = Logger.getLogger("HttpCreditorHelper");
	  }
	  
	  /**
		* Main contructor for this action handler.  
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	  public HttpCreditorHelper(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
		  super(_context, _request);
          logger = Logger.getLogger(HttpCreditorHelper.class);
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
	      this.creditor = null;
	  }

      
      
		/**
		 * Combines the process of probing the client's HttpServletRequest object for creditor 
         * data.  After invoking this method, be sure to call the appropriate getter methods 
         * to obtain the results.  
		 * 
		 * @return Transaction object.
		 * @throws XactException
		 */
	  public void getHttpCombined() throws SystemException {
		  super.getHttpCombined();
		  this.creditor = this.getHttpCreditor();
		  return;
	  }
	  
	  
	  /**
	   * Attempts to obtain basic data pertaining to a creditor from the client JSP.
	   *  
	   * @return {@link Creditor} containing the data retrieved from the client or a 
	   *  newly initialized Creditor object when the creditor id is not found.
	   * @throws SalesOrderException
	   */
	  public Creditor getHttpCreditor() throws SystemException {
	      Creditor obj = AcctManagerFactory.createCreditor();
		   String temp = null;
		   String subMsg = null;
		   boolean listPage = true;
           int creditorId = 0;
	      
		   // Determine if we are coming from a page that presents data as a list or as a single record.
		   // Get selected row number from client page.  If this row number exist, then we 
	       // are coming from page that contains lists of records.
		   subMsg = "Creditor id could not be identified from client";
		   temp = this.request.getParameter("CreditorId" + this.selectedRow);
		   if (temp == null) {
		       listPage = false;
		   }
		   if (!listPage) {
		       // Get order id for single row
		       temp = this.request.getParameter("CreditorId");
           }
		   
		   // Validate value
           try {
               creditorId = Integer.parseInt(temp);
           }
		   catch (NumberFormatException e) {
		       logger.log(Level.INFO, subMsg);
               this.creditor = obj;
		       return obj;
		   }
		   
		   // Get Transaction object
		   try {
			   AcctManagerFactory.packageBean(this.request, obj);
               this.creditor = obj;
               this.creditor.setId(creditorId);
               return obj;
		   }
		   catch (SystemException e) {
			   logger.log(Level.ERROR,  e.getMessage());
		       throw e;
		   }
	  }

      
      /**
       * Gets transaction object.
       * 
       * @return
       */
      public Creditor getCreditor() {
          return this.creditor;
      }
   
}
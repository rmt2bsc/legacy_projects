package com.action.xact.purchases;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.action.ActionHandlerException;
import com.action.xact.HttpXactHelper;

import com.api.GLCreditorApi;
import com.api.XactManagerApi;
import com.api.db.DatabaseException;

import com.bean.VwCreditorBusiness;
import com.bean.VwXactList;
import com.constants.XactConst;

import com.factory.AcctManagerFactory;
import com.factory.XactFactory;

import com.util.SalesOrderException;
import com.util.SystemException;
import com.util.XactException;



/**
 * This abstract class provides functionality obtaining basic transacction related data from the client's request object
 * 
 * @author Roy Terrell
 *
 */ 
public class HttpCreditorPurchaseHelper extends HttpXactHelper {

	  private XactManagerApi api;
      private VwXactList xactExt;
      private VwCreditorBusiness creditor;
      private Logger logger;
	  
      
	
	  /**
	   * Default constructor
	   *
	   */
	  public HttpCreditorPurchaseHelper()  {
		  super(); 
		  logger = Logger.getLogger("HttpCreditorPurchaseHelper");
	  }
	  
	  /**
		* Main contructor for this action handler.  
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	  public HttpCreditorPurchaseHelper(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
		  super(_context, _request);
          logger = Logger.getLogger(HttpCreditorPurchaseHelper.class);
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
              this.api = XactFactory.create(this.dbConn, _request);    
          }
          catch (DatabaseException e) {
              logger.log(Level.ERROR, e.getMessage()); 
              throw new SystemException(e.getMessage());
          }
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
			  this.xactExt = this.getHttpXactExt();
			  this.creditor = this.getHttpCreditor();
		  }
		  catch (XactException e) {
			  throw new SystemException(e.getMessage());
		  }
		  return;
	  }
	  
	  /**
	   * Attempts to obtain extended transaction data from the client JSP into a transaction object.
	   *  
	   * @return {@link VwXactList}- Contains the transaction data retrieved from the client or a 
	   *  newly initialized VwXactList object when transaction is not found.
	   * @throws SalesOrderException
	   */
	  public VwXactList getHttpXactExt() throws XactException {
		  VwXactList xact = XactFactory.createXactView();
		   String temp = null;
		   String subMsg = null;
		   int xactId = 0;
		   boolean listPage = true;
	      
		   // Determine if we are coming from a page that presents data as a list of orders or as single order.
		   // Get selected row number from client page.  If this row number exist, then we 
	       // are coming from page that contains a list of orders.
		   subMsg = "Transaction number could not be obtained from list of Credit charge orders";
		   temp = this.request.getParameter("XactId" + this.selecteRow);
		   if (temp == null) {
		       listPage = false;
		   }
		   if (!listPage) {
		       // Get order id for single row
		       temp = this.request.getParameter("XactId");
           }
           try {
               xactId = Integer.parseInt(temp);
           }
		   catch (NumberFormatException e) {
		       logger.log(Level.INFO, subMsg);
               this.xactExt = xact;
		       return xact;
		   }
		   
		   // Get Transaction object
		   try {
               XactFactory.packageBean(this.request, xact);
               xact.setId(xactId);
               this.xactExt = xact;
               return xact;
		   }
		   catch (SystemException e) {
			   logger.log(Level.ERROR,  e.getMessage());
		       throw new XactException(e);
		   }
	  }

	  
	    /**
       * This method is responsible for gathering creditor data from the client's request object.
       * <p>
       * In order to use this method, the client is required to provide the creditor id identified as "Id" or "CreditorId".   Otherwise, an
       * SalesOrderException is thrown.    The processed data will be returned to the client via the HttpServletRequest object and 
       * can be identified as follows:
       * <p>
       * <blockquote>
       * "customer" - {@link VwCreditorBusiness}
       * "balance" - Double object representing the customer's current balance.
       * </blockquote>
       * 
       * @return {@link VwCreditorBusiness}
       * @throws XactException
       */
      public VwCreditorBusiness getHttpCreditor() throws XactException {
          String credIdStr = null;
          String msg = null;
          String criteria = null;
          int credId = 0;
          boolean listData = true;
          VwCreditorBusiness cb = null;
          List list = null;
          GLCreditorApi api = null;
          
          try {
              // Obtain Creditor data from the request object.
              try {
                  // Get selected row number from client page.  If this row number exist, then we 
                  // are coming from page that contains a list of orders.
            	  credIdStr = this.request.getParameter("CreditorId" + this.selecteRow);
                   if (credIdStr == null) {
                   	listData = false;
                   }
                   else {
                	   credId = Integer.parseInt(credIdStr);	
                   }
              }
              catch (NumberFormatException e) {
                  msg = "NumberFormatException - Problem converting creditor id to a number from list of creditors:  " + credIdStr;
                  logger.log(Level.ERROR, msg);
                  throw new XactException(e);
              }
              
              try {
            	  if (!listData) {
                      credIdStr = this.request.getParameter("Id");
                      if (credIdStr == null) {
                          credIdStr = this.request.getParameter("CreditorId");
                      }
                      credId = Integer.parseInt(credIdStr);                               		  
            	  }
              }
              catch (NumberFormatException e) {
                  msg = "NumberFormatException - Problem converting creditor id to a number:  " + credIdStr;
                  logger.log(Level.ERROR, msg);
                  throw new XactException(e);
              }
              
              api = AcctManagerFactory.createCreditor(this.dbConn, this.request);
              criteria = "creditor_id = " + credId;
              logger.log(Level.DEBUG,  "Criteria to obtain creditor profile: " + criteria);
              list = api.findCreditorBusiness(criteria);
              if (list.size() > 0) {
                  cb = (VwCreditorBusiness) list.get(0);
              }
              if (cb == null) {
                  logger.log(Level.DEBUG,  "Creditor object is null");
                  return null;
              }
              return cb;
          } // end try
          catch (Exception e) {
              logger.log(Level.ERROR,  "GLAcctException occurred. " + e.getMessage());
              throw new XactException(e);
          }
      }

      
  	/**
  	 * This method retrieves credit purchase order data from an external datasource.  It is 
  	 * assumed that the transaction object and the creditor object are valid and accounted 
  	 * for.  Otherwise, this operation bypasses the retrieval.
  	 * 
  	 * @throws ActionHandlerException General database error.
  	 */
  	public void retrieveCreditOrder() throws ActionHandlerException {
  		if (this.xactExt != null && this.creditor != null) {
  			this.retrieveCreditOrder(this.xactExt.getId(), this.creditor.getCreditorId());
  		}
  		return;
  	}
  	
  	  /**
  	   * Uses _xactId and _creditorId to retrieve credit purchase order data from the 
  	   * database.   After the data is retrieved, this method packages the data into 
  	   * various objects which are sent to the client via HttpServletRequest object for 
  	   * presentation.
  	   * 
  	   * @param _xactId The id of the target transactin of credit order.
  	   * @param _creditorId The id of the target creditor
  	   * @throws ActionHandlerException
  	   */
  	  public void retrieveCreditOrder(int _xactId, int _creditorId) throws ActionHandlerException {
  	    List creditors = null;
  	      GLCreditorApi credApi;
  	      
  	      try {
  	    	  // Get vendor data.  Expect to receive a vendor id at all times since purchase orders created per vendor.
  	    	  credApi = AcctManagerFactory.createCreditor(this.dbConn, this.request);
  	    	  creditors = credApi.findCreditorBusiness("creditor_id = " + _creditorId);
  	    	  if (creditors.size() > 0) {
  	    		  this.creditor = (VwCreditorBusiness) creditors.get(0);
  	    	  }
  	    	  else {
  	    		  this.msg = "Creditor could not be found using id: " + _creditorId;
  	    		  logger.log(Level.ERROR, this.msg);
  	    		  throw new ActionHandlerException(this.msg);
  	    	  }
  	    	  
  	    	 // Refresh existing transaction objects from the database only.
              if (this.xact != null && this.xact.getId() > 0) {
                  this.xact = this.api.findXactById(_xactId);
                  this.xactExt = this.api.findXactListViewByXactId(_xactId);
                  // Get Transaction Items
                  this.xactItems = this.api.findVwXactTypeItemActivityByXactId(_xactId);
              }
  	          if (this.xact == null) {
  	    		  // This might be a new credit purchase order request, so ensure that all objects 
  	    		  // are created and initialized to prevent null exceptions on the client.
  	    		  this.createNewCreditPurchase();
  	    		  return;
  	    	  }
      	  }
  	      catch (Exception e) {
  	    	  logger.log(Level.ERROR, e.getMessage());
  	          throw new ActionHandlerException(e);
  	      }
  	      return;
  	  }
      
      
  	/**
  	   * 
  	   * @throws ActionHandlerException
  	   */
  	  private void createNewCreditPurchase() throws ActionHandlerException  {
          this.xact = XactFactory.createXact();
  	   	  this.xactExt = XactFactory.createXactView();  
  		  this.xactItems = new ArrayList();
  	      this.xactType = XactFactory.createXactType();
  	      this.xactType.setId(XactConst.XACT_TYPE_CREDITCHARGE);
  	      this.xactCategory = XactFactory.createXactCategory();
  	      return;
  	  }
      
      /**
       * Gets transaction object.
       * 
       * @return
       */
      public VwXactList getXactExt() {
          return this.xactExt;
      }

    /**
     * @return the creditor
     */
    public VwCreditorBusiness getCreditor() {
        return creditor;
    }
      

}
package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Hashtable;

import com.api.XactManagerApi;

import com.bean.XactType;
import com.bean.XactQuery;

import com.constants.XactConst;

import com.factory.XactFactory;

import com.action.RMT2ServletActionHandler;

import com.servlet.RMT2PollingAbstractServlet;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.XactException;
import com.util.RMT2Utility;


/**
 * This class provides action handlers needed to spawn multiple processes to gather input data, build selection criteria, execute the database query, 
 * and send the results back to the client pertain to Transaction Searches.
 * 
 * @author Roy Terrell
 */
 public class XactQueryPollingAction extends RMT2PollingActionHandler {

  private XactManagerApi api;
  private final String PAGE_TITLE = "Transaction Action";
  private Hashtable errors = new Hashtable();
  private XactQuery criteria;
  boolean isCustomerLookup;
  boolean isCreditorLookup;
  int xactCatgId;
  StringBuffer sql;
  StringBuffer orderBy;
  ArrayList list;


  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @param _servlet  A reference to the calling servlet controller.
	* @throws SystemException
	*/
  public XactQueryPollingAction(ServletContext _context, HttpServletRequest _request, RMT2PollingAbstractServlet _servlet) throws SystemException, DatabaseException {
      super(_context, _request, _servlet);
      double amount = 0;
      int  xactTypeId = 0;
      this.className = "XactAction";
      api = XactFactory.create(this.dbConn, xactTypeId, amount);
      this.isCustomerLookup = false;
      this.isCreditorLookup = false;
      this.sql = new StringBuffer(100);
      this.orderBy = new StringBuffer(100);
      this.successPagePath = "/forms/xact/";
      this.successPage = "XactList.jsp";
      this.pollService = RMT2Utility.getWebAppContext(this.request) + "/xactservlet";   
  }

  /**
   * The implementation for this method gathers all input from the request object for a transaction search.  Afterwards, the next thread is 
   * awaken to continue the process.  If this process fails the next thread will not continue.
   * 
   * @return int - Returns 1= success.   Otherwise, -1=failure.
   */
  protected synchronized int getInputData() {
  	System.out.println("getInputData Thread has started");
    try {
    	this.criteria = new XactQuery();
    	XactFactory.packageBean(this.request, this.criteria);
        this.xactCatgId = Integer.parseInt(this.request.getParameter("XactCatgId"));
    	this.isInputDataOk = true;
    	System.out.println("getInputData Thread has successfully completed");
    	notify();
    	return RMT2ServletActionHandler.RC_SUCCESS;
    }
    catch (SystemException e) {
    	this.isInputDataOk = false;
    	System.out.println("getInputData Thread has problems");
    	return RMT2ServletActionHandler.RC_FAILURE;
    }
  	
  }
  
  /**
   * The implementation for this method builds the selection criteria for a transaction search based on the data gathered from getInputData().  
   * Afterwards, the next thread is awaken to continue the process.  If this process fails the next thread will not continue.
   * 
   * @return int - Returns 1= success.   Otherwise, -1=failure.
   */
  protected synchronized int buildCriteria()  {
      System.out.println("buildCriteria Thread has started");
      System.out.println("buildCriteria Thread has been notified");
	   try {
	       String results = this.buildSelectionCriteria();
	       System.out.println("Criteria: " + results);
	   }
	   catch (SystemException e) {
	       System.out.println("buildCriteria Thread has problems");
	       this.isCriteriaOk = false;
	       return XactQueryPollingAction.RC_FAILURE;
	   }
       finally {
           
       }
       
	   this.isCriteriaOk = true;
	   System.out.println("BuildCriteria Thread has successfully completed");
	   notify();
  	   return RMT2ServletActionHandler.RC_SUCCESS;
  }
  
  /**
   * The implementation performs a database search for the transaction(s).   The datasource view that is to be used is programmatically selected
   * by determining wheter or not the search is customer, creditor, or generically based.  Afterwards, the next thread is awaken to continue the process.
   * If this process fails the next thread will not continue.
   * 
   * @return int - Returns 1= success.   Otherwise, -1=failure.
   */
  protected synchronized int execQuery() {
       System.out.println("execQuery Thread has started");
  	   System.out.println("execQuery Thread has been notified");
	   // Determine which view to use for transaction lookup
	   if (this.isCreditorLookup) {
	       this.baseView = "VwCreditorXactHistView";
	       this.baseClass = "com.bean.VwCreditorXactHist";
	   }
	   if (this.isCustomerLookup) {
	       this.baseView = "VwCustomerXactHistView";
	       this.baseClass = "com.bean.VwCustomerXactHist";    	
	   }
	   if(!this.isCustomerLookup && !this.isCreditorLookup) {
	       this.baseView = "VwXactListView";
	       this.baseClass = "com.bean.VwXactList";
	   }
	  
	  // Set api's base class and base view
	  api.setBaseView(this.baseView);
	  api.setBaseClass(this.baseClass);
	  
	  // Retrieve Data
	  System.out.println("[XactAction.execQuery] Using " + this.baseView + " to search for transactions");
	  try {
	      this.list = api.findXact(this.sql.toString(), this.orderBy.toString());
	  }
	  catch (XactException e) {
	      System.out.println("[XactAction.execQuery] execQuery Thread has problems");
	      System.out.println("[XactAction.execQuery] " + e.getMessage());
	      this.errorMsg = e.getMessage();
	      this.isOutputDataOk = false;
	      this.request.setAttribute("error", this.errorMsg);
	      return XactQueryPollingAction.RC_FAILURE;
	  }
	  this.isOutputDataOk = true;
	  System.out.println("execQuery Thread has successfully completed");
	  notify();
  	
  	  return RMT2ServletActionHandler.RC_SUCCESS;
  }
  
  /**
   * This implementation gathers the results of the process repsonsible for executing the query and sending the packaged results to the controller servlet along 
   * with the polling key.
   * 
   * @return int - Always returns 1= success.
   */
  protected synchronized int sendReply() {
      System.out.println("sendReply Thread has started");
      System.out.println("sendReply Thread has been notified");
  	   
  	   // Send data back to the client
  	   if (isError) { 
  	       this.request.setAttribute("error", XactConst.XACT_POLL_ERROR);
  	   }
      this.request.setAttribute("data", this.list);    
      this.request.setAttribute("pollKey", this.pollKey);
      this.request.setAttribute("pollService", this.pollService);
      this.request.setAttribute("successPagePath", this.successPagePath);
      this.request.setAttribute("successPage", this.successPage);
      Object obj = this.servlet.setPollKey(this.pollKey, this.list);
    
      System.out.println("sendReply Thread has successfully completed");
      if (obj == null) {
          System.out.println("sendReply Thread previous value for poll key, " + this.pollKey + ", was null");
      }
      else {
          System.out.println("sendReply Thread previous value for poll key, " + this.pollKey + ", was not null");
          System.out.println("Client Request has completed.");
      }
      return RMT2ServletActionHandler.RC_SUCCESS;
  }
  
  /**
   * Builds the SQL selection criteria based off of the use's input from the request object.   Returns the where clause selection criteria in the 
   * form of a string.
   *  
   * @return String - Selection Criteria
   * @throws SystemException
   */
  private String buildSelectionCriteria() throws SystemException {
      
      //    Add Transacton Type Id criteria
      if (this.criteria.getXactCatgId() > 0) {
          this.sql.append("xact_category_id in (");
          this.sql.append(XactConst.CATG_REVERSE);
          this.sql.append(", ");
   	  	  this.sql.append(this.criteria.getXactCatgId());
          this.sql.append(") ");
      }
     
	   //  Add Transacton Type Id criteria
	   if (this.criteria.getXactTypeId() > 0) {
	       if (this.sql.length() > 0) {
	           this.sql.append(" and ");
		  	 }
	  	    this.sql.append("xact_type_item_xact_type_id = ");
	  	    this.sql.append(this.criteria.getXactTypeId());
	   }
       
	   //  Add transaction date #1 criteria
	   if (!this.criteria.getXactDate1().equals("") && !this.criteria.getXactDate1Op().equals("")) {
	       try {
	           RMT2Utility.stringToDate(this.criteria.getXactDate1());
	       }
	       catch (SystemException e) {
	           this.isCriteriaOk = false;
	           throw e;
	       }
	  	
	       if (this.sql.length() > 0) {
	           this.sql.append(" and ");
	       }
		   this.sql.append("xact_date ");
		   this.sql.append(this.criteria.getXactDate1Op());
		   this.sql.append(" \'" + this.criteria.getXactDate1() + "\'");
	   }
		  
		  //  Add transaction date #2 criteria
		  if (!this.criteria.getXactDate2().equals("") && !this.criteria.getXactDate2Op().equals("")) {
		  	try {
		  		RMT2Utility.stringToDate(this.criteria.getXactDate2());
		  	}
		  	catch (SystemException e) {
		  		this.isCriteriaOk = false;
		  		throw e;
		  	}
		  	
		  	if (this.sql.length() > 0) {
		  		this.sql.append(" and ");
		  	}
		  	this.sql.append("xact_date ");
		  	this.sql.append(this.criteria.getXactDate2Op());
		  	this.sql.append(" \'" + this.criteria.getXactDate2() + "\'");
		  }
		  
		  //  Add transaction amount #1 criteria
		  if (!this.criteria.getXactAmount1Op().equals("")) {
		  	if (this.sql.length() > 0) {
		  		this.sql.append(" and ");
		  	}
		  	this.sql.append("xact_amount ");
		  	this.sql.append(this.criteria.getXactAmount1Op());
		  	this.sql.append(" " + this.criteria.getXactAmount1());
		  }    
		  
		  //  Add transaction amount #2 criteria
		  if (!this.criteria.getXactAmount1Op().equals("") ) {
		  	if (this.sql.length() > 0) {
		  		this.sql.append(" and ");
		  	}
		  	this.sql.append("xact_amount ");
		  	this.sql.append(this.criteria.getXactAmount1Op());
		  	this.sql.append(" " + this.criteria.getXactAmount2());
		  }    
		  
		  // Add transaction reason this.criteria.  This always perform a contains operation on the string.
		  if(!this.criteria.getXactReason().equals("")) {
		  	if (this.sql.length() > 0) {
		  		this.sql.append(" and ");
		  	}
		  	this.sql.append("reason like \'%");
		  	this.sql.append(this.criteria.getXactReason());
		  	this.sql.append("%\' ");    	
		  }
		  
		  // Get Customer Name
		  if (this.criteria.getXactCustomerName() != null) {
		    if (!this.criteria.getXactCustomerName().equals("")) {
		    	if (this.sql.length() > 0) {
		    		this.sql.append(" and ");
		    	}
		    	isCustomerLookup = true;
		    	this.sql.append("account_name like \'%");
		    	this.sql.append(this.criteria.getXactCustomerName());
		    	this.sql.append("%\' ");
		    }
		  }
		
		  // Get Customer Number
		  if (this.criteria.getXactCustomerNo() != null) {
		    if (!this.criteria.getXactCustomerNo().equals("")) {
		    	if (this.sql.length() > 0) {
		    		this.sql.append(" and ");
		    	}
		    	isCustomerLookup = true;
		    	this.sql.append("account_no = \'");
		    	this.sql.append(this.criteria.getXactCustomerNo());
		    	this.sql.append("\'");
		    }
		  }
		  
		  // Get Creditor Name
		  if (this.criteria.getXactCreditorName() != null) {
		    if (!this.criteria.getXactCreditorName().equals("")) {
		    	if (this.sql.length() > 0) {
		    		this.sql.append(" and ");
		    	}
		    	isCreditorLookup = true;
		    	this.sql.append("longname like \'%");
		    	this.sql.append(this.criteria.getXactCreditorName());
		    	this.sql.append("%\' ");
		    }
		  }
		
		  // Get Creditor Number
		  if (this.criteria.getXactCreditorNo() != null) {
              if (!this.criteria.getXactCreditorNo().equals("")) {
                  if (this.sql.length() > 0) {
                      this.sql.append(" and ");
                  }
                  isCreditorLookup = true;
                  this.sql.append("account_number = \'");
                  this.sql.append(this.criteria.getXactCreditorNo());
                  this.sql.append("\'");
              }
		  }
		  
		  this.orderBy.append(" xact_date desc, id desc ");
		  this.isCriteriaOk = true;
		  return this.sql.toString();
  } // end buildSelectionCriteria

  /**
   * Gathers all transaction type id's based on the current transaction category id.   The applicable transaction category id values should be:
   * <blockquote>
   * 0 = all transaction Id's
   * 2 = cash receipts
   * 3 = cash disbursements
   * 4 = sales
   * 8 = purchases
   * </blockquote>
   * 
   * @return String - transaction type id criteria in the form of an "in" clause for categories 2, 3, 4, and 8.   Null is returned for category 0.
   */
  private String buildXactTypeCriteriaByCategory() {
      String criteria = "";
      ArrayList list = null;
      XactType xactType = null;
 
      if (this.xactCatgId == 0) {
          return null;
      }

      try {
         this.api.setBaseView("XactTypeView");
         this.api.setBaseClass("com.bean.XactType");
         
         // Get transaction types
          list = this.api.findXactTypeByCatgId(this.xactCatgId);
          
          // Build list of transactions which we should query.
          for (int ndx =0; ndx < list.size(); ndx++) {
              xactType = (XactType) list.get(ndx);
              if (ndx == 0) {
                  criteria = String.valueOf(xactType.getId());
              }
              else {
                  criteria += ", " + xactType.getId();    
              }
          }
          
          // Complete the formation of the "in" clause.
          criteria = " xact_type_id in (" + criteria + ") ";
          return criteria;
      }
      catch (XactException e) {
          System.out.println("[XactQueryPollingAction.buildXactTypeCriteriaByCategory] XactException: " + e.getMessage());
          return null;
      }
  }
  
} // end class
package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Hashtable;

import com.api.XactManagerApi;
import com.api.GLBasicApi;
import com.api.CashDisbursementsApi;

import com.bean.GlAccounts;
import com.bean.GlAccountTypeCatg;
import com.bean.VwXactTypeItemActivity;
import com.bean.Xact;
import com.bean.XactQuery;
import com.bean.XactType;
import com.bean.RMT2TagQueryBean;
import com.bean.XactTypeItemActivity;
import com.bean.XactXlatBean;
import com.bean.XactPostDetails;
import com.bean.VwXactList;

import com.constants.RMT2ServletConst;
import com.constants.XactConst;

import com.factory.XactFactory;
import com.factory.AcctManagerFactory;
import com.factory.CashDisburseFactory;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.XactException;
import com.util.CashDisbursementsException;
import com.util.GLAcctException;
import com.util.ActionHandlerException;
import com.util.RMT2Utility;


//  TODO: remove imports when Creditor and Customer search screens are reengineered
import com.api.GLCreditorApi;
import com.api.GLCustomerApi;

/**
 * Class that manages transactions and their related General Ledger accounts
 * 
 * @author Roy Terrell
 *
 */
public class XactCashDisburseAction extends XactAction implements IRMT2ServletActionHandler {

  protected GLBasicApi glApi;
  private CashDisbursementsApi disbApi; 
  private Hashtable errors = new Hashtable();
  private boolean isCriteriaOk = false;
  private boolean isCustomerLookup = false;
  private boolean isCreditorLookup = false;

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public XactCashDisburseAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {

    super(_context, _request);
    this.className = "XactToGLAction";
    this.glApi = AcctManagerFactory.createBasic(this.dbConn, this.request);
    this.disbApi = CashDisburseFactory.createApi(this.dbConn, this.request);
    
    // Notify the ancestor to not to perform and database commits
	 this.isOkToCommit = false;
  }

  
  
  public int search() throws ActionHandlerException {
	  String sql = null;
	  RMT2TagQueryBean query = null;
	  XactQuery criteria = null;
	  ArrayList list = null;
	  
	  query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	  if (query.isWaitPage()) {
		  list = this.execQuery(query.getWhereClause(), query.getOrderByClause());
		  this.request.setAttribute("data", list);  
		  query.setWaitPage(false);
		  this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
		  return 1; // hide "please wait..." page
	  }
	  else {
		  try {
			  criteria = this.getInputData();
			  query.setCustomObj(criteria);
		      query.setWhereClause(null);
			  sql = this.buildCriteria(criteria);  
			  query.setWhereClause(sql);
			  query.setOrderByClause( " xact_date desc, id desc ");
			  query.setWaitPage(true);
			  this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
			  return 0;  // show "please wait..." page
		  }
		  catch (SystemException e) {
			  throw new ActionHandlerException(e);
		  }  
	  }
  }
  
  private  XactQuery getInputData() {
	  XactQuery criteria = null;
	  int xactCatgId = 0;
	  
	  try {
		  criteria = new XactQuery();
		  XactFactory.packageBean(this.request, criteria);
		  xactCatgId = Integer.parseInt(this.request.getParameter("XactCatgId"));
		  criteria.setXactCatgId(xactCatgId);
		  return criteria;
	  }
	  catch (SystemException e) {
		  System.out.println("getInputData Thread has problems");
		  return null;
	  }
  }
  
  
  /**
   * Builds the SQL selection criteria based off of the use's input from the request object.   Returns the where clause selection criteria in the 
   * form of a string.
   *  
   * @return String - Selection Criteria
   * @throws SystemException
   */
  private String buildCriteria(XactQuery _criteria) throws SystemException {
      StringBuffer sql = new StringBuffer(100);
      StringBuffer orderBy = new StringBuffer(100);
      
      
      //    Add Transacton Type Id criteria
      if (_criteria.getXactCatgId() > 0) {
          sql.append("xact_category_id in (");
          sql.append(XactConst.CATG_REVERSE);
          sql.append(", ");
   	  	  sql.append(_criteria.getXactCatgId());
          sql.append(") ");
      }
     
	   //  Add Transacton Type Id criteria
	   if (_criteria.getXactTypeId() > 0) {
	       if (sql.length() > 0) {
	           sql.append(" and ");
		  	 }
	  	    sql.append("xact_type_item_xact_type_id = ");
	  	    sql.append(_criteria.getXactTypeId());
	   }
       
	   //  Add transaction date #1 criteria
	   if (!_criteria.getXactDate1().equals("") && !_criteria.getXactDate1Op().equals("")) {
	       try {
	           RMT2Utility.stringToDate(_criteria.getXactDate1());
	       }
	       catch (SystemException e) {
	           isCriteriaOk = false;
	           throw e;
	       }
	  	
	       if (sql.length() > 0) {
	           sql.append(" and ");
	       }
		   sql.append("xact_date ");
		   sql.append(_criteria.getXactDate1Op());
		   sql.append(" \'" + _criteria.getXactDate1() + "\'");
	   }
		  
		  //  Add transaction date #2 criteria
		  if (!_criteria.getXactDate2().equals("") && !_criteria.getXactDate2Op().equals("")) {
		  	try {
		  		RMT2Utility.stringToDate(_criteria.getXactDate2());
		  	}
		  	catch (SystemException e) {
		  		isCriteriaOk = false;
		  		throw e;
		  	}
		  	
		  	if (sql.length() > 0) {
		  		sql.append(" and ");
		  	}
		  	sql.append("xact_date ");
		  	sql.append(_criteria.getXactDate2Op());
		  	sql.append(" \'" + _criteria.getXactDate2() + "\'");
		  }
		  
		  //  Add transaction amount #1 criteria
		  if (!_criteria.getXactAmount1Op().equals("")) {
		  	if (sql.length() > 0) {
		  		sql.append(" and ");
		  	}
		  	sql.append("xact_amount ");
		  	sql.append(_criteria.getXactAmount1Op());
		  	sql.append(" " + _criteria.getXactAmount1());
		  }    
		  
		  //  Add transaction amount #2 criteria
		  if (!_criteria.getXactAmount1Op().equals("") ) {
		  	if (sql.length() > 0) {
		  		sql.append(" and ");
		  	}
		  	sql.append("xact_amount ");
		  	sql.append(_criteria.getXactAmount1Op());
		  	sql.append(" " + _criteria.getXactAmount2());
		  }    
		  
		  // Add transaction reason _criteria.  This always perform a contains operation on the string.
		  if(!_criteria.getXactReason().equals("")) {
		  	if (sql.length() > 0) {
		  		sql.append(" and ");
		  	}
		  	sql.append("reason like \'");
		  	sql.append(_criteria.getXactReason());
		  	sql.append("%\' ");    	
		  }
		  
		  // Get Customer Name
		  if (_criteria.getXactCustomerName() != null) {
		    if (!_criteria.getXactCustomerName().equals("")) {
		    	if (sql.length() > 0) {
		    		sql.append(" and ");
		    	}
		    	isCustomerLookup = true;
		    	sql.append("account_name like \'");
		    	sql.append(_criteria.getXactCustomerName());
		    	sql.append("%\' ");
		    }
		  }
		
		  // Get Customer Number
		  if (_criteria.getXactCustomerNo() != null) {
		    if (!_criteria.getXactCustomerNo().equals("")) {
		    	if (sql.length() > 0) {
		    		sql.append(" and ");
		    	}
		    	isCustomerLookup = true;
		    	sql.append("account_no = \'");
		    	sql.append(_criteria.getXactCustomerNo());
		    	sql.append("\'");
		    }
		  }
		  
		  // Get Creditor Name
		  if (_criteria.getXactCreditorName() != null) {
		    if (!_criteria.getXactCreditorName().equals("")) {
		    	if (sql.length() > 0) {
		    		sql.append(" and ");
		    	}
		    	isCreditorLookup = true;
		    	sql.append("longname like \'");
		    	sql.append(_criteria.getXactCreditorName());
		    	sql.append("%\' ");
		    }
		  }
		
		  // Get Creditor Number
		  if (_criteria.getXactCreditorNo() != null) {
              if (!_criteria.getXactCreditorNo().equals("")) {
                  if (sql.length() > 0) {
                      sql.append(" and ");
                  }
                  isCreditorLookup = true;
                  sql.append("account_number = \'");
                  sql.append(_criteria.getXactCreditorNo());
                  sql.append("\'");
              }
		  }
		  
		  orderBy.append(" xact_date desc, id desc ");
		  isCriteriaOk = true;
		  return sql.toString();
  } // end buildSelectionCriteria
  
  
  /**
   * The implementation performs a database search for the transaction(s).   The datasource view that is to be used is programmatically selected
   * by determining wheter or not the search is customer, creditor, or generically based.  Afterwards, the next thread is awaken to continue the process.
   * If this process fails the next thread will not continue.
   * 
   * @return int - Returns 1= success.   Otherwise, -1=failure.
   */
  protected ArrayList execQuery(String _sql, String _orderBy) {
	  ArrayList list = new ArrayList();
	  
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
	      list = api.findXact(_sql, _orderBy);
	  }
	  catch (XactException e) {
	      this.request.setAttribute("error", e.getMessage());
	  }

  	  return list;
  }
  
  
  /**
   * Extends the add transaction functionality.
   * 
   * @throws XactException 
   */
  public void add() throws ActionHandlerException {
	  try {
		  super.addXact();
		  return;
	  }
	  catch (XactException e) {
		  throw new ActionHandlerException(e);
	  }
   }
 
  /**
   * Extends the addXactItem functionality.
   * 
   * @thows XactException
   */
  public void addXactItem() throws XactException {
      // call super script
      super.addXactItem();
      return;
  }
  
 
  	
  /**
   * Gathers all data needed to view general transaction details.  Totally relies on the ancestor script for all processing.
   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
   * at the discretion of the client: 
   * 
   * @throws XactException
   */
  public void viewXact() throws XactException {
	super.viewXact();
    return;
  }


  public void edit() throws ActionHandlerException {
	  
  }
  
public void delete() throws ActionHandlerException {
	  
  }


  protected XactManagerApi getCustomXactApi() {
	  XactManagerApi xactApi = CashDisburseFactory.createBaseXactApi(this.dbConn, this.request);
	  return xactApi;
  }

  /**
   * Saves transaction details and post detail amounts to the General Ledger.
   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
   * at the discretion of the client:
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>target</td>
	 *   <td>ArrayList</td>
	 *   <td>List of {@link XactXlatBean} objects as targets. Only one one element should exist</td>
	 * </tr>
	 * <tr>
	 *   <td>offsets</td>
	 *   <td>ArrayList</td>
	 *   <td>List of {@link XactXlatBean} objects as offsets. At least one element is required.</td>
	 * </tr>
	 * <tr>
	 *   <td>targetTypes</td>
	 *   <td>ArrayList</td>
	 *   <td>List of applicable target transaction objects of either type {@link GlAccountTypeCatg}  or {@link GlAccounts}</td>
	 * </tr>
	 * <tr>
	 *   <td>offsetTypes</td>
	 *   <td>ArrayList</td>
	 *   <td>List of applicable offset transaction objects of either type {@link GlAccountTypeCatg}  or {@link GlAccounts}</td>
	 * </tr>
	 * <tr>
	 *   <td>message</td>
	 *   <td>String</td>
	 *   <td>Message denoting the result of the operation.</td>
	 * </tr>
	 *</table> 
   *
   * @return int - the transaction id. 
   * @throws XactException
   * @throws DatabaseException
   * 
   */
  public void save() throws ActionHandlerException {
	    int  xactId = 0;
	    Xact xact = null;
	    ArrayList items = null;
	    ArrayList xtiList = null;
	    VwXactList vwXact = null;
	    
		 
		try {
			xact = this.getHttpXactBase();
			items = this.getHttpXactItems();
			xactId = this.disbApi.maintainCashDisbursement(xact, items);
 
	        // Get combined transaction data object
			vwXact = this.getCombinedXact(xactId);
			
			// Get combined transaction items
	        items = this.api.findVwXactTypeItemActivityByXactId(xactId);
	        
	        this.request.setAttribute(XactConst.PARM_XACT, vwXact);
	        this.request.setAttribute(XactConst.PARM_XACTITEMS, items);
	        
            this.transObj.commitTrans();	        
	        // Set confirmation message which has already set in the request object from the ancestor script.
	        this.msg = "Transaction Saved Successfully";
		    return;
	    }
		catch (CashDisbursementsException e) {
			try {
	    		  this.transObj.rollbackTrans();  
	    	  }
	    	  catch (DatabaseException ee) {
	    		  throw new ActionHandlerException(e);
	    	  }
	  	    System.out.println("CashDisbursementsException: " + e.getMessage());
	  	    throw new ActionHandlerException(e);
		}
	  	catch (DatabaseException e) {
	  		try {
	    		  this.transObj.rollbackTrans();  
	    	  }
	    	  catch (DatabaseException ee) {
	    		  throw new ActionHandlerException(e);
	    	  }
	  	    System.out.println("DatabaseException: " + e.getMessage());
	  	    throw new ActionHandlerException(e);
	  	}
	  	catch (XactException e) {
	  		try {
	    		  this.transObj.rollbackTrans();  
	    	  }
	    	  catch (DatabaseException ee) {
	    		  throw new ActionHandlerException(e);
	    	  }
	        this.request.setAttribute(XactConst.PARM_XACT, xact);
	        this.request.setAttribute(XactConst.PARM_XACTITEMS, items);

	        // Get all transaction type item entries by transaction type to be used generally as a UI Dropdown.
	        try {
	        	xtiList = this.api.findXactTypeItemsByXactTypeId(this.xactTypeBean.getId());
		        this.request.setAttribute(XactConst.PARM_XACTITEMSLIST, xtiList);	
	        }
	        catch (XactException ee) {
	        	throw new ActionHandlerException(e);
	        }
	        
	  	    return;
	  	}
		finally {
		    // Send request results to the client.
		    this.request.setAttribute("msg", this.msg);
		}
	}

  /**
   * Action handler to drive process of reversing the amounts of the transaction details and post the amounts to the General Ledger.
   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
   * at the discretion of the client:
   * <p>
   * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
   * <tr>
   *   <td><strong>Attribute</strong></td>
   *   <td><strong>Type</strong></td>
   *   <td><strong>Description</strong></td>
   *  </tr>
   * <tr>
   *   <td>target</td>
   *   <td>ArrayList</td>
   *   <td>List of {@link XactXlatBean} objects as targets. Only one one element should exist</td>
   * </tr>
   * <tr>
   *   <td>offsets</td>
   *   <td>ArrayList</td>
   *   <td>List of {@link XactXlatBean} objects as offsets. At least one element is required.</td>
   * </tr>
   * <tr>
   *   <td>targetTypes</td>
   *   <td>ArrayList</td>
   *   <td>List of applicable target transaction objects of either type {@link GlAccountTypeCatg}  or {@link GlAccounts}</td>
   * </tr>
   * <tr>
   *   <td>offsetTypes</td>
   *   <td>ArrayList</td>
   *   <td>List of applicable offset transaction objects of either type {@link GlAccountTypeCatg}  or {@link GlAccounts}</td>
   * </tr>
   * <tr>
   *   <td>message</td>
   *   <td>String</td>
   *   <td>Message denoting the result of the operation.</td>
   * </tr>
   *</table>
   * 
   * @return int - the transaction id. 
   * @throws XactException
   * @throws DatabaseException
   * 
   */
  public int reverseXact() throws XactException, DatabaseException {
	    int  xactId = 0;
	    Xact xact = null;
	    ArrayList items = null;
	    VwXactList vwXact = null;
	    
		 
		try {
			xact = this.getHttpXactBase();
			items = this.api.findXactTypeItemsActivityByXactId(xact.getId());
			xactId = this.disbApi.maintainCashDisbursement(xact, items);
 
	        // Get combined transaction data object
			vwXact = this.getCombinedXact(xactId);
			
			// Get combined transaction items
	        items = this.api.findVwXactTypeItemActivityByXactId(xactId);
	        
	        this.request.setAttribute(XactConst.PARM_XACT, vwXact);
	        this.request.setAttribute(XactConst.PARM_XACTITEMS, items);
	        
            this.transObj.commitTrans();	        
	        // Set confirmation message which has already set in the request object from the ancestor script.
	        this.msg = "Transaction Reversed Successfully";
		    return xactId;
	    }
		catch (CashDisbursementsException e) {
			this.transObj.rollbackTrans();
	  	    System.out.println("CashDisbursementsException: " + e.getMessage());
	  	    throw new XactException(e);
		}
	  	catch (DatabaseException e) {
	  	    this.transObj.rollbackTrans();
	  	    System.out.println("DatabaseException: " + e.getMessage());
	  	    throw new XactException(e);
	  	}
	  	catch (XactException e) {
	  	    this.transObj.rollbackTrans();
	  	    System.out.println("XactException: " + e.getMessage());
	  	    throw new XactException(e);
	  	}
		finally {
		    // Send request results to the client.
		    this.request.setAttribute("msg", this.msg);
		}
  }

  
  
  
}
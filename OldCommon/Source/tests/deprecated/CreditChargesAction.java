package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Hashtable;

import com.api.XactManagerApi;
import com.api.GLCreditorApi;
import com.api.CreditChargesApi;

import com.bean.GlAccounts;
import com.bean.GlAccountTypeCatg;
import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VwCreditorBusiness;
import com.bean.VwXactTypeItemActivity;
import com.bean.Xact;
import com.bean.XactCategory;
import com.bean.XactQuery;
import com.bean.XactType;
import com.bean.RMT2TagQueryBean;
import com.bean.XactTypeItemActivity;
import com.bean.XactXlatBean;
import com.bean.XactPostDetails;
import com.bean.VwXactList;
import com.bean.criteria.CreditChargeCriteria;

import com.constants.RMT2ServletConst;
import com.constants.XactConst;
import com.constants.AccountingConst;

import com.factory.PurchasesFactory;
import com.factory.DataSourceAdapter;
import com.factory.XactFactory;
import com.factory.AcctManagerFactory;
import com.factory.CreditChargesFactory;

import com.util.GLAcctException;
import com.util.NotFoundException;
import com.util.PurchaseOrderException;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.XactException;
import com.util.CreditChargeException;
import com.util.ActionHandlerException;
import com.util.RMT2Utility;





/**
 * Class that manages transactions and their related General Ledger accounts
 * 
 * @author Roy Terrell
 *
 */
public class CreditChargesAction extends XactAction implements IRMT2ServletActionHandler{

  protected GLCreditorApi credApi;
  protected CreditChargesApi ccApi; 
  protected Hashtable errors = new Hashtable();

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public CreditChargesAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {

    super(_context, _request);
    this.className = "CreditChargesAction";
    this.credApi = AcctManagerFactory.createCreditor(this.dbConn, _request);
    this.ccApi = CreditChargesFactory.createApi(this.dbConn, this.request);
    this.isOkToCommit = false;
  }

  
  public void newXactSearch() {
      ArrayList list = new ArrayList();
	  super.newXactSearch();
      this.request.setAttribute("data", list);
  }

  /**
   * Searches for credit charge transactions by transforming client data into selection criteria.   Displays a "Wait Please..." page 
   * prior to displaying the results of the query.
   *  
   * @return 0 indicating that the wait page be displayed.  1 indicating that the wait page should be hidden. 
   * @throws ActionHandlerException
   */
  public int search(int _mode) throws ActionHandlerException {
	  RMT2TagQueryBean query = null;
	  ArrayList list = null;
      int rc = 0;
	  
      this.ccApi.setBaseView("VwXactCreditChargeListView");
      this.ccApi.setBaseClass("com.bean.VwXactCreditChargeList");
	  try {
		  query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
		  if (query.isWaitPage()) {
			  list = this.ccApi.findData(query.getWhereClause(), query.getOrderByClause());
			  this.request.setAttribute("data", list);  
			  query.setWaitPage(false);
			  this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
			  rc = RMT2ServletConst.WAITPAGE_HIDE; // hide "please wait..." page
		  }
		  else {
              switch (_mode) {
                  case RMT2ServletConst.SEARCH_MODE_NEW:
                      this.buildSearchCriteria();
                      break;
                  case RMT2ServletConst.SEARCH_MODE_OLD:
                      this.useExistingSearchCriteria();
                      break;
              }
			  rc = RMT2ServletConst.WAITPAGE_SHOW;  // show "please wait..." page
		  }
          return rc;
	  }
	  catch (SystemException e) {
		  throw new ActionHandlerException(e);
	  }
  }
  
  
  
  /**
   * This method is responsible for gathering the user's input of credit charge selection criteria data from the request object.
   * 
   * @return Object which represents the custom object that is a member of {@link RMT2TagQueryBean}. 
   * @throws ActionHandlerException
   */
  protected Object doCustomInitialization() throws ActionHandlerException {
      String method = "[" + this.className + ".getCriteriaObject] ";
      this.setBaseView("VwXactCreditChargeListView");
      CreditChargeCriteria criteriaObj = CreditChargeCriteria.getInstance();
      if (!this.isFirstTime()) {
          try {
              DataSourceAdapter.packageBean(this.request, criteriaObj);    
          }
          catch (SystemException e) {
              System.out.println(method + "Problem gathering Item Master request parameters.");
              System.out.println(method + e.getMessage());
              throw new ActionHandlerException(e); 
          }    
      }
      return criteriaObj;
  }
   
  /**
   * Applies additional search criteria to _query before the _query is updated on the session object.   In addition to adding 
   * criteria, the wait page flag is set to true to indicate that the "Wait Please..." page has been displayed.
   * <p>
   * <p>
   * This method ensures that the following condition(s) are included in the selection criteria that will be used to retrieve credit charge transactions: 1)  each transaction must be associated 
   * with a creditor type of {@link AccountingConst.CREDITOR_TYPE_CREDITOR} and 2) the transaction type must be equal to {@link XactConst.XACT_TYPE_CREDITCHARGE}.
   *  
   * @param _query {@link RMT2TagQueryBean} object.
   * @param __searchMode Set to either {@link RMT2ServletConst.SEARCH_MODE_NEW} or {@link RMT2ServletConst.SEARCH_MODE_OLD}
   * @throws ActionHandlerException
   */
  protected void doPostCustomInitialization(RMT2TagQueryBean _query, int _searchMode) throws ActionHandlerException {
      String sql = null;
      String annex = null;
      
      if (_searchMode == RMT2ServletConst.SEARCH_MODE_NEW) {
          sql = _query.getWhereClause();
          if (sql != null) {
              annex = "creditor_type_id =  " +  AccountingConst.CREDITOR_TYPE_CREDITOR + " and xact_type_id in ( " + XactConst.XACT_TYPE_CREDITCHARGE + ")";
              if (sql.length() > 0) {
                  sql += " and " + annex; 
              }
              else {
                  sql = annex;
              }
              _query.setWhereClause(sql);
          }
          _query.setOrderByClause( " xact_date desc, xact_id desc ");
      }
      _query.setWaitPage(true);      
	  return;
  }
  
  /**
   * Invokes the General Credit Charge Edit page in order to associate the new transaction with a particular creditor.
   * 
   * @throws XactException 
   */
  public void add() throws ActionHandlerException {
	  String creditorIdProp = "qry_CreditorId";
	  int creditorId = 0;
	  String temp = null;
	  VwCreditorBusiness creditor = null;
	  
	  try {
    	  temp = this.getPropertyValue(creditorIdProp);
    	  creditorId = Integer.parseInt(temp);
    	  creditor = this.getCreditorData(creditorId);
   		  this.request.setAttribute("creditor", creditor);
          try {
              this.addXact();
          }
          catch (XactException e) {
              System.out.println(e.getMessage());
              throw new ActionHandlerException(e);
          }
      }
      catch (NotFoundException e) {
          this.msg = "A creditor must be selected for an 'Add' request";
    	  System.out.println(this.msg);
    	  throw new ActionHandlerException(this.msg);
      }
      catch (GLAcctException e) {
    	  System.out.println(e.getMessage());
          throw new ActionHandlerException(e);
      }
      return;
   }
 
  /**
   * Extends the addXactItem functionality.
   * 
   * @thows XactException
   */
  public void addItem() throws ActionHandlerException {
      String creditorIdProp = "CreditorId";
      int creditorId = 0;
      String temp = null;
      VwCreditorBusiness creditor = null;

      try {
          temp = this.getPropertyValue(creditorIdProp);
          creditorId = Integer.parseInt(temp);
          creditor = this.getCreditorData(creditorId);
          this.request.setAttribute("creditor", creditor);
      }
      catch (NotFoundException e) {
          this.msg = "A creditor must be selected for an 'Add Item' request";
          System.out.println(this.msg);
          throw new ActionHandlerException(this.msg);
      }
      catch (GLAcctException e) {
          System.out.println(e.getMessage());
          throw new ActionHandlerException(e);
      }
      
      // call super script
	  try {
		  super.addXactItem();
		  return;
	  }
	  catch (XactException e) {
		  System.out.println(e.getMessage());
		  throw new ActionHandlerException(e);
	  }
  }
  
  
  public void edit() throws ActionHandlerException {
      int xactId = 0;
      int creditorId = 0;
      int row = 0;
      String xactIdProp = "XactId";
      String creditorIdProp = "CreditorId";
      String temp = null;
     
      try {
    	  row = this.getSelectedRow("selCbx"); 
    	  temp = this.getPropertyValue(xactIdProp + row);
    	  xactId = Integer.parseInt(temp);
    	  temp = this.getPropertyValue(creditorIdProp + row);
    	  creditorId = Integer.parseInt(temp);
    	  this.retrieveCreditCharge(xactId, creditorId);
      }
      catch (SystemException e) {
    	  System.out.println(e.getMessage());
    	  throw new ActionHandlerException(e);
      }
      catch (NotFoundException e) {
    	  System.out.println(e.getMessage());
    	  throw new ActionHandlerException(e);
      }
  }
  
  /**
   * Retreives creditor's data.
   * 
   * @param _creditorId The id of the creditor
   * @return {@link VwCreditorBusiness}
   * @throws GLAcctException
   * @throws NotFoundException
   */
  private VwCreditorBusiness getCreditorData(int _creditorId) throws GLAcctException, NotFoundException {
      VwCreditorBusiness creditor = null;
      ArrayList creditors = null;
      
   	  // Get vendor data.  Expect to receive a vendor id at all times since purchase orders created per vendor.
   	  creditors = this.credApi.findCreditorBusiness("creditor_id = " + _creditorId);
   	  if (creditors.size() > 0) {
   		  creditor = (VwCreditorBusiness) creditors.get(0);
   		  return creditor;
   	  }
   	  else {
   		  throw new NotFoundException("Creditor could not be found using id: " + _creditorId);
   	  }
  }
  
  
  /**
   * Gathers all credit charge data from the database and packages data in respective objects to be sent to the client for presentation.   
   * The following objects are created and sett to the client via the request object:
   * <p>
   * {@link VwXactList} identified as {@link XactConst.PARM_XACT}
   * <p>
   * An ArrayList of {@link VwXactTypeItemActivity} objects identified as {@link XactConst.PARM_XACTITEMS}
   * 
   * @param _xactId The id associated with the base transaction. 
   * @param _creditorId The id of the creditor.
   * @throws ActionHandlerException
   */
  protected void retrieveCreditCharge(int _xactId, int _creditorId) throws ActionHandlerException {
      String method = "[" + this.className + ".retrieveCreditChargeConfirmation] ";
      VwXactList xact = null;
      VwCreditorBusiness creditor = null;
      ArrayList xactItems = null;
      
      try {
          // Get vendor data.  Expect to receive a vendor id at all times since purchase orders created per vendor.
          creditor = this.getCreditorData(_creditorId);
          this.request.setAttribute("creditor", creditor);
          
           // Get base transaction object
          xact = this.api.findXactListViewByXactId(_xactId);
          if (xact == null) {
              // This must be a new purchase order request, so ensure that all objects are created and initialized to prevent null exceptions on the client.
              this.createNewCreditChargeData();
          }
          else {
              // Get Transaction Items
              xactItems = this.api.findVwXactTypeItemActivityByXactId(_xactId);
              
              this.request.setAttribute(XactConst.PARM_XACT, xact);
              this.request.setAttribute(XactConst.PARM_XACTITEMS, xactItems);
              this.request.setAttribute("msg", this.msg);
          }
      }
      catch (NotFoundException e) {
          throw new ActionHandlerException(e);
      }
      catch (GLAcctException e) {
          System.out.println(method + e.getMessage());
          throw new ActionHandlerException(e);
      }
      catch (XactException e) {
          System.out.println(method + e.getMessage());
          throw new ActionHandlerException(e);
      }
      return;
  }
  
  /**
   * 
   * @throws ActionHandlerException
   */
  private void createNewCreditChargeData() throws ActionHandlerException  {
	  Xact xact = null;
      XactType xactType = null;
      XactCategory xactCatg = null;
      ArrayList xactItems = null;
      
   	  xact = XactFactory.createXact();  
	  xactItems = new ArrayList();
      xactType = XactFactory.createXactType();
      xactType.setId(XactConst.XACT_TYPE_CREDITCHARGE);
      xactCatg = XactFactory.createXactCategory();
      this.request.setAttribute(XactConst.PARM_XACT, xact);
      this.request.setAttribute(XactConst.PARM_XACTTYPE, xactType);
      this.request.setAttribute(XactConst.PARM_XACTCATG, xactCatg);
      this.request.setAttribute(XactConst.PARM_XACTITEMS, xactItems);
      
      return;
  }
  

 
  protected XactManagerApi getCustomXactApi() {
	  XactManagerApi xactApi = CreditChargesFactory.createBaseXactApi(this.dbConn, this.request);
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
      String creditorIdProp = "CreditorId";
      String temp = null;
      int  xactId = 0;
      int creditorId = 0;
      Xact xact = null;
      ArrayList items = null;
		 
      try {
          temp = this.getPropertyValue(creditorIdProp);
          creditorId = Integer.parseInt(temp);
      }
      catch (NotFoundException e) {
          this.msg = "A creditor must be selected for an 'Add Item' request";
          System.out.println(this.msg);
          throw new ActionHandlerException(this.msg);
      }
      
      try {
          xact = this.getHttpXactBase();
          items = this.getHttpXactItems();
          xactId = this.ccApi.maintainCreditCharge(xact, items, creditorId);
          this.transObj.commitTrans();	        
            
          // Set confirmation message which has already set in the request object from the ancestor script.
          this.msg = "Transaction Saved Successfully";
          this.retrieveCreditCharge(xactId, creditorId);
          return;
      }
      catch (CreditChargeException e) {
    	  try {
    		  this.transObj.rollbackTrans();  
    	  }
    	  catch (DatabaseException ee) {
    		  throw new ActionHandlerException(e);
    	  }
          System.out.println("CreditChargeException: " + e.getMessage());
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
  	        this.retrieveCreditCharge(xactId, creditorId);

  	        // Get all transaction type item entries by transaction type to be used generally as a UI Dropdown.
	        //xtiList = this.api.findXactTypeItemsByXactTypeId(this.xactTypeBean.getId());
	        //this.request.setAttribute(XactConst.PARM_XACTITEMSLIST, xtiList);
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
  public int reverse() throws ActionHandlerException, DatabaseException {
	    int  xactId = 0;
	    int creditorId = 0;
        int row = 0;
        String temp  = null;
        String creditorIdProp = "CreditorId";
	    Xact xact = null;
	    ArrayList items = null;
	    VwXactList vwXact = null;
	    
	    try {
            row = this.getSelectedRow("selCbx");
	        temp = this.getPropertyValue(creditorIdProp + row);
	        creditorId = Integer.parseInt(temp);
	    }
	    catch (NotFoundException e) {
	        this.msg = "A creditor must be selected for an 'Add Item' request";
	        System.out.println(this.msg);
	        throw new ActionHandlerException(this.msg);
	    }
        catch (SystemException e) {
            System.out.println(e.getMessage());
            throw new ActionHandlerException(e);
          }
          
		try {
			xact = this.getHttpXactBase();
            items = this.api.findXactTypeItemsActivityByXactId(xact.getId());
			xactId = this.ccApi.maintainCreditCharge(xact, items, creditorId);
            this.transObj.commitTrans();
            
            this.retrieveCreditCharge(xactId, creditorId);
            this.msg = "Transaction reversed successfully";
            this.request.setAttribute("msg", this.msg);
		    return xactId;
	    }
		catch (CreditChargeException e) {
			this.transObj.rollbackTrans();
	  	    System.out.println("CashDisbursementsException: " + e.getMessage());
	  	    throw new ActionHandlerException(e);
		}
	  	catch (DatabaseException e) {
	  	    this.transObj.rollbackTrans();
	  	    System.out.println("DatabaseException: " + e.getMessage());
	  	    throw new ActionHandlerException(e);
	  	}
	  	catch (XactException e) {
	  	    this.transObj.rollbackTrans();
	  	    System.out.println("XactException: " + e.getMessage());
	  	    throw new ActionHandlerException(e);
	  	}
  }

  
  /**
   * Stub method.
   */
  public void delete() throws ActionHandlerException {
      
  }
  
}
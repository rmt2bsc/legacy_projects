package com.action;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;



import com.bean.GlAccountTypes;
import com.bean.GlAccountCategory;
import com.bean.GlAccountTypeCatg;
import com.bean.GlAccounts;
import com.bean.RMT2Base;

import com.api.GLBasicApi;
import com.api.db.DatabaseException;

import com.action.AbstractActionHandler;

import com.factory.AcctManagerFactory;
import com.factory.GlAccountsFactory;

import com.util.NotFoundException;
import com.util.SystemException;
import com.util.BusinessException;
import com.util.GLAcctException;
import com.util.RMT2Utility;

import com.constants.RMT2SystemExceptionConst;



/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating 
 *  General Ledger Account information.
 * 
 * @author Roy Terrell
 *
 */
public class GlAccountAction extends AbstractActionHandler {

  private GLBasicApi api;
  private final String PAGE_TITLE = "Account Category Maintenance";


  /**
   * Main contructor for this action handler.
   * 
   * @param _context {@link ServletContext} - The servlet context to be associated with this action handler
   * @param _request {@link HttpServletRequest} - The request object sent by the client to be associated with this action handler
   * @throws SystemException
   */
  public GlAccountAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {

    super(_context, _request);
    this.className = "glAcctCategoryAction";
    api = AcctManagerFactory.createBasic(this.dbConn);
  }

  /**
   * This method obtains a complete list of GL account categories that are related to a particular GL Account Type.   This list of account categories are
   * sent back to the client via the request object.
   * <p>
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
	 *   <td>acctTypes</td>
	 *   <td>ArrayList</td>
	 *   <td>A list of {@link GlAccountCategory} objects</td>
	 * </tr>
	 *</table>
	 * 
   * @return  int - Generally returns success (1).   Otherwise, the following exception is thrown.
   * @throws GLAcctException
   */
  public int getAccountCategoryAction() throws GLAcctException {

    String method = "getAccountCategoryAction()";
	 HttpServletRequest req = this.getRequest();
    String rowStr = req.getParameter("selCbx");
    String value = null;
    int     glAcctTypeId;
    int      rowNdx = 0;
    List list;

    //  Client must select a row to edit.
    if (rowStr == null) {
      throw new GLAcctException(RMT2SystemExceptionConst.MSG_ITEM_NOT_SELECTED, RMT2SystemExceptionConst.RC_ITEM_NOT_SELECTED, this.className, method);
    }

    try {
			//  Get index of the row that is to be processed from the HttpServeltRequest object
			rowNdx = RMT2Utility.stringToNumber(rowStr).intValue();

			//  Retrieve values from the request object into the User object.
     GlAccountTypes  acctType = GlAccountsFactory.createAcctType();
     AcctManagerFactory.packageBean(req, acctType, rowNdx);

     // Get all GL Account Categories belonging to account type id.
     list = api.findAcctCatgByAcctType(acctType.getId());

     // Send data basck to the client
     req.setAttribute("acctCatg", list);

		 return RMT2Base.SUCCESS;
	  }
    catch (SystemException e) {
      throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
    }
  }

  
  /**
   * This method is designed to extract the GL Account Type Id and GL Account Category Id from a selected row from the request 
   * object's parameter list.    The GL Account Type Id and the GL Account Category Id are used to obtain a list of related GL Accounts and 
   * send the results to client for front end processing.
   * <p>
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
	 *   <td>glAccounts</td>
	 *   <td>ArrayList</td>
	 *   <td>A list of {@link GlAccountTypeCatg} objects</td>
	 * </tr>
	 * <tr>
	 *   <td>acctCatg</td>
	 *   <td>{@link GlAccountCategory}</td>
	 *   <td>A GL Account Category object</td>
	 * </tr> 
	 *</table>
	 * 
   * @return int - Generally returns 1=success.   Otherwise, the following exception is thrown.
   * @throws GLAcctException
   */
  public int getAccountsAction() throws GLAcctException {

    String method = "getAccountsAction()";
	 HttpServletRequest req = this.getRequest();
    String rowStr = req.getParameter("selCbx");
    String value = null;
    int     glAcctTypeId;
    int      rowNdx = 0;
    List list;

    //  Client must select a row to edit.
    if (rowStr == null) {
      throw new GLAcctException(RMT2SystemExceptionConst.MSG_ITEM_NOT_SELECTED, RMT2SystemExceptionConst.RC_ITEM_NOT_SELECTED,  this.className,  method);
    }

    try {
		//  Get index of the row that is to be processed from the HttpServeltRequest object
		rowNdx = RMT2Utility.stringToNumber(rowStr).intValue();

		//  Retrieve values from the request object into the User object.
		GlAccountCategory  acctCatg = GlAccountsFactory.createCatg();
      AcctManagerFactory.packageBean(req, acctCatg, rowNdx);

      // Get all GL Account based on GL Account Category and GL Account Type.
      list = api.findCombinedGlAcctTypeCatg(acctCatg.getAcctTypeId(), acctCatg.getId());
      if (list == null) {
      	list = new ArrayList();
      }

      // Send data basck to the client
      req.setAttribute("glAccounts", list);
      req.setAttribute("acctCatg", acctCatg);

		  return RMT2Base.SUCCESS;
	  }
    catch (SystemException e) {
      throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
    }
  }

  /**
   * Retrieves a row from a list of account categories that exist in the HTTP Request object, packages the
   * the data into a GlAccountCategory object, and initializes the request object with the account category object
   * <p>
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
	 *   <td>ACCT</td>
	 *   <td>{@link GlAccounts}</td>
	 *   <td>A GL Account object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTCATG</td>
	 *   <td>{@link GlAccountCategory}</td>
	 *   <td>A GL Acount Category object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTTYPE</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>GL Account Type object</td>
	 * </tr>   
	 *</table> 
	 *
   * @throws GLAcctException
   * @throws DatabaseException
   */
  public void editAction() throws GLAcctException, DatabaseException {
  	String method = "editAction";
  	String strRow = this.request.getParameter("selCbx");
  	int  row = 0;
  	GlAccounts obj = null;
  	GlAccountTypes acctType = null;
  	GlAccountCategory acctCatg = null;
  	  	
  	try {
  		row = Integer.valueOf(strRow).intValue();
  	}
  	catch (NumberFormatException e) {
  		throw new GLAcctException("Problem identifying selected row...GL Account could not be processed.", -1, this.className, method);
  	}
  	
  	try {
  		obj = GlAccountsFactory.create();
  		GlAccountsFactory.packageBean(this.request, obj, row);
  		
  		GLBasicApi api = AcctManagerFactory.createBasic(this.dbConn);
 			acctType = api.findAcctTypeById(obj.getAcctTypeId());
 			acctCatg = api.findAcctCatgById(obj.getAcctCatId());

 		this.request.setAttribute("ACCT", obj);
 		this.request.setAttribute("ACCTCATG", acctCatg);
  		this.request.setAttribute("ACCTTYPE", acctType);
      return;
  		
  	}
  	catch (SystemException e) {
  		throw new GLAcctException(e);
  	}
  }
  
  /**
   * Applies new and modified code groups and code details to the database for all like code tables.
   * <p>
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
	 *   <td>ACCT</td>
	 *   <td>{@link GlAccounts}</td>
	 *   <td>A GL Account object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTCATG</td>
	 *   <td>{@link GlAccountCategory}</td>
	 *   <td>A GL Acount Category object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTTYPE</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>GL Account Type object</td>
	 * </tr>
	 * <tr>
	 *   <td>MSG</td>
	 *   <td>String</td>
	 *   <td>Messgae denoting the result of this operation</td>
	 * </tr>       
	 *</table>  
	 *
   * @throws GLAcctException
   * @throws DatabaseException
   */
  public void saveAction() throws GLAcctException, DatabaseException {
	  	String method = "saveAction";
	  	GlAccounts acct = null;
	  	GlAccountTypes acctType = null;
	  	GlAccountCategory acctCatg = null;
	  	int  acctId;
	  	
	  	try {
	  		acct = GlAccountsFactory.create();
	  		GlAccountsFactory.packageBean(this.request, acct);
	  		
	  		GLBasicApi api = AcctManagerFactory.createBasic(this.dbConn);
	  		acctId = api.maintainAccount(acct);
	  		this.transObj.commitUOW();
	  		acct = api.findById(acctId);
			acctType = api.findAcctTypeById(acct.getAcctTypeId());
			acctCatg = api.findAcctCatgById(acct.getAcctCatId());
	
			this.request.setAttribute("ACCT", acct);
			this.request.setAttribute("ACCTCATG", acctCatg);
	  		this.request.setAttribute("ACCTTYPE", acctType);
	  		this.request.setAttribute("MSG", "Changes to this account were successfully save!");
	      return;
	  		
	  	}
	  	catch (SystemException e) {
	  		this.transObj.rollbackUOW();
	  		throw new GLAcctException(e);
	  	}
  }

  /**
   * Creates a new Account Category Bean to be used for adding a new Account Category to the system.
   * <p>
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
	 *   <td>ACCT</td>
	 *   <td>{@link GlAccounts}</td>
	 *   <td>A GL Account object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTCATG</td>
	 *   <td>{@link GlAccountCategory}</td>
	 *   <td>A GL Acount Category object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTTYPE</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>GL Account Type object</td>
	 * </tr>
	 *</table>
	 *   
   * @throws GLAcctException
   * @throws DatabaseException
   */
  public void addAction() throws GLAcctException, DatabaseException {

  	String method = "addAction";
  	int  row = 0;
  	int acctTypeId;
  	int acctCatgId;
  	String temp;
  	GlAccounts acct = null;
  	GlAccountTypes acctType = null;
  	GlAccountCategory acctCatg = null;
  	
  	try {
  		temp = this.request.getParameter("masterAcctTypeId");
  		acctTypeId = Integer.valueOf(temp).intValue();
  	}
  	catch (NumberFormatException e) {
  		acctTypeId = 0;
  	}
  	
  	try {
  		temp = this.request.getParameter("masterAcctCatgId");
  		acctCatgId = Integer.valueOf(temp).intValue();
  	}
  	catch (NumberFormatException e) {
  		acctCatgId = 0;
  	}
  	
  	try {
  		acct = GlAccountsFactory.create();
  		acct.setAcctCatId(acctCatgId);
  		acct.setAcctTypeId(acctTypeId);
  		
  		GLBasicApi api = AcctManagerFactory.createBasic(this.dbConn);
		acctType = api.findAcctTypeById(acct.getAcctTypeId());
		acctCatg = api.findAcctCatgById(acct.getAcctCatId());

		this.request.setAttribute("ACCT", acct);
		this.request.setAttribute("ACCTCATG", acctCatg);
  		this.request.setAttribute("ACCTTYPE", acctType);
      return;
  	}
  	catch (SystemException e) {
  		throw new GLAcctException(e);
  	}
  }
  
  /**
   * Overrides the anestor method in order refresh account type list after the selected account type is deleted.
   * 
   * @param ds Represents the name of the datasource to perform the deletion on
   * @param keyName The name of the HTML or JSP input control that contains the value of the primary key of  the target row. 
   * @return int - 1=Success, -1=Failure
   * @throws SystemException
   * @throws DatabaseException
   * @throws NotFoundException
   */
  public int deleteAction(String _ds, String _keyName) throws SystemException, DatabaseException, NotFoundException {
      super.deleteAction(_ds, _keyName);
      try {
          this.refreshAcctTypeList();    
          return RMT2Base.SUCCESS;
      }
      catch (GLAcctException e) {
          throw new SystemException(e);
      }
  }

  
  /**
   * Refreshes the list of Account Types the account type id stored in the request object as "masterAcctTypeId".
   * 
   * @throws GLAcctException
   */
  public void refreshAcctTypeList() throws GLAcctException {
      String method = "refreshAcctTypeList()";
		HttpServletRequest req = this.getRequest();
		GlAccountTypes  acctType;
	   String acctTypeIdStr = null;
	   String acctCatgIdStr = null;
	   int acctTypeId = 0;
	   int acctCatgId = 0;
	   List list;
	   GlAccountCategory acctCatg = null;
	  	

	   acctTypeIdStr = req.getParameter("masterAcctTypeId");
	   acctCatgIdStr = req.getParameter("masterAcctCatgId");
	   acctTypeId = (acctTypeIdStr == null ? 0 : Integer.parseInt(acctTypeIdStr)); 
	   acctCatgId = (acctCatgIdStr == null ? 0 : Integer.parseInt(acctCatgIdStr));
	   
	   acctCatg = api.findAcctCatgById(acctCatgId);
	   
	    // Get all GL Account based on GL Account Category and GL Account Type.
      list = api.findCombinedGlAcctTypeCatg(acctCatg.getAcctTypeId(), acctCatg.getId());
      if (list == null) {
      	list = new ArrayList();
      }

      // Send data basck to the client
      req.setAttribute("glAccounts", list);
      req.setAttribute("acctCatg", acctCatg);
	   
	   return;
  }
  
  
/**
 * Gathers all GL Accounts rlated to the current GL Account Type and GL Account Category
   * <p>
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
	 *   <td>ACCT</td>
	 *   <td>{@link GlAccounts}</td>
	 *   <td>A GL Account object</td>
	 * </tr>
	 * <tr>
	 *   <td>acctCatg</td>
	 *   <td>{@link GlAccountCategory}</td>
	 *   <td>A GL Acount Category object</td>
	 * </tr>
	 * <tr>
	 *   <td>glAccounts</td>
	 *   <td>{@link GlAccountTypeCatg}</td>
	 *   <td>Object containing GL Account, accout type and accout category data</td>
	 * </tr>
	 *</table> 
	 *
 * @throws GLAcctException
 * @throws DatabaseException
 */
public void backAction() throws GLAcctException, DatabaseException {

  String method = "backAction()";
	HttpServletRequest req = this.getRequest();
  String value = null;
  int     glAcctTypeId;
  int      rowNdx = 0;
  GlAccounts acct = null;
  GlAccountCategory acctCatg = null;
  List list;


  try {
		acct = GlAccountsFactory.create();
    AcctManagerFactory.packageBean(req, acct);

    list = api.findCombinedGlAcctTypeCatg(acct.getAcctTypeId(), acct.getAcctCatId());
    if (list == null) {
    	list = new ArrayList();
    }
    acctCatg = api.findAcctCatgById(acct.getAcctCatId());

    // Send data basck to the client
    req.setAttribute("glAccounts", list);
    req.setAttribute("acctCatg", acctCatg);

	  return;
  }
  catch (SystemException e) {
    throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
  }
}
  
  
/**
 * Validates the account bean.   If an error occurs throw a BusinessException exception.
 * 
 * @param _data Account category data which is of type {@link GlAccountCategory}.
 * @return int
 * @throws BusinessException
 */  
  protected int validateCategory(GlAccountCategory _data) throws BusinessException {

    this.methodName = "validateCategory";
    if (_data.getAcctTypeId() <= 0) {
		  throw new BusinessException(this.getDbConn(), 152, null);
    }
    if (_data.getDescription().length() <= 0) {
        throw new BusinessException(this.getDbConn(), 153, null);
    }

    return RMT2Base.SUCCESS;
  }

  protected void receiveClientData() throws ActionHandlerException{}
  protected void sendClientData() throws ActionHandlerException{}
  public void add() throws ActionHandlerException{}
  public void edit() throws ActionHandlerException{}
  public void save() throws ActionHandlerException{}
  public void delete() throws ActionHandlerException{}

}
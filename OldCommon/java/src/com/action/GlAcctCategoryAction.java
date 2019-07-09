package com.action;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.bean.Address;
import com.bean.Business;
import com.bean.Creditor;
import com.bean.GlAccountTypes;
import com.bean.GlAccountCategory;
import com.bean.Zipcode;

import com.api.GLBasicApi;
import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;
import com.api.DataSourceApi;

import com.action.AbstractActionHandler;

import com.bean.RMT2Base;
import com.bean.RMT2TagQueryBean;
import com.bean.GlAccountTypes;

import com.factory.AcctManagerFactory;
import com.factory.GlAccountsFactory;

import com.util.ContactBusinessException;
import com.util.SystemException;
import com.util.NotFoundException;
import com.util.BusinessException;
import com.util.GLAcctException;
import com.util.RMT2Utility;

import com.constants.RMT2SystemExceptionConst;
import com.constants.RMT2ServletConst;



/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating
 * General Ledger Account Categories.
 *
 * @author Roy Terrell
 *
 */
public class GlAcctCategoryAction extends AbstractActionHandler {

  private GLBasicApi api;
  private final String PAGE_TITLE = "Account Category Maintenance";


  /**
	* Main contructor for this action handler.
	*
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public GlAcctCategoryAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {

    super(_context, _request);
    this.className = "GlAcctCategoryAction";
    api = AcctManagerFactory.createBasic(this.dbConn);
  }

  /**
   * This action handler is used to retrieve all GL Account Types and return to the client.
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
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>A list of GL Account Type objects</td>
	 * </tr>
	 *</table>
	 *
   * @return int - 1 for success
   * @throws GLAcctException
   */
  public int startAction() throws GLAcctException {
      String method = "startAction()";
		HttpServletRequest req = this.getRequest();

		//  Retrieve all GL Account Types
		List list = api.findAcctType(null);
		req.setAttribute("acctTypes", list);
		return RMT2Base.SUCCESS;
  }


   /**
    * Obtains GL Account Type details and a list of related GL Account Category objects using the GL Account type id selected from the
    * Http request object's list of GL Account Types.   The data obtained is returned to the client through the request object to processed.
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
	 *   <td>acctType</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>A GL Account Type object</td>
	 * </tr>
	 * <tr>
	 *   <td>acctCatg</td>
	 *   <td>{@link GlAccountCategory}</td>
	 *   <td>A list of GL Account Category objects</td>
	 * </tr>
	 *</table>
	 *
    * @return int - Generally returns 1 for success.   Otherwise, the following exception is thrown.
    * @throws GLAcctException
    */
  public int getAccountCategoryAction() throws GLAcctException {
	    String method = "getAccountCategoryAction()";
		 HttpServletRequest req = this.getRequest();
	    String rowStr = req.getParameter("selCbx");
	    String value = null;
	    int      rowNdx = 0;
	    ArrayList list;
	
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
		
		     // Using account type id, get accout type object and a list of account 
		     // type categories and send results to the client via the request object
		     this.setupAcctTypeCatgList(req, acctType.getId());
			 return RMT2Base.SUCCESS;
		  }
	    catch (SystemException e) {
	        throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
	    }
  }

  /**
   * Refreshes the list of Account Type Categories the account type id stored in the request object as "masterAcctTypeId".
   * 
   * @throws GLAcctException
   */
  public void refreshCatgList() throws GLAcctException {
      String method = "refreshCatgList()";
		HttpServletRequest req = this.getRequest();
		GlAccountTypes  acctType;
	   String acctTypeId = null;
	   ArrayList list;

	   acctTypeId = req.getParameter("masterAcctTypeId");
	   
	   try {
	 	   // Indicate to the client that data was not available in the event account key data is not available.
	 	   if (acctTypeId == null) {
	 	       acctType = GlAccountsFactory.createAcctType();
	 	       list = new ArrayList();
	 	       req.setAttribute("acctType", acctType);
	 	       req.setAttribute("acctCatg", list);
	 	       return;
	 	   }	       
	   }
	    catch (SystemException e) {
	        throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
	    }
   
      // Using account type id, get accout type object and a list of account 
      // type categories and send results to the client via the request object
	   int acctTypeIdInt = new Integer(acctTypeId).intValue();
	   this.setupAcctTypeCatgList(req, acctTypeIdInt);
	   return;
  }
  
  /**
   * Initializes the HttpServletRequest object with GlAccountTypes objects and a list of account type category objects in preparation to send back to the client.
   * 
   * @param _req Request object used to send results to the client. 
   * @param _acctTypeId integer value representing the account type id.
   * @throws GLAcctException
   */
  private void setupAcctTypeCatgList(HttpServletRequest _req, int _acctTypeId) throws GLAcctException {
      GlAccountTypes  acctType;
      List list;
      
	   // Using the account type id, get account type details from the database related to account type id.
	   acctType = api.findAcctTypeById(_acctTypeId);
	
	   // Get all GL Account Categories belonging to account type id.
	   list = api.findAcctCatgByAcctType(acctType.getId());
	
	   // Send data basck to the client
	   _req.setAttribute("acctType", acctType);
	   _req.setAttribute("acctCatg", list);
  }

  
  /**
   * Overrides the anestor method in order refresh account type category list after the selected category is deleted.
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
          this.refreshCatgList();    
          return RMT2Base.SUCCESS;
      }
      catch (GLAcctException e) {
          throw new SystemException(e);
      }
  }
  
  /**
   *  Retrieves a row from a list of account categories that exist in the HTTP Request object, packages the
   *   data into a GlAccountCategory object, and initializes the request object with the account category object.
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
	 *   <td>ACCTTYPE</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>A GL Account Type object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTCATG</td>
	 *   <td>{@link GlAccountCategory}</td>
	 *   <td>A GL Account Category object</td>
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
  	GlAccountCategory obj = null;
  	GlAccountTypes acctType = null;


  	try {
  		row = Integer.valueOf(strRow).intValue();
  	}
  	catch (NumberFormatException e) {
  		throw new GLAcctException("Problem identifying selected row...GL Account could not be processed.", -1, this.className, method);
  	}

  	try {
  		obj = GlAccountsFactory.createCatg();
  		GlAccountsFactory.packageBean(this.request, obj, row);
  		if (obj.getAcctTypeId() > 0) {
  			GLBasicApi api = AcctManagerFactory.createBasic(this.dbConn);
  			acctType = api.findAcctTypeById(obj.getAcctTypeId());
  		}
  		this.request.setAttribute("ACCTCATG", obj);
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
	 *   <td>acctType</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>A GL Account Type object</td>
	 * </tr>
	 * <tr>
	 *   <td>acctCatg</td>
	 *   <td>ArrayList</td>
	 *   <td>A list of {@link GlAccountCategory} objects</td>
	 * </tr>
	 *</table>
	 *
	 * @return  int
	 * @throws GLAcctException
	 * @throws DatabaseException
	 */
  public int saveAction() throws GLAcctException, DatabaseException {

    String method = "saveAction";

    GlAccountCategory obj = null;

    try {
    	obj = GlAccountsFactory.createCatg(this.request);
    	if (obj.getDescription() == null) {
    		throw new GLAcctException(this.dbConn, 402, null);
    	}
    	GLBasicApi api = AcctManagerFactory.createBasic(this.dbConn);
    	int acctId = api.maintainAcctCatg(obj);
    	this.transObj.commitUOW();

      // Get all GL Account Categories belonging to account type id.
    	List list = api.findAcctCatgByAcctType(obj.getAcctTypeId());

      // Get Account Type
      GlAccountTypes acctType = api.findAcctTypeById(obj.getAcctTypeId());

      // Send data basck to the client
      this.request.setAttribute("acctType", acctType);
      this.request.setAttribute("acctCatg", list);
      return RMT2Base.SUCCESS;
    }  // end try

    catch (DatabaseException e) {
      this.transObj.rollbackUOW();
      throw e;
    }
    catch (SystemException e) {
    	this.transObj.rollbackUOW();
    	throw new GLAcctException(e);
    }
    catch (NumberFormatException e) {
				throw new GLAcctException(this.getDbConn(), 124, null);
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
	 *   <td>ACCTTYPE</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>A GL Account Type object</td>
	 * </tr>
	 * <tr>
	 *   <td>ACCTCATG</td>
	 *   <td> {@link GlAccountCategory}</td>
	 *   <td>A Gl Account Category object</td>
	 * </tr>
	 *</table>
   *
   * @throws GLAcctException
   * @throws DatabaseException
   */
public void addAction() throws GLAcctException, DatabaseException {

  	String method = "addAction";
  	String strAcctTypId = this.request.getParameter("masterAcctTypeId");
  	int acctTypeid = 0;
  	GlAccountCategory obj = null;
  	GlAccountTypes acctType = null;


  	try {
  		acctTypeid = Integer.valueOf(strAcctTypId).intValue();
  	}
  	catch (NumberFormatException e) {
  		throw new GLAcctException("Problem identifying selected row...GL Account could not be processed.", -1, this.className, method);
  	}

  	try {
  		obj = GlAccountsFactory.createCatg();
  		obj.setAcctTypeId(acctTypeid);
 			GLBasicApi api = AcctManagerFactory.createBasic(this.dbConn);
 			acctType = api.findAcctTypeById(acctTypeid);
  		this.request.setAttribute("ACCTCATG", obj);
  		this.request.setAttribute("ACCTTYPE", acctType);
      return;

  	}
  	catch (SystemException e) {
  		throw new GLAcctException(e);
  	}
  }

  /**
    * Cancels user's request to edit/add a GL Account Category record
    * <p>
    * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle at the discretion of the client:
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>acctType</td>
	 *   <td>{@link GlAccountTypes}</td>
	 *   <td>A GL Account Type object</td>
	 * </tr>
	 * <tr>
	 *   <td>acctCatg</td>
	 *   <td>ArrayList</td>
	 *   <td>A list of {@link GlAccountCategory} objects</td>
	 * </tr>
	 *</table>
	 *
	 * @throws GLAcctException
	 * @throws DatabaseException
	 */
public void cancelAction() throws GLAcctException, DatabaseException {

	String method = "cancelAction";
	String strAcctTypId = this.request.getParameter("AcctTypeId");
	int acctTypeid = 0;
	GlAccountTypes acctType = null;


	try {
		acctTypeid = Integer.valueOf(strAcctTypId).intValue();
	}
	catch (NumberFormatException e) {
		throw new GLAcctException("Problem identifying selected row...GL Account could not be processed.", -1, this.className, method);
	}

	try {
		GLBasicApi api = AcctManagerFactory.createBasic(this.dbConn);

    // Get all GL Account Categories belonging to account type id.
		List list = api.findAcctCatgByAcctType(acctTypeid);

    // Get Account Type
    acctType = api.findAcctTypeById(acctTypeid);

    // Send data basck to the client
    this.request.setAttribute("acctType", acctType);
    this.request.setAttribute("acctCatg", list);
    return;

	}
	catch (SystemException e) {
		throw new GLAcctException(e);
	}
}

/**
 * Validates the account bean.   If an error occurs throws a BusinessException exception.
 *
 * @param _data Represents the General Ledger Account Category data which is of type {@link GlAccountCategory}.
 * @return  int
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
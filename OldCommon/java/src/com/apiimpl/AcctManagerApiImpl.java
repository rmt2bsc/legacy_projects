package com.apiimpl;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


import com.api.GLBasicApi;
import com.api.GLCustomerApi;
import com.api.GLCreditorApi;

import com.api.db.DatabaseException;
import com.api.db.DynamicSqlApi;
import com.api.db.DynamicSqlFactory;

import com.api.db.orm.RdbmsDataSourceImpl;

import com.factory.GlAccountsFactory;

import com.util.GLAcctException;
import com.util.SystemException;
import com.util.CustomerException;
import com.util.CreditorException;
import com.util.NotFoundException;

import com.bean.RMT2Base;
import com.bean.CustomerWithName;
import com.bean.GlAccounts;
import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;
import com.bean.Customer;
import com.bean.Creditor;
import com.bean.CreditorType;

import com.bean.db.DatabaseConnectionBean;


/**
Api Implementation that manages the base General Ledger Accounts.
 */

public class AcctManagerApiImpl  extends RdbmsDataSourceImpl  implements GLBasicApi, GLCustomerApi, GLCreditorApi {
   protected String criteria;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: AcctManagerApiImpl
//    Prototype: DatabaseConnectionBean dbConn
//      Returns: void
//       Throws: DatabaseException, SystemException
//  Description: Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public AcctManagerApiImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException    {
    	super(dbConn);
    	this.className = "AcctManagerApiImpl";
    	this.baseView = "GlAccountsView";
    	this.baseBeanClass = "com.bean.GlAccounts";
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: RMT2DataSourceApiImpl
//    Prototype: DatabaseConnectionBean dbConn
//                       HttpServletRequest req
//      Returns: void
//       Throws: DatabaseException, SystemException
//  Description: Constructor begins the initialization of the DatabaseConnectionBean and the HttpServletRequest
//                         object at the acestor level.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public AcctManagerApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest req) throws SystemException, DatabaseException    {
      super(dbConn, req);
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createAccount
//    Prototype: void
//      Returns: GlAccounts
//       Throws: GLAcctException, SystemException
//  Description: Creates a new GlAccounts object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GlAccounts createAccount() throws SystemException, GLAcctException   {
       return GlAccountsFactory.create();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createAccount
//    Prototype: HttpServletRequest
//      Returns: GlAccounts
//       Throws: GLAcctException, SystemException
//  Description: Creates a new GlAccounts from a HttpServletRequest object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GlAccounts createAccount(HttpServletRequest req) throws SystemException, GLAcctException   {
       return null;
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findGlAcct
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Finds one or more GlAccounts objects using custom criteria supplied by the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public List findGlAcct(String _criteria) throws GLAcctException {
			String methodName = "findGlAcct";

			try {
			    List list = this.find(_criteria);
				return list;
			}
			catch (SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findById
//    Prototype: double
//      Returns: GlAccounts
//       Throws: GLAcctException
//  Description: Locates a GlAccounts object by primary key or it GL Account Id and returns GlAccounts object to
//                         the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public GlAccounts findById(int value) throws GLAcctException {
			String methodName = "findById";
			this.criteria = "id = " + value;
			try {
			    List list = this.find(this.criteria);
				if (list.size() == 0) {
					return null;
				}
				return (GlAccounts) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findByAcctNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates one or more GlAccounts objects based on the acct_no
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findByAcctNo(String value) throws GLAcctException   {

        String method = "findByAcctNo";
				try {
					this.criteria = "acct_no like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findByAcctName
//    Prototype: String
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates one or more GlAccounts objects based on the name property.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findByAcctName(String value) throws GLAcctException  {
      try {
					this.criteria = "name like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findByAcctNameExact
//    Prototype: String
//      Returns: GlAccounts
//       Throws: GLAcctException
//  Description: Locates an exact account and packages the account data into a GlAccunts object based on the value of
//                         "name".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GlAccounts  findByAcctNameExact(String value) throws GLAcctException  {
      try {
					this.criteria = "name = \'" + value + "\'";
					List list = this.find(this.criteria);
				  if (list.size() == 0) {
				    	return null;
				  }
					return (GlAccounts) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch(SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
   }

   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Method: findCombinedGlAcctTypeCatg
//Prototype: int Account Type id
//                int Account Category Id
//Returns: ArrayList
// Throws: GLAcctException
//Description: Locates all GL Accounts related to Account type and Account category.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
   public List findCombinedGlAcctTypeCatg(int _acctTypeid, int _acctCatgId) throws GLAcctException {
   	String methodName = "findCombinedGlAcctTypeCatg";
   	String prevBaseView = "GlAccountsView";
		String prevBaseBean = "com.bean.GlAccounts";
		
    try {
			this.baseView = "GlAccountTypeCatgView";
			this.baseBeanClass = "com.bean.GlAccountTypeCatg";
			this.criteria = "gl_accounts.acct_type_id = " + _acctTypeid + " and gl_accounts.acct_cat_id = " + _acctCatgId;
			List list = this.find(this.criteria);
		  if (list.size() == 0) {
		    	return null;
		  }
			return list;
			}
			catch(SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
      finally {
			  this.baseView = prevBaseView;
			  this.baseBeanClass = prevBaseBean;
		}
   }
   
   

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findByAcctType
//    Prototype: double
//      Returns: GlAccounts
//       Throws: GLAcctException
//  Description: Locates an Gl Account and packages the account data into a GlAccunts object based on the value of
//                         "acct_type_id"..
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findByAcctType(int value) throws GLAcctException  {

      try {
				this.criteria = "acct_type_id = " + value;
				List list = this.find(this.criteria);
				return list;
			}
			catch(SystemException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findByAcctCatg
//    Prototype: double
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates all Gl Account and packages the account data into a GlAccunts object based on the value of
//                         "acct_cat_id"..
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findByAcctCatg(int value) throws GLAcctException   {
			try {
				this.criteria = "acct_cat_id = " + value;
				List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
				throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findByAcctCatgName
//    Prototype: String
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates all Gl Account and packages the account data into a GlAccunts object based on the value of
//                         "glac.description "..
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findByAcctCatgName(String value) throws GLAcctException   {
			String prevBaseView = "GlAccountsView";;
			String prevBaseBean = "com.bean.GlAccounts";;

			try {
					this.baseView = "GlAccountsCatgNameView";
					this.baseBeanClass = "com.bean.GlAccountsCatgName";
				  this.criteria = "glac.description like \'" + value + "%\'";
				  List list = this.find(this.criteria);
				  return list;
			}
			catch (SystemException e) {
				  throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
      finally {
				  this.baseView = prevBaseView;
				  this.baseBeanClass = prevBaseBean;
			}
   }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//         Method: findAcctTypeById
//      Prototype: int
//        Returns: GlAccountTypes
//         Throws: GLAcctException
//  Description: Locates a Gl Account Type based on the value of  Account Type Id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GlAccountTypes findAcctTypeById(int value) throws GLAcctException   {

			String prevBaseView = "GlAccountsView";
			String prevBaseBean = "com.bean.GlAccounts";

			try {
					this.baseView = "GlAccountTypesView";
					this.baseBeanClass = "com.bean.GlAccountTypes";
				  this.criteria = "id = " + value;
				  List list = this.find(this.criteria);
				  if (list.size() == 0) {
				    	return null;
				  }
					return (GlAccountTypes) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
				  throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
      finally {
				  this.baseView = prevBaseView;
				  this.baseBeanClass = prevBaseBean;
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//         Method: findAcctType
//      Prototype: String _criteria
//        Returns: ArrayList
//         Throws: GLAcctException
//  Description: Locates one or more GL Account Types based on _criteria.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findAcctType(String _criteria) throws GLAcctException {
			String prevBaseView = "GlAccountsView";
			String prevBaseBean = "com.bean.GlAccounts";
			String method = "findAcctType";

			try {
					this.baseView = "GlAccountTypesView";
					this.baseBeanClass = "com.bean.GlAccountTypes";
				  this.criteria = _criteria;
				  List list = this.find(this.criteria);
				  if (list.size() == 0) {
				    	return null;
				  }
					return list;
			}
			catch (SystemException e) {
				  throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
			}
      finally {
				  this.baseView = prevBaseView;
				  this.baseBeanClass = prevBaseBean;
			}

	 }




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAcctCatgNameById
//    Prototype: String
//      Returns: GlAccountCategory
//       Throws: GLAcctException
//  Description: Locates a Gl Account Category and packages the account data into a GlAccountCategory object
//                         based on the value of  "id "..
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GlAccountCategory findAcctCatgById(int value) throws GLAcctException   {

			String prevBaseView = "GlAccountsView";;
			String prevBaseBean = "com.bean.GlAccounts";;

			try {
					this.baseView = "GlAccountCategoryView";
					this.baseBeanClass = "com.bean.GlAccountCategory";
				  this.criteria = "id = " + value;
				  List list = this.find(this.criteria);
				  if (list.size() == 0) {
				    	return null;
				  }
					return (GlAccountCategory) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
				  throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
      finally {
				  this.baseView = prevBaseView;
				  this.baseBeanClass = prevBaseBean;
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAcctCatgByAcctType
//    Prototype: int  Account Type Id
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates all Gl Account Categories that are associated with a particular account type id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  List findAcctCatgByAcctType(int _value) throws GLAcctException {
			String prevBaseView = "GlAccountsView";
			String prevBaseBean = "com.bean.GlAccounts";

			try {
				this.baseView = "GlAccountCategoryView";
				this.baseBeanClass = "com.bean.GlAccountCategory";
				this.criteria = "acct_type_id = " + _value;
				List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
				throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
      finally {
				this.baseView = prevBaseView;
				this.baseBeanClass = prevBaseBean;
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAcctCatgName
//    Prototype: String
//      Returns: GlAccountCategory
//       Throws: GLAcctException
//  Description: Locates all Gl Account Categories and packages the account data into a GlAccountCategory object
//                         based on the value of  the category's description.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findAcctCatgByName(String value) throws GLAcctException   {
			String prevBaseView = "GlAccountsView";;
			String prevBaseBean = "com.bean.GlAccounts";;

			try {
					this.baseView = "GlAccountCategoryView";
					this.baseBeanClass = "com.bean.GlAccountCategory";
				  this.criteria = "description like \'" + value + "%\'";
				  List list = this.find(this.criteria);
				  return list;
			}
			catch (SystemException e) {
				  throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
      finally {
				  this.baseView = prevBaseView;
				  this.baseBeanClass = prevBaseBean;
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAcctCatgNameExact
//    Prototype: String
//      Returns: GlAccountCategory
//       Throws: GLAcctException
//  Description: Locates a Gl Account Category and packages the account data into a GlAccountCategory object
//                         where the the category's description matches exactly "value".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public GlAccountCategory findAcctCatgByNameExact(String value) throws GLAcctException   {
			String prevBaseView = "GlAccountsView";;
			String prevBaseBean = "com.bean.GlAccounts";;
			String methodName = "findAcctCatgByNameExact";

			try {
					this.baseView = "GlAccountCategoryView";
					this.baseBeanClass = "com.bean.GlAccountCategory";
				  this.criteria = "description = \'" + value + "\'";
				  List list = this.find(this.criteria);
				  if (list.size() == 0) {
				    	return null;
				  }
					return (GlAccountCategory) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
				  throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
      finally {
				  this.baseView = prevBaseView;
				  this.baseBeanClass = prevBaseBean;
			}
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCreditorById
//    Prototype: int
//      Returns: Creditor
//       Throws: GLAcctException
//  Description: Locates a Creditor by creditor id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Creditor  findCreditorById(int _id) throws GLAcctException  {
			String methodName = "findCreditorById";
            this.setBaseClass("com.bean.Creditor");
            this.setBaseView("CreditorView");
			this.criteria = "id = " + _id;
			try {
			    List list = this.find(this.criteria);
				if (list.size() == 0) {
					return null;
				}
				return (Creditor) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCreditorByAcctNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates Creditor(s) by Account Number.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findCreditorByAcctNo(String _value) throws GLAcctException  {
			String methodName = "findCreditorByAcctNo";

			try {
				  this.criteria = "account_number like \'" + _value + "%\'";
				  List list = this.find(this.criteria);
					return list;
			}
			catch (SystemException e) {
				  throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCreditorByCreditorType
//    Prototype: int
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates Creditor(s) by creditor type id.    This is ususally when creditor type is vendor.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findCreditorByCreditorType(int _value) throws GLAcctException  {
			String methodName = "findCreditorByCreditorType";
			this.criteria = "creditor_type_id = " + _value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCreditorType
//    Prototype: String _criteria
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates one or more CreditorType objects using custom criteria supplied by client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findCreditorType(String _criteria) throws GLAcctException {
			String methodName = "findCreditorType";
			this.criteria = _criteria;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCreditorTypeById
//    Prototype: int _id
//      Returns: CreditorType
//       Throws: GLAcctException
//  Description: Locates a CreditorType object by its primary key value as "_id".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public CreditorType  findCreditorTypeById(int _id) throws GLAcctException {
			 String method = "findCreditorTypeById";
             this.setBaseClass("com.bean.CreditorType");
             this.setBaseView("CreditorTypeView");
			  this.criteria = "id = " + _id;
				try {
				    List list = this.find(this.criteria);
					if (list.size() == 0) {
						return null;
					}
					return (CreditorType) list.get(0);
				}
				catch (IndexOutOfBoundsException e) {
						return null;
				}
				catch (SystemException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
				}
	 }

   
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Method: findCreditor
//Prototype: String
//Returns: ArrayList
// Throws: GLAcctException
//Description: Locates a creditor from the creditor table using selection criteria which is built and supplied by
//                 the client.   This method expects that the  selection crieria is syntactically correct.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public List  findCreditor(String criteria) throws GLAcctException {
	String method = "findCreditor";
   
  this.baseView = "CreditorView";
  this.baseBeanClass =  "com.bean.Creditor";
  this.criteria = criteria;
	try {
	    List list = this.find(this.criteria);
		return list;
	}
	catch (SystemException e) {
			throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Method: findCreditorBusiness
//Prototype: String _criteria
//Returns: ArrayList
// Throws: GLAcctException
//Description: Locates a Creditor and related Business data using custom criteria".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findCreditorBusiness(String _criteria) throws GLAcctException {
   	
   	String method = "findCreditorBusiness";
		String prevBaseView = "GlAccountsView";
		String prevBaseBean = "com.bean.GlAccounts";

		try {
		  this.baseView = "VwCreditorBusinessView";
		  this.baseBeanClass =  "com.bean.VwCreditorBusiness";
		  this.criteria = _criteria;
		  List list = this.find(this.criteria);
			return list;
		}
		catch (SystemException e) {
				throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
		}
    finally {
		  this.baseView = prevBaseView;
		  this.baseBeanClass = prevBaseBean;
    }
   }
   
   
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Method: findCustomerBalance
//Prototype: int - customer id
//Returns: double
// Throws: CreditorException
//Description: Calculates the creditor's balance
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public double  findCreditorBalance(int _id) throws CreditorException {
       double amount = 0;
		 try {
				 dynaApi = DynamicSqlFactory.create(this.connector);
				 dynaApi.clearParms();
				 dynaApi.addParm("id", Types.INTEGER,  _id, false);
				 dynaApi.addParm("amount", Types.DOUBLE,  amount, true);

				 // Call stored procedure to obtain Customer balance
				 dynaApi.execute("exec usp_get_creditor_balance ??");
				 Object data = dynaApi.getOutParm("amount");
				 amount = ((Double) data).doubleValue();
				 return amount;
		 }
		 catch (DatabaseException e) {
		     System.out.println("[AcctManagerApiImpl.findCreditorBalance] Error obtaining balance" + e.getMessage());
			  throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
		 }
		 catch (SystemException e) {
		     System.out.println("[AcctManagerApiImpl.findCreditorBalance]  Error obtaining balance" + e.getMessage());
			  throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
		 }
 }

//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCustomerById
//    Prototype: int
//      Returns: Customer
//       Throws: GLAcctException
//  Description: Locates a customer from the customer table using the customer's id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Customer  findCustomerById(int _id) throws GLAcctException {
			 String method = "findCustomerById";
			  this.criteria = "id = " + _id;
				try {
				    List list = this.find(this.criteria);
					if (list.size() == 0) {
						return null;
					}
					return (Customer) list.get(0);
				}
				catch (IndexOutOfBoundsException e) {
						return null;
				}
				catch (SystemException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
				}
	 }


   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Method: findCustomerBalance
//Prototype: int - customer id
//Returns: double
// Throws: CustomerException
//Description: Calculates the customer's balance
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public double  findCustomerBalance(int _id) throws CustomerException {
       double amount = 0;
		 try {
				 dynaApi = DynamicSqlFactory.create(this.connector);
				 dynaApi.clearParms();
				 dynaApi.addParm("id", Types.INTEGER,  _id, false);
				 dynaApi.addParm("amount", Types.DOUBLE,  amount, true);

				 // Call stored procedure to obtain Customer balance
				 dynaApi.execute("exec usp_get_customer_balance ??");
				 Object data = dynaApi.getOutParm("amount");
				 amount = ((Double) data).doubleValue();
				 return amount;
		 }
		 catch (DatabaseException e) {
		     System.out.println("[AcctManagerApiImpl.findCustomerBalance] Error obtaining balance" + e.getMessage());
			  throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
		 }
		 catch (SystemException e) {
		     System.out.println("[AcctManagerApiImpl.findCustomerBalance]  Error obtaining balance" + e.getMessage());
			  throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
		 }
 }
   
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCustomerByPersonId
//    Prototype: int
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates a customers) from the customer table using the customer's person id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findCustomerByPersonId(int _value) throws GLAcctException {
			 String method = "findCustomerByPersonId";
			  this.criteria = "person_id = " + _value;
				try {
				    List list = this.find(this.criteria);
					return list;
				}
				catch (SystemException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
				}

	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCustomerByPersonId
//    Prototype: int
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates a customers) from the customer table using the customer's business id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findCustomerByBusinessId(int _value) throws GLAcctException {
			 String method = "findCustomerByBusinessId";
			  this.criteria = "business_id = " + _value;
				try {
				    List list = this.find(this.criteria);
					return list;
				}
				catch (SystemException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
				}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCustomer
//    Prototype: String
//      Returns: ArrayList
//       Throws: GLAcctException
//  Description: Locates a customer from the customer table using selection criteria which is built and supplied by
//                         the client.   This method expects that the  selection crieria is syntactically correct.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List  findCustomer(String criteria) throws GLAcctException {
			 String method = "findCustomer";
			  this.criteria = criteria;
				try {
				    List list = this.find(this.criteria);
					return list;
				}
				catch (SystemException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, method);
				}
	 }

   
   
   public CustomerWithName findCustomerWithName(int custId) throws GLAcctException {
       CustomerWithName cwn = null;
       List list = null;
       this.setBaseView("CustomerWithNameView");
       this.setBaseClass("com.bean.CustomerWithName");
       criteria = "id = " + custId;
       list = this.findCustomer(criteria);
       if (list.size() > 0) {
           cwn = (CustomerWithName) list.get(0);
       }
       return cwn;
   }
   
   public void listAccounts(String _view, String _criteria, String _order)   {

   }
   
   /**
    * Attempts to obtain the Customer Identifier that is stored in the HttpServletRequest object sent by the client JSP.
    * This method only expects the custsomer id to be identified as either "Id" or "CustomerId".  
    * 
    * @return int - Customer Id.
    * @throws GLAcctException
    * @throws NotFoundException
    */
   public int getHttpCustomerIdentifier() throws SystemException, NotFoundException {
       String custIdStr = null;
       String msg = null;
       int custId = 0;
       
		  try {
		      custIdStr = this.request.getParameter("Id");
		      if (custIdStr == null) {
		          custIdStr = this.request.getParameter("CustomerId");
		          if (custIdStr == null) {
		              msg = "Customer Id could not be obtained from the client's request";
						  System.out.println("[AcctManagerApiImpl.getCustomerIdentifierFromClient] " + msg);
						  throw new NotFoundException(msg);    
		          }
				}
				custId = Integer.parseInt(custIdStr);
				return custId;
		  }
		  catch (NumberFormatException e) {
		      msg = "NumberFormatException - Problem converting customer id to a number:  " + custIdStr;
				System.out.println("[AcctManagerApiImpl.getCustomerIdentifierFromClient] " + msg);
			   throw new SystemException(e);
		  }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainAccount
//    Prototype: GlAccounts
//      Returns: int (GL Account  Id)
//       Throws: GLAcctException
//  Description: This method is responsible for creating and updating General Ledger Accounts.   If GlAccounts.id
//                          is null then an account must be created.  If GlAccount.id has a  value, then apply updates to that
//                          account.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainAccount(GlAccounts _base)  throws GLAcctException {

			 String methodName = "maintainAccount";

			 try {
						if (_base.getId() == 0) {
								this.insertAccount(_base);
						}
						else  {
								this.updateAccount(_base);
						}
						return _base.getId();
				}
				catch(DatabaseException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
				}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: insertAccount
//      Prototype: GlAccounts
//        Returns: GlAccounts
//         Throws: GLAcctException
//  Description: This method is responsible for adding GL Accounts to the systems.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected GlAccounts insertAccount(GlAccounts _base) throws DatabaseException, GLAcctException   {

      String methodName = "insertAccount";
			String  acctTypeCatgIdParm = null;
			int        idParm = 0;
			String  acctName;
			int        newId;
			int        newSeqId;
			String  newAcctNo;
			Object newIdObj;
		  DynamicSqlApi  dynaApi = null;

			   	 // Check that account type and account category exist.
 		   this.validateAcctCatg(_base);

					 // Ensure that GL Account Name has a value.   Otherwise, throw an exception.
			 acctName = _base.getName();
			 if (acctName == null || acctName.length() <= 0) {
					 throw new GLAcctException(this.connector, 401, null);
			}

 		       //  Determine if GL Account Name is not Duplicated
 		  if (this.findByAcctNameExact(_base.getName()) != null) {
					throw new GLAcctException(this.connector, 404, null);
			}

			try {
					 newId = 0;
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add GL Account to the database.
           idParm = 0;
           acctTypeCatgIdParm = String.valueOf(_base.getAcctTypeId());
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  idParm, true);
           dynaApi.addParm("acct_type_id", Types.INTEGER,  acctTypeCatgIdParm, false);
           dynaApi.addParm("acct_catg_id", Types.INTEGER,  _base.getAcctCatId(), false);
           dynaApi.addParm("acct_seq", Types.INTEGER,  _base.getAcctSeq(), true);
           dynaApi.addParm("acct_no", Types.VARCHAR,  _base.getAcctNo(), true);
           dynaApi.addParm("name", Types.VARCHAR,  _base.getName(), false);
           dynaApi.addParm("descr", Types.VARCHAR,  _base.getDescription(), false);

  							 // Call stored procedure to add GL Account  to the database
  							 // using  gl account id, account category description.
           dynaApi.execute("exec usp_add_acct ? ? ? ? ? ? ?");

                 //  Get new Account Category  Id
           newIdObj = dynaApi.getOutParm("id");
           if (newIdObj instanceof Integer) {
							 newId = ((Integer) newIdObj).intValue();
							 _base.setId(newId);
					 }

					       //  Get new Account Sequence Number
					 newIdObj = dynaApi.getOutParm("acct_seq");
           if (newIdObj instanceof Integer) {
							 newSeqId = ((Integer) newIdObj).intValue();
							 _base.setAcctSeq(newSeqId);
					 }

					        //  Get new Account Number
					 newIdObj = dynaApi.getOutParm("acct_no");
           if (newIdObj instanceof String) {
							 newAcctNo = newIdObj.toString();
							 _base.setAcctNo(newAcctNo);
					 }

					 return _base;
			 }
			 catch (DatabaseException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
			 catch (SystemException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: updateAccount
//      Prototype: GlAccounts
//        Returns: GlAccounts
//         Throws: GLAcctException
//  Description: This method is responsible for updating a GL Account.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateAccount(GlAccounts _base) throws DatabaseException, GLAcctException   {
			String  acctName;
			String sql;
		  DynamicSqlApi  dynaApi = null;

			// Check that account  exist.
 		  if ( this.findById(_base.getId()) == null) {
					this.msgArgs.clear();
					this.msgArgs.add(String.valueOf(_base.getId()));
			 	  throw new GLAcctException(this.connector, 422, this.msgArgs);
			}

			 // Ensure that GL Account Name has a value.   Otherwise, throw an exception.
			 acctName = _base.getName();
			 if (acctName == null || acctName.length() <= 0) {
					 throw new GLAcctException(this.connector, 423, null);
			}

 		  //  Determine if GL Account Name is not Duplicated
			sql = "name = \'" + _base.getName() + "\' and id <> " + _base.getId();
			List list = this.findGlAcct(sql);
			
 		  if (list != null && list.size() > 0) {
					throw new GLAcctException(this.connector, 404, null);
			}

			try {
           //  Update GL Account
           dynaApi = DynamicSqlFactory.create(this.connector);
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _base.getId(), false);
           dynaApi.addParm("name", Types.VARCHAR,  _base.getName(), false);
           dynaApi.addParm("descr", Types.VARCHAR,  _base.getDescription(), false);

  							 // Call stored procedure to update GL Account  to the database using  gl account id.
           dynaApi.execute("exec usp_upd_acct ? ? ?");
					 return RMT2Base.SUCCESS;
			 }
			 catch (DatabaseException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
			 catch (SystemException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: deleteAccount
//    Prototype: int
//      Returns: int
//       Throws: GLAcctException
//  Description: Deletes a GL Account
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int deleteAccount(int  _acctId) throws GLAcctException  {
			 DynamicSqlApi  dynaApi = null;

			try {
           dynaApi = DynamicSqlFactory.create(this.connector);
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _acctId, false);

  							 // Call stored procedure to delete GL Account  to the database using  gl account id.
           dynaApi.execute("exec usp_del_acct ?");
					 return RMT2Base.SUCCESS;
			 }
			 catch (DatabaseException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
			 catch (SystemException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
   }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: buildAccountNo
//    Prototype: String
//      Returns: GlAccounts
//       Throws: GLAcctException
//  Description: Locates a Gl Account Category and packages the account data into a GlAccountCategory object
//                         where the the category's description matches exactly "value".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected String buildAccountNo(GlAccounts _acct) throws GLAcctException   {

				String result = "";
				String temp = "";
				int    seq = _acct.getAcctSeq();
				int acctType = _acct.getAcctTypeId();
				int acctCat = _acct.getAcctCatId();


						 //  Validate Data Values
				if (acctType <= 0)  {
						throw new GLAcctException(this.connector, 406, null);
			 }
			 if (acctCat <= 0)  {
						throw new GLAcctException(this.connector, 407, null);
			 }
			 if (seq <= 0)  {
						throw new GLAcctException(this.connector, 408, null);
			 }

						 //  Compute GL Account Number using the Account Type Id, Account Catgegory Id, and Account Sequence Number
				result =  acctType + "-" + acctCat + "-";
				if (seq >= 1 && seq <= 9) {
						temp = "00" + seq;
				}
				if (seq > 9 && seq <= 99) {
  					temp = "0" + seq;
				}
				if (seq >99 && seq <= 999) {
  					temp = String.valueOf(seq);
				}
				result += temp;
				_acct.setAcctNo(result);

				return result;
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: isAccountInUse
//    Prototype: int (GL Account Id)
//      Returns: boolean
//       Throws: GLAcctException
//  Description: Determines if GL Account is tied to other areas of the database, or referenced by other tables.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public boolean isAccountInUse(int _acctId) throws GLAcctException {
      boolean   acctInUse = false;
      int              cnt = 0;
      DynamicSqlApi  dynaApi = null;

			try {
                 //  Add GL Account to the database.
           dynaApi = DynamicSqlFactory.create(this.connector);
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _acctId, false);

  							 // Call stored function to update GL Account  to the database using  gl account id.
           ResultSet rs = dynaApi.execute("select ufn_get_acct_usage_count(?) count");

                //   ResultSet is null if stored function did not execure as expected.
           if (rs == null) {
							 return acctInUse;
					 }

					     //  Get count provided a row exist in "rs".
					 if (rs.next()) {
							 cnt = rs.getInt("count");
							 acctInUse = (cnt > 0);
					 }
					 return acctInUse;
			 }
			 catch (DatabaseException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), className, this.methodName);
			 }
			 catch (SystemException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), className, this.methodName);
			 }
			 catch (SQLException e) {
					 throw new GLAcctException(e.getMessage(), -1, this.className, methodName);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getAcctAssociationCount
//    Prototype: int (GL Account Id)
//      Returns: int
//       Throws: GLAcctException
//  Description: Obtains total count of GL Account  to trasaction associations based on _acctId and the value of
//                         funcName.   funcName should be a string reference to a astored function in the database that
//                         retrieves the count of Account - to - xxx associations.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int getAcctAssociationCount(int _acctId, String funcName) throws GLAcctException   {
      StringBuffer sql = new StringBuffer(100);;
      int              cnt = 0;
      DynamicSqlApi  dynaApi = null;

      if (funcName == null || funcName.length() <= 0) {
					throw new GLAcctException(this.connector, 411, null);
			}

			try {
                 //  Add GL Account to the database.
           dynaApi = DynamicSqlFactory.create(this.connector);
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _acctId, false);

  							 // Call stored function to obtain total count of GL Account  to trasaction associations.
  				 sql.append("select ");
  				 sql.append(funcName);
  				 sql.append(" (?) count");
           ResultSet rs = dynaApi.execute(sql.toString());

                //   ResultSet is null if stored function did not execure as expected.
           if (rs == null) {
							 return cnt;
					 }

					     //  Get count provided a row exist in "rs".
					 if (rs.next()) {
							 cnt = rs.getInt("count");
					 }
					 return cnt;
			 }
			 catch (DatabaseException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), className, this.methodName);
			 }
			 catch (SystemException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), className, this.methodName);
			 }
			 catch (SQLException e) {
					 throw new GLAcctException(e.getMessage(), -1, this.className, methodName);
			 }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getAcctToXactCount
//    Prototype: int (GL Account Id)
//      Returns: int
//       Throws: GLAcctException
//  Description: Obtains total count of GL Account  to transaction associations.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int getAcctToXactCount(int _acctId) throws GLAcctException   {
      return this.getAcctAssociationCount(_acctId, "ufn_get_acct_to_xact_count");
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getAcctToItemMastCount
//    Prototype: int (GL Account Id)
//      Returns: int
//       Throws: GLAcctException
//  Description: Obtains total count of GL Account  to Item Master associations.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int getAcctToItemMastCount(int _acctId) throws GLAcctException   {
      return this.getAcctAssociationCount(_acctId, "ufn_get_acct_to_xact_count");
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: getAcctToSubsidiaryCount
//    Prototype: int (GL Account Id)
//      Returns: int
//       Throws: GLAcctException
//  Description: Obtains total count of GL Account  to Subsidiary associations.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int getAcctToSubsidiaryCount(int _acctId) throws GLAcctException   {
      return this.getAcctAssociationCount(_acctId, "ufn_get_acct_to_xact_count");
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainAcctCatg
//    Prototype: GlAccountCategory
//      Returns: double (GL Account Category Type Id)
//       Throws: GLAcctException
//  Description: This method is responsible for creating and updating General Ledger Account Categories.
//                          If GlAccountCategory.id is null then an account category must be created.  If GlAccountCategory.id
//                          has a value, then apply updates to that account category.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainAcctCatg(GlAccountCategory _base) throws GLAcctException  {

        try {
						if (_base.getId() == 0) {
								this.insertAcctCatg(_base);
						}
						else  {
								this.updateAcctCatg(_base);
						}
						return _base.getId();
				}
				catch(DatabaseException e) {
						throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, methodName);
				}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: insertAcctCatg
//    Prototype: GlAccountCategory _base
//      Returns: GlAccountCategory
//       Throws: GLAcctException, DatabaseException
//  Description: Adds an account category to the database.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected GlAccountCategory insertAcctCatg(GlAccountCategory _base) throws DatabaseException, GLAcctException  {
			 String       catgName;
			 int             newId;
			 DynamicSqlApi  dynaApi = null;
			 Object     newIdObj;
       boolean  acctTypeExist;
			 try {
					     // Check that account type exist.
					 acctTypeExist = this.validateAcctType(_base.getAcctTypeId());
					 if (!acctTypeExist) {
							 throw new GLAcctException(this.connector, 405, null);
					}

					     // Ensure that Category Name has a value.   Otherwise, throw an exception.
					 catgName = _base.getDescription();
					 if (catgName == null || catgName.length() <= 0) {
							 throw new GLAcctException(this.connector, 402, null);
					}

					     //  Throw an exception if Category Name is duplicated.
				  if (this.findAcctCatgByNameExact(catgName) != null) {
							 throw new GLAcctException(this.connector, 403, null);
					 }

					 newId = 0;
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add parms as needed.
           dynaApi.addParm("id", Types.INTEGER,  _base.getId(), true);
           dynaApi.addParm("acct_type_id", Types.INTEGER,  _base.getAcctTypeId(), false);
           dynaApi.addParm("description", Types.VARCHAR,  _base.getDescription(), false);

  							 // Call stored procedure to add account category to the database
  							 // using  gl account id, account category description.
           dynaApi.execute("exec usp_add_acct_catg_type ? ? ?");

                 //  Get new Account Category  Id
           newIdObj = dynaApi.getOutParm("id");
           if (newIdObj instanceof Integer) {
							 newId = ((Integer) newIdObj).intValue();
							 _base.setId(newId);
							 return _base;
					 }

					 return null;
			 }
			 catch (DatabaseException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
			 catch (SystemException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: updateAcctCatg
//      Prototype: GlAccountCategory _base
//        Returns: int
//         Throws: GLAcctException,DatabaseException
//  Description: Updates the description of a GL Account Category.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateAcctCatg(GlAccountCategory _base) throws DatabaseException, GLAcctException  {
			 String       catgName;
			 String       idParm;
			 DynamicSqlApi  dynaApi = null;
			 try {
					     // Check that account category exist.
					 if ( this.findAcctCatgById(_base.getId()) == null ) {
							 throw new GLAcctException(this.connector, 409, null);
					}

					     // Ensure that Category Name has a value.   Otherwise, throw an exception.
					 catgName = _base.getDescription();
					 if (catgName == null || catgName.length() <= 0) {
							 throw new GLAcctException(this.connector, 402, null);
					}

					     //  Throw an exception if Category Name is duplicated.
				  if (this.findAcctCatgByNameExact(catgName) != null) {
							 throw new GLAcctException(this.connector, 403, null);
					 }

           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add parms as needed.
           idParm = String.valueOf(_base.getId());
           dynaApi.addParm("id", Types.INTEGER,  idParm, false);
           dynaApi.addParm("description", Types.VARCHAR,  _base.getDescription(), false);

  							 // Call stored procedure to add account category to the database
  							 // using  gl account id, account category description.
           dynaApi.execute("exec usp_upd_acct_catg_type ? ?");
					 return RMT2Base.SUCCESS;
			 }
			 catch (DatabaseException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
			 catch (SystemException e) {
					 throw new GLAcctException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: validateAcctType
//    Prototype: int (GL Account Type Id)
//      Returns: boolean
//       Throws: GLAcctException
//  Description: Validates _glAcctTypeId as a valid account type.    Returns true is valid.   Otherwise, returns false.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private boolean validateAcctType(int _glAcctTypeId) throws GLAcctException  {
			 GlAccountTypes  glAcctType;
			 glAcctType = this.findAcctTypeById(_glAcctTypeId);
			 return (glAcctType != null);
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: validateAcctCatg
//    Prototype: int (GL Account Type Id)
//                       int (GL Account Type Category Id)
//      Returns: boolean
//       Throws: GLAcctException
//  Description: Validates _glAcctTypeId and _glAcctCatgId and ensures that the _glAcctTypeId is equal to the GL
//                         Account Id that is associated with GL Account Type Id
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private boolean validateAcctCatg(GlAccounts _glAcct) throws GLAcctException  {
			 GlAccountCategory  catgBean;
			 // Check that account category exist.
			 catgBean = this.findAcctCatgById(_glAcct.getAcctCatId());
			 if ( catgBean == null ) {
					 throw new GLAcctException(this.connector, 409, null);
			 }
			 if (_glAcct.getAcctTypeId() != catgBean.getAcctTypeId()) {
					 throw new GLAcctException(this.connector, 405, null);
			}
			return true;
   }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createCreditor
//    Prototype: void
//      Returns: Creditor
//       Throws: SystemException
//  Description: Creates a new Creditor object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Creditor createCreditor() throws SystemException   {
       return GlAccountsFactory.createCreditor();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createCreditor
//    Prototype: HttpServletRequest
//      Returns: Creditor
//       Throws:  SystemException
//  Description: Creates a new Creditor from a HttpServletRequest object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Creditor createCreditor(HttpServletRequest req) throws SystemException   {
       return null;
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainCreditor
//    Prototype: Creditor - Base Creditor object
//                       Object - Creditor details (Application Specific)
//      Returns: int
//       Throws:  CreditorException
//  Description: Initiates the creation or maintenacne of the base creditor object.   Assign null to _credExt if the
//                         application does not need to extend the creditor profile.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainCreditor(Creditor _credBase, Object _credExt) throws CreditorException  {
				if (_credBase.getId() == 0) {
						this.insertCreditor( _credBase, _credExt);
				}
				else  {
						this.updateCreditor( _credBase, _credExt);
				}
				return _credBase.getId();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainCreditor
//    Prototype: Creditor - Base Creditor object
//                       Object - Creditor details (Application Specific)
//      Returns: int
//       Throws:  CreditorException
//  Description: Creates a creditor entity.   Also capable of handling application specific creditor details via _credExt
//                          object
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Creditor insertCreditor(Creditor _base, Object _credExt) throws CreditorException  {
  		 int             newId;
  		 String       newAcctNo;
  		 DynamicSqlApi  dynaApi = null;
  		 Object     newIdObj;


       // Perform validations for inserting a creditor.
		 if ( _base ==  null ) {
			 throw new CreditorException(this.connector, 413, null);
		 }

       //  Begin to add the creditor to the system.
       try {
           newId = 0;
           dynaApi = DynamicSqlFactory.create(this.connector);

           //  Add parms as needed.
           dynaApi.addParm("id", Types.INTEGER,  0, true);
           dynaApi.addParm("gl_account_id", Types.INTEGER,  _base.getGlAccountId(), false);
           dynaApi.addParm("business_id", Types.INTEGER,  _base.getBusinessId(), false);
           dynaApi.addParm("creditor_type_id", Types.INTEGER,  _base.getCreditorTypeId(), false);
           dynaApi.addParm("account_number", Types.VARCHAR,  _base.getAccountNumber(), true);
           dynaApi.addParm("credit_limit", Types.DOUBLE,  _base.getCreditLimit(), false);
           dynaApi.addParm("apr", Types.DOUBLE,  _base.getApr(), false);

			  // Call stored procedure to add account category to the database
			  // using  gl account id, account category description.
           dynaApi.execute("exec usp_add_creditor ? ? ? ? ? ? ?");

           //  Get new Account Category  Id
           newIdObj = dynaApi.getOutParm("id");
           if (newIdObj instanceof Integer) {
               newId = ((Integer) newIdObj).intValue();
				   _base.setId(newId);
			  }
           //  Get and set account number
           newIdObj = dynaApi.getOutParm("account_number");
           if (newIdObj instanceof String) {
			   	newAcctNo = newIdObj.toString();
					_base.setAccountNumber(newAcctNo);
			  }

			  return _base;
			 }
			 catch (DatabaseException e) {
					 throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
			 catch (SystemException e) {
					 throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateCreditor
//    Prototype: Creditor - Base Creditor object
//                       Object - Creditor Exention (Application Specific)
//      Returns: int
//       Throws:  CreditorException
//  Description: UPdates the credlit limt and APR of the creditor's profile.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Creditor updateCreditor(Creditor _base, Object _credExt) throws CreditorException  {

			 String method = "updateCreditor";
  		 DynamicSqlApi  dynaApi = null;

              // Perform validations for inserting a creditor.
			 if ( _base ==  null ) {
					 throw new CreditorException(this.connector, 413, null);
			}

          //  Begin to add the creditor to the system.
      try {
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add parms as needed.
           dynaApi.addParm("id", Types.INTEGER,  _base.getId(), false);
           dynaApi.addParm("credit_limit", Types.DOUBLE,  _base.getCreditLimit(), false);
           dynaApi.addParm("apr", Types.DOUBLE,  _base.getApr(), false);

  							 // Call stored procedure to update creditor to the database
           dynaApi.execute("exec usp_upd_creditor ? ? ? ");
					 return _base;
			 }
			 catch (DatabaseException e) {
					 throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }


	 }

   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 //       Method: deleteCreditor
	 //    Prototype: int
	 //      Returns: int
	 //       Throws: CreditorException
	 //  Description: Initiates the request to delete an Customer.
	 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			 public int deleteCreditor(int _credId) throws CreditorException {
						DynamicSqlApi  dynaApi = null;

						 try {
									 dynaApi = DynamicSqlFactory.create(this.connector);
									 dynaApi.clearParms();
									 dynaApi.addParm("id", Types.INTEGER,  _credId, false);

												 // Call stored procedure to delete Creditor to the database
									 dynaApi.execute("exec usp_del_creditor ?");
									 return RMT2Base.SUCCESS;
							 }
							 catch (DatabaseException e) {
									 throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
							 }
							 catch (SystemException e) {
									 throw new CreditorException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
							 }
			 }


			 public void validateCreditor(Creditor _base, Object _credExt) throws CreditorException  {
			  
			 	  if (_base.getCreditorTypeId() == 0) {
			 	  	throw new CreditorException(this.connector, 425, null);
			 	  }
			 }
			 

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createCustomer
//    Prototype: void
//      Returns: Customer
//       Throws: SystemException
//  Description: Creates a new Customer object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Customer createCustomer() throws SystemException   {
       return GlAccountsFactory.createCustomer();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createCustomer
//    Prototype: HttpServletRequest
//      Returns: Customer
//       Throws:  SystemException
//  Description: Creates a new Customer from a HttpServletRequest object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Customer createCustomer(HttpServletRequest req) throws SystemException   {
       return null;
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainCustomer
//    Prototype: HttpServletRequest
//      Returns: int
//       Throws:  SystemException
//  Description: This method initiates the process of creating or updating a customer record.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainCustomer(Customer _custBase, Object _custExt)  throws CustomerException   {
				if (_custBase.getId() == 0) {
						this.insertCustomer( _custBase, _custExt);
				}
				else  {
						this.updateCustomer( _custBase, _custExt);
				}
				return _custBase.getId();

   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: insertCustomer
//    Prototype: Customer
//                       Object
//      Returns: int
//       Throws:  SystemException
//  Description: This method is responsible for creating a customer record.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int insertCustomer(Customer _base, Object _custExt) throws CustomerException  {

			 String method = "insertCustomer";
  		 int             newId;
  		 DynamicSqlApi  dynaApi = null;
  		 Object     newIdObj;

      this.validateCustomer(_base);

							//  Begin to add the creditor to the system.
				try {
					 newId = 0;
					 dynaApi = DynamicSqlFactory.create(this.connector);

								 //  Add parms as needed.
					 dynaApi.addParm("id", Types.INTEGER,  0, true);
					 dynaApi.addParm("gl_account_id", Types.INTEGER,  _base.getGlAccountId(), false);
					 dynaApi.addParm("person_id", Types.INTEGER,  _base.getPersonId(), false);
					 dynaApi.addParm("business_id", Types.INTEGER,  _base.getBusinessId(), false);
					 dynaApi.addParm("credit_limit", Types.DOUBLE,  _base.getCreditLimit(), false);
					 dynaApi.addParm("acct_no", Types.VARCHAR,  _base.getAccountNo(), false);

								 // Call stored procedure to add base customer to the database
					 dynaApi.execute("exec usp_add_customer ? ? ? ? ? ?");

								 //  Get new Customer
					 newIdObj = dynaApi.getOutParm("id");
					 if (newIdObj instanceof Integer) {
							 newId = ((Integer) newIdObj).intValue();
							 _base.setId(newId);
					 }

					 return RMT2Base.SUCCESS;
			 }
			 catch (DatabaseException e) {
					 throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: updateCustomer
//    Prototype: Customer
//                       Object
//      Returns: int
//       Throws:  SystemException
//  Description: This method is responsible for updating a customer record.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected int updateCustomer(Customer _base, Object _custExt) throws CustomerException  {

			 String method = "updateCustomer";
  		 DynamicSqlApi  dynaApi = null;

       this.validateCustomer(_base);

							//  Begin to add the creditor to the system.
				try {
					 dynaApi = DynamicSqlFactory.create(this.connector);

								 //  Add parms as needed.
					 dynaApi.addParm("id", Types.INTEGER,  _base.getId(), false);
					 dynaApi.addParm("person_id", Types.INTEGER,  _base.getPersonId(), false);
					 dynaApi.addParm("business_id", Types.INTEGER,  _base.getBusinessId(), false);
					 dynaApi.addParm("credit_limit", Types.DOUBLE,  _base.getCreditLimit(), false);

								 // Call stored procedure to add base customer to the database
					 dynaApi.execute("exec usp_upd_customer ? ? ? ?");
					 return RMT2Base.SUCCESS;
			 }
			 catch (DatabaseException e) {
					 throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: validateCustomer
//    Prototype: Customer
//      Returns: int
//       Throws:  SystemException
//  Description: This method is responsible for validating a customer object.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 public void validateCustomer(Customer _cust) throws CustomerException {
			 //  Customer must be a person or a business
			 if (_cust.getPersonId() <= 0 && _cust.getBusinessId() <= 0) {
					 throw new CustomerException(this.connector, 421, null);
			 }
			 // personal id and business id are mutually exclusive.
			 if (_cust.getPersonId() > 0 && _cust.getBusinessId() > 0) {
				 throw new CustomerException(this.connector, 421, null);
  		 }			 
			 
			 return;
	 }


   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 //       Method: deleteCustomer
	 //    Prototype: int
	 //      Returns: int
	 //       Throws: CustomerException
	 //  Description: Initiates the request to delete an Customer.
	 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	     public int deleteCustomer(int _custId) throws CustomerException {
	   			DynamicSqlApi  dynaApi = null;

	  				 try {
	 						 dynaApi = DynamicSqlFactory.create(this.connector);
	 						 dynaApi.clearParms();
	 						 dynaApi.addParm("id", Types.INTEGER,  _custId, false);

	 									 // Call stored procedure to delete Customer to the database
	 						 dynaApi.execute("exec usp_del_customer ?");
	 						 return RMT2Base.SUCCESS;
	 				 }
	 				 catch (DatabaseException e) {
	 						 throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
	 				 }
	 				 catch (SystemException e) {
	 						 throw new CustomerException(e.getMessage(), e.getErrorCode(), this.className, this.methodName);
	 				 }
		}



}


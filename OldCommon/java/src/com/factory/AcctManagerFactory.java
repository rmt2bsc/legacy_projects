package com.factory;

import com.bean.Creditor;
import com.bean.Customer;
import com.bean.VwCustomerName;
import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;
import com.bean.GlAccounts;
import com.bean.ItemMaster;
import com.bean.VwItemMaster;
import com.bean.ItemMasterStatusHist;
import com.bean.db.DatabaseConnectionBean;

import com.api.GLBasicApi;
import com.api.GLCustomerApi;
import com.api.GLCreditorApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.AcctManagerApiImpl;

import com.util.SystemException;

import javax.servlet.http.HttpServletRequest;



public class AcctManagerFactory extends DataSourceAdapter {

  public static GLBasicApi createBasic(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     GLBasicApi api = new AcctManagerApiImpl(_dbo);
     api.setBaseView("GlAccountsView");
     api.setBaseClass("com.bean.GlAccounts");
     return api;
  }

  public static GLBasicApi createBasic(DatabaseConnectionBean _dbo, HttpServletRequest _req) throws SystemException, DatabaseException {
	     GLBasicApi api = new AcctManagerApiImpl(_dbo, _req);
	     api.setBaseView("GlAccountsView");
	     api.setBaseClass("com.bean.GlAccounts");
	     return api;
	  }
  
  public static GLBasicApi create(DatabaseConnectionBean _dbo, HttpServletRequest _req) throws SystemException, DatabaseException {
      GLBasicApi api = new AcctManagerApiImpl(_dbo, _req);
      api.setBaseView("GlAccountsView");
      api.setBaseClass("com.bean.GlAccounts");
      return api;
   }

  public static GLCustomerApi createCustomer(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
      GLCustomerApi api = new AcctManagerApiImpl(_dbo);
      api.setBaseView("CustomerView");
      api.setBaseClass("com.bean.Customer");
	   return api;
  }
  
  public static GLCustomerApi createCustomer(DatabaseConnectionBean _dbo, HttpServletRequest _request) throws SystemException, DatabaseException {
      GLCustomerApi api = new AcctManagerApiImpl(_dbo, _request);
      api.setBaseView("CustomerView");
      api.setBaseClass("com.bean.Customer");
	   return api;
  }

  public static GLCreditorApi createCreditor(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
      GLCreditorApi api = new AcctManagerApiImpl(_dbo);
      api.setBaseView("CreditorView");
      api.setBaseClass("com.bean.Creditor");
      return api;
   }

  public static GLCreditorApi createCreditor(DatabaseConnectionBean _dbo, HttpServletRequest _request) throws SystemException, DatabaseException {
      GLCreditorApi api = new AcctManagerApiImpl(_dbo, _request);
      api.setBaseView("CreditorView");
      api.setBaseClass("com.bean.Creditor");
      return api;
   }

  public static GlAccounts create()  throws SystemException{
      GlAccounts acct = new GlAccounts();
      return acct;
   }

   public static GlAccounts create(HttpServletRequest _req)   throws SystemException {
      GlAccounts obj = create();
      int rc = packageBean(_req, obj);
      return obj;        
   }

   public static GlAccountTypes createAcctType()  throws SystemException{
      GlAccountTypes acct = new GlAccountTypes();
      return acct;
   }

   public static GlAccountTypes createAcctType(HttpServletRequest _req)   throws SystemException {
      GlAccountTypes obj = createAcctType();
      int rc = packageBean(_req, obj);
      return obj;      
   }


   public static GlAccountCategory createCatg()  throws SystemException{
      GlAccountCategory acct = new GlAccountCategory();
      return acct;
   }

   public static GlAccountCategory createCatg(HttpServletRequest _req)   throws SystemException {
      GlAccountCategory obj = createCatg();
      int rc = packageBean(_req, obj);
      return obj;     
   }

   public static Creditor createCreditor()  throws SystemException{
      Creditor acct = new Creditor();
      return acct;
   }

   public static Creditor createCreditor(HttpServletRequest _req)   throws SystemException {
      Creditor obj = createCreditor();
      int rc = packageBean(_req, obj);
      return obj;     
   }

   public static Customer  createCustomer() {
	   try {
		      Customer acct = new Customer();
		      return acct;		   
	   }
	   catch (Exception e) {
		   return null;
	   }
   }

   public static Customer createCustomer(HttpServletRequest _req)   throws SystemException {
      Customer obj = createCustomer();
      int rc = packageBean(_req, obj);
      return obj;      
   }
   
   public static VwCustomerName createVwCustomerName() {
	   VwCustomerName obj = null;
	   try {
		   obj = new VwCustomerName();
		   return obj;
	   }
	   catch (SystemException e) {
		   return null;
	   }
   }

   public static ItemMaster  createItemMaster()  throws SystemException{
      ItemMaster item = new ItemMaster();
      return item;
   }

   public static ItemMaster createItemMaster(HttpServletRequest _req)   throws SystemException {
       ItemMaster obj = createItemMaster();
       int rc = packageBean(_req, obj);
       return obj;      
    }
   
   public static VwItemMaster  createItemMasterView()  {
       try {
           VwItemMaster item = new VwItemMaster();
           return item;
       }
       catch (SystemException e) {
           return null;
       }
    }


   public static ItemMasterStatusHist  createItemMasterStatusHist()  {
       try {
           ItemMasterStatusHist item = new ItemMasterStatusHist();
           return item;
       }
       catch (SystemException e) {
           return null;
       }
    }
   
}



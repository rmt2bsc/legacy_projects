package com.factory;

import javax.servlet.http.HttpServletRequest;

import com.bean.db.DatabaseConnectionBean;

import com.api.CashDisbursementsApi;
import com.api.XactManagerApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.XactCashDisburseApiImpl;

import com.util.SystemException;


public class CashDisburseFactory extends DataSourceAdapter  {

	public static CashDisbursementsApi createApi(DatabaseConnectionBean _dbo) {
		  try {
			  CashDisbursementsApi api = new XactCashDisburseApiImpl(_dbo);
			  return api;  
		  }
		  catch (DatabaseException e) {
			  return null;
		  }
		  catch (SystemException e) {
			  return null;
		  }
	  }
	  
	  public static CashDisbursementsApi createApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
		  try {
			  CashDisbursementsApi api = new XactCashDisburseApiImpl(_dbo, _request);
			  return api;  
		  }
		  catch (DatabaseException e) {
			  return null;
		  }
		  catch (SystemException e) {
			  return null;
		  }
	   }
	  
  public static XactManagerApi createBaseXactApi(DatabaseConnectionBean _dbo) {
	  try {
		  XactManagerApi api = new XactCashDisburseApiImpl(_dbo);
		  return api;  
	  }
	  catch (DatabaseException e) {
		  return null;
	  }
	  catch (SystemException e) {
		  return null;
	  }
  }
  
  public static XactManagerApi createBaseXactApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
	  try {
		  XactManagerApi api = new XactCashDisburseApiImpl(_dbo, _request);
		  return api;  
	  }
	  catch (DatabaseException e) {
		  return null;
	  }
	  catch (SystemException e) {
		  return null;
	  }
   }

}



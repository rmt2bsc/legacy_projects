package com.factory;

import javax.servlet.http.HttpServletRequest;

import com.bean.db.DatabaseConnectionBean;

import com.api.CreditChargesApi;
import com.api.XactManagerApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.CreditChargesApiImpl;

import com.util.SystemException;


public class CreditChargesFactory extends DataSourceAdapter  {

	public static CreditChargesApi createApi(DatabaseConnectionBean _dbo) {
		  try {
			  CreditChargesApi api = new CreditChargesApiImpl(_dbo);
			  return api;  
		  }
		  catch (DatabaseException e) {
			  return null;
		  }
		  catch (SystemException e) {
			  return null;
		  }
	  }
	  
	  public static CreditChargesApi createApi(DatabaseConnectionBean _dbo, HttpServletRequest _request) {
		  try {
			  CreditChargesApi api = new CreditChargesApiImpl(_dbo, _request);
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
		  XactManagerApi api = new CreditChargesApiImpl(_dbo);
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
		  XactManagerApi api = new CreditChargesApiImpl(_dbo, _request);
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



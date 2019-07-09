package com.factory;

import com.bean.db.DatabaseConnectionBean;

import com.api.ContactPersonApi;
import com.api.ContactBusinessApi;
import com.api.ContactAddressApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.ContactsApiImpl;

import com.util.SystemException;

import javax.servlet.http.HttpServletRequest;



public class ContactsApiFactory extends DataSourceAdapter  {

  public static ContactPersonApi createPersonApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     ContactPersonApi api = new ContactsApiImpl(_dbo);
     api.setBaseView("PersonView");
     api.setBaseClass("com.bean.Person");
     return api;
  }

  public static ContactBusinessApi createBusinessApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     ContactBusinessApi api = new ContactsApiImpl(_dbo);
     api.setBaseView("BusinessView");
		 api.setBaseClass("com.bean.Business");
     return api;
  }

  public static ContactAddressApi createAddressApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     ContactAddressApi api = new ContactsApiImpl(_dbo);
     api.setBaseView("AddressView");
     api.setBaseClass("com.bean.Address");
     return api;
  }
}



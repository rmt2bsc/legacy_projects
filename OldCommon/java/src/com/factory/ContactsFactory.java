package com.factory;

import javax.servlet.http.HttpServletRequest;


import com.api.ContactPersonApi;
import com.api.ContactBusinessApi;
import com.api.ContactAddressApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.ContactsApiImpl;

import com.bean.Person;
import com.bean.Business;
import com.bean.Address;
import com.bean.ContactCombine;
import com.bean.Zipcode;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;


public class ContactsFactory extends DataSourceAdapter  {

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

  public static Person createPerson() throws SystemException {
     return new Person();
  }

  public static Person createPerson(Person old) throws SystemException  {
     return new Person();
  }

  public static Person createPerson(HttpServletRequest req) throws SystemException  {
     Person obj = createPerson();
     int rc = packageBean(req, obj);
     return obj;
  }

  public static Business createBusiness() throws SystemException  {
     return new Business();
  }

  public static Business createBusiness(Business old) throws SystemException  {
     return new Business();
  }

  public static Business createBusiness(HttpServletRequest req) throws SystemException  {
     Business obj = createBusiness();
     int rc = packageBean(req, obj);
     return obj;
  }

  public static Address createAddress() throws SystemException  {
     return new Address();
  }

  public static Address createAddress(Person old)  throws SystemException {
     return new Address();
  }

  public static Address createAddress(HttpServletRequest req) throws SystemException  {
     Address obj = createAddress();
     int rc = packageBean(req, obj);
     return obj;
  }

  public static ContactCombine createcontact() throws SystemException  {
     return new ContactCombine();
  }

  public static ContactCombine createcontact(HttpServletRequest req) throws SystemException  {
     ContactCombine obj = createcontact();
     int rc = packageBean(req, obj);
     return obj;
  }

  public static Zipcode createZipcode() throws SystemException {
		return new Zipcode();
	}
}



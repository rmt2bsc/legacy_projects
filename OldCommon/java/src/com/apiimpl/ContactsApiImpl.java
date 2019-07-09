package com.apiimpl;


import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import java.sql.Types;

import com.util.SystemException;
import com.util.ContactException;
import com.util.ContactPersonException;
import com.util.ContactBusinessException;
import com.util.ContactAddressException;

import com.api.ContactPersonApi;
import com.api.ContactBusinessApi;
import com.api.ContactAddressApi;
import com.api.GeneralCodeGroupManagerApi;
import com.api.GeneralCodeManagerApi;
import com.api.db.DatabaseException;
import com.api.db.DynamicSqlApi;
import com.api.db.DynamicSqlFactory;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.Business;
import com.bean.Person;
import com.bean.Address;
import com.bean.GeneralCodes;
import com.bean.Zipcode;
import com.bean.TimeZone;
import com.bean.State;
import com.bean.Country;
import com.bean.db.DatabaseConnectionBean;

import com.factory.GeneralCodeFactory;
import com.factory.ContactsFactory;


public class ContactsApiImpl extends RdbmsDataSourceImpl implements ContactPersonApi, ContactBusinessApi, ContactAddressApi  {

   protected String criteria;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: ContactsApiImpl
//    Prototype: DatabaseConnectionBean dbConn
//      Returns: void
//       Throws: DatabaseException, SystemException
//  Description: Constructor begins the initialization of the DatabaseConnectionBean at the acestor level.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ContactsApiImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException    {
			super(dbConn);
			this.className = "ContactsApiImpl";
			this.baseView = "PersonView";
			this.baseBeanClass = "com.bean.Person";
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: ContactsApiImpl
//    Prototype: DatabaseConnectionBean dbConn
//                       HttpServletRequest req
//      Returns: void
//       Throws: DatabaseException, SystemException
//  Description: Constructor begins the initialization of the DatabaseConnectionBean and the HttpServletRequest
//                         object at the acestor level.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public ContactsApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest req) throws SystemException, DatabaseException    {
      super(dbConn, req);
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createPerson
//    Prototype: void
//      Returns: Person
//       Throws: none
//  Description: Creates a new Person object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Person createPerson()  throws SystemException  {
       return ContactsFactory.createPerson();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createPerson
//    Prototype: HttpServletRequest
//      Returns: Person
//       Throws: SystemException
//  Description: Creates a new Person object  from a HttpServleRequest object and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Person createPerson(HttpServletRequest _req)    throws SystemException  {
       return ContactsFactory.createPerson(_req);
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerById
//    Prototype: int
//      Returns: Person
//       Throws: ContactPersonException
//  Description: Locates a Person object by primary key or it Person Id and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Person findPerById(int value) throws ContactPersonException {
			String methodName = "findPerById";
			this.criteria = "id = " + value;
			try {
			    List list = this.find(this.criteria);
				if (list.size() == 0) {
					return null;
				}
				return (Person) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					throw new ContactPersonException("Personal Profile is not found", -100, this.className, methodName);
			}
			catch (SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerByFirstName
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates one or more Person  objects based on the firstname
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findPerByFirstName(String value) throws ContactPersonException   {

        String methodName = "findPerByFirstName";

				try {
					this.criteria = "firstname like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			  }
  			catch(SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			  }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerByLastName
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates one or more Person  objects based on the last name
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findPerByLastName(String value) throws ContactPersonException   {

        String methodName = "findPerByLastName";

				try {
					this.criteria = "lastname like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerByGender
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates a Person object by gender id primary key or it Person Id and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findPerByGender(int value) throws ContactPersonException {
			String methodName = "findPerByGender";
			this.criteria = "gender_id = " + value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerByMaritalStatus
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates a Person object by marital status and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findPerByMaritalStatus(int value) throws ContactPersonException {
			String methodName = "findPerByMaritalStatus";
			this.criteria = "marital_status = " + value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerByRace
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates a Person object by race and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findPerByRace(int value) throws ContactPersonException {
			String methodName = "findPerByMaritalStatus";
			this.criteria = "race_id = " + value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerBySSN
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates a Person object by SSN and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findPerBySSN(String value) throws ContactPersonException {
			String methodName = "findPerBySSN";
			this.criteria = "ssn  like \'" + value + "%\'";
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerByBirthDate
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates a Person object by Birth Date and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findPerByBirthDate(String value) throws ContactPersonException {
			String methodName = "findPerByBirthDate";
			this.criteria = "birth_date  = \'" + value + "\'";
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findPerByEMail
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates a Person object by email address and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findPerByEMail(String value) throws ContactPersonException {
			String methodName = "findPerByEMail";
			this.criteria = "email like \'" + value + "%\'";
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findContact
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactException
//  Description: Locates a Contact object by custom selection criteria.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findContact(String value) throws ContactException {
			String methodName = "findContact";

			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findGeneralCodeGroups
//    Prototype: void
//      Returns: ArrayList
//       Throws: ContactException
//  Description: Fetches all general code groups from the general_codes_group table
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findGeneralCodeGroups() throws ContactException {
       GeneralCodeGroupManagerApi api = null;
       try {
           api = GeneralCodeFactory.createGroupApi(this.connector);
           return api.findGroup(null);
       }
       catch (Exception e) {
           throw new ContactException(e);
       }
   }
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findGeneralCode
//    Prototype: int  _codeId
//      Returns: GeneralCodes
//       Throws: ContactException
//  Description: Fetches data from the general_codes table that is associated with "_codeId".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GeneralCodes findGeneralCode(int _codeId) throws ContactException {
       GeneralCodeManagerApi api = null;
       try {
           api = GeneralCodeFactory.createCodeApi(this.connector);
           return api.findCodeById(_codeId);
       }
       catch (Exception e) {
           throw new ContactException(e);
       }
   }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findGeneralCodes
//    Prototype: int  _grpId
//      Returns: ArrayList
//       Throws: ContactException
//  Description: Fetches codes related to "_grpId" from the general_codes table.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public List findGeneralCodes(int _grpId) throws ContactException {
       GeneralCodeManagerApi api = null;
       try {
           api = GeneralCodeFactory.createCodeApi(this.connector);
           return api.findCodeByGroup(_grpId);
       }
       catch (Exception e) {
           throw new ContactException(e);
       }
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findStateById
//    Prototype: int  _id
//      Returns: State
//       Throws: ContactAddressException
//  Description: Locates state data using the primary key, "state_id".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public State findStateById(String _stateId) throws ContactAddressException {
			 String methodName = "findState";
			 String criteria;
			 List  list;
			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.setBaseView("StateView");
			 this.setBaseClass("com.bean.State");

			 try {
					 criteria = "state_id = '" + _stateId + "'";
					 list = this.find(criteria);
					 if (list == null) {
							 return null;
					 }
					 return (State) list.get(0);
			 }
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
					throw new ContactAddressException("State code [" + _stateId + "] was not found", -100, this.className, methodName);
			}
			finally {
					this.baseBeanClass = baseClassHold;
					this.baseView = baseViewHold;
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findStateByCountry
//    Prototype: int  _countryId
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates state data based on country code.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findStateByCountry(int _countryId) throws ContactAddressException {
			 String methodName = "findStateByCountry";
			 String criteria;
			 List  list;
			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.setBaseView("StateView");
			 this.setBaseClass("com.bean.State");

			 try {
					 criteria = "country_id = '" + _countryId + "'";
					 list = this.find(criteria);
					 return list;
			 }
			catch (SystemException e) {
					throw new ContactAddressException("State code(s) were not found using country code [" + _countryId + "]", -100, this.className, methodName);
			}
			finally {
					this.baseBeanClass = baseClassHold;
					this.baseView = baseViewHold;
			}
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findStateByName
//    Prototype: String  _stateName
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates state data based on state_name.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findStateByName(String _stateName) throws ContactAddressException {
			 String methodName = "findStateByName";
			 String criteria;
			 List  list;
			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.setBaseView("StateView");
			 this.setBaseClass("com.bean.State");
			 try {
					 criteria = "state_name like '" + _stateName + "%'";
					 list = this.find(criteria);
					 return list;
			 }
			catch (SystemException e) {
					throw new ContactAddressException("State code(s) were not found using state name argument [" + _stateName + "]", -100, this.className, methodName);
			}
			finally {
					this.baseBeanClass = baseClassHold;
					this.baseView = baseViewHold;
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findState
//    Prototype: String  _criteria
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates state data based custome criteria supplied by the client
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findState(String _criteria) throws ContactAddressException {
			 String methodName = "findState";
			 List  list;
			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.setBaseView("StateView");
			 this.setBaseClass("com.bean.State");
			 try {
					 list = this.find(_criteria);
					 return list;
			 }
			catch (SystemException e) {
					throw new ContactAddressException("State code(s) were not found using criteria [" + _criteria + "]", -100, this.className, methodName);
			}
			finally {
					this.baseBeanClass = baseClassHold;
					this.baseView = baseViewHold;
			}
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCountryById
//    Prototype: int  _id
//      Returns: Country
//       Throws: ContactAddressException
//  Description: Locates country data using the primary key, "_id".
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Country findCountryById(int _id) throws ContactAddressException{
			 String methodName = "findCountryById";
			 String criteria;
			 List  list;
			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.setBaseView("CountryView");
			 this.setBaseClass("com.bean.Country");

			 try {
					 criteria = "id = '" + _id + "'";
					 list = this.find(criteria);
					 if (list == null) {
							 return null;
					 }
					 return (Country) list.get(0);
			 }
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
					throw new ContactAddressException("Country code [" + _id + "] was not found", -100, this.className, methodName);
			}
			finally {
					this.baseBeanClass = baseClassHold;
					this.baseView = baseViewHold;
			}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCountryByName
//    Prototype: String _name
//      Returns: Country
//       Throws: ContactAddressException
//  Description: Locates country data using country's name as a "like" search.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findCountryByName(String _name) throws ContactAddressException {
			 String methodName = "findCountryByName";
			 String criteria;
			 List  list;
			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.setBaseView("CountryView");
			 this.setBaseClass("com.bean.Country");

			 try {
					 criteria = "name like '" + _name + "'";
					 list = this.find(criteria);
					 return list;
			 }
			catch (SystemException e) {
					throw new ContactAddressException("Country data was not found using name criteria [" + _name + "]", -100, this.className, methodName);
			}
			finally {
					this.baseBeanClass = baseClassHold;
					this.baseView = baseViewHold;
			}
	}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findCountry
//    Prototype: String _criteria
//      Returns: Country
//       Throws: ContactAddressException
//  Description: Locates country data using custom criteria supplied by the client.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findCountry(String _criteria) throws ContactAddressException {
			 String methodName = "findCountry";
			 String criteria;
			 List  list;
			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.setBaseView("CountryView");
			 this.setBaseClass("com.bean.Country");

			 try {
					 criteria = _criteria;
					 list = this.find(criteria);
					 return list;
			 }
			catch (SystemException e) {
					throw new ContactAddressException("Country data was not found using custom criteria [" + _criteria + "]", -100, this.className, methodName);
			}
			finally {
					this.baseBeanClass = baseClassHold;
					this.baseView = baseViewHold;
			}
	}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipBase
//    Prototype: string  _criteria
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Generic method for retrieving zip code data using various selection criteria.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected  List findZipBase(String _criteria) throws ContactAddressException {
			 String methodName = "findZip";
 			 String baseClassHold = this.baseBeanClass;
			 String baseViewHold = this.baseView;

			 this.baseBeanClass = "com.bean.Zipcode";
			 this.baseView = "ZipcodeView";
  		 this.criteria = _criteria;
			 try {
			     List list = this.find(this.criteria);
			  	return list;
			 }
			 catch (SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
  		 finally {
					this.setBaseView(baseViewHold);
					this.setBaseClass(baseClassHold);
			 }
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipByCode
//    Prototype: string  zipcode
//      Returns: Zipcode
//       Throws: ContactAddressException
//  Description: Locates   zip code using exact matching on column, zip.   If found, returns Zipcode
//                    object.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  Zipcode findZipByCode(String zipcode) throws ContactAddressException {
			 String methodName = "findZipByCode(String)";
			 List list;
       String criteria = null;

			 try {
					 criteria = "zip = '" + zipcode + "'";
			  	 list = this.findZipBase(criteria);
			  	  if (list != null && list.size() > 0) {
					  		return (Zipcode) list.get(0);
					  }
					  return null;
				}
  			catch (IndexOutOfBoundsException e) {
  					throw new ContactAddressException("Zip Code could not be found using criteria:  " + criteria, -100, this.className, methodName);
  			}
   }
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Method: findZipByCode
//Prototype: int  zipcode
//Returns: Zipcode
// Throws: ContactAddressException
//Description: Locates   zip code using exact matching on column, zip.   If found, returns Zipcode
//              object.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public  Zipcode findZipByCode(int zipcode) throws ContactAddressException {
 String methodName = "findZipByCode(int)";
 List list;
 String criteria = null;

 try {
		 criteria = "zip = " +  zipcode;
  	 list = this.findZipBase(criteria);
  	  if (list != null && list.size() > 0) {
		  		return (Zipcode) list.get(0);
		  }
		  return null;
	}
	catch (IndexOutOfBoundsException e) {
			throw new ContactAddressException("Zip Code could not be found using criteria:  " + criteria, -100, this.className, methodName);
	}
}   
   

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipX
//    Prototype: string  zipcode
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more zip codes using incremental matching on column, zip.   If found,
//                    retruns an ArrayList object with one or more elements.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  List findZipX(String zipcode) throws ContactAddressException {
       String criteria = null;

			 criteria = "zip like '" + zipcode + "%'";
	  	 return this.findZipBase(criteria);
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipById
//    Prototype: int  _id
//      Returns: Zipcode
//       Throws: ContactAddressException
//  Description: Locates Zip Code using primary key, "_id".   If found, retruns an ArrayList object with
//                    one or more elements.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  Zipcode findZipById(int _id) throws ContactAddressException {
			 String methodName = "findZipById";
			 List list;
       String criteria = null;

			 try {
					 criteria = "id = " + _id;
			  	 list = this.findZipBase(criteria);
			  	  if (list != null  && list.size() > 0) {
					  		return (Zipcode) list.get(0);
					  }
					  return null;
				}
  			catch (IndexOutOfBoundsException e) {
  					throw new ContactAddressException("Zip Code could not be found using criteria:  " + criteria, -100, this.className, methodName);
  			}
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipByAreaCode
//    Prototype: string  areaCode
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more zip codes using incremental matching on column, area_code.   If found,
//                    retruns an ArrayList object with one or more elements.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  List findZipByAreaCode(String areaCode) throws ContactAddressException {
       String criteria = null;

			 criteria = "area_code like '" + areaCode + "%'";
	  	 return this.findZipBase(criteria);
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipByState
//    Prototype: string  state
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more zip codes using incremental matching on column, state.   If found,
//                    retruns an ArrayList object with one or more elements.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  List findZipByState(String state) throws ContactAddressException {
       String criteria = null;

			 criteria = "state like '" + state + "%'";
	  	 return this.findZipBase(criteria);
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipByCity
//    Prototype: string  city
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more zip codes using incremental matching on column, city.   If found,
//                    retruns an ArrayList object with one or more elements.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  List findZipByCity(String city) throws ContactAddressException {
       String criteria = null;

			 criteria = "city like '" + city + "%'";
	  	 return this.findZipBase(criteria);
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipByCounty
//    Prototype: string  county
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more zip codes using incremental matching on column, county.   If found,
//                    retruns an ArrayList object with one or more elements.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  List findZipByCounty(String county) throws ContactAddressException {
       String criteria = null;

			 criteria = "county like '" + county + "%'";
	  	 return this.findZipBase(criteria);
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZipByTimeZone
//    Prototype: string  timeZone
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more zip codes using incremental matching on column, time_zone.   If found,
//                    retruns an ArrayList object with one or more elements.   Otherwise, returns null.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public  List findZipByTimeZone(String timeZone) throws ContactAddressException {
       String criteria = null;

			 criteria = "time_zone like '" + timeZone + "%'";
	  	 return this.findZipBase(criteria);
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findZip
//    Prototype: string  _criteria
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more zip codes based on custom criteria supplied by the client.    Be sure to
//                    match column names to those of the zipcode table definition.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findZip(String _criteria) throws ContactAddressException {
    	 return this.findZipBase(_criteria);
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findTimeZoneById
//    Prototype: int _id
//      Returns: TimeZone
//       Throws: ContactAddressException
//  Description: Searches the time_zone for "_id".   If found, data associated with "_id" is returned as type,
//                    TimeZone.    Otherwise, null is returned.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public TimeZone findTimeZoneById(int _id) throws ContactAddressException {

			String methodName = "findTimeZoneById";
			String baseClassHold = this.baseBeanClass;
			String baseViewHold = this.baseView;
			String criteria;

			criteria = "id = " + _id;
			this.setBaseView("TimeZoneView");
			this.setBaseClass("com.bean.TimeZone");

			try {
			    List list = this.find(criteria);
				if (list.size() == 0) {
					return null;
				}
				return (TimeZone) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
					throw new ContactAddressException("Time Zone code [" + _id + "] was not found", -100, this.className, methodName);
			}
			finally {
					this.setBaseView(baseViewHold);
					this.setBaseClass(baseClassHold);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAllTimeZones
//    Prototype: void
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Searches the time_zone table for all time zone codes and their descriptions.   If found, an ArrayList of TimeZone
//                    objects are packaged and returned to the caller.     Otherwise, null is returned.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public List findAllTimeZones() throws ContactAddressException {
			String criteria;

			String methodName = "findAllTimeZones";
			String baseClassHold = this.baseBeanClass;
			String baseViewHold = this.baseView;

			this.setBaseView("TimeZoneView");
			this.setBaseClass("com.bean.TimeZone");
			criteria = "";

			try {
			    List list = this.find(criteria);
				if (list.size() == 0) {
					return null;
				}
				return list;
			}
			catch (SystemException e) {
					throw new ContactAddressException(e.getMessage() + "  -  Time Zone list could be retrieve", -100, this.className, methodName);
			}
			finally {
					this.setBaseView(baseViewHold);
					this.setBaseClass(baseClassHold);
			}
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainPerson
//    Prototype: Person
//      Returns: int (Person  Id)
//       Throws: ContactPersonException
//  Description: This method is responsible for creating and updating Person.   If Person.id
//                          is null then Personal profile must be created.  If Person id has a  value, then apply updates to that
//                          Personal Profile.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainPerson(Person _base)  throws ContactPersonException {
				if (_base.getId() == 0) {
						this.insertPerson(_base);
				}
				else  {
						this.updatePerson(_base);
				}
				return _base.getId();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: insertPerson
//      Prototype: Person
//        Returns: Person
//         Throws: ContactPersonException
//  Description: This method is responsible for adding Person to the systems.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Person insertPerson(Person _base) throws ContactPersonException   {

      String methodName = "insertPerson";
			int        idParm = 0;
			int        newId;
			Object newIdObj;
		  DynamicSqlApi  dynaApi = null;

			   	 // Validate person objec
			 this.validatePerson(_base);

			try {
					 newId = 0;
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add Person to the database.
           idParm = 0;
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  idParm, true);
           dynaApi.addParm("firstname", Types.VARCHAR,  _base.getFirstname(), false);
           dynaApi.addParm("midname", Types.VARCHAR,  _base.getMidname(), false);
           dynaApi.addParm("lastname", Types.VARCHAR,  _base.getLastname(), false);
           dynaApi.addParm("maidenname", Types.VARCHAR,  _base.getMaidenname(), false);
           dynaApi.addParm("generation", Types.VARCHAR,  _base.getGeneration(), false);
           dynaApi.addParm("title", Types.INTEGER,  _base.getTitle(), false);
           dynaApi.addParm("gender_id", Types.INTEGER,  _base.getGenderId(), false);
           dynaApi.addParm("marital_status", Types.INTEGER,  _base.getMaritalStatus(), false);
           dynaApi.addParm("birth_date", Types.DATE,  _base.getBirthDate(), false);
           dynaApi.addParm("race_id", Types.INTEGER,  _base.getRaceId(), false);
           dynaApi.addParm("ssn", Types.VARCHAR,  _base.getSsn(), false);
           dynaApi.addParm("email", Types.VARCHAR,  _base.getEmail(), false);

  							 // Call stored procedure to add Person  to the database
  							 // using  id primary key.
           dynaApi.execute("exec usp_add_person ? ? ? ? ? ? ? ? ? ? ? ? ?");

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
					 throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
			 catch (SystemException e) {
					 throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }

   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: updatePerson
//      Prototype: Person
//        Returns: Person
//         Throws: ContactPersonException
//  Description: This method is responsible for updating a Person
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Person updatePerson(Person _base) throws ContactPersonException   {

      String methodName = "updateAccount";
		  DynamicSqlApi  dynaApi = null;

			   	 // Validate person objec
			 this.validatePerson(_base);

			try {
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add Person to the database.
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _base.getId(), false);
           dynaApi.addParm("firstname", Types.VARCHAR,  _base.getFirstname(), false);
           dynaApi.addParm("midname", Types.VARCHAR,  _base.getMidname(), false);
           dynaApi.addParm("lastname", Types.VARCHAR,  _base.getLastname(), false);
           dynaApi.addParm("maidenname", Types.VARCHAR,  _base.getMaidenname(), false);
           dynaApi.addParm("generation", Types.VARCHAR,  _base.getGeneration(), false);
           dynaApi.addParm("title", Types.INTEGER,  _base.getTitle(), false);
           dynaApi.addParm("gender_id", Types.INTEGER,  _base.getGenderId(), false);
           dynaApi.addParm("marital_status", Types.INTEGER,  _base.getMaritalStatus(), false);
           dynaApi.addParm("birth_date", Types.DATE,  _base.getBirthDate(), false);
           dynaApi.addParm("race_id", Types.INTEGER,  _base.getRaceId(), false);
           dynaApi.addParm("ssn", Types.VARCHAR,  _base.getSsn(), false);
           dynaApi.addParm("email", Types.VARCHAR,  _base.getEmail(), false);

  							 // Call stored procedure to update Person  to the database
  							 // using  id primary key.
           dynaApi.execute("exec usp_upd_person ? ? ? ? ? ? ? ? ? ? ? ? ?");
					 return _base;
			 }
			 catch (DatabaseException e) {
					 throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
			 catch (SystemException e) {
					 throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
   }


   public Person copyPerson(Person old) throws ContactPersonException {

			 Person newObj = null;
			 return newObj;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: deletePerson
//      Prototype: int  _id - Person Id
//        Returns: int
//         Throws: ContactBusinessException
//  Description: This method is responsible for deleteing a Person profile
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int deletePerson(int _id) throws ContactPersonException  {
		  String method = "deletePerson";

			try {
           dynaApi = DynamicSqlFactory.create(this.connector);

            //  Add Business to the database.
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _id, false);

					 // Call stored procedure to delete Person from the database using  id primary key.
           dynaApi.execute("exec usp_del_person ?");
					 return 1;
			 }
			 catch (DatabaseException e) {
					 throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ContactPersonException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: validatePerson
//    Prototype: Person
//      Returns: void
//       Throws: ContactPersonException
//  Description: Validates a Person object.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public void validatePerson(Person _obj) throws ContactPersonException   {

			String temp = null;

				      //  Person First Name must exist
				  temp = _obj.getFirstname();
				  if (temp == null || temp.length() <= 0) {
							throw new ContactPersonException(this.connector, 501,null);
					}
					    //  Person Last Name must exist
					temp = _obj.getLastname();
				  if (temp == null || temp.length() <= 0) {
							throw new ContactPersonException(this.connector, 502,null);
				  }
				  return;
   }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createBusiness
//    Prototype: void
//      Returns: Business
//       Throws: none
//  Description: Creates a new Business object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Business createBusiness()    throws SystemException  {
       return ContactsFactory.createBusiness();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createBusiness
//    Prototype: HttpServletRequest
//      Returns: Business
//       Throws: SystemException
//  Description: Creates a new Business object  from a HttpServleRequest object and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Business createBusiness(HttpServletRequest _req)   throws SystemException   {
       return ContactsFactory.createBusiness(_req);
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusById
//    Prototype: int
//      Returns: Person
//       Throws: ContactBusinessException
//  Description: Locates a Business object by primary key or it Person Id and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Business findBusById(int value) throws ContactBusinessException {
			String methodName = "findBusById";
			this.criteria = "id = " + value;
			try {
			    List list = this.find(this.criteria);
				if (list.size() == 0) {
					return null;
				}
				return (Business) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByContactFirstName
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates one or more Person  objects based on the contact first name
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findBusByContactFirstName(String value) throws ContactBusinessException   {

        String methodName = "findBusByContactFirstName";

				try {
					this.criteria = "contact_firstname like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByContactLastName
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactBusinessException
//  Description: Locates one or more Business  objects based based on the contact last name
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findBusByContactLastName(String value) throws ContactBusinessException   {

        String methodName = "findBusByContactLastName";

				try {
					this.criteria = "contact_lastname like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByBusType
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactBusinessException
//  Description: Locates one or more Business objects by business type and returns Person object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findBusByBusType(int value) throws ContactBusinessException {
			String methodName = "findBusByBusType";
			this.criteria = "bus_type = " + value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByServType
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactBusinessException
//  Description: Locates one or more Business objects by business type and returns ArrayList of Business  objects
//                          to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findBusByServType(int value) throws ContactBusinessException {
			String methodName = "findBusByServType";
			this.criteria = "serv_type = " + value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByLongName
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactPersonException
//  Description: Locates one or more Person  objects based on the long name
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findBusByLongName(String value) throws ContactBusinessException   {

        String methodName = "findBusByLongName";

				try {
					this.criteria = "longname like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByShortName
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactBusinessException
//  Description: Locates one or more Business  objects based based on the short  name
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findBusByShortName(String value) throws ContactBusinessException   {

        String methodName = "findBusByShortName";

				try {
					this.criteria = "shortname like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByTaxId
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactBusinessException
//  Description: Locates one or more Business  objects based based on the short  name
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findBusByTaxId(String value) throws ContactBusinessException   {

        String methodName = "findBusByTaxId";

				try {
					this.criteria = "tax_id  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findBusByWebsite
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactBusinessException
//  Description: Locates one or more Business  objects based based on the short  name
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findBusByWebsite(String value) throws ContactBusinessException   {

 			String methodName = "findBusByWebsite";

				try {
					this.criteria = "website  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainBusiness
//    Prototype: Business
//      Returns: int (Person  Id)
//       Throws: ContactBusinessException
//  Description: This method is responsible for creating and updating a Business object.   If Business id
//                          is null then Business profile must be created.  If Business id has a  value, then apply updates to that
//                          Business Profile.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainBusiness(Business _base)  throws ContactBusinessException {
				if (_base.getId() == 0) {
						this.insertBusiness(_base);
				}
				else  {
						this.updateBusiness(_base);
				}
				return _base.getId();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: insertBusiness
//      Prototype: Business
//        Returns: Business
//         Throws: ContactBusinessException
//  Description: This method is responsible for adding a Business profile to the system.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Business insertBusiness(Business _base) throws ContactBusinessException   {

      String methodName = "insertBusiness";
 			int        idParm = 0;
			int        newId;
			Object newIdObj;
		  DynamicSqlApi  dynaApi = null;

			   	 // Validate person objec
			 this.validateBusiness(_base);

			try {
					 newId = 0;
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add Business to the database.
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  idParm, true);
           dynaApi.addParm("bus_type", Types.INTEGER,  _base.getBusType(), false);
           dynaApi.addParm("serv_type", Types.INTEGER,  _base.getServType(), false);
           dynaApi.addParm("longname", Types.VARCHAR,  _base.getLongname(), false);
           dynaApi.addParm("shortname", Types.VARCHAR,  _base.getShortname(), false);
           dynaApi.addParm("contact_firstname", Types.VARCHAR,  _base.getContactFirstname(), false);
           dynaApi.addParm("contact_lastname", Types.VARCHAR,  _base.getContactLastname(), false);
           dynaApi.addParm("contact_phone", Types.VARCHAR,  _base.getContactPhone(), false);
           dynaApi.addParm("contact_ext", Types.VARCHAR,  _base.getContactExt(), false);
           dynaApi.addParm("tax_id", Types.VARCHAR,  _base.getTaxId(), false);
           dynaApi.addParm("website", Types.VARCHAR,  _base.getWebsite(), false);

  							 // Call stored procedure to add Business  to the database using  id primary key.
           dynaApi.execute("exec usp_add_business ? ? ? ? ? ? ? ? ? ? ?");

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
					 throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
			 catch (SystemException e) {
					 throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: updateBusiness
//      Prototype: Business
//        Returns: Business
//         Throws: ContactBusinessException
//  Description: This method is responsible for updating a Business profile
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Business updateBusiness(Business _base) throws ContactBusinessException   {

      String methodName = "updateBusiness";
		  DynamicSqlApi  dynaApi = null;

			   	 // Validate person objec
			 this.validateBusiness(_base);

			try {
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add Business to the database.
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _base.getId(), false);
           dynaApi.addParm("bus_type", Types.INTEGER,  _base.getBusType(), false);
           dynaApi.addParm("serv_type", Types.INTEGER,  _base.getServType(), false);
           dynaApi.addParm("longname", Types.VARCHAR,  _base.getLongname(), false);
           dynaApi.addParm("shortname", Types.VARCHAR,  _base.getShortname(), false);
           dynaApi.addParm("contact_firstname", Types.VARCHAR,  _base.getContactFirstname(), false);
           dynaApi.addParm("contact_lastname", Types.VARCHAR,  _base.getContactLastname(), false);
           dynaApi.addParm("contact_phone", Types.VARCHAR,  _base.getContactPhone(), false);
           dynaApi.addParm("contact_ext", Types.VARCHAR,  _base.getContactExt(), false);
           dynaApi.addParm("tax_id", Types.VARCHAR,  _base.getTaxId(), false);
           dynaApi.addParm("website", Types.VARCHAR,  _base.getWebsite(), false);

  							 // Call stored procedure to add Business  to the database using  id primary key.
           dynaApi.execute("exec usp_upd_business ? ? ? ? ? ? ? ? ? ? ?");
					 return _base;
			 }
			 catch (DatabaseException e) {
					 throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
			 catch (SystemException e) {
					 throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
   }


   public Business copyBusiness(Business old) throws ContactBusinessException {
			 Business newBus = null;
			 return newBus;
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: deleteBusiness
//      Prototype: int  _id - Business Id
//        Returns: int
//         Throws: ContactBusinessException
//  Description: This method is responsible for deleteing a Business profile
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int deleteBusiness(int _id) throws ContactBusinessException  {

		  String method = "deleteBusiness";

			try {
           dynaApi = DynamicSqlFactory.create(this.connector);

            //  Add Business to the database.
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _id, false);

					 // Call stored procedure to delete Business  from the database using  id primary key.
           dynaApi.execute("exec usp_del_business ?");
					 return 1;
			 }
			 catch (DatabaseException e) {
					 throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
			 catch (SystemException e) {
					 throw new ContactBusinessException(e.getMessage(), e.getErrorCode(), this.className, method);
			 }
   }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: validateBusiness
//    Prototype: Business
//      Returns: void
//       Throws: ContactBusinessException
//  Description: Validates a Business object.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public void validateBusiness(Business _base) throws ContactBusinessException   {
			String longName;

				    // Long Name must exist
          longName = _base.getLongname();
          if (longName == null || longName.length() <= 0) {
							throw new ContactBusinessException(this.connector, 505, null);
					}
   }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createAddress
//    Prototype: void
//      Returns: Address
//       Throws: none
//  Description: Creates a new Address object  and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Address createAddress()   throws SystemException   {
       return ContactsFactory.createAddress();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: createAddress
//    Prototype: HttpServletRequest
//      Returns: Address
//       Throws: SystemException
//  Description: Creates a new Business object  from a HttpServleRequest object and returns to caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public Address createAddress(HttpServletRequest _req)   throws SystemException   {
       return ContactsFactory.createAddress(_req);
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrById
//    Prototype: int
//      Returns: Address
//       Throws: ContactAddressException
//  Description: Locates a Address object by primary key or it address Id and returns Address object to the caller.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Address findAddrById(int value) throws ContactAddressException {
			String methodName = "findAddrById";
			this.criteria = "id = " + value;
			try {
			    List list = this.find(this.criteria);
				if (list.size() == 0) {
					return null;
				}
				return (Address) list.get(0);
			}
			catch (IndexOutOfBoundsException e) {
					return null;
			}
			catch (SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByPersonId
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates a Address object by person id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findAddrByPersonId(int value) throws ContactAddressException {
			String methodName = "findAddrByPersonId";
			this.criteria = "person_id = " + value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByBusinessId
//    Prototype: int
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates a Address object by business id.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List findAddrByBusinessId(int value) throws ContactAddressException {
			String methodName = "findAddrByBusinessId";
			this.criteria = "business_id = " + value;
			try {
			    List list = this.find(this.criteria);
				return list;
			}
			catch (SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByAddr1
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on addr1
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findAddrByAddr1(String value) throws ContactAddressException   {

        String methodName = "findAddrByAddr1";

				try {
					this.criteria = "addr1  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByZip
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on zip code
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List findAddrByZip(String value) throws ContactAddressException   {

        String methodName = "findAddrByZip";

				try {
					this.criteria = "zip  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByHomeNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on zip code
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findAddrByHomeNo(String value) throws ContactAddressException   {

        String methodName = "findAddrByHomeNo";

				try {
					this.criteria = "phone_home  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByWorkNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on work phone number
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findAddrByWorkNo(String value) throws ContactAddressException   {

        String methodName = "findAddrByWorkNo";

				try {
					this.criteria = "phone_work  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByCellNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on zip code
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findAddrByCellNo(String value) throws ContactAddressException   {

        String methodName = "findAddrByCellNo";

				try {
					this.criteria = "phone_cell  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByMainNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on Main Phone Number
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findAddrByMainNo(String value) throws ContactAddressException   {

        String methodName = "findAddrByMainNo";

				try {
					this.criteria = "phone_main  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByFaxNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on fax phone number.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findAddrByFaxNo(String value) throws ContactAddressException   {

        String methodName = "findAddrByFaxNo";

				try {
					this.criteria = "phone_fax  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: findAddrByPagerNo
//    Prototype: String
//      Returns: ArrayList
//       Throws: ContactAddressException
//  Description: Locates one or more Address  objects based based on pager number
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public List  findAddrByPagerNo(String value) throws ContactAddressException   {

        String methodName = "findAddrByPagerNo";

				try {
					this.criteria = "phone_pager  like \'" + value + "%\'";
					List list = this.find(this.criteria);
					return list;
			}
			catch(SystemException e) {
					throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			}
	 }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: maintainAddress
//    Prototype: Address
//      Returns: int (Address  Id)
//       Throws: ContactAddressException
//  Description: This method is responsible for creating and updating a Address object.   If Address id
//                          is null then Address profile must be created.  If Address id has a  value, then apply updates to that
//                          Address Profile.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public int maintainAddress(Address _base)  throws ContactAddressException {
				if (_base.getId() == 0) {
						this.insertAddress(_base);
				}
				else  {
						this.updateAddress(_base);
				}
				return _base.getId();
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: insertAddress
//      Prototype: Address
//        Returns: Address
//         Throws: ContactAddressException
//  Description: This method is responsible for adding a Address profile to the system.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Address insertAddress(Address _base) throws ContactAddressException   {

      String methodName = "insertAddress";
 			int        idParm = 0;
			int        newId;
			Object newIdObj;
		  DynamicSqlApi  dynaApi = null;

			   	 // Validate person objec
			 this.validateAddress(_base);

			try {
					 newId = 0;
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Add Business to the database.
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  idParm, true);
           dynaApi.addParm("person_id", Types.INTEGER,  _base.getPersonId(), false);
           dynaApi.addParm("business_id", Types.INTEGER,  _base.getBusinessId(), false);
           dynaApi.addParm("addr1", Types.VARCHAR,  _base.getAddr1(), false);
           dynaApi.addParm("addr2", Types.VARCHAR,  _base.getAddr2(), false);
           dynaApi.addParm("addr3", Types.VARCHAR,  _base.getAddr3(), false);
           dynaApi.addParm("addr4", Types.VARCHAR,  _base.getAddr4(), false);
           dynaApi.addParm("zip", Types.VARCHAR,  _base.getZip(), false);
           dynaApi.addParm("zipext", Types.VARCHAR,  _base.getZipext(), false);
           dynaApi.addParm("phone_home", Types.VARCHAR,  _base.getPhoneHome(), false);
           dynaApi.addParm("phone_work", Types.VARCHAR,  _base.getPhoneWork(), false);
           dynaApi.addParm("phone_ext", Types.VARCHAR,  _base.getPhoneExt(), false);
           dynaApi.addParm("phone_main", Types.VARCHAR,  _base.getPhoneMain(), false);
           dynaApi.addParm("phone_cell", Types.VARCHAR,  _base.getPhoneCell(), false);
           dynaApi.addParm("phone_fax", Types.VARCHAR,  _base.getPhoneFax(), false);
           dynaApi.addParm("phone_pager", Types.VARCHAR,  _base.getPhonePager(), false);

  							 // Call stored procedure to add Address  to the database using  id primary key.
           dynaApi.execute("exec usp_add_address ? ? ? ? ? ? ? ? ? ? ? ? ? ? ? ?");

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
					 throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
			 catch (SystemException e) {
					 throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//          Method: updateAddress
//      Prototype: Address
//        Returns: int
//         Throws: ContactAddressException
//  Description: This method is responsible for updating a Address profile
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   protected Address updateAddress(Address _base) throws ContactAddressException   {

      String methodName = "updateAddress";
		  DynamicSqlApi  dynaApi = null;

			   	 // Validate person objec
			 this.validateAddress(_base);

			try {
           dynaApi = DynamicSqlFactory.create(this.connector);

                 //  Modify  Business to the database.
           dynaApi.clearParms();
           dynaApi.addParm("id", Types.INTEGER,  _base.getId(), false);
           dynaApi.addParm("person_id", Types.INTEGER,  _base.getPersonId(), false);
           dynaApi.addParm("business_id", Types.INTEGER,  _base.getBusinessId(), false);
           dynaApi.addParm("addr1", Types.VARCHAR,  _base.getAddr1(), false);
           dynaApi.addParm("addr2", Types.VARCHAR,  _base.getAddr2(), false);
           dynaApi.addParm("addr3", Types.VARCHAR,  _base.getAddr3(), false);
           dynaApi.addParm("addr4", Types.VARCHAR,  _base.getAddr4(), false);
           dynaApi.addParm("zip", Types.VARCHAR,  _base.getZip(), false);
           dynaApi.addParm("zipext", Types.VARCHAR,  _base.getZipext(), false);
           dynaApi.addParm("phone_home", Types.VARCHAR,  _base.getPhoneHome(), false);
           dynaApi.addParm("phone_work", Types.VARCHAR,  _base.getPhoneWork(), false);
           dynaApi.addParm("phone_ext", Types.VARCHAR,  _base.getPhoneExt(), false);
           dynaApi.addParm("phone_main", Types.VARCHAR,  _base.getPhoneMain(), false);
           dynaApi.addParm("phone_cell", Types.VARCHAR,  _base.getPhoneCell(), false);
           dynaApi.addParm("phone_fax", Types.VARCHAR,  _base.getPhoneFax(), false);
           dynaApi.addParm("phone_pager", Types.VARCHAR,  _base.getPhonePager(), false);

  							 // Call stored procedure to add Address  to the database using  id primary key.
           dynaApi.execute("exec usp_upd_address ? ? ? ? ? ? ? ? ? ? ? ? ? ? ? ?");
  				 return _base;
			 }
			 catch (DatabaseException e) {
					 throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
			 catch (SystemException e) {
					 throw new ContactAddressException(e.getMessage(), e.getErrorCode(), this.className, methodName);
			 }
   }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       Method: validateAddress
//    Prototype: Address
//      Returns: void
//       Throws: ContactAddressException
//  Description: Validates a Address object.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public void validateAddress(Address _base) throws ContactAddressException {
		int person_id;
			int business_id;

          person_id = _base.getPersonId();
          business_id = _base.getBusinessId();
          _base.getZip();

				  // Person Id and Business Id cannot be null.   Either or both must exist.
          if (person_id <= 0 && business_id <= 0) {
							throw new ContactAddressException(this.connector, 508, null);
					}

				  // Person Id and Business Id cannot have values concurrently.   They are mutually exclusive.
          if (person_id > 0 && business_id > 0) {
						ArrayList msg = new ArrayList();
						msg.add(String.valueOf(person_id));
						msg.add(String.valueOf(business_id));
						throw new ContactAddressException(this.connector, 510, msg);
					}


					     //   Zip must be a number > zero.
//					if (zip <= 0) {
							//throw new ContactAddressException(this.dbo, 507, null);
//					}
   }

   public int copyAddress(Address old) throws ContactAddressException {
	  	 return 1;
   }

   public int deleteAddress(Address old) throws ContactAddressException {
	  	 return 1;
	 }


}


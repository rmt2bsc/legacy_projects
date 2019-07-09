
package com.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.SystemException;
import com.util.ContactAddressException;
import com.util.ContactException;

import com.bean.Address;
import com.bean.Zipcode;
import com.bean.TimeZone;
import com.bean.State;
import com.bean.Country;


public interface ContactAddressApi extends BaseDataSource {
    Address createAddress() throws SystemException;
    Address createAddress(HttpServletRequest req) throws SystemException;
    Address findAddrById(int arg0) throws ContactAddressException;
    List findAddrByPersonId(int arg0) throws ContactAddressException;
    List findAddrByBusinessId(int arg0) throws ContactAddressException;
    List findAddrByAddr1(String arg0) throws ContactAddressException;
    List findAddrByZip(String arg0) throws ContactAddressException;
    List findAddrByHomeNo(String arg0) throws ContactAddressException;
    List findAddrByWorkNo(String arg0) throws ContactAddressException;
    List findAddrByCellNo(String arg0) throws ContactAddressException;
    List findAddrByFaxNo(String arg0) throws ContactAddressException;
    List findAddrByPagerNo(String arg0) throws ContactAddressException;
    List findAddrByMainNo(String arg0) throws ContactAddressException;
    List findContact(String arg0) throws ContactException;

       //  Find methods to loacte State data
    State findStateById(String _stateId) throws ContactAddressException;
    List findStateByCountry(int _countryId) throws ContactAddressException;
    List findStateByName(String _stateName) throws ContactAddressException;
    List findState(String _criteria) throws ContactAddressException;

      // Find  methods to locate Country data.
    Country findCountryById(int _id) throws ContactAddressException;
    List findCountryByName(String _name) throws ContactAddressException;
    List findCountry(String _criteria) throws ContactAddressException;


       //  Find methods to locate Zip code data
    Zipcode findZipByCode(String zipcode) throws ContactAddressException;
    Zipcode findZipByCode(int zipcode) throws ContactAddressException;
    List findZipX(String zipcode) throws ContactAddressException;
    Zipcode findZipById(int value) throws ContactAddressException;
    List findZipByAreaCode(String areaCode) throws ContactAddressException;
    List findZipByState(String state) throws ContactAddressException;
    List findZipByCity(String city) throws ContactAddressException;
    List findZipByCounty(String county) throws ContactAddressException;
    List findZipByTimeZone(String timeZone) throws ContactAddressException;
    List findZip(String criteria) throws ContactAddressException;

       //  Find methods to locate Time Zones
    TimeZone findTimeZoneById(int _id) throws ContactAddressException;
    List findAllTimeZones() throws ContactAddressException;

   String setBaseView(String value);
   String setBaseClass(String value) ;
    int maintainAddress(Address contact) throws ContactAddressException;
    int copyAddress(Address old) throws ContactAddressException;
    int deleteAddress(Address old) throws ContactAddressException;
   void validateAddress(Address _base) throws ContactAddressException;

}

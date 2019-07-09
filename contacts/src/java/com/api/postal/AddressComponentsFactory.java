package com.api.postal;

import com.controller.Request;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.bean.CityType;
import com.bean.Country;
import com.bean.State;
import com.bean.TimeZone;
import com.bean.VwStateCountry;
import com.bean.VwZipcode;
import com.bean.Zipcode;

/**
 * A AddressComponentsFactory class can be used to create the following classes:
 * ZipcodeApi, StatesApi, CountryApi, TimezoneApi, Zipcode, State, Country,
 * TimeZone, and CityType.
 *
 * @author RTerrell
 *
 */
public class AddressComponentsFactory extends DataSourceAdapter {

    /**
     * Creates an instance of ZipcodeApi using a DatabaseConnectionBean.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @return ZipcodeApi
     */
    public static ZipcodeApi createZipcodeApi(DatabaseConnectionBean dbo) {
        try {
            ZipcodeApi api = new AddressComponentsImpl(dbo);
            api.setBaseView("ZipcodeView");
            api.setBaseClass("com.bean.Zipcode");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of ZipcodeApi using a DatabaseConnectionBean
     * and a Request object.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @param request Request
     * @return ZipcodeApi
     */
    public static ZipcodeApi createZipcodeApi(DatabaseConnectionBean dbo, Request request) {
        try {
            ZipcodeApi api = new AddressComponentsImpl(dbo, request);
            api.setBaseView("ZipcodeView");
            api.setBaseClass("com.bean.Zipcode");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Uses {@link ZipcodeXmlImpl} to create a new ZipcodeApi object which
     * is associated with a DatabaseConnectionBean object and a
     * Request object.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @param request Request
     * @return ZipcodeApi
     */
    public static ZipcodeApi createZipcodeXmlApi(DatabaseConnectionBean dbo, Request request) {
        try {
            ZipcodeApi api = new ZipcodeXmlImpl(dbo, request);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Creates an instance of StatesApi using a DatabaseConnectionBean.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @return StatesApi
     */
    public static StatesApi createStatesApi(DatabaseConnectionBean dbo) {
        try {
            StatesApi api = new AddressComponentsImpl(dbo);
            api.setBaseView("StateView");
            api.setBaseClass("com.bean.State");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of StatesApi using a DatabaseConnectionBean and
     * a Request.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @param request Request
     * @return StatesApi
     */
    public static StatesApi createStatesApi(DatabaseConnectionBean dbo, Request request) {
        try {
            StatesApi api = new AddressComponentsImpl(dbo, request);
            api.setBaseView("StateView");
            api.setBaseClass("com.bean.State");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Uses {@link StatesXmlImpl} to create a new StatesApi object which
     * is associated with a DatabaseConnectionBean object and a
     * Request object.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @param request Request
     * @return StatesApi
     */
    public static StatesApi createStatesXmlApi(DatabaseConnectionBean dbo, Request request) {
        try {
            StatesApi api = new StatesXmlImpl(dbo, request);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Creates an instance of CountryApi using a DatabaseConnectionBean.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @return CountryApi
     */
    public static CountryApi createCountryApi(DatabaseConnectionBean dbo) {
        try {
            CountryApi api = new AddressComponentsImpl(dbo);
            api.setBaseView("CountryView");
            api.setBaseClass("com.bean.Country");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of CountryApi using a DatabaseConnectionBean and
     * a Request object.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @param request Request
     * @return CountryApi
     */
    public static CountryApi createCountryApi(DatabaseConnectionBean dbo, Request request) {
        try {
            CountryApi api = new AddressComponentsImpl(dbo, request);
            api.setBaseView("CountryView");
            api.setBaseClass("com.bean.Country");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    
    /**
     * Creates an instance of a XML implementation of CountryApi using a DatabaseConnectionBean.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @return CountryApi
     */
    public static CountryApi createCountryXmlApi(DatabaseConnectionBean dbo) {
        try {
            CountryApi api = new CountryXmlImpl(dbo);
            api.setBaseView("CountryView");
            api.setBaseClass("com.bean.Country");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
    /**
     * Creates an instance of TimezoneApi using a DatabaseConnectionBean.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @return TimezoneApi
     */
    public static TimezoneApi createTimezoneApi(DatabaseConnectionBean dbo) {
        try {
            TimezoneApi api = new AddressComponentsImpl(dbo);
            api.setBaseView("TimeZoneView");
            api.setBaseClass("com.bean.TimeZone");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of TimezoneApi using a DatabaseConnectionBean and
     * a Request object.
     *
     * @param dbo {@link DatabaseConnectionBean}
     * @param request Request
     * @return TimezoneApi
     */
    public static TimezoneApi createTimezoneApi(DatabaseConnectionBean dbo,  Request request) {
        try {
            TimezoneApi api = new AddressComponentsImpl(dbo, request);
            api.setBaseView("TimeZoneView");
            api.setBaseClass("com.bean.TimeZone");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of Zipcode object.
     *
     * @return {@link Zipcode}
     * @throws SystemException
     */
    public static Zipcode createZipcode() {
        try {
            return new Zipcode();
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Creates a new Zipcode object which its data is expected to
     * be handled as XML.   By default, the zip code object created
     * will not be configured to serialized any query results to
     * a file.
     *
     * @return {@link Zipcode}
     */
    public static Zipcode createXmlZipcode() {
        try {
        	Zipcode obj = createZipcode();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Creates an instance of VwZipcode object.
     *
     * @return {@link com.bean.VwZipcode}
     * @throws SystemException
     */
    public static VwZipcode createZipcodeExt() {
        try {
            return new VwZipcode();
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Creates a new VwZipcode object which its data is expected to
     * be handled as XML.   By default, the zip code object created
     * will not be configured to serialized any query results to
     * a file.
     *
     * @return @return {@link com.bean.VwZipcode}
     */
    public static VwZipcode createXmlZipcodeExt() {
        try {
            VwZipcode obj = createZipcodeExt();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
    /**
     * Creates an instance of State object.
     *
     * @return {@link State}
     */
    public static State createState() {
        try {
            return new State();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new State object which its data is expected to
     * be handled as XML.   By default, the state object created
     * will not be configured to serialized any query results to
     * a file.
     *
     * @return {@link State}
     */
    public static State createXmlState() {
        State obj = createState();
        try {
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Creates an instance of VwStateCountry object.
     *
     * @return {@link com.bean.VwStateCountry VwStateCountry}
     */
    public static VwStateCountry createStateExt() {
        try {
            return new VwStateCountry();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new VwStateCountry object which its data is expected to
     * be handled as XML.   By default, the VwStateCountry object created
     * will not be configured to serialized any query results to
     * a file.
     *
     * @return {@link com.bean.VwStateCountry VwStateCountry}
     */
    public static VwStateCountry createXmlStateExt() {
        VwStateCountry obj = createStateExt();
        try {
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of Country object.
     *
     * @return {@link Country}
     */
    public static Country createCountry() {
        try {
            return new Country();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of TimeZone object.
     *
     * @return {@link TimeZone}
     */
    public static TimeZone createTimeZone() {
        try {
            return new TimeZone();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of CityType object.
     *
     * @return {@link CityType}
     */
    public static CityType createCityType() {
        try {
            return new CityType();
        }
        catch (Exception e) {
            return null;
        }
    }
}

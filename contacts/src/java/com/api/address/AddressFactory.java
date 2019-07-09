package com.api.address;

import com.controller.Request;

import com.api.address.AddressApi;
import com.api.address.AddresssBeanImpl;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;
import com.bean.Address;


/**
 * A factory class that is used to create Address api and Address data objects.
 * 
 * @author RTerrell
 * 
 */
public class AddressFactory extends DataSourceAdapter {
    
    /**
     * Creates a new createAddressApi object that is assoiciated with 
     * a DatabaseConnectionBean object and a HttpServletRequest object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return AddressApi
     */
    public static AddressApi createAddressApi(DatabaseConnectionBean dbo, Request request) {
        try {
            AddressApi api = new AddresssBeanImpl(dbo, request);
            api.setBaseView("AddressView");
            api.setBaseClass("com.bean.Address");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a new AddressApi object that is assoiciated with 
     * a DatabaseConnectionBean object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @return AddressApi
     */
    public static AddressApi createAddressApi(DatabaseConnectionBean dbo) {
        try {
            AddressApi api = new AddresssBeanImpl(dbo);
            api.setBaseView("AddressView");
            api.setBaseClass("com.bean.Address");
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    
    /**
     * Uses {@link AddresssXmlImpl} to create a new AddressApi object which 
     * is associated with a DatabaseConnectionBean object and a 
     * HttpServletRequest object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return AddressApi
     */
    public static AddressApi createAddressXmlApi(DatabaseConnectionBean dbo, Request request) {
        try {
            AddressApi api = new AddresssXmlImpl(dbo, request);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    

    /**
     * Creates a new Address object.
     * 
     * @return {@link Address}
     */
    public static Address createAddress() {
        try {
            return new Address();
        }
        catch (Exception e) {
            return null;
        }
    }

 
    /**
     * Creates a new Address object by obtaining its property values 
     * from the HttpServletRequest.
     * 
     * @param request HttpServletRequest
     * @return {@link Address}
     */
    public static Address createAddress(Request request) {
        try {
            Address obj = createAddress();
            packageBean(request, obj);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Creates a new Address object which its data is expected to 
     * be handled as XML.   By default, the address object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link Address}
     */
    public static Address createXmlAddress() {
        try {
        	Address obj = createAddress();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }    
}

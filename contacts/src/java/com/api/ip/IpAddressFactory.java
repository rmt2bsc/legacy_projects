package com.api.ip;

import com.controller.Request;

import com.api.db.orm.DataSourceAdapter;

import com.bean.db.DatabaseConnectionBean;


/**
 * A factory class that is used to create Address api and Address data objects.
 * 
 * @author RTerrell
 * 
 */
public class IpAddressFactory extends DataSourceAdapter {
    
    public static IpApi createApi(DatabaseConnectionBean dbo) {
        try {
            IpApi api = new IpBeanImpl(dbo);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Creates a new createAddressApi object that is assoiciated with 
     * a DatabaseConnectionBean object and a HttpServletRequest object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return AddressApi
     */
    public static IpApi createApi(DatabaseConnectionBean dbo, Request request) {
        try {
            IpApi api = new IpBeanImpl(dbo, request);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

}

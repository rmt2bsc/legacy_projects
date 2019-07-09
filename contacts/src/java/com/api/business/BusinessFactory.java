package com.api.business;

import com.controller.Request;

import com.api.business.BusinessApi;
import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;
import com.bean.Business;
import com.bean.VwBusinessAddress;
import com.bean.Person;

/**
 * A factory class that is used to create Business Api and Business 
 * data objects.
 * 
 * @author RTerrell
 * 
 */
public class BusinessFactory extends DataSourceAdapter {

    /**
     * Creates a new BusinessApi object that is assoiciated with 
     * a DatabaseConnectionBean object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @return BusinessApi
     */
    public static BusinessApi createBusinessApi(DatabaseConnectionBean dbo) {
	try {
	    BusinessApi api = new BusinessBeanImpl(dbo);
	    api.setBaseView("BusinessView");
	    api.setBaseClass("com.bean.Business");
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new BusinessApi object that is assoiciated with 
     * a DatabaseConnectionBean object and a HttpServletRequest object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return BusinessApi
     */
    public static BusinessApi createBusinessApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    BusinessApi api = new BusinessBeanImpl(dbo, request);
	    api.setBaseView("BusinessView");
	    api.setBaseClass("com.bean.Business");
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Uses {@link BusinessXmlImpl} to create a new BusinessApi object which 
     * is associated with a DatabaseConnectionBean object and a 
     * HttpServletRequest object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return BusinessApi
     */
    public static BusinessApi createBusinessXmlApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    BusinessApi api = new BusinessXmlImpl(dbo, request);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new Business object.
     * 
     * @return {@link Person}
     */
    public static Business createBusiness() {
	try {
	    return new Business();
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new Business object by obtaining its property values 
     * from the HttpServletRequest.
     * 
     * @param request HttpServletRequest
     * @return {@link Business}
     */
    public static Business createBusiness(Request request) {
	try {
	    Business obj = createBusiness();
	    packageBean(request, obj);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates a new Business object which its data is expected to 
     * be handled as XML.   By default, the business object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return
     */
    public static Business createXmlBusiness() {
	try {
	    Business obj = createBusiness();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    /**
     * Creates a new VwBusinessAddress object.
     * 
     * @return {@link VwBusinessAddress}
     */
    public static VwBusinessAddress createBusinessAddress() {
    try {
        VwBusinessAddress obj = new VwBusinessAddress();
        return obj;
    }
    catch (Exception e) {
        return null;
    }
    }

    /**
     * Creates a new VwBusinessAddress object which its data is expected to 
     * be handled as XML.   By default, the VwBusinessAddress object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return
     */
    public static VwBusinessAddress createXmlBusinessAddress() {
    try {
        VwBusinessAddress obj = createBusinessAddress();
        obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
        obj.setSerializeXml(false);
        return obj;
    }
    catch (Exception e) {
        return null;
    }
    }
    
}

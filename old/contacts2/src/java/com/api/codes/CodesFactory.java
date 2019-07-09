package com.api.codes;

import com.controller.Request;

import com.api.db.orm.DataSourceAdapter;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.bean.GeneralCodes;
import com.bean.GeneralCodesGroup;
import com.bean.VwCodes;



/**
 * A CodesFactory class can be used to create the following classes:
 * CodeGroupApi, CodesApi, GeneralCodesGroup, and GeneralCodes.
 * 
 * @author RTerrell
 *
 */
public class CodesFactory extends DataSourceAdapter {

    
    /**
     * Creates an instance of CodesApi using a DatabaseConnectionBean.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @return CodesApi
     */
    public static CodesApi createCodesApi(DatabaseConnectionBean dbo) {
        try {
            CodesApi api = new GeneralCodesImpl(dbo);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of CodesApi using a DatabaseConnectionBean 
     * and a HttpServletRequest object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return CodesApi
     */
    public static CodesApi createCodesApi(DatabaseConnectionBean dbo, Request request) {
        try {
            CodesApi api = new GeneralCodesImpl(dbo, request);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
    
 
    
    /**
     * Creates an instance of CodesApi from an XML implementation using a DatabaseConnectionBean.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @return CodesApi
     * @deprecated Will be removed in future versions.  Use JAXB to bind DB results to java.
     */
    public static CodesApi createXmlCodesApi(DatabaseConnectionBean dbo) {
        try {
            CodesApi api = new GeneralXmlCodesImpl(dbo);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of CodesApi from an XML implementation using a DatabaseConnectionBean 
     * and a HttpServletRequest object.
     * 
     * @param dbo {@link DatabaseConnectionBean}
     * @param request HttpServletRequest
     * @return CodesApi
     * @deprecated Will be removed in future versions.  Use JAXB to bind DB results to java.
     */
    public static CodesApi createXmlCodesApi(DatabaseConnectionBean dbo, Request request) {
        try {
            CodesApi api = new GeneralXmlCodesImpl(dbo, request);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    
    
    /**
     * Creates an instance of GeneralCodesGroup object.
     * 
     * @return {@link GeneralCodesGroup}
     */
    public static GeneralCodesGroup createGeneralGroup() {
        try {
            return new GeneralCodesGroup();    
        }
        catch (Exception e) {
            return null;
        }
    }

    
    /**
     * Creates an instance of VwCodes object.
     * 
     * @return {@link VwCodes}
     */
    public static VwCodes createVmCodes() {
        try {
            return new VwCodes();    
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Creates a new VwCodes object which its data is expected to 
     * be handled as XML.   By default, the VwCodes object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link GeneralCodes}
     */
    public static VwCodes createXmlVmCodes() {
        try {
            VwCodes obj = CodesFactory.createVmCodes();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Creates an instance of GeneralCodes object.
     * 
     * @return {@link GeneralCodes}
     */
    public static GeneralCodes createGeneralCodes() {
        try {
            return new GeneralCodes();    
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Creates a new GeneralCodes object which its data is expected to 
     * be handled as XML.   By default, the GeneralCodes object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link GeneralCodes}
     */
    public static GeneralCodes createXmlGeneralCodes() {
        try {
            GeneralCodes obj = CodesFactory.createGeneralCodes();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Creates a new GeneralCodesGroup object which its data is expected to 
     * be handled as XML.   By default, the GeneralCodesGroup object created 
     * will not be configured to serialized any query results to 
     * a file. 
     * 
     * @return {@link GeneralCodesGroup}
     */
    public static GeneralCodesGroup createXmlGeneralGroup() {
        try {
            GeneralCodesGroup obj = CodesFactory.createGeneralGroup();
            obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
            obj.setSerializeXml(false);
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }
}

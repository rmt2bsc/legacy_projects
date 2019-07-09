package com.gl.creditor;

import com.controller.Request;
import com.controller.Response;

import com.api.db.orm.DataSourceAdapter;

import com.bean.Creditor;
import com.bean.CreditorType;
import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.gl.creditor.CreditorApi;

import com.util.SystemException;

/**
 * Factory for creating Creditor related resources.
 * 
 * @author RTerrell
 *
 */
public class CreditorFactory extends DataSourceAdapter {

    /**
     * Creates an instance of CreditorApi using a DatabaseConnectionBean object.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @return {@link com.gl.creditor.CreditorApi CreditorApi} or null if api could not be created.
     */
    public static CreditorApi createApi(DatabaseConnectionBean dbo) {
	try {
	    CreditorApi api = new CreditorImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of CreditorApi using a DatabaseConnectionBean object and a 
     * HttpServletRequest.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param req HttpServletRequest object.
     * @return {@link com.gl.creditor.CreditorApi CreditorApi} or null if api could not be created.
     */
    public static CreditorApi createApi(DatabaseConnectionBean dbo, Request req) {
	try {
	    CreditorApi api = new CreditorImpl(dbo, req);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of CreditorHelper which is initialized with a DatabaseConnectionBean 
     * object.
     * 
     * @param dbCon  The databse connection
     * @return {@link CreditorHelper}
     */
    public static CreditorHelper createCreditorHelper(DatabaseConnectionBean dbCon) {
	try {
	    CreditorHelper obj = new CreditorHelper(dbCon);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Creates an instance of CreditorHelper which is initialized with a Request, Response,
     * and DatabaseConnectionBean objects.
     * 
     * @param request The user'srequest
     * @param response The user's response
     * @param dbCon The database connection
     * @return {@link CreditorHelper}
     */
    public static CreditorHelper createCreditorHelper(Request request, Response response, DatabaseConnectionBean dbCon) {
	try {
	    CreditorHelper obj = new CreditorHelper(request, response, dbCon);
	    return obj;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create Creditor object.
     * 
     * @return {@link com.bean.Creditor Creditor}
     */
    public static Creditor createCreditor() {
	try {
	    Creditor obj = new Creditor();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create Creditor object that is able to manage XML data.
     * 
     * @return {@link com.bean.Creditor Creditor}
     */
    public static Creditor createXmlCreditor() {
	try {
	    Creditor obj = CreditorFactory.createCreditor();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create CreditorType object.
     * 
     * @return {@link com.bean.CreditorType CreditorType}
     */
    public static CreditorType createCreditorType() {
	try {
	    CreditorType obj = new CreditorType();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create CreditorType object that is able to manage XML data.
     * 
     * @return {@link com.bean.CreditorType CreditorType}
     */
    public static CreditorType createXmlCreditorType() {
	try {
	    CreditorType obj = CreditorFactory.createCreditorType();
	    obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	    obj.setSerializeXml(false);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

}

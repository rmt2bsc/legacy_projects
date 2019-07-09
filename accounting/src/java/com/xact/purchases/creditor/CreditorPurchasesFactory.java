package com.xact.purchases.creditor;

import com.bean.OrmBean;
import com.bean.VwXactCreditChargeList;
import com.bean.db.DatabaseConnectionBean;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceAdapter;

import com.controller.Request;
import com.controller.Response;

import com.util.SystemException;
import com.xact.XactManagerApi;

/**
 * Factory class for creating creditor purchasing related objects.
 * 
 * @author appdev
 *
 */
public class CreditorPurchasesFactory extends DataSourceAdapter {

    /**
     * Create CreditorPurchasesApi using a database implementation.
     * 
     * @param dbo The Database connection bean.
     * @return {@link CreditorPurchasesApi}
     */
    public static CreditorPurchasesApi createApi(DatabaseConnectionBean dbo) {
	try {
	    CreditorPurchasesApi api = new CreditorPurchasesDatabaseImpl(dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create CreditorPurchasesApi using a database implementation using a database 
     * connection bean and the user's request.
     * 
     * @param dbo The Database connection bean.
     * @param request The user's request.
     * @return {@link CreditorPurchasesApi}
     */
    public static CreditorPurchasesApi createApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    CreditorPurchasesApi api = new CreditorPurchasesDatabaseImpl(dbo, request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create XactManagerApi using a creditor purchases database implementation.
     * 
     * @param dbo The Database connection bean.
     * @return {@link XactManagerApi}
     */
    public static XactManagerApi createBaseXactApi(DatabaseConnectionBean dbo) {
	try {
	    XactManagerApi api = new CreditorPurchasesDatabaseImpl(dbo);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create XactManagerApi using a database implementation using a database 
     * connection bean and the user's request.
     * 
     * @param dbo The Database connection bean.
     * @param request The user's request.
     * @return {@link XactManagerApi}
     */
    public static XactManagerApi createBaseXactApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    XactManagerApi api = new CreditorPurchasesDatabaseImpl(dbo, request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create XactManagerApi using a database implementation using a database 
     * connection bean, the user's request, and the response object.
     * 
     * @param dbo The Database connection bean.
     * @param request The user's request.
     * @param request The user's response.
     * @return {@link XactManagerApi}
     */
    public static XactManagerApi createBaseXactApi(DatabaseConnectionBean dbo, Request request, Response response) {
	try {
	    XactManagerApi api = new CreditorPurchasesDatabaseImpl(dbo, request, response);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

    /**
     * Create VwXactCreditChargeList object.
     * 
     * @return {@link VwXactCreditChargeList}
     */
    public static VwXactCreditChargeList createVwXactCreditChargeList() {
	try {
	    VwXactCreditChargeList obj = new VwXactCreditChargeList();
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Create XML version of VwXactCreditChargeList object.
     * 
     * @return {@link VwXactCreditChargeList}
     */
    public static VwXactCreditChargeList createXmlVwXactCreditChargeList() {
	VwXactCreditChargeList obj = CreditorPurchasesFactory.createVwXactCreditChargeList();
	obj.setResultsetType(OrmBean.RESULTSET_TYPE_XML);
	obj.setSerializeXml(false);
	return obj;
    }
}

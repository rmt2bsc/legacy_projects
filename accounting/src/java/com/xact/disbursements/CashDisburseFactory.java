package com.xact.disbursements;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xact.disbursements.CashDisburseXactImpl;

import com.util.SystemException;

/**
 * Factory class for creating cash disbursement related objects.
 * 
 * @author appdev
 *
 */
public class CashDisburseFactory extends DataSourceAdapter {

    /**
     * Creates a CashDisbursementsApi instance from the general disbursement implementation which 
     * is initialized with a DatabaseConnectionBean.
     * 
     * @param dbo The database connection bean. 
     * @return {@link CashDisbursementsApi}
     */
    public static CashDisbursementsApi createApi(DatabaseConnectionBean dbo) {
	try {
	    CashDisbursementsApi api = new CashDisburseXactImpl(dbo);
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
     * Creates a CashDisbursementsApi instance from the general disbursement implementation which 
     * is initialized with a DatabaseConnectionBean and the user's Request.
     * 
     * @param dbo The database connection bean. 
     * @param request The user's request
     * @return {@link CashDisbursementsApi}
     */
    public static CashDisbursementsApi createApi(DatabaseConnectionBean dbo, Request request) {
	try {
	    CashDisbursementsApi api = new CashDisburseXactImpl(dbo, request);
	    return api;
	}
	catch (DatabaseException e) {
	    return null;
	}
	catch (SystemException e) {
	    return null;
	}
    }

}

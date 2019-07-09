package com.xact.receipts;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xact.receipts.CashReceiptsApi;
import com.xact.receipts.CashReceiptsApiImpl;

import com.util.SystemException;

/**
 * Factory class that contain methods responsible for the creation of  various objects pertaining to the sales order module.
 * 
 * @author Roy Terrell.
 *
 */
public class ReceiptsFactory extends DataSourceAdapter {

    /**
     * Creates a cash receiptsr api implemented with a specific database connection bean.
     * @param _dbo
     * @param _request
     * @return
     */
    public static CashReceiptsApi createCashReceiptsApi(DatabaseConnectionBean _dbo) {
	try {
	    CashReceiptsApi api = new CashReceiptsApiImpl(_dbo);
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
     * Creates a cash receiptsr api implemented with a specific database connection bean and servlet request object.
     * @param _dbo
     * @param _request
     * @return
     */
    public static CashReceiptsApi createCashReceiptsApi(DatabaseConnectionBean _dbo, Request _request) {
	try {
	    CashReceiptsApi api = new CashReceiptsApiImpl(_dbo, _request);
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

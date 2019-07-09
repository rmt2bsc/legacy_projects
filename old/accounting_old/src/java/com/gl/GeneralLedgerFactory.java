package com.gl;

import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;
import com.bean.GlAccounts;
import com.bean.VwAccount;
import com.bean.VwCategory;

import com.bean.db.DatabaseConnectionBean;

import com.gl.BasicGLApi;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.util.SystemException;

import com.controller.Request;

/**
 * Factory for creating General Ledger related resources.
 *
 * @author RTerrell
 *
 */
public class GeneralLedgerFactory extends DataSourceAdapter {

    /**
     * Creates an instance of BasicGLApi using a DatabaseConnectionBean object. Testing SCMS
     *
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @return {@link com.gl.BasicGLApi BasicGLApi} or null if instance cannot be created.
     * @throws SystemException
     * @throws DatabaseException
     */
    public static BasicGLApi createBasicGLApi(DatabaseConnectionBean dbo) throws SystemException, DatabaseException {
	try {
	    BasicGLApi api = new GeneralLedgerCodeImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of BasicGLApi using a DatabaseConnectionBean object and a
     * HttpServletRequest.
     *
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param req HttpServletRequest object.
     * @return {@link com.gl.BasicGLApi BasicGLApi}
     */
    public static BasicGLApi createBasicGLApi(DatabaseConnectionBean dbo, Request req) throws SystemException, DatabaseException {
	try {
	    BasicGLApi api = new GeneralLedgerCodeImpl(dbo, req);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of GlAccounts.
     *
     * @return {@link com.bean.GlAccounts GlAccounts}
     */
    public static GlAccounts createGlAccount() {
	try {
	    GlAccounts acct = new GlAccounts();
	    return acct;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of GlAccounts using the client's request object.
     *
     * @param req Request
     * @return {@link com.bean.GlAccounts GlAccounts}
     */
    public static GlAccounts create(Request req) {
	GlAccounts obj = createGlAccount();
	try {
	    packageBean(req, obj);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of VwAccount.
     *
     * @return {@link com.bean.VwAccount VwAccount}
     */
    public static VwAccount createVwAccount() {
	try {
	    VwAccount acct = new VwAccount();
	    return acct;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of GlAccountTypes.
     *
     * @return {@link com.bean.GlAccountTypes GlAccountTypes}
     */
    public static GlAccountTypes createAcctType() {
	try {
	    GlAccountTypes acct = new GlAccountTypes();
	    return acct;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of GlAccountTypes using the client's request.
     *
     * @param req HttpServletRequest
     * @return {@link com.bean.GlAccountTypes GlAccountTypes}
     */
    public static GlAccountTypes createAcctType(Request req) {
	GlAccountTypes obj = createAcctType();
	try {
	    packageBean(req, obj);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of GlAccountCategory.
     *
     * @return {@link com.bean.GlAccountCategory GlAccountCategory}
     */
    public static GlAccountCategory createCatg() {
	try {
	    GlAccountCategory acct = new GlAccountCategory();
	    return acct;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of VwCategory.
     *
     * @return {@link com.bean.VwCategory VwCategory}
     */
    public static VwCategory createVwCategory() {
	try {
	    VwCategory ctg = new VwCategory();
	    return ctg;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Creates an instance of GlAccountCategory.
     *
     * @param req HttpServletRequest
     * @return {@link com.bean.GlAccountCategory GlAccountCategory}
     */
    public static GlAccountCategory createCatg(Request req) {
	GlAccountCategory obj = createCatg();
	try {
	    packageBean(req, obj);
	    return obj;
	}
	catch (Exception e) {
	    return null;
	}
    }

}

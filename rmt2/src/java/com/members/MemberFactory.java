package com.members;

import com.bean.db.DatabaseConnectionBean;

import com.api.db.orm.DataSourceAdapter;

import com.controller.Request;

/**
 * Factory for creating instances related to members.
 * 
 * @author RTerrell
 *
 */
public class MemberFactory extends DataSourceAdapter {

    /**
     * Factory method for creating an instance of MemberApi using a DatabaseConnectionBean object.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @return {@link com.members.MemberApi MemberApi} or null if instance 
     *         cannot be created.
     * @throws SystemException
     * @throws DatabaseException
     */
    public static MemberApi createApi(DatabaseConnectionBean dbo) {
	try {
	    MemberApi api = new MemberImpl(dbo);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }

    /**
     * Factory method for creating an instance of MemberApi using a DatabaseConnectionBean 
     * object and a Request.
     * 
     * @param dbo {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean} object.
     * @param req HttpServletRequest object.
     * @return @link com.members.MemberApi MemberApi} or null if instance 
     *         cannot be created.
     */
    public static MemberApi createApi(DatabaseConnectionBean dbo, Request req) {
	try {
	    MemberApi api = new MemberImpl(dbo, req);
	    return api;
	}
	catch (Exception e) {
	    return null;
	}
    }
}

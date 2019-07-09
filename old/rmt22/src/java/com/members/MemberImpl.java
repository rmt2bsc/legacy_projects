package com.members;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;

import com.entity.Members;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.db.DatabaseConnectionBean;

import com.util.NotFoundException;
import com.util.SystemException;


/**
 * An implementation of {@link com.members.MemberApi MemberApi}.  It provides functionality 
 * that creates, updates, deletes, and queries inforamation as it pertains to the members of 
 * the RMT2 Business Systems Corp website.
 * 
 * @author RTerrell
 *
 */
class MemberImpl extends RdbmsDaoImpl implements MemberApi {
  
    private RdbmsDaoQueryHelper daoHelper;

    private static Logger logger = Logger.getLogger(MemberImpl.class);;

    /**
     * Default constructor that is inaccessible to the publc.
     * 
     * @throws SystemException
     * @throws DatabaseException
     */
    protected MemberImpl() throws SystemException, DatabaseException {
	super();
    }
    
    /**
     * Creates a MemberImpl object with an initialized database connection.
     * 
     * @param dbConn The database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public MemberImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Creates a MemberImpl object with an initialized database connection and the user's request.
     * 
     * @param dbConn The database connection bean
     * @param req The request object.
     * @throws SystemException
     * @throws DatabaseException
     */
    public MemberImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	MemberImpl.logger = Logger.getLogger(MemberImpl.class);
    }


    /**
     * Releases memory allocated for this instance.
     */
    public void close() {
	super.close();
    }

    /**
     * Retrieve members by member id from the database.
     * 
     * @param memberId The internal unique id of the member.
     * @return Object
     *           a {@link com.bean.Members Members} object when found.
     * @throws MemberException
     *           for general errors
     * @throws NotFoundException
     *           When member cannot be found by input member id.
     */
    public Members findById(int memberId) throws MemberException {
	Members obj = new Members();
	obj.addCriteria(Members.PROP_MEMBERID, memberId);
	try {
	    Object result = this.daoHelper.retrieveObject(obj);
	    if (result == null) {
		throw new NotFoundException();
	    }
	    return (Members) result;
	}
	catch (DatabaseException e) {
	    throw new MemberException(e);
	}
    }

    /**
     * Fetches a member by email address from the database.  <i>emailAddr</i> parameter must not equal 
     * null, not have a length of zero, or not equal spaces. 
     * 
     * @param emailAddr 
     *          the email address of a member.
     * @return Object
     *          A {@link com.bean.Members Members} object when found.
     * @throws MemberException
     *           <i>emailAddr</i> is null, length equals zero, or equal spaces.  General errors.
     * @throws NotFoundException
     *           When member cannot be found by input Email Address.
     */
    public Object findByEmail(String emailAddr) throws MemberException {
	if (emailAddr == null || emailAddr.length() == 0 || emailAddr.equals("")) {
	    this.msg = "Invalid parameter value passed as \"emailAddr\"";
	    MemberImpl.logger.log(Level.ERROR, this.msg);
	    throw new MemberException(this.msg);
	}
	Members obj = new Members();
	obj.addCriteria(Members.PROP_EMAIL, emailAddr);
	try {
	    Object result = this.daoHelper.retrieveObject(obj);
	    if (result == null) {
		throw new NotFoundException();
	    }
	    return (Members) result;
	}
	catch (DatabaseException e) {
	    throw new MemberException(e);
	}
    }
    
    
}

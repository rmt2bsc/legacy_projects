package com.api;

import java.util.List;


import org.apache.log4j.Logger;

import com.bean.VwCommonContact;

import com.util.SystemException;

import com.api.ContactException;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.db.DatabaseConnectionBean;

/**
 * Class that implements the Contact interface which provides common functionality 
 * to query the database for various types of contacts.
 * 
 * @author RTerrell
 *
 */
class CommonContactImpl extends RdbmsDaoImpl implements Contact {
    private static Logger logger = Logger.getLogger("CommonContactImpl");

    protected RdbmsDaoQueryHelper daoHelper;

    protected String criteria;

    /**
     * Construct a CommonContactImpl object with a DatabaseConnectionBean instance.
     * 
     * @param dbConn Database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
    public CommonContactImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
        super(dbConn);
        this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
        this.className = "CommonContactImpl";
    }

  

    /**
     * Releases allocated resources.
     */
    public void close() {
        logger = null;
        this.daoHelper.close();
        this.daoHelper = null;
        super.close();
    }

    /**
     * Fetches a Personal or Business contact by its primary key.
     * 
     * @param uid 
     *         the primary key value to conduct the search.
     *         
     * @throws ContactException
     *         General database errors.         
     */
    public VwCommonContact findContact(int uid) throws ContactException {
        VwCommonContact obj = CommonContactFactory.createCommonContactEntity();
        obj.addCriteria(VwCommonContact.PROP_CONTACTID, uid);
        try {
            return (VwCommonContact) this.daoHelper.retrieveObject(obj);
        }
        catch (DatabaseException e) {
            throw new ContactException(e);
        }
    }

    /**
     * Fetches a List of {@link com.bean.VwCommonContact VwCommonContact} objects using custom 
     * selection criteria.  Be sure that the intended base view and base class is set for this 
     * api before invoking this method.
     */
    public List<VwCommonContact> findContact(String criteria) throws ContactException {
        VwCommonContact obj = CommonContactFactory.createCommonContactEntity();
        obj.addCustomCriteria(criteria);
        try {
            return this.daoHelper.retrieveList(obj);
        }
        catch (DatabaseException e) {
            throw new ContactException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.api.Contact#deleteContact(java.lang.Object)
     */
    public int deleteContact(Object obj) throws ContactException {
        this.msg = "deleteContact is not implemented";
        logger.error(this.msg);
        throw new ContactException(this.msg);
    }

    /* (non-Javadoc)
     * @see com.api.Contact#maintainContact(java.lang.Object)
     */
    public int maintainContact(Object data) throws ContactException {
        this.msg = "maintainContact is not implemented";
        logger.error(this.msg);
        throw new ContactException(this.msg);
    }

    /* (non-Javadoc)
     * @see com.api.Contact#validateContact(java.lang.Object)
     */
    public void validateContact(Object obj) throws ContactException {
        this.msg = "validateContact is not implemented";
        logger.error(this.msg);
        throw new ContactException(this.msg);
    }

}

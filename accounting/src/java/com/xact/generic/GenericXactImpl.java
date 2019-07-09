package com.xact.generic;

import java.util.List;

import org.apache.log4j.Logger;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.orm.RdbmsDataSourceImpl;

import com.bean.VwGenericXactList;
import com.bean.XactType;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.SystemException;

import com.xact.XactException;

/**
 * Api Implementation that manages transaction events.
 * 
 * @author Roy Terrell
 * 
 */
public class GenericXactImpl extends RdbmsDataSourceImpl implements GenericXactManagerApi {

    protected RdbmsDaoQueryHelper daoHelper;

    private Logger logger = Logger.getLogger(GenericXactImpl.class);

    /**
     * Default Constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    public GenericXactImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructor which uses database connection
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public GenericXactImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.setBaseView("XactView");
	this.setBaseClass("com.bean.Xact");
	logger.info("logger created for instance, GenericXactImpl");
    }

    /**
     * Create a GenericXactImpl object containing a database connection bean and 
     * the user's request
     * 
     * @param dbConn
     * @param request
     * @throws DatabaseException
     * @throws SystemException
     */
    public GenericXactImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	this(dbConn);
	this.request = request;
    }

    /**
     * Shutdown the instance.
     */
    public void close() {
	this.daoHelper = null;
	super.close();
    }

    /**
    * 
    * @param xactId
    * @return A XML document of a single generic transaction
    * @throws XactException
    */
    public VwGenericXactList findGenericXact(int xactId) throws XactException {
	VwGenericXactList obj = GenericXactFactory.createGerericXact();
	obj.addCriteria(VwGenericXactList.PROP_XACTID, xactId);
	obj = (VwGenericXactList) this.daoHelper.retrieveObject(obj);
	return obj;
    }

    public List<VwGenericXactList> findGenericXactList(String criteria) throws XactException {
	VwGenericXactList obj = GenericXactFactory.createGerericXact();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(VwGenericXactList.PROP_XACTDATE, VwGenericXactList.ORDERBY_DESCENDING);
	obj.addOrderBy(VwGenericXactList.PROP_XACTID, VwGenericXactList.ORDERBY_DESCENDING);
	return this.daoHelper.retrieveList(obj);
    }

    /**
     * 
     * @param criteria
     * @return A XML document of one or more generic transactions
     * @throws XactException
     */
    public List<VwGenericXactList> findGenericXact(String criteria) throws XactException {
	VwGenericXactList obj = GenericXactFactory.createGerericXact();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(VwGenericXactList.PROP_XACTDATE, VwGenericXactList.ORDERBY_DESCENDING);
	obj.addOrderBy(VwGenericXactList.PROP_XACTID, VwGenericXactList.ORDERBY_DESCENDING);
	List<VwGenericXactList> list = this.daoHelper.retrieveList(obj);
	return list;
    }

    /* (non-Javadoc)
     * @see com.xact.generic.GenericXactManagerApi#findXactSubType(java.lang.String)
     */
    public List<XactType> findXactSubType(String criteria) throws XactException {
	XactType obj = GenericXactFactory.createXactType();
	if (criteria == null) {
	    String subTypes[] = { "-100", "888", "999" };
	    obj.addInClause(XactType.PROP_XACTTYPEID, subTypes);
	    obj.addOrderBy(XactType.PROP_DESCRIPTION, XactType.ORDERBY_ASCENDING);
	}
	else {
	    obj.addCustomCriteria(criteria);
	}
	List<XactType> list = this.daoHelper.retrieveList(obj);
	return list;
    }

    /* (non-Javadoc)
     * @see com.xact.generic.GenericXactManagerApi#findXactType(java.lang.String)
     */
    public List<XactType> findXactType(String criteria) throws XactException {
	XactType obj = GenericXactFactory.createXactType();
	String nonSubTypeCriteria = " xact_type_id not in(-100, 888, 999) ";
	if (criteria == null) {
	    criteria = nonSubTypeCriteria;
	}
	else {
	    criteria += " and " + nonSubTypeCriteria;
	}
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(XactType.PROP_DESCRIPTION, XactType.ORDERBY_ASCENDING);
	List<XactType> list = this.daoHelper.retrieveList(obj);
	return list;
    }

}

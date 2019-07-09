package com.xact.generic;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

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
public class GenericXactXmlImpl extends RdbmsDataSourceImpl implements GenericXactManagerApi {

    protected RdbmsDaoQueryHelper daoHelper;

    private Logger logger = Logger.getLogger(GenericXactXmlImpl.class);

    /**
     * Default Constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    public GenericXactXmlImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructor which uses database connection
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public GenericXactXmlImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.setBaseView("XactView");
	this.setBaseClass("com.bean.Xact");
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
    public GenericXactXmlImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
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
    public String findGenericXact(int xactId) throws XactException {
	VwGenericXactList obj = GenericXactFactory.createGerericXact();
	obj.addCriteria(VwGenericXactList.PROP_XACTID, xactId);
	String xml = this.daoHelper.retrieveXml(obj);
	logger.log(Level.INFO, xml);
	return xml;
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
    public String findGenericXact(String criteria) throws XactException {
	VwGenericXactList obj = GenericXactFactory.createGerericXact();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(VwGenericXactList.PROP_XACTDATE, VwGenericXactList.ORDERBY_DESCENDING);
	obj.addOrderBy(VwGenericXactList.PROP_XACTID, VwGenericXactList.ORDERBY_DESCENDING);
	String xml = this.daoHelper.retrieveXml(obj);
	logger.log(Level.INFO, xml);
	return xml;
    }

    /* (non-Javadoc)
     * @see com.xact.generic.GenericXactManagerApi#findXactSubType(java.lang.String)
     */
    public String findXactSubType(String criteria) throws XactException {
	XactType obj = GenericXactFactory.createXmlXactType();
	if (criteria == null) {
	    String subTypes[] = { "-100", "888", "999" };
	    obj.addInClause(XactType.PROP_XACTTYPEID, subTypes);
	    obj.addOrderBy(XactType.PROP_DESCRIPTION, XactType.ORDERBY_ASCENDING);
	}
	else {
	    obj.addCustomCriteria(criteria);
	}
	String xml = this.daoHelper.retrieveXml(obj);
	logger.log(Level.INFO, xml);
	return xml;
    }

    /* (non-Javadoc)
     * @see com.xact.generic.GenericXactManagerApi#findXactType(java.lang.String)
     */
    public String findXactType(String criteria) throws XactException {
	XactType obj = GenericXactFactory.createXmlXactType();
	String nonSubTypeCriteria = " xact_type_id not in(-100, 888, 999) ";
	if (criteria == null) {
	    criteria = nonSubTypeCriteria;
	}
	else {
	    criteria += " and " + nonSubTypeCriteria;
	}
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(XactType.PROP_DESCRIPTION, obj.ORDERBY_ASCENDING);
	String xml = this.daoHelper.retrieveXml(obj);
	logger.log(Level.INFO, xml);
	return xml;
    }

}

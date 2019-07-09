package com.api.postal;

import com.controller.Request;

import com.bean.State;
import com.bean.VwStateCountry;
import com.util.SystemException;

import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

/**
 * A XML implementation of the StatesApi interface which provides
 * functionality for querying and maintaining zip code data using String data
 * types as input parameters and return values to promote a more open systems
 * architecture. The U.S. States data targeted for this implementation is
 * understood to reside in a relational database.
 *
 * @author RTerrell
 *
 */
class StatesXmlImpl extends RdbmsDaoImpl implements StatesApi {
    private RdbmsDaoQueryHelper daoHelper;

    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at
     * the ancestor level.
     *
     * @param dbConn
     *            Database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public StatesXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and
     * the HttpServletRequest object at the acestor level.
     *
     * @param dbConn
     *            Database connection bean
     * @param req
     *            User's request.
     * @throws SystemException
     * @throws DatabaseException
     */
    public StatesXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Fetches data pertaining to one state using the primary key value of
     * "state_id".
     *
     * @param stateId
     *            The state id.
     * @return String XML document when found and null when not found.
     * @throws StatesException
     */
    public String findStateById(int stateId) throws StatesException {
	State state = AddressComponentsFactory.createXmlState();
	state.addCriteria(State.PROP_STATEID, stateId);
	try {
	    return this.daoHelper.retrieveXml(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Fetches state data based on country code.
     *
     * @param countryId
     *            The id of the country to fetch state information.
     * @return String XML document when found and null when not found.
     * @throws StatesException
     */
    public String findStateByCountry(int countryId) throws StatesException {
	State state = AddressComponentsFactory.createXmlState();
	state.addCriteria(State.PROP_COUNTRYID, countryId);
	state.addOrderBy(State.PROP_STATENAME, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Fetches state data based on state name.
     *
     * @param stateName
     *            The name of the state to search for.
     * @return String XML document when found and null when not found.
     * @throws StatesException
     */
    public String findStateByName(String stateName) throws StatesException {
	State state = AddressComponentsFactory.createXmlState();
	state.addLikeClause(State.PROP_STATENAME, stateName);
	state.addOrderBy(State.PROP_STATENAME, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Fetches VwStateCountry data based on custom criteria supplied by the client
     *
     * @param criteria
     *            Custom selection criteria.
     * @return String XML document when found and null when not found.
     * @throws StatesException
     */
    public String findState(String criteria) throws StatesException {
	VwStateCountry state = AddressComponentsFactory.createXmlStateExt();
	state.addCustomCriteria(criteria);
	state.addOrderBy(VwStateCountry.PROP_COUNTRYNAME, OrmBean.ORDERBY_ASCENDING);
	state.addOrderBy(VwStateCountry.PROP_STATENAME, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveXml(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * N/A
     * 
     * @param obj N/A
     * @return 0
     * @throws StatesException
     */
    public int maintainState(State obj) throws StatesException {
	return 0;
    }

    /**
     * N/A
     * 
     * @param obj N/A
     * @return 0
     * @throws StatesException
     */
    public int deleteState(State obj) throws StatesException {
	// TODO Auto-generated method stub
	return 0;
    }

}

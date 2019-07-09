package com.api.postal;

import java.util.List;

import com.controller.Request;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.Country;
import com.bean.State;
import com.bean.TimeZone;
import com.bean.VwStateCountry;
import com.bean.VwZipcode;
import com.bean.Zipcode;

import com.api.db.DatabaseException;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.pagination.PaginationApi;
import com.api.db.pagination.PaginationException;
import com.api.db.pagination.PaginationFactory;
import com.api.db.pagination.PaginationQueryResults;

import com.bean.OrmBean;

import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2String2;
import com.util.SystemException;

/**
 * Class that implements the ZipcodeApi, StatesApi, TimezoneApi, and CountryApi.
 *
 * @author RTerrell
 *
 */
class AddressComponentsImpl extends RdbmsDaoImpl implements ZipcodeApi, StatesApi, TimezoneApi, CountryApi {
    private static Logger logger =  Logger.getLogger("AddressComponentsImpl");

    private RdbmsDaoQueryHelper daoHelper;

    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean
     * at the acestor level.
     *
     * @param dbConn Database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public AddressComponentsImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.className = "AddressComponentsImpl";
	this.baseView = "PersonView";
	this.baseBeanClass = "com.bean.Person";
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and
     * the HttpServletRequest object at the acestor level.
     *
     * @param dbConn Database connection bean
     * @param req User's request.
     * @throws SystemException
     * @throws DatabaseException
     */
    public AddressComponentsImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Fetches data pertaining to one state using the primary key value of "state_id".
     *
     * @param stateId The state id
     * @return {@link State} when found and null when not found.
     * @throws StatesException
     */
    public Object findStateById(int stateId) throws StatesException {
	State state = AddressComponentsFactory.createState();
	state.addCriteria(State.PROP_STATEID, stateId);
	try {
	    return this.daoHelper.retrieveObject(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Fetches state data based on country code.
     *
     * @param countryId The id of the country to fetch state information.
     * @return A List of {@link com.bean.State State} objects when found and null when not found.
     * @throws StatesException
     */
    public Object findStateByCountry(int countryId) throws StatesException {
	State state = AddressComponentsFactory.createState();
	state.addCriteria(State.PROP_COUNTRYID, countryId);
	state.addOrderBy(State.PROP_STATENAME, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Fetches state data based on state name.
     *
     * @param stateName The name of the state to search for.
     * @return {@link State} when found and null when not found.
     * @throws StatesException
     */
    public Object findStateByName(String stateName) throws StatesException {
	State state = AddressComponentsFactory.createState();
	state.addLikeClause(State.PROP_STATENAME, stateName);
	state.addOrderBy(State.PROP_STATENAME, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Fetches VwStateCountry data based on custom criteria supplied by the client
     *
     * @param criteria Custom selection criteria.
     * @return A List of {@link com.bean.VwStateCountry VwStateCountry} objects.
     * @throws StatesException
     *                     When query does not contain any selection criteria or general database erros.
     */
    public List <VwStateCountry> findState(String criteria) throws StatesException {
        if (RMT2String2.isEmpty(criteria)) {
            this.msg = "Cannot perform a State/Province query without any selection criteria";
            logger.error(this.msg);
            throw new StatesException(this.msg);
        }
        
	VwStateCountry state = AddressComponentsFactory.createStateExt();
	state.addCustomCriteria(criteria);
	state.addOrderBy(VwStateCountry.PROP_COUNTRYNAME, OrmBean.ORDERBY_ASCENDING);
	state.addOrderBy(VwStateCountry.PROP_STATENAME, OrmBean.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(state);
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Fetches country data by the database primary key.
     *
     * @param id The id of the country.
     * @return {@link Country} when found and null when not found.
     * @throws CountryException
     */
    public Object findCountryById(int id) throws CountryException {
	Country country = AddressComponentsFactory.createCountry();
	country.addCriteria(Country.PROP_COUNTRYID, id);
	try {
	    return this.daoHelper.retrieveObject(country);
	}
	catch (DatabaseException e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Fetches country data using country's name as a "like" search.
     *
     * @param name The name country to search.
     * @return When found a List of {@link Country}.  Otherwise, null.
     * @throws CountryException
     */
    public Object findCountryByName(String name) throws CountryException {
	Country country = AddressComponentsFactory.createCountry();
	country.addLikeClause(Country.PROP_NAME, name);
	try {
	    return this.daoHelper.retrieveList(country);
	}
	catch (DatabaseException e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Fetches country data using custom criteria supplied by the client.
     *
     * @param criteria Custom selection criteria.
     * @return List of {@link Country} when found.  Otherwise, null.
     * @throws CountryException
     */
    public Object findCountry(String criteria) throws CountryException {
	Country country = AddressComponentsFactory.createCountry();
	country.addCustomCriteria(criteria);
	country.addOrderBy(Country.PROP_NAME, Country.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(country);
	}
	catch (DatabaseException e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Fetches zip code object using exact matching on column, zip.
     *
     * @param zipcode The actual zip code number.
     * @return If found, returns a List of {@link Zipcode} objects.   Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCode(String zipcode) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addCriteria(Zipcode.PROP_ZIP, zipcode);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches zip code using an integer value as the zip code argument.
     *
     * @param zipcode An integer zip code value.
     * @return If found, returns a List o {@link com.bean.Zipcode Zipcode} objects.   
     *         Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCode(int zipcode) throws ZipcodeException {
	String strZipcode = String.valueOf(zipcode);
	return this.findZipByCode(strZipcode);
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, zip.
     *
     * @param zipcode The zip code.
     * @return If found, retruns a List of Zipcode objects.  Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipX(String zipcode) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addLikeClause(Zipcode.PROP_ZIP, zipcode);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Locates Zip Code using an unique identifier key.
     *
     * @param uid The database primary key associated with this zip code.
     * @return If found, retruns a {@link Zipcode} object.  Otherwise,
     *         returns null.
     * @throws ZipcodeException
     */
    public Object findZipById(int uid) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addCriteria(Zipcode.PROP_ZIPID, uid);
	try {
	    return this.daoHelper.retrieveObject(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches zip code extension object using exact matching on column, zip.
     *
     * @param zipcode The actual zip code number.
     * @return If found, returns a List of {@link com.bean.VwZipcode VwZipcode} instances.  Otherwise,
     *         returns null.
     * @throws ZipcodeException
     */
    public List<VwZipcode> findZipExtByCode(String zipcode) throws ZipcodeException {
	VwZipcode zip = AddressComponentsFactory.createZipcodeExt();
	zip.addCriteria(VwZipcode.PROP_ZIP, zipcode);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Locates Zip Code extension using an unique identifier key.
     *
     * @param uid The database primary key associated with this zip code.
     * @return If found, retruns a {@link com.bean.VwZipcode VwZipcode} object.  Otherwise,
     *         returns null.
     * @throws ZipcodeException
     */
    public Object findZipExtById(int uid) throws ZipcodeException {
	VwZipcode zip = AddressComponentsFactory.createZipcodeExt();
	zip.addCriteria(VwZipcode.PROP_ZIPID, uid);
	try {
	    return this.daoHelper.retrieveObject(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, area_code.
     *
     * @param areaCode The area code.
     * @return If found, retruns a List Zipcode objects.   Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByAreaCode(String areaCode) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addLikeClause(Zipcode.PROP_AREACODE, areaCode);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, state.
     *
     * @param state The state that zip code belongs.
     * @return If found, retruns a List of objects.   Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByState(String state) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addLikeClause(Zipcode.PROP_STATE, state);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, city.
     *
     * @param city The city in which the zip code belongs.
     * @return If found, retruns a List of objects.   Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCity(String city) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addLikeClause(Zipcode.PROP_CITY, city);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, county.
     *
     * @param county The county in which the zip code belongs.
     * @return If found, retruns a List of Zipcode objects.   Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByCounty(String county) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addLikeClause(Zipcode.PROP_COUNTYNAME, county);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes using incremental matching on column, time_zone.
     *
     * @param timeZone The times zone which a zip code belongs.
     * @return If found, retruns a List of Zipcode objects.   Otherwise, returns null.
     * @throws ZipcodeException
     */
    public Object findZipByTimeZone(String timeZone) throws ZipcodeException {
	Zipcode zip = AddressComponentsFactory.createZipcode();
	zip.addLikeClause(Zipcode.PROP_TIMEZONEID, timeZone);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }

    /**
     * Fetches one or more zip codes based on custom criteria supplied by the client.
     * Be sure to match column names to those of the zipcode table definition.
     *
     * @param criteria Custom criteria.
     * @return If found, retruns a List of {@link com.bean.VwZipcode VwZipcode} objects.
     *         Otherwise, returns null.
     * @throws ZipcodeException
     *                  When criteria has not been entered.
     */
    public List<VwZipcode> findZip(String criteria) throws ZipcodeException {
	if (RMT2String2.isEmpty(criteria)) {
	    this.msg = "Cannot perform a Zip Code query without any selection criteria";
            logger.error(this.msg);
            throw new ZipcodeException(this.msg);
	}
	VwZipcode zip = AddressComponentsFactory.createZipcodeExt();
	zip.addCustomCriteria(criteria);
	zip.addOrderBy(VwZipcode.PROP_STATE, VwZipcode.ORDERBY_ASCENDING);
        zip.addOrderBy(VwZipcode.PROP_CITY, VwZipcode.ORDERBY_ASCENDING);
        zip.addOrderBy(VwZipcode.PROP_CITYALIASNAME, VwZipcode.ORDERBY_ASCENDING);
        zip.addOrderBy(VwZipcode.PROP_ZIP, VwZipcode.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(zip);
	}
	catch (DatabaseException e) {
	    throw new ZipcodeException(e);
	}
    }
    
    public PaginationQueryResults findZip(String criteria, int pageNo) throws ZipcodeException {
        if (RMT2String2.isEmpty(criteria)) {
            this.msg = "Cannot perform a Zip Code pagination query without any selection criteria";
            logger.error(this.msg);
            throw new ZipcodeException(this.msg);
        }
        VwZipcode zip = AddressComponentsFactory.createZipcodeExt();
        zip.addCustomCriteria(criteria);
        zip.addOrderBy(VwZipcode.PROP_STATE, VwZipcode.ORDERBY_ASCENDING);
        zip.addOrderBy(VwZipcode.PROP_CITY, VwZipcode.ORDERBY_ASCENDING);
        zip.addOrderBy(VwZipcode.PROP_CITYALIASNAME, VwZipcode.ORDERBY_ASCENDING);
        zip.addOrderBy(VwZipcode.PROP_ZIP, VwZipcode.ORDERBY_ASCENDING);
        try {
            PaginationApi pageApi = PaginationFactory.createDao(this.connector);
            PaginationQueryResults results = (PaginationQueryResults) pageApi.retrieveList(zip, pageNo);
            return results;
        }
        catch (DatabaseException e) {
            this.msg = "Unable to execute pagination query for zip code due to database error";
            logger.error(this.msg);
            throw new ZipcodeException(this.msg, e);
        }
        catch (PaginationException e) {
            this.msg = "Unable to execute pagination query for zip code due to pagination  error";
            logger.error(this.msg);
            throw new ZipcodeException(this.msg, e);
        }
    }
    

    /**
     * Searches for time zone data using a unique identifier which is usually
     * a database primary key.
     *
     * @param id The id of the target time zone.
     * @return If found, data associated with "_id" is returned as type, TimeZone.
     *         Otherwise, null is returned.
     * @throws TimezoneException
     */
    public Object findTimeZoneById(int id) throws TimezoneException {
	TimeZone tz = AddressComponentsFactory.createTimeZone();
	tz.addCriteria(TimeZone.PROP_TIMEZONEID, id);
	try {
	    return this.daoHelper.retrieveObject(tz);
	}
	catch (DatabaseException e) {
	    throw new TimezoneException(e);
	}
    }

    /**
     * Searches the time_zone table for all time zone codes and their descriptions.
     *
     * @return If found, a List of {@link com.bean.TimeZone TimeZone} objects are packaged and returned to 
     *              the caller in ascending order by timezone description.  Otherwise, null is returned.
     * @throws TimezoneException
     */
    public Object findAllTimeZones() throws TimezoneException {
	TimeZone tz = AddressComponentsFactory.createTimeZone();
	tz.addCustomCriteria(null);
	tz.addOrderBy(TimeZone.PROP_DESCR, TimeZone.ORDERBY_ASCENDING);
	try {
	    return this.daoHelper.retrieveList(tz);
	}
	catch (DatabaseException e) {
	    throw new TimezoneException(e);
	}
    }

    /**
     * Creates a new or modifies an existing State object and 
     * persist to the database
     * 
     * @param obj The {@link com.bean.State State} intstance to persist
     * @return int 
     *           as the new state id as a result of an insert or the total 
     *           number of rows effected from an update.
     * @throws StatesException General database error
     */
    public int maintainState(State obj) throws StatesException {
	int rc = 0;
	if (obj.getStateId() > 0) {
	    rc = this.updateState(obj);
	}
	if (obj.getStateId() == 0) {
	    rc = this.insertState(obj);
	}
	return rc;
    }

    /**
     * Adds a state record to the database using <i>obj</i>.
     * 
     * @param obj The state object add.
     * @return The state id of the record to add.
     * @throws StatesException General database error
     */
    private int insertState(State obj) throws StatesException {
	this.validateState(obj);
	try {
	    // Create State
	    int key = this.insertRow(obj, true);
	    obj.setStateId(key);
	    return key;
	}
	catch (Exception e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Modifies a state record and persist the changes to the database.
     * 
     * @param obj The state object to modify.
     * @return int represeinting the total number of records effected by the transaction.
     * @throws StatesException General database error
     */
    private int updateState(State obj) throws StatesException {
	this.validateState(obj);
	try {
	    obj.addCriteria(State.PROP_STATEID, obj.getStateId());
	    int rows = this.updateRow(obj);
	    return rows;
	}
	catch (Exception e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Validates a instance of {@link com.bean.State State}.
     * 
     * @param obj The State instance to update
     * @throws ContactException 
     *            if <i>obj</i> is null or <i>state id</i> is null or <i>state name</i> 
     *            is null or <i>country id</i> is less than or equal to zero.
     */
    protected void validateState(State obj) throws StatesException {
	if (obj == null) {
	    this.msg = "State instance cannot be null for insert/update operations";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new StatesException(this.msg);
	}

	if (obj.getAbbrCode() == null || obj.getAbbrCode().equals("")) {
	    this.msg = "State/Province code must have a value";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new StatesException(this.msg);
	}
	if (obj.getStateName() == null || obj.getStateName().equals("")) {
	    this.msg = "State/Province name must have a value";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new StatesException(this.msg);
	}
	if (obj.getCountryId() <= 0) {
	    this.msg = "State/Province must be assoicated with a country";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new StatesException(this.msg);
	}
    }

    /**
     * Deletes a State record from the database.
     * 
     * @param state The state to delete.
     * @return The total number of row effected.
     * @throws StatesException General database error
     */
    public int deleteState(State obj) throws StatesException {
	int rc = 0;
	if (obj == null) {
	    this.msg = "State instance cannot be null for delete operation";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new StatesException(this.msg);
	}

	obj.addCriteria(State.PROP_STATEID, obj.getStateId());
	try {
	    rc = this.deleteRow(obj);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new StatesException(e);
	}
    }

    /**
     * Uses <i>obj</i> to perform an insert or update a country record to the a RDBMS database.
     * 
     * @param obj A {@link com.bean.Country Country} object
     * @return int 
     *           The country id for insert transactions or the total number of records effected 
     *           by update transactions. 
     * @throws CountryException General database error
     */
    public int maintainCountry(Country obj) throws CountryException {
	int rc = 0;
	if (obj.getCountryId() > 0) {
	    rc = this.updateCountry(obj);
	}
	if (obj.getCountryId() == 0) {
	    rc = this.insertCountry(obj);
	}
	return rc;
    }

    /**
     * Adds a country record to the database using <i>obj</i>.
     * 
     * @param obj A {@link com.bean.Country Country} object
     * @return int The country id of the record added.
     * @throws CountryException General database error
     */
    private int insertCountry(Country obj) throws CountryException {
	this.validateCountry(obj);
	try {
	    int key = this.insertRow(obj, true);
	    obj.setCountryId(key);
	    return key;
	}
	catch (Exception e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Modifies a country record and persist the changes to the database.
     * 
     * @param obj A {@link com.bean.Country Country} object
     * @return The total number of rows effected by the transaction.
     * @throws CountryException General database error
     */
    private int updateCountry(Country obj) throws CountryException {
	this.validateCountry(obj);
	try {
	    obj.addCriteria(Country.PROP_COUNTRYID, obj.getCountryId());
	    int rows = this.updateRow(obj);
	    return rows;
	}
	catch (Exception e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Deletes a country record from a RDBMS database using <i>obj</i>.
     * 
     * @param obj An {@link com.bean.Country Country} object
     * @return  The total number of records effected from the transaction.
     * @throws CountryException General database error
     */
    public int deleteCountry(Country obj) throws CountryException {
	int rc = 0;
	if (obj == null) {
	    this.msg = "Country instance cannot be null for delete operation";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new CountryException(this.msg);
	}

	obj.addCriteria(Country.PROP_COUNTRYID, obj.getCountryId());
	try {
	    rc = this.deleteRow(obj);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new CountryException(e);
	}
    }

    /**
     * Validates a instance of {@link com.bean.Country Country}.
     * 
     * @param obj The Country instance to update
     * @throws CountryException 
     *            if <i>obj</i> is null or <i>Name</i> attribute of obj is null.
     */
    protected void validateCountry(Country obj) throws CountryException {
	if (obj == null) {
	    this.msg = "Country instance cannot be null for insert/update operations";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new CountryException(this.msg);
	}

	if (obj.getName() == null) {
	    this.msg = "Counry name must have a value";
	    AddressComponentsImpl.logger.log(Level.ERROR, this.msg);
	    throw new CountryException(this.msg);
	}
    }
}

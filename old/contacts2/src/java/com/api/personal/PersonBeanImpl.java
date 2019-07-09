package com.api.personal;

import java.util.List;

import com.controller.Request;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.bean.Person;
import com.bean.VwPersonAddress;

import com.api.ContactException;

import com.api.address.AddressApi;
import com.api.address.AddressFactory;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * An implementation of the PersonApi interface which provides functionality 
 * for querying and maintaining personal contact data using OrmBean types as 
 * input and return data sources.   The personal contact data targeted for 
 * this implementatio is understood to reside in a relational database.
 *  
 * @author RTerrell
 *
 */
class PersonBeanImpl extends RdbmsDaoImpl implements PersonApi {
    private Logger logger;
    protected RdbmsDaoQueryHelper daoHelper;
    protected String criteria;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at
     * the acestor level.
     * 
     * @param dbConn
     *            The database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
    public PersonBeanImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.className = "PersonBeanImpl";
	this.baseView = "PersonView";
	this.baseBeanClass = "com.bean.Person";
	this.logger = Logger.getLogger("PersonBeanImpl");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and
     * the HttpServletRequest object at the acestor level.
     * 
     * @param dbConn
     *            The database connection bean.
     * @param req
     *            The user's request object.
     * @throws SystemException
     * @throws DatabaseException
     */
    public PersonBeanImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    
    /**
     * Releases allocated resources.
     */
    public void close() {
	this.logger = null;
	this.daoHelper.close();
	this.daoHelper = null;
        super.close();
    }
    
    
    /**
     * Locates a Person object by primary key or it Person Id.
     * 
     * @param uid
     *            The unique identifier for the personal conact(s) to query.
     * @return {@link Person} object if found and null if not found.
     * @throws ContactException
     */
    public Person findContact(int uid) throws ContactException {
	Person person = PersonFactory.createPerson();
	person.addCriteria(Person.PROP_PERSONID, uid);
    try {
        return (Person) this.daoHelper.retrieveObject(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }
    
    /**
     * Fetches a perrson entity along with its address information.
     * 
     * @param perId The internal id of the business.
     * @return {@link com.bean.VwPersonAddress VwPersonAddress}
     * @throws ContactException
     */
    public VwPersonAddress findPerAddress(int perId) throws PersonException {
        VwPersonAddress person = PersonFactory.createPersonAddress();
        person.addCriteria(VwPersonAddress.PROP_PERSONID, perId);
        try {
            return (VwPersonAddress) this.daoHelper.retrieveObject(person);
        }
        catch (DatabaseException e) {
            throw new PersonException(e);
        }
    }

    /**
     * Fetches personal contact objects by first name.
     * 
     * @param firstName
     *            The contact's first name
     * @return List of {@link Person} objects
     * @throws PersonException
     */
    public List <Person> findPerByFirstName(String firstName) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addLikeClause(Person.PROP_FIRSTNAME, firstName);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches one or more personal contact objects based on the last name.
     * 
     * @param lastName
     *            The contact's last name.
     * @return List of {@link Person} objects.
     * @throws PersonException
     */
    public List <Person> findPerByLastName(String lastName) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addLikeClause(Person.PROP_LASTNAME, lastName);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches personal contacts by gender id.
     * 
     * @param genderId
     *            The id of the gender.
     * @return A List of {@link Person} objects
     * @throws PersonException
     */
    public List <Person> findPerByGender(int genderId) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addCriteria(Person.PROP_GENDERID, genderId);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches personal contact objects by marital status.
     * 
     * @param maritalStatus
     *            The marital status id.
     * @return A List of {@link Person} objects.
     * @throws PersonException
     */
    public List <Person> findPerByMaritalStatus(int maritalStatus) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addCriteria(Person.PROP_MARITALSTATUSID, maritalStatus);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches a Person object by race.
     * 
     * @param raceId
     *            The race id.
     * @return A Lsit of {@link Person}
     * @throws PersonException
     */
    public List <Person> findPerByRace(int raceId) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addCriteria(Person.PROP_RACEID, raceId);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches a Person object by SSN
     * 
     * @param ssn
     *            The social security number of a person.
     * @return A List of {@link Person} objects.
     * @throws PersonException
     */
    public List <Person> findPerBySSN(String ssn) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addLikeClause(Person.PROP_SSN, ssn);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches a Person object by Birth Date.
     * 
     * @param dob
     *            The birth date of a person
     * @return A List o f{@link Person}
     * @throws PersonException
     */
    public List <Person> findPerByBirthDate(String dob) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addCriteria(Person.PROP_BIRTHDATE, dob);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches a Person object by email address.
     * 
     * @param email
     *            The email address of a person.
     * @return A List of {@link Person} objects.
     * @throws PersonException
     */
    public List <Person> findPerByEMail(String email) throws PersonException {
	Person person = PersonFactory.createPerson();
	person.addLikeClause(Person.PROP_EMAIL, email);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * Fetches a personal contact object using custom selection criteria. Be
     * sure that the intended base view and base class is set for this api
     * before invoking this method.
     * 
     * @param criteria
     * @return A List of {@link com.bean.VwPersonAddress}
     * @throws ContactException
     */
    public List <VwPersonAddress> findContact(String criteria) throws ContactException {
	VwPersonAddress person = PersonFactory.createPersonAddress();
	person.addCustomCriteria(criteria);
    try {
        return this.daoHelper.retrieveList(person);
    }
    catch (DatabaseException e) {
        throw new PersonException(e);
    }
    }

    /**
     * This method is responsible for creating and updating personal contacts.
     * If Person id is less than or equal to zero then a personal profile will
     * be created. Otherwise, update the database with personal contact changes.
     * 
     * @param contact {@link Person} object.
     * @return int.  The id of the person object for insert operations or the 
     *         total number of rows effected by an update operation. 
     * @throws ContactException
     */
    public int maintainContact(Object contact) throws ContactException {
	int rc;
	Person per;
	if (contact instanceof Person) {
	    per = (Person) contact;
	}
	else {
	    this.msg = "maintainContact method failed.  Input object failed to resolve to Person type.";
	    throw new PersonException(this.msg);
	}
	if (per.getPersonId() == 0) {
	    rc = this.insertPerson(per);
	    per.setPersonId(rc);
	}
	else {
	    rc = this.updatePerson(per);
	}
	return rc;
    }

    /**
     * This method is responsible for adding Person to the database.
     * 
     * @param obj The Person object
     * @return The id of the person object
     * @throws PersonException
     */
    protected int insertPerson(Person obj) throws PersonException {
	try {
	    // Validate person objec
	    this.validateContact(obj);
	    this.adjustContactKeys(obj);
	    obj.setShortname(obj.getLastname() + ", " + obj.getFirstname());

	    // Set category to null if value is equal zero
            if (obj.getCategoryId() == 0) {
        	obj.setNull(Person.PROP_CATEGORYID);
            }
	    // Create person
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    if (obj.getUserId() == null) {
		obj.setUserId(ut.getLoginId());
	    }
	    int key = this.insertRow(obj, true);
	    return key;
	}
	catch (Exception e) {
	    throw new PersonException(e);
	}
    }

    /**
     * This method is responsible for updating a Person.
     * 
     * @param obj The person object that is to be updated.
     * @return The total number of rows effected by transaction.
     * @throws PersonException
     */
    protected int updatePerson(Person obj) throws PersonException {
	try {
	    // Validate person objec
	    this.validateContact(obj);
	    this.adjustContactKeys(obj);
	    obj.setShortname(obj.getLastname() + ", " + obj.getFirstname());
	    
	    // Set category to null if value is equal zero
            if (obj.getCategoryId() == 0) {
        	obj.setNull(Person.PROP_CATEGORYID);
            }

	    // Update person
	    obj.addCriteria(Person.PROP_PERSONID, obj.getPersonId());
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	    int rows = this.updateRow(obj);
	    return rows;
	}
	catch (Exception e) {
	    throw new PersonException(e);
	}
    }

    /**
     * Check if foreign key values of Contact instance are not set, and if true, set to null.
     * 
     * @param contact An instance of {@link com.api.Contact Contact}.
     */
    private void adjustContactKeys(Person contact) {
	if (contact.getGenderId() == 0) {
	    contact.setNull(Person.PROP_GENDERID);
	}
	if (contact.getTitle() == 0) {
	    contact.setNull(Person.PROP_TITLE);
	}
	if (contact.getMaritalStatusId() == 0) {
	    contact.setNull(Person.PROP_MARITALSTATUSID);
	}
	if (contact.getRaceId() == 0) {
	    contact.setNull(Person.PROP_RACEID);
	}
	return;
    }

    /**
     * Not implemented.
     * 
     * @param source
     *            The Person object that is to be copied.
     * @return null
     * @throws PersonException
     */
    public Object copyPerson(Object source) throws PersonException {
	Person newObj = null;
	return newObj;
    }

    /**
     * Deletes a personal contact from the database.
     * 
     * @param obj The {@link Person} object to be deleted.
     * @return The number of rows effected.
     * @throws ContactException
     */
    public int deleteContact(Object obj) throws ContactException {
	if (obj == null) {
	    this.msg = "Person Contact delete failed.  Input object is null or invalid.";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new PersonException(this.msg);
	}
	Person per;
	if (obj instanceof Person) {
	    per = (Person) obj;
	}
	else {
	    this.msg = "Person Contact delete failed.  Input object failed to resolve to Person type.";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new PersonException(this.msg);
	}

	per.addCriteria(Person.PROP_PERSONID, per.getPersonId());
	try {
	    // First, delete address component
	    AddressApi addrApi = AddressFactory.createAddressApi(this.connector);
	    addrApi.deleteContact(per);
	    return this.deleteRow(per);
	}
	catch (DatabaseException e) {
	    throw new PersonException(e);
	}
    }

    /**
     * Validates a Person objects.
     * 
     * @param contact The person object to be validated.
     * @throws ContactException
     *             if _base is not of type Person or the person's first name or
     *             last name is null.
     */
    public void validateContact(Object contact) throws ContactException {
	Person per;
	if (contact instanceof Person) {
	    per = (Person) contact;
	}
	else {
	    this.msg = "maintainContact method failed.  Input object failed to resolve to Person type.";
	    throw new PersonException(this.msg);
	}
	String temp = null;

	// Person First Name must exist
	temp = per.getFirstname();
	if (temp == null || temp.length() <= 0) {
	    throw new PersonException(this.connector, 501, null);
	}
	// Person Last Name must exist
	temp = per.getLastname();
	if (temp == null || temp.length() <= 0) {
	    throw new PersonException(this.connector, 502, null);
	}
	return;
    }

}

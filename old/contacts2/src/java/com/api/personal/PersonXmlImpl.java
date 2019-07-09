package com.api.personal;

import com.controller.Request;

import com.bean.Person;
import com.bean.VwPersonAddress;

import com.util.SystemException;

import com.api.ContactException;

import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;
import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.db.DatabaseConnectionBean;

/**
 * An implementation of the PersonApi interface which provides functionality for
 * querying and maintaining personal contact data using String data types as
 * input parameters and return values to promote a more open systems
 * architecture. The personal contact data targeted for this implementation is
 * understood to reside in a relational database.
 * 
 * @author RTerrell
 * 
 */
class PersonXmlImpl extends RdbmsDaoImpl implements PersonApi {
    protected String criteria;
    
    protected RdbmsDaoQueryHelper daoHelper;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at
     * the acestor level.
     * 
     * @param dbConn
     *            The database connection bean.
     * @throws SystemException
     * @throws DatabaseException
     */
    public PersonXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
        super(dbConn);
        this.className = "PersonXmlImpl";
        this.baseView = "PersonView";
        this.baseBeanClass = "com.bean.Person";
        this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
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
    public PersonXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
        super(dbConn, req);
    }

 
    /**
     * Locates a person contact along with its address information using a
     * primary key or Person Id.  Returns the results as XML.
     * 
     * @param uid
     *            The unique identifier for the personal conact(s) to query.
     * @return Personal contact data in the format of XML if found and null if
     *         not found.
     * @throws ContactException
     */
    public String findContact(int uid) throws ContactException {
        VwPersonAddress person = PersonFactory.createXmlPersonAddress();
        person.addCriteria(VwPersonAddress.PROP_PERSONID, uid);
        try {
            return this.daoHelper.retrieveXml(person);
        }
        catch (DatabaseException e) {
            throw new PersonException(e);
        }
    }

    
    /**
     * Fetches a perrson entity along with its address information.
     * 
     * @param perId The internal id of the business.
     * @return XML document.
     * @throws ContactException
     */
    public Object findPerAddress(int perId) throws PersonException {
        VwPersonAddress person = PersonFactory.createXmlPersonAddress();
        person.addCriteria(VwPersonAddress.PROP_PERSONID, perId);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found.
     * @throws PersonException
     */
    public String findPerByFirstName(String firstName) throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addLikeClause("Firstname", firstName);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found..
     * @throws PersonException
     */
    public String findPerByLastName(String lastName) throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addLikeClause("Lastname", lastName);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found.
     * @throws PersonException
     */
    public String findPerByGender(int genderId) throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addCriteria("GenderId", genderId);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found..
     * @throws PersonException
     */
    public String findPerByMaritalStatus(int maritalStatus)
            throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addCriteria("MaritalStatus", maritalStatus);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found..
     * @throws PersonException
     */
    public String findPerByRace(int raceId) throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addCriteria("RaceId", raceId);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found..
     * @throws PersonException
     */
    public String findPerBySSN(String ssn) throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addLikeClause("Ssn", ssn);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found..
     * @throws PersonException
     */
    public String findPerByBirthDate(String dob) throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addLikeClause("BirthDate", dob);
        try {
            return this.daoHelper.retrieveXml(person);
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
     * @return Personal contact data in the format of XML when found and null if
     *         not found..
     * @throws PersonException
     */
    public String findPerByEMail(String email) throws PersonException {
        Person person = PersonFactory.createXmlPerson();
        person.addLikeClause("Email", email);
        try {
            return this.daoHelper.retrieveXml(person);
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
    public String findContact(String criteria) throws ContactException {
	VwPersonAddress person = PersonFactory.createXmlPersonAddress();
        this.setDatasourceSql(DbSqlConst.WHERE_KEY, criteria);
        try {
            return this.daoHelper.retrieveXml(person);
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

    /* (non-Javadoc)
     * @see com.api.Contact#deleteContact(java.lang.Object)
     */
    public int deleteContact(Object obj) throws ContactException {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.api.Contact#maintainContact(java.lang.Object)
     */
    public int maintainContact(Object data) throws ContactException {
        // TODO Auto-generated method stub
        return 0;
    }
}

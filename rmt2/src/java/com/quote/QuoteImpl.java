package com.quote;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.entity.Quote;

import com.util.RMT2Date;
import com.util.SystemException;

/**
 * 
 * @author appdev
 *
 */
class QuoteImpl extends RdbmsDaoImpl implements QuoteApi {

    private Logger logger = Logger.getLogger(QuoteImpl.class);

    private RdbmsDaoQueryHelper daoHelper;

    /**
     * Default constructor that is inaccessible to the public.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    private QuoteImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructs a QuoteImpl object with a database connection.
     * 
     * @param dbConn
     *          {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @throws DatabaseException
     * @throws SystemException
     */
    public QuoteImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Constructs a QuoteImpl object with a database connection and the user's request
     * 
     * @param dbConn
     *          {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     * @param request
     *          {@link com.controller.Request Request}
     * @throws DatabaseException
     * @throws SystemException
     */
    public QuoteImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	super(dbConn, request);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Deletes a quote from the database.
     *  
     * @param quoteId
     *          the id of the quote which is a integer
     * @return int
     *          the total number of rows deleted.
     * @throws QuoteException
     */
    public int deleteQuote(int quoteId) throws QuoteException {
	Quote quote = QuoteFactory.createQuote();
	quote.addCriteria(Quote.PROP_QUOTEID, quoteId);
	return this.deleteRow(quote);
    }

    /**
     * Get a single quote from the database by quote id.
     * 
     * @param quoteId
     *           the id of the quote which is a integer
     * @return Object
     *           the actual runtime type is {@link com.entity.Quote Quote} 
     * @throws QuoteException
     */
    public Object getQuote(int quoteId) throws QuoteException {
	Quote quote = QuoteFactory.createQuote();
	quote.addCriteria(Quote.PROP_QUOTEID, quoteId);
	return this.daoHelper.retrieveObject(quote);
    }

    /**
     * Get a single quote from the database by email address.
     * 
     * @param emailId
     *           the email address as a String
     * @return Object 
     *           the actual runtime type is {@link com.entity.Quote Quote} 
     * @throws QuoteException
     */
    public Object getQuote(String emailId) throws QuoteException {
	Quote quote = QuoteFactory.createQuote();
	quote.addCriteria(Quote.PROP_EMAIL, emailId);
	return this.daoHelper.retrieveObject(quote);
    }

    /**
     * Get one or more quotes from the database using custom selection criteria.
     * 
     * @param criteria
     *          SQL based selection criteria as a String
     * @return Object
     *           the actual runtime type is a List of {@link com.entity.Quote Quote} objects
     * @throws QuoteException
     */
    public Object getQuotes(String criteria) throws QuoteException {
	Quote quote = QuoteFactory.createQuote();
	quote.addCustomCriteria(criteria);
	return this.daoHelper.retrieveList(quote);
    }

    /**
     * Saves changes of a quote to the database.  This method is capable of determining if the 
     * quote should be inserted or modified to the database.  If the quote id is euqal to zero, 
     * then an SQL insert is performed.   When the quote id is greater than or equal to 1, then 
     * a SQL update is performed.  
     *  
     * @param quote
     *          the {@link com.entity.Quote Quote} instance that is to be persisted.
     * @return int
     *           The id of primary key when an insert is performed.  The total number of rows 
     *           effected when an update is performed.
     * @throws QuoteException
     *           General data access errors.
     */
    public int maintainQuote(Quote quote) throws QuoteException {
	this.validateQuote(quote);

	int rc = 0;
	if (quote.getQuoteId() <= 0) {
	    rc = this.createQuote(quote);
	}
	else {
	    rc = this.updateQuote(quote);
	}
	return rc;
    }

    /**
     * Adds a quote to the database.
     * 
     * @param quote
     *          {@link com.entity.Quote Quote} 
     * @return int
     *           The primary key value related to the new quote added to the database.
     * @throws QuoteException
     *           General data access errors.
     */
    private int createQuote(Quote quote) throws QuoteException {
	try {
	    UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	    quote.setDateCreated(ut.getDateCreated());
	    quote.setDateUpdated(ut.getDateCreated());
	    if (quote.getUserId() == null) {
		quote.setUserId(ut.getLoginId());
	    }
	}
	catch (SystemException e) {
	    Date curDate = new Date();
	    quote.setDateCreated(curDate);
	    quote.setDateUpdated(curDate);
	}

	// Default quote status to planning
	quote.setQuoteStatusId(QuoteConst.STATUS_PLAN);

	int key = this.insertRow(quote, true);
	return key;
    }

    /**
     * Applies the changes to the database for an existing quote.
     * 
     * @param quote
     *           {@link com.entity.Quote Quote}
     * @return int
     *           The total number of rows effected by the transaction.
     * @throws QuoteException
     *           General data access errors.
     */
    private int updateQuote(Quote quote) throws QuoteException {
	int rows = 0;
	UserTimestamp ut = RMT2Date.getUserTimeStamp(this.request);
	quote.setDateUpdated(ut.getDateCreated());
	if (quote.getUserId() == null) {
	    quote.setUserId(ut.getLoginId());
	}
	rows = this.updateRow(quote);
	return rows;
    }

    /**
     * Validate a quote instance.  This method requires that the company name, contact name, 
     * email address, state, zip code, and solution type properties are provided with values.  
     * Otherwise, and error is thrown. 
     * 
     * @param quote
     *          {@link com.entity.Quote Quote}
     * @throws QuoteException
     *          When either company name, contact name, email address, state, zip code, or solution 
     *          type are not provided with data values.
     */
    private void validateQuote(Quote quote) throws QuoteException {
	if (quote == null) {
	    this.msg = "Quote add/update failed...quote instance is invalid";
	    logger.log(Level.ERROR, this.msg);
	    throw new QuoteException(this.msg);
	}
	if (quote.getContactName() == null || quote.getContactName().equals("")) {
	    this.msg = "Contact name is required";
	    logger.log(Level.ERROR, this.msg);
	    throw new QuoteException(this.msg);
	}

	if (quote.getState() == null || quote.getState().equals("")) {
	    this.msg = "State is required";
	    logger.log(Level.ERROR, this.msg);
	    throw new QuoteException(this.msg);
	}

	if (quote.getZip() == 0) {
	    this.msg = "Zip code is required";
	    logger.log(Level.ERROR, this.msg);
	    throw new QuoteException(this.msg);
	}

	// Validate Email address
	if (quote.getBecomeMember() == 1) {
	    if (quote.getEmail() == null || quote.getEmail().equals("")) {
		this.msg = "Contact email address is required";
		logger.log(Level.ERROR, this.msg);
		throw new QuoteException(this.msg);
	    }
	    // Validate the uniqueness of the email address.
	    Object obj = null;
	    try {
		obj = this.getQuote(quote.getEmail());
	    }
	    catch (Exception e) {
		this.msg = "System problem occurred while trying to determine the uniqueness of contact\'s email, " + quote.getEmail();
		logger.log(Level.ERROR, this.msg);
		throw new QuoteException(this.msg);
	    }
	    if (obj != null) {
		this.msg = "The email address is already being used";
		logger.log(Level.ERROR, this.msg);
		throw new QuoteException(this.msg);
	    }
	}

	// Validate Email address
	if (quote.getSolutionType() == 0) {
	    this.msg = "At least one software need is required";
	    logger.log(Level.ERROR, this.msg);
	    throw new QuoteException(this.msg);
	}
    }

}

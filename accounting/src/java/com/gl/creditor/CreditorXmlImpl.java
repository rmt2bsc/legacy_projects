package com.gl.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;

import java.sql.ResultSet;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;

//import com.remoteservices.http.HttpRemoteServicesConsumer;
import com.util.RMT2Date;
import com.util.SystemException;

import com.bean.Creditor;
import com.bean.CreditorType;
import com.bean.GlAccounts;

import com.bean.criteria.CreditorCriteria;
import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.gl.BasicGLApi;
import com.gl.GeneralLedgerFactory;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

/**
 * A XML implementation of {@link com.gl.BasicGLApi BasicGLApi}.  It provides functionality 
 * that creates, updates, deletes, and queries General Ledger accounts, account types, 
 * and account category entities.
 * 
 * @author RTerrell
 * @deprecated This class is never used.
 *
 */
class CreditorXmlImpl extends RdbmsDaoImpl implements CreditorApi {
    private RdbmsDaoQueryHelper daoHelper;

    private Logger logger;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public CreditorXmlImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and the 
     * HttpServletRequest object at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @param req The request object.
     * @throws SystemException
     * @throws DatabaseException
     */
    public CreditorXmlImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Creates the DAO helper and the logger.
     */
    protected void initApi() throws DatabaseException, SystemException {
	this.logger = Logger.getLogger("CreditorXmlImpl");
    }

    /**
     * Find one or more creditors using custom selection criteria.
     * 
     * @param criteria The selection criteria that can be applied to the SQL statement.
     * @return A List of {@link com.bean.Creditor Creditor} objects or null if no 
     *         creditors are found.
     * @throws CreditorException
     */
    public String findCreditor(String criteria) throws CreditorException {
	Creditor obj = CreditorFactory.createCreditor();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Calculates the creditor's balance.
     * 
     * @param id the internal unique id of the creditor.
     * @return The creditor's balance.
     * @throws CreditorException
     */
    public double findCreditorBalance(int id) throws CreditorException {
	String sql = "select isnull(sum(amount), 0) amt from creditor_activity where creditor_id = " + id;
	ResultSet rs = null;
	double amt = 0;
	try {
	    rs = this.executeQuery(sql);
	    if (rs.next()) {
		amt = rs.getDouble("amt");
	    }
	    return amt;
	}
	catch (Exception e) {
	    this.logger.log(Level.ERROR, e.getMessage());
	    throw new CreditorException(e);
	}
    }

    /**
     * 
     * @param creditorId
     * @return
     * @throws CreditorException
     */
    public String findCreditorBusiness(int creditorId) throws CreditorException {
	return null;
    }

    /**
     * Locates a Creditor and related Business data using <i>criteria</i>.  
     * The result set is order by business name.
     * 
     * @param criteria Custom selection criteria.
     * @return XML document.
     * @throws CreditorException
     */
    public String findCreditorBusiness(String criteria) throws CreditorException {
	return null;
    }

    /**
     * Find creditor by account number.
     * 
     * @param acctNo The account number
     * @return A List of {@link com.bean.Creditor Creditor} objects or null if no 
     *         creditors are found.
     * @throws CreditorException General database errors.
     */
    public String findByAcctNo(String acctNo) throws CreditorException {
	Creditor obj = CreditorFactory.createXmlCreditor();
	obj.addCriteria(Creditor.PROP_ACCOUNTNUMBER, acctNo);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Find creditor by creditor type id.
     * 
     * @param creditorTypeId The creditor type id.
     * @return A List of {@link com.bean.Creditor Creditor} objects or null if no 
     *         creditors are found.
     * @throws CreditorException General database errors.
     */
    public String findByCreditorType(int creditorTypeId) throws CreditorException {
	Creditor obj = CreditorFactory.createXmlCreditor();
	obj.addCriteria(Creditor.PROP_CREDITORTYPEID, creditorTypeId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Find creditor by its unique id which is the database primary key value.
     * 
     * @param id The internal id of the creditory
     * @return {@link com.bean.Creditor Creditor}
     * @throws CreditorException General Database errors
     */
    public String findById(int id) throws CreditorException {
	Creditor obj = CreditorFactory.createXmlCreditor();
	obj.addCriteria(Creditor.PROP_CREDITORID, id);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Retrieve creditors by business contact.
     * 
     * @param businessId The internal unique id of the business.
     * @return A List of {@link com.bean.Creditor Creditor} objects.
     * @throws CreditorException
     */
    public Object findByBusinessId(int businessId) throws CreditorException {
	Creditor obj = CreditorFactory.createXmlCreditor();
	obj.addCriteria(Creditor.PROP_BUSINESSID, businessId);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Locates one or more CreditorType objects using custom criteria supplied by client.
     * 
     * @param criteria Custom selection criteria.
     * @return A List of {@link com.bean.CreditorType CreditorType} objects or null if 
     *         no customer types are found.
     * @throws CreditorException General Database errors
     */
    public String findCreditorType(String criteria) throws CreditorException {
	CreditorType obj = CreditorFactory.createXmlCreditorType();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Locates a CreditorType object by its primary key value as <i>id</i>.
     * 
     * @param id The primary key of the creditor type.
     * @return {@link com.bean.CreditorType CreditorType}
     * @throws CreditorException General Database errors
     */
    public String findCreditorTypeById(int id) throws CreditorException {
	CreditorType obj = CreditorFactory.createXmlCreditorType();
	obj.addCriteria(CreditorType.PROP_CREDITORTYPEID, id);
	try {
	    return this.daoHelper.retrieveXml(obj);
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Initiates the insert or update of creditor data to a database.   Assign null 
     * to <i>objExt</i> if the application does not need to extend the creditor profile.
     * 
     * @param cred The base creditor object
     * @param credExt The extension to the base creditor object
     * @return Either it creditor id when inserting or the total number of rows effected 
     *         when updating.
     * @throws CreditorException General Database errors
     */
    public int maintainCreditor(Creditor cred, Object credExt) throws CreditorException {
	int rc;
	if (cred.getCreditorId() == 0) {
	    rc = this.insertCreditor(cred, credExt);
	    cred.setCreditorId(rc);
	}
	else {
	    rc = this.updateCreditor(cred, credExt);
	}
	return rc;
    }

    /**
     * Creates a creditor entity in the database.  Also capable of handling application 
     * specific creditor details via <i>objExt</i>, if applicable.
     *      
     * @param cred The base creditor object.
     * @param credExt The extended creditor object or null if not used.
     * @return The internal creditor id created from the transaction.
     * @throws CreditorException General database error or validation error.
     */
    private int insertCreditor(Creditor cred, Object credExt) throws CreditorException {
	int rc = 0;

	this.validateCreditor(cred);
	this.validateCreditorExt(credExt);

	String acctNo = this.buildAccountNo(cred.getBusinessId());
	cred.setAccountNumber(acctNo);

	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    cred.setDateCreated(ut.getDateCreated());
	    cred.setDateUpdated(ut.getDateCreated());
	    cred.setUserId(ut.getLoginId());
	}
	catch (SystemException e) {
	    throw new CreditorException(e);
	}

	// Call remote service to insert business contact and address information.
	// Should return business id.
//	HttpRemoteServicesConsumer service = new HttpRemoteServicesConsumer();
//	try {
//	    service.processRequest(this.request, null, "maintainBusinessContact");
//	}
//	catch (ActionHandlerException e) {
//	    throw new CreditorException(e);
//	}

	// Perform the actual insert of creditor.
	try {
	    rc = this.insertRow(cred, true);
	    cred.setCreditorId(rc);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}

    }

    /**
     * Updates the creditor's profile in the database.  Also capable of handling application 
     * specific creditor details via <i>objExt</i>, if applicable.
     * 
     * @param cred The base creditor object.
     * @param credExt The extended creditor object or null if not used.
     * @return The total number of rows effected by the transaction.
     * @throws CreditorException General database error or validation error.
     */
    private int updateCreditor(Creditor cred, Object credExt) throws CreditorException {
	int rc = 0;
	this.validateCreditor(cred);
	this.validateCreditorExt(credExt);

	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    cred.setDateCreated(ut.getDateCreated());
	    cred.setDateUpdated(ut.getDateCreated());
	    cred.setUserId(ut.getLoginId());
	}
	catch (SystemException e) {
	    throw new CreditorException(e);
	}

	// Call remote service to insert business contact and address information.
	// Should return business id.
//	HttpRemoteServicesConsumer service = new HttpRemoteServicesConsumer();
//	try {
//	    service.processRequest(this.request, null, "maintainBusinessContact");
//	}
//	catch (ActionHandlerException e) {
//	    throw new CreditorException(e);
//	}

	// Perform the actual update of creditor.
	try {
	    cred.addCriteria(Creditor.PROP_CREDITORID, cred.getCreditorId());
	    rc = this.updateRow(cred);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}
    }

    /**
     * Constructs an account number for the vendor/creditor.   The format of the account 
     * number is:
     * <pre>
     *    &lt;business id&gt; - &lt;current year&gt; &lt;current month&gt; &lt;current da&gt;
     * </pre>
     * 
     * @param businessId The business id of the vendor/creditor
     * @return The account number.
     */
    private String buildAccountNo(int businessId) {
	// Get current date
	Calendar cal = new GregorianCalendar();
	int yr = cal.get(Calendar.YEAR);
	int mm = cal.get(Calendar.MONTH) + 1;
	int dd = cal.get(Calendar.DAY_OF_MONTH);

	// Build account number
	StringBuffer acctNo = new StringBuffer(10);
	acctNo.append(businessId);
	acctNo.append("-");
	acctNo.append(yr);
	acctNo.append((mm < 10 ? "0" + mm : mm));
	acctNo.append((dd < 10 ? "0" + dd : dd));

	return acctNo.toString();
    }

    /**
     * Validates the creditor's data.  Requires that a creditor type is assoicated with 
     * the creditor.  The following requirements must be met in order to declare a creditor
     * valid:
     * <ul>
     *   <li>Creditor type must be supplied</li>
     *   <li>Creditor must be assoicated with a Liability GL Account</li>
     *   <li>Creditor must have a business id</li>
     * </ul>
     * 
     * @param cred The base creditor object to validate.
     * @throws CreditorException A validation failed.
     */
    public void validateCreditor(Creditor cred) throws CreditorException {
	if (cred.getCreditorTypeId() == 0) {
	    throw new CreditorException("Creditor type is required when adding/updating a vendor/creditor profile");
	}

	// validate GL account and account type. 
	try {
	    BasicGLApi glApi = GeneralLedgerFactory.createBasicGLApi(this.connector);
	    GlAccounts acct = glApi.findByAcctNameExact(BasicGLApi.ACCTNAME_ACCTPAY);
	    if (acct == null) {
		this.msg = "General ledger account could not be found for the creditor/vendor account";
		this.logger.log(Level.ERROR, this.msg);
		throw new CreditorException(this.msg);
	    }
	    // Be sure that we chose the correcte GL Account
	    if (acct.getAcctTypeId() != BasicGLApi.ACCTTYPE_LIABILITY) {
		this.msg = "The incorrect account type was selected for the creditor/vendor account";
		this.logger.log(Level.ERROR, this.msg);
		throw new CreditorException(this.msg);
	    }
	    // Assing the account id.
	    cred.setAcctId(acct.getAcctId());
	}
	catch (Exception e) {
	    throw new CreditorException(e);
	}

	// Validate business id for existing creditors.
	if (cred.getCreditorId() > 0) {
	    if (cred.getBusinessId() <= 0) {
		this.msg = "Business id is required when updating a existing vendor/creditor profile";
		this.logger.log(Level.ERROR, this.msg);
		throw new CreditorException(this.msg);
	    }
	}
    }

    /**
     * Stub method for vaildating the Creditor extension object.
     * 
     * @param obj The creditor extension object.
     * @throws CreditorException
     */
    public void validateCreditorExt(Object obj) throws CreditorException {
	return;
    }

    /* (non-Javadoc)
     * @see com.gl.creditor.CreditorApi#deleteCreditor(int)
     */
    public int deleteCreditor(int credId) throws CreditorException {
	// TODO Auto-generated method stub
	return 0;
    }

    public Object findCreditorBusiness(CreditorCriteria criteria) throws CreditorException {
	// TODO Auto-generated method stub
	return null;
    }
}

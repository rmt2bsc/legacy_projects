package com.gl.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import java.math.BigInteger;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.api.db.DatabaseException;

import com.util.RMT2Date;
import com.util.SystemException;

import com.xml.schema.bindings.BusinessContactCriteria;
import com.xml.schema.bindings.ObjectFactory;

import com.bean.Creditor;
import com.bean.CreditorType;
import com.bean.Customer;
import com.bean.GlAccounts;

import com.bean.bindings.JaxbAccountingFactory;

import com.bean.criteria.CreditorCriteria;

import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.gl.BasicGLApi;
import com.gl.GeneralLedgerFactory;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.messaging.MessagingException;
import com.api.security.authentication.RMT2SessionBean;


/**
 * An implementation of {@link com.gl.BasicGLApi BasicGLApi}.  It provides functionality 
 * that creates, updates, deletes, and queries General Ledger accounts, account types, 
 * and account category entities.
 * 
 * @author RTerrell
 *
 */
class CreditorImpl extends RdbmsDaoImpl implements CreditorApi {
    private RdbmsDaoQueryHelper daoHelper;

    private Logger logger;

    private Response response;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public CreditorImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
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
    public CreditorImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
    }

    /**
     * Construct a CreditorImpl object that contains an initialized database connection, 
     * request, and response.
     * 
     * @param dbConn The database connection
     * @param req The user's request
     * @param resp The user's response
     * @throws SystemException General system failures
     * @throws DatabaseException General database errors.
     */
    public CreditorImpl(DatabaseConnectionBean dbConn, Request req, Response resp) throws SystemException, DatabaseException {
	this(dbConn, req);
	this.response = resp;
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("CreditorImpl");
    }

    /**
     * Creates the DAO helper and the logger.
     */
    protected void initApi() throws DatabaseException, SystemException {
	this.logger = Logger.getLogger("CreditorImpl");
    }

    /**
     * Find one or more creditors using custom selection criteria.
     * 
     * @param criteria The selection criteria that can be applied to the SQL statement.
     * @return A List of {@link com.bean.Creditor Creditor} objects or null if no 
     *         creditors are found.
     * @throws CreditorException
     */
    public Object findCreditor(String criteria) throws CreditorException {
	Creditor obj = CreditorFactory.createCreditor();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
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
     * Retrieve creditor and business contact information for a specific creditor.
     * 
     * @param creditorId the creditor's internal unique id.
     * @return {@link com.bean.CreditorExt CreditorExt}
     * @throws CreditorException General database errors.
     */
    public CreditorExt findCreditorBusiness(int creditorId) throws CreditorException {
	Creditor creditor = (Creditor) this.findById(creditorId);
	if (creditor == null) {
	    logger.warn("findCreditorBusiness web service aborted since creditor was not found by id, " + creditorId);
	    logger.warn("Returning newly initialized CreditorExt object");
	    return new CreditorExt();
	}
	
	// Get Business contact related data
	ObjectFactory f = new ObjectFactory();
	BusinessContactCriteria criteria = f.createBusinessContactCriteria();
	criteria.getBusinessId().add(BigInteger.valueOf(creditor.getBusinessId()));
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory(); 
	List<CreditorExt> extList = null;
	try {
	    extList = jaxbUtil.getCreditorContactData(criteria, userSession.getLoginId());
	}
	catch (MessagingException e) {
	    throw new CreditorException(e);
	}

	CreditorExt credExt = extList.get(0);
	// Set customer related data.
	this.mergeCreditorResults(credExt, creditor);
	return credExt;
    }

    /**
     * Retrieves and merges creditor and business contact information which the process is 
     * driven by creditor data.  Business contact data is obtained from the remote application, contacts, 
     * which the <i>criteria</i> is used to filter the result set.  All creditor contact 
     * data is attempted to be retrieve in the event <i>criteria</i> is null.   Otherwise, 
     * the query result set is filterd using creditor selection criteria.
     * 
     * @param criteria 
     *          A String contain SQL selection criteria clause 
     * @return List of {@link com.bean.CreditorExt CreditorExt} objecs
     * @throws CreditorException  
     *           General database and remote service errors.
     */
    public List<CreditorExt> findCreditorBusiness(CreditorCriteria criteria) throws CreditorException {
	boolean useCreditorParms = false;
	boolean useContactParms = false;

	if (criteria != null) {
	    useCreditorParms = (!criteria.getQry_AccountNo().equals("") || !criteria.getQry_CreditorId().equals("") || !criteria.getQry_CreditorTypeId().equals(""));
	    useContactParms = (!criteria.getQry_Name().equals("") || !criteria.getQry_TaxId().equals("") || !criteria.getQry_PhoneMain().equals(""));
	    
	    if (useCreditorParms && useContactParms) {
		this.msg = "Creditor criteria and business contact criteria must be submitted mutually exclusively";
		this.logger.log(Level.ERROR, this.msg);
		throw new CreditorException(this.msg);
	    }
	    
	    // Fetch all customers
	    if (!useCreditorParms && !useContactParms) {
		criteria = null;
	    }
	}

	List<CreditorExt> list = null;
	if (useCreditorParms) {
	    list = this.findCreditorBusinessByCreditor(criteria);
	}
	else if (useContactParms) {
	    list = this.findCreditorBusinessByContact(criteria);
	}
	else if (!useCreditorParms && !useContactParms) {
	    list = this.findCreditorBusinessByCreditor(null);
	}

	// return null if no creditors are found.
	if (list == null) {
	    return null;
	}
	CreditorExtComparator comp = new CreditorExtComparator();
	Collections.sort(list, comp);
	return list;
    }

    /**
     * Retrieves creditor extension data by which the business contact information drives 
     * the process of combining the business contact data with the creditor data.  Business
     * contact data is obtained from the remote application, contacts, which the <i>criteria</i>
     * is used to filter the result set.
     * <p>
     * The field names in <i>criteria</i> are translated to a their respective database 
     * column names before the SQL statement is executed.
     * 
     * @param criteria 
     *          Key/value pairs representing the parameters for the remote service call.
     * @return  List of {@link com.bean.CreditorExt CreditorExt} objecs
     * @throws CreditorException 
     *           General database and remote service errors.
     */
    private List<CreditorExt> findCreditorBusinessByCreditor(CreditorCriteria criteria) throws CreditorException {
	List<Creditor> credList = null;
	StringBuffer sql = new StringBuffer();

	// Use account number and creditor type id to query local database for customers.
	if (criteria != null) {
	    String acctNo = criteria.getQry_AccountNo();
	    if (acctNo != null && !acctNo.equals("")) {
		sql.append("account_number like \'");
		sql.append(acctNo.trim());
		sql.append("%\'");
	    }

	    String creditorTypeId = criteria.getQry_CreditorTypeId();
	    if (creditorTypeId != null && !creditorTypeId.equals("")) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append("creditor_type_id = ");
		sql.append(creditorTypeId);
	    }
        
            String creditorId = criteria.getQry_CreditorId();
            if (creditorId != null && !creditorId.equals("")) {
                if (sql.length() > 0) {
                    sql.append(" and ");
                }
                sql.append("creditor_id = ");
                sql.append(creditorId);
            }
	}

	// Get base creditor list
	Creditor obj = CreditorFactory.createCreditor();
	obj.addCustomCriteria(sql.toString());
	obj.addOrderBy(Customer.PROP_BUSINESSID, Customer.ORDERBY_ASCENDING);
	try {
	    credList = this.daoHelper.retrieveList(obj);
	    if (credList == null || credList.size() <= 0) {
		return null;
	    }
	}
	catch (DatabaseException e) {
	    throw new CreditorException(e);
	}

	// Create JAXB criteria object
	ObjectFactory f = new ObjectFactory();
	BusinessContactCriteria wsCriteria = f.createBusinessContactCriteria();
	
	// Build list business id's to submit to web service
	for (Creditor cred : credList) {
	    wsCriteria.getBusinessId().add(BigInteger.valueOf(cred.getBusinessId()));
	}

	// Call web service to fetch business contact based on list of business id's
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory(); 
	List<CreditorExt> extList;
	try {
	    extList = jaxbUtil.getCreditorContactData(wsCriteria, userSession.getLoginId());
	}
	catch (MessagingException e) {
	    throw new CreditorException(e);
	}
	
	// use results to create a Map which is keyed by business Id.
	Map <Integer, CreditorExt> map = jaxbUtil.createCreditorContactMap(extList);
	
	// Only merge creditor base with creditor extension where business id's match.
	List<CreditorExt> newLlist = new ArrayList<CreditorExt>();
	for (Creditor cred : credList) {
	    CreditorExt ext = map.get(cred.getBusinessId());
	    if (ext == null) {
		continue;
	    }
	    mergeCreditorResults(ext, cred);
	    newLlist.add(ext);
	}
	return newLlist;
    }

    /**
     * Retrieves creditor extension data by which the business contact information drives 
     * the process of combining the business contact data with the creditor data.  Business
     * contact data is obtained from the remote application, contacts, which the <i>criteria</i>
     * is used to filter the result set.
     * 
     * @param criteria 
     *          Key/value pairs representing the parameters for the remote service call.
     * @return  List of {@link com.bean.CreditorExt CreditorExt} objecs
     * @throws CreditorException 
     *           General database and remote service errors.
     */
    private List<CreditorExt> findCreditorBusinessByContact(CreditorCriteria criteria) throws CreditorException {
	// Create JAXB criteria object
	ObjectFactory f = new ObjectFactory();
	BusinessContactCriteria wsCriteria = f.createBusinessContactCriteria();
	wsCriteria.setBusinessName(criteria.getQry_Name());
	wsCriteria.setTaxId(criteria.getQry_TaxId());
	wsCriteria.setMainPhone(criteria.getQry_PhoneMain());

	// Call remote service to fetch business contact based on list of business id's
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory(); 
	List<CreditorExt> extList = null;
	try {
	    extList = jaxbUtil.getCreditorContactData(wsCriteria, userSession.getLoginId());
	}
	catch (MessagingException e) {
	    throw new CreditorException(e);
	}
	
	// Use results to create a Map which is keyed by business Id.
	Map <Integer, CreditorExt> map = jaxbUtil.createCreditorContactMap(extList);
	
	// Build comma-separated list of business id's from results.
	StringBuffer buf = new StringBuffer();
	for (CreditorExt item : extList) {
	    if (buf.length() > 0) {
		buf.append(", ");
	    }
	    buf.append(item.getBusinessId());
	}
	
	// Get list of customer that match the business id's retrieve from the web service.
	String sql = " business_id in(" + buf.toString() + ") ";
	List <Creditor> credList = (List <Creditor>) this.findCreditor(sql);
	if (credList == null) {
	    return null;
	}
	
	List<CreditorExt> newList = new ArrayList<CreditorExt>();
	for (Creditor cred : credList) {
	    CreditorExt ext = map.get(cred.getBusinessId());
	    if (ext == null) {
		continue;
	    }
	    this.mergeCreditorResults(ext, cred);
	    newList.add(ext);
	}
	return newList;
    }

    
    /**
     * Copies data from the base customer object to the customer extension object.
     * 
     * @param credExt 
     *          The {@link com.bean.CustomerExt CustomerExt} object which serves as 
     *          the destination of the data transfer.
     * @param creditor 
     *          The {@link com.bean.Customer Customer}object which serves as the 
     *          source of the data transfer.
     * @throws CreditorException
     */
    private void mergeCreditorResults(CreditorExt credExt, Creditor creditor) throws CreditorException {
	credExt.setCreditorId(creditor.getCreditorId());
	credExt.setGlAccountId(creditor.getAcctId());
	credExt.setAccountNo(creditor.getAccountNumber());
	credExt.setBusinessId(creditor.getBusinessId());
	credExt.setCreditLimit(creditor.getCreditLimit());
	credExt.setApr(creditor.getApr());
	credExt.setCreditorTypeId(creditor.getCreditorTypeId());
	
	CreditorType ct = this.findCreditorTypeById(creditor.getCreditorTypeId());
	if (ct != null) {
	    credExt.setCreditorTypeDescription(ct.getDescription());    
	}
	else {
	    credExt.setCreditorTypeDescription("");
	}
	
	credExt.setActive(creditor.getActive());
	credExt.setDateCreated(creditor.getDateCreated());
	credExt.setDateUpdated(creditor.getDateUpdated());
	credExt.setUserId(creditor.getUserId());
	
	Double bal = this.findCreditorBalance(creditor.getCreditorId());
	credExt.setBalance(bal);
    }
    

    /**
     * Find creditor by account number.
     * 
     * @param acctNo The account number
     * @return A List of {@link com.bean.Creditor Creditor} objects or null if no 
     *         creditors are found.
     * @throws CreditorException General database errors.
     */
    public List findByAcctNo(String acctNo) throws CreditorException {
	Creditor obj = CreditorFactory.createCreditor();
	obj.addCriteria(Creditor.PROP_ACCOUNTNUMBER, acctNo);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findByCreditorType(int creditorTypeId) throws CreditorException {
	Creditor obj = CreditorFactory.createCreditor();
	obj.addCriteria(Creditor.PROP_CREDITORTYPEID, creditorTypeId);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public Creditor findById(int id) throws CreditorException {
	Creditor obj = CreditorFactory.createCreditor();
	obj.addCriteria(Creditor.PROP_CREDITORID, id);
	try {
	    return (Creditor) this.daoHelper.retrieveObject(obj);
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
    public List findByBusinessId(int businessId) throws CreditorException {
	Creditor obj = CreditorFactory.createCreditor();
	obj.addCriteria(Creditor.PROP_BUSINESSID, businessId);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public List findCreditorType(String criteria) throws CreditorException {
	CreditorType obj = CreditorFactory.createCreditorType();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
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
    public CreditorType findCreditorTypeById(int id) throws CreditorException {
	CreditorType obj = CreditorFactory.createCreditorType();
	obj.addCriteria(CreditorType.PROP_CREDITORTYPEID, id);
	try {
	    return (CreditorType) this.daoHelper.retrieveObject(obj);
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
     * @param objExt The extension to the base creditor object
     * @return Either it creditor id when inserting or the total number of rows effected 
     *         when updating.
     * @throws CreditorException General Database errors
     */
    public int maintainCreditor(Creditor cred, Object objExt) throws CreditorException {
	int rc;
	if (cred.getCreditorId() == 0) {
	    rc = this.insertCreditor(cred, objExt);
	    cred.setCreditorId(rc);
	}
	else {
	    rc = this.updateCreditor(cred, objExt);
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
	    cred.setIpCreated(ut.getIpAddr());
	    cred.setIpUpdated(ut.getIpAddr());
	}
	catch (SystemException e) {
	    throw new CreditorException(e);
	}

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
	    cred.setIpUpdated(ut.getIpAddr());
	}
	catch (SystemException e) {
	    throw new CreditorException(e);
	}

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
     *    &lt;R&gt;&lt;business id&gt; - &lt;current year&gt; &lt;current month&gt; &lt;current da&gt;
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
	acctNo.append("R");
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
	    this.msg = "Creditor type is required when adding/updating a vendor/creditor profile";
	    logger.error(this.msg);
	    throw new CreditorException(this.msg);
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

    /**
     * Method is not supported..
     * 
     * @param criteria 
     * @return 
     * @throws UnsupportedOperationException 
     *           when invoked
     * @deprecated Use {@link com.gl.creditor.CreditorImpl#findCreditorBusiness(CreditorCriteria) findCreditorBusiness(CreditorCriteria)}
     */
    public Object findCreditorBusiness(String criteria) throws CreditorException {
	this.msg = "findCreditorBusiness(String) is no longer supported";
	logger.error(this.msg);
	throw new UnsupportedOperationException(this.msg);
    }
}

package com.gl;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;

import java.util.List;

import java.sql.ResultSet;
import java.sql.Types;

import com.api.db.DatabaseException;
import com.api.db.DynamicSqlApi;
import com.api.db.DynamicSqlFactory;

import com.gl.GeneralLedgerFactory;
import com.gl.GLException;

import com.util.RMT2Date;
import com.util.SystemException;

import com.bean.GlAccounts;
import com.bean.GlAccountCategory;
import com.bean.GlAccountTypes;
import com.bean.VwAccount;
import com.bean.VwCategory;

import com.bean.custom.UserTimestamp;
import com.bean.db.DatabaseConnectionBean;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

/**
 * An implementation of {@link com.gl.BasicGLApi BasicGLApi}.  It provides functionality 
 * that creates, updates, deletes, and queries General Ledger accounts, account types, 
 * and account category entities.
 * 
 * @author RTerrell
 *
 */
public class GeneralLedgerCodeImpl extends RdbmsDaoImpl implements BasicGLApi {
    private RdbmsDaoQueryHelper daoHelper;

    private String criteria;

    private Logger logger;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public GeneralLedgerCodeImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.className = "GeneralLedgerCodeImpl";
	this.baseView = "GlAccountsView";
	this.baseBeanClass = "com.bean.GlAccounts";
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("GeneralLedgerCodeImpl");
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
    public GeneralLedgerCodeImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("GeneralLedgerCodeImpl");
    }

    /**
     * Finds one or more GlAccounts objects using custom criteria supplied by the client.
     * 
     * @param criteria Custom selection criteria.
     * @return A list of {@link com.bean.GlAccounts GlAccounts}
     * @throws GLException
     */
    public List findGlAcct(String criteria) throws GLException {
	GlAccounts obj = GeneralLedgerFactory.createGlAccount();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates a GlAccounts object by primary key or it GL Account Id and returns 
     * GlAccounts object to the caller.
     * 
     * @param id The unique of id of the GL Account.
     * @return {@link com.bean.GlAccounts GlAccounts}
     * @throws GLException
     */
    public GlAccounts findById(int id) throws GLException {
	GlAccounts obj = GeneralLedgerFactory.createGlAccount();
	obj.addCriteria(GlAccounts.PROP_ACCTID, id);
	try {
	    return (GlAccounts) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates one or more GlAccounts objects based on the acct_no
     * 
     * @param acctNo The account number.
     * @return A List of {@link com.bean.GlAccounts GlAccounts} objects.
     * @throws GLException
     */
    public List findByAcctNo(String acctNo) throws GLException {
	GlAccounts obj = GeneralLedgerFactory.createGlAccount();
	obj.addLikeClause(GlAccounts.PROP_ACCTNO, acctNo);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates one or more GlAccounts objects based on the name property.
     * 
     * @param acctName The name of the account.
     * @return A List of {@link com.bean.GlAccounts GlAccounts} objects.
     * @throws GLException
     */
    public List findByAcctName(String acctName) throws GLException {
	GlAccounts obj = GeneralLedgerFactory.createGlAccount();
	obj.addLikeClause(GlAccounts.PROP_NAME, acctName);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates an exact account and packages the account data into a GlAccunts object based 
     * on the value of "name".
     * 
     * @param acctName The name of the account.
     * @return A List of {@link com.bean.GlAccounts GlAccounts} objects.
     * @throws GLException
     */
    public GlAccounts findByAcctNameExact(String acctName) throws GLException {
	GlAccounts obj = GeneralLedgerFactory.createGlAccount();
	obj.addCriteria(GlAccounts.PROP_NAME, acctName);
	try {
	    return (GlAccounts) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates all GL Accounts related to Account type and Account category.
     * 
     * @param acctTypeid The id if the account type.
     * @param acctCatgId The id of the account category.
     * @return A List o f{@link com.bean.GlAccountTypeCatg GlAccountTypeCatg} objects.
     * @throws GLException
     */
    public List findCombinedGlAcctTypeCatg(int acctTypeid, int acctCatgId) throws GLException {
	String prevBaseView = "GlAccountsView";
	String prevBaseBean = "com.bean.GlAccounts";

	try {
	    this.baseView = "GlAccountTypeCatgView";
	    this.baseBeanClass = "com.bean.GlAccountTypeCatg";
	    this.criteria = "gl_accounts.acct_type_id = " + acctTypeid + " and gl_accounts.acct_cat_id = " + acctCatgId;
	    List list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return list;
	}
	catch (SystemException e) {
	    throw new GLException(e);
	}
	finally {
	    this.baseView = prevBaseView;
	    this.baseBeanClass = prevBaseBean;
	}
    }

    /**
     * Locates an Gl Account and packages the account data into a GlAccunts object based 
     * on the value of <i>acct_type_id</i>
     * 
     * @param acctTypeId The account type id.
     * @return A List of {@link com.bean.GlAccounts GlAccounts} objects.
     * @throws GLException
     */
    public List findByAcctType(int acctTypeId) throws GLException {
	GlAccounts obj = GeneralLedgerFactory.createGlAccount();
	obj.addCriteria(GlAccounts.PROP_ACCTTYPEID, acctTypeId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates all Gl Account and packages the account data into a GlAccunts object based 
     * on the value of <i>acct_cat_id</i>
     * 
     * @param acctCatgId The id of the account category.
     * @return A List of {@link com.bean.GlAccounts GlAccounts} objects.
     * @throws GLException
     */
    public List findByAcctCatg(int acctCatgId) throws GLException {
	GlAccounts obj = GeneralLedgerFactory.createGlAccount();
	obj.addCriteria(GlAccounts.PROP_ACCTCATGID, acctCatgId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates all Gl Account and packages the account data into a GlAccunts object based 
     * on the description beinning with <i>acctCatgName</i>
     * 
     * @param acctCatgName The name of the account category.
     * @return A List of {@link com.bean.GlAccountsCatgName GlAccountsCatgName} objects.
     * @throws GLException
     */
    public List findByAcctCatgName(String acctCatgName) throws GLException {

	VwAccount obj = GeneralLedgerFactory.createVwAccount();
	obj.addLikeClause(VwAccount.PROP_ACCTCATGDESCR, acctCatgName);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Find an account by its code.
     * 
     * @param code The account code.
     * @return {@link com.bean.GlAccounts GlAccounts}
     * @throws GLException
     */
    public Object findByCode(String code) throws GLException {
	GlAccounts acct = GeneralLedgerFactory.createGlAccount();
	acct.addCriteria(GlAccounts.PROP_CODE, code);
	try {
	    return this.daoHelper.retrieveObject(acct);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates a Gl Account Type based on the value of  Account Type Id.
     * 
     * @param acctTypeId The id of the account type.
     * @return {@link com.bean.GlAccountTypes GlAccountTypes} objects.
     * @throws GLException
     */
    public GlAccountTypes findAcctTypeById(int acctTypeId) throws GLException {
	GlAccountTypes obj = GeneralLedgerFactory.createAcctType();
	obj.addCriteria(GlAccountTypes.PROP_ACCTTYPEID, acctTypeId);
	try {
	    return (GlAccountTypes) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates one or more GL Account Types based on criteria.
     * 
     * @param criteria Custom selection criteria.
     * @return A List of {@link com.bean.GlAccountTypes GlAccountTypes} objects.
     * @throws GLException
     */
    public List findAcctType(String criteria) throws GLException {
	GlAccountTypes obj = GeneralLedgerFactory.createAcctType();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Obtains the primary key or Id of an account based on the input 
     * value of account name.
     * 
     * @param name Account name.
     * @return The internal id or primary key of the account or -1 if not found.
     * @throws GLException
     * @deprecated No longer needed
     */
    public int getAcctIdByName(String name) throws GLException {
	GlAccounts obj = this.findByAcctNameExact(name);
	if (obj == null) {
	    return -1;
	}
	return obj.getAcctId();
    }

    /**
     * Obtains the primary key or Id of an account type based on the input 
     * value of accout type description.
     * 
     * @param description the description of the account type.
     * @return The internal id of the account type or -1 when not found.
     * @throws GLException
     * @deprecated No longer needed
     */
    public int getAcctTypeId(String description) throws GLException {
	GlAccountTypes obj = GeneralLedgerFactory.createAcctType();
	obj.addCriteria(GlAccountTypes.PROP_DESCRIPTION, description);
	try {
	    obj = (GlAccountTypes) this.daoHelper.retrieveObject(obj);
	    if (obj == null) {
		// Not found
		return -1;
	    }
	    return obj.getAcctTypeId();
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates a Gl Account Category and packages the account data into a GlAccountCategory 
     * object based on the value of  <i>id</i>.
     * 
     * @param acctCatgId The id of the account category.
     * @return A List of {@link com.bean.GlAccountCategory GlAccountCategory} objects.
     * @throws GLException
     */
    public GlAccountCategory findAcctCatgById(int acctCatgId) throws GLException {
	GlAccountCategory obj = GeneralLedgerFactory.createCatg();
	obj.addCriteria(GlAccountCategory.PROP_ACCTCATGID, acctCatgId);
	try {
	    return (GlAccountCategory) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates all Gl Account Categories that are associated with a particular account type id.
     * 
     * @param acctTypeId The id of the account type.
     * @return A List of {@link com.bean.GlAccountCategory GlAccountCategory} objects.
     * @throws GLException
     */
    public List findAcctCatgByAcctType(int acctTypeId) throws GLException {
	GlAccountCategory obj = GeneralLedgerFactory.createCatg();
	obj.addCriteria(GlAccountCategory.PROP_ACCTTYPEID, acctTypeId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates all Gl Account Categories and packages the account data into a 
     * GlAccountCategory object based on the value of  the category's description.
     * 
     * @param acctCatgName The name of the account category.
     * @return A List of {@link com.bean.GlAccountCategory GlAccountCategory} objects.
     * @throws GLException
     */
    public List findAcctCatgByName(String acctCatgName) throws GLException {
	GlAccountCategory obj = GeneralLedgerFactory.createCatg();
	obj.addLikeClause(GlAccountCategory.PROP_DESCRIPTION, acctCatgName);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Locates a Gl Account Category and packages the account data into a GlAccountCategory 
     * object where the the category's description matches exactly <i>acctCatgName</i>.
     * 
     * @param acctCatgName The name of the account category.
     * @return {@link com.bean.GlAccountCategory GlAccountCategory} object.
     * @throws GLException
     */
    public GlAccountCategory findAcctCatgByNameExact(String acctCatgName) throws GLException {
	GlAccountCategory obj = GeneralLedgerFactory.createCatg();
	obj.addCriteria(GlAccountCategory.PROP_DESCRIPTION, acctCatgName);
	try {
	    return (GlAccountCategory) this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * This method is responsible for creating and updating General Ledger Accounts.   If 
     * the id of the GlAccounts instance is null then an account must be created.  If GlAccount 
     * id has a  value, then apply updates to that account.
     * 
     * @param obj {@link com.bean.GlAccounts GlAccounts} object.
     * @return The id of the newly inserted row for an insert transaction or a count of all rows 
     *         effected by an update transaction.
     * @throws GLException
     */
    public int maintainAccount(GlAccounts obj) throws GLException {
	int rc = 0;
	try {
	    if (obj.getAcctId() == 0) {
		this.insertAccount(obj);
		rc = obj.getAcctId();
	    }
	    else {
		rc = this.updateAccount(obj);
	    }
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * This method is responsible for adding GL Accounts to the systems.   Before the actual 
     * insert operation is performed, the account number and account sequence number values 
     * are automatically generated and assigned to the account data object.
     * 
     * @param obj The {@link com.bean.GlAccounts GlAccounts} object.
     * @return the new account id as an int value
     * @throws DatabaseException
     * @throws GLException
     */
    private int insertAccount(GlAccounts obj) throws DatabaseException, GLException {
	// Check that account type and account category exist.
	this.validateAccount(obj);

	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    obj.setDateCreated(ut.getDateCreated());
	    obj.setDateUpdated(ut.getDateCreated());
	    obj.setUserId(ut.getLoginId());
	}
	catch (SystemException e) {
	    throw new DatabaseException(e);
	}

	int nextSeq = this.getNextAccountSeq(obj);
	obj.setAcctSeq(nextSeq);
	String acctNo = this.buildAccountNo(obj);
	obj.setAcctNo(acctNo);
	int newId = this.insertRow(obj, true);
	obj.setAcctId(newId);
	return newId;
    }

    /**
     * This method is responsible for updating a GL Account.  Only the code, name, and 
     * descritpion columns are updated.
     * 
     * @param obj The {@link com.bean.GlAccounts GlAccounts} object.
     * @return The total number of records effected by transaction.
     * @throws DatabaseException
     * @throws GLException
     */
    private int updateAccount(GlAccounts obj) throws DatabaseException, GLException {
	GlAccounts delta = this.validateAccount(obj);
	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    delta.setDateUpdated(ut.getDateCreated());
	    delta.setUserId(ut.getLoginId());
	}
	catch (SystemException e) {
	    throw new DatabaseException(e);
	}
	delta.setCode(obj.getCode());
	delta.setName(obj.getName());
	delta.setDescription(obj.getDescription());
	delta.setAcctBaltypeId(obj.getAcctBaltypeId());
	delta.addCriteria(GlAccounts.PROP_ACCTID, obj.getAcctId());
	return this.updateRow(delta);
    }

    /**
     * Deletes a GL Account fro the database using its primary key identifier.
     * 
     * @param acctId The id of the account to delete.
     * @return The total number of rows effected by the transaction.
     * @throws GLException
     */
    public int deleteAccount(int acctId) throws GLException {
	GlAccounts acct = GeneralLedgerFactory.createGlAccount();
	acct.addCriteria(GlAccounts.PROP_ACCTID, acctId);
	int rows = 0;
	try {
	    rows = this.deleteRow(acct);
	    return rows;
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Validates GlAccounts object.   Commonly, it requires values for account type, 
     * account category, account code, description, name, and balance type.  An 
     * existence test is conducted for the account when <i>obj</i> is targeted for
     * update.  A valid account requires that the code and name do not already exist.
     *
     * @param acct The {@link com.bean.GlAccounts GlAccounts} object that is to be managed.
     * @throws GLException When any validation errors occurs.
     */
    private GlAccounts validateAccount(GlAccounts acct) throws GLException {
	GlAccounts old = null;
	if (acct.getAcctId() > 0) {
	    old = this.findById(acct.getAcctId());
	    if (old == null) {
		this.msg = "Account does not exist";
		this.logger.log(Level.ERROR, this.msg);
		throw new GLException(this.msg, 417);
	    }
	}

	if (acct.getAcctTypeId() == 0) {
	    this.msg = "Account must be assoicated with an account type";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 410);
	}
	if (acct.getAcctCatgId() == 0) {
	    this.msg = "Account must be assoicated with an account category";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 411);
	}
	if (acct.getName() == null || acct.getName().trim().length() <= 0) {
	    this.msg = "Account must be assigned a name";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 412);
	}
	if (acct.getCode() == null || acct.getCode().trim().length() <= 0) {
	    this.msg = "Account must have a code";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 412);
	}
	if (acct.getDescription() == null || acct.getDescription().trim().length() <= 0) {
	    this.msg = "Account must have a description";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 413);
	}
	if (acct.getAcctBaltypeId() == 0) {
	    this.msg = "Account must have a balance type";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 417);
	}

	//  determine if GL Account Name is not Duplicated
	if (acct.getAcctId() == 0 || (old != null && !acct.getName().equalsIgnoreCase(old.getName())))
	    if (this.findByAcctNameExact(acct.getName()) != null) {
		this.msg = "Duplicate GL account name";
		this.logger.log(Level.ERROR, this.msg);
		throw new GLException(this.msg, 415);
	    }
	// Determine if GL Account code is not duplicated for new accounts 
	if (acct.getAcctId() == 0 || (old != null && !acct.getCode().equalsIgnoreCase(old.getCode()))) {
	    if (this.findByCode(acct.getCode()) != null) {
		this.msg = "Duplicate GL account code";
		this.logger.log(Level.ERROR, this.msg);
		throw new GLException(this.msg, 416);
	    }
	}

	// Return null if we are adding an account.
	if (acct.getAcctId() == 0) {
	    return null;
	}

	return old;
    }

    /**
     * Retrieves the next account sequence number for the account.  Sequence numbers 
     * of generated based on the group of GL accounts by account id and account 
     * category id.
     * 
     * @param acct The {@link com.bean.GlAccounts GlAccounts} to generate a sequence number.
     * @return The next sequence number.
     * @throws GLException
     */
    private int getNextAccountSeq(GlAccounts acct) throws GLException {
	String sql = "select max(acct_seq) next_seq from gl_accounts where acct_type_id = " + acct.getAcctTypeId() + " and acct_catg_id = " + acct.getAcctCatgId();
	ResultSet rs = null;
	int nextSeq = 0;
	try {
	    rs = this.executeQuery(sql);
	    if (rs.next()) {
		nextSeq = rs.getInt("next_seq");
		nextSeq = (nextSeq == 0 ? 1 : ++nextSeq);
	    }
	    return nextSeq;
	}
	catch (Exception e) {
	    this.logger.log(Level.ERROR, e.getMessage());
	    throw new GLException(e);
	}
    }

    /**
     * Locates a Gl Account Category and packages the account data into a GlAccountCategory 
     * object where the the category's description matches exactly <i>obj</i>.  Once the 
     * account number is computed, the account number is assigned to <i>obj</i>. 
     * <p>
     * The format of the account number goes as follows:
     * <pre>
     *   &lt;Account Type Id&gt;-&lt;Account Catgegory Id&gt;-&lt;Account Sequence Number&gt;
     * </pre
     * 
     * @param acct A {@link com.bean.GlAccounts GlAccounts} instance.
     * @return The account number in String format.
     * @throws GLException 
     *           When the account type or account category, account sequence values are 
     *           invalid.
     */
    protected String buildAccountNo(GlAccounts acct) throws GLException {

	String result = "";
	String temp = "";
	int seq = acct.getAcctSeq();
	int acctType = acct.getAcctTypeId();
	int acctCat = acct.getAcctCatgId();

	//  Validate Data Values
	if (acctType <= 0) {
	    this.msg = "Account Number cannot be created without a valid account type code";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 406);
	}
	if (acctCat <= 0) {
	    this.msg = "Account Number cannot be created without a valid account category type code";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 407);
	}
	if (seq <= 0) {
	    this.msg = "Account Number cannot be created without a valid account sequence number";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 408);
	}

	//  Compute GL Account Number using the Account Type Id, Account Catgegory Id, and Account Sequence Number
	result = acctType + "-" + acctCat + "-";
	if (seq >= 1 && seq <= 9) {
	    temp = "00" + seq;
	}
	if (seq > 9 && seq <= 99) {
	    temp = "0" + seq;
	}
	if (seq > 99 && seq <= 999) {
	    temp = String.valueOf(seq);
	}
	result += temp;
	acct.setAcctNo(result);

	return result;
    }

    /**
     * Determines if GL Account is tied to other areas of the database, or referenced by other tables.
     * 
     * @param acctId The id of the account to evaluate.
     * @return true when account is in use, and false otherwise.
     * @throws GLException
     */
    public boolean isAccountInUse(int acctId) throws GLException {
	boolean acctInUse = false;
	int cnt = 0;
	DynamicSqlApi dynaApi = null;

	try {
	    //  Add GL Account to the database.
	    dynaApi = DynamicSqlFactory.create(this.connector);
	    dynaApi.clearParms();
	    dynaApi.addParm("id", Types.INTEGER, acctId, false);

	    // Call stored function to update GL Account  to the database using  gl account id.
	    ResultSet rs = dynaApi.execute("select ufn_get_acct_usage_count(?) count");

	    //   ResultSet is null if stored function did not execure as expected.
	    if (rs == null) {
		return acctInUse;
	    }

	    //  Get count provided a row exist in "rs".
	    if (rs.next()) {
		cnt = rs.getInt("count");
		acctInUse = (cnt > 0);
	    }
	    return acctInUse;
	}
	catch (Exception e) {
	    throw new GLException(e);
	}
    }

    /**
     * Obtains total count of GL Account  to trasaction associations based on <i>acctId</i> and 
     * the value of <i>funcName</i>.   <i>funcName</i> should be a string reference to a stored 
     * function in the database that retrieves the count of Account - to - xxx associations.
     * 
     * @param acctId The id of the account.
     * @param funcName The name of the stored function to call.
     * @return The number of access profiles found.
     * @throws GLException
     */
    protected int getAcctAssociationCount(int acctId, String funcName) throws GLException {
	StringBuffer sql = new StringBuffer(100);
	;
	int cnt = 0;
	DynamicSqlApi dynaApi = null;

	if (funcName == null || funcName.length() <= 0) {
	    this.msg = "Function Name must be provided to obtain a count of Account associations";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 411);
	}

	try {
	    //  Add GL Account to the database.
	    dynaApi = DynamicSqlFactory.create(this.connector);
	    dynaApi.clearParms();
	    dynaApi.addParm("id", Types.INTEGER, acctId, false);

	    // Call stored function to obtain total count of GL Account  to trasaction associations.
	    sql.append("select ");
	    sql.append(funcName);
	    sql.append(" (?) count");
	    ResultSet rs = dynaApi.execute(sql.toString());

	    //   ResultSet is null if stored function did not execure as expected.
	    if (rs == null) {
		return cnt;
	    }

	    //  Get count provided a row exist in "rs".
	    if (rs.next()) {
		cnt = rs.getInt("count");
	    }
	    return cnt;
	}
	catch (Exception e) {
	    throw new GLException(e);
	}
    }

    /**
     * Obtains total count of GL Account  to transaction associations.
     * 
     * @param acctId The id of the account.
     * @return int
     * @throws GLException
     */
    protected int getAcctToXactCount(int acctId) throws GLException {
	return this.getAcctAssociationCount(acctId, "ufn_get_acct_to_xact_count");
    }

    /**
     * Obtains total count of GL Account  to Item Master associations.
     * 
     * @param acctId The id of the account.
     * @return int
     * @throws GLException
     */
    protected int getAcctToItemMastCount(int acctId) throws GLException {
	return this.getAcctAssociationCount(acctId, "ufn_get_acct_to_xact_count");
    }

    /**
     * Obtains total count of GL Account  to Subsidiary associations.
     * 
     * @param acctId The id of the account.
     * @return int
     * @throws GLException
     */
    protected int getAcctToSubsidiaryCount(int acctId) throws GLException {
	return this.getAcctAssociationCount(acctId, "ufn_get_acct_to_xact_count");
    }

    /**
     * This method is responsible for creating and updating General Ledger Account Categories.  
     * If GlAccountCategory.id is null then an account category must be created.  If 
     * GlAccountCategory.id has a value, then apply updates to that account category.
     * 
     * @param acctCatg The GL account category object.
     * @return The id of the newly inserted row for an insert transaction or a count of all rows 
     *         effected by an update transaction.
     * @throws GLException
     */
    public int maintainAcctCatg(GlAccountCategory acctCatg) throws GLException {
	int rc = 0;
	try {
	    if (acctCatg.getAcctCatgId() == 0) {
		rc = this.insertAcctCatg(acctCatg);
	    }
	    else {
		rc = this.updateAcctCatg(acctCatg);
	    }
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Adds an account category to the database.
     * 
     * @param acctCatg The {@link com.bean.GlAccountCategory GlAccountCategory}
     * @return The id of the new account category added.
     * @throws DatabaseException General database errors.
     * @throws GLException
     */
    private int insertAcctCatg(GlAccountCategory acctCatg) throws DatabaseException, GLException {
	this.validateAcctCatg(acctCatg);
	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    acctCatg.setDateCreated(ut.getDateCreated());
	    acctCatg.setDateUpdated(ut.getDateCreated());
	    acctCatg.setUserId(ut.getLoginId());
	}
	catch (SystemException e) {
	    throw new DatabaseException(e);
	}
	int newId = this.insertRow(acctCatg, true);
	acctCatg.setAcctCatgId(newId);
	return newId;
    }

    /**
     * Updates the description of a GL Account Category.
     * 
     * @param acctCatg The GL account category object.
     * @return The total number of rows effected by the transaction.
     * @throws DatabaseException General database errors.
     * @throws GLException
     */
    private int updateAcctCatg(GlAccountCategory acctCatg) throws DatabaseException, GLException {
	GlAccountCategory delta = this.validateAcctCatg(acctCatg);
	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    delta.setDateUpdated(ut.getDateCreated());
	    delta.setUserId(ut.getLoginId());
	}
	catch (SystemException e) {
	    throw new DatabaseException(e);
	}
	delta.setDescription(acctCatg.getDescription());
	delta.addCriteria(GlAccountCategory.PROP_ACCTCATGID, delta.getAcctCatgId());
	return this.updateRow(delta);
    }

    /**
     * Deletes an account category from the database.
     * 
     * @param acctCatgId The id of the account category.
     * @return The total number of rows effected by the transaction.
     * @throws GLException
     */
    public int deleteAcctCatg(int acctCatgId) throws GLException {
	GlAccountCategory catg = GeneralLedgerFactory.createCatg();
	catg.addCriteria(GlAccountCategory.PROP_ACCTCATGID, acctCatgId);
	int rc;
	try {
	    rc = this.deleteRow(catg);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Validates an account type by using <i>glAcctTypeId</i> to query the database for 
     * its existence.   
     * 
     * @param glAcctTypeId The GL aacount type id.
     * @return true when account type exists and false, otherwise.
     * @throws GLException General database errors.
     */
    protected boolean validateAcctType(int glAcctTypeId) throws GLException {
	GlAccountTypes glAcctType;
	glAcctType = this.findAcctTypeById(glAcctTypeId);
	return (glAcctType != null);
    }

    /**
     * Validates an account category object.  It is required for an account category to 
     * have an account type id and a description.  When <i>obj</i> is targeted for a 
     * databae update, a check is performed to determine if <i>obj</i> exist in the 
     * database.
     * 
     * @param acctCatg {@link com.bean.GlAccounts GlAccounts}
     * @return The {@link com.bean.GlAccountCategory GlAccountCategory} object retrieved from 
     *         the database before any changes are applied.
     * @throws GLException if any of the validations fail.
     */
    private GlAccountCategory validateAcctCatg(GlAccountCategory acctCatg) throws GLException {
	GlAccountCategory old = null;
	if (acctCatg.getAcctCatgId() > 0) {
	    old = this.findAcctCatgById(acctCatg.getAcctCatgId());
	    if (old == null) {
		this.msg = "Account Category does not exist";
		this.logger.log(Level.ERROR, this.msg);
		throw new GLException(this.msg, 404);
	    }
	}
	if (acctCatg.getAcctTypeId() != acctCatg.getAcctTypeId()) {
	    this.msg = "Account Category account type is invalid";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 405);
	}

	String catgName = acctCatg.getDescription();
	if (catgName == null || catgName.length() <= 0) {
	    this.msg = "Account Category Name must contain a value";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new GLException(this.msg, 402);
	}
	return old;
    }

    /**
     * Find one or more accounts of extended format by account category.
     * 
     * @param acctCatgId The id of the category.
     * @return List of {@link com.bean.VwAccount VwAccount} objects.
     * @throws GLException General database errors.
     */
    public List findByAcctCatgExt(int acctCatgId) throws GLException {
	VwAccount acct = GeneralLedgerFactory.createVwAccount();
	acct.addCriteria(VwAccount.PROP_ACCTCATID, acctCatgId);
	try {
	    return this.daoHelper.retrieveList(acct);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Find one or more accounts of extended format which the account name 
     * begins with <i>acctName</i>.
     * 
     * @param acctName The name of the account. 
     * @return List of {@link com.bean.VwAccount VwAccount} objects.
     * @throws GLException General database errors.
     */
    public List findByAcctNameExt(String acctName) throws GLException {
	VwAccount acct = GeneralLedgerFactory.createVwAccount();
	acct.addLikeClause(VwAccount.PROP_NAME, acctName);
	try {
	    return this.daoHelper.retrieveList(acct);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Find one or more accounts of extended format by its account number.
     * 
     * @param acctNo The unique account number that identifies an account
     * @return List of {@link com.bean.VwAccount VwAccount} objects.
     * @throws GLException General database errors.
     */
    public List findByAcctNoExt(String acctNo) throws GLException {
	VwAccount acct = GeneralLedgerFactory.createVwAccount();
	acct.addLikeClause(VwAccount.PROP_ACCTNO, acctNo);
	try {
	    return this.daoHelper.retrieveList(acct);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Find one or more accounts of extended format by account type.
     *  
     * @param acctTypeId The id of the account type.
     * @return List of {@link com.bean.VwAccount VwAccount} objects.
     * @throws GLException General database errors.
     */
    public List findByAcctTypeExt(int acctTypeId) throws GLException {
	VwAccount acct = GeneralLedgerFactory.createVwAccount();
	acct.addCriteria(VwAccount.PROP_ACCTTYPEID, acctTypeId);
	try {
	    return this.daoHelper.retrieveList(acct);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Find an account of extended format by its unique id.
     * 
     * @param id The unique id that identifies an account.
     * @return {@link com.bean.VwAccount VwAccount} object.
     * @throws GLException General database errors.
     */
    public Object findByIdExt(int id) throws GLException {
	VwAccount acct = GeneralLedgerFactory.createVwAccount();
	acct.addCriteria(VwAccount.PROP_ID, id);
	try {
	    return this.daoHelper.retrieveObject(acct);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

    /**
     * Find an account category instance of extended format based on its 
     * unique identifier.
     * 
     * @param acctCatgId The unique id of the category.
     * @return {@link com.bean.VwCategory VwCategory} object.
     * @throws GLException
     */
    public Object findAcctCatgByIdExt(int acctCatgId) throws GLException {
	VwCategory obj = GeneralLedgerFactory.createVwCategory();
	obj.addCriteria(VwCategory.PROP_ACCTCATID, acctCatgId);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new GLException(e);
	}
    }

}

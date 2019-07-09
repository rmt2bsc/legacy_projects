package com.xact.purchases.creditor;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.api.db.DatabaseException;

import com.api.security.authentication.RMT2SessionBean;

import com.bean.Creditor;
import com.bean.VwXactCreditChargeList;
import com.bean.Xact;
import com.bean.XactTypeItemActivity;

import com.bean.bindings.JaxbAccountingFactory;
import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;
import com.controller.Response;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorException;
import com.gl.creditor.CreditorExt;
import com.gl.creditor.CreditorFactory;

import com.util.NotFoundException;
import com.util.RMT2String;
import com.util.SystemException;

import com.xact.XactConst;
import com.xact.XactException;
import com.xact.XactManagerApiImpl;

import com.xml.schema.bindings.BusinessContactCriteria;
import com.xml.schema.bindings.ObjectFactory;

/**
 * Api Implementation of CreditorPurchasesApi that manages general credit charge transactions.   
 * A <b>general credit charge</b> transaction can be described as the exchange of goods and 
 * services from a merchant on credit generally using a credit card at the time the transaction 
 * occurs.  
 *  <p>
 * When a credit charge is created, the base transaction amount is posted to the xact table as 
 * a positive value, which represents an increase the company's liability to its creditor.   When 
 * a credit charge is reversed,  the base transaction amount is posted to the xact table as a negative 
 * value which decreases the value of the company's liability to its creditor.
 * <p>
 * Credit Charge transactions require an addtional posting of transaction amounts to reflect creditor 
 * activity which offsets the base transaction amount.   When a credit charge is created, the creditor 
 * activity amount is posted as a positive value which increases the value of the creditor's account.   
 * Conversely, when a credit charge is reversed, the creditor activity amount is posted as negative 
 * value decreasing the value of the creditor's account.
 * 
 * @author Roy Terrell
 *
 */
class CreditorPurchasesDatabaseImpl extends XactManagerApiImpl implements CreditorPurchasesApi {

    private Logger logger;

    private Response response;
    
    private int creditorId;

    /**
     * Default Constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    public CreditorPurchasesDatabaseImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructor which uses database connection
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public CreditorPurchasesDatabaseImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.setBaseView("XactView");
	this.setBaseClass("com.bean.Xact");
    }

    /**
     * Construct a CreditorPurchasesDatabaseImpl instance that is initialized with 
     * a Database Connection Bean and a Request object 
     * 
     * @param dbConn The database connection bean
     * @param request The user's request
     * @throws DatabaseException
     * @throws SystemException
     */
    public CreditorPurchasesDatabaseImpl(DatabaseConnectionBean dbConn, Request request) throws DatabaseException, SystemException {
	this(dbConn);
	this.request = request;
    }

    /**
     * Construct a CreditorPurchasesDatabaseImpl instance that is initialized with 
     * a Database Connection Bean, a Request object, and a Response object.
     * 
     * @param dbConn The database connection bean
     * @param request The user's request
     * @param response The user's response
     * @throws DatabaseException
     * @throws SystemException
     */
    public CreditorPurchasesDatabaseImpl(DatabaseConnectionBean dbConn, Request request, Response response) throws DatabaseException, SystemException {
	this(dbConn, request);
	this.response = response;
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2Base#init()
     */
    @Override
    public void init() {
	super.init();
	this.logger = Logger.getLogger("CreditorPurchasesDatabaseImpl");
    }

    public void close() {
	this.daoHelper = null;
	super.close();
    }

    /**
     * Retrieve creditor purchases transactions from the database using on custom criteria.
     * Data is orderd by business name and transaction data in ascending and descending 
     * order, respectively.
     * 
     * @param criteria The custom SQL selection criteria to filter data set.
     * @return A List of {@link com.xact.purchases.creditor.CreditorChargeExt CreditorChargeExt}
     * @throws CreditorPurchasesException General database error.
     */
    public List<CreditorChargeExt> findCreditorPurchasesXact(String criteria) throws CreditorPurchasesException {
	List<VwXactCreditChargeList> credList = null;
	try {
	    VwXactCreditChargeList obj = CreditorPurchasesFactory.createVwXactCreditChargeList();
	    obj.addCustomCriteria(criteria);
	    obj.addOrderBy(VwXactCreditChargeList.PROP_XACTDATE, VwXactCreditChargeList.ORDERBY_DESCENDING);
	    credList = this.daoHelper.retrieveList(obj);
	    if (credList == null) {
		credList = new ArrayList<VwXactCreditChargeList>();
	    }
	}
	catch (DatabaseException e) {
	    throw new CreditorPurchasesException(e);
	}

	List<CreditorChargeExt> credChargeExtList = new ArrayList<CreditorChargeExt>();
	if (credList.size() <= 0) {
	    return credChargeExtList;
	}
	
	// Create JAXB criteria object
	ObjectFactory f = new ObjectFactory();
	BusinessContactCriteria wsCriteria = f.createBusinessContactCriteria();
	
	// Build list business id's to submit to web service
	for (VwXactCreditChargeList cred : credList) {
	    wsCriteria.getBusinessId().add(BigInteger.valueOf(cred.getBusinessId()));
	}

	// Call web service to fetch business contact based on list of business id's
	RMT2SessionBean userSession = (RMT2SessionBean) this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
	JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory(); 
	List<CreditorExt> extList;
	try {
	    extList = jaxbUtil.getCreditorContactData(wsCriteria, userSession.getLoginId());
	}
	catch (Exception e) {
	    throw new CreditorPurchasesException(e);
	}
	
	// use results to create a Map which is keyed by business Id.
	Map <Integer, CreditorExt> credExtMap = jaxbUtil.createCreditorContactMap(extList);
	
	for (VwXactCreditChargeList cred : credList) {
	    CreditorExt ext = credExtMap.get(cred.getBusinessId());
	    CreditorChargeExt credExt = this.createCreditorChargeExtInstance(cred, ext);
	    if (credExt != null) {
		credChargeExtList.add(credExt);
	    }
	}

	return credChargeExtList;
    }
    
    
    
    /**
     * Creates an instance of CreditorChargeExt from <i>cred</i> and <i>ext</i>.  
     * Essentially, data is merged from <i>cred</i> and <i>ext</i>  where the 
     * business id of <i>cred</i> matches the business id of <i>ext</i>.
     *  
     * @param cred 
     *          {@link com.bean.VwXactCreditChargeList VwXactCreditChargeList} object.
     * @param ext 
     *          an instance of CreditorExt containing the busiess contact data to merge; 
     * @return {@link com.xact.purchases.creditor.CreditorChargeExt CreditorChargeExt}
     * @throws CreditorPurchasesException
     */
    private CreditorChargeExt createCreditorChargeExtInstance(VwXactCreditChargeList cred, CreditorExt ext) throws CreditorPurchasesException {
	CreditorChargeExt c = new CreditorChargeExt();
	c.setBusinessId(cred.getBusinessId());
	
	// Get business contact data.
	if (ext != null) {
	    c.setBusType(ext.getBusType());
	    c.setServType(ext.getServType());
	    c.setName(ext.getName());
	    c.setShortname(ext.getShortname());
	    c.setContactFirstname(ext.getContactFirstname());
	    c.setContactLastname(ext.getContactLastname());
	    c.setContactPhone(ext.getContactPhone());
	    c.setContactExt(ext.getContactExt());
	    c.setTaxId(ext.getTaxId());
	    c.setWebsite(ext.getWebsite());    
	}
	
	// Get Creditor data
	c.setCreditorId(cred.getCreditorId());
	c.setCreditorTypeId(cred.getCreditorTypeId());
	c.setAccountNo(cred.getAccountNo());
	c.setCreditLimit(cred.getCreditLimit());
	c.setActive(cred.getActive());
	c.setApr(cred.getApr());
	c.setCreditorDateCreated(cred.getCreditorDateCreated());
	c.setCreditorTypeDescription(cred.getCreditorTypeDescription());
	c.setBalance(cred.getBalance());
	c.setXactId(cred.getXactId());
	c.setXactAmount(cred.getXactAmount());
	c.setXactDate(cred.getXactDate());
	c.setDocumentId(cred.getDocumentId());
	c.setPostedDate(cred.getPostedDate());
	c.setConfirmNo(cred.getConfirmNo());
	c.setNegInstrNo(cred.getNegInstrNo());
	c.setTenderId(cred.getTenderId());
	c.setTenderDescription(cred.getTenderDescription());
	c.setXactSubtypeId(cred.getXactSubtypeId());
	c.setXactTypeId(cred.getXactTypeId());
	c.setXactTypeName(cred.getXactTypeName());
	c.setReason(cred.getReason());
	c.setXactEntryDate(cred.getXactEntryDate());
	c.setUserId(cred.getUserId());
	c.setXactCategoryId(cred.getXactCatgId());
	c.setToMultiplier(cred.getToMultiplier());
	c.setFromMultiplier(cred.getFromMultiplier());
	c.setToAcctTypeId(cred.getToAcctTypeId());
	c.setToAcctCatgId(cred.getToAcctCatgId());
	c.setFromAcctTypeId(cred.getFromAcctTypeId());
	c.setFromAcctCatgId(cred.getFromAcctCatgId());
	c.setHasSubsidiary(cred.getHasSubsidiary());
	
	return c;
    }
    

    /**
     * Creates a creditor related credit chargetransaction or reverses and existing one.   If the transaction 
     * id that is encapsulated in _xact is 0, then a new transaction is created.   Otherwise, an existing 
     * transaction is reversed.   The customer activity is always posted as an offset to the base transaction amount.
     * 
     * @param _xact Source transaction.
     * @param _items An ArrayList of random objects.
     * @param _creditorId  The Id of the creditor.
     * @return id of the new transaction.
     * @throws CashDisbursementsException
     */
    public int maintainCreditCharge(Xact xact, List items, int creditorId) throws CreditorPurchasesException {
	int xactId;
	double xactAmount = 0;
	
	this.creditorId = creditorId;

	if (xact == null) {
	    this.msg = "Credit Charge transaction object cannot be null";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new CreditorPurchasesException(this.msg, 702);
	}

	// Determine if we are creating or reversing the cash disbursement
	if (xact.getXactId() <= 0) {
	    xact.setXactTypeId(XactConst.XACT_TYPE_CREDITCHARGE);
	    xactId = this.createCreditCharge(xact, items);
	}
	else {
	    xactId = this.reverseCreditCharge(xact, items);
	}

	// At this point a transaction was successfully created, and we need to reflect that transaction in the creditor's activity table.
	// Since the creditor activity amount will always post as an offset to the base transaction amount, take the revised base 
	// transaction amount an reverse it.
	try {
	    xactAmount = xact.getXactAmount();
	    this.createCreditorActivity(creditorId, xactId, xactAmount);
	}
	catch (XactException e) {
	    throw new CreditorPurchasesException(e);
	}
	return xactId;
    }

    /**
     * Creates a general credit chargetransasction
     * 
     * @param _xact The transaction to be added to the database.
     * @param _items An ArrayList of random objects.
     * @return The id of the new transaction.
     * @throws CashDisbursementsException
     */
    protected int createCreditCharge(Xact xact, List items) throws CreditorPurchasesException {
	int xactId = 0;

	try {
	    xactId = this.maintainXact(xact, items);
	    return xactId;
	}
	catch (XactException e) {
	    throw new CreditorPurchasesException(e);
	}
    }
    
    

    /**
     * Reverses a credit chargetransaction
     * 
     * @param _xact The target transaction
     * @param _items An ArrayList of random objects.
     * @return The id of the new transaction.
     * @throws CashDisbursementsException If the transaction has already bee flagged as finalized or if a 
     * general transction error occurs.
     */
    protected int reverseCreditCharge(Xact xact, List items) throws CreditorPurchasesException {
	int xactId = 0;

	try {
	    // Cannot reverse credit chargetransaction that has been finalized
	    if (!this.isXactModifiable(xact)) {
		msg = "credit chargecannot be reversed since it is already finalized";
		System.out.println("[XactCashDisburseApiImpl.reverseCashDisbursement] " + msg);
		throw new CreditorPurchasesException(msg);
	    }

	    this.finalizeXact(xact);
	    xactId = this.reverseXact(xact, items);
	    return xactId;
	}
	catch (XactException e) {
	    throw new CreditorPurchasesException(e);
	}
    }

    /**
     * Prepends credit chargecomments with a tag.   If user did not input anything for the transction 
     * reason, then the method is aborted which will allow postValidateXact to catch the error.   Cash 
     * disbursement transactions require the reversal  multiplier to be applied which will yield a negative 
     * amount representing cash outgoing.  The reversal of an existing credit chargetransaction 
     * requires the reversal mulitplier to be applied which offsets the orginal transaction. 
     * 
     * @param _xact The target transaction
     */
    protected void preCreateXact(Xact xact) {
	double xactAmount = 0;
	super.preCreateXact(xact);
	if (xact.getReason() == null || xact.getReason().equals("")) {
	    return;
	}
	// Only modify reason for non-reversal cash receipts
	if (xact.getXactSubtypeId() == 0) {
	    xact.setReason(xact.getReason());

	    // Ensure that credit charge is posted to the base transaction table as a negative amount.
	    //xactAmount = _xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER;
	    xactAmount = xact.getXactAmount();
	    xact.setXactAmount(xactAmount);
	}
	
	// Assign last four digits of credit card number
	CreditorApi credApi = CreditorFactory.createApi(this.connector);
	try {
	    Creditor creditor = (Creditor) credApi.findById(this.creditorId);
	    if (creditor == null) {
		this.msg = "Unable to create creditor purchase transction due to creditor's profile is not found in the database using creditor id: " + this.creditorId;
		logger.error(this.msg);
		throw new NotFoundException();
	    }
	    String ccNoMask = RMT2String.maskCreditCardNumber(creditor.getExtAccountNumber());
	    xact.setNegInstrNo(ccNoMask);
	}
	catch (CreditorException e) {
	    this.msg = "Unable to create creditor purchase transction due to the occurrence of a database error while attempting to fetch creditor's profile from the database using creditor id: " + this.creditorId;
	    logger.error(this.msg);
	    throw new DatabaseException(this.msg, e);
	}
	finally {
	    credApi = null;
	}
	return;
    }

    protected void preReverseXact(Xact xact, ArrayList xactItems) {
	xact.setXactDate(new Date());
    }

    /**
     * Ensures that the base of the transaction meets general credit chargevalidations.   The following validations must be satified:  
     * <ul>
     *    <li>Transaction date must have a value</li>
     *    <li>Transaction date is a valid date</li>
     *    <li>Transaction date is not greater than curent date</li>
     *    <li>Transaction tender is entered</li>
     *    <li>Transaction tender's negotiable instrument number is entered,  if applicable.</li>
     *    <li>Transaction amount must be entered</li>
     *    <li>Transaction reason is entered</li>
     * </ul>
     * 
     * @param _xact The transaction object to be validated.
     * @throws XactException Validation error occurred.
     */
    protected void postValidateXact(Xact xact) throws XactException {
	java.util.Date today = new java.util.Date();

	// Verify that transaction date has a value.
	if (xact.getXactDate() == null) {
	    this.msg = "Creditor Purchase transaction date is required";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg);
	}
	// Verify that the transacton date value is valid
	if (xact.getXactDate().getTime() > today.getTime()) {
	    this.msg = "Creditor Purchase transaction date cannot be in the future";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg);
	}

	// Verify that transaction tender has a value and is valid.
	if (xact.getTenderId() != XactConst.TENDER_COMPANY_CREDIT &&  xact.getTenderId() != XactConst.TENDER_CREDITCARD) {
	    this.msg = "Creditor Purchase Tender Type must be either Bank Credit Card or Finance Company Credit";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg);
	}

	// Ensure that the source of the transction is entered.
	if (xact.getReason() == null || xact.getReason().equals("")) {
	    this.msg = "Transaction reason/source cannot be blank...this is usually the name of the merchant or service provider";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, 703);
	}
	return;
    }

    /**
     * This method checks if <i>xactItems</i> of the general creditor purchase transaction 
     * is not null and contains at least one element with an instance of {@link com.bean.XactTypeItemActivity XactTypeItemActivity}.
     * If successful, basic validations from the ancestor are performed for <i>xact</> and 
     * <i>xactItems</>.
     *   
     * @param xact
     *          {@link com.bean.Xact Xact} instance.
     * @param xactItems
     *          A List of {@link com.bean.XactTypeItemActivity XactTypeItemActivity} instances.
     * @throws XactException
     *          When <i>xact</i> does not meet basic validation requirements, <i>xactItems</i> 
     *          is null or is empty, or basic validations fail.
     * @see {@link com.xact.XactManagerApiImpl#validate(com.bean.Xact, java.util.List) XactManagerApiImpl.validate(Xact, List)}          
     */
    @Override
    protected void validate(Xact xact, List<XactTypeItemActivity> xactItems) throws XactException {
        if (xactItems == null || xactItems.size() == 0) {
            this.msg = "Creditor purchase transaction must contain at least one item detail";
            this.logger.log(Level.ERROR, this.msg);
            throw new XactException(this.msg, 704);
        }
        // Perform common validations
        super.validate(xact, xactItems);
    }

}

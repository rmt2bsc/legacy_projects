package com.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;

import com.api.db.DatabaseException;

import com.bean.VwXactList;
import com.bean.VwXactTypeItemActivity;
import com.bean.Xact;
import com.bean.XactTypeItemActivity;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xact.XactConst;
import com.xact.XactFactory;
import com.xact.XactManagerApiImpl;

import com.xact.XactException;
import com.util.SystemException;

/**
 * Api Implementation that manages general cash disbursement and creditor/vendor 
 * cash disbursement transactions.  A <b>general cash disbursement</b> transaction 
 * can be described as the exchange of goods and services for cash at the time the 
 * transaction occurs.   A <b>creditor/vendor cash disbursement</b> is the cash payment 
 * of a creditor or vendors's invoice for goods and services obtained on credit at an 
 * earlier time.     General and Creditor/Vendor cash disbursements are quite similar 
 * with the exception that creditor/vendor cash disbursement require the posting of 
 * creditor activity entries. 
 *  <p>
 * When a cash disbursement is created, the base transaction amount is posted to the 
 * xact table as a negative value, which decreases the company's cash statust.   When 
 * a cash disbursement is reversed,  the base transaction amount is posted to the xact 
 * table as a positivie value which increases the value of the company's cash status.
 * <p>
 * Creditor/Vendor cash disbursements requires an addtional posting of transaction amounts 
 * to reflect creditor activity which offsets the base transaction amount.   When a cash 
 * disbursement is created, the creditor activity amount is posted as a positive value which 
 * increases the value of the creditor's account.   Conversely, when a cash disbursement is 
 * reversed, the creditor activity amount is posted as negative value decreasing the value 
 * of the creditor's account.
 * 
 * @author Roy Terrell
 *
 */
class CashDisburseXactImpl extends XactManagerApiImpl implements CashDisbursementsApi {
    private Logger logger;
    
    private boolean creditorDisb;

    /**
     * Default Constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    public CashDisburseXactImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructor which uses database connection
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public CashDisburseXactImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.setBaseView("XactView");
	this.setBaseClass("com.bean.Xact");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level and 
     * creates a {@link Xact} bean based on _xactTypeId and _amt.
     * 
     * @param dbConn
     * @param _xactTypeId
     * @param _amt
     * @throws DatabaseException
     * @throws SystemException
     */
    public CashDisburseXactImpl(DatabaseConnectionBean dbConn, Request _request) throws DatabaseException, SystemException {
	this(dbConn);
	this.request = _request;
    }

    public void init() {
	super.init();
	this.logger = Logger.getLogger("CashDisburseXactImpl");
    }

    public void close() {
	this.daoHelper = null;
	super.close();
    }

    /**
     * Return a list of cash disbursements transactions which the result set is 
     * filtered by <i>criteria</i>.  The list will contain instances of VwXactList
     * and the result set is sorted in descending order by the transaction date 
     * and transaction id.  
     * 
     * @param criteria
     *          Selection criteria to filter data that follows SQL '92 standards.
     * @return Object
     *          a List of {@link com.bean.VwXactList VwXactList}
     * @throws CashDisbursementsException
     *          Database access errors.
     */
    public Object findXactList(String criteria) throws CashDisbursementsException {
	VwXactList obj = new VwXactList();
	obj.addCustomCriteria(criteria);
	obj.addOrderBy(VwXactList.PROP_XACTDATE, VwXactList.ORDERBY_DESCENDING);
	obj.addOrderBy(VwXactList.PROP_ID, VwXactList.ORDERBY_DESCENDING);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new CashDisbursementsException(e);
	}
    }

    
    /**
     * Return a list of cash disbursements transaction items which the result set is 
     * filtered by <i>criteria</i>
     * 
     * @param criteria
     *          Selection criteria to filter data.
     * @return {@link com.bean.VwXactTypeItemActivity VwXactTypeItemActivity}
     *          Arbitrary object representing the result set of the query
     * @throws CashDisbursementsException
     */
	public List findXactItems(String criteria) throws CashDisbursementsException {
		VwXactTypeItemActivity item = XactFactory.createXactTypeItemActivityView();
		item.addCustomCriteria(criteria);
		item.addOrderBy(VwXactTypeItemActivity.PROP_XACTDATE, VwXactTypeItemActivity.ORDERBY_DESCENDING);
		item.addOrderBy(VwXactTypeItemActivity.PROP_XACTTYPEITEMNAME, VwXactTypeItemActivity.ORDERBY_ASCENDING);
		item.addOrderBy(VwXactTypeItemActivity.PROP_ITEMNAME, VwXactTypeItemActivity.ORDERBY_ASCENDING);
		return this.daoHelper.retrieveList(item);
	}

	/**
     * Creates a general cash disbursement transaction or reverses and existing cash disbursement 
     * transaction.   If the transaction id that is encapsulated in _xact is 0, then a new 
     * transaction is created.   Otherwise, an existing transaction is reversed.
     * 
     * @param _xact Source transaction.
     * @param _items An ArrayList of random objects.
     * @return id of the new transaction.
     * @throws CashDisbursementsException
     */
    public int maintainCashDisbursement(Xact xact, List items) throws CashDisbursementsException {
	int xactId;

	if (xact == null) {
	    this.msg = "Cash Disbursement object cannot be null";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new CashDisbursementsException(this.msg);
	}

	// Identify this transaction as a general cash disbursement
	this.creditorDisb = false;
	// Determine if we are creating or reversing the cash disbursement
	if (xact.getXactId() <= 0) {
	    xact.setXactTypeId(XactConst.XACT_TYPE_CASHDISBEXP);
	    xactId = this.createCashDisbursement(xact, items);
	}
	else {
	    xactId = this.reverseCashDisbursement(xact, items);
	}
	return xactId;
    }

    /**
     * Creates a creditor related cash disbursement transaction or reverses and existing one.   If the transaction 
     * id that is encapsulated in _xact is 0, then a new transaction is created.   Otherwise, an existing 
     * transaction is reversed.   The creditor activity is always posted as an offset to the base transaction amount.
     * 
     * @param _xact Source transaction.
     * @param _items An ArrayList of random objects.
     * @param _creditorId  The Id of the creditor.
     * @return id of the new transaction.
     * @throws CashDisbursementsException
     */
    public int maintainCashDisbursement(Xact xact, List items, int creditorId) throws CashDisbursementsException {
	int xactId;
	double xactAmount = 0;

	if (xact == null) {
	    this.msg = "Cash Disbursement object cannot be null";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new CashDisbursementsException(this.msg);
	}

	// Identify this transaction as a creditor cash disbursement
        this.creditorDisb = true;
	// Determine if we are creating or reversing the cash disbursement
	if (xact.getXactId() <= 0) {
	    xact.setXactTypeId(XactConst.XACT_TYPE_CASHDISBACCT);
	    xactId = this.createCashDisbursement(xact, items);
	}
	else {
	    xactId = this.reverseCashDisbursement(xact, items);
	}

	// At this point a transaction was successfully created, and we need to reflect that tranaction 
	// in the creditor's activity table.  Since the creditor activity amount will always post as an 
	// offset to the base transaction amount, take the revised base transaction amount an reverse it.
	try {
	    xactAmount = xact.getXactAmount(); // * XactConst.REVERSE_MULTIPLIER;
	    this.createCreditorActivity(creditorId, xactId, xactAmount);
	}
	catch (XactException e) {
	    throw new CashDisbursementsException(e);
	}
	return xactId;
    }

    /**
     * Creates a general cash disbursement transasction
     * 
     * @param _xact The transaction to be added to the database.
     * @param _items An ArrayList of random objects.
     * @return The id of the new transaction.
     * @throws CashDisbursementsException
     */
    protected int createCashDisbursement(Xact _xact, List _items) throws CashDisbursementsException {
	int xactId = 0;

	try {
	    xactId = this.maintainXact(_xact, _items);
	    return xactId;
	}
	catch (XactException e) {
	    throw new CashDisbursementsException(e);
	}
    }

    /**
     * Reverses a cash disbursement transaction
     * 
     * @param _xact The target transaction
     * @param _items An ArrayList of random objects.
     * @return The id of the new transaction.
     * @throws CashDisbursementsException If the transaction has already bee flagged as finalized or if a 
     * general transction error occurs.
     */
    protected int reverseCashDisbursement(Xact _xact, List _items) throws CashDisbursementsException {
	int xactId = 0;

	try {
	    // Cannot reverse cash disbursement transaction that has been finalized
	    if (!this.isXactModifiable(_xact)) {
		msg = "Cash Disbursement cannot be reversed since it is already finalized";
		this.logger.log(Level.ERROR, this.msg);
		throw new CashDisbursementsException(msg);
	    }

	    this.finalizeXact(_xact);
	    _xact.setXactDate(new java.util.Date());
	    if (_xact.getTenderId() == 0) {
	        _xact.setTenderId(XactConst.TENDER_CASH);
	    }
	    xactId = this.reverseXact(_xact, _items);
	    return xactId;
	}
	catch (XactException e) {
	    throw new CashDisbursementsException(e);
	}
    }

    /**
     * Prepends cash disbursement comments with a tag.   If user did not input anything for the transction 
     * reason, then the method is aborted which will allow postValidateXact to catch the error.   Cash 
     * disbursement transactions require the reversal  multiplier to be applied which will yield a negative 
     * amount representing cash outgoing.  The reversal of an existing Cash Disbursement transaction 
     * requires the reversal mulitplier to be applied which offsets the orginal transaction. 
     * 
     * @param _xact The target transaction
     */
    protected void preCreateXact(Xact _xact) {
	double xactAmount = 0;
	super.preCreateXact(_xact);
	if (_xact.getReason() == null || _xact.getReason().equals("")) {
	    return;
	}
	// Only modify reason for non-reversal cash receipts
	if (_xact.getXactSubtypeId() == 0) {
	    // Ensure that cash disbursement  is posted to the base transaction table as a negative amount.
	    xactAmount = _xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER;
	    _xact.setXactAmount(xactAmount);
	}
	return;
    }

    /**
     * Ensures that the base of the transaction meets general Cash Disbursement validations.   The following validations must be satified:  
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
    protected void postValidateXact(Xact _xact) throws XactException {
	java.util.Date today = new java.util.Date();

	// Verify that transaction date has a value.
	if (_xact.getXactDate() == null) {
	    this.msg = "Transaction must be associated with a date";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, 619);

	}
	// Verify that the transacton date value is valid
	if (_xact.getXactDate().getTime() > today.getTime()) {
	    this.msg = "Transaction date cannot be greater than the current date";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, 621);
	}

	// Verify that transaction tender has a value and is valid.
	if (_xact.getTenderId() <= 0) {
	    this.msg = "Transaction must be associated with a tender";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, 622);
	}
	// Verify that the transaction's tender is assoicated with a negotiable instrument number, if applicable
	switch (_xact.getTenderId()) {
	case XactConst.TENDER_CHECK:
	case XactConst.TENDER_COMPANY_CREDIT:
	case XactConst.TENDER_CREDITCARD:
	case XactConst.TENDER_DEBITCARD:
	case XactConst.TENDER_INSURANCE:
	case XactConst.TENDER_MONEYORDER:
	    if (_xact.getNegInstrNo() == null || _xact.getNegInstrNo().equals("")) {
		this.msg = "Transaction tender must be associated with a tender number";
		this.logger.log(Level.ERROR, this.msg);
		throw new XactException(this.msg, 623);
	    }
	}

	// Ensure that reaso is entered.
	if (_xact.getReason() == null || _xact.getReason().equals("")) {
	    this.msg = "Transaction reason cannot be blank...Save failed.";
	    this.logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, -1);
	}
	return;
    }

    /**
     * This method checks if <i>xactItems</i> of the general cash disbursement transaction 
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
        // Transaction items are only required for general cash disbursement type transactions
        if (!this.creditorDisb) {
            if (xactItems == null || xactItems.size() == 0) {
                this.msg = "General cash disbursement transaction must contain at least one item detail";
                this.logger.log(Level.ERROR, this.msg);
                throw new XactException(this.msg, 624);
            }    
        }
        
        // Perform common validations
        super.validate(xact, xactItems);
    }
}

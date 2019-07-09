package com.xact;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import com.api.db.DatabaseException;

import com.api.DaoApi;

import com.api.db.orm.DataSourceFactory;
import com.api.db.orm.RdbmsDaoQueryHelper;
import com.api.db.orm.RdbmsDataSourceImpl;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.VwCreditorXactHist;
import com.bean.Xact;
import com.bean.XactCategory;
import com.bean.XactCodes;
import com.bean.XactCodeGroup;
import com.bean.XactType;
import com.bean.XactTypeItemActivity;
import com.bean.VwXactList;
import com.bean.CustomerActivity;
import com.bean.CreditorActivity;

import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.constants.RMT2ServletConst;
import com.controller.Request;

import com.util.InvalidDataException;
import com.util.RMT2Date;
import com.util.RMT2Money;
import com.util.SystemException;

import com.xact.XactConst;
import com.xact.XactManagerApi;
import com.xact.XactFactory;
import com.xact.XactException;

/**
 * Api Implementation that manages transaction events.
 * 
 * @author Roy Terrell
 *
 */
public class XactManagerApiImpl extends RdbmsDataSourceImpl implements XactManagerApi {

    private String criteria;

    private Xact xactBean;

    private List xactItems;

    protected RdbmsDaoQueryHelper daoHelper;
    
    private Logger logger = Logger.getLogger(XactManagerApiImpl.class);

    /**
     * Default Constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    public XactManagerApiImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructor which uses database connection
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public XactManagerApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.setBaseView("XactView");
	this.setBaseClass("com.bean.Xact");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level and creates a 
     * {@link Xact} bean based on _xactTypeId and _amt.
     * 
     * @param dbConn
     * @param _xactTypeId
     * @param _amt
     * @throws DatabaseException
     * @throws SystemException
     */
    public XactManagerApiImpl(DatabaseConnectionBean dbConn, int _xactTypeId, double _amt) throws DatabaseException, SystemException {
	this(dbConn);
	this.xactBean = XactFactory.createXact(_xactTypeId, _amt);
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the acestor level and creates a 
     * {@link Xact} bean based on _xactTypeId and _amt.
     * 
     * @param dbConn
     * @param _xactTypeId
     * @param _amt
     * @throws DatabaseException
     * @throws SystemException
     */
    public XactManagerApiImpl(DatabaseConnectionBean dbConn, Request _request) throws DatabaseException, SystemException {
	this(dbConn);
	this.request = _request;
    }

    public Xact findXactById(int value) throws XactException {
	this.setBaseView("XactView");
	this.setBaseClass("com.bean.Xact");

	this.criteria = "xact_id = " + value;
	try {
	    List list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return (Xact) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    throw new XactException("Transaction, " + value + ", was not found");
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView("XactPostDetailsView");
	    this.setBaseClass("com.bean.XactPostDetails");
	}
    }

    public List findXact(String _criteria, String _orderBy) throws XactException {
	this.criteria = _criteria;
	try {
	    List list = this.find(this.criteria, _orderBy);
	    return list;
	}
	catch (IndexOutOfBoundsException e) {
	    throw new XactException("Transaction details were not found using custom criteria");
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    /**
     * 
     */
    public void close() {
	this.criteria = null;
	this.xactBean = null;
	this.xactItems = null;
	this.daoHelper = null;
	super.close();
    }

    /**
     * Retrieves customer transaction history using _custId.
     * 
     * @param _custId Customer's internal id.
     * @throws XactExcepion
     */
    public List findCustomerXactHist(int _custId) throws XactException {
	String oldView = this.setBaseView("VwCustomerXactHistView");
	String oldClass = this.setBaseClass("com.bean.VwCustomerXactHist");
	String whereClause = null;
	String orderByClause = null;

	whereClause = " customer_id = " + _custId;
	orderByClause = " xact_id desc ";
	try {
	    List list = this.find(whereClause, orderByClause);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView(oldView);
	    this.setBaseClass(oldClass);
	}
    }

    /**
     * Retrieves List of VwCreditorXactHist objects order by transaction date (descending) 
     * and transaction id (descending).
     */
    public List<VwCreditorXactHist> findCreditorXactHist(int _credId) throws XactException {
        VwCreditorXactHist obj = new VwCreditorXactHist();
        obj.addCriteria(VwCreditorXactHist.PROP_CREDITORID, _credId);
        obj.addOrderBy(VwCreditorXactHist.PROP_XACTDATE, VwCreditorXactHist.ORDERBY_DESCENDING);
        obj.addOrderBy(VwCreditorXactHist.PROP_XACTID, VwCreditorXactHist.ORDERBY_DESCENDING);
        try {
            List<VwCreditorXactHist> list = this.daoHelper.retrieveList(obj);
            return list;
        }
        catch (DatabaseException e) {
            throw new XactException(e);
        }
    }

    public List findXactCatg(String _criteria) throws XactException {
	this.setBaseView("XactCategoryView");
	this.setBaseClass("com.bean.XactCategory");
	this.criteria = _criteria;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public XactCategory findXactCatgById(int _id) throws XactException {
	String oldView = this.setBaseView("XactCategoryView");
	String oldClass = this.setBaseClass("com.bean.XactCategory");
	this.criteria = "id = " + _id;
	try {
	    List list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return (XactCategory) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    this.msgArgs.clear();
	    this.msgArgs.add(String.valueOf(_id));
	    throw new XactException(this.connector, 615, this.msgArgs);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView(oldView);
	    this.setBaseClass(oldClass);
	}
    }

    public List findXactCodeGroup(String _criteria) throws XactException {
	this.criteria = _criteria;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public XactCodeGroup findXactCodeGroupById(int _id) throws XactException {
	this.setBaseView("XactCodeGroupView");
	this.setBaseClass("com.bean.XactCodeGroup");
	this.criteria = "id = " + _id;
	try {
	    List list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return (XactCodeGroup) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    throw new XactException("Transaction Code Group could not be found using Group Id, " + _id);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public List findXactCode(String _criteria) throws XactException {
	this.criteria = _criteria;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public XactCodes findXactCodeById(int _id) throws XactException {
	this.setBaseView("XactCodesView");
	this.setBaseClass("com.bean.XactCodes");
	this.criteria = "id = " + _id;
	try {
	    List list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return (XactCodes) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    this.msgArgs.clear();
	    this.msgArgs.add(String.valueOf(_id));
	    throw new XactException(this.connector, 617, this.msgArgs);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public List findXactCodeByGroupId(int _id) throws XactException {
	this.setBaseView("XactCodesView");
	this.setBaseClass("com.bean.XactCodes");
	this.criteria = "xact_code_grp_id = " + _id;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public List findXactType(String _criteria) throws XactException {
	this.setBaseView("XactTypeView");
	this.setBaseClass("com.bean.XactType");
	this.criteria = _criteria;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public XactType findXactTypeById(int xactTypeId) throws XactException {
	this.setBaseView("XactTypeView");
	this.setBaseClass("com.bean.XactType");
	this.criteria = "xact_type_id = " + xactTypeId;
	try {
	    List list = this.find(this.criteria);
	    if (list.size() == 0) {
		return null;
	    }
	    return (XactType) list.get(0);
	}
	catch (IndexOutOfBoundsException e) {
	    throw new XactException("Transaction Type could not be found using transaction type id, " + xactTypeId);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public List findXactTypeByCatgId(int _id) throws XactException {
	this.criteria = "xact_category_id = " + _id;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public List findXactTypeItemsActivityByXactId(int _xactId) throws XactException {
	String oldView = null;
	String oldClass = null;

	oldView = this.setBaseView("XactTypeItemActivityView");
	oldClass = this.setBaseClass("com.bean.XactTypeItemActivity");
	this.criteria = "xact_id = " + _xactId;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView(oldView);
	    this.setBaseClass(oldClass);
	}
    }

    public List findVwXactTypeItemActivityByXactId(int _xactId) throws XactException {
	String oldView = null;
	String oldClass = null;
	String orderBy = null;

	oldView = this.setBaseView("VwXactTypeItemActivityView");
	oldClass = this.setBaseClass("com.bean.VwXactTypeItemActivity");
	this.criteria = "xact_id = " + _xactId;
	orderBy = " xact_type_item_name asc, item_name asc ";
	try {
	    List list = this.find(this.criteria, orderBy);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView(oldView);
	    this.setBaseClass(oldClass);
	}
    }

    /**
     * Retrieves all transaction type item belonging to _xactTypeId
     * 
     * @param _xactId
     * @return ArrayList of XactTypeItem objects
     * @throws XactException
     */
    public List findXactTypeItemsByXactTypeId(int _xactTypeId) throws XactException {
	String oldView = null;
	String oldClass = null;

	oldView = this.setBaseView("XactTypeItemView");
	oldClass = this.setBaseClass("com.bean.XactTypeItem");
	this.criteria = "xact_type_id = " + _xactTypeId;
	try {
	    List list = this.find(this.criteria, " name ");
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView(oldView);
	    this.setBaseClass(oldClass);
	}
    }

    public VwXactList findXactListViewByXactId(int _xactId) throws XactException {
	String oldView = this.setBaseView("VwXactListView");
	String oldClass = this.setBaseClass("com.bean.VwXactList");
	this.criteria = "id = " + _xactId;
	try {
	    List list = this.find(this.criteria);
	    if (list.size() <= 0) {
		return null;
	    }
	    return (VwXactList) list.get(0);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView(oldView);
	    this.setBaseClass(oldClass);
	}
    }

    public List findXactListViewByXactTypeId(int _xactTypeId) throws XactException {
	String oldView = this.setBaseView("VwXactListView");
	String oldClass = this.setBaseClass("com.bean.VwXactList");
	this.criteria = "xact_type_item_xact_type_id = " + _xactTypeId;
	try {
	    List list = this.find(this.criteria);
	    return list;
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
	finally {
	    this.setBaseView(oldView);
	    this.setBaseClass(oldClass);
	}
    }
    
	public Xact getXactBean() {
	return this.xactBean;
    }

    public void setXactBean(Xact value) {
	this.xactBean = value;
    }

    public List getXactItems() {
	return this.xactItems;
    }

    public void setXactItems(List value) {
	this.xactItems = value;
    }

    public int maintainXact(Xact xact, List xactItems) throws XactException {
        this.validate(xact, xactItems);
        
        // We must be dealing with a valid transaction.  Try to save it!
	int rc = 0;
	rc = this.createXact(xact);
	this.createXactItems(xact, xactItems);
	return rc;
    }

    /**
     * Creates a transaction.
     * 
     * @param xact The transaction to be added.
     * @return New transaction id.
     * @throws XactException
     */
    protected int createXact(Xact xact) throws XactException {
	int rc = 0;

	this.preCreateXact(xact);
	rc = this.insertXact(xact);
	this.postCreateXact(xact);
	return rc;
    }
    
    /**
     * For general disbursements, creditor disbursements, 
     * @param xact
     * @param items
     * @throws XactException
     */
    private void validateAmountEqualsItemSum(Xact xact, List<XactTypeItemActivity> items) throws XactException {
        // Return to caller if transaction type item array has not been initialized.
        if (items == null) {
            this.msg = "Unable to read the list of transaction items due to being invalid or null";
            logger.error(this.msg);
            throw new XactException(this.msg);
        }
        // Verify that at least one item exists.
        if (items.size() == 0) {
            this.msg = "A minimum of one transaction item must exist in order to process this transaction";
            logger.error(this.msg);
            throw new XactException(this.msg);
        }
        // Begin to sum each transaction type item amount.
        double totalItemAmount = 0;
        for (XactTypeItemActivity item : items) {
            totalItemAmount = totalItemAmount + item.getAmount();
        }
        // Round summed item amount
        BigDecimal bd = new BigDecimal(totalItemAmount); 
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP); 
        totalItemAmount = bd.doubleValue();
        
        // Verify that transaction amount must equal the sum of all item amounts.
        if (Math.abs(xact.getXactAmount()) != Math.abs(totalItemAmount)) {
            this.msg = "The base transaction amount [" + Math.abs(xact.getXactAmount()) + "] must equal the sum of all transaction item amounts [" + totalItemAmount + "]";
            logger.error(this.msg);
            throw new XactException(this.msg);
        }
    }
    
    

    /**
     * Ensures that transaction date has a value before applying the transaction to the database.   
     * If transaction date is null or does not exist, default transaction date to the current day.   
     * Override this method to execute custom logic before base transaction is added to the database.
     * 
     * @param xact The target transaction
     */
    protected void preCreateXact(Xact xact) {
	java.util.Date today = new java.util.Date();
	if (xact.getXactDate() == null) {
	    xact.setXactDate(today);
	}
	if (xact.getXactSubtypeId() <= 0) {
	    xact.setNull("xactSubtypeId");
	}
	return;
    }

    /**
     * Override this method to execute custom logic after base transaction is added to the database.
     * 
     * @param _xact The target transaction
     */
    protected void postCreateXact(Xact _xact) {
	return;
    }

    /**
     * Inserts a properly initialized transaction into the database.
     * 
     * @param _xact The target transaction.
     * @return New transaction id.
     * @throws XactException
     */
    private int insertXact(Xact xact) throws XactException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	try {
	    if (this.request == null) {
                ut = RMT2Date.getUserTimeStamp("auto");
	    }
	    else {
	        ut = RMT2Date.getUserTimeStamp(this.request);    
	    }
	    xact.setDateCreated(ut.getDateCreated());
	    xact.setDateUpdated(ut.getDateCreated());
	    xact.setUserId(ut.getLoginId());
	    xact.setIpCreated(ut.getIpAddr());
	    xact.setIpUpdated(ut.getIpAddr());
	    rc = dao.insertRow(xact, true);

	    // Set transaction id with the auto-generated key.
	    xact.setXactId(rc);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new XactException(e);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    /**
     * Performs basic validations on the incoming transaction instance and the collection of 
     * transaction item instances.  <i>xact</i> is required to be not null.   <i>xactItems</i> 
     * is not required and is checked only when it is not null.   When <i>xactItems</i> contains 
     * one or more items, the sum of those items must equal the transaction amount found in 
     * <i>xact</i>.   Some transactions (accounts with subsidiaries), are allowed not to have 
     * any transaction detail items.
     * <p>
     * Override this method to add specific validations pertaining to the business requirement 
     * of the descendent.
     *   
     * @param xact
     *          {@link com.bean.Xact Xact} instance.
     * @param xactItems
     *          A List of {@link com.bean.XactTypeItemActivity XactTypeItemActivity} instances.
     * @throws XactException
     *          When <i>xact</i> does not meet basic validation requirements, one or more elements 
     *          in <i>xactItems</i> do not meet basic validation requirements, or the sum of the 
     *          transaction item amount of each element of <i>xactItems</i> does not equal the 
     *          transaction amount found in <i>xact</i>.
     */
    protected void validate(Xact xact, List<XactTypeItemActivity> xactItems) throws XactException {
        // Perform validations
        this.validateXact(xact);
        
        // For some transactions (Accounts with subsidiaries), it should be permissable to not have any transaction detail items
        if (xactItems != null) {
            this.validateXactItem(xactItems);
            if (xactItems.size() > 0) {
                this.validateAmountEqualsItemSum(xact, xactItems);    
            }
        }
    }
    
    
    /**
     * Validates the base of the transaction.   The following validations must be satified:  
     * <ul>
     *    <li>Base transaction object is valid</li>
     *    <li>Base transaction type id is greater than zero</li>
     * </ul>
     * 
     * @throws XactException
     */
    private void validateXact(Xact _xact) throws XactException {
	// Execute custom pre validations 
	this.preValidateXact(_xact);

	if (_xact == null) {
	    this.msg = "Transaction Base could not be updated since base object is null";
            logger.error(this.msg);
	    throw new XactException(this.msg);
	}

	//  Validate the Transaction Type
	if (_xact.getXactTypeId() <= 0) {
	    this.msg = "Transaction Type code must be greater than zero";
            logger.error(this.msg);
            throw new XactException(this.msg);
	}
	
	// validate money expression
	String temp = String.valueOf(_xact.getXactAmount());
	try {
            RMT2Money.validateMoney(temp);
        }
        catch (InvalidDataException e) {
            throw new XactException(e);
        }

	// Execute custom post validations
	this.postValidateXact(_xact);
    }

    /**
     * Override this method to execute custom logic before base transaction validations.
     * 
     * @param _xact The transaction object to be validated
     * @throws XactException Validation error occurred.
     */
    protected void preValidateXact(Xact _xact) throws XactException {
	return;
    }

    /**
     * Override this method to execute custom logic after base transaction validations.
     * 
     * @param _xact The transaction object to be validated.
     * @throws XactException Validation error occurred.
     */
    protected void postValidateXact(Xact _xact) throws XactException {
	return;
    }

    /**
     * Drives the process of creating entries into the xact_type_item_activity table by cycling
     * through all elements of the transaction type item array.   Validates that the base transaction 
     * amount is equal to the sum of all Transaction Type Item Activity amounts.
     * 
     * @param _xact The base transaction that is to be associated with each transaction _xactItems element.
     * @param _xactItems The list of transaction items to apply to the database whch is expected to be of 
     * type {@link XactTypeItemActivity}
     * @return 0 when either _xactItems is null or has no items available.   > 0 to indicate the number of items successfully processed. 
     * @throws XactException
     */
    protected int createXactItems(Xact xact, List<XactTypeItemActivity> xactItems) throws XactException {
	int totalItems = 0;
	int succesCount = 0;
	double totalItemAmount = 0;
	XactTypeItemActivity xtia = null;

	// Return to caller if transaction type item array has not been initialized.
	if (xactItems == null) {
	    return 0;
	}
	else {
	    totalItems = xactItems.size();
	}

	// Begin to add each transaction type item to the database.
	for (int ndx = 0; ndx < totalItems; ndx++) {
	    xtia = (XactTypeItemActivity) xactItems.get(ndx);
	    xtia.setXactId(xact.getXactId());
	    this.insertXactItem(xtia);
	    totalItemAmount = totalItemAmount + xtia.getAmount();
	    succesCount++;
	}

	//  Successfully return the total number of items added.
	return succesCount;
    }

    /**
     * Creates a entry in the xact_type_item_activity table.
     * 
     * @param _xtia
     * @return int - id of new transaction type item activity object.
     * @throws XactException
     */
    protected int insertXactItem(XactTypeItemActivity xtia) throws XactException {
	int rc = 0;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	UserTimestamp ut = null;

	try {
	    if (this.request == null) {
                ut = RMT2Date.getUserTimeStamp("auto");
            }
            else {
                ut = RMT2Date.getUserTimeStamp(this.request);    
            }
	    xtia.setDateCreated(ut.getDateCreated());
	    xtia.setDateUpdated(ut.getDateCreated());
	    xtia.setUserId(ut.getLoginId());
	    xtia.setIpCreated(ut.getIpAddr());
	    xtia.setIpUpdated(ut.getIpAddr());
	    rc = dao.insertRow(xtia, true);

	    // Set transaction id with the auto-generated key.
	    xtia.setXactTypeItemActvId(rc);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new XactException(e);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    
    private void validateXactItem(List<XactTypeItemActivity> items) throws XactException {
        if (items == null) {
            return;
        }
        for (XactTypeItemActivity item : items) {
            this.validateXactItem(item);
        }
    }
    
    
    /**
     * Validates a transaction type item object by ensuring that a transaction type item id 
     * and item description is provided.  Validates the base of the transaction.   The 
     * following validations must be satified:  
     * <ul>
     *    <li>Transaction Type Item  Activity object must be valid</li>
     *    <li>Transaction Type Item Id must valid (greater than zero)</li>
     *    <li>Transaction Type Item Activity Description cannot be null</li>
     * </ul>
     * 
     * @param xtia
     *          {@link com.bean.XactTypeItemActivity XactTypeItemActivity} instance.
     * @throws XactException
     *          When <i>xtia</i> is null, its id property is less than or equal to zero, or its 
     *          description property is null.
     */
    private void validateXactItem(XactTypeItemActivity xtia) throws XactException {
	// Execute custom pre validation logic.
	this.preValidateXactItem(xtia);

	if (xtia == null) {
	    this.msg = "Transaction type item activity object is invalid or null";
	    logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, -625);
	}
	if (xtia.getXactItemId() <= 0) {
	    this.msg = "Transaction type item id property is required to have a value";
	    logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, -626);
	}
	if (xtia.getDescription() == null || xtia.getDescription().length() <= 0) {
	    this.msg = "Transaction type item description property is required to have a value";
	    logger.log(Level.ERROR, this.msg);
	    throw new XactException(this.msg, -628);
	}

	// Execute custom post validation logic.
	this.postValidateXactItem(xtia);

	return;
    }

    /**
     * Override this method to execute custom logic before base transaction Item Activity validations.
     * 
     * @param _xtia
     * @throws XactException
     */
    protected void preValidateXactItem(XactTypeItemActivity _xtia) throws XactException {
	return;
    }

    /**
     * Override this method to execute custom logic after base transaction Item Activity validations.
     * @param _xtia
     * @throws XactException
     */
    protected void postValidateXactItem(XactTypeItemActivity _xtia) throws XactException {
	return;
    }

    public String createCustomerActivity(int customerId, int xactId, double amount) throws XactException {
	String confirmNo = null;
	UserTimestamp ut = null;
	Xact xact = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	CustomerActivity ca = XactFactory.createCustomerActivity(customerId, xactId, amount);

	try {
	    xact = this.findXactById(xactId);
	    if (xact == null) {
		throw new XactException("Problem creating customer activity.  Transaction object invalid");
	    }
	    
	    if (this.request == null) {
                ut = RMT2Date.getUserTimeStamp("auto");
            }
            else {
                ut = RMT2Date.getUserTimeStamp(this.request);    
            }
	    ca.setDateCreated(ut.getDateCreated());
	    ca.setDateUpdated(ut.getDateCreated());
	    ca.setUserId(ut.getLoginId());
	    ca.setIpCreated(ut.getIpAddr());
	    ca.setIpUpdated(ut.getIpAddr());
	    dao.insertRow(ca, true);

	    // Update transaction with confirmation number
	    switch (xact.getXactTypeId()) {
	    case XactConst.XACT_TYPE_CASHPAY:
	    case XactConst.XACT_TYPE_CASHSALES:
		confirmNo = this.getCustomerConfirmationNo();
		if (xact.getTenderId() == 0) {
		    xact.setNull("tenderId");
		}
		if (xact.getXactSubtypeId() == 0) {
		    xact.setNull("xactSubtypeId");
		}
		xact.setConfirmNo(confirmNo);
		xact.addCriteria(Xact.PROP_XACTID, xact.getXactId());
		dao.updateRow(xact);
		break;
	    } // end switch

	    return confirmNo;
	}
	catch (DatabaseException e) {
	    throw new XactException(e);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    public String createCreditorActivity(int _creditorId, int _xactId, double _amount) throws XactException {
	UserTimestamp ut = null;
	Xact xact = null;
	DaoApi dao = DataSourceFactory.createDao(this.connector);
	CreditorActivity ca = XactFactory.createCreditorActivity(_creditorId, _xactId, _amount);

	try {
	    xact = this.findXactById(_xactId);
	    if (xact == null) {
		throw new XactException("Problem creating creditor activity.  Transaction object invalid");
	    }
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    if (xact.getTenderId() == 0) {
		xact.setNull("tenderId");
	    }
	    if (xact.getXactSubtypeId() == 0) {
		xact.setNull("xactSubtypeId");
	    }
	    ca.setDateCreated(ut.getDateCreated());
	    ca.setDateUpdated(ut.getDateCreated());
	    ca.setUserId(ut.getLoginId());
	    ca.setIpCreated(ut.getIpAddr());
	    ca.setIpUpdated(ut.getIpAddr());
	    dao.insertRow(ca, true);

	    return null;
	}
	catch (DatabaseException e) {
	    throw new XactException(e);
	}
	catch (SystemException e) {
	    throw new XactException(e);
	}
    }

    /**
     * Retrieves a transaction confirmation number pertaining to a customer activity event.   Override this method to provide 
     * custom logic to produce a customer confirmation number
     * 
     * @return confirmation number
     */
    protected String getCustomerConfirmationNo() {
	return this.createGenericConfirmNo();
    }

    /**
     * Retrieves a transaction confirmation number pertaining to a customer activity event.   Override this method to provide 
     * custom logic to produce a creditor confirmation number
     * 
     * @return confirmation number
     */
    protected String getCreditorConfirmationNo() {
	return this.createGenericConfirmNo();
    }

    /**
     * Generates a generic confirmation number using the long number representation of a 
     * java.util.Date object containing the current timestamp at the time of invocation
     * 
     * @return confirmation number
     */
    private String createGenericConfirmNo() {
	java.util.Date today = new java.util.Date();
	String confirmNo = String.valueOf(today.getTime());
	return confirmNo;
    }

    /**
     * Reverses a transaction and its detail items.  As a result of this 
     * operation, the original transaction amount is permanently changed 
     * by offsetting the previous transaction.
     * 
     * @param _xact The origianl transaction that is to be reversed.
     * @param __xactItems Transaction items to be reversed.
     * @return New id of the reversed transaction.
     * @throws XactException
     */
    public int reverseXact(Xact xact, List xactItems) throws XactException {
	int rc = 0;
	this.preReverseXact(xact, xactItems);
	this.reverseBaseXact(xact);
	this.reverseXactItems(xactItems);
	xact.setXactId(0);
	rc = this.maintainXact(xact, xactItems);
	xact.setXactId(rc);
	this.postReverseXact(xact, xactItems);
	return rc;
    }

    /**
     * Reverses the base transaction amount.   As a result of this operation, the internal 
     * transaction amount is permanently changed.  The reversal process simply multiplies 
     * a -1 to the base transaction amount which basically will zero out the transaction.
     * <p>
     * <p>
     * <b>Note:</b>
     * <p>
     * The transaction type id of the original transaction is carried over to the reverse 
     * transaction to maintain history.  The reversal transction code is identified as 
     * <i>xactSubType</i>.  
     * 
     * @return {@link Xact} - The reversed base transaction object.
     */
    private void reverseBaseXact(Xact xact) {
	xact.setXactSubtypeId(XactConst.XACT_TYPE_REVERSE);
	xact.setXactAmount(xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER);
	String reason1 = "Reversed Transaction " + xact.getXactId();
	String reason2 = (xact.getReason() == null ? "" : " " + xact.getReason());
	xact.setReason(reason1 + reason2);
	return;
    }

    /**
     * Reverses all items of an existing transaction withou applying changes to the database.   By default each item is expected
     * to be of type {@link XactTypeItemActivity}.    See documentation on method, Object reverseXactItems(Object, int), about 
     * overriding to utilize transaction items of a different data type.
     * 
     * @param __xactItems Transaction items to be reversed.
     * @return ArrayList of reversed transaction items.   By default, each item is expected to be of type {@link XactTypeItemActivity}.
     */
    private List reverseXactItems(List _xactItems) {
	if (_xactItems == null) {
	    return null;
	}
	List newList = new ArrayList();
	Object item = null;
	int total = _xactItems.size();

	for (int ndx = 0; ndx < total; ndx++) {
	    item = this.reverseXactItems(_xactItems.get(ndx));
	    newList.add(item);
	}
	return newList;
    }

    /**
     * Reverses transaction item, _obj, and returns the results to the caller.   As a result of this operation, the internal item's transaction 
     * amount is permanently changed.  The reversal process simply multiplies a -1 to the item's transaction amount which basically 
     * will zero out the item.  By default, the runtime type of each item is expected to be {@link XactTypeItemActivity}.  Override this 
     * method to handle an object type other than {@link XactTypeItemActivity}.
     *   
     * @param xtia is the transaction item object that is to be reversed.
     * @return Object in its reverse state.   By default, _obj is of type {@link XactTypeItemActivity}.
     */
    protected Object reverseXactItems(Object xtia) {
	XactTypeItemActivity newItem = (XactTypeItemActivity) xtia;

	double amount = newItem.getAmount() * XactConst.REVERSE_MULTIPLIER;
	newItem.setAmount(amount);

	// Reset id to make it appear that this is a new entry.
	newItem.setXactTypeItemActvId(0);
	return newItem;
    }

    /**
     * Override this method to perform any transaction reversal logic before _xact is applied to the database.   This method is 
     * invoked from the reverseXact method just after the modification of the transaction amount and reason.
     *  
     * @param _xact The transaction that is being reversed.
     */
    protected void preReverseXact(Xact _xact, List _xactItems) {
	return;
    }

    /**
     * Override this method to perform any transaction reversal logic after _xact is applied to the database.   This method is 
     * invoked from the reverseXact method 
     * 
     * @param _xact The transaction that is being reversed.
     */
    protected void postReverseXact(Xact _xact, List _xactItems) {
	return;
    }

    /**
     * Determines if _xact can modified or adjusted which will generally require a new transaction to be created.  Typical target 
     * transactions would be reversals, cancellations, and returns
     * 
     * @param _xact The transaction that is to be managed
     * @return true indicating it is eligible to be changed, and false indicating change is not allowd.
     * @throws XactException when _xact is invalid or null.
     */
    public boolean isXactModifiable(Xact _xact) throws XactException {
	if (_xact == null) {
	    throw new XactException("Transaction object is null");
	}
	return _xact.getXactSubtypeId() == 0;
    }

    /**
     * This method flags the transaction, _xact, as finalized by setting the transaction sub type property 
     * to XactConst.XACT_TYPE_FINAL.  By setting a transaction as finalized, a transaction cannot be 
     * reversed anymore.
     * 
     * @param _xact Transaction object that is to finalized.
     * @throws XactException 
     *           If transactio id or transaction type id are invalid, or when a database error occurs.
     */
    public void finalizeXact(Xact xact) throws XactException {
	DaoApi dao = DataSourceFactory.createDao(this.connector);

	if (xact.getXactId() <= 0) {
	    throw new XactException("The finalization of transaction failed due to invalid transaction id");
	}
	if (xact.getXactTypeId() <= 0) {
	    throw new XactException("The finalization of transaction failed due to invalid transaction type id");
	}

	if (xact.getTenderId() == 0) {
	    xact.setNull("tenderId");
	}
	xact.setXactSubtypeId(XactConst.XACT_TYPE_FINAL);
	try {
	    xact.addCriteria(Xact.PROP_XACTID, xact.getXactId());
	    dao.updateRow(xact);
	}
	catch (DatabaseException e) {
	    throw new XactException("The finalization of transaction failed due to a database error:  " + e.getMessage());
	}
    }

}

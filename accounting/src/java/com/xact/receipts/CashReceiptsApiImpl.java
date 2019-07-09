package com.xact.receipts;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.api.db.DatabaseException;

import com.bean.Xact;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.xact.XactConst;
import com.xact.XactManagerApiImpl;

import com.util.SystemException;
import com.xact.XactException;

/**
 * Class provides an implementation for managing cash receipts transactions.
 * <p>
 * When a cash receipt is created, the base transaction amount is posted to the xact table as a 
 * positive value, which increases the company's cash status, and the customer activity amount 
 * is posted as a negative value which decreases the value of the customer's account.   Conversely, 
 * when a cash receipt reversed,  the base transaction amount is posted to the xact table as a 
 * negative value, and the customer activity amount is posted as positive value which decreases 
 * and increases the value of the company's revenue and the customer's account, respectively.
 *  
 * @author Roy Terrell
 *
 */
public class CashReceiptsApiImpl extends XactManagerApiImpl implements CashReceiptsApi {
    private static Logger logger;

    /**
     * Default Constructor.
     * 
     * @throws DatabaseException
     * @throws SystemException
     */
    protected CashReceiptsApiImpl() throws DatabaseException, SystemException {
	super();
    }

    /**
     * Constructs a sales order api with a specified database connection bean
     * 
     * @param dbConn
     * @throws DatabaseException
     * @throws SystemException
     */
    public CashReceiptsApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException {
	super(dbConn);
	this.setBaseView("SalesOrderItemsBySalesOrderView");
	this.setBaseClass("com.bean.SalesOrderItemsBySalesOrder");
    }

    /**
     * Constructs a sales order api with the specified database connection bean and servlet request object
     * 
     * @param dbConn {@link DatabaseConnectionBean} object
     * @param _request Servlet request object
     * @throws DatabaseException
     * @throws SystemException
     */
    public CashReceiptsApiImpl(DatabaseConnectionBean dbConn, Request _request) throws DatabaseException, SystemException {
	this(dbConn);
	this.setRequest(_request);
    }

    /* (non-Javadoc)
     * @see com.bean.RMT2Base#init()
     */
    @Override
    public void init() {
	super.init();
	CashReceiptsApiImpl.logger = Logger.getLogger(CashReceiptsApiImpl.class);
	CashReceiptsApiImpl.logger.log(Level.INFO, "Logger Created");
    }

    public void close() {
	this.daoHelper = null;
	super.close();
    }

    /**
     * Creates a customer payment transaction or reverses and existing customer payment 
     * transaction.   If the transaction id that is encapsulated in _xact is 0, then a 
     * new customer payment transaction is created.   Otherwise, an existing customer 
     * payment transaction is reversed.
     */
    public int maintainCustomerPayment(Xact xact, int custId) throws CashReceiptsException {
	int xactId;

	if (xact == null) {
	    this.msg = "Customer Payment object cannot be null";
	    logger.error(this.msg);
	    throw new CashReceiptsException(this.msg);
	    //throw new CashReceiptsException(this.dbo, 702, null);
	}
	//  Customer Id must be a value greater than zero.
	if (custId <= 0) {
	    this.msg = "Sales order must be associated with a customer id which has a value greater than zero"; 
	    logger.error(this.msg);
	    throw new CashReceiptsException(this.msg);
	}

	// Determine if we are creating or reversing the customer payment.
	if (xact.getXactId() <= 0) {
	    xactId = this.createCustomerPayment(xact, custId);
	}
	else {
	    xactId = this.reverseCustomerPayment(xact, custId);
	}
	return xactId;
    }

    /**
     * Creates a customer payment transaction.   As a rule, the transaction amount is posted to the base transaction 
     * table as a positive amount, and the posted as a negative amount to the customer activity table.
     * 
     * @param _xact Source transaction.
     * @param _custId The id of the customer of this transaction.
     * @return The id of the new customer payment transaction.
     * @throws CashReceiptsException
     */
    protected int createCustomerPayment(Xact _xact, int _custId) throws CashReceiptsException {
	int xactId = 0;
	double xactAmount = 0;

	_xact.setXactTypeId(XactConst.XACT_TYPE_CASHPAY);
	try {
	    xactId = this.maintainXact(_xact, null);

	    // Ensure that the customer activity is posted as a negative amount.
	    xactAmount = _xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER;
	    this.createCustomerActivity(_custId, xactId, xactAmount);
	    return xactId;
	}
	catch (XactException e) {
	    throw new CashReceiptsException(e);
	}
    }

    /**
     * Reverses a customer's payment and finalizes the source tranaction
     * 
     * @param _xact Source transaction.
     * @param _custId The id of the customer of this transaction.
     * @return The id of the reversed transaction.
     * @throws CashReceiptsException If customer payment transction is final or a general transction error occurs.
     */
    protected int reverseCustomerPayment(Xact _xact, int _custId) throws CashReceiptsException {
	int xactId = 0;
	double xactAmount = 0;

	try {
	    // Cannot reverse payment transaction that has been finalized
	    if (!this.isXactModifiable(_xact)) {
		msg = "Customer Payment cannot be reversed since it is already finalized";
		CashReceiptsApiImpl.logger.log(Level.ERROR, msg);
		throw new CashReceiptsException(msg);
	    }

	    this.finalizeXact(_xact);
	    xactId = this.reverseXact(_xact, null);

	    // Apply a reversal multiplier on the revised base transaction amount which will be used to offset the customer activity.
	    xactAmount = _xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER;
	    this.createCustomerActivity(_custId, xactId, xactAmount);
	    return xactId;
	}
	catch (XactException e) {
	    throw new CashReceiptsException(e);
	}
    }
    
    /**
     * Performs pre-initialization of the transaction instance.  Basicaly, sets the 
     * transaction date to null before transaction is persisted.
     *  
     * @param xact 
     *          The transaction that is being reversed.
     * @param xactItems
     *          The transaction items that are to be reversed.          
     */
    protected void preReverseXact(Xact xact, List xactItems) {
        super.preReverseXact(xact, xactItems);
        xact.setXactDate(null);
        return;
    }

    /**
     * Prepends customer payment comments with a tag.   If user did not input anything for the transction 
     * reason, then the method is aborted which will allow postValidateXact to catch the error.   the 
     * transaction amount is applied to the xact table as is.
     * 
     * @param _xact The target transaction
     */
    protected void preCreateXact(Xact _xact) {
	super.preCreateXact(_xact);
	if (_xact.getTenderId() == 0) {
	    _xact.setNull("tenderId");
	}
	if (_xact.getReason() == null || _xact.getReason().equals("")) {
	    return;
	}
	// Only modify reason and accept transaction amount as is for actual cash receipts tranasctions.   
	// For reversals, the negative multiplier would have been appliedd prior to the invocation of this method.
	if (_xact.getXactSubtypeId() == 0) {
	    _xact.setReason("Cash Receipt: " + _xact.getReason());
	}
	return;
    }

    /**
     * Ensures that the transaction reason is populated.
     * 
     * @param _xact The transaction object to be validated
     * @throws XactException Validation error occurred.
     */
    protected void postValidateXact(Xact _xact) throws XactException {
	// Ensure that reaso is entered.
	if (_xact.getReason() == null || _xact.getReason().equals("")) {
	    this.msg = "Transaction reason cannot be blank...Save failed"; 
            logger.error(this.msg);
	    throw new XactException(this.msg);
	}
	return;
    }

}

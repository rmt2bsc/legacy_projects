package com.apiimpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.api.CashDisbursementsApi;
import com.api.db.DatabaseException;

import com.bean.Xact;

import com.bean.db.DatabaseConnectionBean;

import com.constants.XactConst;

import com.util.CashDisbursementsException;
import com.util.XactException;
import com.util.SystemException;

/**
 * Api Implementation that manages general cash disbursement and creditor/vendor cash disbursement transactions.
 * A <b>general cash disbursement</b> transaction can be described as the exchange of goods and services for cash at the 
 * time the transaction occurs.   A <b>creditor/vendor cash disbursement</b> is the cash payment of a creditor or vendors's 
 * invoice for goods and services obtained on credit at an earlier time.     General and Creditor/Vendor cash disbursements are 
 * quite similar with the exception that creditor/vendor cash disbursement require the posting of creditor activity entries. 
 *  <p>
 *  <p>
 * When a cash disbursement is created, the base transaction amount is posted to the xact table as a negative value, which 
 * decreases the company's cash statust.   When a cash disbursement is reversed,  the base transaction amount is posted 
 * to the xact table as a positivie value which increases the value of the company's cash status.
 * <p>
 * <p>
 * Creditor/Vendor cash disbursements requires an addtional posting of transaction amounts to reflect creditor activity which offsets 
 * the base transaction amount.   When a cash disbursement is created, the creditor activity amount is posted as a positive value which 
 * increases the value of the creditor's account.   Conversely, when a cash disbursement is reversed, the creditor activity amount is 
 * posted as negative value decreasing the value of the creditor's account.
 * 
 * @author Roy Terrell
 *
 */
public class XactCashDisburseApiImpl extends XactManagerApiImpl implements CashDisbursementsApi {
	
	/**
	* Default Constructor.
	* 
	* @throws DatabaseException
	* @throws SystemException
	*/
	protected XactCashDisburseApiImpl() throws DatabaseException, SystemException   {
		super();
	}
	
	/**
	* Constructor which uses database connection
	* 
	* @param dbConn
	* @throws DatabaseException
	* @throws SystemException
	*/
	public XactCashDisburseApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
		super(dbConn);
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
	public XactCashDisburseApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
		this(dbConn);
		this.request = _request;
	}
	
	
	/**
	 * Creates a general cash disbursement transaction or reverses and existing cash disbursement transaction.   If the transaction 
     * id that is encapsulated in _xact is 0, then a new transaction is created.   Otherwise, an existing transaction is reversed.
     * 
	 * @param _xact Source transaction.
	 * @param _items An ArrayList of random objects.
	 * @return id of the new transaction.
	 * @throws CashDisbursementsException
	 */
	public  int maintainCashDisbursement(Xact _xact, List _items) throws CashDisbursementsException {
	    int      xactId;

		 if (_xact == null) {
			 //TODO: Create message in database
			 throw new CashDisbursementsException("Cash Disbursement object cannot be null");
			 //throw new CashReceiptsException(this.dbo, 702, null);
		 }
		
		 // Determine if we are creating or reversing the cash disbursement
		if (_xact.getId() <= 0) {
			_xact.setXactTypeId(XactConst.XACT_TYPE_CASHDISBEXP);
			xactId = this.createCashDisbursement(_xact, _items);
		}
		else {
			xactId = this.reverseCashDisbursement(_xact, _items);
		}
		return xactId;
	}
	
	/**
	 * Creates a creditor related cash disbursement transaction or reverses and existing one.   If the transaction 
     * id that is encapsulated in _xact is 0, then a new transaction is created.   Otherwise, an existing 
     * transaction is reversed.   The customer activity is always posted as an offset to the base transaction amount.
     * 
	 * @param _xact Source transaction.
	 * @param _items An ArrayList of random objects.
	 * @param _creditorId  The Id of the creditor.
	 * @return id of the new transaction.
	 * @throws CashDisbursementsException
	 */
	public  int maintainCashDisbursement(Xact _xact, List _items, int _creditorId) throws CashDisbursementsException {
	    int      xactId;
	    double xactAmount = 0;

		 if (_xact == null) {
			 //TODO: Create message in database
			 throw new CashDisbursementsException("Cash Disbursement object cannot be null");
			 //throw new CashDisbursementsException(this.dbo, 702, null);
		 }
		
		 // Determine if we are creating or reversing the cash disbursement
		if (_xact.getId() <= 0) {
			_xact.setXactTypeId(XactConst.XACT_TYPE_CASHDISBACCT);
			xactId = this.createCashDisbursement(_xact, _items);
		}
		else {
			xactId = this.reverseCashDisbursement(_xact, _items);
		}

		// At this point a transaction was successfully created, and we need to reflect that tranaction in the creditor's activity table.
		// Since the creditor activity amount will always post as an offset to the base transaction amount, take the revised base 
		// transaction amount an reverse it.
		try {
			xactAmount = _xact.getXactAmount();  // * XactConst.REVERSE_MULTIPLIER;
			this.createCreditorActivity(_creditorId, xactId, xactAmount);
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
				System.out.println("[XactCashDisburseApiImpl.reverseCashDisbursement] " + msg);
				throw new CashDisbursementsException(msg);
			} 
		  
			this.finalizeXact(_xact);
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
          _xact.setXactDate(null);
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
		String method = "postValidateXact";
		java.util.Date today = new java.util.Date();
		
	    // Verify that transaction date has a value.
	      if (_xact.getXactDate() == null) {
	          throw new XactException(this.connector, 619, null);
	      }
	      // Verify that the transacton date value is valid
	      if (_xact.getXactDate().getTime() > today.getTime()) {
	          throw new XactException(this.connector, 621, null);
	      }
	      
	      // Verify that transaction tender has a value and is valid.
	      if (_xact.getTenderId() <= 0) {
	          throw new XactException(this.connector, 622, null);
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
	                  throw new XactException(this.connector, 623, null);
	              }
	      }
	      
	      // Ensure that reaso is entered.
	      if (_xact.getReason() == null || _xact.getReason().equals("")) {
	    	  //TODO: Add this message to the database
	    	  // throw new XactException(this.dbo, 623, null);
	          throw new XactException("Transaction reason cannot be blank...Save failed.", -1, this.className, method);
	      }
		return;
	}
	
}

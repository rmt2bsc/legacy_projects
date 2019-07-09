package com.apiimpl;



import javax.servlet.http.HttpServletRequest;

import com.api.CashReceiptsApi;
import com.api.db.DatabaseException;

import com.bean.Xact;

import com.bean.db.DatabaseConnectionBean;

import com.constants.XactConst;

import com.util.SystemException;
import com.util.XactException;
import com.util.CashReceiptsException;;

/**
 * Class provides an implementation for managing cash receipts transactions.
 * <p>
 * <p>
 * When a cash receipt is created, the base transaction amount is posted to the xact table as a positive value, which 
 * increases the company's cash status, and the customer activity amount is posted as a negative value which decreases 
 * the value of the customer's account.   Conversely, when a cash receipt reversed,  the base transaction amount is posted 
 * to the xact table as a negative value, and the customer activity amount is posted as positive value which decreases and increases the 
 * value of the company's revenue and the customer's account, respectively.
 *  
 * @author Roy Terrell
 *
 */
public class CashReceiptsApiImpl extends XactManagerApiImpl implements CashReceiptsApi  {

      
   /**
    * Default Constructor.
    * 
    * @throws DatabaseException
    * @throws SystemException
    */
   protected CashReceiptsApiImpl() throws DatabaseException, SystemException   {
	   super();  
   }


   /**
    * Constructs a sales order api with a specified database connection bean
    * 
    * @param dbConn
    * @throws DatabaseException
    * @throws SystemException
    */
    public CashReceiptsApiImpl(DatabaseConnectionBean dbConn) throws DatabaseException, SystemException   {
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
    public CashReceiptsApiImpl(DatabaseConnectionBean dbConn, HttpServletRequest _request) throws DatabaseException, SystemException   {
        super(dbConn);
        this.setRequest(_request);

    }

    /**
     * Creates a customer payment transaction or reverses and existing customer payment transaction.   If the transaction 
     * id that is encapsulated in _xact is 0, then a new customer payment transaction is created.   Otherwise, an existing 
     * customer payment transaction is reversed.
     */
   public int maintainCustomerPayment(Xact _xact, int _custId)  throws CashReceiptsException {
	    int      xactId;

		 if (_xact == null) {
			 //TODO: Create message in database
			 throw new CashReceiptsException("Customer Payment object cannot be null");
			 //throw new CashReceiptsException(this.dbo, 702, null);
		 }
		 //  Customer Id must be a value greater than zero.
			 if (_custId <= 0) {
			     throw new CashReceiptsException(this.connector, 701, null);
			}
		
		 // Determine if we are creating or reversing the customer payment.
		if (_xact.getId() <= 0) {
			xactId = this.createCustomerPayment(_xact, _custId);
		}
		else {
			xactId = this.reverseCustomerPayment(_xact, _custId);
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
		      System.out.println("[CashReceiptsApiImpl.reverseCustomerPayment] " + msg);
		      throw new CashReceiptsException(msg);
          } 
		  
		  this.finalizeXact(_xact);
		  xactId = this.reverseXact(_xact, null);
		  
		  // Apply a reversal multiplier on the revised base transaction amount which will be used to offset the customer activity.
		  xactAmount =  _xact.getXactAmount() * XactConst.REVERSE_MULTIPLIER;
		  this.createCustomerActivity(_custId, xactId, xactAmount);
		  return xactId;
	  }
	  catch (XactException e) {
		  throw new CashReceiptsException(e);
	  }
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
          _xact.setReason("Customer Payment: " + _xact.getReason());
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
          throw new XactException("Transaction reason cannot be blank...Save failed.", -1, this.className, "postValidateXact");
      }
	  return;
  }

  
}

package com.action;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.bean.Creditor;
import com.bean.VwCreditorBusiness;
import com.bean.VwCreditorXactHist;
import com.bean.VwXactList;
import com.bean.Xact;

import com.api.GLCreditorApi;
import com.api.CashDisbursementsApi;
import com.api.XactManagerApi;

import com.constants.XactConst;

import com.factory.AcctManagerFactory;
import com.factory.CashDisburseFactory;
import com.factory.XactFactory;

import com.util.CashDisbursementsException;
import com.util.CreditorException;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.XactException;
import com.util.SalesOrderException;
import com.util.GLAcctException;


/**
 * Concrete class that manages cash disbursement transactions towards creditor accounts only.   The functionality of this 
 * class is to serve the client's request to manage creditor cash disbursements transactions. 
 * 
 * @author Roy Terrell
 *
 */
public class XactCashDisburseCreditorAction extends AbstractXactJournalEntryAction {
    
    protected GLCreditorApi  credApi;
//	protected XactManagerApi xactApi;
	private CashDisbursementsApi disbApi;
	
	/**
	 * Default constructor.
	 *
	 */
	 public XactCashDisburseCreditorAction  () {
	     super();
	 }  

	  /**
		* Main contructor for this action handler.
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	   public XactCashDisburseCreditorAction (ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
	       super(_context, _request);
		   this.credApi = AcctManagerFactory.createCreditor(this.dbConn, _request);
		   this.disbApi = CashDisburseFactory.createApi(this.dbConn, this.request);
	   }
	
	  /**
	   * This method is used to initialize this object.   It is mandatory to invoke this 
	   * method after instantiating this object via the default constructor.
	   * 
	   * @throws SystemException  
	   */
	  public void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
	      super.init(_context, _request);
	  }
	  
	  	  
	  /**
	   * Retrieves a list of transactions pertaining to a customer's account.
	   *  
	   * @throws XactException
	   */
	  public void getJournalEntryList() throws XactException {
	      VwCreditorBusiness cred = null;
		   try {
			   // Get Creditor header information and store results in the the request object for the client.
			   cred = this.setupCreditorHeaderData();
			  
			   // Get Creditor's account transactions  and store results in the the request object for the client.
			   this.getCreditorHist(cred.getCreditorId());
		   }
		   catch (SalesOrderException e ) {
		       throw new XactException(e);
		   }
		   finally {
		       this.request.setAttribute("creditor", cred);
		   }
	  }
	  
	  /**
	   * Retrieves a creditor's transaction history using _cust.
	   * 
	   * @param _cred Creditor object
	   * @return An array of all the customer's transactions
	   * @throws SalesOrderException
	   */
	  private VwCreditorXactHist[] getCreditorHist(int _credId) throws SalesOrderException {
	      VwCreditorXactHist cxh[] = null;
		  ArrayList list = null;
		  try {
			  list = this.api.findCreditorXactHist(_credId);  
			  cxh = new VwCreditorXactHist[list.size()];
			  cxh = (VwCreditorXactHist[]) list.toArray(cxh);
			  return cxh;
		  }
		  catch (XactException e) {
			  System.out.println("[XactCashDisbursementsAction.getPaymentHist] XactException occurred. " + e.getMessage());
		      throw new SalesOrderException(e);
		  }
		  finally {
			  this.request.setAttribute("XactHist", list);
		  }
	  }
	  
	  

	  /**
	   * This method is responsible for gathering creditor data that is to be used as header information 
	   * when displaying cash receipts related pages for the client.
	   * <p>
	   * In order to use this method, the client is required to provide the creditor id identified as "Id" or "CreditorId".   Otherwise, an
	   * SalesOrderException is thrown.    The processed data will be returned to the client via the HttpServletRequest object and 
	   * can be identified as follows:
	   * <p>
	   * <blockquote>
	   * "customer" - {@link VwCreditorBusiness}
	   * "balance" - Double object representing the customer's current balance.
	   * </blockquote>
	   * 
	   * @return {@link VwCreditorBusiness}
	   * @throws SalesOrderException
	   */
	  protected VwCreditorBusiness setupCreditorHeaderData() throws SalesOrderException {
		  String credIdStr = null;
		  String msg = null;
		  String criteria = null;
		  int credId = 0;
		  Creditor cred = null;
		  VwCreditorBusiness cb = null;
		  ArrayList list = null;
		  
		  try {
			  // Obtain Creditor data from the request object.
			  try {
			      credIdStr = this.request.getParameter("Id");
				  if (credIdStr == null) {
				      credIdStr = this.request.getParameter("CreditorId");
				  }
				  credId = Integer.parseInt(credIdStr);  				  
			  }
			  catch (NumberFormatException e) {
				  msg = "NumberFormatException - Problem converting creditor id to a number:  " + credIdStr;
				  System.out.println("[XactCashDisbursementsAction.setupCreditorHeaderData] " + msg);
			      throw new SalesOrderException(e);
			  }
			    
			  cred = this.credApi.createCreditor();
			  cred.setId(credId);
			  criteria = "creditor_id = " + credId;
			  System.out.println("Criteria to obtain creditor profile: " + criteria);
			  list = credApi.findCreditorBusiness(criteria);
			  if (list.size() > 0) {
			      cb = (VwCreditorBusiness) list.get(0);
			  }
			  
			  if (cb == null) {
			      System.out.println("Creditor object is null");
			      return null;
			  }
			  
			  
			  this.request.setAttribute("creditor", cb);
			  return cb;
		  } // end try
		  catch (GLAcctException e) {
		      System.out.println("[XactCashDisbursementsAction.setupCreditorHeaderData] GLAcctException occurred. " + e.getMessage());
		      throw new SalesOrderException(e);
		  }
		  catch (SystemException e) {
			  System.out.println("[XactCashDisbursementsAction.setupCreditorHeaderData] SystemException occurred. " + e.getMessage());
			  throw new SalesOrderException(e);
		  }
	  }
	  
	  

	  /**
	   * Obtins the creditor balance.
	   * 
	   * @param _id The creditor Id
	   * @return Double - The creditor's current balance.
	   */
	  protected Double getCreditorBalance(int _id) {
	    	// Get Creditor Balance
	    	Double balance = null;
	    	try {
	    	   balance = new Double(this.credApi.findCreditorBalance(_id));    
	    	}
	    	catch (CreditorException e) {
	    	    System.out.println("[XactCashReceiptsAction.getCreditorBalance] Creditor balance could not be obtained...defaulting to zero.");
	    	    balance = new Double(0); 
	    	}
	    	return balance;
	  }  

	  /**
	   * Not applicable for this class. 
	   * 
	   * @throws XactException
	   */
	  public void getAvailJournalEntryItems() throws XactException {
		  return;
	  }
	
	  
	  /**
	   * This method is N/A
	   * 
	   * @throws XactException
	   */
	  public int getJournalEntryDetails() throws XactException {
	      return 1;
 	  }


	  /**
	   * Validates a cash receipt transaction.
	   */
	  public void validateJournalEntryDetails() throws XactException {
	      
	  }
	  
	  /**
	   * This method services the client's request to display the Customer Payment on Account, Sales Refund, or the
	   * Sales Transaction Reversal page for the client based on the client's submit action code (identified as "clientAction" 
	   * in the HttpServletReuest object).
	   * <p>.
	   * A transaction type of customer payment, sales returns (refund), or transaction reversal will be determined
	   * and set to the target JSP page that is to be presented on the client.   Since the target JSP is capable 
	   * processing all of the above transaction types, this helps the server-side process to  identify the type of 
	   * transaction it is to handle after the client submits the target JSP.  
	   * <p>
	   * Once the transaction type is determined, it is stored in and transimitted to the client via the HttpServletRequest 
	   * object as a String and can be identified as, "XactTypeId".
	   * 
	   * @throws XactException if the transactio type cannot be determined.
	   */
	   public void addXact() throws XactException {
	       int requestedXactTypeId = 0;
	       int xactId = 0;
	       Xact xact = null;
	       int newXactId = 0;
	       String msg = null;
	       VwCreditorBusiness cred = null;
	       
 		    try {
 		        // Get Creditor data
 		        cred = this.setupCreditorHeaderData();
 		        
 		        // Get Transaction data
 		        xactId = this.getXactIdFromClient();
 		        xact = this.api.findXactById(xactId);
 		        
 		        // Determine the requested transaction type that is to be assoiciate the target page.
 		        if (this.clientAction.equalsIgnoreCase(XactConst.CD_CREDITORPAYMENT)) {
 		            requestedXactTypeId = XactConst.XACT_TYPE_CASHDISBACCT;
 		        }
 		        if (this.clientAction.equalsIgnoreCase(XactConst.CD_ADDREVERSE)) {
 		            requestedXactTypeId = XactConst.XACT_TYPE_REVERSE;
 		        }
				   
 		        // Throw exception if transaction type could not be figured out.
 		        if (requestedXactTypeId == 0) {
 		            msg = "Problem setting the transaction type for creditor/vendor cash disbursement transaction since the client's submit action could not be identified";
 		            System.out.println("[XactCashDisbursementsAction.addXact] " + msg);
 		            throw new XactException(msg);
 		        }
				  
 		        // Set the transaction type in the request object.
 		        this.request.setAttribute("XactTypeId", String.valueOf(requestedXactTypeId));
 		        this.request.setAttribute("xact", xact);
 		        return;
 		    }
			 catch (SalesOrderException e) {
			     System.out.println("[XactCashDisbursementsAction.addXact] SalesOrderException occurred. " + e.getMessage()); 
			     throw new XactException(e);
			 }
	   }
	   

	     /**
	      * This method is responsible for setting the confirmation number of the transaction and negating the transaction amount
	      * @deprecated
	      */
       protected void preSaveXact() {
           double amount = 0;
           String temp = null;
           
           if (this.xactBean == null) {
               System.out.println("[XactCashDisbursementsAction.preSaveXact] transaction bean is null");
               return;
           }
           if (this.xactTypeBean == null) {
               System.out.println("[XactCashDisbursementsAction.preSaveXact] transaction type bean is null");
               return;
           }
           
           // Apply the proper sign to transaction amount
           amount = this.xactBean.getXactAmount();
           amount *= -1;
           this.xactBean.setXactAmount(amount);      
           
           // Set Confirmation number.   Confirmation number will be a value from an external source such 
           // as the creditor/vendor's payment website or it will be null if direct  cash payment.
           String confirmNo =  this.request.getParameter("ConfirmNo");
           this.xactBean.setConfirmNo(confirmNo);
           
           // Prepend special tag in the description o f the transaction to denote reversal.
           if (this.xactTypeBean.getId() == XactConst.XACT_TYPE_REVERSE) {
               temp = this.xactBean.getReason();
               temp = "Reversed Transaction " + this.xactBean.getId() + ": " + this.xactBean.getReason();
               this.xactBean.setReason(temp);
           }
           return;
       }
       
       
       
	  /**
	   * Creates creditor/vendor payment transaction journal entries.    This method will perform one of the following transaction based on value 
	   * of the client's "requestType":  payments towards creditor/vendor accounts, creditor payment reversal, and purchases 
       * returns and allowances. 
	   * <p>
       * Since this class is designed for a specific transaction type, the processing of creditor/vendor cash disbursments, creditor 
       * cash disbursement reversal, and purchases returns and allowances are managed via this method.
       * 
	   * @return int - the transaction id. 
	   * @throws XactException
	   * @throws DatabaseException
	   */
		public int saveXact() throws XactException, DatabaseException {
			String msg = null;
			int xactId =0;
		    Xact xact = null;
		    ArrayList items = null;
		    VwCreditorBusiness cred = null;

		   // Get Creditor Id
		   try {
	       cred = this.setupCreditorHeaderData();    
		   }
		   catch (SalesOrderException e) {
			    throw new XactException(e);
		   }
		   xact = this.getHttpXactBase();
		   items = this.getHttpXactItems();
			
		   // Create base transaction
		   System.out.println("********  Begin Saving Creditor/Vendor Cash Disbursement transaction  *********");
		   try {
			   xactId = this.disbApi.maintainCashDisbursement(xact, items, cred.getCreditorId());
			   xact = this.api.findXactById(xactId);
			     
			   this.request.setAttribute("xact", xact);
               this.request.setAttribute("creditor", cred);
               
               this.transObj.commitTrans();
               System.out.println("********  Saving Creditor/Vendor Cash Disbursement transaction  successful  *********");
			   return xactId;
		   }
		  catch (DatabaseException e) {
			  msg = e.getMessage();
			  System.out.println("[XactCashDisbursementsAction.saveXact] DatabaseException -  " + msg);
			  this.transObj.rollbackTrans();  
			  throw new XactException(msg);  
		  }
		   catch (CashDisbursementsException e) {
				this.transObj.rollbackTrans();
		  	    System.out.println("CashDisbursementsException: " + e.getMessage());
		  	    throw new XactException(e);
			}
		}
		
	   
		
	   /**
		 * Reverses creditor/vendor cash disbursement transaction. 
		 *  
		 * @return int - the transaction id. 
		 * @throws XactException
		 * @throws DatabaseException
		 */
		  public int reverseXact() throws XactException, DatabaseException {
			  String msg = null;
			  int xactId =0;
			  Xact xact = null;
			  ArrayList items = null;
			  VwCreditorBusiness cred = null;
               
               // Get Data from client
               try {
               cred = this.setupCreditorHeaderData();    
               }
               catch (SalesOrderException e) {
                    throw new XactException(e);
               }
               xact = this.getHttpXactBase();
               items = this.api.findXactTypeItemsActivityByXactId(xact.getId());
               
               try {
            	   System.out.println("********  Begin Reversing Creditor/Vendor Cash Disbursement transaction  *********");
            	   xactId = this.disbApi.maintainCashDisbursement(xact, items, cred.getCreditorId());
                   xact = this.api.findXactById(xactId);
                   this.request.setAttribute("xact", xact);
                   this.request.setAttribute("creditor", cred);
                   this.transObj.commitTrans();
                   System.out.println("********  Reversing Creditor/Vendor Cash Disbursement transaction successful  *********");
                   return xactId;
               }
               catch (CashDisbursementsException e) {
                   msg = e.getMessage();
                   System.out.println("[XactCashDisbursementsAction.reverseXact] DatabaseException -  " + msg);
                   this.transObj.rollbackTrans();
                   throw new XactException(msg);
               }
              catch (DatabaseException e) {
                  msg = e.getMessage();
                  System.out.println("[XactCashDisbursementsAction.reverseXact] DatabaseException -  " + msg);
                  this.transObj.rollbackTrans();  
                  throw new XactException(msg);  
              }
		  }
	 
/*		  
		  public int saveXact() throws XactException, DatabaseException {
			   String msg = null;
			   int xactId =0;
			   VwCreditorBusiness cred = null;

			   // Get Creditor Id
			   try {
  		       cred = this.setupCreditorHeaderData();    
			   }
			   catch (SalesOrderException e) {
				    throw new XactException(e);
			   }

			   // Create base transaction
			   System.out.println("********  Begin Saving Creditor/Vendor Cash Disbursement transaction  *********");
			   xactId = super.saveXact();
			   System.out.println("Transaction Amount Saved: " + this.xactBean.getXactAmount());
			   
			   // Ancestor should have determined the type transaction we are dealing with here.  If not, throw an exception.
			   if (this.xactTypeBean == null) {
				   msg = "Transaction Type could not be determined from ancestor script";
				   System.out.println("[XactCashDisbursementsAction.saveXact] SalesOrderException -  " + msg); 
			      throw new XactException(msg);
			   }
			   
			   // Call the appropriate transaction handler.
			   switch (this.xactTypeBean.getId()) {
			   case XactConst.XACT_TYPE_CASHSALES:
				    break;
			   case XactConst.XACT_TYPE_CASHDISBACCT:
			   case XactConst.XACT_TYPE_REVERSE:
			        this.debitAccount(cred.getCreditorId(), xactId);
				    break;

			   default:
				    msg = "The wrong Transaction Type was supplied to the cash disbursement process";
			       System.out.println("[XactCashDisbursementsAction.addXact] SalesOrderException -  " + msg); 
		          throw new XactException(msg);
			   } // end switch
		      
			   try {
			 	  
			 	   // Get transaction data
			 	   Xact xact = this.xactApi.findXactById(xactId);
				   this.transObj.commitTrans();
				   
				   this.request.setAttribute("xact", xact);
                  this.request.setAttribute("creditor", cred);
                  System.out.println("********  Saving Creditor/Vendor Cash Disbursement transaction  successful  *********");
				   return xactId;
			   }
			  catch (DatabaseException e) {
				  msg = e.getMessage();
				  System.out.println("[XactCashDisbursementsAction.saveXact] DatabaseException -  " + msg);
				  try {
					  this.transObj.rollbackTrans();  
				  }
				  catch (DatabaseException ee) {
					  msg += "  :  " + ee.getMessage();
					  System.out.println("[XactCashDisbursementsAction.saveXact] DatabaseException -  " + ee.getMessage());
				  }
				  throw new XactException(msg);  
			  }
		}
		
			  public int reverseXact() throws XactException, DatabaseException {
               String msg = null;
               int xactId =0;
               VwCreditorBusiness cred = null;

               System.out.println("********  Begin Reversing Creditor/Vendor Cash Disbursement transaction  *********");
               
               // Get Creditor Id
               try {
               cred = this.setupCreditorHeaderData();    
               }
               catch (SalesOrderException e) {
                    throw new XactException(e);
               }

               // Create base transaction
               xactId = super.reverseXact();
               System.out.println("Transaction Amount Reversed: " + this.xactBean.getXactAmount());
               
               // Ancestor should have determined the type transaction we are dealing with here.  If not, throw an exception.
               if (this.xactTypeBean == null) {
                   msg = "Transaction Type could not be determined from ancestor script";
                   System.out.println("[XactCashDisbursementsAction.reverseXact] SalesOrderException -  " + msg); 
                  throw new XactException(msg);
               }
               
               this.debitAccount(cred.getCreditorId(), xactId);
              
               try {
                   // Get transaction data
                   Xact xact = this.xactApi.findXactById(xactId);
                   this.transObj.commitTrans();
                   
                   this.request.setAttribute("xact", xact);
                   this.request.setAttribute("creditor", cred);
                   System.out.println("********  Reversing Creditor/Vendor Cash Disbursement transaction successful  *********");
                   return xactId;
               }
              catch (DatabaseException e) {
                  msg = e.getMessage();
                  System.out.println("[XactCashDisbursementsAction.reverseXact] DatabaseException -  " + msg);
                  try {
                      this.transObj.rollbackTrans();  
                  }
                  catch (DatabaseException ee) {
                      msg += "  :  " + ee.getMessage();
                      System.out.println("[XactCashDisbursementsAction.reverseXact] DatabaseException -  " + ee.getMessage());
                  }
                  throw new XactException(msg);  
              }
		  }
*/

			/**
			 * Invokes transaction api method to create creditor activity.
			 * 
			 * @param _credId
			 * @param _xactId
			 * @return int
			 * @throws XactException
			 * @deprecated
			 */
		   protected int debitAccount(int _credId, int _xactId) throws XactException {
	           this.api.createCreditorActivity(_credId, _xactId, this.xactBean.getXactAmount());
			   return 1;
		   }

		  /**
           * Performs special data manipulation on the transaction just prior to being reversed.
           */
          protected void preReverseXact() {
              this.xactBean.setReason("Reversed Transaction:  " + this.xactBean.getId());
          }
          
          
		  
	} // end AbstractXactJournalEntryAction
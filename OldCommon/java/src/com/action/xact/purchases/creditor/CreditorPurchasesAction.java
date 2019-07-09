package com.action.xact.purchases.creditor;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.action.ActionHandlerException;
import com.action.xact.AbstractXactAction;

import com.api.GLCreditorApi;
import com.api.CreditChargesApi;
import com.api.db.DatabaseException;

import com.bean.PurchaseOrder;
import com.bean.PurchaseOrderItems;
import com.bean.PurchaseOrderStatus;
import com.bean.PurchaseOrderStatusHist;
import com.bean.VwCreditorBusiness;

import com.constants.CreditChargesConst;
import com.constants.RMT2ServletConst;

import com.factory.AcctManagerFactory;
import com.factory.CreditChargesFactory;

import com.util.SystemException;

import com.action.xact.purchases.HttpCreditorPurchaseHelper;



/**
 * Class that manages transactions and their related General Ledger accounts
 *
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesAction extends AbstractXactAction {
	private Logger logger;
	protected HttpCreditorPurchaseHelper httpXactHelper;
	protected GLCreditorApi credApi;
	protected CreditChargesApi ccApi;
	protected Hashtable errors = new Hashtable();
	

  /**
   * Default constructor
   * 
   * @throws SystemException
   */
  public CreditorPurchasesAction() throws SystemException {
	  logger = Logger.getLogger("CreditorPurchasesAction");
  }
  
  /**
	* Main contructor for this action handler.
	*
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public CreditorPurchasesAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
	  super(_context, _request);
	  this.className = "CreditChargesAction";
	 
  }


  
  /**
   * Initializes this object using _conext and _request.  This is needed in the 
   * event this object is inistantiated using the default constructor.
   * 
   * @throws SystemException
   */
  protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
      super.init(_context, _request);
      try {
    	  this.credApi = AcctManagerFactory.createCreditor(this.dbConn, _request);
    	  this.ccApi = CreditChargesFactory.createApi(this.dbConn, _request);
          this.httpXactHelper = new HttpCreditorPurchaseHelper(_context, _request);
          this.httpXactHelper.setSelectedRow(this.selectedRow);
      }
      catch (Exception e) {
    	  logger.log(Level.ERROR, e.getMessage());
    	  throw new SystemException(e.getMessage());
      }
  }
  
  /**
   * Processes the client's request using request, response, and command.
   *
   * @param request   The HttpRequest object
   * @param response  The HttpResponse object
   * @param command  Comand issued by the client.
   * @Throws SystemException when an error needs to be reported.
   */
  public void processRequest( HttpServletRequest request, HttpServletResponse response, String command) throws ActionHandlerException {
	  super.processRequest(request, response, command);
  }

  
  /**
   * Retrieves creditor/vendor purchase order data from the client's request.
   * 
   * @throws ActionHandlerException
   */
  protected void receiveClientData() throws ActionHandlerException {
	  //super.receiveClientData();
	  try {
		  this.httpXactHelper.getHttpCombined();
	  }
	  catch (Exception e) {
		  logger.log(Level.ERROR, this.msg);
		  throw new ActionHandlerException(this.msg);
	  }
  }
  
  
  
  /**
   * Set all pertinent purchase order data onto the request object which 
   * is transmitted to the client.
   * 
   * <p>
   * <p>
   * The following list dipicts the java objects and their names that are 
   * stored in the HttpServletRequest object on the client:
   * <table border="1">
   *   <tr>
   *     <th align="left"><strong>Java Data Object</strong></th>
   *     <th><strong>Id on client</strong></th>
   *   </tr>
   *   <tr>
   *     <td>{@link PurchaseOrder}</td>
   *     <td>po</td>
   *   </tr>
   *   <tr>
   *     <td>ArrayList of {@link PurchaseOrderItems}</td>
   *     <td>poItems</td>
   *   </tr>
   *   <tr>
   *     <td>{@link PurchaseOrderStatus}</td>
   *     <td>pos</td>
   *   </tr>
   *   <tr>
   *     <td>{@link PurchaseOrderStatusHist}</td>
   *     <td>posh</td>
   *   </tr>
   *   <tr>
   *     <td>{@link VwCreditorBusiness}</td>
   *     <td>vendor</td>
   *   </tr>
   * </table>
   * 
   * @throws ActionHandlerException
   */
  protected void sendClientData() throws ActionHandlerException {
	  //super.sendClientData();
      this.request.setAttribute(CreditChargesConst.CLIENT_DATA_CREDITOR, this.httpXactHelper.getCreditor());
	  this.request.setAttribute(CreditChargesConst.CLIENT_DATA_XACT, this.httpXactHelper.getXact());
	  this.request.setAttribute(CreditChargesConst.CLIENT_DATA_XACTEXT, this.httpXactHelper.getXactExt());
      this.request.setAttribute(CreditChargesConst.CLIENT_DATA_XACTTYPE, this.httpXactHelper.getXactType());
      this.request.setAttribute(CreditChargesConst.CLIENT_DATA_XACTCATG, this.httpXactHelper.getXactCategory());
      this.request.setAttribute(CreditChargesConst.CLIENT_DATA_XACTITEMS, this.httpXactHelper.getXactItems());
      this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
  }
  
}
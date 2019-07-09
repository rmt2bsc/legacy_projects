package com.action.xact.purchases.creditor;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.VwCreditorBusiness;
import com.bean.RMT2TagQueryBean;
import com.bean.VwXactList;
import com.bean.criteria.CreditChargeCriteria;

import com.constants.CreditChargesConst;
import com.constants.RMT2ServletConst;
import com.constants.XactConst;
import com.constants.AccountingConst;

import com.util.NotFoundException;
import com.util.SystemException;





/**
 * This class provides action handlers to respond to an associated 
 * controller for searching, adding, deleting, and validating 
 * Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class CreditorPurchasesSearchAction extends CreditorPurchasesAction {
    private static final String COMMAND_NEWSEARCH = "XactPurchase.CreditorSearch.newsearch";
    private static final String COMMAND_SEARCH = "XactPurchase.CreditorSearch.search";
    private static final String COMMAND_LIST = "XactPurchase.CreditorSearch.list";
    private static final String COMMAND_EDIT = "XactPurchase.CreditorSearch.edit";
    private static final String COMMAND_ADD = "XactPurchase.CreditorSearch.add";
    
    private Logger logger;
    private List xactList;

    /**
     * Default constructor
     *
     */
    public CreditorPurchasesSearchAction() throws SystemException {
        super(); 
        logger = Logger.getLogger("CreditorPurchasesSearchAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public CreditorPurchasesSearchAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
    super(_context, _request);
    this.init(this.context, this.request);
  
  }
  

  /**
   * Initializes this object using _conext and _request.  This is needed in the 
   * event this object is inistantiated using the default constructor.
   * 
   * @throws SystemException
   */
  protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
      super.init(_context, _request);
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
      if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_NEWSEARCH)) {
          this.doNewSearch();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_SEARCH)) {
          this.doSearch();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_LIST)) {
          this.doList();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_ADD)) {
          this.addData();
      }
      if (command.equalsIgnoreCase(CreditorPurchasesSearchAction.COMMAND_EDIT)) {
          this.editData();
      }
  }
  
  
  /**
   * Handler method that responds to the client's request to display the Vendor Purchases Maintenance 
   * Console for the first time which inclues an empty result set.
   * 
   * @throws ActionHandlerException
   */
  protected void doNewSearch() throws ActionHandlerException {
      this.setFirstTime(true);
      this.startSearchConsole();
      this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
      this.xactList = new ArrayList();
      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
      this.sendClientData();
  }
  
  
  /**
   * Drives the process of building selection criteria using the client's HTTP request 
   * and storing the criteria onto the session object for later use. 
   * 
   * @throws ActionHandlerException
   */
  protected void doSearch() throws ActionHandlerException {
      this.setFirstTime(false);
      this.buildSearchCriteria();
      this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
      this.query.setQuerySource(this.getBaseView());
      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
  }
  
   
  /**
   * This method is responsible for gathering the user's input of credit charge 
   * selection criteria data from the request object.
   * 
   * @return Object which represents the custom object that is a member of 
   * {@link RMT2TagQueryBean}. 
   * @throws ActionHandlerException
   */
  protected Object doCustomInitialization() throws ActionHandlerException {
      this.setBaseView("VwXactCreditChargeListView");
      CreditChargeCriteria criteriaObj = CreditChargeCriteria.getInstance();
      if (!this.isFirstTime()) {
          try {
              DataSourceAdapter.packageBean(this.request, criteriaObj);    
          }
          catch (SystemException e) {
        	  this.msg = "Problem gathering Item Master request parameters.";
              logger.log(Level.ERROR, this.msg);
              throw new ActionHandlerException(this.msg); 
          }    
      }
      return criteriaObj;
  }

  
  /**
   * Applies additional search criteria to _query before the _query is updated on 
   * the session object.   In addition to adding criteria, the wait page flag is 
   * set to true to indicate that the "Wait Please..." page has been displayed.
   * <p>
   * <p>
   * This method ensures that the following condition(s) are included in the 
   * selection criteria that will be used to retrieve credit charge transactions: 
   * <ol>
   *    <li>Each transaction must be associated with a creditor type of {@link AccountingConst.CREDITOR_TYPE_CREDITOR}</li>
   *    <li>The transaction type must be equal to {@link XactConst.XACT_TYPE_CREDITCHARGE}.</li>
   * </ol>
   *  
   * @param _query {@link RMT2TagQueryBean} object.
   * @param __searchMode Set to either {@link RMT2ServletConst.SEARCH_MODE_NEW} or {@link RMT2ServletConst.SEARCH_MODE_OLD}. 
   * @throws ActionHandlerException
   */
  protected void doPostCustomInitialization(RMT2TagQueryBean _query, int _searchMode) throws ActionHandlerException {
      String sql = null;
      String annex = null;
      
      if (_searchMode == RMT2ServletConst.SEARCH_MODE_NEW) {
          sql = _query.getWhereClause();
          if (sql != null) {
              annex = "creditor_type_id =  " +  AccountingConst.CREDITOR_TYPE_CREDITOR + " and xact_type_id in ( " + XactConst.XACT_TYPE_CREDITCHARGE + ")";
              if (sql.length() > 0) {
                  sql += " and " + annex; 
              }
              else {
                  sql = annex;
              }
              _query.setWhereClause(sql);
          }
          _query.setOrderByClause( " xact_id desc ");
          //_query.setOrderByClause( " xact_date desc, xact_id desc ");
      }
	  return;
  }

  
  /**
   * Queries the databse for credit purchases transactions based on the SQL predicate 
   * stored in the session object.  The results of the query are assigned to the 
   * request object to be managed by teh client. 
   *  
   * @throws ActionHandlerException
   */
  public void doList() throws ActionHandlerException {
	  this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	  this.useExistingSearchCriteria();
	  String sql = this.query.getWhereClause();
	  String orderBy = this.query.getOrderByClause();
	  try {
		  //this.xactList = this.ccApi.findData(query.getWhereClause(), query.getOrderByClause());
          this.ccApi.setBaseView("VwXactCreditChargeListView");
          this.ccApi.setBaseClass("com.bean.VwXactCreditChargeList");
		  this.xactList = this.ccApi.findData(sql, orderBy);  
	  }
	  catch (Exception e) {
		  this.msg = e.getMessage();
		  logger.log(Level.ERROR, this.msg);
		  throw new ActionHandlerException(this.msg);
	  }
	  finally {
		  this.sendClientData();
	  }
  }
  
  
  
  /**
   * Preapres the client for creating a creditor purchase order. As a requirement, this method  
   * expects the client to include the creditor's id in the HttpServletRequest parameter 
   * list as "qry_CreditorId".
   * <p>
   * The following objects are set on the request object identified as "creditor" and 
   * "xact", respectively: {@link VwCreditorBusiness} and {@link VwXactList}.  
   *
   * @throws ActionHandlerException
   */
  public void add() throws ActionHandlerException {
	  String creditorIdProp = "qry_CreditorId";
	  int creditorId = 0;
	  String temp = null;
	  
	  try {
    	  temp = this.getPropertyValue(creditorIdProp);
    	  creditorId = Integer.parseInt(temp);
    	  this.httpXactHelper.retrieveCreditOrder(0, creditorId);
      }
      catch (NotFoundException e) {
          this.msg = "A creditor must be selected for an 'Add' request";
    	  System.out.println(this.msg);
    	  throw new ActionHandlerException(this.msg);
      }
      return;
  }
  
  

  /**
   * Retrieves expsnes credit purchase data from the database and sends it to the client. 
   * 
   * @throws ActionHandlerException
   */
  public void edit() throws ActionHandlerException {
	  // Obtain data fromthe database.
	  this.httpXactHelper.retrieveCreditOrder();
  }
  
  protected void sendClientData() throws ActionHandlerException {
	  super.sendClientData();
	  
      // Set data on request object
      this.request.setAttribute(CreditChargesConst.CLIENT_DATA_XACTLIST, this.xactList);
      this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
  }
 
}
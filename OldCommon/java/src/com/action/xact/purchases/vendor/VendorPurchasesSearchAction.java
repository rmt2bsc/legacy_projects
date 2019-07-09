package com.action.xact.purchases.vendor;

import java.util.List;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.action.ActionHandlerException;

import com.bean.ItemMasterStatusHist;
import com.bean.RMT2TagQueryBean;

import com.bean.criteria.PurchaseOrderCriteria;

import com.constants.PurchasesConst;
import com.constants.RMT2ServletConst;

import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.util.NotFoundException;
import com.util.SystemException;



/**
 * This class provides action handlers to respond to an associated controller for searching, 
 * adding, deleting, and validating Creditor/Vendor Purchase Orders.
 * 
 * @author Roy Terrell
 *
 */
public class VendorPurchasesSearchAction extends VendorPurchasesAction {
    private static final String COMMAND_NEWSEARCH = "XactPurchase.VendorSearch.newsearch";
    private static final String COMMAND_SEARCH = "XactPurchase.VendorSearch.search";
    private static final String COMMAND_EDIT = "XactPurchase.VendorSearch.edit";
    private static final String COMMAND_ADD = "XactPurchase.VendorSearch.add";
    
    private Logger logger;

    /**
     * Default constructor
     *
     */
    public VendorPurchasesSearchAction()  {
        super(); 
        logger = Logger.getLogger("VendorPurchasesAction");
    }
    
  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public VendorPurchasesSearchAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
      if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_NEWSEARCH)) {
          this.doNewSearch();
      }
      if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_SEARCH)) {
          this.doSearch();
      }
      if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_ADD)) {
          this.addData();
      }
      if (command.equalsIgnoreCase(VendorPurchasesSearchAction.COMMAND_EDIT)) {
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
      this.query.setQuerySource("VwPurchaseOrderListView");
      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
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
      this.query.setQuerySource("VwPurchaseOrderListView");
      this.query.setOrderByClause("id desc");
      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
  }
  
  /**
   * Returns selection criteria that is sure to retrun an empty result set once 
   * applied to the sql that pertains to the data source of the vendor purchases maintenace console page.
   */
  protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
      return "id = -1";
  }
  
  /**
   * This method is responsible for gathering the user's input of purchase order selection criteria data 
   * from the request object.
   * 
   * @return Object which represents the custom object that is a member of {@link RMT2TagQueryBean}. 
   * @throws ActionHandlerException
   */
  protected Object doCustomInitialization() throws ActionHandlerException {
      String method = "[" + this.className + ".getCriteriaObject] ";
      this.setBaseView("VwPurchaseOrderListView");
      PurchaseOrderCriteria criteriaObj = PurchaseOrderCriteria.getInstance();
      if (!this.isFirstTime()) {
          try {
              DataSourceAdapter.packageBean(this.request, criteriaObj);    
          }
          catch (SystemException e) {
              System.out.println(method + "Problem gathering Item Master request parameters.");
              System.out.println(method + e.getMessage());
              throw new ActionHandlerException(e);
          }    
      }
      return criteriaObj;
  }

  
  /**
   * Preapres the client for creating a purchase order. As a requirement, this method  
   * expects the client to include the vendor's id in the HttpServletRequest parameter 
   * list as "qry_VendorId".
   * <p>
   * The following objects are set on the request object identified as "item" and 
   * "itemhistory", respectively: {@link VwItemMaster} and {@link ItemMasterStatusHist}.  
   *
   * @throws ActionHandlerException
   */
  public void add() throws ActionHandlerException {
	  String vendorIdProp = "qry_VendorId";
	  String temp = null;
	  int vendorId = 0;
	  int poId = 0;
	  List availItems = null;
	  
      try {
    	  temp = this.getPropertyValue(vendorIdProp);
    	  vendorId = Integer.parseInt(temp);
    	  this.httpPoHelper.retrievePurchaseOrder(0, vendorId);
    	  // Since the PO is new, we should expect a comprehensive list of vendor 
    	  // inventory items.
    	  availItems = this.getPoAvailItems(vendorId, poId);
    	  this.request.setAttribute(PurchasesConst.CLIENT_DATA_AVAIL_ITEMS, availItems);
          return;
      }
      catch (NotFoundException e) {
    	  logger.log(Level.ERROR, e.getMessage());
    	  this.msg = "A vendor must be selected in order to begin an \'Add Purchase Order\' operation";
    	  throw new ActionHandlerException(this.msg);
      }
  }
  
  

  /**
   * Uses key data from the client's request object to retrieve purchase order 
   * information from an external datasource.  The response URL is determine 
   * here by using the purchase order status id to determine whether the data 
   * should be displayed in read-only or edit mode.  The data is displayed in 
   * edit mode when the purchase order status is new or in quote mode.   All 
   * other statuses requires the data to be presented as read-only.
   * 
   * @throws ActionHandlerException
   */
  public void edit() throws ActionHandlerException {
	  // Obtain data fromthe database.
	  this.httpPoHelper.retrievePurchaseOrder();
	  
	  // Use the purchase order status id to determine whether the client 
	  // should display the data in read-only or edit mode.  
	  String nextURL = null;
	  int poStatusId = this.httpPoHelper.getPoStatus().getId();
	  if (poStatusId > PurchasesConst.PURCH_STATUS_QUOTE) {
		  nextURL = VendorPurchasesAction.JSP_PATH + "PurchaseOrderView.jsp";
	  }
	  else {
		  nextURL = VendorPurchasesAction.JSP_PATH + "PurchaseOrderEdit.jsp";
	  }
	  this.request.setAttribute(RMT2ServletConst.REQUEST_DELAYED_RESPONSE, nextURL);
  }
 
}
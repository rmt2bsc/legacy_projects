package com.action.xact.disbursements;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;

import com.action.xact.AbstractXactAction;

import com.api.CashDisbursementsApi;

import com.api.db.DatabaseException;

import com.api.db.orm.DataSourceAdapter;

import com.bean.RMT2TagQueryBean;

import com.bean.criteria.XactCriteria;

import com.constants.DisbursementsConst;
import com.constants.RMT2ServletConst;
import com.constants.XactConst;

import com.factory.CashDisburseFactory;

import com.util.RMT2Utility;
import com.util.SystemException;
import com.util.XactException;


/**
 * This class provides functionality needed to serve the requests of the Cash Disbursements Search user interface
 * 
 * @author Roy Terrell
 *
 */ 
public class DisbursementsSearchAction extends AbstractXactAction {
    private static final String COMMAND_NEWSEARCH = "XactDisburse.DisbursementSearch.newsearch";
    private static final String COMMAND_SEARCH = "XactDisburse.DisbursementSearch.search";
    public static final String COMMAND_LIST = "XactDisburse.DisbursementSearch.list";
    private static final String COMMAND_EDIT = "XactDisburse.DisbursementSearch.edit";
    public static final String COMMAND_ADD = "XactDisburse.DisbursementSearch.add";
	private Logger logger;
	private List xactList;
	private CashDisbursementsApi api;
	
	
	
      /**
       * Default constructor
       *
       */
      public DisbursementsSearchAction()  {
          super(); 
          logger = Logger.getLogger("DisbursementsSearchAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public DisbursementsSearchAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
          this.api = CashDisburseFactory.createApi(this.dbConn, this.request);
          this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
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
          if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_NEWSEARCH)) {
              this.doNewSearch();
          }
          if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_SEARCH)) {
              this.doSearch();
          }
          if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_LIST)) {
        	  this.doList();
          }
          if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_ADD)) {
              this.addData();
          }
          if (command.equalsIgnoreCase(DisbursementsSearchAction.COMMAND_EDIT)) {
              this.editData();
          }
      }
      
      /**
       * Returns selection criteria that is sure to retrun an empty result set once 
       * applied to the sql that pertains to the data source of the customer search page.
       */
      protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
          return "id = -1";
      }
      
      /**
       * Capture selection criteria data from the client.
       */
      protected Object doCustomInitialization() throws ActionHandlerException {
    	  XactCriteria criteriaObj = XactCriteria.getInstance();
          if (!this.isFirstTime()) {
              try {
                  DataSourceAdapter.packageBean(this.request, criteriaObj);
                  this.setBaseView("VwXactListView");
              }
              catch (SystemException e) {
                  this.msg = "Problem gathering Cash Disbursment Search request parameters:  " + e.getMessage();
                  logger.log(Level.ERROR, this.msg);
                  throw new ActionHandlerException(this.msg);
              }    
              
              //  Get data items that were designed to be managed by custom routines and 
              //  not to be picked up by the DataSourceAdapter above.
              criteriaObj.setQryRelOp_XactDate_1(this.request.getParameter("qryRelOpXactDate1"));
              criteriaObj.setQry_XactDate_1(this.request.getParameter("qry_XactDate1"));
              criteriaObj.setQryRelOp_XactDate_2(this.request.getParameter("qryRelOpXactDate2"));
              criteriaObj.setQry_XactDate_2(this.request.getParameter("qry_XactDate2"));
              criteriaObj.setQryRelOp_XactAmount_1(this.request.getParameter("qryRelOpXactAmount1"));
              criteriaObj.setQry_XactAmount_1(this.request.getParameter("qry_XactAmount1"));
              criteriaObj.setQryRelOp_XactAmount_2(this.request.getParameter("qryRelOpXactAmount2"));
              criteriaObj.setQry_XactAmount_2(this.request.getParameter("qry_XactAmount2"));
          }
          return criteriaObj;
      }
      
      /**
       * Handler method  that responds to the client's request to display a new Customer Sales Order search page.
       * 
       * @throws ActionHandlerException
       */
      protected void doNewSearch() throws ActionHandlerException {
          this.setFirstTime(true);
          this.startSearchConsole();
          this.query.setQuerySource("VwXactListView");
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
          this.query.setOrderByClause("xact_date desc");
          this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
      }
      
      /**
       * Customizes selection criteria pertaining to transaction date and transaction amount.
       */
      protected String postBuildCustomClientCriteria() {
    	  XactCriteria criteriaObj = (XactCriteria) this.query.getCustomObj();
    	  try {
    		  return this.processSpecialCriteria(criteriaObj);  
    	  }
    	  catch (SystemException e) { 
    		  return "";
    	  }
      }
      
      /**
       * Builds custom selection criteria pertaining to transaction date and amount 
       * which the results can yield compound statements ustilizing various relational 
       * operators.   
       * <p>
       * Example: xact_date >= '2/12/2006' and xact_date <= '3/12/2006' 
       *          and xact_amount >= 1234.55 and xact_amount <= 5000.00.
       *             
       * @param criteriaObj XactCriteria containing selection criteria values gathered 
       *        from the client's request.
       * @return Selection criteria
       * @throws SystemException
       */
      private String processSpecialCriteria(XactCriteria criteriaObj) throws SystemException {
    	  StringBuilder criteria = new StringBuilder(100);
    	  String tempVal = null;
    	  String tempOp = null;
    	  
		  // Add transaction type cash disbursements
		  criteria.append("xact_type_id = ");
		  criteria.append(XactConst.XACT_TYPE_CASHDISBEXP);

		  //  Add transaction date #1 criteria
    	  tempVal = criteriaObj.getQry_XactDate_1();
    	  tempOp = criteriaObj.getQryRelOp_XactDate_1();
    	  if (tempVal != null && tempOp != null && !tempVal.equals("") && !tempOp.equals("")) {
    		  try {
    			  RMT2Utility.stringToDate(tempVal);
    		  }
    		  catch (SystemException e) {
    			  throw e;
    		  }
    		  if (criteria.length() > 0) {
    			  criteria.append(" and ");
    		  }
    		  criteria.append("xact_date ");
    		  criteria.append(tempOp);
    		  criteria.append(" \'" + tempVal + "\'");
    	  }
    	  
    	  //  Add transaction date #2 criteria
    	  tempVal = criteriaObj.getQry_XactDate_2();
    	  tempOp = criteriaObj.getQryRelOp_XactDate_2();
    	  if (tempVal != null && tempOp != null && !tempVal.equals("") && !tempOp.equals("")) {
    		  try {
    			  RMT2Utility.stringToDate(tempVal);
    		  }
    		  catch (SystemException e) {
    			  throw e;
    		  }
    		  if (criteria.length() > 0) {
    			  criteria.append(" and ");
    		  }
    		  criteria.append("xact_date ");
    		  criteria.append(tempOp);
    		  criteria.append(" \'" + tempVal + "\'");
    	  }
    	  
		  //  Add transaction amount #1 criteria
    	  tempVal = criteriaObj.getQry_XactAmount_1();
    	  tempOp = criteriaObj.getQryRelOp_XactAmount_1();
		  if (tempVal != null && tempOp != null && !tempVal.equals("") && !tempOp.equals("")) {
		  	if (criteria.length() > 0) {
		  		criteria.append(" and ");
		  	}
		  	criteria.append("xact_amount ");
		  	criteria.append(tempOp);
		  	criteria.append(" " + tempVal);
		  }
		  
		  //  Add transaction amount #2 criteria
    	  tempVal = criteriaObj.getQry_XactAmount_2();
    	  tempOp = criteriaObj.getQryRelOp_XactAmount_2();
		  if (tempVal != null && tempOp != null && !tempVal.equals("") && !tempOp.equals("")) {
		  	if (criteria.length() > 0) {
		  		criteria.append(" and ");
		  	}
		  	criteria.append("xact_amount ");
		  	criteria.append(tempOp);
		  	criteria.append(" " + tempVal);
		  }    
          return criteria.length() <= 0 ? null : criteria.toString();
      }
      
      /**
       * Handler method  that responds to the client's request to perform a customer search 
       * using the selection criteria entered by the user.
       * 
       * @throws ActionHandlerException
       */
      protected void doList() throws ActionHandlerException {
    	  this.xactList = new ArrayList();
    	  this.api.setBaseClass("com.bean.VwXactList");
    	  this.api.setBaseView(this.query.getQuerySource());
    	  String criteria = this.query.getWhereClause();
    	  String ordering = this.query.getOrderByClause();
    	  try {
    		  this.xactList = this.api.findData(criteria, ordering);  
    	  }
    	  catch (SystemException e) {
    		  throw new ActionHandlerException(e.getMessage());
    	  }
          this.sendClientData();
      }
      
      /**
       * Instantiates the data objects needed to create a new general cash 
       * disbursement transaction which the objects are sent across the wire 
       * to the client for presentation. 
       */
      public void add() throws ActionHandlerException {
    	  super.add();
    	  this.receiveClientData();
    	  try {
    		  // Instantiate transaction and transaction item objects.
        	  this.xactItems = new ArrayList();
        	  
        	  // Get all transaction type item entries by transaction type to be used generally as a UI Dropdown.
              this.xactItemTypes = this.baseApi.findXactTypeItemsByXactTypeId(this.xactType.getId());
          }
          catch (XactException e) {
        	  this.msg = "XactException: " + e.getMessage();
        	  throw new ActionHandlerException(this.msg);
          }
    	  this.sendClientData();
      }

      /**
       * Obtains key data peratining to the transaction fro the client and uses 
       * the key data to fetch related transaction data into member variables 
       * from the database.
       */
      public void edit() throws ActionHandlerException {
    	  super.edit();
          this.refreshXact(this.xact.getId());
          try {
              this.xactType = this.httpHelper.getHttpXactType();
              if (this.xactType.getId() == 0) {
                  this.xactType = this.baseApi.findXactTypeById(XactConst.XACT_TYPE_CASHDISBEXP);
                  this.httpHelper.getHttpXactType().setId(XactConst.XACT_TYPE_CASHDISBEXP);
                  this.httpHelper.getHttpXactType().setDescription(this.xactType.getDescription());
              }
          }
          catch (XactException e) {
              this.msg = "XactException: " + e.getMessage();
              throw new ActionHandlerException(this.msg);
          }
      }
      
      
      /**
       * Retrieves key data from the client pertaining to the transaction and uses the 
       * key data to fetch remaining data from the database to satisfy the client's request. 
       */
      public void receiveClientData() throws ActionHandlerException {
    	  super.receiveClientData();
    	  try {
              this.xactType = this.baseApi.findXactTypeById(XactConst.XACT_TYPE_CASHDISBEXP);
			  this.httpHelper.getHttpXactType().setId(XactConst.XACT_TYPE_CASHDISBEXP);
              this.httpHelper.getHttpXactType().setDescription(this.xactType.getDescription());
          }
          catch (XactException e) {
        	  this.msg = "XactException: " + e.getMessage();
        	  throw new ActionHandlerException(this.msg);
          }
      }
      
      
      /**
       * Sends transaction data to the client as a response to the client's general 
       * cash disbursement request.
       */
      public void sendClientData() throws ActionHandlerException {
    	  super.sendClientData();
    	  this.request.setAttribute(DisbursementsConst.CLIENT_DATA_LIST, this.xactList); 
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.genericXact);
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACTTYPE, this.xactType);
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMS, this.xactItems);
    	  this.request.setAttribute(XactConst.CLIENT_DATA_XACTITEMTYPELIST, this.xactItemTypes);
      }
      

}
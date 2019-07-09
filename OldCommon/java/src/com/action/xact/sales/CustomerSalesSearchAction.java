package com.action.xact.sales;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;
import com.bean.RMT2TagQueryBean;
import com.bean.criteria.CustomerCriteria;

import com.constants.RMT2ServletConst;

import com.util.SystemException;


/**
 * This class provides functionality needed to serve the requests of the Customer Sales Search user interface
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerSalesSearchAction extends SalesOrderMaintAction {
    private final static String CUST_TYPE_BUS = "1";
    private final static String CUST_TYPE_PER = "2";
    
    private static final String COMMAND_NEWSEARCH = "XactSales.CustomerSalesSearch.newsearch";
    private static final String COMMAND_SEARCH = "XactSales.CustomerSalesSearch.search";
    private static final String COMMAND_LIST = "XactSales.CustomerSalesSearch.list";
    private static final String COMMAND_EDIT = "XactSales.CustomerSalesSearch.edit";
	private Logger logger;
	
	
      /**
       * Default constructor
       *
       */
      public CustomerSalesSearchAction()  {
          super(); 
          logger = Logger.getLogger("CustomerSalesSearchAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerSalesSearchAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
          
          this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
          
          if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_NEWSEARCH)) {
              this.doNewSearch();
          }
          if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_SEARCH)) {
              this.doSearch();
          }
          if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_LIST)) {
              // There is no action handler since JSP uses datasource to retrieve data.
          }
          if (command.equalsIgnoreCase(CustomerSalesSearchAction.COMMAND_EDIT)) {
              this.editData();
          }
      }
      
      /**
       * Returns selection criteria that is sure to retrun an empty result set once 
       * applied to the sql that pertains to the data source of the customer search page.
       */
      protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
          _query.setQuerySource("VwCustomerBusinessView");
          return "customer_id = -1";
      }
      
      protected Object doCustomInitialization() throws ActionHandlerException {
          CustomerCriteria criteriaObj = CustomerCriteria.getInstance();
          if (!this.isFirstTime()) {
              try {
                  DataSourceAdapter.packageBean(this.request, criteriaObj);
                  if (CustomerSalesSearchAction.CUST_TYPE_BUS.equals(criteriaObj.getQry_CustomerType())) {
                      this.setBaseView("VwCustomerBusinessView");
                      this.setBaseClass("com.bean.VwCustomerBusiness");
                  }
                  if (CustomerSalesSearchAction.CUST_TYPE_PER.equals(criteriaObj.getQry_CustomerType())) {
                      this.setBaseView("VwCustomerPersonView");
                      this.setBaseClass("com.bean.VwCustomerPerson");
                  }
              }
              catch (SystemException e) {
                  this.msg = "Problem gathering Customer Sales  Search request parameters:  " + e.getMessage();
                  logger.log(Level.ERROR, this.msg);
                  throw new ActionHandlerException(this.msg);
              }    
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
      }
      
      /**
       * Handler method  that responds to the client's request to perform a customer search using the selection criterai entered by the user.
       * 
       * @throws ActionHandlerException
       */
      protected void doSearch() throws ActionHandlerException {
          this.setFirstTime(false);
          this.buildSearchCriteria();
          this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
      }
      
      /**
       * Initiates the process of displaying the Customer Search page fro its most recent state.
       * 
       * @throws ActionHandlerException
       */
      public void edit() throws ActionHandlerException {
          this.recallCustomerSalesConsole();
      }
      

}
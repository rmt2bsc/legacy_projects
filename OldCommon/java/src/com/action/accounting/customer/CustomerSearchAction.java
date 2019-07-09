package com.action.accounting.customer;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;
import com.bean.Customer;
import com.bean.RMT2TagQueryBean;

import com.bean.criteria.CustomerCriteria;

import com.constants.RMT2ServletConst;

import com.factory.AcctManagerFactory;

import com.util.SystemException;


/**
 * This class provides functionality needed to serve the requests of the Customer 
 * Maintenance Search user interface
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerSearchAction extends CustomerAction {
	private final static String CUST_TYPE_BUS = "1";
    private final static String CUST_TYPE_PER = "2";
    
    private static final String COMMAND_NEWSEARCH = "Accounting.CustomerSearch.newsearch";
    private static final String COMMAND_SEARCH = "Accounting.CustomerSearch.search";
    private static final String COMMAND_LIST = "Accounting.CustomerSearch.list";
    private static final String COMMAND_EDIT = "Accounting.CustomerSearch.edit";
    private static final String COMMAND_ADD = "Accounting.CustomerSearch.add";
	
	private Logger logger;
	
	
      /**
       * Default constructor
       *
       */
      public CustomerSearchAction()  {
          super(); 
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerSearchAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
          logger = Logger.getLogger("CustomerSearchAction");
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
          if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_NEWSEARCH)) {
              this.doNewSearch();
          }
          if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_SEARCH)) {
              this.doSearch();
          }
          if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_LIST)) {
              // There is no action handler since JSP uses datasource to retrieve data.
          }
          if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_EDIT)) {
              this.editData();
          }
          if (command.equalsIgnoreCase(CustomerAction.COMMAND_PUBLICFETCH)) {
              this.editData();
          }
          if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_ADD)) {
              this.addData();
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
      
      protected void doPostCustomInitialization(RMT2TagQueryBean _query, int _searchMode) throws ActionHandlerException {
          _query.setQuerySource(this.baseView);
          return;
       }
      
      protected Object doCustomInitialization() throws ActionHandlerException {
          CustomerCriteria criteriaObj = CustomerCriteria.getInstance();
          try {
              DataSourceAdapter.packageBean(this.request, criteriaObj);
              if (CustomerSearchAction.CUST_TYPE_BUS.equals(criteriaObj.getQry_CustomerType())) {
                  this.setBaseView("VwCustomerBusinessView");
                  this.setBaseClass("com.bean.VwCustomerBusiness");
              }
              if (CustomerSearchAction.CUST_TYPE_PER.equals(criteriaObj.getQry_CustomerType())) {
                  this.setBaseView("VwCustomerPersonView");
                  this.setBaseClass("com.bean.VwCustomerPerson");
              }
          }
          catch (SystemException e) {
              this.msg = "Problem gathering Customer Sales  Search request parameters:  " + e.getMessage();
              logger.log(Level.ERROR, this.msg);
              throw new ActionHandlerException(this.msg);
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
       * Initiates the process of displaying the Customer Edit page based on the selected 
       * customer from the search page.  
       * 
       * @throws ActionHandlerException
       */
      public void edit() throws ActionHandlerException {
    	  this.fetchEditData();
      }
	
      /**
       * Initiates the process of displaying the Customer Edit page to created a new customer.
       * 
       * @throws ActionHandlerException
       */	
      public void add() throws ActionHandlerException {
    	  // Create customer related data objects for client display
    	  super.add();
    	  // Verify customer type was selected by the user.
    	  if (this.custType != null && !this.custType.equals("")) {
    		  // Customer type has been selected.
    	  }
    	  else {
    		  this.msg = "Customer Type must be selected";
    		  logger.log(Level.ERROR, this.msg);
    		  throw new ActionHandlerException(this.msg);
    	  }
      }
	}
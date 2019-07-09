package com.action.accounting.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.AbstractActionHandler;
import com.action.ActionHandlerException;
import com.action.ICommand;

import com.api.DaoApi;
import com.api.GLCustomerApi;
import com.api.ContactBusinessApi;
import com.api.ContactPersonApi;
import com.api.ContactAddressApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceFactory;

import com.bean.Address;
import com.bean.Business;
import com.bean.Customer;
import com.bean.CustomerWithName;
import com.bean.Person;
import com.bean.RMT2TagQueryBean;
import com.bean.SalesOrder;
import com.bean.Zipcode;

import com.constants.ContactsConst;
import com.constants.RMT2ServletConst;
import com.constants.SalesConst;

import com.factory.AcctManagerFactory;
import com.factory.ContactsFactory;

import com.util.RMT2BeanUtility;
import com.util.SystemException;


/**
 * This class provides action handler functionality that is common serving sales order requests.
 * 
 * @author Roy Terrell
 *
 */ 
public abstract class CustomerAction extends AbstractActionHandler implements ICommand {
	/** Command for allowing outside processes to perform batch Customer data fetches */ 
	public static final String COMMAND_PUBLICFETCH = "publicfetch";
	
	private Logger logger;
	protected ContactBusinessApi busApi;
	protected ContactPersonApi perApi;
	protected ContactAddressApi addrApi;
    protected GLCustomerApi  custApi;
    protected HttpCustomerHelper httpHelper;
    
	/** Customer's balance */
	protected Double balance;
	/** An ArrayList of Customers */
	protected ArrayList creditors;
	/** Customer */
	protected Customer customer;
	/** Customer Extension */
	protected CustomerWithName customerExt;
	/** Customer's address profile */
	protected Address addr;
	/** Customer's person profile */
	protected Person person;
	/** Customer's Business profile */
	protected Business bus;
	/** Customer's Zip code profile */
	protected Zipcode zip;
	/** Customer type indicator */
	protected String custType;
	
	
      /**
       * Default constructor
       *
       */
      public CustomerAction()  {
          super(); 
          logger = Logger.getLogger("CustomerAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
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
          try {
              this.custApi = AcctManagerFactory.createCustomer(this.dbConn, _request);
              this.busApi = ContactsFactory.createBusinessApi(this.dbConn);
              this.addrApi = ContactsFactory.createAddressApi(this.dbConn);
              this.perApi = ContactsFactory.createPersonApi(this.dbConn);
          }
           catch (DatabaseException e) {
               throw new SystemException(e);
           }
           logger = Logger.getLogger("CustomerAction");
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
          try {
              this.init(null, request);
              this.query = (RMT2TagQueryBean) this.request.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
          }
          catch (Exception e) {
        	  throw new ActionHandlerException(e.getMessage());
          }
    	  return;
      }
      
      
      /**
       * Retreives bsic customer, sales order, and transactoin  data from the client's 
       * request.
       */
      protected void receiveClientData() throws ActionHandlerException {
    	  try {
              this.httpHelper = new HttpCustomerHelper(this.context, this.request);
    		  // Get data from client's page
    		  this.httpHelper.getHttpCombined();
    		  this.customer = this.httpHelper.getCustomer();
    		  this.custType = this.httpHelper.getCustType();
    		  this.bus = this.httpHelper.getBusiness();
    		  this.person = this.httpHelper.getHttpPerson();
    		  this.addr = this.httpHelper.getAddress();
    		  this.zip = this.httpHelper.getZip();
	    	  return;
    	  } // end try
    	  catch (Exception e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  throw new ActionHandlerException(e);
    	  }
      }
      
      
      /**
       * Packages detail Sales Order data into the HttpServlerRequest object which is 
       * required to serve as the response to the client. 
       * 
       * @throws ActionHandlerException
       */
      protected void sendClientData() throws ActionHandlerException {
          // Prepare to return to client
          this.request.setAttribute(SalesConst.CLIENT_DATA_ADDRESS, this.addr);
          this.request.setAttribute(SalesConst.CLIENT_DATA_BUSINESS, this.bus);
          this.request.setAttribute(SalesConst.CLIENT_DATA_PERSON, this.person);
          this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER, this.customer);
          this.request.setAttribute(SalesConst.CLIENT_DATA_ZIP, this.zip);
          this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTTYPE, this.custType);
          this.request.setAttribute(SalesConst.CLIENT_DATA_BALANCE, this.balance);
          this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
      }
      
      /**
       * Obtains data from the database for the purpose of presenting the Customer 
       * Edit Maintenace page using customerId.
       * 
       * @param customerId The id of the custoemr to target.
       * @throws ActionHandlerException
       */
      public void fetchEditData(int customerId) throws ActionHandlerException {
    	  DaoApi dao = DataSourceFactory.createDao(this.dbConn);
    	  Customer cust = AcctManagerFactory.createCustomer();
    	  cust.addCriteria("id", customerId);
    	  try {
    		  Object results[] = dao.retrieve(cust);
    		  if (results.length > 0) {
    			  this.customer = (Customer) results[0];
    		  }
    		  else {
    			  this.customer = cust;
    		  }
    	  }
    	  catch (Exception e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  throw new ActionHandlerException(e.getMessage());
    	  }
    	  this.fetchEditData();
      }
      
      /**
       * Obtains data from the database that pertains to the customer, person, business, 
       * address, zip code, and customer type entities for the purpose of presenting the 
       * Customer Edit Maintenace page.
       * 
       * @throws ActionHandlerException
       */
      protected void fetchEditData() throws ActionHandlerException {
    	  int key;
    	  Object results[];
    	  DaoApi dao = DataSourceFactory.createDao(this.dbConn);
    	  RMT2BeanUtility beanUtil;
    	  List addrList = null;
    	  	
    	  // Get Creditor object	  	
    	  this.customer.addCriteria("Id", this.customer.getId());
    	  try {
    		  results = dao.retrieve(this.customer);
    		  if (results.length > 0) {
    			  this.customer = (Customer) results[0];
    		  }
    		  this.customerExt = this.custApi.findCustomerWithName(this.customer.getId());
    	  }
    	  catch (Exception e) {
    		  throw new ActionHandlerException(e.getMessage());
    	  }
      	    
    	  try {
    		  // Get Business Object.
    		  key = this.customer.getBusinessId();
    		  if (key > 0) {
    			  bus = busApi.findBusById(key);
    			  custType = ContactsConst.CONTACT_TYPE_BUSINESS;
    		  }
    		  else {
    			  bus = busApi.createBusiness();
    			  beanUtil = new RMT2BeanUtility(bus);
    			  bus = (Business) beanUtil.initializeBean();  
    		  }

    		  // Get Person Object.
    		  key = this.customer.getPersonId();
    		  if (key > 0) {
    			  this.person = this.perApi.findPerById(key);
    			  custType = ContactsConst.CONTACT_TYPE_PERSONAL;
    		  }
    		  else {
    			  this.person = this.perApi.createPerson();
    			  beanUtil = new RMT2BeanUtility(this.person);
    			  this.person = (Person) beanUtil.initializeBean();
    		  }     
      	      
    		  // Get Address object based on the business Id or person Id.
    		  if (this.bus.getId() > 0) {
    			  addrList = this.addrApi.findAddrByBusinessId(this.bus.getId());
    		  }
    		  if (this.person.getId() > 0) {
    			  addrList = this.addrApi.findAddrByPersonId(this.person.getId());
    		  }     
    		  if (addrList != null && addrList.size() > 0 ) {
    			  addr = (Address) addrList.get(0);
    			  zip = this.addrApi.findZipByCode(addr.getZip());
    		  }
    		  else {
    			  addr = this.addrApi.createAddress();
    			  zip = new Zipcode();          
    		  }
    		  // Get Customer's Balance
    		  this.balance = this.getBalance(this.customer.getId());      	    	
    	  }
    	  catch (Exception e) {
    		  logger.log(Level.ERROR, e.getMessage());
    		  throw new ActionHandlerException(e.getMessage());
    	  }
      }
      
  
      
      /**
       * Create the data entities needed to represent a new customer. 
       * 
       * @throws ActionHandlerException
       */
      public void add() throws ActionHandlerException {
    	  try {
    		  this.httpHelper = new HttpCustomerHelper(this.context, this.request);
              this.custType = this.httpHelper.getHttpCustomerType();
              this.person = ContactsFactory.createPerson();
              this.bus = ContactsFactory.createBusiness();
              this.addr = ContactsFactory.createAddress();
              this.zip = new Zipcode();
              this.customer = AcctManagerFactory.createCustomer();
    	  }
    	  catch (Exception e) {
    		  throw new ActionHandlerException(e.getMessage());
    	  }
    	  this.balance = new Double(0);
    	  return;
      }
      
      /**
       * No Action 
       * 
       * @throws ActionHandlerException
       */
      public void edit() throws ActionHandlerException {
    	  return;
      }
      
      /**
       * No Action 
       * 
       * @throws ActionHandlerException
       */
      public void save() throws ActionHandlerException {
          return;
      }
      
      /**
       * No Action 
       * 
       * @throws ActionHandlerException
       */
      public void delete() throws ActionHandlerException {
          return;
      }
      
        /**
       * Makes call to customer api to calculate and capture customer's balance.  The balance 
       * is added to the sales order member variable.   If sales orer varialbe is invalid it 
       * is instantiated.
       * 
       * @param custId Customer Id
       * @return {@link SalesOrder}
       * @throws ActionHandlerException
       */
      protected Double getBalance(int custId) throws ActionHandlerException {
          try {
              double balance = this.custApi.findCustomerBalance(custId);
              return new Double(balance);
          }
          catch (Exception e) {
              throw new ActionHandlerException(e.getMessage());
          }
      }

}
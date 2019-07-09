package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;
import java.util.Hashtable;

import com.api.GLCustomerApi;
import com.api.ContactBusinessApi;
import com.api.ContactPersonApi;
import com.api.ContactAddressApi;

import com.bean.Business;
import com.bean.Address;
import com.bean.Person;
import com.bean.Customer;
import com.bean.RMT2TagQueryBean;
import com.bean.Zipcode;

import com.bean.criteria.CustomerCriteria;

import com.constants.ContactsConst;
import com.constants.RMT2ServletConst;
import com.constants.SalesConst;

import com.action.AbstractActionHandler;

import com.factory.ContactsFactory;
import com.factory.AcctManagerFactory;
import com.factory.GlAccountsFactory;
import com.factory.DataSourceAdapter;

import com.util.ActionHandlerException;
import com.util.RMT2BeanUtility;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.GLAcctException;
import com.util.ContactBusinessException;
import com.util.ContactPersonException;
import com.util.ContactAddressException;
import com.util.CustomerException;
import com.util.RMT2Utility;


/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating 
 * Customer information.
 * 
 * @author Roy Terrell
 *
 */
public class GlCustomerAction extends AbstractActionHandler {

   private GLCustomerApi api;
	private ContactBusinessApi busApi;
	private ContactPersonApi perApi;
	private ContactAddressApi addrApi;
   private final String PAGE_TITLE = "GL Customer Maintenance";
   private Hashtable errors = new Hashtable();
   private Logger logger;
   private final static String CUST_TYPE_BUS = "1";
   private final static String CUST_TYPE_PER = "2";
   
   
   
   
   public GlCustomerAction() {
       logger = Logger.getLogger(GlCustomerAction.class);
   }

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public GlCustomerAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {

    super(_context, _request);
    this.className = "GlCustomerAction";
    this.api = AcctManagerFactory.createCustomer(this.dbConn);
  	 this.busApi = ContactsFactory.createBusinessApi(this.dbConn);
  	 this.addrApi = ContactsFactory.createAddressApi(this.dbConn);
  	 this.perApi = ContactsFactory.createPersonApi(this.dbConn);
  	 
  }
  
  
  
  protected Object doCustomInitialization() throws ActionHandlerException {
      String temp = null;
      CustomerCriteria criteriaObj = CustomerCriteria.getInstance();
      if (!this.isFirstTime()) {
          try {
              DataSourceAdapter.packageBean(this.request, criteriaObj);
              if (GlCustomerAction.CUST_TYPE_BUS.equals(criteriaObj.getQry_CustomerType())) {
                this.setBaseView("VwCustomerBusinessView");
                this.setBaseClass("com.bean.VwCustomerBusiness");
            }
            if (GlCustomerAction.CUST_TYPE_PER.equals(criteriaObj.getQry_CustomerType())) {
                this.setBaseView("VwCustomerPersonView");
                this.setBaseClass("com.bean.VwCustomerPerson");
            }

          }
          catch (SystemException e) {
              this.msg = "Problem gathering Project Search request parameters:  " + e.getMessage();
              logger.log(Level.ERROR, this.msg);
              throw new ActionHandlerException(this.msg);
          }    
      }
      return criteriaObj;
  }

  protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionHandlerException {
      return "customer_id = -1";
  }
  
  protected void doPostCustomInitialization(RMT2TagQueryBean _query, int _searchMode) throws ActionHandlerException {
      super.doPostCustomInitialization(_query, _searchMode);
      RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
      query.setQuerySource(this.baseView);
      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
      return;
   }
  
  
  public void startSearchConsole() throws ActionHandlerException {
      super.startSearchConsole();
      RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
      query.setQuerySource("VwCustomerPersonView");
      this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
      return;
  }
  
  

  
  /**
   * Retrieves a row from a list of customers that exist in the HTTP Request object, retrieves from the database and packages the
   * the data into a Customer, Business, Address, and Zipcode object, and initializes the request object with data to be used by the client.
   * <p>
   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
   * at the discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>ADDRESS</td>
	 *   <td>{@link Address}</td>
	 *   <td>Address data</td>
	 * </tr>
	 * <tr>
	 *   <td>BUSINESS</td>
	 *   <td>{@link Business}</td>
	 *   <td>Business data</td>
	 * </tr>
	 * <tr>
	 *   <td>PERSON</td>
	 *   <td>{@link Person}</td>
	 *   <td>Person data</td>
	 * </tr>
	 * <tr>
	 *   <td>CUSTOMER</td>
	 *   <td>{@link Customer}</td>
	 *   <td>Costomer data</td>
	 * </tr>   
	 * <tr>
	 *   <td>ZIP</td>
	 *   <td>{@link Zipcode}</td>
	 *   <td>Address object</td>
	 * </tr>
	 * <tr>
	 *   <td>CUSTTYPE</td>
	 *   <td>String</td>
	 *   <td>Customer Type Code</td>
	 * </tr>        
	 *</table>
   * 
   * @throws GLAcctException
   * @throws DatabaseException
   */
  public void editAction() throws GLAcctException, DatabaseException {
      String method = "editAction";
	  	String temp = null;
	  	int  row = 0;
	  	int  key = 0;
	  	Address addr = null;
	  	Business bus = null;
	  	Person per = null;
	  	Customer cust = null;
	  	Zipcode zip = null;
	  	ArrayList addrList = null;
	  	RMT2BeanUtility beanUtil = null;
	  	String custType = "";
	  	boolean retrieveFromList = true;

	  	try {
	  	    // Get Customer Id either from a list of customers or from the customer maintenance page.
	  	    try {
	  	        row = this.getSelectedRow("selCbx");
		    	  temp = this.request.getParameter("CustomerId" + row);
		    	  
		    	  // If Customer Id is not found through indexing customer id, then try to find customer id as a single occurrence.
		    	  if (temp == null) {
		    	      throw new SystemException("");
		    	  }
	  	    }
	  	    catch (SystemException e) {
	  	        retrieveFromList = false;
	  	        // We are not dealing with a list...try single occurrence.
	  	        temp = this.request.getParameter("CustomerId");
                if (temp == null) {
                    throw new GLAcctException("A customer must be selected in order to perform an edit operation");
                }
	  	    }
	  	    
	  	    // Get the customer's id.
	  	    key = RMT2Utility.stringToNumber(temp).intValue();
	    	
	    	// Get Customer object
	    	cust = api.findCustomerById(key);
	
	    	// Get Business Object.
            key = cust.getBusinessId();
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
            key = cust.getPersonId();
	    	if (key > 0) {
	    		per = this.perApi.findPerById(key);
	    		custType = ContactsConst.CONTACT_TYPE_PERSONAL;
	    	}
	    	else {
	    		per = this.perApi.createPerson();
	    		beanUtil = new RMT2BeanUtility(per);
	    		per = (Person) beanUtil.initializeBean();
	    	}    	
	    	
	    	// Get Address object based on the business Id or person Id.
	    	if (bus.getId() > 0) {
	    		addrList = this.addrApi.findAddrByBusinessId(bus.getId());
	    	}
	    	if (per.getId() > 0) {
	    		addrList = this.addrApi.findAddrByPersonId(per.getId());
	    	}    	
	    	if (addrList != null && addrList.size() > 0 ) {
	    		addr = (Address) addrList.get(0);
	    		zip = this.addrApi.findZipByCode(addr.getZip());
	    	}
	    	else {
	    		addr = this.addrApi.createAddress();
	    		zip = new Zipcode();    		
	    	}

	    	// Get Customer Balance
	    	Double balance = this.getCustomerBalance(cust.getId());
	    	
	      // Prepare to return to client
			this.request.setAttribute(SalesConst.CLIENT_DATA_ADDRESS, addr);
			this.request.setAttribute(SalesConst.CLIENT_DATA_BUSINESS, bus);
			this.request.setAttribute(SalesConst.CLIENT_DATA_PERSON, per);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER, cust);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_ZIP, zip);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTTYPE, custType);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_BALANCE, balance);

	      return;
	  	}
	  	catch (SystemException e) {
	  		throw new GLAcctException(e);
	  	}
	  	catch(ContactBusinessException e) {
	  		throw new GLAcctException(e);
	  	}
	  	catch (ContactPersonException e) {
	  		throw new GLAcctException(e);
	  	}
	  	catch(ContactAddressException e) {
	  		throw new GLAcctException(e);
	  	}
  }

  
  /**
   * Obtins the customer balance.
   * 
   * @param _id The customer Id
   * @return
   */
  protected Double getCustomerBalance(int _id) {
    	// Get Customer Balance
    	Double balance = null;
    	try {
    	   balance = new Double(api.findCustomerBalance(_id));    
    	}
    	catch (CustomerException e) {
    	    System.out.println("[GlCustomerAction.editAction] Customer balance could not be obtained...defaulting to zero.");
    	    balance = new Double(0); 
    	}
    	return balance;
  }
  
  
  /**
   * Applies new and modified Creditor Profiles to the database for all like code tables.
   * <p>
   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
   * at the discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>ADDRESS</td>
	 *   <td>{@link Address}</td>
	 *   <td>Address data</td>
	 * </tr>
	 * <tr>
	 *   <td>BUSINESS</td>
	 *   <td>{@link Business}</td>
	 *   <td>Business data</td>
	 * </tr>
	 * <tr>
	 *   <td>CUSTOMER</td>
	 *   <td>{@link Customer}</td>
	 *   <td>Costomer data</td>
	 * </tr>    
	 * <tr>
	 *   <td>PERSON</td>
	 *   <td>{@link Person}</td>
	 *   <td>Person data</td>
	 * </tr>
	 * <tr>
	 *   <td>ZIP</td>
	 *   <td>{@link Zipcode}</td>
	 *   <td>Address object</td>
	 * </tr>
	 * <tr>
	 *   <td>CUSTTYPE</td>
	 *   <td>String</td>
	 *   <td>Customer Type Code</td>
	 * </tr>        
	 *</table>
	 * 
   * @throws CustomerException
   * @throws DatabaseException
   */
  public void saveAction() throws CustomerException, DatabaseException {
	  	String method = "saveAction";
	  	int  key = 0;
	
	  	Address addr = null;
	  	Business bus = null;
	  	Customer cust = null;
	  	Person per = null;
	  	Zipcode zip = null;
	  	String custType = null;
	  	Double balance = null;
	
	  	try {
	      // Get selected customer type
	      custType = this.request.getParameter("ContactType");
	       
	    	// Get Creditor object
	    	cust = GlAccountsFactory.createCustomer(this.request);
	
	    	// Get Business Object.
	    	bus = ContactsFactory.createBusiness(this.request);
	    	
	    	// Get Person Object.
	    	per = ContactsFactory.createPerson(this.request);
	
	    	// Get Address object.
   		addr = ContactsFactory.createAddress(this.request);
	
	      // Get Zipcode profile to return back to client for display.
	      zip = addrApi.findZipByCode(addr.getZip());
	      if (zip == null) {
	      	zip = ContactsFactory.createZipcode();
	      }
	      
	      // Ensure that person and business objects are mutually exclusive.
	      if (custType.equals("1")) {
	          per.setId(0);
	      }
	      if (custType.equals("2")) {
	          bus.setId(0);
	      }
	
	      // Perform validations
	      this.validateCustomerRequest(custType, cust, bus, per, addr, zip );
	
	      // Begin to apply database updates
	      if (custType.equals(ContactsConst.CONTACT_TYPE_BUSINESS)) {
	      	// Apply Business Updates to database
				  key = busApi.maintainBusiness(bus);
				  
				  //  Ensure that customer type is set properly
				  cust.setBusinessId(key);
				  addr.setBusinessId(key);
	      }
	      if (custType.equals(ContactsConst.CONTACT_TYPE_PERSONAL)) {
	      	// Apply Person Updates to database
				key = perApi.maintainPerson(per);
				  
	        //  Ensure that customer type is set properly
			  cust.setPersonId(key);
			  addr.setPersonId(key);
	      }
	
			// Apply Address Updates to database
			addrApi.maintainAddress(addr);

	      // Apply Customer Updates to database
	      key = api.maintainCustomer(cust, null);
	      
	    	// Get Customer Balance
	    	balance = this.getCustomerBalance(cust.getId()); 
	
	      this.transObj.commitTrans();
	      return;
	  	}
	  	catch (SystemException e) {
	  	    this.transObj.rollbackTrans();
			 System.out.println("SystemExceptin: " + e.getErrorCode() + ": " + e.getMessage());
		    throw new CustomerException(e.getErrorCode() + ": " + e.getMessage());
	  	}
	  	catch (ContactBusinessException e) {
			errors.put("error", e.getErrorCode() + ": " + e.getMessage());
			this.request.setAttribute("ERRORS", this.errors);
			System.out.println("ContactBusinessException: " + e.getErrorCode() + ": " + e.getMessage());
			this.transObj.rollbackTrans();
		   throw new CustomerException(e);
	  	}
	  	catch (ContactPersonException e) {
			errors.put("error", e.getErrorCode() + ": " + e.getMessage());
			this.request.setAttribute("ERRORS", this.errors);
			System.out.println("ContactPersonException: " + e.getErrorCode() + ": " + e.getMessage());
			this.transObj.rollbackTrans();
		   throw new CustomerException(e);
	  	}  	
	  	catch (ContactAddressException e) {
			errors.put("error", e.getErrorCode() + ": " + e.getMessage());
			this.request.setAttribute("ERRORS", this.errors);
			System.out.println("ContactAddressException: " + e.getErrorCode() + ": " + e.getMessage());
			this.transObj.rollbackTrans();
		   throw new CustomerException(e);
	  	}
	  	catch (CustomerException e) {
			errors.put("error", e.getErrorCode() + ": " + e.getMessage());
			this.request.setAttribute("ERRORS", this.errors);
			System.out.println("CreditorException: " + e.getErrorCode() + ": " + e.getMessage());
			this.transObj.rollbackTrans();
		   throw e;
		}
		finally {
		   // Prepare data to be returned  to client regardless of the outcome of transaction.
			this.request.setAttribute("ADDRESS", addr);
			this.request.setAttribute("BUSINESS", bus);
		   this.request.setAttribute("CUSTOMER", cust);
		   this.request.setAttribute("PERSON", per);
		   this.request.setAttribute("ZIP", zip);
		   this.request.setAttribute("CUSTTYPE", custType);
		   this.request.setAttribute("BALANCE", balance);
		}
  }


  /**
   * Creates new Creditor, Business, Address, and Zipcode object  to be used for adding a new Creditor Profile.
   * <p>
   * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object to be handle
   * at the discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>ADDRESS</td>
	 *   <td>{@link Address}</td>
	 *   <td>Address data</td>
	 * </tr>
	 * <tr>
	 *   <td>BUSINESS</td>
	 *   <td>{@link Business}</td>
	 *   <td>Business data</td>
	 * </tr>
	 * <tr>
	 *   <td>CUSTOMER</td>
	 *   <td>{@link Customer}</td>
	 *   <td>Costomer data</td>
	 * </tr>    
	 * <tr>
	 *   <td>PERSON</td>
	 *   <td>{@link Person}</td>
	 *   <td>Person data</td>
	 * </tr>
	 * <tr>
	 *   <td>ZIP</td>
	 *   <td>{@link Zipcode}</td>
	 *   <td>Address object</td>
	 * </tr>
	 * <tr>
	 *   <td>CUSTTYPE</td>
	 *   <td>String</td>
	 *   <td>Customer Type Code</td>
	 * </tr>        
	 *</table>
	 * 
   * @throws SystemException
   */
  public void addAction() throws SystemException {
      String method = "addAction";
	  	Address addr = null;
	  	Business bus = null;
	  	Person per = null;
	  	Customer cust = null;
	  	Zipcode zip = null;
	  	String custType = "";
	  	RMT2BeanUtility beanUtil = null;

	  	try {
	    	// Get Customer object
	  		cust = GlAccountsFactory.createCustomer();
	  		cust = this.api.createCustomer();
	    	beanUtil = new RMT2BeanUtility(cust);
	    	cust = (Customer) beanUtil.initializeBean();
	
	    	// Get Business Object.
	    	bus = ContactsFactory.createBusiness();
	  		bus = this.busApi.createBusiness();
	    	beanUtil = new RMT2BeanUtility(bus);
	    	bus = (Business) beanUtil.initializeBean();
	
	    	// Get Person Object.
	    	per = ContactsFactory.createPerson();
	  		per = this.perApi.createPerson();
	    	beanUtil = new RMT2BeanUtility(per);
	    	per = (Person) beanUtil.initializeBean();
	    	
	    	// Get Address object.
	   		addr = ContactsFactory.createAddress();
	  		addr = this.addrApi.createAddress();
	    	beanUtil = new RMT2BeanUtility(addr);
	    	addr = (Address) beanUtil.initializeBean();
	    	
	      // Get Zipcode profile to return back to client for display.
	      zip = ContactsFactory.createZipcode();
	
	      return;
	
	  	}
	  	catch (SystemException e) {
	  		throw new SystemException(e);
	  	}
	  	finally {
	      // Prepare data in order to return to the client
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_ADDRESS, addr);
			this.request.setAttribute(SalesConst.CLIENT_DATA_BUSINESS, bus);
			this.request.setAttribute(SalesConst.CLIENT_DATA_PERSON, per);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER, cust);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_ZIP, zip);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTTYPE, custType);
	  		this.request.setAttribute(SalesConst.CLIENT_DATA_BALANCE, new Double(0));
		}
  }


  /**
   * Validates all aspects of the customer request.
   * 
   * @param _custType  Indicates the type of customer (Person or Business) that is being validated.
   * @param _cust  General customer data represented as {@link Customer}.
   * @param _bus   Customer's contact profile which is of type {@link Business}.  This is applicable when _custType is equal to 'Business'.
   * @param _per   Customer's contact profile which is of type {@link Person}.  This is applicable when _custType is equal to 'Person'.
   * @param _addr  Customer's address profile which is of type {@link Address}.
   * @param _zip  Ccustomer's zip code profile which is of type {@link Zipcode}.
   * @throws CustomerException
   */  
	private void validateCustomerRequest(String _custType, Customer _cust, Business _bus, Person _per, Address _addr, Zipcode _zip ) throws CustomerException {
		
		 int key = 0;
			
	    try {
		  	//validate objects
		  	if (_custType == null || _custType.equals("")) {
		  	   throw new CustomerException(this.dbConn, 421, null);
		  	}
		  	 
		  	if (_custType.equals(ContactsConst.CONTACT_TYPE_BUSINESS)) {
		  		this.busApi.validateBusiness(_bus);
					key = Integer.valueOf(this.request.getParameter("BusinessId").trim()).intValue();
		      _bus.setId(key);
		      _addr.setBusinessId(key);
		      _cust.setBusinessId(key);
		  	}
		  	
		  	if (_custType.equals(ContactsConst.CONTACT_TYPE_PERSONAL)) {
		  		this.perApi.validatePerson(_per);
					key = Integer.valueOf(this.request.getParameter("PersonId").trim()).intValue();
		      _per.setId(key);      		
		      _addr.setPersonId(key);
		      _cust.setPersonId(key);
		  	}
		  
		   // handle address id
			key = Integer.valueOf(this.request.getParameter("AddressId").trim()).intValue();
		   _addr.setId(key);
			try {
				int tempZip = Integer.valueOf(_zip.getZip()).intValue();
				_addr.setZip(tempZip);
			}
			catch (NumberFormatException e) {
				_addr.setZip(0);
			}			
		  	return;
	    }
		 catch (NumberFormatException e) {
			throw new CustomerException("Update Failed: could not properly obtain Business Id from form.", -1);
		 }
		 catch (ContactBusinessException e) {
			throw new CustomerException(e);
		 }
		 catch (ContactPersonException e) {
			throw new CustomerException(e);
		 }  	
	}  
	
    protected void receiveClientData() throws ActionHandlerException{}
      protected void sendClientData() throws ActionHandlerException{}
      public void add() throws ActionHandlerException{}
      public void edit() throws ActionHandlerException{}
      public void save() throws ActionHandlerException{}
      public void delete() throws ActionHandlerException{}

}
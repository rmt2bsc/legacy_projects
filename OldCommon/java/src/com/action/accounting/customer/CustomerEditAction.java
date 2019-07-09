package com.action.accounting.customer;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.ActionHandlerException;
import com.api.db.DatabaseException;
import com.api.DaoApi;
import com.api.db.orm.DataSourceFactory;

import com.bean.Address;
import com.bean.Business;
import com.bean.Customer;
import com.bean.Person;
import com.bean.Zipcode;

import com.constants.ContactsConst;


import com.util.ContactBusinessException;
import com.util.ContactPersonException;
import com.util.CustomerException;
import com.util.SystemException;


/**
 * This class provides action handlers needed to serve Customer Maintenance 
 * Edit user interface requests.
 * 
 * @author Roy Terrell
 *
 */ 
public class CustomerEditAction extends CustomerAction {
    private static final String COMMAND_SAVE = "Accounting.CustomerEdit.save";
    private static final String COMMAND_BACK = "Accounting.CustomerEdit.back";
    
	private Logger logger;
	
	
      /**
       * Default constructor
       *
       */
      public CustomerEditAction()  {
          super(); 
          logger = Logger.getLogger("CustomerEditAction");
      }
      
      /**
        * Main contructor for this action handler.  
        * 
        * @param _context The servlet context to be associated with this action handler
        * @param _request The request object sent by the client to be associated with this action handler
        * @throws SystemException
        */
      public CustomerEditAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
          super(_context, _request);
          logger = Logger.getLogger(CustomerEditAction.class);
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
          
          if (command.equalsIgnoreCase(CustomerEditAction.COMMAND_SAVE)) {
              this.saveData();
          }
          if (command.equalsIgnoreCase(CustomerEditAction.COMMAND_BACK)) {
              this.doBack();
          }
      }
      
  	/**
  	 * Applies creditor edit changes to the database.
  	 * 
  	 * @throws ActionHandlerException
  	 */
  	public void save() throws ActionHandlerException {
		int key;
	  	Object results[];
	  	DaoApi dao = DataSourceFactory.createDao(this.dbConn);
	  	
    	// Get Zip object	  	
  	    try {
  	    	String zipValue = String.valueOf(this.addr.getZip());
  	    	this.zip.addCriteria("Zip", zipValue);
  	    	results = dao.retrieve(zip);
  	    	if (results.length > 0) {
  	    		this.zip = (Zipcode) results[0];
  	    	}
  	    }
  	    catch (Exception e) {
  	    	logger.log(Level.ERROR, e.getMessage());
  	    	throw new ActionHandlerException(e.getMessage());
  	    }	  	  	 
  	    
  	    // Ensure that person and business objects are mutually exclusive.  	    
  	    if (custType.equals(ContactsConst.CONTACT_TYPE_BUSINESS)) {
  	    	this.person.setId(0);
  	    }
  	    if (custType.equals(ContactsConst.CONTACT_TYPE_PERSONAL)) {
  	    	this.bus.setId(0);
  	    }
  	    
  	    // Perform validations
    	this.validateCustomerRequest(this.custType, this.customer, this.bus, this.person, this.addr, this.zip );	
  	    
  	    try {
  	  	    // Begin to apply database updates
  	  	    if (custType.equals(ContactsConst.CONTACT_TYPE_BUSINESS)) {
  	  	    	// Apply Business Updates to database
  	  	    	key = busApi.maintainBusiness(this.bus);
  					  
  	  	    	//  Ensure that customer type is set properly
  	  	    	this.customer.setBusinessId(key);
                this.customer.setPersonId(0);
  	  	    	addr.setBusinessId(key);
  	  	    }
  	  	    if (custType.equals(ContactsConst.CONTACT_TYPE_PERSONAL)) {
  	  	    	// Apply Person Updates to database
  	  	    	key = perApi.maintainPerson(this.person);
  					  
  	  	    	//  Ensure that customer type is set properly
  	  	    	this.customer.setPersonId(key);
                this.customer.setBusinessId(0);
  	  	    	addr.setPersonId(key);
  	  	    }
  	  	    // Apply Address Updates to database
  	  	    addrApi.maintainAddress(addr);
  	  	    // Apply Customer Updates to database
  	  	    key = this.custApi.maintainCustomer(this.customer, null);
  	  	    this.customer.setId(key);
  	    	// Get Customer Balance
  	    	balance = this.getBalance(this.customer.getId()); 
  	    	this.transObj.commitUOW();
  	    	this.msg = "Customer profile successfully updated";
  	    	return;
  	    	
  	    }
  	    catch (Exception e) {
  	    	this.transObj.rollbackUOW();
  	    	logger.log(Level.ERROR, e.getMessage());
  	    	throw new ActionHandlerException(e.getMessage());
  	    }
  	    
  	}
  	
  	 /**
     * Validates all aspects of the request to update Customer.
     * 
     * @param _custType  Indicates the type of customer (Person or Business) that is being validated.
     * @param _cust  General customer data represented as {@link Customer}.
     * @param _bus   Customer's contact profile which is of type {@link Business}.  This is applicable when _custType is equal to 'Business'.
     * @param _per   Customer's contact profile which is of type {@link Person}.  This is applicable when _custType is equal to 'Person'.
     * @param _addr  Customer's address profile which is of type {@link Address}.
     * @param _zip  Ccustomer's zip code profile which is of type {@link Zipcode}.
     * @throws CustomerException
     */  
  	private void validateCustomerRequest(String _custType, Customer _cust, Business _bus, Person _per, Address _addr, Zipcode _zip ) throws ActionHandlerException {
  		int key = 0;
  	    try {
  		  	//validate objects
  		  	if (_custType == null || _custType.equals("")) {
  		  	   throw new ActionHandlerException(this.dbConn, 421, null);
  		  	}
  		  	 
  		  	if (_custType.equals(ContactsConst.CONTACT_TYPE_BUSINESS)) {
  		  		this.busApi.validateBusiness(_bus);
				key = Integer.valueOf(this.request.getParameter("BusinessId").trim()).intValue();
  		      	_bus.setId(key);
  		      	_addr.setBusinessId(key);
                _addr.setPersonId(0);
  		      	_cust.setBusinessId(key);
  		  	}
  		  	
  		  	if (_custType.equals(ContactsConst.CONTACT_TYPE_PERSONAL)) {
  		  		this.perApi.validatePerson(_per);
				key = Integer.valueOf(this.request.getParameter("PersonId").trim()).intValue();
				_per.setId(key);      		
  		      	_addr.setPersonId(key);
                _addr.setBusinessId(0);
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
  	    	throw new ActionHandlerException("Update Failed: could not properly obtain Business Id from form.", -1);
  	    }
  	    catch (ContactBusinessException e) {
  			throw new ActionHandlerException(e);
  	    }
  	    catch (ContactPersonException e) {
  			throw new ActionHandlerException(e);
  	    }  	
  	}  
  	
  	/**
  	 * No Action.
  	 * 
  	 * @throws ActionHandlerException
  	 */
  	protected void doBack() throws ActionHandlerException {
  		return;
  	}
}
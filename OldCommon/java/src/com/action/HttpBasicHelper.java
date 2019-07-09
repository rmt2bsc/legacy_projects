package com.action;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.api.db.DatabaseException;
import com.bean.Person;
import com.bean.Business;
import com.bean.Address;
import com.bean.Zipcode;

import com.factory.ContactsFactory;

import com.util.SystemException;



/**
 * This abstract class provides functionality obtaining basic data pertaining
 *  to Person, Business, Address, and Zipcode objects from the client's request 
 *  object.
 * 
 * @author Roy Terrell
 *
 */ 
public class HttpBasicHelper extends AbstractActionHandler {
      protected final static String CLIENT_ITEM_SELECTOR = "selCbx";
      protected final static String CLIENT_ITEM_ROWID = "rowId";
      protected static final String ITEM_COUNT_IND = "itemcount";
      
      private Logger logger;
      private Person person;
      private Business business;
      private Address address;
      private Zipcode zip;
      protected int selecteRow;
      
	
	  /**
	   * Default constructor
	   *
	   */
	  public HttpBasicHelper()  {
		  super(); 
		  logger = Logger.getLogger("HttpBasicHelper");
	  }
	  
	  /**
		* Main contructor for this action handler.  
		* 
		* @param _context The servlet context to be associated with this action handler
		* @param _request The request object sent by the client to be associated with this action handler
		* @throws SystemException
		*/
	  public HttpBasicHelper(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {
		  super(_context, _request);
	  }
      
	  /**
	   * Initializes this object using _conext and _request.  This is needed in the 
	   * event this object is inistantiated using the default constructor.
	   * 
	   * @throws SystemException
	   */
	  protected void init(ServletContext _context, HttpServletRequest _request) throws SystemException {
	      super.init(_context, _request);
          logger = Logger.getLogger(HttpBasicHelper.class);
	  }

      
      
		/**
		 * Combines the process of probing the client's HttpServletRequest object for basic  
         * data (Person, Business, Address, and Zipcode).  After invoking this method, be 
         * sure to call the getter methods to obtain the results.  
		 * 
		 * @return Transaction object.
		 * @throws XactException
		 */
	  public void getHttpCombined() throws SystemException {
		  this.person = this.getHttpPerson();
		  this.business = this.getHttpBusiness();
		  this.address = this.getHttpAddress();
		  this.zip = this.getHttpZip();
		  return;
	  }
	  
		  
	  /**
	   * Attempts to obtain person data from the client JSP.
	   * Determines client data exist in the form of a list or a single record.
	   *  
	   * @return {@link Person}- Contains the Person data retrieved from the client or a 
	   *  newly initialized Person object when person_id is not found.
	   * @throws SalesOrderException
	   */
	  public Person getHttpPerson() throws SystemException {
		  Person obj = ContactsFactory.createPerson();	 
		   String temp = null;
		   String subMsg = null;
		   boolean listPage = true;
           int perId = 0;
	      
		   // Determine if we are coming from a page that presents data as a list or as single record.
		   // Get selected row number from client page.  If this row number exist, then we 
	       // are coming from page that contains lists of information.
		   subMsg = "Person id could not be  identified from client";
		   temp = this.request.getParameter("PersonId" + this.selecteRow);
		   if (temp == null) {
		       listPage = false;
		   }
		   if (!listPage) {
		       // Get order id for single row
		       temp = this.request.getParameter("PersonId");
           }
		   
		   // Validate value
           try {
               perId = Integer.parseInt(temp);
           }
		   catch (NumberFormatException e) {
		       logger.log(Level.INFO, subMsg);
               this.person = obj;
		       return obj;
		   }
		   
		   // Get Transaction object
		   try {
			   ContactsFactory.packageBean(this.request, obj);
               this.person = obj;
               this.person.setId(perId);
               return obj;
		   }
		   catch (SystemException e) {
			   logger.log(Level.ERROR,  e.getMessage());
		       throw e;
		   }
	  }

	  
	  
	  /**
	   * Attempts to obtain Business data from the client JSP.
	   * Determines client data exist in the form of a list or a single record.
	   *  
	   * @return {@link Business}- Contains the business data retrieved from the client or a 
	   *  newly initialized Business object when business id is not found.
	   * @throws SystemException
	   */
	  public Business getHttpBusiness() throws SystemException {
		   Business obj = ContactsFactory.createBusiness();	 
		   String temp = null;
		   String subMsg = null;
		   boolean listPage = true;
           int busId = 0;
	      
		   // Determine if we are coming from a page that presents data as a list or as single record.
		   // Get selected row number from client page.  If this row number exist, then we 
	       // are coming from page that contains lists of information.
		   subMsg = "Business id could not be  identified from client";
		   temp = this.request.getParameter("BusinessId" + this.selecteRow);
		   if (temp == null) {
		       listPage = false;
		   }
		   if (!listPage) {
		       // Get order id for single row
		       temp = this.request.getParameter("BusinessId");
           }
		   // Validate value		   
           try {
               busId = Integer.parseInt(temp);
           }
		   catch (NumberFormatException e) {
		       logger.log(Level.INFO, subMsg);
               this.business = obj;
		       return obj;
		   }
		   
		   // Get Transaction object
		   try {
			   ContactsFactory.packageBean(this.request, obj);
               this.business = obj;
               this.business.setId(busId);
               return obj;
		   }
		   catch (SystemException e) {
			   logger.log(Level.ERROR,  e.getMessage());
		       throw e;
		   }
	  }
	  
	  
	  
	  /**
	   * Attempts to obtain Address data from the client JSP.
	   * Determines client data exist in the form of a list or a single record.
	   *  
	   * @return {@link Address}
	   * @throws SystemException
	   */
	  public Address getHttpAddress() throws SystemException {
		  Address obj = ContactsFactory.createAddress();
		   String temp = null;
		   String subMsg = null;
		   boolean listPage = true;
           int addrId = 0;
	      
		   // Determine if we are coming from a page that presents data as a list or as a single record.
		   // Get selected row number from client page.  If this row number exist, then we 
	       // are coming from page that contains lists of information
		   subMsg = "Address id could not be  identified from client";
		   temp = this.request.getParameter("AddressId" + this.selecteRow);
		   if (temp == null) {
		       listPage = false;
		   }
		   if (!listPage) {
		       // Get order id for single row
		       temp = this.request.getParameter("AddressId");
           }
		   
		   // Validate value		   
           try {
               addrId = Integer.parseInt(temp);
           }
		   catch (NumberFormatException e) {
		       logger.log(Level.INFO, subMsg);
               this.address = obj;
		       return obj;
		   }
		   
		   // Get Transaction object
		   try {
			   ContactsFactory.packageBean(this.request, obj);
               this.address = obj;
               this.address.setId(addrId);
               return obj;
		   }
		   catch (SystemException e) {
			   logger.log(Level.ERROR,  e.getMessage());
		       throw e;
		   }
	  }
	  
      /**
       * Obtains zipcode data fromthe client Http request object.   
       * 
       * @return Currently, a newly instantiated Zipcode object is returned to the user.
       * @throws SystemException
       */
	  public Zipcode getHttpZip() throws SystemException {
		  try {
			  this.zip = new Zipcode();
			  return zip;
		  }
		  catch (Exception e) {
			  throw new SystemException(e.getMessage());
		  }	  	  	
	  }
	  
	  
      /**
       * Gets Person object.
       * 
       * @return {@link Person}
       */
      public Person getPerson() {
          return this.person;
      }
      
      /**
       * Gets Business object.
       * 
       * @return {@link Business}
       */
      public Business getBusiness() {
          return this.business;
      }
      
      /**
       * Gets Address object.
       * 
       * @return {@link Address}
       */
      public Address getAddress() {
          return this.address;
      }

      /**
       * Get Zip code object
       * 
       * @return {@link Zipcode}
       */
      public Zipcode getZip() {
    	  return this.zip;
      }
      
      /**
       * Sets the value of the selected row contained in the client's request.
       * 
       * @param rowThe row number selected
       */
      public void setSelectedRow(int row) {
    	  this.selecteRow = row;
      }
      
      protected void receiveClientData() throws ActionHandlerException{}
      protected void sendClientData() throws ActionHandlerException{}
      public void add() throws ActionHandlerException{}
      public void edit() throws ActionHandlerException{}
      public void save() throws ActionHandlerException{}
      public void delete() throws ActionHandlerException{}
   
}
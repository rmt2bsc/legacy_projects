package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Hashtable;

import com.api.GLCreditorApi;
import com.api.ContactBusinessApi;
import com.api.ContactAddressApi;

import com.bean.Business;
import com.bean.Address;
import com.bean.Creditor;
import com.bean.CreditorCombine;
import com.bean.RMT2TagQueryBean;
import com.bean.Zipcode;

import com.factory.ContactsApiFactory;

import com.action.AbstractActionHandler;

import com.factory.ContactsFactory;
import com.factory.AcctManagerFactory;
import com.factory.GlAccountsFactory;

import com.util.ActionHandlerException;
import com.util.RMT2BeanUtility;
import com.util.SystemException;
import com.util.DatabaseException;
import com.util.GLAcctException;
import com.util.ContactBusinessException;
import com.util.ContactAddressException;
import com.util.CreditorException;
import com.util.RMT2Utility;

import com.constants.RMT2ServletConst;

/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating 
 * Creditor/Vendor information.
 * 
 * @author Roy Terrell
 *
 */
public class GlCreditorAction extends AbstractActionHandler {

  private GLCreditorApi api;
  private final String PAGE_TITLE = "GL Creditor Maintenance";
  private Hashtable errors = new Hashtable();

  /**
	* Main contructor for this action handler.
	* 
	* @param _context The servlet context to be associated with this action handler
	* @param _request The request object sent by the client to be associated with this action handler
	* @throws SystemException
	*/
  public GlCreditorAction(ServletContext _context, HttpServletRequest _request) throws SystemException, DatabaseException {

    super(_context, _request);
    this.className = "GlCreditorAction";
    api = AcctManagerFactory.createCreditor(this.dbConn);
  }

  
  /**
   * Displays the Creditor Search Console for the first time rendering the search criteria section with blank values and the 
   * Customer Search Results section with an empty result set.
   *
   */
  public void displayCreditorSearchConsole() {
      CreditorCombine cc = null;
      
      try {
          cc = new CreditorCombine();
      }
      catch (SystemException e) {
          
      }

	   RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	   query.setWhereClause(null);
	   
	   // Force an empty result set by purposely constructing erroneous  selection criteria.
	   query.setWhereClause("account_no = '-1' ");
	   query.setQuerySource("VwCreditorBusinessView");
	   query.setCustomObj(cc);
	   this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
	   return;
  }
  
  
  /**
   *  Retrieves all creditors in the form of an ArrayList of Creditor bean objects and initialized the HttpServletRequest object with
   *  the ArrayList.
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
	 *   <td>acctTypes</td>
	 *   <td>ArrayList</td>
	 *   <td>A list of {@link GlAccounts} objects</td>
	 * </tr>
	 *</table>
	 *  
   * @return int - Count of all creditor records obtained.
   * @throws GLAcctException
   */
  public int getCreditorsAction() throws GLAcctException {
      String method = "getCreditorsAction()";
      HttpServletRequest req = this.getRequest();
      CreditorCombine cc = null;
	   ArrayList list;
	   String temp = null;
	   String custType = null;
	   String msg = null;


	   // Decode request
	   try {
		   cc = new CreditorCombine();
		   GlAccountsFactory.packageBean(req, cc);
	   }
	   catch (SystemException e) {
	       System.out.println("[GlCreditorAction.getCreditorsAction] " + e.getMessage());
	   }
	   
	   // Get SQL Selection criteria.
	   String criteria = this.buildCreditorSelectionCriteria(cc);
	   
	   // Set the SQL selection criteria, name of datasource view, and customer selection criteria data values on the session
	   RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
	   query.setWhereClause(null);
	   query.setWhereClause(criteria);
	   query.setQuerySource("VwCreditorBusinessView");
	   query.setCustomObj(cc);
	   this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);      		 
	
	   // Send data basck to the client
	   //req.setAttribute("data", list);
	   //this.getSession().setAttribute(RMT2ServletConst.SEARCH_CRITERIA, cc);
	   //return list.size();
	   return 1;
  }

  /**
   * Builds the SQL seclection criteria from the JSP request object.  This method will include in the SQL selection critera all general values 
   * that have entered and include either those values that are a part of the personal profile or business profile based on the customer type.
   * 
   * @param _cc The selection criteria data values coming from the Customer Search Console. 
   * @return SQL selection criteria as a String type.
   */
  private String buildCreditorSelectionCriteria(CreditorCombine _cc) {
      StringBuffer criteria = new StringBuffer(100);
      String temp = null;
      
	   temp = _cc.getAccountNo();
	   if (temp != null && !temp.equals("")) {
	       criteria.append("account_no = '");
	       criteria.append(temp);
	       criteria.append("' ");
	   }
	   
	   int intTemp = _cc.getCreditorTypeId();
	   if (intTemp > 0) {
		   if (criteria.length() > 0) { 
		       criteria.append(" and ");
		   }
		   criteria.append(" creditor_type_id = ");
		   criteria.append(intTemp);
	   }
	   
	   temp = this.getBusinessCriteria(_cc);
	   if (temp != null && !temp.equals("")) {
	       if (criteria.length() > 0) { 
			     criteria.append(" and ");
			 }
			 criteria.append(temp);
		}
	   return criteria.toString();
  }

  /**
   * Builds creditor criteria based on the creditr's business profile.
   * 
   * @param _cc
   * @return String - the selection criteria.
   */
  private String getBusinessCriteria(CreditorCombine _cc) {
      StringBuffer criteria = new StringBuffer(100);
      String temp = null;
      
      temp = _cc.getBusinessLongname();
      if (temp != null && !temp.equals("")) {
          criteria.append("longname like '");
          criteria.append(temp);
          criteria.append("%'");
      }
      
      temp = _cc.getBusinessContactFirstname();
      if (temp != null && !temp.equals("")) {
          if (criteria.length() > 0) {
              criteria.append(" and ");
          }
          criteria.append("contact_firstname like '");
          criteria.append(temp);
          criteria.append("%'");
      }
      
      temp = _cc.getBusinessContactLastname();
      if (temp != null && !temp.equals("")) {
          if (criteria.length() > 0) {
              criteria.append(" and ");
          }
          criteria.append("contact_lastname like '");
          criteria.append(temp);
          criteria.append("%'");
      }
      
      temp = _cc.getBusinessContactPhone();
      if (temp != null && !temp.equals("")) {
          if (criteria.length() > 0) {
              criteria.append(" and ");
          }
          criteria.append("contact_phone like '");
          criteria.append(temp);
          criteria.append("%'");
      }
      
      temp = _cc.getBusinessTaxId();
      if (temp != null && !temp.equals("")) {
          if (criteria.length() > 0) {
              criteria.append(" and ");
          }
          criteria.append("tax_id = '");
          criteria.append(temp);
          criteria.append("'");
      }
      
      temp = _cc.getBusinessWebsite();
      if (temp != null && !temp.equals("")) {
          if (criteria.length() > 0) {
              criteria.append(" and ");
          }
          criteria.append("website like '");
          criteria.append(temp);
          criteria.append("%'");
      }
      
      return criteria.toString();
  }

    /**
     * Retrieves a row from a list of creditors that exist in the HTTP Request object, retrieves from the database and packages the
     * the data into a Creditor, Business, Address, and Zipcode object, and initializes the request object with data to be used by the client.
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
	 *   <td>CREDITOR</td>
	 *   <td>{@link Creditor}</td>
	 *   <td>Costomer data</td>
	 * </tr>    
	 * <tr>
	 *   <td>ZIP</td>
	 *   <td>{@link Zipcode}</td>
	 *   <td>Address object</td>
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
  	ContactBusinessApi busApi = null;
  	ContactAddressApi addrApi = null;
  	Address addr = null;
  	Business bus = null;
  	Creditor cred = null;
  	Zipcode zip = null;
  	ArrayList addrList = null;
  	RMT2BeanUtility beanUtil = null;
  	boolean retrieveFromList = true;

  	try {
    	 busApi = ContactsApiFactory.createBusinessApi(this.dbConn);
    	 addrApi = ContactsApiFactory.createAddressApi(this.dbConn);

    	 // Get Creditor object
    	 try {
	        row = this.getSelectedRow("selCbx");
	    	  temp = this.request.getParameter("CreditorId" + row);
		    	  
	    	  // If Creditor Id is not found through indexing customer id, then try to find customer id as a single occurrence.
	    	  if (temp == null) {
	    	      throw new SystemException("");
	    	  }
  	    }
  	    catch (SystemException e) {
  	        retrieveFromList = false;
  	        // We are not dealing with a list...try single occurrence.
  	        temp = this.request.getParameter("CreditorId");
            if (temp == null) {
                throw new GLAcctException("A creditor/vendor must be selected");
            }
  	    }
  	    
  	    // Get the creditor id.
  	    key = RMT2Utility.stringToNumber(temp).intValue();
    	
    	 // Get Customer object
  	    cred = api.findCreditorById(key);

    	 // Get Business Object.
		if (retrieveFromList) {
		   temp = this.request.getParameter("BusinessId" + row);    
		}
		else {
		   temp = this.request.getParameter("BusinessId");
		}
		if (temp != null && !temp.equals("")) {
		   key = RMT2Utility.stringToNumber(temp).intValue();
		}
		else {
		    key = 0;
		}
		 
		if (key > 0) {
    		bus = busApi.findBusById(key);
		}
		else {
		    bus = busApi.createBusiness();
	       beanUtil = new RMT2BeanUtility(bus);
	       bus = (Business) beanUtil.initializeBean();  
	   }

    	 // Get Address object based on the business Id.
    	 addrList = addrApi.findAddrByBusinessId(bus.getId());
    	 if (addrList.size() > 0 ) {
    		 addr = (Address) addrList.get(0);
    		 zip = addrApi.findZipByCode(addr.getZip());
    	 }

  		 // Get Creditor's Balance
    	 Double balance = null;
    	 try {
    	    balance = new Double(api.findCreditorBalance(cred.getId()));    
    	 }
    	 catch (CreditorException e) {
    	     System.out.println("[GlCreditorAction.editAction] Customer balance could not be obtained...defaulting to zero.");
    	     balance = new Double(0); 
    	  }
    	 
       // Prepare to return to client
 		 this.request.setAttribute("ADDRESS", addr);
 		 this.request.setAttribute("BUSINESS", bus);
  		 this.request.setAttribute("CREDITOR", cred);
  		 this.request.setAttribute("ZIP", zip);
  		 this.request.setAttribute("BALANCE", balance);


       return;
  	}
  	catch (SystemException e) {
  		throw new GLAcctException(e);
  	}
  	catch(ContactBusinessException e) {
  		throw new GLAcctException(e);
  	}
  	catch(ContactAddressException e) {
  		throw new GLAcctException(e);
  	}
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
	 *   <td>CREDITOR</td>
	 *   <td>{@link Creditor}</td>
	 *   <td>Costomer data</td>
	 * </tr>    
	 * <tr>
	 *   <td>ZIP</td>
	 *   <td>{@link Zipcode}</td>
	 *   <td>Address object</td>
	 * </tr>
	 *</table>
	 * 
 * @throws CreditorException
 * @throws DatabaseException
 */
  public void saveAction() throws CreditorException, DatabaseException {
  	String method = "saveAction";
  	int  key = 0;
  	ContactBusinessApi busApi = null;
  	ContactAddressApi addrApi = null;
  	Address addr = null;
  	Business bus = null;
  	Creditor cred = null;
  	Zipcode zip = null;
  	Double balance = null;

  	try {
    	busApi = ContactsApiFactory.createBusinessApi(this.dbConn);
    	addrApi = ContactsApiFactory.createAddressApi(this.dbConn);

    	// Get Creditor object
    	cred = GlAccountsFactory.createCreditor(this.request);

    	// Get Business Object.
    	bus = ContactsFactory.createBusiness(this.request);

    	// Get Address object.
   	addr = ContactsFactory.createAddress(this.request);

      // Get Zipcode profile to return back to client for display.
      zip = addrApi.findZipByCode(addr.getZip());
      if (zip == null) {
      	zip = ContactsFactory.createZipcode();
      }

      // Ensure that the business id and the Address Id are properly associated with the Business and Address
      // objects since their key values could have been wrongfully obtain in the previos steps.
      try {
      	//validate objects
      	busApi.validateBusiness(bus);
      	api.validateCreditor(cred, null);
      	      	
			// handle business id
			key = Integer.valueOf(this.request.getParameter("BusinessId").trim()).intValue();
         bus.setId(key);

		}
		catch (NumberFormatException e) {
			throw new SystemException("Update Failed: could not properly obtain Business Id from form.", -1);
		}
      try {
			// handle address id
			key = Integer.valueOf(this.request.getParameter("AddressId").trim()).intValue();
         addr.setId(key);
		}
		catch (NumberFormatException e) {
			throw new SystemException("Update Failed: could not properly obtain Address Id from form.", -1);
		}

		// Apply Business Updates to database
		key = busApi.maintainBusiness(bus);

		// Apply Address Updates to database
		addr.setBusinessId(key);
		try {
			int tempZip = Integer.valueOf(zip.getZip()).intValue();
			addr.setZip(tempZip);
		}
		catch (NumberFormatException e) {
			addr.setZip(0);
		}			
		addrApi.maintainAddress(addr);

      // Apply Creditor Updates to database
      cred.setBusinessId(key);
      key = api.maintainCreditor(cred, null);

      balance = this.getCreditorBalance(cred.getId());
      
      this.transObj.commitTrans();
      return;
  	}
  	catch (SystemException e) {
			this.transObj.rollbackTrans();
			System.out.println("SystemExceptin: " + e.getMessage());
  		throw new CreditorException(e);
  	}
  	catch (ContactBusinessException e) {
			errors.put("error", e.getMessage());
			this.request.setAttribute("ERRORS", errors);
			System.out.println("ContactBusinessException: " + e.getMessage());
			this.transObj.rollbackTrans();
  		throw new CreditorException(e);
  	}
  	catch (ContactAddressException e) {
			errors.put("error", e.getMessage());
			this.request.setAttribute("ERRORS", errors);
			System.out.println("ContactAddressException: " + e.getMessage());
			this.transObj.rollbackTrans();
  		throw new CreditorException(e);
  	}
  	catch (CreditorException e) {
			errors.put("error", e.getMessage());
			this.request.setAttribute("ERRORS", errors);
			System.out.println("CreditorException: " + e.getMessage());
			this.transObj.rollbackTrans();
  		throw e;
		}
		finally {
	      // Prepare data to be returned  to client regardless of the outcome of transaction.
	 		this.request.setAttribute("ADDRESS", addr);
	 		this.request.setAttribute("BUSINESS", bus);
	  		this.request.setAttribute("CREDITOR", cred);
	  		this.request.setAttribute("ZIP", zip);
	  		this.request.setAttribute("BALANCE", balance);
		}

  }

  
  /**
   * Obtins the customer balance.
   * 
   * @param _id The customer Id
   * @return
   */
  protected Double getCreditorBalance(int _id) {
    	// Get Customer Balance
    	Double balance = null;
    	try {
    	   balance = new Double(api.findCreditorBalance(_id));    
    	}
    	catch (CreditorException e) {
    	    System.out.println("[GlCustomerAction.editAction] Customer balance could not be obtained...defaulting to zero.");
    	    balance = new Double(0); 
    	}
    	return balance;
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
	 *   <td>CREDITOR</td>
	 *   <td>{@link Creditor}</td>
	 *   <td>Costomer data</td>
	 * </tr>    
	 * <tr>
	 *   <td>ZIP</td>
	 *   <td>{@link Zipcode}</td>
	 *   <td>Address object</td>
	 * </tr>
	 *</table>
	 * 
 * @throws SystemException
 */
  public void addAction() throws SystemException {
  	String method = "addAction";
  	Address addr = null;
  	Business bus = null;
  	Creditor cred = null;
  	Zipcode zip = null;

  	try {
    	// Get Creditor object
    	cred = GlAccountsFactory.createCreditor();

    	// Get Business Object.
    	bus = ContactsFactory.createBusiness();

    	// Get Address object.
   		addr = ContactsFactory.createAddress();

      // Get Zipcode profile to return back to client for display.
      zip = ContactsFactory.createZipcode();

      return;

  	}
  	catch (SystemException e) {
  		throw new SystemException(e);
  	}
  	finally {
      // Prepare data in order to return to the client
 		this.request.setAttribute("ADDRESS", addr);
 		this.request.setAttribute("BUSINESS", bus);
  		this.request.setAttribute("CREDITOR", cred);
  		this.request.setAttribute("ZIP", zip);
  		this.request.setAttribute("BALANCE", new Double(0));
		}
  }

    /**
     * Gathers all Creditors by calling this.getCreditorsAction method.  This method is used as a response to the user clicking the 
     * application's back button (not the browser).
     * 
     * @return  int Total count of creditor records obtained
     * @throws GLAcctException
     */
	public int backAction() throws GLAcctException {

		String method = "backAction()";
		int count = 0;

		count = this.getCreditorsAction();
		return count;
	}

    protected void receiveClientData() throws ActionHandlerException{}
      protected void sendClientData() throws ActionHandlerException{}
      public void add() throws ActionHandlerException{}
      public void edit() throws ActionHandlerException{}
      public void save() throws ActionHandlerException{}
      public void delete() throws ActionHandlerException{}

}
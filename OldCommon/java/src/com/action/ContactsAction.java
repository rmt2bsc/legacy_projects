package com.action;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

import com.action.AbstractActionHandler;

import com.api.ContactPersonApi;
import com.api.ContactBusinessApi;
import com.api.ContactAddressApi;
import com.api.db.DatabaseException;

import com.bean.RMT2TagQueryBean;
import com.bean.Address;
import com.bean.Business;
import com.bean.Person;
import com.bean.ContactCombine;

import com.factory.ContactsFactory;

import com.util.ContactPersonException;
import com.util.ContactBusinessException;
import com.util.ContactAddressException;
import com.util.ContactException;
import com.util.SystemException;
import com.util.RMT2Utility;

import com.constants.RMT2ServletConst;
import com.constants.ContactsConst;


/**
 * This class provides action handlers to respond to an associated controller for searching, adding, deleting, and validating 
 * person, business, and address contact information.
 * 
 * @author appdev
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContactsAction extends AbstractActionHandler {

  private final String MSG_ADD_NULL_DESCRIPTION = "Cannot locate Description request parameter";
  private final String MSG_ADD_BLANK_DESCRIPTION = "Description must have a value";
  private final String SEARCH_SELECTION_CRITERIA_ID = "ContactMaintSearch";
  private final String SEARCH_SELECTION_CRITERIA_ARGS = "contactArgs";

  private String className;
  private ContactPersonApi perApi;
  private ContactBusinessApi busApi;
  private ContactAddressApi addrApi;


//  TODO: Change the signatures of each method to only throw an UserMaintException exception where applicable.

  /**
   * Main contructor for this action handler.
   * 
   * @param _context The servlet context to be associated with this action handler
   * @param _request The request object sent by the client to be associated with this action handler
   * @throws SystemException
   */
  public ContactsAction(ServletContext _context, HttpServletRequest _request) throws SystemException {

    super(_context, _request);
    this.className = "ContactsAction";

    // Create API's
    try {
			perApi = ContactsFactory.createPersonApi(this.dbConn);
			busApi = ContactsFactory.createBusinessApi(this.dbConn);
			addrApi = ContactsFactory.createAddressApi(this.dbConn);
		}
		catch (DatabaseException e) {
			throw new SystemException(e);
		}

  }

  /**
   * Jump starts the Contact Search and Maintenance process.   This is primarily needed to setup and initialize the search selection
   * criteria javabean which is of type ContactCombine.    ContactCombine is used to recall the previous selection criteria whenever
   * the client returns to the search criteria form after view/maintaining a personal or business contact.  If the client navigates to
   * another module of the application, the search criteria is reset.
   * 
   * @throws SystemException
   */
  public void beginContactSession() throws SystemException {
		ContactCombine contact = ContactsFactory.createcontact();
        this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, new RMT2TagQueryBean());
        this.getSession().setAttribute(SEARCH_SELECTION_CRITERIA_ARGS, contact);
        return;
	}

  /**
   * Gathers all of the contact search criteria from the request object and determines if the criteria is to be used for a business contact or
   * persona contact search.
   * <p>
   * Upon successful completion, the following objects are returned to the client via the HttpSession object to be handle
   * at the discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>ContactMaintSearch</td>
	 *   <td>{@link RMT2TagQueryBean}</td>
	 *   <td>The selection criteria client is to use to  retrieve data</td>
	 * </tr>
	 * <tr>
	 *   <td>contactArgs</td>
	 *   <td>{@link ContactCombine}</td>
	 *   <td>The extended contact object</td>
	 * </tr> 
	 *</table>
	 *   
   * @throws ContactException
   * @throws SystemException
   */
  public void handleSearchAction() throws ContactException, SystemException {

    StringBuffer sql = new StringBuffer(100);
    HttpServletRequest req = this.getRequest();
    String criteria = null;
    String addrCriteria;

    ContactCombine contact = ContactsFactory.createcontact(req);
		if (contact.getContactType().equals(ContactsConst.CONTACT_TYPE_BUSINESS)) {
			criteria = this.getBusinessCriteria(sql, contact);
		}
		if (contact.getContactType().equals(ContactsConst.CONTACT_TYPE_PERSONAL)) {
			criteria = this.getPersonalCriteria(sql, contact);
		}

		// Append address criteria if it exist
		criteria = this.getAddressCriteria(sql, contact);

    RMT2TagQueryBean query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
    query.setWhereClause(null);
    query.setWhereClause(criteria);
    this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, query);
    this.getSession().setAttribute(SEARCH_SELECTION_CRITERIA_ARGS, contact);
    return;
	}

  /**
   * Extracts business criteria from _contact and constructs SQL selection criteria to be used to retrieve business contact data.
   * 
   * @param _sql  StringBuffer contianing the SQL statement used to append the selection criteria.
   * @param _contact object contaning both business and contact contact data.
   * @return  String
   * @throws SystemException
   */
  private String getBusinessCriteria(StringBuffer _sql, ContactCombine _contact)  throws SystemException {

		String strValue;
		int     intValue;
		double numValue;

    intValue = _contact.getBusinessId();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "business.id", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    strValue = _contact.getBusContactFirstname();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "business.contact_firstname", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getBusContactLastname();
    if (strValue != null && strValue.length() > 0) {
      this.buildColumnCriteria(_sql, "business.contact_lastname", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getBusContactPhone();
    if (strValue != null && strValue.length() > 0) {
      this.buildColumnCriteria(_sql, "business.contact_phone", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getBusLongname();
    if (strValue != null && strValue.length() > 0) {
      this.buildColumnCriteria(_sql, "business.longname", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    intValue = _contact.getBusServType();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "business.serv_type", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    intValue = _contact.getBusType();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "business.bus_type", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    strValue = _contact.getBusTaxId();
    if (strValue != null && strValue.length() > 0) {
      this.buildColumnCriteria(_sql, "business.tax_id", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getBusWebsite();
    if (strValue != null && strValue.length() > 0) {
      this.buildColumnCriteria(_sql, "business.website", AbstractActionHandler.COLTYPE_STR, strValue);
    }

    return _sql.toString();
  }

/**
 * Extracts personal criteria from _contact and constructs SQL selection criteria to be used to retrieve personal contact data.
 * 
 * @param _sql StringBuffer contianing the SQL statement used to append the selection criteria.
 * @param _contact object contaning both business and contact contact data.
 * @return String
 * @throws SystemException
 */
  private String getPersonalCriteria(StringBuffer _sql, ContactCombine _contact) throws SystemException {

		String strValue;
		int     intValue;
		double numValue;
		String  strDateValue;

    intValue = _contact.getPersonId();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "person.id", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    strValue = _contact.getPerFirstname();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.firstname", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getPerMidname();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.midname", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getPerLastname();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.lastname", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getPerMaidenname();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.maidenname", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getPerGeneration();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.generation", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getPerSsn();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.ssn", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getPerEmail();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.email", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    intValue = _contact.getPerGenderId();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "person.gender_id", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    intValue = _contact.getPerMaritalStatus();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "person.marital_status", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    intValue = _contact.getPerRaceId();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "person.race_id", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    intValue = _contact.getPerTitle();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "person.title", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }
    strDateValue = RMT2Utility.formatDate(_contact.getPerBirthDate(), "MM/dd/yyyy");
    if (strDateValue != null && strDateValue.length() > 0) {
			this.buildColumnCriteria(_sql, "person.birth_date", AbstractActionHandler.COLTYPE_DATE, strDateValue, false);
    }

    return _sql.toString();
  }


/**
 * Extracts address criteria from _contact and constructs SQL selection criteria to be used to retrieve address contact data.
 * 
 * @param _sql StringBuffer contianing the SQL statement used to append the selection criteria.
 * @param _contact object contaning both business and contact contact data.
 * @return String
 * @throws SystemException
 */
  private String getAddressCriteria(StringBuffer _sql, ContactCombine _contact) throws SystemException {

		String strValue;
		int     intValue;
		double numValue;
		String  strDateValue;

    strValue = _contact.getAddr1();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.addr1", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddr2();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.addr2", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddr3();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.addr3", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddr4();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.addr4", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrCity();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "zipcode.city", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrState();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "zipcode.state", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrPhoneCell();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.phone_cell", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrPhoneExt();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.phone_ext", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrPhoneFax();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.phone_fax", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrPhoneHome();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.phone_home", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrPhoneMain();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.phone_main", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrPhonePager();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.phone_pager", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    strValue = _contact.getAddrPhoneWork();
    if (strValue != null && strValue.length() > 0) {
			this.buildColumnCriteria(_sql, "address.phone_work", AbstractActionHandler.COLTYPE_STR, strValue);
    }
    intValue = _contact.getAddrZip();
    if (intValue > 0) {
			this.buildColumnCriteria(_sql, "address.zip", AbstractActionHandler.COLTYPE_INT, String.valueOf(intValue));
    }

    return _sql.toString();
  }

  /**
   * Applies business contact updates to the database
    * <p>
    * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object and the 
    * HttpSession object to be handle at the discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>business_id</td>
	 *   <td>String</td>
	 *   <td>The Business object's unique identifier</td>
	 * </tr>
	 * <tr>
	 *   <td>address_id</td>
	 *   <td>String</td>
	 *   <td>The Address object's unique identifier</td>
	 * </tr> 
	 *</table>   
	 *
   * @throws ContactException
   * @throws DatabaseException
   */
  public void  handleSaveBusinessAction() throws ContactException, DatabaseException {

	 String parms = null;
	 int     busKey;
	 int     addrKey;
    Business business;
    Address address;

    String method = "handleSaveBusinessAction";

		try {
			//  Get data from request object for business and address entities.
			business = ContactsFactory.createBusiness(this.request);
			address = ContactsFactory.createAddress(this.request);

			this.validateBusiness(business, address);
			busKey = busApi.maintainBusiness(business);

			// Associate business id with address if we are adding contact
			if (address.getBusinessId() == 0) {
				address.setBusinessId(busKey);
			}
			addrKey = addrApi.maintainAddress(address);

			//  Commit Changes to the database
			this.transObj.commitUOW();

			// Add business and address primary keys as attributes on the request object
			this.request.setAttribute("business_id", String.valueOf(busKey));
			this.request.setAttribute("address_id", String.valueOf(addrKey));
			return;
		}
		catch (ContactBusinessException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
		catch (ContactAddressException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
		catch (SystemException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
	}

	/**
	 * Applies personal contact updates to the database
    * <p>
    * Upon successful completion, the following objects are returned to the client via the HttpServletRequest object and the 
    * HttpSession object to be handle at the discretion of the client: 
	 * <p>
	 * <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	 * <tr>
	 *   <td><strong>Attribute</strong></td>
	 *   <td><strong>Type</strong></td>
	 *   <td><strong>Description</strong></td>
	 *  </tr>
	 * <tr>
	 *   <td>business_id</td>
	 *   <td>String</td>
	 *   <td>The Business object's unique identifier</td>
	 * </tr>
	 * <tr>
	 *   <td>person_id</td>
	 *   <td>String</td>
	 *   <td>The Person object's unique identifier</td>
	 * </tr> 
	 *</table>    
	 *
    * @throws ContactException
    * @throws DatabaseException
    */
  public void  handleSavePersonAction() throws ContactException, DatabaseException {

		String parms = null;
		int     perKey;
		int     addrKey;
    Person person;
    Address address;

    String method = "handleSavePersonAction";

		try {
			//  Get data from request object for business and address entities.
			person = ContactsFactory.createPerson(this.request);
			address = ContactsFactory.createAddress(this.request);

			// Associate person id with address if we are adding contact
			if (person.getId() == 0 && address.getId() == 0) {
				address.setPersonId(person.getId());
				address.setBusinessId(0);
			}

			this.validatePerson(person, address);
			perKey = perApi.maintainPerson(person);

			// Associate person id with address if we are adding contact
			if (address.getPersonId() == 0) {
				address.setPersonId(perKey);
			}
			addrKey = addrApi.maintainAddress(address);

			//  Commit Changes to the database
			this.transObj.commitUOW();

			// Add person and address primary keys as attributes on the request object
			this.request.setAttribute("person_id", String.valueOf(perKey));
			this.request.setAttribute("address_id", String.valueOf(addrKey));
			return;
		}
		catch (ContactPersonException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
		catch (ContactAddressException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
		catch (SystemException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
	}

  
  /**
   * Verifies that the all Business and Address data has been supplied.
   * 
   * @param _bus Object of type {@link Business} which contains business entity data.
   * @param _add Object of type {@link Address} which contains business entity data.
   * @throws ContactBusinessException
   */
  private void validateBusiness(Business _bus, Address _add) throws ContactBusinessException {
		String method = "validateBusiness";
		int intKey;
		String strKey;

		// Ensure that business object is valid
		if (_bus == null) {
			throw new ContactBusinessException(this.dbConn, 552, null);
		}

		// Ensure that address object is valid
		if (_add == null) {
			throw new ContactBusinessException(this.dbConn, 555, null);
		}

		//  Ensure that the business id value is set
		try {
			strKey = this.request.getParameter("Id");
			intKey = Integer.valueOf(strKey).intValue();
			_bus.setId(intKey);
		}
		catch (NumberFormatException e) {
			throw new ContactBusinessException(this.dbConn, 553, null);
		}

		//  Ensure that the address id value is set
		try {
			strKey = this.request.getParameter("AddrId");
			intKey = Integer.valueOf(strKey).intValue();
			_add.setId(intKey);
		}
		catch (NumberFormatException e) {
			throw new ContactBusinessException(this.dbConn, 554, null);
		}

		if (_bus.getLongname() != null && RMT2Utility.spaces(_bus.getLongname().length()) != _bus.getLongname()) {
			// continue
		}
		else {
			throw new ContactBusinessException(this.dbConn, 505, null);
		}
	}

/**
 * Verifies that the all Person and Address data has been supplied.
 * 
 * @param _per This is data pertaining to a person entity which is of type {@link Person}.
 * @param _add This is data pertaining to an address entity which is of type {@link Address}.
 * @throws ContactPersonException
 */
  private void validatePerson(Person _per, Address _add) throws ContactPersonException {
		String method = "validatePerson";
		int intKey;
		String strKey;

		// Ensure that person object is valid
		if (_per == null) {
			throw new ContactPersonException(this.dbConn, 548, null);
		}

		// Ensure that address object is valid
		if (_add == null) {
			throw new ContactPersonException(this.dbConn, 555, null);
		}

		//  Ensure that the person id value is set
		try {
			strKey = this.request.getParameter("Id");
			intKey = Integer.valueOf(strKey).intValue();
			_per.setId(intKey);
		}
		catch (NumberFormatException e) {
			throw new ContactPersonException(this.dbConn, 549, null);
		}

		//  Ensure that the address id value is set
		try {
			strKey = this.request.getParameter("AddrId");
			intKey = Integer.valueOf(strKey).intValue();
			_add.setId(intKey);
		}
		catch (NumberFormatException e) {
			throw new ContactPersonException(this.dbConn, 554, null);
		}

		if (_per.getFirstname() != null && RMT2Utility.spaces(_per.getFirstname().length()) != _per.getFirstname()) {
			// continue
		}
		else {
			throw new ContactPersonException(this.dbConn, 501, null);
		}
		if (_per.getLastname() != null && RMT2Utility.spaces(_per.getLastname().length()) != _per.getLastname()) {
			// continue
		}
		else {
			throw new ContactPersonException(this.dbConn, 502, null);
		}
	}

/**
 * Delete a personal or business contact based on the value of _type.
 * 
 * @param _type Determines if deleting personal or business contact
 * @throws ContactException
 * @throws DatabaseException
 */
  public void handleDeleteContactAction(String _type) throws ContactException, DatabaseException {

		String temp;
		int     intKey;

		//  Convert paramerter to int value
		try {
			temp = this.request.getParameter("Id");
			intKey = Integer.valueOf(temp).intValue();

			if (_type.equals("bus")) {
				busApi.deleteBusiness(intKey);
			}
			else {
				perApi.deletePerson(intKey);
			}
			//  Commit Changes to the database
			this.transObj.commitUOW();
			return;
		}
		catch (NumberFormatException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(this.dbConn, 549, null);
		}
		catch (ContactBusinessException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
		catch (ContactPersonException e) {
			this.transObj.rollbackUOW();
			throw new ContactException(e);
		}
  }
  
  protected void receiveClientData() throws ActionHandlerException{}
  protected void sendClientData() throws ActionHandlerException{}
  public void add() throws ActionHandlerException{}
  public void edit() throws ActionHandlerException{}
  public void save() throws ActionHandlerException{}
  public void delete() throws ActionHandlerException{}


}
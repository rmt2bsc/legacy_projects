package com.gl.customer;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.controller.Request;
import com.controller.Response;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.api.DaoApi;

import com.api.db.DatabaseException;

import com.api.db.orm.RdbmsDaoImpl;
import com.api.db.orm.RdbmsDaoQueryHelper;

import com.api.xml.XmlApiFactory;

import com.bean.Customer;
import com.bean.GlAccounts;

import com.bean.criteria.CustomerCriteria;
import com.bean.custom.UserTimestamp;

import com.bean.db.DatabaseConnectionBean;

import com.gl.BasicGLApi;
import com.gl.GeneralLedgerFactory;

import com.util.RMT2Date;
import com.util.RMT2Utility;
import com.util.SystemException;

import com.remoteservices.http.HttpRemoteServicesConsumer;

/**
 * An implementation of {@link com.gl.BasicGLApi BasicGLApi}.  It provides functionality 
 * that creates, updates, deletes, and queries General Ledger accounts, account types, 
 * and account category entities.
 * 
 * @author RTerrell
 * @deprecated Use {@link com.gl.customer.CustomerSoapImpl CustomerSoapImpl} class
 *
 */
class CopyofCustomerImpl extends RdbmsDaoImpl implements CustomerApi {
    private RdbmsDaoQueryHelper daoHelper;

    private Response response;

    private Logger logger;

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @throws SystemException
     * @throws DatabaseException
     */
    public CopyofCustomerImpl(DatabaseConnectionBean dbConn) throws SystemException, DatabaseException {
	super(dbConn);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("CopyofCustomerImpl");
    }

    /**
     * Constructor begins the initialization of the DatabaseConnectionBean and the 
     * HttpServletRequest object at the ancestor level.
     * 
     * @param dbConn The database connection bean
     * @param req The request object.
     * @throws SystemException
     * @throws DatabaseException
     */
    public CopyofCustomerImpl(DatabaseConnectionBean dbConn, Request req) throws SystemException, DatabaseException {
	super(dbConn, req);
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("CopyofCustomerImpl");
    }

    /**
     * Construct a CopyofCustomerImpl object that contains an initialized database connection, 
     * request, and response.
     * 
     * @param dbConn The database connection
     * @param req The user's request
     * @param resp The user's response
     * @throws SystemException General system failures
     * @throws DatabaseException General database errors.
     */
    public CopyofCustomerImpl(DatabaseConnectionBean dbConn, Request req, Response resp) throws SystemException, DatabaseException {
	this(dbConn, req);
	this.response = resp;
	this.daoHelper = new RdbmsDaoQueryHelper(this.connector);
	this.logger = Logger.getLogger("CopyofCustomerImpl");
    }

    public void close() {
	super.close();
    }

    /**
     * Retrieve customers by business contact.
     * 
     * @param businessId The internal unique id of the business.
     * @return A List of {@link com.bean.Customer Customer} objects.
     * @throws CustomerException
     */
    public Object findByBusinessId(int businessId) throws CustomerException {
	Customer obj = CustomerFactory.createCustomer();
	obj.addCriteria(Customer.PROP_BUSINESSID, businessId);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}
    }

    /**
     * Retrieve a customer using its unique identifier.
     * 
     * @param id customer's internal unique id usually the primary key
     * @return An {@link com.bean.Customer Customer} object.
     * @throws CustomerException
     */
    public Object findById(int id) throws CustomerException {
	Customer obj = CustomerFactory.createCustomer();
	obj.addCriteria(Customer.PROP_CUSTOMERID, id);
	try {
	    return this.daoHelper.retrieveObject(obj);
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}
    }

    /**
     * Retrives one or more customers Customer using custom selection criteria.
     * 
     * @param criteria
     *          The selection criteria that is to be applied to the query of the 
     *          implementation. 
     * @return A List of {@link com.bean.Customer Customer} objects.
     * @throws CustomerException
     */
    public Object findCustomer(String criteria) throws CustomerException {
	Customer obj = CustomerFactory.createCustomer();
	obj.addCustomCriteria(criteria);
	try {
	    return this.daoHelper.retrieveList(obj);
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}
    }

    /**
     * Obtain the customer's balance.
     * 
     * @param id customer's internal unique id
     * @return double as the customer's balance.
     * @throws CustomerException General database errors.
     */
    public double findCustomerBalance(int id) throws CustomerException {
	String sql = "select sum(amount) balance from customer_activity where customer_id = " + id;
	double bal;
	try {
	    ResultSet rs = this.executeQuery(sql);
	    if (rs.next()) {
		bal = rs.getDouble("balance");
	    }
	    else {
		bal = 0;
	    }
	    return bal;
	}
	catch (Exception e) {
	    throw new CustomerException(e);
	}
    }

    /**
     * Retrieve customer and business contact information for a specific customer.
     * 
     * @param customerId the customer's internal unique id.
     * @return {@link com.bean.CustomerExt CustomerExt}
     * @throws CustomerException General database errors.
     */
    public Object findCustomerBusiness(int customerId) throws CustomerException {
	Customer customer = (Customer) this.findById(customerId);

	// Get Business contact related data
	CustomerExt custExt = this.getCustomerContactData(customer.getBusinessId());

	// Set customer related data.
	this.populateCustomerExt(custExt, customer);
	return custExt;
    }

    /**
     * Retrieve the customer/business contact information using custom selection 
     * criteria.  If criteria is being used, the following parameters must be 
     * provided as key/value pairs:
     * <table border="1">
     *   <tr>
     *     <th>Parameter/Key</th>
     *     <th>Description</th>
     *   </tr>
     *   <tr>
     *      <td>AccountNo</td>
     *      <td>An account number.  Can be a portion in order to perform a LIKE search</td>
     *   </tr>
     *   <tr>
     *     <td>CustomerId</td>
     *     <td>The customer id</td>
     *   </tr>
     *   <tr>
     *     <td>CustomerIdList</td>
     *     <td>A String of customer id's separated by commas in order</td>
     *   </tr>
     *   <tr>
     *     <td>Name</td>
     *     <td>Contact name</td>
     *   </tr>
     *   <tr>
     *     <td>PhoneMain</td>
     *     <td>Contact phone number</td>
     *   </tr>
     *   <tr>
     *     <td>TaxId</td>
     *     <td>Contact tax id number</td>
     *   </tr>
     *   </table>
     * <p>
     * The field names in <i>criteria</i> are translated to a their respective database 
     * column names before the SQL statement is executed.
     * 
     * @param criteria selection criteria to filter customer query.
     * @return A List of {@link com.bean.CustomerExt CustomerExt} objects.
     * @throws CustomerException General database errors.
     */
    public Object findCustomerBusiness(String criteria) throws CustomerException {
	Map criteriaMap = null;
	boolean useCustomerParms = false;
	boolean useContactParms = false;

	if (criteria != null) {
	    criteriaMap = RMT2Utility.convertCriteriaToHash(criteria);
	    useCustomerParms = criteriaMap.containsKey(CustomerConst.CRITERIATAGS_CUSTOMER[0]) || criteriaMap.containsKey(CustomerConst.CRITERIATAGS_CUSTOMER[1])
		    || criteriaMap.containsKey(CustomerConst.CRITERIATAGS_CUSTOMER[2]);
	    useContactParms = (criteriaMap.containsKey(CustomerConst.CRITERIATAGS_CONTACT[0]) || criteriaMap.containsKey(CustomerConst.CRITERIATAGS_CONTACT[1]) || criteriaMap
		    .containsKey(CustomerConst.CRITERIATAGS_CONTACT[2]));

	    if (useCustomerParms && useContactParms) {
		this.msg = "Customer criteria and business contact criteria must be mutually exclusive";
		this.logger.log(Level.ERROR, this.msg);
		throw new CustomerException(this.msg);
	    }
	}

	List<CustomerExt> list = null;
	if (useCustomerParms) {
	    list = this.findCustomerBusinessByCustomer(criteriaMap);
	}
	else if (useContactParms) {
	    list = this.findCustomerBusinessByContact(criteriaMap);
	}
	else if (!useCustomerParms && !useContactParms) {
	    list = this.findCustomerBusinessByCustomer(null);
	}
	// return null if no customers are found.
	if (list == null) {
	    return null;
	}
	CustomerExtComparator comp = new CustomerExtComparator();
	Collections.sort(list, comp);
	comp = null;
	return list;
    }

    /**
     * Retrieves and merges customer and business contact information which the process 
     * is driven by customer data.  Business contact data is obtained from the remote 
     * application, contacts, which the <i>criteria</i> is used to filter the result set.  
     * All ccustomer contact data is attempted to be retrieve in the event <i>criteria</i>  
     * is null.   Otherwise, the query result set is filterd using customer selectin 
     * criteria.
     * <p>
     * The field names in <i>criteria</i> are translated to a their respective database 
     * column names before the SQL statement is executed.
     * 
     * @param criteria 
     *          A Map contain key/value pairs that represent customer selection criteria. 
     * @return List of {@link com.bean.CustomerExt CustomerExt} objecs
     * @throws CustomerException  
     *           General database and remote service errors.
     */
    private List findCustomerBusinessByCustomer(Map criteria) throws CustomerException {
	StringBuffer sql = new StringBuffer();
	List<Customer> custList = null;

	// Use account number, creditor id, and/or a list of creditor id's  
	// to query local database for customers.
	if (criteria != null) {
	    String acctNo = (String) criteria.get(CustomerConst.CRITERIATAGS_CUSTOMER[0]);
	    if (acctNo != null) {
		sql.append("account_no like \'");
		sql.append(acctNo.trim());
		sql.append("%\'");
	    }

	    String customerId = (String) criteria.get(CustomerConst.CRITERIATAGS_CUSTOMER[1]);
	    if (customerId != null) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append("customer_id = ");
		sql.append(customerId);
	    }

	    String custIdList = (String) criteria.get(CustomerConst.CRITERIATAGS_CUSTOMER[2]);
	    if (custIdList != null) {
		if (sql.length() > 0) {
		    sql.append(" and ");
		}
		sql.append("customer_id in(");
		sql.append(custIdList);
		sql.append(")");
	    }
	}

	// Get base customer list
	Customer obj = CustomerFactory.createCustomer();
	obj.addCustomCriteria(sql.toString());
	obj.addOrderBy(Customer.PROP_BUSINESSID, Customer.ORDERBY_ASCENDING);
	try {
	    custList = this.daoHelper.retrieveList(obj);
	    if (custList == null || custList.size() <= 0) {
		return null;
	    }
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}

	// Build list business id's to submit to remote service
	StringBuffer busId = new StringBuffer();
	for (int ndx = 0; ndx < custList.size(); ndx++) {
	    Customer cust = custList.get(ndx);
	    if (busId.length() > 0) {
		busId.append(",");
	    }
	    busId.append(cust.getBusinessId());
	}

	// Call remote service to fetch business contact based on list of business id's
	List<CustomerExt> custExtList = null;
	try {
	    this.request.setAttribute("Arg_BusinessId", busId.toString());
	    HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer(this.request, this.response, "getBusAddrById");
	    srvc.processRequest();
	    Object results = srvc.getServiceResults();
	    if (results instanceof Exception) {
		this.msg = ((Exception) results).getMessage();
		throw new CustomerException(this.msg);
	    }
	    String xml = results.toString();
	    custExtList = this.populateCustomerExt(xml);
	}
	catch (Exception e) {
	    throw new CustomerException(e);
	}

	// Only merge customer base with customer extension where business id's match.
	List<CustomerExt> newLlist = new ArrayList();
	for (int ndx = 0; ndx < custList.size(); ndx++) {
	    Customer cust = custList.get(ndx);
	    for (int ndx2 = 0; ndx2 < custExtList.size(); ndx2++) {
		CustomerExt ext = custExtList.get(ndx2);
		if (cust.getBusinessId() != ext.getBusinessId()) {
		    continue;
		}
		this.populateCustomerExt(ext, cust);
		newLlist.add(ext);
	    }
	}
	return newLlist;
    }

    /**
     * Retrieves customer extension data by which the business contact information drives 
     * the process of combining the business contact data with the customer data.  Business
     * contact data is obtained from the remote application, contacts, which the <i>criteria</i>
     * is used to filter the result set.
     * <p>
     * The field names in <i>criteria</i> are translated to a their respective database 
     * column names before the SQL statement is executed.
     * 
     * @param criteria 
     *          Key/value pairs representing the parameters for the remote service call.
     * @return  List of {@link com.bean.CustomerExt CustomerExt} objecs
     * @throws CustomerException 
     *           General database and remote service errors.
     */
    private List findCustomerBusinessByContact(Map criteria) throws CustomerException {
	// Setup service parameters on the request object
	for (int ndx = 0; ndx < CustomerConst.CRITERIATAGS_CONTACT.length; ndx++) {
	    String value = (String) criteria.get(CustomerConst.CRITERIATAGS_CONTACT[ndx]);
	    if (value != null) {
		this.request.setAttribute(CustomerConst.CRITERIATAGS_CONTACT[ndx], value);
	    }
	}

	List<CustomerExt> list1 = null;
	HttpRemoteServicesConsumer srvc = null;
	try {
	    srvc = new HttpRemoteServicesConsumer(this.request, this.response, "getBusiness");
	    srvc.processRequest();
	    Object results = srvc.getServiceResults();
	    if (results instanceof Exception) {
		this.msg = ((Exception) results).getMessage();
		throw new CustomerException(this.msg);
	    }
	    String xml = results.toString();
	    list1 = this.populateCustomerExt(xml);
	}
	catch (Exception e) {
	    throw new CustomerException(e);
	}
	finally {
	    srvc.close();
	    srvc = null;
	}

	List<CustomerExt> list2 = new ArrayList();
	for (int ndx = 0; ndx < list1.size(); ndx++) {
	    CustomerExt ext = list1.get(ndx);
	    List custList = (List) this.findByBusinessId(ext.getBusinessId());
	    if (custList == null) {
		//  Perhaps the business founded based on selection 
		//  criteria does not exist as a customer entity.
		continue;
	    }
	    for (int ndx2 = 0; ndx2 < custList.size(); ndx2++) {
		Customer cust = (Customer) custList.get(ndx2);
		if (cust == null) {
		    continue;
		}
		this.populateCustomerExt(ext, cust);
		list2.add(ext);
		cust = null;
	    }
	}
	return list2;
    }

    /**
     * Retrieve the business contact data pertaining to a specific customer.
     * 
     * @param businessId The customer's business contact id.
     * @return {@link com.bean.CustomerExt CustomerExt}
     * @throws CustomerException General database related errors.
     */
    private CustomerExt getCustomerContactData(int businessId) throws CustomerException {
	// Call service to get customer by client id.
	this.request.setAttribute("Arg_BusinessId", businessId);
	HttpRemoteServicesConsumer srvc = new HttpRemoteServicesConsumer(this.request, this.response, "getBusById");
	srvc.processRequest();
	String xml = (String) srvc.getServiceResults();

	// Get customer extension results.
	CustomerExt custExt = null;
	List results = this.populateCustomerExt(xml);
	if (results != null && results.size() == 1) {
	    custExt = (CustomerExt) results.get(0);
	}
	return custExt;
    }

    /**
     * Creates a List of CustomerExt objects and populates each element with values 
     * from <i>xml</i>.   
     *  
     * @param xml XML document containing one or more business contact records
     * @return A List of {@link com.bean.CustomerExt CustomerExt}
     * @throws CustomerException For general system, database, or validation errors.
     */
    private List<CustomerExt> populateCustomerExt(String xml) throws CustomerException {
	int intTmp;
	String value;

	DaoApi dao = XmlApiFactory.createXmlDao(xml);
	try {
	    List list = new ArrayList();
	    dao.retrieve("//VwBusinessAddressView/vw_business_address");
	    while (dao.nextRow()) {
		CustomerExt custExt = new CustomerExt();
		try {
		    intTmp = Integer.parseInt(dao.getColumnValue("business_id"));
		    custExt.setBusinessId(intTmp);
		}
		catch (NumberFormatException e) {
		    custExt.setBusType(0);
		}
		try {
		    intTmp = Integer.parseInt(dao.getColumnValue("bus_entity_type_id"));
		    custExt.setBusType(intTmp);
		}
		catch (NumberFormatException e) {
		    custExt.setBusType(0);
		}
		try {
		    intTmp = Integer.parseInt(dao.getColumnValue("bus_serv_type_id"));
		    custExt.setServType(intTmp);
		}
		catch (NumberFormatException e) {
		    custExt.setServType(0);
		}

		value = dao.getColumnValue("bus_longname");
		custExt.setName(value);
		value = dao.getColumnValue("bus_shortname");
		custExt.setShortname(value);
		value = dao.getColumnValue("bus_contact_firstname");
		custExt.setContactFirstname(value);
		value = dao.getColumnValue("bus_contact_lastname");
		custExt.setContactLastname(value);
		value = dao.getColumnValue("bus_contact_phone");
		custExt.setContactPhone(value);
		value = dao.getColumnValue("bus_contact_ext");
		custExt.setContactExt(value);
		value = dao.getColumnValue("bus_tax_id");
		custExt.setTaxId(value);
		value = dao.getColumnValue("bus_website");
		custExt.setWebsite(value);
		value = dao.getColumnValue("contact_email");
		custExt.setContactEmail(value);

		list.add(custExt);
	    }
	    return list;
	}
	catch (Exception e) {
	    throw new CustomerException(e);
	}
    }

    /**
     * Copies data from the base customer object to the customer extension object.
     * 
     * @param custExt 
     *          The {@link com.bean.CustomerExt CustomerExt} object which serves as 
     *          the destination of the data transfer.
     * @param customer 
     *          The {@link com.bean.Customer Customer}object which serves as the 
     *          source of the data transfer.
     * @throws CustomerException
     */
    private void populateCustomerExt(CustomerExt custExt, Customer customer) throws CustomerException {
	custExt.setCustomerId(customer.getCustomerId());
	custExt.setGlAccountId(customer.getAcctId());
	custExt.setAccountNo(customer.getAccountNo());
	custExt.setBusinessId(customer.getBusinessId());
	custExt.setCreditLimit(customer.getCreditLimit());
	custExt.setDescription(customer.getDescription());
	custExt.setActive(customer.getActive());
	custExt.setDateCreated(customer.getDateCreated());
	custExt.setDateUpdated(customer.getDateUpdated());
	custExt.setUserId(customer.getUserId());
	
	Double bal = this.findCustomerBalance(customer.getCustomerId());
	custExt.setBalance(bal);
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#maintainCustomer(com.bean.Customer, java.lang.Object)
     */
    public int maintainCustomer(Customer cust, Object custExt) throws CustomerException {
	int rc;
	if (cust.getCustomerId() == 0) {
	    rc = this.insertCustomer(cust, custExt);
	    cust.setCustomerId(rc);
	}
	else {
	    rc = this.updateCustomer(cust, custExt);
	}
	return rc;
    }

    /**
     * Creates a customer entity in the database.  Also capable of handling application 
     * specific customer details via <i>objExt</i>, if applicable.
     *      
     * @param cust The base customer object.
     * @param custExt The extended customer object or null if not used.
     * @return The internal customer id created from the transaction.
     * @throws CustomerException General database error or validation error.
     */
    private int insertCustomer(Customer cust, Object custExt) throws CustomerException {
	int rc = 0;

	this.validateCustomer(cust);
	this.validateCustomerExt(custExt);

	String acctNo = this.buildAccountNo(cust.getBusinessId());
	cust.setAccountNo(acctNo);

	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    cust.setDateCreated(ut.getDateCreated());
	    cust.setDateUpdated(ut.getDateCreated());
	    cust.setUserId(ut.getLoginId());
	    cust.setIpCreated(ut.getIpAddr());
	    cust.setIpUpdated(ut.getIpAddr());
	}
	catch (SystemException e) {
	    throw new CustomerException(e);
	}

	// Perform the actual insert of customer.
	try {
	    rc = this.insertRow(cust, true);
	    cust.setCustomerId(rc);
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}

    }

    /**
     * Constructs an account number for the customer. The format of the account number is:
     * <pre>
     *    &lt;U&gt;&lt;business id&gt; - &lt;current year&gt; &lt;current month&gt; &lt;current da&gt;
     * </pre>
     * 
     * @param businessId The business id of the customer
     * @return The account number.
     */
    private String buildAccountNo(int businessId) {
	// Get current date
	Calendar cal = new GregorianCalendar();
	int yr = cal.get(Calendar.YEAR);
	int mm = cal.get(Calendar.MONTH) + 1;
	int dd = cal.get(Calendar.DAY_OF_MONTH);

	// Build account number
	StringBuffer acctNo = new StringBuffer(10);
	// TODO: Add the following line of code in CopyofCustomerImpl.java of the same 
	//       method as:  accNO.append("R"), to identify customers
	acctNo.append("U");
	acctNo.append(businessId);
	acctNo.append("-");
	acctNo.append(yr);
	acctNo.append((mm < 10 ? "0" + mm : mm));
	acctNo.append((dd < 10 ? "0" + dd : dd));

	return acctNo.toString();
    }

    /**
     * Updates the customer's profile in the database.  Also capable of handling application 
     * specific customer details via <i>objExt</i>, if applicable.
     * 
     * @param delta The base customer object.
     * @param objExt The extended customer object or null if not used.
     * @return The total number of rows effected by the transaction.
     * @throws CustomerException General database error or validation error.
     */
    private int updateCustomer(Customer delta, Object objExt) throws CustomerException {
	int rc = 0;
	Customer old = (Customer) this.findById(delta.getCustomerId());
	// Transfer delta to old record.
	old.setCreditLimit(delta.getCreditLimit());
	old.setDescription(delta.getDescription());

	this.validateCustomer(old);
	this.validateCustomerExt(objExt);

	UserTimestamp ut;
	try {
	    ut = RMT2Date.getUserTimeStamp(this.request);
	    old.setDateCreated(ut.getDateCreated());
	    old.setDateUpdated(ut.getDateCreated());
	    old.setUserId(ut.getLoginId());
	    old.setIpUpdated(ut.getIpAddr());
	}
	catch (SystemException e) {
	    throw new CustomerException(e);
	}

	// Perform the actual update of customer.
	try {
	    old.addCriteria(Customer.PROP_CUSTOMERID, old.getCustomerId());
	    rc = this.updateRow(old);
	    delta.setCustomerId(old.getCustomerId());
	    delta.setAccountNo(old.getAccountNo());
	    delta.setBusinessId(old.getBusinessId());
	    return rc;
	}
	catch (DatabaseException e) {
	    throw new CustomerException(e);
	}
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#validateCustomer(com.bean.Customer)
     */
    public void validateCustomer(Customer cust) throws CustomerException {
	// Evaluate GL Account and GL Account Type values. 
	try {
	    BasicGLApi glApi = GeneralLedgerFactory.createBasicGLApi(this.connector);
	    GlAccounts acct = glApi.findByAcctNameExact(BasicGLApi.ACCTNAME_ACCTRCV);
	    if (acct == null) {
		this.msg = "General ledger account could not be found for the customer account";
		this.logger.log(Level.ERROR, this.msg);
		throw new CustomerException(this.msg);
	    }
	    // Be sure that we chose the correcte GL Account
	    if (acct.getAcctTypeId() != BasicGLApi.ACCTTYPE_ASSET) {
		this.msg = "The incorrect account type was selected for the customer account";
		this.logger.log(Level.ERROR, this.msg);
		throw new CustomerException(this.msg);
	    }
	    // Assing the GL Account id.
	    cust.setAcctId(acct.getAcctId());
	}
	catch (Exception e) {
	    throw new CustomerException(e);
	}

	// Validate business id for existing customers.
	if (cust.getCustomerId() > 0) {
	    if (cust.getBusinessId() <= 0) {
		this.msg = "Business id is required when updating an existing customer profile";
		this.logger.log(Level.ERROR, this.msg);
		throw new CustomerException(this.msg);
	    }
	}
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#validateCustomerExt(java.lang.Object)
     */
    public void validateCustomerExt(Object custExt) throws CustomerException {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#deleteCustomer(int)
     */
    public int deleteCustomer(int custId) throws CustomerException {
	// TODO Auto-generated method stub
	return 0;
    }

    /* (non-Javadoc)
     * @see com.gl.customer.CustomerApi#findCustomerBusiness(com.bean.criteria.CustomerCriteria)
     */
    public Object findCustomerBusiness(CustomerCriteria criteria) throws CustomerException {
	// TODO Auto-generated method stub
	return null;
    }

}

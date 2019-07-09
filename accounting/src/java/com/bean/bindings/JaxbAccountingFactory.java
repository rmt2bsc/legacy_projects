package com.bean.bindings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.api.db.DatabaseTransApi;
import com.api.db.DatabaseTransFactory;

import com.api.messaging.MessagingException;
import com.api.messaging.ResourceFactory;


import com.api.messaging.webservice.ServiceReturnCode;
import com.api.messaging.webservice.soap.SoapMessageHelper;
import com.api.messaging.webservice.soap.client.SoapClientWrapper;
import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.RMT2Base;
import com.bean.SalesOrderExt;
import com.bean.SalesOrderItems;
import com.bean.VwGenericXactList;
import com.bean.XactType;

import com.bean.criteria.CreditorCriteria;
import com.bean.criteria.GenericXactCriteria;

import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.gl.creditor.CreditorApi;
import com.gl.creditor.CreditorException;
import com.gl.creditor.CreditorExt;
import com.gl.creditor.CreditorFactory;

import com.gl.customer.CustomerException;
import com.gl.customer.CustomerExt;

import com.util.RMT2Date;
import com.util.RMT2XmlUtility;
import com.util.SystemException;

import com.xml.schema.bindings.AddressType;
import com.xml.schema.bindings.AppRoleType;
import com.xml.schema.bindings.BusinessContactCriteria;
import com.xml.schema.bindings.BusinessType;
import com.xml.schema.bindings.CodeDetailType;
import com.xml.schema.bindings.CustomerType;
import com.xml.schema.bindings.GenericXactCriteriaType;
import com.xml.schema.bindings.HeaderType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.RQBusinessContactDelete;
import com.xml.schema.bindings.RQBusinessContactSearch;
import com.xml.schema.bindings.RQBusinessContactUpdate;
import com.xml.schema.bindings.RQCodeDetailSearch;
import com.xml.schema.bindings.RSBusinessContactDelete;
import com.xml.schema.bindings.RSBusinessContactSearch;
import com.xml.schema.bindings.RSBusinessContactUpdate;
import com.xml.schema.bindings.ReplyStatusType;
import com.xml.schema.bindings.SalesOrderInvoiceResultType;
import com.xml.schema.bindings.SalesOrderItemType;
import com.xml.schema.bindings.SalesOrderType;
import com.xml.schema.bindings.UserSessionType;
import com.xml.schema.bindings.XacttypeType;
import com.xml.schema.bindings.ZipcodeType;

import com.xml.schema.misc.PayloadFactory;



/**
 * Serves as a helper for persisting JAXB changes to the database and a factory that uses 
 * various JAXB type objects to create Accounting bean instances.
 *  
 * @author Roy Terrell
 *
 */
public class JaxbAccountingFactory extends RMT2Base {

    private static Logger logger = Logger.getLogger("JaxbAccountingFactory");
    
    /**
     * Default Constructor
     */
    public JaxbAccountingFactory() {
	super();
    }

    /**
     * Calls the web service, RQ_business_contact_update, to update the business contact data.
     * 
     * @param bt
     *          the contact changes to update
     * @param loginId
     *          the id of the user requesting the update
     * @return int
     *          the business id
     * @throws MessagingException
     *          general web service failures.
     */
    public int updateBusinessContactData(BusinessType bt, String loginId) throws MessagingException {
	ObjectFactory f = new ObjectFactory();
	RQBusinessContactUpdate ws = f.createRQBusinessContactUpdate();
	ws.setBusinessContact(bt);
	HeaderType header = PayloadFactory.createHeader("RQ_business_contact_update", "SYNC", "REQUEST", loginId);
	ws.setHeader(header);
	String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	
	try {
	    SoapClientWrapper client = new SoapClientWrapper();
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		throw new Exception(errMsg);
	    }
	    RSBusinessContactUpdate soapResp = (RSBusinessContactUpdate) client.getSoapResponsePayload();
	    ReplyStatusType rst = soapResp.getReplyStatus();
	    int busId = rst.getReturnCode().getValue().intValue();
	    return busId;
	}
	catch (Exception e) {
	    throw new MessagingException(e);
	}
    }
    
    /**
     * Calls the web service, RQ_business_contact_delete, to delete business contact data.
     * 
     * @param busId
     *         the id of the business contact to delete
     * @param loginId
     *         the id of the user requesting the delete
     * @return int
     *          the number of rows effected by the request
     * @throws CustomerException
     *          general web service failures
     */
    public int deleteBusinessContactData(int busId, String loginId) throws MessagingException {
	ObjectFactory f = new ObjectFactory();
	RQBusinessContactDelete ws = f.createRQBusinessContactDelete();
	ws.setBusinessId(BigInteger.valueOf(busId));
	HeaderType header = PayloadFactory.createHeader("RQ_business_contact_delete", "SYNC", "REQUEST", loginId);
	ws.setHeader(header);
	String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	
	try {
	    SoapClientWrapper client = new SoapClientWrapper();
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		throw new Exception(errMsg);
	    }
	    RSBusinessContactDelete soapResp = (RSBusinessContactDelete) client.getSoapResponsePayload();
	    ReplyStatusType rst = soapResp.getReplyStatus();
	    int rows = rst.getReturnCode().getValue().intValue();
	    return rows;
	}
	catch (Exception e) {
	    throw new MessagingException(e);
	}
    }

    
    /**
     * Calls the web service, RQ_business_contact_search, to fetch business contact data.
     * 
     * @param criteria
     *         an instance of {@link com.xml.schema.bindings.BusinessContactCriteria BusinessContactCriteria}
     * @param loginId
     *         the id of the user requesting the business contact fetch
     * @return
     *         {@link com.xml.schema.bindings.RSBusinessContactSearch RSBusinessContactSearch}
     * @throws CreditorException
     */
    public RSBusinessContactSearch getBusinessContactData(BusinessContactCriteria criteria, String loginId) throws MessagingException {
	ObjectFactory f = new ObjectFactory();
	// Create Payload instance
	RQBusinessContactSearch ws = f.createRQBusinessContactSearch();
	HeaderType header = PayloadFactory.createHeader("RQ_business_contact_search", "SYNC", "REQUEST", loginId);
	ws.setHeader(header);
	ws.setCriteriaData(criteria);
	String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	try {
	    SoapClientWrapper client = new SoapClientWrapper();
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		throw new Exception(errMsg);
	    }
	    RSBusinessContactSearch soapResp = (RSBusinessContactSearch) client.getSoapResponsePayload();
	    return soapResp;
	}
	catch (Exception e) {
	    throw new MessagingException(e);
	}
    }
    
    
    /**
     * Creates an instance of Map of customer contacts where each element is keyed by business id. 
     * 
     * @param contacts
     *         a List of CustomerExt instances.
     * @return
     *        Map <{@link Integer}, {@link com.gl.customer.CustomerExt CustomerExt}>
     */
    public Map <Integer, CustomerExt> createCustomerContactMap(List <CustomerExt> contacts) {
	Map <Integer, CustomerExt> map = new LinkedHashMap<Integer, CustomerExt>();
	for(CustomerExt item : contacts) {
	    map.put(item.getBusinessId(), item);
	}
	return map;
    }
    
    
    /**
     * Creates a XML document of one or more CustomerExt records from a List of CustomerExt objects.
     * 
     * @param contacts
     * @return String
     *           XML document of creditor/contact data.
     */
    public String createCustomerContactXml(List<CustomerExt> contacts) {
	if (contacts == null) {
	    return null;
	}
	StringBuffer xmlBuf = new StringBuffer();
	for (CustomerExt item : contacts) {
	    xmlBuf.append(item.toXml());
	}
	String xml = "<Customers>" + (xmlBuf.length() > 0 ? xmlBuf.toString() : "") + "</Customers>";
	return xml;
    }
    
    /**
     * Obtains a list of business contact data pertaining to one or more customers based the selection 
     * criteria specified.  Merges each business contact record to an instance of CustomerExt.  Each 
     * CustomerExt instance is added to List collection and returned to the client.
     * 
     * @param criteria
     *          {@link com.xml.schema.bindings.BusinessContactCriteria BusinessContactCriteria}
     * @param loginId
     *          the id of the user requesting Customer data
     * @return List <{@link com.gl.customer.CustomerExt CustomerExt}>
     * @throws CustomerException
     */
    public List <CustomerExt> getCustomerContactData(BusinessContactCriteria criteria, String loginId) throws CustomerException, MessagingException {
	RSBusinessContactSearch soapResp = this.getBusinessContactData(criteria, loginId);
	List <CustomerExt> extList = this.createCustomerContactList(soapResp.getBusinessList());
	return extList;
    }
    
    /**
     * Creates a List of CustomerExt objects from a List of BusinessType objects.
     * 
     * @param contacts
     *         List of {@link com.xml.schema.bindings.BusinessType BusinessType} objects
     * @return
     *         List <{@link com.gl.customer.CustomerExt CustomerExt}> or null if <i>contacts</i> is null.
     */
    private List <CustomerExt> createCustomerContactList(List<BusinessType> contacts) {
	if (contacts == null) {
	    return null;
	}
	List<CustomerExt> list = new ArrayList<CustomerExt>();
	for (BusinessType btItem : contacts) {
	    CustomerExt custExt = null;
	    if (btItem.getBusinessId() == null) {
		custExt = new CustomerExt();
	    }
	    else {
		custExt = this.createCustomerContact(btItem);
	    }
	    list.add(custExt);
	}
	return list;
    }
    
   
    /**
     * Creates a CustomerExt object from a single instance of BusinessType.
     * 
     * @param contact
     *         {@link com.xml.schema.bindings.BusinessType BusinessType}
     * @return
     *         {@link com.gl.customer.CustomerExt CustomerExt}
     */
    public CustomerExt createCustomerContact(BusinessType contact) {
	if (contact == null) {
	    return null;
	}
	CustomerExt custExt = new CustomerExt();
	custExt.setBusinessId(contact.getBusinessId().intValue());
	custExt.setBusType(contact.getEntityType().getCodeId().intValue());
	custExt.setServType(contact.getServiceType().getCodeId().intValue());
	custExt.setName(contact.getLongName());
	custExt.setShortname(contact.getShortName());
	custExt.setContactFirstname(contact.getContactFirstname());
	custExt.setContactLastname(contact.getContactLastname());
	custExt.setContactPhone(contact.getContactPhone());
	custExt.setContactExt(contact.getContactExt());
	custExt.setTaxId(contact.getTaxId());
	custExt.setWebsite(contact.getWebsite());
	custExt.setContactEmail(contact.getContactEmail());
	
	AddressType at = contact.getAddress();
	if (at != null) {
	    custExt.setAddrId(at.getAddrId() == null ? 0 : at.getAddrId().intValue());
	    custExt.setAddr1(at.getAddr1());
	    custExt.setAddr2(at.getAddr2());
	    custExt.setAddr3(at.getAddr3());
	    custExt.setAddr4(at.getAddr4());
	    custExt.setAddrPhoneMain(at.getPhoneMain());
	    custExt.setAddrPhoneFax(at.getPhoneFax());
	    
	    // Get zip code extension
	    if (at.getZipExt() != null) {
		custExt.setAddrZipext(at.getZipExt() == null ? 0 : at.getZipExt() == null ? 0 : at.getZipExt().intValue());
	    }
	    // Get zip code
	    ZipcodeType zct = at.getZip();
	    if (zct != null) {
		custExt.setZipCity(zct.getCity());
		custExt.setZipState(zct.getState());
		custExt.setAddrZip(zct.getZipcode() == null ? 0 : zct.getZipcode().intValue());
	    }
	}
	return custExt;
    }
    
    
    /**
     * 
     * @param custExt
     * @return
     */
    public CustomerType createCustomerType(CustomerExt custExt) {
	ObjectFactory f = new ObjectFactory();
	CustomerType ct = f.createCustomerType();
	
	// Setup customer data
	ct.setCustomerId(BigInteger.valueOf(custExt.getCustomerId()));
	ct.setAccountNo(custExt.getAccountNo());
	ct.setAcctDescription(custExt.getDescription());
	ct.setActive(BigInteger.valueOf(custExt.getActive()));
	ct.setCreditLimit(BigDecimal.valueOf(custExt.getCreditLimit()));
	
	// Setup Business contact data
	BusinessType bt = f.createBusinessType();
	bt.setBusinessId(BigInteger.valueOf(custExt.getBusinessId()));
	bt.setLongName(custExt.getName());
	bt.setShortName(custExt.getShortname());
	
	CodeDetailType cdt = f.createCodeDetailType();
	cdt.setCodeId(BigInteger.valueOf(custExt.getBusType()));
	cdt.setLongdesc("");
	cdt.setShortdesc("");
	bt.setEntityType(cdt);
	
	cdt = f.createCodeDetailType();
	cdt.setCodeId(BigInteger.valueOf(custExt.getServType()));
	cdt.setLongdesc("");
	cdt.setShortdesc("");
	bt.setServiceType(cdt);
	
	bt.setContactFirstname(custExt.getContactFirstname());
	bt.setContactLastname(custExt.getContactLastname());
	bt.setContactPhone(custExt.getContactPhone());
	bt.setContactEmail(custExt.getContactEmail());
	bt.setTaxId(custExt.getTaxId());
	bt.setWebsite(custExt.getWebsite());
	
	// Setup Address for business contact.
	AddressType at = f.createAddressType();
	at.setAddrId(BigInteger.valueOf(custExt.getAddrId()));
	at.setAddr1(custExt.getAddr1());
	at.setAddr2(custExt.getAddr2());
	at.setAddr3(custExt.getAddr3());
	at.setAddr4(custExt.getAddr4());
	at.setPhoneMain(custExt.getAddrPhoneMain());
	at.setPhoneFax(custExt.getAddrPhoneFax());
	at.setZipExt(BigInteger.valueOf(custExt.getAddrZipext()));
	
	// Get zipcode information for address
	ZipcodeType zt = f.createZipcodeType();
	zt.setZipcode(BigInteger.valueOf(custExt.getAddrZip()));
	zt.setCity(custExt.getZipCity());
	zt.setState(custExt.getZipState());
	
	// Add zip to address instance
	at.setZip(zt);
	
	// Add address to business instance
	bt.setAddress(at);
	
	// Add business contact data to customer type instance.
	ct.setContactDetails(bt);
	
	return ct;
	
    }
    
    
    /**
     * Obtains a list of business contact data pertaining to one or more creditors based the selection 
     * criteria specified.  Merges each business contact record to an instance of CreditorExt.  Each 
     * CreditorExt instance is added to List collection and returned to the client.
     * 
     * @param criteria
     *          {@link com.xml.schema.bindings.BusinessContactCriteria BusinessContactCriteria}
     * @param loginId
     *          the id of the user requesting Creditor data
     * @return
     *          List <{@link com.gl.creditor.CreditorExt CreditorExt}>
     * @throws CreditorException
     */
    public List <CreditorExt> getCreditorContactData(BusinessContactCriteria criteria, String loginId) throws CreditorException, MessagingException {
	RSBusinessContactSearch soapResp = this.getBusinessContactData(criteria, loginId);
	List <CreditorExt> extList = this.createCreditorContactList(soapResp.getBusinessList());
	return extList;
    }
    
    /**
     * 
     * @param criteria
     * @param loginId
     * @return
     * @throws CreditorException
     * @throws MessagingException
     */
    public String getCreditorContactDataAsXml(BusinessContactCriteria criteria, String loginId) throws CreditorException, MessagingException {
	RSBusinessContactSearch soapResp = this.getBusinessContactData(criteria, loginId);
	String xml = ResourceFactory.getJaxbMessageBinder().marshalMessage(soapResp);
	return xml;
    }
    
    
    /**
     * Creates a List of CreditorExt objects from a List of BusinessType objects.
     * 
     * @param contacts
     *         List of {@link com.xml.schema.bindings.BusinessType BusinessType} objects
     * @return
     *         List <{@link com.gl.creditor.CreditorExt CreditorExt}> or null if <i>contacts</i> is null.
     */
    private List <CreditorExt> createCreditorContactList(List<BusinessType> contacts) {
	if (contacts == null) {
	    return null;
	}
	List<CreditorExt> list = new ArrayList<CreditorExt>();
	for (BusinessType btItem : contacts) {
	    CreditorExt credExt = null;
	    if (btItem.getBusinessId() == null) {
		credExt = new CreditorExt();
	    }
	    else {
		credExt = this.createCreditorContact(btItem);
	    }
	    list.add(credExt);
	}
	return list;
    }
    
    
    /**
     * Creates a XML document of one or more CreditorExt records from a List of CreditorExt objects.
     * 
     * @param contacts
     * @return String
     *           XML document of creditor/contact data.
     */
    public String createCreditorContactXml(List<CreditorExt> contacts) {
	if (contacts == null) {
	    return null;
	}
	StringBuffer xmlBuf = new StringBuffer();
	for (CreditorExt item : contacts) {
	    xmlBuf.append(item.toXml());
	}
	String xml = "<Creditors>" + (xmlBuf.length() > 0 ? xmlBuf.toString() : "") + "</Creditors>";
	return xml;
    }
    
    
    /**
     * Creates a CreditorExt object from a single instance of BusinessType.
     * 
     * @param contact
     *         {@link com.xml.schema.bindings.BusinessType BusinessType}
     * @return
     *         {@link com.gl.creditor.CreditorExt CreditorExt}
     */
    public CreditorExt createCreditorContact(BusinessType contact) {
	if (contact == null) {
	    return null;
	}
	CreditorExt credExt = new CreditorExt();
	credExt.setBusinessId(contact.getBusinessId().intValue());
	credExt.setBusType(contact.getEntityType().getCodeId().intValue());
	credExt.setServType(contact.getServiceType().getCodeId().intValue());
	credExt.setName(contact.getLongName());
	credExt.setShortname(contact.getShortName());
	credExt.setContactFirstname(contact.getContactFirstname());
	credExt.setContactLastname(contact.getContactLastname());
	credExt.setContactPhone(contact.getContactPhone());
	credExt.setContactExt(contact.getContactExt());
	credExt.setTaxId(contact.getTaxId());
	credExt.setWebsite(contact.getWebsite());
	credExt.setContactEmail(contact.getContactEmail());
	
	AddressType at = contact.getAddress();
	if (at != null) {
	    credExt.setAddrId(at.getAddrId() == null ? 0 : at.getAddrId().intValue());
	    credExt.setAddr1(at.getAddr1());
	    credExt.setAddr2(at.getAddr2());
	    credExt.setAddr3(at.getAddr3());
	    credExt.setAddr4(at.getAddr4());
	    credExt.setAddrPhoneMain(at.getPhoneMain());
	    credExt.setAddrPhoneFax(at.getPhoneFax());
	    
	    // Get zip code extension
	    if (at.getZipExt() != null) {
		credExt.setAddrZipext(at.getZipExt() == null ? 0 : at.getZipExt() == null ? 0 : at.getZipExt().intValue());
	    }
	    // Get zip code
	    ZipcodeType zct = at.getZip();
	    if (zct != null) {
		credExt.setZipCity(zct.getCity());
		credExt.setZipState(zct.getState());
		credExt.setAddrZip(zct.getZipcode() == null ? 0 : zct.getZipcode().intValue());
	    }
	}
	return credExt;
    }
    
    
    /**
     * Creates an instance of Map of creditor contacts where each element is keyed by business id. 
     * 
     * @param contacts
     *         a List of CreditorExt instances.
     * @return
     *        Map <{@link Integer}, {@link com.gl.creditor.CreditorExt CreditorExt}>
     */
    public Map <Integer, CreditorExt> createCreditorContactMap(List <CreditorExt> contacts) {
	Map <Integer, CreditorExt> map = new LinkedHashMap<Integer, CreditorExt>();
	for(CreditorExt item : contacts) {
	    map.put(item.getBusinessId(), item);
	}
	return map;
    }
    
    /**
     * Creates a BusinessType instance from the user's request.
     * 
     * @param request
     *         the user's request
     * @return
     *         {@link com.xml.schema.bindings.BusinessType BusinessType}
     */
    public BusinessType createtBusinessType(Request request) {
	BusinessType busContact = null;
	
	// Create JAXB objects needed to build SOAP message
	ObjectFactory f = new ObjectFactory();
	busContact = f.createBusinessType();
	AddressType at = f.createAddressType();
	CodeDetailType entityType = f.createCodeDetailType();
	CodeDetailType srvcType = f.createCodeDetailType();
	ZipcodeType zt = f.createZipcodeType();
	
	// Collect business data
	String temp = request.getParameter("BusinessId2");
	busContact.setBusinessId((temp == null || temp.equals("") ? null : BigInteger.valueOf(Integer.parseInt(temp))));
	temp = request.getParameter("ContactFirstname");
	busContact.setContactFirstname(temp);
	busContact.setLongName(request.getParameter("Longname"));
	temp = request.getParameter("ContactLastname");
	busContact.setContactLastname(temp);
	temp = request.getParameter("ContactPhone");
	busContact.setContactPhone(temp);
	temp = request.getParameter("ContactExt");
	busContact.setContactExt(temp);
	temp = request.getParameter("TaxId");
	busContact.setTaxId(temp);
	temp = request.getParameter("ContactEmail");
	busContact.setContactEmail(temp);
	temp = request.getParameter("Website");
	busContact.setWebsite(temp);
	busContact.setShortName("");
	
	temp = request.getParameter("EntityTypeId");
	entityType.setCodeId((temp == null || temp.equals("") ? null : BigInteger.valueOf(Integer.parseInt(temp))));
//	entityType.setGroupId(BigInteger.ZERO);
	entityType.setLongdesc("");
	busContact.setEntityType(entityType);
	
	temp = request.getParameter("ServTypeId");
	srvcType.setCodeId((temp == null || temp.equals("") ? null : BigInteger.valueOf(Integer.parseInt(temp))));
//	srvcType.setGroupId(BigInteger.ZERO);
	srvcType.setLongdesc("");
	busContact.setServiceType(srvcType);
	
	// Collect address data
	temp = request.getParameter("AddressId");
	at.setAddrId((temp == null || temp.equals("") ? null : BigInteger.valueOf(Integer.parseInt(temp))));
	at.setAddr1(request.getParameter("Addr1"));
	at.setAddr2(request.getParameter("Addr2"));
	at.setAddr3(request.getParameter("Addr3"));
	at.setAddr4(request.getParameter("Addr4"));
	temp = request.getParameter("PhoneMain");
	at.setPhoneMain(temp);
	temp = request.getParameter("PhoneFax");
	at.setPhoneFax(temp);
	temp = request.getParameter("Zipext");
	at.setZipExt((temp == null || temp.equals("") ? null : BigInteger.valueOf(Integer.parseInt(temp))));
	temp = request.getParameter("Zip");
	zt.setZipcode((temp == null || temp.equals("") ? null : BigInteger.valueOf(Integer.parseInt(temp))));
	
	// Add zip to address instance
	at.setZip(zt);
	
	// Add address to business instance
	busContact.setAddress(at);
	return busContact;
    }
    
    
    /**
     * Retrieves a complete list of vendors where creditor type id equals 1.  The result set is 
     * returned as a XML document String.
     * 
     * @return String
     *           XML document of one or more vendor records 
     * @throws CreditorException
     */
    public String getVendorList(Request request) throws CreditorException {
	List<CreditorExt> list;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi credApi = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), request);
	try {
	    // Get comprehensive vendor listing
	    CreditorCriteria criteria = new CreditorCriteria();
	    criteria.setQry_CreditorTypeId("1");
	    list = (List<CreditorExt>) credApi.findCreditorBusiness(criteria);
	    if (list == null) {
		list = new ArrayList<CreditorExt>();
	    }
	    return this.createCreditorContactXml(list);
	}
	catch (Exception e) {
	    throw new CreditorException(e);
	}
	finally {
		credApi = null;
		tx.close();
		tx = null;
	}
    }
    
    /**
     * Retrieves a complete list of creditors where creditor type id equals 2.  The result set is 
     * returned as a XML document String.
     * 
     * @return String
     *           XML document of one or more creditor records 
     * @throws CreditorException
     */
    public String getCreditorList(Request request) throws CreditorException {
	List<CreditorExt> list;
	DatabaseTransApi tx = DatabaseTransFactory.create();
	CreditorApi credApi = CreditorFactory.createApi((DatabaseConnectionBean) tx.getConnector(), request);
	try {
	    // Get comprehensive vendor listing
	    CreditorCriteria criteria = new CreditorCriteria();
	    criteria.setQry_CreditorTypeId("2");
	    list = (List<CreditorExt>) credApi.findCreditorBusiness(criteria);
	    if (list == null) {
		list = new ArrayList<CreditorExt>();
	    }
	    return this.createCreditorContactXml(list);
	}
	catch (Exception e) {
	    throw new CreditorException(e);
	}
	finally {
		credApi = null;
		tx.close();
		tx = null;
	}
    }
    
    /**
     * Invoke web service from the contacts application to obtain  list of business type code records 
     * in the form of an XML document.   The group id for business type codes is "7".
     * 
     * @param loginId
     *          the id of the user requesting the service
     * @return String
     *          XML Payload for the SOAP body in the form of RS_code_detail_search message
     * @throws SystemException
     */
    public String getBusinessTypeCodes(String loginId) throws MessagingException {
	// Invoke web service from the contacts application to obtain  list of general codes pertaining to the group id input parameter.
	// Create JAXB criteria object
	ObjectFactory f = new ObjectFactory();
	// Create Payload instance
	RQCodeDetailSearch ws = f.createRQCodeDetailSearch();
	HeaderType header = PayloadFactory.createHeader("RQ_code_detail_search", "SYNC", "REQUEST", loginId);
	ws.setHeader(header);
	ws.setGroupId(BigInteger.valueOf(7));
	String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	try {
	    SoapClientWrapper client = new SoapClientWrapper();
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		throw new Exception(errMsg);
	    }
	    SoapMessageHelper helper = new SoapMessageHelper();
	    String payload = helper.getBody(resp);
	    return payload;
	}
	catch (Exception e) {
	    throw new MessagingException(e);
	}
    }
    
    
    /**
     * Invoke web service from the contacts application to obtain  list of business service type code records 
     * in the form of an XML document.   The group id for business type codes is "8".
     * 
     * @param loginId
     *          the id of the user requesting the service
     * @return String
     *          XML Payload for the SOAP body in the form of RS_code_detail_search message
     * @throws SystemException
     */
    public String getBusinessServiceTypeCodes(String loginId) throws MessagingException {
	// Invoke web service from the contacts application to obtain  list of general codes pertaining to the group id input parameter.
	// Create JAXB criteria object
	ObjectFactory f = new ObjectFactory();
	// Create Payload instance
	RQCodeDetailSearch ws = f.createRQCodeDetailSearch();
	HeaderType header = PayloadFactory.createHeader("RQ_code_detail_search", "SYNC", "REQUEST", loginId);
	ws.setHeader(header);
	ws.setGroupId(BigInteger.valueOf(8));
	String msg = ResourceFactory.getJaxbMessageBinder().marshalMessage(ws);
	try {
	    SoapClientWrapper client = new SoapClientWrapper();
	    SOAPMessage resp = client.callSoap(msg);
	    if (client.isSoapError(resp)) {
		String errMsg = client.getSoapErrorMessage(resp);
		throw new Exception(errMsg);
	    }
	    SoapMessageHelper helper = new SoapMessageHelper();
	    String payload = helper.getBody(resp);
	    return payload;
	}
	catch (Exception e) {
	    throw new MessagingException(e);
	}
    }
    
    /**
     * 
     * @param so
     * @param invoiceResults
     * @return
     */
    public SalesOrderInvoiceResultType createSalesOrderInvoiceResultType(SalesOrderExt so, ServiceReturnCode invoiceResults) {
        ObjectFactory f = new ObjectFactory();
        SalesOrderInvoiceResultType rc = f.createSalesOrderInvoiceResultType(); 
        
        rc.setReturnCode(BigInteger.valueOf(invoiceResults.getCode()));
        rc.setReturnMessage(invoiceResults.getMessage());
        
        // Capture the sales order data
        rc.setSalesOrderId(BigInteger.valueOf(so.getSalesOrderId()));
        rc.setCustomerId(BigInteger.valueOf(so.getCustomerId()));
        if (so.getItems() != null) {
            rc.setItemCount(BigInteger.valueOf(so.getItems().size()));
        }
        rc.setAccountNo(so.getAccountNo());
        
        // Capture transaction data
        try {
            String xPath = "//ServiceReturnCode/data/Xact";
            String xactXml = invoiceResults.getData();
            // Get transaction date
            String temp = RMT2XmlUtility.getElementValue("xactDate", xPath, xactXml);
            XMLGregorianCalendar cal = RMT2Date.toXmlDate(temp);
            rc.setXactDate(cal);
            // Get Transaction Id
            temp = RMT2XmlUtility.getElementValue("xactId", xPath, xactXml);
            rc.setXactId(BigInteger.valueOf(Integer.parseInt(temp)));
            // Get Transaction reason
            temp = RMT2XmlUtility.getElementValue("reason", xPath, xactXml);
            rc.setXactReason(temp);
            // Get transaction sub type id
            temp = RMT2XmlUtility.getElementValue("xactSubtypeId", xPath, xactXml);
            rc.setXactSubtypeId(BigInteger.valueOf(Integer.parseInt(temp)));
            // Bet transaction type id
            temp = RMT2XmlUtility.getElementValue("xactTypeId", xPath, xactXml);
            rc.setXactTypeId(BigInteger.valueOf(Integer.parseInt(temp)));
            // Get transaction amount
            temp = RMT2XmlUtility.getElementValue("xactAmount", xPath, xactXml);
            rc.setXactTotal(BigDecimal.valueOf(Double.parseDouble(temp)));
        }
        catch (Exception e) {
            String errMsg = "Unable to set transaction data for response due to the following reason: " + (e.getMessage() == null ? "unknown reason" : e.getMessage());
            rc.setXactReason(errMsg);
            logger.error(e.getMessage());
        }
        return rc;
    }

    /**
     * 
     * @param soit
     * @return
     */
    public static SalesOrderItemType getXmlSalesOrderItemInstance(SalesOrderItems soi) {
        ObjectFactory f = new ObjectFactory();
        SalesOrderItemType soit =f.createSalesOrderItemType();
        soit.setItemId(BigInteger.valueOf(soi.getItemId()));
        soit.setSalesOrderItemId(BigInteger.valueOf(soi.getSoItemId()));
        soit.setSalesOrderId(BigInteger.valueOf(soi.getSoId()));
        soit.setItemName(soi.getItemNameOverride());
        soit.setOrderQty(BigInteger.valueOf(Double.valueOf(soi.getOrderQty()).longValue()));        
        soit.setBackOrderQty(BigDecimal.valueOf(soi.getBackOrderQty()));
        soit.setUnitCost(BigDecimal.valueOf(soi.getInitUnitCost()));
        soit.setMarkup(BigDecimal.valueOf(soi.getInitMarkup()));
        return soit;
    }

    /**
     * 
     * @param sot
     * @return
     */
    public static SalesOrderType getXmlSalesOrderInstance(SalesOrderExt soExt) {
        ObjectFactory f = new ObjectFactory();
        SalesOrderType sot = f.createSalesOrderType();
        sot.setAccountNo(soExt.getAccountNo());
        sot.setSalesOrderId(BigInteger.valueOf(soExt.getSalesOrderId()));
        sot.setCustomerId(BigInteger.valueOf(soExt.getCustomerId()));
        sot.setInvoiced(soExt.getInvoiced() == 1);
        sot.setOrderTotal(BigDecimal.valueOf(soExt.getOrderTotal()));
        sot.setSalesOrderReason(soExt.getReason());
        if (soExt.getItems() != null) {
            for (SalesOrderItems item : soExt.getItems()) {
                SalesOrderItemType soit =  JaxbAccountingFactory.getXmlSalesOrderItemInstance(item);
                sot.getItems().add(soit);
            }
        }
        return sot;
    }

    /**
     * 
     * @param soit
     * @return
     */
    public static SalesOrderItems getDtoSalesOrderItemInstance(SalesOrderItemType soit) {
        SalesOrderItems soi = new SalesOrderItems();
        soi.setItemId(soit.getItemId().intValue());
        soi.setSoItemId(soit.getSalesOrderItemId().intValue());
        soi.setSoId(soit.getSalesOrderId().intValue());
        soi.setItemNameOverride(soit.getItemName());
        soi.setOrderQty(soit.getOrderQty().intValue());
        soi.setBackOrderQty(soit.getBackOrderQty().doubleValue());
        soi.setInitUnitCost(soit.getUnitCost().doubleValue());
        soi.setInitMarkup(soit.getMarkup().doubleValue());
        return soi;
    }

    /**
     * 
     * @param sot
     * @return
     */
    public static SalesOrderExt getDtoSalesOrderInstance(SalesOrderType sot) {
        SalesOrderExt soe = new SalesOrderExt();
        soe.setSalesOrderId(sot.getSalesOrderId().intValue());
        soe.setCustomerId(sot.getCustomerId().intValue());
        soe.setInvoiced(sot.isInvoiced() ? 1 : 0);
        soe.setOrderTotal(sot.getOrderTotal().doubleValue());
        soe.setReason(sot.getSalesOrderReason());
        soe.setAccountNo(sot.getAccountNo());
        List <SalesOrderItems> items = new ArrayList<SalesOrderItems>();
        for (SalesOrderItemType item : sot.getItems()) {
            SalesOrderItems soi = JaxbAccountingFactory.getDtoSalesOrderItemInstance(item);
            items.add(soi);
        }
        soe.setItems(items);
        return soe;
    }

    public static RMT2SessionBean  getUserSession(UserSessionType obj, Request request) {
        RMT2SessionBean session;
        try {
            session = AuthenticationFactory.getSessionBeanInstance(obj.getLoginId(), obj.getOrigAppId());
        }
        catch (AuthenticationException e) {
            throw new SystemException(e);
        }
        session.setLoginId(obj.getLoginId());
        
        session.setAuthSessionId(obj.getAuthSessionId());
        session.setFirstName(obj.getFname());
        session.setLastName(obj.getLname());
        session.setAccessLevel(obj.getAccessLevel().intValue());
        session.setGatewayInterface(obj.getGatewayInterface());
        session.setRemoteAppName(obj.getRemoteAppName());
        session.setSessionCreateTime(obj.getSessionCreate());
        session.setSessionLastAccessedTime(obj.getSessionLastAccessed());
        session.setSessionMaxInactSecs(obj.getSessionMax().intValue());
        session.setGroupId(obj.getGroupId() == null ? 0 : Integer.parseInt(obj.getGroupId()));
    
        if (request != null) {
            session.setRemoteHost(request.getRemoteHost());
            session.setRemoteAddress(request.getRemoteAddr());
            session.setServerName(request.getServerName());
            session.setServerPort(request.getServerPort());
            session.setServletContext(request.getContextPath());
            session.setScheme(request.getScheme());
            session.setSessionId(request.getSession().getId());
            
            // TODO:  change interface to recognize these attributes.
            session.setServerProtocol(obj.getServerProtocol());
            session.setServerInfo(obj.getServerInfo());
            session.setServerSoftware(obj.getServerSoftware());
            session.setUserAgent(obj.getUserAgent());
            session.setLocale(obj.getLocal());
            session.setAccept(obj.getAccept());
            session.setAcceptLanguage(obj.getAcceptLang());
            session.setAcceptEncoding(obj.getAcceptEncoding());
        }
        
        List<String> roles = new ArrayList<String>();
        for (AppRoleType role : obj.getRoles()) {
            roles.add(role.getAppRoleCode());
        }
        session.setRoles(roles);
        return session;
    }
    
    
    public GenericXactCriteriaType createGenreicXactCriteriaInstance(GenericXactCriteria item) {
	if (item == null) {
	    return null;
	}
	ObjectFactory f = new ObjectFactory();
	GenericXactCriteriaType c = f.createGenericXactCriteriaType();
	
	c.setQryAccountNo(item.getQry_AccountNo());
	c.setQryBusinessName(item.getQry_BusinessName());
	c.setQryConfirmNo(item.getQry_ConfirmNo());
	c.setQryDateCreated(item.getQry_DateCreated());
	c.setQryDateUpdated(item.getQry_DateUpdated());
	c.setQryUserId(item.getQry_UserId());
	c.setQryId(item.getQry_Id());
	c.setQryInvoiceNo(item.getQry_InvoiceNo());
	c.setQryItemAmount1(item.getQry_ItemAmount_1());
	c.setQryItemAmount2(item.getQry_ItemAmount_2());
	c.setQryRelOpItemAmount1(item.getQryRelOp_ItemAmount_1());
	c.setQryRelOpItemAmount2(item.getQryRelOp_ItemAmount_2());
	c.setQryRelOpXactAmount1(item.getQryRelOp_XactAmount_1());
	c.setQryRelOpXactAmount2(item.getQryRelOp_XactAmount_2());
	c.setQryRelOpXactDate1(item.getQryRelOp_XactDate_1());
	c.setQryRelOpXactDate2(item.getQryRelOp_XactDate_2());
	c.setQryXactAccountNo(item.getQry_XactAccountNo());
	c.setQryXactAmount1(item.getQry_XactAmount_1());
	c.setQryXactAmount2(item.getQry_XactAmount_2());
	c.setQryXactDate1(item.getQry_XactDate_1());
	c.setQryXactDate2(item.getQry_XactDate_2());
	c.setQryXactId(item.getQry_XactId());
	c.setQryXactReason(item.getQry_XactReason());
	c.setQryXactReasonADVSRCHOPTS(item.getQry_XactReason_ADVSRCHOPTS());
	c.setQryXactTypeId(item.getQry_XactTypeId());
	c.setQryXactSubtypeId(item.getQry_XactSubtypeId());
	return c;
    }
    
    public List <XacttypeType> createXacttypeTypeListInstance(List<XactType> xactTypes) {
	if (xactTypes == null) {
	    return null;
	}
	
	List <XacttypeType> list = new ArrayList<XacttypeType>();
	for (XactType t : xactTypes) {
	    XacttypeType xtt = this.createXacttypeTypeInstance(t);
	    list.add(xtt);
	}
	return list;
    }
    
    public XacttypeType createXacttypeTypeInstance(XactType item) {
	ObjectFactory f = new ObjectFactory();
	XacttypeType x = f.createXacttypeType();
	x.setCode(item.getCode());
	x.setDescription(item.getDescription());
	x.setFromAcctCatgId(BigInteger.valueOf(item.getFromAcctCatgId()));
	x.setFromAcctTypeId(BigInteger.valueOf(item.getFromAcctTypeId()));
	x.setFromMultiplier(BigInteger.valueOf(item.getFromMultiplier()));
	x.setHasSubsidiary(BigInteger.valueOf(item.getHasSubsidiary()));
	x.setToAcctCatgId(BigInteger.valueOf(item.getToAcctCatgId()));
	x.setToAcctTypeId(BigInteger.valueOf(item.getToAcctTypeId()));
	x.setToMultiplier(BigInteger.valueOf(item.getToMultiplier()));
	x.setXactCatgId(BigInteger.valueOf(item.getXactCatgId()));
	x.setXactTypeId(BigInteger.valueOf(item.getXactTypeId()));
	return x;
    }
    
    public List <com.xml.schema.bindings.XactType> createXactListInstance(List<VwGenericXactList> xactList) {
	if (xactList == null) {
	    return null;
	}
	
	List <com.xml.schema.bindings.XactType> list = new ArrayList<com.xml.schema.bindings.XactType>();
	for (VwGenericXactList x : xactList) {
	    com.xml.schema.bindings.XactType xt = this.createXactInstance(x);
	    list.add(xt);
	}
	return list;
    }
    
    public com.xml.schema.bindings.XactType createXactInstance(VwGenericXactList item) {
	ObjectFactory f = new ObjectFactory();
	com.xml.schema.bindings.XactType x = f.createXactType();
	
	x.setAccountNo(item.getAccountNo());
	x.setBusinessId(BigInteger.valueOf(item.getBusinessId()));
	x.setBusinessName(item.getBusinessName());
	x.setConfirmNo(item.getConfirmNo());
	x.setDocumentId(BigInteger.valueOf(item.getDocumentId()));
	x.setInvoiceNo(item.getInvoiceNo());
	x.setItemTotal(BigDecimal.valueOf(0));
	x.setXactAmount(BigDecimal.valueOf(item.getXactAmount()));
	x.setXactDate(item.getXactDateStr());
	x.setXactDate(RMT2Date.formatDate(item.getXactDate(), "MM/dd/yyyy"));
	x.setXactId(BigInteger.valueOf(item.getXactId()));
	x.setXactReason(item.getXactReason());
	x.setXactSubtypeId(BigInteger.valueOf(item.getXactSubtypeId()));
	x.setXactTypeId(BigInteger.valueOf(item.getXactTypeId()));
	x.setXactTypeName(item.getXactTypeName());
	return x;
    }
}

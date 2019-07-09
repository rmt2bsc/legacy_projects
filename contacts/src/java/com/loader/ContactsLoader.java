package com.loader;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.api.ContactException;
import com.api.address.AddressApi;
import com.api.address.AddressFactory;
import com.api.business.BusinessApi;
import com.api.business.BusinessFactory;
import com.api.codes.CodesApi;
import com.api.codes.CodesFactory;
import com.api.codes.GeneralCodeException;
import com.api.personal.PersonApi;
import com.api.personal.PersonFactory;
import com.api.security.pool.AppPropertyPool;

import com.bean.Address;
import com.bean.Business;
import com.bean.GeneralCodes;
import com.bean.Person;
import com.bean.db.DatabaseConnectionBean;

import com.controller.Request;

import com.util.RMT2String;
import com.util.RMT2Utility;
import com.util.SystemException;
import com.util.RMT2SaxDocBase;

/**
 * This class builds a Map of {@link com.loader.LoaderTransType ContactCategory} objects, 
 * which is keyed by ContactCategory.name, by way of parsing an XML document using SAX 
 * technologies.
 * 
 * @author roy.terrell
 *
 */
public class ContactsLoader extends RMT2SaxDocBase {
    private static String USERID = "loader";

    private static Logger logger; // = Logger.getLogger(ContactsLoader.class);

    private Map<String, String> catgType;

    private DatabaseConnectionBean dbCon;

    private AddressApi addrApi;

    private BusinessApi busApi;

    private PersonApi perApi;

    private Request request;

    private int evalCount;

    private int personalTot;

    private int businessTot;

    private int skipTot;

    private int errorCount;

    private Contact contact;

    /**
     * Creates a CategoryLoader object with the XML document containing 
     * a master list of transaction types, and subsequently invoke parsing of 
     * the XML document.
     * 
     * @param doc 
     *          The path and file name of the XML document to parse.
     * @throws SystemException 
     *          Parsing errors.
     */
    private ContactsLoader(String doc) throws SystemException {
	super(doc);
	this.text = null;
	this.evalCount = 0;
	this.personalTot = 0;
	this.businessTot = 0;
	logger = Logger.getLogger("ContactsLoader");
	logger.log(Level.INFO, "Transaction loader started: " + new Date().toString());
    }

    /**
     * Created an ContactsLoader object by intializing it with a database 
     * connection bean, the user's request, and the data file that is to be processed.
     *  
     * @param dbCon
     *          The database connection bean.
     * @param request
     *          The user's request.
     * @param doc
     *          The XML document that contains the revenue and expense transactions.
     * @throws SystemException
     *          Illeagal access or resource problems.
     */
    public ContactsLoader(DatabaseConnectionBean dbCon, Request request, String doc) throws SystemException {
	this(doc);
	this.dbCon = dbCon;
	this.request = request;
	this.addrApi = AddressFactory.createAddressApi(this.dbCon, this.request);
	this.busApi = BusinessFactory.createBusinessApi(this.dbCon, this.request);
	this.perApi = PersonFactory.createPersonApi(this.dbCon, this.request);

    }

    /**
     * Initializes this instance by loading the transaction legend.
     *
     */
    private void init() {
	String catgFilename = AppPropertyPool.getProperty("LoaderCatgFilename");
	if (this.docPath == null) {
	    this.docPath = AppPropertyPool.getProperty("LoaderDataPath");
	}
	CategoryLoader catgLoad = new CategoryLoader(this.docPath + catgFilename);
	catgLoad.parseDocument();
	this.catgType = catgLoad.getCategories();
	return;
    }

    /**
     * Obtains the SAX driver needed to parse document
     * 
     * @throws SystemException
     */
    protected void getDocResources() throws SystemException {
	this.init();
	this.docName = AppPropertyPool.getProperty("LoaderDataFilename");
	this.saxDriver = AppPropertyPool.getProperty("SAXDriver");
	this.docName = this.docPath + this.docName;
	return;
    }

    /**
     * Renders the total number of legend items loaded..
     * 
     * @throws SAXException
     */
    public void endDocument() throws SAXException {
	super.endDocument();
	logger.log(Level.INFO, "Total contacts evaluated: " + this.evalCount);
	logger.log(Level.INFO, "Total personal contacts processed: " + this.personalTot);
	logger.log(Level.INFO, "Total business contacts processed: " + this.businessTot);
	logger.log(Level.INFO, "Total contacts skipped: " + this.skipTot);
	logger.log(Level.INFO, "Total errors: " + this.errorCount);
    }

    /**
     * Creates a new {@link Contact} instance once a new <i>item</i> element is encountered.
     * 
     * @param elementName 
     *          the name of the element to process
     * @param amap 
     *          a collection of attributes belonging to the tag named, <i>elementName<?i>.
     * @throws SAXException
     */
    public void startElement(String elementName, AttributeList amap) throws SAXException {
	super.startElement(elementName, amap);
	if (elementName.equalsIgnoreCase("item")) {
	    this.contact = new Contact();
	}
    }

    /**
     * Builds a <i>Contact</i> instance from each <i>item</i> element of the XML document and 
     * applies the finished {@link Contact} object to the database.  Elements belonging to the  
     * <i>item</i> tag are used to build the Contact instance.  Once the <i>item</i> tag is 
     * encountered, the Contact instance is considered built and its data is ready to be applied
     * to the database.  The root tag, <i>Contacts</i>, is completely ignored.
     * 
     * @param elementName 
     *          the element name to be evalutated.  
     * @throws SAXException
     */
    public void endElement(String elementName) throws SAXException {
	super.endElement(elementName);

	if (elementName.equalsIgnoreCase("item")) {
	    try {
		this.processContact(this.contact);
		this.dbCon.getDbConn().commit();
	    }
	    catch (Exception e) {
		logger.log(Level.ERROR, e.getMessage());
		this.errorCount++;
		try {
		    this.dbCon.getDbConn().rollback();
		}
		catch (SQLException e1) {
		    e1.printStackTrace();
		}
	    }
	    finally {
		this.contact = null;
	    }
	    return;
	}

	if (elementName.equalsIgnoreCase("name")) {
	    this.contact.setName(this.text);
	}
	if (elementName.equalsIgnoreCase("email")) {
	    this.contact.setEmail(this.text);
	}
	if (elementName.equalsIgnoreCase("home_street")) {
	    this.contact.setHomeStreet(this.text);
	}
	if (elementName.equalsIgnoreCase("home_street2")) {
	    this.contact.setHomeStreet2(this.text);
	}
	if (elementName.equalsIgnoreCase("home_street3")) {
	    this.contact.setHomeStreet3(this.text);
	}
	if (elementName.equalsIgnoreCase("home_city")) {
	    this.contact.setHomeCity(this.text);
	}
	if (elementName.equalsIgnoreCase("home_zip")) {
	    this.contact.setHomeZip(this.text);
	}
	if (elementName.equalsIgnoreCase("home_state")) {
	    this.contact.setHomeState(this.text);
	}
	if (elementName.equalsIgnoreCase("home_country")) {
	    this.contact.setHomeCountry(this.text);
	}
	if (elementName.equalsIgnoreCase("home_fax")) {
	    this.contact.setHomeFax(this.text);
	}
	if (elementName.equalsIgnoreCase("cell_phone")) {
	    this.contact.setCellPhone(this.text);
	}
	if (elementName.equalsIgnoreCase("other_phone")) {
	    this.contact.setOtherPhone(this.text);
	}
	if (elementName.equalsIgnoreCase("other_fax")) {
	    this.contact.setOtherFax(this.text);
	}
	if (elementName.equalsIgnoreCase("pager")) {
	    this.contact.setPager(this.text);
	}
	if (elementName.equalsIgnoreCase("website")) {
	    this.contact.setWebsite(this.text);
	}
	if (elementName.equalsIgnoreCase("bus_street")) {
	    this.contact.setBusStreet(this.text);
	}
	if (elementName.equalsIgnoreCase("bus_city")) {
	    this.contact.setBusCity(this.text);
	}
	if (elementName.equalsIgnoreCase("bus_zip")) {
	    this.contact.setBusZip(this.text);
	}
	if (elementName.equalsIgnoreCase("bus_state")) {
	    this.contact.setBusState(this.text);
	}
	if (elementName.equalsIgnoreCase("bus_country")) {
	    this.contact.setBusCountry(this.text);
	}
	if (elementName.equalsIgnoreCase("bus_phone")) {
	    this.contact.setBusPhone(this.text);
	}
	if (elementName.equalsIgnoreCase("company")) {
	    this.contact.setCompany(this.text);
	}
	if (elementName.equalsIgnoreCase("category")) {
	    this.contact.setCategory(this.text);
	}
	if (elementName.equalsIgnoreCase("job_title")) {
	    this.contact.setJobTitle(this.text);
	}
    }

    /**
     * Begins processing the contact by determining if the contact is a business 
     * or a person.   Keeps a tally of the total number of records evaluated, 
     * the total number of personal contacts processed, the total number of
     * business contacts processed, and the total number of contacts skipped.
     * <p>
     * There are certain requirements to be met in order for a contact to be identified 
     * as personal or business.   A personal contact must be populated with the name
     * of the person's name only.   A contact is considered to be a business contact 
     * only when the business name exists.   Optionally, a business contact can contain 
     * the person's name which is treated as the business' personal contact data.
     * 
     * @param c
     *          an instance of {@link Contact}
     * @throws SystemException
     */
    private void processContact(Contact c) throws ContactLoaderException {
	this.evalCount++;

	if (c.getName() != null && c.getCompany() == null) {
	    this.createPerson(c);
	    this.personalTot++;
	    return;
	}
	if ((c.getName() == null && c.getCompany() != null) || (c.getName() != null && c.getCompany() != null)) {
	    this.createBusiness(c);
	    this.businessTot++;
	    return;
	}

	// We are only accepting contacts that can be identified by name and/or company
	this.skipTot++;
	return;
    }

    /**
     * Processes a person {@link Contact} instnace.
     * 
     * @param person
     *          the {@link Contact} instance identified as a person
     * @return int
     *           returns 1 for success and -1 for failure.
     * @throws ContactLoaderException
     */
    private int createPerson(Contact person) throws ContactLoaderException {
	Person p = PersonFactory.createPerson();
	Address a = AddressFactory.createAddress();

	logger.log(Level.INFO, "Processing person: " + person.getName());

	// Person's name should exist as <last name>, <first name> format.
	// Separate into individual components.
	List<String> names = RMT2String.getTokens(person.getName(), ",");
	if (names.size() > 1) {
	    p.setFirstname(names.get(1).trim());
	    p.setLastname(names.get(0).trim());
	}
	else {
	    throw new ContactLoaderException("Unable to process personal contact...first name and last name could not be created from, " + person.getName());
	}

	if (p.getFirstname().indexOf("(") >= 0) {
	    throw new ContactLoaderException("Unable to process personal contact...first name is invalid, " + person.getName());	    
	}
	if (p.getLastname().equals("''")) {
	    throw new ContactLoaderException("Unable to process personal contact...lastt name is invalid, " + person.getName());	    
	}
	if (person.getEmail() != null && !person.getEmail().equals("")) {
	    p.setEmail(person.getEmail());
	}

	p.setNull(Person.PROP_CATEGORYID);
	if (person.getCategory() != null) {
	    GeneralCodes gc = this.getCategoryId(person.getCategory());
	    if (gc != null && gc.getCodeId() > 0) {
		p.setCategoryId(gc.getCodeId());
	    }
	}

	// Populate Address
	if (person.getHomeStreet() != null && !person.getHomeStreet().equals("")) {
	    a.setAddr1(person.getHomeStreet());
	}
	if (person.getHomeStreet2() != null && !person.getHomeStreet2().equals("")) {
	    a.setAddr2(person.getHomeStreet2());
	}
	if (person.getHomeStreet3() != null && !person.getHomeStreet3().equals("")) {
	    a.setAddr3(person.getHomeStreet3());
	}
	if (person.getHomeZip() != null && !person.getHomeZip().equals("")) {
	    int zip = Integer.parseInt(person.getHomeZip());
	    a.setZip(zip);
	}
	if (person.getCellPhone() != null && !person.getCellPhone().equals("")) {
	    a.setPhoneCell(person.getCellPhone());
	}
	if (person.getOtherPhone() != null && !person.getOtherPhone().equals("")) {
	    a.setPhoneMain(person.getOtherPhone());
	}
	if (person.getOtherFax() != null && !person.getOtherFax().equals("")) {
	    a.setPhoneFax(person.getOtherFax());
	}
	if (person.getPager() != null && !person.getPager().equals("")) {
	    a.setPhonePager(person.getPager());
	}

	// Apply personal contact to the database
	try {
	    p.setUserId(ContactsLoader.USERID);
	    a.setUserId(ContactsLoader.USERID);
	    this.perApi.maintainContact(p);
	    a.setPersonId(p.getPersonId());
	    this.addrApi.maintainContact(a);
	}
	catch (ContactException e) {
	    logger.log(Level.ERROR, "Unable to add personal contact, " + person.getName());
	    logger.log(Level.ERROR, "Error ==> " + e.getMessage());
	}
	return 1;
    }

    /**
     * Processes a business {@link Contact}
     *  
     * @param trans
     *          The source transaction.
     * @return int
     *          the transaction id.
     * @throws ContactLoaderException
     */
    private int createBusiness(Contact business) throws ContactLoaderException {
	String busName = null;
	Business b = BusinessFactory.createBusiness();
	Address a = AddressFactory.createAddress();
	if (business.getName() == null && business.getCompany() != null) {
	    busName = business.getCompany();
	}

	//  Business contact may have a personal contact associated with it.
	if (business.getName() != null && business.getCompany() != null) {
	    busName = business.getCompany() + " with contact, " + business.getName();

	    // Contact's name should be treated as business' personal contact.
	    // Separate into individual components.
	    List<String> names = RMT2String.getTokens(business.getName(), ",");
	    if (names.size() > 1) {
		b.setContactFirstname(names.get(1).trim());
		b.setContactLastname(names.get(0).trim());
	    }
	    else {
		throw new ContactLoaderException("Unable to process business contact, " + busName + " due to invalid personal contact name data");
	    }

	    // Contact may have both home and cellular phone numbers.   Let cellular number take precedence over home phone
	    if (business.getHomePhone() != null && !business.getHomePhone().equals("")) {
		b.setContactPhone(business.getHomePhone());
	    }
	    if (business.getCellPhone() != null && !business.getCellPhone().equals("")) {
		b.setContactPhone(business.getCellPhone());
	    }
	}

	logger.log(Level.INFO, "Processing business: " + busName);
	if (business.getEmail() != null && !business.getEmail().equals("")) {
	    b.setContactEmail(business.getEmail());
	}
	b.setLongname(business.getCompany());
	b.setWebsite(business.getWebsite());

	b.setNull(Person.PROP_CATEGORYID);
	if (business.getCategory() != null) {
	    GeneralCodes gc = this.getCategoryId(business.getCategory());
	    if (gc != null && gc.getCodeId() > 0) {
		b.setCategoryId(gc.getCodeId());
	    }
	}

	// Populate Address
	if (business.getBusStreet() != null && !business.getBusStreet().equals("")) {
	    a.setAddr1(business.getBusStreet());
	}
	if (business.getBusStreet3() != null && !business.getBusStreet3().equals("")) {
	    a.setAddr3(business.getBusStreet3());
	}
	if (business.getBusZip() != null && !business.getBusZip().equals("")) {
	    int zip = Integer.parseInt(business.getBusZip());
	    a.setZip(zip);
	}

	// Process phone number and possible phone extension
	if (business.getBusPhone() != null && !business.getBusPhone().equals("")) {
	    List<String> phone = RMT2Utility.cleanUpPhoneNo(business.getBusPhone());
	    if (phone.size() > 0) {
		a.setPhoneMain(phone.get(0));
	    }
	    if (phone.size() > 1) {
		a.setPhoneExt(phone.get(1));
	    }
	}
	if (business.getBusFax() != null && !business.getBusFax().equals("")) {
	    a.setPhoneFax(business.getBusFax());
	}
	if (business.getPager() != null && !business.getPager().equals("")) {
	    a.setPhonePager(business.getPager());
	}

	// Apply business contact to the database
	try {
	    b.setUserId(ContactsLoader.USERID);
	    a.setUserId(ContactsLoader.USERID);
	    this.busApi.maintainContact(b);
	    a.setBusinessId(b.getBusinessId());
	    this.addrApi.maintainContact(a);

	}
	catch (ContactException e) {
	    logger.log(Level.ERROR, "Unable to add business contact, " + busName);
	    logger.log(Level.ERROR, "Error ==> " + e.getMessage());
	}
	return 1;
    }

    private GeneralCodes getCategoryId(String name) {
	CodesApi gcApi = CodesFactory.createCodesApi(this.dbCon);
	try {
	    List list = (List) gcApi.findCodeByDescription(name);
	    if (list != null && list.size() == 1) {
		GeneralCodes gc = (GeneralCodes) list.get(0);
		return gc;
	    }
	}
	catch (GeneralCodeException e) {
	    logger.log(Level.WARN, e.getMessage());
	}
	return null;
    }

    /**
     * @return the businessTot
     */
    public int getBusinessTot() {
	return businessTot;
    }

    /**
     * @return the errorCount
     */
    public int getErrorCount() {
	return errorCount;
    }

    /**
     * @return the evalCount
     */
    public int getEvalCount() {
	return evalCount;
    }

    /**
     * @return the personalTot
     */
    public int getPersonalTot() {
	return personalTot;
    }

    /**
     * @return the skipTot
     */
    public int getSkipTot() {
	return skipTot;
    }

}

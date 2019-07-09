package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_business_address database table/view.
 *
 * @author auto generated.
 */
public class VwBusinessAddress extends OrmBean {




	// Property name constants that belong to respective DataSource, VwBusinessAddressView

/** The property name constant equivalent to property, AddrId, of respective DataSource view. */
  public static final String PROP_ADDRID = "AddrId";
/** The property name constant equivalent to property, AddrPhoneCell, of respective DataSource view. */
  public static final String PROP_ADDRPHONECELL = "AddrPhoneCell";
/** The property name constant equivalent to property, AddrPhoneExt, of respective DataSource view. */
  public static final String PROP_ADDRPHONEEXT = "AddrPhoneExt";
/** The property name constant equivalent to property, AddrPhoneFax, of respective DataSource view. */
  public static final String PROP_ADDRPHONEFAX = "AddrPhoneFax";
/** The property name constant equivalent to property, AddrPhoneHome, of respective DataSource view. */
  public static final String PROP_ADDRPHONEHOME = "AddrPhoneHome";
/** The property name constant equivalent to property, AddrPhoneMain, of respective DataSource view. */
  public static final String PROP_ADDRPHONEMAIN = "AddrPhoneMain";
/** The property name constant equivalent to property, AddrPhonePager, of respective DataSource view. */
  public static final String PROP_ADDRPHONEPAGER = "AddrPhonePager";
/** The property name constant equivalent to property, AddrPhoneWork, of respective DataSource view. */
  public static final String PROP_ADDRPHONEWORK = "AddrPhoneWork";
/** The property name constant equivalent to property, AddrZip, of respective DataSource view. */
  public static final String PROP_ADDRZIP = "AddrZip";
/** The property name constant equivalent to property, AddrZipext, of respective DataSource view. */
  public static final String PROP_ADDRZIPEXT = "AddrZipext";
/** The property name constant equivalent to property, Addr1, of respective DataSource view. */
  public static final String PROP_ADDR1 = "Addr1";
/** The property name constant equivalent to property, Addr2, of respective DataSource view. */
  public static final String PROP_ADDR2 = "Addr2";
/** The property name constant equivalent to property, Addr3, of respective DataSource view. */
  public static final String PROP_ADDR3 = "Addr3";
/** The property name constant equivalent to property, Addr4, of respective DataSource view. */
  public static final String PROP_ADDR4 = "Addr4";
/** The property name constant equivalent to property, AddrPersonId, of respective DataSource view. */
  public static final String PROP_ADDRPERSONID = "AddrPersonId";
/** The property name constant equivalent to property, AddrBusinessId, of respective DataSource view. */
  public static final String PROP_ADDRBUSINESSID = "AddrBusinessId";
/** The property name constant equivalent to property, ZipCity, of respective DataSource view. */
  public static final String PROP_ZIPCITY = "ZipCity";
/** The property name constant equivalent to property, ZipState, of respective DataSource view. */
  public static final String PROP_ZIPSTATE = "ZipState";
/** The property name constant equivalent to property, BusContactExt, of respective DataSource view. */
  public static final String PROP_BUSCONTACTEXT = "BusContactExt";
/** The property name constant equivalent to property, BusContactFirstname, of respective DataSource view. */
  public static final String PROP_BUSCONTACTFIRSTNAME = "BusContactFirstname";
/** The property name constant equivalent to property, BusContactLastname, of respective DataSource view. */
  public static final String PROP_BUSCONTACTLASTNAME = "BusContactLastname";
/** The property name constant equivalent to property, BusContactPhone, of respective DataSource view. */
  public static final String PROP_BUSCONTACTPHONE = "BusContactPhone";
/** The property name constant equivalent to property, BusLongname, of respective DataSource view. */
  public static final String PROP_BUSLONGNAME = "BusLongname";
/** The property name constant equivalent to property, BusServTypeId, of respective DataSource view. */
  public static final String PROP_BUSSERVTYPEID = "BusServTypeId";
/** The property name constant equivalent to property, BusShortname, of respective DataSource view. */
  public static final String PROP_BUSSHORTNAME = "BusShortname";
/** The property name constant equivalent to property, ContactEmail, of respective DataSource view. */
  public static final String PROP_CONTACTEMAIL = "ContactEmail";
/** The property name constant equivalent to property, BusTaxId, of respective DataSource view. */
  public static final String PROP_BUSTAXID = "BusTaxId";
/** The property name constant equivalent to property, BusWebsite, of respective DataSource view. */
  public static final String PROP_BUSWEBSITE = "BusWebsite";
/** The property name constant equivalent to property, BusEntityTypeId, of respective DataSource view. */
  public static final String PROP_BUSENTITYTYPEID = "BusEntityTypeId";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";



	/** The javabean property equivalent of database column vw_business_address.addr_id */
  private int addrId;
/** The javabean property equivalent of database column vw_business_address.addr_phone_cell */
  private String addrPhoneCell;
/** The javabean property equivalent of database column vw_business_address.addr_phone_ext */
  private String addrPhoneExt;
/** The javabean property equivalent of database column vw_business_address.addr_phone_fax */
  private String addrPhoneFax;
/** The javabean property equivalent of database column vw_business_address.addr_phone_home */
  private String addrPhoneHome;
/** The javabean property equivalent of database column vw_business_address.addr_phone_main */
  private String addrPhoneMain;
/** The javabean property equivalent of database column vw_business_address.addr_phone_pager */
  private String addrPhonePager;
/** The javabean property equivalent of database column vw_business_address.addr_phone_work */
  private String addrPhoneWork;
/** The javabean property equivalent of database column vw_business_address.addr_zip */
  private int addrZip;
/** The javabean property equivalent of database column vw_business_address.addr_zipext */
  private int addrZipext;
/** The javabean property equivalent of database column vw_business_address.addr1 */
  private String addr1;
/** The javabean property equivalent of database column vw_business_address.addr2 */
  private String addr2;
/** The javabean property equivalent of database column vw_business_address.addr3 */
  private String addr3;
/** The javabean property equivalent of database column vw_business_address.addr4 */
  private String addr4;
/** The javabean property equivalent of database column vw_business_address.addr_person_id */
  private int addrPersonId;
/** The javabean property equivalent of database column vw_business_address.addr_business_id */
  private int addrBusinessId;
/** The javabean property equivalent of database column vw_business_address.zip_city */
  private String zipCity;
/** The javabean property equivalent of database column vw_business_address.zip_state */
  private String zipState;
/** The javabean property equivalent of database column vw_business_address.bus_contact_ext */
  private String busContactExt;
/** The javabean property equivalent of database column vw_business_address.bus_contact_firstname */
  private String busContactFirstname;
/** The javabean property equivalent of database column vw_business_address.bus_contact_lastname */
  private String busContactLastname;
/** The javabean property equivalent of database column vw_business_address.bus_contact_phone */
  private String busContactPhone;
/** The javabean property equivalent of database column vw_business_address.bus_longname */
  private String busLongname;
/** The javabean property equivalent of database column vw_business_address.bus_serv_type_id */
  private int busServTypeId;
/** The javabean property equivalent of database column vw_business_address.bus_shortname */
  private String busShortname;
/** The javabean property equivalent of database column vw_business_address.contact_email */
  private String contactEmail;
/** The javabean property equivalent of database column vw_business_address.bus_tax_id */
  private String busTaxId;
/** The javabean property equivalent of database column vw_business_address.bus_website */
  private String busWebsite;
/** The javabean property equivalent of database column vw_business_address.bus_entity_type_id */
  private int busEntityTypeId;
/** The javabean property equivalent of database column vw_business_address.business_id */
  private int businessId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwBusinessAddress() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable addrId
 *
 * @author auto generated.
 */
  public void setAddrId(int value) {
    this.addrId = value;
  }
/**
 * Gets the value of member variable addrId
 *
 * @author atuo generated.
 */
  public int getAddrId() {
    return this.addrId;
  }
/**
 * Sets the value of member variable addrPhoneCell
 *
 * @author auto generated.
 */
  public void setAddrPhoneCell(String value) {
    this.addrPhoneCell = value;
  }
/**
 * Gets the value of member variable addrPhoneCell
 *
 * @author atuo generated.
 */
  public String getAddrPhoneCell() {
    return this.addrPhoneCell;
  }
/**
 * Sets the value of member variable addrPhoneExt
 *
 * @author auto generated.
 */
  public void setAddrPhoneExt(String value) {
    this.addrPhoneExt = value;
  }
/**
 * Gets the value of member variable addrPhoneExt
 *
 * @author atuo generated.
 */
  public String getAddrPhoneExt() {
    return this.addrPhoneExt;
  }
/**
 * Sets the value of member variable addrPhoneFax
 *
 * @author auto generated.
 */
  public void setAddrPhoneFax(String value) {
    this.addrPhoneFax = value;
  }
/**
 * Gets the value of member variable addrPhoneFax
 *
 * @author atuo generated.
 */
  public String getAddrPhoneFax() {
    return this.addrPhoneFax;
  }
/**
 * Sets the value of member variable addrPhoneHome
 *
 * @author auto generated.
 */
  public void setAddrPhoneHome(String value) {
    this.addrPhoneHome = value;
  }
/**
 * Gets the value of member variable addrPhoneHome
 *
 * @author atuo generated.
 */
  public String getAddrPhoneHome() {
    return this.addrPhoneHome;
  }
/**
 * Sets the value of member variable addrPhoneMain
 *
 * @author auto generated.
 */
  public void setAddrPhoneMain(String value) {
    this.addrPhoneMain = value;
  }
/**
 * Gets the value of member variable addrPhoneMain
 *
 * @author atuo generated.
 */
  public String getAddrPhoneMain() {
    return this.addrPhoneMain;
  }
/**
 * Sets the value of member variable addrPhonePager
 *
 * @author auto generated.
 */
  public void setAddrPhonePager(String value) {
    this.addrPhonePager = value;
  }
/**
 * Gets the value of member variable addrPhonePager
 *
 * @author atuo generated.
 */
  public String getAddrPhonePager() {
    return this.addrPhonePager;
  }
/**
 * Sets the value of member variable addrPhoneWork
 *
 * @author auto generated.
 */
  public void setAddrPhoneWork(String value) {
    this.addrPhoneWork = value;
  }
/**
 * Gets the value of member variable addrPhoneWork
 *
 * @author atuo generated.
 */
  public String getAddrPhoneWork() {
    return this.addrPhoneWork;
  }
/**
 * Sets the value of member variable addrZip
 *
 * @author auto generated.
 */
  public void setAddrZip(int value) {
    this.addrZip = value;
  }
/**
 * Gets the value of member variable addrZip
 *
 * @author atuo generated.
 */
  public int getAddrZip() {
    return this.addrZip;
  }
/**
 * Sets the value of member variable addrZipext
 *
 * @author auto generated.
 */
  public void setAddrZipext(int value) {
    this.addrZipext = value;
  }
/**
 * Gets the value of member variable addrZipext
 *
 * @author atuo generated.
 */
  public int getAddrZipext() {
    return this.addrZipext;
  }
/**
 * Sets the value of member variable addr1
 *
 * @author auto generated.
 */
  public void setAddr1(String value) {
    this.addr1 = value;
  }
/**
 * Gets the value of member variable addr1
 *
 * @author atuo generated.
 */
  public String getAddr1() {
    return this.addr1;
  }
/**
 * Sets the value of member variable addr2
 *
 * @author auto generated.
 */
  public void setAddr2(String value) {
    this.addr2 = value;
  }
/**
 * Gets the value of member variable addr2
 *
 * @author atuo generated.
 */
  public String getAddr2() {
    return this.addr2;
  }
/**
 * Sets the value of member variable addr3
 *
 * @author auto generated.
 */
  public void setAddr3(String value) {
    this.addr3 = value;
  }
/**
 * Gets the value of member variable addr3
 *
 * @author atuo generated.
 */
  public String getAddr3() {
    return this.addr3;
  }
/**
 * Sets the value of member variable addr4
 *
 * @author auto generated.
 */
  public void setAddr4(String value) {
    this.addr4 = value;
  }
/**
 * Gets the value of member variable addr4
 *
 * @author atuo generated.
 */
  public String getAddr4() {
    return this.addr4;
  }
/**
 * Sets the value of member variable addrPersonId
 *
 * @author auto generated.
 */
  public void setAddrPersonId(int value) {
    this.addrPersonId = value;
  }
/**
 * Gets the value of member variable addrPersonId
 *
 * @author atuo generated.
 */
  public int getAddrPersonId() {
    return this.addrPersonId;
  }
/**
 * Sets the value of member variable addrBusinessId
 *
 * @author auto generated.
 */
  public void setAddrBusinessId(int value) {
    this.addrBusinessId = value;
  }
/**
 * Gets the value of member variable addrBusinessId
 *
 * @author atuo generated.
 */
  public int getAddrBusinessId() {
    return this.addrBusinessId;
  }
/**
 * Sets the value of member variable zipCity
 *
 * @author auto generated.
 */
  public void setZipCity(String value) {
    this.zipCity = value;
  }
/**
 * Gets the value of member variable zipCity
 *
 * @author atuo generated.
 */
  public String getZipCity() {
    return this.zipCity;
  }
/**
 * Sets the value of member variable zipState
 *
 * @author auto generated.
 */
  public void setZipState(String value) {
    this.zipState = value;
  }
/**
 * Gets the value of member variable zipState
 *
 * @author atuo generated.
 */
  public String getZipState() {
    return this.zipState;
  }
/**
 * Sets the value of member variable busContactExt
 *
 * @author auto generated.
 */
  public void setBusContactExt(String value) {
    this.busContactExt = value;
  }
/**
 * Gets the value of member variable busContactExt
 *
 * @author atuo generated.
 */
  public String getBusContactExt() {
    return this.busContactExt;
  }
/**
 * Sets the value of member variable busContactFirstname
 *
 * @author auto generated.
 */
  public void setBusContactFirstname(String value) {
    this.busContactFirstname = value;
  }
/**
 * Gets the value of member variable busContactFirstname
 *
 * @author atuo generated.
 */
  public String getBusContactFirstname() {
    return this.busContactFirstname;
  }
/**
 * Sets the value of member variable busContactLastname
 *
 * @author auto generated.
 */
  public void setBusContactLastname(String value) {
    this.busContactLastname = value;
  }
/**
 * Gets the value of member variable busContactLastname
 *
 * @author atuo generated.
 */
  public String getBusContactLastname() {
    return this.busContactLastname;
  }
/**
 * Sets the value of member variable busContactPhone
 *
 * @author auto generated.
 */
  public void setBusContactPhone(String value) {
    this.busContactPhone = value;
  }
/**
 * Gets the value of member variable busContactPhone
 *
 * @author atuo generated.
 */
  public String getBusContactPhone() {
    return this.busContactPhone;
  }
/**
 * Sets the value of member variable busLongname
 *
 * @author auto generated.
 */
  public void setBusLongname(String value) {
    this.busLongname = value;
  }
/**
 * Gets the value of member variable busLongname
 *
 * @author atuo generated.
 */
  public String getBusLongname() {
    return this.busLongname;
  }
/**
 * Sets the value of member variable busServTypeId
 *
 * @author auto generated.
 */
  public void setBusServTypeId(int value) {
    this.busServTypeId = value;
  }
/**
 * Gets the value of member variable busServTypeId
 *
 * @author atuo generated.
 */
  public int getBusServTypeId() {
    return this.busServTypeId;
  }
/**
 * Sets the value of member variable busShortname
 *
 * @author auto generated.
 */
  public void setBusShortname(String value) {
    this.busShortname = value;
  }
/**
 * Gets the value of member variable busShortname
 *
 * @author atuo generated.
 */
  public String getBusShortname() {
    return this.busShortname;
  }
/**
 * Sets the value of member variable contactEmail
 *
 * @author auto generated.
 */
  public void setContactEmail(String value) {
    this.contactEmail = value;
  }
/**
 * Gets the value of member variable contactEmail
 *
 * @author atuo generated.
 */
  public String getContactEmail() {
    return this.contactEmail;
  }
/**
 * Sets the value of member variable busTaxId
 *
 * @author auto generated.
 */
  public void setBusTaxId(String value) {
    this.busTaxId = value;
  }
/**
 * Gets the value of member variable busTaxId
 *
 * @author atuo generated.
 */
  public String getBusTaxId() {
    return this.busTaxId;
  }
/**
 * Sets the value of member variable busWebsite
 *
 * @author auto generated.
 */
  public void setBusWebsite(String value) {
    this.busWebsite = value;
  }
/**
 * Gets the value of member variable busWebsite
 *
 * @author atuo generated.
 */
  public String getBusWebsite() {
    return this.busWebsite;
  }
/**
 * Sets the value of member variable busEntityTypeId
 *
 * @author auto generated.
 */
  public void setBusEntityTypeId(int value) {
    this.busEntityTypeId = value;
  }
/**
 * Gets the value of member variable busEntityTypeId
 *
 * @author atuo generated.
 */
  public int getBusEntityTypeId() {
    return this.busEntityTypeId;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author auto generated.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author atuo generated.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
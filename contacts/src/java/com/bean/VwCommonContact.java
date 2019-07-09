package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_common_contact database table/view.
 *
 * @author auto generated.
 */
public class VwCommonContact extends OrmBean {




	// Property name constants that belong to respective DataSource, VwCommonContactView

/** The property name constant equivalent to property, ContactId, of respective DataSource view. */
  public static final String PROP_CONTACTID = "ContactId";
/** The property name constant equivalent to property, ContactName, of respective DataSource view. */
  public static final String PROP_CONTACTNAME = "ContactName";
/** The property name constant equivalent to property, ContactType, of respective DataSource view. */
  public static final String PROP_CONTACTTYPE = "ContactType";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
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
/** The property name constant equivalent to property, ZipCity, of respective DataSource view. */
  public static final String PROP_ZIPCITY = "ZipCity";
/** The property name constant equivalent to property, ZipState, of respective DataSource view. */
  public static final String PROP_ZIPSTATE = "ZipState";



	/** The javabean property equivalent of database column vw_common_contact.contact_id */
  private int contactId;
/** The javabean property equivalent of database column vw_common_contact.contact_name */
  private String contactName;
/** The javabean property equivalent of database column vw_common_contact.contact_type */
  private String contactType;
/** The javabean property equivalent of database column vw_common_contact.email */
  private String email;
/** The javabean property equivalent of database column vw_common_contact.addr_id */
  private int addrId;
/** The javabean property equivalent of database column vw_common_contact.addr_phone_cell */
  private String addrPhoneCell;
/** The javabean property equivalent of database column vw_common_contact.addr_phone_ext */
  private String addrPhoneExt;
/** The javabean property equivalent of database column vw_common_contact.addr_phone_fax */
  private String addrPhoneFax;
/** The javabean property equivalent of database column vw_common_contact.addr_phone_home */
  private String addrPhoneHome;
/** The javabean property equivalent of database column vw_common_contact.addr_phone_main */
  private String addrPhoneMain;
/** The javabean property equivalent of database column vw_common_contact.addr_phone_pager */
  private String addrPhonePager;
/** The javabean property equivalent of database column vw_common_contact.addr_phone_work */
  private String addrPhoneWork;
/** The javabean property equivalent of database column vw_common_contact.addr_zip */
  private int addrZip;
/** The javabean property equivalent of database column vw_common_contact.addr_zipext */
  private int addrZipext;
/** The javabean property equivalent of database column vw_common_contact.addr1 */
  private String addr1;
/** The javabean property equivalent of database column vw_common_contact.addr2 */
  private String addr2;
/** The javabean property equivalent of database column vw_common_contact.addr3 */
  private String addr3;
/** The javabean property equivalent of database column vw_common_contact.addr4 */
  private String addr4;
/** The javabean property equivalent of database column vw_common_contact.zip_city */
  private String zipCity;
/** The javabean property equivalent of database column vw_common_contact.zip_state */
  private String zipState;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwCommonContact() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable contactId
 *
 * @author auto generated.
 */
  public void setContactId(int value) {
    this.contactId = value;
  }
/**
 * Gets the value of member variable contactId
 *
 * @author atuo generated.
 */
  public int getContactId() {
    return this.contactId;
  }
/**
 * Sets the value of member variable contactName
 *
 * @author auto generated.
 */
  public void setContactName(String value) {
    this.contactName = value;
  }
/**
 * Gets the value of member variable contactName
 *
 * @author atuo generated.
 */
  public String getContactName() {
    return this.contactName;
  }
/**
 * Sets the value of member variable contactType
 *
 * @author auto generated.
 */
  public void setContactType(String value) {
    this.contactType = value;
  }
/**
 * Gets the value of member variable contactType
 *
 * @author atuo generated.
 */
  public String getContactType() {
    return this.contactType;
  }
/**
 * Sets the value of member variable email
 *
 * @author auto generated.
 */
  public void setEmail(String value) {
    this.email = value;
  }
/**
 * Gets the value of member variable email
 *
 * @author atuo generated.
 */
  public String getEmail() {
    return this.email;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
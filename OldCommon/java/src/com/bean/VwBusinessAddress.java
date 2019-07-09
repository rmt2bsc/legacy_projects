package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_business_address database table/view.
 *
 * @author Roy Terrell.
 */
public class VwBusinessAddress extends OrmBean {

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
  private String addrZipext;
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
/** The javabean property equivalent of database column vw_business_address.bus_serv_type */
  private int busServType;
/** The javabean property equivalent of database column vw_business_address.bus_shortname */
  private String busShortname;
/** The javabean property equivalent of database column vw_business_address.bus_tax_id */
  private String busTaxId;
/** The javabean property equivalent of database column vw_business_address.bus_website */
  private String busWebsite;
/** The javabean property equivalent of database column vw_business_address.bus_type */
  private int busType;
/** The javabean property equivalent of database column vw_business_address.business_id */
  private int businessId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwBusinessAddress() throws SystemException {
  }
/**
 * Sets the value of member variable addrId
 *
 * @author Roy Terrell.
 */
  public void setAddrId(int value) {
    this.addrId = value;
  }
/**
 * Gets the value of member variable addrId
 *
 * @author Roy Terrell.
 */
  public int getAddrId() {
    return this.addrId;
  }
/**
 * Sets the value of member variable addrPhoneCell
 *
 * @author Roy Terrell.
 */
  public void setAddrPhoneCell(String value) {
    this.addrPhoneCell = value;
  }
/**
 * Gets the value of member variable addrPhoneCell
 *
 * @author Roy Terrell.
 */
  public String getAddrPhoneCell() {
    return this.addrPhoneCell;
  }
/**
 * Sets the value of member variable addrPhoneExt
 *
 * @author Roy Terrell.
 */
  public void setAddrPhoneExt(String value) {
    this.addrPhoneExt = value;
  }
/**
 * Gets the value of member variable addrPhoneExt
 *
 * @author Roy Terrell.
 */
  public String getAddrPhoneExt() {
    return this.addrPhoneExt;
  }
/**
 * Sets the value of member variable addrPhoneFax
 *
 * @author Roy Terrell.
 */
  public void setAddrPhoneFax(String value) {
    this.addrPhoneFax = value;
  }
/**
 * Gets the value of member variable addrPhoneFax
 *
 * @author Roy Terrell.
 */
  public String getAddrPhoneFax() {
    return this.addrPhoneFax;
  }
/**
 * Sets the value of member variable addrPhoneHome
 *
 * @author Roy Terrell.
 */
  public void setAddrPhoneHome(String value) {
    this.addrPhoneHome = value;
  }
/**
 * Gets the value of member variable addrPhoneHome
 *
 * @author Roy Terrell.
 */
  public String getAddrPhoneHome() {
    return this.addrPhoneHome;
  }
/**
 * Sets the value of member variable addrPhoneMain
 *
 * @author Roy Terrell.
 */
  public void setAddrPhoneMain(String value) {
    this.addrPhoneMain = value;
  }
/**
 * Gets the value of member variable addrPhoneMain
 *
 * @author Roy Terrell.
 */
  public String getAddrPhoneMain() {
    return this.addrPhoneMain;
  }
/**
 * Sets the value of member variable addrPhonePager
 *
 * @author Roy Terrell.
 */
  public void setAddrPhonePager(String value) {
    this.addrPhonePager = value;
  }
/**
 * Gets the value of member variable addrPhonePager
 *
 * @author Roy Terrell.
 */
  public String getAddrPhonePager() {
    return this.addrPhonePager;
  }
/**
 * Sets the value of member variable addrPhoneWork
 *
 * @author Roy Terrell.
 */
  public void setAddrPhoneWork(String value) {
    this.addrPhoneWork = value;
  }
/**
 * Gets the value of member variable addrPhoneWork
 *
 * @author Roy Terrell.
 */
  public String getAddrPhoneWork() {
    return this.addrPhoneWork;
  }
/**
 * Sets the value of member variable addrZip
 *
 * @author Roy Terrell.
 */
  public void setAddrZip(int value) {
    this.addrZip = value;
  }
/**
 * Gets the value of member variable addrZip
 *
 * @author Roy Terrell.
 */
  public int getAddrZip() {
    return this.addrZip;
  }
/**
 * Sets the value of member variable addrZipext
 *
 * @author Roy Terrell.
 */
  public void setAddrZipext(String value) {
    this.addrZipext = value;
  }
/**
 * Gets the value of member variable addrZipext
 *
 * @author Roy Terrell.
 */
  public String getAddrZipext() {
    return this.addrZipext;
  }
/**
 * Sets the value of member variable addr1
 *
 * @author Roy Terrell.
 */
  public void setAddr1(String value) {
    this.addr1 = value;
  }
/**
 * Gets the value of member variable addr1
 *
 * @author Roy Terrell.
 */
  public String getAddr1() {
    return this.addr1;
  }
/**
 * Sets the value of member variable addr2
 *
 * @author Roy Terrell.
 */
  public void setAddr2(String value) {
    this.addr2 = value;
  }
/**
 * Gets the value of member variable addr2
 *
 * @author Roy Terrell.
 */
  public String getAddr2() {
    return this.addr2;
  }
/**
 * Sets the value of member variable addr3
 *
 * @author Roy Terrell.
 */
  public void setAddr3(String value) {
    this.addr3 = value;
  }
/**
 * Gets the value of member variable addr3
 *
 * @author Roy Terrell.
 */
  public String getAddr3() {
    return this.addr3;
  }
/**
 * Sets the value of member variable addr4
 *
 * @author Roy Terrell.
 */
  public void setAddr4(String value) {
    this.addr4 = value;
  }
/**
 * Gets the value of member variable addr4
 *
 * @author Roy Terrell.
 */
  public String getAddr4() {
    return this.addr4;
  }
/**
 * Sets the value of member variable addrPersonId
 *
 * @author Roy Terrell.
 */
  public void setAddrPersonId(int value) {
    this.addrPersonId = value;
  }
/**
 * Gets the value of member variable addrPersonId
 *
 * @author Roy Terrell.
 */
  public int getAddrPersonId() {
    return this.addrPersonId;
  }
/**
 * Sets the value of member variable addrBusinessId
 *
 * @author Roy Terrell.
 */
  public void setAddrBusinessId(int value) {
    this.addrBusinessId = value;
  }
/**
 * Gets the value of member variable addrBusinessId
 *
 * @author Roy Terrell.
 */
  public int getAddrBusinessId() {
    return this.addrBusinessId;
  }
/**
 * Sets the value of member variable zipCity
 *
 * @author Roy Terrell.
 */
  public void setZipCity(String value) {
    this.zipCity = value;
  }
/**
 * Gets the value of member variable zipCity
 *
 * @author Roy Terrell.
 */
  public String getZipCity() {
    return this.zipCity;
  }
/**
 * Sets the value of member variable zipState
 *
 * @author Roy Terrell.
 */
  public void setZipState(String value) {
    this.zipState = value;
  }
/**
 * Gets the value of member variable zipState
 *
 * @author Roy Terrell.
 */
  public String getZipState() {
    return this.zipState;
  }
/**
 * Sets the value of member variable busContactExt
 *
 * @author Roy Terrell.
 */
  public void setBusContactExt(String value) {
    this.busContactExt = value;
  }
/**
 * Gets the value of member variable busContactExt
 *
 * @author Roy Terrell.
 */
  public String getBusContactExt() {
    return this.busContactExt;
  }
/**
 * Sets the value of member variable busContactFirstname
 *
 * @author Roy Terrell.
 */
  public void setBusContactFirstname(String value) {
    this.busContactFirstname = value;
  }
/**
 * Gets the value of member variable busContactFirstname
 *
 * @author Roy Terrell.
 */
  public String getBusContactFirstname() {
    return this.busContactFirstname;
  }
/**
 * Sets the value of member variable busContactLastname
 *
 * @author Roy Terrell.
 */
  public void setBusContactLastname(String value) {
    this.busContactLastname = value;
  }
/**
 * Gets the value of member variable busContactLastname
 *
 * @author Roy Terrell.
 */
  public String getBusContactLastname() {
    return this.busContactLastname;
  }
/**
 * Sets the value of member variable busContactPhone
 *
 * @author Roy Terrell.
 */
  public void setBusContactPhone(String value) {
    this.busContactPhone = value;
  }
/**
 * Gets the value of member variable busContactPhone
 *
 * @author Roy Terrell.
 */
  public String getBusContactPhone() {
    return this.busContactPhone;
  }
/**
 * Sets the value of member variable busLongname
 *
 * @author Roy Terrell.
 */
  public void setBusLongname(String value) {
    this.busLongname = value;
  }
/**
 * Gets the value of member variable busLongname
 *
 * @author Roy Terrell.
 */
  public String getBusLongname() {
    return this.busLongname;
  }
/**
 * Sets the value of member variable busServType
 *
 * @author Roy Terrell.
 */
  public void setBusServType(int value) {
    this.busServType = value;
  }
/**
 * Gets the value of member variable busServType
 *
 * @author Roy Terrell.
 */
  public int getBusServType() {
    return this.busServType;
  }
/**
 * Sets the value of member variable busShortname
 *
 * @author Roy Terrell.
 */
  public void setBusShortname(String value) {
    this.busShortname = value;
  }
/**
 * Gets the value of member variable busShortname
 *
 * @author Roy Terrell.
 */
  public String getBusShortname() {
    return this.busShortname;
  }
/**
 * Sets the value of member variable busTaxId
 *
 * @author Roy Terrell.
 */
  public void setBusTaxId(String value) {
    this.busTaxId = value;
  }
/**
 * Gets the value of member variable busTaxId
 *
 * @author Roy Terrell.
 */
  public String getBusTaxId() {
    return this.busTaxId;
  }
/**
 * Sets the value of member variable busWebsite
 *
 * @author Roy Terrell.
 */
  public void setBusWebsite(String value) {
    this.busWebsite = value;
  }
/**
 * Gets the value of member variable busWebsite
 *
 * @author Roy Terrell.
 */
  public String getBusWebsite() {
    return this.busWebsite;
  }
/**
 * Sets the value of member variable busType
 *
 * @author Roy Terrell.
 */
  public void setBusType(int value) {
    this.busType = value;
  }
/**
 * Gets the value of member variable busType
 *
 * @author Roy Terrell.
 */
  public int getBusType() {
    return this.busType;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
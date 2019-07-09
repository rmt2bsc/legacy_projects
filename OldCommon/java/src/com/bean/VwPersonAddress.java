package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_person_address database table/view.
 *
 * @author Roy Terrell.
 */
public class VwPersonAddress extends OrmBean {

/** The javabean property equivalent of database column vw_person_address.addr_id */
  private int addrId;
/** The javabean property equivalent of database column vw_person_address.addr_phone_cell */
  private String addrPhoneCell;
/** The javabean property equivalent of database column vw_person_address.addr_phone_ext */
  private String addrPhoneExt;
/** The javabean property equivalent of database column vw_person_address.addr_phone_fax */
  private String addrPhoneFax;
/** The javabean property equivalent of database column vw_person_address.addr_phone_home */
  private String addrPhoneHome;
/** The javabean property equivalent of database column vw_person_address.addr_phone_main */
  private String addrPhoneMain;
/** The javabean property equivalent of database column vw_person_address.addr_phone_pager */
  private String addrPhonePager;
/** The javabean property equivalent of database column vw_person_address.addr_phone_work */
  private String addrPhoneWork;
/** The javabean property equivalent of database column vw_person_address.addr_zip */
  private int addrZip;
/** The javabean property equivalent of database column vw_person_address.addr_zipext */
  private String addrZipext;
/** The javabean property equivalent of database column vw_person_address.addr1 */
  private String addr1;
/** The javabean property equivalent of database column vw_person_address.addr2 */
  private String addr2;
/** The javabean property equivalent of database column vw_person_address.addr3 */
  private String addr3;
/** The javabean property equivalent of database column vw_person_address.addr4 */
  private String addr4;
/** The javabean property equivalent of database column vw_person_address.addr_person_id */
  private int addrPersonId;
/** The javabean property equivalent of database column vw_person_address.addr_business_id */
  private int addrBusinessId;
/** The javabean property equivalent of database column vw_person_address.zip_city */
  private String zipCity;
/** The javabean property equivalent of database column vw_person_address.zip_state */
  private String zipState;
/** The javabean property equivalent of database column vw_person_address.per_birth_date */
  private java.util.Date perBirthDate;
/** The javabean property equivalent of database column vw_person_address.per_email */
  private String perEmail;
/** The javabean property equivalent of database column vw_person_address.per_firstname */
  private String perFirstname;
/** The javabean property equivalent of database column vw_person_address.per_gender_id */
  private int perGenderId;
/** The javabean property equivalent of database column vw_person_address.per_generation */
  private String perGeneration;
/** The javabean property equivalent of database column vw_person_address.per_lastname */
  private String perLastname;
/** The javabean property equivalent of database column vw_person_address.per_maidenname */
  private String perMaidenname;
/** The javabean property equivalent of database column vw_person_address.per_marital_status */
  private int perMaritalStatus;
/** The javabean property equivalent of database column vw_person_address.per_midname */
  private String perMidname;
/** The javabean property equivalent of database column vw_person_address.per_race_id */
  private int perRaceId;
/** The javabean property equivalent of database column vw_person_address.per_shortname */
  private String perShortname;
/** The javabean property equivalent of database column vw_person_address.per_ssn */
  private String perSsn;
/** The javabean property equivalent of database column vw_person_address.per_title */
  private int perTitle;
/** The javabean property equivalent of database column vw_person_address.person_id */
  private int personId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwPersonAddress() throws SystemException {
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
 * Sets the value of member variable perBirthDate
 *
 * @author Roy Terrell.
 */
  public void setPerBirthDate(java.util.Date value) {
    this.perBirthDate = value;
  }
/**
 * Gets the value of member variable perBirthDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getPerBirthDate() {
    return this.perBirthDate;
  }
/**
 * Sets the value of member variable perEmail
 *
 * @author Roy Terrell.
 */
  public void setPerEmail(String value) {
    this.perEmail = value;
  }
/**
 * Gets the value of member variable perEmail
 *
 * @author Roy Terrell.
 */
  public String getPerEmail() {
    return this.perEmail;
  }
/**
 * Sets the value of member variable perFirstname
 *
 * @author Roy Terrell.
 */
  public void setPerFirstname(String value) {
    this.perFirstname = value;
  }
/**
 * Gets the value of member variable perFirstname
 *
 * @author Roy Terrell.
 */
  public String getPerFirstname() {
    return this.perFirstname;
  }
/**
 * Sets the value of member variable perGenderId
 *
 * @author Roy Terrell.
 */
  public void setPerGenderId(int value) {
    this.perGenderId = value;
  }
/**
 * Gets the value of member variable perGenderId
 *
 * @author Roy Terrell.
 */
  public int getPerGenderId() {
    return this.perGenderId;
  }
/**
 * Sets the value of member variable perGeneration
 *
 * @author Roy Terrell.
 */
  public void setPerGeneration(String value) {
    this.perGeneration = value;
  }
/**
 * Gets the value of member variable perGeneration
 *
 * @author Roy Terrell.
 */
  public String getPerGeneration() {
    return this.perGeneration;
  }
/**
 * Sets the value of member variable perLastname
 *
 * @author Roy Terrell.
 */
  public void setPerLastname(String value) {
    this.perLastname = value;
  }
/**
 * Gets the value of member variable perLastname
 *
 * @author Roy Terrell.
 */
  public String getPerLastname() {
    return this.perLastname;
  }
/**
 * Sets the value of member variable perMaidenname
 *
 * @author Roy Terrell.
 */
  public void setPerMaidenname(String value) {
    this.perMaidenname = value;
  }
/**
 * Gets the value of member variable perMaidenname
 *
 * @author Roy Terrell.
 */
  public String getPerMaidenname() {
    return this.perMaidenname;
  }
/**
 * Sets the value of member variable perMaritalStatus
 *
 * @author Roy Terrell.
 */
  public void setPerMaritalStatus(int value) {
    this.perMaritalStatus = value;
  }
/**
 * Gets the value of member variable perMaritalStatus
 *
 * @author Roy Terrell.
 */
  public int getPerMaritalStatus() {
    return this.perMaritalStatus;
  }
/**
 * Sets the value of member variable perMidname
 *
 * @author Roy Terrell.
 */
  public void setPerMidname(String value) {
    this.perMidname = value;
  }
/**
 * Gets the value of member variable perMidname
 *
 * @author Roy Terrell.
 */
  public String getPerMidname() {
    return this.perMidname;
  }
/**
 * Sets the value of member variable perRaceId
 *
 * @author Roy Terrell.
 */
  public void setPerRaceId(int value) {
    this.perRaceId = value;
  }
/**
 * Gets the value of member variable perRaceId
 *
 * @author Roy Terrell.
 */
  public int getPerRaceId() {
    return this.perRaceId;
  }
/**
 * Sets the value of member variable perShortname
 *
 * @author Roy Terrell.
 */
  public void setPerShortname(String value) {
    this.perShortname = value;
  }
/**
 * Gets the value of member variable perShortname
 *
 * @author Roy Terrell.
 */
  public String getPerShortname() {
    return this.perShortname;
  }
/**
 * Sets the value of member variable perSsn
 *
 * @author Roy Terrell.
 */
  public void setPerSsn(String value) {
    this.perSsn = value;
  }
/**
 * Gets the value of member variable perSsn
 *
 * @author Roy Terrell.
 */
  public String getPerSsn() {
    return this.perSsn;
  }
/**
 * Sets the value of member variable perTitle
 *
 * @author Roy Terrell.
 */
  public void setPerTitle(int value) {
    this.perTitle = value;
  }
/**
 * Gets the value of member variable perTitle
 *
 * @author Roy Terrell.
 */
  public int getPerTitle() {
    return this.perTitle;
  }
/**
 * Sets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public int getPersonId() {
    return this.personId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
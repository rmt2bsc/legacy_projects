package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_person_address database table/view.
 *
 * @author auto generated.
 */
public class VwPersonAddress extends OrmBean {




	// Property name constants that belong to respective DataSource, VwPersonAddressView

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
/** The property name constant equivalent to property, PerBirthDate, of respective DataSource view. */
  public static final String PROP_PERBIRTHDATE = "PerBirthDate";
/** The property name constant equivalent to property, PerEmail, of respective DataSource view. */
  public static final String PROP_PEREMAIL = "PerEmail";
/** The property name constant equivalent to property, PerFirstname, of respective DataSource view. */
  public static final String PROP_PERFIRSTNAME = "PerFirstname";
/** The property name constant equivalent to property, PerGenderId, of respective DataSource view. */
  public static final String PROP_PERGENDERID = "PerGenderId";
/** The property name constant equivalent to property, PerGeneration, of respective DataSource view. */
  public static final String PROP_PERGENERATION = "PerGeneration";
/** The property name constant equivalent to property, PerLastname, of respective DataSource view. */
  public static final String PROP_PERLASTNAME = "PerLastname";
/** The property name constant equivalent to property, PerMaidenname, of respective DataSource view. */
  public static final String PROP_PERMAIDENNAME = "PerMaidenname";
/** The property name constant equivalent to property, PerMaritalStatus, of respective DataSource view. */
  public static final String PROP_PERMARITALSTATUS = "PerMaritalStatus";
/** The property name constant equivalent to property, PerMidname, of respective DataSource view. */
  public static final String PROP_PERMIDNAME = "PerMidname";
/** The property name constant equivalent to property, PerRaceId, of respective DataSource view. */
  public static final String PROP_PERRACEID = "PerRaceId";
/** The property name constant equivalent to property, PerShortname, of respective DataSource view. */
  public static final String PROP_PERSHORTNAME = "PerShortname";
/** The property name constant equivalent to property, PerSsn, of respective DataSource view. */
  public static final String PROP_PERSSN = "PerSsn";
/** The property name constant equivalent to property, PerTitle, of respective DataSource view. */
  public static final String PROP_PERTITLE = "PerTitle";
/** The property name constant equivalent to property, PersonId, of respective DataSource view. */
  public static final String PROP_PERSONID = "PersonId";



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
  private int addrZipext;
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
 * @author auto generated.
 */
  public VwPersonAddress() throws SystemException {
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
 * Sets the value of member variable perBirthDate
 *
 * @author auto generated.
 */
  public void setPerBirthDate(java.util.Date value) {
    this.perBirthDate = value;
  }
/**
 * Gets the value of member variable perBirthDate
 *
 * @author atuo generated.
 */
  public java.util.Date getPerBirthDate() {
    return this.perBirthDate;
  }
/**
 * Sets the value of member variable perEmail
 *
 * @author auto generated.
 */
  public void setPerEmail(String value) {
    this.perEmail = value;
  }
/**
 * Gets the value of member variable perEmail
 *
 * @author atuo generated.
 */
  public String getPerEmail() {
    return this.perEmail;
  }
/**
 * Sets the value of member variable perFirstname
 *
 * @author auto generated.
 */
  public void setPerFirstname(String value) {
    this.perFirstname = value;
  }
/**
 * Gets the value of member variable perFirstname
 *
 * @author atuo generated.
 */
  public String getPerFirstname() {
    return this.perFirstname;
  }
/**
 * Sets the value of member variable perGenderId
 *
 * @author auto generated.
 */
  public void setPerGenderId(int value) {
    this.perGenderId = value;
  }
/**
 * Gets the value of member variable perGenderId
 *
 * @author atuo generated.
 */
  public int getPerGenderId() {
    return this.perGenderId;
  }
/**
 * Sets the value of member variable perGeneration
 *
 * @author auto generated.
 */
  public void setPerGeneration(String value) {
    this.perGeneration = value;
  }
/**
 * Gets the value of member variable perGeneration
 *
 * @author atuo generated.
 */
  public String getPerGeneration() {
    return this.perGeneration;
  }
/**
 * Sets the value of member variable perLastname
 *
 * @author auto generated.
 */
  public void setPerLastname(String value) {
    this.perLastname = value;
  }
/**
 * Gets the value of member variable perLastname
 *
 * @author atuo generated.
 */
  public String getPerLastname() {
    return this.perLastname;
  }
/**
 * Sets the value of member variable perMaidenname
 *
 * @author auto generated.
 */
  public void setPerMaidenname(String value) {
    this.perMaidenname = value;
  }
/**
 * Gets the value of member variable perMaidenname
 *
 * @author atuo generated.
 */
  public String getPerMaidenname() {
    return this.perMaidenname;
  }
/**
 * Sets the value of member variable perMaritalStatus
 *
 * @author auto generated.
 */
  public void setPerMaritalStatus(int value) {
    this.perMaritalStatus = value;
  }
/**
 * Gets the value of member variable perMaritalStatus
 *
 * @author atuo generated.
 */
  public int getPerMaritalStatus() {
    return this.perMaritalStatus;
  }
/**
 * Sets the value of member variable perMidname
 *
 * @author auto generated.
 */
  public void setPerMidname(String value) {
    this.perMidname = value;
  }
/**
 * Gets the value of member variable perMidname
 *
 * @author atuo generated.
 */
  public String getPerMidname() {
    return this.perMidname;
  }
/**
 * Sets the value of member variable perRaceId
 *
 * @author auto generated.
 */
  public void setPerRaceId(int value) {
    this.perRaceId = value;
  }
/**
 * Gets the value of member variable perRaceId
 *
 * @author atuo generated.
 */
  public int getPerRaceId() {
    return this.perRaceId;
  }
/**
 * Sets the value of member variable perShortname
 *
 * @author auto generated.
 */
  public void setPerShortname(String value) {
    this.perShortname = value;
  }
/**
 * Gets the value of member variable perShortname
 *
 * @author atuo generated.
 */
  public String getPerShortname() {
    return this.perShortname;
  }
/**
 * Sets the value of member variable perSsn
 *
 * @author auto generated.
 */
  public void setPerSsn(String value) {
    this.perSsn = value;
  }
/**
 * Gets the value of member variable perSsn
 *
 * @author atuo generated.
 */
  public String getPerSsn() {
    return this.perSsn;
  }
/**
 * Sets the value of member variable perTitle
 *
 * @author auto generated.
 */
  public void setPerTitle(int value) {
    this.perTitle = value;
  }
/**
 * Gets the value of member variable perTitle
 *
 * @author atuo generated.
 */
  public int getPerTitle() {
    return this.perTitle;
  }
/**
 * Sets the value of member variable personId
 *
 * @author auto generated.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author atuo generated.
 */
  public int getPersonId() {
    return this.personId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
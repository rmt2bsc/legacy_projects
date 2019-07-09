package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_project_employee_ext database table/view.
 *
 * @author Roy Terrell.
 */
public class VwProjectEmployeeExt extends OrmBean {




	// Property name constants that belong to respective DataSource, VwProjectEmployeeExtView

/** The property name constant equivalent to property, EmployeeId, of respective DataSource view. */
  public static final String PROP_EMPLOYEEID = "EmployeeId";
/** The property name constant equivalent to property, LoginId, of respective DataSource view. */
  public static final String PROP_LOGINID = "LoginId";
/** The property name constant equivalent to property, PersonId, of respective DataSource view. */
  public static final String PROP_PERSONID = "PersonId";
/** The property name constant equivalent to property, TitleId, of respective DataSource view. */
  public static final String PROP_TITLEID = "TitleId";
/** The property name constant equivalent to property, TypeId, of respective DataSource view. */
  public static final String PROP_TYPEID = "TypeId";
/** The property name constant equivalent to property, BillRate, of respective DataSource view. */
  public static final String PROP_BILLRATE = "BillRate";
/** The property name constant equivalent to property, OtBillRate, of respective DataSource view. */
  public static final String PROP_OTBILLRATE = "OtBillRate";
/** The property name constant equivalent to property, StartDate, of respective DataSource view. */
  public static final String PROP_STARTDATE = "StartDate";
/** The property name constant equivalent to property, TerminationDate, of respective DataSource view. */
  public static final String PROP_TERMINATIONDATE = "TerminationDate";
/** The property name constant equivalent to property, EmployeeType, of respective DataSource view. */
  public static final String PROP_EMPLOYEETYPE = "EmployeeType";
/** The property name constant equivalent to property, EmployeeTitle, of respective DataSource view. */
  public static final String PROP_EMPLOYEETITLE = "EmployeeTitle";
/** The property name constant equivalent to property, PerTitleId, of respective DataSource view. */
  public static final String PROP_PERTITLEID = "PerTitleId";
/** The property name constant equivalent to property, PerTitle, of respective DataSource view. */
  public static final String PROP_PERTITLE = "PerTitle";
/** The property name constant equivalent to property, PerSsn, of respective DataSource view. */
  public static final String PROP_PERSSN = "PerSsn";
/** The property name constant equivalent to property, PerShortname, of respective DataSource view. */
  public static final String PROP_PERSHORTNAME = "PerShortname";
/** The property name constant equivalent to property, PerFirstname, of respective DataSource view. */
  public static final String PROP_PERFIRSTNAME = "PerFirstname";
/** The property name constant equivalent to property, PerLastname, of respective DataSource view. */
  public static final String PROP_PERLASTNAME = "PerLastname";
/** The property name constant equivalent to property, PerMidname, of respective DataSource view. */
  public static final String PROP_PERMIDNAME = "PerMidname";
/** The property name constant equivalent to property, PerGeneration, of respective DataSource view. */
  public static final String PROP_PERGENERATION = "PerGeneration";
/** The property name constant equivalent to property, PerBirthDate, of respective DataSource view. */
  public static final String PROP_PERBIRTHDATE = "PerBirthDate";
/** The property name constant equivalent to property, PerGenderId, of respective DataSource view. */
  public static final String PROP_PERGENDERID = "PerGenderId";
/** The property name constant equivalent to property, PerGender, of respective DataSource view. */
  public static final String PROP_PERGENDER = "PerGender";
/** The property name constant equivalent to property, PerMaidenname, of respective DataSource view. */
  public static final String PROP_PERMAIDENNAME = "PerMaidenname";
/** The property name constant equivalent to property, PerMaritalStatusId, of respective DataSource view. */
  public static final String PROP_PERMARITALSTATUSID = "PerMaritalStatusId";
/** The property name constant equivalent to property, PerMaritalStatus, of respective DataSource view. */
  public static final String PROP_PERMARITALSTATUS = "PerMaritalStatus";
/** The property name constant equivalent to property, PerRaceId, of respective DataSource view. */
  public static final String PROP_PERRACEID = "PerRaceId";
/** The property name constant equivalent to property, PerRace, of respective DataSource view. */
  public static final String PROP_PERRACE = "PerRace";
/** The property name constant equivalent to property, AddrId, of respective DataSource view. */
  public static final String PROP_ADDRID = "AddrId";
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
/** The property name constant equivalent to property, AddrZip, of respective DataSource view. */
  public static final String PROP_ADDRZIP = "AddrZip";
/** The property name constant equivalent to property, AddrZipext, of respective DataSource view. */
  public static final String PROP_ADDRZIPEXT = "AddrZipext";
/** The property name constant equivalent to property, AddrPhoneWork, of respective DataSource view. */
  public static final String PROP_ADDRPHONEWORK = "AddrPhoneWork";
/** The property name constant equivalent to property, AddrPhoneExt, of respective DataSource view. */
  public static final String PROP_ADDRPHONEEXT = "AddrPhoneExt";
/** The property name constant equivalent to property, AddrPhoneCell, of respective DataSource view. */
  public static final String PROP_ADDRPHONECELL = "AddrPhoneCell";
/** The property name constant equivalent to property, AddrPhoneFax, of respective DataSource view. */
  public static final String PROP_ADDRPHONEFAX = "AddrPhoneFax";
/** The property name constant equivalent to property, AddrPhoneHome, of respective DataSource view. */
  public static final String PROP_ADDRPHONEHOME = "AddrPhoneHome";
/** The property name constant equivalent to property, AddrPhoneMain, of respective DataSource view. */
  public static final String PROP_ADDRPHONEMAIN = "AddrPhoneMain";
/** The property name constant equivalent to property, AddrPhonePager, of respective DataSource view. */
  public static final String PROP_ADDRPHONEPAGER = "AddrPhonePager";
/** The property name constant equivalent to property, PerEmail, of respective DataSource view. */
  public static final String PROP_PEREMAIL = "PerEmail";
/** The property name constant equivalent to property, ProjectId, of respective DataSource view. */
  public static final String PROP_PROJECTID = "ProjectId";
/** The property name constant equivalent to property, ProjClientId, of respective DataSource view. */
  public static final String PROP_PROJCLIENTID = "ProjClientId";
/** The property name constant equivalent to property, ProjectName, of respective DataSource view. */
  public static final String PROP_PROJECTNAME = "ProjectName";
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";



	/** The javabean property equivalent of database column vw_project_employee_ext.employee_id */
  private int employeeId;
/** The javabean property equivalent of database column vw_project_employee_ext.login_id */
  private String loginId;
/** The javabean property equivalent of database column vw_project_employee_ext.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_project_employee_ext.title_id */
  private int titleId;
/** The javabean property equivalent of database column vw_project_employee_ext.TYPE_ID */
  private int typeId;
/** The javabean property equivalent of database column vw_project_employee_ext.BILL_RATE */
  private double billRate;
/** The javabean property equivalent of database column vw_project_employee_ext.OT_BILL_RATE */
  private double otBillRate;
/** The javabean property equivalent of database column vw_project_employee_ext.START_DATE */
  private java.util.Date startDate;
/** The javabean property equivalent of database column vw_project_employee_ext.TERMINATION_DATE */
  private java.util.Date terminationDate;
/** The javabean property equivalent of database column vw_project_employee_ext.employee_type */
  private String employeeType;
/** The javabean property equivalent of database column vw_project_employee_ext.employee_title */
  private String employeeTitle;
/** The javabean property equivalent of database column vw_project_employee_ext.per_title_id */
  private int perTitleId;
/** The javabean property equivalent of database column vw_project_employee_ext.per_title */
  private String perTitle;
/** The javabean property equivalent of database column vw_project_employee_ext.per_ssn */
  private String perSsn;
/** The javabean property equivalent of database column vw_project_employee_ext.per_shortname */
  private String perShortname;
/** The javabean property equivalent of database column vw_project_employee_ext.per_firstname */
  private String perFirstname;
/** The javabean property equivalent of database column vw_project_employee_ext.per_lastname */
  private String perLastname;
/** The javabean property equivalent of database column vw_project_employee_ext.per_midname */
  private String perMidname;
/** The javabean property equivalent of database column vw_project_employee_ext.per_generation */
  private String perGeneration;
/** The javabean property equivalent of database column vw_project_employee_ext.per_birth_date */
  private java.util.Date perBirthDate;
/** The javabean property equivalent of database column vw_project_employee_ext.per_gender_id */
  private int perGenderId;
/** The javabean property equivalent of database column vw_project_employee_ext.per_gender */
  private String perGender;
/** The javabean property equivalent of database column vw_project_employee_ext.per_maidenname */
  private String perMaidenname;
/** The javabean property equivalent of database column vw_project_employee_ext.per_marital_status_id */
  private int perMaritalStatusId;
/** The javabean property equivalent of database column vw_project_employee_ext.per_marital_status */
  private String perMaritalStatus;
/** The javabean property equivalent of database column vw_project_employee_ext.per_race_id */
  private int perRaceId;
/** The javabean property equivalent of database column vw_project_employee_ext.per_race */
  private String perRace;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_id */
  private int addrId;
/** The javabean property equivalent of database column vw_project_employee_ext.addr1 */
  private String addr1;
/** The javabean property equivalent of database column vw_project_employee_ext.addr2 */
  private String addr2;
/** The javabean property equivalent of database column vw_project_employee_ext.addr3 */
  private String addr3;
/** The javabean property equivalent of database column vw_project_employee_ext.addr4 */
  private String addr4;
/** The javabean property equivalent of database column vw_project_employee_ext.zip_city */
  private String zipCity;
/** The javabean property equivalent of database column vw_project_employee_ext.zip_state */
  private String zipState;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_zip */
  private int addrZip;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_zipext */
  private String addrZipext;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_phone_work */
  private String addrPhoneWork;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_phone_ext */
  private String addrPhoneExt;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_phone_cell */
  private String addrPhoneCell;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_phone_fax */
  private String addrPhoneFax;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_phone_home */
  private String addrPhoneHome;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_phone_main */
  private String addrPhoneMain;
/** The javabean property equivalent of database column vw_project_employee_ext.addr_phone_pager */
  private String addrPhonePager;
/** The javabean property equivalent of database column vw_project_employee_ext.per_email */
  private String perEmail;
/** The javabean property equivalent of database column vw_project_employee_ext.project_id */
  private int projectId;
/** The javabean property equivalent of database column vw_project_employee_ext.proj_client_id */
  private int projClientId;
/** The javabean property equivalent of database column vw_project_employee_ext.project_name */
  private String projectName;
/** The javabean property equivalent of database column vw_project_employee_ext.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column vw_project_employee_ext.end_date */
  private java.util.Date endDate;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwProjectEmployeeExt() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable employeeId
 *
 * @author Roy Terrell.
 */
  public void setEmployeeId(int value) {
    this.employeeId = value;
  }
/**
 * Gets the value of member variable employeeId
 *
 * @author Roy Terrell.
 */
  public int getEmployeeId() {
    return this.employeeId;
  }
/**
 * Sets the value of member variable loginId
 *
 * @author Roy Terrell.
 */
  public void setLoginId(String value) {
    this.loginId = value;
  }
/**
 * Gets the value of member variable loginId
 *
 * @author Roy Terrell.
 */
  public String getLoginId() {
    return this.loginId;
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
 * Sets the value of member variable titleId
 *
 * @author Roy Terrell.
 */
  public void setTitleId(int value) {
    this.titleId = value;
  }
/**
 * Gets the value of member variable titleId
 *
 * @author Roy Terrell.
 */
  public int getTitleId() {
    return this.titleId;
  }
/**
 * Sets the value of member variable typeId
 *
 * @author Roy Terrell.
 */
  public void setTypeId(int value) {
    this.typeId = value;
  }
/**
 * Gets the value of member variable typeId
 *
 * @author Roy Terrell.
 */
  public int getTypeId() {
    return this.typeId;
  }
/**
 * Sets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public void setBillRate(double value) {
    this.billRate = value;
  }
/**
 * Gets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public double getBillRate() {
    return this.billRate;
  }
/**
 * Sets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public void setOtBillRate(double value) {
    this.otBillRate = value;
  }
/**
 * Gets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public double getOtBillRate() {
    return this.otBillRate;
  }
/**
 * Sets the value of member variable startDate
 *
 * @author Roy Terrell.
 */
  public void setStartDate(java.util.Date value) {
    this.startDate = value;
  }
/**
 * Gets the value of member variable startDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getStartDate() {
    return this.startDate;
  }
/**
 * Sets the value of member variable terminationDate
 *
 * @author Roy Terrell.
 */
  public void setTerminationDate(java.util.Date value) {
    this.terminationDate = value;
  }
/**
 * Gets the value of member variable terminationDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getTerminationDate() {
    return this.terminationDate;
  }
/**
 * Sets the value of member variable employeeType
 *
 * @author Roy Terrell.
 */
  public void setEmployeeType(String value) {
    this.employeeType = value;
  }
/**
 * Gets the value of member variable employeeType
 *
 * @author Roy Terrell.
 */
  public String getEmployeeType() {
    return this.employeeType;
  }
/**
 * Sets the value of member variable employeeTitle
 *
 * @author Roy Terrell.
 */
  public void setEmployeeTitle(String value) {
    this.employeeTitle = value;
  }
/**
 * Gets the value of member variable employeeTitle
 *
 * @author Roy Terrell.
 */
  public String getEmployeeTitle() {
    return this.employeeTitle;
  }
/**
 * Sets the value of member variable perTitleId
 *
 * @author Roy Terrell.
 */
  public void setPerTitleId(int value) {
    this.perTitleId = value;
  }
/**
 * Gets the value of member variable perTitleId
 *
 * @author Roy Terrell.
 */
  public int getPerTitleId() {
    return this.perTitleId;
  }
/**
 * Sets the value of member variable perTitle
 *
 * @author Roy Terrell.
 */
  public void setPerTitle(String value) {
    this.perTitle = value;
  }
/**
 * Gets the value of member variable perTitle
 *
 * @author Roy Terrell.
 */
  public String getPerTitle() {
    return this.perTitle;
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
 * Sets the value of member variable perGender
 *
 * @author Roy Terrell.
 */
  public void setPerGender(String value) {
    this.perGender = value;
  }
/**
 * Gets the value of member variable perGender
 *
 * @author Roy Terrell.
 */
  public String getPerGender() {
    return this.perGender;
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
 * Sets the value of member variable perMaritalStatusId
 *
 * @author Roy Terrell.
 */
  public void setPerMaritalStatusId(int value) {
    this.perMaritalStatusId = value;
  }
/**
 * Gets the value of member variable perMaritalStatusId
 *
 * @author Roy Terrell.
 */
  public int getPerMaritalStatusId() {
    return this.perMaritalStatusId;
  }
/**
 * Sets the value of member variable perMaritalStatus
 *
 * @author Roy Terrell.
 */
  public void setPerMaritalStatus(String value) {
    this.perMaritalStatus = value;
  }
/**
 * Gets the value of member variable perMaritalStatus
 *
 * @author Roy Terrell.
 */
  public String getPerMaritalStatus() {
    return this.perMaritalStatus;
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
 * Sets the value of member variable perRace
 *
 * @author Roy Terrell.
 */
  public void setPerRace(String value) {
    this.perRace = value;
  }
/**
 * Gets the value of member variable perRace
 *
 * @author Roy Terrell.
 */
  public String getPerRace() {
    return this.perRace;
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
 * Sets the value of member variable projectId
 *
 * @author Roy Terrell.
 */
  public void setProjectId(int value) {
    this.projectId = value;
  }
/**
 * Gets the value of member variable projectId
 *
 * @author Roy Terrell.
 */
  public int getProjectId() {
    return this.projectId;
  }
/**
 * Sets the value of member variable projClientId
 *
 * @author Roy Terrell.
 */
  public void setProjClientId(int value) {
    this.projClientId = value;
  }
/**
 * Gets the value of member variable projClientId
 *
 * @author Roy Terrell.
 */
  public int getProjClientId() {
    return this.projClientId;
  }
/**
 * Sets the value of member variable projectName
 *
 * @author Roy Terrell.
 */
  public void setProjectName(String value) {
    this.projectName = value;
  }
/**
 * Gets the value of member variable projectName
 *
 * @author Roy Terrell.
 */
  public String getProjectName() {
    return this.projectName;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
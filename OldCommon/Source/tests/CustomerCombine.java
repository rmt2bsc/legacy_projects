package com.bean;


import java.util.Date;
import com.bean.RMT2BaseBean;
import com.util.SystemException;


/**
 * Peer object that maps to the customercombine database table/view.
 *
 * @author Roy Terrell.
 */
public class CustomerCombine extends RMT2BaseBean {

/** The javabean property equivalent of database column customercombine.id */
  private int id;
/** The javabean property equivalent of database column customercombine.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column customercombine.account_no */
  private String accountNo;
/** The javabean property equivalent of database column customercombine.person_id */
  private int personId;
/** The javabean property equivalent of database column customercombine.business_id */
  private int businessId;
/** The javabean property equivalent of database column customercombine.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column customercombine.customer_type */
  private String customerType;
/** The javabean property equivalent of database column customercombine.person_firstname */
  private String personFirstname;
/** The javabean property equivalent of database column customercombine.person_midname */
  private String personMidname;
/** The javabean property equivalent of database column customercombine.person_lastname */
  private String personLastname;
/** The javabean property equivalent of database column customercombine.person_maidenname */
  private String personMaidenname;
/** The javabean property equivalent of database column customercombine.person_generation */
  private String personGeneration;
/** The javabean property equivalent of database column customercombine.person_shortname */
  private String personShortname;
/** The javabean property equivalent of database column customercombine.person_title */
  private int personTitle;
/** The javabean property equivalent of database column customercombine.person_gender_id */
  private int personGenderId;
/** The javabean property equivalent of database column customercombine.person_marital_status */
  private int personMaritalStatus;
/** The javabean property equivalent of database column customercombine.person_birth_date */
  private java.util.Date personBirthDate;
/** The javabean property equivalent of database column customercombine.person_race_id */
  private int personRaceId;
/** The javabean property equivalent of database column customercombine.person_ssn */
  private String personSsn;
/** The javabean property equivalent of database column customercombine.person_email */
  private String personEmail;
/** The javabean property equivalent of database column customercombine.business_bus_type */
  private int businessBusType;
/** The javabean property equivalent of database column customercombine.business_serv_type */
  private int businessServType;
/** The javabean property equivalent of database column customercombine.business_longname */
  private String businessLongname;
/** The javabean property equivalent of database column customercombine.business_shortname */
  private String businessShortname;
/** The javabean property equivalent of database column customercombine.business_contact_firstname */
  private String businessContactFirstname;
/** The javabean property equivalent of database column customercombine.business_contact_lastname */
  private String businessContactLastname;
/** The javabean property equivalent of database column customercombine.business_contact_phone */
  private String businessContactPhone;
/** The javabean property equivalent of database column customercombine.business_contact_ext */
  private String businessContactExt;
/** The javabean property equivalent of database column customercombine.business_tax_id */
  private String businessTaxId;
/** The javabean property equivalent of database column customercombine.business_website */
  private String businessWebsite;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public CustomerCombine() throws SystemException {
  }
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
/**
 * Gets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public int getGlAccountId() {
    return this.glAccountId;
  }
/**
 * Sets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public void setAccountNo(String value) {
    this.accountNo = value;
  }
/**
 * Gets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public String getAccountNo() {
    return this.accountNo;
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
 * Sets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public void setCreditLimit(double value) {
    this.creditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public double getCreditLimit() {
    return this.creditLimit;
  }
/**
 * Sets the value of member variable customerType
 *
 * @author Roy Terrell.
 */
  public void setCustomerType(String value) {
    this.customerType = value;
  }
/**
 * Gets the value of member variable customerType
 *
 * @author Roy Terrell.
 */
  public String getCustomerType() {
    return this.customerType;
  }
/**
 * Sets the value of member variable personFirstname
 *
 * @author Roy Terrell.
 */
  public void setPersonFirstname(String value) {
    this.personFirstname = value;
  }
/**
 * Gets the value of member variable personFirstname
 *
 * @author Roy Terrell.
 */
  public String getPersonFirstname() {
    return this.personFirstname;
  }
/**
 * Sets the value of member variable personMidname
 *
 * @author Roy Terrell.
 */
  public void setPersonMidname(String value) {
    this.personMidname = value;
  }
/**
 * Gets the value of member variable personMidname
 *
 * @author Roy Terrell.
 */
  public String getPersonMidname() {
    return this.personMidname;
  }
/**
 * Sets the value of member variable personLastname
 *
 * @author Roy Terrell.
 */
  public void setPersonLastname(String value) {
    this.personLastname = value;
  }
/**
 * Gets the value of member variable personLastname
 *
 * @author Roy Terrell.
 */
  public String getPersonLastname() {
    return this.personLastname;
  }
/**
 * Sets the value of member variable personMaidenname
 *
 * @author Roy Terrell.
 */
  public void setPersonMaidenname(String value) {
    this.personMaidenname = value;
  }
/**
 * Gets the value of member variable personMaidenname
 *
 * @author Roy Terrell.
 */
  public String getPersonMaidenname() {
    return this.personMaidenname;
  }
/**
 * Sets the value of member variable personGeneration
 *
 * @author Roy Terrell.
 */
  public void setPersonGeneration(String value) {
    this.personGeneration = value;
  }
/**
 * Gets the value of member variable personGeneration
 *
 * @author Roy Terrell.
 */
  public String getPersonGeneration() {
    return this.personGeneration;
  }
/**
 * Sets the value of member variable personShortname
 *
 * @author Roy Terrell.
 */
  public void setPersonShortname(String value) {
    this.personShortname = value;
  }
/**
 * Gets the value of member variable personShortname
 *
 * @author Roy Terrell.
 */
  public String getPersonShortname() {
    return this.personShortname;
  }
/**
 * Sets the value of member variable personTitle
 *
 * @author Roy Terrell.
 */
  public void setPersonTitle(int value) {
    this.personTitle = value;
  }
/**
 * Gets the value of member variable personTitle
 *
 * @author Roy Terrell.
 */
  public int getPersonTitle() {
    return this.personTitle;
  }
/**
 * Sets the value of member variable personGenderId
 *
 * @author Roy Terrell.
 */
  public void setPersonGenderId(int value) {
    this.personGenderId = value;
  }
/**
 * Gets the value of member variable personGenderId
 *
 * @author Roy Terrell.
 */
  public int getPersonGenderId() {
    return this.personGenderId;
  }
/**
 * Sets the value of member variable personMaritalStatus
 *
 * @author Roy Terrell.
 */
  public void setPersonMaritalStatus(int value) {
    this.personMaritalStatus = value;
  }
/**
 * Gets the value of member variable personMaritalStatus
 *
 * @author Roy Terrell.
 */
  public int getPersonMaritalStatus() {
    return this.personMaritalStatus;
  }
/**
 * Sets the value of member variable personBirthDate
 *
 * @author Roy Terrell.
 */
  public void setPersonBirthDate(java.util.Date value) {
    this.personBirthDate = value;
  }
/**
 * Gets the value of member variable personBirthDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getPersonBirthDate() {
    return this.personBirthDate;
  }
/**
 * Sets the value of member variable personRaceId
 *
 * @author Roy Terrell.
 */
  public void setPersonRaceId(int value) {
    this.personRaceId = value;
  }
/**
 * Gets the value of member variable personRaceId
 *
 * @author Roy Terrell.
 */
  public int getPersonRaceId() {
    return this.personRaceId;
  }
/**
 * Sets the value of member variable personSsn
 *
 * @author Roy Terrell.
 */
  public void setPersonSsn(String value) {
    this.personSsn = value;
  }
/**
 * Gets the value of member variable personSsn
 *
 * @author Roy Terrell.
 */
  public String getPersonSsn() {
    return this.personSsn;
  }
/**
 * Sets the value of member variable personEmail
 *
 * @author Roy Terrell.
 */
  public void setPersonEmail(String value) {
    this.personEmail = value;
  }
/**
 * Gets the value of member variable personEmail
 *
 * @author Roy Terrell.
 */
  public String getPersonEmail() {
    return this.personEmail;
  }
/**
 * Sets the value of member variable businessBusType
 *
 * @author Roy Terrell.
 */
  public void setBusinessBusType(int value) {
    this.businessBusType = value;
  }
/**
 * Gets the value of member variable businessBusType
 *
 * @author Roy Terrell.
 */
  public int getBusinessBusType() {
    return this.businessBusType;
  }
/**
 * Sets the value of member variable businessServType
 *
 * @author Roy Terrell.
 */
  public void setBusinessServType(int value) {
    this.businessServType = value;
  }
/**
 * Gets the value of member variable businessServType
 *
 * @author Roy Terrell.
 */
  public int getBusinessServType() {
    return this.businessServType;
  }
/**
 * Sets the value of member variable businessLongname
 *
 * @author Roy Terrell.
 */
  public void setBusinessLongname(String value) {
    this.businessLongname = value;
  }
/**
 * Gets the value of member variable businessLongname
 *
 * @author Roy Terrell.
 */
  public String getBusinessLongname() {
    return this.businessLongname;
  }
/**
 * Sets the value of member variable businessShortname
 *
 * @author Roy Terrell.
 */
  public void setBusinessShortname(String value) {
    this.businessShortname = value;
  }
/**
 * Gets the value of member variable businessShortname
 *
 * @author Roy Terrell.
 */
  public String getBusinessShortname() {
    return this.businessShortname;
  }
/**
 * Sets the value of member variable businessContactFirstname
 *
 * @author Roy Terrell.
 */
  public void setBusinessContactFirstname(String value) {
    this.businessContactFirstname = value;
  }
/**
 * Gets the value of member variable businessContactFirstname
 *
 * @author Roy Terrell.
 */
  public String getBusinessContactFirstname() {
    return this.businessContactFirstname;
  }
/**
 * Sets the value of member variable businessContactLastname
 *
 * @author Roy Terrell.
 */
  public void setBusinessContactLastname(String value) {
    this.businessContactLastname = value;
  }
/**
 * Gets the value of member variable businessContactLastname
 *
 * @author Roy Terrell.
 */
  public String getBusinessContactLastname() {
    return this.businessContactLastname;
  }
/**
 * Sets the value of member variable businessContactPhone
 *
 * @author Roy Terrell.
 */
  public void setBusinessContactPhone(String value) {
    this.businessContactPhone = value;
  }
/**
 * Gets the value of member variable businessContactPhone
 *
 * @author Roy Terrell.
 */
  public String getBusinessContactPhone() {
    return this.businessContactPhone;
  }
/**
 * Sets the value of member variable businessContactExt
 *
 * @author Roy Terrell.
 */
  public void setBusinessContactExt(String value) {
    this.businessContactExt = value;
  }
/**
 * Gets the value of member variable businessContactExt
 *
 * @author Roy Terrell.
 */
  public String getBusinessContactExt() {
    return this.businessContactExt;
  }
/**
 * Sets the value of member variable businessTaxId
 *
 * @author Roy Terrell.
 */
  public void setBusinessTaxId(String value) {
    this.businessTaxId = value;
  }
/**
 * Gets the value of member variable businessTaxId
 *
 * @author Roy Terrell.
 */
  public String getBusinessTaxId() {
    return this.businessTaxId;
  }
/**
 * Sets the value of member variable businessWebsite
 *
 * @author Roy Terrell.
 */
  public void setBusinessWebsite(String value) {
    this.businessWebsite = value;
  }
/**
 * Gets the value of member variable businessWebsite
 *
 * @author Roy Terrell.
 */
  public String getBusinessWebsite() {
    return this.businessWebsite;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
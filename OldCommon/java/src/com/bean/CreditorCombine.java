package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the creditorcombine database table/view.
 *
 * @author Roy Terrell.
 */
public class CreditorCombine extends OrmBean {

/** The javabean property equivalent of database column creditorcombine.id */
  private int id;
/** The javabean property equivalent of database column creditorcombine.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column creditorcombine.account_no */
  private String accountNo;
/** The javabean property equivalent of database column creditorcombine.person_id */
  private int personId;
/** The javabean property equivalent of database column creditorcombine.business_id */
  private int businessId;
/** The javabean property equivalent of database column creditorcombine.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column creditorcombine.CREDITOR_TYPE_ID */
  private int creditorTypeId;
/** The javabean property equivalent of database column creditorcombine.business_bus_type */
  private int businessBusType;
/** The javabean property equivalent of database column creditorcombine.business_serv_type */
  private int businessServType;
/** The javabean property equivalent of database column creditorcombine.business_longname */
  private String businessLongname;
/** The javabean property equivalent of database column creditorcombine.business_shortname */
  private String businessShortname;
/** The javabean property equivalent of database column creditorcombine.business_contact_firstname */
  private String businessContactFirstname;
/** The javabean property equivalent of database column creditorcombine.business_contact_lastname */
  private String businessContactLastname;
/** The javabean property equivalent of database column creditorcombine.business_contact_phone */
  private String businessContactPhone;
/** The javabean property equivalent of database column creditorcombine.business_contact_ext */
  private String businessContactExt;
/** The javabean property equivalent of database column creditorcombine.business_tax_id */
  private String businessTaxId;
/** The javabean property equivalent of database column creditorcombine.business_website */
  private String businessWebsite;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public CreditorCombine() throws SystemException {
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
 * Sets the value of member variable creditorTypeId
 *
 * @author Roy Terrell.
 */
  public void setCreditorTypeId(int value) {
    this.creditorTypeId = value;
  }
/**
 * Gets the value of member variable creditorTypeId
 *
 * @author Roy Terrell.
 */
  public int getCreditorTypeId() {
    return this.creditorTypeId;
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
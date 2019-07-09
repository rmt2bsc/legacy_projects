package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_credit_charge_list database table/view.
 *
 * @author Roy Terrell.
 */
public class VwXactCreditChargeList extends OrmBean {

/** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.posted_date */
  private java.util.Date postedDate;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.confirm_no */
  private String confirmNo;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.neg_instr_no */
  private String negInstrNo;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.tender_id */
  private int tenderId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.tender_description */
  private String tenderDescription;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_type_name */
  private String xactTypeName;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.reason */
  private String reason;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_entry_date */
  private java.util.Date xactEntryDate;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.xact_category_id */
  private int xactCategoryId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.to_multiplier */
  private int toMultiplier;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.from_multiplier */
  private int fromMultiplier;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.to_acct_type_id */
  private int toAcctTypeId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.to_acct_catg_id */
  private int toAcctCatgId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.from_acct_type_id */
  private int fromAcctTypeId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.from_acct_catg_id */
  private int fromAcctCatgId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.has_subsidiary */
  private int hasSubsidiary;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_id */
  private int creditorId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_type_id */
  private int creditorTypeId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.active */
  private int active;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.apr */
  private double apr;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_date_created */
  private java.util.Date creditorDateCreated;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.creditor_type_description */
  private String creditorTypeDescription;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.longname */
  private String longname;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.serv_type */
  private int servType;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.bus_type */
  private int busType;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_firstname */
  private String contactFirstname;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_lastname */
  private String contactLastname;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_phone */
  private String contactPhone;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.contact_ext */
  private String contactExt;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.tax_id */
  private String taxId;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.website */
  private String website;
/** The javabean property equivalent of database column vw_xact_credit_charge_list.balance */
  private double balance;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwXactCreditChargeList() throws SystemException {
  }
/**
 * Sets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable xactAmount
 *
 * @author Roy Terrell.
 */
  public void setXactAmount(double value) {
    this.xactAmount = value;
  }
/**
 * Gets the value of member variable xactAmount
 *
 * @author Roy Terrell.
 */
  public double getXactAmount() {
    return this.xactAmount;
  }
/**
 * Sets the value of member variable xactDate
 *
 * @author Roy Terrell.
 */
  public void setXactDate(java.util.Date value) {
    this.xactDate = value;
  }
/**
 * Gets the value of member variable xactDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getXactDate() {
    return this.xactDate;
  }
/**
 * Sets the value of member variable postedDate
 *
 * @author Roy Terrell.
 */
  public void setPostedDate(java.util.Date value) {
    this.postedDate = value;
  }
/**
 * Gets the value of member variable postedDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getPostedDate() {
    return this.postedDate;
  }
/**
 * Sets the value of member variable confirmNo
 *
 * @author Roy Terrell.
 */
  public void setConfirmNo(String value) {
    this.confirmNo = value;
  }
/**
 * Gets the value of member variable confirmNo
 *
 * @author Roy Terrell.
 */
  public String getConfirmNo() {
    return this.confirmNo;
  }
/**
 * Sets the value of member variable negInstrNo
 *
 * @author Roy Terrell.
 */
  public void setNegInstrNo(String value) {
    this.negInstrNo = value;
  }
/**
 * Gets the value of member variable negInstrNo
 *
 * @author Roy Terrell.
 */
  public String getNegInstrNo() {
    return this.negInstrNo;
  }
/**
 * Sets the value of member variable tenderId
 *
 * @author Roy Terrell.
 */
  public void setTenderId(int value) {
    this.tenderId = value;
  }
/**
 * Gets the value of member variable tenderId
 *
 * @author Roy Terrell.
 */
  public int getTenderId() {
    return this.tenderId;
  }
/**
 * Sets the value of member variable tenderDescription
 *
 * @author Roy Terrell.
 */
  public void setTenderDescription(String value) {
    this.tenderDescription = value;
  }
/**
 * Gets the value of member variable tenderDescription
 *
 * @author Roy Terrell.
 */
  public String getTenderDescription() {
    return this.tenderDescription;
  }
/**
 * Sets the value of member variable xactTypeId
 *
 * @author Roy Terrell.
 */
  public void setXactTypeId(int value) {
    this.xactTypeId = value;
  }
/**
 * Gets the value of member variable xactTypeId
 *
 * @author Roy Terrell.
 */
  public int getXactTypeId() {
    return this.xactTypeId;
  }
/**
 * Sets the value of member variable xactTypeName
 *
 * @author Roy Terrell.
 */
  public void setXactTypeName(String value) {
    this.xactTypeName = value;
  }
/**
 * Gets the value of member variable xactTypeName
 *
 * @author Roy Terrell.
 */
  public String getXactTypeName() {
    return this.xactTypeName;
  }
/**
 * Sets the value of member variable reason
 *
 * @author Roy Terrell.
 */
  public void setReason(String value) {
    this.reason = value;
  }
/**
 * Gets the value of member variable reason
 *
 * @author Roy Terrell.
 */
  public String getReason() {
    return this.reason;
  }
/**
 * Sets the value of member variable xactEntryDate
 *
 * @author Roy Terrell.
 */
  public void setXactEntryDate(java.util.Date value) {
    this.xactEntryDate = value;
  }
/**
 * Gets the value of member variable xactEntryDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getXactEntryDate() {
    return this.xactEntryDate;
  }
/**
 * Sets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Sets the value of member variable xactCategoryId
 *
 * @author Roy Terrell.
 */
  public void setXactCategoryId(int value) {
    this.xactCategoryId = value;
  }
/**
 * Gets the value of member variable xactCategoryId
 *
 * @author Roy Terrell.
 */
  public int getXactCategoryId() {
    return this.xactCategoryId;
  }
/**
 * Sets the value of member variable toMultiplier
 *
 * @author Roy Terrell.
 */
  public void setToMultiplier(int value) {
    this.toMultiplier = value;
  }
/**
 * Gets the value of member variable toMultiplier
 *
 * @author Roy Terrell.
 */
  public int getToMultiplier() {
    return this.toMultiplier;
  }
/**
 * Sets the value of member variable fromMultiplier
 *
 * @author Roy Terrell.
 */
  public void setFromMultiplier(int value) {
    this.fromMultiplier = value;
  }
/**
 * Gets the value of member variable fromMultiplier
 *
 * @author Roy Terrell.
 */
  public int getFromMultiplier() {
    return this.fromMultiplier;
  }
/**
 * Sets the value of member variable toAcctTypeId
 *
 * @author Roy Terrell.
 */
  public void setToAcctTypeId(int value) {
    this.toAcctTypeId = value;
  }
/**
 * Gets the value of member variable toAcctTypeId
 *
 * @author Roy Terrell.
 */
  public int getToAcctTypeId() {
    return this.toAcctTypeId;
  }
/**
 * Sets the value of member variable toAcctCatgId
 *
 * @author Roy Terrell.
 */
  public void setToAcctCatgId(int value) {
    this.toAcctCatgId = value;
  }
/**
 * Gets the value of member variable toAcctCatgId
 *
 * @author Roy Terrell.
 */
  public int getToAcctCatgId() {
    return this.toAcctCatgId;
  }
/**
 * Sets the value of member variable fromAcctTypeId
 *
 * @author Roy Terrell.
 */
  public void setFromAcctTypeId(int value) {
    this.fromAcctTypeId = value;
  }
/**
 * Gets the value of member variable fromAcctTypeId
 *
 * @author Roy Terrell.
 */
  public int getFromAcctTypeId() {
    return this.fromAcctTypeId;
  }
/**
 * Sets the value of member variable fromAcctCatgId
 *
 * @author Roy Terrell.
 */
  public void setFromAcctCatgId(int value) {
    this.fromAcctCatgId = value;
  }
/**
 * Gets the value of member variable fromAcctCatgId
 *
 * @author Roy Terrell.
 */
  public int getFromAcctCatgId() {
    return this.fromAcctCatgId;
  }
/**
 * Sets the value of member variable hasSubsidiary
 *
 * @author Roy Terrell.
 */
  public void setHasSubsidiary(int value) {
    this.hasSubsidiary = value;
  }
/**
 * Gets the value of member variable hasSubsidiary
 *
 * @author Roy Terrell.
 */
  public int getHasSubsidiary() {
    return this.hasSubsidiary;
  }
/**
 * Sets the value of member variable creditorId
 *
 * @author Roy Terrell.
 */
  public void setCreditorId(int value) {
    this.creditorId = value;
  }
/**
 * Gets the value of member variable creditorId
 *
 * @author Roy Terrell.
 */
  public int getCreditorId() {
    return this.creditorId;
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
 * Sets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public int getActive() {
    return this.active;
  }
/**
 * Sets the value of member variable apr
 *
 * @author Roy Terrell.
 */
  public void setApr(double value) {
    this.apr = value;
  }
/**
 * Gets the value of member variable apr
 *
 * @author Roy Terrell.
 */
  public double getApr() {
    return this.apr;
  }
/**
 * Sets the value of member variable creditorDateCreated
 *
 * @author Roy Terrell.
 */
  public void setCreditorDateCreated(java.util.Date value) {
    this.creditorDateCreated = value;
  }
/**
 * Gets the value of member variable creditorDateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getCreditorDateCreated() {
    return this.creditorDateCreated;
  }
/**
 * Sets the value of member variable creditorTypeDescription
 *
 * @author Roy Terrell.
 */
  public void setCreditorTypeDescription(String value) {
    this.creditorTypeDescription = value;
  }
/**
 * Gets the value of member variable creditorTypeDescription
 *
 * @author Roy Terrell.
 */
  public String getCreditorTypeDescription() {
    return this.creditorTypeDescription;
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
 * Sets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public void setLongname(String value) {
    this.longname = value;
  }
/**
 * Gets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public String getLongname() {
    return this.longname;
  }
/**
 * Sets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public void setShortname(String value) {
    this.shortname = value;
  }
/**
 * Gets the value of member variable shortname
 *
 * @author Roy Terrell.
 */
  public String getShortname() {
    return this.shortname;
  }
/**
 * Sets the value of member variable servType
 *
 * @author Roy Terrell.
 */
  public void setServType(int value) {
    this.servType = value;
  }
/**
 * Gets the value of member variable servType
 *
 * @author Roy Terrell.
 */
  public int getServType() {
    return this.servType;
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
 * Sets the value of member variable contactFirstname
 *
 * @author Roy Terrell.
 */
  public void setContactFirstname(String value) {
    this.contactFirstname = value;
  }
/**
 * Gets the value of member variable contactFirstname
 *
 * @author Roy Terrell.
 */
  public String getContactFirstname() {
    return this.contactFirstname;
  }
/**
 * Sets the value of member variable contactLastname
 *
 * @author Roy Terrell.
 */
  public void setContactLastname(String value) {
    this.contactLastname = value;
  }
/**
 * Gets the value of member variable contactLastname
 *
 * @author Roy Terrell.
 */
  public String getContactLastname() {
    return this.contactLastname;
  }
/**
 * Sets the value of member variable contactPhone
 *
 * @author Roy Terrell.
 */
  public void setContactPhone(String value) {
    this.contactPhone = value;
  }
/**
 * Gets the value of member variable contactPhone
 *
 * @author Roy Terrell.
 */
  public String getContactPhone() {
    return this.contactPhone;
  }
/**
 * Sets the value of member variable contactExt
 *
 * @author Roy Terrell.
 */
  public void setContactExt(String value) {
    this.contactExt = value;
  }
/**
 * Gets the value of member variable contactExt
 *
 * @author Roy Terrell.
 */
  public String getContactExt() {
    return this.contactExt;
  }
/**
 * Sets the value of member variable taxId
 *
 * @author Roy Terrell.
 */
  public void setTaxId(String value) {
    this.taxId = value;
  }
/**
 * Gets the value of member variable taxId
 *
 * @author Roy Terrell.
 */
  public String getTaxId() {
    return this.taxId;
  }
/**
 * Sets the value of member variable website
 *
 * @author Roy Terrell.
 */
  public void setWebsite(String value) {
    this.website = value;
  }
/**
 * Gets the value of member variable website
 *
 * @author Roy Terrell.
 */
  public String getWebsite() {
    return this.website;
  }
/**
 * Sets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public void setBalance(double value) {
    this.balance = value;
  }
/**
 * Gets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public double getBalance() {
    return this.balance;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
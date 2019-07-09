package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_creditor_xact_hist database table/view.
 *
 * @author Roy Terrell.
 */
public class VwCreditorXactHist extends OrmBean {

/** The javabean property equivalent of database column vw_creditor_xact_hist.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.creditor_id */
  private int creditorId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.account_number */
  private String accountNumber;
/** The javabean property equivalent of database column vw_creditor_xact_hist.creditor_type_id */
  private int creditorTypeId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.active */
  private int active;
/** The javabean property equivalent of database column vw_creditor_xact_hist.apr */
  private double apr;
/** The javabean property equivalent of database column vw_creditor_xact_hist.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_creditor_xact_hist.creditor_type_description */
  private String creditorTypeDescription;
/** The javabean property equivalent of database column vw_creditor_xact_hist.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.longname */
  private String longname;
/** The javabean property equivalent of database column vw_creditor_xact_hist.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_creditor_xact_hist.serv_type */
  private int servType;
/** The javabean property equivalent of database column vw_creditor_xact_hist.bus_type */
  private int busType;
/** The javabean property equivalent of database column vw_creditor_xact_hist.contact_ext */
  private String contactExt;
/** The javabean property equivalent of database column vw_creditor_xact_hist.contact_firstname */
  private String contactFirstname;
/** The javabean property equivalent of database column vw_creditor_xact_hist.contact_lastname */
  private String contactLastname;
/** The javabean property equivalent of database column vw_creditor_xact_hist.contact_phone */
  private String contactPhone;
/** The javabean property equivalent of database column vw_creditor_xact_hist.tax_id */
  private String taxId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.website */
  private String website;
/** The javabean property equivalent of database column vw_creditor_xact_hist.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_creditor_xact_hist.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_creditor_xact_hist.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.reason */
  private String reason;
/** The javabean property equivalent of database column vw_creditor_xact_hist.xact_type_name */
  private String xactTypeName;
/** The javabean property equivalent of database column vw_creditor_xact_hist.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_creditor_xact_hist.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.posted_date */
  private java.util.Date postedDate;
/** The javabean property equivalent of database column vw_creditor_xact_hist.confirm_no */
  private String confirmNo;
/** The javabean property equivalent of database column vw_creditor_xact_hist.neg_instr_no */
  private String negInstrNo;
/** The javabean property equivalent of database column vw_creditor_xact_hist.tender_id */
  private int tenderId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.creditor_activity_id */
  private int creditorActivityId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.creditor_activity_amount */
  private double creditorActivityAmount;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwCreditorXactHist() throws SystemException {
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
 * Sets the value of member variable accountNumber
 *
 * @author Roy Terrell.
 */
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }
/**
 * Gets the value of member variable accountNumber
 *
 * @author Roy Terrell.
 */
  public String getAccountNumber() {
    return this.accountNumber;
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
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
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
 * Sets the value of member variable creditorActivityId
 *
 * @author Roy Terrell.
 */
  public void setCreditorActivityId(int value) {
    this.creditorActivityId = value;
  }
/**
 * Gets the value of member variable creditorActivityId
 *
 * @author Roy Terrell.
 */
  public int getCreditorActivityId() {
    return this.creditorActivityId;
  }
/**
 * Sets the value of member variable creditorActivityAmount
 *
 * @author Roy Terrell.
 */
  public void setCreditorActivityAmount(double value) {
    this.creditorActivityAmount = value;
  }
/**
 * Gets the value of member variable creditorActivityAmount
 *
 * @author Roy Terrell.
 */
  public double getCreditorActivityAmount() {
    return this.creditorActivityAmount;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
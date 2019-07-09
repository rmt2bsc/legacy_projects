package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_creditor_xact_hist database table/view.
 *
 * @author auto generated.
 */
public class VwCreditorXactHist extends OrmBean {




	// Property name constants that belong to respective DataSource, VwCreditorXactHistView

/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, AccountNumber, of respective DataSource view. */
  public static final String PROP_ACCOUNTNUMBER = "AccountNumber";
/** The property name constant equivalent to property, CreditorTypeId, of respective DataSource view. */
  public static final String PROP_CREDITORTYPEID = "CreditorTypeId";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, Apr, of respective DataSource view. */
  public static final String PROP_APR = "Apr";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, CreditorTypeDescription, of respective DataSource view. */
  public static final String PROP_CREDITORTYPEDESCRIPTION = "CreditorTypeDescription";
/** The property name constant equivalent to property, XactAmount, of respective DataSource view. */
  public static final String PROP_XACTAMOUNT = "XactAmount";
/** The property name constant equivalent to property, XactDate, of respective DataSource view. */
  public static final String PROP_XACTDATE = "XactDate";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, Reason, of respective DataSource view. */
  public static final String PROP_REASON = "Reason";
/** The property name constant equivalent to property, XactTypeName, of respective DataSource view. */
  public static final String PROP_XACTTYPENAME = "XactTypeName";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, PostedDate, of respective DataSource view. */
  public static final String PROP_POSTEDDATE = "PostedDate";
/** The property name constant equivalent to property, ConfirmNo, of respective DataSource view. */
  public static final String PROP_CONFIRMNO = "ConfirmNo";
/** The property name constant equivalent to property, NegInstrNo, of respective DataSource view. */
  public static final String PROP_NEGINSTRNO = "NegInstrNo";
/** The property name constant equivalent to property, TenderId, of respective DataSource view. */
  public static final String PROP_TENDERID = "TenderId";
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, CreditorActivityId, of respective DataSource view. */
  public static final String PROP_CREDITORACTIVITYID = "CreditorActivityId";
/** The property name constant equivalent to property, CreditorActivityAmount, of respective DataSource view. */
  public static final String PROP_CREDITORACTIVITYAMOUNT = "CreditorActivityAmount";



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
/** The javabean property equivalent of database column vw_creditor_xact_hist.document_id */
  private int documentId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.creditor_activity_id */
  private int creditorActivityId;
/** The javabean property equivalent of database column vw_creditor_xact_hist.creditor_activity_amount */
  private double creditorActivityAmount;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwCreditorXactHist() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable xactId
 *
 * @author auto generated.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author atuo generated.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable creditorId
 *
 * @author auto generated.
 */
  public void setCreditorId(int value) {
    this.creditorId = value;
  }
/**
 * Gets the value of member variable creditorId
 *
 * @author atuo generated.
 */
  public int getCreditorId() {
    return this.creditorId;
  }
/**
 * Sets the value of member variable accountNumber
 *
 * @author auto generated.
 */
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }
/**
 * Gets the value of member variable accountNumber
 *
 * @author atuo generated.
 */
  public String getAccountNumber() {
    return this.accountNumber;
  }
/**
 * Sets the value of member variable creditorTypeId
 *
 * @author auto generated.
 */
  public void setCreditorTypeId(int value) {
    this.creditorTypeId = value;
  }
/**
 * Gets the value of member variable creditorTypeId
 *
 * @author atuo generated.
 */
  public int getCreditorTypeId() {
    return this.creditorTypeId;
  }
/**
 * Sets the value of member variable active
 *
 * @author auto generated.
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 *
 * @author atuo generated.
 */
  public int getActive() {
    return this.active;
  }
/**
 * Sets the value of member variable apr
 *
 * @author auto generated.
 */
  public void setApr(double value) {
    this.apr = value;
  }
/**
 * Gets the value of member variable apr
 *
 * @author atuo generated.
 */
  public double getApr() {
    return this.apr;
  }
/**
 * Sets the value of member variable creditLimit
 *
 * @author auto generated.
 */
  public void setCreditLimit(double value) {
    this.creditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 *
 * @author atuo generated.
 */
  public double getCreditLimit() {
    return this.creditLimit;
  }
/**
 * Sets the value of member variable creditorTypeDescription
 *
 * @author auto generated.
 */
  public void setCreditorTypeDescription(String value) {
    this.creditorTypeDescription = value;
  }
/**
 * Gets the value of member variable creditorTypeDescription
 *
 * @author atuo generated.
 */
  public String getCreditorTypeDescription() {
    return this.creditorTypeDescription;
  }
/**
 * Sets the value of member variable xactAmount
 *
 * @author auto generated.
 */
  public void setXactAmount(double value) {
    this.xactAmount = value;
  }
/**
 * Gets the value of member variable xactAmount
 *
 * @author atuo generated.
 */
  public double getXactAmount() {
    return this.xactAmount;
  }
/**
 * Sets the value of member variable xactDate
 *
 * @author auto generated.
 */
  public void setXactDate(java.util.Date value) {
    this.xactDate = value;
  }
/**
 * Gets the value of member variable xactDate
 *
 * @author atuo generated.
 */
  public java.util.Date getXactDate() {
    return this.xactDate;
  }
/**
 * Sets the value of member variable xactTypeId
 *
 * @author auto generated.
 */
  public void setXactTypeId(int value) {
    this.xactTypeId = value;
  }
/**
 * Gets the value of member variable xactTypeId
 *
 * @author atuo generated.
 */
  public int getXactTypeId() {
    return this.xactTypeId;
  }
/**
 * Sets the value of member variable reason
 *
 * @author auto generated.
 */
  public void setReason(String value) {
    this.reason = value;
  }
/**
 * Gets the value of member variable reason
 *
 * @author atuo generated.
 */
  public String getReason() {
    return this.reason;
  }
/**
 * Sets the value of member variable xactTypeName
 *
 * @author auto generated.
 */
  public void setXactTypeName(String value) {
    this.xactTypeName = value;
  }
/**
 * Gets the value of member variable xactTypeName
 *
 * @author atuo generated.
 */
  public String getXactTypeName() {
    return this.xactTypeName;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author auto generated.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author auto generated.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author atuo generated.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Sets the value of member variable postedDate
 *
 * @author auto generated.
 */
  public void setPostedDate(java.util.Date value) {
    this.postedDate = value;
  }
/**
 * Gets the value of member variable postedDate
 *
 * @author atuo generated.
 */
  public java.util.Date getPostedDate() {
    return this.postedDate;
  }
/**
 * Sets the value of member variable confirmNo
 *
 * @author auto generated.
 */
  public void setConfirmNo(String value) {
    this.confirmNo = value;
  }
/**
 * Gets the value of member variable confirmNo
 *
 * @author atuo generated.
 */
  public String getConfirmNo() {
    return this.confirmNo;
  }
/**
 * Sets the value of member variable negInstrNo
 *
 * @author auto generated.
 */
  public void setNegInstrNo(String value) {
    this.negInstrNo = value;
  }
/**
 * Gets the value of member variable negInstrNo
 *
 * @author atuo generated.
 */
  public String getNegInstrNo() {
    return this.negInstrNo;
  }
/**
 * Sets the value of member variable tenderId
 *
 * @author auto generated.
 */
  public void setTenderId(int value) {
    this.tenderId = value;
  }
/**
 * Gets the value of member variable tenderId
 *
 * @author atuo generated.
 */
  public int getTenderId() {
    return this.tenderId;
  }
/**
 * Sets the value of member variable documentId
 *
 * @author auto generated.
 */
  public void setDocumentId(int value) {
    this.documentId = value;
  }
/**
 * Gets the value of member variable documentId
 *
 * @author atuo generated.
 */
  public int getDocumentId() {
    return this.documentId;
  }
/**
 * Sets the value of member variable creditorActivityId
 *
 * @author auto generated.
 */
  public void setCreditorActivityId(int value) {
    this.creditorActivityId = value;
  }
/**
 * Gets the value of member variable creditorActivityId
 *
 * @author atuo generated.
 */
  public int getCreditorActivityId() {
    return this.creditorActivityId;
  }
/**
 * Sets the value of member variable creditorActivityAmount
 *
 * @author auto generated.
 */
  public void setCreditorActivityAmount(double value) {
    this.creditorActivityAmount = value;
  }
/**
 * Gets the value of member variable creditorActivityAmount
 *
 * @author atuo generated.
 */
  public double getCreditorActivityAmount() {
    return this.creditorActivityAmount;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
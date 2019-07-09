package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact database table/view.
 *
 * @author Roy Terrell.
 */
public class Xact extends OrmBean {

/** The javabean property equivalent of database column xact.id */
  private int id;
/** The javabean property equivalent of database column xact.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column xact.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column xact.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column xact.xact_subtype_id */
  private int xactSubtypeId;
/** The javabean property equivalent of database column xact.tender_id */
  private int tenderId;
/** The javabean property equivalent of database column xact.neg_instr_no */
  private String negInstrNo;
/** The javabean property equivalent of database column xact.bank_trans_ind */
  private String bankTransInd;
/** The javabean property equivalent of database column xact.confirm_no */
  private String confirmNo;
/** The javabean property equivalent of database column xact.entity_ref_no */
  private String entityRefNo;
/** The javabean property equivalent of database column xact.posted_date */
  private java.util.Date postedDate;
/** The javabean property equivalent of database column xact.reason */
  private String reason;
/** The javabean property equivalent of database column xact.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column xact.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column xact.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public Xact() throws SystemException {
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
 * Sets the value of member variable xactSubtypeId
 *
 * @author Roy Terrell.
 */
  public void setXactSubtypeId(int value) {
    this.xactSubtypeId = value;
  }
/**
 * Gets the value of member variable xactSubtypeId
 *
 * @author Roy Terrell.
 */
  public int getXactSubtypeId() {
    return this.xactSubtypeId;
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
 * Sets the value of member variable bankTransInd
 *
 * @author Roy Terrell.
 */
  public void setBankTransInd(String value) {
    this.bankTransInd = value;
  }
/**
 * Gets the value of member variable bankTransInd
 *
 * @author Roy Terrell.
 */
  public String getBankTransInd() {
    return this.bankTransInd;
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
 * Sets the value of member variable entityRefNo
 *
 * @author Roy Terrell.
 */
  public void setEntityRefNo(String value) {
    this.entityRefNo = value;
  }
/**
 * Gets the value of member variable entityRefNo
 *
 * @author Roy Terrell.
 */
  public String getEntityRefNo() {
    return this.entityRefNo;
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
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
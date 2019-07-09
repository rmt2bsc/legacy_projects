package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_list database table/view.
 *
 * @author Roy Terrell.
 */
public class VwXactList extends OrmBean {

/** The javabean property equivalent of database column vw_xact_list.id */
  private int id;
/** The javabean property equivalent of database column vw_xact_list.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_xact_list.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_xact_list.posted_date */
  private java.util.Date postedDate;
/** The javabean property equivalent of database column vw_xact_list.confirm_no */
  private String confirmNo;
/** The javabean property equivalent of database column vw_xact_list.neg_instr_no */
  private String negInstrNo;
/** The javabean property equivalent of database column vw_xact_list.tender_id */
  private int tenderId;
/** The javabean property equivalent of database column vw_xact_list.tender_description */
  private String tenderDescription;
/** The javabean property equivalent of database column vw_xact_list.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_xact_list.xact_subtype_id */
  private int xactSubtypeId;
/** The javabean property equivalent of database column vw_xact_list.xact_type_name */
  private String xactTypeName;
/** The javabean property equivalent of database column vw_xact_list.reason */
  private String reason;
/** The javabean property equivalent of database column vw_xact_list.create_date */
  private java.util.Date createDate;
/** The javabean property equivalent of database column vw_xact_list.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_xact_list.xact_category_id */
  private int xactCategoryId;
/** The javabean property equivalent of database column vw_xact_list.to_multiplier */
  private int toMultiplier;
/** The javabean property equivalent of database column vw_xact_list.from_multiplier */
  private int fromMultiplier;
/** The javabean property equivalent of database column vw_xact_list.to_acct_type_id */
  private int toAcctTypeId;
/** The javabean property equivalent of database column vw_xact_list.to_acct_catg_id */
  private int toAcctCatgId;
/** The javabean property equivalent of database column vw_xact_list.from_acct_type_id */
  private int fromAcctTypeId;
/** The javabean property equivalent of database column vw_xact_list.from_acct_catg_id */
  private int fromAcctCatgId;
/** The javabean property equivalent of database column vw_xact_list.has_subsidiary */
  private int hasSubsidiary;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwXactList() throws SystemException {
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
 * Sets the value of member variable createDate
 *
 * @author Roy Terrell.
 */
  public void setCreateDate(java.util.Date value) {
    this.createDate = value;
  }
/**
 * Gets the value of member variable createDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getCreateDate() {
    return this.createDate;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
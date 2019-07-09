package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_list database table/view.
 *
 * @author auto generated.
 */
public class VwXactList extends OrmBean {




	// Property name constants that belong to respective DataSource, VwXactListView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, XactAmount, of respective DataSource view. */
  public static final String PROP_XACTAMOUNT = "XactAmount";
/** The property name constant equivalent to property, XactDate, of respective DataSource view. */
  public static final String PROP_XACTDATE = "XactDate";
/** The property name constant equivalent to property, PostedDate, of respective DataSource view. */
  public static final String PROP_POSTEDDATE = "PostedDate";
/** The property name constant equivalent to property, ConfirmNo, of respective DataSource view. */
  public static final String PROP_CONFIRMNO = "ConfirmNo";
/** The property name constant equivalent to property, NegInstrNo, of respective DataSource view. */
  public static final String PROP_NEGINSTRNO = "NegInstrNo";
/** The property name constant equivalent to property, TenderId, of respective DataSource view. */
  public static final String PROP_TENDERID = "TenderId";
/** The property name constant equivalent to property, TenderDescription, of respective DataSource view. */
  public static final String PROP_TENDERDESCRIPTION = "TenderDescription";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, XactSubtypeId, of respective DataSource view. */
  public static final String PROP_XACTSUBTYPEID = "XactSubtypeId";
/** The property name constant equivalent to property, XactTypeName, of respective DataSource view. */
  public static final String PROP_XACTTYPENAME = "XactTypeName";
/** The property name constant equivalent to property, Reason, of respective DataSource view. */
  public static final String PROP_REASON = "Reason";
/** The property name constant equivalent to property, CreateDate, of respective DataSource view. */
  public static final String PROP_CREATEDATE = "CreateDate";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, XactCatgId, of respective DataSource view. */
  public static final String PROP_XACTCATGID = "XactCatgId";
/** The property name constant equivalent to property, ToMultiplier, of respective DataSource view. */
  public static final String PROP_TOMULTIPLIER = "ToMultiplier";
/** The property name constant equivalent to property, FromMultiplier, of respective DataSource view. */
  public static final String PROP_FROMMULTIPLIER = "FromMultiplier";
/** The property name constant equivalent to property, ToAcctTypeId, of respective DataSource view. */
  public static final String PROP_TOACCTTYPEID = "ToAcctTypeId";
/** The property name constant equivalent to property, ToAcctCatgId, of respective DataSource view. */
  public static final String PROP_TOACCTCATGID = "ToAcctCatgId";
/** The property name constant equivalent to property, FromAcctTypeId, of respective DataSource view. */
  public static final String PROP_FROMACCTTYPEID = "FromAcctTypeId";
/** The property name constant equivalent to property, FromAcctCatgId, of respective DataSource view. */
  public static final String PROP_FROMACCTCATGID = "FromAcctCatgId";
/** The property name constant equivalent to property, HasSubsidiary, of respective DataSource view. */
  public static final String PROP_HASSUBSIDIARY = "HasSubsidiary";



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
/** The javabean property equivalent of database column vw_xact_list.document_id */
  private int documentId;
/** The javabean property equivalent of database column vw_xact_list.xact_catg_id */
  private int xactCatgId;
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
 * @author auto generated.
 */
  public VwXactList() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable id
 *
 * @author auto generated.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author atuo generated.
 */
  public int getId() {
    return this.id;
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
 * Sets the value of member variable tenderDescription
 *
 * @author auto generated.
 */
  public void setTenderDescription(String value) {
    this.tenderDescription = value;
  }
/**
 * Gets the value of member variable tenderDescription
 *
 * @author atuo generated.
 */
  public String getTenderDescription() {
    return this.tenderDescription;
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
 * Sets the value of member variable xactSubtypeId
 *
 * @author auto generated.
 */
  public void setXactSubtypeId(int value) {
    this.xactSubtypeId = value;
  }
/**
 * Gets the value of member variable xactSubtypeId
 *
 * @author atuo generated.
 */
  public int getXactSubtypeId() {
    return this.xactSubtypeId;
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
 * Sets the value of member variable createDate
 *
 * @author auto generated.
 */
  public void setCreateDate(java.util.Date value) {
    this.createDate = value;
  }
/**
 * Gets the value of member variable createDate
 *
 * @author atuo generated.
 */
  public java.util.Date getCreateDate() {
    return this.createDate;
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
 * Sets the value of member variable xactCatgId
 *
 * @author auto generated.
 */
  public void setXactCatgId(int value) {
    this.xactCatgId = value;
  }
/**
 * Gets the value of member variable xactCatgId
 *
 * @author atuo generated.
 */
  public int getXactCatgId() {
    return this.xactCatgId;
  }
/**
 * Sets the value of member variable toMultiplier
 *
 * @author auto generated.
 */
  public void setToMultiplier(int value) {
    this.toMultiplier = value;
  }
/**
 * Gets the value of member variable toMultiplier
 *
 * @author atuo generated.
 */
  public int getToMultiplier() {
    return this.toMultiplier;
  }
/**
 * Sets the value of member variable fromMultiplier
 *
 * @author auto generated.
 */
  public void setFromMultiplier(int value) {
    this.fromMultiplier = value;
  }
/**
 * Gets the value of member variable fromMultiplier
 *
 * @author atuo generated.
 */
  public int getFromMultiplier() {
    return this.fromMultiplier;
  }
/**
 * Sets the value of member variable toAcctTypeId
 *
 * @author auto generated.
 */
  public void setToAcctTypeId(int value) {
    this.toAcctTypeId = value;
  }
/**
 * Gets the value of member variable toAcctTypeId
 *
 * @author atuo generated.
 */
  public int getToAcctTypeId() {
    return this.toAcctTypeId;
  }
/**
 * Sets the value of member variable toAcctCatgId
 *
 * @author auto generated.
 */
  public void setToAcctCatgId(int value) {
    this.toAcctCatgId = value;
  }
/**
 * Gets the value of member variable toAcctCatgId
 *
 * @author atuo generated.
 */
  public int getToAcctCatgId() {
    return this.toAcctCatgId;
  }
/**
 * Sets the value of member variable fromAcctTypeId
 *
 * @author auto generated.
 */
  public void setFromAcctTypeId(int value) {
    this.fromAcctTypeId = value;
  }
/**
 * Gets the value of member variable fromAcctTypeId
 *
 * @author atuo generated.
 */
  public int getFromAcctTypeId() {
    return this.fromAcctTypeId;
  }
/**
 * Sets the value of member variable fromAcctCatgId
 *
 * @author auto generated.
 */
  public void setFromAcctCatgId(int value) {
    this.fromAcctCatgId = value;
  }
/**
 * Gets the value of member variable fromAcctCatgId
 *
 * @author atuo generated.
 */
  public int getFromAcctCatgId() {
    return this.fromAcctCatgId;
  }
/**
 * Sets the value of member variable hasSubsidiary
 *
 * @author auto generated.
 */
  public void setHasSubsidiary(int value) {
    this.hasSubsidiary = value;
  }
/**
 * Gets the value of member variable hasSubsidiary
 *
 * @author atuo generated.
 */
  public int getHasSubsidiary() {
    return this.hasSubsidiary;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
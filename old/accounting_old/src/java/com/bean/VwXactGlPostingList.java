package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_gl_posting_list database table/view.
 *
 * @author auto generated.
 */
public class VwXactGlPostingList extends OrmBean {




	// Property name constants that belong to respective DataSource, VwXactGlPostingListView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, GlAccountId, of respective DataSource view. */
  public static final String PROP_GLACCOUNTID = "GlAccountId";
/** The property name constant equivalent to property, GlAcctName, of respective DataSource view. */
  public static final String PROP_GLACCTNAME = "GlAcctName";
/** The property name constant equivalent to property, XactAmount, of respective DataSource view. */
  public static final String PROP_XACTAMOUNT = "XactAmount";
/** The property name constant equivalent to property, XactDate, of respective DataSource view. */
  public static final String PROP_XACTDATE = "XactDate";
/** The property name constant equivalent to property, PostDate, of respective DataSource view. */
  public static final String PROP_POSTDATE = "PostDate";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, XactTypeName, of respective DataSource view. */
  public static final String PROP_XACTTYPENAME = "XactTypeName";
/** The property name constant equivalent to property, XactCategoryId, of respective DataSource view. */
  public static final String PROP_XACTCATEGORYID = "XactCategoryId";
/** The property name constant equivalent to property, XactCategoryDescription, of respective DataSource view. */
  public static final String PROP_XACTCATEGORYDESCRIPTION = "XactCategoryDescription";
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
/** The property name constant equivalent to property, Reason, of respective DataSource view. */
  public static final String PROP_REASON = "Reason";



	/** The javabean property equivalent of database column vw_xact_gl_posting_list.id */
  private int id;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.gl_acct_name */
  private String glAcctName;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.post_date */
  private java.util.Date postDate;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.xact_type_name */
  private String xactTypeName;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.xact_category_id */
  private int xactCategoryId;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.xact_category_description */
  private String xactCategoryDescription;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.to_multiplier */
  private int toMultiplier;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.from_multiplier */
  private int fromMultiplier;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.to_acct_type_id */
  private int toAcctTypeId;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.to_acct_catg_id */
  private int toAcctCatgId;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.from_acct_type_id */
  private int fromAcctTypeId;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.from_acct_catg_id */
  private int fromAcctCatgId;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.has_subsidiary */
  private int hasSubsidiary;
/** The javabean property equivalent of database column vw_xact_gl_posting_list.reason */
  private String reason;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwXactGlPostingList() throws SystemException {
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
 * Sets the value of member variable glAccountId
 *
 * @author auto generated.
 */
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
/**
 * Gets the value of member variable glAccountId
 *
 * @author atuo generated.
 */
  public int getGlAccountId() {
    return this.glAccountId;
  }
/**
 * Sets the value of member variable glAcctName
 *
 * @author auto generated.
 */
  public void setGlAcctName(String value) {
    this.glAcctName = value;
  }
/**
 * Gets the value of member variable glAcctName
 *
 * @author atuo generated.
 */
  public String getGlAcctName() {
    return this.glAcctName;
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
 * Sets the value of member variable postDate
 *
 * @author auto generated.
 */
  public void setPostDate(java.util.Date value) {
    this.postDate = value;
  }
/**
 * Gets the value of member variable postDate
 *
 * @author atuo generated.
 */
  public java.util.Date getPostDate() {
    return this.postDate;
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
 * Sets the value of member variable xactCategoryId
 *
 * @author auto generated.
 */
  public void setXactCategoryId(int value) {
    this.xactCategoryId = value;
  }
/**
 * Gets the value of member variable xactCategoryId
 *
 * @author atuo generated.
 */
  public int getXactCategoryId() {
    return this.xactCategoryId;
  }
/**
 * Sets the value of member variable xactCategoryDescription
 *
 * @author auto generated.
 */
  public void setXactCategoryDescription(String value) {
    this.xactCategoryDescription = value;
  }
/**
 * Gets the value of member variable xactCategoryDescription
 *
 * @author atuo generated.
 */
  public String getXactCategoryDescription() {
    return this.xactCategoryDescription;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_generic_xact_list database table/view.
 *
 * @author auto generated.
 */
public class VwGenericXactList extends OrmBean {




	// Property name constants that belong to respective DataSource, VwGenericXactListView

/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, XactDate, of respective DataSource view. */
  public static final String PROP_XACTDATE = "XactDate";
/** The property name constant equivalent to property, XactDateStr, of respective DataSource view. */
  public static final String PROP_XACTDATESTR = "XactDateStr";
/** The property name constant equivalent to property, XactAmount, of respective DataSource view. */
  public static final String PROP_XACTAMOUNT = "XactAmount";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, XactSubtypeId, of respective DataSource view. */
  public static final String PROP_XACTSUBTYPEID = "XactSubtypeId";
/** The property name constant equivalent to property, XactTypeName, of respective DataSource view. */
  public static final String PROP_XACTTYPENAME = "XactTypeName";
/** The property name constant equivalent to property, XactReason, of respective DataSource view. */
  public static final String PROP_XACTREASON = "XactReason";
/** The property name constant equivalent to property, ConfirmNo, of respective DataSource view. */
  public static final String PROP_CONFIRMNO = "ConfirmNo";
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, ParentEntityId, of respective DataSource view. */
  public static final String PROP_PARENTENTITYID = "ParentEntityId";
/** The property name constant equivalent to property, SpecXactLevel1Id, of respective DataSource view. */
  public static final String PROP_SPECXACTLEVEL1ID = "SpecXactLevel1Id";
/** The property name constant equivalent to property, SpecXactLevel1Date, of respective DataSource view. */
  public static final String PROP_SPECXACTLEVEL1DATE = "SpecXactLevel1Date";
/** The property name constant equivalent to property, SpecXactLevel2Id, of respective DataSource view. */
  public static final String PROP_SPECXACTLEVEL2ID = "SpecXactLevel2Id";
/** The property name constant equivalent to property, SpecXactLevel2Date, of respective DataSource view. */
  public static final String PROP_SPECXACTLEVEL2DATE = "SpecXactLevel2Date";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, BusinessName, of respective DataSource view. */
  public static final String PROP_BUSINESSNAME = "BusinessName";
/** The property name constant equivalent to property, InvoiceNo, of respective DataSource view. */
  public static final String PROP_INVOICENO = "InvoiceNo";



	/** The javabean property equivalent of database column vw_generic_xact_list.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_generic_xact_list.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_generic_xact_list.xact_date_str */
  private String xactDateStr;
/** The javabean property equivalent of database column vw_generic_xact_list.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_generic_xact_list.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_generic_xact_list.xact_subtype_id */
  private int xactSubtypeId;
/** The javabean property equivalent of database column vw_generic_xact_list.xact_type_name */
  private String xactTypeName;
/** The javabean property equivalent of database column vw_generic_xact_list.xact_reason */
  private String xactReason;
/** The javabean property equivalent of database column vw_generic_xact_list.confirm_no */
  private String confirmNo;
/** The javabean property equivalent of database column vw_generic_xact_list.document_id */
  private int documentId;
/** The javabean property equivalent of database column vw_generic_xact_list.parent_entity_id */
  private int parentEntityId;
/** The javabean property equivalent of database column vw_generic_xact_list.spec_xact_level1_id */
  private int specXactLevel1Id;
/** The javabean property equivalent of database column vw_generic_xact_list.spec_xact_level1_date */
  private java.util.Date specXactLevel1Date;
/** The javabean property equivalent of database column vw_generic_xact_list.spec_xact_level2_id */
  private int specXactLevel2Id;
/** The javabean property equivalent of database column vw_generic_xact_list.spec_xact_level2_date */
  private java.util.Date specXactLevel2Date;
/** The javabean property equivalent of database column vw_generic_xact_list.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_generic_xact_list.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_generic_xact_list.business_name */
  private String businessName;
/** The javabean property equivalent of database column vw_generic_xact_list.invoice_no */
  private String invoiceNo;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwGenericXactList() throws SystemException {
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
 * Sets the value of member variable xactDateStr
 *
 * @author auto generated.
 */
  public void setXactDateStr(String value) {
    this.xactDateStr = value;
  }
/**
 * Gets the value of member variable xactDateStr
 *
 * @author atuo generated.
 */
  public String getXactDateStr() {
    return this.xactDateStr;
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
 * Sets the value of member variable xactReason
 *
 * @author auto generated.
 */
  public void setXactReason(String value) {
    this.xactReason = value;
  }
/**
 * Gets the value of member variable xactReason
 *
 * @author atuo generated.
 */
  public String getXactReason() {
    return this.xactReason;
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
 * Sets the value of member variable parentEntityId
 *
 * @author auto generated.
 */
  public void setParentEntityId(int value) {
    this.parentEntityId = value;
  }
/**
 * Gets the value of member variable parentEntityId
 *
 * @author atuo generated.
 */
  public int getParentEntityId() {
    return this.parentEntityId;
  }
/**
 * Sets the value of member variable specXactLevel1Id
 *
 * @author auto generated.
 */
  public void setSpecXactLevel1Id(int value) {
    this.specXactLevel1Id = value;
  }
/**
 * Gets the value of member variable specXactLevel1Id
 *
 * @author atuo generated.
 */
  public int getSpecXactLevel1Id() {
    return this.specXactLevel1Id;
  }
/**
 * Sets the value of member variable specXactLevel1Date
 *
 * @author auto generated.
 */
  public void setSpecXactLevel1Date(java.util.Date value) {
    this.specXactLevel1Date = value;
  }
/**
 * Gets the value of member variable specXactLevel1Date
 *
 * @author atuo generated.
 */
  public java.util.Date getSpecXactLevel1Date() {
    return this.specXactLevel1Date;
  }
/**
 * Sets the value of member variable specXactLevel2Id
 *
 * @author auto generated.
 */
  public void setSpecXactLevel2Id(int value) {
    this.specXactLevel2Id = value;
  }
/**
 * Gets the value of member variable specXactLevel2Id
 *
 * @author atuo generated.
 */
  public int getSpecXactLevel2Id() {
    return this.specXactLevel2Id;
  }
/**
 * Sets the value of member variable specXactLevel2Date
 *
 * @author auto generated.
 */
  public void setSpecXactLevel2Date(java.util.Date value) {
    this.specXactLevel2Date = value;
  }
/**
 * Gets the value of member variable specXactLevel2Date
 *
 * @author atuo generated.
 */
  public java.util.Date getSpecXactLevel2Date() {
    return this.specXactLevel2Date;
  }
/**
 * Sets the value of member variable accountNo
 *
 * @author auto generated.
 */
  public void setAccountNo(String value) {
    this.accountNo = value;
  }
/**
 * Gets the value of member variable accountNo
 *
 * @author atuo generated.
 */
  public String getAccountNo() {
    return this.accountNo;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author auto generated.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author atuo generated.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Sets the value of member variable businessName
 *
 * @author auto generated.
 */
  public void setBusinessName(String value) {
    this.businessName = value;
  }
/**
 * Gets the value of member variable businessName
 *
 * @author atuo generated.
 */
  public String getBusinessName() {
    return this.businessName;
  }
/**
 * Sets the value of member variable invoiceNo
 *
 * @author auto generated.
 */
  public void setInvoiceNo(String value) {
    this.invoiceNo = value;
  }
/**
 * Gets the value of member variable invoiceNo
 *
 * @author atuo generated.
 */
  public String getInvoiceNo() {
    return this.invoiceNo;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
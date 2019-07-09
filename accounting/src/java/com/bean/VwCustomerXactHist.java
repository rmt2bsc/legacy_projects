package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_customer_xact_hist database table/view.
 *
 * @author auto generated.
 */
public class VwCustomerXactHist extends OrmBean {




	// Property name constants that belong to respective DataSource, VwCustomerXactHistView

/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, CustomerId, of respective DataSource view. */
  public static final String PROP_CUSTOMERID = "CustomerId";
/** The property name constant equivalent to property, PersonId, of respective DataSource view. */
  public static final String PROP_PERSONID = "PersonId";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, XactAmount, of respective DataSource view. */
  public static final String PROP_XACTAMOUNT = "XactAmount";
/** The property name constant equivalent to property, XactDate, of respective DataSource view. */
  public static final String PROP_XACTDATE = "XactDate";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, XactSubtypeId, of respective DataSource view. */
  public static final String PROP_XACTSUBTYPEID = "XactSubtypeId";
/** The property name constant equivalent to property, Reason, of respective DataSource view. */
  public static final String PROP_REASON = "Reason";
/** The property name constant equivalent to property, ConfirmNo, of respective DataSource view. */
  public static final String PROP_CONFIRMNO = "ConfirmNo";
/** The property name constant equivalent to property, DocumentId, of respective DataSource view. */
  public static final String PROP_DOCUMENTID = "DocumentId";
/** The property name constant equivalent to property, XactTypeName, of respective DataSource view. */
  public static final String PROP_XACTTYPENAME = "XactTypeName";
/** The property name constant equivalent to property, CustomerActivityId, of respective DataSource view. */
  public static final String PROP_CUSTOMERACTIVITYID = "CustomerActivityId";
/** The property name constant equivalent to property, CustomerActivityAmount, of respective DataSource view. */
  public static final String PROP_CUSTOMERACTIVITYAMOUNT = "CustomerActivityAmount";



	/** The javabean property equivalent of database column vw_customer_xact_hist.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_customer_xact_hist.customer_id */
  private int customerId;
/** The javabean property equivalent of database column vw_customer_xact_hist.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_customer_xact_hist.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_customer_xact_hist.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_customer_xact_hist.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_customer_xact_hist.active */
  private int active;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_subtype_id */
  private int xactSubtypeId;
/** The javabean property equivalent of database column vw_customer_xact_hist.reason */
  private String reason;
/** The javabean property equivalent of database column vw_customer_xact_hist.confirm_no */
  private String confirmNo;
/** The javabean property equivalent of database column vw_customer_xact_hist.document_id */
  private int documentId;
/** The javabean property equivalent of database column vw_customer_xact_hist.xact_type_name */
  private String xactTypeName;
/** The javabean property equivalent of database column vw_customer_xact_hist.customer_activity_id */
  private int customerActivityId;
/** The javabean property equivalent of database column vw_customer_xact_hist.customer_activity_amount */
  private double customerActivityAmount;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwCustomerXactHist() throws SystemException {
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
 * Sets the value of member variable customerId
 *
 * @author auto generated.
 */
  public void setCustomerId(int value) {
    this.customerId = value;
  }
/**
 * Gets the value of member variable customerId
 *
 * @author atuo generated.
 */
  public int getCustomerId() {
    return this.customerId;
  }
/**
 * Sets the value of member variable personId
 *
 * @author auto generated.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author atuo generated.
 */
  public int getPersonId() {
    return this.personId;
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
 * Sets the value of member variable customerActivityId
 *
 * @author auto generated.
 */
  public void setCustomerActivityId(int value) {
    this.customerActivityId = value;
  }
/**
 * Gets the value of member variable customerActivityId
 *
 * @author atuo generated.
 */
  public int getCustomerActivityId() {
    return this.customerActivityId;
  }
/**
 * Sets the value of member variable customerActivityAmount
 *
 * @author auto generated.
 */
  public void setCustomerActivityAmount(double value) {
    this.customerActivityAmount = value;
  }
/**
 * Gets the value of member variable customerActivityAmount
 *
 * @author atuo generated.
 */
  public double getCustomerActivityAmount() {
    return this.customerActivityAmount;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_purchase_order_list database table/view.
 *
 * @author auto generated.
 */
public class VwPurchaseOrderList extends OrmBean {




	// Property name constants that belong to respective DataSource, VwPurchaseOrderListView

/** The property name constant equivalent to property, AccountNumber, of respective DataSource view. */
  public static final String PROP_ACCOUNTNUMBER = "AccountNumber";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, CreditTypeId, of respective DataSource view. */
  public static final String PROP_CREDITTYPEID = "CreditTypeId";
/** The property name constant equivalent to property, CreditorTypeDescr, of respective DataSource view. */
  public static final String PROP_CREDITORTYPEDESCR = "CreditorTypeDescr";
/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, VendorId, of respective DataSource view. */
  public static final String PROP_VENDORID = "VendorId";
/** The property name constant equivalent to property, StatusId, of respective DataSource view. */
  public static final String PROP_STATUSID = "StatusId";
/** The property name constant equivalent to property, RefNo, of respective DataSource view. */
  public static final String PROP_REFNO = "RefNo";
/** The property name constant equivalent to property, Total, of respective DataSource view. */
  public static final String PROP_TOTAL = "Total";
/** The property name constant equivalent to property, StatusDescription, of respective DataSource view. */
  public static final String PROP_STATUSDESCRIPTION = "StatusDescription";
/** The property name constant equivalent to property, StatusHistId, of respective DataSource view. */
  public static final String PROP_STATUSHISTID = "StatusHistId";
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";



	/** The javabean property equivalent of database column vw_purchase_order_list.account_number */
  private String accountNumber;
/** The javabean property equivalent of database column vw_purchase_order_list.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_purchase_order_list.credit_type_id */
  private int creditTypeId;
/** The javabean property equivalent of database column vw_purchase_order_list.creditor_type_descr */
  private String creditorTypeDescr;
/** The javabean property equivalent of database column vw_purchase_order_list.id */
  private int id;
/** The javabean property equivalent of database column vw_purchase_order_list.vendor_id */
  private int vendorId;
/** The javabean property equivalent of database column vw_purchase_order_list.status_id */
  private int statusId;
/** The javabean property equivalent of database column vw_purchase_order_list.ref_no */
  private String refNo;
/** The javabean property equivalent of database column vw_purchase_order_list.total */
  private double total;
/** The javabean property equivalent of database column vw_purchase_order_list.status_description */
  private String statusDescription;
/** The javabean property equivalent of database column vw_purchase_order_list.status_hist_id */
  private int statusHistId;
/** The javabean property equivalent of database column vw_purchase_order_list.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column vw_purchase_order_list.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column vw_purchase_order_list.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_purchase_order_list.creditor_id */
  private int creditorId;
/** The javabean property equivalent of database column vw_purchase_order_list.business_id */
  private int businessId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwPurchaseOrderList() throws SystemException {
	super();
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
 * Sets the value of member variable creditTypeId
 *
 * @author auto generated.
 */
  public void setCreditTypeId(int value) {
    this.creditTypeId = value;
  }
/**
 * Gets the value of member variable creditTypeId
 *
 * @author atuo generated.
 */
  public int getCreditTypeId() {
    return this.creditTypeId;
  }
/**
 * Sets the value of member variable creditorTypeDescr
 *
 * @author auto generated.
 */
  public void setCreditorTypeDescr(String value) {
    this.creditorTypeDescr = value;
  }
/**
 * Gets the value of member variable creditorTypeDescr
 *
 * @author atuo generated.
 */
  public String getCreditorTypeDescr() {
    return this.creditorTypeDescr;
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
 * Sets the value of member variable vendorId
 *
 * @author auto generated.
 */
  public void setVendorId(int value) {
    this.vendorId = value;
  }
/**
 * Gets the value of member variable vendorId
 *
 * @author atuo generated.
 */
  public int getVendorId() {
    return this.vendorId;
  }
/**
 * Sets the value of member variable statusId
 *
 * @author auto generated.
 */
  public void setStatusId(int value) {
    this.statusId = value;
  }
/**
 * Gets the value of member variable statusId
 *
 * @author atuo generated.
 */
  public int getStatusId() {
    return this.statusId;
  }
/**
 * Sets the value of member variable refNo
 *
 * @author auto generated.
 */
  public void setRefNo(String value) {
    this.refNo = value;
  }
/**
 * Gets the value of member variable refNo
 *
 * @author atuo generated.
 */
  public String getRefNo() {
    return this.refNo;
  }
/**
 * Sets the value of member variable total
 *
 * @author auto generated.
 */
  public void setTotal(double value) {
    this.total = value;
  }
/**
 * Gets the value of member variable total
 *
 * @author atuo generated.
 */
  public double getTotal() {
    return this.total;
  }
/**
 * Sets the value of member variable statusDescription
 *
 * @author auto generated.
 */
  public void setStatusDescription(String value) {
    this.statusDescription = value;
  }
/**
 * Gets the value of member variable statusDescription
 *
 * @author atuo generated.
 */
  public String getStatusDescription() {
    return this.statusDescription;
  }
/**
 * Sets the value of member variable statusHistId
 *
 * @author auto generated.
 */
  public void setStatusHistId(int value) {
    this.statusHistId = value;
  }
/**
 * Gets the value of member variable statusHistId
 *
 * @author atuo generated.
 */
  public int getStatusHistId() {
    return this.statusHistId;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author auto generated.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author auto generated.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
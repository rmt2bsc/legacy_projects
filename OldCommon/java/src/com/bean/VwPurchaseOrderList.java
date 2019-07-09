package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_purchase_order_list database table/view.
 *
 * @author Roy Terrell.
 */
public class VwPurchaseOrderList extends OrmBean {

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
/** The javabean property equivalent of database column vw_purchase_order_list.account_number */
  private String accountNumber;
/** The javabean property equivalent of database column vw_purchase_order_list.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_purchase_order_list.credit_type_id */
  private int creditTypeId;
/** The javabean property equivalent of database column vw_purchase_order_list.creditor_type_id */
  private String creditorTypeId;
/** The javabean property equivalent of database column vw_purchase_order_list.longname */
  private String longname;
/** The javabean property equivalent of database column vw_purchase_order_list.shortname */
  private String shortname;
/** The javabean property equivalent of database column vw_purchase_order_list.tax_id */
  private String taxId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwPurchaseOrderList() throws SystemException {
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
 * Sets the value of member variable vendorId
 *
 * @author Roy Terrell.
 */
  public void setVendorId(int value) {
    this.vendorId = value;
  }
/**
 * Gets the value of member variable vendorId
 *
 * @author Roy Terrell.
 */
  public int getVendorId() {
    return this.vendorId;
  }
/**
 * Sets the value of member variable statusId
 *
 * @author Roy Terrell.
 */
  public void setStatusId(int value) {
    this.statusId = value;
  }
/**
 * Gets the value of member variable statusId
 *
 * @author Roy Terrell.
 */
  public int getStatusId() {
    return this.statusId;
  }
/**
 * Sets the value of member variable refNo
 *
 * @author Roy Terrell.
 */
  public void setRefNo(String value) {
    this.refNo = value;
  }
/**
 * Gets the value of member variable refNo
 *
 * @author Roy Terrell.
 */
  public String getRefNo() {
    return this.refNo;
  }
/**
 * Sets the value of member variable total
 *
 * @author Roy Terrell.
 */
  public void setTotal(double value) {
    this.total = value;
  }
/**
 * Gets the value of member variable total
 *
 * @author Roy Terrell.
 */
  public double getTotal() {
    return this.total;
  }
/**
 * Sets the value of member variable statusDescription
 *
 * @author Roy Terrell.
 */
  public void setStatusDescription(String value) {
    this.statusDescription = value;
  }
/**
 * Gets the value of member variable statusDescription
 *
 * @author Roy Terrell.
 */
  public String getStatusDescription() {
    return this.statusDescription;
  }
/**
 * Sets the value of member variable statusHistId
 *
 * @author Roy Terrell.
 */
  public void setStatusHistId(int value) {
    this.statusHistId = value;
  }
/**
 * Gets the value of member variable statusHistId
 *
 * @author Roy Terrell.
 */
  public int getStatusHistId() {
    return this.statusHistId;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
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
 * Sets the value of member variable creditTypeId
 *
 * @author Roy Terrell.
 */
  public void setCreditTypeId(int value) {
    this.creditTypeId = value;
  }
/**
 * Gets the value of member variable creditTypeId
 *
 * @author Roy Terrell.
 */
  public int getCreditTypeId() {
    return this.creditTypeId;
  }
/**
 * Sets the value of member variable creditorTypeId
 *
 * @author Roy Terrell.
 */
  public void setCreditorTypeId(String value) {
    this.creditorTypeId = value;
  }
/**
 * Gets the value of member variable creditorTypeId
 *
 * @author Roy Terrell.
 */
  public String getCreditorTypeId() {
    return this.creditorTypeId;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
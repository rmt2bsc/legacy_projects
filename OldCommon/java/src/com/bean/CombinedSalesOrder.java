package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the combined_sales_order database table/view.
 *
 * @author Roy Terrell.
 */
public class CombinedSalesOrder extends OrmBean {

/** The javabean property equivalent of database column combined_sales_order.order_id */
  private int orderId;
/** The javabean property equivalent of database column combined_sales_order.customer_id */
  private int customerId;
/** The javabean property equivalent of database column combined_sales_order.is_invoiced */
  private int isInvoiced;
/** The javabean property equivalent of database column combined_sales_order.order_date */
  private java.util.Date orderDate;
/** The javabean property equivalent of database column combined_sales_order.user_id */
  private String userId;
/** The javabean property equivalent of database column combined_sales_order.item_qty */
  private double itemQty;
/** The javabean property equivalent of database column combined_sales_order.backorder_qty */
  private double backorderQty;
/** The javabean property equivalent of database column combined_sales_order.status_id */
  private int statusId;
/** The javabean property equivalent of database column combined_sales_order.status_description */
  private String statusDescription;
/** The javabean property equivalent of database column combined_sales_order.invoice_no */
  private String invoiceNo;
/** The javabean property equivalent of database column combined_sales_order.invoice_date */
  private java.util.Date invoiceDate;
/** The javabean property equivalent of database column combined_sales_order.xact_id */
  private int xactId;
/** The javabean property equivalent of database column combined_sales_order.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column combined_sales_order.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column combined_sales_order.xact_confirm_no */
  private String xactConfirmNo;
/** The javabean property equivalent of database column combined_sales_order.xact_reason */
  private String xactReason;
/** The javabean property equivalent of database column combined_sales_order.xact_posted_date */
  private java.util.Date xactPostedDate;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public CombinedSalesOrder() throws SystemException {
  }
/**
 * Sets the value of member variable orderId
 *
 * @author Roy Terrell.
 */
  public void setOrderId(int value) {
    this.orderId = value;
  }
/**
 * Gets the value of member variable orderId
 *
 * @author Roy Terrell.
 */
  public int getOrderId() {
    return this.orderId;
  }
/**
 * Sets the value of member variable customerId
 *
 * @author Roy Terrell.
 */
  public void setCustomerId(int value) {
    this.customerId = value;
  }
/**
 * Gets the value of member variable customerId
 *
 * @author Roy Terrell.
 */
  public int getCustomerId() {
    return this.customerId;
  }
/**
 * Sets the value of member variable isInvoiced
 *
 * @author Roy Terrell.
 */
  public void setIsInvoiced(int value) {
    this.isInvoiced = value;
  }
/**
 * Gets the value of member variable isInvoiced
 *
 * @author Roy Terrell.
 */
  public int getIsInvoiced() {
    return this.isInvoiced;
  }
/**
 * Sets the value of member variable orderDate
 *
 * @author Roy Terrell.
 */
  public void setOrderDate(java.util.Date value) {
    this.orderDate = value;
  }
/**
 * Gets the value of member variable orderDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getOrderDate() {
    return this.orderDate;
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
 * Sets the value of member variable itemQty
 *
 * @author Roy Terrell.
 */
  public void setItemQty(double value) {
    this.itemQty = value;
  }
/**
 * Gets the value of member variable itemQty
 *
 * @author Roy Terrell.
 */
  public double getItemQty() {
    return this.itemQty;
  }
/**
 * Sets the value of member variable backorderQty
 *
 * @author Roy Terrell.
 */
  public void setBackorderQty(double value) {
    this.backorderQty = value;
  }
/**
 * Gets the value of member variable backorderQty
 *
 * @author Roy Terrell.
 */
  public double getBackorderQty() {
    return this.backorderQty;
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
 * Sets the value of member variable invoiceNo
 *
 * @author Roy Terrell.
 */
  public void setInvoiceNo(String value) {
    this.invoiceNo = value;
  }
/**
 * Gets the value of member variable invoiceNo
 *
 * @author Roy Terrell.
 */
  public String getInvoiceNo() {
    return this.invoiceNo;
  }
/**
 * Sets the value of member variable invoiceDate
 *
 * @author Roy Terrell.
 */
  public void setInvoiceDate(java.util.Date value) {
    this.invoiceDate = value;
  }
/**
 * Gets the value of member variable invoiceDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getInvoiceDate() {
    return this.invoiceDate;
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
 * Sets the value of member variable xactConfirmNo
 *
 * @author Roy Terrell.
 */
  public void setXactConfirmNo(String value) {
    this.xactConfirmNo = value;
  }
/**
 * Gets the value of member variable xactConfirmNo
 *
 * @author Roy Terrell.
 */
  public String getXactConfirmNo() {
    return this.xactConfirmNo;
  }
/**
 * Sets the value of member variable xactReason
 *
 * @author Roy Terrell.
 */
  public void setXactReason(String value) {
    this.xactReason = value;
  }
/**
 * Gets the value of member variable xactReason
 *
 * @author Roy Terrell.
 */
  public String getXactReason() {
    return this.xactReason;
  }
/**
 * Sets the value of member variable xactPostedDate
 *
 * @author Roy Terrell.
 */
  public void setXactPostedDate(java.util.Date value) {
    this.xactPostedDate = value;
  }
/**
 * Gets the value of member variable xactPostedDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getXactPostedDate() {
    return this.xactPostedDate;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
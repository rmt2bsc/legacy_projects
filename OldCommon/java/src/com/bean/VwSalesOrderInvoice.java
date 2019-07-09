package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_sales_order_invoice database table/view.
 *
 * @author Roy Terrell.
 */
public class VwSalesOrderInvoice extends OrmBean {

/** The javabean property equivalent of database column vw_sales_order_invoice.sales_order_id */
  private int salesOrderId;
/** The javabean property equivalent of database column vw_sales_order_invoice.customer_id */
  private int customerId;
/** The javabean property equivalent of database column vw_sales_order_invoice.invoiced */
  private int invoiced;
/** The javabean property equivalent of database column vw_sales_order_invoice.order_status_id */
  private int orderStatusId;
/** The javabean property equivalent of database column vw_sales_order_invoice.order_status_descr */
  private String orderStatusDescr;
/** The javabean property equivalent of database column vw_sales_order_invoice.sales_order_date */
  private java.util.Date salesOrderDate;
/** The javabean property equivalent of database column vw_sales_order_invoice.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_sales_order_invoice.invoice_no */
  private String invoiceNo;
/** The javabean property equivalent of database column vw_sales_order_invoice.invoice_date */
  private java.util.Date invoiceDate;
/** The javabean property equivalent of database column vw_sales_order_invoice.order_total */
  private double orderTotal;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwSalesOrderInvoice() throws SystemException {
  }
/**
 * Sets the value of member variable salesOrderId
 *
 * @author Roy Terrell.
 */
  public void setSalesOrderId(int value) {
    this.salesOrderId = value;
  }
/**
 * Gets the value of member variable salesOrderId
 *
 * @author Roy Terrell.
 */
  public int getSalesOrderId() {
    return this.salesOrderId;
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
 * Sets the value of member variable invoiced
 *
 * @author Roy Terrell.
 */
  public void setInvoiced(int value) {
    this.invoiced = value;
  }
/**
 * Gets the value of member variable invoiced
 *
 * @author Roy Terrell.
 */
  public int getInvoiced() {
    return this.invoiced;
  }
/**
 * Sets the value of member variable orderStatusId
 *
 * @author Roy Terrell.
 */
  public void setOrderStatusId(int value) {
    this.orderStatusId = value;
  }
/**
 * Gets the value of member variable orderStatusId
 *
 * @author Roy Terrell.
 */
  public int getOrderStatusId() {
    return this.orderStatusId;
  }
/**
 * Sets the value of member variable orderStatusDescr
 *
 * @author Roy Terrell.
 */
  public void setOrderStatusDescr(String value) {
    this.orderStatusDescr = value;
  }
/**
 * Gets the value of member variable orderStatusDescr
 *
 * @author Roy Terrell.
 */
  public String getOrderStatusDescr() {
    return this.orderStatusDescr;
  }
/**
 * Sets the value of member variable salesOrderDate
 *
 * @author Roy Terrell.
 */
  public void setSalesOrderDate(java.util.Date value) {
    this.salesOrderDate = value;
  }
/**
 * Gets the value of member variable salesOrderDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getSalesOrderDate() {
    return this.salesOrderDate;
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
 * Sets the value of member variable orderTotal
 *
 * @author Roy Terrell.
 */
  public void setOrderTotal(double value) {
    this.orderTotal = value;
  }
/**
 * Gets the value of member variable orderTotal
 *
 * @author Roy Terrell.
 */
  public double getOrderTotal() {
    return this.orderTotal;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
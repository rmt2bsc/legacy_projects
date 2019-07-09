package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_sales_order_invoice database table/view.
 *
 * @author auto generated.
 */
public class VwSalesOrderInvoice extends OrmBean {




	// Property name constants that belong to respective DataSource, VwSalesOrderInvoiceView

/** The property name constant equivalent to property, SalesOrderId, of respective DataSource view. */
  public static final String PROP_SALESORDERID = "SalesOrderId";
/** The property name constant equivalent to property, CustomerId, of respective DataSource view. */
  public static final String PROP_CUSTOMERID = "CustomerId";
/** The property name constant equivalent to property, Invoiced, of respective DataSource view. */
  public static final String PROP_INVOICED = "Invoiced";
/** The property name constant equivalent to property, OrderStatusId, of respective DataSource view. */
  public static final String PROP_ORDERSTATUSID = "OrderStatusId";
/** The property name constant equivalent to property, OrderStatusDescr, of respective DataSource view. */
  public static final String PROP_ORDERSTATUSDESCR = "OrderStatusDescr";
/** The property name constant equivalent to property, SalesOrderDate, of respective DataSource view. */
  public static final String PROP_SALESORDERDATE = "SalesOrderDate";
/** The property name constant equivalent to property, InvoiceId, of respective DataSource view. */
  public static final String PROP_INVOICEID = "InvoiceId";
/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, InvoiceNo, of respective DataSource view. */
  public static final String PROP_INVOICENO = "InvoiceNo";
/** The property name constant equivalent to property, InvoiceDate, of respective DataSource view. */
  public static final String PROP_INVOICEDATE = "InvoiceDate";
/** The property name constant equivalent to property, OrderTotal, of respective DataSource view. */
  public static final String PROP_ORDERTOTAL = "OrderTotal";
/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, AccountNo, of respective DataSource view. */
  public static final String PROP_ACCOUNTNO = "AccountNo";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



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
/** The javabean property equivalent of database column vw_sales_order_invoice.invoice_id */
  private int invoiceId;
/** The javabean property equivalent of database column vw_sales_order_invoice.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_sales_order_invoice.invoice_no */
  private String invoiceNo;
/** The javabean property equivalent of database column vw_sales_order_invoice.invoice_date */
  private java.util.Date invoiceDate;
/** The javabean property equivalent of database column vw_sales_order_invoice.order_total */
  private double orderTotal;
/** The javabean property equivalent of database column vw_sales_order_invoice.acct_id */
  private int acctId;
/** The javabean property equivalent of database column vw_sales_order_invoice.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_sales_order_invoice.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_sales_order_invoice.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwSalesOrderInvoice() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable salesOrderId
 *
 * @author auto generated.
 */
  public void setSalesOrderId(int value) {
    this.salesOrderId = value;
  }
/**
 * Gets the value of member variable salesOrderId
 *
 * @author atuo generated.
 */
  public int getSalesOrderId() {
    return this.salesOrderId;
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
 * Sets the value of member variable invoiced
 *
 * @author auto generated.
 */
  public void setInvoiced(int value) {
    this.invoiced = value;
  }
/**
 * Gets the value of member variable invoiced
 *
 * @author atuo generated.
 */
  public int getInvoiced() {
    return this.invoiced;
  }
/**
 * Sets the value of member variable orderStatusId
 *
 * @author auto generated.
 */
  public void setOrderStatusId(int value) {
    this.orderStatusId = value;
  }
/**
 * Gets the value of member variable orderStatusId
 *
 * @author atuo generated.
 */
  public int getOrderStatusId() {
    return this.orderStatusId;
  }
/**
 * Sets the value of member variable orderStatusDescr
 *
 * @author auto generated.
 */
  public void setOrderStatusDescr(String value) {
    this.orderStatusDescr = value;
  }
/**
 * Gets the value of member variable orderStatusDescr
 *
 * @author atuo generated.
 */
  public String getOrderStatusDescr() {
    return this.orderStatusDescr;
  }
/**
 * Sets the value of member variable salesOrderDate
 *
 * @author auto generated.
 */
  public void setSalesOrderDate(java.util.Date value) {
    this.salesOrderDate = value;
  }
/**
 * Gets the value of member variable salesOrderDate
 *
 * @author atuo generated.
 */
  public java.util.Date getSalesOrderDate() {
    return this.salesOrderDate;
  }
/**
 * Sets the value of member variable invoiceId
 *
 * @author auto generated.
 */
  public void setInvoiceId(int value) {
    this.invoiceId = value;
  }
/**
 * Gets the value of member variable invoiceId
 *
 * @author atuo generated.
 */
  public int getInvoiceId() {
    return this.invoiceId;
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
 * Sets the value of member variable invoiceDate
 *
 * @author auto generated.
 */
  public void setInvoiceDate(java.util.Date value) {
    this.invoiceDate = value;
  }
/**
 * Gets the value of member variable invoiceDate
 *
 * @author atuo generated.
 */
  public java.util.Date getInvoiceDate() {
    return this.invoiceDate;
  }
/**
 * Sets the value of member variable orderTotal
 *
 * @author auto generated.
 */
  public void setOrderTotal(double value) {
    this.orderTotal = value;
  }
/**
 * Gets the value of member variable orderTotal
 *
 * @author atuo generated.
 */
  public double getOrderTotal() {
    return this.orderTotal;
  }
/**
 * Sets the value of member variable acctId
 *
 * @author auto generated.
 */
  public void setAcctId(int value) {
    this.acctId = value;
  }
/**
 * Gets the value of member variable acctId
 *
 * @author atuo generated.
 */
  public int getAcctId() {
    return this.acctId;
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
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
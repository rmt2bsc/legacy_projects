package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the sales_invoice database table/view.
 *
 * @author auto generated.
 */
public class SalesInvoice extends OrmBean {




	// Property name constants that belong to respective DataSource, SalesInvoiceView

/** The property name constant equivalent to property, InvoiceId, of respective DataSource view. */
  public static final String PROP_INVOICEID = "InvoiceId";
/** The property name constant equivalent to property, SoId, of respective DataSource view. */
  public static final String PROP_SOID = "SoId";
/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, InvoiceNo, of respective DataSource view. */
  public static final String PROP_INVOICENO = "InvoiceNo";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, IpCreated, of respective DataSource view. */
  public static final String PROP_IPCREATED = "IpCreated";
/** The property name constant equivalent to property, IpUpdated, of respective DataSource view. */
  public static final String PROP_IPUPDATED = "IpUpdated";



	/** The javabean property equivalent of database column sales_invoice.invoice_id */
  private int invoiceId;
/** The javabean property equivalent of database column sales_invoice.so_id */
  private int soId;
/** The javabean property equivalent of database column sales_invoice.xact_id */
  private int xactId;
/** The javabean property equivalent of database column sales_invoice.invoice_no */
  private String invoiceNo;
/** The javabean property equivalent of database column sales_invoice.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column sales_invoice.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column sales_invoice.user_id */
  private String userId;
/** The javabean property equivalent of database column sales_invoice.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column sales_invoice.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public SalesInvoice() throws SystemException {
	super();
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
 * Sets the value of member variable soId
 *
 * @author auto generated.
 */
  public void setSoId(int value) {
    this.soId = value;
  }
/**
 * Gets the value of member variable soId
 *
 * @author atuo generated.
 */
  public int getSoId() {
    return this.soId;
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
 * Sets the value of member variable dateCreated
 *
 * @author auto generated.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * @author auto generated.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
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
 * Sets the value of member variable ipCreated
 *
 * @author auto generated.
 */
  public void setIpCreated(String value) {
    this.ipCreated = value;
  }
/**
 * Gets the value of member variable ipCreated
 *
 * @author atuo generated.
 */
  public String getIpCreated() {
    return this.ipCreated;
  }
/**
 * Sets the value of member variable ipUpdated
 *
 * @author auto generated.
 */
  public void setIpUpdated(String value) {
    this.ipUpdated = value;
  }
/**
 * Gets the value of member variable ipUpdated
 *
 * @author atuo generated.
 */
  public String getIpUpdated() {
    return this.ipUpdated;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
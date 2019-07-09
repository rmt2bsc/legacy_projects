package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order database table/view.
 *
 * @author Roy Terrell.
 */
public class PurchaseOrder extends OrmBean {

/** The javabean property equivalent of database column purchase_order.id */
  private int id;
/** The javabean property equivalent of database column purchase_order.vendor_id */
  private int vendorId;
/** The javabean property equivalent of database column purchase_order.status */
  private int status;
/** The javabean property equivalent of database column purchase_order.xact_id */
  private int xactId;
/** The javabean property equivalent of database column purchase_order.ref_no */
  private String refNo;
/** The javabean property equivalent of database column purchase_order.total */
  private double total;
/** The javabean property equivalent of database column purchase_order.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column purchase_order.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column purchase_order.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public PurchaseOrder() throws SystemException {
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
 * Sets the value of member variable status
 *
 * @author Roy Terrell.
 */
  public void setStatus(int value) {
    this.status = value;
  }
/**
 * Gets the value of member variable status
 *
 * @author Roy Terrell.
 */
  public int getStatus() {
    return this.status;
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
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
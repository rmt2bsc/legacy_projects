package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_items database table/view.
 *
 * @author Roy Terrell.
 */
public class PurchaseOrderItems extends OrmBean {

/** The javabean property equivalent of database column purchase_order_items.id */
  private int id;
/** The javabean property equivalent of database column purchase_order_items.purchase_order_id */
  private int purchaseOrderId;
/** The javabean property equivalent of database column purchase_order_items.item_master_id */
  private int itemMasterId;
/** The javabean property equivalent of database column purchase_order_items.unit_cost */
  private double unitCost;
/** The javabean property equivalent of database column purchase_order_items.qty */
  private int qty;
/** The javabean property equivalent of database column purchase_order_items.qty_rcvd */
  private int qtyRcvd;
/** The javabean property equivalent of database column purchase_order_items.qty_rtn */
  private int qtyRtn;
/** The javabean property equivalent of database column purchase_order_items.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column purchase_order_items.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column purchase_order_items.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public PurchaseOrderItems() throws SystemException {
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
 * Sets the value of member variable purchaseOrderId
 *
 * @author Roy Terrell.
 */
  public void setPurchaseOrderId(int value) {
    this.purchaseOrderId = value;
  }
/**
 * Gets the value of member variable purchaseOrderId
 *
 * @author Roy Terrell.
 */
  public int getPurchaseOrderId() {
    return this.purchaseOrderId;
  }
/**
 * Sets the value of member variable itemMasterId
 *
 * @author Roy Terrell.
 */
  public void setItemMasterId(int value) {
    this.itemMasterId = value;
  }
/**
 * Gets the value of member variable itemMasterId
 *
 * @author Roy Terrell.
 */
  public int getItemMasterId() {
    return this.itemMasterId;
  }
/**
 * Sets the value of member variable unitCost
 *
 * @author Roy Terrell.
 */
  public void setUnitCost(double value) {
    this.unitCost = value;
  }
/**
 * Gets the value of member variable unitCost
 *
 * @author Roy Terrell.
 */
  public double getUnitCost() {
    return this.unitCost;
  }
/**
 * Sets the value of member variable qty
 *
 * @author Roy Terrell.
 */
  public void setQty(int value) {
    this.qty = value;
  }
/**
 * Gets the value of member variable qty
 *
 * @author Roy Terrell.
 */
  public int getQty() {
    return this.qty;
  }
/**
 * Sets the value of member variable qtyRcvd
 *
 * @author Roy Terrell.
 */
  public void setQtyRcvd(int value) {
    this.qtyRcvd = value;
  }
/**
 * Gets the value of member variable qtyRcvd
 *
 * @author Roy Terrell.
 */
  public int getQtyRcvd() {
    return this.qtyRcvd;
  }
/**
 * Sets the value of member variable qtyRtn
 *
 * @author Roy Terrell.
 */
  public void setQtyRtn(int value) {
    this.qtyRtn = value;
  }
/**
 * Gets the value of member variable qtyRtn
 *
 * @author Roy Terrell.
 */
  public int getQtyRtn() {
    return this.qtyRtn;
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
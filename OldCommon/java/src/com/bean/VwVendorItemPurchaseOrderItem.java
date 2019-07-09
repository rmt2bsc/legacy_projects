package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_vendor_item_purchase_order_item database table/view.
 *
 * @author Roy Terrell.
 */
public class VwVendorItemPurchaseOrderItem extends OrmBean {

/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.item_master_id */
  private int itemMasterId;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.vendor_id */
  private int vendorId;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.vendor_item_no */
  private String vendorItemNo;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.item_serial_no */
  private String itemSerialNo;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.unit_cost */
  private double unitCost;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.description */
  private String description;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_on_hand */
  private int qtyOnHand;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.markup */
  private double markup;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.override_retail */
  private int overrideRetail;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.purchase_order_id */
  private int purchaseOrderId;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_orderd */
  private int qtyOrderd;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.actual_unit_cost */
  private double actualUnitCost;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_received */
  private int qtyReceived;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_returned */
  private int qtyReturned;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwVendorItemPurchaseOrderItem() throws SystemException {
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
 * Sets the value of member variable vendorItemNo
 *
 * @author Roy Terrell.
 */
  public void setVendorItemNo(String value) {
    this.vendorItemNo = value;
  }
/**
 * Gets the value of member variable vendorItemNo
 *
 * @author Roy Terrell.
 */
  public String getVendorItemNo() {
    return this.vendorItemNo;
  }
/**
 * Sets the value of member variable itemSerialNo
 *
 * @author Roy Terrell.
 */
  public void setItemSerialNo(String value) {
    this.itemSerialNo = value;
  }
/**
 * Gets the value of member variable itemSerialNo
 *
 * @author Roy Terrell.
 */
  public String getItemSerialNo() {
    return this.itemSerialNo;
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
 * Sets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable qtyOnHand
 *
 * @author Roy Terrell.
 */
  public void setQtyOnHand(int value) {
    this.qtyOnHand = value;
  }
/**
 * Gets the value of member variable qtyOnHand
 *
 * @author Roy Terrell.
 */
  public int getQtyOnHand() {
    return this.qtyOnHand;
  }
/**
 * Sets the value of member variable markup
 *
 * @author Roy Terrell.
 */
  public void setMarkup(double value) {
    this.markup = value;
  }
/**
 * Gets the value of member variable markup
 *
 * @author Roy Terrell.
 */
  public double getMarkup() {
    return this.markup;
  }
/**
 * Sets the value of member variable overrideRetail
 *
 * @author Roy Terrell.
 */
  public void setOverrideRetail(int value) {
    this.overrideRetail = value;
  }
/**
 * Gets the value of member variable overrideRetail
 *
 * @author Roy Terrell.
 */
  public int getOverrideRetail() {
    return this.overrideRetail;
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
 * Sets the value of member variable qtyOrderd
 *
 * @author Roy Terrell.
 */
  public void setQtyOrderd(int value) {
    this.qtyOrderd = value;
  }
/**
 * Gets the value of member variable qtyOrderd
 *
 * @author Roy Terrell.
 */
  public int getQtyOrderd() {
    return this.qtyOrderd;
  }
/**
 * Sets the value of member variable actualUnitCost
 *
 * @author Roy Terrell.
 */
  public void setActualUnitCost(double value) {
    this.actualUnitCost = value;
  }
/**
 * Gets the value of member variable actualUnitCost
 *
 * @author Roy Terrell.
 */
  public double getActualUnitCost() {
    return this.actualUnitCost;
  }
/**
 * Sets the value of member variable qtyReceived
 *
 * @author Roy Terrell.
 */
  public void setQtyReceived(int value) {
    this.qtyReceived = value;
  }
/**
 * Gets the value of member variable qtyReceived
 *
 * @author Roy Terrell.
 */
  public int getQtyReceived() {
    return this.qtyReceived;
  }
/**
 * Sets the value of member variable qtyReturned
 *
 * @author Roy Terrell.
 */
  public void setQtyReturned(int value) {
    this.qtyReturned = value;
  }
/**
 * Gets the value of member variable qtyReturned
 *
 * @author Roy Terrell.
 */
  public int getQtyReturned() {
    return this.qtyReturned;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
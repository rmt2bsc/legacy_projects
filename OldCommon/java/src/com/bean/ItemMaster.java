package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the item_master database table/view.
 *
 * @author Roy Terrell.
 */
public class ItemMaster extends OrmBean {

/** The javabean property equivalent of database column item_master.id */
  private int id;
/** The javabean property equivalent of database column item_master.vendor_id */
  private int vendorId;
/** The javabean property equivalent of database column item_master.item_type_id */
  private int itemTypeId;
/** The javabean property equivalent of database column item_master.description */
  private String description;
/** The javabean property equivalent of database column item_master.vendor_item_no */
  private String vendorItemNo;
/** The javabean property equivalent of database column item_master.item_serial_no */
  private String itemSerialNo;
/** The javabean property equivalent of database column item_master.qty_on_hand */
  private int qtyOnHand;
/** The javabean property equivalent of database column item_master.unit_cost */
  private double unitCost;
/** The javabean property equivalent of database column item_master.markup */
  private double markup;
/** The javabean property equivalent of database column item_master.retail_price */
  private double retailPrice;
/** The javabean property equivalent of database column item_master.override_retail */
  private int overrideRetail;
/** The javabean property equivalent of database column item_master.active */
  private int active;
/** The javabean property equivalent of database column item_master.change_reason */
  private String changeReason;
/** The javabean property equivalent of database column item_master.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column item_master.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column item_master.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ItemMaster() throws SystemException {
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
 * Sets the value of member variable itemTypeId
 *
 * @author Roy Terrell.
 */
  public void setItemTypeId(int value) {
    this.itemTypeId = value;
  }
/**
 * Gets the value of member variable itemTypeId
 *
 * @author Roy Terrell.
 */
  public int getItemTypeId() {
    return this.itemTypeId;
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
 * Sets the value of member variable retailPrice
 *
 * @author Roy Terrell.
 */
  public void setRetailPrice(double value) {
    this.retailPrice = value;
  }
/**
 * Gets the value of member variable retailPrice
 *
 * @author Roy Terrell.
 */
  public double getRetailPrice() {
    return this.retailPrice;
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
 * Sets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public int getActive() {
    return this.active;
  }
/**
 * Sets the value of member variable changeReason
 *
 * @author Roy Terrell.
 */
  public void setChangeReason(String value) {
    this.changeReason = value;
  }
/**
 * Gets the value of member variable changeReason
 *
 * @author Roy Terrell.
 */
  public String getChangeReason() {
    return this.changeReason;
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
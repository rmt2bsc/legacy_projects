package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_item_master database table/view.
 *
 * @author Roy Terrell.
 */
public class VwItemMaster extends OrmBean {

/** The javabean property equivalent of database column vw_item_master.id */
  private int id;
/** The javabean property equivalent of database column vw_item_master.vendor_id */
  private int vendorId;
/** The javabean property equivalent of database column vw_item_master.item_type_id */
  private int itemTypeId;
/** The javabean property equivalent of database column vw_item_master.description */
  private String description;
/** The javabean property equivalent of database column vw_item_master.vendor_item_no */
  private String vendorItemNo;
/** The javabean property equivalent of database column vw_item_master.item_serial_no */
  private String itemSerialNo;
/** The javabean property equivalent of database column vw_item_master.qty_on_hand */
  private int qtyOnHand;
/** The javabean property equivalent of database column vw_item_master.unit_cost */
  private double unitCost;
/** The javabean property equivalent of database column vw_item_master.markup */
  private double markup;
/** The javabean property equivalent of database column vw_item_master.retail_price */
  private double retailPrice;
/** The javabean property equivalent of database column vw_item_master.override_retail */
  private int overrideRetail;
/** The javabean property equivalent of database column vw_item_master.active */
  private int active;
/** The javabean property equivalent of database column vw_item_master.item_create_date */
  private java.util.Date itemCreateDate;
/** The javabean property equivalent of database column vw_item_master.item_update_date */
  private java.util.Date itemUpdateDate;
/** The javabean property equivalent of database column vw_item_master.update_userid */
  private String updateUserid;
/** The javabean property equivalent of database column vw_item_master.item_type_description */
  private String itemTypeDescription;
/** The javabean property equivalent of database column vw_item_master.vendor_name */
  private String vendorName;
/** The javabean property equivalent of database column vw_item_master.item_status */
  private String itemStatus;
/** The javabean property equivalent of database column vw_item_master.item_status_id */
  private int itemStatusId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwItemMaster() throws SystemException {
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
 * Sets the value of member variable itemCreateDate
 *
 * @author Roy Terrell.
 */
  public void setItemCreateDate(java.util.Date value) {
    this.itemCreateDate = value;
  }
/**
 * Gets the value of member variable itemCreateDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getItemCreateDate() {
    return this.itemCreateDate;
  }
/**
 * Sets the value of member variable itemUpdateDate
 *
 * @author Roy Terrell.
 */
  public void setItemUpdateDate(java.util.Date value) {
    this.itemUpdateDate = value;
  }
/**
 * Gets the value of member variable itemUpdateDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getItemUpdateDate() {
    return this.itemUpdateDate;
  }
/**
 * Sets the value of member variable updateUserid
 *
 * @author Roy Terrell.
 */
  public void setUpdateUserid(String value) {
    this.updateUserid = value;
  }
/**
 * Gets the value of member variable updateUserid
 *
 * @author Roy Terrell.
 */
  public String getUpdateUserid() {
    return this.updateUserid;
  }
/**
 * Sets the value of member variable itemTypeDescription
 *
 * @author Roy Terrell.
 */
  public void setItemTypeDescription(String value) {
    this.itemTypeDescription = value;
  }
/**
 * Gets the value of member variable itemTypeDescription
 *
 * @author Roy Terrell.
 */
  public String getItemTypeDescription() {
    return this.itemTypeDescription;
  }
/**
 * Sets the value of member variable vendorName
 *
 * @author Roy Terrell.
 */
  public void setVendorName(String value) {
    this.vendorName = value;
  }
/**
 * Gets the value of member variable vendorName
 *
 * @author Roy Terrell.
 */
  public String getVendorName() {
    return this.vendorName;
  }
/**
 * Sets the value of member variable itemStatus
 *
 * @author Roy Terrell.
 */
  public void setItemStatus(String value) {
    this.itemStatus = value;
  }
/**
 * Gets the value of member variable itemStatus
 *
 * @author Roy Terrell.
 */
  public String getItemStatus() {
    return this.itemStatus;
  }
/**
 * Sets the value of member variable itemStatusId
 *
 * @author Roy Terrell.
 */
  public void setItemStatusId(int value) {
    this.itemStatusId = value;
  }
/**
 * Gets the value of member variable itemStatusId
 *
 * @author Roy Terrell.
 */
  public int getItemStatusId() {
    return this.itemStatusId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
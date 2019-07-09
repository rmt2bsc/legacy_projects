package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the item_master database table/view.
 *
 * @author auto generated.
 */
public class ItemMaster extends OrmBean {




	// Property name constants that belong to respective DataSource, ItemMasterView

/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, ItemTypeId, of respective DataSource view. */
  public static final String PROP_ITEMTYPEID = "ItemTypeId";
/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, VendorItemNo, of respective DataSource view. */
  public static final String PROP_VENDORITEMNO = "VendorItemNo";
/** The property name constant equivalent to property, ItemSerialNo, of respective DataSource view. */
  public static final String PROP_ITEMSERIALNO = "ItemSerialNo";
/** The property name constant equivalent to property, QtyOnHand, of respective DataSource view. */
  public static final String PROP_QTYONHAND = "QtyOnHand";
/** The property name constant equivalent to property, UnitCost, of respective DataSource view. */
  public static final String PROP_UNITCOST = "UnitCost";
/** The property name constant equivalent to property, Markup, of respective DataSource view. */
  public static final String PROP_MARKUP = "Markup";
/** The property name constant equivalent to property, RetailPrice, of respective DataSource view. */
  public static final String PROP_RETAILPRICE = "RetailPrice";
/** The property name constant equivalent to property, OverrideRetail, of respective DataSource view. */
  public static final String PROP_OVERRIDERETAIL = "OverrideRetail";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
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



	/** The javabean property equivalent of database column item_master.item_id */
  private int itemId;
/** The javabean property equivalent of database column item_master.item_type_id */
  private int itemTypeId;
/** The javabean property equivalent of database column item_master.creditor_id */
  private int creditorId;
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
/** The javabean property equivalent of database column item_master.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column item_master.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column item_master.user_id */
  private String userId;
/** The javabean property equivalent of database column item_master.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column item_master.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ItemMaster() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable itemId
 *
 * @author auto generated.
 */
  public void setItemId(int value) {
    this.itemId = value;
  }
/**
 * Gets the value of member variable itemId
 *
 * @author atuo generated.
 */
  public int getItemId() {
    return this.itemId;
  }
/**
 * Sets the value of member variable itemTypeId
 *
 * @author auto generated.
 */
  public void setItemTypeId(int value) {
    this.itemTypeId = value;
  }
/**
 * Gets the value of member variable itemTypeId
 *
 * @author atuo generated.
 */
  public int getItemTypeId() {
    return this.itemTypeId;
  }
/**
 * Sets the value of member variable creditorId
 *
 * @author auto generated.
 */
  public void setCreditorId(int value) {
    this.creditorId = value;
  }
/**
 * Gets the value of member variable creditorId
 *
 * @author atuo generated.
 */
  public int getCreditorId() {
    return this.creditorId;
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
 * Sets the value of member variable vendorItemNo
 *
 * @author auto generated.
 */
  public void setVendorItemNo(String value) {
    this.vendorItemNo = value;
  }
/**
 * Gets the value of member variable vendorItemNo
 *
 * @author atuo generated.
 */
  public String getVendorItemNo() {
    return this.vendorItemNo;
  }
/**
 * Sets the value of member variable itemSerialNo
 *
 * @author auto generated.
 */
  public void setItemSerialNo(String value) {
    this.itemSerialNo = value;
  }
/**
 * Gets the value of member variable itemSerialNo
 *
 * @author atuo generated.
 */
  public String getItemSerialNo() {
    return this.itemSerialNo;
  }
/**
 * Sets the value of member variable qtyOnHand
 *
 * @author auto generated.
 */
  public void setQtyOnHand(int value) {
    this.qtyOnHand = value;
  }
/**
 * Gets the value of member variable qtyOnHand
 *
 * @author atuo generated.
 */
  public int getQtyOnHand() {
    return this.qtyOnHand;
  }
/**
 * Sets the value of member variable unitCost
 *
 * @author auto generated.
 */
  public void setUnitCost(double value) {
    this.unitCost = value;
  }
/**
 * Gets the value of member variable unitCost
 *
 * @author atuo generated.
 */
  public double getUnitCost() {
    return this.unitCost;
  }
/**
 * Sets the value of member variable markup
 *
 * @author auto generated.
 */
  public void setMarkup(double value) {
    this.markup = value;
  }
/**
 * Gets the value of member variable markup
 *
 * @author atuo generated.
 */
  public double getMarkup() {
    return this.markup;
  }
/**
 * Sets the value of member variable retailPrice
 *
 * @author auto generated.
 */
  public void setRetailPrice(double value) {
    this.retailPrice = value;
  }
/**
 * Gets the value of member variable retailPrice
 *
 * @author atuo generated.
 */
  public double getRetailPrice() {
    return this.retailPrice;
  }
/**
 * Sets the value of member variable overrideRetail
 *
 * @author auto generated.
 */
  public void setOverrideRetail(int value) {
    this.overrideRetail = value;
  }
/**
 * Gets the value of member variable overrideRetail
 *
 * @author atuo generated.
 */
  public int getOverrideRetail() {
    return this.overrideRetail;
  }
/**
 * Sets the value of member variable active
 *
 * @author auto generated.
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 *
 * @author atuo generated.
 */
  public int getActive() {
    return this.active;
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
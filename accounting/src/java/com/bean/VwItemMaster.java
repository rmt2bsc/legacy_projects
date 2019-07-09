package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_item_master database table/view.
 *
 * @author auto generated.
 */
public class VwItemMaster extends OrmBean {




	// Property name constants that belong to respective DataSource, VwItemMasterView

/** The property name constant equivalent to property, UpdateUserid, of respective DataSource view. */
  public static final String PROP_UPDATEUSERID = "UpdateUserid";
/** The property name constant equivalent to property, ItemTypeDescription, of respective DataSource view. */
  public static final String PROP_ITEMTYPEDESCRIPTION = "ItemTypeDescription";
/** The property name constant equivalent to property, ItemStatus, of respective DataSource view. */
  public static final String PROP_ITEMSTATUS = "ItemStatus";
/** The property name constant equivalent to property, ItemStatusId, of respective DataSource view. */
  public static final String PROP_ITEMSTATUSID = "ItemStatusId";
/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, VendorId, of respective DataSource view. */
  public static final String PROP_VENDORID = "VendorId";
/** The property name constant equivalent to property, ItemTypeId, of respective DataSource view. */
  public static final String PROP_ITEMTYPEID = "ItemTypeId";
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
/** The property name constant equivalent to property, ItemCreateDate, of respective DataSource view. */
  public static final String PROP_ITEMCREATEDATE = "ItemCreateDate";
/** The property name constant equivalent to property, ItemUpdateDate, of respective DataSource view. */
  public static final String PROP_ITEMUPDATEDATE = "ItemUpdateDate";



	/** The javabean property equivalent of database column vw_item_master.update_userid */
  private String updateUserid;
/** The javabean property equivalent of database column vw_item_master.item_type_description */
  private String itemTypeDescription;
/** The javabean property equivalent of database column vw_item_master.item_status */
  private String itemStatus;
/** The javabean property equivalent of database column vw_item_master.item_status_id */
  private int itemStatusId;
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



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwItemMaster() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable updateUserid
 *
 * @author auto generated.
 */
  public void setUpdateUserid(String value) {
    this.updateUserid = value;
  }
/**
 * Gets the value of member variable updateUserid
 *
 * @author atuo generated.
 */
  public String getUpdateUserid() {
    return this.updateUserid;
  }
/**
 * Sets the value of member variable itemTypeDescription
 *
 * @author auto generated.
 */
  public void setItemTypeDescription(String value) {
    this.itemTypeDescription = value;
  }
/**
 * Gets the value of member variable itemTypeDescription
 *
 * @author atuo generated.
 */
  public String getItemTypeDescription() {
    return this.itemTypeDescription;
  }
/**
 * Sets the value of member variable itemStatus
 *
 * @author auto generated.
 */
  public void setItemStatus(String value) {
    this.itemStatus = value;
  }
/**
 * Gets the value of member variable itemStatus
 *
 * @author atuo generated.
 */
  public String getItemStatus() {
    return this.itemStatus;
  }
/**
 * Sets the value of member variable itemStatusId
 *
 * @author auto generated.
 */
  public void setItemStatusId(int value) {
    this.itemStatusId = value;
  }
/**
 * Gets the value of member variable itemStatusId
 *
 * @author atuo generated.
 */
  public int getItemStatusId() {
    return this.itemStatusId;
  }
/**
 * Sets the value of member variable id
 *
 * @author auto generated.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author atuo generated.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable vendorId
 *
 * @author auto generated.
 */
  public void setVendorId(int value) {
    this.vendorId = value;
  }
/**
 * Gets the value of member variable vendorId
 *
 * @author atuo generated.
 */
  public int getVendorId() {
    return this.vendorId;
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
 * Sets the value of member variable itemCreateDate
 *
 * @author auto generated.
 */
  public void setItemCreateDate(java.util.Date value) {
    this.itemCreateDate = value;
  }
/**
 * Gets the value of member variable itemCreateDate
 *
 * @author atuo generated.
 */
  public java.util.Date getItemCreateDate() {
    return this.itemCreateDate;
  }
/**
 * Sets the value of member variable itemUpdateDate
 *
 * @author auto generated.
 */
  public void setItemUpdateDate(java.util.Date value) {
    this.itemUpdateDate = value;
  }
/**
 * Gets the value of member variable itemUpdateDate
 *
 * @author atuo generated.
 */
  public java.util.Date getItemUpdateDate() {
    return this.itemUpdateDate;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
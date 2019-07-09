package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_vendor_item_purchase_order_item database table/view.
 *
 * @author auto generated.
 */
public class VwVendorItemPurchaseOrderItem extends OrmBean {




	// Property name constants that belong to respective DataSource, VwVendorItemPurchaseOrderItemView

/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, QtyOnHand, of respective DataSource view. */
  public static final String PROP_QTYONHAND = "QtyOnHand";
/** The property name constant equivalent to property, Markup, of respective DataSource view. */
  public static final String PROP_MARKUP = "Markup";
/** The property name constant equivalent to property, OverrideRetail, of respective DataSource view. */
  public static final String PROP_OVERRIDERETAIL = "OverrideRetail";
/** The property name constant equivalent to property, PoId, of respective DataSource view. */
  public static final String PROP_POID = "PoId";
/** The property name constant equivalent to property, QtyOrderd, of respective DataSource view. */
  public static final String PROP_QTYORDERD = "QtyOrderd";
/** The property name constant equivalent to property, ActualUnitCost, of respective DataSource view. */
  public static final String PROP_ACTUALUNITCOST = "ActualUnitCost";
/** The property name constant equivalent to property, QtyReceived, of respective DataSource view. */
  public static final String PROP_QTYRECEIVED = "QtyReceived";
/** The property name constant equivalent to property, QtyReturned, of respective DataSource view. */
  public static final String PROP_QTYRETURNED = "QtyReturned";
/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, VendorId, of respective DataSource view. */
  public static final String PROP_VENDORID = "VendorId";
/** The property name constant equivalent to property, VendorItemNo, of respective DataSource view. */
  public static final String PROP_VENDORITEMNO = "VendorItemNo";
/** The property name constant equivalent to property, ItemSerialNo, of respective DataSource view. */
  public static final String PROP_ITEMSERIALNO = "ItemSerialNo";
/** The property name constant equivalent to property, UnitCost, of respective DataSource view. */
  public static final String PROP_UNITCOST = "UnitCost";



	/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.description */
  private String description;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_on_hand */
  private int qtyOnHand;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.markup */
  private double markup;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.override_retail */
  private int overrideRetail;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.po_id */
  private int poId;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_orderd */
  private int qtyOrderd;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.actual_unit_cost */
  private double actualUnitCost;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_received */
  private int qtyReceived;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.qty_returned */
  private int qtyReturned;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.item_id */
  private int itemId;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.vendor_id */
  private int vendorId;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.vendor_item_no */
  private String vendorItemNo;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.item_serial_no */
  private String itemSerialNo;
/** The javabean property equivalent of database column vw_vendor_item_purchase_order_item.unit_cost */
  private double unitCost;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwVendorItemPurchaseOrderItem() throws SystemException {
	super();
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
 * Sets the value of member variable poId
 *
 * @author auto generated.
 */
  public void setPoId(int value) {
    this.poId = value;
  }
/**
 * Gets the value of member variable poId
 *
 * @author atuo generated.
 */
  public int getPoId() {
    return this.poId;
  }
/**
 * Sets the value of member variable qtyOrderd
 *
 * @author auto generated.
 */
  public void setQtyOrderd(int value) {
    this.qtyOrderd = value;
  }
/**
 * Gets the value of member variable qtyOrderd
 *
 * @author atuo generated.
 */
  public int getQtyOrderd() {
    return this.qtyOrderd;
  }
/**
 * Sets the value of member variable actualUnitCost
 *
 * @author auto generated.
 */
  public void setActualUnitCost(double value) {
    this.actualUnitCost = value;
  }
/**
 * Gets the value of member variable actualUnitCost
 *
 * @author atuo generated.
 */
  public double getActualUnitCost() {
    return this.actualUnitCost;
  }
/**
 * Sets the value of member variable qtyReceived
 *
 * @author auto generated.
 */
  public void setQtyReceived(int value) {
    this.qtyReceived = value;
  }
/**
 * Gets the value of member variable qtyReceived
 *
 * @author atuo generated.
 */
  public int getQtyReceived() {
    return this.qtyReceived;
  }
/**
 * Sets the value of member variable qtyReturned
 *
 * @author auto generated.
 */
  public void setQtyReturned(int value) {
    this.qtyReturned = value;
  }
/**
 * Gets the value of member variable qtyReturned
 *
 * @author atuo generated.
 */
  public int getQtyReturned() {
    return this.qtyReturned;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
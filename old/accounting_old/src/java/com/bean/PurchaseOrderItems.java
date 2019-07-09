package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_items database table/view.
 *
 * @author auto generated.
 */
public class PurchaseOrderItems extends OrmBean {




	// Property name constants that belong to respective DataSource, PurchaseOrderItemsView

/** The property name constant equivalent to property, PoItemId, of respective DataSource view. */
  public static final String PROP_POITEMID = "PoItemId";
/** The property name constant equivalent to property, PoId, of respective DataSource view. */
  public static final String PROP_POID = "PoId";
/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, UnitCost, of respective DataSource view. */
  public static final String PROP_UNITCOST = "UnitCost";
/** The property name constant equivalent to property, Qty, of respective DataSource view. */
  public static final String PROP_QTY = "Qty";
/** The property name constant equivalent to property, QtyRcvd, of respective DataSource view. */
  public static final String PROP_QTYRCVD = "QtyRcvd";
/** The property name constant equivalent to property, QtyRtn, of respective DataSource view. */
  public static final String PROP_QTYRTN = "QtyRtn";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column purchase_order_items.po_item_id */
  private int poItemId;
/** The javabean property equivalent of database column purchase_order_items.po_id */
  private int poId;
/** The javabean property equivalent of database column purchase_order_items.item_id */
  private int itemId;
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
 * @author auto generated.
 */
  public PurchaseOrderItems() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable poItemId
 *
 * @author auto generated.
 */
  public void setPoItemId(int value) {
    this.poItemId = value;
  }
/**
 * Gets the value of member variable poItemId
 *
 * @author atuo generated.
 */
  public int getPoItemId() {
    return this.poItemId;
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
 * Sets the value of member variable qty
 *
 * @author auto generated.
 */
  public void setQty(int value) {
    this.qty = value;
  }
/**
 * Gets the value of member variable qty
 *
 * @author atuo generated.
 */
  public int getQty() {
    return this.qty;
  }
/**
 * Sets the value of member variable qtyRcvd
 *
 * @author auto generated.
 */
  public void setQtyRcvd(int value) {
    this.qtyRcvd = value;
  }
/**
 * Gets the value of member variable qtyRcvd
 *
 * @author atuo generated.
 */
  public int getQtyRcvd() {
    return this.qtyRcvd;
  }
/**
 * Sets the value of member variable qtyRtn
 *
 * @author auto generated.
 */
  public void setQtyRtn(int value) {
    this.qtyRtn = value;
  }
/**
 * Gets the value of member variable qtyRtn
 *
 * @author atuo generated.
 */
  public int getQtyRtn() {
    return this.qtyRtn;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
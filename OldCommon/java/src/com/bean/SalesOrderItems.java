package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the sales_order_items database table/view.
 *
 * @author Roy Terrell.
 */
public class SalesOrderItems extends OrmBean {

/** The javabean property equivalent of database column sales_order_items.id */
  private int id;
/** The javabean property equivalent of database column sales_order_items.sales_order_id */
  private int salesOrderId;
/** The javabean property equivalent of database column sales_order_items.item_master_id */
  private int itemMasterId;
/** The javabean property equivalent of database column sales_order_items.item_name_override */
  private String itemNameOverride;
/** The javabean property equivalent of database column sales_order_items.order_qty */
  private double orderQty;
/** The javabean property equivalent of database column sales_order_items.init_unit_cost */
  private double initUnitCost;
/** The javabean property equivalent of database column sales_order_items.init_markup */
  private double initMarkup;
/** The javabean property equivalent of database column sales_order_items.back_order_qty */
  private double backOrderQty;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public SalesOrderItems() throws SystemException {
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
 * Sets the value of member variable salesOrderId
 *
 * @author Roy Terrell.
 */
  public void setSalesOrderId(int value) {
    this.salesOrderId = value;
  }
/**
 * Gets the value of member variable salesOrderId
 *
 * @author Roy Terrell.
 */
  public int getSalesOrderId() {
    return this.salesOrderId;
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
 * Sets the value of member variable itemNameOverride
 *
 * @author Roy Terrell.
 */
  public void setItemNameOverride(String value) {
    this.itemNameOverride = value;
  }
/**
 * Gets the value of member variable itemNameOverride
 *
 * @author Roy Terrell.
 */
  public String getItemNameOverride() {
    return this.itemNameOverride;
  }
/**
 * Sets the value of member variable orderQty
 *
 * @author Roy Terrell.
 */
  public void setOrderQty(double value) {
    this.orderQty = value;
  }
/**
 * Gets the value of member variable orderQty
 *
 * @author Roy Terrell.
 */
  public double getOrderQty() {
    return this.orderQty;
  }
/**
 * Sets the value of member variable initUnitCost
 *
 * @author Roy Terrell.
 */
  public void setInitUnitCost(double value) {
    this.initUnitCost = value;
  }
/**
 * Gets the value of member variable initUnitCost
 *
 * @author Roy Terrell.
 */
  public double getInitUnitCost() {
    return this.initUnitCost;
  }
/**
 * Sets the value of member variable initMarkup
 *
 * @author Roy Terrell.
 */
  public void setInitMarkup(double value) {
    this.initMarkup = value;
  }
/**
 * Gets the value of member variable initMarkup
 *
 * @author Roy Terrell.
 */
  public double getInitMarkup() {
    return this.initMarkup;
  }
/**
 * Sets the value of member variable backOrderQty
 *
 * @author Roy Terrell.
 */
  public void setBackOrderQty(double value) {
    this.backOrderQty = value;
  }
/**
 * Gets the value of member variable backOrderQty
 *
 * @author Roy Terrell.
 */
  public double getBackOrderQty() {
    return this.backOrderQty;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
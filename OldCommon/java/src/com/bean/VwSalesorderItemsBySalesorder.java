package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_salesorder_items_by_salesorder database table/view.
 *
 * @author Roy Terrell.
 */
public class VwSalesorderItemsBySalesorder extends OrmBean {

/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.sales_order_item_id */
  private int salesOrderItemId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.sales_order_id */
  private int salesOrderId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.item_master_id */
  private int itemMasterId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.vendor_id */
  private int vendorId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.item_type_id */
  private int itemTypeId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.item_name */
  private String itemName;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.vendor_item_no */
  private String vendorItemNo;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.item_serial_no */
  private String itemSerialNo;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.qty_on_hand */
  private int qtyOnHand;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.unit_cost */
  private double unitCost;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.markup */
  private double markup;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.retail_price */
  private double retailPrice;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.item_type_descr */
  private String itemTypeDescr;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.order_qty */
  private double orderQty;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.back_order_qty */
  private double backOrderQty;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.init_unit_cost */
  private double initUnitCost;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.init_markup */
  private double initMarkup;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.customer_id */
  private int customerId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.invoiced */
  private int invoiced;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.person_id */
  private int personId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.business_id */
  private int businessId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.customer_name */
  private String customerName;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwSalesorderItemsBySalesorder() throws SystemException {
  }
/**
 * Sets the value of member variable salesOrderItemId
 *
 * @author Roy Terrell.
 */
  public void setSalesOrderItemId(int value) {
    this.salesOrderItemId = value;
  }
/**
 * Gets the value of member variable salesOrderItemId
 *
 * @author Roy Terrell.
 */
  public int getSalesOrderItemId() {
    return this.salesOrderItemId;
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
 * Sets the value of member variable itemName
 *
 * @author Roy Terrell.
 */
  public void setItemName(String value) {
    this.itemName = value;
  }
/**
 * Gets the value of member variable itemName
 *
 * @author Roy Terrell.
 */
  public String getItemName() {
    return this.itemName;
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
 * Sets the value of member variable itemTypeDescr
 *
 * @author Roy Terrell.
 */
  public void setItemTypeDescr(String value) {
    this.itemTypeDescr = value;
  }
/**
 * Gets the value of member variable itemTypeDescr
 *
 * @author Roy Terrell.
 */
  public String getItemTypeDescr() {
    return this.itemTypeDescr;
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
 * Sets the value of member variable customerId
 *
 * @author Roy Terrell.
 */
  public void setCustomerId(int value) {
    this.customerId = value;
  }
/**
 * Gets the value of member variable customerId
 *
 * @author Roy Terrell.
 */
  public int getCustomerId() {
    return this.customerId;
  }
/**
 * Sets the value of member variable invoiced
 *
 * @author Roy Terrell.
 */
  public void setInvoiced(int value) {
    this.invoiced = value;
  }
/**
 * Gets the value of member variable invoiced
 *
 * @author Roy Terrell.
 */
  public int getInvoiced() {
    return this.invoiced;
  }
/**
 * Sets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public int getPersonId() {
    return this.personId;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Sets the value of member variable customerName
 *
 * @author Roy Terrell.
 */
  public void setCustomerName(String value) {
    this.customerName = value;
  }
/**
 * Gets the value of member variable customerName
 *
 * @author Roy Terrell.
 */
  public String getCustomerName() {
    return this.customerName;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
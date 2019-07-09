package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_salesorder_items_by_salesorder database table/view.
 *
 * @author auto generated.
 */
public class VwSalesorderItemsBySalesorder extends OrmBean {




	// Property name constants that belong to respective DataSource, VwSalesorderItemsBySalesorderView

/** The property name constant equivalent to property, SalesOrderItemId, of respective DataSource view. */
  public static final String PROP_SALESORDERITEMID = "SalesOrderItemId";
/** The property name constant equivalent to property, SoId, of respective DataSource view. */
  public static final String PROP_SOID = "SoId";
/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, ItemTypeId, of respective DataSource view. */
  public static final String PROP_ITEMTYPEID = "ItemTypeId";
/** The property name constant equivalent to property, ItemName, of respective DataSource view. */
  public static final String PROP_ITEMNAME = "ItemName";
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
/** The property name constant equivalent to property, ItemTypeDescr, of respective DataSource view. */
  public static final String PROP_ITEMTYPEDESCR = "ItemTypeDescr";
/** The property name constant equivalent to property, ItemNameOverride, of respective DataSource view. */
  public static final String PROP_ITEMNAMEOVERRIDE = "ItemNameOverride";
/** The property name constant equivalent to property, OrderQty, of respective DataSource view. */
  public static final String PROP_ORDERQTY = "OrderQty";
/** The property name constant equivalent to property, BackOrderQty, of respective DataSource view. */
  public static final String PROP_BACKORDERQTY = "BackOrderQty";
/** The property name constant equivalent to property, InitUnitCost, of respective DataSource view. */
  public static final String PROP_INITUNITCOST = "InitUnitCost";
/** The property name constant equivalent to property, InitMarkup, of respective DataSource view. */
  public static final String PROP_INITMARKUP = "InitMarkup";
/** The property name constant equivalent to property, CustomerId, of respective DataSource view. */
  public static final String PROP_CUSTOMERID = "CustomerId";
/** The property name constant equivalent to property, Invoiced, of respective DataSource view. */
  public static final String PROP_INVOICED = "Invoiced";
/** The property name constant equivalent to property, PersonId, of respective DataSource view. */
  public static final String PROP_PERSONID = "PersonId";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";



	/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.sales_order_item_id */
  private int salesOrderItemId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.so_id */
  private int soId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.item_id */
  private int itemId;
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.creditor_id */
  private int creditorId;
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
/** The javabean property equivalent of database column vw_salesorder_items_by_salesorder.item_name_override */
  private String itemNameOverride;
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



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwSalesorderItemsBySalesorder() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable salesOrderItemId
 *
 * @author auto generated.
 */
  public void setSalesOrderItemId(int value) {
    this.salesOrderItemId = value;
  }
/**
 * Gets the value of member variable salesOrderItemId
 *
 * @author atuo generated.
 */
  public int getSalesOrderItemId() {
    return this.salesOrderItemId;
  }
/**
 * Sets the value of member variable soId
 *
 * @author auto generated.
 */
  public void setSoId(int value) {
    this.soId = value;
  }
/**
 * Gets the value of member variable soId
 *
 * @author atuo generated.
 */
  public int getSoId() {
    return this.soId;
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
 * Sets the value of member variable itemName
 *
 * @author auto generated.
 */
  public void setItemName(String value) {
    this.itemName = value;
  }
/**
 * Gets the value of member variable itemName
 *
 * @author atuo generated.
 */
  public String getItemName() {
    return this.itemName;
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
 * Sets the value of member variable itemTypeDescr
 *
 * @author auto generated.
 */
  public void setItemTypeDescr(String value) {
    this.itemTypeDescr = value;
  }
/**
 * Gets the value of member variable itemTypeDescr
 *
 * @author atuo generated.
 */
  public String getItemTypeDescr() {
    return this.itemTypeDescr;
  }
/**
 * Sets the value of member variable itemNameOverride
 *
 * @author auto generated.
 */
  public void setItemNameOverride(String value) {
    this.itemNameOverride = value;
  }
/**
 * Gets the value of member variable itemNameOverride
 *
 * @author atuo generated.
 */
  public String getItemNameOverride() {
    return this.itemNameOverride;
  }
/**
 * Sets the value of member variable orderQty
 *
 * @author auto generated.
 */
  public void setOrderQty(double value) {
    this.orderQty = value;
  }
/**
 * Gets the value of member variable orderQty
 *
 * @author atuo generated.
 */
  public double getOrderQty() {
    return this.orderQty;
  }
/**
 * Sets the value of member variable backOrderQty
 *
 * @author auto generated.
 */
  public void setBackOrderQty(double value) {
    this.backOrderQty = value;
  }
/**
 * Gets the value of member variable backOrderQty
 *
 * @author atuo generated.
 */
  public double getBackOrderQty() {
    return this.backOrderQty;
  }
/**
 * Sets the value of member variable initUnitCost
 *
 * @author auto generated.
 */
  public void setInitUnitCost(double value) {
    this.initUnitCost = value;
  }
/**
 * Gets the value of member variable initUnitCost
 *
 * @author atuo generated.
 */
  public double getInitUnitCost() {
    return this.initUnitCost;
  }
/**
 * Sets the value of member variable initMarkup
 *
 * @author auto generated.
 */
  public void setInitMarkup(double value) {
    this.initMarkup = value;
  }
/**
 * Gets the value of member variable initMarkup
 *
 * @author atuo generated.
 */
  public double getInitMarkup() {
    return this.initMarkup;
  }
/**
 * Sets the value of member variable customerId
 *
 * @author auto generated.
 */
  public void setCustomerId(int value) {
    this.customerId = value;
  }
/**
 * Gets the value of member variable customerId
 *
 * @author atuo generated.
 */
  public int getCustomerId() {
    return this.customerId;
  }
/**
 * Sets the value of member variable invoiced
 *
 * @author auto generated.
 */
  public void setInvoiced(int value) {
    this.invoiced = value;
  }
/**
 * Gets the value of member variable invoiced
 *
 * @author atuo generated.
 */
  public int getInvoiced() {
    return this.invoiced;
  }
/**
 * Sets the value of member variable personId
 *
 * @author auto generated.
 */
  public void setPersonId(int value) {
    this.personId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author atuo generated.
 */
  public int getPersonId() {
    return this.personId;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author auto generated.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author atuo generated.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
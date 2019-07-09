package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vendor_items database table/view.
 *
 * @author auto generated.
 */
public class VendorItems extends OrmBean {




	// Property name constants that belong to respective DataSource, VendorItemsView

/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, VendorItemNo, of respective DataSource view. */
  public static final String PROP_VENDORITEMNO = "VendorItemNo";
/** The property name constant equivalent to property, ItemSerialNo, of respective DataSource view. */
  public static final String PROP_ITEMSERIALNO = "ItemSerialNo";
/** The property name constant equivalent to property, UnitCost, of respective DataSource view. */
  public static final String PROP_UNITCOST = "UnitCost";



	/** The javabean property equivalent of database column vendor_items.item_id */
  private int itemId;
/** The javabean property equivalent of database column vendor_items.creditor_id */
  private int creditorId;
/** The javabean property equivalent of database column vendor_items.vendor_item_no */
  private String vendorItemNo;
/** The javabean property equivalent of database column vendor_items.item_serial_no */
  private String itemSerialNo;
/** The javabean property equivalent of database column vendor_items.unit_cost */
  private double unitCost;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VendorItems() throws SystemException {
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
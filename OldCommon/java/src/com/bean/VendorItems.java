package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vendor_items database table/view.
 *
 * @author Roy Terrell.
 */
public class VendorItems extends OrmBean {

/** The javabean property equivalent of database column vendor_items.item_master_id */
  private int itemMasterId;
/** The javabean property equivalent of database column vendor_items.vendor_id */
  private int vendorId;
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
 * @author Roy Terrell.
 */
  public VendorItems() throws SystemException {
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
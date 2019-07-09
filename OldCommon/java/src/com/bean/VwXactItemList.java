package com.bean;


import java.util.Date;
import com.bean.RMT2BaseBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_item_list database table/view.
 *
 * @author Roy Terrell.
 */
public class VwXactItemList extends RMT2BaseBean {

/** The javabean property equivalent of database column vw_xact_item_list.item_activity_id */
  private int itemActivityId;
/** The javabean property equivalent of database column vw_xact_item_list.xact_type_item_id */
  private int xactTypeItemId;
/** The javabean property equivalent of database column vw_xact_item_list.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_xact_item_list.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_xact_item_list.amount */
  private double amount;
/** The javabean property equivalent of database column vw_xact_item_list.name */
  private String name;
/** The javabean property equivalent of database column vw_xact_item_list.item_xact_reason */
  private String itemXactReason;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwXactItemList() throws SystemException {
  }
/**
 * Sets the value of member variable itemActivityId
 *
 * @author Roy Terrell.
 */
  public void setItemActivityId(int value) {
    this.itemActivityId = value;
  }
/**
 * Gets the value of member variable itemActivityId
 *
 * @author Roy Terrell.
 */
  public int getItemActivityId() {
    return this.itemActivityId;
  }
/**
 * Sets the value of member variable xactTypeItemId
 *
 * @author Roy Terrell.
 */
  public void setXactTypeItemId(int value) {
    this.xactTypeItemId = value;
  }
/**
 * Gets the value of member variable xactTypeItemId
 *
 * @author Roy Terrell.
 */
  public int getXactTypeItemId() {
    return this.xactTypeItemId;
  }
/**
 * Sets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author Roy Terrell.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable xactTypeId
 *
 * @author Roy Terrell.
 */
  public void setXactTypeId(int value) {
    this.xactTypeId = value;
  }
/**
 * Gets the value of member variable xactTypeId
 *
 * @author Roy Terrell.
 */
  public int getXactTypeId() {
    return this.xactTypeId;
  }
/**
 * Sets the value of member variable amount
 *
 * @author Roy Terrell.
 */
  public void setAmount(double value) {
    this.amount = value;
  }
/**
 * Gets the value of member variable amount
 *
 * @author Roy Terrell.
 */
  public double getAmount() {
    return this.amount;
  }
/**
 * Sets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable itemXactReason
 *
 * @author Roy Terrell.
 */
  public void setItemXactReason(String value) {
    this.itemXactReason = value;
  }
/**
 * Gets the value of member variable itemXactReason
 *
 * @author Roy Terrell.
 */
  public String getItemXactReason() {
    return this.itemXactReason;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_return database table/view.
 *
 * @author Roy Terrell.
 */
public class PurchaseOrderReturn extends OrmBean {

/** The javabean property equivalent of database column purchase_order_return.id */
  private int id;
/** The javabean property equivalent of database column purchase_order_return.purchase_order_id */
  private int purchaseOrderId;
/** The javabean property equivalent of database column purchase_order_return.xact_id */
  private int xactId;
/** The javabean property equivalent of database column purchase_order_return.reason */
  private String reason;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public PurchaseOrderReturn() throws SystemException {
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
 * Sets the value of member variable purchaseOrderId
 *
 * @author Roy Terrell.
 */
  public void setPurchaseOrderId(int value) {
    this.purchaseOrderId = value;
  }
/**
 * Gets the value of member variable purchaseOrderId
 *
 * @author Roy Terrell.
 */
  public int getPurchaseOrderId() {
    return this.purchaseOrderId;
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
 * Sets the value of member variable reason
 *
 * @author Roy Terrell.
 */
  public void setReason(String value) {
    this.reason = value;
  }
/**
 * Gets the value of member variable reason
 *
 * @author Roy Terrell.
 */
  public String getReason() {
    return this.reason;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
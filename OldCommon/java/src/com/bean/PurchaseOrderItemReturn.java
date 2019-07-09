package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_item_return database table/view.
 *
 * @author Roy Terrell.
 */
public class PurchaseOrderItemReturn extends OrmBean {

/** The javabean property equivalent of database column purchase_order_item_return.id */
  private int id;
/** The javabean property equivalent of database column purchase_order_item_return.purchase_order_return_id */
  private int purchaseOrderReturnId;
/** The javabean property equivalent of database column purchase_order_item_return.purchase_order_item_id */
  private int purchaseOrderItemId;
/** The javabean property equivalent of database column purchase_order_item_return.qty_rtn */
  private int qtyRtn;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public PurchaseOrderItemReturn() throws SystemException {
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
 * Sets the value of member variable purchaseOrderReturnId
 *
 * @author Roy Terrell.
 */
  public void setPurchaseOrderReturnId(int value) {
    this.purchaseOrderReturnId = value;
  }
/**
 * Gets the value of member variable purchaseOrderReturnId
 *
 * @author Roy Terrell.
 */
  public int getPurchaseOrderReturnId() {
    return this.purchaseOrderReturnId;
  }
/**
 * Sets the value of member variable purchaseOrderItemId
 *
 * @author Roy Terrell.
 */
  public void setPurchaseOrderItemId(int value) {
    this.purchaseOrderItemId = value;
  }
/**
 * Gets the value of member variable purchaseOrderItemId
 *
 * @author Roy Terrell.
 */
  public int getPurchaseOrderItemId() {
    return this.purchaseOrderItemId;
  }
/**
 * Sets the value of member variable qtyRtn
 *
 * @author Roy Terrell.
 */
  public void setQtyRtn(int value) {
    this.qtyRtn = value;
  }
/**
 * Gets the value of member variable qtyRtn
 *
 * @author Roy Terrell.
 */
  public int getQtyRtn() {
    return this.qtyRtn;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
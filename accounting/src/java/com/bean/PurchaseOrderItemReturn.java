package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_item_return database table/view.
 *
 * @author auto generated.
 */
public class PurchaseOrderItemReturn extends OrmBean {




	// Property name constants that belong to respective DataSource, PurchaseOrderItemReturnView

/** The property name constant equivalent to property, PoItemReturnId, of respective DataSource view. */
  public static final String PROP_POITEMRETURNID = "PoItemReturnId";
/** The property name constant equivalent to property, PoReturnId, of respective DataSource view. */
  public static final String PROP_PORETURNID = "PoReturnId";
/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, QtyRtn, of respective DataSource view. */
  public static final String PROP_QTYRTN = "QtyRtn";



	/** The javabean property equivalent of database column purchase_order_item_return.po_item_return_id */
  private int poItemReturnId;
/** The javabean property equivalent of database column purchase_order_item_return.po_return_id */
  private int poReturnId;
/** The javabean property equivalent of database column purchase_order_item_return.item_id */
  private int itemId;
/** The javabean property equivalent of database column purchase_order_item_return.qty_rtn */
  private int qtyRtn;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public PurchaseOrderItemReturn() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable poItemReturnId
 *
 * @author auto generated.
 */
  public void setPoItemReturnId(int value) {
    this.poItemReturnId = value;
  }
/**
 * Gets the value of member variable poItemReturnId
 *
 * @author atuo generated.
 */
  public int getPoItemReturnId() {
    return this.poItemReturnId;
  }
/**
 * Sets the value of member variable poReturnId
 *
 * @author auto generated.
 */
  public void setPoReturnId(int value) {
    this.poReturnId = value;
  }
/**
 * Gets the value of member variable poReturnId
 *
 * @author atuo generated.
 */
  public int getPoReturnId() {
    return this.poReturnId;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
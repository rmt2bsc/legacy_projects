package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_item_associations database table/view.
 *
 * @author auto generated.
 */
public class VwItemAssociations extends OrmBean {




	// Property name constants that belong to respective DataSource, VwItemAssociationsView

/** The property name constant equivalent to property, AssocId, of respective DataSource view. */
  public static final String PROP_ASSOCID = "AssocId";
/** The property name constant equivalent to property, AssocItemId, of respective DataSource view. */
  public static final String PROP_ASSOCITEMID = "AssocItemId";
/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, ItemCost, of respective DataSource view. */
  public static final String PROP_ITEMCOST = "ItemCost";
/** The property name constant equivalent to property, OrderQty, of respective DataSource view. */
  public static final String PROP_ORDERQTY = "OrderQty";
/** The property name constant equivalent to property, AssocType, of respective DataSource view. */
  public static final String PROP_ASSOCTYPE = "AssocType";



	/** The javabean property equivalent of database column vw_item_associations.assoc_id */
  private int assocId;
/** The javabean property equivalent of database column vw_item_associations.assoc_item_id */
  private int assocItemId;
/** The javabean property equivalent of database column vw_item_associations.item_id */
  private int itemId;
/** The javabean property equivalent of database column vw_item_associations.item_cost */
  private double itemCost;
/** The javabean property equivalent of database column vw_item_associations.order_qty */
  private double orderQty;
/** The javabean property equivalent of database column vw_item_associations.assoc_type */
  private char assocType;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwItemAssociations() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable assocId
 *
 * @author auto generated.
 */
  public void setAssocId(int value) {
    this.assocId = value;
  }
/**
 * Gets the value of member variable assocId
 *
 * @author atuo generated.
 */
  public int getAssocId() {
    return this.assocId;
  }
/**
 * Sets the value of member variable assocItemId
 *
 * @author auto generated.
 */
  public void setAssocItemId(int value) {
    this.assocItemId = value;
  }
/**
 * Gets the value of member variable assocItemId
 *
 * @author atuo generated.
 */
  public int getAssocItemId() {
    return this.assocItemId;
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
 * Sets the value of member variable itemCost
 *
 * @author auto generated.
 */
  public void setItemCost(double value) {
    this.itemCost = value;
  }
/**
 * Gets the value of member variable itemCost
 *
 * @author atuo generated.
 */
  public double getItemCost() {
    return this.itemCost;
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
 * Sets the value of member variable assocType
 *
 * @author auto generated.
 */
  public void setAssocType(char value) {
    this.assocType = value;
  }
/**
 * Gets the value of member variable assocType
 *
 * @author atuo generated.
 */
  public char getAssocType() {
    return this.assocType;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
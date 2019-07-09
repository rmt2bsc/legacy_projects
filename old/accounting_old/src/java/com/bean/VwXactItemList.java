package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_item_list database table/view.
 *
 * @author auto generated.
 */
public class VwXactItemList extends OrmBean {




	// Property name constants that belong to respective DataSource, VwXactItemListView

/** The property name constant equivalent to property, ItemActivityId, of respective DataSource view. */
  public static final String PROP_ITEMACTIVITYID = "ItemActivityId";
/** The property name constant equivalent to property, XactTypeItemId, of respective DataSource view. */
  public static final String PROP_XACTTYPEITEMID = "XactTypeItemId";
/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, Amount, of respective DataSource view. */
  public static final String PROP_AMOUNT = "Amount";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, ItemXactReason, of respective DataSource view. */
  public static final String PROP_ITEMXACTREASON = "ItemXactReason";



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
 * @author auto generated.
 */
  public VwXactItemList() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable itemActivityId
 *
 * @author auto generated.
 */
  public void setItemActivityId(int value) {
    this.itemActivityId = value;
  }
/**
 * Gets the value of member variable itemActivityId
 *
 * @author atuo generated.
 */
  public int getItemActivityId() {
    return this.itemActivityId;
  }
/**
 * Sets the value of member variable xactTypeItemId
 *
 * @author auto generated.
 */
  public void setXactTypeItemId(int value) {
    this.xactTypeItemId = value;
  }
/**
 * Gets the value of member variable xactTypeItemId
 *
 * @author atuo generated.
 */
  public int getXactTypeItemId() {
    return this.xactTypeItemId;
  }
/**
 * Sets the value of member variable xactId
 *
 * @author auto generated.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author atuo generated.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable xactTypeId
 *
 * @author auto generated.
 */
  public void setXactTypeId(int value) {
    this.xactTypeId = value;
  }
/**
 * Gets the value of member variable xactTypeId
 *
 * @author atuo generated.
 */
  public int getXactTypeId() {
    return this.xactTypeId;
  }
/**
 * Sets the value of member variable amount
 *
 * @author auto generated.
 */
  public void setAmount(double value) {
    this.amount = value;
  }
/**
 * Gets the value of member variable amount
 *
 * @author atuo generated.
 */
  public double getAmount() {
    return this.amount;
  }
/**
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable itemXactReason
 *
 * @author auto generated.
 */
  public void setItemXactReason(String value) {
    this.itemXactReason = value;
  }
/**
 * Gets the value of member variable itemXactReason
 *
 * @author atuo generated.
 */
  public String getItemXactReason() {
    return this.itemXactReason;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
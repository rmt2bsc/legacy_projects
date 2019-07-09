package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_type_item_activity database table/view.
 *
 * @author auto generated.
 */
public class VwXactTypeItemActivity extends OrmBean {




	// Property name constants that belong to respective DataSource, VwXactTypeItemActivityView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, XactTypeItemId, of respective DataSource view. */
  public static final String PROP_XACTTYPEITEMID = "XactTypeItemId";
/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, ItemAmount, of respective DataSource view. */
  public static final String PROP_ITEMAMOUNT = "ItemAmount";
/** The property name constant equivalent to property, ItemName, of respective DataSource view. */
  public static final String PROP_ITEMNAME = "ItemName";
/** The property name constant equivalent to property, XactDate, of respective DataSource view. */
  public static final String PROP_XACTDATE = "XactDate";
/** The property name constant equivalent to property, XactAmount, of respective DataSource view. */
  public static final String PROP_XACTAMOUNT = "XactAmount";
/** The property name constant equivalent to property, Reason, of respective DataSource view. */
  public static final String PROP_REASON = "Reason";
/** The property name constant equivalent to property, ConfirmNo, of respective DataSource view. */
  public static final String PROP_CONFIRMNO = "ConfirmNo";
/** The property name constant equivalent to property, XactTypeId, of respective DataSource view. */
  public static final String PROP_XACTTYPEID = "XactTypeId";
/** The property name constant equivalent to property, XactTypeItemName, of respective DataSource view. */
  public static final String PROP_XACTTYPEITEMNAME = "XactTypeItemName";
/** The property name constant equivalent to property, XactTypeXactCode, of respective DataSource view. */
  public static final String PROP_XACTTYPEXACTCODE = "XactTypeXactCode";
/** The property name constant equivalent to property, XactTypeDescription, of respective DataSource view. */
  public static final String PROP_XACTTYPEDESCRIPTION = "XactTypeDescription";
/** The property name constant equivalent to property, XactCategoryId, of respective DataSource view. */
  public static final String PROP_XACTCATEGORYID = "XactCategoryId";
/** The property name constant equivalent to property, XactCategoryDescription, of respective DataSource view. */
  public static final String PROP_XACTCATEGORYDESCRIPTION = "XactCategoryDescription";
/** The property name constant equivalent to property, XactCategoryCode, of respective DataSource view. */
  public static final String PROP_XACTCATEGORYCODE = "XactCategoryCode";



	/** The javabean property equivalent of database column vw_xact_type_item_activity.id */
  private int id;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_type_item_id */
  private int xactTypeItemId;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_xact_type_item_activity.item_amount */
  private double itemAmount;
/** The javabean property equivalent of database column vw_xact_type_item_activity.item_name */
  private String itemName;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_date */
  private java.util.Date xactDate;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_amount */
  private double xactAmount;
/** The javabean property equivalent of database column vw_xact_type_item_activity.reason */
  private String reason;
/** The javabean property equivalent of database column vw_xact_type_item_activity.confirm_no */
  private String confirmNo;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_type_id */
  private int xactTypeId;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_type_item_name */
  private String xactTypeItemName;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_type_xact_code */
  private String xactTypeXactCode;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_type_description */
  private String xactTypeDescription;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_category_id */
  private int xactCategoryId;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_category_description */
  private String xactCategoryDescription;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_category_code */
  private String xactCategoryCode;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwXactTypeItemActivity() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable id
 *
 * @author auto generated.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author atuo generated.
 */
  public int getId() {
    return this.id;
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
 * Sets the value of member variable itemAmount
 *
 * @author auto generated.
 */
  public void setItemAmount(double value) {
    this.itemAmount = value;
  }
/**
 * Gets the value of member variable itemAmount
 *
 * @author atuo generated.
 */
  public double getItemAmount() {
    return this.itemAmount;
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
 * Sets the value of member variable xactDate
 *
 * @author auto generated.
 */
  public void setXactDate(java.util.Date value) {
    this.xactDate = value;
  }
/**
 * Gets the value of member variable xactDate
 *
 * @author atuo generated.
 */
  public java.util.Date getXactDate() {
    return this.xactDate;
  }
/**
 * Sets the value of member variable xactAmount
 *
 * @author auto generated.
 */
  public void setXactAmount(double value) {
    this.xactAmount = value;
  }
/**
 * Gets the value of member variable xactAmount
 *
 * @author atuo generated.
 */
  public double getXactAmount() {
    return this.xactAmount;
  }
/**
 * Sets the value of member variable reason
 *
 * @author auto generated.
 */
  public void setReason(String value) {
    this.reason = value;
  }
/**
 * Gets the value of member variable reason
 *
 * @author atuo generated.
 */
  public String getReason() {
    return this.reason;
  }
/**
 * Sets the value of member variable confirmNo
 *
 * @author auto generated.
 */
  public void setConfirmNo(String value) {
    this.confirmNo = value;
  }
/**
 * Gets the value of member variable confirmNo
 *
 * @author atuo generated.
 */
  public String getConfirmNo() {
    return this.confirmNo;
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
 * Sets the value of member variable xactTypeItemName
 *
 * @author auto generated.
 */
  public void setXactTypeItemName(String value) {
    this.xactTypeItemName = value;
  }
/**
 * Gets the value of member variable xactTypeItemName
 *
 * @author atuo generated.
 */
  public String getXactTypeItemName() {
    return this.xactTypeItemName;
  }
/**
 * Sets the value of member variable xactTypeXactCode
 *
 * @author auto generated.
 */
  public void setXactTypeXactCode(String value) {
    this.xactTypeXactCode = value;
  }
/**
 * Gets the value of member variable xactTypeXactCode
 *
 * @author atuo generated.
 */
  public String getXactTypeXactCode() {
    return this.xactTypeXactCode;
  }
/**
 * Sets the value of member variable xactTypeDescription
 *
 * @author auto generated.
 */
  public void setXactTypeDescription(String value) {
    this.xactTypeDescription = value;
  }
/**
 * Gets the value of member variable xactTypeDescription
 *
 * @author atuo generated.
 */
  public String getXactTypeDescription() {
    return this.xactTypeDescription;
  }
/**
 * Sets the value of member variable xactCategoryId
 *
 * @author auto generated.
 */
  public void setXactCategoryId(int value) {
    this.xactCategoryId = value;
  }
/**
 * Gets the value of member variable xactCategoryId
 *
 * @author atuo generated.
 */
  public int getXactCategoryId() {
    return this.xactCategoryId;
  }
/**
 * Sets the value of member variable xactCategoryDescription
 *
 * @author auto generated.
 */
  public void setXactCategoryDescription(String value) {
    this.xactCategoryDescription = value;
  }
/**
 * Gets the value of member variable xactCategoryDescription
 *
 * @author atuo generated.
 */
  public String getXactCategoryDescription() {
    return this.xactCategoryDescription;
  }
/**
 * Sets the value of member variable xactCategoryCode
 *
 * @author auto generated.
 */
  public void setXactCategoryCode(String value) {
    this.xactCategoryCode = value;
  }
/**
 * Gets the value of member variable xactCategoryCode
 *
 * @author atuo generated.
 */
  public String getXactCategoryCode() {
    return this.xactCategoryCode;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
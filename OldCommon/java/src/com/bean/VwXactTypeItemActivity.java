package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_xact_type_item_activity database table/view.
 *
 * @author Roy Terrell.
 */
public class VwXactTypeItemActivity extends OrmBean {

/** The javabean property equivalent of database column vw_xact_type_item_activity.id */
  private int id;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_type_item_id */
  private int xactTypeItemId;
/** The javabean property equivalent of database column vw_xact_type_item_activity.xact_id */
  private int xactId;
/** The javabean property equivalent of database column vw_xact_type_item_activity.amount */
  private double amount;
/** The javabean property equivalent of database column vw_xact_type_item_activity.description */
  private String description;
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
 * @author Roy Terrell.
 */
  public VwXactTypeItemActivity() throws SystemException {
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
 * Sets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public String getDescription() {
    return this.description;
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
 * Sets the value of member variable xactTypeItemName
 *
 * @author Roy Terrell.
 */
  public void setXactTypeItemName(String value) {
    this.xactTypeItemName = value;
  }
/**
 * Gets the value of member variable xactTypeItemName
 *
 * @author Roy Terrell.
 */
  public String getXactTypeItemName() {
    return this.xactTypeItemName;
  }
/**
 * Sets the value of member variable xactTypeXactCode
 *
 * @author Roy Terrell.
 */
  public void setXactTypeXactCode(String value) {
    this.xactTypeXactCode = value;
  }
/**
 * Gets the value of member variable xactTypeXactCode
 *
 * @author Roy Terrell.
 */
  public String getXactTypeXactCode() {
    return this.xactTypeXactCode;
  }
/**
 * Sets the value of member variable xactTypeDescription
 *
 * @author Roy Terrell.
 */
  public void setXactTypeDescription(String value) {
    this.xactTypeDescription = value;
  }
/**
 * Gets the value of member variable xactTypeDescription
 *
 * @author Roy Terrell.
 */
  public String getXactTypeDescription() {
    return this.xactTypeDescription;
  }
/**
 * Sets the value of member variable xactCategoryId
 *
 * @author Roy Terrell.
 */
  public void setXactCategoryId(int value) {
    this.xactCategoryId = value;
  }
/**
 * Gets the value of member variable xactCategoryId
 *
 * @author Roy Terrell.
 */
  public int getXactCategoryId() {
    return this.xactCategoryId;
  }
/**
 * Sets the value of member variable xactCategoryDescription
 *
 * @author Roy Terrell.
 */
  public void setXactCategoryDescription(String value) {
    this.xactCategoryDescription = value;
  }
/**
 * Gets the value of member variable xactCategoryDescription
 *
 * @author Roy Terrell.
 */
  public String getXactCategoryDescription() {
    return this.xactCategoryDescription;
  }
/**
 * Sets the value of member variable xactCategoryCode
 *
 * @author Roy Terrell.
 */
  public void setXactCategoryCode(String value) {
    this.xactCategoryCode = value;
  }
/**
 * Gets the value of member variable xactCategoryCode
 *
 * @author Roy Terrell.
 */
  public String getXactCategoryCode() {
    return this.xactCategoryCode;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
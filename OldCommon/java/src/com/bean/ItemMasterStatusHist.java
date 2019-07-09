package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the item_master_status_hist database table/view.
 *
 * @author Roy Terrell.
 */
public class ItemMasterStatusHist extends OrmBean {

/** The javabean property equivalent of database column item_master_status_hist.id */
  private int id;
/** The javabean property equivalent of database column item_master_status_hist.item_master_id */
  private int itemMasterId;
/** The javabean property equivalent of database column item_master_status_hist.item_status_id */
  private int itemStatusId;
/** The javabean property equivalent of database column item_master_status_hist.unit_cost */
  private double unitCost;
/** The javabean property equivalent of database column item_master_status_hist.markup */
  private double markup;
/** The javabean property equivalent of database column item_master_status_hist.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column item_master_status_hist.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column item_master_status_hist.reason */
  private String reason;
/** The javabean property equivalent of database column item_master_status_hist.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column item_master_status_hist.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ItemMasterStatusHist() throws SystemException {
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
 * Sets the value of member variable itemStatusId
 *
 * @author Roy Terrell.
 */
  public void setItemStatusId(int value) {
    this.itemStatusId = value;
  }
/**
 * Gets the value of member variable itemStatusId
 *
 * @author Roy Terrell.
 */
  public int getItemStatusId() {
    return this.itemStatusId;
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
 * Sets the value of member variable markup
 *
 * @author Roy Terrell.
 */
  public void setMarkup(double value) {
    this.markup = value;
  }
/**
 * Gets the value of member variable markup
 *
 * @author Roy Terrell.
 */
  public double getMarkup() {
    return this.markup;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
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
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the item_master_status_hist database table/view.
 *
 * @author auto generated.
 */
public class ItemMasterStatusHist extends OrmBean {




	// Property name constants that belong to respective DataSource, ItemMasterStatusHistView

/** The property name constant equivalent to property, ItemStatusHistId, of respective DataSource view. */
  public static final String PROP_ITEMSTATUSHISTID = "ItemStatusHistId";
/** The property name constant equivalent to property, ItemId, of respective DataSource view. */
  public static final String PROP_ITEMID = "ItemId";
/** The property name constant equivalent to property, ItemStatusId, of respective DataSource view. */
  public static final String PROP_ITEMSTATUSID = "ItemStatusId";
/** The property name constant equivalent to property, UnitCost, of respective DataSource view. */
  public static final String PROP_UNITCOST = "UnitCost";
/** The property name constant equivalent to property, Markup, of respective DataSource view. */
  public static final String PROP_MARKUP = "Markup";
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";
/** The property name constant equivalent to property, Reason, of respective DataSource view. */
  public static final String PROP_REASON = "Reason";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, IpCreated, of respective DataSource view. */
  public static final String PROP_IPCREATED = "IpCreated";
/** The property name constant equivalent to property, IpUpdated, of respective DataSource view. */
  public static final String PROP_IPUPDATED = "IpUpdated";



	/** The javabean property equivalent of database column item_master_status_hist.item_status_hist_id */
  private int itemStatusHistId;
/** The javabean property equivalent of database column item_master_status_hist.item_id */
  private int itemId;
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
/** The javabean property equivalent of database column item_master_status_hist.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column item_master_status_hist.ip_updated */
  private String ipUpdated;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ItemMasterStatusHist() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable itemStatusHistId
 *
 * @author auto generated.
 */
  public void setItemStatusHistId(int value) {
    this.itemStatusHistId = value;
  }
/**
 * Gets the value of member variable itemStatusHistId
 *
 * @author atuo generated.
 */
  public int getItemStatusHistId() {
    return this.itemStatusHistId;
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
 * Sets the value of member variable itemStatusId
 *
 * @author auto generated.
 */
  public void setItemStatusId(int value) {
    this.itemStatusId = value;
  }
/**
 * Gets the value of member variable itemStatusId
 *
 * @author atuo generated.
 */
  public int getItemStatusId() {
    return this.itemStatusId;
  }
/**
 * Sets the value of member variable unitCost
 *
 * @author auto generated.
 */
  public void setUnitCost(double value) {
    this.unitCost = value;
  }
/**
 * Gets the value of member variable unitCost
 *
 * @author atuo generated.
 */
  public double getUnitCost() {
    return this.unitCost;
  }
/**
 * Sets the value of member variable markup
 *
 * @author auto generated.
 */
  public void setMarkup(double value) {
    this.markup = value;
  }
/**
 * Gets the value of member variable markup
 *
 * @author atuo generated.
 */
  public double getMarkup() {
    return this.markup;
  }
/**
 * Sets the value of member variable effectiveDate
 *
 * @author auto generated.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author auto generated.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
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
 * Sets the value of member variable dateCreated
 *
 * @author auto generated.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author auto generated.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author atuo generated.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Sets the value of member variable ipCreated
 *
 * @author auto generated.
 */
  public void setIpCreated(String value) {
    this.ipCreated = value;
  }
/**
 * Gets the value of member variable ipCreated
 *
 * @author atuo generated.
 */
  public String getIpCreated() {
    return this.ipCreated;
  }
/**
 * Sets the value of member variable ipUpdated
 *
 * @author auto generated.
 */
  public void setIpUpdated(String value) {
    this.ipUpdated = value;
  }
/**
 * Gets the value of member variable ipUpdated
 *
 * @author atuo generated.
 */
  public String getIpUpdated() {
    return this.ipUpdated;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
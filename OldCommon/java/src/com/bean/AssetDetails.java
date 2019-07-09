package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the asset_details database table/view.
 *
 * @author Roy Terrell.
 */
public class AssetDetails extends OrmBean {

/** The javabean property equivalent of database column asset_details.gl_account_id */
  private int glAccountId;
/** The javabean property equivalent of database column asset_details.account_number */
  private String accountNumber;
/** The javabean property equivalent of database column asset_details.serial_number */
  private String serialNumber;
/** The javabean property equivalent of database column asset_details.upc */
  private String upc;
/** The javabean property equivalent of database column asset_details.make */
  private String make;
/** The javabean property equivalent of database column asset_details.title */
  private String title;
/** The javabean property equivalent of database column asset_details.model */
  private String model;
/** The javabean property equivalent of database column asset_details.mfg_date */
  private java.util.Date mfgDate;
/** The javabean property equivalent of database column asset_details.date_acquired */
  private java.util.Date dateAcquired;
/** The javabean property equivalent of database column asset_details.date_disposed */
  private java.util.Date dateDisposed;
/** The javabean property equivalent of database column asset_details.pay_off_date */
  private java.util.Date payOffDate;
/** The javabean property equivalent of database column asset_details.reciept_photo_id */
  private int recieptPhotoId;
/** The javabean property equivalent of database column asset_details.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column asset_details.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column asset_details.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public AssetDetails() throws SystemException {
  }
/**
 * Sets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public void setGlAccountId(int value) {
    this.glAccountId = value;
  }
/**
 * Gets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public int getGlAccountId() {
    return this.glAccountId;
  }
/**
 * Sets the value of member variable accountNumber
 *
 * @author Roy Terrell.
 */
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }
/**
 * Gets the value of member variable accountNumber
 *
 * @author Roy Terrell.
 */
  public String getAccountNumber() {
    return this.accountNumber;
  }
/**
 * Sets the value of member variable serialNumber
 *
 * @author Roy Terrell.
 */
  public void setSerialNumber(String value) {
    this.serialNumber = value;
  }
/**
 * Gets the value of member variable serialNumber
 *
 * @author Roy Terrell.
 */
  public String getSerialNumber() {
    return this.serialNumber;
  }
/**
 * Sets the value of member variable upc
 *
 * @author Roy Terrell.
 */
  public void setUpc(String value) {
    this.upc = value;
  }
/**
 * Gets the value of member variable upc
 *
 * @author Roy Terrell.
 */
  public String getUpc() {
    return this.upc;
  }
/**
 * Sets the value of member variable make
 *
 * @author Roy Terrell.
 */
  public void setMake(String value) {
    this.make = value;
  }
/**
 * Gets the value of member variable make
 *
 * @author Roy Terrell.
 */
  public String getMake() {
    return this.make;
  }
/**
 * Sets the value of member variable title
 *
 * @author Roy Terrell.
 */
  public void setTitle(String value) {
    this.title = value;
  }
/**
 * Gets the value of member variable title
 *
 * @author Roy Terrell.
 */
  public String getTitle() {
    return this.title;
  }
/**
 * Sets the value of member variable model
 *
 * @author Roy Terrell.
 */
  public void setModel(String value) {
    this.model = value;
  }
/**
 * Gets the value of member variable model
 *
 * @author Roy Terrell.
 */
  public String getModel() {
    return this.model;
  }
/**
 * Sets the value of member variable mfgDate
 *
 * @author Roy Terrell.
 */
  public void setMfgDate(java.util.Date value) {
    this.mfgDate = value;
  }
/**
 * Gets the value of member variable mfgDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getMfgDate() {
    return this.mfgDate;
  }
/**
 * Sets the value of member variable dateAcquired
 *
 * @author Roy Terrell.
 */
  public void setDateAcquired(java.util.Date value) {
    this.dateAcquired = value;
  }
/**
 * Gets the value of member variable dateAcquired
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateAcquired() {
    return this.dateAcquired;
  }
/**
 * Sets the value of member variable dateDisposed
 *
 * @author Roy Terrell.
 */
  public void setDateDisposed(java.util.Date value) {
    this.dateDisposed = value;
  }
/**
 * Gets the value of member variable dateDisposed
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateDisposed() {
    return this.dateDisposed;
  }
/**
 * Sets the value of member variable payOffDate
 *
 * @author Roy Terrell.
 */
  public void setPayOffDate(java.util.Date value) {
    this.payOffDate = value;
  }
/**
 * Gets the value of member variable payOffDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getPayOffDate() {
    return this.payOffDate;
  }
/**
 * Sets the value of member variable recieptPhotoId
 *
 * @author Roy Terrell.
 */
  public void setRecieptPhotoId(int value) {
    this.recieptPhotoId = value;
  }
/**
 * Gets the value of member variable recieptPhotoId
 *
 * @author Roy Terrell.
 */
  public int getRecieptPhotoId() {
    return this.recieptPhotoId;
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
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
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
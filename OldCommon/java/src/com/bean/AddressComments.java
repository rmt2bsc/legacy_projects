package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the address_comments database table/view.
 *
 * @author Roy Terrell.
 */
public class AddressComments extends OrmBean {

/** The javabean property equivalent of database column address_comments.id */
  private int id;
/** The javabean property equivalent of database column address_comments.address_id */
  private int addressId;
/** The javabean property equivalent of database column address_comments.data */
  private String data;
/** The javabean property equivalent of database column address_comments.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column address_comments.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column address_comments.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public AddressComments() throws SystemException {
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
 * Sets the value of member variable addressId
 *
 * @author Roy Terrell.
 */
  public void setAddressId(int value) {
    this.addressId = value;
  }
/**
 * Gets the value of member variable addressId
 *
 * @author Roy Terrell.
 */
  public int getAddressId() {
    return this.addressId;
  }
/**
 * Sets the value of member variable data
 *
 * @author Roy Terrell.
 */
  public void setData(String value) {
    this.data = value;
  }
/**
 * Gets the value of member variable data
 *
 * @author Roy Terrell.
 */
  public String getData() {
    return this.data;
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
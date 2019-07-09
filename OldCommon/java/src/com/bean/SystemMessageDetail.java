package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the system_message_detail database table/view.
 *
 * @author Roy Terrell.
 */
public class SystemMessageDetail extends OrmBean {

/** The javabean property equivalent of database column system_message_detail.id */
  private int id;
/** The javabean property equivalent of database column system_message_detail.system_message_type_id */
  private int systemMessageTypeId;
/** The javabean property equivalent of database column system_message_detail.description */
  private String description;
/** The javabean property equivalent of database column system_message_detail.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column system_message_detail.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column system_message_detail.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public SystemMessageDetail() throws SystemException {
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
 * Sets the value of member variable systemMessageTypeId
 *
 * @author Roy Terrell.
 */
  public void setSystemMessageTypeId(int value) {
    this.systemMessageTypeId = value;
  }
/**
 * Gets the value of member variable systemMessageTypeId
 *
 * @author Roy Terrell.
 */
  public int getSystemMessageTypeId() {
    return this.systemMessageTypeId;
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
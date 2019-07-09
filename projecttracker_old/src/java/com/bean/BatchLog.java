package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the batch_log database table/view.
 *
 * @author auto generated.
 */
public class BatchLog extends OrmBean {




	// Property name constants that belong to respective DataSource, BatchLogView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, BatId, of respective DataSource view. */
  public static final String PROP_BATID = "BatId";
/** The property name constant equivalent to property, Msg, of respective DataSource view. */
  public static final String PROP_MSG = "Msg";
/** The property name constant equivalent to property, BatchDate, of respective DataSource view. */
  public static final String PROP_BATCHDATE = "BatchDate";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column batch_log.id */
  private int id;
/** The javabean property equivalent of database column batch_log.bat_id */
  private String batId;
/** The javabean property equivalent of database column batch_log.msg */
  private String msg;
/** The javabean property equivalent of database column batch_log.batch_date */
  private java.util.Date batchDate;
/** The javabean property equivalent of database column batch_log.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public BatchLog() throws SystemException {
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
 * Sets the value of member variable batId
 *
 * @author auto generated.
 */
  public void setBatId(String value) {
    this.batId = value;
  }
/**
 * Gets the value of member variable batId
 *
 * @author atuo generated.
 */
  public String getBatId() {
    return this.batId;
  }
/**
 * Sets the value of member variable msg
 *
 * @author auto generated.
 */
  public void setMsg(String value) {
    this.msg = value;
  }
/**
 * Gets the value of member variable msg
 *
 * @author atuo generated.
 */
  public String getMsg() {
    return this.msg;
  }
/**
 * Sets the value of member variable batchDate
 *
 * @author auto generated.
 */
  public void setBatchDate(java.util.Date value) {
    this.batchDate = value;
  }
/**
 * Gets the value of member variable batchDate
 *
 * @author atuo generated.
 */
  public java.util.Date getBatchDate() {
    return this.batchDate;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
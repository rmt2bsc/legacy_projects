package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the batch_log database table/view.
 *
 * @author Roy Terrell.
 */
public class BatchLog extends OrmBean {

/** The javabean property equivalent of database column batch_log.id */
  private int id;
/** The javabean property equivalent of database column batch_log.batch_job_id */
  private String batchJobId;
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
 * @author Roy Terrell.
 */
  public BatchLog() throws SystemException {
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
 * Sets the value of member variable batchJobId
 *
 * @author Roy Terrell.
 */
  public void setBatchJobId(String value) {
    this.batchJobId = value;
  }
/**
 * Gets the value of member variable batchJobId
 *
 * @author Roy Terrell.
 */
  public String getBatchJobId() {
    return this.batchJobId;
  }
/**
 * Sets the value of member variable msg
 *
 * @author Roy Terrell.
 */
  public void setMsg(String value) {
    this.msg = value;
  }
/**
 * Gets the value of member variable msg
 *
 * @author Roy Terrell.
 */
  public String getMsg() {
    return this.msg;
  }
/**
 * Sets the value of member variable batchDate
 *
 * @author Roy Terrell.
 */
  public void setBatchDate(java.util.Date value) {
    this.batchDate = value;
  }
/**
 * Gets the value of member variable batchDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getBatchDate() {
    return this.batchDate;
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
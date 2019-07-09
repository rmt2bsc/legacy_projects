package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_status_hist database table/view.
 *
 * @author Roy Terrell.
 */
public class PurchaseOrderStatusHist extends OrmBean {

/** The javabean property equivalent of database column purchase_order_status_hist.id */
  private int id;
/** The javabean property equivalent of database column purchase_order_status_hist.purchase_order_id */
  private int purchaseOrderId;
/** The javabean property equivalent of database column purchase_order_status_hist.purchase_order_status_id */
  private int purchaseOrderStatusId;
/** The javabean property equivalent of database column purchase_order_status_hist.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column purchase_order_status_hist.end_date */
  private java.util.Date endDate;
/** The javabean property equivalent of database column purchase_order_status_hist.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public PurchaseOrderStatusHist() throws SystemException {
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
 * Sets the value of member variable purchaseOrderId
 *
 * @author Roy Terrell.
 */
  public void setPurchaseOrderId(int value) {
    this.purchaseOrderId = value;
  }
/**
 * Gets the value of member variable purchaseOrderId
 *
 * @author Roy Terrell.
 */
  public int getPurchaseOrderId() {
    return this.purchaseOrderId;
  }
/**
 * Sets the value of member variable purchaseOrderStatusId
 *
 * @author Roy Terrell.
 */
  public void setPurchaseOrderStatusId(int value) {
    this.purchaseOrderStatusId = value;
  }
/**
 * Gets the value of member variable purchaseOrderStatusId
 *
 * @author Roy Terrell.
 */
  public int getPurchaseOrderStatusId() {
    return this.purchaseOrderStatusId;
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
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_status_hist database table/view.
 *
 * @author auto generated.
 */
public class PurchaseOrderStatusHist extends OrmBean {




	// Property name constants that belong to respective DataSource, PurchaseOrderStatusHistView

/** The property name constant equivalent to property, PoStatusHistId, of respective DataSource view. */
  public static final String PROP_POSTATUSHISTID = "PoStatusHistId";
/** The property name constant equivalent to property, PoStatusId, of respective DataSource view. */
  public static final String PROP_POSTATUSID = "PoStatusId";
/** The property name constant equivalent to property, PoId, of respective DataSource view. */
  public static final String PROP_POID = "PoId";
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column purchase_order_status_hist.po_status_hist_id */
  private int poStatusHistId;
/** The javabean property equivalent of database column purchase_order_status_hist.po_status_id */
  private int poStatusId;
/** The javabean property equivalent of database column purchase_order_status_hist.po_id */
  private int poId;
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
 * @author auto generated.
 */
  public PurchaseOrderStatusHist() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable poStatusHistId
 *
 * @author auto generated.
 */
  public void setPoStatusHistId(int value) {
    this.poStatusHistId = value;
  }
/**
 * Gets the value of member variable poStatusHistId
 *
 * @author atuo generated.
 */
  public int getPoStatusHistId() {
    return this.poStatusHistId;
  }
/**
 * Sets the value of member variable poStatusId
 *
 * @author auto generated.
 */
  public void setPoStatusId(int value) {
    this.poStatusId = value;
  }
/**
 * Gets the value of member variable poStatusId
 *
 * @author atuo generated.
 */
  public int getPoStatusId() {
    return this.poStatusId;
  }
/**
 * Sets the value of member variable poId
 *
 * @author auto generated.
 */
  public void setPoId(int value) {
    this.poId = value;
  }
/**
 * Gets the value of member variable poId
 *
 * @author atuo generated.
 */
  public int getPoId() {
    return this.poId;
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
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the purchase_order_status database table/view.
 *
 * @author auto generated.
 */
public class PurchaseOrderStatus extends OrmBean {




	// Property name constants that belong to respective DataSource, PurchaseOrderStatusView

/** The property name constant equivalent to property, PoStatusId, of respective DataSource view. */
  public static final String PROP_POSTATUSID = "PoStatusId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column purchase_order_status.po_status_id */
  private int poStatusId;
/** The javabean property equivalent of database column purchase_order_status.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public PurchaseOrderStatus() throws SystemException {
	super();
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
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
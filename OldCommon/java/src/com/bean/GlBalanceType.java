package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_balance_type database table/view.
 *
 * @author Roy Terrell.
 */
public class GlBalanceType extends OrmBean {

/** The javabean property equivalent of database column gl_balance_type.id */
  private int id;
/** The javabean property equivalent of database column gl_balance_type.long_desc */
  private String longDesc;
/** The javabean property equivalent of database column gl_balance_type.short_desc */
  private String shortDesc;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public GlBalanceType() throws SystemException {
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
 * Sets the value of member variable longDesc
 *
 * @author Roy Terrell.
 */
  public void setLongDesc(String value) {
    this.longDesc = value;
  }
/**
 * Gets the value of member variable longDesc
 *
 * @author Roy Terrell.
 */
  public String getLongDesc() {
    return this.longDesc;
  }
/**
 * Sets the value of member variable shortDesc
 *
 * @author Roy Terrell.
 */
  public void setShortDesc(String value) {
    this.shortDesc = value;
  }
/**
 * Gets the value of member variable shortDesc
 *
 * @author Roy Terrell.
 */
  public String getShortDesc() {
    return this.shortDesc;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_period database table/view.
 *
 * @author Roy Terrell.
 */
public class ProjPeriod extends OrmBean {

/** The javabean property equivalent of database column proj_period.id */
  private int id;
/** The javabean property equivalent of database column proj_period.prd_type */
  private String prdType;
/** The javabean property equivalent of database column proj_period.max_reg_hrs */
  private int maxRegHrs;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public ProjPeriod() throws SystemException {
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
 * Sets the value of member variable prdType
 *
 * @author Roy Terrell.
 */
  public void setPrdType(String value) {
    this.prdType = value;
  }
/**
 * Gets the value of member variable prdType
 *
 * @author Roy Terrell.
 */
  public String getPrdType() {
    return this.prdType;
  }
/**
 * Sets the value of member variable maxRegHrs
 *
 * @author Roy Terrell.
 */
  public void setMaxRegHrs(int value) {
    this.maxRegHrs = value;
  }
/**
 * Gets the value of member variable maxRegHrs
 *
 * @author Roy Terrell.
 */
  public int getMaxRegHrs() {
    return this.maxRegHrs;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
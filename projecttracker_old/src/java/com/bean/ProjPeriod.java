package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the proj_period database table/view.
 *
 * @author auto generated.
 */
public class ProjPeriod extends OrmBean {




	// Property name constants that belong to respective DataSource, ProjPeriodView

/** The property name constant equivalent to property, ProjPeriodId, of respective DataSource view. */
  public static final String PROP_PROJPERIODID = "ProjPeriodId";
/** The property name constant equivalent to property, PrdType, of respective DataSource view. */
  public static final String PROP_PRDTYPE = "PrdType";
/** The property name constant equivalent to property, MaxRegHrs, of respective DataSource view. */
  public static final String PROP_MAXREGHRS = "MaxRegHrs";



	/** The javabean property equivalent of database column proj_period.proj_period_id */
  private double projPeriodId;
/** The javabean property equivalent of database column proj_period.prd_type */
  private String prdType;
/** The javabean property equivalent of database column proj_period.max_reg_hrs */
  private int maxRegHrs;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public ProjPeriod() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable projPeriodId
 *
 * @author auto generated.
 */
  public void setProjPeriodId(double value) {
    this.projPeriodId = value;
  }
/**
 * Gets the value of member variable projPeriodId
 *
 * @author atuo generated.
 */
  public double getProjPeriodId() {
    return this.projPeriodId;
  }
/**
 * Sets the value of member variable prdType
 *
 * @author auto generated.
 */
  public void setPrdType(String value) {
    this.prdType = value;
  }
/**
 * Gets the value of member variable prdType
 *
 * @author atuo generated.
 */
  public String getPrdType() {
    return this.prdType;
  }
/**
 * Sets the value of member variable maxRegHrs
 *
 * @author auto generated.
 */
  public void setMaxRegHrs(int value) {
    this.maxRegHrs = value;
  }
/**
 * Gets the value of member variable maxRegHrs
 *
 * @author atuo generated.
 */
  public int getMaxRegHrs() {
    return this.maxRegHrs;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
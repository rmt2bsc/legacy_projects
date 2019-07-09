package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the acct_period database table/view.
 *
 * @author auto generated.
 */
public class AcctPeriod extends OrmBean {




	// Property name constants that belong to respective DataSource, AcctPeriodView

/** The property name constant equivalent to property, AcctPeriodId, of respective DataSource view. */
  public static final String PROP_ACCTPERIODID = "AcctPeriodId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";



	/** The javabean property equivalent of database column acct_period.acct_period_id */
  private int acctPeriodId;
/** The javabean property equivalent of database column acct_period.description */
  private String description;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public AcctPeriod() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable acctPeriodId
 *
 * @author auto generated.
 */
  public void setAcctPeriodId(int value) {
    this.acctPeriodId = value;
  }
/**
 * Gets the value of member variable acctPeriodId
 *
 * @author atuo generated.
 */
  public int getAcctPeriodId() {
    return this.acctPeriodId;
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
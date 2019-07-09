package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the acct_pref database table/view.
 *
 * @author auto generated.
 */
public class AcctPref extends OrmBean {




	// Property name constants that belong to respective DataSource, AcctPrefView

/** The property name constant equivalent to property, AcctPrefId, of respective DataSource view. */
  public static final String PROP_ACCTPREFID = "AcctPrefId";
/** The property name constant equivalent to property, AcctPeriodId, of respective DataSource view. */
  public static final String PROP_ACCTPERIODID = "AcctPeriodId";
/** The property name constant equivalent to property, AcctPrdBegMonth, of respective DataSource view. */
  public static final String PROP_ACCTPRDBEGMONTH = "AcctPrdBegMonth";



	/** The javabean property equivalent of database column acct_pref.acct_pref_id */
  private int acctPrefId;
/** The javabean property equivalent of database column acct_pref.acct_period_id */
  private int acctPeriodId;
/** The javabean property equivalent of database column acct_pref.acct_prd_beg_month */
  private int acctPrdBegMonth;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public AcctPref() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable acctPrefId
 *
 * @author auto generated.
 */
  public void setAcctPrefId(int value) {
    this.acctPrefId = value;
  }
/**
 * Gets the value of member variable acctPrefId
 *
 * @author atuo generated.
 */
  public int getAcctPrefId() {
    return this.acctPrefId;
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
 * Sets the value of member variable acctPrdBegMonth
 *
 * @author auto generated.
 */
  public void setAcctPrdBegMonth(int value) {
    this.acctPrdBegMonth = value;
  }
/**
 * Gets the value of member variable acctPrdBegMonth
 *
 * @author atuo generated.
 */
  public int getAcctPrdBegMonth() {
    return this.acctPrdBegMonth;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
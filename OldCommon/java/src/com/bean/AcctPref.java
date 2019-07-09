package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the acct_pref database table/view.
 *
 * @author Roy Terrell.
 */
public class AcctPref extends OrmBean {

/** The javabean property equivalent of database column acct_pref.id */
  private double id;
/** The javabean property equivalent of database column acct_pref.acct_prd_type_id */
  private double acctPrdTypeId;
/** The javabean property equivalent of database column acct_pref.acct_prd_beg_month */
  private int acctPrdBegMonth;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public AcctPref() throws SystemException {
  }
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setId(double value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public double getId() {
    return this.id;
  }
/**
 * Sets the value of member variable acctPrdTypeId
 *
 * @author Roy Terrell.
 */
  public void setAcctPrdTypeId(double value) {
    this.acctPrdTypeId = value;
  }
/**
 * Gets the value of member variable acctPrdTypeId
 *
 * @author Roy Terrell.
 */
  public double getAcctPrdTypeId() {
    return this.acctPrdTypeId;
  }
/**
 * Sets the value of member variable acctPrdBegMonth
 *
 * @author Roy Terrell.
 */
  public void setAcctPrdBegMonth(int value) {
    this.acctPrdBegMonth = value;
  }
/**
 * Gets the value of member variable acctPrdBegMonth
 *
 * @author Roy Terrell.
 */
  public int getAcctPrdBegMonth() {
    return this.acctPrdBegMonth;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
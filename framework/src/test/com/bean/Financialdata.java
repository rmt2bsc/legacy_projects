package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the financialdata database table/view.
 *
 * @author auto generated.
 */
public class Financialdata extends OrmBean {




	// Property name constants that belong to respective DataSource, FinancialdataView

/** The property name constant equivalent to property, Year, of respective DataSource view. */
  public static final String PROP_YEAR = "Year";
/** The property name constant equivalent to property, Quarter, of respective DataSource view. */
  public static final String PROP_QUARTER = "Quarter";
/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";
/** The property name constant equivalent to property, Amount, of respective DataSource view. */
  public static final String PROP_AMOUNT = "Amount";



	/** The javabean property equivalent of database column financialdata.year */
  private char year;
/** The javabean property equivalent of database column financialdata.quarter */
  private char quarter;
/** The javabean property equivalent of database column financialdata.code */
  private char code;
/** The javabean property equivalent of database column financialdata.amount */
  private double amount;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Financialdata() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable year
 *
 * @author auto generated.
 */
  public void setYear(char value) {
    this.year = value;
  }
/**
 * Gets the value of member variable year
 *
 * @author atuo generated.
 */
  public char getYear() {
    return this.year;
  }
/**
 * Sets the value of member variable quarter
 *
 * @author auto generated.
 */
  public void setQuarter(char value) {
    this.quarter = value;
  }
/**
 * Gets the value of member variable quarter
 *
 * @author atuo generated.
 */
  public char getQuarter() {
    return this.quarter;
  }
/**
 * Sets the value of member variable code
 *
 * @author auto generated.
 */
  public void setCode(char value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author atuo generated.
 */
  public char getCode() {
    return this.code;
  }
/**
 * Sets the value of member variable amount
 *
 * @author auto generated.
 */
  public void setAmount(double value) {
    this.amount = value;
  }
/**
 * Gets the value of member variable amount
 *
 * @author atuo generated.
 */
  public double getAmount() {
    return this.amount;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
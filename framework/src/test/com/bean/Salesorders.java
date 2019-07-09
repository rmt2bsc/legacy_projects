package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the salesorders database table/view.
 *
 * @author auto generated.
 */
public class Salesorders extends OrmBean {




	// Property name constants that belong to respective DataSource, SalesordersView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, Customerid, of respective DataSource view. */
  public static final String PROP_CUSTOMERID = "Customerid";
/** The property name constant equivalent to property, Orderdate, of respective DataSource view. */
  public static final String PROP_ORDERDATE = "Orderdate";
/** The property name constant equivalent to property, Financialcode, of respective DataSource view. */
  public static final String PROP_FINANCIALCODE = "Financialcode";
/** The property name constant equivalent to property, Region, of respective DataSource view. */
  public static final String PROP_REGION = "Region";
/** The property name constant equivalent to property, Salesrepresentative, of respective DataSource view. */
  public static final String PROP_SALESREPRESENTATIVE = "Salesrepresentative";



	/** The javabean property equivalent of database column salesorders.id */
  private int id;
/** The javabean property equivalent of database column salesorders.customerid */
  private int customerid;
/** The javabean property equivalent of database column salesorders.orderdate */
  private java.util.Date orderdate;
/** The javabean property equivalent of database column salesorders.financialcode */
  private char financialcode;
/** The javabean property equivalent of database column salesorders.region */
  private char region;
/** The javabean property equivalent of database column salesorders.salesrepresentative */
  private int salesrepresentative;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Salesorders() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable id
 *
 * @author auto generated.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author atuo generated.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable customerid
 *
 * @author auto generated.
 */
  public void setCustomerid(int value) {
    this.customerid = value;
  }
/**
 * Gets the value of member variable customerid
 *
 * @author atuo generated.
 */
  public int getCustomerid() {
    return this.customerid;
  }
/**
 * Sets the value of member variable orderdate
 *
 * @author auto generated.
 */
  public void setOrderdate(java.util.Date value) {
    this.orderdate = value;
  }
/**
 * Gets the value of member variable orderdate
 *
 * @author atuo generated.
 */
  public java.util.Date getOrderdate() {
    return this.orderdate;
  }
/**
 * Sets the value of member variable financialcode
 *
 * @author auto generated.
 */
  public void setFinancialcode(char value) {
    this.financialcode = value;
  }
/**
 * Gets the value of member variable financialcode
 *
 * @author atuo generated.
 */
  public char getFinancialcode() {
    return this.financialcode;
  }
/**
 * Sets the value of member variable region
 *
 * @author auto generated.
 */
  public void setRegion(char value) {
    this.region = value;
  }
/**
 * Gets the value of member variable region
 *
 * @author atuo generated.
 */
  public char getRegion() {
    return this.region;
  }
/**
 * Sets the value of member variable salesrepresentative
 *
 * @author auto generated.
 */
  public void setSalesrepresentative(int value) {
    this.salesrepresentative = value;
  }
/**
 * Gets the value of member variable salesrepresentative
 *
 * @author atuo generated.
 */
  public int getSalesrepresentative() {
    return this.salesrepresentative;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
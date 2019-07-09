package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_client_ext database table/view.
 *
 * @author Roy Terrell.
 */
public class VwClientExt extends OrmBean {

/** The javabean property equivalent of database column vw_client_ext.client_id */
  private int clientId;
/** The javabean property equivalent of database column vw_client_ext.account_no */
  private String accountNo;
/** The javabean property equivalent of database column vw_client_ext.credit_limit */
  private double creditLimit;
/** The javabean property equivalent of database column vw_client_ext.active */
  private int active;
/** The javabean property equivalent of database column vw_client_ext.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_client_ext.perbus_id */
  private int perbusId;
/** The javabean property equivalent of database column vw_client_ext.longname */
  private String longname;
/** The javabean property equivalent of database column vw_client_ext.name */
  private String name;
/** The javabean property equivalent of database column vw_client_ext.balance */
  private double balance;
/** The javabean property equivalent of database column vw_client_ext.bill_rate */
  private double billRate;
/** The javabean property equivalent of database column vw_client_ext.ot_bill_rate */
  private double otBillRate;
/** The javabean property equivalent of database column vw_client_ext.customer_type */
  private String customerType;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwClientExt() throws SystemException {
  }
/**
 * Sets the value of member variable clientId
 *
 * @author Roy Terrell.
 */
  public void setClientId(int value) {
    this.clientId = value;
  }
/**
 * Gets the value of member variable clientId
 *
 * @author Roy Terrell.
 */
  public int getClientId() {
    return this.clientId;
  }
/**
 * Sets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public void setAccountNo(String value) {
    this.accountNo = value;
  }
/**
 * Gets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public String getAccountNo() {
    return this.accountNo;
  }
/**
 * Sets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public void setCreditLimit(double value) {
    this.creditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public double getCreditLimit() {
    return this.creditLimit;
  }
/**
 * Sets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 *
 * @author Roy Terrell.
 */
  public int getActive() {
    return this.active;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable perbusId
 *
 * @author Roy Terrell.
 */
  public void setPerbusId(int value) {
    this.perbusId = value;
  }
/**
 * Gets the value of member variable perbusId
 *
 * @author Roy Terrell.
 */
  public int getPerbusId() {
    return this.perbusId;
  }
/**
 * Sets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public void setLongname(String value) {
    this.longname = value;
  }
/**
 * Gets the value of member variable longname
 *
 * @author Roy Terrell.
 */
  public String getLongname() {
    return this.longname;
  }
/**
 * Sets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author Roy Terrell.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public void setBalance(double value) {
    this.balance = value;
  }
/**
 * Gets the value of member variable balance
 *
 * @author Roy Terrell.
 */
  public double getBalance() {
    return this.balance;
  }
/**
 * Sets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public void setBillRate(double value) {
    this.billRate = value;
  }
/**
 * Gets the value of member variable billRate
 *
 * @author Roy Terrell.
 */
  public double getBillRate() {
    return this.billRate;
  }
/**
 * Sets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public void setOtBillRate(double value) {
    this.otBillRate = value;
  }
/**
 * Gets the value of member variable otBillRate
 *
 * @author Roy Terrell.
 */
  public double getOtBillRate() {
    return this.otBillRate;
  }
/**
 * Sets the value of member variable customerType
 *
 * @author Roy Terrell.
 */
  public void setCustomerType(String value) {
    this.customerType = value;
  }
/**
 * Gets the value of member variable customerType
 *
 * @author Roy Terrell.
 */
  public String getCustomerType() {
    return this.customerType;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_accounts database table/view.
 *
 * @author Roy Terrell.
 */
public class GlAccounts extends OrmBean {

/** The javabean property equivalent of database column gl_accounts.id */
  private int id;
/** The javabean property equivalent of database column gl_accounts.acct_type_id */
  private int acctTypeId;
/** The javabean property equivalent of database column gl_accounts.acct_cat_id */
  private int acctCatId;
/** The javabean property equivalent of database column gl_accounts.acct_seq */
  private int acctSeq;
/** The javabean property equivalent of database column gl_accounts.acct_no */
  private String acctNo;
/** The javabean property equivalent of database column gl_accounts.name */
  private String name;
/** The javabean property equivalent of database column gl_accounts.code */
  private String code;
/** The javabean property equivalent of database column gl_accounts.description */
  private String description;
/** The javabean property equivalent of database column gl_accounts.balance_type_id */
  private int balanceTypeId;
/** The javabean property equivalent of database column gl_accounts.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column gl_accounts.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column gl_accounts.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public GlAccounts() throws SystemException {
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
 * Sets the value of member variable acctTypeId
 *
 * @author Roy Terrell.
 */
  public void setAcctTypeId(int value) {
    this.acctTypeId = value;
  }
/**
 * Gets the value of member variable acctTypeId
 *
 * @author Roy Terrell.
 */
  public int getAcctTypeId() {
    return this.acctTypeId;
  }
/**
 * Sets the value of member variable acctCatId
 *
 * @author Roy Terrell.
 */
  public void setAcctCatId(int value) {
    this.acctCatId = value;
  }
/**
 * Gets the value of member variable acctCatId
 *
 * @author Roy Terrell.
 */
  public int getAcctCatId() {
    return this.acctCatId;
  }
/**
 * Sets the value of member variable acctSeq
 *
 * @author Roy Terrell.
 */
  public void setAcctSeq(int value) {
    this.acctSeq = value;
  }
/**
 * Gets the value of member variable acctSeq
 *
 * @author Roy Terrell.
 */
  public int getAcctSeq() {
    return this.acctSeq;
  }
/**
 * Sets the value of member variable acctNo
 *
 * @author Roy Terrell.
 */
  public void setAcctNo(String value) {
    this.acctNo = value;
  }
/**
 * Gets the value of member variable acctNo
 *
 * @author Roy Terrell.
 */
  public String getAcctNo() {
    return this.acctNo;
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
 * Sets the value of member variable code
 *
 * @author Roy Terrell.
 */
  public void setCode(String value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author Roy Terrell.
 */
  public String getCode() {
    return this.code;
  }
/**
 * Sets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author Roy Terrell.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable balanceTypeId
 *
 * @author Roy Terrell.
 */
  public void setBalanceTypeId(int value) {
    this.balanceTypeId = value;
  }
/**
 * Gets the value of member variable balanceTypeId
 *
 * @author Roy Terrell.
 */
  public int getBalanceTypeId() {
    return this.balanceTypeId;
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
 * Sets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author Roy Terrell.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author Roy Terrell.
 */
  public String getUserId() {
    return this.userId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
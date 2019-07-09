package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_acct_catg_name database table/view.
 *
 * @author Roy Terrell.
 */
public class VwAcctCatgName extends OrmBean {

/** The javabean property equivalent of database column vw_acct_catg_name.acct_cat_id */
  private int acctCatId;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_no */
  private String acctNo;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_seq */
  private int acctSeq;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_type_id */
  private int acctTypeId;
/** The javabean property equivalent of database column vw_acct_catg_name.balance_type_id */
  private int balanceTypeId;
/** The javabean property equivalent of database column vw_acct_catg_name.description */
  private String description;
/** The javabean property equivalent of database column vw_acct_catg_name.id */
  private int id;
/** The javabean property equivalent of database column vw_acct_catg_name.name */
  private String name;
/** The javabean property equivalent of database column vw_acct_catg_name.gl_catg_description */
  private String glCatgDescription;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public VwAcctCatgName() throws SystemException {
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
 * Sets the value of member variable glCatgDescription
 *
 * @author Roy Terrell.
 */
  public void setGlCatgDescription(String value) {
    this.glCatgDescription = value;
  }
/**
 * Gets the value of member variable glCatgDescription
 *
 * @author Roy Terrell.
 */
  public String getGlCatgDescription() {
    return this.glCatgDescription;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author Roy Terrell.
 */
  public void initBean() throws SystemException {}
}
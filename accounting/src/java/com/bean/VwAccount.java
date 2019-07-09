package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_account database table/view.
 *
 * @author auto generated.
 */
public class VwAccount extends OrmBean {




	// Property name constants that belong to respective DataSource, VwAccountView

/** The property name constant equivalent to property, AcctNo, of respective DataSource view. */
  public static final String PROP_ACCTNO = "AcctNo";
/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, BalanceTypeId, of respective DataSource view. */
  public static final String PROP_BALANCETYPEID = "BalanceTypeId";
/** The property name constant equivalent to property, Accttypedescr, of respective DataSource view. */
  public static final String PROP_ACCTTYPEDESCR = "Accttypedescr";
/** The property name constant equivalent to property, Acctcatgdescr, of respective DataSource view. */
  public static final String PROP_ACCTCATGDESCR = "Acctcatgdescr";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, AcctTypeId, of respective DataSource view. */
  public static final String PROP_ACCTTYPEID = "AcctTypeId";
/** The property name constant equivalent to property, AcctCatId, of respective DataSource view. */
  public static final String PROP_ACCTCATID = "AcctCatId";
/** The property name constant equivalent to property, AcctSeq, of respective DataSource view. */
  public static final String PROP_ACCTSEQ = "AcctSeq";



	/** The javabean property equivalent of database column vw_account.acct_no */
  private String acctNo;
/** The javabean property equivalent of database column vw_account.code */
  private String code;
/** The javabean property equivalent of database column vw_account.name */
  private String name;
/** The javabean property equivalent of database column vw_account.description */
  private String description;
/** The javabean property equivalent of database column vw_account.balance_type_id */
  private int balanceTypeId;
/** The javabean property equivalent of database column vw_account.accttypedescr */
  private String accttypedescr;
/** The javabean property equivalent of database column vw_account.acctcatgdescr */
  private String acctcatgdescr;
/** The javabean property equivalent of database column vw_account.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_account.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column vw_account.user_id */
  private String userId;
/** The javabean property equivalent of database column vw_account.id */
  private int id;
/** The javabean property equivalent of database column vw_account.acct_type_id */
  private int acctTypeId;
/** The javabean property equivalent of database column vw_account.acct_cat_id */
  private int acctCatId;
/** The javabean property equivalent of database column vw_account.acct_seq */
  private int acctSeq;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwAccount() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable acctNo
 *
 * @author auto generated.
 */
  public void setAcctNo(String value) {
    this.acctNo = value;
  }
/**
 * Gets the value of member variable acctNo
 *
 * @author atuo generated.
 */
  public String getAcctNo() {
    return this.acctNo;
  }
/**
 * Sets the value of member variable code
 *
 * @author auto generated.
 */
  public void setCode(String value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 *
 * @author atuo generated.
 */
  public String getCode() {
    return this.code;
  }
/**
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
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
 * Sets the value of member variable balanceTypeId
 *
 * @author auto generated.
 */
  public void setBalanceTypeId(int value) {
    this.balanceTypeId = value;
  }
/**
 * Gets the value of member variable balanceTypeId
 *
 * @author atuo generated.
 */
  public int getBalanceTypeId() {
    return this.balanceTypeId;
  }
/**
 * Sets the value of member variable accttypedescr
 *
 * @author auto generated.
 */
  public void setAccttypedescr(String value) {
    this.accttypedescr = value;
  }
/**
 * Gets the value of member variable accttypedescr
 *
 * @author atuo generated.
 */
  public String getAccttypedescr() {
    return this.accttypedescr;
  }
/**
 * Sets the value of member variable acctcatgdescr
 *
 * @author auto generated.
 */
  public void setAcctcatgdescr(String value) {
    this.acctcatgdescr = value;
  }
/**
 * Gets the value of member variable acctcatgdescr
 *
 * @author atuo generated.
 */
  public String getAcctcatgdescr() {
    return this.acctcatgdescr;
  }
/**
 * Sets the value of member variable dateCreated
 *
 * @author auto generated.
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 *
 * @author auto generated.
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 *
 * @author atuo generated.
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 *
 * @author auto generated.
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 *
 * @author atuo generated.
 */
  public String getUserId() {
    return this.userId;
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
 * Sets the value of member variable acctTypeId
 *
 * @author auto generated.
 */
  public void setAcctTypeId(int value) {
    this.acctTypeId = value;
  }
/**
 * Gets the value of member variable acctTypeId
 *
 * @author atuo generated.
 */
  public int getAcctTypeId() {
    return this.acctTypeId;
  }
/**
 * Sets the value of member variable acctCatId
 *
 * @author auto generated.
 */
  public void setAcctCatId(int value) {
    this.acctCatId = value;
  }
/**
 * Gets the value of member variable acctCatId
 *
 * @author atuo generated.
 */
  public int getAcctCatId() {
    return this.acctCatId;
  }
/**
 * Sets the value of member variable acctSeq
 *
 * @author auto generated.
 */
  public void setAcctSeq(int value) {
    this.acctSeq = value;
  }
/**
 * Gets the value of member variable acctSeq
 *
 * @author atuo generated.
 */
  public int getAcctSeq() {
    return this.acctSeq;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
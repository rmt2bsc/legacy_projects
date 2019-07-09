package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_accounts database table/view.
 *
 * @author auto generated.
 */
public class GlAccounts extends OrmBean {




	// Property name constants that belong to respective DataSource, GlAccountsView

/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, AcctTypeId, of respective DataSource view. */
  public static final String PROP_ACCTTYPEID = "AcctTypeId";
/** The property name constant equivalent to property, AcctCatgId, of respective DataSource view. */
  public static final String PROP_ACCTCATGID = "AcctCatgId";
/** The property name constant equivalent to property, AcctSeq, of respective DataSource view. */
  public static final String PROP_ACCTSEQ = "AcctSeq";
/** The property name constant equivalent to property, AcctNo, of respective DataSource view. */
  public static final String PROP_ACCTNO = "AcctNo";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, AcctBaltypeId, of respective DataSource view. */
  public static final String PROP_ACCTBALTYPEID = "AcctBaltypeId";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column gl_accounts.acct_id */
  private int acctId;
/** The javabean property equivalent of database column gl_accounts.acct_type_id */
  private int acctTypeId;
/** The javabean property equivalent of database column gl_accounts.acct_catg_id */
  private int acctCatgId;
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
/** The javabean property equivalent of database column gl_accounts.acct_baltype_id */
  private int acctBaltypeId;
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
 * @author auto generated.
 */
  public GlAccounts() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable acctId
 *
 * @author auto generated.
 */
  public void setAcctId(int value) {
    this.acctId = value;
  }
/**
 * Gets the value of member variable acctId
 *
 * @author atuo generated.
 */
  public int getAcctId() {
    return this.acctId;
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
 * Sets the value of member variable acctCatgId
 *
 * @author auto generated.
 */
  public void setAcctCatgId(int value) {
    this.acctCatgId = value;
  }
/**
 * Gets the value of member variable acctCatgId
 *
 * @author atuo generated.
 */
  public int getAcctCatgId() {
    return this.acctCatgId;
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
 * Sets the value of member variable acctBaltypeId
 *
 * @author auto generated.
 */
  public void setAcctBaltypeId(int value) {
    this.acctBaltypeId = value;
  }
/**
 * Gets the value of member variable acctBaltypeId
 *
 * @author atuo generated.
 */
  public int getAcctBaltypeId() {
    return this.acctBaltypeId;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
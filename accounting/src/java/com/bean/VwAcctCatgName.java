package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_acct_catg_name database table/view.
 *
 * @author auto generated.
 */
public class VwAcctCatgName extends OrmBean {




	// Property name constants that belong to respective DataSource, VwAcctCatgNameView

/** The property name constant equivalent to property, AcctCatgId, of respective DataSource view. */
  public static final String PROP_ACCTCATGID = "AcctCatgId";
/** The property name constant equivalent to property, AcctNo, of respective DataSource view. */
  public static final String PROP_ACCTNO = "AcctNo";
/** The property name constant equivalent to property, AcctSeq, of respective DataSource view. */
  public static final String PROP_ACCTSEQ = "AcctSeq";
/** The property name constant equivalent to property, AcctTypeId, of respective DataSource view. */
  public static final String PROP_ACCTTYPEID = "AcctTypeId";
/** The property name constant equivalent to property, AcctBaltypeId, of respective DataSource view. */
  public static final String PROP_ACCTBALTYPEID = "AcctBaltypeId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, GlCatgDescription, of respective DataSource view. */
  public static final String PROP_GLCATGDESCRIPTION = "GlCatgDescription";



	/** The javabean property equivalent of database column vw_acct_catg_name.acct_catg_id */
  private int acctCatgId;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_no */
  private String acctNo;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_seq */
  private int acctSeq;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_type_id */
  private int acctTypeId;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_baltype_id */
  private int acctBaltypeId;
/** The javabean property equivalent of database column vw_acct_catg_name.description */
  private String description;
/** The javabean property equivalent of database column vw_acct_catg_name.acct_id */
  private int acctId;
/** The javabean property equivalent of database column vw_acct_catg_name.name */
  private String name;
/** The javabean property equivalent of database column vw_acct_catg_name.gl_catg_description */
  private String glCatgDescription;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwAcctCatgName() throws SystemException {
	super();
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
 * Sets the value of member variable glCatgDescription
 *
 * @author auto generated.
 */
  public void setGlCatgDescription(String value) {
    this.glCatgDescription = value;
  }
/**
 * Gets the value of member variable glCatgDescription
 *
 * @author atuo generated.
 */
  public String getGlCatgDescription() {
    return this.glCatgDescription;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
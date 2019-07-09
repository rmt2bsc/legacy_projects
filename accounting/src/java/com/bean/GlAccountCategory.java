package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the gl_account_category database table/view.
 *
 * @author auto generated.
 */
public class GlAccountCategory extends OrmBean {




	// Property name constants that belong to respective DataSource, GlAccountCategoryView

/** The property name constant equivalent to property, AcctCatgId, of respective DataSource view. */
  public static final String PROP_ACCTCATGID = "AcctCatgId";
/** The property name constant equivalent to property, AcctTypeId, of respective DataSource view. */
  public static final String PROP_ACCTTYPEID = "AcctTypeId";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column gl_account_category.acct_catg_id */
  private int acctCatgId;
/** The javabean property equivalent of database column gl_account_category.acct_type_id */
  private int acctTypeId;
/** The javabean property equivalent of database column gl_account_category.description */
  private String description;
/** The javabean property equivalent of database column gl_account_category.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column gl_account_category.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column gl_account_category.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public GlAccountCategory() throws SystemException {
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
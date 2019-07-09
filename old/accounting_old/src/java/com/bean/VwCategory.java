package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_category database table/view.
 *
 * @author auto generated.
 */
public class VwCategory extends OrmBean {




	// Property name constants that belong to respective DataSource, VwCategoryView

/** The property name constant equivalent to property, Accttypeid, of respective DataSource view. */
  public static final String PROP_ACCTTYPEID = "Accttypeid";
/** The property name constant equivalent to property, Accttypedescr, of respective DataSource view. */
  public static final String PROP_ACCTTYPEDESCR = "Accttypedescr";
/** The property name constant equivalent to property, Acctcatid, of respective DataSource view. */
  public static final String PROP_ACCTCATID = "Acctcatid";
/** The property name constant equivalent to property, Acctcatgdescr, of respective DataSource view. */
  public static final String PROP_ACCTCATGDESCR = "Acctcatgdescr";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column vw_category.accttypeid */
  private int accttypeid;
/** The javabean property equivalent of database column vw_category.accttypedescr */
  private String accttypedescr;
/** The javabean property equivalent of database column vw_category.acctcatid */
  private int acctcatid;
/** The javabean property equivalent of database column vw_category.acctcatgdescr */
  private String acctcatgdescr;
/** The javabean property equivalent of database column vw_category.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column vw_category.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column vw_category.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwCategory() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable accttypeid
 *
 * @author auto generated.
 */
  public void setAccttypeid(int value) {
    this.accttypeid = value;
  }
/**
 * Gets the value of member variable accttypeid
 *
 * @author atuo generated.
 */
  public int getAccttypeid() {
    return this.accttypeid;
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
 * Sets the value of member variable acctcatid
 *
 * @author auto generated.
 */
  public void setAcctcatid(int value) {
    this.acctcatid = value;
  }
/**
 * Gets the value of member variable acctcatid
 *
 * @author atuo generated.
 */
  public int getAcctcatid() {
    return this.acctcatid;
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
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
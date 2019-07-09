package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the xact_posting database table/view.
 *
 * @author auto generated.
 */
public class XactPosting extends OrmBean {




	// Property name constants that belong to respective DataSource, XactPostingView

/** The property name constant equivalent to property, XactPostId, of respective DataSource view. */
  public static final String PROP_XACTPOSTID = "XactPostId";
/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, XactId, of respective DataSource view. */
  public static final String PROP_XACTID = "XactId";
/** The property name constant equivalent to property, AcctSubsidiaryId, of respective DataSource view. */
  public static final String PROP_ACCTSUBSIDIARYID = "AcctSubsidiaryId";
/** The property name constant equivalent to property, AcctPeriodId, of respective DataSource view. */
  public static final String PROP_ACCTPERIODID = "AcctPeriodId";
/** The property name constant equivalent to property, Period, of respective DataSource view. */
  public static final String PROP_PERIOD = "Period";
/** The property name constant equivalent to property, PostAmount, of respective DataSource view. */
  public static final String PROP_POSTAMOUNT = "PostAmount";
/** The property name constant equivalent to property, PostDate, of respective DataSource view. */
  public static final String PROP_POSTDATE = "PostDate";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column xact_posting.xact_post_id */
  private int xactPostId;
/** The javabean property equivalent of database column xact_posting.acct_id */
  private int acctId;
/** The javabean property equivalent of database column xact_posting.xact_id */
  private int xactId;
/** The javabean property equivalent of database column xact_posting.acct_subsidiary_id */
  private String acctSubsidiaryId;
/** The javabean property equivalent of database column xact_posting.acct_period_id */
  private int acctPeriodId;
/** The javabean property equivalent of database column xact_posting.period */
  private int period;
/** The javabean property equivalent of database column xact_posting.post_amount */
  private double postAmount;
/** The javabean property equivalent of database column xact_posting.post_date */
  private java.util.Date postDate;
/** The javabean property equivalent of database column xact_posting.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column xact_posting.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column xact_posting.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public XactPosting() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable xactPostId
 *
 * @author auto generated.
 */
  public void setXactPostId(int value) {
    this.xactPostId = value;
  }
/**
 * Gets the value of member variable xactPostId
 *
 * @author atuo generated.
 */
  public int getXactPostId() {
    return this.xactPostId;
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
 * Sets the value of member variable xactId
 *
 * @author auto generated.
 */
  public void setXactId(int value) {
    this.xactId = value;
  }
/**
 * Gets the value of member variable xactId
 *
 * @author atuo generated.
 */
  public int getXactId() {
    return this.xactId;
  }
/**
 * Sets the value of member variable acctSubsidiaryId
 *
 * @author auto generated.
 */
  public void setAcctSubsidiaryId(String value) {
    this.acctSubsidiaryId = value;
  }
/**
 * Gets the value of member variable acctSubsidiaryId
 *
 * @author atuo generated.
 */
  public String getAcctSubsidiaryId() {
    return this.acctSubsidiaryId;
  }
/**
 * Sets the value of member variable acctPeriodId
 *
 * @author auto generated.
 */
  public void setAcctPeriodId(int value) {
    this.acctPeriodId = value;
  }
/**
 * Gets the value of member variable acctPeriodId
 *
 * @author atuo generated.
 */
  public int getAcctPeriodId() {
    return this.acctPeriodId;
  }
/**
 * Sets the value of member variable period
 *
 * @author auto generated.
 */
  public void setPeriod(int value) {
    this.period = value;
  }
/**
 * Gets the value of member variable period
 *
 * @author atuo generated.
 */
  public int getPeriod() {
    return this.period;
  }
/**
 * Sets the value of member variable postAmount
 *
 * @author auto generated.
 */
  public void setPostAmount(double value) {
    this.postAmount = value;
  }
/**
 * Gets the value of member variable postAmount
 *
 * @author atuo generated.
 */
  public double getPostAmount() {
    return this.postAmount;
  }
/**
 * Sets the value of member variable postDate
 *
 * @author auto generated.
 */
  public void setPostDate(java.util.Date value) {
    this.postDate = value;
  }
/**
 * Gets the value of member variable postDate
 *
 * @author atuo generated.
 */
  public java.util.Date getPostDate() {
    return this.postDate;
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
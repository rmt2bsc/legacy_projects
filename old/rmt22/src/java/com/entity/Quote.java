package com.entity;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the quote database table/view.
 *
 * @author auto generated.
 */
public class Quote extends OrmBean {




	// Property name constants that belong to respective DataSource, QuoteView

/** The property name constant equivalent to property, QuoteId, of respective DataSource view. */
  public static final String PROP_QUOTEID = "QuoteId";
/** The property name constant equivalent to property, QuoteStatusId, of respective DataSource view. */
  public static final String PROP_QUOTESTATUSID = "QuoteStatusId";
/** The property name constant equivalent to property, ContactName, of respective DataSource view. */
  public static final String PROP_CONTACTNAME = "ContactName";
/** The property name constant equivalent to property, CompanyName, of respective DataSource view. */
  public static final String PROP_COMPANYNAME = "CompanyName";
/** The property name constant equivalent to property, Email, of respective DataSource view. */
  public static final String PROP_EMAIL = "Email";
/** The property name constant equivalent to property, Addr1, of respective DataSource view. */
  public static final String PROP_ADDR1 = "Addr1";
/** The property name constant equivalent to property, Addr2, of respective DataSource view. */
  public static final String PROP_ADDR2 = "Addr2";
/** The property name constant equivalent to property, City, of respective DataSource view. */
  public static final String PROP_CITY = "City";
/** The property name constant equivalent to property, State, of respective DataSource view. */
  public static final String PROP_STATE = "State";
/** The property name constant equivalent to property, Zip, of respective DataSource view. */
  public static final String PROP_ZIP = "Zip";
/** The property name constant equivalent to property, SolutionType, of respective DataSource view. */
  public static final String PROP_SOLUTIONTYPE = "SolutionType";
/** The property name constant equivalent to property, ContactPref, of respective DataSource view. */
  public static final String PROP_CONTACTPREF = "ContactPref";
/** The property name constant equivalent to property, Website, of respective DataSource view. */
  public static final String PROP_WEBSITE = "Website";
/** The property name constant equivalent to property, TimeFrame, of respective DataSource view. */
  public static final String PROP_TIMEFRAME = "TimeFrame";
/** The property name constant equivalent to property, BecomeMember, of respective DataSource view. */
  public static final String PROP_BECOMEMEMBER = "BecomeMember";
/** The property name constant equivalent to property, Comments, of respective DataSource view. */
  public static final String PROP_COMMENTS = "Comments";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, Phone, of respective DataSource view. */
  public static final String PROP_PHONE = "Phone";



	/** The javabean property equivalent of database column quote.quote_id */
  private int quoteId;
/** The javabean property equivalent of database column quote.quote_status_id */
  private int quoteStatusId;
/** The javabean property equivalent of database column quote.contact_name */
  private String contactName;
/** The javabean property equivalent of database column quote.company_name */
  private String companyName;
/** The javabean property equivalent of database column quote.email */
  private String email;
/** The javabean property equivalent of database column quote.addr1 */
  private String addr1;
/** The javabean property equivalent of database column quote.addr2 */
  private String addr2;
/** The javabean property equivalent of database column quote.city */
  private String city;
/** The javabean property equivalent of database column quote.state */
  private String state;
/** The javabean property equivalent of database column quote.zip */
  private int zip;
/** The javabean property equivalent of database column quote.solution_type */
  private int solutionType;
/** The javabean property equivalent of database column quote.contact_pref */
  private int contactPref;
/** The javabean property equivalent of database column quote.website */
  private String website;
/** The javabean property equivalent of database column quote.time_frame */
  private String timeFrame;
/** The javabean property equivalent of database column quote.become_member */
  private int becomeMember;
/** The javabean property equivalent of database column quote.comments */
  private String comments;
/** The javabean property equivalent of database column quote.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column quote.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column quote.user_id */
  private String userId;
/** The javabean property equivalent of database column quote.phone */
  private String phone;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Quote() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable quoteId
 *
 * @author auto generated.
 */
  public void setQuoteId(int value) {
    this.quoteId = value;
  }
/**
 * Gets the value of member variable quoteId
 *
 * @author atuo generated.
 */
  public int getQuoteId() {
    return this.quoteId;
  }
/**
 * Sets the value of member variable quoteStatusId
 *
 * @author auto generated.
 */
  public void setQuoteStatusId(int value) {
    this.quoteStatusId = value;
  }
/**
 * Gets the value of member variable quoteStatusId
 *
 * @author atuo generated.
 */
  public int getQuoteStatusId() {
    return this.quoteStatusId;
  }
/**
 * Sets the value of member variable contactName
 *
 * @author auto generated.
 */
  public void setContactName(String value) {
    this.contactName = value;
  }
/**
 * Gets the value of member variable contactName
 *
 * @author atuo generated.
 */
  public String getContactName() {
    return this.contactName;
  }
/**
 * Sets the value of member variable companyName
 *
 * @author auto generated.
 */
  public void setCompanyName(String value) {
    this.companyName = value;
  }
/**
 * Gets the value of member variable companyName
 *
 * @author atuo generated.
 */
  public String getCompanyName() {
    return this.companyName;
  }
/**
 * Sets the value of member variable email
 *
 * @author auto generated.
 */
  public void setEmail(String value) {
    this.email = value;
  }
/**
 * Gets the value of member variable email
 *
 * @author atuo generated.
 */
  public String getEmail() {
    return this.email;
  }
/**
 * Sets the value of member variable addr1
 *
 * @author auto generated.
 */
  public void setAddr1(String value) {
    this.addr1 = value;
  }
/**
 * Gets the value of member variable addr1
 *
 * @author atuo generated.
 */
  public String getAddr1() {
    return this.addr1;
  }
/**
 * Sets the value of member variable addr2
 *
 * @author auto generated.
 */
  public void setAddr2(String value) {
    this.addr2 = value;
  }
/**
 * Gets the value of member variable addr2
 *
 * @author atuo generated.
 */
  public String getAddr2() {
    return this.addr2;
  }
/**
 * Sets the value of member variable city
 *
 * @author auto generated.
 */
  public void setCity(String value) {
    this.city = value;
  }
/**
 * Gets the value of member variable city
 *
 * @author atuo generated.
 */
  public String getCity() {
    return this.city;
  }
/**
 * Sets the value of member variable state
 *
 * @author auto generated.
 */
  public void setState(String value) {
    this.state = value;
  }
/**
 * Gets the value of member variable state
 *
 * @author atuo generated.
 */
  public String getState() {
    return this.state;
  }
/**
 * Sets the value of member variable zip
 *
 * @author auto generated.
 */
  public void setZip(int value) {
    this.zip = value;
  }
/**
 * Gets the value of member variable zip
 *
 * @author atuo generated.
 */
  public int getZip() {
    return this.zip;
  }
/**
 * Sets the value of member variable solutionType
 *
 * @author auto generated.
 */
  public void setSolutionType(int value) {
    this.solutionType = value;
  }
/**
 * Gets the value of member variable solutionType
 *
 * @author atuo generated.
 */
  public int getSolutionType() {
    return this.solutionType;
  }
/**
 * Sets the value of member variable contactPref
 *
 * @author auto generated.
 */
  public void setContactPref(int value) {
    this.contactPref = value;
  }
/**
 * Gets the value of member variable contactPref
 *
 * @author atuo generated.
 */
  public int getContactPref() {
    return this.contactPref;
  }
/**
 * Sets the value of member variable website
 *
 * @author auto generated.
 */
  public void setWebsite(String value) {
    this.website = value;
  }
/**
 * Gets the value of member variable website
 *
 * @author atuo generated.
 */
  public String getWebsite() {
    return this.website;
  }
/**
 * Sets the value of member variable timeFrame
 *
 * @author auto generated.
 */
  public void setTimeFrame(String value) {
    this.timeFrame = value;
  }
/**
 * Gets the value of member variable timeFrame
 *
 * @author atuo generated.
 */
  public String getTimeFrame() {
    return this.timeFrame;
  }
/**
 * Sets the value of member variable becomeMember
 *
 * @author auto generated.
 */
  public void setBecomeMember(int value) {
    this.becomeMember = value;
  }
/**
 * Gets the value of member variable becomeMember
 *
 * @author atuo generated.
 */
  public int getBecomeMember() {
    return this.becomeMember;
  }
/**
 * Sets the value of member variable comments
 *
 * @author auto generated.
 */
  public void setComments(String value) {
    this.comments = value;
  }
/**
 * Gets the value of member variable comments
 *
 * @author atuo generated.
 */
  public String getComments() {
    return this.comments;
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
 * Sets the value of member variable phone
 *
 * @author auto generated.
 */
  public void setPhone(String value) {
    this.phone = value;
  }
/**
 * Gets the value of member variable phone
 *
 * @author atuo generated.
 */
  public String getPhone() {
    return this.phone;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
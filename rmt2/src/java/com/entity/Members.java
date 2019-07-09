package com.entity;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the members database table/view.
 *
 * @author auto generated.
 */
public class Members extends OrmBean {




	// Property name constants that belong to respective DataSource, MembersView

/** The property name constant equivalent to property, MemberId, of respective DataSource view. */
  public static final String PROP_MEMBERID = "MemberId";
/** The property name constant equivalent to property, Firstname, of respective DataSource view. */
  public static final String PROP_FIRSTNAME = "Firstname";
/** The property name constant equivalent to property, Lastname, of respective DataSource view. */
  public static final String PROP_LASTNAME = "Lastname";
/** The property name constant equivalent to property, Company, of respective DataSource view. */
  public static final String PROP_COMPANY = "Company";
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
/** The property name constant equivalent to property, EffectiveDate, of respective DataSource view. */
  public static final String PROP_EFFECTIVEDATE = "EffectiveDate";
/** The property name constant equivalent to property, EndDate, of respective DataSource view. */
  public static final String PROP_ENDDATE = "EndDate";



	/** The javabean property equivalent of database column members.member_id */
  private int memberId;
/** The javabean property equivalent of database column members.firstname */
  private String firstname;
/** The javabean property equivalent of database column members.lastname */
  private String lastname;
/** The javabean property equivalent of database column members.company */
  private String company;
/** The javabean property equivalent of database column members.email */
  private String email;
/** The javabean property equivalent of database column members.addr1 */
  private String addr1;
/** The javabean property equivalent of database column members.addr2 */
  private String addr2;
/** The javabean property equivalent of database column members.city */
  private String city;
/** The javabean property equivalent of database column members.state */
  private String state;
/** The javabean property equivalent of database column members.zip */
  private int zip;
/** The javabean property equivalent of database column members.effective_date */
  private java.util.Date effectiveDate;
/** The javabean property equivalent of database column members.end_date */
  private java.util.Date endDate;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Members() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable memberId
 *
 * @author auto generated.
 */
  public void setMemberId(int value) {
    this.memberId = value;
  }
/**
 * Gets the value of member variable memberId
 *
 * @author atuo generated.
 */
  public int getMemberId() {
    return this.memberId;
  }
/**
 * Sets the value of member variable firstname
 *
 * @author auto generated.
 */
  public void setFirstname(String value) {
    this.firstname = value;
  }
/**
 * Gets the value of member variable firstname
 *
 * @author atuo generated.
 */
  public String getFirstname() {
    return this.firstname;
  }
/**
 * Sets the value of member variable lastname
 *
 * @author auto generated.
 */
  public void setLastname(String value) {
    this.lastname = value;
  }
/**
 * Gets the value of member variable lastname
 *
 * @author atuo generated.
 */
  public String getLastname() {
    return this.lastname;
  }
/**
 * Sets the value of member variable company
 *
 * @author auto generated.
 */
  public void setCompany(String value) {
    this.company = value;
  }
/**
 * Gets the value of member variable company
 *
 * @author atuo generated.
 */
  public String getCompany() {
    return this.company;
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
 * Sets the value of member variable effectiveDate
 *
 * @author auto generated.
 */
  public void setEffectiveDate(java.util.Date value) {
    this.effectiveDate = value;
  }
/**
 * Gets the value of member variable effectiveDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEffectiveDate() {
    return this.effectiveDate;
  }
/**
 * Sets the value of member variable endDate
 *
 * @author auto generated.
 */
  public void setEndDate(java.util.Date value) {
    this.endDate = value;
  }
/**
 * Gets the value of member variable endDate
 *
 * @author atuo generated.
 */
  public java.util.Date getEndDate() {
    return this.endDate;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}
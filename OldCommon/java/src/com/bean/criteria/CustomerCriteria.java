package com.bean.criteria;

import com.util.SystemException;


/**
 * Peer object that maps to the customer selection criteria
 *
 * @author Roy Terrell.
 */
public class CustomerCriteria extends AncestorQueryCriteria {

/** The javabean property equivalent of database column customerId */
  private int qry_CustomerId;
/** The javabean property equivalent of database column gl_accountId */
  private int qry_GlAccountId;
/** The javabean property equivalent of database column accountNo */
  private String qry_AccountNo;
/** The javabean property equivalent of database column personId */
  private int qry_PersonId;
/** The javabean property equivalent of database column BusinessId */
  private int qry_BusinessId;
/** The javabean property equivalent of database column creditLimit */
  private double qry_CreditLimit;
/** The javabean property equivalent of database column customerType */
  private String qry_CustomerType;
/** The javabean property equivalent of database column firstname */
  private String qry_Firstname;
/** The javabean property equivalent of database column midname */
  private String qry_Midname;
/** The javabean property equivalent of database column lastname */
  private String qry_Lastname;
/** The javabean property equivalent of database column maidenname */
  private String qry_Maidenname;
/** The javabean property equivalent of database column generation */
  private String qry_Generation;
/** The javabean property equivalent of database column title */
  private int qry_Title;
/** The javabean property equivalent of database column genderId */
  private int qry_GenderId;
/** The javabean property equivalent of database column maritalStatus */
  private int qry_MaritalStatus;
/** The javabean property equivalent of database column birthDate */
  private java.util.Date qry_BirthDate;
/** The javabean property equivalent of database column raceId */
  private int qry_RaceId;
/** The javabean property equivalent of database column ssn */
  private String qry_Ssn;
/** The javabean property equivalent of database column email */
  private String qry_Email;
/** The javabean property equivalent of database column bus_type */
  private int qry_BusType;
/** The javabean property equivalent of database column serv_type */
  private int qry_ServType;
/** The javabean property equivalent of database column longname */
  private String qry_Longname;
/** The javabean property equivalent of database column shortname */
  private String qry_Shortname;
/** The javabean property equivalent of database column contactFirstname */
  private String qry_ContactFirstname;
/** The javabean property equivalent of database column contactLastname */
  private String qry_ContactLastname;
/** The javabean property equivalent of database column contactPhone */
  private String qry_ContactPhone;
/** The javabean property equivalent of database column contactExt */
  private String qry_ContactExt;
/** The javabean property equivalent of database column taxId */
  private String qry_TaxId;
/** The javabean property equivalent of database column website */
  private String qry_Website;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author Roy Terrell.
 */
  public CustomerCriteria() throws SystemException {
	  super();
  }

  /**
   * Returns an instance of CustomerCriteria.
   * 
   * @return
   */
  public static CustomerCriteria getInstance() {
      try {
          return new CustomerCriteria();
      }
      catch (SystemException e) {
          return null;
      }
  }

  
  /**
   * Initializes all properties to spaces.

   *
   * @author Roy Terrell.
   */
  	public void initBean() throws SystemException {
  		super.initBean();
  		this.qry_CustomerId = 0;
  		this.qry_GlAccountId = 0;
  		this.qry_PersonId = 0;
  		this.qry_BusinessId = 0;
  		this.qry_CreditLimit = 0;
  		this.qry_Title = 0;
  		this.qry_GenderId = 0;
  		this.qry_MaritalStatus = 0;
  		this.qry_BirthDate = new java.util.Date();
  		this.qry_RaceId = 0;
  		this.qry_BusType = 0;
  		this.qry_ServType = 0;
  		this.qry_AccountNo = "";
  		this.qry_CustomerType = "";
  		this.qry_Firstname = "";
  		this.qry_Midname = "";
  		this.qry_Lastname = "";
  		this.qry_Maidenname = "";
  		this.qry_Generation = "";
  		this.qry_Ssn = "";
  		this.qry_Email = "";
  		this.qry_Longname = "";
  		this.qry_Shortname = "";
  		this.qry_ContactFirstname = "";
  		this.qry_ContactLastname = "";
  		this.qry_ContactPhone = "";
  		this.qry_ContactExt = "";
  		this.qry_TaxId = "";
  		this.qry_Website = "";
  	}
    
  	
/**
 * Sets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public void setQry_CustomerId(int value) {
    this.qry_CustomerId = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author Roy Terrell.
 */
  public int getQry_CustomerId() {
    return this.qry_CustomerId;
  }
/**
 * Sets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public void setQry_GlAccountId(int value) {
    this.qry_GlAccountId = value;
  }
/**
 * Gets the value of member variable glAccountId
 *
 * @author Roy Terrell.
 */
  public int getQry_GlAccountId() {
    return this.qry_GlAccountId;
  }
/**
 * Sets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public void setQry_AccountNo(String value) {
    this.qry_AccountNo = value;
  }
/**
 * Gets the value of member variable accountNo
 *
 * @author Roy Terrell.
 */
  public String getQry_AccountNo() {
    return this.qry_AccountNo;
  }
/**
 * Sets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public void setQry_PersonId(int value) {
    this.qry_PersonId = value;
  }
/**
 * Gets the value of member variable personId
 *
 * @author Roy Terrell.
 */
  public int getQry_PersonId() {
    return this.qry_PersonId;
  }
/**
 * Sets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public void setQry_BusinessId(int value) {
    this.qry_BusinessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author Roy Terrell.
 */
  public int getQry_BusinessId() {
    return this.qry_BusinessId;
  }
/**
 * Sets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public void setQry_CreditLimit(double value) {
    this.qry_CreditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 *
 * @author Roy Terrell.
 */
  public double getQry_CreditLimit() {
    return this.qry_CreditLimit;
  }
/**
 * Sets the value of member variable customerType
 *
 * @author Roy Terrell.
 */
  public void setQry_CustomerType(String value) {
    this.qry_CustomerType = value;
  }
/**
 * Gets the value of member variable customerType
 *
 * @author Roy Terrell.
 */
  public String getQry_CustomerType() {
    return this.qry_CustomerType;
  }
/**
 * Sets the value of member variable Firstname
 *
 * @author Roy Terrell.
 */
  public void setQry_Firstname(String value) {
    this.qry_Firstname = value;
  }
/**
 * Gets the value of member variable Firstname
 *
 * @author Roy Terrell.
 */
  public String getQry_Firstname() {
    return this.qry_Firstname;
  }
/**
 * Sets the value of member variable Midname
 *
 * @author Roy Terrell.
 */
  public void setQry_Midname(String value) {
    this.qry_Midname = value;
  }
/**
 * Gets the value of member variable Midname
 *
 * @author Roy Terrell.
 */
  public String getQry_Midname() {
    return this.qry_Midname;
  }
/**
 * Sets the value of member variable Lastname
 *
 * @author Roy Terrell.
 */
  public void setQry_Lastname(String value) {
    this.qry_Lastname = value;
  }
/**
 * Gets the value of member variable Lastname
 *
 * @author Roy Terrell.
 */
  public String getQry_Lastname() {
    return this.qry_Lastname;
  }
/**
 * Sets the value of member variable Maidenname
 *
 * @author Roy Terrell.
 */
  public void setQry_Maidenname(String value) {
    this.qry_Maidenname = value;
  }
/**
 * Gets the value of member variable Maidenname
 *
 * @author Roy Terrell.
 */
  public String getQry_Maidenname() {
    return this.qry_Maidenname;
  }
/**
 * Sets the value of member variable Generation
 *
 * @author Roy Terrell.
 */
  public void setQry_Generation(String value) {
    this.qry_Generation = value;
  }
/**
 * Gets the value of member variable Generation
 *
 * @author Roy Terrell.
 */
  public String getQry_Generation() {
    return this.qry_Generation;
  }
/**
 * Sets the value of member variable Title
 *
 * @author Roy Terrell.
 */
  public void setQry_Title(int value) {
    this.qry_Title = value;
  }
/**
 * Gets the value of member variable Title
 *
 * @author Roy Terrell.
 */
  public int getQry_Title() {
    return this.qry_Title;
  }
/**
 * Sets the value of member variable GenderId
 *
 * @author Roy Terrell.
 */
  public void setQry_GenderId(int value) {
    this.qry_GenderId = value;
  }
/**
 * Gets the value of member variable GenderId
 *
 * @author Roy Terrell.
 */
  public int getQry_GenderId() {
    return this.qry_GenderId;
  }
/**
 * Sets the value of member variable MaritalStatus
 *
 * @author Roy Terrell.
 */
  public void setQry_MaritalStatus(int value) {
    this.qry_MaritalStatus = value;
  }
/**
 * Gets the value of member variable MaritalStatus
 *
 * @author Roy Terrell.
 */
  public int getQry_MaritalStatus() {
    return this.qry_MaritalStatus;
  }
/**
 * Sets the value of member variable BirthDate
 *
 * @author Roy Terrell.
 */
  public void setQry_BirthDate(java.util.Date value) {
    this.qry_BirthDate = value;
  }
/**
 * Gets the value of member variable BirthDate
 *
 * @author Roy Terrell.
 */
  public java.util.Date getQry_BirthDate() {
    return this.qry_BirthDate;
  }
/**
 * Sets the value of member variable RaceId
 *
 * @author Roy Terrell.
 */
  public void setQry_RaceId(int value) {
    this.qry_RaceId = value;
  }
/**
 * Gets the value of member variable RaceId
 *
 * @author Roy Terrell.
 */
  public int getQry_RaceId() {
    return this.qry_RaceId;
  }
/**
 * Sets the value of member variable Ssn
 *
 * @author Roy Terrell.
 */
  public void setQry_Ssn(String value) {
    this.qry_Ssn = value;
  }
/**
 * Gets the value of member variable Ssn
 *
 * @author Roy Terrell.
 */
  public String getQry_Ssn() {
    return this.qry_Ssn;
  }
/**
 * Sets the value of member variable Email
 *
 * @author Roy Terrell.
 */
  public void setQry_Email(String value) {
    this.qry_Email = value;
  }
/**
 * Gets the value of member variable Email
 *
 * @author Roy Terrell.
 */
  public String getQry_Email() {
    return this.qry_Email;
  }
/**
 * Sets the value of member variable BusType
 *
 * @author Roy Terrell.
 */
  public void setQry_BusType(int value) {
    this.qry_BusType = value;
  }
/**
 * Gets the value of member variable BusType
 *
 * @author Roy Terrell.
 */
  public int getQry_BusType() {
    return this.qry_BusType;
  }
/**
 * Sets the value of member variable ServType
 *
 * @author Roy Terrell.
 */
  public void setQry_ServType(int value) {
    this.qry_ServType = value;
  }
/**
 * Gets the value of member variable ServType
 *
 * @author Roy Terrell.
 */
  public int getQry_ServType() {
    return this.qry_ServType;
  }
/**
 * Sets the value of member variable Longname
 *
 * @author Roy Terrell.
 */
  public void setQry_Longname(String value) {
    this.qry_Longname = value;
  }
/**
 * Gets the value of member variable Longname
 *
 * @author Roy Terrell.
 */
  public String getQry_Longname() {
    return this.qry_Longname;
  }
/**
 * Sets the value of member variable Shortname of a person or business
 *
 * @author Roy Terrell.
 */
  public void setQry_Shortname(String value) {
    this.qry_Shortname = value;
  }
/**
 * Gets the value of member variable Shortname of a person or business
 *
 * @author Roy Terrell.
 */
  public String getQry_Shortname() {
    return this.qry_Shortname;
  }
/**
 * Sets the value of member variable ContactFirstname
 *
 * @author Roy Terrell.
 */
  public void setQry_ContactFirstname(String value) {
    this.qry_ContactFirstname = value;
  }
/**
 * Gets the value of member variable ContactFirstname
 *
 * @author Roy Terrell.
 */
  public String getQry_ContactFirstname() {
    return this.qry_ContactFirstname;
  }
/**
 * Sets the value of member variable ContactLastname
 *
 * @author Roy Terrell.
 */
  public void setQry_ContactLastname(String value) {
    this.qry_ContactLastname = value;
  }
/**
 * Gets the value of member variable ContactLastname
 *
 * @author Roy Terrell.
 */
  public String getQry_ContactLastname() {
    return this.qry_ContactLastname;
  }
/**
 * Sets the value of member variable ContactPhone
 *
 * @author Roy Terrell.
 */
  public void setQry_ContactPhone(String value) {
    this.qry_ContactPhone = value;
  }
/**
 * Gets the value of member variable ContactPhone
 *
 * @author Roy Terrell.
 */
  public String getQry_ContactPhone() {
    return this.qry_ContactPhone;
  }
/**
 * Sets the value of member variable ContactExt
 *
 * @author Roy Terrell.
 */
  public void setQry_ContactExt(String value) {
    this.qry_ContactExt = value;
  }
/**
 * Gets the value of member variable ContactExt
 *
 * @author Roy Terrell.
 */
  public String getQry_ContactExt() {
    return this.qry_ContactExt;
  }
/**
 * Sets the value of member variable TaxId
 *
 * @author Roy Terrell.
 */
  public void setQry_TaxId(String value) {
    this.qry_TaxId = value;
  }
/**
 * Gets the value of member variable TaxId
 *
 * @author Roy Terrell.
 */
  public String getQry_TaxId() {
    return this.qry_TaxId;
  }
/**
 * Sets the value of member variable Website
 *
 * @author Roy Terrell.
 */
  public void setQry_Website(String value) {
    this.qry_Website = value;
  }
/**
 * Gets the value of member variable Website
 *
 * @author Roy Terrell.
 */
  public String getQry_Website() {
    return this.qry_Website;
  }
}
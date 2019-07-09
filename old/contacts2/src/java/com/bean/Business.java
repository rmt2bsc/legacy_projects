package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the business database table/view.
 *
 * @author auto generated.
 */
public class Business extends OrmBean {




	// Property name constants that belong to respective DataSource, BusinessView

/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, EntityTypeId, of respective DataSource view. */
  public static final String PROP_ENTITYTYPEID = "EntityTypeId";
/** The property name constant equivalent to property, ServTypeId, of respective DataSource view. */
  public static final String PROP_SERVTYPEID = "ServTypeId";
/** The property name constant equivalent to property, Longname, of respective DataSource view. */
  public static final String PROP_LONGNAME = "Longname";
/** The property name constant equivalent to property, Shortname, of respective DataSource view. */
  public static final String PROP_SHORTNAME = "Shortname";
/** The property name constant equivalent to property, ContactFirstname, of respective DataSource view. */
  public static final String PROP_CONTACTFIRSTNAME = "ContactFirstname";
/** The property name constant equivalent to property, ContactLastname, of respective DataSource view. */
  public static final String PROP_CONTACTLASTNAME = "ContactLastname";
/** The property name constant equivalent to property, ContactPhone, of respective DataSource view. */
  public static final String PROP_CONTACTPHONE = "ContactPhone";
/** The property name constant equivalent to property, ContactExt, of respective DataSource view. */
  public static final String PROP_CONTACTEXT = "ContactExt";
/** The property name constant equivalent to property, ContactEmail, of respective DataSource view. */
  public static final String PROP_CONTACTEMAIL = "ContactEmail";
/** The property name constant equivalent to property, TaxId, of respective DataSource view. */
  public static final String PROP_TAXID = "TaxId";
/** The property name constant equivalent to property, Website, of respective DataSource view. */
  public static final String PROP_WEBSITE = "Website";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, CategoryId, of respective DataSource view. */
  public static final String PROP_CATEGORYID = "CategoryId";



	/** The javabean property equivalent of database column business.business_id */
  private int businessId;
/** The javabean property equivalent of database column business.entity_type_id */
  private int entityTypeId;
/** The javabean property equivalent of database column business.serv_type_id */
  private int servTypeId;
/** The javabean property equivalent of database column business.longname */
  private String longname;
/** The javabean property equivalent of database column business.shortname */
  private String shortname;
/** The javabean property equivalent of database column business.contact_firstname */
  private String contactFirstname;
/** The javabean property equivalent of database column business.contact_lastname */
  private String contactLastname;
/** The javabean property equivalent of database column business.contact_phone */
  private String contactPhone;
/** The javabean property equivalent of database column business.contact_ext */
  private String contactExt;
/** The javabean property equivalent of database column business.contact_email */
  private String contactEmail;
/** The javabean property equivalent of database column business.tax_id */
  private String taxId;
/** The javabean property equivalent of database column business.website */
  private String website;
/** The javabean property equivalent of database column business.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column business.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column business.user_id */
  private String userId;
/** The javabean property equivalent of database column business.category_id */
  private int categoryId;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Business() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable businessId
 *
 * @author auto generated.
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 *
 * @author atuo generated.
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Sets the value of member variable entityTypeId
 *
 * @author auto generated.
 */
  public void setEntityTypeId(int value) {
    this.entityTypeId = value;
  }
/**
 * Gets the value of member variable entityTypeId
 *
 * @author atuo generated.
 */
  public int getEntityTypeId() {
    return this.entityTypeId;
  }
/**
 * Sets the value of member variable servTypeId
 *
 * @author auto generated.
 */
  public void setServTypeId(int value) {
    this.servTypeId = value;
  }
/**
 * Gets the value of member variable servTypeId
 *
 * @author atuo generated.
 */
  public int getServTypeId() {
    return this.servTypeId;
  }
/**
 * Sets the value of member variable longname
 *
 * @author auto generated.
 */
  public void setLongname(String value) {
    this.longname = value;
  }
/**
 * Gets the value of member variable longname
 *
 * @author atuo generated.
 */
  public String getLongname() {
    return this.longname;
  }
/**
 * Sets the value of member variable shortname
 *
 * @author auto generated.
 */
  public void setShortname(String value) {
    this.shortname = value;
  }
/**
 * Gets the value of member variable shortname
 *
 * @author atuo generated.
 */
  public String getShortname() {
    return this.shortname;
  }
/**
 * Sets the value of member variable contactFirstname
 *
 * @author auto generated.
 */
  public void setContactFirstname(String value) {
    this.contactFirstname = value;
  }
/**
 * Gets the value of member variable contactFirstname
 *
 * @author atuo generated.
 */
  public String getContactFirstname() {
    return this.contactFirstname;
  }
/**
 * Sets the value of member variable contactLastname
 *
 * @author auto generated.
 */
  public void setContactLastname(String value) {
    this.contactLastname = value;
  }
/**
 * Gets the value of member variable contactLastname
 *
 * @author atuo generated.
 */
  public String getContactLastname() {
    return this.contactLastname;
  }
/**
 * Sets the value of member variable contactPhone
 *
 * @author auto generated.
 */
  public void setContactPhone(String value) {
    this.contactPhone = value;
  }
/**
 * Gets the value of member variable contactPhone
 *
 * @author atuo generated.
 */
  public String getContactPhone() {
    return this.contactPhone;
  }
/**
 * Sets the value of member variable contactExt
 *
 * @author auto generated.
 */
  public void setContactExt(String value) {
    this.contactExt = value;
  }
/**
 * Gets the value of member variable contactExt
 *
 * @author atuo generated.
 */
  public String getContactExt() {
    return this.contactExt;
  }
/**
 * Sets the value of member variable contactEmail
 *
 * @author auto generated.
 */
  public void setContactEmail(String value) {
    this.contactEmail = value;
  }
/**
 * Gets the value of member variable contactEmail
 *
 * @author atuo generated.
 */
  public String getContactEmail() {
    return this.contactEmail;
  }
/**
 * Sets the value of member variable taxId
 *
 * @author auto generated.
 */
  public void setTaxId(String value) {
    this.taxId = value;
  }
/**
 * Gets the value of member variable taxId
 *
 * @author atuo generated.
 */
  public String getTaxId() {
    return this.taxId;
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
 * Sets the value of member variable categoryId
 *
 * @author auto generated.
 */
  public void setCategoryId(int value) {
    this.categoryId = value;
  }
/**
 * Gets the value of member variable categoryId
 *
 * @author atuo generated.
 */
  public int getCategoryId() {
    return this.categoryId;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}